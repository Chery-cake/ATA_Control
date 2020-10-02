$("#submit").on("click", function (evt) {
    $.ajax({
        method: "POST",
        url: "/terminar/torneio/" + $("#torneio").val(),
        contentType: 'application/json',
        data: $("#torneio").val(),
        success: function (result) {
            top.location.href = "/perfil";
        }
    });

});