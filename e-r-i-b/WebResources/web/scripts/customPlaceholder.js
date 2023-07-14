function compareTitle(node)
{
    return $.trim($(node).val()) == $(node).attr('title');
}

var customPlaceholder =
{
    /**
     * ����� ��� ������������� ��������� � ����� input
     * @param node - �������������� ������� ����������� �� DOM ��������
     *               ������������ �������� ���� ������ ������� (�� ��������� document)
     * @param showTitleIfEmpty - �������������� ������� ����������� �� ������������� ���������� ��������� � ����, ���� ���� �� ���������
     */
    init: function(node, showTitleIfEmpty)
    {
        if (node == null) node = document;

        $(window).blur(function() {
            var firstInputField = $($("#authBlock .enterBlock").filter(":visible").get(0)).find(":input").filter(":visible").get(0);
            if (compareTitle(firstInputField))
            {
                // ��� ������ ������ ������� ����� (������� � ������ �������)
                $(firstInputField).val(" " +
                        $.trim($(firstInputField).val()));
            }
        });

        if (showTitleIfEmpty == undefined)
            showTitleIfEmpty = false;

        $(node).find('.customPlaceholder')
        .each(function(){
            // ���������� ������������ ����� ����
            this.savedMaxLen = $(this).attr('maxlength');
        }).focus(function(){
            if ($.trim($(this).val()).length == 0 || compareTitle(this))
            {
                setCaretPosition(this, 0, 0);
                if (!$(this).hasClass('csaRegistration') && !showTitleIfEmpty) $(this).val('');
                if(!compareTitle(this))
                    $(this).removeClass('inputPlaceholder');
                // ��������������� ������������ ����� ����
                $(this).attr('maxlength', this.savedMaxLen);
            }
        }).blur(function(){
            if($.trim($(this).val()).length == 0 || compareTitle(this))
            {
                // ������� ������������ ����� ����
                // (� ��������� ���������, ���� ��������� ������� ����������� �����, �� � ����� ����������)
                this.removeAttribute('maxlength');  // removeAttr � jQuery � ��7 ������������ �������
                $(this).val($(this).attr('title'));
                $(this).addClass('inputPlaceholder');
            }
        }).keypress(function(e){
             if(compareTitle(this) && !showTitleIfEmpty)
             {
                 $(this).val('');
             }
        }).keydown(function(e){
            if(compareTitle(this))
            {
               if (e.keyCode == 8 || e.keyCode == 46)
               {
                   $(this).val('');                    
               }
            }
        }).click(function(e){
            if(compareTitle(this) && !showTitleIfEmpty)
            {
                $(this).val('');
            }
        }).bind('paste', function(){
            if(compareTitle(this))
            {
                $(this).val('');
            }
        }).blur();
    },

    /**
     * ����� ��� ������� ����� input �� ���������
     * @param node - �������������� ������� ����������� �� DOM ��������
     *               ������������ �������� ���� ������ ������� (�� ��������� document)
     */
    clearPlaceholderVal: function(node)
    {
        if (node == null) node = document;

        $(node).find('.inputPlaceholder')
        .each(function(){
            $(this).val('');
        });
    },

    /**
     * ���������� �������� ���� (���� �������� ���������, �� ������ ������)
     * @param input - ����
     */
    getCurrentVal: function(input)
    {
        if($(input).val() == $(input).attr('title'))
            return '';
        else
            return $(input).val();
    }
};