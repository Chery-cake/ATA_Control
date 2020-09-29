$("#form-register-planilheiro").submit(function (evt) {
    evt.preventDefault();

    var usuario = {};
    usuario.email = $("#usuario").val();
    usuario.password = $("#senha").val();

    $.ajax({
        method: "POST",
        url: "/inicia/torneio/" + $("#torneio").val(),
        contentType: 'application/json',
        data: JSON.stringify(usuario),
        success: function (result) {
            top.location.href = "/perfil";
        }
    });

});