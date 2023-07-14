<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html>
    <head>
        <title>Тестовая страница шлюза к ВС ФМ</title>
    </head>
    <body>
        <h3>Тесты</h3>
        <ul>
            <li>
                <a href='${phiz:calculateActionURL(pageContext,'/monitoring/fraud/ws')}'>Обратный ws</a>
            </li>
            <li>
                <a href='${phiz:calculateActionURL(pageContext,'/monitoring/fraud/jms')}'>Очереди</a>
            </li>
        </ul>
    </body>
</html>