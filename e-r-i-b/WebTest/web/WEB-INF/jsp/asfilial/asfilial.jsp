<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
    <head><title>Тестирование взаимодействия ЕРИБ с АС Филиал-Сбербанк</title></head>
    <body>
    <h1>Тестирование взаимодействия ЕРИБ с АС Филиал-Сбербанк</h1>

    <html:form action="/asfilial" show="true">
        <ul>
            <li>
                Адрес слушателя в ЕРИБ
                <html:text property="ASListenerUrl"
                           value="http://localhost:8888/ASFilialListener/axis-services/ASFilialInfoServicePort"
                           size="100"/>
            </li>
            <li>
                <html:submit property="operation" value="QueryProfile"/>
                получения профиля клиента в ЕРИБ
            </li>
            <li>
                <html:submit property="operation" value="UpdateProfile"/>
                обновления профиля клиента в ЕРИБ
            </li>
            <li>
                <html:submit property="operation" value="ConfirmPhoneHolder"/>
                подтверждения держателя номера мобильного телефона
            </li>
            <li>
                <html:submit property="operation" value="RequestPhoneHolder"/>
                проверки уникальности номера мобильного телефона
            </li>
        </ul>
    </html:form>
    </body>
</html:html>