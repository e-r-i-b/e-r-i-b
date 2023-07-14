package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @author akrenev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * оболочка для таблицы (таблица + заголовок таблицы)
 */
public class PdfPTableWrapper
{
	private final Paragraph title;
	private final PdfPTable table;

	/**
	 * @param title заголовок таблицы
	 * @param table таблица
	 */
	public PdfPTableWrapper(Paragraph title, PdfPTable table)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.title = title;
		this.table = table;
	}

	/**
	 * @param table таблица
	 */
	public PdfPTableWrapper(PdfPTable table)
	{
		this.title = null;
		this.table = table;
	}

	/**
	 * @return таблица
	 */
	public Paragraph getTitle()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return title;
	}

	/**
	 * @return заголовок таблиц
	 */
	public PdfPTable getTable()
	{
		return table;
	}
}
