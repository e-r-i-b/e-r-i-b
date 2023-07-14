<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>
<html:form action="/loans/products/edit" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="loansBundle"/>
    <c:set var="terbankIds" value="${form.terbankIds}"/>

	<tiles:insert definition="loansEdit">
		<tiles:put name="submenu" type="string" value="LoanProducts"/>

		<tiles:put name="menu" type="string">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save.product.help"/>
				<tiles:put name="bundle"  value="${bundle}"/>
		        <tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.list"/>
				<tiles:put name="commandHelpKey"     value="button.list.product.help"/>
				<tiles:put name="bundle"  value="${bundle}"/>
				<tiles:put name="action"  value="/loans/products/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
        <input type="hidden" id="id" name="id" value="${form.id}"/>
			<tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="EditModifiableLoanProduct"/>
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
                        showHideInitialInstalment();
                        checkMaxIncludes();
                        loadCurrencies();
                        loadConditions();
                        refreshConditions();
                        initData();
                    }

                    //////////////////  Общие условия  //////////////////
                    function showHideInitialInstalment()
                    {
                        var needInitialInstalment = ensureElement("needInitialInstalment").value == 'true';
                        var initialInstalmentFields = ensureElement("initialInstalmentFields");
                        initialInstalmentFields.style.display = needInitialInstalment ? "block" : "none";
                    }

                    //по умолчанию флаги "включительно" включены
                    function checkMaxIncludes()
                    {
                        if(ensureElement("id").value == '')
                        {
                            ensureElement("isMaxDurationInclude").checked = true;
                            ensureElement("isMaxInitInstInclude").checked = true;
                        }
                    }

                    //////////////////  Условия в разрезе валют  //////////////////
                    function condition(conditionId, curNumber, curCode, minAmount, maxAmount, isMaxAmountInclude, maxAmountPercent, amountType, minInterestRate, maxInterestRate, isMaxInterestRateInclude)
                    {
                        this.conditionId = conditionId;
                        this.curNumber = curNumber;
                        this.curCode = curCode;
                        this.minAmount = minAmount;
                        this.maxAmount = maxAmount;
                        this.isMaxAmountInclude = isMaxAmountInclude;
                        this.maxAmountPercent = maxAmountPercent;
                        this.amountType = amountType;
                        this.minInterestRate = minInterestRate;
                        this.maxInterestRate = maxInterestRate;
                        this.isMaxInterestRateInclude = isMaxInterestRateInclude;
                    }
                    //конструктор копирования
                    function conditionCopy(condition)
                    {
                        this.conditionId = condition.conditionId;
                        this.curNumber = condition.curNumber;
                        this.curCode = condition.curCode;
                        this.minAmount = condition.minAmount;
                        this.maxAmount = condition.maxAmount;
                        this.isMaxAmountInclude = condition.isMaxAmountInclude;
                        this.maxAmountPercent = condition.maxAmountPercent;
                        this.amountType = condition.amountType;
                        this.minInterestRate = condition.minInterestRate;
                        this.maxInterestRate = condition.maxInterestRate;
                        this.isMaxInterestRateInclude = condition.isMaxInterestRateInclude;
                    }
                    function currency(number, code)
                    {
                        this.number = number;
                        this.code = code;
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
                                var minAmount = ${empty condition.minAmount.decimal} ? '' : '${condition.minAmount.decimal}';
                                var maxAmount = ${empty condition.maxAmount.decimal} ? '' : '${condition.maxAmount.decimal}';
                                var maxAmountPercent = ${empty condition.maxAmountPercent} ? '' : '${condition.maxAmountPercent}';
                                var minInterestRate = ${empty condition.minInterestRate} ? '' : '${condition.minInterestRate}';
                                var maxInterestRate = ${empty condition.maxInterestRate} ? '' : '${condition.maxInterestRate}';
                                conditions.push(new condition(
                                        conditionId,
                                        ${condition.currency.number},
                                        '${condition.currency.code}',
                                        minAmount,
                                        maxAmount,
                                        ${condition.maxAmountInclude},
                                        maxAmountPercent,
                                        '${condition.amountType}',
                                        minInterestRate,
                                        maxInterestRate,
                                        ${condition.maxInterestRateInclude}
                                        ));
                            }
                            dflt = false;
                        </c:forEach>
                    }

                    function openEditConditionWindow()
                    {
                        var url = "${phiz:calculateActionURL(pageContext,'/loans/products/edit_condition.do')}";
                        var winpar = "resizable=1,menubar=1,toolbar=0,scrollbars=1"+
                                ",width=1000" +
                                ",height=0" + (screen.height - 600) +
                                ",left=0" + ((screen.width) / 2 - 450) +
                                ",top=0" + 100;
                        window.open(url, "EditLoanCondition", winpar);
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
                            var curCodes = findChildsByClassName(table, "curCode", 3);
                            for(var j = 0; j < curCodes.length; ++j)
                                curCodes[j].innerHTML = conditions[i].curCode;
                            $(findChildByClassName(table, "minAmount")).setMoneyValue(conditions[i].minAmount);
                            $(findChildByClassName(table, "maxAmount")).setMoneyValue(conditions[i].maxAmount);
                            findChildByClassName(table, "maxAmountPercent").value = conditions[i].maxAmountPercent;
                            findChildByClassName(table, "amountType").value = conditions[i].amountType;
                            findChildByClassName(table, "amountTypeCURRENCY").style.display = (conditions[i].amountType == 'CURRENCY') ? "" : "none";
                            findChildByClassName(table, "amountTypePERCENT").style.display = (conditions[i].amountType == 'PERCENT') ? "" : "none";
                            findChildByClassName(table, "isMaxAmountInclude").checked = conditions[i].isMaxAmountInclude ? "true" : "";
                            findChildByClassName(table, "maxAmountInclude").value = conditions[i].isMaxAmountInclude ? "true" : "false";
                            findChildByClassName(table, "minInterestRate").value = conditions[i].minInterestRate;
                            findChildByClassName(table, "maxInterestRate").value = conditions[i].maxInterestRate;
                            findChildByClassName(table, "isMaxInterestRateInclude").checked = conditions[i].isMaxInterestRateInclude ? "true" : "";
                            findChildByClassName(table, "maxInterestRateInclude").value = conditions[i].isMaxInterestRateInclude ? "true" : "false";
                            table.style.display = "";
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
                        if(!confirm("Вы действительно хотите удалить данные по выбранной валюте?"))
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
                </script>

                <tiles:put name="data">

                    <%--Общие условия--%>
                    <h3><bean:message key="label.product.common.conditions" bundle="${bundle}"/></h3>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.product.kind" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(loanKind)" styleClass="select">
                                <c:forEach var="loanKind" items="${form.loanKinds}">
                                    <html:option value="${loanKind.id}">${loanKind.name}</html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.product.name" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="58" maxlength="100"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.product.duration" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <span class="bold"><bean:message key="label.product.from" bundle="${bundle}"/></span>
                            <html:text property="field(minDurationYears)" size="2" maxlength="2"/>
                            <span class="bold"><bean:message key="label.product.years" bundle="${bundle}"/></span>
                            <span class="LabelAll"><bean:message key="label.product.or.and" bundle="${bundle}"/></span>
                            <html:text property="field(minDurationMonths)" size="3" maxlength="3"/>
                            <span class="bold"><bean:message key="label.product.months" bundle="${bundle}"/></span>
                            <br>
                            <span class="bold"><bean:message key="label.product.to" bundle="${bundle}"/></span>
                            <html:text property="field(maxDurationYears)" size="2" maxlength="2"/>
                            <span class="bold"><bean:message key="label.product.years" bundle="${bundle}"/></span>
                            <span class="LabelAll"><bean:message key="label.product.or.and" bundle="${bundle}"/></span>
                            <span class="bold"><html:text property="field(maxDurationMonths)" size="3" maxlength="3"/></span>
                            <span class="bold"><bean:message key="label.product.months" bundle="${bundle}"/></span>
                            <html:checkbox styleId="isMaxDurationInclude" property="field(isMaxDurationInclude)"/>
                            <span class="lowercase"><bean:message key="label.inclusive" bundle="${bundle}"/></span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.product.initial.instalment" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select styleId="needInitialInstalment" property="field(needInitialInstalment)" onchange="showHideInitialInstalment();">
                                <html:option value="false"><bean:message key="label.product.not.required" bundle="${bundle}"/></html:option>
                                <html:option value="true"><bean:message key="label.product.necessary" bundle="${bundle}"/></html:option>
                            </html:select>
                            <span id="initialInstalmentFields" style="${form.needInitialInstalment == true ? '' : 'display:none'}">
                                <span class="bold"><bean:message key="label.product.from" bundle="${bundle}"/></span>
                                <html:text property="field(minInitialInstalment)" size="3" maxlength="5"/>
                                <span class="bold"><bean:message key="label.product.percent" bundle="${bundle}"/></span>
                                <span class="bold"><bean:message key="label.product.to" bundle="${bundle}"/></span>
                                <html:text property="field(maxInitialInstalment)" size="3" maxlength="5"/>
                                <span class="bold"><bean:message key="label.product.percent.from.cost" bundle="${bundle}"/></span>
                                <html:checkbox styleId="isMaxInitInstInclude" property="field(isMaxInitialInstalmentInclude)"/>
                                <span class="lowercase"><bean:message key="label.inclusive" bundle="${bundle}"/></span>
                            </span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.product.surety" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(needSurety)" styleClass="select">
                                <html:option value="true"><bean:message key="label.product.required" bundle="${bundle}"/></html:option>
                                <html:option value="false"><bean:message key="label.product.not.required" bundle="${bundle}"/></html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.product.additional.terms" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="field(additionalTerms)" cols="38" rows="4"/>
                        </tiles:put>
                    </tiles:insert>

                    <%--Доступ к ТБ--%>
                    <h3><bean:message key="label.product.departments.access" bundle="${bundle}"/></h3>

                    <tiles:insert definition="simpleTableTemplate" flush="false">
                        <tiles:put name="id" value="terbanksList"/>
                        <tiles:put name="grid">
                            <sl:collection id="terbank" model="list" property="allTerbanks">
                                <sl:collectionParam id="selectType" value="checkbox"/>
                                <sl:collectionParam id="selectName" value="terbankIds"/>
                                <sl:collectionParam id="selectProperty" value="id"/>

                                <sl:collectionItem title="Наименование" property="name"/>
                            </sl:collection>
                        </tiles:put>
                        <tiles:put name="isEmpty" value="${empty form.allTerbanks}"/>
                        <tiles:put name="emptyMessage">Не найдено ни одного доступного ТБ!</tiles:put>
                    </tiles:insert>

                    <%--Условия в разрезе валют--%>
                    <h3><bean:message key="label.product.currency.access" bundle="${bundle}"/></h3>

                    <div id="addConditionButton">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.add.condition"/>
                            <tiles:put name="commandHelpKey" value="button.add.condition"/>
                            <tiles:put name="bundle" value="${bundle}"/>
                            <tiles:put name="onclick">
                                addCondition();
                            </tiles:put>
                            <tiles:put name="viewType" value="buttonGrayNew"/>
                        </tiles:insert>
                    </div>
                    <div class="clear"></div>
                    <div id="conditionNullText" style="display:none;">
                        <fieldset class="fieldsetPadding">
                            Для того чтобы задать условия предоставления кредитного продукта в одной или нескольких валютах, нажмите на кнопку «Добавить условия».
                        </fieldset>
                    </div>

                    <c:forEach var="currency" items="${form.currencies}" varStatus="currencyCounter">
                    <div id="condition${currencyCounter.count-1}" style="display:none">
                        <fieldset>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.product.currency" bundle="${bundle}"/>
                                </tiles:put>
                                <tiles:put name="needMargin" value="true"/>
                                <tiles:put name="data">
                                    <input type="hidden" class="conditionId bold" name="conditionId[]"/>
                                    <input type="hidden" class="currencyNumber bold" name="currencyNumber[]"/>
                                    <span class="curNumber bold"></span>(<span class="curCode bold"></span>)
                                    <a href="#" onclick="editCondition(${currencyCounter.count-1});"><bean:message key="button.edit" bundle="${bundle}"/></a>
                                    <a href="#" onclick="removeCondition(${currencyCounter.count-1});"><bean:message key="button.remove" bundle="${bundle}"/></a>
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.product.amount" bundle="${bundle}"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <span class="bold"><bean:message key="label.product.from" bundle="${bundle}"/></span>
                                    <input type="text" class="minAmount moneyField" name="minAmount[]" readonly="true" size="10"/>
                                    <span class="curCode bold"></span>
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <span class="bold"><bean:message key="label.product.to" bundle="${bundle}"/></span>
                                    <input type="hidden" class="amountType" style="display:none;" name="amountType[]" />
                                    <span class="amountTypeCURRENCY">
                                        <input type="text" class="maxAmount moneyField" name="maxAmount[]" readonly="true" size="10"/>
                                        <span class="curCode bold"></span>
                                    </span>
                                    <span class="amountTypePERCENT">
                                        <input type="text" class="maxAmountPercent" name="maxAmountPercent[]" readonly="true" size="5"/>
                                        <span class="bold"><bean:message key="label.product.percent.from.cost" bundle="${bundle}"/></span>
                                    </span>
                                    <input type="hidden" class="maxAmountInclude" name="maxAmountInclude"/>


                                    <input type="checkbox" class="isMaxAmountInclude" onclick="return false;"/>
                                    <span class="LabelAll lowercase"><bean:message key="label.inclusive" bundle="${bundle}"/></span>
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.product.percent.rate" bundle="${bundle}"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <span class="bold"><bean:message key="label.product.from" bundle="${bundle}"/></span>
                                    <input type="text" class="minInterestRate" name="minInterestRate[]" readonly="true" size="10"/>
                                    <span class="bold"><bean:message key="label.product.percent" bundle="${bundle}"/></span>
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <span class="bold"><bean:message key="label.product.to" bundle="${bundle}"/></span>
                                    <input type="text" class="maxInterestRate" name="maxInterestRate[]" readonly="true" size="10"/>
                                    <span class="bold"><bean:message key="label.product.percent" bundle="${bundle}"/></span>
                                    <input type="hidden" class="maxInterestRateInclude" name="maxInterestRateInclude"/>

                                    <input type="checkbox" class="isMaxInterestRateInclude" onclick="return false;"/>
                                    <span class="LabelAll lowercase"><bean:message key="label.inclusive" bundle="${bundle}"/></span>
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>
                    </div>
                    </c:forEach>
			    </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.product.help"/>
                        <tiles:put name="bundle"  value="${bundle}"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"  value="${bundle}"/>
                        <tiles:put name="action"  value="/loans/products/list.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
		</tiles:put>

	</tiles:insert>
</html:form>