<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>

<!-- Информация по цели -->

<c:if test="${target != null}">
    <c:set var="commonImagePath" value="${globalUrl}/commonSkin/images" scope="request"/>
    <c:set var="targetInfoUrl" scope="request"
           value="${phiz:calculateActionURL(pageContext,'/private/targets/detailInfoNoAccount.do?id=')}${target.id}"/>
    <c:set var="color" value="green"/>
    <c:set var="datesDiff" value="${phiz:datesDiff(target.plannedDate, phiz:currentDate())}"/>
    <c:if test="${datesDiff < 0}">
        <c:set var="color" value="orange"/>
    </c:if>
    <c:set var="account" value="" scope="request"/>
    <c:set var="accountState" value="" scope="request"/>
    <c:set var="showInSys" value="true"/>
    <c:set var="isTarget" value="true" scope="request"/>

    <c:choose>
        <c:when test="${not empty accountLink}">
            <c:set var="account" value="${accountLink.account}" scope="request"/>
            <c:set var="accountState" value="${account.accountState}" scope="request"/>
            <c:set var="isLock" value="${accountState !='OPENED' && accountState != 'LOST_PASSBOOK'}" scope="request"/>
            <c:set var="isBlocked" value="${account.accountState!='OPENED' && account.accountState!='ARRESTED'}" scope="request"/>
            <c:set var="isLostPassbook" value="${accountState == 'LOST_PASSBOOK'}" scope="request"/>
            <c:set var="balanceAsCents" value="${account.balance.asCents}" scope="request"/>
            <c:set var="debitAllowed" scope="request"
               value="${((account.debitAllowed==null || account.debitAllowed == false) && (account.maxSumWrite!=null && account.maxSumWrite.decimal>0))||account.debitAllowed}"/>
            <c:set var="showInSys" value="${not empty accountLink.showInSystem && accountLink.showInSystem}"/>
            <c:if test="${showInSys}">
                <c:set var="targetInfoUrl" scope="request"
                   value="${phiz:calculateActionURL(pageContext,'/private/accounts/info.do?id=')}${accountLink.id}"/>
            </c:if>
            <c:set var="accountAmount" value="${account.balance.decimal}"/>
            <c:set var="maxAmount" value="${target.amount.decimal}"/>
            <c:if test="${maxAmount < accountAmount}">
                <c:set var="maxAmount" value="${accountAmount}"/>
                <c:set var="color" value="green"/>
            </c:if>
        </c:when>
        <c:otherwise>
            <c:set var="claim" value="${phiz:getAccountOpeningClaimById(target.claimId)}"/>
        </c:otherwise>
    </c:choose>


    <div class="targetProduct">
        <tiles:insert page="/WEB-INF/jsp-sbrf/accounts/simpleAccountTemplate.jsp" flush="false">
            <tiles:put name="detailsPage" value="${detailsPage}"/>
            <tiles:put name="img" value="${commonImagePath}/account_targets/${target.dictionaryTarget}.png"/>
            <tiles:put name="alt" value="${target.dictionaryTarget.description}"/>
            <tiles:put name="showInMainCheckBox" value="${showInMainCheckBox}"/>

            <tiles:put name="title"><c:out value="${target.name}"/></tiles:put>
            <c:choose>
                <c:when test="${detailsPage}">
                    <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
                </c:otherwise>
            </c:choose>

            <tiles:put name="additionalProductInfo">
                <span class="word-wrap accTargetComment"><c:out value="${target.nameComment}"/></span>
            </tiles:put>

            <tiles:put name="productImgAdditionalData">
                <div class="accTargetImgAdditionalData">
                    <span class="targetImgStatus">
                        Дата покупки
                        <c:choose>
                            <c:when test="${datesDiff < 0}">
                                <tiles:insert definition="roundedPlate" flush="false">
                                    <tiles:put name="data">
                                        <span class="bold"><fmt:formatDate value="${target.plannedDate.time}" pattern="dd.MM.yyyy"/></span>
                                    </tiles:put>
                                    <tiles:put name="color" value="orange"/>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <span class="bold"><fmt:formatDate value="${target.plannedDate.time}" pattern="dd.MM.yyyy"/></span>
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>
            </tiles:put>

            <c:if test="${targetInfoLink}">
                <tiles:put name="src" value="${targetInfoUrl}"/>
            </c:if>

            <tiles:put name="leftData">
                <c:if test="${!empty accountLink && !empty account.interestRate && showInSys}">
                    <nobr>ставка: <fmt:formatNumber value="${account.interestRate}" pattern="##0.00"/>%</nobr>
                </c:if>
            </tiles:put>

            <c:if test="${!showInSys}">
                <tiles:put name="showBottomData" value="false"/>
            </c:if>

            <tiles:put name="additionalData">
                <c:set var="createTargetUrl" value="${phiz:calculateActionURL(pageContext,'/private/finances/targets/editTarget?id=')}${target.id}"/>
                <c:set var="openAccountUrl" value="${phiz:calculateActionURL(pageContext,'/private/finances/targets/viewTarget?targetId=')}${target.id}"/>
                <c:choose>
                    <c:when test="${!showInSys}">
                        <div class="errAccountTarget">
                            <bean:message bundle="financesBundle" key="label.account.display.off.text"/>
                        </div>
                    </c:when>
                    <c:when test="${not empty accountLink && accountState == 'CLOSED'}">
                        <div class="errAccountTarget">
                            Вклад для достижения цели закрыт. Вы можете
                            <a href="#" onclick="window.location='${openAccountUrl}'; cancelBubbling(event);">открыть</a>
                            новый вклад или удалить цель.
                        </div>    
                    </c:when>
                    <c:when test="${not empty accountLink and empty accountAmount}"> <%-- значит не пришла информация по вкладу --%>
                        <div class="errAccountTarget">Информация по вкладам временно недоступна.</div>
                    </c:when>
                    <c:when test="${not empty accountLink}">
                        <tiles:insert definition="thermometerTemplate" flush="false">
                            <tiles:put name="id" value="targetTemplate${target.id}"/>
                            <tiles:put name="maxValue" value="${maxAmount}"/>
                            <tiles:put name="value" value="${accountAmount}"/>
                            <tiles:put name="color" value="${color}"/>
                            <tiles:put name="rightData">${phiz:formatAmount(target.amount)}</tiles:put>
                        </tiles:insert>
                    </c:when>
                    <c:otherwise>
                        <c:set var="state" value="${claim.state.code}"/>
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
                    </c:otherwise>
                </c:choose>
            </tiles:put>
        </tiles:insert>
    </div>
</c:if>
