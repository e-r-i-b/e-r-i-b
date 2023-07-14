<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pfptags" uri="http://rssl.com/pfptags" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="portfolioType" value="${form.portfolio.type}"/>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.product.description"/></tiles:put>
    <tiles:put name="data">
        <span class="bold">${product.description}</span>
    </tiles:put>
</tiles:insert>

<fieldset>
    <legend><bean:message bundle="pfpBundle" key="label.addProduct.legend.fund"/></legend>
    <c:set var="fundProducts" value="${product.fundProducts}"/>
    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.fundName"/></tiles:put>
        <tiles:put name="data">
            <html:select property="field(fundProductId)" onchange="setFundAmountDescription();setFundIncomeScroll();" styleClass="select">
                <html:options collection="fundProducts" property="id" labelProperty="name"/>
            </html:select>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.income"/></tiles:put>
        <tiles:put name="data">
            <c:set var="fundProduct" value="${product.fundProducts[0]}"/>
            <c:set var="selectedFundProductId"><bean:write name="org.apache.struts.taglib.html.BEAN" property="field(fundProductId)"/></c:set>
            <c:forEach var="currentFundProduct" items="${product.fundProducts}">
                <c:if test="${currentFundProduct.id == selectedFundProductId}">
                    <c:set var="fundProduct" value="${currentFundProduct}"/>
                </c:if>
            </c:forEach>
            <c:choose>
                <c:when test="${not empty fundProduct.minIncome and not empty fundProduct.maxIncome}">
                    <c:set var="incomeFundScrollStyle" value=""/>
                    <c:set var="incomeSpanStyle" value="displayNone"/>
                </c:when>
                <c:otherwise>
                    <c:set var="incomeFundScrollStyle" value="displayNone"/>
                    <c:set var="incomeSpanStyle" value=""/>
                </c:otherwise>
            </c:choose>

            <div id="fundProductIncomeScroll">
                <tiles:insert definition="scrollTemplate2" flush="false">
                    <tiles:put name="id" value="fundIncome"/>
                    <tiles:put name="minValue" value="${fundProduct.minIncome}"/>
                    <tiles:put name="maxValue" value="${fundProduct.maxIncome}"/>
                    <tiles:put name="unit" value="%"/>
                    <tiles:put name="currValue" value="${fundProduct.defaultIncome}"/>
                    <tiles:put name="fieldName" value="field(fundIncome)"/>
                    <tiles:put name="inputData"> % годовых <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/></tiles:put>
                    <tiles:put name="step" value="0.1"/>
                    <tiles:put name="round" value="1"/>
                    <tiles:put name="callback" value="calculateIncomeAmount();"/>
                </tiles:insert>
            </div>
            <span id="fundProductIncomeSpan" class="bold ${incomeSpanStyle}">
                <c:set var="fundIncome"><bean:write name="fundProduct" property="defaultIncome"/></c:set>
                <span id="fundProductIncomeSpanValue">
                    ${fundIncome}
                </span>
                <bean:message bundle="pfpBundle" key="label.addProduct.income.help"/>
                <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/>
            </span>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="needMark" value="false"/>
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.recommend.amount"/></tiles:put>
        <tiles:put name="data">
             <c:set var="recommendedAmountFund" value="${pfptags:getCurrentRecommendedAmount(profile.personRiskProfile, portfolio, 'fund')}"/>
            <span class="notation">${phiz:formatAmount(recommendedAmountFund)}</span>
        </tiles:put>
        <tiles:put name="clazz" value="recommendateAmount"/>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.amount"/></tiles:put>
        <tiles:put name="data">
            <html:text property="field(fundAmount)" size="10" styleClass="moneyField" onkeyup="calculateIncomeAmount();" maxlength="13"/>&nbsp;
            <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span>
            <span id="fundAmountHelpDescription" class="italic text-gray"></span>
        </tiles:put>
        <tiles:put name="clazz" value="amountHelpMessage"/>
    </tiles:insert>
</fieldset>

<fieldset>
    <legend><bean:message bundle="pfpBundle" key="label.addProduct.legend.account"/></legend>
    <c:set var="account" value="${product.account}"/>
    <c:set var="accountPortfolioParameters" value="${account.parameters[portfolioType]}"/>
    <c:set var="minAccountProductSum" value="${accountPortfolioParameters.minSum}"/>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.productName"/></tiles:put>
        <tiles:put name="data">
            <span class="bold"><bean:write name="product" property="account.name"/></span>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.income"/></tiles:put>
        <tiles:put name="data">
            <c:choose>
                <c:when test="${not empty account.minIncome and not empty account.maxIncome}">
                    <tiles:insert definition="scrollTemplate2" flush="false">
                        <tiles:put name="id" value="accountIncome"/>
                        <tiles:put name="minValue" value="${account.minIncome}"/>
                        <tiles:put name="maxValue" value="${account.maxIncome}"/>
                        <tiles:put name="unit" value="%"/>
                        <tiles:put name="currValue" value="${account.defaultIncome}"/>
                        <tiles:put name="fieldName" value="field(accountIncome)"/>
                        <tiles:put name="inputData"> % годовых <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/></tiles:put>
                        <tiles:put name="step" value="0.1"/>
                        <tiles:put name="round" value="1"/>
                        <tiles:put name="callback" value="calculateIncomeAmount();"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <span class="bold">
                        <c:set var="accountIncome"><bean:write name="account" property="defaultIncome"/></c:set>
                        <html:hidden property="field(accountIncome)" value="${accountIncome}"/>
                        ${accountIncome}<bean:message bundle="pfpBundle" key="label.addProduct.income.help"/>
                        <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/>
                    </span>
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="needMark" value="false"/>
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.recommend.amount"/></tiles:put>
        <tiles:put name="data">
            <c:set var="recommendedAmountAcc" value="${pfptags:getCurrentRecommendedAmount(profile.personRiskProfile, portfolio, 'account')}"/>
            <c:if test="${recommendedAmountFund < recommendedAmountAcc}"><c:set var="recommendedAmountAcc" value="${recommendedAmountFund}"/> </c:if>
            <span class="notation">${phiz:formatAmount(recommendedAmountAcc)}</span>
        </tiles:put>
        <tiles:put name="clazz" value="recommendateAmount"/>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.accountAmount"/></tiles:put>
        <tiles:put name="data">
            <html:text property="field(accountAmount)" size="10" styleClass="moneyField" onkeyup="calculateIncomeAmount();" maxlength="13"/>&nbsp;
            <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span>
            <c:set var="productPortfolioParameters" value="${product.parameters[portfolioType]}"/>
            <c:set var="productMinSum" value="${productPortfolioParameters.minSum}"/>
            <c:if test="${portfolio.freeAmount.decimal >= productMinSum and portfolio.freeAmount.decimal >= minAccountProductSum }">
                <span class="italic text-gray">
                    от <fmt:formatNumber value="${minAccountProductSum}" pattern=",##0.00"/> <bean:message bundle="pfpBundle" key="currency.rub"/>
                </span>
            </c:if>
        </tiles:put>
        <tiles:put name="clazz" value="amountHelpMessage"/>
    </tiles:insert>
</fieldset>

<script type="text/javascript">
    var fundList = {};
    var product;
    <c:forEach var="fundProduct" items="${product.fundProducts}">
        product = {};
        <c:set var="productMaxSum" value="${portfolio.freeAmount.decimal - fundProduct.sumFactor}"/>
        <c:set var="fundPortfolioParameters" value="${fundProduct.parameters[portfolioType]}"/>
        <c:set var="productMinSum" value="${fundPortfolioParameters.minSum}"/>
        <c:if test="${productMaxSum > productMinSum}">
            product.minSum = '<fmt:formatNumber value="${productMinSum}" pattern=",##0.00"/> <bean:message bundle="pfpBundle" key="currency.rub"/>';
            product.maxSum = '<fmt:formatNumber value="${productMaxSum}" pattern=",##0.00"/> <bean:message bundle="pfpBundle" key="currency.rub"/>';
        </c:if>
        <c:if test="${not empty fundProduct.minIncome}">
            product.minIncome = ${fundProduct.minIncome};
        </c:if>
        <c:if test="${not empty fundProduct.maxIncome}">
            product.maxIncome = ${fundProduct.maxIncome};
        </c:if>
        <c:if test="${not empty fundProduct.defaultIncome}">
            product.defaultIncome = ${fundProduct.defaultIncome};
        </c:if>
        fundList['${fundProduct.id}'] = product;
    </c:forEach>


    function setFundAmountDescription()
    {
        var selectFundProduct = $('select[name=field(fundProductId)]');
        var product = fundList[selectFundProduct.val()];
        if(product.minSum != undefined && product.maxSum != undefined)
            $('#fundAmountHelpDescription').html('от ' + product.minSum);
        else
            $('#fundAmountHelpDescription').html('');
    }

    function setFundIncomeScroll()
    {
        var selectFundProduct = $('select[name=field(fundProductId)]');
        var product = fundList[selectFundProduct.val()];
        if(product.minIncome != undefined && product.maxIncome != undefined)
        {
            $('#fundProductIncomeSpan').hide();
            $('#fundProductIncomeScroll').show();
            updateScroll('fundIncome', horizDragfundIncome, product.defaultIncome, product.minIncome, product.maxIncome);
        }
        else
        {
            $('#fundProductIncomeScroll').hide();
            $('#fundProductIncomeSpan').show();
            $('#fundProductIncomeSpanValue').html(product.defaultIncome);
            $('input[name=field(fundIncome)]').val(product.defaultIncome);
        }
        calculateIncomeAmount();
    }

    function calculateIncomeAmount()
    {
        var incomePeriod = getIncomePeriod();

        var fundAmount = parseFloatVal($('input[name="field(fundAmount)"]').val());
        if(isNaN(fundAmount))
            fundAmount = 0;
        var fundIncome = parseFloatVal($('input[name="field(fundIncome)"]').val());
        var productAmount = pfpMath.calculateIncome('${form.portfolioType}', fundAmount, fundIncome, incomePeriod);

        var accountAmount = parseFloatVal($('input[name="field(accountAmount)"]').val());
        if(isNaN(accountAmount))
            accountAmount = 0;
        var accountIncome = parseFloatVal($('input[name="field(accountIncome)"]').val());        
        productAmount.add(pfpMath.calculateIncome('${form.portfolioType}', accountAmount, accountIncome, incomePeriod));

        setBatteryVals('battery', productAmount.investment, productAmount.income);
    }

    $(document).ready(function(){
        setFundAmountDescription();
    });

    doOnLoad(function(){
        calculateIncomeAmount();
        $('#fundProductIncomeScroll').addClass('${incomeFundScrollStyle}');
    });

</script>

