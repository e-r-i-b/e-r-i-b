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
        <bean:message bundle="pfpBundle" key="label.addProduct.fundName"/>
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

<c:set var="riskName" value="${product.risk.name}"/>
<c:if test="${not empty riskName}">
    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.risk"/></tiles:put>
        <tiles:put name="data">
            <div class="float"><span class="bold"><c:out value="${riskName}"/></span></div>
            <tiles:insert definition="floatMessage" flush="false">
                <tiles:put name="id" value="riskFloatMessage"/>
                <tiles:put name="text">
                    <bean:message bundle="pfpBundle" key="label.addProduct.risk.description"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</c:if>

<c:set var="investmentPeriod" value="${product.investmentPeriod.period}"/>
<c:if test="${not empty investmentPeriod}">
    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.investmentPeriod"/></tiles:put>
        <tiles:put name="data">
            <div class="float"><span class="bold">${investmentPeriod}</span></div>
            <tiles:insert definition="floatMessage" flush="false">
                <tiles:put name="id" value="investmentPeriodFloatMessage"/>
                <tiles:put name="text">
                    <bean:message bundle="pfpBundle" key="label.addProduct.investmentPeriod.description"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</c:if>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.income"/></tiles:put>
    <tiles:put name="data">
        <c:choose>
            <c:when test="${not empty product.minIncome and not empty product.maxIncome}">
                <tiles:insert definition="scrollTemplate2" flush="false">
                    <tiles:put name="id" value="fundIncome"/>
                    <tiles:put name="minValue" value="${product.minIncome}"/>
                    <tiles:put name="maxValue" value="${product.maxIncome}"/>
                    <tiles:put name="unit" value="%"/>
                    <tiles:put name="currValue" value="${product.defaultIncome}"/>
                    <tiles:put name="fieldName" value="field(fundIncome)"/>
                    <tiles:put name="inputData"> % годовых <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/></tiles:put>
                    <tiles:put name="step" value="0.1"/>
                    <tiles:put name="round" value="1"/>
                    <tiles:put name="callback" value="calculateIncomeAmount();"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <span class="bold">
                    <c:set var="fundIncome"><bean:write name="product" property="defaultIncome"/></c:set>
                    <html:hidden property="field(fundIncome)" value="${fundIncome}"/>
                    ${fundIncome}<bean:message bundle="pfpBundle" key="label.addProduct.income.help"/>
                    <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/>
                </span>
            </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title">
        <bean:message bundle="pfpBundle" key="label.addProduct.amount"/>
    </tiles:put>
    <tiles:put name="data">
        <html:text property="field(fundAmount)" size="10" styleClass="moneyField" onkeyup="calculateIncomeAmount();" maxlength="13"/>&nbsp;
        <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span>
        <c:set var="productPortfolioParameters" value="${product.parameters[portfolioType]}"/>
        <c:set var="minProductSum" value="${productPortfolioParameters.minSum}"/>
        <c:if test="${portfolio.freeAmount.decimal >= minProductSum}">
            <span class="italic text-gray">
	            от <fmt:formatNumber value="${minProductSum}" pattern=",##0.00"/> <bean:message bundle="pfpBundle" key="currency.rub"/>
	            до ${phiz:formatAmount(portfolio.freeAmount)}
            </span>
        </c:if>
    </tiles:put>
    <tiles:put name="clazz" value="amountHelpMessage"/>
</tiles:insert>


<script type="text/javascript">

    function calculateIncomeAmount()
    {
        var incomePeriod = getIncomePeriod();

        var amount = parseFloatVal($('input[name="field(fundAmount)"]').val());
        if(isNaN(amount))
            amount = 0;
        var income = parseFloatVal($('input[name="field(fundIncome)"]').val());
        var productAmount = pfpMath.calculateIncome('${portfolioType}', amount, income, incomePeriod);
        setBatteryVals('battery', productAmount.investment, productAmount.income);
    }

    doOnLoad(calculateIncomeAmount);

</script>