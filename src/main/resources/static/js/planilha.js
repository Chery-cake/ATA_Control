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

                var div = document.getElementById("seletor");
                var label = document.createElement("label");
                label.textContent = "Este numero não possui ringues";
                div.appendChild(label);
            } else {
                ringue_atual = result[0];
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

                        var div = document.getElementById("seletor");
                        var label = document.createElement("label");
                        label.textContent = "Este numero não possui mais ringues";
                        div.appendChild(label);
                    } else {
                        for (i in result) {
                            if (result[i].numeroRodada === ringue_atual.numeroRodada + 1) {
                                ringue_atual = result[0];
                                break;
                            }
                        }
                        inicia_ringue();
                    }
                }
            });
        }
    });
});

var tipo_plan = null;

$(document).on("change", "select[id='planilha_select']", function () {
    var id_plan = $("#planilha_select").val().substr(0, 1);
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

            var button = document.createElement("button");
            button.id = "submit_lista";
            button.textContent = "Submit";
            button.className = " btn btn-lg btn-github btn-icon my-4";
            div.appendChild(button);

        }
    });
}

$(document).on("click", "button[id*='A_']", function () {
    var tr = document.getElementById("chaves").children[chave];
    var td = tr.children[2];
    td.textContent = $(this).val();
});

$(document).on("click", "button[id*='B_']", function () {
    var tr = document.getElementById("chaves").children[chave];
    var td = tr.children[4];
    td.textContent = $(this).val();
});

$(document).on("click", "button[id*='C_']", function () {
    var tr = document.getElementById("chaves").children[chave];
    var td = tr.children[3];
    td.textContent = $(this).val();
});

$(document).on("click", "button[id='submit_lista']", function () {
    var tr = document.getElementById("chaves").children[chave];
    tr.children[6].textContent = "true";
    tr.children[5].textContent = parseInt(tr.children[2].textContent) + parseInt(tr.children[3].textContent) + parseInt(tr.children[4].textContent);

    var data = {};

    data.nota_juiz_a = tr.children[2].textContent;
    data.nota_juiz_b = tr.children[3].textContent;
    data.nota_juiz_c = tr.children[4].textContent;

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

                var label = document.createElement("label");
                label.textContent = "Todos os competidores ja receberam suas notas"

                document.getElementById("planilha").appendChild(label);

                chave = 1;
            }
        }
    });

});

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
            th.textContent = "Cronometro";
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
                td.textContent = "02:00";
                tr.appendChild(td);

                td = document.createElement("td");
                if(result[i].competidorBranco === null){
                    td.textContent = "vazio";
                }else {
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


            // BOTOES VERMELHOS
            var row = document.createElement("div");
            row.className = "row";
            var col = document.createElement("div");
            col.className = "column";

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
            h4.textContent = "CRONOMETRO "
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

        }
    });
}

$(document).on("click", "button[id='ponto_vermelho']", function () {
    var tr = document.getElementById("chaves").children[chave];

    if ($(this).val() === "+") {
        tr.children[2].textContent = parseInt(tr.children[2].textContent) + 1;
    } else if ($(this).val() === "-") {
        tr.children[2].textContent = parseInt(tr.children[2].textContent) - 1;
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='advertencia_vermelha']", function () {
    var tr = document.getElementById("chaves").children[chave];

    if ($(this).val() === "+") {
        tr.children[3].textContent = parseInt(tr.children[3].textContent) + 1;
    } else if ($(this).val() === "-") {
        tr.children[3].textContent = parseInt(tr.children[3].textContent) - 1;
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='penalidade_vermelha']", function () {
    var tr = document.getElementById("chaves").children[chave];

    if ($(this).val() === "+") {
        tr.children[4].textContent = parseInt(tr.children[4].textContent) + 1;
    } else if ($(this).val() === "-") {
        tr.children[4].textContent = parseInt(tr.children[4].textContent) - 1;
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='ponto_branco']", function () {
    var tr = document.getElementById("chaves").children[chave];

    if ($(this).val() === "+") {
        tr.children[7].textContent = parseInt(tr.children[7].textContent) + 1;
    } else if ($(this).val() === "-") {
        tr.children[7].textContent = parseInt(tr.children[7].textContent) - 1;
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='advertencia_branca']", function () {
    var tr = document.getElementById("chaves").children[chave];

    if ($(this).val() === "+") {
        tr.children[8].textContent = parseInt(tr.children[8].textContent) + 1;
    } else if ($(this).val() === "-") {
        tr.children[8].textContent = parseInt(tr.children[8].textContent) - 1;
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='penalidade_branca']", function () {
    var tr = document.getElementById("chaves").children[chave];

    if ($(this).val() === "+") {
        tr.children[9].textContent = parseInt(tr.children[9].textContent) + 1;
    } else if ($(this).val() === "-") {
        tr.children[9].textContent = parseInt(tr.children[9].textContent) - 1;
    }

    saveChaveLuta();
});

$(document).on("click", "button[id='desqualificacao']", function () {
    var tr = document.getElementById("chaves").children[chave];

    var data = {};

    if ($(this).val() === "branco") {
        data.vermelha = false;
        data.branca = true;
    } else if ($(this).val() === "vermelho") {
        data.vermelha = true;
        data.branca = false;
    }

    $.ajax({
        method: "POST",
        url: "/chave/luta/individual/desqualificacao/" + $(tr).attr("id"),
        contentType: 'application/json',
        data: JSON.stringify(data)
    });

});

$(document).on("click", "button[id='submit_chave']", function () {
    var tr = document.getElementById("chaves").children[chave];
    tr.children[10].textContent = "true";

    saveCronometro(false);
    saveChaveLuta();

    if (tr.children[12].textContent !== "0") {
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
                                if (tr.children[6].textContent === "vazio") {
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
                            td.textContent = "02:00";
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
                    }
                });


                for (var i in document.getElementById("chaves").children) {
                    var tr = document.getElementById("chaves").children[i];
                    if (tr.children) {
                        if (tr.children[10].textContent === "false") {
                            chave = i;
                            break;
                        }
                    }
                }
                // var nova_chave = false;
                // for (var i in document.getElementById("chaves").children) {
                //     var tr = document.getElementById("chaves").children[i];
                //     if (tr.children) {
                //         if (tr.children[10].textContent === "false") {
                //             nova_chave = true;
                //             break;
                //         }
                //     }
                // }
                // if (nova_chave === false) {//todo adicionar funcao para remover os botoes para n modificar as chaves e confirmar q acabou todas as chaves
                //     document.getElementById("submit_chave").remove();
                //
                //     var label = document.createElement("label");
                //     label.textContent = "Todas as lutas ja abacaram"
                //
                //     document.getElementById("planilha").appendChild(label);
                //
                //     chave = 1;
                // }
            }
        });
    }

});

$(document).on("click", "button[id='cronometro']", function () {
    var tr = document.getElementById("chaves").children[chave];
    if ($(this).val() === "iniciar") {
        tr.children[11].textContent = "true";
        saveCronometro(true);
    } else {
        tr.children[11].textContent = "false";
        saveCronometro(false)
    }
});

function saveChaveLuta() {
    var tr = document.getElementById("chaves").children[chave];

    var data = {};

    data.pontos_vermelho = tr.children[2].textContent;
    data.advertencias_vermelhas = tr.children[3].textContent;
    data.penalidades_vermelhas = tr.children[4].textContent;
    data.pontos_brancos = tr.children[7].textContent;
    data.advertencias_brancas = tr.children[8].textContent;
    data.penalidades_brancas = tr.children[9].textContent;

    $.ajax({
        method: "POST",
        url: "/chave/luta/individual/" + $(tr).attr("id"),
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
            for (var i in document.getElementById("chaves").children) {
                var tr = document.getElementById("chaves").children[i];
                if (tr.children) {
                    if (tr.children[10].textContent === "false") {
                        chave = i;
                        break;
                    }
                }
            }
            var nova_chave = false;
            for (var i in document.getElementById("chaves").children) {
                var tr = document.getElementById("chaves").children[i];
                if (tr.children) {
                    if (tr.children[10].textContent === "false") {
                        nova_chave = true;
                        break;
                    }
                }
            }
        }
    });
}

function saveCronometro(rodando) {

    var tr = document.getElementById("chaves").children[chave];

    var data = {};

    data.rodando = rodando;
    data.tempo_mim = parseInt(tr.children[5].textContent.split(":")[0]);
    data.tempo_seg = parseInt(tr.children[5].textContent.split(":")[1]);

    $.ajax({
        method: "POST",
        url: "/ringue/individual/cronometro/save/" + ringue_atual.id,
        contentType: 'application/json',
        data: JSON.stringify(data),
    });

}

var countDown = setInterval(function () {
    if (tipo_plan === "chave") {
        if (document.getElementById("chaves").children[chave] != null) {

            var tr = document.getElementById("chaves").children[chave];
            if (tr.children[5].textContent !== "00:00") {
                if (tr.children[11].textContent === "true") {
                    var tempo_mim = parseInt(tr.children[5].textContent.split(":")[0]);
                    var tempo_seg = parseInt(tr.children[5].textContent.split(":")[1]);

                    tempo_seg--;
                    if (tempo_seg < 0) {
                        tempo_mim--;
                        tempo_seg = 59;
                    }
                    if (tempo_mim < 0) {
                        tempo_mim = 0;
                        tempo_seg = 0;
                    }

                    tr.children[5].textContent = tempo_mim.toString().padStart(2, "0") + ":" + tempo_seg.toString().padStart(2, "0");

                }
            }
        }
    }
}, 1000);