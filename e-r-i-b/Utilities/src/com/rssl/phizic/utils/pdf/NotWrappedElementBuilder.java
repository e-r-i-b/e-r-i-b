package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class NotWrappedElementBuilder
{
	private final List<Element> elements = new ArrayList<Element>();
	private final PDFBuilder builder;
	private final int elementWidth;

	NotWrappedElementBuilder(PDFBuilder builder, int elementWidth)
	{
		this.builder = builder;
		this.elementWidth = elementWidth;
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
		Paragraph paragraph = builder.getParagraph(text, style, fontStyle);
		add(paragraph);
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
		Image image = builder.getImage(bufImage);
		image.setAlignment(alignment.getCode());
		image.scaleAbsolute(width, height);// ������������� ������ �����������
		add(image);
    }

	private void add(Element element)
	{
		elements.add(element);
	}

	public void addToPage() throws PDFBuilderException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(1, PDFBuilder.BASE_PARAGRAPH);
		tableBuilder.setWidths(elementWidth, new int[] {elementWidth});
		for (Element element: elements)
			tableBuilder.add(element);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
	}

	/**
	 * �������� �������
	 * @param tableBuilder - �������
	 * @throws PDFBuilderException
	 */
	public void addTable(PDFTableBuilder tableBuilder) throws
			PDFBuilderException
	{
		add(tableBuilder.getTable());
	}
}
