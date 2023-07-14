var amChartHelper = {
    getDelimiter: function()
    {
        if(isIE(6))
            return '<br>';
        else
            return '\n';
    },
    /**
     * ���������� ������ ������������ �������.
     * ����� ��� ��6(BUG065226)
     * @returns {string} alt+0160
     */
    getNonBreakingSpace: function()
    {
        return '�';
    }

}

/**
 * ������ 3D ���������
 */

/**
 * ��� ����
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
 * ��� �������
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
 * ��� ���������.
 */
var diagramType = {
    /**
     * ��������
     * @param parameters ��������� �������������
     */
    circle: function(parameters)
    {
        var chart = new AmCharts.AmPieChart();
        $.extend(true, chart, parameters);
        $.extend(true, chart.balloon, parameters.balloonForDiagram);
        return chart;
    },
    /**
     * ����������
     * @param parameters ��������� �������������
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
 * ���������
 * @param parameters ��������� �������������
 * @param diagramId ������������� ���������(���� ����� ��������)
 * @param type ��� ��������� (circle, column)
 * @param defaultData ������ �� ������� �������� ���������
 */
function diagram(parameters, diagramId, type, defaultData)
{
    var self = this;

    this.chart = null;

    /**
     * ������ ���������
     * @param chartData ������ ��� ���������� ���������
     */
    this.init = function(chartData)
    {
        self.chart = diagramType[type](parameters);
        this.chart.dataProvider = chartData;
        this.chart.write(diagramId);
    };

    /**
     * ���������� ����� ������ ��� �������
     * @param chartData
     */
    this.setChartData = function(chartData)
    {
        this.chart.dataProvider = chartData;
    };

    /**
     * �������� ������ ��� ������� ������ ����������
     * @param chartData - ������ ��� ����������
     */
    this.updateChartData = function(chartData)
    {
        $.extend(true, this.chart.dataProvider, chartData);
    };

    /**
     * ����������� ���������
     */
    this.redraw = function()
    {
        this.chart.validateData();
    };

    AmCharts.ready(function() {
            self.init(defaultData);
    });
}