package com.rssl.phizic.operations.news;

import com.rssl.phizic.business.news.NewsState;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.news.News;
import com.rssl.phizic.business.news.NewsService;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.Calendar;

/**
 * User: Zhuravleva
 * Date: 05.12.2006
 * Time: 20:42:36
 */
public class EditNewsOperation extends OperationBase implements EditEntityOperation
{
	protected static final NewsService newsService = new NewsService();
	protected static final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	private News news;

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	/**
	 * инициализация существующей новостью
	 * @param newsId идентификатор новости
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long newsId) throws BusinessException, BusinessLogicException
	{
		news = newsService.findById(newsId, getInstanceName());
		if(news == null)
			throw new BusinessLogicException("Событие с id " + newsId + " не найдено.");
	}

	/**
	 * Инициализация новой новости или создание копии
	 * @param newsId идентификатор новости с которой копируем данные
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initializeNew(Long newsId) throws BusinessException, BusinessLogicException
	{
		if (newsId == null)
			news = new News();
		else
			initialize(newsId);

		news.setId(null);
		news.setState(NewsState.NEW);
		news.setNewsDate(null);
		news.setUuid(null);
		news.setStartPublishDate(null);
	}

	public News getEntity()
	{
		return news;
	}

	protected void doSave() throws BusinessException
	{
		newsService.addOrUpdate(news, getInstanceName());
	}

	public void save() throws BusinessException
	{
		doSave();
		MultiBlockModeDictionaryHelper.updateDictionary(News.class);
	}

	/**
	 * @return Текущие дата и время сервера БД
	 */
	public static Calendar getCurrentDate() throws BusinessException
	{
		try
		{
			return databaseService.getDatabaseCurSysDate(null);
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка получения текущей системной даты сервера БД", e);
		}
	}
}
