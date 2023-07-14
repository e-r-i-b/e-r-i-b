<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:html>
    <head><title>Тестирование взаимодействия ЕРИБ с СРМ</title></head>
    <body>

    <html:form action="/private/loanclaim/crm/test" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:importAttribute/>
        <c:if test="${not empty form.infoMessage}">
          <font size="4" color="red">
              <c:out  value="${form.infoMessage}"/>
          <font size="4" color="red">
        </c:if>

        <table>
            <tr>
                <td>
                    Уникальный  номер заявки во внешней системе (номер/идентификатор заявки в ЕРИБ) [1]
                </td>
                <td>
                    <html:text property="number" maxlength="50" size="50" value="1234567890"/>
                </td>
            </tr>
            <tr>
                <td>
                    Источник заявки, он же Канал создания заявки (1=Web, 2=ТМ внутренний, 3=ТМ внешний, 4=ВСП, 5=ЕРИБ-СБОЛ, 6=ЕРИБ-МП, 7=ЕРИБ-УС, 8=ЕРИБ-МБ, 9=ЕРИБ-ГОСТЕВОЙ) [1]
                </td>
                <td>
                    <html:select property="channelType">
                        <html:option value="1">Web</html:option>
                        <html:option value="2">ТМ внутренний</html:option>
                        <html:option value="3">ТМ внешний</html:option>
                        <html:option value="4">ВСП</html:option>
                        <html:option value="5">ЕРИБ-СБОЛ</html:option>
                        <html:option value="6">ЕРИБ-МП</html:option>
                        <html:option value="7">ЕРИБ-УС</html:option>
                        <html:option value="8">ЕРИБ-МБ</html:option>
                        <html:option value="9">ЕРИБ-ГОСТЕВОЙ</html:option>
                    </html:select>
                </td>
            </tr>
            <tr>
                <td>
                    Логин сотрудника, оформившего заявку в ЕРИБ (Login сотрудника в СУДИР) [0-1]
                </td>
                <td>
                    <html:text property="employerLogin" maxlength="50" size="50" value="1234567890"/>
                </td>
            </tr>
            <tr>
                <td>
                    ФИО сотрудника, оформившего заявку ЕРИБ [0-1]
                </td>
                <td>
                    <html:text property="employerFIO" maxlength="255" size="50" value="Сотрудников Сотрудник Сотрудникович"/>
                </td>
            </tr>
            <tr>
                <td>
                    Имя клиента [1]
                </td>
                <td>
                    <html:text property="firstName" maxlength="160" size="50" value="Клиент"/>
                </td>
            </tr>
            <tr>
                <td>
                    Фамилия клиента [1]
                </td>
                <td>
                    <html:text property="lastName" maxlength="160" size="50" value="Клиентов"/>
                </td>
            </tr>
            <tr>
                <td>
                    Отчество клиента [0-1]
                </td>
                <td>
                    <html:text property="middleName" maxlength="160" size="50" value=""/>
                </td>
            </tr>
            </tr>
            <tr>
                <td>
                    Дата рождения(dd.mm.YYYY)[1]
                </td>
                <td>
                    <html:text  property="birthDay" maxlength="10" size="10" value="21.10.1983"/>
                </td>
            </tr>
            <tr>
                <td>
                    Номер паспорта РФ: серия + номер через пробел [1]
                </td>
                <td>
                    <html:text property="passportNumber" maxlength="32" size="32" value="1234 123456"/>
                </td>
            </tr>
            <tr>
                <td>
                    Номер карты в Way (идентификатор карты в карточной системе). Для случая,  если клиент откликнулся в канале «УС» [0-1]
                </td>
                <td>
                    <html:text property="wayCardNumber" maxlength="30" size="30" value="123456"/>
                </td>
            </tr>
            <tr>
                <td>
                    Идентификатор участника кампании.  Заполняется в случае участия клиента в кампании [0-1]
                </td>
                <td>
                    <html:text property="campaingMemberId" maxlength="50" size="50"/>
                </td>
            </tr>
            <tr>
                <td>
                    Мобильный телефон [1]
                </td>
                <td>
                    <html:text property="mobilePhone" maxlength="12"  size="12" value="79510734345"/>
                </td>
            </tr>
            <tr>
                <td>
                    Рабочий телефон [0-1]
                </td>
                <td>
                    <html:text property="workPhone" maxlength="12" size="12" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    Дополнительный  телефон [0-1]
                </td>
                <td>
                    <html:text property="addPhone" maxlength="12" size="12" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    Наименование продукта из справочника кредитных продуктов ЕРИБ  [1]
                </td>
                <td>
                    <html:text property="productName" maxlength="250" size="50" value="Кредит на покупку кошки"/>
                </td>
            </tr>
            <tr>
                <td>
                    Код типа продукта в TSM [0-1]
                </td>
                <td>
                    <html:text property="targetProductType" maxlength="250" size="50" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    Код продукта в TSM [0-1]
                </td>
                <td>
                    <html:text property="targetProduct" maxlength="250" size="50" value=""/>
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td>--%>
                    <%--Тип продукта заявки, самый верхний уровень – кредит ("Consumer Credit") [1]--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<html:text property="productType" maxlength="250" size="50" value="Кредит на покупку кошки"/>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td>
                    Код субпродукта в TSM [0-1]
                </td>
                <td>
                    <html:text property="targetProductSub" maxlength="250" size="50" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                   Валюта кредита [1]
                </td>
                <td>
                    <html:select property="currency">
                        <html:option value="RUB"/>
                        <html:option value="USD"/>
                        <html:option value="EUR"/>
                    </html:select>
                </td>
            </tr>
            <tr>
                <td>
                    Сумма кредита [1]
                </td>
                <td>
                    <html:text property="amount" maxlength="50" size="50" value="500000"/>
                </td>
            </tr>
            <tr>
                <td>
                    Срок кредитования.  Кол-во месяцев. Целое число в интервале 1-360. [1]
                </td>
                <td>
                    <html:text property="duration" maxlength="3" size="3" value="36"/>
                </td>
            </tr>
            <tr>
                <td>
                    Процентная ставка, %.  Число с точностью до двух знаков после запятой в интервале от 0 до 100. [1]
                </td>
                <td>
                    <html:text property="interestRate" maxlength="5" size="3" value="10.5"/>
                </td>
            </tr>
            <tr>
                <td>
                    Комментарии [0-1]
                </td>
                <td>
                    <html:text property="comments" maxlength="250" size="50" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    ТБ [1]
                </td>
                <td>
                    <html:text property="tb" maxlength="2" size="2" value="77"/>
                </td>
            </tr>
            <tr>
                <td>
                   ОСБ [1]
                </td>
                <td>
                    <html:text property="osb" maxlength="4" size="4" value="1573"/>
                </td>
            </tr>
            <tr>
                <td>
                    ВСП [1]
                </td>
                <td>
                    <html:text property="vsp" maxlength="5" size="5" value="15730"/>
                </td>
            </tr>
            <tr>
                <td>
                    Планируемое дата визита в ВСП (dd.mm.YYYY)[0-1]
                </td>
                <td>
                    <html:text  property="plannedVisitDate" maxlength="10" size="10" value="21.10.2014"/>
                </td>
            </tr>
            <tr>
                <td>
                    Планируемое время визита в ВСП [0-1]
                </td>
                <td>
                    <html:select property="plannedVisitTime">
                        <html:option value="1">с 09:00 до 11:00</html:option>
                        <html:option value="2">с 11:00 до 13:00</html:option>
                        <html:option value="3">с 13:00 до 15:00</html:option>
                        <html:option value="4">с 15:00 до 17:00</html:option>
                        <html:option value="5">с 17:00 до 19:00</html:option>
                        <html:option value="6">с 19:00 до 20:00</html:option>
                    </html:select>
                </td>
            </tr>
        </table>
        <html:submit property="operation" value="Send"/>
    </html:form>
</body>
</html:html>
