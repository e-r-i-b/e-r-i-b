package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @author akrenev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� ������� (������� + ��������� �������)
 */
public class PdfPTableWrapper
{
	private final Paragraph title;
	private final PdfPTable table;

	/**
	 * @param title ��������� �������
	 * @param table �������
	 */
	public PdfPTableWrapper(Paragraph title, PdfPTable table)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.title = title;
		this.table = table;
	}

	/**
	 * @param table �������
	 */
	public PdfPTableWrapper(PdfPTable table)
	{
		this.title = null;
		this.table = table;
	}

	/**
	 * @return �������
	 */
	public Paragraph getTitle()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return title;
	}

	/**
	 * @return ��������� ������
	 */
	public PdfPTable getTable()
	{
		return table;
	}
}
