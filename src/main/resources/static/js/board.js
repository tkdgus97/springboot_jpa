$(document).ready(function () {
    $(".board-content").click((e) => {
        let id = e.currentTarget.children[0].id;
        location.href = "/board/detail/"+id;
    })
})
