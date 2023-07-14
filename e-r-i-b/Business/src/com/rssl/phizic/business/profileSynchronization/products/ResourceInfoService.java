package com.rssl.phizic.business.profileSynchronization.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author lepihina
 * @ created 28.05.14
 * $Author$
 * $Revision$
 *
 * —ервис дл€ работы с настройками отображени€ и видимости продуктов (дл€ хранени€ в централизованном хранилище)
 */
public class ResourceInfoService
{
	private static final String QUERY_PREFIX = ResourceInfo.class.getName();

	/**
	 * ѕолучает настройками отображени€ и видимости продуктов по заданному классу продукта
	 * @param productClass - класс продукта
	 * @param loginId - идентификатор клиента
	 * @return настройки отображени€ и видимости продуктов
	 * @throws BusinessException
	 */
	public List<ResourceInfo> getResourceInfo(final Class productClass, final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ResourceInfo>>()
			{
				public List<ResourceInfo> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + ".getResourceInfo." + productClass.getSimpleName());
					query.setParameter("loginId", loginId);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
