package com.rssl.phizic.utils.pdf.event;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.rssl.phizic.utils.pdf.Alignment;
import com.rssl.phizic.utils.pdf.PDFBuilder;
import com.rssl.phizic.utils.pdf.PDFBuilderException;

import java.io.IOException;

/**
 * @author akrenev
 * @ created 02.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * хеадер + футер для страницы
 * хеадер: либо картинка, либо текст;
 * футер: номер страницы
 */
public class ImageHeader extends HeaderFooterBase
{
	private Image image;

	/**
	 * задать в качестве хеадера картинку
	 * @param url урл картинки
	 * @param width ширина картинки
	 * @param height высота картинки
	 * @param alignment выравнивание
	 * @throws com.rssl.phizic.utils.pdf.PDFBuilderException
	 */
	public ImageHeader(String url, int width, int height, Alignment alignment) throws PDFBuilderException
	{
		try
		{
			image = Image.getInstance(url);
			image.setAlignment(alignment.getCode());
			image.scaleAbsolute(width, height);// устанавливаем размер изображения
			image.setAbsolutePosition(0.0F, 0.0F);
		}
		catch (BadElementException e)
		{
			throw new PDFBuilderException(e);
		}
		catch (IOException e)
		{
			throw new PDFBuilderException(e);
		}
	}

	@Override
	protected boolean needAddFooter(PdfWriter writer)
	{
		return true;
	}

	public void doEndPage(PdfWriter writer, Document document)
	{
		float height = image.getScaledHeight();
		if (getPageNumber() == 1)
		{
			document.setMargins(document.leftMargin(), document.rightMargin(), document.topMargin() + height, document.bottomMargin());
			return;
		}
		try
		{
			Rectangle rect = writer.getBoxSize(PDFBuilder.MAIN_BOX_NAME);
			float width = image.getScaledWidth();
			writer.getDirectContent().addImage(image, width, 0, 0, height, rect.getRight() - width, rect.getTop() - height);
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}
}
