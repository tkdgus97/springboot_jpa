$(document).ready(function (){
    $("#login_btn").click(() =>  {
        location.href = "/login";
    });

    $("#signup_btn").click(() =>  {
        location.href = "/signup";
    });

    $("#logout_btn").click(() => {
        location.href = "/logout";
    })

    $("#board-add").click(() => {
        location.href="/board/add";
    })

    $("#game-start").click(() => {
        location.href="/game/character";
    })

    $(".create-unit").click(() => {
        location.href = "/game/create";
    })

    $(".select-job").click((e) => {
        if (e.currentTarget.id == "warrior"){
            $("#job").val("전사");
        } else if(e.currentTarget.id == "bow") {
            $("#job").val("궁수");
        } else if(e.currentTarget.id == "wizard") {
            $("#job").val("마법사");
        }
    })
})

