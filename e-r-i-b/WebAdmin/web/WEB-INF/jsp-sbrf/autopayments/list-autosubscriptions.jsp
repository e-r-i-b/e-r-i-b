<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:put name="data" type="string">
    <script type="text/javascript">
        function printList(event)
        {
            <c:set var="url" value="/autopayment/printlist.do"/>
            openWindow(event, "${phiz:calculateActionURL(pageContext, url)}");
        }
    </script>
    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="id" value="AutoSubscriptionsList"/>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data">

                <c:set var="autoSubscriptionDate"><bean:message key="label.date" bundle="autopaymentsBundle"/></c:set>
                <c:set var="startDate">
                    <fmt:formatDate value="${listElement.startDate.time}" pattern="dd.MM.yyyy HH:mm"/>
                </c:set>
                <sl:collectionItem title="${autoSubscriptionDate}" value="${startDate}"/>

                <c:set var="autoSubscriptionName"><bean:message key="label.autosubscription.name" bundle="autopaymentsBundle"/></c:set>
                <c:set var="asUrl">${paymentUrl}${listElement.number}</c:set>
                <sl:collectionItem title="${autoSubscriptionName}" property="friendlyName" href="${asUrl}"/>

                <c:set var="chargeOffResource"><bean:message key="label.chargeoffcard" bundle="autopaymentsBundle"/></c:set>
                <c:set var="cutCardNum" value="${phiz:getCutCardNumber(listElement.cardLink.number)}"/>
                <sl:collectionItem title="${chargeOffResource}" value="${cutCardNum}"/>

                <c:set var="receiverName"><bean:message key="label.receiverName" bundle="autopaymentsBundle"/></c:set>
                <sl:collectionItem title="${receiverName}" property="receiverName"/>

                <c:set var="summa"><bean:message key="label.summa" bundle="autopaymentsBundle"/></c:set>
                <c:set var="summaVal" value="${phiz:formatAmount(listElement.amount)}"/>
                <sl:collectionItem title="${summa}" value="${summaVal}"/>

                <c:set var="autoSubscriptionType"><bean:message key="label.autosubscription.type" bundle="autopaymentsBundle"/></c:set>
                <c:set var="type">
                    <%--
                        1)    регулярных (PERIODIC):
                            a.    ExeEventCode (ONCE_IN_WEEK/ONCE_IN_MONTH/ONCE_IN_QUARTER)
                            b.    SummaKindCode (FIXED_SUMMA_IN_RECIP_CURR)
                        2)    по выставленному счету (CHECK):
                            a.    ExeEventCode (ONCE_IN_WEEK/ONCE_IN_MONTH/ONCE_IN_QUARTER)
                            b.    SummaKindCode (BY_BILLING)
                        3)    пороговых (LIMIT):
                            a.    ExeEventCode (ON_REMAIND)
                            b.    SummaKindCode (FIXED_SUMMA_IN_RECIP_CURR)
                    --%>
                    <c:choose>
                        <c:when test="${listElement.executionEventType=='ON_REMAIND'}">
                            <bean:message key="type.LIMIT" bundle="autopaymentsBundle"/>
                        </c:when>
                        <c:when test="${listElement.sumType=='BY_BILLING'}">
                            <bean:message key="type.CHECK" bundle="autopaymentsBundle"/>
                        </c:when>
                        <c:when test="${listElement.sumType=='FIXED_SUMMA_IN_RECIP_CURR' && listElement.executionEventType!='ON_REMAIND'}">
                            <bean:message key="type.PERIODIC" bundle="autopaymentsBundle"/>
                        </c:when>
                    </c:choose>
                </c:set>

                <sl:collectionItem title="${autoSubscriptionType}" value="${type}"/>

                <c:set var="autoSubscriptionState"><bean:message key="label.state" bundle="autopaymentsBundle"/></c:set>
                <c:set var="stateDesc" value="${listElement.autoPayStatusType.description}"/>
                <sl:collectionItem title="${autoSubscriptionState}" value="${stateDesc}"/>

                <c:set var="autoPayNumberTitle"><bean:message key="label.autopay.number" bundle="autopaymentsBundle"/> </c:set>
                <sl:collectionItem title="${autoPayNumberTitle}" value="${listElement.value.autopayNumber}"/>
            </sl:collection>
        </tiles:put>
        <tiles:put name="emptyMessage" value="Не найдено ни одного автоплатежа для заданного клиента."/>
    </tiles:insert>

</tiles:put>