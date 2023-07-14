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
	 * @param width - ������ ������������ ���������
	 * @param height - ������ ������������ ���������
	 */
	public ChartSettings(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	/**
	 * @param width - ������ ������������ ���������
	 * @param height - ������ ������������ ���������
	 * @param chartStyle - ��������� ����� ����������� ���������
	 */
	public ChartSettings(int width, int height, ChartStyle chartStyle)
	{
		this.width = width;
		this.height = height;
		this.chartStyle = chartStyle;
	}

	/**
	 * @return ������ ��� ���������� ���������
	 */
	public Dataset getDataset()
	{
		return dataset;
	}

	/**
	 * @param dataset - ������ ��� ���������� ���������
	 */
	public void setDataset(Dataset dataset)
	{
		this.dataset = dataset;
	}

	/**
	 * @return ������ ������������ ���������
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @return ������ ������������ ���������
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @return ��������� ���������
	 */
	public String getChartTitle()
	{
		return chartTitle;
	}

	/**
	 * @param chartTitle - ��������� ���������
	 */
	public void setChartTitle(String chartTitle)
	{
		this.chartTitle = chartTitle;
	}

	/**
	 * @return true - ���������� ��� �������
	 */
	public boolean isShowAxisX()
	{
		return showAxisX;
	}

	/**
	 * @param showAxisX  true - ���������� ��� �������
	 */
	public void setShowAxisX(boolean showAxisX)
	{
		this.showAxisX = showAxisX;
	}

	/**
	 * @return ��������� ��� �������
	 */
	public String getTitleAxisX()
	{
		return titleAxisX;
	}

	/**
	 * @param titleAxisX - ��������� ��� �������
	 */
	public void setTitleAxisX(String titleAxisX)
	{
		this.titleAxisX = titleAxisX;
	}

	/**
	 * @return true - ���������� ��� �������
	 */
	public boolean isShowAxisY()
	{
		return showAxisY;
	}

	/**
	 * @param showAxisY  true - ���������� ��� �������
	 */
	public void setShowAxisY(boolean showAxisY)
	{
		this.showAxisY = showAxisY;
	}

	/**
	 * @return ��������� ��� �������
	 */
	public String getTitleAxisY()
	{
		return titleAxisY;
	}

	/**
	 * @param titleAxisY - ��������� ��� �������
	 */
	public void setTitleAxisY(String titleAxisY)
	{
		this.titleAxisY = titleAxisY;
	}

	/**
	 * @return true - ���������� �������
	 */
	public boolean isShowLegend()
	{
		return showLegend;
	}

	/**
	 * @param showLegend - true - ���������� �������
	 */
	public void setShowLegend(boolean showLegend)
	{
		this.showLegend = showLegend;
	}

	/**
	 * @return true - ���������� ������� �� �������
	 */
	public boolean isShowLabelsOnChart()
	{
		return showLabelsOnChart;
	}

	/**
	 * @param showLabelsOnChart - true - ���������� ������� �� �������
	 */
	public void setShowLabelsOnChart(boolean showLabelsOnChart)
	{
		this.showLabelsOnChart = showLabelsOnChart;
	}

	/**
	 * @return ��������� ����� ����������� ���������
	 */
	public ChartStyle getChartStyle()
	{
		return chartStyle;
	}

	/**
	 * @param chartStyle - ��������� ����� ����������� ���������
	 */
	public void setChartStyle(ChartStyle chartStyle)
	{
		this.chartStyle = chartStyle;
	}
}
