package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.chart.DocumentOrientation;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 02.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class PDFBuilder
{
    public static final String MAIN_BOX_NAME = "art";
	public static final String FONT_NAME = "Arial.ttf";
	public static final int INDENT = 60;
	private static final float SUB_DELTA = 3f;
	private static final float SUP_DELTA = 5f;

	private DocumentWriter documentWriter;

	private BaseFont baseFont;
	private Paragraph tempParagraph = null;

	// �������� ����� ��� ���������� ���������
	public static final ParagraphStyle BASE_PARAGRAPH = new ParagraphStyle(20f, 0f, 15f, 0f);
	public static final ParagraphStyle BASE_LAST_ON_PAGE_PARAGRAPH = new ParagraphStyle(20f, 0f, 0f, 0f);
	public static final ParagraphStyle BASE_JUSTIFIED_PARAGRAPH = new ParagraphStyle(20f, 0f, 15f, 0f, Alignment.justified);
	public static final ParagraphStyle BASE_TITLE_PARAGRAPH =  new ParagraphStyle(15f, 0f, 6f, 0f, Alignment.center);
	public static final ParagraphStyle TITLE_PARAGRAPH = new ParagraphStyle(15f, 0f, 12f, 0f);
	public static final ParagraphStyle SUB_TITLE_PARAGRAPH = new ParagraphStyle(15f, 0f, 12f, 0f);
	public static final ParagraphStyle TITLE_FILE_PARAGRAPH = new ParagraphStyle(40f, 70f, 50f, 0f);
	public static final ParagraphStyle TITLE_FILE_PARAGRAPH_CENTER = new ParagraphStyle(40f, 70f, 50f, 0f, Alignment.center);
	public static final ParagraphStyle LONG_PARAGRAPH = new ParagraphStyle(150f, 50f, 0f, 0f);

	// �������� ����� ��� ������� ���������
	public static final FontStyle TEXT_FONT = new FontStyle(12);
	public static final FontStyle TEXT_FONT_WHITE = new FontStyle(12,FontDecoration.normal,Color.WHITE);
	public static final FontStyle TITLE_LIST_FONT = new FontStyle(40);
	public static final FontStyle TITLE_LIST_FONT_WHITE= new FontStyle(40,FontDecoration.normal,Color.WHITE);
	public static final FontStyle TITLE_FONT = new FontStyle(16, FontDecoration.bold);
	public static final FontStyle SUB_TITLE_FONT = new FontStyle(14, FontDecoration.bold);
	public static final FontStyle BOLD_FONT = new FontStyle(12, FontDecoration.bold);
	public static final FontStyle ITALIC_BOLD_FONT = new FontStyle(12, FontDecoration.boldItalic);
	public static final FontStyle SUB_SUP_BOLD_FONT = new FontStyle(8, FontDecoration.bold);
	public static final FontStyle HYPERLINK_FONT = new FontStyle(12, FontDecoration.underline, Color.BLUE);

	private PDFBuilder(String fontName, float fontSize, DocumentOrientation orientation, HeaderFooterBuilder builder) throws PDFBuilderException
	{
		try
		{
			baseFont = getBaseFont(fontName);
			documentWriter = new DocumentWriter(orientation, MAIN_BOX_NAME, builder, new Font(baseFont, fontSize));
		}
		catch (DocumentException e)
		{
			throw new PDFBuilderException(e);
		}
	}

	/**
	 * @param builder ������ ������-������
	 * @param fontName ��� ������ ��� ���� � ����
	 * @param fontSize ������ ������ �� ���������
	 * @param orientation ���������� ��������
	 * @return ������ PDF ���������
	 * @throws PDFBuilderException
	 */
	public static PDFBuilder getInstance(HeaderFooterBuilder builder, String fontName, float fontSize, DocumentOrientation orientation) throws PDFBuilderException
	{
		return new PDFBuilder(fontName, fontSize, orientation, builder);
	}

	/**
	 * �������� ������� PDFTableBuilder
	 * @param columnCount   ���������� �������� � �������
	 * @param fontName      �����, ������������ � �������
	 * @param baseParagraph ��������� ����������� ������� � �������
	 * @return ������� PDFTableBuilder
	 * @throws PDFBuilderException
	 */
	public PDFTableBuilder getTableBuilderInstance(int columnCount, String fontName, ParagraphStyle baseParagraph) throws PDFBuilderException
	{
		return new PDFTableBuilder(this, columnCount, fontName, baseParagraph);
	}

	/**
	 *
	 * @param columnCount
	 * @param fontName
	 * @param baseParagraph
	 * @param widthPercentage
	 * @return
	 * @throws PDFBuilderException
	 */
	public PDFTableBuilder getTableBuilderInstance(int columnCount, String fontName, ParagraphStyle baseParagraph, float widthPercentage) throws PDFBuilderException
	{
		return new PDFTableBuilder(this, columnCount, fontName, baseParagraph, widthPercentage);
	}

	/**
	 *
	 * @param columnCount
	 * @param fontName
	 * @param baseParagraph
	 * @param widthPercentage
	 * @param addFonts �������������� ������
	 * @return
	 * @throws PDFBuilderException
	 */
	public PDFTableBuilder getTableBuilderInstance(int columnCount, String fontName,Map<String,String> addFonts, ParagraphStyle baseParagraph, float widthPercentage) throws PDFBuilderException
	{
		return new PDFTableBuilder(this, columnCount, fontName, addFonts, baseParagraph, widthPercentage);
	}

	/**
	 * �������� ������� PDFTableBuilder
	 * @param columnCount   ���������� �������� � �������
	 * @param baseParagraph ��������� ����������� ������� � �������
	 * @return ������� PDFTableBuilder
	 * @throws PDFBuilderException
	 */
	public PDFTableBuilder getTableBuilderInstance(int columnCount, ParagraphStyle baseParagraph) throws PDFBuilderException
	{
		return new PDFTableBuilder(this, columnCount, baseFont, baseParagraph);
	}


	/**
	 * �������� ������� NotWrappedElementBuilder
	 * @return ������� NotWrappedElementBuilder
	 * @throws PDFBuilderException
	 */
	public NotWrappedElementBuilder getNotWrappedElementBuilderInstance() throws PDFBuilderException
	{
		int elementWidth = Math.round(documentWriter.getOrientation().getBoxSize().getWidth());
		return new NotWrappedElementBuilder(this, elementWidth);
	}

	/**
	 * ������ ���������� ��������
	 * @param orientation ����������
	 */
	public void setOrientation(DocumentOrientation orientation)
	{
		documentWriter.setOrientation(orientation);
	}

	/**
	 * ������������� ���� ���� ������� ��������.
	 * @param backgroundColor ���� ���� ���������.
	 */
	public void setBackgroundColor(BaseColor backgroundColor)
	{
		documentWriter.flush();
		documentWriter.setBackgroundColor(backgroundColor);
		documentWriter.flush();
	}

	/**
	 * ������� �� ����� ��������
	 */
	public void newPage()
	{
		documentWriter.newPage();
	}

	/**
	 * �������� ������ ��������
	 * @throws PDFBuilderException
	 */
	public void addEmptyParagraph() throws PDFBuilderException
	{
		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(Element.ALIGN_CENTER);
		add(paragraph);
	}

	/**
	 * �������� ������ �������� � �������� ������
	 * @param style ����� ���������
	 * @throws PDFBuilderException
	 */
	public void addEmptyParagraph(ParagraphStyle style) throws PDFBuilderException
	{
		Font font = documentWriter.getMainFont();
		Paragraph paragraph = new Paragraph(" ", font);

		paragraph.setAlignment(style.getAlignment().getCode());
		paragraph.setLeading(style.getLeading());
		paragraph.setSpacingBefore(style.getSpacingBefore());
		paragraph.setSpacingAfter(style.getSpacingAfter());
		paragraph.setFirstLineIndent(style.getFirstLineIndent());

		add(paragraph);
	}

	/**
	 * �������� ��������
	 * @param text �����
	 * @throws PDFBuilderException
	 */
	public void addParagraph(String text) throws PDFBuilderException
	{
		addParagraph(text, Alignment.left);
    }

	/**
	 * �������� ��������
	 * @param text �����
	 * @param alignment ������������
	 * @throws PDFBuilderException
	 */
	public void addParagraph(String text, Alignment alignment) throws PDFBuilderException
	{
		Font font = documentWriter.getMainFont();
		Paragraph paragraph = new Paragraph(text, font);
		paragraph.setAlignment(alignment.getCode());
		add(paragraph);
    }

	/**
	 * �������� ��������
	 * @param text �����
	 * @param style ����� ���������
	 * @throws PDFBuilderException
	 */
	public void addParagraph(String text, ParagraphStyle style) throws PDFBuilderException
	{
		Font font = documentWriter.getMainFont();
		Paragraph paragraph = new Paragraph(text, font);

		paragraph.setAlignment(style.getAlignment().getCode());
		paragraph.setLeading(style.getLeading());
		paragraph.setSpacingBefore(style.getSpacingBefore());
		paragraph.setSpacingAfter(style.getSpacingAfter());
		paragraph.setFirstLineIndent(style.getFirstLineIndent());

		add(paragraph);
    }

	/**
	 * �������� ��������
	 * @param text �����
	 * @param style ����� ���������
	 * @param fontStyle ����� ������
	 * @throws PDFBuilderException
	 */
	public void addParagraph(String text, ParagraphStyle style, FontStyle fontStyle) throws PDFBuilderException
	{
		Paragraph paragraph = getParagraph(text, style, fontStyle);
		add(paragraph);
    }

	/**
	 * �������� �������� �� ������ ����
	 * @param phrases �����
	 * @param paragraphStyle ����� ���������
	 * @throws PDFBuilderException
	 */
	public void addParagraph(java.util.List<Pair<String, FontStyle>> phrases, ParagraphStyle paragraphStyle) throws PDFBuilderException
	{
		Paragraph paragraph = getParagraph(StringUtils.EMPTY, paragraphStyle);
		for (Pair<String, FontStyle> phrase : phrases)
		{
			String text = phrase.getFirst();
			FontStyle fontStyle = phrase.getSecond();
			Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
			Phrase phraseInstance = new Phrase(text, font);
			paragraph.add(phraseInstance);
		}
		add(paragraph);
	}

	/**
	 * �������� ��������
	 * @param text �����
	 * @param style ����� ���������
	 * @return ������������ ��������
	 * @throws PDFBuilderException
	 */
	Paragraph getParagraph(String text, ParagraphStyle style) throws PDFBuilderException
	{
		Font font = documentWriter.getMainFont();
		Paragraph paragraph = new Paragraph(text, font);

		paragraph.setAlignment(style.getAlignment().getCode());
		paragraph.setLeading(style.getLeading());
		paragraph.setSpacingBefore(style.getSpacingBefore());
		paragraph.setSpacingAfter(style.getSpacingAfter());
		paragraph.setFirstLineIndent(style.getFirstLineIndent());

		return paragraph;
    }

	/**
	 * �������� ��������
	 * @param text �����
	 * @param style ����� ���������
	 * @param fontStyle ����� ������
	 * @return ������������ ��������
	 * @throws PDFBuilderException
	 */
	Paragraph getParagraph(String text, ParagraphStyle style, FontStyle fontStyle) throws PDFBuilderException
	{
		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		Paragraph paragraph = new Paragraph(text, font);

		paragraph.setAlignment(style.getAlignment().getCode());
		paragraph.setLeading(style.getLeading());
		paragraph.setSpacingBefore(style.getSpacingBefore());
		paragraph.setSpacingAfter(style.getSpacingAfter());
		paragraph.setFirstLineIndent(style.getFirstLineIndent());

		return paragraph;
    }

	/**
	 * �������� ������ (��������� ����� �������� � �������)
	 * @param linkText ����� ��� ������
	 * @param url ���
	 * @param style ����� ���������
	 * @param fontStyle ����� ������
	 * @throws PDFBuilderException
	 */
	public void addHyperlink(String linkText, String url, ParagraphStyle style, FontStyle fontStyle) throws PDFBuilderException
	{
		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		Anchor anchor = new Anchor(linkText, font);
		anchor.setReference(url);
		anchor.setName(linkText);

		Phrase hyperlink = new Phrase();
		hyperlink.add(anchor);

		Paragraph paragraph = new Paragraph(hyperlink);
		paragraph.setAlignment(style.getAlignment().getCode());
		paragraph.setLeading(style.getLeading());
		paragraph.setSpacingBefore(style.getSpacingBefore());
		paragraph.setSpacingAfter(style.getSpacingAfter());
		paragraph.setFirstLineIndent(style.getFirstLineIndent());

		add(paragraph);
	}

	/**
	 * �������� ������ (��������� ������ � ������� ��������)
	 * @param linkText ����� ��� ������
	 * @param url ���
	 * @param fontStyle ����� ������
	 * @throws PDFBuilderException
	 */
	public void addHyperlinkPhrase(String linkText, String url, FontStyle fontStyle) throws PDFBuilderException
	{
		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		Anchor anchor = new Anchor(linkText, font);
		anchor.setReference(url);
		anchor.setName(linkText);

		Phrase hyperlink = new Phrase();
		hyperlink.add(anchor);

		add(hyperlink);
	}

	/**
	 * �������� �����
	 * @param text �����
	 * @throws PDFBuilderException
	 */
	public void addPhrase(String text) throws PDFBuilderException
	{
		Font font = documentWriter.getMainFont();
		Phrase phrase = new Phrase(text, font);

		add(phrase);
	}

	/**
	 * ��������
	 * @param text �����
	 * @param fontStyle ����� ������
	 * @throws PDFBuilderException
	 */
	public void addPhrase(String text, FontStyle fontStyle) throws PDFBuilderException
	{
		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		Phrase phrase = new Phrase(text, font);

		add(phrase);
	}

	/**
	 * ��������� ����������� �������
	 * @param text �������
	 * @param fontStyle �����
	 * @return ����������� �������
	 */
	public Chunk subString(String text, FontStyle fontStyle)
	{
		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		Chunk subString = new Chunk(text, font);
	    subString.setTextRise(-SUB_DELTA);

		return subString;
	}

	/**
	 * ��������� ����������� �������
	 * @param text �������
	 * @param fontStyle �����
	 * @return ����������� �������
	 */
	public Chunk supString(String text, FontStyle fontStyle)
	{
		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		Chunk supString = new Chunk(text, font);
	    supString.setTextRise(SUP_DELTA);

		return supString;
	}

	/**
	 * ��������� ������ ������
	 * @param text ������
	 * @param fontStyle �����
	 * @return ������ ������
	 */
	public Chunk textString(String text, FontStyle fontStyle)
	{
		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		return new Chunk(text, font);
	}

	/**
	 * ��������� ����������� �������
	 * @param text �������
	 * @param fontStyle �����
	 */
	public void addSubString(String text, FontStyle fontStyle) throws PDFBuilderException
	{
		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		Chunk subString = new Chunk(text, font);
	    subString.setTextRise(-SUB_DELTA);

		add(subString);
	}

	/**
	 * ��������� ����������� �������
	 * @param text �������
	 * @param fontStyle �����
	 */
	public void addSupString(String text, FontStyle fontStyle) throws PDFBuilderException
	{
		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		Chunk supString = new Chunk(text, font);
	    supString.setTextRise(SUP_DELTA);

		add(supString);
	}

	/**
	 * ��������� ������ ������
	 * @param text ������
	 */
	public void addTextString(String text) throws PDFBuilderException
	{
		Font font = documentWriter.getMainFont();
		add(new Chunk(text, font));
	}

	/**
	 * ��������� ������ ������
	 * @param text ������
	 * @param fontStyle �����
	 */
	public void addTextString(String text, FontStyle fontStyle) throws PDFBuilderException
	{
		Font font = new Font(baseFont, fontStyle.getSize(), fontStyle.getStyle(), fontStyle.getColor());
		add(new Chunk(text, font));
	}

	/**
	 * �������� ��������
	 * @param url ��� ��������
	 * @param width ������ �����������
	 * @param height ������ �����������
	 * @throws PDFBuilderException
	 */
    public void addImage(String url, int width, int height) throws PDFBuilderException
    {
	    addImage(url, width, height, Alignment.center);
    }

	/**
	 * �������� ��������
	 * @param url ��� ��������
	 * @param width ������ �����������
	 * @param height ������ �����������
	 * @param alignment ������������
	 * @throws PDFBuilderException
	 */
    public void addImage(String url, int width, int height, Alignment alignment) throws PDFBuilderException
    {
	    try
	    {
		    Image image = Image.getInstance(url);
		    if (documentWriter.getVerticalPosition() - INDENT < height)
		        documentWriter.newPage();

		    image.setAlignment(alignment.getCode());
		    image.scaleAbsolute(width, height);// ������������� ������ �����������
		    add(image);
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

	public static BaseFont getBaseFont(String fontName) throws PDFBuilderException
	{
		try
		{
			return BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		}
		catch (DocumentException e)
		{
			throw new PDFBuilderException(e);
		}
		catch (IOException e)
		{
			throw new PDFBuilderException(e);
		}
	}

	Image getImage(BufferedImage bufImage) throws PDFBuilderException
	{
		try
		{
			return documentWriter.getImageInstance(bufImage);
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
	 * �������� ��������
	 * @param bufImage ��������
	 * @param width ������ �����������
	 * @param height ������ �����������
	 * @param alignment ������������
	 * @throws PDFBuilderException
	 */
    public void addImage(BufferedImage bufImage, int width, int height, Alignment alignment) throws PDFBuilderException
    {
		Image image = getImage(bufImage);
		if (documentWriter.getVerticalPosition() - INDENT < height)
			documentWriter.newPage();

		image.setAlignment(alignment.getCode());
		image.scaleAbsolute(width, height);// ������������� ������ �����������

		add(image);
    }

	/**
	 * �������� ������� � ����������
	 * @param tableWrapper ������� �������
	 * @param rule ������� ���������
	 * @throws PDFBuilderException
	 */
	void addTable(PdfPTableWrapper tableWrapper, TableBreakRule rule, Alignment titleAlignment) throws PDFBuilderException
	{
		try
		{
			if (rule.needNewPage(documentWriter, tableWrapper))
				documentWriter.newPage();

			if (titleAlignment == Alignment.top && tableWrapper.getTitle() != null)
				add(tableWrapper.getTitle());

			add(tableWrapper.getTable());

			if (titleAlignment == Alignment.bottom && tableWrapper.getTitle() != null)
				add(tableWrapper.getTitle());
		}
		catch (DocumentException e)
		{
			throw new PDFBuilderException(e);
		}
	}

	/**
	 * ������ ��������
	 * @param style ����� ���������
	 * @throws PDFBuilderException
	 */
	public void startParagraph(ParagraphStyle style) throws PDFBuilderException
	{
		Font font = documentWriter.getMainFont();
		Paragraph paragraph = new Paragraph(" ", font);

		paragraph.setAlignment(style.getAlignment().getCode());
		paragraph.setLeading(style.getLeading());
		paragraph.setSpacingBefore(style.getSpacingBefore());
		paragraph.setSpacingAfter(style.getSpacingAfter());
		paragraph.setFirstLineIndent(style.getFirstLineIndent());

		tempParagraph = paragraph;
	}

	/**
	 * ��������� ��������
	 * @throws PDFBuilderException
	 */
	public void endParagraph()  throws PDFBuilderException
	{
		try
		{
			if(tempParagraph != null)
			{
				documentWriter.add(tempParagraph);
				tempParagraph = null;
			}
		}
		catch (DocumentException e)
		{
			throw new PDFBuilderException(e);
		}
	}

	private void add(Element element) throws PDFBuilderException
	{
		try
		{
			//���� � ��� �� ����� ��������, �� ������ ��������� �������, ����� ��������� ��� � ���������.
			if (tempParagraph==null)
				documentWriter.add(element);
			else
				tempParagraph.add(element);
		}
		catch (DocumentException e)
		{
			throw new PDFBuilderException(e);
		}
	}

	/**
	 * @return PDF
	 */
    public byte[] build()
    {
		return documentWriter.getResult();
    }

	/**
	 * Adds the keywords to a Document
	 * @param key ����
	 */
	public void addKeyword(String key)
	{
		documentWriter.addKeyword(key);
	}

}
