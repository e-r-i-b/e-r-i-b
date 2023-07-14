package com.rssl.phizic.utils.chart;

import java.awt.*;

/**
 * @author lepihina
 * @ created 04.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class AreaChartItem
{
	private double value1; //количество продукта
	private double value2; //количество продукта
	private String descr;
	private Category category1;   //категория 1
	private Category category2;   //категория 2

	public AreaChartItem(double value1, double  value2, String descr, Category category1, Category category2)
	{
		this.value1 = value1;
		this.value2 = value2;
		this.descr = descr;
		this.category1 = category1;
		this.category2 = category2;
	}

	public double getValue1()
	{
		return value1;
	}

	public void setValue1(double value1)
	{
		this.value1 = value1;
	}

	public double getValue2()
	{
		return value2;
	}

	public void setValue2(double value2)
	{
		this.value2 = value2;
	}

	public String getName1()
	{
		return category1.getId();
	}

	public void setName1(String name1)
	{
		this.category1.setId(name1);
	}

	public String getName2()
	{
		return category2.getId();
	}

	public void setName2(String name2)
	{
		this.category2.setId(name2);
	}

	public Color getColor1()
	{
		return category1.getChartColor();
	}

	public void setColor1(Color color1)
	{
		this.category1.setChartColor(color1);
	}

	public Color getColor2()
	{
		return category2.getChartColor();
	}

	public void setColor2(Color color2)
	{
		this.category2.setChartColor(color2);
	}

	public String getDescr()
	{
		return descr;
	}

	public void setDescr(String descr)
	{
		this.descr = descr;
	}

	public Category getCategory1()
	{
		return category1;
	}

	public Category getCategory2()
	{
		return category2;
	}
}
