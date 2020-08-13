//submit do formulario para o controller
$("#form-register-pessoa").submit(function (evt) {//todo arrumar o batao q ainda n ta enviando
    //bloqueia o comportamento padrao do submit
    evt.preventDefault();//todo adicionar endereco, telefone e foto

    var pessoaDTO = {};
    pessoaDTO.nome = $("#nome").val();
    pessoaDTO.sobrenome = $("#sobrenome").val();
    pessoaDTO.genero = getRadio("genero");
    pessoaDTO.dataNascimento = $("#dataNascimento").val();
    pessoaDTO.email = $("#email").val();
    pessoaDTO.senha = $("#senha").val();
    pessoaDTO.status = $("#status").val();
    pessoaDTO.ataNumberWorld = $("#ataNumberWorld").val();
    pessoaDTO.ataNumberBrasil = $("#ataNumberBrasil").val();
    pessoaDTO.isInstrutor = getRadio("isInstrutor");
    pessoaDTO.faixa = $("#faixa").val();

    if (pessoaDTO.isInstrutor === false) {
        pessoaDTO.instrutor = $("#instrutor").val();
    }

    console.log("pessoa: ", pessoaDTO);

    $.ajax({
        method: "POST",
        url: "/pessoa/save",
        data: pessoaDTO,
        success: function () {//todo adicionar login automatico
            top.location.href = "/";
        },
        error: function (xhr) {
            console.log("error: ", xhr.responseText);
        }
    });

});

function getRadio(radio_name) {
    var radios = document.getElementsByName(radio_name);
    for (var i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
            return radios[i].value;
        }
    }
}

function hideInstrutor() {
    if (document.getElementById('isInstrutor_f').checked === true) {
        document.getElementById("instrutor_div").removeAttribute("hidden");
    } else {
        document.getElementById("instrutor_div").setAttribute("hidden", "hidden");
    }
}

function select(id) {
    var option = document.getElementById(id).value;
    if (id === "pais") {
        document.getElementById("estado").removeAttribute("hidden");
        $.ajax({
            method: "POST",
            url: 'register/pais/' + option,
            success: function (response) {
                var array = [];
                var i = 0;
                while (true) {
                    if (response[i] !== undefined || response[i] != null) {
                        array[i] = response[i];
                    } else {
                        break;
                    }
                    i++;
                }
                for (i = 0; i < array.length; i++) {
                    var optionElement = document.createElement("option");
                    optionElement.value = array[i].id;
                    optionElement.text = array[i].nome + " -/- " + array[i].sigla;
                    document.getElementById("estado").appendChild(optionElement);
                }
            }
        });
    }
    if (id === "estado") {
        document.getElementById("cidade").removeAttribute("hidden");
        $.ajax({
            method: "POST",
            url: 'register/estado/' + option,
            success: function (response){
                // var array = [];
                // var i = 0;
                // while (true) {
                //     if (response[i] !== undefined || response[i] != null) {
                //         array[i] = response[i];
                //     } else {
                //         break;
                //     }
                //     i++;
                // }
                // for (var i = 0; i < array.length; i++) {
                //     var optionElement = document.createElement("option");
                //     optionElement.value = array[i].id;
                //     optionElement.text = array[i].nome;
                //     document.getElementById("cidade").appendChild(optionElement);
                // }
                console.log(response);
            }
        });
    }
    if (id === "cidade") {
    }
}