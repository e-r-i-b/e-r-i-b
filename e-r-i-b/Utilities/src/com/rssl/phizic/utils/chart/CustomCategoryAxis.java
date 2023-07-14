package com.rssl.phizic.utils.chart;

import com.rssl.phizic.utils.StringHelper;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.text.TextUtilities;
import org.jfree.text.TextBlock;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Iterator;

/**
 * @author mihaylov
 * @ created 02.10.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ось для отображения категорий.
 * Отличается от оси из библиотеки JFreeChart, тем что можно задать положение легенды к оси относительно самой оси.
 * В библиотеке, если ось находится снизу, то легенда к оси отображается под осью, если ось слева, то легенда слева от оси.
 * ВАЖНО!!! Пока реализовано только возможность указать, что для оси отображаемой снизу от графика, ось должна находиться слева.
 * ВАЖНО!!! Во всех остальных случаях работает аналогично оси из библиотеки.
 * ВАЖНО!!! При необходимости доработать свой вариант.
 */
public class CustomCategoryAxis extends CategoryAxis
{

	private RectangleEdge axisLabelEdge; // положение легенды к оси, относительно оси

	public RectangleEdge getAxisLabelEdge()
	{
		return axisLabelEdge;
	}

	public void setAxisLabelEdge(RectangleEdge axisLabelEdge)
	{
		this.axisLabelEdge = axisLabelEdge;
	}

	/**
	 * Метод рисования оси
	 * @param g2
	 * @param cursor
	 * @param plotArea
	 * @param dataArea
	 * @param edge
	 * @param plotState
	 * @return
	 */
	public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState)
	{
        if (!isVisible())
        {
            return new AxisState(cursor);
        }

        if (isAxisLineVisible())
        {
            drawAxisLine(g2, cursor, dataArea, edge);
        }
        AxisState state = new AxisState(cursor);
        if (isTickMarksVisible())
        {
            drawTickMarks(g2, cursor, dataArea, edge, state);
        }

        createAndAddEntity(cursor, state, dataArea, edge, plotState);

        // draw the category labels and axis label
        state = drawCategoryLabels(g2, plotArea, dataArea, edge, state,
                plotState);
        state = drawLabel(getLabel(), g2, plotArea, dataArea, edge, state);
        return state;
	}

	/**
	 * Метод рисования легенды к оси.
	 * @param label
	 * @param g2
	 * @param plotArea
	 * @param dataArea
	 * @param axisEdge
	 * @param state
	 * @return
	 */
	protected AxisState drawLabel(String label, Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge axisEdge, AxisState state)
	{
		// it is unlikely that 'state' will be null, but check anyway...
		if (state == null)
		{
		    throw new IllegalArgumentException("Null 'state' argument.");
		}

		if (StringHelper.isEmpty(label))
		{
		    return state;
		}


		Font font = getLabelFont();
		RectangleInsets insets = getLabelInsets();
		g2.setFont(font);
		g2.setPaint(getLabelPaint());
		FontMetrics fm = g2.getFontMetrics();
		Rectangle2D labelBounds = TextUtilities.getTextBounds(label, g2, fm);

		if (axisEdge == RectangleEdge.TOP)
		{
		    AffineTransform t = AffineTransform.getRotateInstance(
		            getLabelAngle(), labelBounds.getCenterX(),
		            labelBounds.getCenterY());
		    Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
		    labelBounds = rotatedLabelBounds.getBounds2D();
		    double labelx = dataArea.getCenterX();
		    double labely = state.getCursor() - insets.getBottom()
		                    - labelBounds.getHeight() / 2.0;
		    TextUtilities.drawRotatedString(label, g2, (float) labelx,
		            (float) labely, TextAnchor.CENTER, getLabelAngle(),
		            TextAnchor.CENTER);
		    state.cursorUp(insets.getTop() + labelBounds.getHeight()
		            + insets.getBottom());
		}
		else if (axisEdge == RectangleEdge.BOTTOM)
		{
		    AffineTransform t = AffineTransform.getRotateInstance(
		            getLabelAngle(), labelBounds.getCenterX(),
		            labelBounds.getCenterY());
		    Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
		    labelBounds = rotatedLabelBounds.getBounds2D();
			double labelx;
			double labely;
			if(axisLabelEdge == RectangleEdge.LEFT)
			{
				labelx = dataArea.getMinX() - labelBounds.getWidth() / 2.0;
				labely = state.getCursor() - insets.getTop() - labelBounds.getHeight() / 2.0;
				TextUtilities.drawRotatedString(label, g2, (float) labelx,
		            (float) labely, TextAnchor.CENTER, getLabelAngle(),
		            TextAnchor.CENTER);
			}
			else
			{
				labelx = dataArea.getCenterX();
		        labely = state.getCursor()
		                    + insets.getTop() + labelBounds.getHeight() / 2.0;
				TextUtilities.drawRotatedString(label, g2, (float) labelx,
		            (float) labely, TextAnchor.CENTER, getLabelAngle(),
		            TextAnchor.CENTER);
				state.cursorDown(insets.getTop() + labelBounds.getHeight()
		            + insets.getBottom());
			}		    
		}
		else if (axisEdge == RectangleEdge.LEFT)
		{
		    AffineTransform t = AffineTransform.getRotateInstance(
                    getLabelAngle() - Math.PI / 2.0, labelBounds.getCenterX(),
                    labelBounds.getCenterY());
            Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
            labelBounds = rotatedLabelBounds.getBounds2D();
            double labelx = state.getCursor()
                            - insets.getRight() - labelBounds.getWidth() / 2.0;
            double labely = dataArea.getCenterY();
            TextUtilities.drawRotatedString(label, g2, (float) labelx,
                    (float) labely, TextAnchor.CENTER,
                    getLabelAngle() - Math.PI / 2.0, TextAnchor.CENTER);
            state.cursorLeft(insets.getLeft() + labelBounds.getWidth()
                    + insets.getRight());
		}
		else if (axisEdge == RectangleEdge.RIGHT) {

		    AffineTransform t = AffineTransform.getRotateInstance(
		            getLabelAngle() + Math.PI / 2.0,
		            labelBounds.getCenterX(), labelBounds.getCenterY());
		    Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
		    labelBounds = rotatedLabelBounds.getBounds2D();
		    double labelx = state.getCursor()
		                    + insets.getLeft() + labelBounds.getWidth() / 2.0;
		    double labely = dataArea.getY() + dataArea.getHeight() / 2.0;
		    TextUtilities.drawRotatedString(label, g2, (float) labelx,
		            (float) labely, TextAnchor.CENTER,
		            getLabelAngle() + Math.PI / 2.0, TextAnchor.CENTER);
		    state.cursorRight(insets.getLeft() + labelBounds.getWidth()
		            + insets.getRight());

		}

		return state;
	}

	/**
	 * Метод возвращает подписи по оси. Отличие от метода из библиотеки в том, что в случае если у нас всего одна подпись к оси,
	 * то данный метод вернет столько подписей, сколько у главной оси графика.
	 * @param g2
	 * @param state
	 * @param dataArea
	 * @param edge
	 * @return
	 */
	public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge)
	{
		List ticks = new java.util.ArrayList();

        // sanity check for data area...
        if (dataArea.getHeight() <= 0.0 || dataArea.getWidth() < 0.0)
            return ticks;


        CategoryPlot plot = (CategoryPlot) getPlot();
		List mainCategories = plot.getCategories();
        List categories = plot.getCategoriesForAxis(this);
        double max = 0.0;

        if (categories != null)
        {
	        if(categories.size() < mainCategories.size())
	        {
		        for(int i = categories.size(); i < mainCategories.size(); i++)
			        categories.add(categories.get(categories.size()-1));
	        }
            CategoryLabelPosition position
                    = this.getCategoryLabelPositions().getLabelPosition(edge);
            float r = this.getMaximumCategoryLabelWidthRatio();
            if (r <= 0.0)
            {
                r = position.getWidthRatio();
            }

            float l = 0.0f;
            if (position.getWidthType() == CategoryLabelWidthType.CATEGORY)
            {
                l = (float) calculateCategorySize(categories.size(), dataArea, edge);
            }
            else
            {
                if (RectangleEdge.isLeftOrRight(edge))
                {
                    l = (float) dataArea.getWidth();
                }
                else
                {
                    l = (float) dataArea.getHeight();
                }
            }
            int categoryIndex = 0;
            Iterator iterator = categories.iterator();
            while (iterator.hasNext())
            {
                DublicateKeyedValue category = (DublicateKeyedValue) iterator.next();
                g2.setFont(getTickLabelFont(category.getLabel()));
                TextBlock label = createLabel(category.getLabel(), l * r, edge, g2);
                if (edge == RectangleEdge.TOP || edge == RectangleEdge.BOTTOM)
                    max = Math.max(max, calculateTextBlockHeight(label,position, g2));
                else if (edge == RectangleEdge.LEFT || edge == RectangleEdge.RIGHT)
                    max = Math.max(max, calculateTextBlockWidth(label, position, g2));
                Tick tick = new CategoryTick(category, label, position.getLabelAnchor(), position.getRotationAnchor(), position.getAngle());
                ticks.add(tick);
                categoryIndex = categoryIndex + 1;
            }
        }
        state.setMax(max);
        return ticks;
	}
}
