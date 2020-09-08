$("#form-register-torneio").submit(function (evt){
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

    $.ajax({
        method: "POST",
        url: "/save/torneio",
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

function getRadio(radio_name) {
    var radios = document.getElementsByName(radio_name);
    for (var i in radios) {
        if (radios[i].checked) {
            return radios[i].value;
        }
    }
}