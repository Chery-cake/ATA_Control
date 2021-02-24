$("#form_novo_adm").submit(function (evt) {
    evt.preventDefault();

    var data = {};

    data.id_usuario = $("#id_usuario").val();
    data.username = $("#username").val();
    data.password = $("#password").val();

    $.ajax({
        method: "POST",
        url: "/save/administrador",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
            top.location.href = "/perfil";
        }
    });

});
