package com.rssl.phizic.utils.pdf.event;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.awt.*;

/**
 * User: Moshenko
 * Date: 24.10.14
 * Time: 12:21
 * Добавляет закругление клетке справа
 * + четырехугольник, слева в высоту клетки
 */
public class AddCircleEvent implements PdfPCellEvent
{
	private Color color;
	private float radius;

	/**
	 *
	 * @param color цвет заливки
	 * @param radius радиус круга
	 */
	public AddCircleEvent(Color color, float radius)
	{
		this.color = color;
		this.radius = radius;
	}

	public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases)
	{
		PdfContentByte cb = canvases[PdfPTable.BACKGROUNDCANVAS];
		PdfGState state = new PdfGState();
		state.setFillOpacity(0);
		cb.setGState(state);
		cb.setColorStroke(new BaseColor(color));
		cb.setLineWidth(radius*2f);
		cb.circle(position.getRight(), (position.getTop() - position.getBottom()) / 2 +position.getBottom(), radius);
		cb.stroke();
		cb.rectangle(position.getLeft() - radius*7, position.getBottom() + radius, radius*7 , radius*2f);
		cb.stroke();


	}
}
