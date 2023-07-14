package com.rssl.phizic.utils.chart;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleEdge;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author akrenev
 * @ created 01.10.13
 * @ $Author$
 * @ $Revision$
 *
 * рендер столбчатой диаграммы с картинками
 */

public class ImageBarRenderer extends BarRenderer3D
{
	private BufferedImage image;

	/**
	 * собрать рендер по урлу
	 * @param url урл
	 */
	public ImageBarRenderer(String url) throws ChartException
	{
		try
		{
			image = ImageIO.read(com.itextpdf.text.Utilities.toURL(url));
		}
		catch (IOException e)
		{
			throw new ChartException(e);
		}
	}

	/**
	 * собрать рендер по картинке
	 * @param bytes картинка
	 */
	public ImageBarRenderer(byte[] bytes) throws ChartException
	{
		try
		{
			image = ImageIO.read(new ByteArrayInputStream(bytes));
		}
		catch (IOException e)
		{
			throw new ChartException(e);
		}
	}

	@Override
	public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass)
	{
		Number dataValue = dataset.getValue(row, column);
		if (dataValue == null)
			return;

		double value = dataValue.doubleValue();
		Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX(), dataArea.getY() + getYOffset(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
		PlotOrientation orientation = plot.getOrientation();

		double barW0 = calculateBarW0(plot, orientation, adjusted, domainAxis, state, row, column);
		double[] barL0L1 = calculateBarL0L1(value);
		if (barL0L1 == null)
			return;

		RectangleEdge edge = plot.getRangeAxisEdge();
		double transL0 = rangeAxis.valueToJava2D(barL0L1[0], adjusted, edge);
		double transL1 = rangeAxis.valueToJava2D(barL0L1[1], adjusted, edge);
		double barL0 = Math.min(transL0, transL1);
		double barLength = Math.abs(transL1 - transL0);

		Rectangle2D bar = null;
		if (orientation == PlotOrientation.HORIZONTAL)
			bar = new Rectangle2D.Double(barL0, barW0, barLength, state.getBarWidth());
		else
			bar = new Rectangle2D.Double(barW0, barL0, state.getBarWidth(), barLength);

		if (image == null)
			return;

		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		double minX = bar.getMinX();
		double maxX = bar.getMaxX();
		double minY = bar.getMinY();

		int x = (int) Math.round(minX + (maxX - minX - imageWidth) / 2);
		int y = (int) Math.round(minY) - imageHeight / 2;

		g2.drawImage(image, x, y, imageWidth, imageHeight, null);
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
