package com.rssl.phizic.business.email;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

import java.util.List;

/**
 * @author lukina
 * @ created 16.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class EmailResourceService
{
	private SimpleService simpleService = new SimpleService();

	public List<EmailResource>  getEmailResources()  throws BusinessException
	{
		return simpleService.getAll(EmailResource.class);
	}

	public EmailResource getEmailMessage( final String key) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(EmailResource.class)
				.add(Expression.eq("key", key));
		return simpleService.findSingle(criteria);
	}

	/**
	 * Возвращает текст email-ресурса
	 * @param key - ключ
	 * @param templateField - название шаблона (theme,plainText,htmlText)
	 * @return текст email-ресурса
	 */
	public Object getEmailTemplate(String key, String templateField) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(EmailResource.class);
		criteria.add(Expression.eq("key", key)).setProjection(Projections.property(templateField));
		return simpleService.findSingle(criteria);
	}
}
