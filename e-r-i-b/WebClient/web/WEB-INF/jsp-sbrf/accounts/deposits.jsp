<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/accounts" onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<tiles:insert definition="accountInfo">
<tiles:put name="mainmenu" value="Deposits"/>
<tiles:put name="enabledLink" value="false"/>
<tiles:put name="menu" type="string"/>
<tiles:put name="data" type="string">
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:if test="${phiz:impliesService('AccountOpeningClaim') || phiz:impliesService('AccountClosingPayment') || phiz:impliesService('AccountOpeningClaimWithClose')}">
        <div class="mainWorkspace productListLink">
           <tiles:insert definition="paymentCardsDiv" service="AccountOpeningClaim" flush="false">
               <tiles:put name="serviceId"   value="AccountOpeningClaim"/>
               <tiles:put name="action"      value="/private/deposits/products/list"/>
               <tiles:put name="globalImage"       value="payment/iconPmntList_Accounts.png"/>
               <tiles:put name="description" value="Открыть новый вклад и перевести на него деньги."/>
           </tiles:insert>

           <tiles:insert definition="paymentsPaymentCardsDiv" service="AccountClosingPayment" flush="false">
               <tiles:put name="serviceId"   value="AccountClosingPayment"/>
               <tiles:put name="globalImage"       value="payment/iconPmntList_AccountOpeningClaim.png"/>
               <tiles:put name="description" value="Закрыть вклад и перевести остаток на другой счёт или карту."/>
           </tiles:insert>

           <tiles:insert definition="paymentCardsDiv" service="AccountOpeningClaimWithClose" flush="false">
               <tiles:put name="serviceId"   value="AccountOpeningClaimWithClose"/>
               <tiles:put name="action"      value="/private/deposits/products/list"/>
               <tiles:put name="globalImage"       value="payment/iconPmntList_Accounts.png"/>
               <tiles:put name="listPayTitle" value="Открытие вклада (закрыть счет списания)"/>
               <tiles:put name="description" value="Открыть новый вклад с закрытием счета списания."/>
               <tiles:putList name="params">
                   <tiles:putList name="names"><tiles:add>form</tiles:add></tiles:putList>
                   <tiles:putList name="values"><tiles:add>AccountOpeningClaimWithClose</tiles:add></tiles:putList>
               </tiles:putList>
               <tiles:put name="notForm" value="true"/>
           </tiles:insert>

           <c:if test="${phiz:impliesService('ClientPromoCode')}">
               <tiles:insert definition="promoDiv" service="AccountOpeningClaim" flush="false">
                   <tiles:put name="serviceId" value="AccountOpeningClaim"/>
                   <tiles:put name="maxLength" value="${form.promoDivMaxLength}"/>
                   <tiles:put name="listPayTitle" value="У меня есть промо-код"/>
                   <tiles:put name="description" value="Промо-коды открывают доступ к вкладам на выгодных условиях"/>
               </tiles:insert>
           </c:if>

           <div class="clear"></div>
        </div>
    </c:if>

        <c:choose>
            <c:when test="${not empty form.activeAccounts || not empty form.closedAccounts}">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Вклады и счета"/>
                    <tiles:put name="data">
                        <c:set var="availableTargets" value="${phiz:impliesOperation('GetTargetOperation', 'TargetsService')}"/>
                        <c:if test="${not empty form.activeAccounts}">
                            <c:set var="accountInfoLink" value="true" scope="request"/>
                            <c:set var="accountsCount" value="${fn:length(form.activeAccounts)}"/>
                            <logic:iterate id="listElement" name="form" property="activeAccounts" indexId="i">
                                <c:set var="target" value="" scope="request"/>
                                <c:if test="${availableTargets}">
                                    <c:set var="target" value="${listElement.target}" scope="request"/>
                                </c:if>
                                <c:set var="accountLink" value="${listElement}" scope="request"/>
                                <c:set var="resourceAbstract" value="${form.accountAbstract[listElement]}" scope="request"/>
                                <c:set var="abstractError" value="${form.accountAbstractErrors[listElement]}" scope="request"/>

                                <c:choose>
                                    <c:when test="${not empty target}">
                                        <c:set var="targetInfoLink" value="true" scope="request"/>
                                        <c:set var="isDetailInfoPage" value="false" scope="request"/>
                                        <tiles:insert page="targetTemplate.jsp" flush="false"/>
                                    </c:when>
                                    <c:otherwise>
                                        <tiles:insert page="accountTemplate.jsp" flush="false"/>
                                    </c:otherwise>
                                </c:choose>

                                <c:if test="${accountsCount-1 > i}">
                                    <div class="productDivider"></div>
                                </c:if>
                            </logic:iterate>
                        </c:if>
                    </tiles:put>
                </tiles:insert>

                <c:if test="${not empty form.closedAccounts}">
                    <tiles:insert definition="hidableRoundBorder" flush="false">
                        <a id="closedAccounts"><tiles:put name="title" value="Закрытые вклады"/></a>
                        <tiles:put name="color" value="whiteTop"/>
                        <tiles:put name="data">
                            <c:set var="accountInfoLink" value="true" scope="request"/>
                            <c:set var="accountsCount" value="${fn:length(form.closedAccounts)}"/>
                            <logic:iterate id="listElement" name="form" property="closedAccounts" indexId="i">
                                <c:set var="target" value="" scope="request"/>
                                <c:if test="${availableTargets}">
                                    <c:set var="target" value="${listElement.target}" scope="request"/>
                                </c:if>
                                <c:set var="accountLink" value="${listElement}" scope="request"/>
                                <c:set var="resourceAbstract" value="${form.accountAbstract[listElement]}" scope="request"/>
                                <c:set var="abstractError" value="${form.accountAbstractErrors[listElement]}" scope="request"/>

                                <c:choose>
                                    <c:when test="${not empty target}">
                                        <c:set var="targetInfoLink" value="true" scope="request"/>
                                        <c:set var="isDetailInfoPage" value="false" scope="request"/>
                                        <tiles:insert page="targetTemplate.jsp" flush="false"/>
                                    </c:when>
                                    <c:otherwise>
                                        <tiles:insert page="accountTemplate.jsp" flush="false"/>
                                    </c:otherwise>
                                </c:choose>

                                <c:if test="${accountsCount-1 > i}">
                                    <div class="productDivider"></div>
                                </c:if>
                            </logic:iterate>
                        </tiles:put>
                    </tiles:insert>
                </c:if>
            </c:when>
            <c:otherwise>
                <c:if test="${not form.allAccountDown}">
                    <div id="infotext">
                        <c:set var="creationType">${phiz:getPersonInfo().creationType}</c:set>
                        <c:choose>
                            <c:when test="${creationType == 'UDBO' || creationType == 'SBOL'}">
                                <tiles:insert page="no-deposits.jsp" flush="false"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert page="/WEB-INF/jsp-sbrf/needUDBO.jsp" flush="false">
                                    <tiles:put name="product" value="вкладам" />
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>

        <c:if test="${phiz:isScriptsRSAActive()}">
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

            <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
            <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>

            <script type="text/javascript">
                <%-- формирование основных данных для ФМ --%>
                new RSAObject().toHiddenParameters();
            </script>
        </c:if>
    </tiles:put>
</tiles:insert>
</html:form>