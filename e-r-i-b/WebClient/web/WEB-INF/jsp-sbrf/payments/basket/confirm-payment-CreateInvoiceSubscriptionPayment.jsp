<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="mode" value="${phiz:getUserVisitingMode()}"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<html:form action="/private/userprofile/basket/payments/confirm" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
<c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
<c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
<c:set var="paymentFormName" value="${form.metadata.listFormName}" scope="request"/>
<c:set var="metadata" value="${form.metadata}"/>
<c:set var="mode" value="${phiz:getUserVisitingMode()}"/>

<tiles:insert definition="paymentsBasket">
    <c:if test="${not empty confirmRequest and not empty form.autoConfirmRequestType}">
        <tiles:put name="needShowMessages" value="false"/>
    </c:if>
    <tiles:put name="mainmenu" value="Payments"/>
    <tiles:put name="submenu" type="string" value="${form.metadata.listFormName}"/>

    <%-- заголовок --%>
    <tiles:put name="pageTitle" type="string">
        <c:out value="${form.formDescription}"/>
    </tiles:put>

    <tiles:put name="data" type="string">
        <tiles:insert definition="profileTemplate" flush="false">
            <tiles:put name="activeItem" value="searchAccounts"/>
            <tiles:put name="title">
                <span class="size24">Настройка автопоиска счетов по услуге</span>
            </tiles:put>
            <tiles:put name="data">
                <div id="paymentStripe">
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="выбор услуги"/>
                        <tiles:put name="width" value="230px"/>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="заполнение формы"/>
                        <tiles:put name="width" value="230px"/>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="подтверждение"/>
                        <tiles:put name="width" value="230px"/>
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                </div>
                <div class="clear"></div>
                <c:set var="message">
                    <c:choose>
                        <c:when test="${confirmRequest.strategyType eq 'card'}">
                            <bean:message key="card.payments.security.message" bundle="securityBundle"/>
                        </c:when>
                        <c:when test="${confirmRequest.strategyType eq 'sms'}">
                            <bean:message key="sms.payments.security.message" bundle="securityBundle"/>
                        </c:when>
                        <c:when test="${confirmRequest.strategyType eq 'cap'}">
                            <bean:message key="cap.payments.security.message" bundle="securityBundle"/>
                        </c:when>
                        <c:otherwise>
                            Внимательно проверьте, правильно ли Вы заполнили заявку. Для подтверждения операции нажмите на кнопку &laquo;Подтвердить&raquo;.
                        </c:otherwise>
                    </c:choose>
                </c:set>
                <div id="paymentForm">
                    ${form.html}
                </div>
                <div class="clear"></div>
                <div class="buttonsArea">
                    <tiles:insert definition="confirmationButton" flush="false">
                        <tiles:put name="winId" value="confirmation"/>
                        <tiles:put name="title" value="Подтверждение удаления документа"/>
                        <tiles:put name="currentBundle" value="commonBundle"/>
                        <tiles:put name="confirmCommandKey" value="button.remove"/>
                        <tiles:put name="buttonViewType" value="buttonGrey"/>
                        <tiles:put name="message"><bean:message key="confirm.text" bundle="paymentsBundle"/></tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="action" value="/private/payments"/>
                    </tiles:insert>

                    <c:set var="hasSMS" value="${phiz:isContainStrategy(confirmStrategy,'sms')}"/>
                    <c:set var="hasCard" value="${phiz:isContainStrategy(confirmStrategy,'card')}"/>
                    <c:set var="hasCap" value="${phiz:isContainStrategy(confirmStrategy,'cap')}"/>
                    <c:choose>
                        <c:when test="${not empty confirmRequest}">
                            <span class="clientButton chooseConfirmStrategy">
                                <tiles:insert definition="confirmButtons" flush="false">
                                    <tiles:put name="ajaxUrl" value="/private/async/payments/confirm"/>
                                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                    <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                    <tiles:put name="message" value="${message}"/>
                                    <tiles:put name="formName" value="${metadata.form.name}"/>
                                    <tiles:put name="mode" value="${mode}"/>
                                    <tiles:put name="stateObject" value="document"/>
                                </tiles:insert>
                            </span>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.confirm"/>
                                <tiles:put name="commandHelpKey" value="button.dispatch.help"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="isDefault" value="true"/>
                                <tiles:put name="stateObject" value="document"/>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${phiz:isScriptsRSAActive()}">
                        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
                        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
                        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

                        <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
                        <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>
                    </c:if>
                    <script type="text/javascript">
                        doOnLoad(function()
                        {
                            var button = findCommandButton("button.dispatch");
                            if (button)
                            {
                                button.validationFunction = function()
                                {
                                    <c:if test="${phiz:isScriptsRSAActive()}">
                                        new RSAObject().toHiddenParameters();
                                    </c:if>
                                    return true;
                                }
                            }
                        });
                    </script>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.edit"/>
                    <tiles:put name="commandHelpKey" value="button.edit.help"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="stateObject" value="document"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/backIcon.png"/>
                    <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/backIconHover.png"/>
                    <tiles:put name="imagePosition"  value="left"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
        <c:if test="${not empty form.document.state.code}">
            <div id="stateDescription" onmouseover="showLayer('state','stateDescription','default');"
                 onmouseout="hideLayer('stateDescription');" class="layerFon stateDescription">
                <div class="floatMessageHeader"></div>
                <div class="layerFonBlock">
                    <bean:message key="payment.state.hint.${form.document.state.code}" bundle="paymentsBundle"/>
                </div>
            </div>
        </c:if>
    </tiles:put>
</tiles:insert>
</html:form>
