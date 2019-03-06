<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
    <script>
    window.onload = function(){
        document.getElementById("img").onclick = function(){
            this.src="/regist1.0/checkCodeServlet?time"+new Date().getTime();
        }
    }
    </script>
    <style>
        div{
            color: red;
        }
    </style>

<body>
<form action="${pageContext.request.contextPath}/loginServlet" method="post">
    <table>
        <tr>
            <td>登录账号</td>
            <td><input type="text" name="loginId" value="<%=request.getAttribute("loginId") == null ? "" : request.getAttribute("loginId")%>"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td>验证码</td>
            <td><input type="text" name="checkCode"></td>
        </tr>
        <tr>
            <td colspan="2"><img id="img" src="${pageContext.request.contextPath}/checkCodeServlet"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="登录"></td>
        </tr>
    </table>
</form>

    <div><%=request.getAttribute("cc_error") == null ? "" : request.getAttribute("cc_error")%></div>
    <div><%=request.getAttribute("login_error") == null ? "" : request.getAttribute("login_error")%></div>

</body>
</html>
