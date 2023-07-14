<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/editPortfolio" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="portfolio" value="${form.portfolio}"/>
    <c:set var="riskProfile" value="${form.riskProfile}"/>

    <tiles:insert definition="webModulePagePfp">
        <tiles:put name="title">
            <bean:message bundle="pfpBundle" key="page.title"/>
        </tiles:put>
        <tiles:put name="description">
            <bean:message bundle="pfpBundle" key="editPortfolio.description"/>
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
                <tiles:put name="action" value="/private/pfp/editPortfolioList.do?id=${form.id}"/>
            </tiles:insert>

            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name">&laquo;${portfolio.type.description}&raquo;</tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data">
            <div class="pfpBlocks">
                <div id="paymentStripe">
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.targets" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.riskProfile" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.portfolio" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="current" value="true"/>
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

                <div id="portfolioStart" class="pfpBlock">
                    <fieldset>
                        <legend>Портфель &laquo;${portfolio.type.description}&raquo;</legend>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title"><bean:message key="label.${portfolio.type}.sumName" bundle="pfpBundle"/>:</tiles:put>
                            <tiles:put name="data">
                                <span class="bold float">${phiz:formatAmount(portfolio.startAmount)}</span>
                                <c:if test="${portfolio.isEmpty}">
                                    <script type="text/javascript">
                                        function addChangeStartAmountResult(data)
                                        {
                                            data = trim(data);
                                            //если вернулась пустая строка, то вероятнее всего произошл тайм аут сессии, перезагружаем страницу
                                            if (data == '')
                                                reload();
                                            //если в ответе не нашли changeStartAmountSuccessful, то сумма для формирования портфеля не изменилась. Вытаскиваем сообщение об ошибке.
                                            else if(data.search("changeStartAmountSuccessful") == -1)
                                            {
                                                addErrorMessage(data);
                                            }
                                            else
                                            {
                                                win.close('editStartAmountWindow');
                                                reload();
                                            }
                                        }
                                    </script>

                                    <span class="editPortfolio">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.edit"/>
                                            <tiles:put name="commandHelpKey" value="button.edit.help"/>
                                            <tiles:put name="bundle" value="pfpBundle"/>
                                            <tiles:put name="viewType" value="blueGrayLinkDotted"/>
                                            <tiles:put name="onclick" value="openChangeStartAmountWindow()"/>
                                        </tiles:insert>
                                    </span>
                                </c:if>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>

                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Доходность портфеля:</tiles:put>
                            <tiles:put name="data">
                                <c:set var="portfolioIncome" value="${portfolio.income}"/>
                                <div class="float"><span class="bold">${portfolioIncome}% годовых</span>&nbsp;</div>
                                <%@ include file="floatPortfolioIncomeHint.jsp"%>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>

                        <c:if test="${not empty portfolio.wantedIncome}">
                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="title">Целевая доходность:</tiles:put>
                                <tiles:put name="data">
                                    <div class="float"><span class="bold">${portfolio.wantedIncome}% годовых</span>&nbsp;</div>
                                </tiles:put>
                                <tiles:put name="clazz" value="pfpFormRow"/>
                            </tiles:insert>
                        </c:if>


                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">Неиспользованные средства:</tiles:put>
                            <tiles:put name="data">
                                <div class="float"> <span class="bold">${phiz:formatAmount(portfolio.freeAmount)}</span>  </div>
                                <tiles:insert definition="floatMessage" flush="false">
                                    <tiles:put name="id" value="floatMessage"/>
                                    <tiles:put name="text">
                                        Сумма свободных средств, которые у вас остались для распределения по продуктам.
                                    </tiles:put>
                                </tiles:insert>
                            </tiles:put>
                            <tiles:put name="clazz" value="pfpFormRow"/>
                        </tiles:insert>

                    </fieldset>
                </div>

                <c:set var="productSumMap" value="${form.productDistribution}"/>
                <div id="portfolioResourses" class="pfpBlock">
                    <fieldset>
                        <legend>Распределение средств в портфеле</legend>
                        <div class="pfpTable">
                            <table class="pfpTableContainer" cellpadding="0" cellspacing="0">
                                <tr class="tblInfHeader">
                                    <th>&nbsp;</th>
                                    <th class="alignRight">Размещенная сумма</th>
                                    <th class="alignRight">Рекомендуемая сумма</th>
                                </tr>
                                <c:if test="${not empty productSumMap[form.insuranceType]}">
                                    <tr>
                                        <td class="kind">
                                            <bean:message key="label.${portfolio.type}.insuranceSumName" bundle="pfpBundle"/>
                                            <c:if test="${portfolio.type == 'QUARTERLY_INVEST'}">
                                                <a class="imgHintBlock save-template-hint" onclick="openFAQ('/PhizIC/faq.do#g03')" title="Часто задаваемые вопросы"></a>
                                            </c:if>
                                        </td>
                                        <td class="alignRight sum">
                                            ${phiz:formatAmount(productSumMap[form.insuranceType])}
                                        </td>
                                        <td class="alignRight reccomendSum">&nbsp;</td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty productSumMap[form.pensionType]}">
                                    <tr>
                                        <td class="kind">
                                            Сумма для оплаты пенсионных взносов
                                            <c:if test="${portfolio.type == 'QUARTERLY_INVEST'}">
                                                <a class="imgHintBlock save-template-hint" onclick="openFAQ('/PhizIC/faq.do#g03')" title="Часто задаваемые вопросы"></a>
                                            </c:if>
                                        </td>
                                        <td class="alignRight sum">
                                            ${phiz:formatAmount(productSumMap[form.pensionType])}
                                        </td>
                                        <td class="alignRight reccomendSum">&nbsp;</td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty productSumMap[form.accountType]}">
                                    <tr>
                                        <td class="kind">Вклады</td>
                                        <td class="alignRight sum">
                                            ${phiz:formatAmount(productSumMap[form.accountType])}
                                        </td>
                                        <td class="alignRight reccomendSum">
                                            <c:set var="recommendedAccountAmount" value="${pfptags:getRecommendedAmount(riskProfile, portfolio, 'account')}"/>
                                            ${phiz:formatAmount(recommendedAccountAmount)}
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty productSumMap[form.fundType]}">
                                    <tr>
                                        <td class="kind">ПИФы</td>
                                        <td class="alignRight sum">
                                            ${phiz:formatAmount(productSumMap[form.fundType])}
                                        </td>
                                        <td class="alignRight reccomendSum">
                                            <c:set var="recommendedFundAmount" value="${pfptags:getRecommendedAmount(riskProfile, portfolio, 'fund')}"/>
                                            ${phiz:formatAmount(recommendedFundAmount)}
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty productSumMap[form.imaType]}">
                                    <tr>
                                        <td class="kind">ОМС</td>
                                        <td class="alignRight sum">
                                            ${phiz:formatAmount(productSumMap[form.imaType])}
                                        </td>
                                        <td class="alignRight reccomendSum">
                                            <c:set var="recommendedImaAmount" value="${pfptags:getRecommendedAmount(riskProfile, portfolio, 'IMA')}"/>
                                            ${phiz:formatAmount(recommendedImaAmount)}
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty productSumMap[form.trustManagingType]}">
                                    <tr>
                                        <td class="kind">Доверительное управление</td>
                                        <td class="alignRight sum">
                                            ${phiz:formatAmount(productSumMap[form.trustManagingType])}
                                        </td>
                                        <td class="alignRight reccomendSum">
                                            <c:set var="recommendedImaAmount" value="${pfptags:getRecommendedAmount(riskProfile, portfolio, 'trustManaging')}"/>
                                            ${phiz:formatAmount(recommendedImaAmount)}
                                        </td>
                                    </tr>
                                </c:if>
                                <tr class="summary">
                                    <td class="kind">Итого:</td>
                                    <td class="alignRight sum">${phiz:formatAmount(portfolio.productSum)}</td>
                                    <td>&nbsp;</td>
                                </tr>
                            </table>
                        </div>
                    </fieldset>
                </div>

                <div class="categories">
                    <fieldset>
                        <legend>Каталог продуктов</legend>
                        <div class="doubleColumnPFP">
                            <div class="categoryPayments">
                                <c:if test="${pfptags:isProductTypeAvailableAdd(portfolio, riskProfile, 'INSURANCE') or pfptags:isProductTypeAvailableAdd(portfolio, riskProfile, 'PENSION')}">
                                    <tiles:insert definition="categoryTemplatePFP" flush="false">
                                        <tiles:put name="title">Банковское страхование</tiles:put>
                                        <tiles:put name="imagePath">${globalImagePath}/pfp/icon_INSURANCE.png</tiles:put>
                                        <tiles:put name="url">
                                            <c:url value="/editPortfolio/chooseProduct/insurance.do">
                                                <c:param name="dictionaryProductType" value="INSURANCE"/>
                                                <c:param name="id" value="${form.id}"/>
                                                <c:param name="portfolioId" value="${portfolio.id}"/>
                                                <c:param name="portfolioType" value="${portfolio.type}"/>
                                            </c:url>
                                        </tiles:put>
                                        <tiles:put name="hint">
                                            <bean:message key="label.insurance.hint" bundle="pfpBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${pfptags:isProductTypeAvailableAdd(portfolio, riskProfile, 'COMPLEX_INSURANCE')}">
                                    <tiles:insert definition="categoryTemplatePFP" flush="false">
                                        <tiles:put name="title">Комплексные страховые продукты</tiles:put>
                                        <tiles:put name="imagePath">${globalImagePath}/pfp/icon_COMPLEX_INSURANCE.png</tiles:put>
                                        <tiles:put name="url">
                                            <c:url value="/editPortfolio/chooseProduct.do">
                                                <c:param name="dictionaryProductType" value="COMPLEX_INSURANCE"/>
                                                <c:param name="id" value="${form.id}"/>
                                                <c:param name="portfolioId" value="${portfolio.id}"/>
                                                <c:param name="portfolioType" value="${portfolio.type}"/>
                                            </c:url>
                                        </tiles:put>
                                        <tiles:put name="hint">
                                            <bean:message key="label.complex_insurance.hint" bundle="pfpBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${pfptags:isProductTypeAvailableAdd(portfolio, riskProfile, 'COMPLEX_INVESTMENT')}">
                                    <tiles:insert definition="categoryTemplatePFP" flush="false">
                                        <tiles:put name="title">Комплексные инвестиционные продукты</tiles:put>
                                        <tiles:put name="imagePath">${globalImagePath}/pfp/icon_COMPLEX_INVESTMENT.png</tiles:put>
                                        <tiles:put name="url">
                                            <c:url value="/editPortfolio/chooseProduct.do">
                                                <c:param name="dictionaryProductType" value="COMPLEX_INVESTMENT"/>
                                                <c:param name="id" value="${form.id}"/>
                                                <c:param name="portfolioId" value="${portfolio.id}"/>
                                                <c:param name="portfolioType" value="${portfolio.type}"/>
                                            </c:url>
                                        </tiles:put>
                                        <tiles:put name="hint">
                                            <bean:message key="label.complex_investment.hint" bundle="pfpBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${pfptags:isProductTypeAvailableAdd(portfolio, riskProfile, 'ACCOUNT')}">
                                    <tiles:insert definition="categoryTemplatePFP" flush="false">
                                        <tiles:put name="title">Вклады</tiles:put>
                                        <tiles:put name="imagePath">${globalImagePath}/pfp/icon_ACCOUNT.png</tiles:put>
                                        <tiles:put name="url">
                                            <c:url value="/editPortfolio/chooseProduct.do">
                                                <c:param name="dictionaryProductType" value="ACCOUNT"/>
                                                <c:param name="id" value="${form.id}"/>
                                                <c:param name="portfolioId" value="${portfolio.id}"/>
                                                <c:param name="portfolioType" value="${portfolio.type}"/>
                                            </c:url>
                                        </tiles:put>
                                       <tiles:put name="hint">
                                           <bean:message key="label.account.hint" bundle="pfpBundle"/>
                                       </tiles:put>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${pfptags:isProductTypeAvailableAdd(portfolio, riskProfile, 'FUND')}">
                                    <tiles:insert definition="categoryTemplatePFP" flush="false">
                                        <tiles:put name="title">Паевые инвестиционные фонды</tiles:put>
                                        <tiles:put name="imagePath">${globalImagePath}/pfp/icon_FUND.png</tiles:put>
                                        <tiles:put name="url">
                                            <c:url value="/editPortfolio/chooseProduct.do">
                                                <c:param name="dictionaryProductType" value="FUND"/>
                                                <c:param name="id" value="${form.id}"/>
                                                <c:param name="portfolioId" value="${portfolio.id}"/>
                                                <c:param name="portfolioType" value="${portfolio.type}"/>
                                            </c:url>
                                       </tiles:put>
                                       <tiles:put name="hint">
                                           <bean:message key="label.fund.hint" bundle="pfpBundle"/>
                                       </tiles:put>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${pfptags:isProductTypeAvailableAdd(portfolio, riskProfile, 'IMA')}">
                                    <tiles:insert definition="categoryTemplatePFP" flush="false">
                                        <tiles:put name="title">Обезличенные металлические счета</tiles:put>
                                        <tiles:put name="imagePath">${globalImagePath}/pfp/icon_IMA.png</tiles:put>
                                        <tiles:put name="url">
                                            <c:url value="/editPortfolio/chooseProduct.do">
                                                <c:param name="dictionaryProductType" value="IMA"/>
                                                <c:param name="id" value="${form.id}"/>
                                                <c:param name="portfolioId" value="${portfolio.id}"/>
                                                <c:param name="portfolioType" value="${portfolio.type}"/>
                                            </c:url>
                                        </tiles:put>
                                        <tiles:put name="hint">
                                            <bean:message key="label.ima.hint" bundle="pfpBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${pfptags:isProductTypeAvailableAdd(portfolio, riskProfile, 'TRUST_MANAGING')}">
                                    <tiles:insert definition="categoryTemplatePFP" flush="false">
                                        <tiles:put name="title">Доверительное управление</tiles:put>
                                        <tiles:put name="imagePath">${globalImagePath}/pfp/icon_TRUST_MANAGING.png</tiles:put>
                                        <tiles:put name="url">
                                            <c:url value="/editPortfolio/chooseProduct.do">
                                                <c:param name="dictionaryProductType" value="TRUST_MANAGING"/>
                                                <c:param name="id" value="${form.id}"/>
                                                <c:param name="portfolioId" value="${portfolio.id}"/>
                                                <c:param name="portfolioType" value="${portfolio.type}"/>
                                            </c:url>
                                        </tiles:put>
                                        <tiles:put name="hint">
                                            <bean:message key="label.trust_managment.hint" bundle="pfpBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </fieldset>
                </div>

                <div class="portfolioProductsList">
                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="confirmRemoveProduct"/>
                        <tiles:put name="styleClass" value="confirmWidow"/>
                        <tiles:put name="data">
                            <tiles:insert page="/WEB-INF/jsp/common/pfp/windows/confirmRemoveProduct.jsp" flush="false"/>
                        </tiles:put>
                    </tiles:insert>
                    <html:hidden property="productId" styleId="editingProductId"/>
                    <c:if test="${not empty portfolio.currentProductList}">
                        <fieldset>
                            <legend>
                                <div class="pfpSubtitle">
                                    <bean:message key="label.my_product.title" bundle="pfpBundle"/>
                                </div>
                            </legend>

                            <table class="abstractContentTable" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td colspan="2" class="abstractItem tdTitleItem tdTitleProduct">
                                        Продукт
                                    </td>
                                    <td class="abstractItem tdTitleItem">
                                        Доходность
                                    </td>
                                    <td class="abstractItem tdTitleItem alignRight">
                                        Сумма
                                    </td>
                                    <td class="tdTitleItem">&nbsp;</td>
                                </tr>

                                <c:set var="currProductType" value=""/>
                                <c:set var="lastProductType" value=""/>
                                <c:forEach var="portfolioProduct" items="${portfolio.currentProductList}">
                                    <c:if test="${pfptags:isProductTypeSupportedForPortfolio(portfolio, portfolioProduct.productType)}">
                                        <c:choose>
                                            <c:when test="${portfolioProduct.productType == 'COMPLEX_INSURANCE' ||
                                                            portfolioProduct.productType == 'COMPLEX_INVESTMENT_FUND' ||
                                                            portfolioProduct.productType == 'COMPLEX_INVESTMENT_FUND_IMA'}">
                                                <c:set var="currProductType" value="COMPLEX"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="currProductType">${portfolioProduct.productType}</c:set>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:if test="${currProductType != lastProductType}">
                                            <tr>
                                                <td colspan="5" class="grayTitleProducts">
                                                    <bean:message key="label.groupProductsTitle.${currProductType}" bundle="pfpBundle"/>
                                                </td>
                                            </tr>
                                            <c:set var="lastProductType" value="${currProductType}"/>
                                        </c:if>

                                        <c:set var="productListSize" value="${phiz:size(portfolioProduct.baseProductList)}"/>
                                        <tr>
                                            <td class="portfolioProductImg" rowspan="${productListSize + 1}">
                                                <c:set var="imgSrc" value=""/>
                                                <c:if test="${not empty portfolioProduct.imageId}">
                                                    <c:set var="imageData" value="${phiz:getImageById(portfolioProduct.imageId)}"/>
                                                    <c:set var="imgSrc" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                </c:if>
                                                <c:if test="${empty imgSrc}">
                                                    <c:set var="imgSrc" value="${globalImagePath}/pfp/icon_${portfolioProduct.productType}.png"/>
                                                </c:if>
                                                <img border="0" width="32px" height="32px" src="${imgSrc}">
                                            </td>
                                            <td colspan="4">
                                                <c:set var="canEditCurrentProduct" value="${pfptags:canEditPfpProduct(riskProfile, portfolioProduct.dictionaryProductId, portfolioProduct.productType)}"/>
                                                <div class="editPortfolio">
                                                    <c:choose>
                                                        <c:when test="${canEditCurrentProduct}">
                                                            <html:link title="Редактировать" styleClass="editIconButton" action="/addProduct.do?id=${form.id}&portfolioId=${portfolio.id}&portfolioType=${portfolio.type}&editProductId=${portfolioProduct.id}&dictionaryProductType=${portfolioProduct.productType}"></html:link>
                                                        </c:when>
                                                        <c:otherwise>
                                                                <a title="Редактировать" class="editIconButton" onclick="win.open('cantEditProductWindow'); return false;"></a>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <c:set var="productId" value="${portfolioProduct.id}" scope="request"/>
                                                    <img class="iconButton" title="Удалить" alt="X" src="${globalImagePath}/icon_delete.gif" onclick="setRemovedProduct('${productId}');win.open('confirmRemoveProduct');return false;"/>
                                                </div>

                                                <div class="portfolioProductTitle">
                                                    <c:choose>
                                                        <c:when test="${canEditCurrentProduct}">
                                                            <html:link action="/addProduct.do?id=${form.id}&portfolioId=${portfolio.id}&portfolioType=${portfolio.type}&editProductId=${portfolioProduct.id}&dictionaryProductType=${portfolioProduct.productType}">
                                                                <c:out value="${portfolioProduct.name}"/>
                                                            </html:link>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="#" onclick="win.open('cantEditProductWindow'); return false;">
                                                                <c:out value="${portfolioProduct.name}"/>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </td>
                                        </tr>

                                        <c:forEach var="baseProduct" items="${portfolioProduct.baseProductList}" varStatus="i">
                                            <tr <c:if test="${i.count == productListSize}">class="productItem"</c:if>>
                                                <td class="abstractItem portfolioProductName">
                                                    <div class="word-wrap"><c:out value="${baseProduct.productName}"/></div>
                                                </td>
                                                <td class="abstractValue">
                                                    ${baseProduct.income}% годовых
                                                </td>
                                                <td class="abstractValue alignRight">
                                                    ${phiz:formatAmount(baseProduct.amount)}
                                                </td>
                                                <td class="abstractValueButton">
                                                    <c:if test="${form.clientOwner}">
                                                        <c:set var="actionUrl" value="${pfptags:calculateActionURLToPhizIC(pageContext, '/private/payments/payment')}"/>
                                                        <c:set var="pfpUrlParams" value="pfpId=${form.id}&pfpPortfolioId=${portfolio.id}"/>
                                                        <c:choose>
                                                            <c:when test="${baseProduct.productType == 'ACCOUNT'}">
                                                                <c:set var="deposit" value="${pfptags:getDepositByAccountPfpId(baseProduct.dictionaryProductId)}"/>
                                                                <c:if test="${not empty deposit}">
                                                                    <div class="goToOpenClaim">
                                                                        <c:set var="urlParams" value="?form=AccountOpeningClaim&depositType=${deposit.productId}&depositId=${deposit.id}&fromResource=&${pfpUrlParams}"/>
                                                                        <tiles:insert definition="clientButton" flush="false">
                                                                            <tiles:put name="commandTextKey" value="button.goToOpenClaim"/>
                                                                            <tiles:put name="commandHelpKey" value="button.goToOpenClaim.help"/>
                                                                            <tiles:put name="bundle" value="pfpBundle"/>
                                                                            <tiles:put name="viewType" value="buttonRoundGrayLight"/>
                                                                            <tiles:put name="onclick" value="window.location='${actionUrl}${urlParams}'"/>
                                                                        </tiles:insert>
                                                                    </div>
                                                                </c:if>
                                                            </c:when>
                                                            <c:when test="${baseProduct.productType == 'IMA' }">
                                                                <c:set var="ima" value="${pfptags:getIMAProductId(baseProduct.dictionaryProductId)}"/>
                                                                <c:if test="${not empty ima}">
                                                                    <div class="goToOpenClaim">
                                                                        <c:set var="urlParams" value="?imaId=${ima.id}&form=IMAOpeningClaim&fromResource=&${pfpUrlParams}"/>
                                                                        <tiles:insert definition="clientButton" flush="false">
                                                                            <tiles:put name="commandTextKey" value="button.goToOpenClaim"/>
                                                                            <tiles:put name="commandHelpKey" value="button.goToOpenClaim.help"/>
                                                                            <tiles:put name="bundle" value="pfpBundle"/>
                                                                            <tiles:put name="viewType" value="buttonRoundGrayLight"/>
                                                                            <tiles:put name="onclick" value="window.location='${actionUrl}${urlParams}'"/>
                                                                        </tiles:insert>
                                                                    </div>
                                                                </c:if>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:if>
                                                    <c:if test="${!form.clientOwner}">&nbsp;</c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                            </table>

                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id" value="cantEditProductWindow"/>
                                <tiles:put name="data">
                                    <jsp:include page="/WEB-INF/jsp/common/pfp/windows/cantEditProductWindow.jsp"/>
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>
                    </c:if>
                </div>
            </div>

            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="editStartAmountWindow"/>
                <tiles:put name="data">
                    <jsp:include page="/WEB-INF/jsp/common/pfp/windows/editStartAmountWindow.jsp"/>
                </tiles:put>
            </tiles:insert>

            <div class="pfpButtonsBlock">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="pfpBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save.portfolio"/>
                        <tiles:put name="commandHelpKey" value="button.save.portfolio.help"/>
                        <tiles:put name="validationFunction" value=""/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="bundle" value="pfpBundle"/>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>
        </tiles:put>
    </tiles:insert>

    <script type="text/javascript">
        function setRemovedProduct(productId)
        {
            $("#editingProductId").val(productId);
            return true;
        }
    </script>

</html:form>