<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="mobile" uri="http://rssl.com/tags/mobile" %>

<html:form action="/private/autosubscriptions/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="link" value="${form.link}"/>
    <c:set var="payment"  value="${form.autoSubscriptionInfo}"/>
    <c:set var="cardLink" value="${link.cardLink}"/>
    <c:set var="bank" value="${payment.receiverBank}"/>
    <c:set var="isAutoTransfer" value="${phiz:isAutoTransfer(link)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <receiver>
                <c:if test="${not empty payment.receiverName}">
                    <name><c:out value="${payment.receiverName}"/></name>
                </c:if>
                <c:if test="${not empty payment.service.name}">
                    <service><c:out value="${payment.service.name}"/></service>
                </c:if>
                <c:if test="${not empty payment.receiverINN}">
                    <inn>${payment.receiverINN}</inn>
                </c:if>
                <c:if test="${not empty payment.receiverAccount}">
                    <account>${payment.receiverAccount}</account>
                </c:if>
                <c:if test="${not empty payment.receiverKPP}">
                    <kpp>${payment.receiverKPP}</kpp>
                </c:if>
                <c:if test="${not empty bank}">
                    <bank>
                        <name><c:out value="${bank.name}"/></name>
                        <bic>${bank.BIC}</bic>
                        <account>${bank.account}</account>
                    </bank>
                </c:if>
                <c:if test="${isAutoTransfer}">
                    <c:set var="receiverCardLink" value="${phiz:getCardLink(payment.receiverCard)}"/>
                    <c:choose>
                        <c:when test="${not empty receiverCardLink}">
                            <paymentProduct>
                                <cards>
                                    <card>
                                        <id>${receiverCardLink.id}</id>
                                        <code>${receiverCardLink.code}</code>
                                        <name>${receiverCardLink.name}</name>
                                        <state>${receiverCardLink.card.cardState}</state>
                                    </card>
                                </cards>
                            </paymentProduct>
                        </c:when>
                        <c:otherwise>
                            <cardNumber>${payment.receiverCard}</cardNumber>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </receiver>
            <c:if test="${not empty cardLink}">
                <paymentResource>${cardLink.code}</paymentResource>
            </c:if>
            <c:if test="${not empty payment.extendedFields or not empty payment.executionDate}">
                <paymentDetails>
                    <c:if test="${not empty payment.executionDate}">
                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name" value="executionDate"/>
                            <tiles:put name="calendar" beanName="payment" beanProperty="executionDate"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty payment.amount}">
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="amount"/>
                            <tiles:put name="money" beanName="payment" beanProperty="amount"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty payment.commission}">
                        <commission>${phiz:formatAmount(payment.commission)}</commission>
                    </c:if>
                    <c:if test="${not empty payment.extendedFields}">
                        <fields>
                            <c:forEach var="field" items="${payment.extendedFields}">
                                <field>
                                    <c:set var="fieldValue" value="${empty field.value ? field.defaultValue : field.value}"/>
                                    <mobile:fieldBody
                                            name="field(${field.externalId})"
                                            title="${field.name}"
                                            description="${field.description}"
                                            hint="${field.hint}"
                                            type="${field.type}"
                                            maxLength="${field.maxLength}"
                                            minLength="${field.minLength}"
                                            required="${field.required}"
                                            editable="${field.editable}"
                                            visible="${field.visible}"
                                            isSum="${field.mainSum}"
                                            value="${phiz:formatDateFroSubscription(fieldValue)}">
                                        <c:if test="${not empty field.values}">
                                            <c:forEach var="valueItem" items="${field.values}">
                                                <mobile:fieldListItem value="${valueItem.id}" title="${valueItem.value}"/>
                                            </c:forEach>
                                        </c:if>
                                    </mobile:fieldBody>
                                </field>
                            </c:forEach>
                        </fields>
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
                        <tiles:insert definition="mobileDateType" flush="false">
                            <tiles:put name="name" value="startDate"/>
                            <tiles:put name="calendar" beanName="payment" beanProperty="startDate"/>
                        </tiles:insert>
                        <c:if test="${not empty payment.updateDate}">
                            <tiles:insert definition="mobileDateType" flush="false">
                                <tiles:put name="name" value="updateDate"/>
                                <tiles:put name="calendar" beanName="payment" beanProperty="updateDate"/>
                            </tiles:insert>
                        </c:if>
                        <name><c:out value="${payment.friendlyName}"/></name>
                        <c:choose>
                            <c:when test="${payment.executionEventType == 'ON_REMAIND'}">
                                <typeDescription><bean:message key='label.field.autopay.border' bundle='providerBundle'/></typeDescription>
                            </c:when>
                            <c:when test="${payment.sumType =='BY_BILLING'}">
                                <typeDescription><bean:message key='label.field.autopay.invoice' bundle='providerBundle'/></typeDescription>
                            </c:when>
                            <c:when test="${payment.sumType =='FIXED_SUMMA_IN_RECIP_CURR' && payment.executionEventType != 'ON_REMAIND'}">
                                <typeDescription><bean:message key='label.field.autopay.always' bundle='providerBundle'/></typeDescription>
                            </c:when>
                        </c:choose>
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
                            <c:when test="${payment.sumType=='FIXED_SUMMA_IN_RECIP_CURR' && payment.executionEventType!='ON_REMAIND'}">
                                <always>
                                    <c:if test="${not empty payment.nextPayDate}">
                                        <tiles:insert definition="mobileDateType" flush="false">
                                            <tiles:put name="name" value="nextPayDate"/>
                                            <tiles:put name="calendar" beanName="payment" beanProperty="nextPayDate"/>
                                        </tiles:insert>
                                    </c:if>
                                    <tiles:insert definition="mobileMoneyType" flush="false">
                                        <tiles:put name="name" value="amount"/>
                                        <tiles:put name="money" beanName="payment" beanProperty="amount"/>
                                    </tiles:insert>
                                </always>
                            </c:when>
                            <c:when test="${payment.sumType=='BY_BILLING'}">
                                <invoice>
                                    <c:if test="${not empty payment.nextPayDate}">
                                        <tiles:insert definition="mobileDateType" flush="false">
                                            <tiles:put name="name" value="nextPayDate"/>
                                            <tiles:put name="calendar" beanName="payment" beanProperty="nextPayDate"/>
                                        </tiles:insert>
                                    </c:if>
                                    <c:if test="${not empty payment.maxSumWritePerMonth}">
                                        <tiles:insert definition="mobileMoneyType" flush="false">
                                            <tiles:put name="name" value="maxSumWritePerMonth"/>
                                            <tiles:put name="money" beanName="payment" beanProperty="maxSumWritePerMonth"/>
                                        </tiles:insert>
                                    </c:if>
                                </invoice>
                            </c:when>
                        </c:choose>
                    </autoSubDetails>
                </c:if>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>