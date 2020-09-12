$("#form-register-competidor").submit(function (evt) {
    evt.preventDefault();

    var competidorDTO = {};
    competidorDTO.pessoa = $("#pessoa_id").val();
    competidorDTO.peso = $("#peso").val();
    competidorDTO.altura = $("#altura").val();
    competidorDTO.torneio = $("#torneio").val();

    competidorDTO.categoriaCompeticao = [];
    for (var i = 0; i < $("#quantidade-categorias").val(); i++) {
        competidorDTO.categoriaCompeticao.push($("#select_categoria_" + i).val());
    }

    $.ajax({
        method: "POST",
        url: "/save/competidor",
        contentType: 'application/json',
        data: JSON.stringify(competidorDTO),
        success: function (result) {
            top.location.href = "/perfil";
        },
        error: function (xhr) {
            console.log("error: ", xhr.responseText);
        }
    });

});

$("#quantidade-categorias").change(function (evt) {
    $.ajax({
        method: "POST",
        url: "/categorias/competicao",
        beforeSend: function () {
            while (document.getElementById("categorias_select_div").firstChild) {
                document.getElementById("categorias_select_div").firstChild.remove();
            }
        },
        success: function (response) {
            for (var i = 0; i < $("#quantidade-categorias").val(); i++) {
                var div = document.getElementById("categorias_select_div");
                var select = document.createElement("select");
                select.className = "form-control";
                select.id = "select_categoria_" + i;
                select.name = "select_categoria_" + i;
                select.required;
                var option = document.createElement("option");
                option.value = "";
                option.disabled = true;
                option.selected = true;
                option.text = "Selecione";
                select.appendChild(option);
                for (var j in response) {
                    var option = document.createElement("option");
                    option.value = response[j].id;
                    option.text = response[j].nome;
                    select.appendChild(option);
                }
                div.appendChild(select);
            }
        }
    });
});