$("#form").submit(function (evt) {
    evt.preventDefault();

    if ($("#confi_senha").val() !== $("#senha").val()) {
        $("#confi_senha").value = "";
        $("#confi_senha").focus();
        var label = document.createElement("label");
        label.textContent = "As senhas estão incompativeis";
        label.className = "text-center";
        label.id = "not_senha";
        document.getElementById("senha_div").appendChild(label);
    } else {
        if (document.getElementById("not_senha")) {
            document.getElementById("not_senha").remove();
        }
        $.ajax({
            method: "POST",
            url: "/recuper/senha/" + new URLSearchParams(window.location.hash).get('token'),
            contentType: 'application/json',
            data: JSON.stringify($("#senha").val()),
            success: function () {
                window.location.href = "/login";
            }
        });
    }
});