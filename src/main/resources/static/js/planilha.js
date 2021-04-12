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
                inicia_ringue();
            }
        }
    });
});

var planilhas_lista = {};
var planilhas_chave = {};

function inicia_ringue() {
    var div = document.getElementById("seletor");
    var label = document.createElement("label");
    label.id = "planilha_select_lb";
    label.textContent = "Planilha:";
    div.appendChild(label);
    var select = document.createElement("select");
    select.id = "planilha_select";
    select.className = "form-control";
    select.required = true;
    var option = document.createElement("option");
    option.value = "";
    option.disabled = true;
    option.selected = true;
    option.text = "Selecione";
    select.appendChild(option);
    $.ajax({
        method: "POST",
        url: "/ringue/individual/lista/planilhas/lista/" + ringue_atual.id,
        contentType: 'application/json',
        success: function (result) {
            planilhas_lista = result;
            select = document.getElementById("planilha_select");
            for (i in result) {
                option = document.createElement("option");
                option.value = result[i].id + "-" + result[i].categoriaCompeticao.tipoChave;
                option.text = result[i].categoriaCompeticao.nome;
                select.appendChild(option);
            }
        }
    });
    $.ajax({
        method: "POST",
        url: "/ringue/individual/lista/planilhas/chave/" + ringue_atual.id,
        contentType: 'application/json',
        success: function (result) {
            planilhas_chave = result;
            select = document.getElementById("planilha_select");
            for (i in result) {
                option = document.createElement("option");
                option.value = result[i].id + "-" + result[i].categoriaCompeticao.tipoChave;
                option.text = result[i].categoriaCompeticao.nome;
                select.appendChild(option);
            }
        }
    });
    div.appendChild(select);

    while (document.getElementById("planilha").firstChild) {
        document.getElementById("planilha").firstChild.remove();
    }
}

$("#ter_ringue").on("click", function () {
    $.ajax({
        method: "POST",
        url: "/ringue/individual/finalizar/" + ringue_atual.id,
        success: function () {
            document.getElementById("planilha_select").remove();
            document.getElementById("planilha_select_lb").remove();
            $.ajax({
                method: "POST",
                url: "/ringue/individual/lista/" + $("#numero_ringue").val(),
                contentType: 'application/json',
                success: function (result) {
                    if (result == null || result.length === 0) {
                        document.getElementById("numero_ringue_lab").removeAttribute("hidden");
                        document.getElementById("numero_ringue").removeAttribute("hidden");

                        if (!document.getElementById("not_ring")) {
                            var div = document.getElementById("seletor");
                            var label = document.createElement("label");
                            label.id = "not_ring";
                            label.textContent = "Este numero não possui mais ringues";
                            div.appendChild(label);
                        }
                        while (document.getElementById("planilha").firstChild) {
                            document.getElementById("planilha").firstChild.remove();
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
                        inicia_ringue();
                    }
                }
            });
        }
    });
});

var tipo_plan = null;
var id_plan = null;

$(document).on("change", "select[id='planilha_select']", function () {
    id_plan = $("#planilha_select").val().substr(0, 1);
    var tipo_chave = $("#planilha_select").val().substr(2);

    while (document.getElementById("planilha").firstChild) {
        document.getElementById("planilha").firstChild.remove();
    }

    if (tipo_chave === "true") {
        monta_plan_chave(id_plan);
        tipo_plan = "chave";
    } else if (tipo_chave === "false") {
        tipo_plan = "lista";
        monta_plan_lista(id_plan);
    }

    var data = {};

    data.id_plan = id_plan;
    data.tipo_plan = tipo_plan;

    $.ajax({
        method: "POST",
        url: "/ringue/individual/placar/save/" + ringue_atual.id,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
        }
    });

});

var chave = 1;

function monta_plan_lista(id_plan) {
    chave = 1;
    $.ajax({
        method: "POST",
        url: "/planilha/individual/lista/competidores/" + id_plan,
        contentType: 'application/json',
        success: function (result) {
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

            var possivel_dar_nota = true;
            var aux = -1;

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
                if (result[i].soma !== 0) {
                    td.textContent = true;
                    aux++;
                } else {
                    td.textContent = false;
                }
                tr.appendChild(td);
                table.appendChild(tr);
            }
            if (aux.toString() === i.toString()) {
                possivel_dar_nota = false;
            }

            div.appendChild(table);

            var div_A = document.createElement("div");
            div_A.id = "juiz_A";
            var label = document.createElement("label");
            label.textContent = "Nota Juiz A: "
            div_A.appendChild(label);
            for (var i = 1; i < 10; i++) {
                var button = document.createElement("button");
                button.id = "A_" + i;
                button.textContent = i;
                button.value = i;
                button.className = "btn btn-info btn-icon btn-sm";
                div_A.appendChild(button);
            }
            div.appendChild(div_A);

            var div_C = document.createElement("div");
            div_C.id = "juiz_C";
            var label = document.createElement("label");
            label.textContent = "Nota Juiz C: "
            div_C.appendChild(label);
            for (var i = 1; i < 10; i++) {
                var button = document.createElement("button");
                button.id = "C_" + i;
                button.textContent = i;
                button.value = i;
                button.className = "btn btn-info btn-icon btn-sm";
                div_C.appendChild(button);
            }
            div.appendChild(div_C);

            var div_B = document.createElement("div");
            div_B.id = "juiz_B";
            var label = document.createElement("label");
            label.textContent = "Nota Juiz B: "
            div_B.appendChild(label);
            for (var i = 1; i < 10; i++) {
                var button = document.createElement("button");
                button.id = "B_" + i;
                button.textContent = i;
                button.value = i;
                button.className = "btn btn-info btn-icon btn-sm";
                div_B.appendChild(button);
            }
            div.appendChild(div_B);

            if (possivel_dar_nota === true) {
                var button = document.createElement("button");
                button.id = "submit_lista";
                button.textContent = "Submit";
                button.className = " btn btn-lg btn-github btn-icon my-4";
                div.appendChild(button);
            } else {
                var label = document.createElement("label");
                label.textContent = "Todos os competidores ja receberam suas notas";
                document.getElementById("juiz_B").remove();
                document.getElementById("juiz_A").remove();
                document.getElementById("juiz_C").remove();
                div.appendChild(label);
            }

        }
    });
}

$(document).on("click", "button[id*='A_']", function () {
    var tr = document.getElementById("chaves").children[chave];
    var td = tr.children[2];
    if (tr.children[6].textContent !== "true") {
        td.textContent = $(this).val();
    }
});

$(document).on("click", "button[id*='B_']", function () {
    var tr = document.getElementById("chaves").children[chave];
    var td = tr.children[4];
    if (tr.children[6].textContent !== "true") {
        td.textContent = $(this).val();
    }
});

$(document).on("click", "button[id*='C_']", function () {
    var tr = document.getElementById("chaves").children[chave];
    var td = tr.children[3];
    if (tr.children[6].textContent !== "true") {
        td.textContent = $(this).val();
    }
});

$(document).on("click", "button[id='submit_lista']", function () {
    var tr = document.getElementById("chaves").children[chave];
    tr.children[6].textContent = "true";
    tr.children[5].textContent = parseInt(tr.children[2].textContent) + parseInt(tr.children[3].textContent) + parseInt(tr.children[4].textContent);

    var incompleto = false;
    var data = {};

    data.nota_juiz_a = tr.children[2].textContent;
    data.nota_juiz_b = tr.children[4].textContent;
    data.nota_juiz_c = tr.children[3].textContent;

    if (data.nota_juiz_a === "0") {
        incompleto = true;
        tr.children[6].textContent = "false";
        tr.children[5].textContent = "0";
    }
    if (data.nota_juiz_b === "0") {
        incompleto = true;
        tr.children[6].textContent = "false";
        tr.children[5].textContent = "0";
    }
    if (data.nota_juiz_c === "0") {
        incompleto = true;
        tr.children[6].textContent = "false";
        tr.children[5].textContent = "0";
    }

    if (incompleto === false) {
        $.ajax({
            method: "POST",
            url: "/chave/lista/individual/" + $(tr).attr("id"),
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                for (var i in document.getElementById("chaves").children) {
                    var tr = document.getElementById("chaves").children[i];
                    if (tr.children) {
                        if (tr.children[6].textContent === "false") {
                            chave = i;
                            break;
                        }
                    }
                }
                var nova_chave = false;
                for (var i in document.getElementById("chaves").children) {
                    var tr = document.getElementById("chaves").children[i];
                    if (tr.children) {
                        if (tr.children[6].textContent === "false") {
                            nova_chave = true;
                            break;
                        }
                    }
                }
                if (nova_chave === false) {
                    document.getElementById("submit_lista").remove();

                    document.getElementById("juiz_B").remove();
                    document.getElementById("juiz_A").remove();
                    document.getElementById("juiz_C").remove();

                    empate_lista();

                    var label = document.createElement("label");
                    label.textContent = "Todos os competidores ja receberam suas notas";
                    document.getElementById("planilha").appendChild(label);

                    chave = 1;
                }
            }
        });
    }

});

var lugar_def_1 = 0;
var lugar_def_2 = 0;
var lugar_def_3 = 0;

function empate_lista() {

    var lugar_1 = [];
    var nota_1 = 0;

    var lugar_2 = [];
    var nota_2 = 0;

    var lugar_3 = [];
    var nota_3 = 0;

    var change = true;
    while (change) {
        change = false;
        for (var i = 1; i < document.getElementById("chaves").children.length; i++) {
            var tr = document.getElementById("chaves").children[i];
            var nota = parseInt(tr.children[5].textContent);
            if (nota > nota_1) {
                lugar_1 = [];
                lugar_1.push(tr.id);
                change = true;
                nota_1 = nota;
            } else if (nota === nota_1) {
                if (!inArray(tr.id, lugar_1)) {
                    lugar_1.push(tr.id);
                }
            } else if (nota > nota_2) {
                lugar_2 = [];
                lugar_2.push(tr.id);
                change = true;
                nota_2 = nota;
            } else if (nota === nota_2) {
                if (!inArray(tr.id, lugar_2)) {
                    lugar_2.push(tr.id);
                }
            } else if (nota > nota_3) {
                lugar_3 = [];
                lugar_3.push(tr.id);
                change = true;
                nota_3 = nota;
            } else if (nota === nota_3) {
                if (!inArray(tr.id, lugar_3)) {
                    lugar_3.push(tr.id);
                }
            }
        }
    }

    lugar_def_1 = lugar_1[0];
    lugar_def_2 = lugar_2[0];
    lugar_def_3 = lugar_3[0];

    var emp = false;

    if (lugar_1.length > 1 || lugar_2.length > 1 || lugar_3.length > 1) {
        emp = true;
    } else {
        save_colocacao();
    }

    var emp_1 = false;
    var emp_2 = false;

    var div_prin = document.createElement("div");
    div_prin.id = "div_emp";

    if (lugar_1.length >= 3) {
        emp_2 = true;

        for (var q = 1; q <= 3; q++) {

            var div = document.createElement("div");

            var label = document.createElement("label");
            label.textContent = "Selecionar competidor para " + q + "º colocação: ";
            div.appendChild(label);

            var select = document.createElement("select");
            select.id = "select_lugar_" + q;
            select.className = "form-control";
            select.required = true;

            var option = document.createElement("option");
            option.value = "";
            option.disabled = true;
            option.selected = true;
            option.text = "Selecione";
            select.appendChild(option);

            for (var i = 0; i < lugar_1.length; i++) {
                var tr;
                for (var j = 1; j < document.getElementById("chaves").children.length; j++) {
                    var aux = document.getElementById("chaves").children[j];
                    if (aux.id === lugar_1[i]) {
                        tr = aux;
                        break;
                    }
                }

                option = document.createElement("option");
                option.value = lugar_1[i];
                option.text = tr.children[1].textContent;
                select.appendChild(option);
            }

            div.appendChild(select);

            div_prin.appendChild(div);
        }

    } else if (lugar_1.length === 2) {
        emp_1 = true;

        lugar_def_3 = lugar_def_2;

        for (var q = 1; q <= 2; q++) {

            var div = document.createElement("div");

            var label = document.createElement("label");
            label.textContent = "Selecionar competidor para " + q + "º colocação: ";
            div.appendChild(label);

            var select = document.createElement("select");
            select.id = "select_lugar_" + q;
            select.className = "form-control";
            select.required = true;

            var option = document.createElement("option");
            option.value = "";
            option.disabled = true;
            option.selected = true;
            option.text = "Selecione";
            select.appendChild(option);

            for (var i = 0; i < lugar_1.length; i++) {
                var tr;
                for (var j = 1; j < document.getElementById("chaves").children.length; j++) {
                    var aux = document.getElementById("chaves").children[j];
                    if (aux.id === lugar_1[i]) {
                        tr = aux;
                        break;
                    }
                }

                option = document.createElement("option");
                option.value = lugar_1[i];
                option.text = tr.children[1].textContent;
                select.appendChild(option);
            }

            div.appendChild(select);

            div_prin.appendChild(div);
        }

    }

    if (lugar_2.length >= 2 && !emp_2) {
        emp_2 = true;

        if (emp_1) {
            for (var q = 3; q <= 3; q++) {

                var div = document.createElement("div");

                var label = document.createElement("label");
                label.textContent = "Selecionar competidor para " + q + "º colocação: ";
                div.appendChild(label);

                var select = document.createElement("select");
                select.id = "select_lugar_" + q;
                select.className = "form-control";
                select.required = true;

                var option = document.createElement("option");
                option.value = "";
                option.disabled = true;
                option.selected = true;
                option.text = "Selecione";
                select.appendChild(option);

                for (var i = 0; i < lugar_2.length; i++) {
                    var tr;
                    for (var j = 1; j < document.getElementById("chaves").children.length; j++) {
                        var aux = document.getElementById("chaves").children[j];
                        if (aux.id === lugar_2[i]) {
                            tr = aux;
                            break;
                        }
                    }

                    option = document.createElement("option");
                    option.value = lugar_2[i];
                    option.text = tr.children[1].textContent;
                    select.appendChild(option);
                }

                div.appendChild(select);

                div_prin.appendChild(div);
            }
        } else {

            for (var q = 2; q <= 3; q++) {

                var div = document.createElement("div");

                var label = document.createElement("label");
                label.textContent = "Selecionar competidor para " + q + "º colocação: ";
                div.appendChild(label);

                var select = document.createElement("select");
                select.id = "select_lugar_" + q;
                select.className = "form-control";
                select.required = true;

                var option = document.createElement("option");
                option.value = "";
                option.disabled = true;
                option.selected = true;
                option.text = "Selecione";
                select.appendChild(option);

                for (var i = 0; i < lugar_2.length; i++) {
                    var tr;
                    for (var j = 1; j < document.getElementById("chaves").children.length; j++) {
                        var aux = document.getElementById("chaves").children[j];
                        if (aux.id === lugar_2[i]) {
                            tr = aux;
                            break;
                        }
                    }

                    option = document.createElement("option");
                    option.value = lugar_2[i];
                    option.text = tr.children[1].textContent;
                    select.appendChild(option);
                }

                div.appendChild(select);

                div_prin.appendChild(div);
            }

        }
    }

    if (lugar_3.length >= 2 && !emp_2) {

        for (var q = 3; q <= 3; q++) {

            var div = document.createElement("div");

            var label = document.createElement("label");
            label.textContent = "Selecionar competidor para " + q + "º colocação: ";
            div.appendChild(label);

            var select = document.createElement("select");
            select.id = "select_lugar_" + q;
            select.className = "form-control";
            select.required = true;

            var option = document.createElement("option");
            option.value = "";
            option.disabled = true;
            option.selected = true;
            option.text = "Selecione";
            select.appendChild(option);

            for (var i = 0; i < lugar_3.length; i++) {
                var tr;
                for (var j = 1; j < document.getElementById("chaves").children.length; j++) {
                    var aux = document.getElementById("chaves").children[j];
                    if (aux.id === lugar_3[i]) {
                        tr = aux;
                        break;
                    }
                }

                option = document.createElement("option");
                option.value = lugar_3[i];
                option.text = tr.children[1].textContent;
                select.appendChild(option);
            }

            div.appendChild(select);

            div_prin.appendChild(div);
        }

    }

    if (emp) {
        var div = document.createElement("div");

        var button = document.createElement("button");
        button.id = "finalizar_emp";
        button.textContent = "Finalizar empates";
        button.className = "btn btn-lg btn-github btn-icon my-4";
        div.appendChild(button);

        div_prin.appendChild(div);
    }

    document.getElementById("planilha").appendChild(div_prin);
}

$(document).on("click", "button[id='finalizar_emp']", function () {
    if (document.getElementById("select_lugar_1")) {
        lugar_def_1 = $("#select_lugar_1").val();
    }
    if (document.getElementById("select_lugar_2")) {
        lugar_def_2 = $("#select_lugar_2").val();
    }
    if (document.getElementById("select_lugar_3")) {
        lugar_def_3 = $("#select_lugar_3").val();
    }

    save_colocacao();
    $("#div_emp").remove();
});

function save_colocacao() {

    var data = {};

    data.lugar_1 = lugar_def_1;
    data.lugar_2 = lugar_def_2;
    data.lugar_3 = lugar_def_3;

    $.ajax({
        method: "POST",
        url: "/planilha/lista/individual/colocacao/" + id_plan,
        contentType: 'application/json',
        data: JSON.stringify(data)
    });
}

function inArray(obj, array) {
    for (var aux in array) {
        if (array.hasOwnProperty(aux) && array[aux] === obj) {
            return true;
        }
    }
    return false;
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

            var finalizou_todas_chaves = 0;

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

                var vazio = false;
                td = document.createElement("td");
                if (result[i].competidorBranco === null) {
                    vazio = true;
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
                td.textContent = result[i].finalizado;
                tr.appendChild(td);

                td = document.createElement("td");
                td.hidden = true;
                td.textContent = result[i].fase;
                tr.appendChild(td);

                td = document.createElement("td");
                td.hidden = true;
                td.textContent = vazio;
                tr.appendChild(td);
                table.appendChild(tr);

                if (result[i].finalizado === true && result[i].fase === 0) {
                    finalizou_todas_chaves++;
                }
            }

            div.appendChild(table);

            if (finalizou_todas_chaves !== 2) {
                // BOTOES VERMELHOS
                var row = document.createElement("div");
                row.className = "row";
                var col = document.createElement("div");
                col.className = "column";
                col.id = "botoes_vermelhos";

                var table = document.createElement("table");
                table.className = "table-bordered";
                var tr = document.createElement("tr");

                var th = document.createElement("th");
                var label = document.createElement("label");
                label.textContent = "Ponto vermelho: "
                th.appendChild(label);
                tr.appendChild(th);

                var th = document.createElement("th");
                var button = document.createElement("button");
                button.id = "ponto_vermelho";
                button.textContent = "";
                button.value = "+";
                button.className = "btn btn-success btn-icon btn-sm ni ni-fat-add"
                th.appendChild(button);
                tr.appendChild(th);

                var th = document.createElement("th");
                button = document.createElement("button");
                button.id = "ponto_vermelho";
                button.textContent = "";
                button.value = "-";
                button.className = "btn btn-danger btn-icon btn-sm ni ni-fat-delete"
                th.appendChild(button);   //tr.append(th)
                tr.appendChild(th);

                tr.appendChild(th);  //tr
                table.appendChild(tr);


                var tr = document.createElement("tr");

                var th = document.createElement("th");
                var label = document.createElement("label");
                label.textContent = "Advertencia vermelha: "
                th.appendChild(label);
                tr.appendChild(th);

                var th = document.createElement("th");
                var button = document.createElement("button");
                button.id = "advertencia_vermelha";
                button.textContent = "";
                button.value = "+";
                button.className = "btn btn-success btn-icon btn-sm ni ni-fat-add"
                th.appendChild(button);
                tr.appendChild(th);

                var th = document.createElement("th");
                button = document.createElement("button");
                button.id = "advertencia_vermelha";
                button.textContent = "";
                button.value = "-";
                button.className = "btn btn-danger btn-icon btn-sm ni ni-fat-delete"
                th.appendChild(button);
                tr.appendChild(th);

                tr.appendChild(th);
                table.appendChild(tr);

                var tr = document.createElement("tr");

                var th = document.createElement("th");
                var label = document.createElement("label");
                label.textContent = "Penalidade vermelha: "
                th.appendChild(label);
                tr.appendChild(th);

                var th = document.createElement("th");
                var button = document.createElement("button");
                button.id = "penalidade_vermelha";
                button.textContent = "";
                button.value = "+";
                button.className = "btn btn-success btn-icon btn-sm ni ni-fat-add"
                th.appendChild(button);
                tr.appendChild(th);

                var th = document.createElement("th");
                button = document.createElement("button");
                button.id = "penalidade_vermelha";
                button.textContent = "";
                button.value = "-";
                button.className = "btn btn-danger btn-icon btn-sm ni ni-fat-delete"
                th.appendChild(button);
                tr.appendChild(th);

                tr.appendChild(th);
                table.appendChild(tr);

                var tr = document.createElement("tr");

                var th = document.createElement("th");
                var label = document.createElement("label");
                label.textContent = "Ações"
                th.appendChild(label);
                tr.appendChild(th);
                var th = document.createElement("th");
                var button = document.createElement("button");
                button.id = "desqualificacao";
                button.textContent = "Desqualificar";
                button.value = "vermelho";
                button.className = "btn btn-default";
                th.appendChild(button);
                tr.appendChild(th);
                var th = document.createElement("th");
                th.className = "bg-ttbyu"
                tr.appendChild(th);


                table.appendChild(tr);
                div.appendChild(table);
                col.appendChild(table);
                row.appendChild(col);
                div.appendChild(col);
                div.appendChild(row);
                // BOTOES BRANCOS

                var table = document.createElement("table");
                table.className = "table-bordered";
                var col = document.createElement("div");
                col.className = "column";
                col.id = "botoes_brancos";

                var tr = document.createElement("tr");

                var th = document.createElement("th");

                var label = document.createElement("label");
                label.textContent = "Ponto branco: "
                th.appendChild(label);
                tr.appendChild(th);

                var th = document.createElement("th");
                var button = document.createElement("button");
                button.id = "ponto_branco";
                button.textContent = "";
                button.value = "+";
                button.className = "btn btn-success btn-icon btn-sm ni ni-fat-add"
                th.appendChild(button);
                tr.appendChild(th);

                var th = document.createElement("th");
                button = document.createElement("button");
                button.id = "ponto_branco";
                button.textContent = "";
                button.value = "-";
                button.className = "btn btn-danger btn-icon btn-sm ni ni-fat-delete"
                th.appendChild(button);
                tr.appendChild(th);

                tr.appendChild(th);
                table.appendChild(tr);


                var tr = document.createElement("tr");

                var th = document.createElement("th");
                var label = document.createElement("label");
                label.textContent = "Advertencia branca: "
                th.appendChild(label);
                tr.appendChild(th);

                var th = document.createElement("th");
                var button = document.createElement("button");
                button.id = "advertencia_branca";
                button.textContent = "";
                button.value = "+";
                button.className = "btn btn-success btn-icon btn-sm ni ni-fat-add"
                th.appendChild(button);
                tr.appendChild(th);

                var th = document.createElement("th");
                button = document.createElement("button");
                button.id = "advertencia_branca";
                button.textContent = "";
                button.value = "-";
                button.className = "btn btn-danger btn-icon btn-sm ni ni-fat-delete"
                th.appendChild(button);
                tr.appendChild(th);

                tr.appendChild(th);
                table.appendChild(tr);


                var tr = document.createElement("tr");

                var th = document.createElement("th");
                var label = document.createElement("label");
                label.textContent = "Penalidade branca: "
                th.appendChild(label);
                tr.appendChild(th);

                var th = document.createElement("th");
                var button = document.createElement("button");
                button.id = "penalidade_branca";
                button.textContent = "";
                button.value = "+";
                button.className = "btn btn-success btn-icon btn-sm ni ni-fat-add"
                th.appendChild(button);
                tr.appendChild(th);

                var th = document.createElement("th");
                button = document.createElement("button");
                button.id = "penalidade_branca";
                button.textContent = "";
                button.value = "-";
                button.className = "btn btn-danger btn-icon btn-sm ni ni-fat-delete"
                th.appendChild(button);
                tr.appendChild(th);

                tr.appendChild(th);
                table.appendChild(tr);


                var tr = document.createElement("tr");

                var th = document.createElement("th");
                var label = document.createElement("label");
                label.textContent = "Ações"
                th.appendChild(label);
                tr.appendChild(th);

                var th = document.createElement("th");
                var button = document.createElement("button");
                button.id = "desqualificacao";
                button.textContent = "Desqualificar";
                button.value = "branco";
                button.className = "btn btn-default";
                th.appendChild(button);
                tr.appendChild(th);
                var th = document.createElement("th");
                th.className = "bg-ttbyu"
                tr.appendChild(th);


                table.appendChild(tr);
                div.appendChild(table);
                col.appendChild(table);
                row.appendChild(col);
                div.appendChild(col);
                div.appendChild(row);


                // FIM DOS BOTÕES

                var crono = document.createElement("div");
                crono.className = "row d-flex justify-content-center bg-white text-center";
                var div_cronometro = document.createElement("div");
                div_cronometro.id = "cronometro";
                var h4 = document.createElement("h4");
                h4.textContent = "CRONOMETRO";
                div_cronometro.appendChild(h4);

                var h4 = document.createElement("h4");
                h4.textContent = "02:00";
                h4.id = "cronometro_timer";
                div_cronometro.appendChild(h4);

                var button = document.createElement("button");
                button.id = "cronometro";
                button.textContent = "Iniciar";
                button.value = "iniciar";
                button.className = "btn btn-info mr-4";
                div_cronometro.appendChild(button);

                button = document.createElement("button");
                button.id = "cronometro";
                button.textContent = "Pausar";
                button.value = "pausar";
                button.className = "btn btn-info mr-4";
                div_cronometro.appendChild(button);

                div.appendChild(div_cronometro);
                // crono.appendChild(div_cronometro);
                // div.appendChild(crono);

                button = document.createElement("button");
                button.id = "submit_chave";
                button.textContent = "Submit";
                button.className = " btn btn-lg btn-github btn-icon my-4 text-center"
                div.appendChild(button);

            } else {
                var label = document.createElement("label");
                label.textContent = "Todas as lutas ja abacaram";

                document.getElementById("planilha").appendChild(label);
            }
        }
    });
}

$(document).on("click", "button[id='ponto_vermelho']", function () {
    var tr = document.getElementById("chaves").children[chave];

    var skip = true;
    var loop = true;

    while (loop) {
        loop = false;

        if (tr.children[9].textContent === "true" && tr.children[10].textContent !== "0") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0" && Object.keys(document.getElementById("chaves").children[chave + 1]).length !== 0 && document.getElementById("chaves").children[chave + 1].children[9].textContent === "false") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0") {
            skip = false;
        }
    }

    if ($(this).val() === "+" && skip) {
        tr.children[2].textContent = parseInt(tr.children[2].textContent) + 1;
    } else if ($(this).val() === "-" && skip) {
        if ((parseInt(tr.children[2].textContent) - 1) >= 0) {
            tr.children[2].textContent = parseInt(tr.children[2].textContent) - 1;
        }
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='advertencia_vermelha']", function () {
    var tr = document.getElementById("chaves").children[chave];

    var skip = true;
    var loop = true;

    while (loop) {
        loop = false;

        if (tr.children[9].textContent === "true" && tr.children[10].textContent !== "0") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0" && Object.keys(document.getElementById("chaves").children[chave + 1]).length !== 0 && document.getElementById("chaves").children[chave + 1].children[9].textContent === "false") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0") {
            skip = false;
        }
    }

    if ($(this).val() === "+" && skip) {
        tr.children[3].textContent = parseInt(tr.children[3].textContent) + 1;
    } else if ($(this).val() === "-" && skip) {
        if ((parseInt(tr.children[3].textContent) - 1) >= 0) {
            tr.children[3].textContent = parseInt(tr.children[3].textContent) - 1;
        }
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='penalidade_vermelha']", function () {
    var tr = document.getElementById("chaves").children[chave];

    var skip = true;
    var loop = true;

    while (loop) {
        loop = false;

        if (tr.children[9].textContent === "true" && tr.children[10].textContent !== "0") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0" && Object.keys(document.getElementById("chaves").children[chave + 1]).length !== 0 && document.getElementById("chaves").children[chave + 1].children[9].textContent === "false") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0") {
            skip = false;
        }
    }

    if ($(this).val() === "+" && skip) {
        tr.children[4].textContent = parseInt(tr.children[4].textContent) + 1;
    } else if ($(this).val() === "-" && skip) {
        if ((parseInt(tr.children[4].textContent) - 1) >= 0) {
            tr.children[4].textContent = parseInt(tr.children[4].textContent) - 1;
        }
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='ponto_branco']", function () {
    var tr = document.getElementById("chaves").children[chave];

    var skip = true;
    var loop = true;

    while (loop) {
        loop = false;

        if (tr.children[9].textContent === "true" && tr.children[10].textContent !== "0") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0" && Object.keys(document.getElementById("chaves").children[chave + 1]).length !== 0 && document.getElementById("chaves").children[chave + 1].children[9].textContent === "false") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0") {
            skip = false;
        }
    }

    if ($(this).val() === "+" && skip) {
        tr.children[6].textContent = parseInt(tr.children[6].textContent) + 1;
    } else if ($(this).val() === "-" && skip) {
        if ((parseInt(tr.children[6].textContent) - 1) >= 0) {
            tr.children[6].textContent = parseInt(tr.children[6].textContent) - 1;
        }
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='advertencia_branca']", function () {
    var tr = document.getElementById("chaves").children[chave];

    var skip = true;
    var loop = true;

    while (loop) {
        loop = false;

        if (tr.children[9].textContent === "true" && tr.children[10].textContent !== "0") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0" && Object.keys(document.getElementById("chaves").children[chave + 1]).length !== 0 && document.getElementById("chaves").children[chave + 1].children[9].textContent === "false") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0") {
            skip = false;
        }
    }

    if ($(this).val() === "+" && skip) {
        tr.children[8].textContent = parseInt(tr.children[7].textContent) + 1;
    } else if ($(this).val() === "-" && skip) {
        if ((parseInt(tr.children[7].textContent) - 1) >= 0) {
            tr.children[8].textContent = parseInt(tr.children[7].textContent) - 1;
        }
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='penalidade_branca']", function () {
    var tr = document.getElementById("chaves").children[chave];

    var skip = true;
    var loop = true;

    while (loop) {
        loop = false;

        if (tr.children[9].textContent === "true" && tr.children[10].textContent !== "0") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0" && Object.keys(document.getElementById("chaves").children[chave + 1]).length !== 0 && document.getElementById("chaves").children[chave + 1].children[9].textContent === "false") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0") {
            skip = false;
        }
    }

    if ($(this).val() === "+" && skip) {
        tr.children[8].textContent = parseInt(tr.children[8].textContent) + 1;
    } else if ($(this).val() === "-" && skip) {
        if ((parseInt(tr.children[8].textContent) - 1) >= 0) {
            tr.children[8].textContent = parseInt(tr.children[8].textContent) - 1;
        }
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='desqualificacao']", function () {
    var tr = document.getElementById("chaves").children[chave];

    var skip = true;
    var loop = true;

    while (loop) {
        loop = false;

        if (tr.children[9].textContent === "true" && tr.children[10].textContent !== "0") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0" && Object.keys(document.getElementById("chaves").children[chave + 1]).length !== 0 && document.getElementById("chaves").children[chave + 1].children[9].textContent === "false") {
            chave++;
            tr = document.getElementById("chaves").children[chave];
            loop = true;
        } else if (tr.children[9].textContent === "true" && tr.children[10].textContent === "0") {
            skip = false;
        }
    }

    var data = {};

    if ($(this).val() === "branco" && skip) {
        data.vermelha = false;
        data.branca = true;
    } else if ($(this).val() === "vermelho" && skip) {
        data.vermelha = true;
        data.branca = false;
    }

    if (skip) {
        $.ajax({
            method: "POST",
            url: "/chave/luta/individual/desqualificacao/" + $(tr).attr("id"),
            contentType: 'application/json',
            data: JSON.stringify(data)
        });
        submit_chave();
    }

});

function submit_chave() {
    var tr = document.getElementById("chaves").children[chave];
    tr.children[9].textContent = "true";

    saveCronometro(false);
    saveChaveLuta();

    $.ajax({
        method: "POST",
        url: "/chave/luta/individual/avancar/" + $(tr).attr("id"),
        contentType: 'application/json',
        success: function (fase) {

            var data = {};
            data.fase = fase;
            data.id_plan = $("#planilha_select").val().substr(0, 1);

            $.ajax({
                method: "POST",
                url: "/planilha/individual/chave/fase/competidores",
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (result) {
                    var table = document.getElementById("chaves");

                    var aux = [];
                    for (var i in table.children) {
                        tr = table.children[i];
                        if (tr.children) {
                            if (tr.children[5].textContent === "vazio" && tr.children[11].textContent === "false" && result.length !== 0) {
                                aux.push(table.children[i]);
                            }
                        }
                    }

                    if (aux !== []) {
                        for (var i in aux) {
                            for (var z in table.children) {
                                if (table.children[z] === aux[i]) {
                                    table.children[z].remove();
                                    break;
                                }
                            }
                        }
                    }

                    for (var i in result) {
                        tr = document.createElement("tr");
                        tr.id = result[i].id;

                        var td = document.createElement("td");
                        td.textContent = document.getElementById("chaves").children.length;
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
                        td.textContent = result[i].finalizado;
                        tr.appendChild(td);

                        td = document.createElement("td");
                        td.hidden = true;
                        td.textContent = result[i].fase;
                        tr.appendChild(td);

                        td = document.createElement("td");
                        td.hidden = true;
                        td.textContent = false;
                        tr.appendChild(td);
                        table.appendChild(tr);
                    }
                }
            });


            for (var i in document.getElementById("chaves").children) {
                var tr = document.getElementById("chaves").children[i];
                if (tr.children) {
                    if (tr.children[9].textContent === "false") {
                        chave = i;
                        break;
                    }
                }
            }
            var nova_chave = false;
            for (var i in document.getElementById("chaves").children) {
                var tr = document.getElementById("chaves").children[i];
                if (tr.children) {
                    if (tr.children[9].textContent === "false") {
                        nova_chave = true;
                        break;
                    }
                }
            }

            if (nova_chave === false) {

                document.getElementById("submit_chave").remove();
                document.getElementById("botoes_vermelhos").remove();
                document.getElementById("botoes_brancos").remove();
                document.getElementById("cronometro").remove();

                saveCronometro(false);

                var label = document.createElement("label");
                label.textContent = "Todas as lutas ja abacaram";

                document.getElementById("planilha").appendChild(label);

                chave = 1;
            }
        }
    });
}

$(document).on("click", "button[id='submit_chave']", function () {
    submit_chave();
});

var crono_rodando = false;

$(document).on("click", "button[id='cronometro']", function () {
    if ($(this).val() === "iniciar") {
        crono_rodando = true;
        saveCronometro(true);
    } else if ($(this).val() === "pausar") {
        crono_rodando = false;
        saveCronometro(false)
    }
});

function saveChaveLuta() {
    var tr = document.getElementById("chaves").children[chave];

    var data = {};

    data.pontos_vermelho = tr.children[2].textContent;
    data.advertencias_vermelhas = tr.children[3].textContent;
    data.penalidades_vermelhas = tr.children[4].textContent;
    data.pontos_brancos = tr.children[6].textContent;
    data.advertencias_brancas = tr.children[7].textContent;
    data.penalidades_brancas = tr.children[8].textContent;

    $.ajax({
        method: "POST",
        url: "/chave/luta/individual/" + $(tr).attr("id"),
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
            for (var i in document.getElementById("chaves").children) {
                var tr = document.getElementById("chaves").children[i];
                if (tr.children) {
                    if (tr.children[9].textContent === "false") {
                        chave = i;
                        break;
                    }
                }
            }
            var nova_chave = false;
            for (var i in document.getElementById("chaves").children) {
                var tr = document.getElementById("chaves").children[i];
                if (tr.children) {
                    if (tr.children[9].textContent === "false") {
                        nova_chave = true;
                        break;
                    }
                }
            }
        }
    });
}

function saveCronometro(rodando) {

    var h4 = document.getElementById("cronometro_timer");

    if (h4 !== null) {

        var data = {};

        data.rodando = rodando;
        data.tempo_mim = parseInt(h4.textContent.split(":")[0]);
        data.tempo_seg = parseInt(h4.textContent.split(":")[1]);

        $.ajax({
            method: "POST",
            url: "/ringue/individual/cronometro/save/" + ringue_atual.id,
            contentType: 'application/json',
            data: JSON.stringify(data),
        });

    }

}

setInterval(function () {
    if (tipo_plan === "chave") {

        var tr = document.getElementById("chaves").children[chave];
        if (tr.children[5].textContent === "vazio") {
            var data = {};

            data.vermelha = false;
            data.branca = true;

            $.ajax({
                method: "POST",
                url: "/chave/luta/individual/desqualificacao/" + $(tr).attr("id"),
                contentType: 'application/json',
                data: JSON.stringify(data)
            });
            submit_chave();
        }

        if (document.getElementById("chaves").children[chave] !== null) {

            var h4 = document.getElementById("cronometro_timer");
            if (h4 !== null && h4.textContent !== "00:00") {
                if (crono_rodando === true) {
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

                }
            }
        }
    }
}, 1000);