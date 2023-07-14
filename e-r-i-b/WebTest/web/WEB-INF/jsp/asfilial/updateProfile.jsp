<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:html>
<jsp:include page="addPhone.jsp"/>
        ready(function()
        {
            addPhoneField(query("#addphone"),"phoneNumber","mobilePhoneOperator","phoneConfirmHolderCode","phoneAndCode","last");
        });
    });
</script>
<script type="text/javascript">
    function hideTable(el, tableId)
    {
        var table = document.getElementById(tableId);
        if (el.checked === true)
            table.style.display = 'block';
        else
            table.style.display = 'none';

    }

    window.onload = function()
    {
        hideTable(document.getElementsByName('mobileBankService')[0], 'mobileBank');
        hideTable(document.getElementsByName('mobileBankServiceParams')[0], 'mobileBankServiceParamsTable');
        hideTable(document.getElementsByName('clientDataChange')[0], 'clientDataTab');
        hideTable(document.getElementsByName('internetClientServiceChange')[0], 'internetClientServiceTab');
        hideTable(document.getElementsByName('mobileClientServiceChange')[0], 'mobileClientServiceTab');
        hideTable(document.getElementsByName('ATMClientServiceChange')[0], 'ATMClientServiceTab');
        hideTable(document.getElementsByName('informPeriodChange')[0], 'informPeriodTable');
    }
</script>
<head><title>Тестирование взаимодействия ЕРИБ с АС Филиал-Сбербанк</title></head>
<body>
<h1>обновления профиля клиента</h1>

<html:form action="/asfilial" show="true">
<html:hidden property="ASListenerUrl"/>
<table>
<tr>
    <td>
        Идентификационные данные клиента([1])
    </td>
    <td>
        <jsp:include page="clientIdentity.jsp"/>
    </td>
</tr>
<tr>
    <td>информация о подразделении, в рамках которого выполняется запрос[1]</td>
    <td>
        <jsp:include page="bankInfo.jsp"/>
    </td>
</tr>
<tr>
    <td colspan="2">
        <hr/>
        Данные по клиенту([0-1]
        0 – данные по клиенту не меняются)
        <html:checkbox property="clientDataChange" onchange="hideTable(this,'clientDataTab')"/>
    </td>
</tr>
<tr>
    <table id="clientDataTab" style="background:green;">
        <tr>
            <td>
                <a id="addphone" href="#">добавить номер</a>
            </td>
        </tr>
        <tr>
            <td>все телефоны клиента([0-n]).</td>
            <td>
                Номер телефона/Мобильный Оператор/код подтверждения
                <ul id="phoneAndCode"></ul>
            </td>
        </tr>
    </table>
</tr>
<tr>
    <td colspan="2">
        <hr/>
        Данные по услуге «Интернет-клиент»([0-1] 0 – данные по клиенту не меняются)
        <html:checkbox property="internetClientServiceChange"
                       onchange="hideTable(this,'internetClientServiceTab')"/>
    </td>
</tr>
<tr>
    <table id="internetClientServiceTab" style="background:green;">
        <tr>
            <td>
                Продукты, доступные в интернет-приложении ([0-n])(например: card:номер|account:..|loan:..)
            </td>
            <td><html:text property="internetVisibleResources" maxlength="254" size="40" value=""/></td>
        </tr>
    </table>

</tr>
<tr>
    <td colspan="2">
        <hr/>
        Данные по услуге «Мобильный клиент»([0-1] 0 – данные по клиенту не меняются)
        <html:checkbox property="mobileClientServiceChange"
                       onchange="hideTable(this,'mobileClientServiceTab')"/>
    </td>
</tr>
<tr>
    <table id="mobileClientServiceTab" style="background:green;">
        <tr>
            <td>
                Продукты, доступные в мобильном приложении ([0-n])(например:
                card:номер|account:..|loan:..)
            </td>
            <td><html:text property="mobileVisibleResources" maxlength="254" size="40" value=""/></td>
        </tr>
    </table>
</tr>
<tr>
    <td colspan="2">
        <hr/>
        Данные по услуге «Устройства самообслуживания»([0-1] 0 – данные по клиенту не меняются)
        <html:checkbox property="ATMClientServiceChange" onchange="hideTable(this,'ATMClientServiceTab')"/>
    </td>
</tr>
<tr>
    <table id="ATMClientServiceTab" style="background:green;">
        <tr>
            <td>
                Продукты, доступные в ATM([0-n])(например: card:номер|account:..|loan:..)
            </td>
            <td><html:text property="ATMVisibleResources" maxlength="254" size="40" value=""/></td>
        </tr>
    </table>
</tr>
<tr>
    <td colspan="2">
        <hr/>
        Данные по услуге «Мобильный банк»([0-1]0
        - данные по услуге не меняются)
        <html:checkbox property="mobileBankService" onchange="hideTable(this,'mobileBank');"/>
    </td>
</tr>
<tr>
    <td>
        <table id="mobileBank" style="background:green;">
            <tr>
                <td>
                    <hr/>
                    Флажок «услуга подключена».
                    <html:checkbox property="registrationStatus"/>
                </td>
            </tr>
            <tr>
                <td>
                    <hr/>
                    Параметры услуги([0-1]0 , если услуга отключена)
                    <html:checkbox property="mobileBankServiceParams"
                                   onchange="hideTable(this,'mobileBankServiceParamsTable');"/>
                </td>
            <tr>
                <td>
                    <table id="mobileBankServiceParamsTable" style="background:gold;">
                        <tr>
                            <td>
                                <hr/>
                                Тарифный план full – полный saving - экономный
                                <html:select property="tariffId">
                                    <html:option value="full"/>
                                    <html:option value="saving"/>
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                Признак включения для клиента возможности оплаты чужого телефона и
                                переводов по номеру телефона([1])
                                <html:checkbox property="quickServices"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                Данный телефон является «активным». На него будут отправляться
                                одноразовые пароли и уведомления МБ. С него же будут поступать
                                запросы от клиента.
                                Должен входить в общий список телефонов клиента
                                ([0-1])
                                Номер:
                                <html:text property="activePhoneNumber" maxlength="11" size="11"/>
                                Мобильный Оператор:
                                <html:text property="activeMobilePhoneOperator" maxlength="100" size="10"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                Продукты, доступные в СМС-канале.
                                Должны входить в исходный список продуктов из профиля [0-n]
                                <html:text property="visibleResources"  maxlength="254" size="40" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                Продукты клиента, на которые должны отправляться оповещения.Должны
                                входить в исходный список продуктов из профиля([0-n])
                                <html:text property="informResources" maxlength="254" size="40"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                Номер приоритетной карты для списания абонентской платы ([1])
                                <html:text property="chargeOffCard" maxlength="19" size="19"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                Флажок «отправлять уведомления по вновь добавленному
                                продукту»([1])
                                <html:checkbox property="informNewResource"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                Временные интервалы, в которые разрешено отправлять уведомления([0-1])
                                <html:checkbox property="informPeriodChange"
                                               onchange="hideTable(this,'informPeriodTable');"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <table id="informPeriodTable" style="background:red;">
                                    <tr>
                                        <td>График оповещений(HH:MM):
                                            c ([1])
                                            <html:text property="ntfStartTimeString" maxlength="5" size="5"/>
                                            до ([1])
                                            <html:text property="ntfEndTimeString" maxlength="5" size="5"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Часовой пояс([1]):
                                            <html:select property="timeZone">
                                                <option value="120">
                                                    Калининградское время (MSK -01:00)
                                                </option>
                                                <option value="180">
                                                    Московское время (MSK)
                                                </option>
                                                <option value="240">
                                                    Самарское время (MSK +01:00)
                                                </option>
                                                <option value="300">
                                                    Екатеринбургское время (MSK +02:00)
                                                </option>
                                                <option value="360">
                                                    Омское время  (MSK +03:00)
                                                </option>
                                                <option value="420">
                                                    Красноярское время (MSK +04:00)
                                                </option>
                                                <option value="480">
                                                    Иркутское время (MSK +05:00)
                                                </option>
                                                <option value="540">
                                                    Якутское время (MSK +06:00)
                                                </option>
                                                <option value="600">
                                                    Владивостокское время (MSK +07:00)
                                                </option>
                                                <option value="660">
                                                    Среднеколымское время (MSK +08:00)
                                                </option>
                                                <option value="720">
                                                    Камчатское время (MSK +09:00)
                                                </option>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td> ([0-7])
                                            <html:multibox property="ntfDays" value="MON"/> пн.
                                            <html:multibox property="ntfDays" value="TUE"/> вт.
                                            <html:multibox property="ntfDays" value="WED"/> ср.
                                            <html:multibox property="ntfDays" value="THU"/> чт.
                                            <html:multibox property="ntfDays" value="FRI"/> пт.
                                            <html:multibox property="ntfDays" value="SAT"/> сб.
                                            <html:multibox property="ntfDays" value="SUN"/> вс.
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>


                        <tr>
                            <td>
                                <hr/>
                                Признак запрета рекламных рассылок([1])
                                <html:checkbox property="suppressAdvertising"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

        </table>
    </td>
</tr>
</table>

<html:submit property="operation" value="UpdateProfile"/>
<html:submit property="operation" value="Back"/>
</html:form>
</body>
</html:html>