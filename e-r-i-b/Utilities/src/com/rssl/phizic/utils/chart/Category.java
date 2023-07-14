package com.rssl.phizic.utils.chart;

import java.awt.*;

/**
 * Категория для столбчатой диаграммы
 * @author lepihina
 * @ created 20.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class Category
{
	private String id;
	private Color chartColor; // цвет категории на графике
	private Color labelColor; // цвет подписи
	private String labelText; // подпись
	private ImageLegendItem legendItem; // настраиваемая легенда с картинкой

	public Category(String id, Color chartColor)
	{
		this(id, chartColor, Color.black, "", null);
	}

	public Category(String id, Color chartColor, Color labelColor)
	{
		this(id, chartColor, labelColor, "", null);
	}

	public Category(String id, Color chartColor, String labelText)
	{
		this(id, chartColor, Color.black, labelText, null);
	}

	public Category(String id, Color chartColor, Color labelColor, String labelText)
	{
		this(id, chartColor, labelColor, labelText, null);
	}

	public Category(String id, Color chartColor, Color labelColor, String labelText, ImageLegendItem legendItem)
	{
		this.id = id;
		this.chartColor = chartColor;
		this.labelColor = labelColor;
		this.labelText = labelText;
		this.legendItem = legendItem;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Color getChartColor()
	{
		return chartColor;
	}

	public void setChartColor(Color chartColor)
	{
		this.chartColor = chartColor;
	}

	public Color getLabelColor()
	{
		return labelColor;
	}

	public void setLabelColor(Color labelColor)
	{
		this.labelColor = labelColor;
	}

	public String getLabelText()
	{
		return labelText;
	}

	public void setLabelText(String labelText)
	{
		this.labelText = labelText;
	}

	public ImageLegendItem getLegendItem()
	{
		return legendItem;
	}
}
