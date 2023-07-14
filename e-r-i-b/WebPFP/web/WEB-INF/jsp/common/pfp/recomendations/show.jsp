<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="globalPath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

     <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
     <c:set var="outcomeSumm" value="${phiz:formatAmount(form.outcomeMoney)}"/>
     <c:set var="outcome" value="${form.outcomeMoney.decimal}"/>
     <tiles:insert definition="webModulePagePfp">
         <tiles:put name="title">
             <bean:message bundle="pfpBundle" key="page.title"/>
         </tiles:put>
        <tiles:put name="description">
            <bean:message bundle="pfpBundle" key="show.recommendation.description"/>
        </tiles:put>

        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message bundle="pfpBundle" key="index.breadcrumbsLink"/></tiles:put>
                <c:choose>
                    <c:when test="${phiz:impliesService('UseWebAPIService')}">
                        <tiles:put name="url" value="${phiz:getWebAPIUrl('graphics.finance')}"/>
                    </c:when>
                    <c:otherwise>
                        <tiles:put name="action" value="/private/graphics/finance"/>
                    </c:otherwise>
                </c:choose>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name">Финансовое планирование</tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
            <div class="clear"></div>
        </tiles:put>

        <tiles:put name="data">
             <div class="pfpBlocks">
                <div id="paymentStripe">
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.targets" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.riskProfile" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.portfolio" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.financePlan" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.plan" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>

                <c:if test="${pageName == 'SHOW_RECOMMENDATIONS'}">
                    <%@ include file="/WEB-INF/jsp/common/pfp/recomendations/usingCreditCards.jsp"%>
                </c:if>

                <c:if test="${pageName == 'CHOOSE_CARD'}">
                    <%@ include file="/WEB-INF/jsp/common/pfp/recomendations/effectUseCard.jsp"%>
                    <%@ include file="/WEB-INF/jsp/common/pfp/recomendations/chooseCreditCard.jsp"%>
                </c:if>

                <div class="pfpButtonsBlock">
                    <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.next"/>
                            <tiles:put name="commandHelpKey" value="button.next.help"/>                            
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="bundle" value="pfpBundle"/>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>

                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.back2"/>
                    <tiles:put name="commandHelpKey" value="button.back2.help"/>
                    <tiles:put name="bundle" value="pfpBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>

