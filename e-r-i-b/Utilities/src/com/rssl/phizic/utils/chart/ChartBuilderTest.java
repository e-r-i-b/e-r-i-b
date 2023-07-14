package com.rssl.phizic.utils.chart;

import junit.framework.TestCase;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.VerticalAlignment;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mihaylov
 * @ created 17.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ChartBuilderTest extends TestCase
{

	private static final List<Product> productList = new ArrayList<Product>();

	public void testChartBuilder() throws IOException
	{
		productList.add(new Product("Вклады",42L,new Color(115,198,64)));
		productList.add(new Product("ОМС", 14L,new Color(176,129,210)));
		productList.add(new Product("ПИФы", 44L,new Color(247,110,29)));

		final PieDataset dataset = createDataset();
		final JFreeChart jFreeChart = createChart(dataset);
		BufferedImage image = jFreeChart.createBufferedImage(500,270);//изображение с диаграммой
		ChartUtilities.saveChartAsJPEG(new File("C:\\temp\\diagramPie.jpg"),jFreeChart,500,270);

		final CategoryDataset barDataset = createBarDataset();
		final JFreeChart jFreeBar = createBar(barDataset);
		image = jFreeBar.createBufferedImage(500,270);
		ChartUtilities.saveChartAsJPEG(new File("C:\\temp\\diagramBar.jpg"),jFreeBar,500,270);
	}

	public BufferedImage generatePieChar()
	{
		productList.add(new Product("Вклады",42L,new Color(115,198,64)));
		productList.add(new Product("ОМС", 14L,new Color(176,129,210)));
		productList.add(new Product("ПИФы", 44L,new Color(247,110,29)));

		final PieDataset dataset = createDataset();
		final JFreeChart jFreeChart = createChart(dataset);
		BufferedImage image = jFreeChart.createBufferedImage(500,270);//изображение с диаграммой

		return image;
	}

	private PieDataset createDataset()
	{
		final DefaultPieDataset pieDataset = new DefaultPieDataset();
		for (Product product : productList)
			pieDataset.setValue(product.name,product.value);
		return pieDataset;
	}

	private CategoryDataset createBarDataset()
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    dataset.setValue(255000, "", "Ваши ежемесячные доходы");
	    dataset.setValue(155000, "", "Рекомендуемая сумма на сберегательном счете");
	    dataset.setValue(25000, "", "Ежемесячные своббодные средства");

		return dataset;
    }

	private JFreeChart createChart(PieDataset dataset)
	{
		final JFreeChart chart = ChartFactory.createPieChart(null,dataset,true,false,false);
		final PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelGenerator(new PercentLabelGenerator());//устанавливаем генератор подписей на диаграмме
		plot.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
		plot.setSimpleLabels(true);
		for (Product product : productList)
		{
			plot.setSectionPaint(product.name,product.color);
			plot.setExplodePercent(product.name,0.05f);
		}
		plot.setLegendLabelGenerator(new CustomLabelLegendGenerator());//устанавливаем генератор подписей в легенде
		plot.setBackgroundPaint(Color.white);//устанавливаем цвет фона
		plot.setLegendItemShape(new Rectangle(10, 10, 10, 6));// форма иконок в подписях легенды
		plot.setOutlineStroke(null);//убираем границу у диаграммы
		chart.setBorderVisible(false);//убираем границу у всего рисунка с диаграммой

		//Настраиваем легенду
		LegendTitle legend = chart.getLegend();
		legend.setPosition(RectangleEdge.RIGHT);
		legend.setBorder(0,0,0,0);

		TextTitle legendText = new TextTitle("Диаграмма показывает рекомендуемое распределение Ваших свободных денежных средств для получения стабильного дохода.");
		legendText.setPosition(RectangleEdge.RIGHT);
		legendText.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		legendText.setVerticalAlignment(VerticalAlignment.TOP);
		chart.addSubtitle(legendText);
		
		return chart;
	}

	private JFreeChart createBar(CategoryDataset dataset)
	{
		final JFreeChart chart = ChartFactory.createBarChart("","", "Сумма, руб.", dataset, PlotOrientation.VERTICAL, false, true, false);
		chart.setBackgroundPaint(Color.white); // цвет фона для всей диаграммы

        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white); // цвет фона
		plot.setRangeGridlinePaint(Color.black); 
		plot.setOutlineStroke(null);

		final CategoryItemRenderer renderer = new CustomRenderer(
            new Paint[] {new Color(255,211,6), new Color(132,178,212), new Color(245,135,40)}
        );
		// добавляем подписи над столбцами
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2} руб.", new DecimalFormat("#,##0")));
		renderer.setBaseItemLabelsVisible(true);

		plot.setRenderer(renderer);

		BarRenderer rend = (BarRenderer) plot.getRenderer();
		rend.setBarPainter(new StandardBarPainter()); // чтобы не было градиента
		rend.setDrawBarOutline(false);

		// ось OX
		final CategoryAxis axis = plot.getDomainAxis();
		axis.setMaximumCategoryLabelLines(6);

		// ось OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.1);
		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//устанавливаем отступы осей от графика

        return chart;
	}

	class Product
	{
		public String name;//название продукта
		public Long value;//количество продукта
		public Color color;//цвет на диаграмме

		Product(String name, Long value, Color color)
		{
			this.name = name;
			this.value = value;
			this.color = color;
		}
	}

	class CustomRenderer extends BarRenderer
	{
        private Paint[] colors;

        public CustomRenderer(final Paint[] colors) {
            this.colors = colors;
        }

        public Paint getItemPaint(final int row, final int column) {
            return this.colors[column % this.colors.length];
        }
	}
}
