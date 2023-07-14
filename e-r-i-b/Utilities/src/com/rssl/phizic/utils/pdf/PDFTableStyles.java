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
	// толщина границы в таблице
	public static final float TABLE_BORDER_WIDTH = 0.2f;

	// ќсновные стили дл€ шрифтов таблицы
	public static final FontStyle BOLD_FONT = new FontStyle(11, FontDecoration.bold);
	public static final FontStyle TEXT_FONT = new FontStyle(11);

	// ќсновные стили дл€ параграфов таблицы
	public static final ParagraphStyle BASE_PARAGRAPH = new ParagraphStyle(14f, 0f, 11f, 0f);

	// —тили дл€ €чеек таблицы
	public static final CellStyle CELL_STYLE = new CellStyle(); // обычные €чейки таблицы
	public static final CellStyle QUARTER_STYLE = new CellStyle(); // обычные €чейки таблицы
	public static final CellStyle TH_CELL_STYLE = new CellStyle(); // €чейки заголовка таблицы

	public static final CellStyle CELL_TD_ALIGNMENT_CENTER = new CellStyle(); // выравнивание в €чейке по центру
	public static final CellStyle CELL_TD_ALIGNMENT_RIGHT = new CellStyle(); // выравнивание в €чейке по центру

	public static final CellStyle CELL_WITHOUT_BORDER = new CellStyle(); // €чейка таблицы без границы
	public static final CellStyle CELL_BORDER_TOP = new CellStyle(); // у €чейки есть только нижн€€ граница
	public static final CellStyle CELL_BORDER_TOP_SIDES = new CellStyle(); // у €чейки есть верхн€€ и боковые границы
	public static final CellStyle CELL_BORDER_TOP_RIGHT = new CellStyle(); // у €чейки есть верхн€€ и права€ границы
	public static final CellStyle CELL_BORDER_TOP_LEFT = new CellStyle(); // у €чейки есть верхн€€ и лева€ границы
	public static final CellStyle CELL_BORDER_SIDES = new CellStyle(); // у €чейки есть и лева€ и права€ границы
	public static final CellStyle CELL_BORDER_RIGHT = new CellStyle(); // у €чейки есть только права€ граница
	public static final CellStyle CELL_BORDER_LEFT = new CellStyle(); // у €чейки есть только лева€ граница
	public static final CellStyle CELL_BORDER_BOTTOM = new CellStyle(); // у €чейки есть только нижн€€ граница
	public static final CellStyle CELL_BORDER_BOTTOM_SIDES = new CellStyle(); // у €чейки есть нижн€€ и боковые границы
	public static final CellStyle CELL_BORDER_BOTTOM_RIGHT = new CellStyle(); // у €чейки есть нижн€€ и права€ границы
	public static final CellStyle CELL_BORDER_BOTTOM_LEFT = new CellStyle(); // у €чейки есть нижн€€ и лева€ границы

	static
	{
		CELL_STYLE.setBorderWidthBottom(TABLE_BORDER_WIDTH); // устанавлтваем только нижнюю границу
		CELL_STYLE.setBorderColor(Color.GRAY); // цвет нижней границы

		QUARTER_STYLE.setBorderWidthBottom(TABLE_BORDER_WIDTH); // устанавлтваем только нижнюю границу
		QUARTER_STYLE.setBorderColor(Color.GRAY); // цвет нижней границы
		QUARTER_STYLE.setHorizontalAlignment(Alignment.center); // выравнивание по центру

		CELL_TD_ALIGNMENT_CENTER.setBorderWidthBottom(TABLE_BORDER_WIDTH); // устанавлтваем только нижнюю границу
		CELL_TD_ALIGNMENT_CENTER.setBorderColor(Color.GRAY); // цвет нижней границы
		CELL_TD_ALIGNMENT_CENTER.setHorizontalAlignment(Alignment.center); // выравнивание по центру

		CELL_TD_ALIGNMENT_RIGHT.setBorderWidthBottom(TABLE_BORDER_WIDTH); // устанавлтваем только нижнюю границу
		CELL_TD_ALIGNMENT_RIGHT.setBorderColor(Color.GRAY); // цвет нижней границы
		CELL_TD_ALIGNMENT_RIGHT.setHorizontalAlignment(Alignment.right); // выравнивание по центру

		CELL_WITHOUT_BORDER.setBorderWidth(0); // убираем границы в таблице

		TH_CELL_STYLE.setBorderWidthBottom(TABLE_BORDER_WIDTH); // устанавлтваем только нижнюю границу
		TH_CELL_STYLE.setBorderColor(Color.GRAY); // цвет нижней границы
		TH_CELL_STYLE.setBackgroundColor(new Color(242, 242, 242)); // цвет фона светло-серый
		TH_CELL_STYLE.setMinimumHeight(30f); // минимальна€ высота €чейки

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
