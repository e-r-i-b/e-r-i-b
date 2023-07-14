package com.rssl.phizic.utils.pdf.event;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

import java.awt.*;

/**
 * User: Moshenko
 * Date: 21.10.14
 * Time: 1:35
 * Добавляет линию подчеркивания под ячейкой  с отступами.
 */
public class CellSpacingEvent implements PdfPCellEvent
{
	private int cellSpacing;
	private Color color;
	private float width;
	private int pos;
	private int lineUp;

	/**
	 *
	 * @param cellSpacing кол-во разрывов
	 * @param color цвет линии
	 * @param width ширина линии
	 * @param pos с какой стороны добавляем разрывы (отрицательное число: только с лева, 0: слева и с права, положительно число: только с права)
	 * @param lineUp подьем/опуск линии
	 */
	public CellSpacingEvent(int cellSpacing, Color color,float width, int pos, int lineUp)
	{
		this.cellSpacing = cellSpacing;
		this.color = color;
		this.width = width;
		this.pos = pos;
		this.lineUp = lineUp;
	}

	public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases)
	{
		PdfContentByte cb = canvases[PdfPTable.BACKGROUNDCANVAS];

		cb.setColorStroke(new BaseColor(color));
		cb.setLineWidth(width);
		if (pos < 0)
		{
			cb.moveTo(position.getLeft() + this.cellSpacing,position.getBottom() + lineUp);
			cb.lineTo(position.getRight(), position.getBottom() + lineUp);
		}
		else if (pos == 0)
		{
			cb.moveTo(position.getLeft() + this.cellSpacing,position.getBottom() + lineUp);
			cb.lineTo(position.getRight() - this.cellSpacing, position.getBottom() + lineUp);
		}
		else if (pos > 0)
		{
			cb.moveTo(position.getLeft(),position.getBottom() + lineUp);
			cb.lineTo(position.getRight() - this.cellSpacing, position.getBottom() + lineUp);
		}
		cb.stroke();
	}
}
