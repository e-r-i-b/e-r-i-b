/* Скриптовая пагинация для шаблона tableTemplate */
var jsPagination = {
    BACK_ACTIVE_PAGINATION_BUTTON: '<a href="#" onclick="jsPagination.nextValues(this, true); return false;">'+
                                       '<div class="activePaginLeftArrow"></div>'+
                                   '</a>',
    NEXT_ACTIVE_PAGINATION_BUTTON: '<a href="#" onclick="jsPagination.nextValues(this, false); return false;">'+
                                       '<div class="activePaginRightArrow"></div>'+
                                   '</a>',
    BACK_INACTIVE_PAGINATION_BUTTON: '<div class="inactivePaginLeftArrow"></div>',
    NEXT_INACTIVE_PAGINATION_BUTTON: '<div class="inactivePaginRightArrow"></div>',
    VISIBLE_ROW_STYLE: "cursor:pointer;cursor:hand",
    PAGINATION_SIZE_NAME: "$$pagination_size0",

    nextValues: function(elem, back)
    {
        var offset = $('[name=offset]');
        var paginationSize = parseInt($('[name=' + this.PAGINATION_SIZE_NAME + ']').val());
        var paginationOffset = parseInt(offset.val());
        offset.val(paginationOffset + (back ? -1 : 1) * paginationSize);
        paginationOffset = parseInt(offset.val());

        var i = 0;
        $('[class^=ListLine]').each(function(){
            if($(this).css('display')=="none" && paginationOffset <= i && i < paginationOffset + paginationSize)
            {
                $(this).show();
                $(this).attr('style', this.VISIBLE_ROW_STYLE);
            }
            else
            {
                $(this).hide();
            }
            i++;
        });

        var backButton = $(elem).parents('tr:first').children('td').get(1);
        var nextButton = $(elem).parents('tr:first').children('td').get(3);

        if(paginationOffset == 0)
        {
            $(backButton).html(this.BACK_INACTIVE_PAGINATION_BUTTON);
        }

        if(paginationOffset > 0)
        {
            $(backButton).html(this.BACK_ACTIVE_PAGINATION_BUTTON);
        }

        if(i <= paginationOffset + paginationSize)
        {
            $(nextButton).html(this.NEXT_INACTIVE_PAGINATION_BUTTON);
        }

        if(i > paginationOffset + paginationSize)
        {
            $(nextButton).html(this.NEXT_ACTIVE_PAGINATION_BUTTON);
        }
    },

    moreValues: function(elem)
    {
        $('[name=' + this.PAGINATION_SIZE_NAME + ']').val(parseInt($(elem).text()));
        $('[name=offset]').val(-parseInt($(elem).text()));

        var paginationSizeButtons = $(elem).parents('td:first').children('div');
        $(paginationSizeButtons).each(function()
        {
            var size = trim($(this).text());
            if(this == $(elem).parents('.paginationSize:first')[0])
            {
                $(this).addClass('circle');
                $(this).removeClass('paginationSize');
                $(this).html('<div class="greenSelector"><span>'+size+'</span></div>');
            }
            else
            {
                $(this).removeClass('circle');
                $(this).addClass('paginationSize');
                $(this).html('<a href="#" onclick="jsPagination.moreValues(this); return false;">'+size+'</a>');
            }
        });

        $('[class^=ListLine]:visible').hide();
        this.nextValues($('#pagination').find('[class$=PaginRightArrow]'), false);
    }
};

/* Скриптовая пагинация для шаблона simpleTableTemplate */
var jsSimplePagination = {
    BACK_ACTIVE_PAGINATION_BUTTON: '<a href="#" onclick="jsSimplePagination.nextValues(this, true); return false;">'+
                                       '<div class="activePaginLeftArrow"></div>'+
                                   '</a>',
    NEXT_ACTIVE_PAGINATION_BUTTON: '<a href="#" onclick="jsSimplePagination.nextValues(this, false); return false;">'+
                                       '<div class="activePaginRightArrow"></div>'+
                                    '</a>',
    BACK_INACTIVE_PAGINATION_BUTTON: '<div class="inactivePaginLeftArrow"></div>',
    NEXT_INACTIVE_PAGINATION_BUTTON: '<div class="inactivePaginRightArrow"></div>',
    VISIBLE_ROW_STYLE: "cursor:pointer;cursor:hand",
    PAGINATION_SIZE_NAME: "$$pagination_size0",

    nextValues: function(elem, back)
    {
        var offset = $('[name=offset]');
        var paginationSize = parseInt($('[name=' + this.PAGINATION_SIZE_NAME + ']').val());
        var paginationOffset = parseInt(offset.val());
        offset.val(paginationOffset + (back ? -1 : 1) * paginationSize);
        paginationOffset = parseInt(offset.val());

        var i = 0;
        $('[class^=ListLine]').each(function(){
            if($(this).css('display')=="none" && paginationOffset <= i && i < paginationOffset + paginationSize)
            {
                $(this).show();
                $(this).attr('style', this.VISIBLE_ROW_STYLE);
            }
            else
            {
                $(this).hide();
            }
            i++;
        });

        var buttons = "";

        if(paginationOffset == 0)
        {
            buttons += this.BACK_INACTIVE_PAGINATION_BUTTON;
        }

        if(paginationOffset > 0)
        {
            buttons += this.BACK_ACTIVE_PAGINATION_BUTTON;
        }

        if(i <= paginationOffset + paginationSize)
        {
            buttons += this.NEXT_INACTIVE_PAGINATION_BUTTON;
        }

        if(i > paginationOffset + paginationSize)
        {
            buttons += this.NEXT_ACTIVE_PAGINATION_BUTTON;
        }

        $(elem).parents('td:first').html(buttons);
    },

    moreValues: function(elem)
    {
        console.log("moreValues");
        $('[name=' + this.PAGINATION_SIZE_NAME + ']').val(parseInt($(elem).text()));
        $('[name=offset]').val(-parseInt($(elem).text()));
        $('.paginationSize').each(function(){
            var size = trim($(this).text());
            if(this == $(elem).parents('.paginationSize:first')[0])
            {
                $(this).html('<div class="greenSelector"><span>'+size+'</span></div>');
            }
            else
            {
                $(this).html('<a href="#" onclick="jsSimplePagination.moreValues(this); return false;">'+size+'</a>');
            }
        });

        $('[class^=ListLine]:visible').hide();
        this.nextValues($('#pagination').find('[class$=PaginRightArrow]'), false);
    }
};