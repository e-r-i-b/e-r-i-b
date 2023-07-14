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
 * �����-�������� ��� ������ ��������������� � ����������
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
	 * @param orientation ���������� ���������
	 * @param builder   ������� ������������ �� ��������
	 * @param mainFont �������� �����
	 * @param addFonts �������������� ������
	 * @param mainBoxName ��� ������� �������
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
	 * @return �����
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
	 * ������ ���������� ��������
	 * @param orientation ����������
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
	 * @param backgroundColor ���� ���� ���������.
	 */
	public void setBackgroundColor(BaseColor backgroundColor)
	{
		if (document == null)
			throw new IllegalArgumentException("document �� ���������������.");
		if (document.getPageSize() == null)
			throw new IllegalArgumentException("pageSize �� ���������������.");

		document.getPageSize().setBackgroundColor(backgroundColor);

	}


	/**
	 * ������� ������� �� ����� ��������
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
	 * @return ������������ ������� �������
	 */
	public float getVerticalPosition()
	{
	    return writer.getVerticalPosition(true);
	}

	/**
	 * @param bufImage �������� ��������
	 * @return �������� ��� pdf
	 * @throws IOException
	 * @throws BadElementException
	 */
	public Image getImageInstance(BufferedImage bufImage) throws IOException, BadElementException
	{
		return Image.getInstance(writer, bufImage, 1.0f);
	}

	/**
	 * �������� �������
	 * @param element �������
	 * @throws DocumentException
	 */
	public void add(Element element) throws DocumentException
	{
		document.add(element);
	}

	/**
	 * @return �������� ����� ��� ���������
	 */
	public Font getMainFont()
	{
		return mainFont;
	}

	/**
	 * ��������� ������
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
			// �������� ������� �����. ���� �� ����������, ������ �� ������.
		}
	}

	/**
	 * ��������� ������ � ������� ���������
	 * @return ��������� ������
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
	 * @param key ����
	 */
	public void addKeyword(String key)
	{
		document.addKeywords(key);
	}
}
