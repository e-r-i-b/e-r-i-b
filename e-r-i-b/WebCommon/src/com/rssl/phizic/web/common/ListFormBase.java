package com.rssl.phizic.web.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 19.12.2008
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class ListFormBase<T> extends FilterActionForm
{
	protected String[] selectedIds = new String[]{};
	private List<T> data = new ArrayList<T>();

	/**
	 * @return выделенные идентификаторы
	 */
	public String[] getSelectedIds()
	{
		return selectedIds;
	}

	/**
	 *  Установить идентивикаторы для выделения
	 * @param selectedIds идентификаторы
	 */
	public void setSelectedIds(String[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	//TODO нужен ли??
	public void clearSelection()
	{
		selectedIds = new String[0];
	}

	//TODO нужен ли??
	public Long getSelectedId()
	{
		if (selectedIds.length == 0)
		{
			return null;
		}
		return Long.valueOf(selectedIds[0]);
	}

	/**
	 * Установить данные списка
	 * @param data данные списка
	 */
	public void setData(List<T> data)
	{
		this.data = data;
	}

	/**
	 * @return данные списка
	 */
	public List<T> getData()
	{
		return data;
	}
}
