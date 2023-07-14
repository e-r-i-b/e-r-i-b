package com.rssl.phizic.web.news;

import com.rssl.auth.csa.front.business.news.News;
import org.apache.struts.action.ActionForm;

/**
 * @author basharin
 * @ created 06.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoginViewNewsForm extends ActionForm
{
	private News news;
	private Long id;

	public News getNews()
	{
		return news;
	}

	public void setNews(News news)
	{
		this.news = news;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
