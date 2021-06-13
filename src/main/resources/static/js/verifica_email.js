$("#form").submit(function (evt) {

    evt.preventDefault();

    console.log($("#email").val());

    $.ajax({
        method: "POST",
        url: "/recuperar/email/",
        contentType: 'application/json',
        data: JSON.stringify($("#email").val()),
        success: function () {
            window.location.href = "/home";
        }
    });

});