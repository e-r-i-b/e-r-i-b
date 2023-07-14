/*������ ��� ������ � ������������ ���*/

var financesUtils = {
    otherCategoryPercentLimit: 3.5,//���������� ��������� �� ������ ������ �������, ��� ������� ��������� ������� � "���������"
    showTransfers: false, // ������� ����������� ���������

    setShowTransfers: function(showTransfers)
    {
        this.showTransfers = showTransfers;
    },

    /* ������� ��� ������ � ��������� �������� � "���� ��������" */
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

    /* �������� ������ �������� �� �������� ����� */
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

    /* �������� ������ �������� �� �������� ����� */
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
     * �������� ����� ��������� ����������: �������� ������, ���������� ������� � ������, ���� �������
     * @param parameters - ���������
     * @returns ��������� �������
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

        //���� ���� �������� � ����������, ���������� �, ����� ����� �������� �������
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
    * ������������ ��������� ������������ ����� � ������ �� ����� (������������� ������ � �������)
     * @param id �� ����������
     * @param income �����
     * @param outgo ������
     * @param maxVal ������������ �������� ����� ���� �������/�������� (���������� ����� ��������� ��������� ������)
     */
    setIncomeOutgoVals : function(id, income, outgo, maxVal)
    {
        var DELTA_WIDTH = 115; // ����� ����������� �� ������
        var LINE_DELTA = 5; // 5px �� ����������� �����

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
     * ������ (������������� ������) ��� ���������� �����/������/�������
     * @param id �� ����������
     * @param elemName �������� �������� ������� ������ (�����/������/�������)
     * @param width ������
     * @param val ��������
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
     * ������ (������������� ������) ��� ���������� �������� � ����������� ��������
     * @param id �� ����������
     * @param cashIncome ����� ���������
     * @param cashOutgo ������ ���������
     * @param cashlessIncome ����� ������������
     * @param cashlessOutgo ������ ������������
     * @param maxVal ������������ �������� ����� ���� �������/�������� (���������� ����� ��������� ��������� ������)
     */
    setCashOperVals: function(id, cashIncome, cashOutgo, cashlessIncome, cashlessOutgo, maxVal)
    {
        var DELTA_WIDTH = 115; // ����� ����������� �� ������
        var LINE_DELTA = 5; // 5px �� ����������� �����

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
        var DELTA_WIDTH = 103; // ����� ����������� �� ������
        var LINE_DELTA = 4; // �� ����������� �����

        var perOutgo = outgo/maxVal;
        var perIncome = income/maxVal;

        var totalWidht = ($('#incomeOutgoDay' + id).width() - DELTA_WIDTH - 1)/2;
        this.drawIncomeOutgoElem(id, 'outgoDayLine', totalWidht*perOutgo, LINE_DELTA, outgo, false);
        this.drawIncomeOutgoElem(id, 'incomeDayLine',totalWidht*perIncome, LINE_DELTA, income, false);
    },

    setCashDayVals : function(id, cashIncome, cashOutcome, cashlessIncome, cashlessOutcome, maxVal)
    {
        var DELTA_WIDTH = 103; // ����� ����������� �� ������
        var LINE_DELTA = 4; // �� ����������� �����

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
     * ���������� �������� �� ������� mouseenter ��� �������� (����������� ��������� � �������)
     * @param id �� ��������
     * @param date ����
     * @param amount �����
     */
    bindEnter: function(id, date, amount)
    {
        $('#' + id).bind('mouseenter',
            function (event)
            {
                var hintText = date + '<br/>' + amount + " ���.";
                window.graphics.showInfoDiv(hintText, event);
            });
    },

    /**
     * ���������� �������� �� ������� mouseleave ��� ��������
     * @param id �� ��������
     */
    bindLeave: function(id)
    {
        $('#' + id).bind('mouseleave', function (event){window.graphics.closeInfoDiv();});
    },

    /**
     * ���������� � ����������� �������� ��� �����
     * @param id �� �����
     * @param maxVal ������������ ��������
     */
    setScaleVals:function(id, maxVal)
    {
        var mod = maxVal % 5;
        if (mod > 0) maxVal += 5 - mod; // ����� ������ ���� ������ 5
        var step = maxVal / 5;

        var currVal = step;
        for(var i=1; i < 4; i++)
        {
            $('#negScaleValue' + id + '_' + i).text(FloatToString(-currVal, 0, ' ') + " ���.");
            $('#posScaleValue' + id + '_' + i).text(FloatToString(currVal, 0, ' ') + " ���.");
            currVal += 2*step;
        }

        currVal -= step; // � �������� �������������/������������ �� �����: ������������+���
        $('#minScaleValue' + id).text(FloatToString(-currVal, 0, ' ') + " ���.");
        $('#maxScaleValue' + id).text(FloatToString(currVal, 0, ' ') + " ���.");
    },

    /**
     * ������� �� ����� ��������� �������
     * @param businessDocumentId - ������������� �������
     */
    goToViewPayment: function(businessDocumentId)
    {
        var actionURL = document.webRoot + "/private/payments/view.do";
        window.location = actionURL + "?id=" + businessDocumentId;
    }
};
