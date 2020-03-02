/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function changeQuantity(id, child) {

    $("#" + id + "*").val(child.html());

//    document.getElementById(id).value = child.html();
//    console.log($("#" + id ).val());
}
$(document).ready(function () {
    $(".qt-plus").click(function () {
        var id = $(this).parent().children(".qt").prop("id");
        var child = $(this).parent().children(".qt");
        console.log(id);

        //       var maxQuantity = $(this).parent().children(".maxQuantity").val();
        //       var currentQuantity = parseInt(child.html()) + 1;
        //       console.log(currentQuantity);
//        if (currentQuantity > maxQuantity) {
//            console.log(currentQuantity + ">" + maxQuantity);
//            $(this).parent().children(".message-popup").addClass("is-visible");
//            $(".message-popup-container").children(".message-display").replaceWith("<p class='message-display'>This quantity is out of stock !!</p>");
//        } else {
        $(this).parent().children(".qt").html(parseInt($(this).parent().children(".qt").html()) + 1);
        changeQuantity(id, child);
        //    }

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
            changeQuantity(id, child);

        }
    });
});

