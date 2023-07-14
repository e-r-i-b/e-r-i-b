package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.card.PrimaryCardResolver;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.annotation.OptionalParameter;
import com.rssl.phizic.common.types.exceptions.UserErrorException;

/**
 * @author Erkin
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * СМС-комманда "Баланс"
 */
public class BalanceCommand extends CommandBase
{
	/**
	 * Алиас продукта, по которому запрашивается баланс
	 * null = используется "главный" продукт (главный продукт - это карта, которая выбирается по специальному алгоритму)
	 */
	private String productAlias;

	//линк продукта, полученный по алиасу productAlias
	private transient ErmbProductLink link;

	///////////////////////////////////////////////////////////////////////////

	@OptionalParameter
	void setProductAlias(String productAlias)
	{
		this.productAlias = productAlias;
	}

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());

		if (productAlias == null)
		{
			link = getPriorityCardLink();
		}
		else
		{
			PersonBankrollManager bankrollManager = getPersonBankrollManager();
			link = (ErmbProductLink) bankrollManager.findProductBySmsAlias(productAlias, AccountLink.class, CardLink.class, LoanLink.class);
		}

		if (link == null || !link.getShowInSms())
			throw new UserErrorException(messageBuilder.buildAliasBalanceNotFoundMessage());

		checkAndThrowExternalSystem(link);

		sendMessage(messageBuilder.buildProductBalanceMessage(link));
	}

	private ErmbProductLink getPriorityCardLink()
	{
		ErmbProfileImpl profile = getErmbProfile();
		try
		{
			return PrimaryCardResolver.getPrimaryLink(profile.getCardLinks());
		}
		catch (BusinessException e)
		{
			throw new UserErrorException(messageBuilder.buildAliasBalanceNotFoundMessage(), e);
		}
	}

	public String getProductAlias()
	{
		return productAlias;
	}

	public ErmbProductLink getLink()
	{
		return link;
	}

	@Override
	public String toString()
	{
		if (productAlias != null)
			return String.format("БАЛАНС[алиас:%s]", productAlias);
		else return "БАЛАНС[]";
	}

	public String getCommandName()
	{
		return "BALANCE";
	}
}
