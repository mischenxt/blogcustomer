$(function () {
    $(".subcomm").click(function () {
        //验证用户输入的评论给非空
        var txt = $(this).parent().prev().val();
        if (txt == null || txt.trim() == "") {
            $(this).parent().next().html("请填写评论内容");
        } else {
            $(this).parents("form")[0].submit();
        }
    })
})