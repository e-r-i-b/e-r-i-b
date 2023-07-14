package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.BaseColor;

import java.awt.*;

/**
 * —тиль €чейки тблицы
 * @author lepihina
 * @ created 17.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class CellStyle
{
	private BaseColor backgroundColor = BaseColor.WHITE; // цвет фона

	private BaseColor borderColor   = null;  // цвет границ
	private float borderWidthLeft   = 0; // толщина левой границы
	private float borderWidthRight  = 0; // толщина правой границы
	private float borderWidthTop    = 0; // толщина верхней границы
	private float borderWidthBottom = 0;// толщина нижней границы

	private float minimumHeight; // минимальна€ высота €чейки
	private float fixedHeight; // фиксирована€ высота €чейки

	private Alignment horizontalAlignment = Alignment.left;
	private Alignment verticalAlignment = Alignment.middle;

	/**
	 * ѕустой конструктор
	 */
	public CellStyle()
	{
	}

	/**
	 *  онструктор на основе существующего стил€
	 * @param cellStyle - стиль €чейки тблицы, на основе которого создаеьс€ новый стиль
	 */
	public CellStyle(CellStyle cellStyle)
	{
		this.backgroundColor = cellStyle.getBackgroundColor();
		this.borderColor = cellStyle.getBorderColor();
		this.borderWidthLeft = cellStyle.getBorderWidthLeft();
		this.borderWidthRight = cellStyle.getBorderWidthRight();
		this.borderWidthTop = cellStyle.getBorderWidthTop();
		this.borderWidthBottom = cellStyle.getBorderWidthBottom();
		this.minimumHeight = cellStyle.getMinimumHeight();
		this.fixedHeight = cellStyle.getFixedHeight();
		this.horizontalAlignment = cellStyle.getHorizontalAlignment();
		this.verticalAlignment = cellStyle.getVerticalAlignment();
	}

	/**
	 * @return цвет фона
	 */
	public BaseColor getBackgroundColor()
	{
		return backgroundColor;
	}

	/**
	 * @param backgroundColor - цвет фона
	 */
	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = new BaseColor(backgroundColor);
	}

	/**
	 * @param borderWidth - толщина границы
	 */
	public void setBorderWidth(float borderWidth)
	{
		this.borderWidthLeft = borderWidth;
		this.borderWidthRight = borderWidth;
		this.borderWidthTop = borderWidth;
		this.borderWidthBottom = borderWidth;
	}

	/**
	 * @return толщина левой границы
	 */
	public float getBorderWidthLeft()
	{
		return borderWidthLeft;
	}

	/**
	 * @param borderWidthLeft - толщина левой границы
	 */
	public void setBorderWidthLeft(float borderWidthLeft)
	{
		this.borderWidthLeft = borderWidthLeft;
	}

	/**
	 * @return толщина правой границы
	 */
	public float getBorderWidthRight()
	{
		return borderWidthRight;
	}

	/**
	 * @param borderWidthRight - толщина правой границы
	 */
	public void setBorderWidthRight(float borderWidthRight)
	{
		this.borderWidthRight = borderWidthRight;
	}

	/**
	 * @return толщина верхней границы
	 */
	public float getBorderWidthTop()
	{
		return borderWidthTop;
	}

	/**
	 * @param borderWidthTop - толщина верхней границы
	 */
	public void setBorderWidthTop(float borderWidthTop)
	{
		this.borderWidthTop = borderWidthTop;
	}

	/**
	 * @return толщина нижней границы
	 */
	public float getBorderWidthBottom()
	{
		return borderWidthBottom;
	}

	/**
	 * @param borderWidthBottom - толщина нижней границы
	 */
	public void setBorderWidthBottom(float borderWidthBottom)
	{
		this.borderWidthBottom = borderWidthBottom;
	}

	/**
	 * @return цвет границы
	 */
	public BaseColor getBorderColor()
	{
		return borderColor;
	}

	/**
	 * @param borderColor - цвет границы
	 */
	public void setBorderColor(Color borderColor)
	{
		this.borderColor = new BaseColor(borderColor);
	}

	/**
	 * @return минимальна€ высота €чейки
	 */
	public float getMinimumHeight()
	{
		return minimumHeight;
	}

	/**
	 * @param minimumHeight - минимальна€ высота €чейки
	 */
	public void setMinimumHeight(float minimumHeight)
	{
		this.minimumHeight = minimumHeight;
	}

	/**
	 * @return фиксирована€ высота €чейки
	 */
	public float getFixedHeight()
	{
		return fixedHeight;
	}

	/**
	 * @param fixedHeight - фиксирована€ высота €чейки
	 */
	public void setFixedHeight(float fixedHeight)
	{
		this.fixedHeight = fixedHeight;
	}

	/**
	 * @return выравнивание по горизонтали
	 */
	public Alignment getHorizontalAlignment()
	{
		return horizontalAlignment;
	}

	/**
	 * @param horizontalAlignment - выравнивание по горизонтали
	 */
	public void setHorizontalAlignment(Alignment horizontalAlignment)
	{
		this.horizontalAlignment = horizontalAlignment;
	}

	/**
	 * @return выравнивание по вертикали
	 */
	public Alignment getVerticalAlignment()
	{
		return verticalAlignment;
	}

	/**
	 * @param verticalAlignment - выравнивание по вертикали
	 */
	public void setVerticalAlignment(Alignment verticalAlignment)
	{
		this.verticalAlignment = verticalAlignment;
	}
}
