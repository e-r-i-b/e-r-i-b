<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/jsp/common/socialApi/types/status.jsp"%>

<fmt:setLocale value="en-US"/>

<html:form action="/private/products/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <c:if test="${form.allCardDown || form.allAccountDown || form.allLoanDown || form.allIMAccountDown}">
            <tiles:put name="status">${STATUS_PRODUCT_ERROR}</tiles:put>
            <tiles:put name="errorDescription">АБС временно недоступна. Часть информации не получена.</tiles:put>
        </c:if>
        <tiles:put name="data">
            <%-- Карты --%>
            <c:if test="${not empty form.cards || form.allCardDown}">
                <cards>
                    <status>
                        <c:choose>
                            <c:when test="${form.allCardDown}">
                                <code>${STATUS_PRODUCT_ERROR}</code>
                                <errors>
                                    <error>
                                        <text>Информация по картам из АБС временно недоступна. Повторите операцию позже.</text>
                                    </error>
                                </errors>
                            </c:when>
                            <c:otherwise>
                                <code>${STATUS_OK}</code>
                            </c:otherwise>
                        </c:choose>
                    </status>
                    <logic:iterate id="listElement" name="form" property="cards" indexId="i">
                        <card>
                            <c:set var="cardLink" value="${listElement}"/>
                            <c:set var="card" value="${cardLink.card}"/>

                            <id>${cardLink.id}</id>
                            <name><c:out value="${phiz:getCardUserName(cardLink)}"/></name>
                            <c:choose>
                                <c:when test="${not empty cardLink.ermbSmsAlias}">
                                    <smsName>${cardLink.ermbSmsAlias}</smsName>
                                </c:when>
                                <c:when test="${not empty cardLink.autoSmsAlias}">
                                    <smsName>${cardLink.autoSmsAlias}</smsName>
                                </c:when>
                            </c:choose>
                            <c:if test="${not empty cardLink.description}">
                                <description>${cardLink.description}</description>
                            </c:if>
                            <c:if test="${not empty cardLink.number}">
                                <number>${phiz:getCutCardNumber(cardLink.number)}</number>
                            </c:if>
                            <isMain>${cardLink.main}</isMain>
                            <type>${card.cardType}</type>
                            <c:if test="${not empty card.availableLimit}">
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="availableLimit" />
                                    <tiles:put name="money" beanName="card" beanProperty="availableLimit"/>
                                </tiles:insert>
                            </c:if>
                            <state>${card.cardState}</state>
                            <c:if test="${not empty cardLink.additionalCardType}">
                                <additionalCardType>${cardLink.additionalCardType.value}</additionalCardType>
                            </c:if>
                            <c:if test="${not empty form.mainCardIds[cardLink.id]}">
                                <mainCardId>${form.mainCardIds[cardLink.id]}</mainCardId>
                            </c:if>
                            <c:if test="${not empty cardLink.cardPrimaryAccount}">
                                <cardAccount>${cardLink.cardPrimaryAccount}</cardAccount>
                            </c:if>
                        </card>
                    </logic:iterate>
                </cards>
            </c:if>

        </tiles:put>
    </tiles:insert>
</html:form>
