package com.rssl.phizic.utils.chart;

import org.jfree.chart.axis.*;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.text.G2TextMeasurer;
import org.jfree.text.TextBlock;
import org.jfree.text.TextBlockAnchor;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

/**
 * Ось, на которой отображаются подписи для подкатегорий.
 * Отличается от оси из библиотеки JFreeChart, тем что подписи отображаются не в одну строку,
 * при необходимости добавляются переносы на новую строку.
 *
 * @author lepihina
 * @ created 26.08.13
 * @ $Author$
 * @ $Revision$
 */
public class MultiLineSubCategoryAxis extends SubCategoryAxis
{
	private java.util.List<Comparable> subCategories;

	/**
	 * Создает новую ось
	 * @param label - заголовок оси
	 */
	public MultiLineSubCategoryAxis(String label)
	{
		super(label);
        this.subCategories = new java.util.ArrayList<Comparable>();
	}

	public void addSubCategory(Comparable subCategory)
	{
        if (subCategory == null)
        {
            throw new IllegalArgumentException("Null 'subcategory' axis.");
        }
        subCategories.add(subCategory);
        notifyListeners(new AxisChangeEvent(this));
    }

	/**
	 * Резервирует место для подписей на оси.
	 * Отличается от библиотечного, тем что учитывает, что длинная подпись разбивается на несколько строк.
	 * @param g2  the graphics device (used to obtain font information).
     * @param plot  the plot that the axis belongs to.
     * @param plotArea  the area within which the axis should be drawn.
     * @param edge  the axis location (top or bottom).
     * @param space  the space already reserved.
	 * @return область необходимая для подписей на оси
	 */
	public AxisSpace reserveSpace(Graphics2D g2, Plot plot, Rectangle2D plotArea, RectangleEdge edge, AxisSpace space)
	{
        if (space == null)
        {
            space = new AxisSpace();
        }

        if (!isVisible())
        {
            return space;
        }

        double tickLabelHeight = 0.0;
        double tickLabelWidth = 0.0;
        if (isTickLabelsVisible())
        {
            g2.setFont(getTickLabelFont());
            AxisState state = new AxisState();
            refreshTicks(g2, state, plotArea, edge);
            if (edge == RectangleEdge.TOP)
            {
                tickLabelHeight = state.getMax();
            }
            else if (edge == RectangleEdge.BOTTOM)
            {
                tickLabelHeight = state.getMax();
            }
            else if (edge == RectangleEdge.LEFT)
            {
                tickLabelWidth = state.getMax();
            }
            else if (edge == RectangleEdge.RIGHT)
            {
                tickLabelWidth = state.getMax();
            }
        }

        Rectangle2D labelEnclosure = getLabelEnclosure(g2, edge);
        if (RectangleEdge.isTopOrBottom(edge))
        {
	        double labelHeight = labelEnclosure.getHeight();
            space.add(labelHeight + tickLabelHeight + getCategoryLabelPositionOffset(), edge);
        }
        else if (RectangleEdge.isLeftOrRight(edge))
        {
	        double labelWidth = labelEnclosure.getWidth();
            space.add(labelWidth + tickLabelWidth + getCategoryLabelPositionOffset(), edge);
        }

		CategoryLabelPosition position = getCategoryLabelPositions().getLabelPosition(edge);
        float r = getMaximumCategoryLabelWidthRatio();
        if (r <= 0.0)
        {
            r = position.getWidthRatio();
        }

        float l = 0.0f;
		CategoryPlot categoryPlot = (CategoryPlot) getPlot();
        if (position.getWidthType() == CategoryLabelWidthType.CATEGORY)
        {
            l = (float) calculateCategorySize(categoryPlot.getCategoriesForAxis(this).size(), plotArea, edge);
        }
        else
        {
            if (RectangleEdge.isLeftOrRight(edge))
            {
                l = (float) plotArea.getWidth();
            }
            else
            {
                l = (float) (plotArea.getHeight() );
            }
        }

        double maxdim = getMaxDim(g2, edge, l*r, subCategories.size());
        if (RectangleEdge.isTopOrBottom(edge)) {
            space.add(maxdim, edge);
        }
        else if (RectangleEdge.isLeftOrRight(edge)) {
            space.add(maxdim, edge);
        }
        return space;
    }

	/**
	 * Расчитывает масимальный размер для подписи.
	 * Отличается от библиотечного, тем что учитывает, что длинная подпись разбивается на несколько строк.
	 * @param g2 - the graphics device
	 * @param edge - the edge
	 * @param fullValue - полный размер отведенный для подписей
	 * @param count - количество подкатегорий
	 * @return масимальный размер для подписи
	 */
    private double getMaxDim(Graphics2D g2, RectangleEdge edge, float fullValue, int count)
    {
        double result = 0.0;
        g2.setFont(getSubLabelFont());
        Iterator iterator = subCategories.iterator();
        while (iterator.hasNext())
        {
	        Comparable subcategory = (Comparable) iterator.next();
            String label = subcategory.toString();

            double dim = 0.0;
	        TextBlock textBlock = TextUtilities.createTextBlock(label, getSubLabelFont(), getSubLabelPaint(), fullValue/count, new G2TextMeasurer(g2));
            if (RectangleEdge.isLeftOrRight(edge))
            {
	            dim = textBlock.calculateDimensions(g2).getWidth();
            }
            else
            {
	            dim = textBlock.calculateDimensions(g2).getHeight();
            }
            result = Math.max(result, dim);
        }
        return result;
    }

	/**
	 * Отрисовывает подписи для подкатегорий.
	 * Отличается от библиотечного, тем что учитывает, что длинная подпись разбивается на несколько строк.
	 * @param g2  the graphics device.
	 * @param plotArea  the plot area.
	 * @param dataArea  the area inside the axes.
	 * @param edge  the axis location.
	 * @param state  the axis state.
	 * @param plotState  collects information about the plot.
	 * @return обновленное состояние оси
	 */
    protected AxisState drawSubCategoryLabels(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, AxisState state, PlotRenderingInfo plotState)
    {
        if (state == null)
        {
            throw new IllegalArgumentException("Null 'state' argument.");
        }

        g2.setFont(getSubLabelFont());
        g2.setPaint(getSubLabelPaint());
        CategoryPlot plot = (CategoryPlot) getPlot();
        int categoryCount = 0;
        CategoryDataset dataset = plot.getDataset();
        if (dataset != null)
        {
            categoryCount = dataset.getColumnCount();
        }

	    int subCategoryCount = subCategories.size();
	    double maxdim = 0.0;
        for (int categoryIndex = 0; categoryIndex < categoryCount; categoryIndex++)
        {
            double x0 = 0.0;
            double x1 = 0.0;
            double y0 = 0.0;
            double y1 = 0.0;
            if (edge == RectangleEdge.TOP)
            {
                x0 = state.getCursor();
                x1 = getCategoryEnd(categoryIndex, categoryCount, dataArea, edge);
                y1 = state.getCursor();
	            maxdim = Math.max(maxdim, getMaxDim(g2, edge, (float)(x1-x0), subCategoryCount));
                y0 = y1 - maxdim;
            }
            else if (edge == RectangleEdge.BOTTOM)
            {
                x0 = getCategoryStart(categoryIndex, categoryCount, dataArea, edge);
                x1 = getCategoryEnd(categoryIndex, categoryCount, dataArea, edge);
                y0 = state.getCursor();
	            maxdim = Math.max(maxdim, getMaxDim(g2, edge, (float)(x1-x0), subCategoryCount));
                y1 = y0 + maxdim;
            }
            else if (edge == RectangleEdge.LEFT)
            {
                y0 = getCategoryStart(categoryIndex, categoryCount, dataArea, edge);
                y1 = getCategoryEnd(categoryIndex, categoryCount, dataArea, edge);
                x1 = state.getCursor();
	            maxdim = Math.max(maxdim, getMaxDim(g2, edge, 0, subCategoryCount));
                x0 = x1 - maxdim;
            }
            else if (edge == RectangleEdge.RIGHT)
            {
                y0 = getCategoryStart(categoryIndex, categoryCount, dataArea, edge);
                y1 = getCategoryEnd(categoryIndex, categoryCount, dataArea, edge);
                x0 = state.getCursor();
	            maxdim = Math.max(maxdim, getMaxDim(g2, edge, 0, subCategoryCount));
                x1 = x0 + getMaxDim(g2, edge, 0, subCategoryCount);
            }

	        float width = (float) calculateSubCategoryWidth(plot, dataArea, categoryIndex);
	        float delta = (float) (x1 - x0 - width*subCategoryCount)/(subCategoryCount-1);
            float height = (float) (y1 - y0);
            float xx = 0.0f;
            float yy = 0.0f;
            for (int i = 0; i < subCategoryCount; i++)
            {
	            Rectangle2D area = new Rectangle2D.Double(x0 + i * width + i*delta, y0, width, y1 - y0);
	            Point2D anchorPoint = RectangleAnchor.coordinates(area, RectangleAnchor.TOP);
	            if (RectangleEdge.isTopOrBottom(edge))
                {
	                xx = (float) anchorPoint.getX();
                    yy = (float) area.getCenterY();
                }
                else
                {
                    xx = (float) area.getCenterX();
                    yy = (float) (y0 + (i + 0.5) * height);
                }
                String label = this.subCategories.get(i).toString();

	            TextBlock textBlock = TextUtilities.createTextBlock(label, getSubLabelFont(), getSubLabelPaint(), width, new G2TextMeasurer(g2));
	            textBlock.draw(g2, xx, yy, TextBlockAnchor.TOP_CENTER);
            }
        }

        if (edge.equals(RectangleEdge.TOP))
        {
            double h = maxdim;
            state.cursorUp(h);
        }
        else if (edge.equals(RectangleEdge.BOTTOM))
        {
            double h = maxdim;
            state.cursorDown(h);
        }
        else if (edge == RectangleEdge.LEFT)
        {
            double w = maxdim;
            state.cursorLeft(w);
        }
        else if (edge == RectangleEdge.RIGHT)
        {
            double w = maxdim;
            state.cursorRight(w);
        }
        return state;
    }

	/**
	 * Расчитывает ширину области для подписи у подкатегории
	 * @param plot - график
	 * @param dataArea - область с данными
	 * @param rendererIndex - номер серии
	 * @return ширина области для подписи у подкатегории
	 */
	private double calculateSubCategoryWidth(CategoryPlot plot, Rectangle2D dataArea, int rendererIndex)
	{
        CategoryAxis domainAxis = plot.getDomainAxis();
        CategoryDataset dataset = plot.getDataset(rendererIndex);
		BarRenderer renderer = (BarRenderer)plot.getRenderer();
        if (dataset != null)
        {
            int columns = dataset.getColumnCount();
            int rows = subCategories.size();
            double space = 0.0;
            PlotOrientation orientation = plot.getOrientation();
            if (orientation == PlotOrientation.HORIZONTAL)
            {
                space = dataArea.getHeight();
            }
            else if (orientation == PlotOrientation.VERTICAL)
            {
                space = dataArea.getWidth();
            }
	        double maxWidth = space * renderer.getMaximumBarWidth();
	        double categoryMargin = 0.0;
            if (columns > 1)
            {
                categoryMargin = domainAxis.getCategoryMargin();
            }
	        double currentItemMargin = 0.0;
            if (rows > 1)
            {
                currentItemMargin = renderer.getItemMargin();
            }
            double used = space * (1 - domainAxis.getLowerMargin() - domainAxis.getUpperMargin() - categoryMargin - currentItemMargin);
            if ((rows * columns) > 0)
            {
                return Math.min(used / (rows * columns), maxWidth);
            }
            else
            {
	            return Math.min(used, maxWidth);
            }
        }
		return 0;
    }
}
