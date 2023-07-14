package com.rssl.phizic.utils.pdf;

import java.awt.Color;
import java.awt.Paint;
import javax.swing.ImageIcon;

/**
 * Стиль графика
 * @author lepihina
 * @ created 20.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ChartStyle
{
	private Paint backgroundColor = Color.white;
	private ImageIcon backgroundImage;
	private Paint plotBackgroundColor = Color.white;  // цвет фона графика
	private boolean domainGridlinesVisible = false;
	private Color domainGridlineColor = Color.black; // цвет сетки (ось X)
	private boolean rangeGridlinesVisible = false;
	private Color rangeGridlineColor = Color.black; // цвет сетки (ось Y)
	private float foregroundAlpha = 1; // прозрачность графика

	/**
	 * @return цвет фона всей диаграммы
	 */
	public Paint getBackgroundColor()
	{
		return backgroundColor;
	}

	/**
	 * @param backgroundColor - цвет фона всей диаграммы
	 */
	public void setBackgroundColor(Paint backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return картинка для фона диаграммы
	 */
	public ImageIcon getBackgroundImage()
	{
		return backgroundImage;
	}

	/**
	 * @param backgroundImagePath - путь до картинк для фона диаграммы
	 */
	public void setBackgroundImagePath(String backgroundImagePath)
	{
		this.backgroundImage = new ImageIcon(backgroundImagePath);
	}

	public Paint getPlotBackgroundColor()
	{
		return plotBackgroundColor;
	}

	public void setPlotBackgroundColor(Paint plotBackgroundColor)
	{
		this.plotBackgroundColor = plotBackgroundColor;
	}

	public boolean isRangeGridlinesVisible()
	{
		return rangeGridlinesVisible;
	}

	public void setRangeGridlinesVisible(boolean rangeGridlinesVisible)
	{
		this.rangeGridlinesVisible = rangeGridlinesVisible;
	}

	public Color getRangeGridlineColor()
	{
		return rangeGridlineColor;
	}

	public void setRangeGridlineColor(Color rangeGridlineColor)
	{
		this.rangeGridlineColor = rangeGridlineColor;
	}

	public boolean isDomainGridlinesVisible()
	{
		return domainGridlinesVisible;
	}

	public void setDomainGridlinesVisible(boolean domainGridlinesVisible)
	{
		this.domainGridlinesVisible = domainGridlinesVisible;
	}

	public Color getDomainGridlineColor()
	{
		return domainGridlineColor;
	}

	public void setDomainGridlineColor(Color domainGridlineColor)
	{
		this.domainGridlineColor = domainGridlineColor;
	}

	public float getForegroundAlpha()
	{
		return foregroundAlpha;
	}

	public void setForegroundAlpha(float foregroundAlpha)
	{
		this.foregroundAlpha = foregroundAlpha;
	}
}
