var quantRing = 0;

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
    var div = document.getElementById("cadastros");
    for (var i = 1; i <= quantRing; i++) {
        var label = document.createElement("label");
        label.textContent = "Ringue numero " + i;
        label.id = "label_numero" + i;
        div.appendChild(label);
        var p = document.createElement("div");
        p.id = i;
        p.name = "numeroRingue";
        addCadastroRingue(i, 1, p);
        if (document.getElementById("sem_juiz")) {//todo arrumar o break para n repetir a menssagem
            document.getElementById("label_numero" + i).remove();
            break;
        } else {
            var button = document.createElement("button");
            button.textContent = "Adicionar um ringue neste numero";
            button.onclick = function (){
                var parent = $(this).parent();
                var test = document.createElement("p");
                test.textContent = "test";
                parent.append(test);
                console.log(parent.children());
            }
            p.appendChild(button);
        }
        div.appendChild(p);
    }
});

function addCadastroRingue(numeroRingue, numeroRodada, localDocumento) {
    $.ajax({
        method: "POST",
        url: "/juiz/rodada/" + $("#rodada_select").val(),
        data: $("#rodada_select").val(),
        success: function (response) {
            var div = document.createElement("div");
            div.id = numeroRodada;
            div.name = "numeroRodada";
            if (response.length === 0) {
                var p = document.createElement("p");
                p.id = "sem_juiz";
                p.textContent = "Nao ha juizes cadastrados";
                div.appendChild(p);
            } else {
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
                    select.id = "select_" + numeroRingue + "_" + numeroRodada;
                    select.name = "select_" + numeroRingue + "_" + numeroRodada;
                    select.required;
                    var option = document.createElement("option");
                    option.value = "";
                    option.disabled = true;
                    option.selected = true;
                    option.text = "Selecione";
                    select.appendChild(option);
                    for (var i in response) {
                        option = document.createElement("option");
                        option.value = response[i].id;
                        if (response[i].pessoa.genero) {
                            option.text = response[i].pessoa.nome + " " + response[i].pessoa.nome + " -/- Masculino";
                        } else {
                            option.text = response[i].pessoa.nome + " " + response[i].pessoa.nome + " -/- Feminino";
                        }
                        select.appendChild(option);
                    }
                    div.appendChild(select);
                }

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
            }
            localDocumento.appendChild(div);
        }
    });
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