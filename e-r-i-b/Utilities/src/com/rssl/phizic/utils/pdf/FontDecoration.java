package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.Font;

/**
 * @author lepihina
 * @ created 20.04.2012
 * @ $Author$
 * @ $Revision$
 */
public enum FontDecoration
{
	normal(Font.NORMAL),
	bold(Font.BOLD),
	boldItalic(Font.BOLDITALIC),
	italic(Font.ITALIC),
	underline(Font.UNDERLINE);

	private int code;

	FontDecoration(int code)
	{
		this.code = code;
	}

	/**
	 * @return форматирование
	 */
	public int getCode()
	{
		return code;
	}
}
