<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<tiles:importAttribute/>
<html:form action="/creditcards/incomes/edit" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="creditcardsBundle"/>

	<tiles:insert definition="creditCardsEdit">
		<tiles:put name="submenu" type="string" value="IncomeLevels"/>
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.list"/>
				<tiles:put name="commandHelpKey"     value="button.list"/>
				<tiles:put name="bundle"  value="${bundle}"/>
				<tiles:put name="action"  value="/creditcards/incomes/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
			<tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="EditIncomeLevel"/>
                <tiles:put name="name">
                    <bean:message key="title.income.edit" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="title.income.edit.description" bundle="${bundle}"/>
                </tiles:put>

                <script type="text/javascript">
                    doOnLoad(function() {
                        init();
                    });

                    function init()
                    {
                        loadCurrencies();
                        loadConditions();
                        loadCreditLimits();
                        refreshConditions();
                        initData();
                    }

                    //////////////////  Условия в разрезе валют  //////////////////
                    function condition(conditionId, curNumber, curCode, minCreditLimitId, minCreditLimitValue, maxCreditLimitId, maxCreditLimitValue, isMaxCreditLimitInclude, availableProducts)
                    {
                        this.conditionId = conditionId;
                        this.curNumber = curNumber;
                        this.curCode = curCode;
                        this.minCreditLimitId = minCreditLimitId;
                        this.minCreditLimitValue = minCreditLimitValue;
                        this.maxCreditLimitId = maxCreditLimitId;
                        this.maxCreditLimitValue = maxCreditLimitValue;
                        this.isMaxCreditLimitInclude = isMaxCreditLimitInclude;
                        this.availableProducts = availableProducts; //Доступные карточные кредитные продукты в виде "имя1, имя2, ..."
                    }
                    //конструктор копирования
                    function conditionCopy(condition)
                    {
                        this.conditionId = condition.conditionId;
                        this.curNumber = condition.curNumber;
                        this.curCode = condition.curCode;
                        this.minCreditLimitId = condition.minCreditLimitId;
                        this.minCreditLimitValue = condition.minCreditLimitValue;
                        this.maxCreditLimitId = condition.maxCreditLimitId;
                        this.maxCreditLimitValue = condition.maxCreditLimitValue;
                        this.isMaxCreditLimitInclude = condition.isMaxCreditLimitInclude;
                        this.availableProducts = "";
                        
                        this.setAvailableProducts = function(availableProducts){
                            this.availableProducts = availableProducts;
                        };
                    }
                    function currency(number, code)
                    {
                        this.number = number;
                        this.code = code;
                    }
                    function creditLimit(id, value)
                    {
                        this.id = id;
                        this.value = value;
                    }
                    function creditLimitsByCurrency(curCode, creditLimits)
                    {
                        this.curCode = curCode;
                        this.creditLimits = creditLimits;
                    }

                    var defaultCurrencies = new Array(); //основные валюты: RUB,USD,EUR. Других быть не должно.
                    function loadCurrencies()
                    {
                        <c:forEach var="currency" items="${form.currencies}">
                            defaultCurrencies.push(new currency(${currency.number}, '${currency.code}'));
                        </c:forEach>
                    }

                    var conditions = new Array(); //Массив условий в разрезе валют
                    function loadConditions()
                    {
                        var dflt = false; //признак того, что валюта одна из RUB,USD,EUR. Другие не учитываем.
                        <c:forEach var="condition" items="${form.conditions}">
                            for(var k = 0; k < defaultCurrencies.length; ++k)
                                if('${condition.currency.number}' == defaultCurrencies[k].number){
                                    dflt = true;
                                    break;
                                }
                            if(dflt)
                            {
                                var conditionId = ${empty condition.id} ? '' : '${condition.id}';
                                var productsArray = new Array();
                                <c:forEach var="product" items="${phiz:getAvailableCreditCardProducts(condition.currency.code, condition.maxCreditLimit.value.decimal, condition.maxCreditLimitInclude)}">
                                    productsArray.push('${product.name}');
                                </c:forEach>
                                conditions.push(new condition(
                                        conditionId,
                                        ${condition.currency.number},
                                        '${condition.currency.code}',
                                        ${condition.minCreditLimit.id},
                                        ${condition.minCreditLimit.value.decimal},
                                        ${condition.maxCreditLimit.id},
                                        ${condition.maxCreditLimit.value.decimal},
                                        ${condition.maxCreditLimitInclude},
                                        productsArray.join(', ')
                                        ));
                            }
                            dflt = false;
                        </c:forEach>
                    }

                    var creditLimitsByCurrencyArray = new Array();
                    function loadCreditLimits()
                    {
                        var creditLimitsRUB = new Array();
                        var creditLimitsUSD = new Array();
                        var creditLimitsEUR = new Array();

                        <c:forEach var="creditLimit" items="${form.creditLimits}">
                            if(${creditLimit.value.currency.code == 'RUB'})
                                creditLimitsRUB.push(new creditLimit(${creditLimit.id}, ${creditLimit.value.decimal}));
                            if(${creditLimit.value.currency.code == 'USD'})
                                creditLimitsUSD.push(new creditLimit(${creditLimit.id}, ${creditLimit.value.decimal}));
                            if(${creditLimit.value.currency.code == 'EUR'})
                                creditLimitsEUR.push(new creditLimit(${creditLimit.id}, ${creditLimit.value.decimal}));
                        </c:forEach>
                        creditLimitsByCurrencyArray.push(new creditLimitsByCurrency('RUB', creditLimitsRUB));
                        creditLimitsByCurrencyArray.push(new creditLimitsByCurrency('USD', creditLimitsUSD));
                        creditLimitsByCurrencyArray.push(new creditLimitsByCurrency('EUR', creditLimitsEUR));
                    }

                    function openEditConditionWindow()
                    {
                        var url = "${phiz:calculateActionURL(pageContext,'/creditcards/incomes/edit_condition.do')}";
                        var winpar = "resizable=1,menubar=1,toolbar=0,scrollbars=1"+
                                ",width=1000" +
                                ",height=0" + (screen.height - 635) +
                                ",left=0" + ((screen.width) / 2 - 400) +
                                ",top=0" + 100;
                        window.open(url, "EditIncomeCondition", winpar);
                    }

                    function refreshConditions()
                    {
                        var table;
                        for(var i = 0; i < conditions.length; ++i)
                        {
                            table = ensureElement("condition"+i);
                            findChildByClassName(table, "conditionId").value = conditions[i].conditionId;
                            findChildByClassName(table, "currencyNumber").value = conditions[i].curNumber;
                            findChildByClassName(table, "curNumber").innerHTML = conditions[i].curNumber == 643 ? 810 : conditions[i].curNumber;
                            var curCodes = findChildsByClassName(table, "curCode", 5);
                            for(var j = 0; j < curCodes.length; ++j)
                                curCodes[j].innerHTML = conditions[i].curCode;
                            findChildByClassName(table, "minCreditLimitId").value = conditions[i].minCreditLimitId;
                            findChildByClassName(table, "minCreditLimitValue").value = conditions[i].minCreditLimitValue;
                            findChildByClassName(table, "maxCreditLimitId").value = conditions[i].maxCreditLimitId;
                            findChildByClassName(table, "maxCreditLimitValue").value = conditions[i].maxCreditLimitValue;
                            findChildByClassName(table, "isMaxCreditLimitInclude").checked = conditions[i].isMaxCreditLimitInclude ? "true" : "";
                            findChildByClassName(table, "maxCreditLimitInclude").value = conditions[i].isMaxCreditLimitInclude ? "true" : "false";
                            findChildByClassName(table, "availableProducts").innerHTML = conditions[i].availableProducts;

                            table.style.display = "block";
                        }
                        for(var i = conditions.length; i < 3; ++i)
                        {
                            table = ensureElement("condition"+i);
                            findChildByClassName(table, "currencyNumber").value = "";
                            table.style.display = "none";
                        }
                        ensureElement("addConditionButton").style.display = conditions.length < 3 ? "block" : "none";
                        ensureElement("conditionNullText").style.display = conditions.length == 0 ? "" : "none";
                    }

                    function addCondition()
                    {
                        openEditConditionWindow();
                    }

                    var editingCondition = null;

                    function editCondition(divCount)
                    {
                        var div = ensureElement("condition"+divCount);
                        var curNumber = findChildByClassName(div, "curNumber").innerHTML == 810 ? 643 : findChildByClassName(div, "curNumber").innerHTML;
                        for(var i = 0; i < conditions.length; ++i)
                            if(conditions[i].curNumber == curNumber){
                                editingCondition = conditions[i];
                                break;
                            }
                        openEditConditionWindow();
                    }

                    function removeCondition(divCount)
                    {
                        if(!confirm("Вы действительно хотите удалить доступный кредитный лимит для данной валюты?"))
                            return;

                        var div = ensureElement("condition"+divCount);
                        var curNumber = findChildByClassName(div, "curNumber").innerHTML == 810 ? 643 : findChildByClassName(div, "curNumber").innerHTML;
                        var i;
                        for(i = 0; i < conditions.length; ++i)
                            if(conditions[i].curNumber == curNumber){
                                break;
                            }
                        conditions.splice(i, 1);
                        refreshConditions();
                    }

                    //Все следующие функции вызываются из editCondition.jsp
                    function getEditingCondition()
                    {
                        var tmp = editingCondition;
                        editingCondition = null;
                        return tmp;
                    }

                    function getAvailableCurrencies()
                    {
                        var currencies = new Array();
                        //разрешаем добавлять условия только в тех валютах, по которым еще нет условий.
                        var used = false; //условие для этой валюты уже есть в conditions
                        <c:forEach var="currency" items="${form.currencies}">
                            for(var i = 0; i < conditions.length; ++i)
                                if(conditions[i].curNumber == ${currency.number}){
                                    used = true;
                                    break;
                                }

                            if(!used) currencies.push(new currency(${currency.number}, '${currency.code}'));
                            used = false;
                        </c:forEach>
                        return currencies;
                    }

                    function getCreditLimitsByCurrency(curCode)
                    {
                        for(var i = 0; i < creditLimitsByCurrencyArray.length; ++i)
                            if(creditLimitsByCurrencyArray[i].curCode == curCode)
                                return creditLimitsByCurrencyArray[i].creditLimits;
                        return new Array();
                    }

                    function addOrUpdateCondition(editingCondition)
                    {
                        var isNew = true;
                        for(var i = 0; i < conditions.length; ++i)
                            if(conditions[i].curNumber == editingCondition.curNumber){
                                conditions[i] = new conditionCopy(editingCondition);
                                isNew = false;
                                break;
                            }
                        if(isNew) conditions.push(new conditionCopy(editingCondition));
                        refreshConditions();
                        
                        //Для пришедшего условия получаем с помощью ajax-а список доступных карточных кредитных продуктов.
                        var url = "${phiz:calculateActionURL(pageContext,'/creditcards/incomes/products_list')}";
                        var params = "currency=" + editingCondition.curCode + "&creditLimit=" +  editingCondition.maxCreditLimitValue + "&include=" + editingCondition.isMaxCreditLimitInclude;
                        ajaxQuery(
                            params,
                            url,
                            function(data){
                                var availableProducts = trim(data);
                                for(var i = 0; i < conditions.length; ++i)
                                    if(conditions[i].curNumber == editingCondition.curNumber)
                                        conditions[i].setAvailableProducts(availableProducts);
                                refreshConditions();
                            }
                        );
                    }
                </script>

                <tiles:put name="data">

                    <%--Общие условия--%>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.income.average.level" bundle="${bundle}"/><span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <span class="bold"><bean:message key="label.product.from" bundle="${bundle}"/></span>
                                <html:text property="field(minIncome)" size="10" maxlength="10" styleClass="moneyField"/>
                                <b>RUB</b>
                                <span class="bold"><bean:message key="label.product.to" bundle="${bundle}"/></span>
                                <html:text property="field(maxIncome)" size="10" maxlength="10" styleClass="moneyField"/>
                                <b>RUB</b>

                                <html:checkbox property="field(isMaxIncomeInclude)"/>
                                <bean:message key="label.inclusive" bundle="${bundle}"/>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <%--Условия в разрезе валют--%>
                    <div class="buttonMargin" id="addConditionButton">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.add.condition"/>
                            <tiles:put name="commandHelpKey" value="button.add.condition"/>
                            <tiles:put name="bundle" value="${bundle}"/>
                            <tiles:put name="viewType" value="buttonGrayNew"/>
                            <tiles:put name="onclick">
                                addCondition();
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <div class="clear"></div>
                    <div id="conditionNullText" style="display:none;">
                        <fieldset class="fieldsetPadding">
                            Для того, чтобы задать доступный кредитный лимит в одной или нескольких валютах, нажмите на кнопку «Добавить условия».
                        </fieldset>
                    </div>

                    <c:forEach var="currency" items="${form.currencies}" varStatus="currencyCounter">
                        <fieldset id="condition${currencyCounter.count-1}" style="display:none">
                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.product.currency" bundle="${bundle}"/>
                                </div>
                                <div class="paymentValue">
                                    <div class="paymentInputDiv">
                                        <input type="hidden" class="conditionId bold" name="conditionId[]"/>
                                        <input type="hidden" class="currencyNumber bold" name="currencyNumber[]"/>
                                        <span class="curNumber bold"></span>(<span class="curCode bold"></span>)
                                        <a class="blueGrayLink" onclick="editCondition(${currencyCounter.count-1});"><bean:message key="button.edit" bundle="${bundle}"/></a>
                                        <a class="blueGrayLink" onclick="removeCondition(${currencyCounter.count-1});"><bean:message key="button.remove" bundle="${bundle}"/></a>
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </div>

                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.income.available.credit.limit" bundle="${bundle}"/>
                                </div>
                                <div class="paymentValue">
                                    <span class="bold"><bean:message key="label.product.from" bundle="${bundle}"/></span>
                                    <input type="hidden" class="minCreditLimitId" name="minCreditLimitId[]"/>
                                    <input type="text" class="minCreditLimitValue" readonly="true" size="10"/>
                                    <span class="curCode bold"></span>

                                    <span class="bold"><bean:message key="label.product.to" bundle="${bundle}"/></span>
                                    <input type="hidden" class="maxCreditLimitId" name="maxCreditLimitId[]"/>
                                    <input type="text" class="maxCreditLimitValue" readonly="true" size="10"/>
                                    <span class="curCode bold"></span>
                                    <input type="hidden" class="maxCreditLimitInclude" name="maxCreditLimitInclude[]"/>
                                    <input type="checkbox" class="isMaxCreditLimitInclude" onclick="return false;"/>
                                    <span class="LabelAll lowercase"><bean:message key="label.inclusive" bundle="${bundle}"/></span>
                                </div>
                                <div class="clear"></div>
                            </div>

                            <div class="form-row">
                                <div class="paymentLabel">
                                    <bean:message key="label.income.available.products" bundle="${bundle}"/>
                                </div>
                                <div class="paymentValue">
                                    <div class="paymentInputDiv">
                                        <span class="availableProducts"></span>
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </div>

                        </fieldset>
                    </c:forEach>
			    </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle"  value="${bundle}"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle"  value="${bundle}"/>
                        <tiles:put name="action"  value="/creditcards/incomes/list.do"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
		</tiles:put>

	</tiles:insert>
</html:form>