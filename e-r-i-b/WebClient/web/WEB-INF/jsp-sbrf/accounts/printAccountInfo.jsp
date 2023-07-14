<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/private/accounts/printAcc">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>

    <c:set var="accountLink" value="${form.accountLink}" scope="request"/>
    <c:set var="office" value="${accountLink.office}"/>
    <c:set var="account" value="${accountLink.account}"/>

    <tiles:insert definition="printDoc">
        <tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>
        <tiles:put name="data" type="string">

            <table style="width:100%;">

                <tr>
                    <td class="labelAbstractPrint" >
                        ${office.name}
                    </td>
                </tr>
                <c:if test="${not empty office.code}">
                <tr>
                    <td class="labelAbstractPrint">
                             №<bean:write name="office" property="code.fields(branch)"/>/<bean:write name="office" property="code.fields(office)"/>
                    </td>
                </tr>
                </c:if>
                <tr>
                    <td style="font-size:11pt;text-align:center;padding-top:20mm">
                            Информация по вкладу ${accountLink.description} в «Сбербанк России ОАО»
                    </td>
                </tr>
                <tr>
                    <td class="needBorder" style="padding-top:5mm;">
                        <table cellpadding="0" cellspacing="0" style="width:100%;">
                            <tr>
                                <td class="labelAbstractPrint">Название типа вклада:</td>
                                <td><span class="bold">${accountLink.description}</span></td>
                            </tr>
                            <tr>
                                <td class="labelAbstractPrint">Сумма вклада:</td>
                                <td>
                                    <span class="bold">
                                        ${phiz:formatAmount(account.balance)}
                                    </span>
                                </td>
                            </tr>

                            <tr>
                                <td class="labelAbstractPrint">Срок вклада:</td>
                                <td>
                                    <c:if test="${not empty account.period}">
                                        <span class="bold">
                                            <c:out value="${phiz:getFormatedPeriod(account.period)}"/>
                                        </span>
                                    </c:if>
                                </td>
                            </tr>


                            <tr>
                                <td class="labelAbstractPrint">Процентная ставка:</td>
                                <td>
                                    <c:if test="${!empty account.interestRate}">
                                        <span class="bold"><bean:write name="account" property="interestRate" format="0.00"/>&nbsp;%</span>
                                    </c:if>
                                </td>
                            </tr>

                            <tr>
                                <td class="labelAbstractPrint">Номер счета вклада:</td>
                                <td><span class="bold">${phiz:getFormattedAccountNumber(accountLink.number)}</span></td>
                            </tr>

                            <tr>
                                <td class="labelAbstractPrint">Сумма неснижаемого остатка:</td>
                                <td>
                                    <c:if test="${not empty account.minimumBalance}">
                                        <span class="bold">
                                            ${phiz:formatAmount(account.minimumBalance)}
                                        </span>
                                    </c:if>
                                </td>
                            </tr>


                            <tr>
                                <td class="labelAbstractPrint">Максимальная сумма снятия:</td>
                                <td>
                                    <c:if test="${not empty account.maxSumWrite}">
                                        <span class="bold">
                                            ${phiz:formatAmount(account.maxSumWrite)}
                                        </span>
                                    </c:if>
                                </td>
                            </tr>


                            <tr>
                                <td class="labelAbstractPrint">Номер счета для перечисления процентов:</td>
                                <td>
                                    <c:if test="${not empty account.interestTransferAccount}">
                                        <span class="bold">
                                            <c:out value="${phiz:getFormattedAccountNumber(account.interestTransferAccount)}"/>
                                        </span>
                                    </c:if>
                                </td>
                            </tr>


                            <tr>
                                <td class="labelAbstractPrint">Номер карты для перечисления процентов:</td>
                                <td>
                                    <c:if test="${not empty account.interestTransferCard}">
                                        <span class="bold">
                                            <c:out value="${phiz:getCutCardNumber(account.interestTransferCard)}"/>
                                        </span>
                                    </c:if>
                                </td>
                            </tr>

                            <tr>
                                <td class="labelAbstractPrint">Текущее состояние:</td>
                                <td>
                                    <span class="bold">
                                        <c:choose>
                                            <c:when test="${account.accountState=='OPENED'}">
                                                Действующий
                                            </c:when>
                                            <c:otherwise>
                                                Закрытый
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </td>
                            </tr>

                            <tr>
                                <td class="labelAbstractPrint">Пролонгация:</td>
                                <td>
                                    <c:if test="${not empty account.prolongationAllowed}">
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${account.prolongationAllowed}">
                                                    осуществляется
                                                </c:when>
                                                <c:otherwise>
                                                    не осуществляется
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </c:if>
                                </td>
                            </tr>

                            <tr>
                                <td class="labelAbstractPrint">Дата открытия:</td>
                                <td>
                                    <span class="bold">
                                        <c:if test="${not empty account.openDate}">
                                            <bean:write name="account" property="openDate.time" format="dd.MM.yyyy"/>
                                        </c:if>
                                    </span>
                                </td>
                            </tr>

                            <tr>
                                <td class="labelAbstractPrint">Дата закрытия:</td>
                                <td>
                                    <c:if test="${not empty account.demand}">
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${not account.demand}">
                                                    <c:if test="${not empty account.closeDate}">
                                                        <bean:write name="account" property="closeDate.time" format="dd.MM.yyyy"/>
                                                    </c:if>
                                                </c:when>
                                                <c:otherwise>
                                                    бессрочный
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </c:if>
                                </td>
                            </tr>


                            <tr>
                                <td class="labelAbstractPrint">Сберкнижка:</td>
                                <td>
                                    <c:if test="${not empty account.passbook}">
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${account.passbook}">
                                                    Выдавалась
                                                </c:when>
                                                <c:otherwise>
                                                    Не выдавалась
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </c:if>
                                </td>
                            </tr>


                            <tr>
                                <td class="labelAbstractPrint">Списание:</td>
                                <td>
                                    <c:if test="${not empty account.debitAllowed}">
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${account.debitAllowed}">
                                                    разрешено
                                                </c:when>
                                                <c:otherwise>
                                                    запрещено
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </c:if>
                                </td>
                            </tr>


                            <tr>
                                <td class="labelAbstractPrint">Зачисление:</td>
                                <td>
                                    <c:if test="${not empty account.creditAllowed}">
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${account.creditAllowed}">
                                                    разрешено
                                                </c:when>
                                                <c:otherwise>
                                                    запрещено
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </c:if>
                                </td>
                            </tr>


                            <tr>
                                <td class="labelAbstractPrint">"Зеленая улица":</td>
                                <td>
                                    <c:if test="${not empty account.creditCrossAgencyAllowed and not empty account.debitCrossAgencyAllowed}">
                                        <span class="bold">
                                        <c:choose>
                                            <c:when test="${account.creditCrossAgencyAllowed and account.debitCrossAgencyAllowed}">
                                                разрешены расходные операции/разрешены приходные операции
                                            </c:when>
                                            <c:when test="${account.creditCrossAgencyAllowed}">
                                                разрешены расходные операции
                                            </c:when>
                                            <c:when test="${account.debitCrossAgencyAllowed}">
                                                разрешены приходные операции
                                            </c:when>
                                            <c:otherwise>
                                                операции запрещены
                                            </c:otherwise>
                                        </c:choose>
                                        </span>
                                    </c:if>
                                </td>
                            </tr>

                            <tr class="form-row">
                                <td class="labelAbstractPrint">Название:</td>
                                <td style="width: 400px;">
                                     <span class="bold">
                                         <c:out value="${form.accountLink.name}"/>
                                    </span>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="needBorder" style="padding-top:7mm;">
                        <table cellpadding="0" cellspacing="0" style="width:100%;">
                            <tr>
                                <td class="labelAbstractPrint">
                                    ФИО клиента:&nbsp;
                                    <c:set var="accountClient" value="${accountLink.accountClient}"/>
                                    <c:if test="${not empty accountClient}">
                                        <c:out value="${phiz:getFormattedPersonName(accountClient.firstName, accountClient.surName, accountClient.patrName)}"/>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelAbstractPrint">
                                    Дата распечатки документа:&nbsp;
                                    <c:set var="date" value="${phiz:currentDate()}"/>
                                    <bean:write name="date" property="time" format="dd.MM.yyyy"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelAbstractPrint">
                                    <bean:message bundle="commonBundle" key="text.SBOL.executor"/>
                                    <bean:message bundle="commonBundle" key="text.SBOL.formedIn"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>