package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;
import com.rssl.phizic.payment.TemplatePaymentTask;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;

/**
 * Смс-команда оплаты по шаблону
 * @author Rtischeva
 * @created 14.10.13
 * @ $Author$
 * @ $Revision$
 */
public class TemplatePaymentCommand extends CommandBase
{

	/**
	 * шаблон
	 */
	private transient TemplateDocument template;

	/**
	 * алиас карты списания
	 */
	private String cardAlias;

	/**
	 * сумма
	 */
	private BigDecimal amount;


	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());
		TemplatePaymentTask task = paymentManager.createTemplatePaymentTask();
		task.setTemplate(template);

		if (StringHelper.isNotEmpty(cardAlias))
		{
			PersonBankrollManager bankrollManager = getPersonBankrollManager();
			BankrollProductLink cardLink = bankrollManager.findProductBySmsAlias(cardAlias, CardLink.class);
			if (cardLink == null || !cardLink.getShowInSms())
				throw new UserErrorException(messageBuilder.buildPaymentCommandIncorrectAliasMessage(getCardSmsAutoAliasLength()));
			task.setCardLink(cardLink);
			task.setCardAlias(cardAlias);
		}

		if (amount == null && template.getExactAmount() == null)
			throw new UserErrorException(messageBuilder.buildTemplatePaymentBadSumMessage());
		task.setAmount(amount);
		task.execute();
	}

	public TemplateDocument getTemplate()
	{
		return template;
	}

	public void setTemplate(TemplateDocument template)
	{
		this.template = template;
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

	public String getCommandName()
	{
		return "TEMPLATE_PAYMENT";
	}
}
