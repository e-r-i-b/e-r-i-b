package com.rssl.phizic.business.fund.initiator;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * @author osminin
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с группами получателей
 */
public class FundGroupService
{
	private static SimpleService simpleService = new SimpleService();

	/**
	 * Получить группу по идентификатору
	 * @param id идентификатор
	 * @return группа получателей
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public FundGroup getById(Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор группы получателей не может быть null.");
		}
		return simpleService.findById(FundGroup.class, id);
	}

	/**
	 * Добавить группу получателей
	 * @param fundGroup - добавляемая группа
	 * @throws BusinessException
	 */
	public void add(FundGroup fundGroup) throws BusinessException
	{
		simpleService.add(fundGroup);
		for (FundGroupPhone phone:fundGroup.getPhones())
		{
			phone.setGroupId(fundGroup.getId());
			addPhone(phone);
		}
	}

	/**
	 * Добавить запись о телефоне в группе получателей
	 * @param phone - добавляемый телефон
	 * @throws BusinessException
	 */
	public void addPhone(FundGroupPhone phone) throws BusinessException
	{
		simpleService.add(phone);
	}

	/**
	 * Удалить группу получателей
	 * @param fundGroup - группа получателей
	 * @throws BusinessException
	 */
	public void remove(FundGroup fundGroup) throws BusinessException
	{
		removePhonesByGroupId(fundGroup.getId());
		simpleService.remove(fundGroup);
	}

	/**
	 * Удалить телефоны получателей по идентификатору соответствующей группы
	 * @param groupId - ид группы
	 * @throws BusinessException
	 */
	public void removePhonesByGroupId(final Long groupId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundGroupPhone.removeByGroupId")
							.setParameter("groupId", groupId)
							.executeUpdate();
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
	 * Получение телефонов получателей по идентификатору соответствующей группы
	 * @param groupId - ид группы
	 * @return
	 * @throws BusinessException
	 */
	public List<FundGroupPhone> getPhonesByGroupId(final Long groupId) throws BusinessException
	{
		List<String> phonesList;
		try
		{
			phonesList = HibernateExecutor.getInstance().execute(new HibernateAction<List>()
			{
				public List run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundGroupPhone.selectByGroupId");
					query.setParameter("groupId", groupId);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		List<FundGroupPhone> result = new ArrayList<FundGroupPhone>();
		for (String phone:phonesList)
		{
			result.add(new FundGroupPhone(groupId, phone));
		}
		return result;
	}

	/**
	 * Обновление записи о группе получателей
	 * @param fundGroup
	 * @throws BusinessException
	 */
	public void updateFundGroup(FundGroup fundGroup) throws BusinessException
	{
		simpleService.update(fundGroup);
		updateFundGroupPhones(fundGroup);
	}

	/**
	 * Обновление записей о телефонах получателей
	 * @param fundGroup
	 * @throws BusinessException
	 */
	private void updateFundGroupPhones(FundGroup fundGroup) throws BusinessException
	{
		List<FundGroupPhone> newPhonesList = fundGroup.getPhones();
		List<FundGroupPhone> currentPhonesList = getPhonesByGroupId(fundGroup.getId());

		List<FundGroupPhone> phonesToAdd = new ArrayList<FundGroupPhone>();
		phonesToAdd.addAll(newPhonesList);
		phonesToAdd.removeAll(currentPhonesList);
		for (FundGroupPhone phone:phonesToAdd)
		{
			simpleService.add(phone);
		}

		List<FundGroupPhone> phonesToDelete = new ArrayList<FundGroupPhone>();
		phonesToDelete.addAll(currentPhonesList);
		phonesToDelete.removeAll(newPhonesList);
		for (FundGroupPhone phone:phonesToDelete)
		{
			simpleService.remove(phone);
		}
	}

	/**
	 * Получение списка групп получателей для клиента
	 * @param loginId - ид логина клиента
	 * @return
	 * @throws BusinessException
	 */
	public List<FundGroup> getFundGroupsByLoginId(final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FundGroup>>()
			{
				public List<FundGroup> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundGroup.getByLoginId");
					query.setParameter("loginId", loginId);
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
