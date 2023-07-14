var amChartHelper = {
    getDelimiter: function()
    {
        if(isIE(6))
            return '<br>';
        else
            return '\n';
    },
    /**
     * Возвращает символ неразрывного пробела.
     * Нужно для ие6(BUG065226)
     * @returns {string} alt+0160
     */
    getNonBreakingSpace: function()
    {
        return ' ';
    }

}

/**
 * Объект 3D диаграмма
 */

/**
 * Тип осей
 */
var valueAxisType = {
    valueAxis: function(parameters)
    {
        var axis = new AmCharts.ValueAxis();
        $.extend(true, axis, parameters);
        return axis;
    }
};

var amGraphType = {
    AmGraph: function(parameters)
    {
        var graph = new AmCharts.AmGraph();
        $.extend(true, graph, parameters);
        return graph;
    }
};

/**
 * Тип легенды
 */
var amLegendType = {
    AmLegend: function(parameters)
    {
        var legend = new AmCharts.AmLegend();
        $.extend(true, legend, parameters);
        return legend;
    }
};

/**
 * Тип диаграммы.
 */
var diagramType = {
    /**
     * Круговая
     * @param parameters параметры инициализации
     */
    circle: function(parameters)
    {
        var chart = new AmCharts.AmPieChart();
        $.extend(true, chart, parameters);
        $.extend(true, chart.balloon, parameters.balloonForDiagram);
        return chart;
    },
    /**
     * Столбчатая
     * @param parameters параметры инициализации
     */
    column: function(parameters)
    {
        var chart = new AmCharts.AmSerialChart();
        $.extend(true, chart, parameters);
        $.extend(true, chart.categoryAxis, parameters.categoryAxisForDiagram);
        $.extend(true, chart.balloon, parameters.balloonForDiagram);
        chart.numberFormatter = parameters.numberFormatter;
        chart.addValueAxis(valueAxisType[parameters.valueAxisForDiagram.axisTypeForDiagram](parameters.valueAxisForDiagram));

        if(parameters.legendForDiagram)
            chart.addLegend(amLegendType[parameters.legendForDiagram.legendTypeForDiagram](parameters.legendForDiagram));

        if(parameters.graphForDiagram)
        {
            for(var i = 0; i < parameters.graphForDiagram.length; i++)
                chart.addGraph(amGraphType[parameters.graphForDiagram[i].graphTypeForDiagram](parameters.graphForDiagram[i]));
        }
        return  chart;
    }
};

/**
 * Диаграмма
 * @param parameters параметры инициализации
 * @param diagramId идентификатор диаграммы(куда будем рисовать)
 * @param type тип диаграммы (circle, column)
 * @param defaultData данные по которым строится диаграмма
 */
function diagram(parameters, diagramId, type, defaultData)
{
    var self = this;

    this.chart = null;

    /**
     * Рисует диаграмму
     * @param chartData данные для построения диаграммы
     */
    this.init = function(chartData)
    {
        self.chart = diagramType[type](parameters);
        this.chart.dataProvider = chartData;
        this.chart.write(diagramId);
    };

    /**
     * Установить новые данные для графика
     * @param chartData
     */
    this.setChartData = function(chartData)
    {
        this.chart.dataProvider = chartData;
    };

    /**
     * Обновить данные для графика новыми значениями
     * @param chartData - данные для обновления
     */
    this.updateChartData = function(chartData)
    {
        $.extend(true, this.chart.dataProvider, chartData);
    };

    /**
     * Перерисовка диаграммы
     */
    this.redraw = function()
    {
        this.chart.validateData();
    };

    AmCharts.ready(function() {
            self.init(defaultData);
    });
}