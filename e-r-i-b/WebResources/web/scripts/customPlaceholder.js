function compareTitle(node)
{
    return $.trim($(node).val()) == $(node).attr('title');
}

var customPlaceholder =
{
    /**
     * метод для инициализации подсказок к полям input
     * @param node - необязательный элемент указывающий на DOM родителя
     *               относительно которого надо искать селекты (по умолчанию document)
     * @param showTitleIfEmpty - необязательный элемент указывающий на необходимость оторажения подсказки в поле, если поле не заполнено
     */
    init: function(node, showTitleIfEmpty)
    {
        if (node == null) node = document;

        $(window).blur(function() {
            var firstInputField = $($("#authBlock .enterBlock").filter(":visible").get(0)).find(":input").filter(":visible").get(0);
            if (compareTitle(firstInputField))
            {
                // при потере фокуса текущим окном (переход к другой вкладке)
                $(firstInputField).val(" " +
                        $.trim($(firstInputField).val()));
            }
        });

        if (showTitleIfEmpty == undefined)
            showTitleIfEmpty = false;

        $(node).find('.customPlaceholder')
        .each(function(){
            // запоминаем максимальную длину поля
            this.savedMaxLen = $(this).attr('maxlength');
        }).focus(function(){
            if ($.trim($(this).val()).length == 0 || compareTitle(this))
            {
                setCaretPosition(this, 0, 0);
                if (!$(this).hasClass('csaRegistration') && !showTitleIfEmpty) $(this).val('');
                if(!compareTitle(this))
                    $(this).removeClass('inputPlaceholder');
                // восстанавливаем максимальную длину поля
                $(this).attr('maxlength', this.savedMaxLen);
            }
        }).blur(function(){
            if($.trim($(this).val()).length == 0 || compareTitle(this))
            {
                // удаляем максимальную длину поля
                // (в некоторых браузерах, если подсказка длиннее масимальной длины, то её текст обрезается)
                this.removeAttribute('maxlength');  // removeAttr в jQuery в ие7 отрабатывает неверно
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
     * метод для очистки полей input от подсказок
     * @param node - необязательный элемент указывающий на DOM родителя
     *               относительно которого надо искать селекты (по умолчанию document)
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
     * Возвращает значение поля (если записана подсказка, то пустая строка)
     * @param input - поле
     */
    getCurrentVal: function(input)
    {
        if($(input).val() == $(input).attr('title'))
            return '';
        else
            return $(input).val();
    }
};