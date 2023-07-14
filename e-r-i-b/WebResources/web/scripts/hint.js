/* Скрипты для работы с подсказками */

var hintUtils = {
    /**
     * Создает подсказку, которая отображается под элементом страницы (element)
     * @param id - идентификатор подсказки
     * @param element - элемент, для которого создается подсказка
     * @param showX - отображать ли крестик для закрытия подсказки
     * @param styleClass - css класс для подсказки
     * @param data - содержимое подсказки (можно с тегами)
     * @param closeCallback - сallback
     */
    createElementHint: function(id, element, data, showX, styleClass, closeCallback)
    {
        if (element == null || element.length == 0)
            return false;

        var hint = $('#elementHint'+id);
        if (hint.length != 0)
        {
            this.setData(id, data);
            this.resetHintPosition(id, element);
            this.showElementHint(id);
            return true;
        }

        var hintClass = styleClass != undefined ? styleClass : '';
        var hintDiv = document.createElement('div');
        hintDiv.className = "floatMessage orangeFloatMessage elementFloatHint " + hintClass;
        hintDiv.id = "elementHint" + id;

        // треугольничек
        var hintTopPick = document.createElement('div');
        hintTopPick.className = "floatMessageTop r-top";
        hintDiv.appendChild(hintTopPick);

        // основной блок подсказки
        var hintBlock = document.createElement('div');
        hintBlock.className = "floatMessageHint";

        var hintTop = document.createElement('div');

        hintBlock.appendChild(hintTop);

        // canter
        var hintCenterContent = document.createElement('div');
        hintCenterContent.className = "floatMessageHintCC r-content";
        hintCenterContent.innerHTML = data;

        hintBlock.appendChild(hintCenterContent);
        hintDiv.appendChild(hintBlock);

        if (showX)
        {
            var closeImg = document.createElement('div');
            closeImg.className = "closeImg";
            closeImg.onclick = function(){
                hintUtils.closeElementHint(id);
                if (closeCallback != undefined)
                    closeCallback();
            };
            hintDiv.appendChild(closeImg);
        }

        var parent;
        if (isClientApp)
            parent = $('body');
        else
            parent = $('#workspaceDiv');
        parent.append(hintDiv);
        setPositionAfterObj(hintDiv, element);

        $(window).resize(function(){
            setPositionAfterObj(hintDiv, element);
        });

        return true;
    },

    showElementHint: function(id)
    {
        $('#elementHint'+id).show();
    },

    setData: function(id, data)
    {
        $('#elementHint'+id+ ' .floatMessageHintCC').html(data);
    },

    closeElementHint: function(id)
    {
        $('#elementHint'+id).hide();
    },

    resetHintPosition: function(id, element)
    {
        setPositionAfterObj($('#elementHint'+id), element);
    },

    removeHint: function(id)
    {
        $('#elementHint'+id).remove();
    }
};