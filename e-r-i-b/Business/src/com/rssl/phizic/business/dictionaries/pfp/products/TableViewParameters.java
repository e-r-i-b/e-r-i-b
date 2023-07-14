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
 * Параметры отображения продукта в табличном виде
 */

public class TableViewParameters
{
	private boolean useIcon;
	private Map<TableColumn, String> columns = new TreeMap<TableColumn, String>(new TableColumnComparator());

	/**
	 * @return использовать ли иконку
	 */
	public boolean isUseIcon()
	{
		return useIcon;
	}

	/**
	 * задать признак использования иконки
	 * @param useIcon использовать ли иконку
	 */
	public void setUseIcon(boolean useIcon)
	{
		this.useIcon = useIcon;
	}

	/**
	 * @return пераметры столбцов
	 */
	public Map<TableColumn, String> getColumns()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return columns;
	}

	/**
	 * задать пераметры столбцов
	 * @param columns пераметры столбцов
	 */
	public void setColumns(Map<TableColumn, String> columns)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.columns = columns;
	}
}
