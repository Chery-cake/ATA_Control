$("#submit").on("click", function (evt) {
    $.ajax({
        method: "POST",
        url: "/inicia/torneio/" + $("#torneio").val(),
        success: function (result) {
            top.location.href = "/perfil";
        }
    });

});