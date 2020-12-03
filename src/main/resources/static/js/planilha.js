$.ajax({
    method: "POST",
    url: "/torneio/numero/ringues/" + $("#torneio_id").val(),
    contentType: 'application/json',
    success: function (result) {
        console.log("numero ringue: ");
        console.log(result);
        var div = document.getElementById("seletor");
        var label = document.createElement("label");
        label.id = "numero_ringue_lab";
        label.textContent = "Numero do ringue:";
        div.appendChild(label);
        var select = document.createElement("select");
        select.id = "numero_ringue";
        select.className = "form-control";
        select.required = true;
        var option = document.createElement("option");
        option.value = "";
        option.disabled = true;
        option.selected = true;
        option.text = "Selecione";
        select.appendChild(option);
        for (var i = 1; i <= result; i++) {
            option = document.createElement("option");
            option.value = i;
            option.text = i;
            select.appendChild(option);
        }
        div.appendChild(select);
    }
});

var ringue_atual = {};

$(document).on("change", "select[id='numero_ringue']", function () {
    document.getElementById("numero_ringue_lab").setAttribute("hidden", "hidden");
    document.getElementById("numero_ringue").setAttribute("hidden", "hidden");

    $.ajax({
        method: "POST",
        url: "/ringue/lista/" + $("#numero_ringue").val(),
        contentType: 'application/json',
        success: function (result) {
            console.log("ringue lista: ");
            console.log(result);
            if (result == null || result.length === 0) {
                document.getElementById("numero_ringue_lab").removeAttribute("hidden");
                document.getElementById("numero_ringue").removeAttribute("hidden");

                var div = document.getElementById("seletor");
                var label = document.createElement("label");
                label.textContent = "Este numero não possui ringues";
                div.appendChild(label);
            } else {
                ringue_atual = result[0];
                inicia_ringue();
            }
        }
    });
});

var planilhas_lista = {};
var planilhas_chave = {};

function inicia_ringue() {
    console.log("ringue atual: ");
    console.log(ringue_atual);
    var div = document.getElementById("seletor");
    var label = document.createElement("label");
    label.id = "planilha_select_lb";
    label.textContent = "Planilha:";
    div.appendChild(label);
    var select = document.createElement("select");
    select.id = "planilha_select";
    select.className = "form-control";
    select.required = true;
    var option = document.createElement("option");
    option.value = "";
    option.disabled = true;
    option.selected = true;
    option.text = "Selecione";
    select.appendChild(option);
    $.ajax({
        method: "POST",
        url: "/ringue/lista/planilhas/lista/" + ringue_atual.id,
        contentType: 'application/json',
        success: function (result) {
            planilhas_lista = result;
            console.log("planilha lista: ");
            console.log(result);
            select = document.getElementById("planilha_select");
            for (i in result) {
                option = document.createElement("option");
                option.value = result[i].id + "-" + result[i].categoriaCompeticao.tipoChave;
                option.text = result[i].categoriaCompeticao.nome;
                select.appendChild(option);
            }
        }
    });
    $.ajax({
        method: "POST",
        url: "/ringue/lista/planilhas/chave/" + ringue_atual.id,
        contentType: 'application/json',
        success: function (result) {
            planilhas_chave = result;
            console.log("planilha chave: ");
            console.log(result);
            select = document.getElementById("planilha_select");
            for (i in result) {
                option = document.createElement("option");
                option.value = result[i].id + "-" + result[i].categoriaCompeticao.tipoChave;
                option.text = result[i].categoriaCompeticao.nome;
                select.appendChild(option);
            }
        }
    });
    div.appendChild(select);
}

$("#ter_ringue").on("click", function (){
    $.ajax({
        method: "POST",
        url: "/ringue/finalizar/" + ringue_atual.id,
        success:function (){
            document.getElementById("planilha_select").remove();
            document.getElementById("planilha_select_lb").remove();
            $.ajax({
                method: "POST",
                url: "/ringue/lista/" + $("#numero_ringue").val(),
                contentType: 'application/json',
                success: function (result) {
                    console.log("ringue lista: ");
                    console.log(result);
                    if (result == null || result.length === 0) {
                        document.getElementById("numero_ringue_lab").removeAttribute("hidden");
                        document.getElementById("numero_ringue").removeAttribute("hidden");

                        var div = document.getElementById("seletor");
                        var label = document.createElement("label");
                        label.textContent = "Este numero não possui mais ringues";
                        div.appendChild(label);
                    } else {
                        for (i in result){
                            if(result[i].numeroRodada === ringue_atual.numeroRodada + 1){
                                ringue_atual = result[0];
                                break;
                            }
                        }
                        inicia_ringue();
                    }
                }
            });
        }
    });
});

$(document).on("change", "select[id='planilha_select']", function () {
    var id_plan = $("#planilha_select").val().substr(0, 1);
    var tipo_chave = $("#planilha_select").val().substr(2);

    if(tipo_chave === "true"){
        console.log($("#planilha_select").val());
    }else if(tipo_chave === "false"){
        console.log(id_plan);
    }

});