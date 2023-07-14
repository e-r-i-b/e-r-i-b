package com.rssl.phizic.operations.fund.group;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.initiator.FundGroup;
import com.rssl.phizic.context.PersonContext;

/**
 * @author osminin
 * @ created 25.11.14
 * @ $Author$
 * @ $Revision$
 *
 * Просмотр группы получателей запросов
 * Фейковая операция в рамках запроса  BUG081589 [Краудгифтинг] нет прав доступа клиента
 */
public class ViewFundGroupOperation extends FundGroupOperation
{
	private FundGroup fundGroup;

	@Override
	public String getForwardName()
	{
		return "View";
	}

	@Override
	public void initialize(Long id, String name, String phones) throws BusinessException, BusinessLogicException
	{
		initialize(id);
	}

	private void initialize(Long id) throws BusinessLogicException, BusinessException
	{
		this.fundGroup = fundGroupService.getById(id);
		if (fundGroup == null)
		{
			throw new BusinessLogicException("Группа получателей с id=" + id + " не существует");
		}
		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		if (loginId != fundGroup.getLoginId())
		{
			throw new BusinessLogicException("Группа получателей с id=" + id + " недоступна клиенту с loginId=" + loginId);
		}
		this.fundGroup.setPhones(fundGroupService.getPhonesByGroupId(id));
	}

	public FundGroup getFundGroup()
	{
		return fundGroup;
	}
}
