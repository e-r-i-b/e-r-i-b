var productsGraphUtils = 
{
    COL_COUNT: 6,
    ROW_COUNT: 6,

    /**
     * Позиционирует блок по высоте
     * @param graphId
     * @param productId
     * @param row
     */
    setProductOnGraphRowPosition: function(graphId, productId, row)
    {
        var graph = $('#'+graphId);
        var productBox = graph.find('#'+productId);
        var deltaHeight = graph.height() / this.ROW_COUNT;
        productBox.css('top', graph.width() - deltaHeight*row - productBox.height()/2);
    },

    /**
     * Позиционирует блок по ширине
     * @param graphId
     * @param productId
     * @param col
     */
    setProductOnGraphColPosition: function(graphId, productId, col)
    {
        var graph = $('#'+graphId);
        var productBox = graph.find('#'+productId);
        var deltaWidth = graph.width() / this.COL_COUNT;
        productBox.css('left', deltaWidth*col - productBox.width()/2);
    },

    /**
     * Добавляет значение на ось
     * @param graphId - идентификатор графика
     * @param from - с какого значения начинается шаг
     * @param to - до какого значеение шаг
     * @param title - заголовок оси
     */
    drawXaxisValue: function(graphId, from, to, title)
    {
        var graph = $('#'+graphId);
        var axis = graph.parent().find('.productsGraphAxisValues');
        var stepWidth = graph.width() / this.COL_COUNT;

        $('<div />', {className: 'productsGraphAxisValue'})
            .appendTo(axis)
            .text(title)
            .css('width', (to-from)*stepWidth)
            .css('left', from*stepWidth)
            .css('top', 0)
            .breakWords();

        // Если высота новой подписи оказалась больше, чем высота блока с подписями, увеличиваем высоту блока
        var newHeight = axis.find('.productsGraphAxisValue:last').height();
        if (newHeight > axis.height())
            axis.css('height', newHeight);
    }
};