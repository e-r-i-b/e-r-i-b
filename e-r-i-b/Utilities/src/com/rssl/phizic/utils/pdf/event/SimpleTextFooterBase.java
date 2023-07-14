package com.rssl.phizic.utils.pdf.event;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;
import com.rssl.phizic.utils.pdf.PDFBuilder;

/**
 * @author akrenev
 * @ created 06.12.2013
 * @ $Author$
 * @ $Revision$
 *
 * Ѕазовый класс футера, добавл€ющего текст в конец страницы
 */

public abstract class SimpleTextFooterBase extends HeaderFooterBase
{
	protected abstract int getAlignment();

	protected abstract Phrase getFooter();

	protected abstract float getXPosition(Rectangle rect);

	protected abstract float getYPosition(Rectangle rect);

	public void doEndPage(PdfWriter writer, Document document)
	{
		Rectangle rect = writer.getBoxSize(PDFBuilder.MAIN_BOX_NAME);
		ColumnText.showTextAligned(writer.getDirectContent(), getAlignment(), getFooter(), getXPosition(rect), getYPosition(rect), 0);
	}
}
