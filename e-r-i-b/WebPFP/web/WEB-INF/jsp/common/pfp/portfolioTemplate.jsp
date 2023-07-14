<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--
Параметры шаблона:
portfolio - портфель клиента
--%>

<c:set var="portfolioType" value="${portfolio.type}"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<c:url var="editPortfolioUrl" value="/editPortfolio.do" scope="request">
    <c:param name="id" value="${form.id}"/>
</c:url>

<div class="portfolioProduct">
    <tiles:insert definition="productTemplate" flush="false">
        <tiles:put name="img" value="${globalImagePath}/pfp/${portfolio.type}_icon.png"/>
        <tiles:put name="alt" value="Портфель"/>
        <tiles:put name="src" value="${editPortfolioUrl}&portfolioId=${portfolio.id}"/>
        <tiles:put name="title">${portfolioType.description}</tiles:put>
        <tiles:put name="productViewBacklight" value="false"/>
        <tiles:put name="titleClass" value="mainProductTitle"/>
        <tiles:put name="centerData">
            <span class="overallAmount nowrap">${phiz:formatAmount(portfolio.startAmount)}</span>
        </tiles:put>
        <tiles:put name="rightData">
            <div class="editPortfolio">
                <div class="lightGrayButton" onclick="goToEdit(${portfolio.id});">
                    <div class="lightGrayButtonLeft"></div>
                    <div class="lightGrayButtonCenter">
                        <c:choose>
                            <c:when test="${portfolio.isEmpty}">
                                <bean:message bundle="pfpBundle" key="button.edit.portfolio.new"/>
                            </c:when>
                            <c:otherwise>
                                <bean:message bundle="pfpBundle" key="button.edit.portfolio"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="lightGrayButtonRight"></div>
                    <div class="clear"></div>
                </div>
            </div>
        </tiles:put>

        <tiles:put name="leftData">
            <table class="productDetail">
                <tr>
                    <td class="productDetailField">
                        <table>
                            <tr>
                                <td>
                                    Доходность портфеля:
                                </td>
                                <td>
                                    <c:set var="portfolioIncome" value="${portfolio.income}"/>
                                    ${portfolio.income}% годовых
                                </td>
                                <td>
                                    <%@ include file="floatPortfolioIncomeHint.jsp" %>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td class="portfolioSumName" rowspan="2">
                        <bean:message key="label.${portfolio.type}.sumName" bundle="pfpBundle"/>
                    </td>
                </tr>
                <tr>
                    <td class="productDetailField">
                        Неиспользованные средства:&nbsp;${phiz:formatAmount(portfolio.freeAmount)}
                    </td>
                </tr>
            </table>
        </tiles:put>

        <tiles:put name="bottomData">
            <c:set var="productCount" value="0"/>
            <div class="portfolioAbstractList" style="display: none">
                <c:forEach var="product" items="${portfolio.currentProductList}">
                    <c:if test="${pfptags:isProductTypeSupportedForPortfolio(portfolio, product.productType)}">
                        <c:set var="productCount" value="${productCount+1}"/>
                        <div class="portfolioAbstract">
                            <div class="abstractTitle"><c:out value="${product.name}"/></div>
                            <div class="abstractContent">
                                <table class="abstractContentTable" cellpadding="0" cellspacing="0">
                                    <c:forEach var="baseProduct" items="${product.baseProductList}">
                                        <tr>
                                            <td class="abstractItem">
                                                <c:out value="${baseProduct.productName}"/>
                                            </td>
                                            <td class="abstractValue alignRight">
                                                ${baseProduct.income}% годовых
                                            </td>
                                            <td class="abstractValue alignRight">
                                                ${phiz:formatAmount(baseProduct.amount)}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <c:if test="${productCount>0}">
                <c:set var="showText" value="Показать распределение средств"/>
                <c:set var="hideText" value="Скрыть распределение средств"/>
                <c:set var="classShow" value="text-gray hideable-element"/>
                <a class="${classShow}" onclick="showHideAbstract(this, 'portfolioAbstractList'); cancelBubbling(event); return false;" altText="${hideText}">${showText}</a>
            </c:if>
        </tiles:put>
    </tiles:insert>
</div>