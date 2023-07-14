package com.rssl.phizic.business.pfp;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 02.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class PersonalFinanceProfileService
{
	private static final String COMPLETE_STATE_CODE = "COMPLITE";
	private static final String OLD_COMPLETE_STATE_CODE = "COMPLITE_OLD";
	public static final String COMPLETED_PFP     = "completed";
	public static final String NOT_COMPLETED_PFP = "notCompleted";
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Добавить или обновить финансовый профиль клиента
	 * @param profile - профиль
	 * @throws BusinessException
	 */
	public PersonalFinanceProfile addOrUpdateProfile(PersonalFinanceProfile profile) throws BusinessException, ConstraintViolationException
	{
		return simpleService.addOrUpdateWithConstraintViolationException(profile, PFPConstants.INSTANCE_NAME);
	}

	/**
	 * Получить финансовые профили (2 пфп: завершенное и незавершенное) клиента
	 * @param login - логин клиента
	 * @return финансовый профиль
	 * @throws BusinessException
	 */
	public Map<String, PersonalFinanceProfile> getLastProfilesByLogin(final Login login) throws BusinessException
	{
		List<PersonalFinanceProfile> profiles = null;
		try
		{
			profiles = HibernateExecutor.getInstance(PFPConstants.INSTANCE_NAME).execute(new HibernateAction<List<PersonalFinanceProfile>>()
			{
				public List<PersonalFinanceProfile> run(Session session) throws Exception
				{

					Query namedQuery = session.getNamedQuery("com.rssl.phizic.business.pfp.PersonalFinanceProfile.getLastProfilesByLogin");
					namedQuery.setLong("loginId", login.getId());
					//noinspection unchecked
					return namedQuery.list();
				}
			}
			);
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		if (CollectionUtils.isEmpty(profiles))
			return Collections.emptyMap();

		Map<String, PersonalFinanceProfile> result = new HashMap<String, PersonalFinanceProfile>(2);
		for (PersonalFinanceProfile profile: profiles)
		{
			if (COMPLETE_STATE_CODE.equals(profile.getState().getCode()) || OLD_COMPLETE_STATE_CODE.equals(profile.getState().getCode()))
				result.put(COMPLETED_PFP, profile);
			else
				result.put(NOT_COMPLETED_PFP, profile);
		}
		return result;
	}

	/**
	 * Получить все планирования клиента
	 * @param login - логин клиента
	 * @return финансовый профиль
	 * @throws BusinessException
	 */
	public List<PersonalFinanceProfile> getProfilesByLogin(Login login) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(PersonalFinanceProfile.class);
		criteria.add(Expression.eq("owner", login));
		criteria.addOrder(Order.desc("creationDate"));
		return simpleService.find(criteria, PFPConstants.INSTANCE_NAME);
	}

	/**
	 * Получить финансовый профиль клиента
	 * @param id - идентификатор ПФП
	 * @return финансовый профиль
	 * @throws BusinessException
	 */
	public PersonalFinanceProfile getProfileById(Long id) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(PersonalFinanceProfile.class);
		criteria.add(Expression.eq("id", id));
		return simpleService.findSingle(criteria, PFPConstants.INSTANCE_NAME);
	}

	/**
	 * Получить новй финансовый профиль клиента
	 * @param login - логин клиента
	 * @return финансовый профиль
	 * @throws BusinessException
	 */
	public PersonalFinanceProfile getNewProfile(Login login) throws BusinessException
	{
		return new PersonalFinanceProfile(login);
	}

	/**
	 * Удаляет незавершенные ПФП для клиента
	 * @param login логин клиента
	 * @throws BusinessException
	 */
	public void removeNotCompleted(final Login login) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(PersonalFinanceProfile.class);
		criteria.add(Expression.and(Expression.eq("owner", login), Expression.and(Expression.ne("state.code", COMPLETE_STATE_CODE), Expression.ne("state.code", OLD_COMPLETE_STATE_CODE))));
		List<PersonalFinanceProfile> removedPFPList = simpleService.find(criteria, PFPConstants.INSTANCE_NAME);
		if (CollectionUtils.isEmpty(removedPFPList))
			return;

		for (PersonalFinanceProfile profile: removedPFPList)
			simpleService.remove(profile, PFPConstants.INSTANCE_NAME);
	}
}
