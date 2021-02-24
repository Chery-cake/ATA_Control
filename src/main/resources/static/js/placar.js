$.ajax({
    method: "POST",
    url: "/torneio/numero/ringues/individual/" + $("#torneio_id").val(),
    contentType: 'application/json',
    success: function (result) {
        var div = document.getElementById("seletor");
        var label = document.createElement("label");
        label.id = "numero_ringue_lab";
        label.textContent = "Numero do ringue:";
        div.appendChild(label);
        var select = document.createElement("select");
        select.id = "numero_ringue";
        select.className = "form-control";
        select.required = true;
        var option = document.createElement("option");
        option.value = "";
        option.disabled = true;
        option.selected = true;
        option.text = "Selecione";
        select.appendChild(option);
        for (var i = 1; i <= result; i++) {
            option = document.createElement("option");
            option.value = i;
            option.text = i;
            select.appendChild(option);
        }
        div.appendChild(select);
    }
});

var ringue_atual = {};
var placar = {};

$(document).on("change", "select[id='numero_ringue']", function () {
    document.getElementById("numero_ringue_lab").setAttribute("hidden", "hidden");
    document.getElementById("numero_ringue").setAttribute("hidden", "hidden");

    $.ajax({
        method: "POST",
        url: "/ringue/individual/lista/" + $("#numero_ringue").val(),
        contentType: 'application/json',
        success: function (result) {
            if (result == null || result.length === 0) {
                document.getElementById("numero_ringue_lab").removeAttribute("hidden");
                document.getElementById("numero_ringue").removeAttribute("hidden");

                var div = document.getElementById("seletor");
                var label = document.createElement("label");
                label.textContent = "Este numero não possui ringues";
                div.appendChild(label);
            } else {
                ringue_atual = result[0];
                placar = ringue_atual.placar;
                setPlan();
            }
        }
    });
});

var tipo_plan = null;

var chave = 1;

function monta_plan_lista(id_plan) {
    chave = 1;
    $.ajax({
        method: "POST",
        url: "/planilha/individual/lista/competidores/" + id_plan,
        contentType: 'application/json',
        success: function (result) {

            if (JSON.stringify(plan) !== JSON.stringify(result)) {

                while (document.getElementById("planilha").firstChild) {
                    document.getElementById("planilha").firstChild.remove();
                }

                plan = result;

                var div = document.getElementById("planilha");
                var table = document.createElement("table");
                table.className = "table";
                table.id = "chaves";

                var tr = document.createElement("tr");
                var th = document.createElement("th");
                th.textContent = "Sequência";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Nome";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Juiz A";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Juiz C";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Juiz B";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Soma";
                tr.appendChild(th);

                th = document.createElement("th");
                th.hidden = true;
                th.textContent = true;
                tr.appendChild(th);
                table.appendChild(tr);

                for (var i in result) {
                    tr = document.createElement("tr");
                    tr.id = result[i].id;

                    var td = document.createElement("td");
                    td.textContent = (parseInt(i) + 1);
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].competidor.pessoa.nome + " " + result[i].competidor.pessoa.sobrenome;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].notaJuizA;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].notaJuizC;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].notaJuizB;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].soma;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.hidden = true;
                    td.textContent = false;
                    tr.appendChild(td);
                    table.appendChild(tr);
                }

                div.appendChild(table);

            }
        }
    });
}

var fase = 0;
var posicao = 0;

function monta_plan_chave(id_plan) {
    chave = 1;
    $.ajax({
        method: "POST",
        url: "/planilha/individual/chave/competidores/" + id_plan,
        contentType: 'application/json',
        success: function (result) {

            if (JSON.stringify(plan) !== JSON.stringify(result)) {

                while (document.getElementById("planilha").firstChild) {
                    document.getElementById("planilha").firstChild.remove();
                }

                plan = result;

                var div = document.getElementById("planilha");

                var crono = document.createElement("div");
                crono.id = "cronometro";
                div.appendChild(crono);

                crono.className = "row d-flex justify-content-center bg-white text-center";
                var div_cronometro = document.createElement("div");
                div_cronometro.id = "cronometro";
                var h4 = document.createElement("h4");
                h4.textContent = "CRONOMETRO";
                div_cronometro.appendChild(h4);

                var h4 = document.createElement("h4");
                h4.textContent = "02:00";
                h4.id = "tempo";
                div_cronometro.appendChild(h4);

                crono.appendChild(div_cronometro);

                var table = document.createElement("table");
                table.className = "table ";
                table.id = "chaves";

                var tr = document.createElement("tr");
                var th = document.createElement("th");
                th.textContent = "Sequência";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Competidor 1";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Pontos";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Ad";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Pena";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Competidor 2";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Pontos";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Ad";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Pena";
                tr.appendChild(th);

                td = document.createElement("td");
                td.hidden = true;
                td.textContent = true;
                tr.appendChild(td);

                td = document.createElement("td");
                td.hidden = true;
                td.textContent = true;
                tr.appendChild(td);
                table.appendChild(tr);

                for (var i in result) {
                    tr = document.createElement("tr");
                    tr.id = result[i].id;

                    var td = document.createElement("td");
                    td.textContent = table.children.length;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].competidorVermelho.pessoa.nome + " " + result[i].competidorVermelho.pessoa.sobrenome;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].pontosVermelhos;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].advertenciasVermelhas;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].penalidadesVermelhas;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    if (result[i].competidorBranco === null) {
                        td.textContent = "vazio";
                    } else {
                        td.textContent = result[i].competidorBranco.pessoa.nome + " " + result[i].competidorBranco.pessoa.sobrenome;
                    }
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].pontosBrancos;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].advertenciasBrancas;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = result[i].penalidadesBrancas;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.hidden = true;
                    td.textContent = false;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.hidden = true;
                    td.textContent = false;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.hidden = true;
                    td.textContent = result[i].fase;
                    tr.appendChild(td);
                    table.appendChild(tr);
                }

                div.appendChild(table);

            }
        }
    });
}

var cronometro_obj = {};

function getCronometro() {

    $.ajax({
        method: "POST",
        url: "/ringue/individual/cronometro/get/" + ringue_atual.id,
        success: function (response) {
            cronometro_obj = response;
        }
    });

}

setInterval(function () {

    if (Object.keys(ringue_atual).length !== 0) {
        $.ajax({
            method: "POST",
            url: "/ringue/individual/get/" + ringue_atual.id,
            success: function (response) {
                ringue_atual = response;
                placar = ringue_atual.placar;
            }
        });
    }

    setPlan();
}, 1000);

setInterval(function () {
    if (tipo_plan === "chave") {
        if (document.getElementById("chaves").children[chave] != null) {

            var h4 = document.getElementById("tempo");
            if (h4.textContent !== "00:00") {
                if (Object.keys(cronometro_obj).length !== 0 && cronometro_obj.rodando === true) {
                    var tempo_mim = parseInt(h4.textContent.split(":")[0]);
                    var tempo_seg = parseInt(h4.textContent.split(":")[1]);

                    tempo_seg--;
                    if (tempo_seg < 0) {
                        tempo_mim--;
                        tempo_seg = 59;
                    }
                    if (tempo_mim < 0) {
                        tempo_mim = 0;
                        tempo_seg = 0;
                    }

                    h4.textContent = tempo_mim.toString().padStart(2, "0") + ":" + tempo_seg.toString().padStart(2, "0");

                }else if(Object.keys(cronometro_obj).length !== 0 && cronometro_obj.rodando === false){
                    var h4 = document.getElementById("tempo");
                    h4.textContent = cronometro_obj.tempo_mim.toString().padStart(2, "0") + ":" + cronometro_obj.tempo_seg.toString().padStart(2, "0");
                }
            }
        }
    }
}, 1000);

var plan = {};

function setPlan() {
    if (tipo_plan !== placar.tipo_plan) {
        tipo_plan = placar.tipo_plan;
    }
    if (tipo_plan === "lista") {
        monta_plan_lista(placar.id_plan);
    } else if (tipo_plan === "chave") {
        monta_plan_chave(placar.id_plan);
        getCronometro();
    }
}