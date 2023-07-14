package com.rssl.phizic.web.struts.forms;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Roshka
 * @ created 11.05.2006
 * @ $Author$
 * @ $Revision$
 */

public class ShowFormBasedListForm extends ActionFormBase
{

	private String name;
	private String[] selectedIds = new String[0];
	private StringBuffer filterHtml;
	private StringBuffer listHtml;
	private String title;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSelectedIds(String[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	public Long getSelectedId()
	{
		if (selectedIds.length > 0)
		{
			return Long.valueOf(selectedIds[0]);
		}
		else
		{
			return null;
		}
	}

	public String[] getSelectedIds()
	{
		return selectedIds;
	}

	public StringBuffer getFilterHtml()
	{
		return filterHtml;
	}

	public void setFilterHtml(StringBuffer html)
	{
		this.filterHtml = html;
	}

	public StringBuffer getListHtml()
	{
		return listHtml;
	}

	public void setListHtml(StringBuffer listHtml)
	{
		this.listHtml = listHtml;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

}