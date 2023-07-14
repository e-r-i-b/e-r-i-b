<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="mobile" uri="http://rssl.com/tags/mobile" %>

<html:form action="/private/moneyboxes/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="link" value="${form.link}"/>
    <c:set var="cardLink" value="${form.cardLink}"/>
    <c:set var="accountLink" value="${form.accountLink}"/>
    <c:set var="isClaim" value="${phiz:isInstance(link.value, 'com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment')}"/>
    <c:if test="${!isClaim}">
        <c:set var="payment"  value="${link.autoSubscriptionInfo}"/>
    </c:if>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <receiver>
                <accountNumber><c:out value="${link.value.accountNumber}"/></accountNumber>
            </receiver>
            <c:if test="${not empty cardLink}">
                <paymentResource>${cardLink.code}</paymentResource>
            </c:if>
            <autoSubDetails>
                <c:choose>
                    <c:when test="${isClaim}">
                        <c:set var="sumType" value="${link.sumType}"/>
                        <status>New</status>
                        <startDate/>
                        <name><c:out value="${link.friendlyName}"/></name>
                        <typeDescription><c:out value="${link.sumType.description}"/></typeDescription>
                        <executionEventDescription><c:out value="${link.value.executionEventType.description}"/></executionEventDescription>
                        <executionEventType>${link.value.executionEventType}</executionEventType>
                    </c:when>
                    <c:otherwise>
                        <c:set var="sumType" value="${payment.sumType}"/>
                        <status><c:out value="${payment.autoPayStatusType}"/></status>
                        <startDate><bean:write name="payment" property="startDate.time" format="dd.MM.yyyy" filter="true"/></startDate>
                        <c:if test="${not empty payment.updateDate}">
                            <updateDate><bean:write name="payment" property="updateDate.time" format="dd.MM.yyyy" filter="true"/></updateDate>
                        </c:if>
                        <name><c:out value="${payment.friendlyName}"/></name>
                        <typeDescription><c:out value="${payment.sumType.description}"/></typeDescription>
                        <c:if test="${not empty payment.executionEventType.description}">
                            <executionEventDescription><c:out value="${payment.executionEventType.description}"/></executionEventDescription>
                        </c:if>
                        <executionEventType>${payment.executionEventType}</executionEventType>
                    </c:otherwise>
                </c:choose>

                <sumType>${sumType}</sumType>

                <c:choose>
                    <c:when test="${sumType=='FIXED_SUMMA'}">
                        <always>
                            <payDay>
                                <c:set var="payDay" value="${link.value.longOfferPayDay}"/>
                                <day><c:out value="${payDay.day}"/></day>
                                <monthInQuarter><c:out value="${payDay.monthInQuarter}"/></monthInQuarter>
                                <monthInYear><c:out value="${payDay.monthInYear}"/></monthInYear>
                                <weekDay><c:out value="${payDay.weekDay}"/></weekDay>
                            </payDay>
                            <c:choose>
                                <c:when test="${isClaim}">
                                    <tiles:insert definition="mobileMoneyType" flush="false">
                                        <tiles:put name="name" value="amount"/>
                                        <tiles:put name="money" beanName="link" beanProperty="value.amount"/>
                                    </tiles:insert>
                                </c:when>
                                <c:otherwise>
                                    <tiles:insert definition="mobileMoneyType" flush="false">
                                        <tiles:put name="name" value="amount"/>
                                        <tiles:put name="money" beanName="payment" beanProperty="amount"/>
                                    </tiles:insert>
                                </c:otherwise>
                            </c:choose>
                        </always>
                    </c:when>
                    <c:when test="${sumType=='PERCENT_BY_ANY_RECEIPT' || sumType=='PERCENT_BY_DEBIT'}">
                        <byPercent>
                            <percent>
                                <c:choose>
                                    <c:when test="${isClaim}">
                                        <c:out value="${link.value.percent}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${payment.percent}"/>
                                    </c:otherwise>
                                </c:choose>
                            </percent>
                            <c:set var="maxSumWrite">
                                <c:choose>
                                    <c:when test="${isClaim}">
                                        <c:out value="${link.value.amount}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${payment.maxSumWritePerMonth}"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:set>
                            <c:if test="${not empty maxSumWrite}">
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="maxSumWritePerMonth"/>
                                    <c:choose>
                                        <c:when test="${isClaim}">
                                            <tiles:put name="money" beanName="link" beanProperty="value.amount"/>
                                        </c:when>
                                        <c:otherwise>
                                            <tiles:put name="money" beanName="payment" beanProperty="maxSumWritePerMonth"/>
                                        </c:otherwise>
                                    </c:choose>
                                </tiles:insert>
                            </c:if>
                        </byPercent>
                    </c:when>
                </c:choose>
            </autoSubDetails>
        </tiles:put>
    </tiles:insert>
</html:form>