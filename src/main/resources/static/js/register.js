//submit do formulario para o controller
$("#form-register-pessoa").submit(function (evt) {
    //bloqueia o comportamento padrao do submit
    evt.preventDefault();//todo adicionar ataNumbers, dataNasc, telefone e foto

    var data = {};
    var pessoaDTO = {};
    pessoaDTO.nome = $("#nome").val();
    pessoaDTO.sobrenome = $("#sobrenome").val();
    pessoaDTO.genero = getRadio("genero");
    pessoaDTO.dataNascimento = $("#dataNascimento").val();
    pessoaDTO.email = $("#email").val();
    pessoaDTO.senha = $("#senha").val();
    pessoaDTO.status = null;
    pessoaDTO.ataNumberWorld = $("#ataNumberWorld").val();
    pessoaDTO.ataNumberBrasil = $("#ataNumberBrasil").val();
    pessoaDTO.isInstrutor = getRadio("isInstrutor");
    pessoaDTO.faixa = $("#faixa").val();
    pessoaDTO.telefones = null;
    pessoaDTO.foto = null;
    pessoaDTO.enderecoDTO = null;

    if (pessoaDTO.isInstrutor === false) {
        pessoaDTO.instrutor = $("#instrutor").val();
    }else {
        pessoaDTO.instrutor = null;
    }

    data.pessoaDTO = pessoaDTO;

    var enderecoDTO = {};
    enderecoDTO.cidade = $("#cidade").val();
    enderecoDTO.rua = $("#endereco").val();
    data.enderecoDTO = enderecoDTO;

    console.log(JSON.stringify(data));

    $.ajax({
        method: "POST",
        url: "/pessoa/save",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(data),
        success: function () {//todo adicionar login automatico
            // top.location.href = "/";
        },
        error: function (xhr) {
            console.log("error: ", xhr.responseText);
        }
    });

});

function getRadio(radio_name) {
    var radios = document.getElementsByName(radio_name);
    for (var i in radios) {
        if (radios[i].checked) {
            return radios[i].value;
        }
    }
}

function hideInstrutor() {
    if (document.getElementById('inlineRadio2 isInstrutor_F').checked === true) {
        document.getElementById("instrutor_div").removeAttribute("hidden");
    } else {
        document.getElementById("instrutor_div").setAttribute("hidden", "hidden");
    }
}

$(document).on("change", "select[id='pais']", function () {
    $.ajax({
        method: "POST",
        url: 'register/pais/' + document.getElementById("pais").value,
        beforeSend: function () {
            if (document.getElementById("estado") != null) {
                document.getElementById("estado").remove();
            } else {
                var div = document.getElementById("endereco_div");
                var select = document.createElement("select");
                select.className = "form-control";
                select.id = "estado";
                select.name = "estado";
                select.onchange = "select('estado')";
                var option = document.createElement("option");
                option.value = "";
                option.text = "Selecione";
                select.appendChild(option);
                div.appendChild(select);
            }
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
        url: 'register/estado/' + document.getElementById("estado").value,
        beforeSend: function () {
            if (document.getElementById("cidade") != null) {
                document.getElementById("cidade").remove();
            } else {
                var div = document.getElementById("endereco_div");
                var select = document.createElement("select");
                select.className = "form-control";
                select.id = "cidade";
                select.name = "cidade";
                select.onchange = "select('cidade')";
                var option = document.createElement("option");
                option.value = "";
                option.text = "Selecione";
                select.appendChild(option);
                div.appendChild(select);
            }
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
        document.getElementById("cidade").remove();
    } else {
        var div = document.getElementById("endereco_div");
        var input = document.createElement("input");
        input.className = "form-control";
        input.id = "endereco";
        input.placeholder = "Rua Afonso, 805";
        input.required = "required";
        input.type = "text";
        div.appendChild(input);
    }
});