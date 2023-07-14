package com.rssl.phizic.business.dictionaries.pfp.products.types;

import java.util.List;


/**
 * @author akrenev
 * @ created 26.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Параметры таблицы
 */

public class TableParameters
{
	private List<TableColumn> columns;

	/**
	 * @return названия столбцов
	 */
	public List<TableColumn> getColumns()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return columns;
	}

	/**
	 * задать названия столбцов
	 * @param columns названия
	 */
	public void setColumns(List<TableColumn> columns)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.columns = columns;
	}

	/**
	 * очистка параматров таблицы
	 */
	public void clear()
	{
		columns.clear();
	}
}
