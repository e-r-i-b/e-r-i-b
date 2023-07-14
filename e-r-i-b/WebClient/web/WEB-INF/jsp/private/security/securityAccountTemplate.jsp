<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<%-- Информация о сберегательном сертификате --%>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images/products"/>
<div class="securityAccountTemplate <c:if test="${detailsPage}">detailTemplate</c:if>">
    <c:set var="periodExpired" value="${securityAccount.termStartDt <= phiz:currentDate()}" />
    <tiles:insert definition="productTemplate" flush="false">
        <tiles:put name="productViewBacklight" value="false"/>
        <tiles:put name="img" value="${imagePath}/icon_certificate.jpg"/>
        <c:set var="infoUrl" value="${phiz:calculateActionURL(pageContext,'/private/security/view')}"/>
        <c:if test="${securityInfoLink == true}">
            <tiles:put name="src" value="${infoUrl}?id=${securityLink.id}"/>
        </c:if>
        <c:set var="linkName" value="${securityLink.name}"/>
        <tiles:put name="title"><c:out value = "${linkName}"/></tiles:put>
        <c:choose>
            <c:when test="${detailsPage}">
                <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
            </c:otherwise>
        </c:choose>
        <tiles:put name="leftData">
            <span class="productNumber"> ${securityAccount.serialNumber}</span>
            <table class="productDetail">
                <tr>
                    <td>Процентная ставка:</td>
                    <td class="value">
                        <c:choose>
                            <c:when test="${periodExpired}">
                                0%
                            </c:when>
                            <c:otherwise>
                                ${securityAccount.incomeRate} %
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>Сумма дохода:</td>
                    <td  class="value">
                        <c:choose>
                            <c:when test="${periodExpired}">
                                0
                            </c:when>
                            <c:otherwise>
                                ${phiz:formatAmount(securityAccount.incomeAmt)}
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr><td>Дата востребования:</td><td  class="value"> ${phiz:сalendarToString(securityAccount.termStartDt)}</td></tr>
                <tr><td>Место хранения:</td ><td  class="value">
                    <c:choose>
                        <c:when test="${securityLink.onStorageInBank}">
                            в Банке
                        </c:when>
                        <c:otherwise>
                            на руках
                        </c:otherwise>
                    </c:choose>
                </td></tr>
            </table>
        </tiles:put>
        <tiles:put name="centerData">
            <c:set var="secAmount" value="${phiz:getSecurityAmount(securityAccount)}"/>
            <c:set var="amountinfo" value="${phiz:formatAmount(secAmount)}"/>
            <c:if test="${secAmount.decimal < 0}">
                <c:set var="amountinfo">
                    &minus;${fn:substring(amountinfo, 1, -1)}
                </c:set>
            </c:if>
            <c:choose>
                <c:when test="${detailsPage}">
                    <span class="detailAmount">${amountinfo}</span>
                </c:when>
                <c:otherwise>
                    <span class="overallAmount">${amountinfo}</span>
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</div>

