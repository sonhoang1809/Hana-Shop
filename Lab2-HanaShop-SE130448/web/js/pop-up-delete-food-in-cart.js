/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery(document).ready(function ($) {
//open popup
    $('.qt-remove').click(function () {
        event.preventDefault();
        // $('.cd-popup').addClass('is-visible');
//        var div =
//                "<div class='cd-popup' role='alert'>" +
//                "<div class='cd-popup-container'>" +
//                "<p>Are you sure you want to delete this element?</p>" +
//                "<ul class='cd-buttons'>" +
//                "<li><a href='#0'>Yes</a></li>" +
//                "<li><a href='#0'>No</a></li>" +
//                "</ul>" +
//                "<a href='#0' onclick='$(this).removeClass('is-visible');' class='cd-popup-close img-replace'>Close</a>" +
//                "</div>" +
//                "</div>";
//        $(this).parent().append(div);
        $(this).parent().children(".cd-popup").addClass("is-visible");
    });
    //close popup
    $('.cd-popup').on('click', function (event) {
        if ($(event.target).is('.cd-popup-close') || $(event.target).is('.cd-popup') || $(event.target).is('.cd-no')) {
            event.preventDefault();
            $(this).removeClass('is-visible');
        }
    });



    //close popup when clicking the esc keyboard button
    $(document).keyup(function (event) {
        if (event.which == '27') {
            $('.cd-popup').removeClass('is-visible');
        }
    });
});
function displayPopup() {
    // $(this).parent().parent().children('.cd-popup').addClass('.is-visible');
}