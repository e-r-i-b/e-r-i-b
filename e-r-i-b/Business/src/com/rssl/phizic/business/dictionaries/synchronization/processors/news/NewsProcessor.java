package com.rssl.phizic.business.dictionaries.synchronization.processors.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.news.News;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.HashSet;

/**
 * @author akrenev
 * @ created 24.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей событий
 */

public class NewsProcessor extends ProcessorBase<News>
{
	private static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "uuid";

	@Override
	protected Class<News> getEntityClass()
	{
		return News.class;
	}

	@Override
	protected News getNewEntity()
	{
		News news = new News();
		news.setDepartments(new HashSet<String>());
		return news;
	}

	@Override
	protected News getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(News.class).add(Expression.eq(MULTI_BLOCK_RECORD_ID_FIELD_NAME, uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(News source, News destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
		destination.setTitle(source.getTitle());
		destination.setNewsDate(source.getNewsDate());
		destination.setText(source.getText());
		destination.setShortText(source.getShortText());
		destination.setState(source.getState());
		destination.setType(source.getType());
		destination.setImportant(source.getImportant());
		destination.getDepartments().clear();
		destination.getDepartments().addAll(source.getDepartments());
		destination.setStartPublishDate(source.getStartPublishDate());
		destination.setEndPublishDate(source.getEndPublishDate());
	}
}
