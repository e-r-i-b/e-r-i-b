package com.rssl.phizic.operations.news.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourcesBaseService;
import com.rssl.phizic.business.news.News;
import com.rssl.phizic.business.news.NewsService;
import com.rssl.phizic.business.news.locale.NewsResources;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * @author koptyaev
 * @ created 01.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditNewsResourcesOperation extends EditDictionaryEntityOperationBase implements EditLanguageResourcesOperation<NewsResources,Long>
{
	private static final NewsService NEWS_SERVICE = new NewsService();
	private static final LanguageResourcesBaseService<NewsResources> NEWS_RESOURCES_SERVICE = new LanguageResourcesBaseService<NewsResources>(NewsResources.class);
	private static final MultiInstanceEribLocaleService LOCALE_SERVICE = new MultiInstanceEribLocaleService();

	private ERIBLocale locale;
	private NewsResources newsResources;

	/**
	 * Инициализировать операцию данными основной сущности и локали
	 * @param id идентификатор базовой сущности
	 * @param localeId локаль
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		News news = NEWS_SERVICE.findById(id, getInstanceName());

		if (news == null)
			throw new BusinessLogicException("Событие с id = " + id + " не найдено");

		try
		{
			locale = LOCALE_SERVICE.getById(localeId,null);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		newsResources = NEWS_RESOURCES_SERVICE.findResById(news.getUuid(), localeId, getInstanceName());
		if (newsResources == null)
		{
			newsResources = new NewsResources();
			newsResources.setUuid(news.getUuid());
			newsResources.setLocaleId(localeId);
		}
	}

	public void doSave() throws BusinessException, BusinessLogicException
	{
		NEWS_RESOURCES_SERVICE.addOrUpdate(newsResources, getInstanceName());
	}

	public NewsResources getEntity()
	{
		return newsResources;
	}

	/**
	 * @return локаль
	 */
	public ERIBLocale getLocale()
	{
		return locale;
	}
}
