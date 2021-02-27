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

    competidorDTO.categoriaCompeticaoFechada = [];
    if (document.getElementById('fechado_T').checked === true) {
        for (var i = 0; i < $("#quant_cat_fec").val(); i++) {
            competidorDTO.categoriaCompeticaoFechada.push($("#select_categoria_fechada_" + i).val());
        }
    }

    $.ajax({
        method: "POST",
        url: "/save/competidor",
        contentType: 'application/json',
        data: JSON.stringify(competidorDTO),
        success: function (result) {
            top.location.href = "/";
        },
        error: function (xhr) {
            console.log("error: ", xhr.responseText);
        }
    });

});

var categorias = {};
$.ajax({
    method: "POST",
    url: "/categorias/competicao",
    success: function (response) {
        categorias = response;
    }
});

$("#quantidade-categorias").change(function (evt) {

    if (document.getElementById("categorias_select_div").firstChild) {
        while (document.getElementById("categorias_select_div").firstChild) {
            document.getElementById("categorias_select_div").firstChild.remove();
        }
    }

    for (var i = 0; i < $("#quantidade-categorias").val(); i++) {
        var div = document.getElementById("categorias_select_div");
        var label = document.createElement("label");
        label.textContent = (i + 1) + "º Categoria:";
        div.appendChild(label);
        var select = document.createElement("select");
        select.className = "form-control";
        select.id = "select_categoria_" + i;
        select.name = "select_categoria_" + i;
        select.required = true;
        var option = document.createElement("option");
        option.value = "";
        option.disabled = true;
        option.selected = true;
        option.text = "Selecione";
        select.appendChild(option);
        for (var j in categorias) {
            var option = document.createElement("option");
            option.value = categorias[j].id;
            option.text = categorias[j].nome;
            select.appendChild(option);
        }
        div.appendChild(select);
    }
});

var categoriasFechadas = {};
$.ajax({
    method: "POST",
    url: "/categorias/rank/pessoa/" + $("#pessoa_id").val(),
    success: function (response) {
        categoriasFechadas = response;
    }
});

function forCatFech() {
    if (document.getElementById('fechado_T').checked === true) {
        if (categoriasFechadas == null || categoriasFechadas.length === 0) {
            var label = document.createElement("label");
            label.textContent = "Não há categorias para se inscrever";
            label.id = "cat_fec_lb_temp"
            document.getElementById("categorias_fechadas").appendChild(label);
        } else {
            document.getElementById("quant_cat_fec_lb").removeAttribute("hidden");
            document.getElementById("quant_cat_fec").removeAttribute("hidden");
        }
    } else {
        if(document.getElementById("cat_fec_lb_temp")){
            document.getElementById("cat_fec_lb_temp").remove();
        }
        $("#quant_cat_fec_lb").hidden = true;
        $("#quant_cat_fec").hidden = true;
        document.getElementById("quant_cat_fec_lb").setAttribute("hidden", "hidden");
        document.getElementById("quant_cat_fec").setAttribute("hidden", "hidden");
        $("#quant_cat_fec").val(0);
        if (document.getElementById("categorias_fechadas_select_div").firstChild) {
            while (document.getElementById("categorias_fechadas_select_div").firstChild) {
                document.getElementById("categorias_fechadas_select_div").firstChild.remove();
            }
        }
    }
}

$("#quant_cat_fec").change(function () {
    if (document.getElementById("categorias_fechadas_select_div").firstChild) {
        while (document.getElementById("categorias_fechadas_select_div").firstChild) {
            document.getElementById("categorias_fechadas_select_div").firstChild.remove();
        }
    }
    for (var i = 0; i < $("#quant_cat_fec").val(); i++) {
        var div = document.getElementById("categorias_fechadas_select_div");
        var label = document.createElement("label");
        label.textContent = (i + 1) + "º Categoria fechada:";
        div.appendChild(label);
        var select = document.createElement("select");
        select.className = "form-control";
        select.id = "select_categoria_fechada_" + i;
        select.name = "select_categoria_fechada_" + i;
        select.required = true;
        var option = document.createElement("option");
        option.value = "";
        option.disabled = true;
        option.selected = true;
        option.text = "Selecione";
        select.appendChild(option);
        for (var j in categoriasFechadas) {
            var option = document.createElement("option");
            option.value = categoriasFechadas[j].id;
            option.text = categoriasFechadas[j].nome;
            select.appendChild(option);
        }
        div.appendChild(select);
    }
});