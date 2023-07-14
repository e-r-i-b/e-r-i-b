package com.rssl.phizic.payment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.messaging.PersonSmsMessanger;
import com.rssl.phizic.task.TaskNotReadyException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gulov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public class CardIssueTaskImpl extends PaymentTaskBase implements CardIssueTask
{
	private String blockingProductAlias;

	private transient BankrollProductLink cardLink;

	/**
	 * Код перевыпуска (never null)
	 * 0 – Карта украдена;
	 * 1 – Карта утеряна;
	 * 2 – Компрометация;
	 * 3 – Утрата ПИН-кода
	 * 4 – Изъята банком/Порча или тех. неисправность
	 */
	private Integer issueCode;

	private String reason;

	public void setCardLink(BankrollProductLink cardLink)
	{
		this.cardLink = cardLink;
	}

	public void setIssueCode(Integer issueCode)
	{
		this.issueCode = issueCode;
	}

	public String getBlockingProductAlias()
	{
		return blockingProductAlias;
	}

	public void setBlockingProductAlias(String blockingProductAlias)
	{
		this.blockingProductAlias = blockingProductAlias;
	}

	private String getBlockReason()
	{
		if (issueCode.intValue() == 0)
			return "S";
		else if (issueCode.intValue() == 1)
			return "L";
		else if (issueCode.intValue() == 2)
			return "F";
		else if (issueCode.intValue() == 3)
			return "P";
		else if (issueCode.intValue() == 4)
			return "Y";
		throw new UserErrorException(messageBuilder.buildUnknownIssueCodeMessage());
	}

	@Override
	protected void checkParameters() throws TaskNotReadyException
	{
		super.checkParameters();
		reason = getBlockReason();
	}

	@Override
	protected String getFormName()
	{
		return FormConstants.REISSUE_CARD_CLAIM;
	}

	@Override
	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();

		if (cardLink != null)
		{
			map.put(PaymentFieldKeys.CARD_LINK_ISSUE, cardLink.getCode());
			CardLink link = (CardLink) cardLink;
			map.put(PaymentFieldKeys.DESTINATION_OFFICE_REGION, link.getGflTB());
			map.put(PaymentFieldKeys.DESTINATION_OFFICE_BRANCH, link.getGflOSB());
			map.put(PaymentFieldKeys.DESTINATION_OFFICE_OFFICE, link.getGflVSP());
		}

		if (reason != null)
			map.put(PaymentFieldKeys.REASON_ISSUE, reason);

		return new MapValuesSource(map);
	}

	@Override
	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.CARD_ISSUE;
	}

	@Override
	public void confirmGranted()
	{
		super.confirmGranted();
		PersonBankrollManager bankrollManager = getPersonBankrollManager();
		BankrollProductLink productLink = bankrollManager.findProductBySmsAlias(blockingProductAlias, CardLink.class);
		try
		{
			PersonSmsMessanger messanger = getPersonSmsMessanger();
			messanger.sendSms(messageBuilder.buildCardIssueSuccessMessage(productLink));
		}
		catch (Exception e)
		{
			throw new UserErrorException(messageBuilder.buildNotCardIssuedMessage(productLink));
		}
	}
}
