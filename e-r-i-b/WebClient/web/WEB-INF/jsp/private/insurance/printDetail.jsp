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
<html:form action="/private/insurance/print">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="user" value="${form.user}"/>
    <c:set var="insuranceLink" value="${form.link}"/>
    <c:set var="insuranceApp" value="${insuranceLink.insuranceApp}"/>

    <tiles:insert definition="printDoc" flush="false">
        <tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>

        <tiles:put name="data" type="string">
            <table style="width:100%;">
                <tr>
                    <td style="font-size:11pt;text-align:center;padding-top:20px">
                        ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ ПО <c:out value="${insuranceApp.program}"/> <c:out value="${insuranceApp.company}"/> 
                    </td>
                </tr>
                <tr>
                    <td class="needBorder" style="padding-top:7px; padding-bottom:7px;">
                        <table cellpadding="0" cellspacing="0" style="width:100%;">
                            <tr>
                                <td class="labelAbstractPrint">
                                    ФИО клиента:&nbsp; <c:out value="${phiz:getFormattedPersonName(user.firstName, user.surName, user.patrName)}"/>
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
                                <td><span class="bold">Страховая программа:</span></td>
                                <td>
                                   <c:out value="${insuranceApp.program}"/>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Страховая компания:</span></td>
                                <td>
                                    <c:out value="${insuranceApp.company}"/>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Страховая сумма:</span></td>
                                <td>
                                    <c:if test="${not empty insuranceApp.amount}">
                                        ${phiz:formatAmount(insuranceApp.amount)}
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Дата начала действия страховки:</span></td>
                                <td>
                                    ${phiz:сalendarToString(insuranceApp.startDate)}
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Срок окончания страховки:</span></td>
                                <td>
                                    ${phiz:сalendarToString(insuranceApp.endDate)}
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Реквизиты договора:</span></td>
                                <td>
                                    №${insuranceApp.policyDetails.series} ${insuranceApp.policyDetails.num} от ${phiz:formatDateWithStringMonth(insuranceApp.policyDetails.issureDt)}.
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Страховые риски:</span></td>
                                <td>
                                    <c:out value="${insuranceApp.risk}"/>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Дополнительная информация:</span></td>
                                <td>
                                    <c:out value="${insuranceApp.additionalInfo}"/>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">Статус:</span></td>
                                <td>
                                    <c:out value="${insuranceApp.status}"/>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="bold">№ СНИЛС:</span></td>
                                <td>
                                    <c:out value="${insuranceApp.SNILS}"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>