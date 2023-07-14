package com.rssl.phizic.business.incognitoDictionary;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Сервис для работы со справочником "инкогнито"
 *
 * @author EgorovaA
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */
public class IncognitoPhonesService
{
	private static final String QUERY_PREFIX = "com.rssl.phizic.business.incognitoDictionary.";

	private static SimpleService simpleService = new SimpleService();

	/**
	 * Поиск телефонов в справочнике "инкогнито" по номерам
	 * @param numbers список номеров телефонов
	 * @throws BusinessException
	 */
	public List<IncognitoPhone> findPhones(final Collection<String> numbers) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<IncognitoPhone>>()
			{
				public List<IncognitoPhone> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findPhones");
					query.setParameterList("numbers", numbers);
					return (List<IncognitoPhone>) query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException("Ошибка при получении списка клиентов", ex);
		}
	}

	/**
	 * @param phone добавляемый телефон.
	 * @param loginId идентификатор логина.
	 * @throws BusinessException
	 */
	public void add(String phone, Long loginId) throws BusinessException
	{
		IncognitoPhone ph = new IncognitoPhone(phone);
		ph.setLoginId(loginId);
		simpleService.addOrUpdate(ph);
	}

	/**
	 * @param phone добавляемый телефон.
	 * @throws BusinessException
	 */
	public void add(String phone) throws BusinessException
	{
		IncognitoPhone ph = new IncognitoPhone(phone);
		delete(phone);
		simpleService.addOrUpdate(ph);
	}

	/**
	 * @param phone удаленеи инкогнито телефона.
	 * @throws BusinessException
	 */
	public void delete(String phone) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.incognitoDictionary.delete");
			executorQuery.setParameter("phone", phone)
						.executeUpdate();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаление по ид пользователя.
	 *
	 * @param loginId логинид.
	 * @throws BusinessException
	 */
	public void delete(Long loginId) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.incognitoDictionary.deleteByLoginId");
			executorQuery.setParameter("loginId", loginId)
						.executeUpdate();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
