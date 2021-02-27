$("#form-register-planilheiro").submit(function (evt) {
    evt.preventDefault();

    var usuario = {};
    usuario.email = $("#usuario").val();
    usuario.password = $("#senha").val();

    $.ajax({
        method: "POST",
        url: "/verifica/usuario/pessoa/" + $("#usuario").val(),
        success: function (result) {
            console.log(result);
            if (result === true) {
                $.ajax({
                    method: "POST",
                    url: "/inicia/torneio/" + $("#torneio").val(),
                    contentType: 'application/json',
                    data: JSON.stringify(usuario),
                    success: function (result) {
                        top.location.href = "/perfil";
                    }
                });
            } else {
                if (document.getElementById("not_email")) {
                    $("#email").focus();
                } else {
                    var label = document.createElement("label");
                    label.textContent = "Este usuario ja existe";
                    label.className = "text-center";
                    label.id = "not_email";
                    document.getElementById("div_usuario").appendChild(label);
                    $("#email").focus();
                }
            }
        }
    });

});