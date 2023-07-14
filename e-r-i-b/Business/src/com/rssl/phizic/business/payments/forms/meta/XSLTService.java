package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Erkin
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для работы с XSLT-бинами
 */
public class XSLTService
{
	private static final String QUERY_PREFIX = "com.rssl.phizic.business.payments.forms.meta.XSLTBean.";

	private static final SimpleService simpleService = new SimpleService();

	public static final String PFR_STATEMENT_NAME = "pfr-statement";

	public static final String PFR_STATEMENT_ATM_NAME = "pfr-statement-atm";

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Создаёт либо обновляет XSLT-бин в БД.
	 * В бине должны быть заполнены поля:
	 *  - xsltName,
	 *  - xsltTemplate,
	 *  - xsd
	 * @param xsltBean - XSLT-бин
	 * @throws BusinessException
	 */
	public void addOrUpdate(XSLTBean xsltBean) throws BusinessException
	{
		if (xsltBean == null)
			throw new NullPointerException("Аргумент 'xsltBean' не может быть null");

		simpleService.addOrUpdate(xsltBean);
	}

	/**
	 * Возвращает XSLT-бин по имени
	 * @param name - имя XSLT-бина
	 * @return XSLT-бин либо null, если бин не найден
	 * @throws BusinessException
	 */
	public XSLTBean getBeanByName(final String name) throws BusinessException
	{
		if (StringHelper.isEmpty(name))
			throw new IllegalArgumentException("Аргумент 'name' не может быть пустым");

		try
	    {
			return HibernateExecutor.getInstance().execute(new HibernateAction<XSLTBean>()
			{
				public XSLTBean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getBeanByName");
					query.setParameter("name", name);
					return (XSLTBean) query.uniqueResult();
				}
			});
	    }
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает XSLT-шаблон по имени XSLT-бина
	 * @param name - имя XSLT-бина
	 * @return XSLT-шаблон либо null, если XSLT-бин не найден
	 * @throws BusinessException
	 */
	public String getXSLTByName(final String name) throws BusinessException
	{
		if (StringHelper.isEmpty(name))
			throw new IllegalArgumentException("Аргумент 'name' не может быть пустым");

		try
	    {
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getXSLTByName");
					query.setParameter("name", name);
					return (String) query.uniqueResult();
				}
			});
	    }
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает XSD-схему по имени XSLT-бина
	 * @param name - имя XSLT-бина
	 * @return XSD-схема либо null, если XSLT-бин не найден
	 * @throws BusinessException
	 */
	public String getXSDByName(final String name) throws BusinessException
	{
		if (StringHelper.isEmpty(name))
			throw new IllegalArgumentException("Аргумент 'name' не может быть пустым");

		try
	    {
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getXSDByName");
					query.setParameter("name", name);
					return (String) query.uniqueResult();
				}
			});
	    }
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}
}
