package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.BaseColor;

import java.awt.*;

/**
 * ����� ������ ������
 * @author lepihina
 * @ created 17.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class CellStyle
{
	private BaseColor backgroundColor = BaseColor.WHITE; // ���� ����

	private BaseColor borderColor   = null;  // ���� ������
	private float borderWidthLeft   = 0; // ������� ����� �������
	private float borderWidthRight  = 0; // ������� ������ �������
	private float borderWidthTop    = 0; // ������� ������� �������
	private float borderWidthBottom = 0;// ������� ������ �������

	private float minimumHeight; // ����������� ������ ������
	private float fixedHeight; // ������������ ������ ������

	private Alignment horizontalAlignment = Alignment.left;
	private Alignment verticalAlignment = Alignment.middle;

	/**
	 * ������ �����������
	 */
	public CellStyle()
	{
	}

	/**
	 * ����������� �� ������ ������������� �����
	 * @param cellStyle - ����� ������ ������, �� ������ �������� ��������� ����� �����
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
	 * @return ���� ����
	 */
	public BaseColor getBackgroundColor()
	{
		return backgroundColor;
	}

	/**
	 * @param backgroundColor - ���� ����
	 */
	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = new BaseColor(backgroundColor);
	}

	/**
	 * @param borderWidth - ������� �������
	 */
	public void setBorderWidth(float borderWidth)
	{
		this.borderWidthLeft = borderWidth;
		this.borderWidthRight = borderWidth;
		this.borderWidthTop = borderWidth;
		this.borderWidthBottom = borderWidth;
	}

	/**
	 * @return ������� ����� �������
	 */
	public float getBorderWidthLeft()
	{
		return borderWidthLeft;
	}

	/**
	 * @param borderWidthLeft - ������� ����� �������
	 */
	public void setBorderWidthLeft(float borderWidthLeft)
	{
		this.borderWidthLeft = borderWidthLeft;
	}

	/**
	 * @return ������� ������ �������
	 */
	public float getBorderWidthRight()
	{
		return borderWidthRight;
	}

	/**
	 * @param borderWidthRight - ������� ������ �������
	 */
	public void setBorderWidthRight(float borderWidthRight)
	{
		this.borderWidthRight = borderWidthRight;
	}

	/**
	 * @return ������� ������� �������
	 */
	public float getBorderWidthTop()
	{
		return borderWidthTop;
	}

	/**
	 * @param borderWidthTop - ������� ������� �������
	 */
	public void setBorderWidthTop(float borderWidthTop)
	{
		this.borderWidthTop = borderWidthTop;
	}

	/**
	 * @return ������� ������ �������
	 */
	public float getBorderWidthBottom()
	{
		return borderWidthBottom;
	}

	/**
	 * @param borderWidthBottom - ������� ������ �������
	 */
	public void setBorderWidthBottom(float borderWidthBottom)
	{
		this.borderWidthBottom = borderWidthBottom;
	}

	/**
	 * @return ���� �������
	 */
	public BaseColor getBorderColor()
	{
		return borderColor;
	}

	/**
	 * @param borderColor - ���� �������
	 */
	public void setBorderColor(Color borderColor)
	{
		this.borderColor = new BaseColor(borderColor);
	}

	/**
	 * @return ����������� ������ ������
	 */
	public float getMinimumHeight()
	{
		return minimumHeight;
	}

	/**
	 * @param minimumHeight - ����������� ������ ������
	 */
	public void setMinimumHeight(float minimumHeight)
	{
		this.minimumHeight = minimumHeight;
	}

	/**
	 * @return ������������ ������ ������
	 */
	public float getFixedHeight()
	{
		return fixedHeight;
	}

	/**
	 * @param fixedHeight - ������������ ������ ������
	 */
	public void setFixedHeight(float fixedHeight)
	{
		this.fixedHeight = fixedHeight;
	}

	/**
	 * @return ������������ �� �����������
	 */
	public Alignment getHorizontalAlignment()
	{
		return horizontalAlignment;
	}

	/**
	 * @param horizontalAlignment - ������������ �� �����������
	 */
	public void setHorizontalAlignment(Alignment horizontalAlignment)
	{
		this.horizontalAlignment = horizontalAlignment;
	}

	/**
	 * @return ������������ �� ���������
	 */
	public Alignment getVerticalAlignment()
	{
		return verticalAlignment;
	}

	/**
	 * @param verticalAlignment - ������������ �� ���������
	 */
	public void setVerticalAlignment(Alignment verticalAlignment)
	{
		this.verticalAlignment = verticalAlignment;
	}
}
