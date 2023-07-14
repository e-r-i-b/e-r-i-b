<%--
  Created by IntelliJ IDEA.
  User: bogdanov
  Date: 07.02.2012
  Time: 16:56:28
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/private/autosubscriptions/info/printScheduleReport">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="link" value="${form.link}"/>
    <c:set var="payment" value="${link.value}"/>
    <c:set var="items" value="${form.scheduleItems}"/>
    <c:set var="isMock" value="${phiz:isInstance(payment, 'com.rssl.phizic.business.autoSubscription.MockAutoSubscription')}"/>

    <tiles:insert definition="printDoc">
        <tiles:put name="data" type="string">
            <c:if test="${link != null}">
                 <table style="width:70%">
                    <tr>
                        <td style="font-size:11pt;text-align:center;padding-top:20px; padding-bottom: 20px; font: bold 11pt Arial;">
                            <h3>������ ��������, ��������� �� ��������
                                 <c:if test="${form.showPaymentForPeriod}">
                                    �� ������ � ${form.fromDateString} �� ${form.toDateString}
                                </c:if>
                            </h3>
                        </td>
                    </tr>
                </table>
                <table style="width:100%">
                    <tr>
                        <td>
                            �������� ��������: <c:out value="${payment.friendlyName}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            ����������: <c:out value="${payment.receiverName}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            �������������: <bean:write name="link" property="executionEventType" ignore="true"/>
                        </td>
                    </tr>
                </table>
                <br>
                <table cellpadding="0" cellspacing="0" style="width:70%">
                    <tr class="tblInfHeaderAbstrPrint">
                        <td class="docTdTopBorder" style="text-align:center;">���� � �����</td>
                        <td class="docTdTopBorder" style="text-align:center;">����� �������</td>
                        <td class="docTdTopBorder" style="text-align:center;">����� ��������</td>
                        <td class="docTdTopBorder" style="text-align:center;">����� ����� ��������� �������</td>
                        <td class="docTdTopBorder" style="text-align:center;">������ �������</td>
                        <td class="docTdTopBorder" style="text-align:center;">������� ������<br>(��� ����������� ��������)</td>
                    </tr>
                    <c:forEach items="${items}" var="item">
                        <tr>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                ${phiz:formatDateToStringWithSlash2(item.date)}
                            </td>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                <bean:write name="item" property="summ" format="0.00"/>&nbsp;${phiz:getCurrencySign("RUB")}
                            </td>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                <c:if test="${not empty item.commission}">
                                    <bean:write name="item" property="commission" format="0.00"/>&nbsp;${phiz:getCurrencySign("RUB")}
                                </c:if>
                            </td>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                <c:set var="totalSumm" value="${item.summ + item.commission}"/>
                                <c:if test="${not(totalSumm == null)}">
                                    <bean:write name="totalSumm" format="0.00"/>&nbsp;${phiz:getCurrencySign("RUB")}
                                </c:if>
                            </td>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                <c:if test="${not empty item}">
                                    <bean:message key="payment.autoSub.payment.state.${item.status}" bundle="paymentsBundle"/>
                                </c:if>
                            </td>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                <c:if test="${not empty item.rejectionCause}">
                                    <bean:write name="item" property="rejectionCause"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>