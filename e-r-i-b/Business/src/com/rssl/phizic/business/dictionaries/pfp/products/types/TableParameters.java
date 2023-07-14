package com.rssl.phizic.business.dictionaries.pfp.products.types;

import java.util.List;


/**
 * @author akrenev
 * @ created 26.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������
 */

public class TableParameters
{
	private List<TableColumn> columns;

	/**
	 * @return �������� ��������
	 */
	public List<TableColumn> getColumns()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return columns;
	}

	/**
	 * ������ �������� ��������
	 * @param columns ��������
	 */
	public void setColumns(List<TableColumn> columns)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.columns = columns;
	}

	/**
	 * ������� ���������� �������
	 */
	public void clear()
	{
		columns.clear();
	}
}
