package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbPermissionCalculator;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.card.PrimaryCardResolver;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.annotation.OptionalParameter;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;

/**
 * @author Erkin
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * СМС-комманда ИСТОРИЯ
 */
public class HistoryCommand extends CommandBase
{
	/**
	 * Алиас продукта, по которому запрашивается выписка
	 * null = используется "главный" продукт
	 */
	private String productAlias;

	//линк продукта, полученный по алиасу productAlias
	private transient BankrollProductLink link;

	///////////////////////////////////////////////////////////////////////////

	@OptionalParameter
	void setProductAlias(String productAlias)
	{
		this.productAlias = productAlias;
	}

	public void doExecute()
	{
		ErmbProfileImpl profile = getErmbProfile();
		if (!getPhoneTransmitter().equals(profile.getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		PersonBankrollManager bankrollManager = getPersonBankrollManager();

		TextMessage message = null;

		// TODO: (ЕРМБ) Унести получение выписки в PersonBankrollManager. Исполнитель Еркин С.
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

		try
		{
			boolean quickServiceStatus = profile.getFastServiceAvailable();
			List<TransactionBase> transactions;
			Long numberOfTransactions = bankrollManager.getNumberOfTransactions();

			ErmbPermissionCalculator permissionCalculator = new ErmbPermissionCalculator(profile);
			if (productAlias == null)
			{
				if (!permissionCalculator.impliesCardHistoryOperation())
					throw new UserErrorException(messageBuilder.buildProductHistoryNotProvidedMessage());
				Card card = getPriorityCard(profile);
				transactions = GroupResultHelper.getOneResult(bankrollService.getAbstract(numberOfTransactions, card)).getTransactions();
				message = messageBuilder.buildCardAbstractMessage((CardLink) link, transactions, quickServiceStatus);
			}
			else
			{
				link = bankrollManager.findProductBySmsAlias(productAlias, AccountLink.class, CardLink.class);

				if(link == null || !link.getShowInSms())
					throw new UserErrorException(messageBuilder.buildAliasHistoryNotFoundMessage(findSMSAliaasLength(profile)));

				if (link instanceof AccountLink)
				{
					if (!permissionCalculator.impliesAccountHistoryOperation())
						throw new UserErrorException(messageBuilder.buildProductHistoryNotProvidedMessage());

					AccountLink accountLink = (AccountLink) link;
					Account account = GroupResultHelper.getOneResult(bankrollService.getAccount(accountLink.getExternalId()));
					if (account == null)
						throw new UserErrorException(messageBuilder.buildNotAvailableProductMessage(accountLink));

					transactions = GroupResultHelper.getOneResult(bankrollService.getAbstract(numberOfTransactions, account)).getTransactions();
					if (transactions.size()>=5) transactions = transactions.subList(0,5);
					message =messageBuilder.buildAccountAbstractMessage(accountLink, transactions, quickServiceStatus);
				}

				else if (link instanceof CardLink)
				{
					if (!permissionCalculator.impliesCardHistoryOperation())
						throw new UserErrorException(messageBuilder.buildProductHistoryNotProvidedMessage());

					CardLink cardLink = (CardLink) link;
					Card card = GroupResultHelper.getOneResult(bankrollService.getCard(cardLink.getExternalId()));
					if (card == null)
						throw new UserErrorException(messageBuilder.buildNotAvailableProductMessage(cardLink));

					transactions = GroupResultHelper.getOneResult(bankrollService.getAbstract(numberOfTransactions, card)).getTransactions();
					message = messageBuilder.buildCardAbstractMessage(cardLink, transactions, quickServiceStatus);
				}

				else throw new InternalErrorException("Неизвестная ошибка");
			}

			if (message!=null)
				sendMessage(message);
		}
		catch (LogicException e)
		{
			throw new InternalErrorException(e);
		}
		catch (SystemException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private Card getPriorityCard(ErmbProfileImpl profile)
	{
		try
		{
			link = PrimaryCardResolver.getPrimaryLink(profile.getCardLinks());
		}
		catch (BusinessException e)
		{
			throw new UserErrorException(messageBuilder.buildAliasHistoryNotFoundMessage(findSMSAliaasLength(profile)));
		}
		if (!link.getShowInSms())
			throw new UserErrorException(messageBuilder.buildAliasHistoryNotFoundMessage(findSMSAliaasLength(profile)));
		Card card = ((CardLink) link).getValue();
		if (card == null)
			throw new UserErrorException(messageBuilder.buildAliasHistoryNotFoundMessage(findSMSAliaasLength(profile)));
		return card;
	}

	public String getProductAlias()
	{
		return productAlias;
	}

	public BankrollProductLink getLink()
	{
		return link;
	}

	@Override
	public String toString()
	{
		if (productAlias != null)
			return String.format("ИСТОРИЯ[алиас:%s]", productAlias);
		else return "ИСТОРИЯ[]";
	}

	public String getCommandName()
	{
		return "HISTORY";
	}

	private int findSMSAliaasLength(ErmbProfileImpl profile)
	{
		for (CardLink cardLink : profile.getCardLinks())
		{
			if (StringHelper.isNotEmpty(cardLink.getAutoSmsAlias()))
				return cardLink.getAutoSmsAlias().length();
		}
		for (AccountLink accountLink : profile.getAccountLinks())
		{
			if (StringHelper.isNotEmpty(accountLink.getAutoSmsAlias()))
				return accountLink.getAutoSmsAlias().length();
		}
		for (LoanLink loanLink : profile.getLoanLinks())
		{
			if (StringHelper.isNotEmpty(loanLink.getAutoSmsAlias()))
				return loanLink.getAutoSmsAlias().length();
		}
		return 4;
	}
}
