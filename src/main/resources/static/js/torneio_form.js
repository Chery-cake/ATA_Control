$("#form-register-torneio").submit(function (evt) {
    evt.preventDefault();

    var data = {};

    var torneioDTO = {};
    torneioDTO.dataInicio = $("#dataInicio").val();
    torneioDTO.dataTermino = $("#dataTermino").val();
    torneioDTO.maxNumeroRingues = $("#maxNumeroRingues").val();
    torneioDTO.pontuar = getRadio("pontuar");
    torneioDTO.categoriaTorneio = $("#categoriaTorneio").val();
    data.torneioDTO = torneioDTO;

    var enderecoDTO = {};
    enderecoDTO.cidade = $("#cidade").val();
    enderecoDTO.rua = $("#endereco").val();
    data.enderecoDTO = enderecoDTO;

    var inicio = [];
    var termino = [];
    var dia = [];
    for (var i = 0; i < $("#quant_rodada").val(); i++) {
        inicio.push($("#rodada_" + i + "_inicio").val());
        termino.push($("#rodada_" + i + "_termino").val());
        dia.push($("#rodada_" + i + "_data").val());
    }
    data.inicio = inicio;
    data.termino = termino;
    data.dia = dia;

    $.ajax({
        method: "POST",
        url: "/save/torneio",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            top.location.href = "/criar/categoria";
        },
        error: function (xhr) {
            console.log("error: ", xhr.responseText);
        }
    });

});

function getRadio(radio_name) {
    var radios = document.getElementsByName(radio_name);
    for (var i in radios) {
        if (radios[i].checked) {
            return radios[i].value;
        }
    }
}

$("#quant_rodada").change(function (evt) {
    if (document.getElementById("rodadas_div").firstChild) {
        while (document.getElementById("rodadas_div").firstChild) {
            document.getElementById("rodadas_div").firstChild.remove();
        }
    }

    var div = document.getElementById("rodadas_div");
    for (var i = 0; i < $("#quant_rodada").val(); i++) {
        var p = document.createElement("p");
        var aux = i + 1;
        p.textContent = "Rodada " + aux;
        div.appendChild(p);
        p = document.createElement("p");
        var label = document.createElement("label");
        label.textContent = " Horario de inicio";
        label.setAttribute("for", "rodada_" + i);
        p.appendChild(label);
        var input = document.createElement("input");
        input.className = "form-control";
        input.type = "text";
        input.id = "rodada_" + i + "_inicio";
        input.name = "rodada_" + i + "_inicio";
        input.required = true;
        input.placeholder = "XX:YY";
        p.appendChild(input);
        label = document.createElement("label");
        label.textContent = "Horario de termino";
        label.setAttribute("for", "rodada_" + i);
        p.appendChild(label);
        input = document.createElement("input");
        input.className = "form-control";
        input.type = "text";
        input.id = "rodada_" + i + "_termino";
        input.name = "rodada_" + i + "_termino";
        input.required = true;
        input.placeholder = "XX:YY";
        p.appendChild(input);
        label = document.createElement("label");
        label.textContent = "Dia da rodada";
        label.setAttribute("for", "rodada_" + i);
        p.appendChild(label);
        input = document.createElement("input");
        input.className = "form-control";
        input.type = "date";
        input.id = "rodada_" + i + "_data";
        input.name = "rodada_" + i + "_data";
        input.required = true;
        input.placeholder = "XX:YY";
        p.appendChild(input);
        div.appendChild(p);
    }
});
