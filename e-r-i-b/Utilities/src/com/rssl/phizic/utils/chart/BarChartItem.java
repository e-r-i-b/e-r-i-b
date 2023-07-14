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
	private double value; // значение
	private String category; // категория (подпись категории)
	private String series; // серия (подпись под столбцом)
	private Color positiveColor; // цвет столбца для положительных значений
	private Color negativeColor; // цвет столбца для отрицательных значений

	/**
	 * @param value - значение
	 * @param category - категория
	 * @param series - серия
	 * @param positiveColor - цвет столбца для положительных значений
	 */
	public BarChartItem(double value, String category, String series, Color positiveColor)
	{
		this.value = value;
		this.category = category;
		this.series = series;
		this.positiveColor = positiveColor;
	}

	/**
	 * @param value - значение
	 * @param category - категория
	 * @param series - серия
	 * @param positiveColor - цвет столбца для положительных значений
	 * @param negativeColor - цвет столбца для отрицательных значений
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
	 * @return значение
	 */
	public double getValue()
	{
		return value;
	}

	/**
	 * @param value - значение
	 */
	public void setValue(double value)
	{
		this.value = value;
	}

	/**
	 * @return категория
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * @param category - категория
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}

	/**
	 * @return серия
	 */
	public String getSeries()
	{
		return series;
	}

	/**
	 * @param series - серия
	 */
	public void setSeries(String series)
	{
		this.series = series;
	}

	/**
	 * @return цвет столбца для положительных значений
	 */
	public Color getPositiveColor()
	{
		return positiveColor;
	}

	/**
	 * @return цвет столбца для отрицательных значений
	 */
	public Color getNegativeColor()
	{
		return negativeColor;
	}
}
