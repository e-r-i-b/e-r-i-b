package com.rssl.phizic.utils.chart;

import java.awt.*;

/**
 * Цвета для серии (для положительных и отрицательных значений)
 * @author lepihina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */
public class SeriesColor
{
	private Color positiveColor;
    private Color negativeColor;

	/**
	 * @param positiveColor - цвет для положительных значений
	 * @param negativeColor - цвет для отрицательных значений
	 */
	public SeriesColor(Color positiveColor, Color negativeColor)
    {
	    this.positiveColor = positiveColor;
	    this.negativeColor = negativeColor;
    }

	/**
	 * @return цвет для положительных значений
	 */
	public Color getPositiveColor()
	{
		return positiveColor;
	}

	/**
	 * @return цвет для отрицательных значений
	 */
	public Color getNegativeColor()
	{
		return negativeColor;
	}
}
