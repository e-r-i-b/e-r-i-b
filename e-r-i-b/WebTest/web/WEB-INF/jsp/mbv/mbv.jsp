<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
    <head><title>Тестирование взаимодействия ЕРИБ с МБВ</title></head>
    <body>
    <h1>Тестирование взаимодействия ЕРИБ с МБВ</h1>

    <html:form action="/mbv" show="true">
        <ul>
            <li>
                Адрес слушателя в МБВ
                <html:text property="MBVMigratorUrl"
                           value="http://localhost:8888/PhizIC-test/axis-services/MBVMigrator"
                           size="100"/>
            </li>
            <li>
                <html:submit property="operation" value="getClientByPhone"/>
               Метод для определения ФИО, ДУЛ, ДР  клиента, подключенного в МБВ,  по номеру телефона
            </li>
            <li>
                <html:submit property="operation" value="clientAccPh"/>
                Получение списка вкладов,списка телефонов с временем последнего использования  в МБВ
            </li>
            <li>
                <html:submit property="operation" value="beginMigration"/>
               Метод старта миграции клиента из МБВ
            </li>
            <li>
                <html:submit property="operation" value="commitMigration"/>
                Метод завершения транзакции миграции клиента
            </li>
            <li>
                <html:submit property="operation" value="rollbackMigration"/>
                Метод отмены старта транзакции миграции клиента
            </li>
            <li>
                <html:submit property="operation" value="discByPhone"/>
                Метод отключения договора МБВ по номеру телефона
            </li>
        </ul>
    </html:form>
    </body>
</html:html>