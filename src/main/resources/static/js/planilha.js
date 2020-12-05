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
            console.log("planilha chave: ");
            console.log(result);
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

$(document).on("change", "select[id='planilha_select']", function () {
    var id_plan = $("#planilha_select").val().substr(0, 1);
    var tipo_chave = $("#planilha_select").val().substr(2);

    while (document.getElementById("planilha").firstChild) {
        document.getElementById("planilha").firstChild.remove();
    }

    if (tipo_chave === "true") {
        monta_plan_chave(id_plan);
    } else if (tipo_chave === "false") {
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
            table.className = "table-bordered";
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
                div_B.appendChild(button);
            }
            div.appendChild(div_B);

            var button = document.createElement("button");
            button.id = "submit_lista";
            button.textContent = "Submit";
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
                document.getElementById("submit").remove();

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
            console.log("chaves luta:");
            console.log(result);

            var div = document.getElementById("planilha");
            var table = document.createElement("table");
            table.className = "table-bordered";
            table.id = "chaves";

            var tr = document.createElement("tr");
            var th = document.createElement("th");
            th.textContent = "Sequência";
            tr.appendChild(th);

            th = document.createElement("th");
            th.textContent = "Competidor Vermelho";
            tr.appendChild(th);

            th = document.createElement("th");
            th.textContent = "Pontos";
            tr.appendChild(th);

            th = document.createElement("th");
            th.textContent = "Advertencias";
            tr.appendChild(th);

            th = document.createElement("th");
            th.textContent = "Penalidades";
            tr.appendChild(th);

            th = document.createElement("th");
            th.textContent = "Cronometro";
            tr.appendChild(th);

            th = document.createElement("th");
            th.textContent = "Competidor Branco";
            tr.appendChild(th);

            th = document.createElement("th");
            th.textContent = "Pontos";
            tr.appendChild(th);

            th = document.createElement("th");
            th.textContent = "Advertencias";
            tr.appendChild(th);

            th = document.createElement("th");
            th.textContent = "Penalidades";
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
                td.textContent = "00:00";
                tr.appendChild(td);

                td = document.createElement("td");
                td.textContent = result[i].competidorBranco.pessoa.nome + " " + result[i].competidorBranco.pessoa.sobrenome;
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
                table.appendChild(tr);
            }

            div.appendChild(table);

            var div_ponto_vermelho = document.createElement("div");
            div_ponto_vermelho.id = "ponto_vermelho";
            var label = document.createElement("label");
            label.textContent = "Ponto vermelho: "
            div_ponto_vermelho.appendChild(label);

            var button = document.createElement("button");
            button.id = "ponto_vermelho";
            button.textContent = "+";
            button.value = "+";
            div_ponto_vermelho.appendChild(button);

            button = document.createElement("button");
            button.id = "ponto_vermelho";
            button.textContent = "-";
            button.value = "-";
            div_ponto_vermelho.appendChild(button);

            div.appendChild(div_ponto_vermelho);

            var div_advertencia_vermelha = document.createElement("div");
            div_advertencia_vermelha.id = "advertencia_vermelha";
            var label = document.createElement("label");
            label.textContent = "Advertencia vermelha: "
            div_advertencia_vermelha.appendChild(label);

            var button = document.createElement("button");
            button.id = "advertencia_vermelha";
            button.textContent = "+";
            button.value = "+";
            div_advertencia_vermelha.appendChild(button);

            button = document.createElement("button");
            button.id = "advertencia_vermelha";
            button.textContent = "-";
            button.value = "-";
            div_advertencia_vermelha.appendChild(button);

            div.appendChild(div_advertencia_vermelha);

            var div_penalidade_vermelha = document.createElement("div");
            div_penalidade_vermelha.id = "penalidade_vermelha";
            var label = document.createElement("label");
            label.textContent = "Penalidade vermelha: "
            div_penalidade_vermelha.appendChild(label);

            var button = document.createElement("button");
            button.id = "penalidade_vermelha";
            button.textContent = "+";
            button.value = "+";
            div_penalidade_vermelha.appendChild(button);

            button = document.createElement("button");
            button.id = "penalidade_vermelha";
            button.textContent = "-";
            button.value = "-";
            div_penalidade_vermelha.appendChild(button);

            div.appendChild(div_penalidade_vermelha);

            var div_extra_vermelho = document.createElement("div");
            div_extra_vermelho.id = "extra_vermelho";
            var label = document.createElement("label");
            label.textContent = "Competidor vermelho: "
            div_extra_vermelho.appendChild(label);

            var button = document.createElement("button");
            button.id = "desqualificacao";
            button.textContent = "Competidor desqualificado";
            button.value = "vermelho";
            div_extra_vermelho.appendChild(button);

            button = document.createElement("button");
            button.id = "vencedor";
            button.textContent = "Competidor ganhador";
            button.value = "vermelho";
            div_extra_vermelho.appendChild(button);

            div.appendChild(div_extra_vermelho);

            var div_ponto_branco = document.createElement("div");
            div_ponto_branco.id = "ponto_branco";
            var label = document.createElement("label");
            label.textContent = "Ponto branco: "
            div_ponto_branco.appendChild(label);

            var button = document.createElement("button");
            button.id = "ponto_branco";
            button.textContent = "+";
            button.value = "+";
            div_ponto_branco.appendChild(button);

            button = document.createElement("button");
            button.id = "ponto_branco";
            button.textContent = "-";
            button.value = "-";
            div_ponto_branco.appendChild(button);

            div.appendChild(div_ponto_branco);

            var div_advertencia_branca = document.createElement("div");
            div_advertencia_branca.id = "advertencia_branca";
            var label = document.createElement("label");
            label.textContent = "Advertencia branca: "
            div_advertencia_branca.appendChild(label);

            var button = document.createElement("button");
            button.id = "advertencia_branca";
            button.textContent = "+";
            button.value = "+";
            div_advertencia_branca.appendChild(button);

            button = document.createElement("button");
            button.id = "advertencia_branca";
            button.textContent = "-";
            button.value = "-";
            div_advertencia_branca.appendChild(button);

            div.appendChild(div_advertencia_branca);

            var div_penalidade_branca = document.createElement("div");
            div_penalidade_branca.id = "penalidade_branca";
            var label = document.createElement("label");
            label.textContent = "Penalidade branca: "
            div_penalidade_branca.appendChild(label);

            var button = document.createElement("button");
            button.id = "penalidade_branca";
            button.textContent = "+";
            button.value = "+";
            div_penalidade_branca.appendChild(button);

            button = document.createElement("button");
            button.id = "penalidade_branca";
            button.textContent = "-";
            button.value = "-";
            div_penalidade_branca.appendChild(button);

            div.appendChild(div_penalidade_branca);

            var div_extra_branco = document.createElement("div");
            div_extra_branco.id = "extra_branco";
            var label = document.createElement("label");
            label.textContent = "Competidor branco: "
            div_extra_branco.appendChild(label);

            var button = document.createElement("button");
            button.id = "desqualificacao";
            button.textContent = "Competidor desqualificado";
            button.value = "branco";
            div_extra_branco.appendChild(button);

            button = document.createElement("button");
            button.id = "vencedor";
            button.textContent = "Competidor ganhador";
            button.value = "branco";
            div_extra_branco.appendChild(button);

            div.appendChild(div_extra_branco);

            var div_cronometro = document.createElement("div");
            div_cronometro.id = "cronometro";
            var label = document.createElement("label");
            label.textContent = "Cronometro: "
            div_cronometro.appendChild(label);

            var button = document.createElement("button");
            button.id = "cronometro";
            button.textContent = "Iniciar";
            button.value = "iniciar";
            div_cronometro.appendChild(button);

            button = document.createElement("button");
            button.id = "cronometro";
            button.textContent = "Pausar";
            button.value = "pausar";
            div_cronometro.appendChild(button);

            div.appendChild(div_cronometro);

            button = document.createElement("button");
            button.id = "submit_chave";
            button.textContent = "Submit";
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

});

$(document).on("click", "button[id='advertencia_vermelha']", function () {
    var tr = document.getElementById("chaves").children[chave];
    console.log(tr.children);

    var tr = document.getElementById("chaves").children[chave];

    if ($(this).val() === "+") {
        tr.children[3].textContent = parseInt(tr.children[3].textContent) + 1;
    } else if ($(this).val() === "-") {
        tr.children[3].textContent = parseInt(tr.children[3].textContent) - 1;
    }

});

$(document).on("click", "button[id='penalidade_vermelha']", function () {
    var tr = document.getElementById("chaves").children[chave];

    if ($(this).val() === "+") {
        tr.children[4].textContent = parseInt(tr.children[4].textContent) + 1;
    } else if ($(this).val() === "-") {
        tr.children[4].textContent = parseInt(tr.children[4].textContent) - 1;
    }
});

$(document).on("click", "button[id='ponto_branco']", function () {
    var tr = document.getElementById("chaves").children[chave];

    if ($(this).val() === "+") {
        tr.children[7].textContent = parseInt(tr.children[7].textContent) + 1;
    } else if ($(this).val() === "-") {
        tr.children[7].textContent = parseInt(tr.children[7].textContent) - 1;
    }
});

$(document).on("click", "button[id='advertencia_branca']", function () {
    var tr = document.getElementById("chaves").children[chave];

    if ($(this).val() === "+") {
        tr.children[8].textContent = parseInt(tr.children[8].textContent) + 1;
    } else if ($(this).val() === "-") {
        tr.children[8].textContent = parseInt(tr.children[8].textContent) - 1;
    }
});

$(document).on("click", "button[id='penalidade_branca']", function () {
    var tr = document.getElementById("chaves").children[chave];

    if ($(this).val() === "+") {
        tr.children[9].textContent = parseInt(tr.children[9].textContent) + 1;
    } else if ($(this).val() === "-") {
        tr.children[9].textContent = parseInt(tr.children[9].textContent) - 1;
    }
});

$(document).on("click", "button[id='vencedor']", function () {
    console.log($(this).attr("id"));
});

$(document).on("click", "button[id='desqualificacao']", function () {
    console.log($(this).attr("id"));
});

$(document).on("click", "button[id='submit_chave']", function () {
    console.log($(this).attr("id"));
});

$(document).on("click", "button[id='cronometro']", function () {
    console.log($(this).attr("id"));
});


// alert();