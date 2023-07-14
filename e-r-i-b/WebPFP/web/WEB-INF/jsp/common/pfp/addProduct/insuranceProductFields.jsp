<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.productName"/></tiles:put>
    <tiles:put name="data">
        <span class="bold"><bean:write name="product" property="name"/></span>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.companyName"/></tiles:put>
    <tiles:put name="data">
        <span class="bold"><bean:write name="product" property="insuranceCompany.name"/></span>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.description"/></tiles:put>
    <tiles:put name="data">
        <span class="bold"><bean:write name="product" property="description"/></span>
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
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.periodicity"/></tiles:put>
    <tiles:put name="data">
        <c:set var="periods" value="${product.periods}"/>
        <c:set var="selectStyle" value=""/>
        <c:if test="${phiz:size(periods) == 1}">
            <c:set var="selectStyle" value="display: none;"/>
            <span class="bold"><c:out value="${periods[0].type.name}"/></span>
        </c:if>
        <html:select property="field(selectedPeriodId)" onchange="setPeriodValues();getCurrentCalculator().updatePeriodicityValue(getPeriodicityValue(), getPeriodValue());" style="${selectStyle}">
            <html:options collection="periods" property="id" labelProperty="type.name"/>
        </html:select>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.programTerm"/></tiles:put>
    <tiles:put name="data">
        <%--TODO сделаны 2 дива обертки, поскольку в данный момент есть проблемы со скрытием нашего селекта через класс displayNone--%>
        <div id="periodTermsBlockForSpan" class="displayNone">
            <span id="selectedTermSpan" class="bold"></span>
        </div>
        <div id="periodTermsBlockForSelect" class="displayNone">
            <html:select property="field(selectedTerm)" onchange="getCurrentCalculator().updatePeriodValue(getPeriodValue());">
                <html:option value="${form.fields['selectedTerm']}"/>
            </html:select>
        </div>
    </tiles:put>
    <tiles:put name="description">
        <span id="programTermHelp" class="displayNone"><bean:message bundle="pfpBundle" key="label.addProduct.insurance.term.help"/></span>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.income"/></tiles:put>
    <tiles:put name="data">
        <c:choose>
            <c:when test="${not empty product.minIncome and not empty product.maxIncome}">
                <tiles:insert definition="scrollTemplate2" flush="false">
                    <tiles:put name="id" value="income"/>
                    <tiles:put name="minValue" value="${product.minIncome}"/>
                    <tiles:put name="maxValue" value="${product.maxIncome}"/>
                    <tiles:put name="unit" value="%"/>
                    <tiles:put name="currValue" value="${product.defaultIncome}"/>
                    <tiles:put name="fieldName" value="field(insuranceIncome)"/>
                    <tiles:put name="inputData"> % годовых <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/></tiles:put>
                    <tiles:put name="step" value="0.1"/>
                    <tiles:put name="round" value="1"/>
                    <tiles:put name="callback" value="calculateIncomeAmount();"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <span class="bold">
                    <c:set var="insuranceIncome"><bean:write name="product" property="defaultIncome"/></c:set>
                    <html:hidden property="field(insuranceIncome)" value="${insuranceIncome}"/>
                    ${insuranceIncome}<bean:message bundle="pfpBundle" key="label.addProduct.income.help"/>
                    <jsp:include page="/WEB-INF/jsp/common/pfp/addProduct/productIncomeFloatMessage.jsp"/>
                </span>
            </c:otherwise>
        </c:choose>
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
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addProduct.insuranceAmount"/></tiles:put>
    <tiles:put name="data">
        <html:text property="field(insuranceAmount)" size="10" styleClass="moneyField" onkeyup="calculateIncomeAmount();" maxlength="13"/>
        <span class="bold">&nbsp;<bean:message bundle="pfpBundle" key="label.addProduct.insuranceAmount.unit"/></span>
        <span id="amountHelpDescription" class="italic text-gray"></span>
    </tiles:put>
    <tiles:put name="clazz" value="amountHelpMessage"/>
</tiles:insert>

<%--поле дл€ хранени€ максимальной суммы в портфеле. ≈сли сразу т€нуть это значение в JavaScript, то WebSphere не увидит библиотеку phiz--%>
<input type="hidden" id="portfolioFreeAmount" value="${phiz:formatAmount(portfolio.freeAmount)}">

<script type="text/javascript">    

    var periodList = {};
    var periodTerms;
    var period;
    var defaultValue="";
    var portfolioFreeAmount = $('#portfolioFreeAmount').val();
    var nullAmount = '<fmt:formatNumber value="0" pattern=",##0.00"/> <bean:message bundle="pfpBundle" key="currency.rub"/>';
    var dontShowWarningMessage = true;/*Ќе показываем информационное сообщение, так как если эта переменна€ не true
                                        , то сообщение выведетс€ из экшена через ActionMessages*/
    <c:forEach var="period" items="${product.periods}">
        period = {};
        periodTerms=new Array();
        <c:forEach var="periodValue" items="${period.periodList}">
            periodTerms.push('${periodValue}');
        </c:forEach>
        <c:choose>
            <c:when test="${period.minSum > portfolio.freeAmount.decimal}">
                period.warningMessage = 'ќбратите внимание! ¬ данном портфеле у ¬ас осталось свободных средств на сумму ' + portfolioFreeAmount + ' Ёта сумма меньше минимального взноса по продукту ${phiz:formatAmount(period.minSumInNationalCurrency)} ћинимальна€ сумма может мен€тьс€ в зависимости от выбранной периодичности взносов.';
            </c:when>
            <c:otherwise>
                dontShowWarningMessage = false;
            </c:otherwise>
        </c:choose>
        period.minSum = '${phiz:formatAmount(period.minSumInNationalCurrency)}';
        period.maxSum = '${phiz:formatAmount(period.maxSumInNationalCurrency)}';
        <c:if test="${empty period.minSum}">
            period.minSum = nullAmount;
        </c:if>
        <c:if test="${empty period.maxSum}">
            period.maxSum = portfolioFreeAmount;
        </c:if>
        <c:if test="${period.defaultPeriod == 'true'}">
            defaultValue = '${period.id}';
        </c:if>
        period.terms = periodTerms;
        <c:if test="${not empty period.type.months}">
            period.monthCount = ${period.type.months};
        </c:if>
        periodList['${period.id}'] = period;
    </c:forEach>

    function getCurrentCalculator()
    {
        return getInsuranceCalculator('complexInsurance');
    }

    function getPeriodicityValue()
    {
        return periodList[$('select[name=field(selectedPeriodId)]').val()].monthCount;
    }

    function getPeriodValue()
    {
        return $('select[name=field(selectedTerm)]').val();
    }

    function setPeriodValues()
    {
        var periodType = $('select[name=field(selectedPeriodId)]');
        var period = periodList[periodType.val()];
        var periodTermsList = period.terms;
        var selectedTerm = $('select[name=field(selectedTerm)]');
        var selectedTermValue = selectedTerm.val(); /*текущее значение селекта*/
        selectedTerm.empty();/*очищаем значени€ селекта*/
        for(var i=0;i<periodTermsList.length;i++)
        {
            var formatTerm = periodTermsList[i] + " " + selectSklonenie(periodTermsList[i], years);
            selectedTerm.append($('<option value="'+periodTermsList[i]+'">'+ formatTerm +'</option>'));
        }
        /*устанавливаем предыдущее значение селекта, если оно есть в новом селекте*/
        $('select[name=field(selectedTerm)] option[value='+selectedTermValue+']').attr("selected","true");
        /*если дл€ селекта есть всего одно возможное значение, то скрываем его и отображаем спан*/
        if(periodTermsList.length == 1)
        {
            $('#selectedTermSpan').html(selectedTerm.text());
            $('#periodTermsBlockForSpan').removeClass("displayNone");
            $('#periodTermsBlockForSelect').addClass("displayNone");
            $('#programTermHelp').addClass("displayNone");
        }
        else
        {
            $('#periodTermsBlockForSpan').addClass("displayNone");
            $('#periodTermsBlockForSelect').removeClass("displayNone");
            $('#programTermHelp').removeClass("displayNone");
        }
        calculateIncomeAmount();
        if(dontShowWarningMessage)
            return;
        if(period.warningMessage != undefined)
        {
            $('#amountHelpDescription').html('');
            removeMessage();/*удал€ем все информационные сообщени€*/
            addMessage(period.warningMessage);
        }
        else
        {
            $('#amountHelpDescription').html('от ' + period.minSum + ' до ' + period.maxSum);
            removeMessage();/*удал€ем все информационные сообщени€*/
        }
    }
    <c:if test="${empty form.fields.selectedPeriodId}">
        $('select[name=field(selectedPeriodId)] option[value='+defaultValue+']').attr("selected","true");
    </c:if>

    function calculateIncomeAmount()
    {
        var incomePeriod = getIncomePeriod();

        var amount = parseFloatVal($('input[name="field(insuranceAmount)"]').val());
        if(isNaN(amount))
            amount = 0;
        var income = parseFloatVal($('input[name="field(insuranceIncome)"]').val());
        var periodType = $('select[name=field(selectedPeriodId)]');
        var period = periodList[periodType.val()];
        var productAmount = pfpMath.calculateInsuranceIncome(period.monthCount, amount, income, incomePeriod);
        setBatteryVals('battery', productAmount.investment, productAmount.income);
    }

    $(document).ready(function(){
        setPeriodValues();
        addInsuranceCalculator("complexInsurance", $('#insuranceSum').val(), getPeriodicityValue(), getPeriodValue(), $('#calculatingSum'));
    });

    doOnLoad(calculateIncomeAmount);

</script>