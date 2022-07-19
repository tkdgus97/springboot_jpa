import Ckeditor from "../ckeditor5/src/ckeditor";

$(document).ready(function () {
    var myClassEditor;
    ClassicEditor
        .create(document.querySelector("#editor"), {
            language: "ko",
            // 폰트 종류
            fontFamily: {
                options: [
                    'default', '궁서체', '나눔고딕/나눔고딕', 'Nanum Gothic', 'ng', 'sans-serif',
                    'Ubuntu', 'monospace', 'Arial', 'Courier'
                ]
            },
            fontSize: {
                options: [8, 10, 12, 'default', 14, 16, 20, 25, 30, 35, 40]
            },
            resizeOptions: [
                {
                    name: 'resizeImage:original',
                    value: null,
                    label: 'Original'
                },
                {
                    name: 'resizeImage:40',
                    value: '40',
                    label: '40%'
                },
                {
                    name: 'resizeImage:60',
                    value: '60',
                    label: '60%'
                }
            ],
            extraPlugins: [CustomUploadAdapter]
        })
        .then(editor => {
            myClassEditor = editor;
        })
        .catch(error => {
            console.log(error);
        })

    function CustomUploadAdapter(editor) {
        editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
            return new UploadAdapter(loader);
        }
    }

    $("#save").click(function () {
        console.log(myClassEditor.getData());
        let data = {
            "title": $("#title").val(),
            "content": $("#editor").val()
        };
        $.ajax({
            type: "POST",
            url: "/board/add",
            data: data,
            success: function () {
                alert("성공")
            },
            error: function () {
                alert("실패")
            }
        })
    })
})
