var financeCalendar = {
    INVOICE_ID_PREFIX: 'invoiceDetailInfo',
    openPageDate: null,
    selectedCardIds: null,
    extractLoading: false,

    setOpenPageDate: function(openPageDate)
    {
        this.openPageDate = openPageDate;
    },

    setSelectedCardIds: function(selectedCardIds)
    {
        this.selectedCardIds = selectedCardIds;
    },

    getCommonParams: function()
    {
        var params = "";
        params += "filter(openPageDate)=" + this.openPageDate;
        params += "&filter(selectedCardIds)=" + this.selectedCardIds;
        return params;
    },

    /**
     * ���������� ������ �� ��������� ��������� ������� �� ����
     * @param clickedDay - ������ � ���������
     * @param extractId - ������������� �������
     * @param date - ���� �� ���������, �� ������� ������� �������
     */
    getDayExtract: function(clickedDay, extractId, date)
    {
        if (this.extractLoading)
            return;

        $('.calendarGridBody .calendarBox').removeClass('clicked');

        var extract = $('#financeCalendarExtract' + extractId);
        if (extract.length)
        {
            if ($(extract).is(":hidden"))
            {
                $('.financeCalendarExtract').hide();
                $(clickedDay).addClass('clicked');
                extract.find('.invoiceDetailInfo').hide();
                extract.find('.extractBox').show();
                extract.show();
            }
            else
            {
                extract.hide();
            }
            return;
        }

        this.extractLoading = true;
        $('.financeCalendarExtract').hide();

        var params = this.getCommonParams();
        params += "&filter(onDate)=" + date;

        var actionURL = document.webRoot + "/private/async/finances/financeCalendar/dayExtract.do";
        var calendar = this;
        ajaxQuery (params, actionURL, function(data){calendar.dayExtractAjaxResult(data, clickedDay, extractId);});
    },

    /**
     * ���������� ���������� ������� �� ��������� ������� �� ����
     * @param data - ������ � ������������ �������
     * @param clickedDay - ���� � ���������
     * @param extractId - ������������� �������
     */
    dayExtractAjaxResult: function(data, clickedDay, extractId)
    {
        if ($(data).attr('id') != 'financeCalendarExtract' + extractId) // ������� �� �����������
        {
            this.extractLoading = false;
            return;
        }

        data = trim(data);
        $('#wrapper').append(data);

        var extract = $('#financeCalendarExtract' + extractId);
        setPositionAfterObj(extract, clickedDay);
        $(clickedDay).addClass('clicked');

        var extractList = $(extract).find('.extractBlock');
        if ($(extractList).height() > 280)
        {
            extractList.css('overflow-y', 'scroll');
            extractList.css('height', '280px');
        }
        this.correctExtractPosition(extract);

        if ($(clickedDay).hasClass('currentDay'))
        {
            var balance = $('#currentBalanceAmount').val();
            if (balance != null)
            {
                $(extract).find('#currentBalanceAmountTd').text(balance);
            }
            else
            {
                $(extract).find('#currentBalanceAmountTd').parent().hide();
            }
        }
        this.extractLoading = false;
    },

    /**
     * ������������� ������� ���� � �������� (��������� ���������� �� ������ ���������)
     * @param extract - ���� � ��������
     */
    correctExtractPosition: function(extract)
    {
        var calendarGrid = $('#calendarGrid').find('.calendarGridBody');
        var calendarGridPos = absPosition($(calendarGrid).get(0));
        var extractPos = absPosition($(extract).get(0));
        var deltaPos = extractPos.left - calendarGridPos.left;
        var deltaWidth = calendarGrid.width() - extract.width();
        if (deltaWidth < deltaPos)
        {
            extract.css('left', extractPos.left - (deltaPos - deltaWidth));
            var triangle = extract.find('.financeCalendarExtractTriangle');
            triangle.css('left', triangle.position().left + (deltaPos - deltaWidth));
        }
    },

    /**
     * ������������� ������� ����� � ������� ��������
     */
    setCurrentDateBalancePosition: function()
    {
        var DELTA = 6; // ������ �� ���� ������

        var grid = $('#calendarGrid');
        var calendar = grid.find('table');
        var box = grid.find('.currentDay');
        if (box.length)
        {
            var topPosition = box.position().top + box.outerHeight() - DELTA;
            var boxPosLeft = box.position().left + box.outerWidth();

            var balanceBox = grid.find('.calendarBalanceAmountByCards');
            balanceBox.css('left', calendar.outerWidth());
            balanceBox.css('top', topPosition - balanceBox.outerHeight());

            var calendarBalanceCircle = $('#calendarBalanceCircle');
            calendarBalanceCircle.css('left', boxPosLeft - 4);
            calendarBalanceCircle.css('top', topPosition - 2);

            var calendarBalanceAmountByCardsLine = $('#calendarBalanceAmountByCardsLine');
            var width = $(grid).find('.calendarGridBody').width() - boxPosLeft + balanceBox.outerWidth();
            calendarBalanceAmountByCardsLine.css('left', boxPosLeft);
            calendarBalanceAmountByCardsLine.css('top', topPosition);
            calendarBalanceAmountByCardsLine.width(width);
        }
    },

    /**
     * ���������� ������ �� ��������� ��������� ���������� �� ����������� �����
     * @param extractId - ������������� �������
     * @param id - ������������� �����
     * @param type ��� ����� (invoice ��� shopOrder)
     */
    getInvoiceDetailInfo: function(extractId, id, type)
    {
        if (type == 'shopOrder')
        {
            document.location.href = document.webRoot + '/private/payments/internetShops/payment.do' + "?orderId=" + id + "&internetOrder=true";
            return;
        }

        var extract = $('#' + extractId);
        var invoice = extract.find('#' + this.INVOICE_ID_PREFIX + id);
        if (invoice.length)
        {
            extract.find('.extractBox').hide();
            invoice.show();
        }
        else
        {
            var calendar = this, params;
            if(type == "shopOrder")
            {
                params = {
                    id : id,
                    type : "page",
                    pageUrl : document.webRoot + "/private/payments/internetShops/payment.do"
                }
            }
            else
            {
                params = {
                    id : id,
                    type : "block",
                    blockId : calendar.INVOICE_ID_PREFIX + id,
                    actionUrl : document.webRoot  + "/private/async/basket/invoice/process.do",
                    viewUrl : document.webRoot + "/private/basket/invoice/view.do",
                    preLoadBlock : function(data){
                        calendar.addInvoiceDetailInfoBlock(extractId, id)
                    }
                }
            }

            new InvoiceManager(params).open();
        }
    },

    /**
     * ���������� ����� ��� ���������� ��������� ���������� �� ����������� �����
     * @param extractId - ������������� �������
     * @param invoiceId - ������������� ����������� �����
     */
    addInvoiceDetailInfoBlock: function(extractId, invoiceId)
    {
        var extract = $('#' + extractId);
        extract.find('.extractBox').hide();

        $('<div />').attr('id', this.INVOICE_ID_PREFIX + invoiceId).addClass('invoiceDetailInfo').appendTo(extract.find('.dataBox'));
    },

    closeExtract: function(id)
    {
        $('#' + id).hide();
        $('.calendarGridBody .calendarBox').removeClass('clicked');
        var arrayOfInvoiceDivs = $('.invoiceDetailInfo');
        for (var i = 0; i < arrayOfInvoiceDivs.length; i++)
        {
            var invoiceId = arrayOfInvoiceDivs[i].id.split('invoiceDetailInfo')[1];
            removeAllMessages('editEntityWarnings' + invoiceId);
            removeAllErrors('editEntityErrors' + invoiceId);
        }
    }
};