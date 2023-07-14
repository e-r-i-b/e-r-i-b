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
		productList.add(new Product("������",42L,new Color(115,198,64)));
		productList.add(new Product("���", 14L,new Color(176,129,210)));
		productList.add(new Product("����", 44L,new Color(247,110,29)));

		final PieDataset dataset = createDataset();
		final JFreeChart jFreeChart = createChart(dataset);
		BufferedImage image = jFreeChart.createBufferedImage(500,270);//����������� � ����������
		ChartUtilities.saveChartAsJPEG(new File("C:\\temp\\diagramPie.jpg"),jFreeChart,500,270);

		final CategoryDataset barDataset = createBarDataset();
		final JFreeChart jFreeBar = createBar(barDataset);
		image = jFreeBar.createBufferedImage(500,270);
		ChartUtilities.saveChartAsJPEG(new File("C:\\temp\\diagramBar.jpg"),jFreeBar,500,270);
	}

	public BufferedImage generatePieChar()
	{
		productList.add(new Product("������",42L,new Color(115,198,64)));
		productList.add(new Product("���", 14L,new Color(176,129,210)));
		productList.add(new Product("����", 44L,new Color(247,110,29)));

		final PieDataset dataset = createDataset();
		final JFreeChart jFreeChart = createChart(dataset);
		BufferedImage image = jFreeChart.createBufferedImage(500,270);//����������� � ����������

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
	    dataset.setValue(255000, "", "���� ����������� ������");
	    dataset.setValue(155000, "", "������������� ����� �� �������������� �����");
	    dataset.setValue(25000, "", "����������� ���������� ��������");

		return dataset;
    }

	private JFreeChart createChart(PieDataset dataset)
	{
		final JFreeChart chart = ChartFactory.createPieChart(null,dataset,true,false,false);
		final PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelGenerator(new PercentLabelGenerator());//������������� ��������� �������� �� ���������
		plot.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
		plot.setSimpleLabels(true);
		for (Product product : productList)
		{
			plot.setSectionPaint(product.name,product.color);
			plot.setExplodePercent(product.name,0.05f);
		}
		plot.setLegendLabelGenerator(new CustomLabelLegendGenerator());//������������� ��������� �������� � �������
		plot.setBackgroundPaint(Color.white);//������������� ���� ����
		plot.setLegendItemShape(new Rectangle(10, 10, 10, 6));// ����� ������ � �������� �������
		plot.setOutlineStroke(null);//������� ������� � ���������
		chart.setBorderVisible(false);//������� ������� � ����� ������� � ����������

		//����������� �������
		LegendTitle legend = chart.getLegend();
		legend.setPosition(RectangleEdge.RIGHT);
		legend.setBorder(0,0,0,0);

		TextTitle legendText = new TextTitle("��������� ���������� ������������� ������������� ����� ��������� �������� ������� ��� ��������� ����������� ������.");
		legendText.setPosition(RectangleEdge.RIGHT);
		legendText.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		legendText.setVerticalAlignment(VerticalAlignment.TOP);
		chart.addSubtitle(legendText);
		
		return chart;
	}

	private JFreeChart createBar(CategoryDataset dataset)
	{
		final JFreeChart chart = ChartFactory.createBarChart("","", "�����, ���.", dataset, PlotOrientation.VERTICAL, false, true, false);
		chart.setBackgroundPaint(Color.white); // ���� ���� ��� ���� ���������

        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white); // ���� ����
		plot.setRangeGridlinePaint(Color.black); 
		plot.setOutlineStroke(null);

		final CategoryItemRenderer renderer = new CustomRenderer(
            new Paint[] {new Color(255,211,6), new Color(132,178,212), new Color(245,135,40)}
        );
		// ��������� ������� ��� ���������
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2} ���.", new DecimalFormat("#,##0")));
		renderer.setBaseItemLabelsVisible(true);

		plot.setRenderer(renderer);

		BarRenderer rend = (BarRenderer) plot.getRenderer();
		rend.setBarPainter(new StandardBarPainter()); // ����� �� ���� ���������
		rend.setDrawBarOutline(false);

		// ��� OX
		final CategoryAxis axis = plot.getDomainAxis();
		axis.setMaximumCategoryLabelLines(6);

		// ��� OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.1);
		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//������������� ������� ���� �� �������

        return chart;
	}

	class Product
	{
		public String name;//�������� ��������
		public Long value;//���������� ��������
		public Color color;//���� �� ���������

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
