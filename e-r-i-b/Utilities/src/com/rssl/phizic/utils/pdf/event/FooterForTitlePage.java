package com.rssl.phizic.utils.pdf.event;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.rssl.phizic.utils.pdf.FontStyle;

/**
 * @author akrenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �������, ����������� ����� ����� (�����) �������� �����
 */

public class FooterForTitlePage extends SimpleTextFooterBase
{
	private static final int X_MARGIN = 20;
	private static final int Y_MARGIN = 20;

	private String footerPhrase;

	/**
	 * ����������� �� ������
	 * @param footerPhrase �����
	 */
	public FooterForTitlePage(String footerPhrase)
	{
		this.footerPhrase = footerPhrase;
	}

	/**
	 * ����������� �� ������ � ��������
	 * @param footerPhrase �����
	 * @param fontStyle ������
	 */
	public FooterForTitlePage(String footerPhrase, FontStyle fontStyle)
	{
		setFontStyle(fontStyle);
		this.footerPhrase = footerPhrase;
	}

	@Override
	protected int getAlignment()
	{
		return Element.ALIGN_LEFT;
	}

	@Override
	protected Phrase getFooter()
	{
		return new Phrase(footerPhrase, getFontWithStyle());
	}

	@Override
	protected float getXPosition(Rectangle rect)
	{
		return rect.getLeft() + X_MARGIN;
	}

	@Override
	protected float getYPosition(Rectangle rect)
	{
		return rect.getBottom() + Y_MARGIN;
	}

	@Override
	protected boolean needAddFooter(PdfWriter writer)
	{
		return getPageNumber() == 1;
	}
}
