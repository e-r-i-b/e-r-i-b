<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/private/targets/detailInfoNoAccount" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="target" value="${form.target}" scope="request"/>
    <c:set var="detailsPage" value="true" scope="request"/>

    <tiles:insert definition="financePlot">
        <%--Хлебные крошки--%>
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message key="label.finance" bundle="favouriteBundle"/></tiles:put>
                <c:choose>
                    <c:when test="${phiz:impliesService('UseWebAPIService')}">
                        <tiles:put name="url" value="${phiz:getWebAPIUrl('graphics.finance')}"/>
                    </c:when>
                    <c:otherwise>
                        <tiles:put name="action" value="/private/graphics/finance.do"/>
                    </c:otherwise>
                </c:choose>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:write name="target" property="name"/></tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
            <c:set var="baseInfo">
                <bean:message key="favourite.link.target" bundle="paymentsBundle"/>
            </c:set>
            <tiles:insert definition="addToFavouriteButton" flush="false">
                <tiles:put name="formName"><c:out value='${baseInfo} «${target.name}»'/></tiles:put>
                <tiles:put name="patternName"><c:out value='${baseInfo} «${target.name}»'/></tiles:put>
                <tiles:put name="typeFormat">TARGET_LINK</tiles:put>
                <tiles:put name="productId">${form.id}</tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="roundBorderWithoutTop" flush="false">
                <tiles:put name="top">
                    <c:set var="selectedTab" value="myTargets"/>
                    <%@ include file="/WEB-INF/jsp/graphics/showFinanceHeader.jsp" %>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="financeContainer" flush="false">

                        <c:set var="showBottomData" value="false" scope="request"/>
                        <c:set var="isDetailInfoPage" value="true" scope="request"/>
                        <c:set var="accountLink" value="${target.accountLink}" scope="request"/>
                        <%@ include file="/WEB-INF/jsp-sbrf/accounts/targetTemplate.jsp"%>

                        <br/>
                        <div class="tabContainer" onkeypress="onEnterKey(event)">
                            <tiles:insert definition="paymentTabs" flush="false">
                                <tiles:put name="count" value="1"/>
                                <tiles:put name="tabItems">
                                    <tiles:insert definition="paymentTabItem" flush="false">
                                        <tiles:put name="position" value="first-last"/>
                                        <tiles:put name="active" value="true"/>
                                        <tiles:put name="title" value="Детальная информация"/>
                                        <tiles:put name="action" value="/private/targets/detailInfoNoAccount.do?id=${target.id}"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>

                            <div class="abstractFilter">

                                <c:set var="claim" value="${phiz:getAccountOpeningClaimById(target.claimId)}"/>
                                <c:set var="state" value="${claim.state.code}"/>
                                <c:set var="createTargetUrl" value="${phiz:calculateActionURL(pageContext,'/private/finances/targets/editTarget?id=')}${target.id}"/>
                                <c:set var="openAccountUrl" value="${phiz:calculateActionURL(pageContext,'/private/finances/targets/viewTarget?targetId=')}${target.id}"/>
                                <c:if test="${empty accountLink}">
                                    <div class="errAccountTarget">
                                        <c:choose>
                                            <c:when test="${state == 'EXECUTED'}">
                                                <bean:message key="message.account.executed" bundle="financesBundle"/>
                                            </c:when>
                                            <c:when test="${state == 'DELAYED_DISPATCH'}">
                                                Заявка на открытие вклада отправлена в банк на обработку. Плановая дата исполнения
                                                <fmt:formatDate value="${claim.admissionDate.time}" pattern="dd.MM.yyyy в HH:mm"/>
                                            </c:when>
                                            <c:when test="${state == 'SAVED'}">
                                                <bean:message key="message.account.not.confirmed.prefix" bundle="financesBundle"/>
                                                <a href="#" onclick="window.location='${openAccountUrl}'; cancelBubbling(event);"><bean:message key="message.account.not.confirmed.suffix" bundle="financesBundle"/></a>
                                                <bean:message key="message.account.not.confirmed.end" bundle="financesBundle"/>
                                            </c:when>
                                            <c:when test="${state == 'REFUSED' || state == 'DELETED'}">
                                                Для открытия вклада создайте новую заявку. Оформленная ранее заявка отказана.

                                                <a href="#" onclick="window.location='${createTargetUrl}'; cancelBubbling(event);"><bean:message key="button.reopen.account" bundle="financesBundle"/></a>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </c:if>
                            </div>
                            <br/>
                            <div class="clear"></div>
                            <div class="hasLayout accountProductInfo">
                                <fieldset>
                                    <table class="additional-product-info firstColumnFix">
                                        <tr>
                                            <td class="dottedBorderTop align-left grayBlockTitle fixColumn">Параметры цели</td>
                                            <td class="dottedBorderTop">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td class="align-right field">Название цели:</td>
                                            <td>
                                                <html:hidden name="form" property="field(dictionaryTarget)"/>
                                                <c:choose>
                                                    <c:when test="${target.dictionaryTarget == 'OTHER'}">
                                                        <html:text name="form" property="field(targetName)" size="25"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="word-wrap bold"><c:out value="${target.name}"/></span>
                                                    </c:otherwise>
                                                </c:choose>
                                                <br/><br/>
                                                <html:text name="form" property="field(targetNameComment)" styleClass="customPlaceholder" title="введите комментарий" size="25"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="align-right field">Дата достижения:</td>
                                            <td>
                                                <input type="text" name="field(targetPlanedDate)" class="date-pick dp-applied" size="10"
                                                      value="<bean:write name="org.apache.struts.taglib.html.BEAN" property="field(targetPlanedDate)" format="dd/MM/yyyy"/>"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="align-right field">Сумма цели:</td>
                                            <td>
                                                <html:text name="form" property="field(targetAmount)" styleClass="moneyField"/>
                                                <bean:message key="currency.rub" bundle="financesBundle"/>
                                            </td>
                                        </tr>
                                    </table>
                                </fieldset>
                            </div>
                        </div>
                        <div class="clear"></div>

                       <div class="buttonsArea">
                           <tiles:insert definition="clientButton" flush="false">
                               <tiles:put name="commandTextKey" value="button.cancel"/>
                               <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                               <tiles:put name="bundle" value="accountInfoBundle"/>
                               <tiles:put name="viewType" value="simpleLink"/>
                               <tiles:put name="action" value="/private/targets/detailInfoNoAccount.do?id=${target.id}"/>
                           </tiles:insert>
                           <tiles:insert definition="commandButton" flush="false">
                               <tiles:put name="commandKey" value="button.save"/>
                               <tiles:put name="commandTextKey" value="button.saveTargetParams"/>
                               <tiles:put name="commandHelpKey" value="button.saveTargetParams.help"/>
                               <tiles:put name="bundle" value="accountInfoBundle"/>
                               <tiles:put name="isDefault" value="true"/>
                           </tiles:insert>
                       </div>
                        
                       <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.back.to.targetList"/>
                            <tiles:put name="commandHelpKey" value="button.back.to.targetList.help"/>
                            <tiles:put name="bundle" value="financesBundle"/>
                            <tiles:put name="viewType" value="blueGrayLink"/>
                            <tiles:put name="action" value="/private/finances/targets/targetsList"/>
                        </tiles:insert>

                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>