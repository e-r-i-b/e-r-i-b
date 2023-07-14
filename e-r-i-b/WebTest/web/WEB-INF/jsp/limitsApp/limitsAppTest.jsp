<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 27.01.14
  Time: 12:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>Приложение для работы с лимитами</title></head>
<body>
<h3>Лимиты</h3>
<ul>
    <li><a href='${phiz:calculateActionURL(pageContext,'/limits/add')}'>Лимиты. Добавление транзакции.</a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/limits/rollback')}'>Лимиты. Откат транзакции.</a></li>
</ul>
</body>
</html>