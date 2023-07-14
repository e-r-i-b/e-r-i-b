package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.annotation.OptionalParameter;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * СМС-команда ИНФОРМАЦИЯ
 * @author Rtischeva
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class ProductInfoCommand extends CommandBase
{
	/**
	 * Алиас продукта, по которому запрашивается информация
	 * null = информация по всем продуктам
	 */
	private String productAlias;

	/**
	 * Линк продукта, полученный по алиасу productAlias
	 */
	private transient BankrollProductLink link;

	///////////////////////////////////////////////////////////////////////////

	@OptionalParameter
	void setProductAlias(String productAlias)
	{
		this.productAlias = productAlias;
	}

	public BankrollProductLink getLink()
	{
		return link;
	}

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		PersonBankrollManager bankrollManager = getPersonBankrollManager();

		TextMessage message;
		if (productAlias == null)
		{
			ErmbProfileImpl profile = getErmbProfile();
			boolean quickServiceStatus = profile.getFastServiceAvailable();

			List<BankrollProductLink> cardLinks = filterShowInSmsLinks(bankrollManager.getCards());
			List<BankrollProductLink> accountLinks = filterShowInSmsLinks(bankrollManager.getAccounts());
			List<BankrollProductLink> loanLinks = filterShowInSmsLinks(bankrollManager.getLoans());

			message = new TextMessage(messageBuilder.buildProductInfoMessage(cardLinks, accountLinks, loanLinks, quickServiceStatus).getText());
		}

		else
		{
			link = bankrollManager.findProductBySmsAlias(productAlias, AccountLink.class, CardLink.class, LoanLink.class);

			if(link == null || !link.getShowInSms())
				throw new UserErrorException(messageBuilder.buildAliasProductInfoNotFoundMessage());

			checkAndThrowExternalSystem((ErmbProductLink) link);
			if (link instanceof AccountLink)
				message = messageBuilder.buildAccountInfoMessage((AccountLink) link);

			else if (link instanceof CardLink)
				message = messageBuilder.buildCardInfoMessage((CardLink) link);

			else if (link instanceof LoanLink)
				message = messageBuilder.buildLoanInfoMessage((LoanLink) link);

			else throw new InternalErrorException("Неизвестная ошибка");
		}

		sendMessage(message);
	}

	private List<BankrollProductLink> filterShowInSmsLinks(Collection<? extends BankrollProductLink> links)
	{
		List<BankrollProductLink> result = new ArrayList<BankrollProductLink>();
		for (BankrollProductLink productLink : links)
		{
			if (productLink.getShowInSms())
				result.add(productLink);
		}

		return result;
	}

	public String getProductAlias()
	{
		return productAlias;
	}

	@Override
	public String toString()
	{
		if (productAlias != null)
			return String.format("ИНФОРМАЦИЯ[алиас:%s]", productAlias);
		else return "ИНФОРМАЦИЯ[]";
	}

	public String getCommandName()
	{
		return "PRODUCT_INFO";
	}
}
