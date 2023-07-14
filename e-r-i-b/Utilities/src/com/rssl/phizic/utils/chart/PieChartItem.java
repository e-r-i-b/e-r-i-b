package com.rssl.phizic.utils.chart;

import java.awt.*;

/**
 * @author lepihina
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class PieChartItem
{
	private String name; //название продукта
	private double  value; //количество продукта
	private Color color; //цвет на диаграмме

	public PieChartItem(String name, double  value, Color color)
	{
		this.name = name;
		this.value = value;
		this.color = color;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double  getValue()
	{
		return value;
	}

	public void setValue(double  value)
	{
		this.value = value;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
}