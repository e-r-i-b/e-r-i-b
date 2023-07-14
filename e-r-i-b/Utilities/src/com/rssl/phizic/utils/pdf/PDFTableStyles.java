package com.rssl.phizic.utils.pdf;

import java.awt.*;

/**
 * @author lepihina
 * @ created 30.08.13
 * @ $Author$
 * @ $Revision$
 */
public class PDFTableStyles
{
	// ������� ������� � �������
	public static final float TABLE_BORDER_WIDTH = 0.2f;

	// �������� ����� ��� ������� �������
	public static final FontStyle BOLD_FONT = new FontStyle(11, FontDecoration.bold);
	public static final FontStyle TEXT_FONT = new FontStyle(11);

	// �������� ����� ��� ���������� �������
	public static final ParagraphStyle BASE_PARAGRAPH = new ParagraphStyle(14f, 0f, 11f, 0f);

	// ����� ��� ����� �������
	public static final CellStyle CELL_STYLE = new CellStyle(); // ������� ������ �������
	public static final CellStyle QUARTER_STYLE = new CellStyle(); // ������� ������ �������
	public static final CellStyle TH_CELL_STYLE = new CellStyle(); // ������ ��������� �������

	public static final CellStyle CELL_TD_ALIGNMENT_CENTER = new CellStyle(); // ������������ � ������ �� ������
	public static final CellStyle CELL_TD_ALIGNMENT_RIGHT = new CellStyle(); // ������������ � ������ �� ������

	public static final CellStyle CELL_WITHOUT_BORDER = new CellStyle(); // ������ ������� ��� �������
	public static final CellStyle CELL_BORDER_TOP = new CellStyle(); // � ������ ���� ������ ������ �������
	public static final CellStyle CELL_BORDER_TOP_SIDES = new CellStyle(); // � ������ ���� ������� � ������� �������
	public static final CellStyle CELL_BORDER_TOP_RIGHT = new CellStyle(); // � ������ ���� ������� � ������ �������
	public static final CellStyle CELL_BORDER_TOP_LEFT = new CellStyle(); // � ������ ���� ������� � ����� �������
	public static final CellStyle CELL_BORDER_SIDES = new CellStyle(); // � ������ ���� � ����� � ������ �������
	public static final CellStyle CELL_BORDER_RIGHT = new CellStyle(); // � ������ ���� ������ ������ �������
	public static final CellStyle CELL_BORDER_LEFT = new CellStyle(); // � ������ ���� ������ ����� �������
	public static final CellStyle CELL_BORDER_BOTTOM = new CellStyle(); // � ������ ���� ������ ������ �������
	public static final CellStyle CELL_BORDER_BOTTOM_SIDES = new CellStyle(); // � ������ ���� ������ � ������� �������
	public static final CellStyle CELL_BORDER_BOTTOM_RIGHT = new CellStyle(); // � ������ ���� ������ � ������ �������
	public static final CellStyle CELL_BORDER_BOTTOM_LEFT = new CellStyle(); // � ������ ���� ������ � ����� �������

	static
	{
		CELL_STYLE.setBorderWidthBottom(TABLE_BORDER_WIDTH); // ������������� ������ ������ �������
		CELL_STYLE.setBorderColor(Color.GRAY); // ���� ������ �������

		QUARTER_STYLE.setBorderWidthBottom(TABLE_BORDER_WIDTH); // ������������� ������ ������ �������
		QUARTER_STYLE.setBorderColor(Color.GRAY); // ���� ������ �������
		QUARTER_STYLE.setHorizontalAlignment(Alignment.center); // ������������ �� ������

		CELL_TD_ALIGNMENT_CENTER.setBorderWidthBottom(TABLE_BORDER_WIDTH); // ������������� ������ ������ �������
		CELL_TD_ALIGNMENT_CENTER.setBorderColor(Color.GRAY); // ���� ������ �������
		CELL_TD_ALIGNMENT_CENTER.setHorizontalAlignment(Alignment.center); // ������������ �� ������

		CELL_TD_ALIGNMENT_RIGHT.setBorderWidthBottom(TABLE_BORDER_WIDTH); // ������������� ������ ������ �������
		CELL_TD_ALIGNMENT_RIGHT.setBorderColor(Color.GRAY); // ���� ������ �������
		CELL_TD_ALIGNMENT_RIGHT.setHorizontalAlignment(Alignment.right); // ������������ �� ������

		CELL_WITHOUT_BORDER.setBorderWidth(0); // ������� ������� � �������

		TH_CELL_STYLE.setBorderWidthBottom(TABLE_BORDER_WIDTH); // ������������� ������ ������ �������
		TH_CELL_STYLE.setBorderColor(Color.GRAY); // ���� ������ �������
		TH_CELL_STYLE.setBackgroundColor(new Color(242, 242, 242)); // ���� ���� ������-�����
		TH_CELL_STYLE.setMinimumHeight(30f); // ����������� ������ ������

		CELL_BORDER_TOP.setBorderWidthTop(TABLE_BORDER_WIDTH);
		CELL_BORDER_TOP.setBorderColor(Color.LIGHT_GRAY);

		CELL_BORDER_TOP_SIDES.setBorderWidthTop(TABLE_BORDER_WIDTH);
		CELL_BORDER_TOP_SIDES.setBorderWidthRight(TABLE_BORDER_WIDTH);
		CELL_BORDER_TOP_SIDES.setBorderWidthLeft(TABLE_BORDER_WIDTH);
		CELL_BORDER_TOP_SIDES.setBorderColor(Color.LIGHT_GRAY);

		CELL_BORDER_TOP_RIGHT.setBorderWidthTop(TABLE_BORDER_WIDTH);
		CELL_BORDER_TOP_RIGHT.setBorderWidthRight(TABLE_BORDER_WIDTH);
		CELL_BORDER_TOP_RIGHT.setBorderColor(Color.LIGHT_GRAY);

		CELL_BORDER_TOP_LEFT.setBorderWidthTop(TABLE_BORDER_WIDTH);
		CELL_BORDER_TOP_LEFT.setBorderWidthLeft(TABLE_BORDER_WIDTH);
		CELL_BORDER_TOP_LEFT.setBorderColor(Color.LIGHT_GRAY);

		CELL_BORDER_SIDES.setBorderWidthRight(TABLE_BORDER_WIDTH);
		CELL_BORDER_SIDES.setBorderWidthLeft(TABLE_BORDER_WIDTH);
		CELL_BORDER_SIDES.setBorderColor(Color.LIGHT_GRAY);

		CELL_BORDER_RIGHT.setBorderWidthRight(TABLE_BORDER_WIDTH);
		CELL_BORDER_RIGHT.setBorderColor(Color.LIGHT_GRAY);

		CELL_BORDER_LEFT.setBorderWidthLeft(TABLE_BORDER_WIDTH);
		CELL_BORDER_LEFT.setBorderColor(Color.LIGHT_GRAY);

		CELL_BORDER_BOTTOM.setBorderWidthBottom(TABLE_BORDER_WIDTH);
		CELL_BORDER_BOTTOM.setBorderColor(Color.LIGHT_GRAY);

		CELL_BORDER_BOTTOM_SIDES.setBorderWidthBottom(TABLE_BORDER_WIDTH);
		CELL_BORDER_BOTTOM_SIDES.setBorderWidthRight(TABLE_BORDER_WIDTH);
		CELL_BORDER_BOTTOM_SIDES.setBorderWidthLeft(TABLE_BORDER_WIDTH);
		CELL_BORDER_BOTTOM_SIDES.setBorderColor(Color.LIGHT_GRAY);

		CELL_BORDER_BOTTOM_RIGHT.setBorderWidthBottom(TABLE_BORDER_WIDTH);
		CELL_BORDER_BOTTOM_RIGHT.setBorderWidthRight(TABLE_BORDER_WIDTH);
		CELL_BORDER_BOTTOM_RIGHT.setBorderColor(Color.LIGHT_GRAY);

		CELL_BORDER_BOTTOM_LEFT.setBorderWidthBottom(TABLE_BORDER_WIDTH);
		CELL_BORDER_BOTTOM_LEFT.setBorderWidthLeft(TABLE_BORDER_WIDTH);
		CELL_BORDER_BOTTOM_LEFT.setBorderColor(Color.LIGHT_GRAY);
	}
}
