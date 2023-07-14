package com.rssl.phizic.captcha;

/**
 * Визуальные настройки кода каптчи.
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
	 * Имена шрифтов для создания каптчи.
	 */
	private String[] fontNames;
	/**
	 * Цвет символов и линий.
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