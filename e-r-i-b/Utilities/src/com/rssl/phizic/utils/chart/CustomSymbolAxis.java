package com.rssl.phizic.utils.chart;

import org.jfree.chart.axis.*;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.data.Range;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;

/**
 * Ось со строковыми подписями (подписи передаются в массиве)
 * Отличие от SymbolAxis - можно задавать отступы (справа, слева), можно задать угол наклона подписей на оси.
 *
 * @author lepihina
 * @ created 07.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class CustomSymbolAxis extends SymbolAxis
{
	private double tickLabelAngle = 0.0; // угол наклона подписей на оси
	private float tickLabelPadding = 2f; // отступ подписей от оси

	public CustomSymbolAxis(String s, String[] strings)
	{
		super(s, strings);
	}

	public double getTickLabelAngle()
	{
		return tickLabelAngle;
	}

	public void setTickLabelAngle(double tickLabelAngle)
	{
		this.tickLabelAngle = tickLabelAngle;
	}

	public float getTickLabelPadding()
	{
		return tickLabelPadding;
	}

	public void setTickLabelPadding(float tickLabelPadding)
	{
		this.tickLabelPadding = tickLabelPadding;
	}

	protected void autoAdjustRange()
	{
		Plot plot = getPlot();
        if (plot == null)
        {
            return;  // no plot, no data
        }

		setRange(new Range(0, 0), false, false);

        if (plot instanceof ValueAxisPlot)
        {

            // ensure that all the symbols are displayed
            double upper = getSymbols().length - 1;
            double lower = 0;
            double range = upper - lower;

            // ensure the autorange is at least <minRange> in size...
            double minRange = getAutoRangeMinimumSize();
            if (range < minRange)
            {
                upper = (upper + lower + minRange) / 2;
                lower = (upper + lower - minRange) / 2;
            }

            // this ensure that the grid bands will be displayed correctly.
            double upperMargin = getUpperMargin();
            double lowerMargin = getLowerMargin();

            if (getAutoRangeIncludesZero())
            {
                if (getAutoRangeStickyZero())
                {
                    if (upper <= 0.0)
                        upper = 0.0;
                    else
                        upper = upper + upperMargin;

                    if (lower >= 0.0)
                        lower = 0.0;
                    else
                        lower = lower - lowerMargin;
                }
                else
                {
                    upper = Math.max(0.0, upper + upperMargin);
                    lower = Math.min(0.0, lower - lowerMargin);
                }
            }
            else
            {
                if (getAutoRangeStickyZero())
                {
                    if (upper <= 0.0)
                        upper = Math.min(0.0, upper + upperMargin);
                    else
                        upper = upper + upperMargin * range;

                    if (lower >= 0.0)
                        lower = Math.max(0.0, lower - lowerMargin);
                    else
                        lower = lower - lowerMargin;
                }
                else
                {
                    upper = upper + upperMargin;
                    lower = lower - lowerMargin;
                }
            }

            setRange(new Range(lower, upper), false, false);
        }
	}

	protected java.util.List<Tick> refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
	{
        java.util.List ticks = new java.util.ArrayList();

        Font tickLabelFont = getTickLabelFont();
        g2.setFont(tickLabelFont);

        double size = getTickUnit().getSize();
        int count = calculateVisibleTickCount();
        double lowestTickValue = calculateLowestVisibleTickValue();

        double previousDrawnTickLabelPos = 0.0;
        double previousDrawnTickLabelLength = 0.0;

        if (count <= ValueAxis.MAXIMUM_TICK_COUNT)
        {
            for (int i = 0; i < count; i++)
            {
                double currentTickValue = lowestTickValue + (i * size);
                double yy = valueToJava2D(currentTickValue, dataArea, edge);
                String tickLabel;
                NumberFormat formatter = getNumberFormatOverride();
                if (formatter != null)
                {
                    tickLabel = formatter.format(currentTickValue);
                }
                else
                {
                    tickLabel = valueToString(currentTickValue);
                }

                // avoid to draw overlapping tick labels
                Rectangle2D bounds = TextUtilities.getTextBounds(tickLabel, g2, g2.getFontMetrics());
                double tickLabelLength = isVerticalTickLabels() ? bounds.getWidth() : bounds.getHeight();
                boolean tickLabelsOverlapping = false;
                if (i > 0)
                {
                    double avgTickLabelLength = (previousDrawnTickLabelLength + tickLabelLength) / 2.0;
                    if (Math.abs(yy - previousDrawnTickLabelPos) < avgTickLabelLength)
                    {
                        tickLabelsOverlapping = true;
                    }
                }
                if (tickLabelsOverlapping)
                {
                    tickLabel = ""; // don't draw this tick label
                }
                else
                {
                    // remember these values for next comparison
                    previousDrawnTickLabelPos = yy;
                    previousDrawnTickLabelLength = tickLabelLength;
                }

                TextAnchor anchor = null;
                TextAnchor rotationAnchor = null;
                double angle = getTickLabelAngle();
                if (isVerticalTickLabels())
                {
                    anchor = TextAnchor.BOTTOM_CENTER;
                    rotationAnchor = TextAnchor.BOTTOM_CENTER;
                    if (edge == RectangleEdge.LEFT)
                    {
                        angle = -Math.PI / 2.0;
                    }
                    else
                    {
                        angle = Math.PI / 2.0;
                    }
                }
                else
                {
                    if (edge == RectangleEdge.LEFT)
                    {
                        anchor = TextAnchor.CENTER_RIGHT;
                        rotationAnchor = TextAnchor.CENTER_RIGHT;
                    }
                    else
                    {
                        anchor = TextAnchor.CENTER_LEFT;
                        rotationAnchor = TextAnchor.CENTER_LEFT;
                    }
                }
                Tick tick = new NumberTick(new Double(currentTickValue), tickLabel, anchor, rotationAnchor, angle);
                ticks.add(tick);
            }
        }
        return ticks;
    }

	protected java.util.List<Tick> refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
	{
        java.util.List ticks = new java.util.ArrayList();

        Font tickLabelFont = getTickLabelFont();
        g2.setFont(tickLabelFont);

        double size = getTickUnit().getSize();
        int count = calculateVisibleTickCount();
        double lowestTickValue = calculateLowestVisibleTickValue();

        double previousDrawnTickLabelPos = 0.0;
        double previousDrawnTickLabelLength = 0.0;

		boolean isAngledTickLabels = getTickLabelAngle() != 0;

        if (count <= ValueAxis.MAXIMUM_TICK_COUNT)
        {
            for (int i = 0; i < count; i++)
            {
                double currentTickValue = lowestTickValue + (i * size);
                double xx = valueToJava2D(currentTickValue, dataArea, edge);
                String tickLabel;
                NumberFormat formatter = getNumberFormatOverride();
                if (formatter != null)
                {
                    tickLabel = formatter.format(currentTickValue);
                }
                else
                {
                    tickLabel = valueToString(currentTickValue);
                }

                // avoid to draw overlapping tick labels
                Rectangle2D bounds = TextUtilities.getTextBounds(tickLabel, g2, g2.getFontMetrics());
                double tickLabelLength = isVerticalTickLabels() || isAngledTickLabels ? bounds.getHeight() : bounds.getWidth();
                boolean tickLabelsOverlapping = false;
                if (i > 0)
                {
                    double avgTickLabelLength = (previousDrawnTickLabelLength + tickLabelLength) / 2.0;
                    if (Math.abs(xx - previousDrawnTickLabelPos) < avgTickLabelLength) 
                    {
                        tickLabelsOverlapping = true;
                    }
                }
                if (tickLabelsOverlapping)
                {
                    tickLabel = ""; // don't draw this tick label
                }
                else
                {
                    // remember these values for next comparison
                    previousDrawnTickLabelPos = xx;
                    previousDrawnTickLabelLength = tickLabelLength;
                }

                TextAnchor anchor = TextAnchor.CENTER_RIGHT;
                TextAnchor rotationAnchor = TextAnchor.CENTER_RIGHT;
                double angle = getTickLabelAngle();
                if (isVerticalTickLabels())
                {
                    if (edge == RectangleEdge.TOP)
                    {
                        angle = Math.PI / 2.0;
                    }
                    else
                    {
                        angle = -Math.PI / 2.0;
                    }
                }
                else if (angle == 0)
                {
                    if (edge == RectangleEdge.TOP)
                    {
                        anchor = TextAnchor.BOTTOM_CENTER;
                        rotationAnchor = TextAnchor.BOTTOM_CENTER;
                    }
                    else
                    {
                        anchor = TextAnchor.TOP_CENTER;
                        rotationAnchor = TextAnchor.TOP_CENTER;
                    }
                }
                Tick tick = new NumberTick(new Double(currentTickValue), tickLabel, anchor, rotationAnchor, angle);
                ticks.add(tick);
            }
        }
        return ticks;
    }

	protected double findMaximumTickLabelHeight(java.util.List ticks, Graphics2D g2, Rectangle2D drawArea, boolean vertical)
	{
		RectangleInsets insets = getTickLabelInsets();
        Font font = getTickLabelFont();

		boolean isAngledTickLabels = getTickLabelAngle() != 0;
        double maxHeight = 0.0;
        if (vertical || isAngledTickLabels)
        {
            FontMetrics fm = g2.getFontMetrics(font);
            for (Object tick : ticks)
            {
	            Tick t = (Tick)tick;
                Rectangle2D labelBounds = TextUtilities.getTextBounds(t.getText(), g2, fm);
                if (labelBounds.getWidth() + insets.getTop() + insets.getBottom() > maxHeight)
                {
                    maxHeight = labelBounds.getWidth() + insets.getTop() + insets.getBottom();
                }
            }
        }
        else
        {
            LineMetrics metrics = font.getLineMetrics("ABCxyz", g2.getFontRenderContext());
            maxHeight = metrics.getHeight() + insets.getTop() + insets.getBottom();
        }

        return maxHeight;
     }

	protected float[] calculateAnchorPoint(ValueTick tick, double cursor, Rectangle2D dataArea, RectangleEdge edge)
	{
		RectangleInsets insets = getTickLabelInsets();
        float[] result = new float[2];
        if (edge == RectangleEdge.TOP) {
            result[0] = (float) valueToJava2D(tick.getValue(), dataArea, edge);
            result[1] = (float) (cursor - insets.getBottom() - getTickLabelPadding());
        }
        else if (edge == RectangleEdge.BOTTOM) {
            result[0] = (float) valueToJava2D(tick.getValue(), dataArea, edge);
            result[1] = (float) (cursor + insets.getTop() + getTickLabelPadding());
        }
        else if (edge == RectangleEdge.LEFT) {
            result[0] = (float) (cursor - insets.getLeft() - getTickLabelPadding());
            result[1] = (float) valueToJava2D(tick.getValue(), dataArea, edge);
        }
        else if (edge == RectangleEdge.RIGHT) {
            result[0] = (float) (cursor + insets.getRight() + getTickLabelPadding());    
            result[1] = (float) valueToJava2D(tick.getValue(), dataArea, edge);
        }
        return result;
	}
}
