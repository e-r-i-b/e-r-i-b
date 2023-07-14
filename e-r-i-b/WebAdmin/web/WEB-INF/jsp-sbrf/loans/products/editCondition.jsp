<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<html:form action="/loans/products/edit_condition" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="loansBundle"/>

    <tiles:insert definition="dictionary">
        <tiles:put name="submenu" type="string" value="LoanProducts"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="EditLoanCondition"/>
                <tiles:put name="name">
                    <bean:message key="title.condition.edit" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="title.condition.edit.description" bundle="${bundle}"/>
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
                            changeAmountType();
                            checkMaxIncludes();
                        }

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
                        function currency(number, code)
                        {
                            this.number = number;
                            this.code = code;
                        }

                        var editingCondition; //редактируемое (добавляемое) условие
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
                            $(ensureElement("minAmount")).setMoneyValue(editingCondition.minAmount);
                            $(ensureElement("maxAmount")).setMoneyValue(editingCondition.maxAmount);
                            ensureElement("maxAmountPercent").value = editingCondition.maxAmountPercent;
                            var amountTypeSelect = ensureElement("amountTypeSelect");
                            for(var i = 0; i < amountTypeSelect.options.length; ++i)
                                if(amountTypeSelect.options[i].value == editingCondition.amountType){
                                    amountTypeSelect.selectedIndex = i;
                                    break;
                                }
                            ensureElement("isMaxAmountInclude").checked = editingCondition.isMaxAmountInclude ? "true" : "";
                            ensureElement("minInterestRate").value = editingCondition.minInterestRate;
                            ensureElement("maxInterestRate").value = editingCondition.maxInterestRate;
                            ensureElement("isMaxInterestRateInclude").checked = editingCondition.isMaxInterestRateInclude ? "true" : "";
                        }

                        //Загружем список доступных валют. Только при добавлении нового условия. При редактировании валюта одна.
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
                            var minCurrency = ensureElement("minAmountCurCode");
                            var maxCurrency = ensureElement("maxAmountCurCode");
                            minCurrency.innerHTML = maxCurrency.innerHTML = currencySelect.options[currencySelect.selectedIndex].value;
                        }

                        function changeAmountType()
                        {
                            var amountTypeSelect = ensureElement("amountTypeSelect");
                            ensureElement("amountTypeCURRENCY").style.display = (amountTypeSelect.options[amountTypeSelect.selectedIndex].value == "CURRENCY") ? "" : "none";
                            ensureElement("amountTypePERCENT").style.display = (amountTypeSelect.options[amountTypeSelect.selectedIndex].value == "PERCENT") ? "" : "none";
                        }

                        //по умолчанию флаги "включительно" включены 
                        function checkMaxIncludes()
                        {
                            if(editingCondition == null)
                            {
                                ensureElement("isMaxAmountInclude").checked = true;
                                ensureElement("isMaxInterestRateInclude").checked = true;
                            }
                        }

                            // Проверка, что введенные проценты входят в интервал [0,100]
                        function isCorrectPercent(percent)
                        {
                            return (percent > 0 && percent <= 100);
                        }
                        
                        function sendCondition()
                        {
                            var conditionId = ensureElement("conditionId").value;
                            var minAmount = getStringWithoutSpace(ensureElement("minAmount").value);
                            var maxAmount = getStringWithoutSpace(ensureElement("maxAmount").value);
                            var maxAmountPercent = trim(ensureElement("maxAmountPercent").value);
                            var amountTypeSelect = ensureElement("amountTypeSelect");
                            var amountType = amountTypeSelect.options[amountTypeSelect.selectedIndex].value;
                            var isMaxAmountInclude = ensureElement("isMaxAmountInclude").checked;
                            var minInterestRate = trim(ensureElement("minInterestRate").value);
                            var maxInterestRate = trim(ensureElement("maxInterestRate").value);
                            var isMaxInterestRateInclude = ensureElement("isMaxInterestRateInclude").checked;

                            minAmount = minAmount.replace(new RegExp(",",'g'), ".");
                            maxAmount = maxAmount.replace(new RegExp(",",'g'), ".");
                            maxAmountPercent = maxAmountPercent.replace(new RegExp(",",'g'), ".");
                            minInterestRate = minInterestRate.replace(new RegExp(",",'g'), ".");
                            maxInterestRate = maxInterestRate.replace(new RegExp(",",'g'), ".");
                            
                            var regexCurrency = new RegExp(/^\s*(((\d{1,10}(\.\d{0,2})?))|((\d{1,10}(\,\d{0,2})?)))?\s*$/);
                            var regexPercent = new RegExp(/^\s*(((\d{0,3}(\.\d{0,2})?))|((\d{0,3}(\,\d{0,2})?)))\s*$/);

                            //сумма продукта
                            if(!regexCurrency.test(minAmount)
                                    || (amountType == "CURRENCY" && !regexCurrency.test(maxAmount))
                                    || (amountType == "PERCENT" && !regexPercent.test(maxAmountPercent)))
                            {
                                alert("Некорректо заполнена сумма продукта. Формат для денежных и процентных величин: ###.##.");
                                return;
                            }
                            else if(minAmount == ""
                                    && ((amountType == "CURRENCY" && maxAmount == "")
                                        || (amountType == "PERCENT" && maxAmountPercent == "")))
                            {
                                alert("Вы неправильно указали сумму продукта. Пожалуйста, введите минимальную и максимальную сумму продукта. Должно быть задано хотя бы одно значение.");
                                return;
                            }
                            else if(parseFloat(minAmount) == 0 || (amountType == "CURRENCY" && parseFloat(maxAmount) == 0) || (amountType == "PERCENT" && parseFloat(maxAmountPercent) == 0))
                            {
                                alert("Сумма продукта не может быть равна нулю.");
                                return;
                            }
                            else if(amountType == "CURRENCY" && minAmount != "" && maxAmount != "" && parseFloat(minAmount) >= parseFloat(maxAmount))
                            {
                                alert("Вы неправильно указали сумму продукта. Пожалуйста, проверьте минимальную и максимальную сумму продукта. Минимальная сумма должна быть меньше максимальной.");
                                return;
                            }

                            //процентная ставка
                            if(!regexPercent.test(minInterestRate) || !regexPercent.test(maxInterestRate))
                            {
                                alert("Некорректо заполнена процентная ставка. Формат для процентных величин: ###.##.");
                                return;
                            }
                            else if(minInterestRate == "" && maxInterestRate == "")
                            {
                                alert("Вы неправильно указали процентную ставку кредита. Пожалуйста, введите минимальную и максимальную ставку. Должно быть задано хотя бы одно значение.");
                                return;
                            }
                            else  if ((maxInterestRate != "" && !isCorrectPercent(maxInterestRate)) || (minInterestRate != "" && !isCorrectPercent(minInterestRate)) )
                            {
                                alert("Некорректно заполнена процентная ставка. Значение процентной ставки не может быть меньше либо равна 0 или больше 100");
                                return;
                            }
                            else if(minInterestRate != "" && maxInterestRate != "" && parseFloat(minInterestRate) >= parseFloat(maxInterestRate))
                            {
                                alert("Вы не правильно указали процентную ставку кредита. Пожалуйста, проверьте минимальную и максимальную ставку. Минимальная процентная ставка должна быть меньше максимальной.");
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
                                    minAmount,
                                    maxAmount,
                                    isMaxAmountInclude,
                                    maxAmountPercent,
                                    amountType,
                                    minInterestRate,
                                    maxInterestRate,
                                    isMaxInterestRateInclude
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

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.product.currency" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <select id="currencySelect" onchange="changeCurrency();"></select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.product.amount" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <span class="bold"><bean:message key="label.product.from" bundle="${bundle}"/></span>

                            <input type="text" id="minAmount" size="10" maxlength="15" class="moneyField"/>
                            <span id="minAmountCurCode" class="bold"></span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <span class="bold"><bean:message key="label.product.to" bundle="${bundle}"/></span>

                            <span id="amountTypeCURRENCY" class="bold">
                                <input type="text" id="maxAmount" size="10" maxlength="15" class="moneyField"/>
                            </span>
                            <span id="amountTypePERCENT" class="bold">
                                <input type="text" id="maxAmountPercent" size="10" maxlength="15"/>
                            </span>

                            <select id="amountTypeSelect" onchange="changeAmountType();">
                                <option value="CURRENCY" id="maxAmountCurCode"><bean:message key="label.product.in.currency" bundle="${bundle}"/></option>
                                <option value="PERCENT"><bean:message key="label.product.percent.from.cost" bundle="${bundle}"/></option>
                            </select>

                            <input type="checkbox" id="isMaxAmountInclude"/>
                            <span class="lowercase"><bean:message key="label.inclusive" bundle="${bundle}"/></span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.product.percent.rate" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <span class="bold"><bean:message key="label.product.from" bundle="${bundle}"/></span>

                            <input type="text" id="minInterestRate" size="3" maxlength="5"/>
                            <span class="bold"><bean:message key="label.product.percent" bundle="${bundle}"/></span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <span class="bold"><bean:message key="label.product.to" bundle="${bundle}"/></span>

                            <input type="text" id="maxInterestRate" size="3" maxlength="5"/>
                            <span class="bold"><bean:message key="label.product.percent" bundle="${bundle}"/></span>

                            <input type="checkbox" id="isMaxInterestRateInclude"/>
                            <span class="lowercase"><bean:message key="label.inclusive" bundle="${bundle}"/></span>
                        </tiles:put>
                    </tiles:insert>
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
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="onclick">cancel();</tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>