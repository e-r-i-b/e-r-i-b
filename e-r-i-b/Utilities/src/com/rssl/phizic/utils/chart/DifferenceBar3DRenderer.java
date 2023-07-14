package com.rssl.phizic.utils.chart;

import org.jfree.chart.renderer.category.BarRenderer3D;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author lepihina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DifferenceBar3DRenderer extends BarRenderer3D
{
	private java.util.List<SeriesColor> seriesColors;

	/**
	 * @param seriesCount - количество серий
	 */
	public DifferenceBar3DRenderer(int seriesCount)
    {
	    this.seriesColors = new ArrayList<SeriesColor>(seriesCount);
    }

	/**
	 * Добавляет цвета для серии
	 * @param seriesNum - номер серии
	 * @param seriesColor - цвета для серии
	 */
	public void addSeriesColor(int seriesNum, SeriesColor seriesColor)
	{
		seriesColors.add(seriesNum, seriesColor);
	}

    public Paint getItemPaint(int row, int column)
    {
	    SeriesColor seriesColor = seriesColors.get(row);
	    if (seriesColor == null)
	    {
		    return super.getItemPaint(row, column);
	    }

	    double value = getPlot().getDataset().getValue(row, column).doubleValue();
        if (value > 0 )
        {
            return seriesColor.getPositiveColor();
        }
	    else
        {
	        if (seriesColor.getNegativeColor() != null)
	        {
	            return seriesColor.getNegativeColor();
	        }
	        else
	        {
		        return seriesColor.getPositiveColor();
	        }
        }
    }
}
