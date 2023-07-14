package com.rssl.phizic.business.ermb;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.bankroll.BasicPersonBankrollManager;
import com.rssl.phizic.business.ermb.card.PrimaryCardResolver;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.module.Module;

/**
 * @author Rtischeva
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class SmsPersonBankrollManager extends BasicPersonBankrollManager
{
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	private final ErmbProfileImpl profile;

	/**
	 * ctor
	 * @param module - модуль, в котором работает менеджер (never null)
	 * @param person - клиент, с которым работает менеджер (never null)
	 */
	public SmsPersonBankrollManager(Module module, ActivePerson person)
	{
		super(module, person);

		try
		{
			// todo: уйти от лишнего запроса профиля. Исполнитель: Еркин С.
			profile = profileService.findByUser(person);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	public BankrollProductLink getPriorityCard()
	{
		try
		{
			return PrimaryCardResolver.getPrimaryLink(profile.getCardLinks());
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}
}