package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.BaseColor;

import java.awt.*;

/**
 * ����� ������
 * @author lepihina
 * @ created 16.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class FontStyle
{
	private float size; // ������ ������
	private int style; // ���������� ������ (��������, ������, ������������)
	private BaseColor color; // ����
	private String fontName; //��� ������

	/**
	 * @param fontStyle - �����, �� ������ �������� ��������� ����� ����� ������
	 */
	public FontStyle(FontStyle fontStyle)
	{
		this.size = fontStyle.getSize();
		this.style = fontStyle.getStyle();
		this.color = fontStyle.getColor();
		this.fontName = fontStyle.getFontName();
	}

	/**
	 * @param size - ������ ������
	 */
	public FontStyle(float size)
	{
		this.size = size;
		this.style = FontDecoration.normal.getCode();
		this.color = new BaseColor(Color.BLACK);
	}

	/**
	 * @param size - ������ ������
	 * @param style - ���������� ������
	 */
	public FontStyle(float size, FontDecoration style)
	{
		this.size = size;
		this.style = style.getCode();
		this.color = new BaseColor(Color.BLACK);
	}

	/**
	 * @param size - ������ ������
	 * @param style - ���������� ������
	 * @param fontName - �������� ������
	 */
	public FontStyle(float size, FontDecoration style, String fontName)
	{
		this.size = size;
		this.style = style.getCode();
		this.color = new BaseColor(Color.BLACK);
		this.fontName = fontName;
	}

	/**
	 * @param size - ������ ������
	 * @param style - ���������� ������
	 * @param color - ����
	 */
	public FontStyle(float size, FontDecoration style, Color color)
	{
		this.size = size;
		this.style = style.getCode();
		this.color = new BaseColor(color);
	}

	/**
	 * @param size - ������ ������
	 * @param style - ���������� ������
	 * @param color - ����
	 * @param fontName - �������� ������
	 */
	public FontStyle(float size, FontDecoration style, Color color,  String fontName)
	{
		this.size = size;
		this.style = style.getCode();
		this.color = new BaseColor(color);
		this.fontName = fontName;
	}

	/**
	 * @return ������ ������
	 */
	public float getSize()
	{
		return size;
	}

	/**
	 * @param size - ������ ������
	 */
	public void setSize(float size)
	{
		this.size = size;
	}

	/**
	 * @return ���������� ������
	 */
	public int getStyle()
	{
		return style;
	}

	/**
	 * @param style - ���������� ������
	 */
	public void setStyle(int style)
	{
		this.style = style;
	}

	/**
	 * @return ����
	 */
	public BaseColor getColor()
	{
		return color;
	}

	/**
	 * @param color - ����
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
