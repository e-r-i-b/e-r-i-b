<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<html:form action="/creditcards/products/edit_condition" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="creditcardsBundle"/>

    <tiles:insert definition="dictionary">
        <tiles:put name="submenu" type="string" value="CreditCardProducts"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="EditCreditCardCondition"/>
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
                            checkIsMaxCreditLimitInclude();
                        }

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
                            ensureElement("interestRate").value = editingCondition.interestRate;
                            ensureElement("offerInterestRate").value = editingCondition.offerInterestRate;
                            $(ensureElement("firstYearPayment")).setMoneyValue(editingCondition.firstYearPayment);
                            $(ensureElement("nextYearPayment")).setMoneyValue(editingCondition.nextYearPayment);
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

                        // Проверка, что введенные проценты входят в интервал [0,100]
                        function isCorrectPercent(percent)
                        {
                            return (percent >= 0 && percent <= 100);
                        }

                        function changeCurrency()
                        {
                            var currencySelect = ensureElement("currencySelect");
                            var curCode = currencySelect.options[currencySelect.selectedIndex].value;

                            var curCodes = findChildsByClassName(ensureElement("tableCondition"), "curCode", 4);
                            for(var j = 0; j < curCodes.length; ++j)
                                curCodes[j].innerHTML = curCode;

                            //загрузка кредитных лимитов по выбранной валюте
                            var creditLimits = window.opener.getCreditLimitsByCurrency(curCode);
                            var minCreditLimitSelect = ensureElement("minCreditLimitSelect");
                            var maxCreditLimitSelect = ensureElement("maxCreditLimitSelect");
                            minCreditLimitSelect.options.length = 0;
                            maxCreditLimitSelect.options.length = 0;
                            if (creditLimits.length == 0)
                                alert('Для того , чтобы задать условия для карточного кредитного продукта в ' +
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
                            var interestRate = trim(ensureElement("interestRate").value);
                            var offerInterestRate = trim(ensureElement("offerInterestRate").value);
                            var firstYearPayment = getStringWithoutSpace(ensureElement("firstYearPayment").value);
                            var nextYearPayment = getStringWithoutSpace(ensureElement("nextYearPayment").value);

                            interestRate = interestRate.replace(new RegExp(",",'g'), ".");
                            offerInterestRate = offerInterestRate.replace(new RegExp(",",'g'), ".");
                            firstYearPayment = firstYearPayment.replace(new RegExp(",",'g'), ".");
                            nextYearPayment = nextYearPayment.replace(new RegExp(",",'g'), ".");

                            var regexCurrency = new RegExp(/^\s*(((\d{1,10}(\.\d{0,2})?))|((\d{1,10}(\,\d{0,2})?)))\s*$/);
                            var regexPercent = new RegExp(/^\s*(((\d{1,3}(\.\d{0,2})?))|((\d{1,3}(\,\d{0,2})?)))\s*$/);

                            if(interestRate == "")
                            {
                                alert("Не заполнена процентная ставка.");
                                return;
                            }
                            if(offerInterestRate == "")
                            {
                                alert("Не заполнена процентная ставка по предодобренной карте.");
                                return;
                            }

                            if(firstYearPayment == "" || nextYearPayment == "")
                            {
                                alert("Не заполнена плата за годовое обслуживание.");
                                return;
                            }

                            if(!regexPercent.test(interestRate))
                            {
                                alert("Некорректно заполнена процентная ставка. Формат для процентных величин: ###.##.");
                                return;
                            }

                            if (!isCorrectPercent(interestRate))
                            {
                                alert("Некорректно заполнена процентная ставка. Процентная ставка не может быть меньше 0 или больше 100");
                                return;
                            }

                            if(!regexPercent.test(offerInterestRate))
                            {
                                alert("Некорректно заполнена процентная ставка по предодобренной карте. Формат для процентных величин: ###.##.");
                                return;
                            }

                            if (!isCorrectPercent(offerInterestRate))
                            {
                                alert("Некорректно заполнена процентная ставка по предодобренной карте. Процентная ставка по предодобренной карте не может быть меньше 0 или больше 100");
                                return;
                            }

                            if(!regexCurrency.test(firstYearPayment) || !regexCurrency.test(nextYearPayment))
                            {
                                alert("Некорректо заполнена плата за годовое обслуживание. Формат для денежных величин: #.##.");
                                return;
                            }
                            
                            else if(parseFloat(minCreditLimitValue) >= parseFloat(maxCreditLimitValue))
                            {
                                alert("Вы неправильно указали кредитный лимит продукта. Пожалуйста, проверьте сумму минимального и максимального кредитного лимита. Минимальный лимит должен быть меньше максимального.");
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
                                    isMaxCreditLimitInclude,
                                    interestRate,
                                    offerInterestRate,
                                    firstYearPayment,
                                    nextYearPayment
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
                                <bean:message key="label.product.credit.limit" bundle="${bundle}"/><span class="asterisk">*</span>
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

                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.product.percent.rate" bundle="${bundle}"/><span class="asterisk">*</span>
                            </div>
                            <div class="paymentValue">
                                <input type="text" id="interestRate" size="4" maxlength="6"/>
                                <span class="bold"><bean:message key="label.product.percent" bundle="${bundle}"/></span>
                            </div>
                            <div class="clear"></div>
                        </div>

                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.product.offer.percent.rate" bundle="${bundle}"/><span class="asterisk">*</span>
                            </div>
                            <div class="paymentValue">
                                <input type="text" id="offerInterestRate" size="4" maxlength="6"/>
                                <span class="bold"><bean:message key="label.product.percent" bundle="${bundle}"/></span>
                            </div>
                            <div class="clear"></div>
                        </div>

                        <div>
                            <bean:message key="label.product.year.payment" bundle="${bundle}"/>:
                        </div>

                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.product.first.year" bundle="${bundle}"/><span class="asterisk">*</span>
                            </div>
                            <div class="paymentValue">
                                <input type="text" id="firstYearPayment" size="10" maxlength="15" class="moneyField"/>
                                <span class="curCode bold"></span>
                            </div>
                            <div class="clear"></div>
                        </div>

                        <div class="form-row">
                            <div class="paymentLabel">
                                <bean:message key="label.product.next.year" bundle="${bundle}"/><span class="asterisk">*</span>
                            </div>
                            <div class="paymentValue">
                                <input type="text" id="nextYearPayment" size="10" maxlength="15" class="moneyField"/>
                                <span class="curCode bold"></span>
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