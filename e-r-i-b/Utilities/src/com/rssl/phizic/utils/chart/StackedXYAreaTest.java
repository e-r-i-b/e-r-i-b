package com.rssl.phizic.utils.chart;

/**
 * @author lepihina
 * @ created 03.09.2012
 * @ $Author$
 * @ $Revision$
 */

import junit.framework.TestCase;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer2;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StackedXYAreaTest extends TestCase
{
	int DATASET_SIZE = 10;

    public void testStart() throws IOException{
        DefaultTableXYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartUtilities.saveChartAsJPEG(new File("C:\\temp\\StackedXYAreaTest.jpg"),chart,1100,600);
    }

    private DefaultTableXYDataset createDataset()
    {
        DefaultTableXYDataset dataset = new DefaultTableXYDataset();

	    final XYSeries series1 = new XYSeries("Серия 1", true, false);
	    final XYSeries series2 = new XYSeries("Серия 2", true, false);
        double value = 0.0;

	    for (int i = 0; i < DATASET_SIZE; i++)
	    {
            value = value + 1;
            series1.add(i, value);
	        series2.add(i, value-1);
        }

	    dataset.addSeries(series1);
	    dataset.addSeries(series2);

        return dataset;
    }

    private JFreeChart createChart(DefaultTableXYDataset dataset)
    {
	    TickUnitSource ticks = NumberAxis.createIntegerTickUnits();
		// ось Y
	    final NumberAxis rangeAxis = new NumberAxis();
        rangeAxis.setLabelAngle(0);
		rangeAxis.setStandardTickUnits(ticks);
		rangeAxis.setTickLabelsVisible(true);

	    // генерация подписей для оси X
	    String[] tickLabels = new String[DATASET_SIZE];
	    for (int i = 0; i < DATASET_SIZE; i++)
	    {
		    tickLabels[i] = "значение " + i;
        }

		// ось X
	    final CustomSymbolAxis domainAxis = new CustomSymbolAxis("", tickLabels);
        domainAxis.setLowerMargin(0.0);// отступ слева
	    domainAxis.setUpperMargin(0.0);// отступ справа
	    domainAxis.setVerticalTickLabels(true);// подписи вертикально
		domainAxis.setStandardTickUnits(ticks);
	    domainAxis.setTickLabelAngle(-Math.PI / 3.0);

		// установка цвета серий
	    StackedXYAreaRenderer2 renderer = new StackedXYAreaRenderer2();
		renderer.setSeriesPaint(0, new Color(235,147,14));
		renderer.setSeriesPaint(1, new Color(48,133,28));

		XYPlot plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0)); // отступы осей от графика
	    plot.setForegroundAlpha(0.7f); // прозрачность
	    plot.setBackgroundPaint(Color.white); // цвет фона графика
        plot.setDomainGridlinePaint(Color.lightGray); // цвет сетки по оси X
		plot.setDomainGridlineStroke(new BasicStroke(1.0f)); // тип сетки по оси X (тонкая сплошная линия)
        plot.setRangeGridlinesVisible(false);  // не отображаем внутреннюю сетку (ось Y)

        return new JFreeChart("", plot);
    }

}
