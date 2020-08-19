$("#form-register-academia").submit(function (evt) {

    evt.preventDefault();
    var data = {};
    var academia = {};
    academia.nome = $("#nome").val();
    data.academia = academia;

    var enderecoDTO = {};
    enderecoDTO.cidade = $("#cidade").val();
    enderecoDTO.rua = $("#endereco").val();
    data.enderecoDTO = enderecoDTO;

    $.ajax({
        method: "POST",
        url: "/pessoa/academia/save",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            console.log(result);
            window.location.href = "/pessoa/register";
        },
        error: function (xhr) {
            console.log("error: ", xhr.responseText);
        }
    });

});