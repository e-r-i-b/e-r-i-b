<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags"  prefix="phiz"%>
<fmt:setLocale value="ru-RU"/>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="dictionaryLoanList" value="${form.dictionaryLoanList}"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="addLoan"/>
    <tiles:put name="closeCallback" value="function(){ $('#editLoanWarningMessages').addClass('displayNone');return true;}"/>
    <tiles:put name="data">
        <h1><bean:message key="editLoanWindow.label"  bundle="pfpBundle"/></h1>

        <div class="warningMessage displayNone errMessagesBlock" id="editLoanWarningMessages">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="red"/>
                <tiles:put name="data">
                    <div id="editLoanMessageContainer" class="messageContainer">
                    </div>
                    <div class="clear"></div>
                </tiles:put>
            </tiles:insert>
        </div>

        <div class="warningMessage displayNone" id="warningMessages">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="red"/>
                <tiles:put name="data">
                    <div id="messageContainer" class="messageContainer">
                    </div>
                    <div class="clear"></div>
                </tiles:put>
            </tiles:insert>
        </div>

        <div class="winDescription">
            <bean:message key="editLoanWindow.description" bundle="pfpBundle"/>
        </div>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">
                <bean:message key="editLoanWindow.fields.loanProduct" bundle="pfpBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <div class="float">
                    <select id="loanSelect" class="select" style="width:210px" onchange="pfpLoan.setLoanProductData();pfpLoan.updateQuarterPayment();">
                        <c:forEach items="${dictionaryLoanList}" var="dictionaryLoan">
                            <option value="${dictionaryLoan.id}"><c:out value="${dictionaryLoan.name}"/></option>
                        </c:forEach>
                    </select>
                </div>
                <c:forEach items="${dictionaryLoanList}" var="dictionaryLoan">
                    <c:set var="imageData" value="${phiz:getImageById(dictionaryLoan.imageId)}"/>
                    <img id="loanImage${dictionaryLoan.id}" src="${phiz:getAddressImage(imageData, pageContext)}" class="loanImage displayNone" width="64px" height="64px"/>
                </c:forEach>
            </tiles:put>
            <tiles:put name="clazz" value="pfpFormRow"/>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">
                <bean:message key="editLoanWindow.fields.loanComment" bundle="pfpBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="false"/>
            <tiles:put name="data">
                <input id="loanComment" type="text" name="loanComment" class="customPlaceholder" size="20" maxlength="100" title="введите комментарий"/>
            </tiles:put>
            <tiles:put name="fieldName">loanComment</tiles:put>
            <tiles:put name="clazz" value="pfpFormRow"/>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">
                <bean:message key="editLoanWindow.fields.needLoanAmount" bundle="pfpBundle"/>
            </tiles:put>
            <tiles:put name="data">
                <span class="bold"><span id="loanRecomendAmount">0</span> руб.</span>
            </tiles:put>
            <tiles:put name="fieldName">needLaonAmount</tiles:put>
            <tiles:put name="clazz" value="pfpFormRow"/>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">
                <bean:message key="editLoanWindow.fields.loanAmount" bundle="pfpBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <input id="loanAmount" type="text" name="loanAmount" size="10" maxlength="10" class="moneyField" onchange="pfpLoan.updateQuarterPayment();" onkeyup="pfpLoan.updateQuarterPayment();"/>
                <span class="bold">руб.</span> <span id="loanBorder" class="text-gray">от N руб. до M руб.</span>
            </tiles:put>
            <tiles:put name="fieldName">loanAmount</tiles:put>
            <tiles:put name="clazz" value="pfpFormRow"/>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">
                <bean:message key="editLoanWindow.fields.loanDuration" bundle="pfpBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <tiles:insert definition="scrollTemplate2" flush="false">
                    <tiles:put name="id" value="loanDuration"/>
                    <tiles:put name="minValue" value="0"/>
                    <tiles:put name="maxValue" value="100"/>
                    <tiles:put name="currValue" value="1"/>
                    <tiles:put name="fieldName" value="field(loanDuration)"/>
                    <tiles:put name="step" value="1"/>
                    <tiles:put name="minUnit" value="квартал"/>
                    <tiles:put name="maxUnit" value="кварталов"/>
                    <tiles:put name="round" value="0"/>
                    <tiles:put name="valuesPosition" value="bottom"/>
                    <tiles:put name="inputData"><span class="bold">кварталов</span> <span id="monthCount" class="text-gray">(NN) месяцев</span></tiles:put>
                    <tiles:put name="callback" value="pfpLoan.setLoanEndDate();pfpLoan.updateQuarterPayment();"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="fieldName">loanDuration</tiles:put>
            <tiles:put name="clazz" value="pfpFormRow"/>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">
                <bean:message key="editLoanWindow.fields.startDate" bundle="pfpBundle"/>                
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <input type="text" name="field(startDateQuarter)" size="2" maxlength="1" onchange="pfpLoan.setLoanEndDate();"/> <span class="bold">квартал</span>
                <input type="text" name="field(startDateYear)" size="5" maxlength="4" onchange="pfpLoan.setLoanEndDate();"/> <span class="bold">года</span>
            </tiles:put>
            <tiles:put name="fieldName">startDate</tiles:put>
            <tiles:put name="clazz" value="pfpFormRow"/>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">
                <bean:message key="editLoanWindow.fields.endDate" bundle="pfpBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="false"/>
            <tiles:put name="data">
                <input id="endDate" type="hidden" name="endDate">
                <span id="endDateSpan" class="bold float"></span>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="pfpEditLoanHint"/>
                    <tiles:put name="text">
                        <bean:message key="editLoanWindow.fields.endDate.help" bundle="pfpBundle"/>
                    </tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="fieldName">endDate</tiles:put>
            <tiles:put name="clazz" value="pfpFormRow"/>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">
                <bean:message key="editLoanWindow.fields.loanRate" bundle="pfpBundle"/>                
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <tiles:insert definition="scrollTemplate2" flush="false">
                    <tiles:put name="id" value="loanRate"/>
                    <tiles:put name="minValue" value="0"/>
                    <tiles:put name="maxValue" value="100"/>
                    <tiles:put name="currValue" value="1"/>
                    <tiles:put name="fieldName" value="field(loanRate)"/>
                    <tiles:put name="step" value="0.1"/>
                    <tiles:put name="unit" value="%"/>
                    <tiles:put name="round" value="1"/>
                    <tiles:put name="valuesPosition" value="bottom"/>
                    <tiles:put name="inputData"><span class="bold">% годовых</span></tiles:put>
                    <tiles:put name="callback" value="pfpLoan.setLoanEndDate();pfpLoan.updateQuarterPayment();"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="fieldName">loanRate</tiles:put>
            <tiles:put name="clazz" value="pfpFormRow"/>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">
                <bean:message key="editLoanWindow.fields.quarterlyPayment" bundle="pfpBundle"/>                
            </tiles:put>
            <tiles:put name="isNecessary" value="false"/>
            <tiles:put name="data">
                <span id="quarterlyPayment" class="bold"></span>
                <span class="text-gray"> или </span>
                <span id="monthPayment" class="text-gray bold"></span>
            </tiles:put>
            <tiles:put name="fieldName">quarterlyPayment</tiles:put>
            <tiles:put name="clazz" value="pfpFormRow"/>
        </tiles:insert>

        <div class="tableArea pfpButtonsBlock">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancelAddTarget"/>
                <tiles:put name="commandHelpKey" value="button.cancelAddTarget.help"/>
                <tiles:put name="bundle" value="pfpBundle"/>
                <tiles:put name="onclick" value="win.close('addLoan');"/>
                <tiles:put name="viewType" value="buttonGrey"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.saveAddTarget"/>
                <tiles:put name="commandHelpKey" value="button.saveAddTarget.help"/>
                <tiles:put name="bundle" value="pfpBundle"/>
                <tiles:put name="onclick" value="pfpLoan.savePersonLoan();"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>

    </tiles:put>
</tiles:insert>

<c:set var="editUrl" value="${phiz:calculateActionURL(pageContext,'/async/editPfpLoan')}"/>

<script type="text/javascript">

    var quorters = ["квартал","квартала", "кварталов"];
    function getQuortersForm(num)
    {
        return selectSklonenie(num, quorters);
    }
    var pfpLoan =
    {
        saved: false,
        editableLoanId : null,
        loanProductList: {},
        callbackFunctions : [],
        init:function()
        {
            var loanProduct;
            <c:forEach items="${dictionaryLoanList}" var="dictionaryLoan">
                loanProduct = new Object();
                loanProduct.id = ${dictionaryLoan.id};
                loanProduct.fromAmount = ${dictionaryLoan.fromAmount};
                loanProduct.toAmount = ${dictionaryLoan.toAmount};
                loanProduct.fromPeriod = ${dictionaryLoan.fromPeriod};
                loanProduct.toPeriod = ${dictionaryLoan.toPeriod};
                loanProduct.defaultPeriod = ${dictionaryLoan.defaultPeriod};

                loanProduct.fromRate = ${dictionaryLoan.fromRate};
                loanProduct.toRate = ${dictionaryLoan.toRate};
                loanProduct.defaultRate = ${dictionaryLoan.defaultRate};
                this.loanProductList['${dictionaryLoan.id}'] = loanProduct;
            </c:forEach>
        },
        initNewLoan: function(startDate,amount)
        {
            $('#loanAmount').setMoneyValue(amount.toFixed(2));
            $('#loanRecomendAmount').text(FloatToString(amount,2,' '));
            this.setLoanStartDate(startDate);
            this.clearFields();
            this.setLoanProductData();
            this.updateQuarterPayment();
            $('#loanComment').blur();
        },
        clearFields: function()
        {
            this.editableLoanId = null;
            $('#loanComment').val('');
            $("#loanSelect :first").attr("selected", "selected");
        },
        /**
         * Добавить каллбек функцию в массив
         * @param func - функция
         */
        addEditLoanCallbackFunction : function(func)
        {
            this.callbackFunctions.push(func);
        },
        /**
         * Колбек, выполняемый после редактирования кредита.
         */
        editLoanCallback: function(personLoan)
        {
            for (var i = 0; i < this.callbackFunctions.length; i++)
            {
                this.callbackFunctions[i](personLoan);
            }
        },
        /**
         * Установить параметры редактируемого кредита
         * @param loanId - идентификатор редактируемого кредита
         * @param dictionaryLoan - идентификатор редактируемого кредита
         * @param nameComment - комментарий к кредиту
         * @param amount - сумма
         * @param startDate - дата начала платежей. В формате даты
         * @param duration - срок кредита в кварталах
         * @param rate - ставка по кредиту
         */
        editPersonLoan: function(loanId,dictionaryLoan,nameComment,amount,startDate,duration,rate)
        {
            $('#loanComment').val(nameComment);
            $('#loanComment').blur();
            $('#loanAmount').setMoneyValue(amount);
            this.setLoanStartDate(startDate)

            this.editableLoanId = loanId;
            $("#loanSelect [value='"+dictionaryLoan+"']").attr("selected", "selected");
            this.setLoanProductData();

            $('input[name=field(loanDuration)]').val(duration);
            $('input[name=field(loanRate)]').val(rate);
            this.setLoanEndDate();
            this.updateQuarterPayment();
            win.open("addLoan");
        },
        setLoanProductData: function()
        {
            var id = $('#loanSelect :selected').val();
            $('.loanImage').hide();
            $('#loanImage'+id).show();
            var loanProduct = this.loanProductList[id];
            updateScroll('loanDuration', horizDragloanDuration, loanProduct.defaultPeriod, loanProduct.fromPeriod, loanProduct.toPeriod, getQuortersForm);
            updateScroll('loanRate', horizDragloanRate, loanProduct.defaultRate, loanProduct.fromRate, loanProduct.toRate);
            this.setLoanEndDate();
        },
        getQuarterPayment: function()
        {
            var loanAmount = parseFloatVal(getStringWithoutSpace($('#loanAmount').val()));
            var loanRate = $('input[name=field(loanRate)]').val();
            var loanDuration = $('input[name=field(loanDuration)]').val();
            return pfpMath.calculateQuarterPaymentForLoan(loanAmount,loanRate,loanDuration);
        },
        updateQuarterPayment: function()
        {
            var quarterPayment = this.getQuarterPayment();
            var monthPayment = quarterPayment/3;
            $('#quarterlyPayment').html(FloatToString(quarterPayment,2,' ') + ' руб.');
            $('#monthPayment').html(FloatToString(monthPayment,2,' ') + ' руб. в месяц');

            var loan = this.loanProductList[$('#loanSelect').val()];
            var fromAmount = loan.fromAmount;
            var toAmount = loan.toAmount;
            $("#loanBorder").text('от '+FloatToString(fromAmount,2,' ')+' руб. до '+FloatToString(toAmount,2,' ')+' руб.');

        },
        setLoanEndDate: function()
        {
            var startDateStr = this.getLoanStartDateStr();
            if (isEmpty(startDateStr))
            {
                $('#editLoanMessageContainer').html("Пожалуйста, укажите корректную дату в поле \"Оформление кредита\".");
                $('#editLoanWarningMessages').removeClass("displayNone");
                return;
            }
            var loanDuration = $('input[name=field(loanDuration)]').val();
            <!-- Используются функции из jquery.datePicker.js -->
            var endDate = Date.fromString(startDateStr);
            if (!endDate && !(endDate instanceof Date))
            {
                $('#editLoanMessageContainer').html("Пожалуйста, укажите корректную дату в поле \"Оформление кредита\".");
                $('#editLoanWarningMessages').removeClass("displayNone");
                return;
            }
            pfpMath.addQuarters(endDate,loanDuration);
            $('#endDateSpan').text(parseQuarter(endDate)+' года');
            var monthCount = parseFloat(loanDuration)*3;
            $("#monthCount").text('(' + monthCount + ') ' + selectSklonenie(monthCount, months));
            $('#endDate').val(endDate.asString());
        },
        getLoanStartDateStr: function()
        {
            var loanStartDateQuarter = $('input[name=field(startDateQuarter)]').val();
            var loanStartDateYear = $('input[name=field(startDateYear)]').val();
            if(loanStartDateQuarter == "" || loanStartDateYear == "")
                return;
            var loanStatDateMonth = (loanStartDateQuarter - 1) * 3 + 1;
            if(loanStatDateMonth < 10)
               loanStatDateMonth = '0' + loanStatDateMonth;

            return '01/' + loanStatDateMonth + '/' + loanStartDateYear;
        },
        setLoanStartDate: function(date)
        {
            var quarter = pfpMath.getQuarter(date);
            $('input[name=field(startDateQuarter)]').val(quarter);
            $('input[name=field(startDateYear)]').val(date.getFullYear());
        },
        savePersonLoan: function()
        {
            var personLoan = new Object();
            if(this.editableLoanId)
                personLoan.id = this.editableLoanId;
            personLoan.dictionaryLoan = $('#loanSelect :selected').val();
            personLoan.loanComment = customPlaceholder.getCurrentVal($('#loanComment'));
            var amount = $('#loanAmount').val();
            if (isEmpty(amount))
            {
                $('#editLoanMessageContainer').html("Пожалуйста, укажите корректное значение в поле \"Возьму в кредит\".");
                $('#editLoanWarningMessages').removeClass("displayNone");
                return;
            }
            personLoan.amount = parseFloatVal(amount);
            if (isNaN(personLoan.amount))
            {
                $('#editLoanMessageContainer').html("Пожалуйста, укажите корректное значение в поле \"Возьму в кредит\".");
                $('#editLoanWarningMessages').removeClass("displayNone");
                return;
            }

            personLoan.startDateStr = this.getLoanStartDateStr();
            if (isEmpty(personLoan.startDateStr))
            {
                $('#editLoanMessageContainer').html("Пожалуйста, укажите корректную дату в поле \"Оформление кредита\".");
                $('#editLoanWarningMessages').removeClass("displayNone");
                return;
            }
            personLoan.endDateStr = $('#endDate').val();
            personLoan.startDate = Str2Date(personLoan.startDateStr);
            if (personLoan.startDate == null || personLoan.startDate == undefined)
            {
                $('#editLoanMessageContainer').html("Пожалуйста, укажите корректную дату в поле \"Оформление кредита\".");
                $('#editLoanWarningMessages').removeClass("displayNone");
                return;
            }

            personLoan.endDate = Str2Date(personLoan.endDateStr);
            personLoan.loanRate = $('input[name=field(loanRate)]').val();
            personLoan.quarterPayment = this.getQuarterPayment();

            if (!financePlan.financePlanCalculator.canAddLoan(personLoan))
            {
                $('#editLoanMessageContainer').html("Вы не можете воспользоваться кредитом на указанную сумму, так как у Вас не хватит средств для оплаты квартального платежа.");
                $('#editLoanWarningMessages').removeClass("displayNone");
                return;
            }

            var params = "operation=button.save";
            params += "&profileId=" + ${form.id};
            if(personLoan.id)
                params += "&loanId=" + personLoan.id;
            params += "&field(dictionaryLoan)=" + personLoan.dictionaryLoan;
            params += "&field(loanComment)=" + decodeURItoWin(personLoan.loanComment);
            params += "&field(loanAmount)=" + personLoan.amount;
            params += "&field(startDate)=" + personLoan.startDateStr;
            params += "&field(endDate)=" + personLoan.endDateStr;
            params += "&field(loanRate)=" + personLoan.loanRate;
            if (this.saved)
                return;
            this.saved = true;
            var _self = this;
            ajaxQuery(params,'${editUrl}',function(data){pfpLoan.addLoanResult(data, personLoan); _self.saved = false; processLoanList();});
        },
        addLoanResult: function(data,personLoan)
        {
            data = trim(data);
            //если вернулась пустая строка, то вероятнее всего произошел тайм аут сессии, перезагружаем страницу
            if (data == '')
            {
                reload();
            }
            //если в ответе не нашли loan, то кредита в сообщении нет. Вытаскиваем сообщение об ошибке.
            else if(data.search("personLoan") == -1)
            {
                $('#editLoanWarningMessages').removeClass("displayNone");
                $('#editLoanMessageContainer').html(data);
                return;
            }
            else if(this.editableLoanId)
            {
                $('#personLoan'+this.editableLoanId).replaceWith($(data).find('tr[id^=personLoan]'));
            }
            else
            {
                personLoan.id = $(data).find('#loanId').val();
                $('#pfpTableLoansContainer > tbody').append($(data).find('tr[id^=personLoan]'));
            }
            pfpLoan.editLoanCallback(personLoan);
            win.close('addLoan');
        }
    };

    $(document).ready(function(){
        pfpLoan.init();
    })

</script>