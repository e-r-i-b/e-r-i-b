<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<html:form action="/addProduct" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="product" value="${form.product}" scope="request"/>
    <c:set var="portfolio" value="${form.portfolio}" scope="request"/>
    <c:set var="profile" value="${form.profile}" scope="request"/>
<tiles:insert definition="webModulePagePfp">
    <tiles:put name="title">
        <bean:message bundle="pfpBundle" key="page.title"/>
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
                <tiles:put name="action" value="/private/pfp/editPortfolio.do?id=${form.id}&portfolioId=${form.portfolioId}"/>
            </tiles:insert>
            <c:if test="${form.dictionaryProductType == 'COMPLEX_INVESTMENT_FUND' || form.dictionaryProductType == 'COMPLEX_INVESTMENT_FUND_IMA'}">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name"><bean:message key="label.productTitle.COMPLEX_INVESTMENT" bundle="pfpBundle"/> </tiles:put>
                    <tiles:put name="action" value="/private/pfp/editPortfolio/chooseProduct.do?dictionaryProductType=COMPLEX_INVESTMENT&id=${form.id}&portfolioId=${form.portfolioId}"/>
                </tiles:insert>
            </c:if>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message key="label.productTitle.${form.dictionaryProductType}" bundle="pfpBundle"/></tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
    <tiles:put name="data">
    <div class="pfpBlocks ">
        <tiles:insert definition="formHeader" flush="false">
            <c:set var="insuranceImageId" value="${product.imageId}"/>
            <c:if test="${form.dictionaryProductType=='INSURANCE'}">
                <c:set var="insuranceImageId" value="${pfptags:getInsuranceProductImageId(form.productId)}"/>
            </c:if>
            <c:choose>
                <c:when test="${insuranceImageId != null}">
                    <c:set var="imageData" value="${phiz:getImageById(insuranceImageId)}"/>
                    <tiles:put name="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="image" value="${globalImagePath}/pfp/icon_${form.dictionaryProductType}.png"/>
                </c:otherwise>
            </c:choose>
            <tiles:put name="width" value="64px"/>
            <tiles:put name="height" value="64px"/>
            <tiles:put name="description">
                <bean:message key="label.addProduct.${form.dictionaryProductType}" bundle="pfpBundle"/>
                <br/>
                <span class="bold">
                <c:if test="${form.dictionaryProductType == 'ACCOUNT'}">
                    Рекомендуемая сумма Вклада
                    <c:if test="${product.advisableSum == 'monthlyExpends'}">
                        ${phiz:formatAmount(profile.outcomeMoney)}
                    </c:if>
                    <c:if test="${product.advisableSum == 'advisableSumToProduct'}">
                        <c:set var="recommendedAmount" value="${pfptags:getCurrentRecommendedAmount(profile.personRiskProfile, portfolio, 'account')}"/>
                        ${phiz:formatAmount(recommendedAmount)}
                    </c:if>
                </c:if>
                <c:if test="${form.dictionaryProductType == 'TRUST_MANAGING'}">
                    <c:set var="recommendedAmount" value="${pfptags:getCurrentRecommendedAmount(profile.personRiskProfile, portfolio, 'trustManaging')}"/>
                    Рекомендуемая сумма ${phiz:formatAmount(recommendedAmount)}
                </c:if>
                <c:if test="${form.dictionaryProductType == 'FUND'}">
                    <c:set var="recommendedAmount" value="${pfptags:getCurrentRecommendedAmount(profile.personRiskProfile, portfolio, 'fund')}"/>
                    Рекомендуемая сумма ${phiz:formatAmount(recommendedAmount)}
                </c:if>
                <c:if test="${form.dictionaryProductType == 'IMA'}">
                    <c:set var="recommendedAmount" value="${pfptags:getCurrentRecommendedAmount(profile.personRiskProfile, portfolio, 'IMA')}"/>
                    Рекомендуемая сумма ${phiz:formatAmount(recommendedAmount)}
                </c:if>
                <c:if test="${form.dictionaryProductType == 'COMPLEX_INSURANCE'}">
                    <bean:message bundle="pfpBundle" key="label.addProduct.minAmount"/> <fmt:formatNumber value="${product.minSum}" pattern=",##0.00"/> <bean:message bundle="pfpBundle" key="currency.rub"/>
                </c:if>
                <c:if test="${form.dictionaryProductType == 'COMPLEX_INVESTMENT_FUND' or form.dictionaryProductType == 'COMPLEX_INVESTMENT_FUND_IMA'}">
                    <c:set var="portfolioType" value="${form.portfolio.type}"/>
                    <c:set var="productPortfolioParameters" value="${product.parameters[portfolioType]}"/>
                    <c:set var="productMinSum" value="${productPortfolioParameters.minSum}"/>
                    <bean:message bundle="pfpBundle" key="label.addProduct.minAmount"/> <fmt:formatNumber value="${productMinSum}" pattern=",##0.00"/> <bean:message bundle="pfpBundle" key="currency.rub"/>
                </c:if>
                </span>
            </tiles:put>
        </tiles:insert>
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
    </div>

    <div class="pfpBlocks addProducts" onkeydown="onEnterKey(event);">
        <fieldset class="dashedBorder fullWidthPFP">
            <c:choose>
                <c:when test="${form.dictionaryProductType == 'INSURANCE'}">
                    <tiles:insert page="/WEB-INF/jsp/common/pfp/addProduct/insuranceProductFields.jsp" flush="false"/>
                </c:when>
                <c:when test="${form.dictionaryProductType == 'PENSION'}">
                    <tiles:insert page="/WEB-INF/jsp/common/pfp/addProduct/pensionProductFields.jsp" flush="false"/>
                </c:when>
                <c:when test="${form.dictionaryProductType == 'COMPLEX_INSURANCE'}">
                    <tiles:insert page="/WEB-INF/jsp/common/pfp/addProduct/complexInsuranceProductFields.jsp" flush="false"/>
                </c:when>
                <c:when test="${form.dictionaryProductType == 'COMPLEX_INVESTMENT_FUND'}">
                    <tiles:insert page="/WEB-INF/jsp/common/pfp/addProduct/complexInvestmentFundFields.jsp" flush="false"/>
                </c:when>
                <c:when test="${form.dictionaryProductType == 'COMPLEX_INVESTMENT_FUND_IMA'}">
                    <tiles:insert page="/WEB-INF/jsp/common/pfp/addProduct/complexInvestmentFundImaFields.jsp" flush="false"/>
                </c:when>
                <c:when test="${form.dictionaryProductType == 'ACCOUNT'}">
                    <tiles:insert page="/WEB-INF/jsp/common/pfp/addProduct/accountProductFields.jsp" flush="false"/>
                </c:when>
                <c:when test="${form.dictionaryProductType == 'FUND'}">
                    <tiles:insert page="/WEB-INF/jsp/common/pfp/addProduct/fundProductFields.jsp" flush="false"/>
                </c:when>
                <c:when test="${form.dictionaryProductType == 'IMA'}">
                    <tiles:insert page="/WEB-INF/jsp/common/pfp/addProduct/imaProductFields.jsp" flush="false"/>
                </c:when>
                <c:when test="${form.dictionaryProductType == 'TRUST_MANAGING'}">
                    <tiles:insert page="/WEB-INF/jsp/common/pfp/addProduct/trustManagingProductFields.jsp" flush="false"/>
                </c:when>
                <c:otherwise>Не удалось определить тип добавляемого продукта</c:otherwise>
            </c:choose>
        </fieldset>

        <fieldset class="fullWidthPFP">
            <tiles:insert page="/WEB-INF/jsp/common/pfp/addProduct/productIncome.jsp" flush="false"/>
        </fieldset>

        <div class="pfpButtonsBlock">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="viewType" value="simpleLink"/>
                <tiles:put name="bundle" value="pfpBundle"/>
                <tiles:put name="onclick" value="goBackToPortfolio();"/>
            </tiles:insert>

            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.next"/>
                <tiles:put name="commandHelpKey" value="button.next.help"/>
                <tiles:put name="validationFunction" value=""/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="bundle" value="pfpBundle"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.back2"/>
            <tiles:put name="commandHelpKey" value="button.back2.help"/>
            <tiles:put name="bundle" value="pfpBundle"/>
            <tiles:put name="viewType" value="blueGrayLink"/>
            <tiles:put name="onclick" value="goBackToChooseProduct();"/>
        </tiles:insert>
    </div>
        
    <c:url var="editPortfolioUrl" value="/editPortfolio.do">
        <c:param name="id" value="${form.id}"/>
        <c:param name="portfolioId" value="${form.portfolioId}"/>
    </c:url>

    <c:choose>
        <c:when test="${form.dictionaryProductType == 'COMPLEX_INVESTMENT_FUND' or form.dictionaryProductType == 'COMPLEX_INVESTMENT_FUND_IMA'}">
            <c:url var="chooseProductUrl" value="/editPortfolio/chooseProduct.do">
                <c:param name="id" value="${form.id}"/>
                <c:param name="portfolioId" value="${form.portfolioId}"/>
                <c:param name="portfolioType" value="${form.portfolioType}"/>
                <c:param name="dictionaryProductType" value="COMPLEX_INVESTMENT"/>
            </c:url>
        </c:when>
        <c:when test="${form.dictionaryProductType == 'INSURANCE' or form.dictionaryProductType == 'PENSION'}">
            <c:url var="chooseProductUrl" value="/editPortfolio/chooseProduct/insurance.do">
                <c:param name="id" value="${form.id}"/>
                <c:param name="portfolioId" value="${form.portfolioId}"/>
                <c:param name="portfolioType" value="${form.portfolioType}"/>
                <c:param name="dictionaryProductType" value="INSURANCE"/>
            </c:url>
        </c:when>
        <c:otherwise>
            <c:url var="chooseProductUrl" value="/editPortfolio/chooseProduct.do">
                <c:param name="id" value="${form.id}"/>
                <c:param name="portfolioId" value="${form.portfolioId}"/>
                <c:param name="portfolioType" value="${form.portfolioType}"/>
                <c:param name="dictionaryProductType" value="${form.dictionaryProductType}"/>
            </c:url>
        </c:otherwise>
    </c:choose>

    <%-- TODO убрать сделать нормальный переход через параметр action в clientButton --%>
    <%-- TODO в текущий момент для модульности такой переход(через экшен) не работает --%>
    <script type="text/javascript">
        function goBackToChooseProduct()
        {
            loadNewAction('', '');
            window.location = "${chooseProductUrl}";
        }
        function goBackToPortfolio()
        {
            loadNewAction('', '');
            window.location = "${editPortfolioUrl}";
        }

        var MAX_SELECT_WIDTH = 400;
        $(document).ready(function(){
            $('.select').each(function(){setMaxSelectWidth(this, MAX_SELECT_WIDTH);});
        });
    </script>
   </tiles:put>
</tiles:insert>
</html:form>