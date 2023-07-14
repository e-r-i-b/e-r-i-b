<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/jsp/types/status.jsp"%>

<html:form action="/private/cards/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="cardLink" value="${form.cardLink}" scope="request"/>
    <c:set var="card"    value="${cardLink.card}"/>
    <c:set var="cardDetail" value="${card}" scope="request"/>
    <c:set var="bundle" value="moneyboxBundle"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <detail>
            <c:if test="${not empty card and card.cardType=='credit'}">
                <creditType>
                    <c:if test="${not empty card.overdraftLimit}">
                    <tiles:insert definition="atmMoneyType" flush="false">
                        <tiles:put name="name" value="limit" />
                        <tiles:put name="money" beanName="card" beanProperty="overdraftLimit"/>
                    </tiles:insert>
                    </c:if>
                    <c:if test="${not empty card.overdraftOwnSum}">
                    <tiles:insert definition="atmMoneyType" flush="false">
                        <tiles:put name="name" value="ownSum" />
                        <tiles:put name="money" beanName="card" beanProperty="overdraftOwnSum"/>
                    </tiles:insert>
                    </c:if>
                    <c:if test="${not empty card.overdraftMinimalPayment}">
                    <tiles:insert definition="atmMoneyType" flush="false">
                        <tiles:put name="name" value="minPayment" />
                        <tiles:put name="money" beanName="card" beanProperty="overdraftMinimalPayment"/>
                    </tiles:insert>
                    </c:if>

                    <tiles:insert definition="atmDateTimeType" flush="false">
                        <tiles:put name="name" value="minPaymentDate"/>
                        <tiles:put name="calendar" beanName="card" beanProperty="overdraftMinimalPaymentDate"/>
                    </tiles:insert>
                </creditType>
            </c:if>
            <c:set var="cardClient" value="${cardLink.cardClient}"/>
            <holderName>${phiz:getFormattedPersonName(cardClient.firstName,cardClient.surName,cardClient.patrName)}</holderName>
            <c:if test="${not empty cardDetail}">
                <c:if test="${not empty cardDetail.availableCashLimit}">
                <tiles:insert definition="atmMoneyType" flush="false">
                    <tiles:put name="name" value="availableCashLimit"/>
                    <tiles:put name="money" beanName="cardDetail" beanProperty="availableCashLimit"/>
                </tiles:insert>
                </c:if>
                <c:if test="${not empty cardDetail.purchaseLimit}">
                <tiles:insert definition="atmMoneyType" flush="false">
                    <tiles:put name="name" value="purchaseLimit"/>
                    <tiles:put name="money" beanName="cardDetail" beanProperty="purchaseLimit"/>                     
                </tiles:insert>
                </c:if>
            </c:if>
            <officeName>${card.office.name}</officeName>
            <c:if test="${!empty cardLink.cardAccount}">
                <accountNumber>${cardLink.cardAccount.number}</accountNumber>
            </c:if>
            <c:if test="${not empty card.issueDate}">
                <issueDate><bean:write name="cardLink" property="card.issueDate.time" format="dd.MM.yyyy"/></issueDate>
            </c:if>
            <c:if test="${not empty card}">
                <displayedExpireDate>${phiz:formatExpirationCardDate(cardLink)}</displayedExpireDate>
            </c:if>
            <productValue>
                <cards>
                    <status>
                        <c:choose>
                            <c:when test="${empty cardLink}">
                                <code>${STATUS_PRODUCT_ERROR}</code>
                                <errors>
                                    <error>
                                        <text>Информация по карте из АБС временно недоступна. Повторите операцию позже.</text>
                                    </error>
                                </errors>
                            </c:when>
                            <c:otherwise>
                                <code>${STATUS_OK}</code>
                            </c:otherwise>
                        </c:choose>
                    </status>
                    <c:if test="${not empty cardLink}">
                        <card>
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
                            <c:if test="${not empty card}">
                                <type>${card.cardType}</type>
                                <c:if test="${not empty card.availableLimit}">
                                    <tiles:insert definition="atmMoneyType" flush="false">
                                        <tiles:put name="name" value="availableLimit" />
                                        <tiles:put name="money" beanName="card" beanProperty="availableLimit"/>
                                    </tiles:insert>
                                </c:if>
                                <state>${card.cardState}</state>
                                <c:if test="${not empty card.mainCardNumber}">
                                    <mainCardNumber>${phiz:getCutCardNumber(card.mainCardNumber)}</mainCardNumber>
                                </c:if>
                                <c:if test="${not empty card.additionalCardType}">
                                    <additionalCardType>${fn:toUpperCase(card.additionalCardType.value)}</additionalCardType>
                                </c:if>
                            </c:if>

                            <c:if test="${not empty form.moneyBoxes}">
                                 <jsp:include page="/WEB-INF/jsp/products/moneyBoxDetailInfo.jsp"/>
                            </c:if>
                        </card>
                    </c:if>
                </cards>
            </productValue>
            </detail>
        </tiles:put>
    </tiles:insert>
</html:form>
