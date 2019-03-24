<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册</title>
    <%--<link rel="stylesheet" type="text/css" href="./css/style.css">--%>
    <link rel="stylesheet" type="text/css" href="./css/reset.css">
    <link rel="stylesheet" type="text/css" href="./css/iconfont.css">
    <link rel="stylesheet" type="text/css" href="./css/style_1.css">
</head>

<script type="text/javascript">
    function getSandSms() {
        var phone = document.getElementById("phonenumber");
        $.ajax({
                url: "${pageContext.request.contextPath}/sandSmsServlet?loginId=" + phone.value,
                type: 'get',
                async: true
            }
        );
    }
</script>



<script>
    window.onload = function(){
        document.getElementById("img").onclick = function(){
            this.src="${pageContext.request.contextPath}/checkCodeServlet?time"+new Date().getTime();
        }
    }</script>
<style>
    div{
        /*color: red;*/
    }
</style>

<body>

<form action="${pageContext.request.contextPath}/registServlet" method="post">

<div class="public_header">
    <div class="public_container">
        <div class="header_logo" onclick="" style="cursor: pointer;">
            <div class="header_logo">
                <img class="logo" src="./img/logo.png" alt="">
            </div>
        </div>
        <ul class="header_ul">
            <a href="" title=""><li class="clearfloat">首页</li></a>
            <a href="" title=""><li class="clearfloat">视频</li></a>
            <a href="" title=""><li class="clearfloat">发现</li></a>
            <a href="" title=""><li class="clearfloat">游戏</li></a>
        </ul>
        <div class="header_search">
            <input type="text" name="">
            <i class="iconfont">&#xe600;</i>
        </div>
        <div class="header_rank">
            <a href="" title="">
                <a href="" title=""><i class="iconfont">&#xe665;</i></a>
                <p class="rank_text">排行榜</p>
            </a>
        </div>
        <div class="header_register">
            <a href="/login" title="" class="register_p1"><p>登录</p></a>
            <p class="register_string"></p>
            <a href="" title="" class="register_p2"><p>注册</p></a>
        </div>
    </div>
</div>

<div class="main">
    <div class="public_container">
        <div class="content">
            <p class="title">账号注册</p>

            <input id="phonenumber" type="tel" name="loginId" placeholder="请输入手机号" value="<%=request.getSession().getAttribute("id") == null ? "" : request.getSession().getAttribute("id")%>">
            <p id="phonenumber_collect" style="display: none;"></p>

            <input id="pic_verification" type="text" name="checkCode" placeholder="请输入图形验证码" class="verification_text">
            <p id="pic_verification_collect" style="display: none;"></p>

            <img src="${pageContext.request.contextPath}/checkCodeServlet" alt="img" id="img" class="verification_img">

            <input id="password" type="password" name="password" placeholder="请输入密码" value="<%=request.getSession().getAttribute("password") == null ? "" : request.getSession().getAttribute("password")%>">
            <p id="password_collect" style="display: none;"></p>

            <input id="password_again" type="password" name="password2" placeholder="请再次输入密码" value="<%=request.getSession().getAttribute("password2") == null ? "" : request.getSession().getAttribute("password2")%>">
            <p id="password_again_collect" style="display: none;"></p>

            <input id="message_verification" type="text" name="sms" placeholder="请输入短信验证码" class="message_verification" value="<%=request.getSession().getAttribute("sms") == null ? "" : request.getSession().getAttribute("sms")%>">
            <p id="message_verification_collect" style="display: none;"></p>

            <input id="" type="button" name="" value="点击获取" class="click" onclick="getSandSms()">
            <p id="sms_collect" style="display: none;"></p>

            <div style="height:20px;width:200px;margin: 0 auto;text-align: center;"><%=request.getSession().getAttribute("regist_error") == null ? "" : request.getSession().getAttribute("regist_error")%></div>
            <input id="button" type="submit" name="" value="注册" class="submit" >

        </div>

    </div>
</div>

<div class="copyright">
    <div class="public_container">
        <p>Copyright © 2018 云Design Powered By 云顶书院 测试项目</p>
    </div>
</div>

</form>
</body>
</html>

<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>

<%--<script type="text/javascript" src="./js/index.js"></script>--%>