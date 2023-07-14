package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.events.PdfPageEventForwarder;
import com.rssl.phizic.utils.chart.DocumentOrientation;
import com.rssl.phizic.utils.pdf.event.HeaderFooterBase;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author akrenev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * Класс-оболочка для работы непосредственно с документом
 */
public class DocumentWriter
{
	public static final int NINETY_ROTATION = 90;

	private final DocumentOrientation orientation;
	private final String mainBoxName;

	private final Document document;
	private final PdfWriter writer;
    private final ByteArrayOutputStream outputStream;
	private final Font mainFont;
	private final Map<String,Font> addFonts;

	/**
	 * @param orientation ориентация документа
	 * @param builder   события навешиваемые на страницы
	 * @param mainFont основной шрифт
	 * @param addFonts дополнительные шрифты
	 * @param mainBoxName имя рабочей области
	 * @throws DocumentException
	 */
	public DocumentWriter(DocumentOrientation orientation, String mainBoxName, HeaderFooterBuilder builder, Font mainFont, Map<String,Font> addFonts) throws DocumentException
	{
		this.orientation = orientation;
		this.mainBoxName = mainBoxName;
		this.mainFont = mainFont;
		this.addFonts = addFonts;

		document = new Document(orientation.getPageSize());
		document.setMarginMirroringTopBottom(true);

		outputStream = new ByteArrayOutputStream();

	    writer = PdfWriter.getInstance(document, outputStream);
		writer.setBoxSize(mainBoxName, orientation.getBoxSize());
		PdfPageEventForwarder event = new PdfPageEventWhithUpdBackgroundForwarder();
		if (builder != null)
		{
			for (HeaderFooterBase pageEvent : builder.getHeaderFooters())
			{
				pageEvent.setFont(mainFont);
				event.addPageEvent(pageEvent);
			}
		}
		writer.setPageEvent(event);
	    writer.setRgbTransparencyBlending(true);

		document.open();
	}

	public DocumentWriter(DocumentOrientation orientation, String mainBoxName, HeaderFooterBuilder builder, Font mainFont) throws DocumentException
	{
	 	this(orientation,mainBoxName,builder,mainFont,null);
	}

	/**
	 * @return копия
	 * @throws DocumentException
	 */
	public DocumentWriter getCopy() throws DocumentException
	{
		return new DocumentWriter(orientation, mainBoxName, null, mainFont);
	}

	DocumentOrientation getOrientation()
	{
		return orientation;
	}

	/**
	 * задать ориентацию страницы
	 * @param orientation ориентация
	 */
	public void setOrientation(DocumentOrientation orientation)
	{
		Rectangle pageSize = new Rectangle(orientation.getPageSize());
		Rectangle boxSize = new Rectangle(orientation.getBoxSize());
		if (DocumentOrientation.VERTICAL == orientation)
		{
			pageSize.setRotation(pageSize.getRotation() - NINETY_ROTATION);
			boxSize.setRotation(pageSize.getRotation() - NINETY_ROTATION);
		}
		else if (DocumentOrientation.HORIZONTAL == orientation)
		{
			pageSize.setRotation(pageSize.getRotation() + NINETY_ROTATION);
			boxSize.setRotation(pageSize.getRotation() + NINETY_ROTATION);
		}
		document.setPageSize(pageSize);
		writer.setBoxSize(mainBoxName, boxSize);
	}

	/**
	 * @param backgroundColor Цвет фона документа.
	 */
	public void setBackgroundColor(BaseColor backgroundColor)
	{
		if (document == null)
			throw new IllegalArgumentException("document не инициализирован.");
		if (document.getPageSize() == null)
			throw new IllegalArgumentException("pageSize не инициализирован.");

		document.getPageSize().setBackgroundColor(backgroundColor);

	}


	/**
	 * перенос каретки на новую страницу
	 */
	public void newPage()
	{
		document.newPage();
	}

	public void flush()
	{
		writer.flush();
	}


	/**
	 * @return вертикальная позиция каретки
	 */
	public float getVerticalPosition()
	{
	    return writer.getVerticalPosition(true);
	}

	/**
	 * @param bufImage исходная картинка
	 * @return картинка для pdf
	 * @throws IOException
	 * @throws BadElementException
	 */
	public Image getImageInstance(BufferedImage bufImage) throws IOException, BadElementException
	{
		return Image.getInstance(writer, bufImage, 1.0f);
	}

	/**
	 * добавить элемент
	 * @param element элемент
	 * @throws DocumentException
	 */
	public void add(Element element) throws DocumentException
	{
		document.add(element);
	}

	/**
	 * @return основной шрифт для документа
	 */
	public Font getMainFont()
	{
		return mainFont;
	}

	/**
	 * завершить запись
	 */
	public void clear()
	{
		try
		{
			if (outputStream != null)
				outputStream.close();
		}
		catch (Exception ignore)
		{
			// Пытаемся закрыть поток. Если не получилось, ничего не делаем.
		}
	}

	/**
	 * завершить запись и вернуть результат
	 * @return результат записи
	 */
	public byte[] getResult()
	{
		try
		{
			document.close();
			return outputStream.toByteArray();
		}
		finally
		{
			clear();
		}
	}

	class PdfPageEventWhithUpdBackgroundForwarder extends PdfPageEventForwarder
	{
		public void onEndPage(PdfWriter writer, Document document)
		{
			writer.getPageSize().setBackgroundColor(BaseColor.WHITE);
			super.onEndPage(writer, document);
			writer.flush();

		}
	}

	/**
	 * Adds the keywords to a Document
	 * @param key ключ
	 */
	public void addKeyword(String key)
	{
		document.addKeywords(key);
	}
}
