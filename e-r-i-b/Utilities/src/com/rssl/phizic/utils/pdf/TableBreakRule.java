package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;

import java.util.List;

/**
 * @author akrenev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������� �������� ������ �� ����� ��������
 */
public enum TableBreakRule
{
	/**
	 * �������� �������� ��� ������� �������
	 */
	wholeTableInPage
			{
				float getTableHeight(PdfPTable table) throws DocumentException
				{
					return table.getTotalHeight();
				}
			},
	/**
	 * ���� ������� 2 ������, �� ������ �� ������
	 */
	twoLineMinimumInPage
			{
				float getTableHeight(PdfPTable table) throws DocumentException
				{
					List<PdfPRow> rowArrayList = table.getRows();
					if (rowArrayList.size() < 3)
						return table.getTotalHeight();

					return rowArrayList.get(0).getMaxHeights() + rowArrayList.get(1).getMaxHeights();
				}
			};

	private static final int INDENT = 60;

	float getTableHeight(PdfPTable table) throws DocumentException
	{
		throw new UnsupportedOperationException("�� ������ ������� ��� �������� �������.");
	}

	private float getMinTableHeight(DocumentWriter documentWriter, PdfPTableWrapper tableWrapper) throws DocumentException
	{
		Paragraph title = tableWrapper.getTitle();
		float titleHeight = 0;
		if (title != null)
			titleHeight = getTableTitleHeight(documentWriter, title);

		PdfPTable table = tableWrapper.getTable();
		documentWriter.newPage();
		documentWriter.add(table);
		float tableHeight = getTableHeight(table);

		return titleHeight + tableHeight;
	}

	boolean needNewPage(DocumentWriter documentWriter, PdfPTableWrapper tableWrapper) throws DocumentException
	{

		DocumentWriter templateWriter = null;
		try
		{
			templateWriter = documentWriter.getCopy();
			float minTableHeight = getMinTableHeight(templateWriter, tableWrapper);
			return documentWriter.getVerticalPosition() - INDENT < minTableHeight && minTableHeight < documentWriter.getOrientation().getBoxSize().getHeight();
		}
		finally
		{
			if (templateWriter != null)
				templateWriter.clear();
		}
	}

	private float getTableTitleHeight(DocumentWriter documentWriter, Paragraph paragraph) throws DocumentException
	{
		documentWriter.newPage();
		//�������� ���������� �� ����� �������� �� ���������� ���������
		float beforeAddingPosition = documentWriter.getVerticalPosition();
		documentWriter.add(paragraph);
		//�������� ���������� �� ����� �������� ����� ���������� ���������
		float afterAddingPosition = documentWriter.getVerticalPosition();
		return beforeAddingPosition - afterAddingPosition;
	}

}
