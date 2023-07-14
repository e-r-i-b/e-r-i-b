package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.pdf.event.CellSpacingEvent;
import org.apache.commons.lang.StringUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author akrenev
 * @ created 06.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� �������� ������ � PDF ���������
 */
public class PDFTableBuilder
{
	private PDFBuilder mainBuilder;
	private PdfPTable table; // �������� �������
	private Paragraph title; // ��������� �������
	private int columnCount;  // ����� �������� � �������
	private int currentColumn;  // ������� �������
	private int[] rowspanMatrix; // ������� ����������� ����� ��� ������� ������
	private BaseFont baseFont;   // ������� �����
	private Map<String, BaseFont> addFonts;    //������������� ������, <�����, ��� ������>
	private ParagraphStyle baseParagraph;

	public static final int TABLE_WIDTH_VERT_LIST = 360;
	public static final int TABLE_WIDTH_HORIZ_LIST = 600;

	PDFTableBuilder(PDFBuilder mainBuilder, int columnCount, BaseFont baseFont, ParagraphStyle baseParagraph) throws PDFBuilderException
	{
		this(mainBuilder, columnCount, baseFont, null, baseParagraph,100);
	}

	/**
	 *
	 * @param mainBuilder
	 * @param columnCount
	 * @param baseFont
	 * @param baseParagraph
	 * @param widthPercentage
	 * @throws PDFBuilderException
	 */
	PDFTableBuilder(PDFBuilder mainBuilder, int columnCount, BaseFont baseFont, Map<String,String> addFontsStr, ParagraphStyle baseParagraph, float widthPercentage) throws PDFBuilderException
	{
		this.mainBuilder = mainBuilder;
		table = new PdfPTable(columnCount);
		table.setSpacingBefore(10);
		table.setSpacingAfter(10);
		this.columnCount = columnCount;
		currentColumn = 0;
		rowspanMatrix = new int[columnCount];
		for (int i = 0; i < columnCount; i++)
			rowspanMatrix[i] = 0;

		this.baseFont = baseFont;
		this.baseParagraph = baseParagraph;
		table.setWidthPercentage(widthPercentage);
		if (addFontsStr != null && !addFontsStr.isEmpty())
		{
			this.addFonts = new HashMap<String,BaseFont>();
			for (String fontKey: addFontsStr.keySet())
			{
				FontFactory.register(addFontsStr.get(fontKey),fontKey);
				BaseFont bFont = FontFactory.getFont(fontKey).getBaseFont();
				this.addFonts.put(fontKey,bFont);
			}

		}
	}

	PDFTableBuilder(PDFBuilder mainBuilder, int columnCount, String fontName, ParagraphStyle baseParagraph) throws PDFBuilderException
	{
		this(mainBuilder, columnCount,PDFBuilder.getBaseFont(fontName), baseParagraph);
	}

	PDFTableBuilder(PDFBuilder mainBuilder, int columnCount, String fontName, ParagraphStyle baseParagraph, float widthPercentage) throws PDFBuilderException
	{
		this(mainBuilder, columnCount,PDFBuilder.getBaseFont(fontName), null, baseParagraph, widthPercentage);
	}

	PDFTableBuilder(PDFBuilder mainBuilder, int columnCount, String fontName, Map<String,String> addFonts, ParagraphStyle baseParagraph, float widthPercentage) throws PDFBuilderException
	{
		this(mainBuilder, columnCount, PDFBuilder.getBaseFont(fontName), addFonts, baseParagraph, widthPercentage);
	}

	/**
	 * @return �������� �������
	 */
	public PdfPTable getTable()
	{
		return table;
	}

	/**
	 * ������������� ��������� �������
	 * @param titleText  ����� ���������
	 * @param titleStyle  ����� ��������� � ����������
	 * @param titleFontStyle  ����� ���������
	 * @throws PDFBuilderException
	 */
	public void setTableTitle(String titleText, ParagraphStyle titleStyle, FontStyle titleFontStyle) throws PDFBuilderException
	{
		title = mainBuilder.getParagraph(titleText, titleStyle, titleFontStyle);
	}

	public void setTableWidthPercentage(float percentage)
	{
		table.setWidthPercentage(percentage);
	}

	public void setWidths(int tableWidth, int[] widths) throws PDFBuilderException
	{
		try
		{
			table.setWidths(widths);

			float totalWidth = 0;
			for (int i=0; i < widths.length; i++)
				totalWidth += widths[i];
			table.setTotalWidth(totalWidth);
			table.setWidthPercentage(totalWidth/tableWidth*100);
		}
		catch (DocumentException ex)
		{
			throw new PDFBuilderException(ex);
		}

	}

	private void makeTableForNewRow()
	{
		for (int i = 0; i < columnCount; i++)
		{
			if (rowspanMatrix[i] != 0)
				rowspanMatrix[i]--;
		}
	}

	private void incColumnNumber(int colspan)
	{
		currentColumn = (currentColumn + colspan) % columnCount;
		if (currentColumn == 0)
			makeTableForNewRow();

		int i = currentColumn;
		boolean needInc = rowspanMatrix[i] != 0;
		while (needInc)
		{
			i++;
			currentColumn = (currentColumn + 1) % columnCount;
			if (currentColumn == 0)
			{
				makeTableForNewRow();
				i = 0;
			}
			needInc = rowspanMatrix[i] != 0;
		}
	}

	/**
	 * �������� � ������ ��������
	 * @param url ���� � ��������
	 * @throws PDFBuilderException
	 */
	public void addImageToCurrentRow(String url, int width, int height, CellStyle cellStyle) throws PDFBuilderException
	{
		addImageToCurrentRow(url, width, height, 1, 1, cellStyle);
	}

	/**
	 * �������� � ������ ��������
	 * @param image ��������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @throws PDFBuilderException
	 */
	public void addImageToCurrentRow(ImageWrapper image, int colspan, int rowspan, CellStyle cellStyle) throws PDFBuilderException
	{
		addImage(image.getImage(), colspan, rowspan, cellStyle);
	}

	/**
	 * �������� � ������ �������� �� url
	 * @param url ���� � ��������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @throws PDFBuilderException
	 */
	public void addImageToCurrentRow(String url, int width, int height, int colspan, int rowspan, CellStyle cellStyle) throws PDFBuilderException
	{
		try
		{
			if (colspan + currentColumn > columnCount)
				throw new PDFBuilderException("�������� ������ ������");

			Image image = Image.getInstance(url);
			image.scaleAbsolute(width, height);// ������������� ������ ����������� � �������� ���������
			addImage(image, colspan, rowspan, cellStyle);
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

	/**
	 * �������� � ������ ��������
	 * @param bufImage ��������
	 * @throws PDFBuilderException
	 */
	public void addImageToCurrentRow(BufferedImage bufImage, int width, int height, CellStyle cellStyle) throws PDFBuilderException
	{
		addImageToCurrentRow(bufImage, width, height, 1, 1, cellStyle);
	}

	/**
	 * �������� � ������ ��������
	 * @param bufImage ��������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @throws PDFBuilderException
	 */
	public void addImageToCurrentRow(BufferedImage bufImage, int width, int height, int colspan, int rowspan, CellStyle cellStyle) throws PDFBuilderException
	{
		if (colspan + currentColumn > columnCount)
			throw new PDFBuilderException("�������� ������ ������");

		Image image = mainBuilder.getImage(bufImage);
		image.scaleAbsolute(width, height);// ������������� ������ �����������
		addImage(image, colspan, rowspan, cellStyle);
	}

	/**
	 * �������� � ������ �������� �� url
	 * @param byteImage ��������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @throws PDFBuilderException
	 */
	public void addImageToCurrentRow(byte[] byteImage, int width, int height, int colspan, int rowspan, CellStyle cellStyle) throws PDFBuilderException
	{
		try
		{
			if (colspan + currentColumn > columnCount)
				throw new PDFBuilderException("�������� ������ ������");

			Image image = Image.getInstance(byteImage);
			image.scaleAbsolute(width, height);// ������������� ������ �����������
			addImage(image, colspan, rowspan, cellStyle);
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

	/**
	 * �������� � ������ ��������
	 * @param image ��������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 */
	public void addImage(Image image, int colspan, int rowspan, CellStyle cellStyle)
	{
		image.setAlignment(cellStyle.getHorizontalAlignment().getCode());
		image.setWidthPercentage(100);
		PdfPCell cell = new PdfPCell(image);
		cell.setHorizontalAlignment(cellStyle.getHorizontalAlignment().getCode());
		cell.setVerticalAlignment(cellStyle.getVerticalAlignment().getCode());
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);

		cell.setBackgroundColor(cellStyle.getBackgroundColor());
		cell.setBorderWidth(0);

		cell.setBorderWidthTop(cellStyle.getBorderWidthTop());
		cell.setBorderWidthRight(cellStyle.getBorderWidthRight());
		cell.setBorderWidthBottom(cellStyle.getBorderWidthBottom());
		cell.setBorderWidthLeft(cellStyle.getBorderWidthLeft());

		cell.setBorderColor(cellStyle.getBorderColor());

		if (cellStyle.getMinimumHeight() != 0)
			cell.setMinimumHeight(cellStyle.getMinimumHeight());

		if (cellStyle.getFixedHeight() != 0)
			cell.setFixedHeight(cellStyle.getFixedHeight());

		table.addCell(cell);


		for (int i = currentColumn; i < currentColumn + colspan; i++)
		{
			rowspanMatrix[i] = rowspan;
		}
		incColumnNumber(colspan);
	}

	/**
	 * �������� ������ ������
	 * @param cellStyle ����� ������
	 * @throws PDFBuilderException
	 */
	public void addEmptyValueToCell(CellStyle cellStyle) throws PDFBuilderException
	{
		addValueToCell(StringUtils.EMPTY, PDFTableStyles.TEXT_FONT, cellStyle);
	}

	/**
	 * �������� ������ ������
	 * @param cellStyle ����� ������
	 * @throws PDFBuilderException
	 */
	public void addEmptyValueToCell(int colSpan, int rowSpan, CellStyle cellStyle) throws PDFBuilderException
	{
		addValueToCell(StringUtils.EMPTY, colSpan, rowSpan, PDFTableStyles.TEXT_FONT, cellStyle);
	}

	/**
	 * �������� ������ ������
	 * @param cellStyle ����� ������
	 * @param event ������� �� ���������� ���������� ������
	 * @throws PDFBuilderException
	 */
	public void addEmptyValueToCell(int colSpan, int rowSpan, CellStyle cellStyle, PdfPCellEvent event) throws PDFBuilderException
	{
		addValueToCell(StringUtils.EMPTY, colSpan, rowSpan, PDFTableStyles.TEXT_FONT, cellStyle, event);
	}

	/**
	 * �������� ������
	 * @param value �������� ������
	 * @param fontStyle ����� ������
	 * @param cellStyle ����� ������
	 * @param event ������� �� ���������� ���������� ������
	 * @throws PDFBuilderException
	 */
	public void addValueToCell(String value, FontStyle fontStyle, CellStyle cellStyle, PdfPCellEvent event) throws PDFBuilderException
	{
		addValueToCell(value, 1, 1, fontStyle, cellStyle, event);
	}

	/**
	 * �������� ������
	 * @param value �������� ������
	 * @param fontStyle ����� ������
	 * @param cellStyle ����� ������
	 * @throws PDFBuilderException
	 */
	public void addValueToCell(String value, FontStyle fontStyle, CellStyle cellStyle) throws PDFBuilderException
	{
		addValueToCell(value, 1, 1, fontStyle, cellStyle, null);
	}

	/**
	 * �������� ������
	 * @param value �������� ������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @throws PDFBuilderException
	 */
	public void addCell(String value, int colspan, int rowspan, Alignment alignment) throws PDFBuilderException
	{
		if (colspan + currentColumn > columnCount)
			throw new PDFBuilderException("�������� ������ ������");

		Font font = new Font(baseFont);
		PdfPCell cell = new PdfPCell(new Phrase(value, font));
		cell.setHorizontalAlignment(alignment.getCode());
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);
		table.addCell(cell);


		for (int i = currentColumn; i < currentColumn + colspan; i++)
		{
			rowspanMatrix[i] = rowspan;
		}
		incColumnNumber(colspan);
	}

	/**
	 * �������� �������� � ������
	 * @param value �������� ������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @param fontStyle ����� ������
	 * @param event ������� �� ���������� ���������� ������
	 * @throws PDFBuilderException
	 */
	public void addValueToCell(String value, int colspan, int rowspan, FontStyle fontStyle, CellStyle cellStyle, PdfPCellEvent event) throws PDFBuilderException
	{
		if (colspan + currentColumn > columnCount)
			throw new PDFBuilderException("�������� ������ ������");

		Font font = getFont(fontStyle);

		Paragraph paragraph = new Paragraph(value, font);
		paragraph.setAlignment(cellStyle.getHorizontalAlignment().getCode());
		paragraph.setLeading(baseParagraph.getLeading());
		paragraph.setSpacingBefore(baseParagraph.getSpacingBefore());
		paragraph.setSpacingAfter(baseParagraph.getSpacingAfter());
		paragraph.setFirstLineIndent(baseParagraph.getFirstLineIndent());

		PdfPCell cell = new PdfPCell();
		if (event != null)
			cell.setCellEvent(event);
		cell.addElement(paragraph);
		addCell(cell, colspan, rowspan, cellStyle);
	}

	/**
	 * @param textAndStyle �������� � �����
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @param event ������� �� ���������� ���������� ������
	 * @throws PDFBuilderException
	 */
	public void addValueToCell(Queue<Pair<String,FontStyle>> textAndStyle, int colspan, int rowspan,  CellStyle cellStyle, PdfPCellEvent event) throws PDFBuilderException
	{
		if (colspan + currentColumn > columnCount)
			throw new PDFBuilderException("�������� ������ ������");

		PdfPCell cell = new PdfPCell();

		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(cellStyle.getHorizontalAlignment().getCode());
		paragraph.setLeading(baseParagraph.getLeading());
		paragraph.setSpacingBefore(baseParagraph.getSpacingBefore());
		paragraph.setSpacingAfter(baseParagraph.getSpacingAfter());
		paragraph.setFirstLineIndent(baseParagraph.getFirstLineIndent());
		for (Pair<String,FontStyle> pair :textAndStyle)
		{
			Font font = getFont(pair.getSecond());
			paragraph.add(new Phrase(pair.getFirst(), font));
		}
		if (event != null)
			cell.setCellEvent(event);
		cell.addElement(paragraph);
		addCell(cell, colspan, rowspan, cellStyle);
	}
	/**
	 * @param textAndStyle �������� � �����
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @throws PDFBuilderException
	 */
	public void addValueToCell(Queue<Pair<String,FontStyle>> textAndStyle, int colspan, int rowspan,  CellStyle cellStyle) throws PDFBuilderException
	{
		addValueToCell(textAndStyle, colspan, rowspan, cellStyle, null);
	}


	/**
	 * @param fontStyle ����� ������.
	 * @return �������������� �����.
	 * @throws PDFBuilderException
	 */
	private Font getFont(FontStyle fontStyle) throws PDFBuilderException
	{
		Font font = null;
		if (StringHelper.isEmpty(fontStyle.getFontName()))
			font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		else
		{
			BaseFont addFont = addFonts.get(fontStyle.getFontName());
			if (addFont == null)
				throw new PDFBuilderException("����� � ������: " + fontStyle.getFontName() + " �� ������");

			font = new Font(addFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		}
		return font;
	}

	/**
	 * �������� �������� � ������
	 * @param value �������� ������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @param fontStyle ����� ������
	 * @throws PDFBuilderException
	 */
	public void addValueToCell(String value, int colspan, int rowspan, FontStyle fontStyle, CellStyle cellStyle) throws PDFBuilderException
	{
		addValueToCell(value,colspan,rowspan,fontStyle,cellStyle,null);
	}

	/**
	 *
	 * @param value
	 * @param colspan
	 * @param rowspan
	 * @param underLinecolor
	 * @param underThickness
	 * @param underCap
	 * @param cellStyle
	 * @throws PDFBuilderException
	 */
	public void addValueWhithUnderLine(String value,
	                                   int colspan,
	                                   int rowspan,
	                                   BaseColor underLinecolor,
	                                   float underThickness,
	                                   int underCap,
	                                   FontStyle fontStyle,
	                                   CellStyle cellStyle) throws PDFBuilderException
	{
		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());

		Chunk chunk = new Chunk(value);
		chunk.setUnderline(underLinecolor, underThickness, 0, -0.5f, -0.5f, underCap);
		chunk.setFont(font);


		Paragraph paragraph = new Paragraph(chunk);
		paragraph.setAlignment(cellStyle.getHorizontalAlignment().getCode());
		paragraph.setLeading(baseParagraph.getLeading());
		paragraph.setSpacingBefore(baseParagraph.getSpacingBefore());
		paragraph.setSpacingAfter(baseParagraph.getSpacingAfter());
		paragraph.setFirstLineIndent(baseParagraph.getFirstLineIndent());

		PdfPCell cell = new PdfPCell();
		cell.addElement(paragraph);
		addCell(cell, colspan, rowspan, cellStyle);
	}

	/**
	 *
	 * @param value
	 * @param underLineColor
	 * @param fontStyle
	 * @param cellStyle
	 * @throws PDFBuilderException
	 */
	public void addValueWhithUnderLine(String value,
	                                   BaseColor underLineColor,
	                                   FontStyle fontStyle,
	                                   CellStyle cellStyle) throws PDFBuilderException
	{
		addValueWhithUnderLine(value, 1, 1, underLineColor, 0.1f, PdfContentByte.LINE_CAP_PROJECTING_SQUARE, fontStyle, cellStyle);
	}

	/**
	 *
	 * @param value
	 * @param fontStyle
	 * @param cellStyle
	 * @throws PDFBuilderException
	 */
	public void addValueWhithUnderLine(String value,
	                                   int colspan,
	                                   int rowspan,
	                                   BaseColor underLinecolor,
	                                   FontStyle fontStyle,
	                                   CellStyle cellStyle) throws PDFBuilderException
	{
		addValueWhithUnderLine(value, colspan, rowspan, underLinecolor, 0.1f, PdfContentByte.LINE_CAP_PROJECTING_SQUARE, fontStyle, cellStyle);
	}

	/**
	 *
	 * @param value
	 * @param fontStyle
	 * @param cellStyle
	 * @throws PDFBuilderException
	 */
	public void addValueToCellWhithUnderLine(String value,
	                                         FontStyle fontStyle,
	                                         CellStyle cellStyle) throws PDFBuilderException
	{
		addValueWhithUnderLine(value, 1, 1, BaseColor.BLACK, 0.1f, PdfContentByte.LINE_CAP_PROJECTING_SQUARE, fontStyle, cellStyle);
	}

	/**
	 * �������� �������� (���������) � ������
	 * @param cellValues �������� ������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @param fontStyle ����� ������
	 * @throws PDFBuilderException
	 */
	public void addValuesToCell(java.util.List<String> cellValues, int colspan, int rowspan, FontStyle fontStyle, CellStyle cellStyle) throws PDFBuilderException
	{
		if (colspan + currentColumn > columnCount)
			throw new PDFBuilderException("�������� ������ ������");

		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		PdfPCell cell = new PdfPCell();

		for (int i = 0; i < cellValues.size(); i++)
		{
			Paragraph paragraph = new Paragraph(cellValues.get(i), font);
			paragraph.setAlignment(baseParagraph.getAlignment().getCode());
			paragraph.setLeading(baseParagraph.getLeading());
			paragraph.setSpacingBefore(baseParagraph.getSpacingBefore());
			paragraph.setSpacingAfter(baseParagraph.getSpacingAfter());
			paragraph.setFirstLineIndent(baseParagraph.getFirstLineIndent());

			cell.addElement(paragraph);
		}

		addCell(cell, colspan, rowspan, cellStyle);
	}

	/**
	 * �������� �������� � ������
	 * @param cellValues �������� ������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @throws PDFBuilderException
	 */
	public void addElementsToCell(java.util.List<Chunk> cellValues, int colspan, int rowspan, CellStyle cellStyle) throws PDFBuilderException
	{
		if (colspan + currentColumn > columnCount)
			throw new PDFBuilderException("�������� ������ ������");

		PdfPCell cell = new PdfPCell();

		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(cellStyle.getHorizontalAlignment().getCode());
		paragraph.setLeading(baseParagraph.getLeading());
		paragraph.setSpacingBefore(baseParagraph.getSpacingBefore());
		paragraph.setSpacingAfter(baseParagraph.getSpacingAfter());
		paragraph.setFirstLineIndent(baseParagraph.getFirstLineIndent());

		for (int i = 0; i < cellValues.size(); i++)
		{
			Chunk element = cellValues.get(i);
			paragraph.add(element);
		}
		cell.addElement(paragraph);

		addCell(cell, colspan, rowspan, cellStyle);
	}

	/**
	 *
	 * @param cellValue
	 * @param colspan
	 * @param rowspan
	 * @param cellStyle
	 * @throws PDFBuilderException
	 */
	public void addElementToCell(Chunk cellValue, int colspan, int rowspan, CellStyle cellStyle) throws PDFBuilderException
	{

		PdfPCell cell = new PdfPCell();

		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(cellStyle.getHorizontalAlignment().getCode());
		paragraph.setLeading(baseParagraph.getLeading());
		paragraph.setSpacingBefore(baseParagraph.getSpacingBefore());
		paragraph.setSpacingAfter(baseParagraph.getSpacingAfter());
		paragraph.setFirstLineIndent(baseParagraph.getFirstLineIndent());
		Chunk element = cellValue;
		paragraph.add(element);
		cell.addElement(paragraph);

		addCell(cell, colspan, rowspan, cellStyle);
	}

	/**
	 * �������� �������� � ������ (���� ��������, ������ ����� � ��������� ���������)
	 * @param cellValues �������� ������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @param cellStyle
	 * @throws PDFBuilderException
	 */
	public void addValuesToCell(List<Pair<String, FontStyle>> cellValues, int colspan, int rowspan, CellStyle cellStyle) throws PDFBuilderException
	{
		if (colspan + currentColumn > columnCount)
			throw new PDFBuilderException("�������� ������ ������");

		Paragraph paragraph = new Paragraph(StringUtils.EMPTY);
		paragraph.setAlignment(baseParagraph.getAlignment().getCode());
		paragraph.setLeading(baseParagraph.getLeading());
		paragraph.setSpacingBefore(baseParagraph.getSpacingBefore());
		paragraph.setSpacingAfter(baseParagraph.getSpacingAfter());
		paragraph.setFirstLineIndent(baseParagraph.getFirstLineIndent());

		for (Pair<String, FontStyle> phrase : cellValues)
		{
			String text = phrase.getFirst();
			FontStyle fontStyle = phrase.getSecond();
			Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
			Phrase phraseInstance = new Phrase(text, font);
			paragraph.add(phraseInstance);
		}
		PdfPCell cell = new PdfPCell();
		cell.addElement(paragraph);

		addCell(cell, colspan, rowspan, cellStyle);
	}

	/**
	 * �������� ������� � ������
	 * @param tableBuilder - ������
	 * @param cellStyle - ����� ������
	 * @throws PDFBuilderException
	 */
	public void addTableToCell(PDFTableBuilder tableBuilder, CellStyle cellStyle) throws PDFBuilderException
	{
		PdfPCell cell = new PdfPCell();
		cell.addElement(tableBuilder.table);

		addCell(cell, 1, 1, cellStyle);
	}

	/**
	 * �������� ������� � ������
	 * @param tableBuilder - ������
	 * @param cellStyle - ����� ������
	 * @param colSpan
	 * @param rowSpan
	 * @throws PDFBuilderException
	 */
	public void addTableToCell(PDFTableBuilder tableBuilder, int colSpan, int rowSpan, CellStyle cellStyle) throws PDFBuilderException
	{
		PdfPCell cell = new PdfPCell();
		cell.addElement(tableBuilder.table);

		addCell(cell, colSpan, rowSpan, cellStyle);
	}

	/**
	 * �������� ������
	 * @param cell ������
	 * @param colspan ����� ��������, ������������ ������ �������
	 * @param rowspan ����� �����, ������������ ������ �������
	 * @param cellStyle ����� ������
	 * @throws PDFBuilderException
	 */
	public void addCell(PdfPCell cell, int colspan, int rowspan, CellStyle cellStyle) throws PDFBuilderException
	{
		cell.setHorizontalAlignment(cellStyle.getHorizontalAlignment().getCode());
		cell.setVerticalAlignment(cellStyle.getVerticalAlignment().getCode());
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);

		cell.setBackgroundColor(cellStyle.getBackgroundColor());
		cell.setBorderWidth(0);

		cell.setBorderWidthTop(cellStyle.getBorderWidthTop());
		cell.setBorderWidthRight(cellStyle.getBorderWidthRight());
		cell.setBorderWidthBottom(cellStyle.getBorderWidthBottom());
		cell.setBorderWidthLeft(cellStyle.getBorderWidthLeft());

		cell.setBorderColor(cellStyle.getBorderColor());

		if (cellStyle.getMinimumHeight() != 0)
			cell.setMinimumHeight(cellStyle.getMinimumHeight());

		if (cellStyle.getFixedHeight() != 0)
			cell.setFixedHeight(cellStyle.getFixedHeight());

		table.addCell(cell);


		for (int i = currentColumn; i < currentColumn + colspan; i++)
		{
			rowspanMatrix[i] = rowspan;
		}
		incColumnNumber(colspan);
	}

	public void add(Element element)
	{
		PdfPCell cell = new PdfPCell();
		cell.setBorderWidth(0);
		cell.addElement(element);
		table.addCell(cell);
	}

	/**
	 * �������� ������� �� ��������
	 * @param rule ������� �������� �� ��������� ��������
	 * @throws PDFBuilderException
	 */
	public void addToPage(TableBreakRule rule) throws PDFBuilderException
	{
		mainBuilder.addTable(new PdfPTableWrapper(title, table), rule, Alignment.top);
	}

	/**
	 * �������� ������� �� ��������
	 * @param rule ������� �������� �� ��������� ��������
	 * @param titleAlignment - ����� ��������� ��������� ������� ������������ ����� ������� (������ ��� �����)
	 * @throws PDFBuilderException
	 */
	public void addToPage(TableBreakRule rule, Alignment titleAlignment) throws PDFBuilderException
	{
		mainBuilder.addTable(new PdfPTableWrapper(title, table), rule, titleAlignment);
	}
}
