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
	 * Генерирует трехмерную круговую диаграмму
	 * @param productList список продуктов для отображения на диаграмме
	 * @param chartSettings настройки диаграммы
	 * @return диаграмма
	 */
	public BufferedImage generate3DPieChart(java.util.List<PieChartItem> productList, ChartSettings chartSettings)
	{
		chartSettings.setDataset(createPieDataset(productList));
		final JFreeChart jFreeChart = create3DPieChart(chartSettings, productList);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //изображение с диаграммой
	}

	/**
	 * Генерирует 3D столбцовую диаграмму
	 * @param productList - список продуктов для отображения на диаграмме
	 * @param chartSettings - настройки диаграммы
	 * @param seriesNum - количество серий
	 * @return диаграмма
	 */
	public BufferedImage generate3DBarChart(java.util.List<BarChartItem> productList, ChartSettings chartSettings, int seriesNum)
	{
		chartSettings.setDataset(createBarDataset(productList));
		final JFreeChart jFreeChart = create3DBarChart(chartSettings, productList, seriesNum);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //изображение с диаграммой
	}

	/**
	 * Генерирует столбцовую диаграмму с иконками
	 * @param productList - список продуктов для отображения на диаграмме
	 * @param chartSettings - настройки диаграммы
	 * @param seriesNum - количество серий
	 * @param imageUrl урл ссылки на картинку
	 * @return диаграмма
	 */
	public BufferedImage generateImageBarChart(java.util.List<BarChartItem> productList, ChartSettings chartSettings, int seriesNum, String imageUrl) throws ChartException
	{
		chartSettings.setDataset(createBarDataset(productList));
		final JFreeChart jFreeChart = createImageBarChart(chartSettings, productList, seriesNum, imageUrl);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //изображение с диаграммой
	}

	/**
	 * Генерирует столбцовую диаграмму с иконками
	 * @param productList - список продуктов для отображения на диаграмме
	 * @param chartSettings - настройки диаграммы
	 * @param seriesNum - количество серий
	 * @param imageData картинка в виде массива
	 * @return диаграмма
	 */
	public BufferedImage generateImageBarChart(java.util.List<BarChartItem> productList, ChartSettings chartSettings, int seriesNum, byte[] imageData) throws ChartException
	{
		chartSettings.setDataset(createBarDataset(productList));
		final JFreeChart jFreeChart = createImageBarChart(chartSettings, productList, seriesNum, imageData);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //изображение с диаграммой
	}

	/**
	 * Генерирует 3D стековую столбцовую диаграмму
	 * @param productList - список продуктов для отображения на диаграмме
	 * @param chartSettings - настройки диаграммы
	 * @param seriesNum - количество серий
	 * @return диаграмма
	 */
	public BufferedImage generate3DStakedBarChartWithSeveralDomainAxis(java.util.List<StackedBarChartItem> productList, ChartSettings chartSettings, int seriesNum)
	{
		chartSettings.setDataset(createStackedBarDataset(productList));
		final JFreeChart jFreeChart = create3DStackedBarChartWithSeveralDomainAxis(chartSettings, seriesNum, productList);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //изображение с диаграммой
	}

	/**
	 * Генерирует 3D стековую столбцовую диаграмму с картинкой на фоне
	 * @param productList - список продуктов для отображения на диаграмме
	 * @param chartSettings - настройки диаграммы
	 * @param seriesNum - количество серий
	 * @return диаграмма
	 */
	public BufferedImage generate3DStakedBarChart(java.util.List<StackedBarChartItem> productList, ChartSettings chartSettings, int seriesNum)
	{
		chartSettings.setDataset(createStackedBarDataset(productList));
		final JFreeChart jFreeChart = create3DStackedBarChart(chartSettings, seriesNum, productList);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //изображение с диаграммой
	}

	/**
	 * Генерирует 3D стековую столбцовую диаграмму с картинкой на фоне
	 * @param chartSettings - настройки диаграммы
	 * @param productList - список продуктов для отображения на диаграмме
	 * @param seriesNum - количество серий
	 * @param yAxisValues - массив значений для оси (отображается справа от графика)
	 * @return диаграмма
	 */
	public BufferedImage generate3DStakedBarChart(java.util.List<StackedBarChartItem> productList, ChartSettings chartSettings, int seriesNum, String[] yAxisValues)
	{
		chartSettings.setDataset(createStackedBarDataset(productList));
		final JFreeChart jFreeChart = create3DStackedBarChart(chartSettings, seriesNum, productList, yAxisValues);
		return jFreeChart.createBufferedImage(chartSettings.getWidth(), chartSettings.getHeight()); //изображение с диаграммой
	}

	/**
	 * Генерирует стековый график по точкам
	 * @param charTitle заголовок графика
	 * @param domainAxisTitle заголовок оси X
	 * @param rangeAxisTitle заголовок оси Y
	 * @param productList список значений
	 * @param width ширина графика
	 * @param height высота графика
	 * @return график
	 */
	public BufferedImage generateStakedAreaChart(String charTitle, String domainAxisTitle, String rangeAxisTitle, java.util.List<AreaChartItem> productList, int width, int height)
	{
		final DefaultTableXYDataset dataset = createStakedAreaDataset(productList);
		final JFreeChart jFreeChart = createStakedAreaChart(charTitle, domainAxisTitle, rangeAxisTitle, dataset, productList);
		return jFreeChart.createBufferedImage(width, height); //изображение с диаграммой
	}

	/**
	 * Формирует Dataset для круговой диаграммы
	 * @param productList список продуктов для отображения на диаграмме
	 * @return Dataset для круговой диаграммы
	 */
	private PieDataset createPieDataset(java.util.List<PieChartItem> productList)
	{
		final DefaultPieDataset pieDataset = new DefaultPieDataset();
		for (PieChartItem pieChartItem : productList)
			pieDataset.setValue(pieChartItem.getName(), pieChartItem.getValue());
		return pieDataset;
	}

	/**
	 * Формирует Dataset для столбчатой диаграммы
	 * @param productList список продуктов для отображения на диаграмме
	 * @return Dataset для столбчатой диаграммы
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
	 * Формирует Dataset для столбчатой диаграммы
	 * @param productList список продуктов для отображения на диаграмме
	 * @return Dataset для столбчатой диаграммы
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
	 * Формирует Dataset для графика
	 * @param productList список значений для отображения на графике
	 * @return Dataset для графика
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
	 * Строит трехмерную круговую диаграмму
	 * @param chartSettings настройки диаграммы
	 * @param productList список продуктов для отображения на диаграмме
	 * @return диаграмма
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
			plot.setLabelGenerator(new PercentLabelGenerator()); //устанавливаем генератор подписей на диаграмме
		}
		else
		{
			plot.setLabelGenerator(new PercentAndTitleLabelGenerator()); //устанавливаем генератор подписей на диаграмме
		}
		plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
		plot.setLabelFont(plainFont20);
		plot.setMaximumLabelWidth(0.17);
		plot.setLabelPadding(new RectangleInsets(4, 4, 4, 4));
		plot.setSimpleLabels(false);//отображаем подписи на самой диаграмме(true) или выносим за диаграмму(false)

		for (PieChartItem pieChartItem : productList)
		{
			plot.setSectionPaint(pieChartItem.getName(), pieChartItem.getColor());
		}
		plot.setBackgroundPaint(chartStyle.getPlotBackgroundColor()); //устанавливаем цвет фона
		plot.setOutlineStroke(null); //убираем границу у диаграммы
		plot.setDarkerSides(true);//отображать тень на объемном граффике
		chart.setBorderVisible(false); //убираем границу у всего рисунка с диаграммой

		if (chartSettings.isShowLegend())
		{
			plot.setLegendItemShape(new Rectangle(20, 20, 20, 12)); // форма иконок в подписях легенды
			plot.setLegendLabelGenerator(new CustomLabelLegendGenerator()); //устанавливаем генератор подписей в легенде
			//Настраиваем легенду
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
	 * Строит 3D столбцовую диаграмму
	 * @param chartSettings настройки диаграммы
	 * @param productList значения, отображаемые на диаграмме
	 * @param seriesNum количество серий
	 * @return диаграмма
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
		chart.setBackgroundPaint(chartStyle.getBackgroundColor()); // цвет фона для всей диаграммы

        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(chartStyle.getPlotBackgroundColor()); // устанавливаем цвет фона графика
		plot.setRangeGridlinePaint(chartStyle.getRangeGridlineColor()); // устанавливаем цвет для линий сетки
		plot.setOutlineStroke(null); //убираем границу у диаграммы
		plot.setForegroundAlpha(chartStyle.getForegroundAlpha());

		// устанавливаем цвета для каждого столбца диаграммы
		DifferenceBar3DRenderer renderer = new DifferenceBar3DRenderer(seriesNum);
		for(int i = 0; i < seriesNum; i++)
		{
			renderer.addSeriesColor(i, new SeriesColor(productList.get(i).getPositiveColor(), productList.get(i).getNegativeColor()));
		}

		if (chartSettings.isShowLabelsOnChart())
		{
			// добавляем подписи над столбцами (например, "25 000.25 руб.")
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2} руб.", new DecimalFormat("#,##0.##")));
			renderer.setBaseItemLabelsVisible(true);
			renderer.setBaseItemLabelFont(plainFont20);
		}
		plot.setRenderer(renderer);

		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//устанавливаем отступы осей от графика

		// ось OX
		final MultiLineSubCategoryAxis domainAxis = new MultiLineSubCategoryAxis("");
		for(int i = 0; i < seriesNum; i++)
			domainAxis.addSubCategory(productList.get(i).getSeries());
		domainAxis.setLabelFont(plainFont20);
		domainAxis.setTickLabelFont(plainFont16);
		domainAxis.setSubLabelFont(plainFont16);
		domainAxis.setCategoryLabelPositionOffset(20);
		domainAxis.setMaximumCategoryLabelLines(3);
		plot.setDomainAxis(domainAxis);

		// ось OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.1);
		yAxis.setLabelFont(plainFont20);
		yAxis.setTickLabelFont(plainFont18);

		// заголовок
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);
		title.setFont(boldFont);

        return chart;
	}

	/**
	 * Строит 3D столбцовую диаграмму
	 * @param chartSettings настройки диаграммы
	 * @param productList значения, отображаемые на диаграмме
	 * @param seriesNum количество серий
	 * @param imageUrl урл ссылки на картинку
	 * @return диаграмма
	 */
	private JFreeChart createImageBarChart(ChartSettings chartSettings, java.util.List<BarChartItem> productList, int seriesNum, String imageUrl) throws ChartException
	{
		return createImageBarChart(chartSettings, productList, seriesNum, new ImageBarRenderer(imageUrl));
	}

	/**
	 * Строит 3D столбцовую диаграмму
	 * @param chartSettings настройки диаграммы
	 * @param productList значения, отображаемые на диаграмме
	 * @param seriesNum количество серий
	 * @param imageData картинка в виде массива
	 * @return диаграмма
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
		chart.setBackgroundPaint(chartStyle.getBackgroundColor()); // цвет фона для всей диаграммы

        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(chartStyle.getPlotBackgroundColor()); // устанавливаем цвет фона графика
		plot.setRangeGridlinePaint(chartStyle.getRangeGridlineColor()); // устанавливаем цвет для линий сетки
		plot.setOutlineStroke(null); //убираем границу у диаграммы
		plot.setForegroundAlpha(chartStyle.getForegroundAlpha());

		if (chartSettings.isShowLabelsOnChart())
		{
			// добавляем подписи над столбцами (например, "25 000.25 руб.")
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2} руб.", new DecimalFormat("#,##0.##")));
			renderer.setBaseItemLabelsVisible(true);
			renderer.setBaseItemLabelFont(plainFont20);
		}
		plot.setRenderer(renderer);

		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//устанавливаем отступы осей от графика

		// ось OX
		final MultiLineSubCategoryAxis domainAxis = new MultiLineSubCategoryAxis("");
		for(int i = 0; i < seriesNum; i++)
			domainAxis.addSubCategory(productList.get(i).getSeries());
		domainAxis.setLabelFont(plainFont20);
		domainAxis.setTickLabelFont(plainFont16);
		domainAxis.setSubLabelFont(plainFont16);
		domainAxis.setCategoryLabelPositionOffset(20);
		domainAxis.setMaximumCategoryLabelLines(3);
		plot.setDomainAxis(domainAxis);

		// ось OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.1);
		yAxis.setLabelFont(plainFont20);
		yAxis.setTickLabelFont(plainFont18);

		// заголовок
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);
		title.setFont(boldFont);

        return chart;
	}

	/**
	 * Строит 3D стековую столбцовую диаграмму
	 * @param chartSettings настройки диаграммы
	 * @param productList значения, отображаемые на диаграмме
	 * @return диаграмма
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
		chart.setBackgroundPaint(chartStyle.getBackgroundColor()); // цвет фона для всей диаграммы

        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(chartStyle.getPlotBackgroundColor()); // устанавливаем цвет фона
		plot.setRangeGridlinePaint(chartStyle.getRangeGridlineColor()); // устанавливаем цвет для линий сетки
		plot.setOutlineStroke(null); //убираем границу у диаграммы
		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//устанавливаем отступы осей от графика

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

		// Первая ось OX
		final CategoryAxis axis = plot.getDomainAxis();
		axis.setMaximumCategoryLabelLines(6);
		axis.setLabelFont(plainFont20);
		axis.setTickLabelFont(plainFont18);
		plot.setDomainAxis(0, axis);
		plot.setDataset(0, dataset);
		plot.mapDatasetToDomainAxis(0,0);

		// Вторая ось OX
		CustomCategoryAxis axis2 = new CustomCategoryAxis();
		axis2.setLabel(dataset2.getRowKey());
		axis2.setAxisLabelEdge(RectangleEdge.LEFT);
		axis2.setLabelFont(boldFont14);
		axis2.setTickLabelFont(boldFont14);
		plot.setDomainAxis(1, axis2);
		plot.setDataset(1, dataset2);
		plot.setDomainAxisLocation(1, AxisLocation.BOTTOM_OR_LEFT);
		plot.mapDatasetToDomainAxis(1,1);

		// Третья ось OX
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

		// ось OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.1);
		yAxis.setLabelFont(plainFont20);
		yAxis.setTickLabelFont(plainFont18);

		// заголовок
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);
		title.setFont(boldFont);

		// легенда
		LegendTitle legend = chart.getLegend();
		legend.setHorizontalAlignment(HorizontalAlignment.LEFT);// выравнивание по горизонтали
		legend.setPosition(RectangleEdge.TOP);// легенда под графиком
		legend.setBorder(0,0,0,0);// убираем границы
		legend.setBackgroundPaint(chartStyle.getBackgroundColor());// убираем границы
		legend.setItemFont(plainFont20);

        return chart;
	}

	/**
	 * Строит 3D стековую столбцовую диаграмму с изображением на фоне
	 * @param chartSettings настройки диаграммы
	 * @param seriesNum количество серий
	 * @param productList значения, отображаемые на диаграмме
	 * @return диаграмма
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
		chart.setBackgroundPaint(Color.white); // цвет фона для всей диаграммы

		ChartStyle chartStyle = chartSettings.getChartStyle();
        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(chartStyle.getBackgroundColor()); // устанавливаем цвет фона
		plot.setDomainGridlinesVisible(chartStyle.isDomainGridlinesVisible()); // устанавливаем видимость для линий сетки оси X
		plot.setDomainGridlinePaint(chartStyle.getDomainGridlineColor()); // устанавливаем цвет для линий сетки оси X
		plot.setRangeGridlinesVisible(chartStyle.isRangeGridlinesVisible()); // устанавливаем видимость для линий сетки оси Y
		plot.setRangeGridlinePaint(chartStyle.getRangeGridlineColor()); // устанавливаем цвет для линий сетки оси X
		plot.setOutlineStroke(null); //убираем границу у диаграммы
		ImageIcon imageIcon = chartSettings.getChartStyle().getBackgroundImage();
		if (imageIcon != null)
		{
			plot.setBackgroundImage(imageIcon.getImage()); // устанавливаем изображение на фон графика
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

		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//устанавливаем отступы осей от графика
		// ось OX
		final CategoryAxis axis = plot.getDomainAxis();
		axis.setVisible(chartSettings.isShowAxisX());
		axis.setMaximumCategoryLabelLines(6);
		axis.setLabelFont(plainFont20);
		axis.setTickLabelFont(plainFont18);

		// ось OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.3);
		yAxis.setVisible(chartSettings.isShowAxisY());
		yAxis.setLabelFont(plainFont20);
		yAxis.setTickLabelFont(plainFont18);
		plot.setRangeAxis(0, yAxis);

		// заголовок
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);
		title.setFont(boldFont);

		if (chartSettings.isShowLegend())
		{
			// легенда
			LegendTitle legend = chart.getLegend();
			legend.setHorizontalAlignment(HorizontalAlignment.LEFT);// выравнивание по горизонтали
			legend.setPosition(RectangleEdge.BOTTOM);// легенда под графиком
			legend.setBorder(0, 0, 0, 0);// убираем границы
			legend.setItemFont(plainFont20);

			LegendItemCollection collection = getLegendItemCollection(categories);
			if (collection != null)
				plot.setFixedLegendItems(collection);
		}

        return chart;
	}

	/**
	 * Строит 3D стековую столбцовую диаграмму с изображением на фоне + дорисовывает справа ось Y с заданными значениями
	 * @param chartSettings настройки диаграммы
	 * @param productList значения, отображаемые на диаграмме
	 * @param yAxisValues массив значений для оси (отображается справа от графика)
	 * @return диаграмма
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
		chart.setBackgroundPaint(chartStyle.getBackgroundColor()); // цвет фона для всей диаграммы

        final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(chartStyle.getPlotBackgroundColor()); // устанавливаем цвет фона
		plot.setRangeGridlinesVisible(false); // устанавливаем цвет для линий сетки
		plot.setOutlineStroke(null); //убираем границу у диаграммы

		ImageIcon imageIcon = chartStyle.getBackgroundImage();
		if (imageIcon != null)
		{
			plot.setBackgroundImage(imageIcon.getImage()); // устанавливаем изображение на фон графика
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

		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));//устанавливаем отступы осей от графика
		// ось OX
		final CategoryAxis axis = plot.getDomainAxis();
		axis.setMaximumCategoryLabelLines(6);
		axis.setLabelFont(plainFont20);
		axis.setTickLabelFont(plainFont18);

		// ось OY
		final ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setUpperMargin(0.25);
		yAxis.setVisible(false);
		plot.setRangeAxis(0, yAxis);

		final CustomSymbolAxis yAxis2 = new CustomSymbolAxis("", yAxisValues);
		yAxis2.setTickLabelFont(plainFont18);
		yAxis2.setAxisLineVisible(false);
		yAxis2.setTickMarksVisible(false);
		plot.setRangeAxis(1, yAxis2);

		// заголовок
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);
		title.setFont(boldFont);

        return chart;
	}

	/**
	 * Строит стековый график, закрашивая область снизу
	 * @param charTitle заголовок графика
	 * @param dataset Dataset для графика
	 * @param productList значения, отображаемые на графике
	 * @return график
	 */
	private JFreeChart createStakedAreaChart(String charTitle, String domainAxisTitle, String rangeAxisTitle, DefaultTableXYDataset dataset, java.util.List<AreaChartItem> productList)
	{
		JFreeChart chart = ChartFactory.createStackedXYAreaChart(
            charTitle,  // подпись графика
            domainAxisTitle, // подпись оси X
            rangeAxisTitle,  // подпись оси Y
            dataset, // данные
            PlotOrientation.VERTICAL, // ориентация графика
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

		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0)); // отступы осей от графика
	    plot.setForegroundAlpha(0.8f); // прозрачность
	    plot.setBackgroundPaint(Color.white); // цвет фона графика
        plot.setDomainGridlinePaint(Color.lightGray); // цвет сетки по оси X
		plot.setDomainGridlineStroke(new BasicStroke(1.0f)); // тип сетки по оси X (тонкая сплошная линия)
        plot.setRangeGridlinesVisible(false);  // не отображаем внутреннюю сетку (ось Y)

		// ось Y
		final NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setLabelAngle(0);// угол наклона подписи оси

		NumberTickUnit unit = getMonyFormatTickUnit(dataset);
		if (unit == null)
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //если не смогли собрать метки для оси, используем стандартные
		else
			rangeAxis.setTickUnit(unit);

		rangeAxis.setAutoTickUnitSelection(false);
		rangeAxis.setTickLabelsVisible(true);// отображать подписи на оси
		rangeAxis.setLabelFont(plainFont20);// шрифт для заголовка оси
		rangeAxis.setTickLabelFont(plainFont18);// шрифт для подписей на оси

		// формируем массив подписей по оси X
		String[] tickLabels = new String[productList.size()];
		int i = 0;
		for (AreaChartItem areaChartItem : productList)
			tickLabels[i++] =  areaChartItem.getDescr();

		// ось X
		final CustomSymbolAxis domainAxis = new CustomSymbolAxis(domainAxisTitle, tickLabels);// создаем ось с подписями из массива
        domainAxis.setLowerMargin(0.0);// отступ слева
	    domainAxis.setUpperMargin(0.0);// отступ справа
		domainAxis.setTickLabelAngle(-Math.PI / 3.0); // угол наклона подписей
		domainAxis.setTickLabelPadding(10); // отступ от оси координат
		domainAxis.setGridBandsVisible(false);
		domainAxis.setLabelFont(plainFont20);// шрифт для заголовка оси
		domainAxis.setTickLabelFont(plainFont18);// шрифт для подписей на оси
		plot.setDomainAxis(domainAxis);

		StackedXYAreaRenderer2 renderer = (StackedXYAreaRenderer2)plot.getRenderer();
		renderer.setSeriesPaint(0, chartItem.getColor1());
		renderer.setSeriesPaint(1, chartItem.getColor2());

		// легенда
		LegendTitle legend = chart.getLegend();
		legend.setHorizontalAlignment(HorizontalAlignment.LEFT);// выравнивание по горизонтали
		legend.setPosition(RectangleEdge.TOP);// легенда под графиком
		legend.setBorder(0,0,0,0);// убираем границы
		legend.setItemFont(plainFont20);
		legend.setItemLabelPadding(new RectangleInsets(1.0,1.0,1.0,1.0));

		// заголовок графика
		TextTitle title = chart.getTitle();
		title.setPosition(RectangleEdge.BOTTOM);// под графиком
		title.setHorizontalAlignment(HorizontalAlignment.LEFT);// слева
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
	 * Округляет число до 2х десятичных знаков
	 * @param value - число
	 * @return округленое число
	 */
	private double round(double value)
	{
		return Math.round(value * 100.0) / 100.0;
	}
}
