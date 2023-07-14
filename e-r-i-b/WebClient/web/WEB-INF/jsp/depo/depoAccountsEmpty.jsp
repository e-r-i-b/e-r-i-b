<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:if test="${not empty form.confirmableObject}">
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
</c:if>
<c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
<c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
<c:set var="personInfo" value="${phiz:getPersonInfo()}"/>

<c:choose>
    <c:when test="${not personInfo.isRegisteredInDepo}">
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="title"><bean:message bundle="depoBundle" key="form.depo.empty.title"/></tiles:put>
            <tiles:put name="data">
                <c:choose>
                    <c:when test="${form.registrationError == 'true'}">
                        <h3><bean:message bundle="depoBundle" key="form.depo.empty.error.title"/></h3>
                        <div class="clear"></div>
                        <ul class="payments">
                            <bean:message bundle="depoBundle" key="form.depo.empty.error.reason.title"/>
                            <li><bean:message bundle="depoBundle" key="form.depo.empty.error.reason.no_accounts"/>
                                <html:link href="http://sberbank.ru/ru/person/investments/depository/"
                                target="_blank"
                                styleClass="text-green"><bean:message bundle="depoBundle" key="form.depo.empty.error.reason.no_accounts.more"/> </html:link>
                            <li><bean:message bundle="depoBundle" key="form.depo.empty.error.reason.change_dul"/></li>
                            <li><bean:message bundle="depoBundle" key="depo.accounts.access.description"/></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <script type="text/javascript">
                            var message =
                                    '<p>' +
                                        '<bean:message bundle="depoBundle" key="form.depo.empty.message.title"/>' +
                                    '</p>' +
                                    '<p> ' +
                                        '<bean:message bundle="depoBundle" key="form.depo.empty.message.reason"/>' +
                                    '</p>'+
                                    '<p> ' +
                                        '<bean:message bundle="depoBundle" key="form.depo.empty.message.detail.prefix"/> ' +
                                        '<a href="http://sberbank.ru/ru/person/investments/depository/"  target="_blank">' +
                                            '<bean:message bundle="depoBundle" key="form.depo.empty.message.detail.link"/>' +
                                        '</a>' +
                                        '<bean:message bundle="depoBundle" key="form.depo.empty.message.detail.suffix"/>' +
                                    '</p>';
                            addMessage(message);
                        </script>
                        <p class="headerTitleText"><bean:message bundle="depoBundle" key="form.depo.empty.agreement"/> </p>
                    </c:otherwise>
                </c:choose>

                <div class="buttonsArea">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle"         value="commonBundle"/>
                        <tiles:put name="viewType"       value="buttonGrey"/>
                         <tiles:put name="action" value="/private/accounts"/>
                    </tiles:insert>
                    <c:choose>
                        <c:when test="${not empty confirmRequest}">
                            <c:set var="confirmDepoMessage"><bean:message bundle="depoBundle" key="form.depo.empty.confirm.card.message"/></c:set>
                            <c:choose>
                                <c:when test="${not empty form.fields and form.fields.confirmByCard}">
                                    <c:set var="confirmTemplate" value="confirm_card"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
                                    <c:if test="${confirmRequest.strategyType == 'sms'}">
                                        <c:set var="confirmDepoMessage"><bean:message bundle="depoBundle" key="form.depo.empty.confirm.sms.message"/></c:set>
                                    </c:if>
                                    <c:if test="${confirmRequest.strategyType == 'cap'}">
                                        <c:set var="confirmDepoMessage"><bean:message bundle="depoBundle" key="form.depo.empty.confirm.cap.message"/></c:set>
                                    </c:if>
                                    <c:if test="${confirmRequest.strategyType == 'push'}">
                                        <c:set var="confirmDepoMessage"><bean:message bundle="depoBundle" key="form.depo.empty.confirm.push.message"/></c:set>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                            <tiles:insert  definition="${confirmTemplate}" flush="false">
                                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                <tiles:put name="anotherStrategy" value="${anotherStrategy}"/>
                                <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                <tiles:put name="confirmableObject" value="activePerson"/>
                                <tiles:put name="message" value="${confirmDepoMessage}"/>
                                <c:if test="${confirmRequest.strategyType == 'card'}">
                                    <tiles:put name="cardNumber" value="${phiz:getCutCardNumber(form.fields.confirmCard)}"/>
                                </c:if>
                                <tiles:put name="ajaxUrl" value="/private/depo/list"/>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.register"/>
                                <tiles:put name="commandTextKey" value="button.register"/>
                                <tiles:put name="commandHelpKey" value="button.register"/>
                                <tiles:put name="bundle"         value="depoBundle"/>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                </div>
            </tiles:put>
        </tiles:insert>
    </c:when>
    <c:otherwise>
        <script type="text/javascript">
            var message =
                    '<p>' +
                        '<bean:message bundle="depoBundle" key="form.depo.empty.not_registered.message"/>' +
                    '</p>';
            addMessage(message);
        </script>
    </c:otherwise>
</c:choose>
