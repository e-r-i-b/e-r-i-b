package com.rssl.phizic.operations.news;

import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourcesBaseService;
import com.rssl.phizic.business.news.NewsState;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.news.News;
import com.rssl.phizic.business.news.NewsService;
import com.rssl.phizic.business.news.locale.NewsResources;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import org.hibernate.Session;

/**
 * User: Zhuravleva
 * Date: 20.12.2006
 * Time: 15:59:23
 */
public class RemoveNewsOperation extends OperationBase implements RemoveEntityOperation
{
	protected static final NewsService newsService = new NewsService();
	private static final LanguageResourcesBaseService<NewsResources> NEWS_RESOURCES_SERVICE = new LanguageResourcesBaseService<NewsResources>(NewsResources.class);

	protected News news;

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	public void initialize(Long newsId) throws BusinessException, BusinessLogicException
	{
		news = newsService.findById(newsId, getInstanceName());

		if(news == null)
			throw new BusinessLogicException("Событие с id " + newsId + " не найдено.");

		if(news.getState() == NewsState.PUBLISHED)
			throw new BusinessLogicException("Данная операция доступна только для события со статусом \"Новое\" и \"Снято с публикации\".");

	}

	public Object getEntity()
	{
		return news;
	}

	protected void doRemove() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					NEWS_RESOURCES_SERVICE.removeRes(news.getUuid(), getInstanceName());
					newsService.remove(news, getInstanceName());
					return null;
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}

	}

	public void remove() throws BusinessException, BusinessLogicException
	{	
		doRemove();
		MultiBlockModeDictionaryHelper.updateDictionary(News.class);
	}
}
