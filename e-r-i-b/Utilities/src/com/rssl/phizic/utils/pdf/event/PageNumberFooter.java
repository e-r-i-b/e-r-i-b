package com.rssl.phizic.utils.pdf.event;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author akrenev
 * @ created 06.12.2013
 * @ $Author$
 * @ $Revision$
 *
 * Добавляет номер страницы, начиная со startPageNumber
 */

public class PageNumberFooter extends SimpleTextFooterBase
{
	private final int startPageNumber;

	/**
	 * конструктор
	 * @param startPageNumber номер страницы, начиная с которого отображается футер
	 */
	public PageNumberFooter(int startPageNumber)
	{
		this.startPageNumber = startPageNumber;
	}

	private String getPageNumberFormat()
	{
		return "%d";
	}

	@Override
	protected int getAlignment()
	{
		return Element.ALIGN_CENTER;
	}

	@Override
	protected Phrase getFooter()
	{
		return new Phrase(String.format(getPageNumberFormat(), getPageNumber() - 1));
	}

	@Override
	protected float getXPosition(Rectangle rect)
	{
		return rect.getRight();
	}

	@Override
	protected float getYPosition(Rectangle rect)
	{
		return rect.getBottom() + 5;
	}

	@Override
	protected boolean needAddFooter(PdfWriter writer)
	{
		return getPageNumber() > startPageNumber;
	}
}
