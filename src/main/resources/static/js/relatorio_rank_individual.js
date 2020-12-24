$("#categoria").change(function () {

    $.ajax({
        method: "POST",
        url: "/rank/individual/categoria/" + $("#categoria").val(),
        beforeSend: function () {
            while (document.getElementById("table").firstChild) {
                document.getElementById("table").firstChild.remove();
            }
        },
        success: function (response) {

            var table = document.createElement("table");
            table.className = "table";
            var tr = document.createElement("tr");
            var th = document.createElement("th");
            th.textContent = "Pessoa";
            tr.appendChild(th);
            th = document.createElement("th");
            th.textContent = "Pontuação";
            tr.appendChild(th);
            table.appendChild(tr);

            for (var i in response){
                tr = document.createElement("tr");
                var td = document.createElement("td");
                td.textContent = response[i].pessoa.nome + " " + response[i].pessoa.sobrenome;
                tr.appendChild(td);

                td = document.createElement("td");
                td.textContent = response[i].pontuacao;
                tr.appendChild(td);

                table.appendChild(tr);
            }

            document.getElementById("table").appendChild(table);
        }
    });

});