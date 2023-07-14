package com.rssl.phizic.utils.chart;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.RectangleReadOnly;

/**
 * @author lepihina
 * @ created 10.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * ¬озможные ориентации страницы в PDF файле
 */
public enum DocumentOrientation
{
	VERTICAL(PageSize.A4, new Rectangle(10, 10, 575, 820)),
	VERTICAL_575_800(new Rectangle(595,842), new Rectangle(10, 10, 575, 800)),
	HORIZONTAL(PageSize.A4.rotate(), new Rectangle(10, 10, 820, 575));

	private final Rectangle pageSize;
	private final Rectangle boxSize;

	DocumentOrientation(Rectangle pageSize, Rectangle boxSize)
	{
		this.pageSize = pageSize;
		this.boxSize = boxSize;
	}

	/**
	 * @return размер страницы
	 */
	public Rectangle getPageSize()
	{
		return pageSize;
	}

	/**
	 * @return пол€ рабочей области
	 */
	public Rectangle getBoxSize()
	{
		return boxSize;
	}
}
