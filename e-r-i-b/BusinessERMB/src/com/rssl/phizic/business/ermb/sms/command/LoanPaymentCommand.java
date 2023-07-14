package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.payment.LoanPaymentTask;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;
import com.rssl.phizic.utils.MoneyHelper;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Смс-команда "Оплата кредита"
 * @author Rtischeva
 * @created 09.10.13
 * @ $Author$
 * @ $Revision$
 */
public class LoanPaymentCommand extends CommandBase
{
	/**
	 * алиас кредита, который оплачиваем
	 */
	private String loanAlias;

	/**
	 * алиас карты, с которой оплачиваем
	 */
	private String cardAlias;

	/**
	 * сумма
	 */
	private BigDecimal amount;

	/**
	 * валюта кредита
	 */
	private String loanCurrency;

	/**
	 * Линк кредита, полученный по алиасу loanAlias
	 */
	private transient BankrollProductLink loanLink;

	/**
	 *  Линк карты, полученный по алиасу cardAlias
	 */
	private transient BankrollProductLink cardLink;

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		PersonBankrollManager bankrollManager = getPersonBankrollManager();

		loanLink = findLoanLink(bankrollManager);

		LoanLink link = (LoanLink) loanLink;
		Loan loan = link.getLoan();
		if (loan.getIsAnnuity())
			throw new UserErrorException(messageBuilder.buildAnnuityLoanPaymentMessage());

		Currency currency = link.getLoan().getLoanAmount().getCurrency();
		loanCurrency = currency.getCode();

		if (amount == null)
			amount = loan.getNextPaymentAmount().getDecimal();
		else
		{
			if (loan.getNextPaymentAmount().compareTo(new Money(amount, currency)) > 0)
				throw new UserErrorException(messageBuilder.buildAmountLessThanNextAmountMessage());
		}

		if (cardAlias == null)
			cardLink = getPriorityCardLink(new Money(amount, currency));
		else
			cardLink = bankrollManager.findProductBySmsAlias(cardAlias, CardLink.class);
		if (cardLink == null || !cardLink.getShowInSms())
		{
			if (cardLink != null)
				throw new UserErrorException(messageBuilder.buildPaymentCommandIncorrectAliasMessage(cardLink.getAutoSmsAlias().length()));

			Collection<CardLink> cardLinks = (Collection<CardLink>) bankrollManager.getCards();
			throw new UserErrorException(messageBuilder.buildPaymentCommandIncorrectAliasMessage(getCardSmsAutoAliasLength(cardLinks)));
		}

		PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());
		LoanPaymentTask task = paymentManager.createLoanPaymentTask();
		task.setAmount(amount);
		task.setFromResourceAlias(cardAlias);
		task.setFromResourceCode(cardLink.getCode());
		task.setLoanLinkCode(loanLink.getCode());
		task.setLoanAlias(loanAlias);
		task.setLoanBalanceAmount(MoneyHelper.formatMoney(loan.getBalanceAmount()));
		task.setLoanCurrency(loanCurrency);
		task.execute();
	}

	private BankrollProductLink findLoanLink(PersonBankrollManager bankrollManager)
	{
		BankrollProductLink result = null;
		if (StringHelper.isEmpty(loanAlias))
		{
			List<LoanLink> loanLinks = getErmbProfile().getLoanLinks();
			if (loanLinks.size() == 1)
				result = loanLinks.get(0);
		}
		else
			result = bankrollManager.findProductBySmsAlias(loanAlias, LoanLink.class);
		if (result == null || !result.getShowInSms())
			throw new UserErrorException(messageBuilder.buildIncorrectLoanAliasMessage());
		return result;
	}

	private CardLink getPriorityCardLink(Money amount)
	{
		try
		{
			return ErmbHelper.getPriorityCardLink(amount);
		}
		catch (BusinessException e)
		{
			throw new UserErrorException(messageBuilder.buildPaymentCommandIncorrectAliasMessage(getCardSmsAutoAliasLength()));
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(messageBuilder.buildPaymentCommandIncorrectAliasMessage(getCardSmsAutoAliasLength()));
		}
	}

	public String getLoanAlias()
	{
		return loanAlias;
	}

	public void setLoanAlias(String loanAlias)
	{
		this.loanAlias = loanAlias;
	}

	public String getCardAlias()
	{
		return cardAlias;
	}

	public void setCardAlias(String cardAlias)
	{
		this.cardAlias = cardAlias;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public String getLoanCurrency()
	{
		return loanCurrency;
	}

	public String getCommandName()
	{
		return "LOAN_PAYMENT";
	}
}
