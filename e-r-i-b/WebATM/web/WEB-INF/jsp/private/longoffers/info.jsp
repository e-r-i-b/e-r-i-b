<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/private/longoffers/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="link" value="${form.longOfferLink}"/>
    <c:set var="longOffer" value="${link.value}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty longOffer.type}">
                <operationType><c:out value="${longOffer.type.simpleName}"/></operationType>
            </c:if>
            <documentNumber>${link.number}</documentNumber>
            <c:if test="${not empty link.receiverAccount || not empty link.receiverCard}">
                <receiver>
                    <c:if test="${not empty link.receiverName}">
                        <name><c:out value="${link.receiverName}"/></name>
                    </c:if>
                    <c:choose>
                        <c:when test="${not empty link.receiverCard}"><card>${phiz:getCutCardNumber(link.receiverCard)}</card></c:when>
                        <c:otherwise><account>${link.receiverAccount}</account></c:otherwise>
                    </c:choose>
                </receiver>
            </c:if>

            <payment>
                <c:if test="${not empty form.payerResourceLink}">
                    <resource>${form.payerResourceLink.code}</resource>
                    <product>
                        <c:choose>
                            <c:when test="${phiz:startsWith(form.payerResourceLink.code, 'card')}">
                                <cards>
                                <card>
                                    <c:set var="cardLink" value="${form.payerResourceLink}"/>
                                    <c:set var="card" value="${cardLink.card}"/>

                                    <id>${cardLink.id}</id>
                                    <code>${cardLink.code}</code>
                                    <name><c:out value="${phiz:getCardUserName(cardLink)}"/></name>
                                    <c:if test="${not empty cardLink.description}">
                                        <description>${cardLink.description}</description>
                                    </c:if>
                                    <c:if test="${not empty cardLink.number}">
                                        <number>${phiz:getCutCardNumber(cardLink.number)}</number>
                                    </c:if>
                                    <isMain>${cardLink.main}</isMain>
                                    <type>${card.cardType}</type>
                                    <c:if test="${not empty card.availableLimit}">
                                        <tiles:insert definition="atmMoneyType" flush="false">
                                            <tiles:put name="name" value="availableLimit" />
                                            <tiles:put name="money" beanName="card" beanProperty="availableLimit"/>
                                        </tiles:insert>
                                    </c:if>
                                    <state>${card.cardState}</state>
                                </card>
                                </cards>
                            </c:when>
                            <c:when test="${phiz:startsWith(form.payerResourceLink.code, 'account')}">
                                <c:set var="accountLink" value="${form.payerResourceLink}"/>
                                <c:set var="account" value="${accountLink.account}"/>
                                <accounts>
                                <account>
                                    <id>${accountLink.id}</id>
                                    <code>${accountLink.code}</code>
                                    <name><bean:write name="accountLink" property="name"/></name>
                                    <c:if test="${not empty accountLink.number}">
                                        <number>${accountLink.number}</number>
                                    </c:if>
                                    <c:if test="${not empty account.balance}">
                                    <tiles:insert definition="atmMoneyType" flush="false">
                                        <tiles:put name="name" value="balance" />
                                        <tiles:put name="money" beanName="account" beanProperty="balance"/>
                                    </tiles:insert>
                                    </c:if>
                                    <state>${accountLink.account.accountState}</state>
                                </account>
                                </accounts>
                            </c:when>
                        </c:choose>
                    </product>
                </c:if>
                <c:if test="${not empty longOffer.percent || not empty longOffer.amount}">
                    <c:choose>
                         <c:when test="${not empty longOffer.percent}">
                             <percent>${longOffer.percent}</percent>
                         </c:when>
                         <c:otherwise>
                             <tiles:insert definition="atmMoneyType" flush="false">
                                <tiles:put name="name" value="amount"/>
                                <tiles:put name="money" beanName="longOffer" beanProperty="amount"/>
                            </tiles:insert>
                         </c:otherwise>
                     </c:choose>
                </c:if>
            </payment>
            <longOfferParameters>
                <c:if test="${not empty longOffer.startDate}">
                    <tiles:insert definition="atmDateType" flush="false">
                        <tiles:put name="name" value="startDate"/>
                        <tiles:put name="calendar" beanName="longOffer" beanProperty="startDate"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty longOffer.endDate}">
                    <tiles:insert definition="atmDateType" flush="false">
                        <tiles:put name="name" value="endDate"/>
                        <tiles:put name="calendar" beanName="longOffer" beanProperty="endDate"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty link.executionEventType}">
                    <executionEventDescription>${link.executionEventType}</executionEventDescription>
                </c:if>
                <c:if test="${not empty longOffer.executionEventType}">
                    <executionEventType>${longOffer.executionEventType}</executionEventType>
                </c:if>
                <c:if test="${not empty longOffer.priority}">
                    <priority>${longOffer.priority}</priority>
                </c:if>
                <c:if test="${not empty longOffer.office}">
                <office><c:out value="${longOffer.office.name}"/></office>
                </c:if>
                <name><c:out value="${link.name}"/></name>
            </longOfferParameters>
        </tiles:put>
    </tiles:insert>
</html:form>