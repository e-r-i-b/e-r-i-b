package com.rssl.phizic.business.news.locale;

import com.rssl.phizic.business.news.News;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * @author mihaylov
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Ќовости с локалезависимыми текстовками
 */
public class LocaledNews extends News
{
	private Set<NewsResources> resources;

	@Override
	public String getTitle()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getTitle();
		return super.getTitle();
	}

	@Override
	public String getText()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getText();
		return super.getText();
	}

	@Override
	public String getShortText()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getShortText();
		return super.getShortText();
	}
}
