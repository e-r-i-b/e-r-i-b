package com.rssl.phizic.utils.pdf.event;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

import java.awt.*;

/**
 * User: Moshenko
 * Date: 22.10.14
 * Time: 2:44
 * Добавляе линию под ячейкой задоного цвета, толщины, и уровня.
 */
public class AddSubLineEvent implements PdfPCellEvent
{
	private Color color;
	private float width;
	private int lineUp;

	/**
	 *
	 * @param color цвет линии
	 * @param width ширина линии
	 * @param lineUp подьем/опуск линии
	 */
	public AddSubLineEvent(Color color,float width, int lineUp)
	{
		this.color = color;
		this.width = width;
		this.lineUp = lineUp;
	}

	public void cellLayout(PdfPCell cell, com.itextpdf.text.Rectangle position, PdfContentByte[] canvases)
	{
		PdfContentByte cb = canvases[PdfPTable.BACKGROUNDCANVAS];

		cb.setColorStroke(new BaseColor(color));
		cb.setLineWidth(width);
		cb.moveTo(position.getLeft(),position.getBottom() + lineUp);
		cb.lineTo(position.getRight(), position.getBottom() + lineUp);
		cb.stroke();
	}
}
