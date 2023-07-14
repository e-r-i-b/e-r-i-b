package com.rssl.phizic.utils.chart;

/**
 * @author lepihina
 * @ created 14.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class StackedBarChartItem
{
	private double value; //количество продукта
	private Category category; // категория с настройками
	private String descr; // подпись 

	public StackedBarChartItem(Category category, double value, String descr)
	{
		this.value = value;
		this.category = category;
		this.descr = descr;
	}

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}

	public String getDescr()
	{
		return descr;
	}

	public void setDescr(String descr)
	{
		this.descr = descr;
	}
}
