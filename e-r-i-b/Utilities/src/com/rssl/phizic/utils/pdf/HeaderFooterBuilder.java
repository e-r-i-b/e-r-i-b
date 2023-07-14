package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.rssl.phizic.utils.pdf.event.FooterForTitlePage;
import com.rssl.phizic.utils.pdf.event.HeaderFooterBase;
import com.rssl.phizic.utils.pdf.event.ImageHeader;
import com.rssl.phizic.utils.pdf.event.PageNumberFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 11.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * билдер хеадера
 */
public class HeaderFooterBuilder
{
	private List<HeaderFooterBase> headerFooters = new ArrayList<HeaderFooterBase>();

	private void addHeaderFooter(HeaderFooterBase event)
	{
		headerFooters.add(event);
	}

	/**
	 * в качестве хеадера - картинка
	 * @param url урл картинки
	 * @param width ширина картинки
	 * @param height высота картинки
	 * @param alignment выравнивание
	 * @throws PDFBuilderException
	 */
	public void addImageInstance(String url, int width, int height, Alignment alignment) throws PDFBuilderException
	{
		addHeaderFooter(new ImageHeader(url, width, height, alignment));
	}

	/**
	 * в качестве футера - номер страницы
	 * @throws PDFBuilderException
	 */
	public void addPageNumberInstance() throws PDFBuilderException
	{
		addHeaderFooter(new PageNumberFooter(1));
	}

	/**
	 * в качестве хеадера/футера - текст
	 * @param text текст
	 * @throws PDFBuilderException
	 */
	public void addOnlyTitleInstance(String text) throws PDFBuilderException
	{
		addHeaderFooter(new FooterForTitlePage(text));
	}

	/**
	 * в качестве хеадера/футера - текст
	 * @param text текст
	 * @param fontStyle стиль текста
	 * @throws PDFBuilderException
	 */
	public void addOnlyTitleInstance(String text, FontStyle fontStyle) throws PDFBuilderException
	{
		addHeaderFooter(new FooterForTitlePage(text, fontStyle));
	}

	/**
	 * @return Хеадер-футер
	 */
	public List<HeaderFooterBase> getHeaderFooters()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return headerFooters;
	}

	public void addCustomHeaderFooter(HeaderFooterBase headerFoorer)
	{
		addHeaderFooter(headerFoorer);
	}
}