package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.OptionalParameter;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.payment.BlockingCardTask;
import com.rssl.phizic.payment.LossPassbookTask;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;
import com.rssl.phizic.security.ConfirmableTask;

/**
 * @author Gulov
 * @ created 10.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * СМС-команда БЛОКИРОВКА
 */
public class ProductBlockCommand extends CommandBase implements ConfirmableTask
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * Алиас блокируемого продукта (never null)
	 */
	private String productAlias;

	/**
	 * Код блокировки (необязательное поле)
	 * Поле является обязательным:
	 * 1) для блокировки карты,
	 * 2) для счета при условии что интеграция с внешней системой происходит не через шину (напрямую)
	 */
	private Integer blockCode;

	/**
	 * Линк продукта (не кладется в базу)
	 */
	private transient BankrollProductLink productLink;

	@MandatoryParameter
	public void setProductAlias(String productAlias)
	{
		this.productAlias = productAlias;
	}

	public String getProductAlias()
	{
		return productAlias;
	}

	@OptionalParameter
	public void setBlockCode(Integer blockCode)
	{
		this.blockCode = blockCode;
	}

	public BankrollProductLink getProductLink()
	{
		if(productLink == null)
		   productLink = findProductLink();

		return productLink;
	}

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());

		BankrollProductLink link = getProductLink();

		if (link == null || !link.getShowInSms())
			throw new UserErrorException(messageBuilder.buildBlockingProductNotFoundMessage());

		if (link instanceof AccountLink)
		{
			LossPassbookTask task = paymentManager.createLossPassbookTask();
			task.setLink(getProductLink());
			task.setBlockCode(blockCode);
			task.setBlockingProductAlias(productAlias);
			task.execute();
		}

		else if(link instanceof CardLink)
		{
			BlockingCardTask task = paymentManager.createBlockingCardTask();
			task.setCardLink(getProductLink());
			task.setBlockCode(blockCode);
			task.setBlockingProductAlias(productAlias);
			task.execute();
		}

		else
			throw new UserErrorException(messageBuilder.buildBlockingProductNotFoundMessage());
	}

	public void confirmGranted() {}

	private BankrollProductLink findProductLink()
	{
		PersonBankrollManager bankrollManager = getPersonBankrollManager();
		return bankrollManager.findProductBySmsAlias(productAlias, AccountLink.class, CardLink.class);
	}

	@Override
	public String toString()
	{
		if (blockCode != null)
			return String.format("БЛОКИРОВКА[%s, код причины: %s]", productAlias, blockCode);
		return String.format("БЛОКИРОВКА[%s]", productAlias);
	}

	public String getCommandName()
	{
		return "PRODUCT_BLOCK";
	}
}