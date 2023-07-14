<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<html:form action="/private/ima/printMoreInfo">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="user" value="${form.user}"/>
    <c:set var="imAccountLink" value="${form.imAccountLink}"/>
    <c:set var="imAccount" value="${imAccountLink.imAccount}"/>

    <tiles:insert definition="printDoc" flush="false">
        <tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>

        <tiles:put name="data" type="string">
            <table style="width:100%;">
                <tr>
                    <td style="font-size:11pt;text-align:center;padding-top:20px">
                        ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ ПО СЧЁТУ № ${phiz:getFormattedAccountNumber(imAccountLink.number)}
                    </td>
                </tr>
                <tr>
                    <td class="needBorder" style="padding-top:7px; padding-bottom:7px;">
                        <table cellpadding="0" cellspacing="0" style="width:100%;">
                            <tr>
                                <td class="labelAbstractPrint">
                                    ФИО клиента:&nbsp;
                                    <c:set var="owner" value="${imAccountLink.imAccountClient}"/>
                                    <c:choose>
                                        <c:when test="${not empty owner and not empty owner.patrName}">
                                            <c:out value="${phiz:getFormattedPersonName(owner.firstName, owner.surName, owner.patrName)}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${not empty user}">
                                                <c:out value="${phiz:getFormattedPersonName(user.firstName, user.surName, user.patrName)}"/>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelAbstractPrint">
                                    Дата распечатки документа:&nbsp;
                                    <c:set var="date" value="${phiz:currentDate()}"/>
                                    <bean:write name="date" property="time" format="dd.MM.yyyy"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="borderedTable" style="padding:5px;">
                        <table style="width:100%; text-indent: 5px; text-align:left; font-size:12px">
                            <tr>
                                <td><span class="bold">Название:</span></td>
                                <td>
                                    <c:if test="${not empty imAccountLink.name}">
                                        <bean:write name="imAccountLink" property="name"/>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Тип счёта:</span></td>
                                <td>
                                    <c:if test="${not empty imAccountLink.currency}">
                                        ${imAccountLink.currency.name}&nbsp;(${phiz:normalizeCurrencyCode(imAccountLink.currency.code)})
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Номер договора:</span></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty imAccount.agreementNumber && imAccount.agreementNumber != ''}">
                                            ${imAccount.agreementNumber}
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${not empty imAccount.number}">
                                                ${imAccount.number}
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Дата открытия:</span></td>
                                <td>
                                    <c:if test="${not empty imAccount.openDate}">
                                        ${phiz:formatDateWithStringMonth(imAccount.openDate)}
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Текущий остаток:</span></td>
                                <td>
                                    <c:if test="${not empty imAccount.balance}">
                                        ${phiz:formatAmount(imAccount.balance)}
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Текущее состояние:</span></td>
                                <td>
                                    <c:if test="${not empty imAccount.state}">
                                        ${imAccount.state == 'opened' or imAccount.state == 'lost_passbook' ? 'открыт' : 'закрыт'}
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Дата закрытия:</span></td>
                                <td>
                                    <c:if test="${imAccount.state == 'closed' and not empty imAccount.closingDate}">
                                        ${phiz:formatDateWithStringMonth(imAccount.closingDate)}
                                    </c:if>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>