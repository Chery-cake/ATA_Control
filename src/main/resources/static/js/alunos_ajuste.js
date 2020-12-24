$.ajax({
    method: "POST",
    url: "/alunos/instrutor/" + $("#id_pessoa").val(),
    success: function (response) {
        var select = document.getElementById("aluno");
        for (var i in response){
            var option = document.createElement("option");
            option.value = response[i].id;
            option.text = response[i].nome + " " + response[i].sobrenome;
            select.appendChild(option);
        }
    }
});

$("#form-ajustar-aluno").submit(function (evt){
    evt.preventDefault();

    var info = {};

    info.aluno = $("#aluno").val();
    info.ataNumberWorld = $("#ataNumberWorld").val();
    info.ataNumberBrasil = $("#ataNumberBrasil").val();
    info.status = $("#status").val();

    $.ajax({
        method: "POST",
        url: "/save/ajuste/aluno",
        contentType: 'application/json',
        data: JSON.stringify(info),
        success: function () {
            top.location.href = "/ajustar/alunos";
        }
    });

});