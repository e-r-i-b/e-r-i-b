package com.rssl.phizic.utils.chart;

import com.rssl.phizic.utils.pdf.ChartStyle;
import org.jfree.data.general.Dataset;

/**
 * @author lepihina
 * @ created 26.08.13
 * @ $Author$
 * @ $Revision$
 */
public class ChartSettings
{
	private Dataset dataset;

	private int width;
	private int height;
	private String chartTitle = "";

	private boolean showAxisX = true;
	private String titleAxisX = "";
	private boolean showAxisY = true;
	private String titleAxisY = "";

	private boolean showLegend = true;
	private boolean showLabelsOnChart = true;

	private ChartStyle chartStyle = new ChartStyle();

	/**
	 * @param width - ширина генерируемой диаграммы
	 * @param height - высота генерируемой диаграммы
	 */
	public ChartSettings(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	/**
	 * @param width - ширина генерируемой диаграммы
	 * @param height - высота генерируемой диаграммы
	 * @param chartStyle - настройки стиля отображения диаграммы
	 */
	public ChartSettings(int width, int height, ChartStyle chartStyle)
	{
		this.width = width;
		this.height = height;
		this.chartStyle = chartStyle;
	}

	/**
	 * @return данные для построения диаграммы
	 */
	public Dataset getDataset()
	{
		return dataset;
	}

	/**
	 * @param dataset - данные для построения диаграммы
	 */
	public void setDataset(Dataset dataset)
	{
		this.dataset = dataset;
	}

	/**
	 * @return ширина генерируемой диаграммы
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @return высота генерируемой диаграммы
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @return заголовок диаграммы
	 */
	public String getChartTitle()
	{
		return chartTitle;
	}

	/**
	 * @param chartTitle - заголовок диаграммы
	 */
	public void setChartTitle(String chartTitle)
	{
		this.chartTitle = chartTitle;
	}

	/**
	 * @return true - отображать ось абсцисс
	 */
	public boolean isShowAxisX()
	{
		return showAxisX;
	}

	/**
	 * @param showAxisX  true - отображать ось абсцисс
	 */
	public void setShowAxisX(boolean showAxisX)
	{
		this.showAxisX = showAxisX;
	}

	/**
	 * @return заголовок оси абсцисс
	 */
	public String getTitleAxisX()
	{
		return titleAxisX;
	}

	/**
	 * @param titleAxisX - заголовок оси абсцисс
	 */
	public void setTitleAxisX(String titleAxisX)
	{
		this.titleAxisX = titleAxisX;
	}

	/**
	 * @return true - отображать ось ординат
	 */
	public boolean isShowAxisY()
	{
		return showAxisY;
	}

	/**
	 * @param showAxisY  true - отображать ось ординат
	 */
	public void setShowAxisY(boolean showAxisY)
	{
		this.showAxisY = showAxisY;
	}

	/**
	 * @return заголовок оси ординат
	 */
	public String getTitleAxisY()
	{
		return titleAxisY;
	}

	/**
	 * @param titleAxisY - заголовок оси ординат
	 */
	public void setTitleAxisY(String titleAxisY)
	{
		this.titleAxisY = titleAxisY;
	}

	/**
	 * @return true - отображать легенду
	 */
	public boolean isShowLegend()
	{
		return showLegend;
	}

	/**
	 * @param showLegend - true - отображать легенду
	 */
	public void setShowLegend(boolean showLegend)
	{
		this.showLegend = showLegend;
	}

	/**
	 * @return true - отображать подписи на графике
	 */
	public boolean isShowLabelsOnChart()
	{
		return showLabelsOnChart;
	}

	/**
	 * @param showLabelsOnChart - true - отображать подписи на графике
	 */
	public void setShowLabelsOnChart(boolean showLabelsOnChart)
	{
		this.showLabelsOnChart = showLabelsOnChart;
	}

	/**
	 * @return настройки стиля отображения диаграммы
	 */
	public ChartStyle getChartStyle()
	{
		return chartStyle;
	}

	/**
	 * @param chartStyle - настройки стиля отображения диаграммы
	 */
	public void setChartStyle(ChartStyle chartStyle)
	{
		this.chartStyle = chartStyle;
	}
}
