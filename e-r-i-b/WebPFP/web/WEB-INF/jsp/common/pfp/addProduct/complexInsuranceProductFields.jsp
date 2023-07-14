<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="portfolioType" value="${form.portfolio.type}"/>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.product.description"/></tiles:put>
    <tiles:put name="data">
        <span class="bold">${product.description}</span>
    </tiles:put>
</tiles:insert>

<fieldset>
    <legend><bean:message bundle="pfpBundle" key="label.addProduct.legend.account"/></legend>
    <c:set var="account" value="${product.account}"/>
    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.accountName"/></tiles:put>
        <tiles:put name="data">
            <span class="bold"><bean:write name="product" property="account.name"/></span>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.accountTerm"/></tiles:put>
        <tiles:put name="data">
            <span class="bold">1 год</span>
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

    <c:set var="accountProduct" value="${product.account}"/>
    <c:set var="accountProductPortfolioParameters" value="${accountProduct.parameters[portfolioType]}"/>
    <c:set var="minAccountProductSum" value="${accountProductPortfolioParameters.minSum}"/>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.accountAmount"/></tiles:put>
        <tiles:put name="data">
            <html:text property="field(accountAmount)" size="10" styleClass="moneyField" onkeyup="calculateIncomeAmount();" maxlength="13"/>
            <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span>
            <c:set var="maxAccountAmount" value="${product.minSumInsurance * product.account.sumFactor}"/>
            <c:if test="${maxAccountAmount > portfolio.freeAmount.decimal}">
              <c:set var="maxAccountAmount" value="${portfolio.freeAmount.decimal - product.minSumInsurance}"/>
            </c:if>
            <c:if test="${portfolio.freeAmount.decimal >= product.minSum and maxAccountAmount >= minAccountProductSum }">
                <span class="italic text-gray">
                    от <fmt:formatNumber value="${minAccountProductSum}" pattern=",##0.00"/> <bean:message bundle="pfpBundle" key="currency.rub"/>
                </span>
            </c:if>
        </tiles:put>
        <tiles:put name="clazz" value="amountHelpMessage"/>
    </tiles:insert>
</fieldset>

<fieldset>
    <legend><bean:message bundle="pfpBundle" key="label.addProduct.legend.insurance"/></legend>

    <c:set var="insuranceProducts" value="${product.insuranceProducts}"/>
    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.productName"/></tiles:put>
        <tiles:put name="data">
            <c:if test="${phiz:size(insuranceProducts) == 1}">
                <c:set var="selectStyle" value="display: none;"/>
                <span class="bold"><c:out value="${insuranceProducts[0].name}"/></span>
            </c:if>
            <html:select property="field(insuranceProductId)" onchange="setInsuranceProductValues();setInsuranceIncomeScroll();updateInsuranceCalculator();" style="${selectStyle}" styleClass="select">
                <html:options collection="insuranceProducts" property="id" labelProperty="name"/>
            </html:select>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.companyName"/></tiles:put>
        <tiles:put name="data">
            <span id="insuranceCompanyName" class="bold"></span>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title">
            <bean:message bundle="pfpBundle" key="label.addProduct.insurance.insuranceSum"/>
        </tiles:put>
        <tiles:put name="data">
            <div class="float bold">
                <html:text styleId="insuranceSum" property="field(insuranceSum)" size="10" styleClass="moneyField"
                           onkeyup="getCurrentCalculator().updateInsuranceSumValue(this.value);" maxlength="20"/>
                <span>&nbsp;<bean:message bundle="pfpBundle" key="label.addProduct.insurance.insuranceSum.unit"/></span>
            </div>
            <tiles:insert definition="floatMessage" flush="false">
                <tiles:put name="id" value="insuranceSumFloatMessage"/>
                <tiles:put name="text">
                    <bean:message bundle="pfpBundle" key="label.addProduct.insurance.insuranceSum.hint"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.insurance.periodicity"/></tiles:put>
        <tiles:put name="data">
            <div id="periodBlockForSpan" class="displayNone">
                <span id="selectedPeriodSpan" class="bold"></span>
            </div>
            <div id="periodBlockForSelect" class="displayNone">
                <html:select property="field(selectedPeriodId)"
                             onchange="setPeriodTermValues(); getCurrentCalculator().updatePeriodicityValue(getPeriodicityValue(), getPeriodValue());" >
                    <html:option value="${form.fields['selectedPeriodId']}"/>
                </html:select>
            </div>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.programTerm"/></tiles:put>
        <tiles:put name="data">
            <div id="periodTermsBlockForSpan" class="displayNone">
                <span id="selectedTermSpan" class="bold"></span>
            </div>
            <div id="periodTermsBlockForSelect" class="displayNone">
                <html:select property="field(selectedTerm)" onchange="getCurrentCalculator().updatePeriodValue(getPeriodValue());">
                    <html:option value="${form.fields['selectedTerm']}"/>
                </html:select>
            </div>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.income"/></tiles:put>
        <tiles:put name="data">
            <c:set var="insuranceProduct" value="${product.insuranceProducts[0]}"/>
            <c:set var="selectedInsuranceProductId"><bean:write name="org.apache.struts.taglib.html.BEAN" property="field(insuranceProductId)"/></c:set>
            <c:forEach var="currentInsuranceProduct" items="${product.insuranceProducts}">
                <c:if test="${currentInsuranceProduct.id == selectedInsuranceProductId}">
                    <c:set var="insuranceProduct" value="${currentInsuranceProduct}"/>
                </c:if>
            </c:forEach>
            <c:choose>
                <c:when test="${not empty insuranceProduct.minIncome and not empty insuranceProduct.maxIncome}">
                    <c:set var="incomeScrollStyle" value=""/>
                    <c:set var="incomeSpanStyle" value="displayNone"/>
                </c:when>
                <c:otherwise>
                    <c:set var="incomeScrollStyle" value="displayNone"/>
                    <c:set var="incomeSpanStyle" value=""/>
                </c:otherwise>
            </c:choose>

            <div id="insuranceProductIncomeScroll">
                <tiles:insert definition="scrollTemplate2" flush="false">
                    <tiles:put name="id" value="insuranceIncome"/>
                    <tiles:put name="minValue" value="${insuranceProduct.minIncome}"/>
                    <tiles:put name="maxValue" value="${insuranceProduct.maxIncome}"/>
                    <tiles:put name="unit" value="%"/>
                    <tiles:put name="currValue" value="${insuranceProduct.defaultIncome}"/>
                    <tiles:put name="fieldName" value="field(insuranceIncome)"/>
                    <tiles:put name="inputData"> % годовых <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/></tiles:put>
                    <tiles:put name="step" value="0.1"/>
                    <tiles:put name="round" value="1"/>
                    <tiles:put name="callback" value="calculateIncomeAmount();"/>
                </tiles:insert>
            </div>
            <span id="insuranceProductIncomeSpan" class="bold ${incomeSpanStyle}">
                <c:set var="insuranceIncome"><bean:write name="insuranceProduct" property="defaultIncome"/></c:set>
                <span id="insuranceProductIncomeSpanValue">
                    ${insuranceIncome}
                </span>
                <bean:message bundle="pfpBundle" key="label.addProduct.income.help"/>
                <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/>
            </span>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title">
            <bean:message bundle="pfpBundle" key="label.addProduct.insurance.calculatingSum"/>
        </tiles:put>
        <tiles:put name="data">
            <div class="float bold"><span id="calculatingSum"></span>&nbsp;<bean:message bundle="pfpBundle" key="label.addProduct.insurance.calculatingSum.unit"/></div>
            <tiles:insert definition="floatMessage" flush="false">
                <tiles:put name="id" value="calculatingSumFloatMessage"/>
                <tiles:put name="text">
                    <bean:message bundle="pfpBundle" key="label.addProduct.insurance.calculatingSum.hint"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.insuranceAmountForComplex"/></tiles:put>
        <tiles:put name="data">
            <html:text property="field(insuranceAmount)" size="10" styleClass="moneyField" onkeyup="calculateIncomeAmount();" maxlength="13"/>
            <span class="bold">&nbsp;<bean:message bundle="pfpBundle" key="label.addProduct.insuranceAmountForComplex.unit"/></span>
            <c:if test="${portfolio.freeAmount.decimal >= product.minSum and portfolio.freeAmount.decimal >= product.minSumInsurance}">
                <span class="italic text-gray">
                    от <fmt:formatNumber value="${product.minSumInsurance}" pattern=",##0.00"/> <bean:message bundle="pfpBundle" key="currency.rub"/>
                </span>
            </c:if>
        </tiles:put>
        <tiles:put name="clazz" value="amountHelpMessage"/>
    </tiles:insert>
</fieldset>

<script type="text/javascript">

    var insuranceProductList = {};
    var insurancePeriods;
    var periodList;
    var periodTerms;
    <c:forEach var="product" items="${insuranceProducts}">
        insuranceProductList['${product.id}'] = new Object();
        insurancePeriods = {};
        <c:forEach var="period" items="${product.periods}">
            insurancePeriods['${period.id}'] = new Object();
            periodTerms=new Array();
            <c:forEach var="periodValue" items="${period.periodList}">
                periodTerms.push('${periodValue}');
            </c:forEach>
            insurancePeriods['${period.id}'].periodName = '${period.type.name}';
            insurancePeriods['${period.id}'].periodTerms = periodTerms;
            <c:if test="${not empty period.type.months}">
                insurancePeriods['${period.id}'].monthCount = ${period.type.months};
            </c:if>
        </c:forEach>
        insuranceProductList['${product.id}'].companyName = '${product.insuranceCompany.name}';
        <c:if test="${not empty product.minIncome}">
            insuranceProductList['${product.id}'].minIncome = ${product.minIncome};
        </c:if>
        <c:if test="${not empty product.maxIncome}">
            insuranceProductList['${product.id}'].maxIncome = ${product.maxIncome};
        </c:if>
        <c:if test="${not empty product.defaultIncome}">
            insuranceProductList['${product.id}'].defaultIncome = ${product.defaultIncome};
        </c:if>
        insuranceProductList['${product.id}'].periods = insurancePeriods;
    </c:forEach>

    function getCurrentCalculator()
    {
        return getInsuranceCalculator('complexInsurance');
    }

    function getPeriodicityValue()
    {
        return insuranceProductList[$('select[name=field(insuranceProductId)]').val()].periods[$('select[name=field(selectedPeriodId)]').val()].monthCount;    
    }

    function getPeriodValue()
    {
        return $('select[name=field(selectedTerm)]').val();
    }

    function updateInsuranceCalculator()
    {
        addInsuranceCalculator("complexInsurance", $('#insuranceSum').val(), getPeriodicityValue(), getPeriodValue(), $('#calculatingSum'));
    }

    function setInsuranceProductValues()
    {
        var insuranceProductSelect = $("select[name=field(insuranceProductId)]");
        var insuranceProduct = insuranceProductList[insuranceProductSelect.val()];
        $('#insuranceCompanyName').html(insuranceProduct.companyName);
        var selectedPeriod = $('select[name=field(selectedPeriodId)]');
        var selectedPeriodValue = selectedPeriod.val();
        selectedPeriod.empty();/*очищаем значения селекта*/
        var selectElementCount = 0;
        for(var key in insuranceProduct.periods)
        {
            selectedPeriod.append($('<option value="'+key+'">'+insuranceProduct.periods[key].periodName+'</option>'));
            selectElementCount++;
        }
        /*устанавливаем предыдущее значение селекта, если оно есть в новом селекте*/
        $('select[name=field(selectedPeriodId)] option[value='+selectedPeriodValue+']').attr("selected","true");
        /*если для селекта есть всего одно возможное значение, то скрываем его и отображаем спан*/
        if(selectElementCount == 1)
        {
            $('#selectedPeriodSpan').html(selectedPeriod.text());
            $('#periodBlockForSpan').removeClass("displayNone");
            $('#periodBlockForSelect').addClass("displayNone");
        }
        else
        {
            $('#periodBlockForSpan').addClass("displayNone");
            $('#periodBlockForSelect').removeClass("displayNone");
        }
        setPeriodTermValues();
    }

    function setPeriodTermValues()
    {
        var insuranceProductSelect = $("select[name=field(insuranceProductId)]");
        var selectedProductPeriod = $("select[name=field(selectedPeriodId)]");
        /*получаем для выбранного страхового продукта и выбранной периодичности возможные сроки*/
        var periodTerms = insuranceProductList[insuranceProductSelect.val()].periods[selectedProductPeriod.val()].periodTerms;
        var selectedTerm = $('select[name=field(selectedTerm)]');
        var selectedTermValue = selectedTerm.val();
        selectedTerm.empty();/*очищаем значения селекта*/
        for(var i=0;i<periodTerms.length;i++)
        {
            var formatTerm = periodTerms[i] + " " + selectSklonenie(periodTerms[i], years);
            selectedTerm.append($('<option value="'+periodTerms[i]+'">'+formatTerm+'</option>'));
        }
        /*устанавливаем предыдущее значение селекта, если оно есть в новом селекте*/
        $('select[name=field(selectedTerm)] option[value='+selectedTermValue+']').attr("selected","true");
        /*если для селекта есть всего одно возможное значение, то скрываем его и отображаем спан*/
        if(periodTerms.length == 1)
        {
            $('#selectedTermSpan').html(selectedTerm.text());
            $('#periodTermsBlockForSpan').removeClass("displayNone");
            $('#periodTermsBlockForSelect').addClass("displayNone");
        }
        else
        {
            $('#periodTermsBlockForSpan').addClass("displayNone");
            $('#periodTermsBlockForSelect').removeClass("displayNone");
        }
        calculateIncomeAmount();
    }

    function setInsuranceIncomeScroll()
    {
        var insuranceProductSelect = $("select[name=field(insuranceProductId)]");
        var insuranceProduct = insuranceProductList[insuranceProductSelect.val()];
        if(insuranceProduct.minIncome != undefined && insuranceProduct.maxIncome != undefined)
        {
            $('#insuranceProductIncomeSpan').hide();
            $('#insuranceProductIncomeScroll').show();
            updateScroll('insuranceIncome', horizDraginsuranceIncome, insuranceProduct.defaultIncome, insuranceProduct.minIncome, insuranceProduct.maxIncome);
        }
        else
        {
            $('#insuranceProductIncomeScroll').hide();
            $('#insuranceProductIncomeSpan').show();
            $('#insuranceProductIncomeSpanValue').html(insuranceProduct.defaultIncome);
            $('input[name=field(insuranceIncome)]').val(insuranceProduct.defaultIncome);
        }
        calculateIncomeAmount();
    }

    function calculateIncomeAmount()
    {
        var incomePeriod = getIncomePeriod();

        var accountAmount = parseFloatVal($('input[name="field(accountAmount)"]').val());
        if(isNaN(accountAmount))
            accountAmount = 0;
        var accountIncome = parseFloatVal($('input[name="field(accountIncome)"]').val());
        var productAmount = pfpMath.calculateIncome('${form.portfolioType}', accountAmount, accountIncome, incomePeriod);

        var insuranceAmount = parseFloatVal($('input[name="field(insuranceAmount)"]').val());
        if(isNaN(insuranceAmount))
            insuranceAmount = 0;
        var insuranceIncome = parseFloatVal($('input[name="field(insuranceIncome)"]').val());

        var insuranceProductSelect = $("select[name=field(insuranceProductId)]");
        var selectedProductPeriod = $('select[name=field(selectedPeriodId)]');
        var period = insuranceProductList[insuranceProductSelect.val()].periods[selectedProductPeriod.val()];
        productAmount.add(pfpMath.calculateInsuranceIncome(period.monthCount, insuranceAmount, insuranceIncome, incomePeriod));

        setBatteryVals('battery', productAmount.investment, productAmount.income);
    }

    $(document).ready(function(){
        setInsuranceProductValues();
        updateInsuranceCalculator();
    });

    doOnLoad(function(){
        calculateIncomeAmount();
        $('#insuranceProductIncomeScroll').addClass('${incomeScrollStyle}');
    });

</script>