<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="portfolioType" value="${form.portfolio.type}"/>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title">
        <bean:message bundle="pfpBundle" key="label.addProduct.productName"/>
    </tiles:put>
    <tiles:put name="data">
        <span class="bold"><bean:write name="product" property="name"/></span>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.product.description"/></tiles:put>
    <tiles:put name="data">
        <span class="bold">${product.description}</span>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.pension.startAmount"/></tiles:put>
    <tiles:put name="data">
        <html:text property="field(pensionStartAmount)" size="10" styleClass="moneyField" onkeyup="calculateIncomeAmount();" maxlength="13"/>&nbsp;
        <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span>
        <c:if test="${portfolio.freeAmount.decimal >= product.entryFee}">
            <span class="italic text-gray">
                от <fmt:formatNumber value="${product.entryFee}" pattern=",##0.00"/> <bean:message bundle="pfpBundle" key="currency.rub"/>
                до ${phiz:formatAmount(portfolio.freeAmount)}
            </span>
        </c:if>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.pension.quarterlyAmount"/></tiles:put>
    <tiles:put name="data">
        <c:set var="quarterlyInvestPortfolio" value="${null}"/>
        <c:set var="portfolioList" value="${profile.portfolioList}"/>
        <c:forEach var="cPortfolio" items="${portfolioList}">
            <c:if test="${cPortfolio.type == 'QUARTERLY_INVEST'}">
                <c:set var="quarterlyInvestPortfolio" value="${cPortfolio}"/>
            </c:if>
        </c:forEach>
        <html:text property="field(pensionQuarterlyAmount)" size="10" styleClass="moneyField" onkeyup="calculateIncomeAmount();" maxlength="13"/>&nbsp;
        <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span>
        <c:if test="${quarterlyInvestPortfolio.freeAmount.decimal >= product.quarterlyFee}">
            <span class="italic text-gray">
                от <fmt:formatNumber value="${product.quarterlyFee}" pattern=",##0.00"/> <bean:message bundle="pfpBundle" key="currency.rub"/>
                до ${phiz:formatAmount(quarterlyInvestPortfolio.freeAmount)}
            </span>
        </c:if>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.pension.term"/></tiles:put>
    <tiles:put name="data">
        <tiles:insert definition="scrollTemplate2" flush="false">
            <tiles:put name="id" value="pensionPeriod"/>
            <tiles:put name="minValue" value="${product.minPeriod}"/>
            <tiles:put name="maxValue" value="${product.maxPeriod}"/>
            <tiles:put name="unit" value=" кв"/>
            <tiles:put name="currValue" value="${product.defaultPeriod}"/>
            <tiles:put name="fieldName" value="field(pensionPeriod)"/>
            <tiles:put name="inputData">
                <span class="bold">кварталов</span>
                &nbsp;
                <span class="quarterToMonths italic text-gray">NN  месяцев</span>
                <a class="imgHintBlock save-template-hint" title="Период времени пенсионных взносов."></a>
            </tiles:put>
            <tiles:put name="maxInputLength" value="15"/>
            <tiles:put name="step" value="1"/>
            <tiles:put name="callback" value="recalculateMonths();"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.income"/></tiles:put>
    <tiles:put name="data">
        <c:choose>
            <c:when test="${not empty product.minIncome and not empty product.maxIncome}">
                <tiles:insert definition="scrollTemplate2" flush="false">
                    <tiles:put name="id" value="pensionIncome"/>
                    <tiles:put name="minValue" value="${product.minIncome}"/>
                    <tiles:put name="maxValue" value="${product.maxIncome}"/>
                    <tiles:put name="unit" value="%"/>
                    <tiles:put name="currValue" value="${product.defaultIncome}"/>
                    <tiles:put name="fieldName" value="field(pensionIncome)"/>
                    <tiles:put name="inputData"> % годовых <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/></tiles:put>
                    <tiles:put name="step" value="0.1"/>
                    <tiles:put name="round" value="1"/>
                    <tiles:put name="callback" value="calculateIncomeAmount();"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <span class="bold">
                    <c:set var="pensionIncome"><bean:write name="product" property="defaultIncome"/></c:set>
                    <html:hidden property="field(pensionIncome)" value="${pensionIncome}"/>
                    ${pensionIncome}<bean:message bundle="pfpBundle" key="label.addProduct.income.help"/>
                    <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/>
                </span>
            </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>

<script type="text/javascript">

    function calculateIncomeAmount()
    {
        var incomePeriod = getIncomePeriod();

        var startAmount = parseFloatVal($('input[name="field(pensionStartAmount)"]').val());
        if(isNaN(startAmount))
            startAmount = 0;
        var quarterlyAmount = parseFloatVal($('input[name="field(pensionQuarterlyAmount)"]').val());
        if(isNaN(quarterlyAmount))
            quarterlyAmount = 0;
        var income = parseFloatVal($('input[name="field(pensionIncome)"]').val());
        var productAmount = pfpMath.calculatePensionIncome(startAmount, quarterlyAmount, income, incomePeriod);
        setBatteryVals('battery', productAmount.investment, productAmount.income);
    }

    doOnLoad(calculateIncomeAmount);

    function recalculateMonths()
    {
        var quarter = parseInt($('[name=field(pensionPeriod)]').val());
        $('.quarterToMonths').text(quarter*3 + " " + selectSklonenie(quarter*3, months));
    }

    $(document).ready(function()
    {
        recalculateMonths();
    });



</script>