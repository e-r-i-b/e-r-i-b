package com.rssl.auth.csa.front.business.news;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * сервис для работы с новостями в CSA
 * @author basharin
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */

public final class NewsCSAService
{
	private static final String CSA_INSTANCE_NAME = "CSA2";
	private static final int CSA_LOGIN_PAGE_NEWS_AMOUNT = 2;
	/**
	 * получение новостей для главной страницы
	 * @param regionTB тербанк для которого отображаем новость
	 * @return список новостей
	 */
	public List<News> getAuthNews(final String regionTB) throws Exception
	{
		return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<List<News>>()
		{
			public List<News> run(Session session)
			{
				Query query = session.getNamedQuery(News.class.getName()+".getListNews");
				query.setParameter("regionTB", regionTB);
				query.setMaxResults(CSA_LOGIN_PAGE_NEWS_AMOUNT);
				return query.list();
			}
		});
	}

	/**
	 * Возвращает новость
	 * @param id идентификатор новости
	 * @return новость
	 * @throws Exception
	 */
	public News getOneNews(final Long id) throws Exception
	{
		return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<News>()
		{
			public News run(Session session)
			{
				Query query = session.getNamedQuery(News.class.getName()+".getNewsById");
				query.setParameter("news_id", id);
				return (News) query.uniqueResult();
			}
		});
	}
}

