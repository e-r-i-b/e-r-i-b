package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.payment.InternalTransferTask;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 01.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * СМС-команда ПЕРЕВОД Между Своими Счетами
 */
public class InternalTransferCommand extends CommandBase
{
	/**
	 * Алиас продукта списания текущего пользователя (never null)
	 */
	private String chargeOffAlias;

	/**
	 * Алиас продукта зачисления текущего пользователя (never null)
	 */
	private String destinationAlias;

	/**
	 * Сумма (never null)
	 */
	private BigDecimal amount;

	/**
	 * линк продукта списания
	 */
	private transient BankrollProductLink chargeOffLink;

	/**
	 * линк продукта зачисления
	 */
	private transient BankrollProductLink destinationLink;

	///////////////////////////////////////////////////////////////////////////

	@MandatoryParameter
	void setChargeOffAlias(String chargeOffAlias)
	{
		this.chargeOffAlias = chargeOffAlias;
	}

	@MandatoryParameter
	void setDestinationAlias(String destinationAlias)
	{
		this.destinationAlias = destinationAlias;
	}

	@MandatoryParameter
	void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public BankrollProductLink getChargeOffLink()
	{
		return chargeOffLink;
	}

	public BankrollProductLink getDestinationLink()
	{
		return destinationLink;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());
		PersonBankrollManager bankrollManager = getPersonBankrollManager();

		chargeOffLink = bankrollManager.findProductBySmsAlias(chargeOffAlias, AccountLink.class, CardLink.class);

		if (chargeOffLink == null || !chargeOffLink.getShowInSms())
			throw new UserErrorException(messageBuilder.buildCardTransferBadSenderError());

		destinationLink = bankrollManager.findProductBySmsAlias(destinationAlias, AccountLink.class, CardLink.class);
		if (destinationLink == null)
			throw new UserErrorException(messageBuilder.buildCardTransferBadReceiverError());

		checkAndThrowExternalSystem((ErmbProductLink) destinationLink);
		checkAndThrowExternalSystem((ErmbProductLink) chargeOffLink);

		InternalTransferTask task = paymentManager.createInternalTransferTask();
		task.setFromResourceCode(chargeOffLink.getCode());
		task.setToResourceCode(destinationLink.getCode());
		task.setAmount(amount);

		task.setFromResourceCurrencyCode(chargeOffLink.getCurrency().getCode());
		task.setToResourceCurrencyCode(destinationLink.getCurrency().getCode());

		if (chargeOffLink instanceof CardLink && destinationLink instanceof AccountLink)
			task.setCardToAccountTransfer(true);

		task.setChargeOffLink(chargeOffLink);

		task.execute();
	}

	@Override
	public String toString()
	{
		return String.format("ПЕРЕВОД_МЕЖДУ_СВОИМИ_СЧЕТАМИ[%s -> %s, сумма: %s]", chargeOffAlias, destinationAlias, amount);
	}

	public String getCommandName()
	{
		return "INTERNAL_TRANSFER";
	}
}
