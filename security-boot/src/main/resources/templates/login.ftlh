<html lang="en">
<head>
    <title>用户登录</title>
</head>
<body>
<form action="login" method="post">
    <#--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />-->
    <table>
        <tr>
            <td><label>用户名：</label></td>
            <td><label><input type="text" name="username"/></label></td>
        </tr>
        <tr>
            <td><label>密码：</label></td>
            <td><label><input type="password" name="password"/></label></td>
        </tr>
        <tr>
            <td><label><input type="submit" value="登录"/></label></td>
        </tr>
    </table>
</form>
</body>
</html>