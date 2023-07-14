package com.rssl.phizic.utils.chart;

import java.awt.*;

/**
 * @author lepihina
 * @ created 29.08.13
 * @ $Author$
 * @ $Revision$
 */
public class BarChartItem
{
	private double value; // ��������
	private String category; // ��������� (������� ���������)
	private String series; // ����� (������� ��� ��������)
	private Color positiveColor; // ���� ������� ��� ������������� ��������
	private Color negativeColor; // ���� ������� ��� ������������� ��������

	/**
	 * @param value - ��������
	 * @param category - ���������
	 * @param series - �����
	 * @param positiveColor - ���� ������� ��� ������������� ��������
	 */
	public BarChartItem(double value, String category, String series, Color positiveColor)
	{
		this.value = value;
		this.category = category;
		this.series = series;
		this.positiveColor = positiveColor;
	}

	/**
	 * @param value - ��������
	 * @param category - ���������
	 * @param series - �����
	 * @param positiveColor - ���� ������� ��� ������������� ��������
	 * @param negativeColor - ���� ������� ��� ������������� ��������
	 */
	public BarChartItem(double value, String category, String series, Color positiveColor, Color negativeColor)
	{
		this.value = value;
		this.category = category;
		this.series = series;
		this.positiveColor = positiveColor;
		this.negativeColor = negativeColor;
	}

	/**
	 * @return ��������
	 */
	public double getValue()
	{
		return value;
	}

	/**
	 * @param value - ��������
	 */
	public void setValue(double value)
	{
		this.value = value;
	}

	/**
	 * @return ���������
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * @param category - ���������
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}

	/**
	 * @return �����
	 */
	public String getSeries()
	{
		return series;
	}

	/**
	 * @param series - �����
	 */
	public void setSeries(String series)
	{
		this.series = series;
	}

	/**
	 * @return ���� ������� ��� ������������� ��������
	 */
	public Color getPositiveColor()
	{
		return positiveColor;
	}

	/**
	 * @return ���� ������� ��� ������������� ��������
	 */
	public Color getNegativeColor()
	{
		return negativeColor;
	}
}
