package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.BaseColor;

import java.awt.*;

/**
 * Стиль шрифта
 * @author lepihina
 * @ created 16.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class FontStyle
{
	private float size; // размер шрифта
	private int style; // оформление шрифта (например, жирный, подчеркнутый)
	private BaseColor color; // цвет
	private String fontName; //имя шрифта

	/**
	 * @param fontStyle - стиль, на основе которого создается новый стиль шрифта
	 */
	public FontStyle(FontStyle fontStyle)
	{
		this.size = fontStyle.getSize();
		this.style = fontStyle.getStyle();
		this.color = fontStyle.getColor();
		this.fontName = fontStyle.getFontName();
	}

	/**
	 * @param size - размер шрифта
	 */
	public FontStyle(float size)
	{
		this.size = size;
		this.style = FontDecoration.normal.getCode();
		this.color = new BaseColor(Color.BLACK);
	}

	/**
	 * @param size - размер шрифта
	 * @param style - оформление шрифта
	 */
	public FontStyle(float size, FontDecoration style)
	{
		this.size = size;
		this.style = style.getCode();
		this.color = new BaseColor(Color.BLACK);
	}

	/**
	 * @param size - размер шрифта
	 * @param style - оформление шрифта
	 * @param fontName - название шрифта
	 */
	public FontStyle(float size, FontDecoration style, String fontName)
	{
		this.size = size;
		this.style = style.getCode();
		this.color = new BaseColor(Color.BLACK);
		this.fontName = fontName;
	}

	/**
	 * @param size - размер шрифта
	 * @param style - оформление шрифта
	 * @param color - цвет
	 */
	public FontStyle(float size, FontDecoration style, Color color)
	{
		this.size = size;
		this.style = style.getCode();
		this.color = new BaseColor(color);
	}

	/**
	 * @param size - размер шрифта
	 * @param style - оформление шрифта
	 * @param color - цвет
	 * @param fontName - название шрифта
	 */
	public FontStyle(float size, FontDecoration style, Color color,  String fontName)
	{
		this.size = size;
		this.style = style.getCode();
		this.color = new BaseColor(color);
		this.fontName = fontName;
	}

	/**
	 * @return размер шрифта
	 */
	public float getSize()
	{
		return size;
	}

	/**
	 * @param size - размер шрифта
	 */
	public void setSize(float size)
	{
		this.size = size;
	}

	/**
	 * @return оформление шрифта
	 */
	public int getStyle()
	{
		return style;
	}

	/**
	 * @param style - оформление шрифта
	 */
	public void setStyle(int style)
	{
		this.style = style;
	}

	/**
	 * @return цвет
	 */
	public BaseColor getColor()
	{
		return color;
	}

	/**
	 * @param color - цвет
	 */
	public void setColor(Color color)
	{
		this.color = new BaseColor(color);
	}

	public String getFontName()
	{
		return fontName;
	}

	public void setFontName(String fontName)
	{
		this.fontName = fontName;
	}
}
