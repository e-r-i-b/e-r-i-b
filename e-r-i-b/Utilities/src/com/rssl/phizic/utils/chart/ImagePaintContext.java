package com.rssl.phizic.utils.chart;

import sun.awt.image.IntegerInterleavedRaster;

import java.awt.*;
import java.awt.image.*;

/**
 * @author akrenev
 * @ created 17.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * PaintContext заточенный под загрузку картинок
 * (TexturePaintContext мостит картинкой)
 */

public class ImagePaintContext implements PaintContext
{
	private static final int[] BIT_MASKS = {16711680, 65280, 255, -16777216}; // для работы с IntegerInterleavedRaster нужна такая маска
	private BufferedImage bufImg;
	private ColorModel colorModel;

	/**
	 * @param bufImg картинка
	 * @param colorModel Цветовая модель
	 */
	public ImagePaintContext(BufferedImage bufImg, ColorModel colorModel)
	{
		this.colorModel = colorModel;
		this.bufImg = bufImg;
	}

	public void dispose()
	{}

	public ColorModel getColorModel()
	{       
		return colorModel;
	}

	public Raster getRaster(int x, int y, int w, int h)
	{
		int width = bufImg.getWidth();
		int height = bufImg.getHeight();
		SinglePixelPackedSampleModel model = new SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, width, height, width, BIT_MASKS);
		DataBufferInt bufferInt = new DataBufferInt(bufImg.getRGB(0, 0, width, height, null, 0, width), width * height);
		Point point = new Point(0, 0);
		return new IntegerInterleavedRaster(model, bufferInt, point);
	}
}
