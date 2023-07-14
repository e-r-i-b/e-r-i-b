package com.rssl.phizic.business.dictionaries.security;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.depo.SecurityBase;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

/**
 * @author mihaylov
 * @ created 09.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class SecurityService
{
	private static final SimpleService simpleService = new SimpleService();

	public Security findById(Long id) throws BusinessException
	{
		return simpleService.findById(Security.class,id);
	}

	public void update(Security security) throws BusinessException
	{
		security.setId(findIdByInsideCode(security.getInsideCode()));
		simpleService.addOrUpdate(security);
	}

	public Long findIdByInsideCode(final String insideCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
			{
				public Long run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.security.SecurityService.findIdByInsideCode");
					query.setParameter("insideCode", insideCode);
					return (Long)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Security findByInsideCode(final String insideCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Security>()
			{
				public Security run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.security.SecurityService.findByInsideCode");
					query.setParameter("insideCode", insideCode);
					return (Security) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получаем список типов ЦБ, которые есть в справочник
	 * @return  список типов ЦБ
	 * @throws BusinessException
	 */
	public List<String> getSecurityTypes() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.security.SecurityService.getSecurityTypes");
					return ( List<String>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получаем список типов ЦБ, которые есть в справочник
	 * @return  список типов ЦБ
	 * @throws BusinessException
	 */
	public List<String> getOpenSecurityTypes() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.security.SecurityService.getOpenSecurityTypes");
					return ( List<String>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param insideCodes - список кодов выпуска ценной бумаги (регистрационные номера)
	 * @return список ценных бумаг
	 * @throws BusinessException
	 */
	public List<SecurityBase> getSecuritiesByCodes(final List<String> insideCodes) throws BusinessException
	{
		if(insideCodes == null || insideCodes.size() == 0)
			return Collections.EMPTY_LIST;
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<SecurityBase>>()
			{
				public List<SecurityBase> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.security.SecurityService.getSecuritiesByCodes");
					query.setParameterList("insideCodes",insideCodes);
					return ( List<SecurityBase>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
