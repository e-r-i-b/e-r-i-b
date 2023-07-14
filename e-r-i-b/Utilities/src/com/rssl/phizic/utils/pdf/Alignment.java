package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.Element;

/**
 * @author akrenev
 * @ created 11.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * враппер для выравнивания элементов iText
 */
public enum Alignment
{
	left(Element.ALIGN_LEFT),
	right(Element.ALIGN_RIGHT),
	center(Element.ALIGN_CENTER),
	justified(Element.ALIGN_JUSTIFIED),
	top(Element.ALIGN_TOP),
	bottom(Element.ALIGN_BOTTOM),
	middle(Element.ALIGN_MIDDLE);

	private int code;

	Alignment(int code)
	{
		this.code = code;
	}

	/**
	 * @return выравнивание
	 */
	public int getCode()
	{
		return code;
	}
}
