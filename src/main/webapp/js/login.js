$(function () {
    ///^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/
    $(".sublogin").click(function () {
        //清空信息
        $(this).next().next().remove();
        //验证用户名非空
        var name = $("#name").val();
        if (name == null || name.trim().length < 4) {
            $(this).next().html(" <span class='fa fa-times-rectangle' style='color:red'></span> 用户名不能为空，并且长度需4位以上");
            return;
        }
        //验证密码非空
        var pwd = $("#pwd").val();
        if (pwd == null || pwd.trim().length < 3) {
            $(this).next().html(" <span class='fa fa-times-rectangle' style='color:red'></span> 密码不能为空，并且长度需3位以上");
            return;
        }
        $("#myform")[0].submit();
    })
})