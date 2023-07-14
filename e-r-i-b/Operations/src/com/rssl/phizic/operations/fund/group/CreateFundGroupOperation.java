package com.rssl.phizic.operations.fund.group;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.fund.initiator.FundGroup;
import com.rssl.phizic.context.PersonContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author osminin
 * @ created 25.11.14
 * @ $Author$
 * @ $Revision$
 *
 * Создание группы получателей запросов
 * Фейковая операция в рамках запроса  BUG081589 [Краудгифтинг] нет прав доступа клиента
 */
public class CreateFundGroupOperation extends FundGroupOperation
{
	@Override public String getForwardName()
	{
		return "Status";
	}

	@Override public void initialize(Long id, String name, String phones) throws BusinessException
	{
		initialize(name, phones);
	}

	private void initialize(String name, String phones) throws BusinessException
	{
		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		FundGroup fundGroup = new FundGroup(loginId, name);
		fundGroup.setPhones(phones);
		fundGroupService.add(fundGroup);
	}
}
