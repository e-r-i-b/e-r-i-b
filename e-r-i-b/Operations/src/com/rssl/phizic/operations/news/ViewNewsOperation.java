package com.rssl.phizic.operations.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.locale.MultiLocaleSimpleService;
import com.rssl.phizic.business.news.News;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * @author lukina
 * @ created 18.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class ViewNewsOperation extends OperationBase implements ViewEntityOperation
{
	private static final MultiLocaleSimpleService simpleService = new MultiLocaleSimpleService();


	private News news;
	public void initialize(Long newsId) throws BusinessException, BusinessLogicException
	{
		news = simpleService.findById(News.class, newsId, getInstanceName());
		if(news == null)
			throw new ResourceNotFoundBusinessException("—обыте с id " + newsId + " не найдено.", News.class);
	}

	public News getEntity()
	{
		return news;
	}
}
