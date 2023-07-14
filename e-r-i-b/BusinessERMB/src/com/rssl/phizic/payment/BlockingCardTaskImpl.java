package com.rssl.phizic.payment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.gate.cms.BlockReason;
import com.rssl.phizic.messaging.PersonSmsMessanger;
import com.rssl.phizic.task.TaskNotReadyException;

import java.util.HashMap;
import java.util.Map;

/**
 * –еализаци€ платежной задачи "Ѕлокировка карты"
 * @author Rtischeva
 * @ created 14.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class BlockingCardTaskImpl extends PaymentTaskBase implements BlockingCardTask
{
	private String blockingProductAlias;

	private transient BankrollProductLink cardLink;

	/**
	 *  од причины блокировки карты:
	 *  0 Ц карта потер€на;
	 *  1 Ц карта украдена;
	 *  2 Ц карта оставлена в банкомате;
	 *  3 Ц иное
	 */
	private Integer blockCode;

	private BlockReason reason;

	//////////////////////////////////////////////////////////////////////

	public void setCardLink(BankrollProductLink cardLink)
	{
		this.cardLink = cardLink;
	}

	public void setBlockCode(Integer blockCode)
	{
		this.blockCode = blockCode;
	}

	protected String getFormName()
	{
		return FormConstants.BLOCKING_CARD_CLAIM;
	}

	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();

		if (cardLink != null)
			map.put(PaymentFieldKeys.BLOCKING_CARD, cardLink.getCode());

		if (blockCode != null)
			map.put(PaymentFieldKeys.BLOCKING_CARD_REASON, reason.name());

		return new MapValuesSource(map);
	}

	private BlockReason getBlockReason()
	{

		for (BlockReason reason : BlockReason.values())
			if (reason.ordinal() == blockCode.intValue())
				return reason;
		return null;
	}

	@Override
	protected void checkParameters() throws TaskNotReadyException
	{
		super.checkParameters();
		if (blockCode == null)
			throw new UserErrorException(new TextMessage("Ќеверный код блокировки карты\n" +
					"¬озможные значени€ кода:\n" +
					"0 Ц карта потер€на;\n" +
					"1 Ц карта украдена;\n" +
					"2 Ц карта оставлена в банкомате;\n" +
					"3 Ц иное"));
		reason = getBlockReason();
		if (reason == null)
			throw new UserErrorException(new TextMessage("Ќеверный код блокировки карты\n" +
					"¬озможные значени€ кода:\n" +
					"0 Ц карта потер€на;\n" +
					"1 Ц карта украдена;\n" +
					"2 Ц карта оставлена в банкомате;\n" +
					"3 Ц иное"));
	}

	public String getBlockingProductAlias()
	{
		return blockingProductAlias;
	}

	public void setBlockingProductAlias(String blockingProductAlias)
	{
		this.blockingProductAlias = blockingProductAlias;
	}

	@Override
	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.BLOCKING_CARD;
	}

	@Override
	public void confirmGranted()
	{
		super.confirmGranted();
		PersonBankrollManager bankrollManager = getPersonBankrollManager();
		BankrollProductLink productLink = bankrollManager.findProductBySmsAlias(blockingProductAlias, AccountLink.class, CardLink.class);
		try
		{
			PersonSmsMessanger messanger = getPersonSmsMessanger();
			if (productLink instanceof CardLink)
				messanger.sendSms(messageBuilder.buildCardBlockSuccessMessage(productLink));
			else
				messanger.sendSms(messageBuilder.buildBlockingProductNotFoundMessage());
		}
		catch (Exception e)
		{
			if(productLink instanceof CardLink)
				throw new UserErrorException(messageBuilder.buildNotBlockedCardMessage(productLink));

			else throw new UserErrorException(messageBuilder.buildBlockingProductNotFoundMessage());
		}
	}
}
