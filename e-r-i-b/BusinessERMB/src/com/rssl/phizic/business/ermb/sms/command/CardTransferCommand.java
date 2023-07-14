package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.OptionalParameter;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.payment.CardTransferTask;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;
import com.rssl.phizic.utils.MaskUtil;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 13.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * СМС-команда ПЕРЕВОД По Номеру карты
 * Перевод со счёта на чужую карту не работает!
 */
public class CardTransferCommand extends CommandBase
{
	/**
	 * Алиас продукта списания текущего пользователя (can be null)
	 */
	private String chargeOffAlias;

	/**
	 * Номер карты зачисления (never null)
	 */
	private String destinationCard;

	/**
	 * Сумма (never null)
	 */
	private BigDecimal amount;

	///////////////////////////////////////////////////////////////////////////

	@OptionalParameter
	void setChargeOffAlias(String chargeOffAlias)
	{
		this.chargeOffAlias = chargeOffAlias;
	}

	@MandatoryParameter
	void setDestinationCard(String destinationCard)
	{
		this.destinationCard = destinationCard;
	}

	@MandatoryParameter
	void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	///////////////////////////////////////////////////////////////////////////

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());
		PersonBankrollManager bankrollManager = getPersonBankrollManager();

		// noinspection unchecked
		BankrollProductLink senderProductLink = bankrollManager.findProductBySmsAlias(chargeOffAlias, CardLink.class);
		if (senderProductLink == null || !senderProductLink.getShowInSms())
			throw new UserErrorException(messageBuilder.buildCardTransferBadSenderError());

		CardTransferTask task = paymentManager.createCardTransferTask();
		task.setSenderProduct(senderProductLink);
		task.setReceiverCardNumber(destinationCard);
		task.setAmount(new Money(amount, getRURCurrency()));

		task.execute();
	}

	private Currency getRURCurrency()
	{
		Currency rurCurrency;
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			rurCurrency = currencyService.findByAlphabeticCode("RUR");
		}
		catch (GateException e)
		{
			throw new UserErrorException(e);
		}
		return rurCurrency;
	}

	@Override
	public String toString()
	{
		return String.format("ПЕРЕВОД_ПО_НОМЕРУ_КАРТЫ[%s -> %s, сумма: %s]",
				chargeOffAlias, MaskUtil.getCutCardNumberForLog(destinationCard), amount);
	}

	public String getCommandName()
	{
		return "CARD_TRANSFER";
	}
}
