/*скрипт для работы с функционалом АЛФ*/

var financesUtils = {
    otherCategoryPercentLimit: 3.5,//количество процентов от общего объема средств, при котором категорию относим в "Остальные"
    showTransfers: false, // признак отображения переводов

    setShowTransfers: function(showTransfers)
    {
        this.showTransfers = showTransfers;
    },

    /* Функции для работы с загрузкой операций в "Моих финансах" */
    showOperations: function(id, date, showCreditCards, openPageDate, showOtherAccounts, callback)
    {
        if ($('#'+id).css("display") != "none")
            return;

        var params = {
            date: date,
            showCreditCards: showCreditCards,
            showOtherAccounts: showOtherAccounts,
            openPageDate: openPageDate
        };
        this.getOperations(id, params,callback);
    },

    getOperations: function(id, parameters, callback)
    {
        if (parameters.selectedCardIds)
            this.getOperationsToCategories(id, parameters, callback);
        else
            this.getOperationsList(id, parameters, callback);
    },

    /* Получаем список операций за заданный месяц */
    getOperationsList: function(id, parameters, callback)
    {
        var params = this.getCommonParams(parameters);
        if(parameters.income != null)
            params += "&filter(income)=" + parameters.income;
        else
        {
            var income = $("#income"+id).val();
            if (income)
                params += "&filter(income)=" + income;
        }

        var cash = $("#cash"+id).val();
        if (cash != undefined && cash != "")
            params += "&filter(cash)=" + cash;

        params += "&filter(showCreditCards)=" + parameters.showCreditCards;
        params += "&filter(showCash)=" + getElementValue("filter(showCash)");
        params += "&filter(showOtherAccounts)=" + parameters.showOtherAccounts;
        params += "&filter(openPageDate)=" + parameters.openPageDate;
        params += "&operation=button.filter";

        var clickedElem = null;
        if(parameters.clickElem != undefined)
            clickedElem = parameters.clickElem;

        var actionURL = document.webRoot + "/private/async/operations/list.do";
        var finUtils = this;
        if(callback)
            ajaxQuery (params, actionURL, function(data){callback.call(); finUtils.operationsAjaxResult(data, id, clickedElem);});
        else
            ajaxQuery (params, actionURL, function(data){finUtils.operationsAjaxResult(data, id, clickedElem);});
    },

    /* Получаем список операций за заданный месяц */
    getOperationsToCategories: function(id, parameters, callback)
    {
        var params = this.getCommonParams(parameters);
        if(parameters.category)
            params += "&filter(categoryId)=" + parameters.category;

        params += "&filter(onlyAvailableCategories)=" + parameters.onlyAvailableCategories;
        params += "&filter(showCash)=" + getElementValue("filter(showCash)");
        params += "&filter(showTransfers)=" + this.showTransfers;
        params += "&filter(selectedCardIds)=" + parameters.selectedCardIds;
        params += "&filter(openPageDate)=" + parameters.openPageDate;
        params += "&operation=button.filter";

        var clickedElem = null;
        if(parameters.clickElem != undefined)
            clickedElem = parameters.clickElem;

        var actionURL = document.webRoot + "/private/async/operations/listToCategories.do";
        var finUtils = this;
        if(callback)
            ajaxQuery (params, actionURL, function(data){callback.call(); finUtils.operationsAjaxResult(data, id, clickedElem);});
        else
            ajaxQuery (params, actionURL, function(data){finUtils.operationsAjaxResult(data, id, clickedElem);});
    },

    /**
     * Получает общие параметры фильтрации: страница списка, количество записей в списке, даты периода
     * @param parameters - параметры
     * @returns параметры строкой
     */
    getCommonParams: function(parameters)
    {
        var params = "";
        if(!parameters.searchPage)
            params += "searchPage=0";
        else
            params += "searchPage="+parameters.searchPage;

        if(!parameters.resOnPage)
            params += "&resOnPage=10";
        else
            params += "&resOnPage="+parameters.resOnPage;

        //если дата передана в параметрах, используем её, иначе берем значения фильтра
        if(parameters.date)
        {
            params += "&filter(typeDate)=month";
            params += "&filter(onDate)=" + parameters.date;
        }
        else if('month' == parameters.typeDate)
        {
            params += "&filter(typeDate)=" + parameters.typeDate;
            params += "&filter(onDate)=" + parameters.fromDate;
        }
        else if('period' == parameters.typeDate)
        {
            params += "&filter(typeDate)=" + parameters.typeDate;
            params += "&filter(fromDate)=" + parameters.fromDate;
            params += "&filter(toDate)=" + parameters.toDate;
        }
        else
        {
            var typeDate = $("input[name=filter(typeDate)]").val();
            params += "&filter(typeDate)=" + typeDate;
            if(typeDate == "month")
                params += "&filter(onDate)=" + $("input[name=filter(onDate)]").val();
            else
            {
                params += "&filter(fromDate)=" + $("input[name=filter(fromDate)]").val();
                params += "&filter(toDate)=" + $("input[name=filter(toDate)]").val();
            }
        }
        return params;
    },

    changePage: function(id, params, paginationType, searchPage)
    {
        params.searchPage = paginationType == 'next' ? searchPage+1 : searchPage-1;
        this.getOperations(id, params);
    },

    reloadPage: function(id, params)
    {
        this.getOperations(id, params);
    },

    changeResOnPage: function(id, params)
    {
        params.searchPage = 0;
        this.getOperations(id, params);
    },

    operationsAjaxResult: function(data, resultBlockId, clickedElem)
    {
        $(".operationsList").empty();
        $('.operationsListBlock').hide();
        $('#list'+resultBlockId).html(data);
        $('#'+resultBlockId).show();
    },

    changeSearchTypeValue: function(id, type, newValue, date, showCreditCards, openPageDate, showOtherAccounts)
    {
        var operations = $('#' + type + 'Operations' + id);
        var currVal = operations.val();
        if (currVal == newValue)
            return;

        var currType = $("#"+type+"_"+currVal+id);
        currType.removeClass("active");
        currType.addClass("transparent");

        var newType = $("#"+type+"_"+newValue+id);
        newType.removeClass("transparent");
        newType.addClass("active");

        operations.val(newValue);
        var params = {
            date: date,
            showCreditCards: showCreditCards,
            showOtherAccounts: showOtherAccounts,
            openPageDate: openPageDate
        };
        
        this.getOperations('Operations'+id, params);
    },

    /**
    * Отрисовывает компонент отображающий доход и расход за месяц (устанавливает ширину и подписи)
     * @param id ид компонента
     * @param income доход
     * @param outgo расход
     * @param maxVal максимальное значение среди всех доходов/расходов (необходимо чтобы правильно расчитать ширину)
     */
    setIncomeOutgoVals : function(id, income, outgo, maxVal)
    {
        var DELTA_WIDTH = 115; // чтобы состыковать со шкалой
        var LINE_DELTA = 5; // 5px на закругление линии

        var balance = income - outgo;
        var perOutgo = outgo/maxVal;
        var perIncome = income/maxVal;
        var perBalance = Math.abs(balance)/maxVal;

        var totalWidht = ($('#incomeOutgoMonth' + id).width() - DELTA_WIDTH - 1)/2;
        this.drawIncomeOutgoElem(id, 'outgoLine', totalWidht*perOutgo, LINE_DELTA, outgo, true);
        this.drawIncomeOutgoElem(id, 'incomeLine',totalWidht*perIncome, LINE_DELTA, income, true);

        if (balance < 0)
        {
            $('#negativeBalanceLine' + id).show();
            $('#positiveBalanceLine' + id).hide();
            $('#positiveBalanceLineDescr' + id).text("");

            var balanceDelta = $('#negativeBalanceLine' + id + ' .balanceLineLeft').width();
            this.drawIncomeOutgoElem(id, 'negativeBalanceLine', totalWidht*perBalance, LINE_DELTA, balance, true, true);
        }
        else if (balance > 0)
        {
            $('#positiveBalanceLine' + id).show();
            $('#negativeBalanceLine' + id).hide();
            $('#negativeBalanceLineDescr' + id).text("");

            var balanceDelta = $('#positiveBalanceLine' + id + ' .balanceLineRight').width();
            this.drawIncomeOutgoElem(id, 'positiveBalanceLine', totalWidht*perBalance, LINE_DELTA, balance, true, true);
        }
        else
        {
            $('#negativeBalanceLine' + id).hide();
            $('#positiveBalanceLine' + id).hide();
            $('#positiveBalanceLineDescr' + id).text("0");
            $('#negativeBalanceLineDescr' + id).text("");
        }
    },

    /**
     * Рисует (устанавливает ширину) для компонента доход/расход/остаток
     * @param id ид компонента
     * @param elemName название элемента который рисуем (доход/расход/остаток)
     * @param width ширина
     * @param val значение
     */
    drawIncomeOutgoElem:function(id, elemName, width, delta, val, showDescr, drawSign)
    {
        if (val == 0)
        {
            $('#' + elemName + id).hide();
            return;
        }

        $('#' + elemName + id).show();
        $('#' + elemName + id).width(width > delta ? width : delta);
        $('#' + elemName + id + ' .lineCenter').width(width > delta ? (width - delta) : 0);

        if (showDescr == undefined || !showDescr) return;

        var sign = '';
        if (drawSign && val > 0)
            sign = '+';
        
        $('#' + elemName + 'Descr' + id).text(sign + FloatToString(val, 0, ' '));
        var descrText = $('#' + elemName + 'Descr' + id);
        var descrDiv = $(descrText).closest('.descrText')
        var relativeDiv = $(descrText).closest('.' + elemName);
        if (descrDiv.width() > relativeDiv.width())
            descrDiv.addClass(elemName + 'DescrCenter');
        else
            descrDiv.addClass(elemName + 'Descr');
    },

    /**
     * Рисует (устанавливает ширину) для компонента наличных и безналичных операций
     * @param id ид компонента
     * @param cashIncome доход наличными
     * @param cashOutgo расход наличными
     * @param cashlessIncome доход безналичными
     * @param cashlessOutgo расход безналичными
     * @param maxVal максимальное значение среди всех доходов/расходов (необходимо чтобы правильно расчитать ширину)
     */
    setCashOperVals: function(id, cashIncome, cashOutgo, cashlessIncome, cashlessOutgo, maxVal)
    {
        var DELTA_WIDTH = 115; // чтобы состыковать со шкалой
        var LINE_DELTA = 5; // 5px на закругление линии

        var perCashOutgo = cashOutgo/maxVal;
        var perCashIncome = cashIncome/maxVal;
        var perCashlessOutgo = cashlessOutgo/maxVal;
        var perCashlessIncome = cashlessIncome/maxVal;

        var totalWidht = ($('#cashMonth' + id).width() - DELTA_WIDTH - 1)/2;
        this.drawIncomeOutgoElem(id, 'outgoCashLine', totalWidht*perCashOutgo, LINE_DELTA, cashOutgo, true);
        this.drawIncomeOutgoElem(id, 'incomeCashLine',totalWidht*perCashIncome, LINE_DELTA, cashIncome, true);
        this.drawIncomeOutgoElem(id, 'outgoCashlessLine', totalWidht*perCashlessOutgo, LINE_DELTA, cashlessOutgo, true);
        this.drawIncomeOutgoElem(id, 'incomeCashlessLine',totalWidht*perCashlessIncome, LINE_DELTA, cashlessIncome, true);
    },

    setIncomeOutgoDayVals : function(id, income, outgo, maxVal)
    {
        var DELTA_WIDTH = 103; // чтобы состыковать со шкалой
        var LINE_DELTA = 4; // на закругление линии

        var perOutgo = outgo/maxVal;
        var perIncome = income/maxVal;

        var totalWidht = ($('#incomeOutgoDay' + id).width() - DELTA_WIDTH - 1)/2;
        this.drawIncomeOutgoElem(id, 'outgoDayLine', totalWidht*perOutgo, LINE_DELTA, outgo, false);
        this.drawIncomeOutgoElem(id, 'incomeDayLine',totalWidht*perIncome, LINE_DELTA, income, false);
    },

    setCashDayVals : function(id, cashIncome, cashOutcome, cashlessIncome, cashlessOutcome, maxVal)
    {
        var DELTA_WIDTH = 103; // чтобы состыковать со шкалой
        var LINE_DELTA = 4; // на закругление линии

        var perCashOutgo = cashOutcome/maxVal;
        var perCashIncome = cashIncome/maxVal;
        var perCashlessOutgo = cashlessOutcome/maxVal;
        var perCashlessIncome = cashlessIncome/maxVal;

        var totalWidht = ($('#cashDay' + id).width() - DELTA_WIDTH - 1)/2;
        this.drawIncomeOutgoElem(id, 'outgoCashDayLine', totalWidht*perCashOutgo, LINE_DELTA, cashOutcome, false);
        this.drawIncomeOutgoElem(id, 'incomeCashDayLine',totalWidht*perCashIncome, LINE_DELTA, cashIncome, false);
        this.drawIncomeOutgoElem(id, 'outgoCashlessDayLine', totalWidht*perCashlessOutgo, LINE_DELTA, cashlessOutcome, false);
        this.drawIncomeOutgoElem(id, 'incomeCashlessDayLine',totalWidht*perCashlessIncome, LINE_DELTA, cashlessIncome, false);
    },

    /**
     * Навешивает действие на событие mouseenter для элемента (всплывающая подсказка с данными)
     * @param id ид элемента
     * @param date дата
     * @param amount сумма
     */
    bindEnter: function(id, date, amount)
    {
        $('#' + id).bind('mouseenter',
            function (event)
            {
                var hintText = date + '<br/>' + amount + " руб.";
                window.graphics.showInfoDiv(hintText, event);
            });
    },

    /**
     * Навешивает действие на событие mouseleave для элемента
     * @param id ид элемента
     */
    bindLeave: function(id)
    {
        $('#' + id).bind('mouseleave', function (event){window.graphics.closeInfoDiv();});
    },

    /**
     * Вычисление и отображение значений для шкалы
     * @param id ид шкалы
     * @param maxVal максимальное значение
     */
    setScaleVals:function(id, maxVal)
    {
        var mod = maxVal % 5;
        if (mod > 0) maxVal += 5 - mod; // число должно быть кратно 5
        var step = maxVal / 5;

        var currVal = step;
        for(var i=1; i < 4; i++)
        {
            $('#negScaleValue' + id + '_' + i).text(FloatToString(-currVal, 0, ' ') + " руб.");
            $('#posScaleValue' + id + '_' + i).text(FloatToString(currVal, 0, ' ') + " руб.");
            currVal += 2*step;
        }

        currVal -= step; // в качестве максимального/минимального на шкале: максимальное+шаг
        $('#minScaleValue' + id).text(FloatToString(-currVal, 0, ' ') + " руб.");
        $('#maxScaleValue' + id).text(FloatToString(currVal, 0, ' ') + " руб.");
    },

    /**
     * Перейти на форму просмотра платежа
     * @param businessDocumentId - идентификатор платежа
     */
    goToViewPayment: function(businessDocumentId)
    {
        var actionURL = document.webRoot + "/private/payments/view.do";
        window.location = actionURL + "?id=" + businessDocumentId;
    }
};
