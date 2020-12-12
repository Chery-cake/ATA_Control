$("#torneio").change(function () {

    $.ajax({//juiz
        method: "POST",
        url: "/juiz/torneio/" + $("#torneio").val(),
        data: $("#torneio").val(),
        beforeSend: function () {
            while (document.getElementById("juiz_table").firstChild) {
                document.getElementById("juiz_table").firstChild.remove();
            }
        },
        success: function (response) {
            var table = document.createElement("table");
            table.className = "table";

            var tr = document.createElement("tr");
            var th = document.createElement("th");
            th.textContent = "Juiz";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Genero";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Idade";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Faixa";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Rodadas";
            tr.appendChild(th);

            table.appendChild(tr);

            for (i in response) {
                tr = document.createElement("tr");
                var td = document.createElement("td");
                td.textContent = response[i].pessoa.nome + " " + response[i].pessoa.sobrenome;
                tr.appendChild(td);
                td = document.createElement("td");
                if (response[i].pessoa.genero) {
                    td.textContent = "Masculino";
                } else {
                    td.textContent = "Feminino";
                }
                tr.appendChild(td);
                td = document.createElement("td");
                var anoNasci = parseInt(response[i].pessoa.dataNascimento.substr(0, 4));
                var anoAtual = new Date().getFullYear();
                td.textContent = anoAtual - anoNasci;
                tr.appendChild(td);
                td = document.createElement("td");
                td.textContent = response[i].pessoa.faixa.nome;
                tr.appendChild(td);
                td = document.createElement("td");
                var rod = "";
                for (var j = 0; j < response[i].rodadaJuizList.length; j++) {
                    rod += response[i].rodadaJuizList[j].inicio + " ate " + response[i].rodadaJuizList[j].termino + " no dia " + response[i].rodadaJuizList[j].dia.substr(0, 10).replaceAll("-", "/");
                    if (response[i].rodadaJuizList[j + 1] != null) {
                        rod += ", ";
                    }
                }
                td.textContent = rod;
                tr.appendChild(td);
                table.appendChild(tr);
            }
            document.getElementById("juiz_table").appendChild(table);
        }
    });

    $.ajax({//competidor
        method: "POST",
        url: "/competidor/torneio/" + $("#torneio").val(),
        data: $("#torneio").val(),
        beforeSend: function () {
            while (document.getElementById("competidor_table").firstChild) {
                document.getElementById("competidor_table").firstChild.remove();
            }
        },
        success: function (response) {
            var table = document.createElement("table");
            table.className = "table";
            var tr = document.createElement("tr");
            var th = document.createElement("th");
            th.textContent = "Competidor";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Genero";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Idade";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Faixa";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Peso";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Altura";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Categorias";
            tr.appendChild(th);
            table.appendChild(tr);
            for (i in response) {
                tr = document.createElement("tr");
                var td = document.createElement("td");
                td.textContent = response[i].pessoa.nome + " " + response[i].pessoa.sobrenome;
                tr.appendChild(td);
                td = document.createElement("td");
                if (response[i].pessoa.genero) {
                    td.textContent = "Masculino";
                } else {
                    td.textContent = "Feminino";
                }
                tr.appendChild(td);
                td = document.createElement("td");
                var anoNasci = parseInt(response[i].pessoa.dataNascimento.substr(0, 4));
                var anoAtual = new Date().getFullYear();
                td.textContent = anoAtual - anoNasci;
                tr.appendChild(td);
                td = document.createElement("td");
                td.textContent = response[i].pessoa.faixa.nome;
                tr.appendChild(td);
                td = document.createElement("td");
                td.textContent = response[i].peso;
                tr.appendChild(td);
                td = document.createElement("td");
                td.textContent = response[i].altura;
                tr.appendChild(td);
                td = document.createElement("td");
                var cat = "";
                for (var j = 0; j < response[i].categoriaCompeticao.length; j++) {
                    cat += response[i].categoriaCompeticao[j].nome;
                    if (response[i].categoriaCompeticao[j + 1] != null) {
                        cat += ", ";
                    }
                }
                td.textContent = cat;
                tr.appendChild(td);
                table.appendChild(tr);
            }
            document.getElementById("competidor_table").appendChild(table);
        }
    });

});