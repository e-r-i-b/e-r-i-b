package com.rssl.phizic.web.common.socialApi.news;

import com.rssl.phizic.business.news.News;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Rydvanskiy
 * @ created 25.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListNewsForm extends ListFormBase<News>
{
	private String from;
	private String to;

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}
}
