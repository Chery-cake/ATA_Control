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
    if (document.getElementById("menssagem")) {
        document.getElementById("menssagem").remove();
    }

    $.ajax({
        method: "POST",
        url: "/ringue/individual/lista/" + $("#numero_ringue").val(),
        contentType: 'application/json',
        success: function (result) {
            if (result == null || result.length === 0) {
                document.getElementById("numero_ringue_lab").removeAttribute("hidden");
                document.getElementById("numero_ringue").removeAttribute("hidden");

                if (!document.getElementById("menssagem")) {
                    var div = document.getElementById("seletor");
                    var label = document.createElement("label");
                    label.textContent = "Este numero não possui ringues";
                    label.id = "menssagem";
                    div.appendChild(label);
                }
            } else {
                var ringue_menor_rodada = result[0];
                var roda = true;

                while (roda) {
                    roda = false;
                    for (i in result) {
                        if (parseInt(result[i].rodadaJuiz.inicio.split(":")[0]) < parseInt(ringue_menor_rodada.rodadaJuiz.inicio.split(":")[0])) {
                            ringue_menor_rodada = result[i];
                            roda = true;
                        } else if (parseInt(result[i].rodadaJuiz.inicio.split(":")[0]) === parseInt(ringue_menor_rodada.rodadaJuiz.inicio.split(":")[0])) {
                            if (parseInt(result[i].rodadaJuiz.inicio.split(":")[1]) < parseInt(ringue_menor_rodada.rodadaJuiz.inicio.split(":")[1])) {
                                ringue_menor_rodada = result[i];
                                roda = true;
                            }
                        }
                    }
                }

                ringue_atual = ringue_menor_rodada;
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
                    td.textContent = "9." + result[i].notaJuizA;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = "9." + result[i].notaJuizC;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = "9." + result[i].notaJuizB;
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

                var table = document.createElement("table");
                table.className = "table ";
                table.id = "chaves";

                var tr = document.createElement("tr");
                var th = document.createElement("th");
                th.textContent = "Sequência";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Competidor V";
                th.style = "color: white; background-color: red;";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Pontos";
                th.style = "color: white; background-color: red;";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Ad";
                th.style = "color: white; background-color: red;";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Pena";
                th.style = "color: white; background-color: red;";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Competidor B";
                th.style = "color: black; background-color: white;";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Pontos";
                th.style = "color: black; background-color: white;";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Ad";
                th.style = "color: black; background-color: white;";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Pena";
                th.style = "color: black; background-color: white;";
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

function monta_chave_luta(id_chave) {
    $.ajax({
        method: "POST",
        url: "/planilha/individual/chave/luta/" + id_chave,
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
                th.textContent = "Competidor";
                th.style = "color: white; background-color: red;";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Pontos";
                th.style = "color: white; background-color: red;";
                tr.appendChild(th);

                th = document.createElement("th");
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Competidor";
                th.style = "color: black; background-color: white;";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Pontos";
                th.style = "color: black; background-color: white;";
                tr.appendChild(th);
                table.appendChild(tr);

                tr = document.createElement("tr");

                var td = document.createElement("td");
                td.textContent = result.competidorVermelho.pessoa.nome + " " + result.competidorVermelho.pessoa.sobrenome;
                tr.appendChild(td);

                td = document.createElement("td");
                td.textContent = result.pontosVermelhos;
                tr.appendChild(td);

                td = document.createElement("td");
                tr.appendChild(td);

                td = document.createElement("td");
                if (result.competidorBranco === null) {
                    td.textContent = "vazio";
                } else {
                    td.textContent = result.competidorBranco.pessoa.nome + " " + result.competidorBranco.pessoa.sobrenome;
                }
                tr.appendChild(td);

                td = document.createElement("td");
                td.textContent = result.pontosBrancos;
                tr.appendChild(td);
                table.appendChild(tr);

                tr = document.createElement("tr");

                th = document.createElement("th");
                th.textContent = "Advertencias";
                th.style = "color: white; background-color: red;";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Penalidades";
                th.style = "color: white; background-color: red;";
                tr.appendChild(th);

                th = document.createElement("th");
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Advertencias";
                th.style = "color: black; background-color: white;";
                tr.appendChild(th);

                th = document.createElement("th");
                th.textContent = "Penalidades";
                th.style = "color: black; background-color: white;";
                tr.appendChild(th);
                table.appendChild(tr);

                tr = document.createElement("tr");

                var td = document.createElement("td");
                td.textContent = result.advertenciasVermelhas;
                tr.appendChild(td);

                td = document.createElement("td");
                td.textContent = result.penalidadesVermelhas;
                tr.appendChild(td);

                td = document.createElement("td");
                tr.appendChild(td);

                td = document.createElement("td");
                td.textContent = result.advertenciasBrancas;
                tr.appendChild(td);

                td = document.createElement("td");
                td.textContent = result.penalidadesBrancas;
                tr.appendChild(td);
                table.appendChild(tr);

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

var ha_ring = true;

setInterval(function () {

    if (ha_ring === false) {
        if (document.getElementById("not_ring")) {
            ha_ring = false;
        } else {
            var div = document.getElementById("seletor");
            var label = document.createElement("label");
            label.id = "not_ring";
            label.textContent = "Não há mais ringues";
            div.appendChild(label);
        }
    } else if (ringue_atual.finalizado === true) {
        $.ajax({
            method: "POST",
            url: "/ringue/individual/lista/" + $("#numero_ringue").val(),
            contentType: 'application/json',
            success: function (result) {
                if (result == null || result.length === 0) {

                    while (document.getElementById("planilha").firstChild) {
                        document.getElementById("planilha").firstChild.remove();
                    }

                    if (document.getElementById("not_ring")) {
                        ha_ring = false;
                    } else {
                        var div = document.getElementById("seletor");
                        var label = document.createElement("label");
                        label.id = "not_ring";
                        label.textContent = "Não há mais ringues";
                        div.appendChild(label);
                    }
                } else {
                    var mesma_rodada = false;

                    for (i in result) {
                        if (parseInt(result[i].rodadaJuiz.id) === parseInt(ringue_atual.rodadaJuiz.id)) {
                            mesma_rodada = true;
                        }
                    }

                    if (mesma_rodada) {
                        var roda = true;

                        while (roda) {
                            roda = false;
                            for (i in result) {
                                if (parseInt(result[i].rodadaJuiz.id) === parseInt(ringue_atual.rodadaJuiz.id)) {
                                    if (parseInt(result[i].numeroRodada) === (parseInt(ringue_atual.numeroRodada) + 1)) {
                                        ringue_atual = result[i];
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        var ringue_menor_rodada = result[0];
                        var roda = true;

                        while (roda) {
                            roda = false;
                            for (i in result) {
                                if (parseInt(result[i].rodadaJuiz.inicio.split(":")[0]) < parseInt(ringue_menor_rodada.rodadaJuiz.inicio.split(":")[0])) {
                                    ringue_menor_rodada = result[i];
                                    roda = true;
                                } else if (parseInt(result[i].rodadaJuiz.inicio.split(":")[0]) === parseInt(ringue_menor_rodada.rodadaJuiz.inicio.split(":")[0])) {
                                    if (parseInt(result[i].rodadaJuiz.inicio.split(":")[1]) < parseInt(ringue_menor_rodada.rodadaJuiz.inicio.split(":")[1])) {
                                        ringue_menor_rodada = result[i];
                                        roda = true;
                                    }
                                }
                            }
                        }

                        ringue_atual = ringue_menor_rodada;
                    }
                    setPlan();
                }
            }
        });
    } else if (Object.keys(ringue_atual).length !== 0) {
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

                } else if (Object.keys(cronometro_obj).length !== 0 && cronometro_obj.rodando === false) {
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
    } else if (Number.isInteger(placar.id_chave)) {
        monta_chave_luta(placar.id_chave);
        getCronometro();
    } else if (tipo_plan === "chave") {
        monta_plan_chave(placar.id_plan);
    } else if (Object.keys(ringue_atual).length !== 0) {
        info_ringue();
    }
}

function info_ringue() {
    while (document.getElementById("planilha").firstChild) {
        document.getElementById("planilha").firstChild.remove();
    }

    var div = document.getElementById("planilha");

    var ringue = document.createElement("div");
    div.appendChild(ringue);

    ringue.className = "row d-flex justify-content-center bg-white text-center";
    var div_ringue = document.createElement("div");

    var idade;

    switch (ringue_atual.idade) {
        case 1:
            idade = "7 e 8";
            break;
        case 2:
            idade = "9 e 10";
            break;
        case 3:
            idade = "11 e 12";
            break;
        case 4:
            idade = "13 e 14";
            break;
        case 5:
            idade = "15 a 17";
            break;
        case 6:
            idade = "18 a 29";
            break;
        case 7:
            idade = "30 a 39";
            break;
        case 8:
            idade = "40 a 49";
            break;
        case 9:
            idade = "50 a 59";
            break;
        case 10:
            idade = "60 acima";
            break;
        default:
            idade = "7 e 8";
            break;
    }

    var nivel;

    switch (ringue_atual.nivel) {
        case 0:
            nivel = "1 -/- Faixas: Branca, Laranja, Amarela";
            break;
        case 1:
            nivel = "2 -/- Faixas: Camuflada, Verde, Roxa";
            break;
        case 2:
            nivel = "3 -/- Faixas: Aluz, Marron, Vermelha";
            break;
        case 3:
            nivel = "4 -/- Faixas: Vermelha e Preta";
            break;
        case 4:
            nivel = "5 -/- Faixas: 1º Dan";
            break;
        case 5:
            nivel = "6 -/- Faixas: 2º Dan e 3º Dan";
            break;
        case 6:
            nivel = "7 -/- Faixas: 4º Dan e 5º Dan";
            break;
        case 7:
            nivel = "8 -/- Faixas: 6º Dan e 7º Dan";
            break;
        case 8:
            nivel = "9 -/- Faixas: 8º Dan e 9º Dan";
            break;
        default:
            nivel = "1 -/- Faixas: Branca, Laranja, Amarela";
            break;
    }

    var h4 = document.createElement("h4");
    h4.textContent = "Idade: " + idade;
    div_ringue.appendChild(h4);

    var h4 = document.createElement("h4");
    h4.textContent = "Nivel: " + nivel;
    div_ringue.appendChild(h4);

    var h4 = document.createElement("h4");
    if (ringue_atual.genero) {
        h4.textContent = "Gênero: Masculino";
    } else {
        h4.textContent = "Gênero: Feminino";
    }
    div_ringue.appendChild(h4);

    ringue.appendChild(div_ringue);

    var div_juiz = document.createElement("div");

    var juiz = document.createElement("div");
    div.appendChild(juiz);
    juiz.className = "row d-flex justify-content-center bg-white text-center";

    var h4 = document.createElement("h4");
    h4.textContent = "Juizes";
    juiz.appendChild(h4);

    div_juiz.appendChild(juiz);

    var table_juiz = document.createElement("table");
    table_juiz.className = "table text-center";

    var tr = document.createElement("tr");

    for (var i in ringue_atual.juiz) {
        var td = document.createElement("th");
        td.textContent = ringue_atual.juiz[i].pessoa.nome + " " + ringue_atual.juiz[i].pessoa.sobrenome;
        tr.appendChild(td);
    }

    table_juiz.appendChild(tr);

    var div_comp = document.createElement("div");

    var comp = document.createElement("div");
    div.appendChild(comp);
    comp.className = "row d-flex justify-content-center bg-white text-center";

    var h4 = document.createElement("h4");
    h4.textContent = "Competidores";
    comp.appendChild(h4);

    div_comp.appendChild(comp);

    var table_comp = document.createElement("table");
    table_comp.className = "table text-center";

    var aux = 0;
    var tr = document.createElement("tr");

    for (var i in ringue_atual.competidor) {
        var td = document.createElement("th");
        td.textContent = ringue_atual.competidor[i].pessoa.nome + " " + ringue_atual.competidor[i].pessoa.sobrenome;
        tr.appendChild(td);
        aux++;
        if (aux === 3) {
            table_comp.appendChild(tr);
            tr = document.createElement("tr");
            aux = 0;
        }
    }

    table_comp.appendChild(tr);

    div_juiz.appendChild(table_juiz);
    div_comp.appendChild(table_comp);

    div.appendChild(div_juiz);
    div.appendChild(div_comp);
}