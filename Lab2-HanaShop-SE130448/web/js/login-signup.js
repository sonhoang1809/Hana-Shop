/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function onSignIn(googleUser) {
    logOut();
    var kindID = $("#kindID").val();
    var categoryID = $("#categoryID").val();
    var priceLower = $("#priceLower").val();
    var priceHigher = $("#priceHigher").val();
    var searchName = $("#searchName").val();
    var profile = googleUser.getBasicProfile();
    var imageURL = profile.getImageUrl();
    var name = profile.getName();
    var email = profile.getEmail();
    var ID = profile.getId();
    var redirectUrl = 'LoginByGoogleController';
    console.log(googleUser.getBasicProfile());
    console.log('ID: ' + profile.getId());
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    console.log('id_token: ' + googleUser.getAuthResponse().id_token);
    var form = $('<form action="' + redirectUrl + '" method="post">' +
            '<input type="text" name="id_token" value="' +
            googleUser.getAuthResponse().id_token + '" />' +
            '<input type="hidden" name="ID" value="' + ID + '" />' +
            '<input type="hidden" name="name" value="' + name + '" />' +
            '<input type="hidden" name="email" value="' + email + '" />' +
            '<input type="hidden" name="avatar" value="' + imageURL + '" />' +
            '<input type="hidden" name="kindID" value="' + kindID + '" />' +
            '<input type="hidden" name="categoryID" value="' + categoryID + '" />' +
            '<input type="hidden" name="priceLower" value="' + priceLower + '" />' +
            '<input type="hidden" name="priceHigher" value="' + priceHigher + '" />' +
            '<input type="hidden" name="searchName" value="' + searchName + '" />' +
            '</form>');
    $('body').append(form);
    form.submit();
}
function onSignup(googleUser) {
    logOut();
    var profile = googleUser.getBasicProfile();
    var imageURL = profile.getImageUrl();
    var name = profile.getName();
    var email = profile.getEmail();
    var ID = profile.getId();
    var redirectUrl = 'SignupByGoogleController';
    console.log(googleUser.getBasicProfile());
    console.log('ID: ' + profile.getId());
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    console.log('id_token: ' + googleUser.getAuthResponse().id_token);
    var form = $('<form action="' + redirectUrl + '" method="post">' +
            '<input type="text" name="id_token" value="' +
            googleUser.getAuthResponse().id_token + '" />' +
            '<input type="hidden" name="ID" value="' + ID + '" />' +
            '<input type="hidden" name="name" value="' + name + '" />' +
            '<input type="hidden" name="email" value="' + email + '" />' +
            '<input type="hidden" name="avatar" value="' + imageURL + '" />' +
            '<input type="hidden" name="action" value="SignupByGoogle" />' +
            '</form>');
    $('body').append(form);
    form.submit();
}
function logOut() {
    gapi.auth2.getAuthInstance().signOut().then(function () {
        console.log('User signed out.');
    });
    location.reload();
}