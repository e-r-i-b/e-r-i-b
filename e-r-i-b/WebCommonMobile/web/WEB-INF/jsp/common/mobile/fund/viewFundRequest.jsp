<%--
  User: usachev
  Date: 09.12.14
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:form action="/private/fund/request">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="contactsMap" value="${form.contactsMap}"/>
    <c:set var="profileAvatarMap" value="${form.profileAvatarMap}"/>
    <tiles:insert definition="iphone">
        <tiles:put name="defaultStatus" value="0"/>
        <tiles:put name="data">
            <c:set var="fundRequest" value="${form.fundRequest}"/>
            <request>
                <id>${fundRequest.id}</id>
                <state>${fundRequest.viewState.description}</state>
                <c:if test="${not empty fundRequest.requiredSum}">
                    <requiredSum>${fundRequest.requiredSum}</requiredSum>
                </c:if>
                <c:if test="${not empty fundRequest.reccomendSum}">
                    <reccomendSum>${fundRequest.reccomendSum}</reccomendSum>
                </c:if>
                <accumulatedSum>${form.accumulatedSum}</accumulatedSum>
                <resource>${phiz:getCutCardNumber(fundRequest.resource)}</resource>
                <createDate>${phiz:ñalendarToString(fundRequest.createdDate)}</createDate>
                <c:if test="${not empty fundRequest.viewClosedDate}">
                    <closeDate>${phiz:ñalendarToString(fundRequest.viewClosedDate)}</closeDate>
                </c:if>
                <c:if test="${not empty fundRequest.expectedClosedDate}">
                    <expectedCloseDate>${phiz:ñalendarToString(fundRequest.expectedClosedDate)}</expectedCloseDate>
                </c:if>
                <c:if test="${not empty fundRequest.viewClosedReason}">
                    <closedReason>${fundRequest.viewClosedReason.description}</closedReason>
                </c:if>
                <message>${fundRequest.message}</message>
                <senders>
                    <c:forEach items="${form.listFundInitiatorResponse}" var="sender">
                        <sender>
                            <c:set var="contact" value="${contactsMap[sender.id]}"/>
                            <phone>${sender.phone}</phone>
                            <c:choose>
                                <c:when test="${sender.defaultFIO != ''}">
                                    <fio>${sender.defaultFIO}</fio>
                                </c:when>
                                <c:when test="${not empty contact}">
                                    <fio>${contact.fullName}</fio>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty contact}">
                                    <%--Ýòî óñëîâèå ñïåöèàëüíî íå ïîìåøàåòñÿ â ðîäèòåëüñêèé òåã when--%>
                                    <c:if test="${not empty contact.avatarPath}">
                                        <avatarPath>${contact.avatarPath}</avatarPath>
                                    </c:if>
                                </c:when>
                                <c:when test="${not empty profileAvatarMap[sender.phone]}">
                                    <avatarPath>${profileAvatarMap[sender.phone]}</avatarPath>
                                </c:when>
                            </c:choose>
                            <state>${sender.state.description}</state>
                            <c:if test="${not empty sender.sum}">
                                <sum>${sender.sum}</sum>
                            </c:if>
                            <c:if test="${not empty sender.message}">
                                <message>${sender.message}</message>
                            </c:if>
                        </sender>
                    </c:forEach>
                </senders>
            </request>
        </tiles:put>
    </tiles:insert>
</html:form>
