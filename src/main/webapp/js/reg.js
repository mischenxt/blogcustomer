$(function () {
    $(".subreg").click(function () {
        //清空信息
        $(this).next().next().remove();
        //注册用户名验证非空
        var name = $("#name").val();
        if (name == null || name.trim().length < 4) {
            $(this).next().html(" <span class='fa fa-times-rectangle' style='color:red'></span> 用户名不能为空，并且长度需4位以上");
            return;
        }
        //注册电子邮箱格式验证
        var email = $("#email").val();
        var zheng = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
        if (email == null || !zheng.test(email)) {
            $(this).next().html(" <span class='fa fa-times-rectangle' style='color:red'></span> 电子邮箱不能为空，并且必须可用");
            return;
        }
        //注册密码验证非空
        var pwd = $("#pwd").val();
        if (pwd == null || pwd.trim().length < 3) {
            $(this).next().html(" <span class='fa fa-times-rectangle' style='color:red'></span> 密码不能为空，并且长度需3位以上");
            return;
        }
        //注册确认密码验证一致
        var repwd = $("#repwd").val();
        if (repwd != pwd) {
            $(this).next().html(" <span class='fa fa-times-rectangle' style='color:red'></span> 两次密码输入不一致");
            return;
        }
        $("#myform")[0].submit();
    })
})