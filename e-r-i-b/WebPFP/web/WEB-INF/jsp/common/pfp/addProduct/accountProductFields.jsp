<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="currentYear"><fmt:formatDate value="${phiz:currentDate().time}" pattern="yyyy"/></c:set>
<c:set var="portfolioType" value="${form.portfolio.type}"/>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.productName"/></tiles:put>
    <tiles:put name="data">
        <span class="bold"><bean:write name="product" property="name"/></span>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.description"/></tiles:put>
    <tiles:put name="data">
        <span class="bold"><bean:write name="product" property="description"/></span>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.income"/></tiles:put>
    <tiles:put name="data">
        <c:choose>
            <c:when test="${not empty product.minIncome and not empty product.maxIncome}">
                <tiles:insert definition="scrollTemplate2" flush="false">
                    <tiles:put name="id" value="accountIncome"/>
                    <tiles:put name="minValue" value="${product.minIncome}"/>
                    <tiles:put name="maxValue" value="${product.maxIncome}"/>
                    <tiles:put name="unit" value="%"/>
                    <tiles:put name="currValue" value="${product.defaultIncome}"/>
                    <tiles:put name="fieldName" value="field(accountIncome)"/>
                    <tiles:put name="inputData"> % годовых <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/></tiles:put>
                    <tiles:put name="step" value="0.1"/>
                    <tiles:put name="round" value="1"/>
                    <tiles:put name="callback" value="calculateIncomeAmount();"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <span class="bold">
                    <c:set var="accountIncome"><bean:write name="product" property="defaultIncome"/></c:set>
                    <html:hidden property="field(accountIncome)" value="${accountIncome}"/>
                    ${accountIncome}<bean:message bundle="pfpBundle" key="label.addProduct.income.help"/>
                    <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/>
                </span>
            </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.amount"/></tiles:put>
    <tiles:put name="data">
        <html:text property="field(accountAmount)" size="10" maxlength="13" styleClass="moneyField" onkeyup="calculateIncomeAmount();"/>&nbsp;
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

        var amount = parseFloatVal($('input[name="field(accountAmount)"]').val());
        if(isNaN(amount))
            amount = 0;
        var income = parseFloatVal($('input[name="field(accountIncome)"]').val());
        var productAmount = pfpMath.calculateIncome('${portfolioType}', amount, income, incomePeriod);
        setBatteryVals('battery', productAmount.investment, productAmount.income);
    }

    doOnLoad(calculateIncomeAmount);
    
</script>
