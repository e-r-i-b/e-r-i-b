package com.rssl.phizic.utils.chart;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.pdf.ChartStyle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer2;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import javax.swing.*;

/**
 * @author lepihina
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ChartBuilder
{
	private static final Font boldFont14  = new Font("Arial", Font.BOLD, 14);
	private static final Font plainFont16 = new Font("Arial", Font.PLAIN, 16);
	private static final Font plainFont18 = new Font("Arial", Font.PLAIN, 18);
	private static final Font plainFont20 = new Font("Arial", Font.PLAIN, 20);
	private static final Font boldFont = new Font("Arial", Font.BOLD, 20);

	/**
	 * ���������� ���������� �������� ���������
	 * @param productList ������ ��������� ��� ����������� �� ���������
	 * @param chartSettings ��������� ���������
	 * @return ���������
	 */
	public BufferedImage generate3DPieChart(java.util.List<PieChartItem> productList, ChartSettings chartSettings)
	{
		chartSettings.setDataset(createPieDataset(productList));
		final JFreeChart jFreeChart = create3DPieChart(chartSettings, productList);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //����������� � ����������
	}

	/**
	 * ���������� 3D ���������� ���������
	 * @param productList - ������ ��������� ��� ����������� �� ���������
	 * @param chartSettings - ��������� ���������
	 * @param seriesNum - ���������� �����
	 * @return ���������
	 */
	public BufferedImage generate3DBarChart(java.util.List<BarChartItem> productList, ChartSettings chartSettings, int seriesNum)
	{
		chartSettings.setDataset(createBarDataset(productList));
		final JFreeChart jFreeChart = create3DBarChart(chartSettings, productList, seriesNum);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //����������� � ����������
	}

	/**
	 * ���������� ���������� ��������� � ��������
	 * @param productList - ������ ��������� ��� ����������� �� ���������
	 * @param chartSettings - ��������� ���������
	 * @param seriesNum - ���������� �����
	 * @param imageUrl ��� ������ �� ��������
	 * @return ���������
	 */
	public BufferedImage generateImageBarChart(java.util.List<BarChartItem> productList, ChartSettings chartSettings, int seriesNum, String imageUrl) throws ChartException
	{
		chartSettings.setDataset(createBarDataset(productList));
		final JFreeChart jFreeChart = createImageBarChart(chartSettings, productList, seriesNum, imageUrl);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //����������� � ����������
	}

	/**
	 * ���������� ���������� ��������� � ��������
	 * @param productList - ������ ��������� ��� ����������� �� ���������
	 * @param chartSettings - ��������� ���������
	 * @param seriesNum - ���������� �����
	 * @param imageData �������� � ���� �������
	 * @return ���������
	 */
	public BufferedImage generateImageBarChart(java.util.List<BarChartItem> productList, ChartSettings chartSettings, int seriesNum, byte[] imageData) throws ChartException
	{
		chartSettings.setDataset(createBarDataset(productList));
		final JFreeChart jFreeChart = createImageBarChart(chartSettings, productList, seriesNum, imageData);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //����������� � ����������
	}

	/**
	 * ���������� 3D �������� ���������� ���������
	 * @param productList - ������ ��������� ��� ����������� �� ���������
	 * @param chartSettings - ��������� ���������
	 * @param seriesNum - ���������� �����
	 * @return ���������
	 */
	public BufferedImage generate3DStakedBarChartWithSeveralDomainAxis(java.util.List<StackedBarChartItem> productList, ChartSettings chartSettings, int seriesNum)
	{
		chartSettings.setDataset(createStackedBarDataset(productList));
		final JFreeChart jFreeChart = create3DStackedBarChartWithSeveralDomainAxis(chartSettings, seriesNum, productList);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //����������� � ����������
	}

	/**
	 * ���������� 3D �������� ���������� ��������� � ��������� �� ����
	 * @param productList - ������ ��������� ��� ����������� �� ���������
	 * @param chartSettings - ��������� ���������
	 * @param seriesNum - ���������� �����
	 * @return ���������
	 */
	public BufferedImage generate3DStakedBarChart(java.util.List<StackedBarChartItem> productList, ChartSettings chartSettings, int seriesNum)
	{
		chartSettings.setDataset(createStackedBarDataset(productList));
		final JFreeChart jFreeChart = create3DStackedBarChart(chartSettings, seriesNum, productList);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //����������� � ����������
	}

	/**
	 * ���������� 3D �������� ���������� ��������� � ��������� �� ����
	 * @param chartSettings - ��������� ���������
	 * @param productList - ������ ��������� ��� ����������� �� ���������
	 * @param seriesNum - ���������� �����
	 * @param yAxisValues - ������ �������� ��� ��� (������������ ������ �� �������)
	 * @return ���������
	 */
	public BufferedImage generate3DStakedBarChart(java.util.List<StackedBarChartItem> productList, ChartSettings chartSettings, int seriesNum, String[] yAxisValues)
	{
		chartSettings.setDataset(createStackedBarDataset(productList));
		final JFreeChart jFreeChart = create3DStackedBarChart(chartSettings, seriesNum, productList, yAxisValues);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //����������� � ����������
	}

	/**
	 * ���������� �������� ������ �� ������
	 * @param charTitle ��������� �������
	 * @param domainAxisTitle ��������� ��� X
	 * @param rangeAxisTitle ��������� ��� Y
	 * @param productList ������ ��������
	 * @param width ������ �������
	 * @param height ������ �������
	 * @return ������
	 */
	public BufferedImage generateStakedAreaChart(String charTitle, String domainAxisTitle, String rangeAxisTitle, java.util.List<AreaChartItem> productList, int width, int height)
	{
		final DefaultTableXYDataset dataset = createStakedAreaDataset(productList);
		final JFreeChart jFreeChart = createStakedAreaChart(charTitle, domainAxisTitle, rangeAxisTitle, dataset, productList);
		return jFreeChart.createBufferedImage(width, height); //����������� � ����������
	}

	/**
	 * ��������� Dataset ��� �������� ���������
	 * @param productList ������ ��������� ��� ����������� �� ���������
	 * @return Dataset ��� �������� ���������
	 */
	private PieDataset createPieDataset(java.util.List<PieChartItem> productList)
	{
		final DefaultPieDataset pieDataset = new DefaultPieDataset();
		for (PieChartItem pieChartItem : productList)
			pieDataset.setValue(pieChartItem.getName(), pieChartItem.getValue());
		return pieDataset;
	}

	/**
	 * ��������� Dataset ��� ���������� ���������
	 * @param productList ������ ��������� ��� ����������� �� ���������
	 * @return Dataset ��� ���������� ���������
	 */
	private CategoryDataset createBarDataset(java.util.List<BarChartItem> productList)
	{
		DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

		for (BarChartItem chartItem : productList)
		{
			barDataset.addValue(round(chartItem.getValue()), chartItem.getSeries(), chartItem.getCategory());
		}

		return barDataset;
    }

	/**
	 * ��������� Dataset ��� ���������� ���������
	 * @param productList ������ ��������� ��� ����������� �� ���������
	 * @return Dataset ��� ���������� ���������
	 */
	private CategoryDataset createStackedBarDataset(java.util.List<StackedBarChartItem> productList)
	{
		DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

		for (StackedBarChartItem chartItem : productList)
		{
			barDataset.addValue(round(chartItem.getValue()), chartItem.getCategory().getId(), chartItem.getDescr());
		}

		return barDataset;
    }

	/**
	 * ��������� Dataset ��� �������
	 * @param productList ������ �������� ��� ����������� �� �������
	 * @return Dataset ��� �������
	 */
	private DefaultTableXYDataset createStakedAreaDataset(java.util.List<AreaChartItem> productList)
	{
		DefaultTableXYDataset dataset = new DefaultTableXYDataset();
		XYSeries s1 = new XYSeries(productList.get(0).getName1(), true, false);
		XYSeries s2 = new XYSeries(productList.get(0).getName2(), true, false);

		int i = 0;
		for (AreaChartItem areaChartItem : productList)
		{
			s1.add(i, Math.round(areaChartItem.getValue1()));
	        s2.add(i++, Math.round(areaChartItem.getValue2()));
		}

		dataset.addSeries(s1);
		dataset.addSeries(s2);

		return dataset;
    }

	/**
	 * ������ ���������� �������� ���������
	 * @param chartSettings ��������� ���������
	 * @param productList ������ ��������� ��� ����������� �� ���������
	 * @return ���������
	 */
	private JFreeChart create3DPieChart(ChartSettings chartSettings, java.util.List<PieChartItem> productList)
	{
		final JFreeChart chart = ChartFactory.createPieChart3D(
				chartSettings.getChartTitle(),
				(PieDataset)chartSettings.getDataset(),
				chartSettings.isShowLegend(),
				false,
				false
		);
		ChartStyle chartStyle = chartSettings.getChartStyle();
		chart.setBackgroundPaint(chartStyle.getBackgroundColor());

		final PiePlot3D plot = (PiePlot3D) chart.getPlot();
		if (chartSettings.isShowLegend())
		{
			plot.setLabelGenerator(new PercentLabelGenerator()); //������������� ��������� �������� �� ���������
		}
		else
		{
			plot.setLabelGenerator(new PercentAndTitleLabelGenerator()); //������������� ��������� �������� �� ���������
		}
		plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
		plot.setLabelFont(plainFont20);
		plot.setMaximumLabelWidth(0.17);
		plot.setLabelPadding(new RectangleInsets(4, 4, 4, 4));
		plot.setSimpleLabels(false);//���������� ������� �� ����� ���������(true) ��� ������� �� ���������(false)

		for (PieChartItem pieChartItem : productList)
		{
			plot.setSectionPaint(pieChartItem.getName(), pieChartItem.getColor());
		}
		plot.setBackgroundPaint(chartStyle.getPlotBackgroundColor()); //������������� ���� ����
		plot.setOutlineStroke(null); //������� ������� � ���������
		plot.setDarkerSides(true);//���������� ���� �� �������� ��������
		chart.setBorderVisible(false); //������� ������� � ����� ������� � ����������

		if (chartSettings.isShowLegend())
		{
			plot.setLegendItemShape(new Rectangle(20, 20, 20, 12)); // ����� ������ � �������� �������
			plot.setLegendLabelGenerator(new CustomLabelLegendGenerator()); //������������� ��������� �������� � �������
			//����������� �������
			LegendTitle legend = chart.getLegend();
			legend.setBackgroundPaint(chartStyle.getPlotBackgroundColor());
			legend.setPosition(RectangleEdge.RIGHT);
			legend.setBorder(0,0,0,0);
			legend.setItemFont(plainFont20);
		}

		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);
		title.setFont(boldFont);

		return chart;
	}

	/**
	 * ������ 3D ���������� ���������
	 * @param chartSettings ��������� ���������
	 * @param productList ��������, ������������ �� ���������
	 * @param seriesNum ���������� �����
	 * @return ���������
	 */
	private JFreeChart create3DBarChart(ChartSettings chartSettings, java.util.List<BarChartItem> productList, int seriesNum)
	{
		ChartStyle chartStyle = chartSettings.getChartStyle();
		final JFreeChart chart = ChartFactory.createBarChart3D(
				chartSettings.getChartTitle(),
				chartSettings.getTitleAxisX(),
				chartSettings.getTitleAxisY(),
				(CategoryDataset) chartSettings.getDataset(),
				PlotOrientation.VERTICAL,
				chartSettings.isShowLegend(),
				true,
				false
		);
		chart.setBackgroundPaint(chartStyle.getBackgroundColor()); // ���� ���� ��� ���� ���������

        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(chartStyle.getPlotBackgroundColor()); // ������������� ���� ���� �������
		plot.setRangeGridlinePaint(chartStyle.getRangeGridlineColor()); // ������������� ���� ��� ����� �����
		plot.setOutlineStroke(null); //������� ������� � ���������
		plot.setForegroundAlpha(chartStyle.getForegroundAlpha());

		// ������������� ����� ��� ������� ������� ���������
		DifferenceBar3DRenderer renderer = new DifferenceBar3DRenderer(seriesNum);
		for(int i = 0; i < seriesNum; i++)
		{
			renderer.addSeriesColor(i, new SeriesColor(productList.get(i).getPositiveColor(), productList.get(i).getNegativeColor()));
		}

		if (chartSettings.isShowLabelsOnChart())
		{
			// ��������� ������� ��� ��������� (��������, "25 000.25 ���.")
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2} ���.", new DecimalFormat("#,##0.##")));
			renderer.setBaseItemLabelsVisible(true);
			renderer.setBaseItemLabelFont(plainFont20);
		}
		plot.setRenderer(renderer);

		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//������������� ������� ���� �� �������

		// ��� OX
		final MultiLineSubCategoryAxis domainAxis = new MultiLineSubCategoryAxis("");
		for(int i = 0; i < seriesNum; i++)
			domainAxis.addSubCategory(productList.get(i).getSeries());
		domainAxis.setLabelFont(plainFont20);
		domainAxis.setTickLabelFont(plainFont16);
		domainAxis.setSubLabelFont(plainFont16);
		domainAxis.setCategoryLabelPositionOffset(20);
		domainAxis.setMaximumCategoryLabelLines(3);
		plot.setDomainAxis(domainAxis);

		// ��� OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.1);
		yAxis.setLabelFont(plainFont20);
		yAxis.setTickLabelFont(plainFont18);

		// ���������
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);
		title.setFont(boldFont);

        return chart;
	}

	/**
	 * ������ 3D ���������� ���������
	 * @param chartSettings ��������� ���������
	 * @param productList ��������, ������������ �� ���������
	 * @param seriesNum ���������� �����
	 * @param imageUrl ��� ������ �� ��������
	 * @return ���������
	 */
	private JFreeChart createImageBarChart(ChartSettings chartSettings, java.util.List<BarChartItem> productList, int seriesNum, String imageUrl) throws ChartException
	{
		return createImageBarChart(chartSettings, productList, seriesNum, new ImageBarRenderer(imageUrl));
	}

	/**
	 * ������ 3D ���������� ���������
	 * @param chartSettings ��������� ���������
	 * @param productList ��������, ������������ �� ���������
	 * @param seriesNum ���������� �����
	 * @param imageData �������� � ���� �������
	 * @return ���������
	 */
	private JFreeChart createImageBarChart(ChartSettings chartSettings, java.util.List<BarChartItem> productList, int seriesNum, byte[] imageData) throws ChartException
	{
		return createImageBarChart(chartSettings, productList, seriesNum, new ImageBarRenderer(imageData));
	}

	private JFreeChart createImageBarChart(ChartSettings chartSettings, java.util.List<BarChartItem> productList, int seriesNum, ImageBarRenderer renderer)
	{
		ChartStyle chartStyle = chartSettings.getChartStyle();
		final JFreeChart chart = ChartFactory.createBarChart3D(
				chartSettings.getChartTitle(),
				chartSettings.getTitleAxisX(),
				chartSettings.getTitleAxisY(),
				(CategoryDataset) chartSettings.getDataset(),
				PlotOrientation.VERTICAL,
				chartSettings.isShowLegend(),
				true,
				false
		);
		chart.setBackgroundPaint(chartStyle.getBackgroundColor()); // ���� ���� ��� ���� ���������

        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(chartStyle.getPlotBackgroundColor()); // ������������� ���� ���� �������
		plot.setRangeGridlinePaint(chartStyle.getRangeGridlineColor()); // ������������� ���� ��� ����� �����
		plot.setOutlineStroke(null); //������� ������� � ���������
		plot.setForegroundAlpha(chartStyle.getForegroundAlpha());

		if (chartSettings.isShowLabelsOnChart())
		{
			// ��������� ������� ��� ��������� (��������, "25 000.25 ���.")
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2} ���.", new DecimalFormat("#,##0.##")));
			renderer.setBaseItemLabelsVisible(true);
			renderer.setBaseItemLabelFont(plainFont20);
		}
		plot.setRenderer(renderer);

		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//������������� ������� ���� �� �������

		// ��� OX
		final MultiLineSubCategoryAxis domainAxis = new MultiLineSubCategoryAxis("");
		for(int i = 0; i < seriesNum; i++)
			domainAxis.addSubCategory(productList.get(i).getSeries());
		domainAxis.setLabelFont(plainFont20);
		domainAxis.setTickLabelFont(plainFont16);
		domainAxis.setSubLabelFont(plainFont16);
		domainAxis.setCategoryLabelPositionOffset(20);
		domainAxis.setMaximumCategoryLabelLines(3);
		plot.setDomainAxis(domainAxis);

		// ��� OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.1);
		yAxis.setLabelFont(plainFont20);
		yAxis.setTickLabelFont(plainFont18);

		// ���������
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);
		title.setFont(boldFont);

        return chart;
	}

	/**
	 * ������ 3D �������� ���������� ���������
	 * @param chartSettings ��������� ���������
	 * @param productList ��������, ������������ �� ���������
	 * @return ���������
	 */
	private JFreeChart create3DStackedBarChartWithSeveralDomainAxis(ChartSettings chartSettings, int seriesNum, java.util.List<StackedBarChartItem> productList)
	{
		CategoryDataset dataset = (CategoryDataset)chartSettings.getDataset();
		final JFreeChart chart = ChartFactory.createStackedBarChart3D(
				chartSettings.getChartTitle(),
				chartSettings.getTitleAxisX(),
				chartSettings.getTitleAxisY(),
				dataset,
				PlotOrientation.VERTICAL,
				chartSettings.isShowLegend(),
				true,
				false
		);
		ChartStyle chartStyle = chartSettings.getChartStyle();
		chart.setBackgroundPaint(chartStyle.getBackgroundColor()); // ���� ���� ��� ���� ���������

        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(chartStyle.getPlotBackgroundColor()); // ������������� ���� ����
		plot.setRangeGridlinePaint(chartStyle.getRangeGridlineColor()); // ������������� ���� ��� ����� �����
		plot.setOutlineStroke(null); //������� ������� � ���������
		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//������������� ������� ���� �� �������

		Category[] categories = new Category[seriesNum];
		CategoryItemRenderer renderer = plot.getRenderer();
		for(int i = 0; i < seriesNum; i++)
		{
			StackedBarChartItem bar = productList.get(i);
			Category category = bar.getCategory();
			renderer.setSeriesPaint(i, category.getChartColor());
			renderer.setSeriesItemLabelPaint(i, category.getLabelColor());
			categories[i] = category;
		}

		LegendItemCollection collection = getLegendItemCollection(categories);
		if (collection != null)
			plot.setFixedLegendItems(collection);

		DublicateCategoryDataset dataset2 = new DublicateCategoryDataset();
		DublicateCategoryDataset dataset3 = new DublicateCategoryDataset();
		int i = 0;
		for (StackedBarChartItem chartItem : productList)
		{
			String amount = StringHelper.getStringInNumberFormat(Math.round(chartItem.getValue())+"");
			if (i % 2 == 0)
				dataset3.addValue(chartItem.getValue(), chartItem.getCategory().getId(), i / 2, amount);
			else
				dataset2.addValue(chartItem.getValue(), chartItem.getCategory().getId(), i / 2, amount);
			i++;
		}

		// ������ ��� OX
		final CategoryAxis axis = plot.getDomainAxis();
		axis.setMaximumCategoryLabelLines(6);
		axis.setLabelFont(plainFont20);
		axis.setTickLabelFont(plainFont18);
		plot.setDomainAxis(0, axis);
		plot.setDataset(0, dataset);
		plot.mapDatasetToDomainAxis(0,0);

		// ������ ��� OX
		CustomCategoryAxis axis2 = new CustomCategoryAxis();
		axis2.setLabel(dataset2.getRowKey());
		axis2.setAxisLabelEdge(RectangleEdge.LEFT);
		axis2.setLabelFont(boldFont14);
		axis2.setTickLabelFont(boldFont14);
		plot.setDomainAxis(1, axis2);
		plot.setDataset(1, dataset2);
		plot.setDomainAxisLocation(1, AxisLocation.BOTTOM_OR_LEFT);
		plot.mapDatasetToDomainAxis(1,1);

		// ������ ��� OX
		CustomCategoryAxis axis3 = new CustomCategoryAxis();
		axis3.setLabel(dataset3.getRowKey());
		axis3.setAxisLabelEdge(RectangleEdge.LEFT);
		axis3.setLabelFont(boldFont14);
		axis3.setTickLabelFont(boldFont14);
		axis3.setAxisLineVisible(false);
		axis3.setTickMarksVisible(false);
		plot.setDomainAxis(2, axis3);
		plot.setDataset(2, dataset3);
		plot.setDomainAxisLocation(2, AxisLocation.BOTTOM_OR_LEFT);
		plot.mapDatasetToDomainAxis(2,2);

		// ��� OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.1);
		yAxis.setLabelFont(plainFont20);
		yAxis.setTickLabelFont(plainFont18);

		// ���������
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);
		title.setFont(boldFont);

		// �������
		LegendTitle legend = chart.getLegend();
		legend.setHorizontalAlignment(HorizontalAlignment.LEFT);// ������������ �� �����������
		legend.setPosition(RectangleEdge.TOP);// ������� ��� ��������
		legend.setBorder(0,0,0,0);// ������� �������
		legend.setBackgroundPaint(chartStyle.getBackgroundColor());// ������� �������
		legend.setItemFont(plainFont20);

        return chart;
	}

	/**
	 * ������ 3D �������� ���������� ��������� � ������������ �� ����
	 * @param chartSettings ��������� ���������
	 * @param seriesNum ���������� �����
	 * @param productList ��������, ������������ �� ���������
	 * @return ���������
	 */
	private JFreeChart create3DStackedBarChart(ChartSettings chartSettings, int seriesNum, java.util.List<StackedBarChartItem> productList)
	{
		final JFreeChart chart = ChartFactory.createStackedBarChart3D(
				chartSettings.getChartTitle(),
				chartSettings.getTitleAxisX(),
				chartSettings.getTitleAxisY(),
				(CategoryDataset)chartSettings.getDataset(),
				PlotOrientation.VERTICAL,
				chartSettings.isShowLegend(),
				true,
				false
		);
		chart.setBackgroundPaint(Color.white); // ���� ���� ��� ���� ���������

		ChartStyle chartStyle = chartSettings.getChartStyle();
        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(chartStyle.getBackgroundColor()); // ������������� ���� ����
		plot.setDomainGridlinesVisible(chartStyle.isDomainGridlinesVisible()); // ������������� ��������� ��� ����� ����� ��� X
		plot.setDomainGridlinePaint(chartStyle.getDomainGridlineColor()); // ������������� ���� ��� ����� ����� ��� X
		plot.setRangeGridlinesVisible(chartStyle.isRangeGridlinesVisible()); // ������������� ��������� ��� ����� ����� ��� Y
		plot.setRangeGridlinePaint(chartStyle.getRangeGridlineColor()); // ������������� ���� ��� ����� ����� ��� X
		plot.setOutlineStroke(null); //������� ������� � ���������
		ImageIcon imageIcon = chartSettings.getChartStyle().getBackgroundImage();
		if (imageIcon != null)
		{
			plot.setBackgroundImage(imageIcon.getImage()); // ������������� ����������� �� ��� �������
		}

		Category[] categories = new Category[seriesNum];
		CategoryItemRenderer renderer = plot.getRenderer();
		for(int i = 0; i < seriesNum; i++)
		{
			StackedBarChartItem bar = productList.get(i);
			renderer.setSeriesPaint(i, bar.getCategory().getChartColor());
			renderer.setSeriesItemLabelPaint(i, bar.getCategory().getLabelColor());
			renderer.setSeriesItemLabelsVisible(i, true);
			renderer.setSeriesItemLabelGenerator(i, new StandardCategoryItemLabelGenerator(bar.getCategory().getLabelText(), new DecimalFormat("#,##0.##")));
			renderer.setSeriesItemLabelFont(i, plainFont20);

			categories[i] = bar.getCategory();
		}

		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//������������� ������� ���� �� �������
		// ��� OX
		final CategoryAxis axis = plot.getDomainAxis();
		axis.setVisible(chartSettings.isShowAxisX());
		axis.setMaximumCategoryLabelLines(6);
		axis.setLabelFont(plainFont20);
		axis.setTickLabelFont(plainFont18);

		// ��� OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.3);
		yAxis.setVisible(chartSettings.isShowAxisY());
		yAxis.setLabelFont(plainFont20);
		yAxis.setTickLabelFont(plainFont18);
		plot.setRangeAxis(0, yAxis);

		// ���������
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);
		title.setFont(boldFont);

		if (chartSettings.isShowLegend())
		{
			// �������
			LegendTitle legend = chart.getLegend();
			legend.setHorizontalAlignment(HorizontalAlignment.LEFT);// ������������ �� �����������
			legend.setPosition(RectangleEdge.BOTTOM);// ������� ��� ��������
			legend.setBorder(0, 0, 0, 0);// ������� �������
			legend.setItemFont(plainFont20);

			LegendItemCollection collection = getLegendItemCollection(categories);
			if (collection != null)
				plot.setFixedLegendItems(collection);
		}

        return chart;
	}

	/**
	 * ������ 3D �������� ���������� ��������� � ������������ �� ���� + ������������ ������ ��� Y � ��������� ����������
	 * @param chartSettings ��������� ���������
	 * @param productList ��������, ������������ �� ���������
	 * @param yAxisValues ������ �������� ��� ��� (������������ ������ �� �������)
	 * @return ���������
	 */
	private JFreeChart create3DStackedBarChart(ChartSettings chartSettings, int seriesNum, java.util.List<StackedBarChartItem> productList, String[] yAxisValues)
	{
		final JFreeChart chart = ChartFactory.createStackedBarChart3D(
				chartSettings.getChartTitle(),
				chartSettings.getTitleAxisX(),
				chartSettings.getTitleAxisY(),
				(CategoryDataset)chartSettings.getDataset(),
				PlotOrientation.VERTICAL,
				chartSettings.isShowLegend(),
				true,
				false
		);
		ChartStyle chartStyle = chartSettings.getChartStyle();
		chart.setBackgroundPaint(chartStyle.getBackgroundColor()); // ���� ���� ��� ���� ���������

        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(chartStyle.getPlotBackgroundColor()); // ������������� ���� ����
		plot.setRangeGridlinesVisible(false); // ������������� ���� ��� ����� �����
		plot.setOutlineStroke(null); //������� ������� � ���������

		ImageIcon imageIcon = chartStyle.getBackgroundImage();
		if (imageIcon != null)
		{
			plot.setBackgroundImage(imageIcon.getImage()); // ������������� ����������� �� ��� �������
		}

		CategoryItemRenderer renderer = plot.getRenderer();
		for(int i = 0; i < seriesNum; i++)
		{
			StackedBarChartItem bar = productList.get(i);
			renderer.setSeriesPaint(i, bar.getCategory().getChartColor());
			renderer.setSeriesItemLabelPaint(i,bar.getCategory().getLabelColor());
			renderer.setSeriesItemLabelsVisible(i, true);
			renderer.setSeriesItemLabelGenerator(i, new StandardCategoryItemLabelGenerator(bar.getCategory().getLabelText(), new DecimalFormat("#,##0.##")));
			renderer.setSeriesItemLabelFont(i, plainFont20);
		}

		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//������������� ������� ���� �� �������
		// ��� OX
		final CategoryAxis axis = plot.getDomainAxis();
		axis.setMaximumCategoryLabelLines(6);
		axis.setLabelFont(plainFont20);
		axis.setTickLabelFont(plainFont18);

		// ��� OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.25);
		yAxis.setVisible(false);
		plot.setRangeAxis(0, yAxis);

		final CustomSymbolAxis yAxis2 = new CustomSymbolAxis("", yAxisValues);
		yAxis2.setTickLabelFont(plainFont18);
		yAxis2.setAxisLineVisible(false);
		yAxis2.setTickMarksVisible(false);
		plot.setRangeAxis(1, yAxis2);

		// ���������
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);
		title.setFont(boldFont);

        return chart;
	}

	/**
	 * ������ �������� ������, ���������� ������� �����
	 * @param charTitle ��������� �������
	 * @param dataset Dataset ��� �������
	 * @param productList ��������, ������������ �� �������
	 * @return ������
	 */
	private JFreeChart createStakedAreaChart(String charTitle, String domainAxisTitle, String rangeAxisTitle, DefaultTableXYDataset dataset, java.util.List<AreaChartItem> productList)
	{
		JFreeChart chart = ChartFactory.createStackedXYAreaChart(
            charTitle,  // ������� �������
            domainAxisTitle, // ������� ��� X
            rangeAxisTitle,  // ������� ��� Y
            dataset, // ������
            PlotOrientation.VERTICAL, // ���������� �������
            true,                            // legend
            true,                            // tooltips
            false                            // urls
        );

		chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();

		AreaChartItem chartItem = productList.get(0);
		LegendItemCollection collection = getLegendItemCollection(chartItem.getCategory1(), chartItem.getCategory2());
		if (collection != null)
			plot.setFixedLegendItems(collection);

		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0)); // ������� ���� �� �������
	    plot.setForegroundAlpha(0.8f); // ������������
	    plot.setBackgroundPaint(Color.white); // ���� ���� �������
        plot.setDomainGridlinePaint(Color.lightGray); // ���� ����� �� ��� X
		plot.setDomainGridlineStroke(new BasicStroke(1.0f)); // ��� ����� �� ��� X (������ �������� �����)
        plot.setRangeGridlinesVisible(false);  // �� ���������� ���������� ����� (��� Y)

		// ��� Y
		final NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setLabelAngle(0);// ���� ������� ������� ���

		NumberTickUnit unit = getMonyFormatTickUnit(dataset);
		if (unit == null)
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //���� �� ������ ������� ����� ��� ���, ���������� �����������
		else
			rangeAxis.setTickUnit(unit);

		rangeAxis.setAutoTickUnitSelection(false);
		rangeAxis.setTickLabelsVisible(true);// ���������� ������� �� ���
		rangeAxis.setLabelFont(plainFont20);// ����� ��� ��������� ���
		rangeAxis.setTickLabelFont(plainFont18);// ����� ��� �������� �� ���

		// ��������� ������ �������� �� ��� X
		String[] tickLabels = new String[productList.size()];
		int i = 0;
		for (AreaChartItem areaChartItem : productList)
			tickLabels[i++] =  areaChartItem.getDescr();

		// ��� X
		final CustomSymbolAxis domainAxis = new CustomSymbolAxis(domainAxisTitle, tickLabels);// ������� ��� � ��������� �� �������
        domainAxis.setLowerMargin(0.0);// ������ �����
	    domainAxis.setUpperMargin(0.0);// ������ ������
		domainAxis.setTickLabelAngle(-Math.PI / 3.0); // ���� ������� ��������
		domainAxis.setTickLabelPadding(10); // ������ �� ��� ���������
		domainAxis.setGridBandsVisible(false);
		domainAxis.setLabelFont(plainFont20);// ����� ��� ��������� ���
		domainAxis.setTickLabelFont(plainFont18);// ����� ��� �������� �� ���
		plot.setDomainAxis(domainAxis);

		StackedXYAreaRenderer2 renderer = (StackedXYAreaRenderer2)plot.getRenderer();
		renderer.setSeriesPaint(0, chartItem.getColor1());
		renderer.setSeriesPaint(1, chartItem.getColor2());

		// �������
		LegendTitle legend = chart.getLegend();
		legend.setHorizontalAlignment(HorizontalAlignment.LEFT);// ������������ �� �����������
		legend.setPosition(RectangleEdge.TOP);// ������� ��� ��������
		legend.setBorder(0,0,0,0);// ������� �������
		legend.setItemFont(plainFont20);
		legend.setItemLabelPadding(new RectangleInsets(1.0,1.0,1.0,1.0));

		// ��������� �������
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);// ��� ��������
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);// �����
		title.setFont(boldFont);

        return chart;

	}

	private LegendItemCollection getLegendItemCollection(Category... categories)
	{
		LegendItemCollection collection = new LegendItemCollection();
		for (Category category : categories)
		{
			ImageLegendItem legendItem = category.getLegendItem();
			if (legendItem == null)
				return null;

			LegendItem item = legendItem.getLegendItem();
			if (item == null)
				return null;

			collection.add(item);
		}

		return collection;
	}

	private NumberTickUnit getMonyFormatTickUnit(DefaultTableXYDataset dataset)
	{
		if (dataset == null || dataset.getSeriesCount() == 0)
			return null;

		int elementCountInSeries = dataset.getSeries(0).getItemCount();
		for (int i = 1; i < dataset.getSeriesCount(); i++)
			if (elementCountInSeries != dataset.getSeries(i).getItemCount())
				return null;

		double maxY = 0.0;
		for (int i = 0; i < elementCountInSeries; i++)
		{
			double sum = 0;
			for (int j = 0; j < dataset.getSeriesCount(); j++)
			{
				sum += ((XYDataItem)dataset.getSeries(j).getItems().get(i)).getYValue();
			}
			maxY = Math.max(maxY, sum);
		}

		return new MoneyTickUnit(maxY / 10);
	}

	/**
	 * ��������� ����� �� 2� ���������� ������
	 * @param value - �����
	 * @return ���������� �����
	 */
	private double round(double value)
	{
		return Math.round(value * 100.0) / 100.0;
	}
}
