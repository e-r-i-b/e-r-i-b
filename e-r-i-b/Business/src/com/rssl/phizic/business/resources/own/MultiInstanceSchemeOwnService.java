package com.rssl.phizic.business.resources.own;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.schemes.*;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 10.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstanceSchemeOwnService
{
	private static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	private static final DefaultAccessSchemeService defaultSchemeService = new DefaultAccessSchemeService();

	/**
	 * реплицируем схему прав
	 * @param login логин клиента, для которого производится реплицирование
	 * @param accessType тип доступа
	 * @param fromInstanceName имя экземпляра Бд, откуда производиться реплицирование
	 * @param toInstanceName имя экхемпляра БД, куда реплицируем
	 * @throws BusinessException
	 */
	public void replicate(final CommonLogin login, AccessType accessType, String fromInstanceName, String toInstanceName) throws BusinessException
	{
		SchemeOwn toSchemeOwn = findSchemeOwn(login, accessType, toInstanceName);
		SchemeOwn schemeOwn = findSchemeOwn(login, accessType, fromInstanceName);

		boolean isUpdate = ( toSchemeOwn  != null && schemeOwn!=null && toSchemeOwn.getId().equals(schemeOwn.getId()));


		//иначе копировать нечего
		if(schemeOwn!=null)
		{
			if(!isUpdate)
			{
				AccessScheme scheme = findScheme(login, accessType, fromInstanceName);

				if(scheme!=null)
				{
					AccessScheme schemClone = scheme instanceof PersonalAccessScheme?new PersonalAccessScheme((PersonalAccessScheme)scheme):scheme;
					simpleService.replicate(schemClone, toInstanceName);
					setScheme(login, accessType, schemClone, toInstanceName);
				}
			}
			else
				simpleService.update(schemeOwn, toInstanceName);
		}
	}
	
	/**
	 * Установить схемы доступа для логина
	 *
	 * @param login      логин
	 * @param accessType тип доступа
	 * @param scheme     схема доступа
	 */
	public void setScheme(final CommonLogin login, final AccessType accessType, final AccessScheme scheme
			, String instanceName) throws BusinessException
	{
		if(login instanceof Login && accessType == AccessType.employee)
			throw new BusinessException("Невозможно назначить клиенту тип доступа для сотрудников банка");

		if (scheme == null)
		{
			removeScheme(login, accessType, instanceName);
		}
		else
		{
			nullSafeSetScheme(login, accessType, scheme, instanceName);
		}
	}

	private void nullSafeSetScheme(final CommonLogin login, final AccessType accessType,
	                               final AccessScheme scheme,final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					SchemeOwn schemeOwn = findSchemeOwn(login, accessType,instanceName);
					if (schemeOwn == null)
					{
						schemeOwn = new SchemeOwn(login, accessType);
					}
					schemeOwn.setAccessScheme(scheme);

					simpleService.addOrUpdate(schemeOwn, instanceName);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Убрать назначенную схему дступа для логина
	 *
	 * @param login      логин
	 * @param accessType тип доступа
	 */
	public void removeScheme(final CommonLogin login, final AccessType accessType,
	                         final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					SchemeOwn schemeOwn = findSchemeOwn(login, accessType, instanceName);

					if (schemeOwn != null)
						simpleService.remove(schemeOwn, instanceName);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Найти схемы прав связанную с логином
	 *
	 * @param login      логин
	 * @param accessType тип доступа
	 * @return схема прав
	 */
	public AccessScheme findScheme(CommonLogin login, AccessType accessType,
	                               String instanceName) throws BusinessException
	{
		SchemeOwn schemeOwn = findSchemeOwn(login, accessType, instanceName);
		return schemeOwn == null ? null : schemeOwn.getAccessScheme();
	}

	private SchemeOwn findSchemeOwn(CommonLogin login, AccessType accessType
			, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(SchemeOwn.class)
				.add(Expression.eq("loginId", login.getId()))
				.add(Expression.eq("accessType", accessType.getKey()));


		List<SchemeOwn> list = simpleService.find(criteria, instanceName);

		return (list.size() > 0 ? list.get(0) : null);
	}

	/**
	 * Удалить все связи логина и схем
	 *
	 * @param login логин
	 */
	public void removeAllSchemes(CommonLogin login
			, String instanceName) throws BusinessException
	{
		if(login instanceof Login)
		{
			removeScheme(login, AccessType.simple, instanceName);
			removeScheme(login, AccessType.secure, instanceName);
			removeScheme(login, AccessType.smsBanking, instanceName);
		}
		else
		{
			removeScheme(login, AccessType.employee, instanceName);
		}
	}

	/**
	 * Установить схемы по умолчанию для клиента
	 * @param login логин клиента
	 * @param creationType тип клиента
	 * @param departmentTb код Тербанка клиента, null если клиент карточный
	 * @param instanceName инстанс БД
	 */
	public void setClientDefaultSchemes(Login login, DefaultSchemeType creationType, String departmentTb, String instanceName, AccessType accessType) throws BusinessException
	{
		if (accessType == null || accessType.equals(AccessType.simple))
		{
			AccessScheme defaultScheme = defaultSchemeService.findByCreationTypeAndDepartment(creationType, departmentTb);
			setScheme(login, AccessType.simple, defaultScheme, instanceName);
		}

		AccessSchemesConfig schemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class);

		if (accessType == null || accessType.equals(AccessType.secure))
		{
			AccessScheme secureScheme = schemesConfig.getDefaultAccessScheme(AccessType.secure);
			setScheme(login, AccessType.secure, secureScheme, instanceName);
		}
		if (accessType == null || accessType.equals(AccessType.smsBanking))
		{
			AccessScheme smsBankingScheme = schemesConfig.getDefaultAccessScheme(AccessType.smsBanking);
			setScheme(login, AccessType.smsBanking, smsBankingScheme, instanceName);
		}
		if (accessType == null || accessType.equals(AccessType.mobileLimited))
		{
			AccessScheme mobileLimitedScheme = schemesConfig.getDefaultAccessScheme(AccessType.mobileLimited);
			setScheme(login, AccessType.mobileLimited, mobileLimitedScheme, instanceName);
		}
	}

	/**
	 * Установить схему по умолчанию для сотрудника
	 * @param login логин сотрудника
	 */
	public void setEmployeeDefaultScheme(BankLogin login, String instanceName) throws BusinessException
	{
		AccessScheme defaultScheme = ConfigFactory.getConfig(DbAccessSchemesConfig.class).getDefaultAccessScheme(AccessType.employee);
		setScheme(login, AccessType.employee, defaultScheme, instanceName);
	}

	/**
	 * Проверить у клиента наличие схем прав с определенным типом
	 * @param login - логин клиента
	 * @param accessTypes - проверяемые типы прав
	 * @return список с типами прав схем, присвоенных клиенту (из перечня проверяемых)
	 * @throws BusinessException
	 */
	public List<String> findClientSchemeOwnTypes(final CommonLogin login, final List<String> accessTypes)  throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.resources.own.SchemeOwn.findTypes");
					query.setParameter("loginId", login.getId());
					query.setParameterList("accessTypes", accessTypes);
					return (List<String>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
