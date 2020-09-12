$("#form-register-juiz").submit(function (evt) {
    evt.preventDefault();

    var data = {};

    data.pessoa = $("#pessoa_id").val();

    var rodadas = [];
    for (var i = 0; i < $("#quant_rodada").val(); i++) {
        rodadas.push($("#rodada_" + i).val());
    }
    data.rodadas = rodadas;

    $.ajax({
        method: "POST",
        url: "/save/juiz",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            top.location.href = "/perfil";
        },
        error: function (xhr) {
            console.log("error: ", xhr.responseText);
        }
    });

});

$("#quant_rodada").change(function () {
    troca_rodada();
});

$("#torneio").change(function () {
    troca_rodada();
});

function troca_rodada() {
    if ($("#torneio").val() != null && $("#quant_rodada").val() > 0) {
        $.ajax({
            method: "POST",
            url: "/rodadas/torneio/" + $("#torneio").val(),
            data: $("#torneio").val(),
            beforeSend: function () {
                if (document.getElementById("rodadas_div").firstChild) {
                    while (document.getElementById("rodadas_div").firstChild) {
                        document.getElementById("rodadas_div").firstChild.remove();
                    }
                }
            },
            success: function (response) {
                var div = document.getElementById("rodadas_div");
                for (var i = 0; i < $("#quant_rodada").val(); i++) {
                    var p = document.createElement("p");
                    var aux = i + 1;
                    p.textContent = "Rodada " + aux;
                    div.appendChild(p);
                    p = document.createElement("p");
                    var select = document.createElement("select");
                    select.className = "form-control";
                    select.id = "rodada_" + i;
                    select.name = "rodada_" + i;
                    select.required;
                    var option = document.createElement("option");
                    option.value = "";
                    option.disabled = true;
                    option.selected = true;
                    option.text = "Selecione";
                    select.appendChild(option);
                    for (var j in response) {
                        option = document.createElement("option");
                        option.value = response[j].id;
                        option.text = "Horario de inicio: " + response[j].inicio + " -/- Horario de Termino: " + response[j].termino + " -/- Dia: " + response[i].rodadaJuizList[j].dia.substr(0, 10).replaceAll("-", "/");
                        select.appendChild(option);
                    }
                    p.appendChild(select);
                    div.appendChild(p);
                }
            }
        });
    }
}