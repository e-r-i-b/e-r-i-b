<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="mobile" uri="http://rssl.com/tags/mobile" %>

<html:form action="/private/autotransfers/info">
    <c:set var="form"               value="${phiz:currentForm(pageContext)}"/>
    <c:set var="payment"            value="${form.autoSubscriptionInfo}"/>
    <c:set var="fromResourceLink"   value="${form.link.cardLink}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <receiver>
                <c:if test="${payment.type.simpleName == 'ExternalCardsTransferToOurBankLongOffer' || payment.type.simpleName == 'ExternalCardsTransferToOtherBankLongOffer'}">
                    <name><c:out value="${payment.receiverName}"/></name>
                    <cardNumber>${payment.receiverCard}</cardNumber>
                </c:if>
                <c:if test="${payment.type.simpleName == 'InternalCardsTransferLongOffer'}">
                    <cardNumber>${payment.receiverCard}</cardNumber>
                    <c:set var="toResourceLink" value="${phiz:getCardLink(payment.receiverCard)}"/>
                    <c:if test="${not empty toResourceLink}">
                        <c:set var="toResource" value="${toResourceLink.card}"/>
                        <paymentProduct>
                            <cards>
                                <card>
                                    <id>${toResourceLink.id}</id>
                                    <code>${toResourceLink.code}</code>
                                    <name><c:out value="${phiz:getCardUserName(toResourceLink)}"/></name>
                                    <c:if test="${not empty toResourceLink.description}">
                                        <description>${toResourceLink.description}</description>
                                    </c:if>
                                    <c:if test="${not empty toResourceLink.number}">
                                        <number>${phiz:getCutCardNumber(toResourceLink.number)}</number>
                                    </c:if>
                                    <isMain>${toResourceLink.main}</isMain>
                                    <type>${toResource.cardType}</type>
                                    <c:if test="${not empty toResource.availableLimit}">
                                        <tiles:insert definition="atmMoneyType" flush="false">
                                            <tiles:put name="name" value="availableLimit" />
                                            <tiles:put name="money" beanName="toResource" beanProperty="availableLimit"/>
                                        </tiles:insert>
                                    </c:if>
                                    <state>${toResource.cardState}</state>
                                </card>
                            </cards>
                        </paymentProduct>
                    </c:if>
                </c:if>
            </receiver>
            <c:if test="${not empty fromResourceLink}">
                <paymentResource>${fromResourceLink.code}</paymentResource>
                <paymentProduct>
                    <cards>
                        <card>
                            <c:set var="fromResource" value="${fromResourceLink.card}"/>
                            <id>${fromResourceLink.id}</id>
                            <code>${fromResourceLink.code}</code>
                            <name><c:out value="${phiz:getCardUserName(fromResourceLink)}"/></name>
                            <c:if test="${not empty fromResourceLink.description}">
                                <description>${fromResourceLink.description}</description>
                            </c:if>
                            <c:if test="${not empty fromResourceLink.number}">
                                <number>${phiz:getCutCardNumber(fromResourceLink.number)}</number>
                            </c:if>
                            <isMain>${fromResourceLink.main}</isMain>
                            <type>${fromResource.cardType}</type>
                            <c:if test="${not empty fromResource.availableLimit}">
                                <tiles:insert definition="atmMoneyType" flush="false">
                                    <tiles:put name="name" value="availableLimit" />
                                    <tiles:put name="money" beanName="fromResource" beanProperty="availableLimit"/>
                                </tiles:insert>
                            </c:if>
                            <state>${fromResource.cardState}</state>
                        </card>
                    </cards>
                </paymentProduct>
            </c:if>
            <paymentDetails>
                <c:if test="${not empty payment.executionDate}">
                    <executionDate>${phiz:formatDateDependsOnSysDate(payment.executionDate, true, false)}</executionDate>
                </c:if>
                <c:if test="${not empty payment.amount}">
                    <tiles:insert definition="atmMoneyType" flush="false">
                        <tiles:put name="name" value="amount"/>
                        <tiles:put name="money" beanName="payment" beanProperty="amount"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty payment.commission}">
                    <commission>${phiz:formatAmount(payment.commission)}</commission>
                </c:if>
                <c:if test="${not empty payment.executionDate}">
                    <state>Исполнен</state>
                </c:if>
            </paymentDetails>
            <c:if test="${empty payment.executionDate}">
                <autoSubDetails>
                    <c:if test="${not empty payment.autoPayStatusType}">
                        <status><c:out value="${payment.autoPayStatusType}"/></status>
                    </c:if>
                    <startDate><bean:write name="payment" property="startDate.time" format="dd.MM.yyyy" filter="true"/></startDate>
                    <c:if test="${not empty payment.updateDate}">
                        <updateDate><bean:write name="payment" property="updateDate.time" format="dd.MM.yyyy" filter="true"/></updateDate>
                    </c:if>
                    <name><c:out value="${payment.friendlyName}"/></name>
                    <typeDescription><bean:message key='label.field.autopay.always' bundle='providerBundle'/></typeDescription>
                    <c:if test="${not empty payment.executionEventType.description}">
                        <executionEventDescription><c:out value="${payment.executionEventType.description}"/></executionEventDescription>
                    </c:if>
                    <c:if test="${not empty payment.executionEventType}">
                        <executionEventType>${payment.executionEventType}</executionEventType>
                    </c:if>
                    <c:if test="${not empty payment.sumType}">
                        <sumType>${payment.sumType}</sumType>
                    </c:if>
                    <c:choose>
                        <c:when test="${payment.sumType=='RUR_SUMMA'}">
                            <always>
                                <c:if test="${ not empty payment.nextPayDate }">
                                    <tiles:insert definition="atmDateType" flush="false">
                                        <tiles:put name="name" value="nextPayDate"/>
                                        <tiles:put name="calendar" beanName="payment" beanProperty="nextPayDate"/>
                                    </tiles:insert>
                                </c:if>
                                <tiles:insert definition="atmMoneyType" flush="false">
                                    <tiles:put name="name" value="amount"/>
                                    <tiles:put name="money" beanName="payment" beanProperty="amount"/>
                                </tiles:insert>
                            </always>
                        </c:when>
                    </c:choose>
                </autoSubDetails>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>