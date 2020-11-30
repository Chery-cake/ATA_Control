$("#form-register-pessoa").submit(function (evt) {
    evt.preventDefault();

    var categoria = {};

    categoria.nome = $("#nome").val();
    categoria.tipoChave = getRadio("chaves");
    categoria.tipoTime = getRadio("time");
    categoria.limiteTempo = $("#tempo").val();
    categoria.limitePonto = $("#ponto").val();
    categoria.minimoMasculino = $("#maxParticipantes").val();
    categoria.minimoFeminino = $("#mimFem").val();
    categoria.maximoTotal = $("#mimMasc").val();

    $.ajax({
        method: "POST",
        url: "/save/categoria",
        contentType: 'application/json',
        data: JSON.stringify(categoria),
        success: function (result) {
            top.location.href = "/cadastrar/ringues";
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

function hideChaveInfo() {
    if (document.getElementById('tipo-chave-F').checked === true) {
        document.getElementById("ponto_div").setAttribute("hidden", "hidden");
        document.getElementById("tempo_div").setAttribute("hidden", "hidden");
    } else {
        document.getElementById("ponto_div").removeAttribute("hidden");
        document.getElementById("tempo_div").removeAttribute("hidden");
    }
}