package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author gladishev
 * @ created 03.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class DefaultAccessSchemeService
{
	private static final String QUERY_PREFIX = DefaultAccessSchemeService.class.getName() + ".";
	private static SimpleService simpleService = new SimpleService();

	/**
	 * Добавить запись дефолтной схемы типа клиента
	 * @param defaultAccessScheme - дефолная схема
	 * @return дефолная схема
	 */
    public DefaultAccessScheme addOrUpdate(DefaultAccessScheme defaultAccessScheme) throws BusinessException
    {
	    return simpleService.addOrUpdate(defaultAccessScheme);
    }

	/**
	 * @return список дефолных схем
	 */
    public List<DefaultAccessScheme> getAll() throws BusinessException
    {
	    return simpleService.getAll(DefaultAccessScheme.class);
    }

	/**
	 * Возвращает дефолтную схему доступа по типу клиента и департаменту
	 * @param creationType тип клиента
	 * @param departmentTb код тербанка клиента
	 * @return дефолную схему
	 */
    public AccessScheme findByCreationTypeAndDepartment(final DefaultSchemeType creationType, final String departmentTb) throws BusinessException
    {
	    try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<AccessScheme>()
			{
				public AccessScheme run ( Session session ) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX+"findSchemeByTypeAndDepartment");
					query.setParameter("creationType", creationType.name());
					query.setParameter("departmentTb", StringUtils.defaultIfEmpty(departmentTb, "NULL"));
					
					return (AccessScheme) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
    }

	/**
	 * Удалить запись
	 * @param scheme - удаляемая схема
	 * @throws BusinessException
	 */
	public void remove(DefaultAccessScheme scheme) throws BusinessException
	{
		simpleService.remove(scheme);
	}
}
