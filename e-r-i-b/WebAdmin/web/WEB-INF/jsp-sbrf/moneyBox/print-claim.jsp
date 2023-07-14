<%--
  User: usachev
  Date: 14.11.14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>


<html:form action="private/payments/print">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="person" value="${form.activePerson}" />
    <c:set var="document" value="${form.document}" />
    <c:set var="attrubutes" value="${document.attributes}"/>
    <c:set var="typeMoneyBoxSum" value="${document.sumType}"/>
    <c:set var="amount" value="${document.chargeOffAmount!=null? phiz:formatAmount(document.chargeOffAmount):'не указана'}"/>
    <c:set var="persent" value="${attrubutes['long-offer-percent'].value}"/>
    <c:set var="status" value="${document.formName}"/>
    <c:set var="period" value="${attrubutes['long-offer-event-type'].value}"/>
    <c:set var="code" value="${form.department.code}"/>

    <c:choose>
        <c:when test="${status eq 'CreateMoneyBoxPayment'}">
            <c:set var="textOfAction" value="предоставление"/>
            <c:set var="textOfAction2" value="подключить"/>
        </c:when>
        <c:when test="${status eq 'CloseMoneyBoxPayment'}">
            <c:set var="textOfAction" value="прекращение предоставления"/>
            <c:set var="textOfAction2" value="отключить"/>
        </c:when>
        <c:when test="${status eq 'RefuseMoneyBoxPayment'}">
            <c:set var="textOfAction" value="приостановление"/>
            <c:set var="textOfAction2" value="приостановить"/>
        </c:when>
        <c:when test="${status eq 'RecoverMoneyBoxPayment'}">
            <c:set var="textOfAction" value="возобновление"/>
            <c:set var="textOfAction2" value="возобновить"/>
        </c:when>
        <c:otherwise>
            <c:set var="textOfAction" value="изменение параметров"/>
            <c:set var="textOfAction2" value="изменить параметры"/>
        </c:otherwise>
    </c:choose>

    <tiles:insert definition="personsPrint">
        <tiles:put name="pageTitle" value="pageTitle"/>
        <tiles:put name="data">
            <style>
                .header {
                    font-size: 14pt;
                    font-weight: bold;
                    text-align: center;
                }

                .usualText {
                    font-size: 12pt;
                }

                .list {
                    padding: 0 0 0 20px;
                    list-style: disc;
                }

                .workArea {
                    padding: 10px;
                }

                .employeeInformation td {
                    border: 1px solid;
                    font-size: 12pt;
                }
            </style>

            <div class="workArea">
                <p class="header">Заявление на ${textOfAction} услуги «Копилка»</p>
                <p class="usualText">Я, ${person.surName} ${person.firstName} ${person.patrName}, прошу Банк ${textOfAction2} услугу «Копилка»
                    со счета банковской карты № ${phiz:getCutCardNumber(document.chargeOffAccount)} на счет № ${document.receiverAccount}, в соответствии со следующими параметрами:</p>

                <c:if test="${status eq 'EditMoneyBoxPayment'}">
                    <p class="usualText"> Прошу изменить значения параметров услуги «Копилка» на следующие:</p>
                </c:if>
                <ul class="usualText list">
                    <li>Наименование: ${attrubutes['money-box-name'].value}</li>
                    <li>Дата первого перевода: ${phiz:dateToString(attrubutes['long-offer-start-date'].value)}</li>
                    <c:choose>
                        <c:when test="${typeMoneyBoxSum eq 'FIXED_SUMMA'}">
                            <c:choose>
                                <c:when test="${period eq 'ONCE'}">
                                    <li>Периодичность зачисления: Один раз</li>
                                </c:when>
                                <c:when test="${period eq 'ONCE_IN_WEEK'}">
                                    <li>Периодичность зачисления: Раз в неделю</li>
                                </c:when>
                                <c:when test="${period eq 'ONCE_IN_MONTH'}">
                                    <li>Периодичность зачисления: Раз в месяц</li>
                                </c:when>
                                <c:when test="${period eq 'ONCE_IN_QUARTER'}">
                                    <li>Периодичность зачисления: Раз в квартал</li>
                                </c:when>
                                <c:when test="${period eq 'ONCE_IN_YEAR'}">
                                    <li>Периодичность зачисления: Раз в год</li>
                                </c:when>
                            </c:choose>
                            <li>Сумма перевода: ${amount}</li>
                        </c:when>
                        <c:when test="${typeMoneyBoxSum eq 'PERCENT_BY_ANY_RECEIPT'}">
                            <li>% от зачисления: ${persent}%</li>
                            <li>Максимальная сумма одного перевода: ${amount}</li>
                        </c:when>
                        <c:when test="${typeMoneyBoxSum eq 'PERCENT_BY_DEBIT'}">
                            <li>% от списания: ${persent}%</li>
                            <li>Максимальная сумма одного перевода: ${amount}</li>
                        </c:when>
                    </c:choose>
                </ul>

                <c:if test="${not(status eq 'CloseMoneyBoxPayment')}">
                    <p class="usualText"><b>Я проинформирован и согласен с «Условиями предоставления услуги «Копилка».</b></p>
                </c:if>
                <p class="usualText"><b>Дата, подпись.</b></p>
                <c:if test="${status  eq 'CloseMoneyBoxPayment'}">
                    <p class="usualText"><b>Отметки для служебного пользования:</b></p>
                    <table class="employeeInformation">
                        <tr>
                            <td colspan="2">
                                <c:choose>
                                    <c:when test="${code != null}">
                                        Номер ${code.region},${code.branch}<c:if test="${code.office != null}">/${code.office}</c:if></td>
                                    </c:when>
                                    <c:otherwise>Сбербанк ОнЛ@йн</c:otherwise>
                                </c:choose>
                        </tr>
                        <tr>
                            <td colspan="2">Номер терминала:</td>
                        </tr>
                        <tr>
                            <td colspan="2">Порядковый номер операции: ${document.documentNumber}</td>
                        </tr>
                        <c:if test="${code != null && code.office != null}">
                            <tr>
                                <td colspan="2">Заявление принято, идентификация клиента проведена, подпись верна</td>
                            </tr>
                            <tr>
                                <td colspan="2">Наименование подразделения ОАО "Сбербанк России": ${form.department.name} </td>
                            </tr>
                            <tr>
                                <td colspan="2">Сотрудник, принявший заявление: ${form.fields.employeeFullName}</td>
                            </tr>
                            <tr style="height: 60px; vertical-align: top;">
                                <td>Дата: ${phiz:formatDateWithStringMonth(phiz:currentDate())}</td>
                                <td style="width: 300px">Подпись:</td>
                            </tr>
                        </c:if>
                    </table>
                </c:if>

            </div>
        </tiles:put>
    </tiles:insert>
</html:form>