package com.rssl.phizic.web.log;

import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 27.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class DownloadLogFilterBaseForm extends ListFormBase
{
	protected static final String DATESTAMP_FORMAT = "dd.MM.yyyy";

	protected static final String TIMESTAMP_FORMAT = "HH:mm:ss";

	private List<Object[]> list = new ArrayList<Object[]>();

	public List<Object[]> getList()
	{
		return list;
	}

	public void setList(List<Object[]> list)
	{
		this.list = list;
	}
}
