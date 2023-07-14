package com.rssl.phizic.web.common.client.news;

import com.rssl.phizic.business.news.News;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author lukina
 * @ created 18.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class ViewNewsForm extends EditFormBase
{
	private News news;

	public News getNews()
	{
		return news;
	}

	public void setNews(News news)
	{
		this.news = news;
	}
}
