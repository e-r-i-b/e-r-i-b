package com.rssl.phizic.business.finances.targets;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import java.util.List;

/**
 * —ервис дл€ работы с цел€ми
 * @author lepihina
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class AccountTargetService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ƒобавл€ет цель
	 * @param target - добавл€ема€ цель
	 * @return цель
	 * @throws BusinessException
	 */
	public AccountTarget addOrUpdate(AccountTarget target) throws BusinessException
	{
		return simpleService.addOrUpdate(target);
	}

	/**
	 * ѕоиск цели по id
	 * @param id - id цели
	 * @return цель
	 * @throws BusinessException
	 */
	public AccountTarget findTargetById(Long id) throws BusinessException
	{
		return simpleService.findById(AccountTarget.class,id);
	}

	/**
	 * ѕоиск списка целей по логину пользовател€
	 * @param login - логин пользовател€
	 * @return список целей
	 * @throws BusinessException
	 */
	public List<AccountTarget> findTargetsByOwner(Login login) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AccountTarget.class);
		criteria.add(Expression.eq("loginId", login.getId()));
		criteria.addOrder(Order.desc("id"));
		return simpleService.find(criteria);
	}

	/**
	 * ѕоиск цели по идентификатору линка вклада
	 * @param id - идентификатор линка вклада
	 * @return цель
	 * @throws BusinessException
	 */
	public AccountTarget findTargetByAccountId(Long id) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AccountTarget.class);
		criteria.add(Expression.eq("accountLink.id", id));
		return simpleService.findSingle(criteria);
	}

	/**
	 * ѕоиск целей, к которым прив€зан вклад
	 * @param login - логин клиента
	 * @return список целей
	 * @throws BusinessException
	 */
	public List<AccountTarget> findTargetsWithAccounts(Login login) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AccountTarget.class);
		criteria.add(Expression.isNotNull("accountLink"));
		criteria.add(Expression.eq("loginId", login.getId()));
		return simpleService.find(criteria);
	}

	/**
	 * ѕоиск цели клиента по номеру вклада
	 * @param accountNumber - номер вклада
	 * @param login - логин клиента
	 * @return цель клиента
	 * @throws BusinessException
	 */
	public AccountTarget findTargetByAccountForLogin(String accountNumber, Login login) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AccountTarget.class);	
		criteria.add(Expression.eq("accountNum", accountNumber));
		criteria.add(Expression.eq("loginId", login.getId()));
		return simpleService.findSingle(criteria);
	}

	/**
	 * ¬озвращает количество целей по логину пользовател€
	 * @param login - логин пользовател€
	 * @return количество целей
	 * @throws BusinessException
	 */
	public Integer countTargets(Login login) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AccountTarget.class).
				add(Expression.eq("loginId",login.getId())).
				setProjection(Projections.rowCount());
		return (Integer)simpleService.findSingle(criteria);
	}

	/**
	 * ”дал€ет цель
	 * @param target - удал€ема€ цель
	 * @throws BusinessException
	 */
	public void remove(AccountTarget target) throws BusinessException
	{
		simpleService.remove(target);
	}

	/**
	 * получить цель по за€вке
	 * @param accountOpeningClaim за€вка на открытие вклада
	 * @return цель
	 * @throws BusinessException
	 */
	public AccountTarget findByClaim(AccountOpeningClaim accountOpeningClaim) throws BusinessException
	{
		BusinessDocumentOwner documentOwner = accountOpeningClaim.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("ќпераци€ в гостевой сессии не поддерживаетс€");
		DetachedCriteria criteria = DetachedCriteria.forClass(AccountTarget.class)
				.add(Expression.eq("loginId", documentOwner.getLogin().getId()))
				.add(Expression.eq("claimId", accountOpeningClaim.getId()));
		return (AccountTarget)simpleService.findSingle(criteria);
	}
}
