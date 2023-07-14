package com.rssl.phizic.business.dictionaries.pfp.products;

import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumn;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumnComparator;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author akrenev
 * @ created 12.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������� �������� � ��������� ����
 */

public class TableViewParameters
{
	private boolean useIcon;
	private Map<TableColumn, String> columns = new TreeMap<TableColumn, String>(new TableColumnComparator());

	/**
	 * @return ������������ �� ������
	 */
	public boolean isUseIcon()
	{
		return useIcon;
	}

	/**
	 * ������ ������� ������������� ������
	 * @param useIcon ������������ �� ������
	 */
	public void setUseIcon(boolean useIcon)
	{
		this.useIcon = useIcon;
	}

	/**
	 * @return ��������� ��������
	 */
	public Map<TableColumn, String> getColumns()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return columns;
	}

	/**
	 * ������ ��������� ��������
	 * @param columns ��������� ��������
	 */
	public void setColumns(Map<TableColumn, String> columns)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.columns = columns;
	}
}
