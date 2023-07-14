package com.rssl.phizic.web.ermb.migration.list.start;

import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * Форма запуска миграции МБК/МБВ в ЕРИБ
 * @author Puzikov
 * @ created 05.12.13
 * @ $Author$
 * @ $Revision$
 */

public class StartMigrationForm extends ActionFormBase
{
	private List<Segment> data;
	private String[] selectedData;
	private String status;

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public List<Segment> getData()
	{
		return data;
	}

	public void setData(List<Segment> data)
	{
		this.data = data;
	}

	public String[] getSelectedData()
	{
		return selectedData;
	}

	public void setSelectedData(String[] selectedData)
	{
		this.selectedData = selectedData;
	}
}
