<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/payments">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm">

        <tiles:put name="data" type="string">
            <%-- Обход мапа "карта => линк-на-шаблон-платежа --%>
            <sl:collection id="cardEntry" property="cardPaymentTemplateLinks" model="xml-list" title="templateList">
                <sl:collectionItem title="card">                    
                    <c:set var="cardLink" value="${cardEntry.key}"/>
                    <c:set var="card" value="${cardLink.card}"/>
                    <c:set var="templatesList" value="${cardEntry.value}"/>
                    <id>${cardLink.id}</id>
                    <name>${phiz:getCardUserName(cardLink)}</name>
                    <c:if test="${not empty cardLink.number}">
                        <number>${phiz:getCutCardNumber(cardLink.number)}</number>
                    </c:if>
                    <tiles:insert definition="atmMoneyType" flush="false">
                        <tiles:put name="name" value="availableLimit" />
                        <tiles:put name="money" beanName="card" beanProperty="availableLimit"/>
                    </tiles:insert>
                    <state>${card.cardState}</state>
                    <c:if test="${not empty templatesList}">
                        <templates>
                            <%-- Обход шаблонов карты --%>
                            <c:forEach var="templateLink" items="${templatesList}">
                                <c:set var="provider" value="${templateLink.serviceProvider}"/>
                                <c:set var="template" value="${templateLink.value}"/>

                                <template>

                                    <id>${templateLink.id}</id>

                                        <c:choose>
                                            <c:when test="${not empty templateLink.error}">
                                                <state>error</state>
                                            </c:when>
                                            <c:when test="${cardLink.active and template.active}">
                                                <state>active</state>
                                            </c:when>
                                            <c:otherwise>
                                                <state>inactive</state>
                                            </c:otherwise>
                                        </c:choose>

                                    <c:if test="${not empty templateLink.error}">
                                        <error><bean:write name="templateLink" property="error"/></error>
                                    </c:if>

                                    <recipient><bean:write name="provider" property="name"/></recipient>

                                    <payer>
                                        <%-- имя поля может быть неизвестно --%>
                                        <c:if test="${not empty templateLink.payerFieldName}">
                                            <name><bean:write name="templateLink" property="payerFieldName"/></name>
                                        </c:if>
                                        <%-- значение поля известно всегда --%>
                                        <value><bean:write name="templateLink" property="payerFieldValue"/></value>
                                    </payer>
                                </template>

                            </c:forEach>
                        </templates>
                    </c:if>
                </sl:collectionItem>
           </sl:collection>
        </tiles:put>

    </tiles:insert>

</html:form>
