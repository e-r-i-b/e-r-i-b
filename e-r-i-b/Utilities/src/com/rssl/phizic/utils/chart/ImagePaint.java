package com.rssl.phizic.utils.chart;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 * @author akrenev
 * @ created 17.10.2012
 * @ $Author$
 * @ $Revision$
 *
 *  Paint ��� ������ � ���������
 */

public class ImagePaint extends TexturePaint
{
	private BufferedImage bufImg;

	/**
	 * @param txtr ������� ��� ��������
	 * @param anchor ��������
	 */
	public ImagePaint(BufferedImage txtr, Rectangle2D anchor)
	{
		super(txtr, anchor);
		this.bufImg = txtr;
	}

	@Override
	public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints)
	{
		return new ImagePaintContext(bufImg, cm);
	}
}
