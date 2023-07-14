<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<html:form action="/creditcards/incomes/edit_condition" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="creditcardsBundle"/>

    <tiles:insert definition="dictionary">
        <tiles:put name="submenu" type="string" value="IncomeLevels"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="EditIncomeCondition"/>
                <tiles:put name="name">
                    <bean:message key="title.income.condition.edit" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="title.income.condition.edit.description" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        doOnLoad(function() {
                            init();
                        });

                        function init()
                        {
                            updateEditingCondition();
                            loadCurrencies();
                            changeCurrency();
                            checkIsMaxCreditLimitInclude();
                        }

                        function condition(conditionId, curNumber, curCode, minCreditLimitId, minCreditLimitValue, maxCreditLimitId, maxCreditLimitValue, isMaxCreditLimitInclude)
                        {
                            this.conditionId = conditionId;
                            this.curNumber = curNumber;
                            this.curCode = curCode;
                            this.minCreditLimitId = minCreditLimitId;
                            this.minCreditLimitValue = minCreditLimitValue;
                            this.maxCreditLimitId = maxCreditLimitId;
                            this.maxCreditLimitValue = maxCreditLimitValue;
                            this.isMaxCreditLimitInclude = isMaxCreditLimitInclude;
                        }
                        function currency(number, code)
                        {
                            this.number = number;
                            this.code = code;
                        }

                        var editingCondition; //редактируемое условие
                        var currencies = new Array();

                        function updateEditingCondition()
                        {
                            editingCondition = window.opener.getEditingCondition();
                            if(editingCondition == null) return;
                            //заполняем поля данными из пришедшего условия
                            ensureElement("conditionId").value = editingCondition.conditionId;
                            var currencySelect = ensureElement("currencySelect");
                            currencySelect.options.add(new Option((editingCondition.curNumber == 643 ? 810 : editingCondition.curNumber) + "(" + editingCondition.curCode + ")", editingCondition.curCode));
                            currencies.push(new currency(editingCondition.curNumber, editingCondition.curCode));
                            ensureElement("isMaxCreditLimitInclude").checked = editingCondition.isMaxCreditLimitInclude ? "true" : "";
                            //кредитные лимиты загружаются в changeCurrency()
                        }

                        //Загружаем список доступных валют. Только при добавлении нового условия. При редактировании валюта одна.
                        function loadCurrencies()
                        {
                            if(editingCondition != null) return;
                            currencies = window.opener.getAvailableCurrencies();
                            var currencySelect = ensureElement("currencySelect");
                            for(var i = 0; i < currencies.length; ++i)
                                currencySelect.options.add(new Option((currencies[i].number == 643 ? 810 : currencies[i].number) + "(" + currencies[i].code + ")", currencies[i].code));
                        }

                        function changeCurrency()
                        {
                            var currencySelect = ensureElement("currencySelect");
                            var curCode = currencySelect.options[currencySelect.selectedIndex].value;

                            var curCodes = findChildsByClassName(ensureElement("tableCondition"), "curCode", 2);
                            for(var j = 0; j < curCodes.length; ++j)
                                curCodes[j].innerHTML = curCode;

                            //загрузка кредитных лимитов по выбранной валюте
                            var creditLimits = window.opener.getCreditLimitsByCurrency(curCode);
                            var minCreditLimitSelect = ensureElement("minCreditLimitSelect");
                            var maxCreditLimitSelect = ensureElement("maxCreditLimitSelect");
                            minCreditLimitSelect.options.length = 0;
                            maxCreditLimitSelect.options.length = 0;
                            if (creditLimits.length == 0)
                                alert('Для того, чтобы задать условия для  доступного кредитного лимита в ' +
                                      'данной валюте необходимо сначала задать значения кредитных лимитов в ' +
                                      'справочнике лимитов для этой валюты');
                            for(var i = 0; i < creditLimits.length; ++i)
                            {
                                minCreditLimitSelect.options.add(new Option(creditLimits[i].value, creditLimits[i].id));
                                if(editingCondition != null && editingCondition.minCreditLimitId == creditLimits[i].id)
                                    minCreditLimitSelect.selectedIndex = i;
                                maxCreditLimitSelect.options.add(new Option(creditLimits[i].value, creditLimits[i].id));
                                if(editingCondition != null && editingCondition.maxCreditLimitId == creditLimits[i].id)
                                    maxCreditLimitSelect.selectedIndex = i;
                            }
                        }

                        //по-умолчанию верхний кредитный лимит включен
                        function checkIsMaxCreditLimitInclude()
                        {
                            if(editingCondition == null)
                                ensureElement("isMaxCreditLimitInclude").checked = true;
                        }

                        function sendCondition()
                        {
                            var conditionId = ensureElement("conditionId").value;
                            var minCreditLimitSelect = ensureElement("minCreditLimitSelect");
                            var minCreditLimitId = minCreditLimitSelect.options[minCreditLimitSelect.selectedIndex].value;
                            var minCreditLimitValue = minCreditLimitSelect.options[minCreditLimitSelect.selectedIndex].text;
                            var maxCreditLimitSelect = ensureElement("maxCreditLimitSelect");
                            var maxCreditLimitId = maxCreditLimitSelect.options[maxCreditLimitSelect.selectedIndex].value;
                            var maxCreditLimitValue = maxCreditLimitSelect.options[maxCreditLimitSelect.selectedIndex].text;
                            var isMaxCreditLimitInclude = ensureElement("isMaxCreditLimitInclude").checked;

                            if(parseFloat(minCreditLimitValue) >= parseFloat(maxCreditLimitValue))
                            {
                                alert("Вы неправильно указали доступный кредитный лимит. Пожалуйста, проверьте сумму минимального и максимального доступного кредитного лимита. Минимальный лимит должен быть меньше максимального.");
                                return;
                            }

                            var currencySelect = ensureElement("currencySelect");
                            var curCode = currencySelect.options[currencySelect.selectedIndex].value;
                            var curNumber;
                            for(var i = 0; i < currencies.length; ++i)
                                if(currencies[i].code == curCode){
                                    curNumber = currencies[i].number;
                                    break;
                                }

                            editingCondition = new condition(
                                    conditionId,
                                    curNumber,
                                    curCode,
                                    minCreditLimitId,
                                    minCreditLimitValue,
                                    maxCreditLimitId,
                                    maxCreditLimitValue,
                                    isMaxCreditLimitInclude
                                    );
                            window.opener.addOrUpdateCondition(editingCondition);
                            window.close();
                        }

                        function cancel()
                        {
                            window.close();
                        }
                    </script>

                    <input type="hidden" id="conditionId"/>
                    <div id="tableCondition">
                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.product.currency" bundle="${bundle}"/>
                            </div>
                            <div class="paymentValue">
                                <select id="currencySelect" onchange="changeCurrency();"></select>
                            </div>
                            <div class="clear"></div>
                        </div>

                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.income.available.credit.limit" bundle="${bundle}"/><span class="asterisk">*</span>
                            </div>
                            <div class="paymentValue">
                                <span class="bold centerLine"><bean:message key="label.product.from" bundle="${bundle}"/></span>
                                <select id="minCreditLimitSelect"></select>
                                <span class="curCode bold"></span>

                                <span class="bold"><bean:message key="label.product.to" bundle="${bundle}"/></span>
                                <select id="maxCreditLimitSelect"></select>
                                <span class="curCode bold"></span>
                                <input class="marginLeft" type="checkbox" id="isMaxCreditLimitInclude"/>
                                <span class="lowercase"><bean:message key="label.inclusive" bundle="${bundle}"/></span>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </div>
                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.filter"/>
                        <tiles:put name="commandHelpKey" value="button.filter"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="onclick">sendCondition();</tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="onclick">cancel();</tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>