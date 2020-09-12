$(document).on("change", "select[id='pais']", function () {
    $.ajax({
        method: "POST",
        url: '/pais/' + document.getElementById("pais").value,
        beforeSend: function () {
            if (document.getElementById("estado") != null) {
                document.getElementById("estado").remove();
                if (document.getElementById("cidade") != null) {
                    document.getElementById("cidade").remove();
                    if (document.getElementById("endereco") != null) {
                        document.getElementById("endereco").remove();
                    }
                }
            }
            var div = document.getElementById("endereco_div");
            var select = document.createElement("select");
            select.className = "form-control";
            select.id = "estado";
            select.name = "estado";
            select.required;
            var option = document.createElement("option");
            option.value = "";
            option.disabled = true;
            option.selected = true;
            option.text = "Selecione";
            select.appendChild(option);
            div.appendChild(select);
        },
        success: function (response) {
            for (var i in response) {
                var optionElement = document.createElement("option");
                optionElement.value = response[i].id;
                optionElement.text = response[i].nome + " -/- " + response[i].sigla;
                document.getElementById("estado").appendChild(optionElement);
            }
        },
        error: function (xhr) {
            console.log("error: ", xhr.responseText);
        }
    });
});

$(document).on("change", "select[id='estado']", function () {
    $.ajax({
        method: "POST",
        url: '/estado/' + document.getElementById("estado").value,
        beforeSend: function () {
            if (document.getElementById("cidade") != null) {
                document.getElementById("cidade").remove();
                if (document.getElementById("endereco") != null) {
                    document.getElementById("endereco").remove();
                }
            }
            var div = document.getElementById("endereco_div");
            var select = document.createElement("select");
            select.className = "form-control";
            select.id = "cidade";
            select.name = "cidade";
            select.required;
            var option = document.createElement("option");
            option.value = "";
            option.disabled = true;
            option.selected = true;
            option.text = "Selecione";
            select.appendChild(option);
            div.appendChild(select);
        },
        success: function (response) {
            for (var i in response) {
                var optionElement = document.createElement("option");
                optionElement.value = response[i].id;
                optionElement.text = response[i].nome;
                document.getElementById("cidade").appendChild(optionElement);
            }
        },
        error: function (xhr) {
            console.log("error: ", xhr.responseText);
        }
    });
});

$(document).on("change", "select[id='cidade']", function () {
    if (document.getElementById("endereco") != null) {
        document.getElementById("endereco").remove();
    }
    var div = document.getElementById("endereco_div");
    var input = document.createElement("input");
    input.className = "form-control";
    input.id = "endereco";
    input.placeholder = "Rua Afonso, 805";
    input.required;
    input.type = "text";
    div.appendChild(input);
});