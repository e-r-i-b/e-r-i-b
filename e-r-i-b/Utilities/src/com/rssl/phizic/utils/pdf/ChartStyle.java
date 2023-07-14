package com.rssl.phizic.utils.pdf;

import java.awt.Color;
import java.awt.Paint;
import javax.swing.ImageIcon;

/**
 * ����� �������
 * @author lepihina
 * @ created 20.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ChartStyle
{
	private Paint backgroundColor = Color.white;
	private ImageIcon backgroundImage;
	private Paint plotBackgroundColor = Color.white;  // ���� ���� �������
	private boolean domainGridlinesVisible = false;
	private Color domainGridlineColor = Color.black; // ���� ����� (��� X)
	private boolean rangeGridlinesVisible = false;
	private Color rangeGridlineColor = Color.black; // ���� ����� (��� Y)
	private float foregroundAlpha = 1; // ������������ �������

	/**
	 * @return ���� ���� ���� ���������
	 */
	public Paint getBackgroundColor()
	{
		return backgroundColor;
	}

	/**
	 * @param backgroundColor - ���� ���� ���� ���������
	 */
	public void setBackgroundColor(Paint backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return �������� ��� ���� ���������
	 */
	public ImageIcon getBackgroundImage()
	{
		return backgroundImage;
	}

	/**
	 * @param backgroundImagePath - ���� �� ������� ��� ���� ���������
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
