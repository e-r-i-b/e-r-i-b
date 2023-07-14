package com.rssl.phizic.captcha;

/**
 * Настройки код каптчи.
 *
 * @author bogdanov
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */

class CaptchaCodeConfig
{
	static final String CHARS_KEY = "com.rssl.captcha.code.chars";
	static final String MIN_LENGTH_KEY = "com.rssl.captcha.code.minLength";
	static final String MAX_LENGTH_KEY = "com.rssl.captcha.code.maxLength";
	static final String WIDTH_KEY = "com.rssl.captcha.code.width";
	static final String HEIGHT_KEY = "com.rssl.captcha.code.height";
	static final String LEFT_KEY = "com.rssl.captcha.code.left";
	static final String TOP_KEY = "com.rssl.captcha.code.top";

	/**
	 * Список разрешенных символов.
	 */
	private char[] chars;
	/**
	 * Список разрешенных символов в строчных буквах.
	 */
	private char[] lowerCaseChars;
	/**
	 * Минимальная длина кода
	 */
	private int minLength;
	/**
	 * Максимальная длина кода.
	 */
	private int maxLength;

	/**
	 * Ширина окна для вывода каптчи.
	 */
	private int width;
	/**
	 * Высота окна для вывода каптчи.
	 */
	private int height;
	/**
	 * Положение верха окна для вывода каптчи.
	 */
	private int top;
	/**
	 * Положение окна слева.
	 */
	private int left;

	public char[] getChars()
	{
		return chars;
	}

	public char[] getLowerCaseChars()
	{
		return lowerCaseChars;
	}

	public void setChars(char[] chars)
	{
		this.chars = chars;
		lowerCaseChars = new char[chars.length]; 
		for (int i = 0; i < chars.length; i++)
		{
			lowerCaseChars[i] = Character.toLowerCase(chars[i]);
		}
	}

	public int getMaxLength()
	{
		return maxLength;
	}

	public void setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
	}

	public int getMinLength()
	{
		return minLength;
	}

	public void setMinLength(int minLength)
	{
		this.minLength = minLength;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getLeft()
	{
		return left;
	}

	public void setLeft(int left)
	{
		this.left = left;
	}

	public int getTop()
	{
		return top;
	}

	public void setTop(int top)
	{
		this.top = top;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}
}
