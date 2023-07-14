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
	 * @return ���������� ��������������
	 */
	public String[] getSelectedIds()
	{
		return selectedIds;
	}

	/**
	 *  ���������� �������������� ��� ���������
	 * @param selectedIds ��������������
	 */
	public void setSelectedIds(String[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	//TODO ����� ��??
	public void clearSelection()
	{
		selectedIds = new String[0];
	}

	//TODO ����� ��??
	public Long getSelectedId()
	{
		if (selectedIds.length == 0)
		{
			return null;
		}
		return Long.valueOf(selectedIds[0]);
	}

	/**
	 * ���������� ������ ������
	 * @param data ������ ������
	 */
	public void setData(List<T> data)
	{
		this.data = data;
	}

	/**
	 * @return ������ ������
	 */
	public List<T> getData()
	{
		return data;
	}
}
