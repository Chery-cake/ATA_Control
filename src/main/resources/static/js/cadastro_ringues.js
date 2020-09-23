var quantRing = 0;
var juizes = {};

$("#torneio").change(function () {
    $.ajax({
        method: "POST",
        url: "/rodadas/torneio/" + $("#torneio").val(),
        data: $("#torneio").val(),
        beforeSend: function () {
            while (document.getElementById("rodada").firstChild) {
                document.getElementById("rodada").firstChild.remove();
            }
            while (document.getElementById("cadastros").firstChild) {
                document.getElementById("cadastros").firstChild.remove();
            }
        },
        success: function (response) {
            quantRing = response[0].torneio.maxNumeroRingues;
            var div = document.getElementById("rodada");
            var label = document.createElement("label");
            label.textContent = "Rodada:";
            div.appendChild(label);
            var select = document.createElement("select");// add select rodada
            select.id = "rodada_select";
            select.className = "form-control";
            select.required;
            var option = document.createElement("option");
            option.value = "";
            option.disabled = true;
            option.selected = true;
            option.text = "Selecione";
            select.appendChild(option);
            for (i in response) {
                option = document.createElement("option");
                option.value = response[i].id;
                option.text = "Horario de inicio: " + response[i].inicio + " -/- Horario de Termino: " + response[i].termino + " -/- Dia: " + response[i].dia.substr(0, 10).replaceAll("-", "/");
                select.appendChild(option);
            }
            div.appendChild(select);
        }
    });
});

$(document).on("change", "select[id='rodada_select']", function () {
    if (document.getElementById("cadastros").firstChild) {
        while (document.getElementById("cadastros").firstChild) {
            document.getElementById("cadastros").firstChild.remove();
        }
    }

    $.ajax({
        method: "POST",
        url: "/juiz/rodada/" + $("#rodada_select").val(),
        data: $("#rodada_select").val(),
        success: function (response) {

            juizes = response;

            var div = document.getElementById("cadastros");

            if (response.length === 0) {
                var p = document.createElement("p");
                p.textContent = "Nao ha juizes cadastrados";
                div.appendChild(p);
            } else {

                for (var i = 1; i <= quantRing; i++) {

                    var label = document.createElement("label");
                    label.textContent = "Ringue numero " + i;
                    label.id = "label_numero" + i;
                    div.appendChild(label);

                    var p = document.createElement("div");
                    p.id = i;
                    p.name = "numeroRingue";

                    var button = document.createElement("button");
                    button.textContent = "Adicionar um ringue neste numero";
                    button.onclick = function () {
                        var parent = $(this).parent();

                        var divs = parent.children("div");

                        addCadastroRingue($(parent).attr("id"), divs.length + 1, parent);
                    }
                    p.appendChild(button);

                    addCadastroRingue(i, 1, p);

                    div.appendChild(p);
                }
            }

        }
    });
});

function addCadastroRingue(numeroRingue, numeroRodada, localDocumento) {//todo adicionar genero

    var div = document.createElement("div");
    div.id = numeroRodada;
    div.name = "numeroRodada";

    var label = document.createElement("label");
    label.textContent = numeroRodada + "º ringue da rodada";
    div.appendChild(label);
    div.appendChild(document.createElement("br"));


    for (var j = 0; j < 3; j++) {// add select juizes
        var label = document.createElement("label");
        label.textContent = "Escolha um juiz:";
        div.appendChild(label);
        var select = document.createElement("select");
        select.className = "form-control";
        select.id = "select_" + j + "_" + numeroRingue + "_" + numeroRodada;
        select.name = "select_" + j + "_" + numeroRingue + "_" + numeroRodada;
        select.required;
        var option = document.createElement("option");
        option.value = "";
        option.disabled = true;
        option.selected = true;
        option.text = "Selecione";
        select.appendChild(option);
        for (var i in juizes) {
            option = document.createElement("option");
            option.value = juizes[i].id;
            if (juizes[i].pessoa.genero) {
                option.text = juizes[i].pessoa.nome + " " + juizes[i].pessoa.nome + " -/- Masculino";
            } else {
                option.text = juizes[i].pessoa.nome + " " + juizes[i].pessoa.nome + " -/- Feminino";
            }
            select.appendChild(option);
        }
        div.appendChild(select);
    }

    label = document.createElement("label");//add input fechado
    label.textContent = "O ringue é fechado:";
    div.appendChild(label);
    var input = document.createElement("input");
    input.type = "radio";
    input.name = "fechado_" + numeroRingue + "_" + numeroRodada;
    input.value = true;
    input.id = "fechado_T_" + numeroRingue + "_" + numeroRodada;
    div.appendChild(input);
    label = document.createElement("label");
    label.textContent = "Fechado";
    label.setAttribute("for", "genero_T_" + numeroRingue + "_" + numeroRodada)
    div.appendChild(label);
    input = document.createElement("input");
    input.type = "radio";
    input.name = "fechado_" + numeroRingue + "_" + numeroRodada;
    input.value = false;
    input.id = "genero_F_" + numeroRingue + "_" + numeroRodada;
    input.setAttribute("checked", "checked");
    div.appendChild(input);
    label = document.createElement("label");
    label.textContent = "Aberto";
    label.setAttribute("for", "genero_F_" + numeroRingue + "_" + numeroRodada)
    div.appendChild(label);
    div.appendChild(document.createElement("br"));

    label = document.createElement("label");//add input genero
    label.textContent = "Genero do ringue:";
    div.appendChild(label);
    var input = document.createElement("input");
    input.type = "radio";
    input.name = "genero_" + numeroRingue + "_" + numeroRodada;
    input.value = true;
    input.id = "genero_M_" + numeroRingue + "_" + numeroRodada;
    input.setAttribute("checked", "checked");
    div.appendChild(input);
    label = document.createElement("label");
    label.textContent = "Masculino";
    label.setAttribute("for", "genero_M_" + numeroRingue + "_" + numeroRodada)
    div.appendChild(label);
    input = document.createElement("input");
    input.type = "radio";
    input.name = "genero_" + numeroRingue + "_" + numeroRodada;
    input.value = false;
    input.id = "genero_F_" + numeroRingue + "_" + numeroRodada;
    div.appendChild(input);
    label = document.createElement("label");
    label.textContent = "Feminino";
    label.setAttribute("for", "genero_F_" + numeroRingue + "_" + numeroRodada)
    div.appendChild(label);
    div.appendChild(document.createElement("br"));

    label = document.createElement("label");// add select idades
    label.textContent = "Idade do ringue:";
    div.appendChild(label);
    var select = document.createElement("select");
    select.className = "form-control";
    select.id = "select_idade_" + numeroRingue + "_" + numeroRodada;
    select.name = "select_idade_" + numeroRingue + "_" + numeroRodada;
    select.required;
    var option = document.createElement("option");
    option.value = "";
    option.disabled = true;
    option.selected = true;
    option.text = "Selecione";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "1";
    option.text = "7 e 8";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "2";
    option.text = "9 e 10";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "3";
    option.text = "11 e 12";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "4";
    option.text = "13 e 14";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "5";
    option.text = "15 a 17";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "6";
    option.text = "18 a 29";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "7";
    option.text = "30 a 39";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "8";
    option.text = "40 a 49";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "9";
    option.text = "50 a 59";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "10";
    option.text = "60 acima";
    select.appendChild(option);
    div.appendChild(select);

    label = document.createElement("label");// add select nivel
    label.textContent = "Nivel do ringue:";
    div.appendChild(label);
    var select = document.createElement("select");
    select.className = "form-control";
    select.id = "select_nivel_" + numeroRingue + "_" + numeroRodada;
    select.name = "select_nivel_" + numeroRingue + "_" + numeroRodada;
    select.required;
    var option = document.createElement("option");
    option.value = "";
    option.disabled = true;
    option.selected = true;
    option.text = "Selecione";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "0";
    option.text = "nivel 1 -/- Faixas: Branca, Laranja, Amarela";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "1";
    option.text = "nivel 2 -/- Faixas: Camuflada, Verde, Roxa";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "2";
    option.text = "nivel 3 -/- Faixas: Aluz, Marron, Vermelha";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "3";
    option.text = "nivel 4 -/- Faixas: Vermelha e Preta";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "4";
    option.text = "nivel 5 -/- Faixas: 1º Dan";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "5";
    option.text = "nivel 6 -/- Faixas: 2º Dan e 3º Dan";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "6";
    option.text = "nivel 7 -/- Faixas: 4º Dan e 5º Dan";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "7";
    option.text = "nivel 8 -/- Faixas: 6º Dan e 7º Dan";
    select.appendChild(option);
    option = document.createElement("option");
    option.value = "8";
    option.text = "nivel 9 -/- Faixas: 8º Dan e 9º Dan";
    select.appendChild(option);
    div.appendChild(select);

    label = document.createElement("label");// add quant categorias
    label.textContent = "Quantidade de categorias:";
    div.appendChild(label);
    var div_cat = document.createElement("div");
    var input = document.createElement("input");
    input.type = "number";
    input.id = "quantCat_" + numeroRingue + "_" + numeroRodada;
    input.className = "form-control";
    input.onchange = function () {
        while (div_cat.firstChild) {
            div_cat.firstChild.remove();
        }
        addCategorias(numeroRingue, numeroRodada, div_cat);
    };
    div.appendChild(input);
    div.appendChild(div_cat);

    if (numeroRodada === 1) {
        localDocumento.appendChild(div);
    } else {
        localDocumento.append(div);
    }

}

function addCategorias(numeroRingue, numeroRodada, div) {
    $.ajax({
        method: "POST",
        url: "/categorias/competicao",
        success: function (response) {
            var quantCat = parseInt($("#quantCat_" + numeroRingue + "_" + numeroRodada).val());
            for (var i = 0; i < quantCat; i++) {// add select categorias
                var label = document.createElement("label");
                label.textContent = "Escolha uma categoria:";
                div.appendChild(label);
                var select = document.createElement("select");
                select.className = "form-control";
                select.id = "select_cat_" + numeroRingue + "_" + numeroRodada + "_" + i;
                select.name = "select_cat_" + numeroRingue + "_" + numeroRodada + "_" + i;
                select.required;
                var option = document.createElement("option");
                option.value = "";
                option.disabled = true;
                option.selected = true;
                option.text = "Selecione";
                select.appendChild(option);
                for (j in response) {
                    option = document.createElement("option");
                    option.value = response[j].id;
                    option.text = response[j].nome;
                    select.appendChild(option);
                }
                div.appendChild(select);
            }
        }
    });
}

function getRadio(radio_name) {
    var radios = document.getElementsByName(radio_name);
    for (var i in radios) {
        if (radios[i].checked) {
            return radios[i].value;
        }
    }
}

$(document).on("click", "button[id='submit']", function () {

    var data = {};
    data.arrayRingue = [];

    var div_numRingue = $("#cadastros").children("div");

    for (var numeroRingue = 1; numeroRingue <= div_numRingue.length; numeroRingue++) {
        var div_numRodada = $(div_numRingue[numeroRingue - 1]).children("div");
        for (var numeroRodada = 1; numeroRodada <= div_numRodada.length; numeroRodada++) {

            var torneio_individual_dto = {};
            torneio_individual_dto.torneio = $("#torneio").val();
            torneio_individual_dto.rodada = $("#rodada_select").val();
            torneio_individual_dto.numeroRingue = numeroRingue;
            torneio_individual_dto.numeroRodada = numeroRodada;

            var juizes = [];
            for (var i = 0; i < 3; i++) {
                juizes.push($("#select_" + i + "_" + numeroRingue + "_" + numeroRodada).val());
            }
            torneio_individual_dto.juizes = juizes;

            torneio_individual_dto.fechado = getRadio("fechado_" + numeroRingue + "_" + numeroRodada);
            torneio_individual_dto.genero = getRadio("genero_" + numeroRingue + "_" + numeroRodada);
            torneio_individual_dto.idade = $("#select_idade_" + numeroRingue + "_" + numeroRodada).val();
            torneio_individual_dto.nivel = $("#select_nivel_" + numeroRingue + "_" + numeroRodada).val();

            var quantCat = $("#quantCat_" + numeroRingue + "_" + numeroRodada).val();
            var categorias = [];
            for (var i = 0; i < quantCat; i++) {
                categorias.push($("#select_cat_" + numeroRingue + "_" + numeroRodada + "_" + i).val());
            }
            torneio_individual_dto.categorias = categorias;

            data.arrayRingue.push(torneio_individual_dto);

        }
    }

    $.ajax({
        method: "POST",
        url: "/save/ringues",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            top.location.href = "/cadastrar/ringues";
        }
    });

});