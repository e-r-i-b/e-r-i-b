package com.rssl.phizic.captcha;

/**
 * ���������� ��������� ���� ������.
 *
 * @author bogdanov
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */

class CaptchaCodeVisualConfig
{
	static final String FONTS_KEY = "com.rssl.captcha.code.visual.fonts";
	static final String COLOR_KEY = "com.rssl.captcha.code.visual.color";

	/**
	 * ����� ������� ��� �������� ������.
	 */
	private String[] fontNames;
	/**
	 * ���� �������� � �����.
	 */
	private int color;

	public int getColor()
	{
		return color;
	}

	public void setColor(int color)
	{
		this.color = color;
	}

	public String[] getFontNames()
	{
		return fontNames;
	}

	public void setFontNames(String[] fontNames)
	{
		this.fontNames = fontNames;
	}
}