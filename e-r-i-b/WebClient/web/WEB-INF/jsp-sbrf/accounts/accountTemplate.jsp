<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 03.05.2010
  Time: 14:51:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>
<%--
 bottomDataInfo - данные низа. Если не пустое то отображается содержимое данной переменной а опереции не отображаются.
 showInMainCheckBox - признак, указывающий на необходимость отображения checkbox'а отвечающего за отображение
                        данного вклада на главной странице. Значение по умолчанию false
accountInfoLink  - если true тогда картинка и другие элементы будут кликабильные и будут вести на детальную страницу
accountLink - текущего вклада
resourceAbstract - текущего вклада
abstractError - ошибка при получении выписки
showInThisWidgetCheckBox - признак, указывающий на необходимость отображения checkbox'а, отвечающего за отображение
                           данного вклада в виджете. 
--%>

<!-- Информация по счету -->
<c:if test="${accountLink != null}">
    <c:set var="account" value="${accountLink.account}" scope="request"/>
    <c:set var="isTarget" value="false" scope="request"/>
    <c:set var="isArrested" value="${account.accountState == 'ARRESTED'}" scope="request"/>
    <c:set var="isBlocked" value="${account.accountState!='OPENED' && account.accountState!='ARRESTED'}" scope="request"/>
    <c:set var="isLock" value="${account.accountState!='OPENED' && account.accountState!='LOST_PASSBOOK' && !isArrested}" scope="request"/>
    <c:set var="isNegative" value="${account.balance.decimal<0}" scope="request"/>
    <c:set var="isLostPassbook" value="${account.accountState=='LOST_PASSBOOK'}" scope="request"/>
    <c:set var="accountBalans" value="${fn:substring(account.number, 0,5)}" scope="request"/>
    <c:set var="balanceAsCents" value="${account.balance.asCents}" scope="request"/>
    <c:set var="imagePath" value="${globalUrl}/commonSkin/images" scope="request"/>
    <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images" scope="request"/>
    <c:set var="accountInfoUrl" scope="request"
           value="${phiz:calculateActionURL(pageContext,'/private/accounts/operations.do?id=')}"/>
    <c:set var="debitAllowed" scope="request"
           value="${((account.debitAllowed==null || account.debitAllowed == false) && (account.maxSumWrite!=null && account.maxSumWrite.decimal>0))||account.debitAllowed}"/>
    <%--
    Сущность вклады(вклады) [42301-42307, 42601-42507],
    сущность вклады(счета) [40817 и 40820, если они не являются СКС, т.е. к ним не привязана карта]
    --%>

    <tiles:insert page="/WEB-INF/jsp-sbrf/accounts/simpleAccountTemplate.jsp" flush="false">
        <tiles:put name="detailsPage" value="${detailsPage}"/>
        <tiles:put name="showInMainCheckBox" value="${showInMainCheckBox}"/>
        <c:choose>
            <c:when test="${accountBalans=='40817' ||accountBalans=='40820'}">
                <c:if test="${account.accountState == 'OPENED'}">
                    <tiles:put name="img" value="${imagePath}/deposits_type/account.jpg"/>
                </c:if>
                <c:if test="${isLock || isLostPassbook || isArrested}">
                    <tiles:put name="img" value="${imagePath}/deposits_type/accountBlocked.jpg"/>
                </c:if>
                <tiles:put name="alt" value="Вклад"/>
            </c:when>
            <c:otherwise>
                <c:if test="${account.accountState == 'OPENED'}">
                    <tiles:put name="img" value="${imagePath}/deposits_type/deposit.jpg"/>
                </c:if>
                <c:if test="${isLock || isLostPassbook || isArrested}">
                    <tiles:put name="img" value="${imagePath}/deposits_type/accountBlocked.jpg"/>
                </c:if>
                <tiles:put name="alt" value="Вклад"/>
            </c:otherwise>
        </c:choose>
        <tiles:put name="title"><bean:write name="accountLink" property="name"/></tiles:put>
        <c:choose>
            <c:when test="${detailsPage}">
                <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
            </c:otherwise>
        </c:choose>

        <c:set var="accNumLength">${fn:length(account.number)}</c:set>
        <c:set var="preparedAccNumber">№...${fn:substring(account.number, accNumLength - 7, accNumLength)}</c:set>
        <c:set var="emptyPeriod" value="00-00-000"/>
        <c:set var="isTermless" value="${empty account.period || account.period == emptyPeriod}"/>
        <c:if test="${!empty account.closeDate && account.accountState ne'CLOSED' && !isTermless}">
            <c:set var="preparedAccNumber">${preparedAccNumber}, <bean:message key="label.validTo" bundle="accountInfoBundle"/> ${phiz:formatDateWithStringMonth(account.closeDate)}</c:set>
        </c:if>
        <c:if test="${isTermless}">
            <c:set var="preparedAccNumber">${preparedAccNumber},&nbsp;<bean:message key="label.termless" bundle="accountInfoBundle"/></c:set>
        </c:if>
        <tiles:put name="productNumbers">
            <c:choose>
                <c:when test="${accountInfoLink}">
                    <div class="productNumber decoration-none">${preparedAccNumber}</div>
                </c:when>
                <c:otherwise>
                    <div class="productNumber">${preparedAccNumber}</div>
                </c:otherwise>
            </c:choose>
        </tiles:put>

        <c:if test="${(!empty account.interestRate) && (account.interestRate >= 0)}">
            <tiles:put name="rightComment" value="true"/>
            <tiles:put name="comment">
                <fmt:formatNumber value="${account.interestRate}" pattern="##0.00"/>%
            </tiles:put>
        </c:if>

        <c:if test="${accountInfoLink}">
            <tiles:put name="src" value="${accountInfoUrl}${accountLink.id}"/>
        </c:if>

        <tiles:put name="leftData">
            <table class="productDetail">
                <c:if test="${!empty account.maxSumWrite}">
                    <tr>
                        <td>
                            <div class="availableReight"><bean:message key="label.availableToGet" bundle="accountInfoBundle"/> &nbsp;</div>
                        </td>
                        <td>
                            <div class="maxSumWriteContainer availableReight">${phiz:productFormatAmount(account.maxSumWrite)}</div>
                        </td>
                    </tr>
                </c:if>
            </table>
        </tiles:put>

        <tiles:put name="centerData">
            <c:if test="${!empty account.balance && account.balance.decimal != 0}">
                <c:set var="amountinfo" value="${phiz:formatAmount(account.balance)}"/>
                <c:if test="${isNegative}">
                    <c:set var="amountinfo">
                        &minus;${fn:substring(amountinfo, 1, -1)}
                    </c:set>
                </c:if>
                <c:choose>
                    <c:when test="${isLock || isNegative}">
                        <c:if test="${detailsPage}">
                            <span class="detailAmount negativeAmount">
                                ${amountinfo}
                            </span>
                        </c:if>
                        <c:if test="${not detailsPage}">
                            <span class="overallAmount negativeAmount">
                                ${amountinfo}
                            </span>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${detailsPage}">
                            <span class="detailAmount">${amountinfo}</span>
                        </c:if>
                        <c:if test="${not detailsPage}">
                            <span class="overallAmount">${amountinfo}</span>
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <br />
            </c:if>
        </tiles:put>

        <tiles:put name="showRightData" value="true"/>

        <c:if test="${isLock || isLostPassbook || isArrested}">
            <tiles:put name="imgStatus" value="${account.accountState.description}"/>
        </c:if>

        <c:if test="${isLock || isArrested}">
            <tiles:put name="status" value="error"/>
        </c:if>

        <c:if test="${showInThisWidgetCheckBox}">
            <tiles:put name="id" value="${accountLink.id}"/>
            <tiles:put name="showInThisWidgetCheckBox" value="true"/>
            <tiles:put name="productType" value="account"/>
        </c:if>
    </tiles:insert>
</c:if>
