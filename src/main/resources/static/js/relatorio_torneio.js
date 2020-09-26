$("#torneio").change(function () {

    $.ajax({
        method: "POST",
        url: "/rodadas/torneio/" + $("#torneio").val(),
        data: $("#torneio").val(),
        beforeSend: function () {
            while (document.getElementById("table").firstChild) {
                document.getElementById("table").firstChild.remove();
            }
        },
        success: function (response) {

            var table = document.createElement("table");
            table.className = "table-bordered";
            var tr = document.createElement("tr");
            var th = document.createElement("th");
            th.textContent = "Rodada";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Genero";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Idade";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Nivel";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Fechado";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Juizes";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Numero do ringue";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Lugar na seguencia na rodada";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Categorias";
            tr.appendChild(th);
            table.appendChild(tr);

            for (i in response) {
                $.ajax({
                    method: "POST",
                    url: "/ringueInd/rodada/" + response[i].id,
                    data: response[i].id,
                    success: function (response2) {

                        console.log(response2);

                        for (j in response2) {
                            tr = document.createElement("tr");
                            var td = document.createElement("td");
                            td.textContent = response[i].inicio + " ate " + response[i].termino + " no dia " + response[i].dia.substr(0, 10).replaceAll("-", "/");
                            tr.appendChild(td);
                            td = document.createElement("td");
                            if (response2[j].genero) {
                                td.textContent = "Masculino";
                            } else {
                                td.textContent = "Feminino";
                            }
                            tr.appendChild(td);
                            td = document.createElement("td");
                            switch (response2[j].idade) {
                                default:
                                    td.textContent = "7 e 8";
                                    break;
                                case "1":
                                    td.textContent = "7 e 8";
                                    break;
                                case "2":
                                    td.textContent = "9 e 10";
                                    break;
                                case "3":
                                    td.textContent = "11 e 12";
                                    break;
                                case "4":
                                    td.textContent = "13 e 14";
                                    break;
                                case "5":
                                    td.textContent = "15 a 17";
                                    break;
                                case "6":
                                    td.textContent = "18 a 29";
                                    break;
                                case "7":
                                    td.textContent = "30 a 39";
                                    break;
                                case "8":
                                    td.textContent = "40 a 49";
                                    break;
                                case "9":
                                    td.textContent = "50 a 59";
                                    break;
                                case "10":
                                    td.textContent = "60 acima";
                                    break;
                            }
                            tr.appendChild(td);
                            td = document.createElement("td");
                            switch (response2[j].nivel) {
                                default:
                                    td.textContent = "nivel 1 -/- Faixas: Branca, Laranja, Amarela";
                                    break;
                                case 0:
                                    td.textContent = "nivel 1 -/- Faixas: Branca, Laranja, Amarela";
                                    break;
                                case 1:
                                    td.textContent = "nivel 2 -/- Faixas: Camuflada, Verde, Roxa";
                                    break;
                                case 2:
                                    td.textContent = "nivel 3 -/- Faixas: Aluz, Marron, Vermelha";
                                    break;
                                case 3:
                                    td.textContent = "nivel 4 -/- Faixas: Vermelha e Preta";
                                    break;
                                case 4:
                                    td.textContent = "nivel 5 -/- Faixas: 1º Dan";
                                    break;
                                case 5:
                                    td.textContent = "nivel 6 -/- Faixas: 2º Dan e 3º Dan";
                                    break;
                                case 6:
                                    td.textContent = "nivel 7 -/- Faixas: 4º Dan e 5º Dan";
                                    break;
                                case 7:
                                    td.textContent = "nivel 8 -/- Faixas: 6º Dan e 7º Dan";
                                    break;
                                case 8:
                                    td.textContent = "nivel 9 -/- Faixas: 8º Dan e 9º Dan";
                                    break;
                            }
                            tr.appendChild(td);
                            td = document.createElement("td");
                            if (response2[j].fechado) {
                                td.textContent = "Fechado";
                            } else {
                                td.textContent = "Aberto";
                            }
                            tr.appendChild(td);

                            td = document.createElement("td");
                            var jui = "";
                            for (var z = 0; z < response2[j].juiz.length; z++) {
                                jui += response2[j].juiz[z].pessoa.nome + " " + response2[j].juiz[z].pessoa.sobrenome;
                                if (response2[j].juiz[z + 1] != null) {
                                    jui += ", ";
                                }
                            }
                            td.textContent = jui;
                            tr.appendChild(td);

                            td = document.createElement("td");
                            td.textContent = response2[j].numeroRingue;
                            tr.appendChild(td);
                            td = document.createElement("td");
                            td.textContent = response2[j].numeroRodada;
                            tr.appendChild(td);

                            td = document.createElement("td");
                            var cat = "";
                            for (var z = 0; z < response2[j].categoriaCompeticao.length; z++) {
                                cat += response2[j].categoriaCompeticao[z].nome;
                                if (response2[j].juiz[z + 1] != null) {
                                    cat += ", ";
                                }
                            }
                            td.textContent = cat;
                            tr.appendChild(td);

                            table.appendChild(tr);
                        }
                    }
                });
            }
            document.getElementById("table").appendChild(table);
        }
    });

});