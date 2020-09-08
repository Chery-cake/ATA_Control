//submit do formulario para o controller
$("#form-register-pessoa").submit(function (evt) {
    //bloqueia o comportamento padrao do submit
    evt.preventDefault();//todo adicionar ataNumbers

    var data = {};

    var pessoaDTO = {};
    pessoaDTO.nome = $("#nome").val();
    pessoaDTO.sobrenome = $("#sobrenome").val();
    pessoaDTO.genero = getRadio("genero");
    pessoaDTO.dataNascimento = $("#dataNascimento").val();
    pessoaDTO.status = 1;//ativo e pode ser mudado pelo instrutor
    pessoaDTO.ataNumberWorld = null;//adicionado pelo instrutor ou se for um instrutor
    pessoaDTO.ataNumberBrasil = null;//adicionado pelo instrutor ou se for um instrutor
    pessoaDTO.isInstrutor = getRadio("isInstrutor");
    pessoaDTO.faixa = $("#faixa").val();
    pessoaDTO.telefone = $("#telefone").val();
    pessoaDTO.enderecoDTO = null;
    pessoaDTO.academia = null;

    if (pessoaDTO.isInstrutor === "false") {
        pessoaDTO.instrutor = $("#instrutor").val();
    } else {
        pessoaDTO.instrutor = null;
        pessoaDTO.academia = $("#academia").val();
        pessoaDTO.ataNumberWorld = $("#ataNumberWorld").val();
        pessoaDTO.ataNumberBrasil = $("#ataNumberBrasil").val();
    }

    data.pessoaDTO = pessoaDTO;

    var usuario = {};
    usuario.email = $("#email").val();
    usuario.password = $("#senha").val();
    data.usuario = usuario;

    var enderecoDTO = {};
    enderecoDTO.cidade = $("#cidade").val();
    enderecoDTO.rua = $("#endereco").val();
    data.enderecoDTO = enderecoDTO;

    $.ajax({
        method: "POST",
        url: "/save/pessoa",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {//todo adicionar login automatico
            top.location.href = "/";
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
        document.getElementById("academia_div").setAttribute("hidden", "hidden");
        document.getElementById("academia").removeAttribute("required");
        document.getElementById("ataNumber_div").setAttribute("hidden", "hidden");
        document.getElementById("ataNumberWorld").removeAttribute("required");
        document.getElementById("ataNumberBrasil").removeAttribute("required");
        document.getElementById("instrutor").setAttribute("required", "required");
    } else {
        document.getElementById("instrutor_div").setAttribute("hidden", "hidden");
        document.getElementById("academia_div").removeAttribute("hidden");
        document.getElementById("academia").setAttribute("required", "required");
        document.getElementById("ataNumber_div").removeAttribute("hidden");
        document.getElementById("ataNumberWorld").setAttribute("required", "required");
        document.getElementById("ataNumberBrasil").setAttribute("required", "required");
        document.getElementById("instrutor").removeAttribute("required");
    }
}
