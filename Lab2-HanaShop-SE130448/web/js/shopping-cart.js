/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var check = false;

function changeVal(el) {
    var qt = parseFloat(el.parent().children(".qt").html());
    var price = parseFloat(el.parent().children(".price").html());
    var eq = Math.round(price * qt * 100) / 100;
    el.parent().children(".full-price").html(eq + "$");
    changeTotal();
}

function changeTotal() {
    var price = 0;
    $(".full-price").each(function (index) {
        price += parseFloat($(".full-price").eq(index).html());
    });

    price = Math.round(price * 100) / 100;

    var tax = Math.round(price * 0.1 * 100) / 100;

    var fullPrice = Math.round((price + tax) * 100) / 100;

    if (price === 0) {
        fullPrice = 0;
    }

    $(".subtotal span").html(price);
    $(".tax span").html(tax);
    $(".total span").html(fullPrice);
}

function changeQuantity(id, child) {

    $("#" + id + "*").val(id + "-" + child.html());

//    document.getElementById(id).value = child.html();
//    console.log($("#" + id ).val());
}

$(document).ready(function () {
    $(".remove").click(function () {
        var el = $(this);
        el.parent().parent().addClass("removed");
        window.setTimeout(
                function () {
                    el.parent().parent().slideUp('fast', function () {
                        el.parent().parent().remove();
                        if ($(".product").length === 0) {
                            if (check) {
                                $("#cart").html("<h3>No products!</h3>");
                            } else {
                                $("#cart").html("<h3>No products!</h3>");
                            }
                        }
                        changeTotal();
                    });
                }, 200);
    });

    $(".qt-plus").click(function () {
        var id = $(this).parent().children(".qt").prop("id");
        var child = $(this).parent().children(".qt");
        console.log(id);

//        var maxQuantity = $(this).parent().children(".maxQuantity").val();
//        var currentQuantity = parseInt(child.html()) + 1;
//        console.log(currentQuantity);
//        if (currentQuantity > maxQuantity) {
//            console.log(currentQuantity + ">" + maxQuantity);
//            $(this).parent().children(".message-popup").addClass("is-visible");
//            $(".message-popup-container").children(".message-display").replaceWith("<p class='message-display'>This quantity is out of stock !!</p>");
//        } else {
        $(this).parent().children(".qt").html(parseInt($(this).parent().children(".qt").html()) + 1);
        changeQuantity(id, child);
        //       }
//        console.log(maxQuantity);
//        document.getElementsByClassName(id).value=child.html();
//        console.log(document.getElementsByClassName(id).value);



        $(this).parent().children(".full-price").addClass("added");
        var el = $(this);
        window.setTimeout(function () {
            el.parent().children(".full-price").removeClass("added");
            changeVal(el);
        }, 150);
    });

    $(".qt-minus").click(function () {

        child = $(this).parent().children(".qt");

        if (parseInt(child.html()) > 1) {
            child.html(parseInt(child.html()) - 1);
            var id = $(this).parent().children(".qt").prop("id");
            console.log(id);
            console.log(child.html());
//        $(this).closest("input:hidden").val(child.html);
//        console.log($(this).closest("input:hidden").val());
            changeQuantity(id, child );
            $(this).parent().children(".full-price").addClass("minused");
            var el = $(this);
            window.setTimeout(function () {
                el.parent().children(".full-price").removeClass("minused");
                changeVal(el);
            }, 150);
        } else {
            $(this).parent().children(".cd-popup").addClass("is-visible");

        }


    });

    window.setTimeout(function () {
        $(".is-open").removeClass("is-open");
    }, 1200);

    $(".btn").click(function () {
        check = true;
        $(".remove").click();
    });
});

