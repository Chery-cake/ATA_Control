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

function monta_plan_lista(id_plan) {
    var chaves_lista = {};
    $.ajax({
        method: "POST",
        url: "/planilha/individual/lista/competidores/" + id_plan,
        contentType: 'application/json',
        success: function (result) {
            console.log("Chaves lista: ");
            console.log(result);
            chaves_lista = result;

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
            button.id = "submit";
            button.textContent = "Submit";
            div.appendChild(button);

        }
    });
}

var chave = 1;

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

$(document).on("click", "button[id='submit']", function () {
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

function monta_plan_chave(id_plan) {
    alert(id_plan);
}