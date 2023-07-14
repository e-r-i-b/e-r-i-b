package com.rssl.phizic.business.dictionaries.synchronization.processors.locale.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.ResourcesProcessorBase;
import com.rssl.phizic.business.news.locale.NewsResources;

/**
 * @author koptyaev
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class NewsResourcesProcessor extends ResourcesProcessorBase<NewsResources>
{
	@Override
	protected Class<NewsResources> getEntityClass()
	{
		return NewsResources.class;
	}

	@Override
	protected NewsResources getNewEntity()
	{
		return new NewsResources();
	}

	@Override
	protected void update(NewsResources source, NewsResources destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setTitle(source.getTitle());
		destination.setShortText(source.getShortText());
		destination.setText(source.getText());
	}
}
