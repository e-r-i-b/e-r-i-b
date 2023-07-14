<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<tiles:importAttribute/>
<html:form action="/creditcards/products/edit" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="creditcardsBundle"/>

	<tiles:insert definition="creditCardsEdit">
		<tiles:put name="submenu" type="string" value="CreditCardProducts"/>

		<tiles:put name="menu" type="string">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save"/>
				<tiles:put name="bundle"  value="${bundle}"/>
		        <tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.list"/>
				<tiles:put name="commandHelpKey"     value="button.list"/>
				<tiles:put name="bundle"  value="${bundle}"/>
				<tiles:put name="action"  value="/creditcards/products/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
			<tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="EditCreditCardProduct"/>
                <tiles:put name="name">
                    <bean:message key="title.product.edit" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="title.product.edit.description" bundle="${bundle}"/>
                </tiles:put>

                <script type="text/javascript">
                    doOnLoad(function() {
                        init();
                    });

                    function init()
                    {
                        switchAllowGracePeriod();
                        loadCurrencies();
                        loadConditions();
                        loadCreditLimits();
                        refreshConditions();
                        initData();
                        changeAvailableDefault();
                    }

                    function setUseForPreapprovedOffers()
                    {
                        if($('#defaultForPreapprovedOffers').attr('checked')){
                            $('#useForPreapprovedOffers').attr('checked', 'checked');
                        }
                    }

                    //////////////////  Общие условия  //////////////////
                    function switchAllowGracePeriod()
                    {
                        var allowGracePeriod = ensureElement("allowGracePeriod").checked;
                        ensureElement("gracePeriodDuration").readOnly = !allowGracePeriod;
                        ensureElement("gracePeriodInterestRate").readOnly = !allowGracePeriod;
                    }

                    //////////////////  Условия в разрезе валют  //////////////////
                    function condition(conditionId, curNumber, curCode, minCreditLimitId, minCreditLimitValue, maxCreditLimitId, maxCreditLimitValue, isMaxCreditLimitInclude, interestRate,offerInterestRate, firstYearPayment, nextYearPayment)
                    {
                        this.conditionId = conditionId;
                        this.curNumber = curNumber;
                        this.curCode = curCode;
                        this.minCreditLimitId = minCreditLimitId;
                        this.minCreditLimitValue = minCreditLimitValue;
                        this.maxCreditLimitId = maxCreditLimitId;
                        this.maxCreditLimitValue = maxCreditLimitValue;
                        this.isMaxCreditLimitInclude = isMaxCreditLimitInclude;
                        this.interestRate = interestRate;
                        this.offerInterestRate = offerInterestRate;
                        this.firstYearPayment = firstYearPayment;
                        this.nextYearPayment = nextYearPayment;
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
                        this.interestRate = condition.interestRate;
                        this.offerInterestRate = condition.offerInterestRate;
                        this.firstYearPayment = condition.firstYearPayment;
                        this.nextYearPayment = condition.nextYearPayment;
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
                                conditions.push(new condition(
                                conditionId,
                                ${condition.currency.number},
                                '${condition.currency.code}',
                                ${condition.minCreditLimit.id},
                                ${condition.minCreditLimit.value.decimal},
                                ${condition.maxCreditLimit.id},
                                ${condition.maxCreditLimit.value.decimal},
                                ${condition.maxCreditLimitInclude},
                                ${condition.interestRate},
                                ${condition.offerInterestRate},
                                ${condition.firstYearPayment.decimal},
                                ${condition.nextYearPayment.decimal}
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
                        var url = "${phiz:calculateActionURL(pageContext,'/creditcards/products/edit_condition.do')}";
                        var winpar = "resizable=1,menubar=1,toolbar=0,scrollbars=1"+
                                ",width=1000" +
                                ",height=0" + (screen.height - 635) +
                                ",left=0" + ((screen.width) / 2 - 400) +
                                ",top=0" + 100;
                        window.open(url, "EditCreditCardCondition", winpar);
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
                            findChildByClassName(table, "interestRate").value = conditions[i].interestRate;
                            findChildByClassName(table, "offerInterestRate").value = conditions[i].offerInterestRate;
                            $(findChildByClassName(table, "firstYearPayment")).setMoneyValue(conditions[i].firstYearPayment);
                            $(findChildByClassName(table, "nextYearPayment")).setMoneyValue(conditions[i].nextYearPayment);
                            table.style.display = "block";
                        }
                        for(var i = conditions.length; i < 3; ++i)
                        {
                            table = ensureElement("condition"+i);
                            findChildByClassName(table, "currencyNumber").value = "";
                            table.style.display = "none";
                        }
                        ensureElement("addConditionButton").style.display = conditions.length < 3 ? "" : "none";
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
                        if(!confirm("Вы действительно хотите удалить условия кредитного продукта для данной валюты?"))
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
                    }

                    function keypress(e)
                    {
                        var kCode;
                        if (e.charCode){
                            kCode = e.charCode;
                        }else{
                            kCode = e.keyCode;
                        }
                        <%--Backspace и delet пропускаем--%>
                        if (kCode == 8 || kCode == 46)
                            return true

                        return /[0-9@]/.test(String.fromCharCode(kCode));
                    }

                    function changeAvailableDefault()
                    {
                        var defaultForPreapprovedOffers = $('input[name="field(defaultForPreapprovedOffers)"]');
                        if ($('input[name="field(useForPreapprovedOffers)"]').is(':checked'))
                        {
                            $(defaultForPreapprovedOffers).removeAttr('disabled');
                        }
                        else
                        {
                            $(defaultForPreapprovedOffers).attr('disabled','disabled');
                            $(defaultForPreapprovedOffers).attr('checked', false);
                        }
                    }
                </script>

                <tiles:put name="data">

                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.product.name" bundle="${bundle}"/><span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text property="field(name)" size="50" maxlength="100"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="form-row">
                        <div class="paymentLabel"></div>
                        <div class="paymentValue">
                            <html:checkbox styleId="allowGracePeriod" property="field(allowGracePeriod)" onchange="switchAllowGracePeriod();"/><span class="lowercase"><bean:message key="label.product.allow.grace.period" bundle="${bundle}"/></span>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.product.grace.period" bundle="${bundle}"/>&nbsp;<bean:message key="label.product.to" bundle="${bundle}"/><span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text styleId="gracePeriodDuration" property="field(gracePeriodDuration)" size="3" maxlength="3"/>
                            <span class="bold"><bean:message key="label.product.calendar.days" bundle="${bundle}"/></span>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.product.grace.period.interest.rate" bundle="${bundle}"/><span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text styleId="gracePeriodInterestRate" property="field(gracePeriodInterestRate)" size="3" maxlength="5"/>
                            <span class="bold"><bean:message key="label.product.percent" bundle="${bundle}"/></span>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.product.card.type.code" bundle="${bundle}"/><span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text property="field(cardTypeCode)" size="3" maxlength="3" onkeypress="return keypress(event);"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.product.additional.terms" bundle="${bundle}"/>
                        </div>
                        <div class="paymentValue">
                            <html:textarea property="field(additionalTerms)" cols="38" rows="4"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="form-row">
                        <div class="paymentLabel"></div>
                        <div class="paymentValue">
                            <html:checkbox styleId="useForPreapprovedOffers" property="field(useForPreapprovedOffers)"/><span class="lowercase"><bean:message key="label.product.for.preapproved.offers" bundle="${bundle}"/></span>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="form-row">
                        <div class="paymentLabel"></div>
                        <div class="paymentValue">
                            <html:checkbox styleId="defaultForPreapprovedOffers" property="field(defaultForPreapprovedOffers)"  onchange="setUseForPreapprovedOffers();"/><span class="lowercase"><bean:message key="label.product.default.in.preapproved.offers" bundle="${bundle}"/></span>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <html:text property="field(productId)" style="display:none"/>

                    <%--Условия в разрезе валют--%>
                    <div id="addConditionButton">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.add.condition"/>
                            <tiles:put name="commandHelpKey" value="button.add.condition"/>
                            <tiles:put name="bundle" value="${bundle}"/>
                            <tiles:put name="onclick">
                                addCondition();
                            </tiles:put>
                        </tiles:insert>
                    </div>
                    <div class="clear"></div>
                    <div id="conditionNullText" style="display:none;">
                        <fieldset class="fieldsetPadding">
                            Для того, чтобы задать условия предоставления карточного кредитного продукта в одной или нескольких валютах, нажмите на кнопку «Добавить условия».
                        </fieldset>
                    </div>

                    <tr>
                        <td colspan="3">


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
                                            <bean:message key="label.product.credit.limit" bundle="${bundle}"/>
                                        </div>
                                        <div class="paymentValue">
                                            <span class="bold"><bean:message key="label.product.from" bundle="${bundle}"/></span>
                                            <input type="hidden" class="minCreditLimitId" name="minCreditLimitId[]"/>
                                            <input type="text" class="minCreditLimitValue" readonly="true" size="10"/>
                                            <span class="curCode bold"></span>
                                            &nbsp;
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
                                            <bean:message key="label.product.percent.rate" bundle="${bundle}"/>
                                        </div>
                                        <div class="paymentValue">
                                            <input type="text" class="interestRate" name="interestRate[]" readonly="true" size="10"/>
                                            <span class="bold"><bean:message key="label.product.percent" bundle="${bundle}"/></span>
                                        </div>
                                        <div class="clear"></div>
                                    </div>

                                    <div class="form-row">
                                        <div class="paymentLabel">
                                            <bean:message key="label.product.offer.percent.rate" bundle="${bundle}"/>
                                        </div>
                                        <div class="paymentValue">
                                            <input type="text" class="offerInterestRate" name="offerInterestRate[]" readonly="true" size="10"/>
                                            <span class="bold"><bean:message key="label.product.percent" bundle="${bundle}"/></span>
                                        </div>
                                        <div class="clear"></div>
                                    </div>

                                    <div>
                                        <bean:message key="label.product.year.payment" bundle="${bundle}"/>:
                                    </div>

                                    <div class="form-row">
                                        <div class="paymentLabel">
                                            <bean:message key="label.product.first.year" bundle="${bundle}"/>
                                        </div>
                                        <div class="paymentValue">
                                            <input type="text" class="firstYearPayment moneyField" name="firstYearPayment[]" readonly="true" size="10"/>
                                            <span class="curCode bold"></span>
                                        </div>
                                        <div class="clear"></div>
                                    </div>

                                    <div class="form-row">
                                        <div class="paymentLabel">
                                            <bean:message key="label.product.next.year" bundle="${bundle}"/>
                                        </div>
                                        <div class="paymentValue">
                                            <input type="text" class="nextYearPayment moneyField" name="nextYearPayment[]" readonly="true" size="10"/>
                                            <span class="curCode bold"></span>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </fieldset>
                            </c:forEach>
                        </td>
                    </tr>
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
                        <tiles:put name="action"  value="/creditcards/products/list.do"/>
                    </tiles:insert>

                    <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/async/creditcards/products/language/save')}"/>
                    <tiles:insert definition="languageSelectForEdit" flush="false">
                        <tiles:put name="selectId" value="chooseLocale"/>
                        <tiles:put name="entityId" value="${form.id}"/>
                        <tiles:put name="styleClass" value="float"/>
                        <tiles:put name="selectTitleOnclick" value="openEditLocaleWin(this);"/>
                        <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
		</tiles:put>

	</tiles:insert>
</html:form>