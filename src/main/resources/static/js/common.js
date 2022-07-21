$(document).ready(function (){
    $("#login_btn").click(function () {
        location.href = "/login";
    });

    $("#signup_btn").click(function () {
        location.href = "/signup";
    });

    $("#logout_btn").click(function (){
        location.href = "/logout";
    })

    $("#board-add").click(function (){
        location.href="/board/add";
    })

    $("#game-start").click(function (){
        location.href="/game/selectCharacter";
    })
})

