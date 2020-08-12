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

function loop(){//todo arrumar para que a opcao de instrutores suma de acordo com o radio
    if (document.getElementById('isInstrutor_f').checked === true) {
        document.getElementById("instrutor_div").removeAttribute("hidden");
    } else {
        document.getElementById("instrutor_div").setAttribute("hidden", "hidden");
    }
}
