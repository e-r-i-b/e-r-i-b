package com.rssl.phizic.utils.pdf.event;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.rssl.phizic.utils.pdf.FontStyle;

/**
 * @author akrenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * Базовый обработчик событий для добавления хеадера и футера на страницу
 */

@SuppressWarnings("AbstractClassExtendsConcreteClass")
public abstract class HeaderFooterBase  extends PdfPageEventHelper
{
	private Font font;
	private FontStyle fontStyle;
	private int pageNumber = 0;

	/**
	 * задать шрифт для обработчика событий
	 * @param font шрифт
	 */
	public void setFont(Font font)
	{
		this.font = font;
	}

	protected Font getFontWithStyle()
	{
		Font fontWithStyle = new Font(font);
		if (fontStyle != null)
		{
			fontWithStyle.setStyle(fontStyle.getStyle());
			fontWithStyle.setColor(fontStyle.getColor());
			fontWithStyle.setSize(fontStyle.getSize());
		}

		return fontWithStyle;
	}

	protected void setFontStyle(FontStyle fontStyle)
	{
		this.fontStyle = fontStyle;
	}

	protected int getPageNumber()
	{
		return pageNumber;
	}

	public void onStartPage(PdfWriter writer, Document document)
	{
		pageNumber++;
	}

	protected abstract boolean needAddFooter(PdfWriter writer);

	protected abstract void doEndPage(PdfWriter writer, Document document);

	@Override
	public final void onEndPage(PdfWriter writer, Document document)
	{
		if (needAddFooter(writer))
			doEndPage(writer, document);
	}
}
