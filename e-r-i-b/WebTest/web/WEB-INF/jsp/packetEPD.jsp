<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head><title>Make FNS</title></head>
<body>
    <form action="http://localhost:8888/PhizIC-test/fns.do" method="POST" target="_blank">
        Введите количество платежных поручений:<input type="text" name="countPayment" value="1"/>
        <br/>
        Введите url страницы логина для редиректа:<input type="text" name="loginUrl" size="80" value="http://localhost:8888/CSAFront/payOrderPaymentLogin.do"/>
        <br/>
        <input type="submit" value="Сформировать"/>
    </form>
</body>
