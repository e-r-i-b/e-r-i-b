package com.rssl.phizic.payment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.utils.PhoneNumber;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация платежной задачи "отключение автоплатежа"
 * @author Rtischeva
 * @ created 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class RefuseAutoPaymentTaskImpl extends PaymentTaskBase implements RefuseAutoPaymentTask
{
	/**
	 * номер телефона
	 */
	private PhoneNumber phoneNumber;

	/**
	 * линк карты списания
	 */
	private transient CardLink cardLink;

	/**
	 * id линка автоплатежа
	 */
	private String autoPaymentLinkId;

	@Override
	protected String getFormName()
	{
		return FormConstants.REFUSE_AUTOPAYMENT_FORM;
	}

	@Override
	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("cardId", String.valueOf(cardLink.getId()));
		map.put("linkId", autoPaymentLinkId);
		return new MapValuesSource(map);
	}

	@Override
	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.REFUSE_AUTOPAYMENT;
	}

	@Override
	protected boolean needConfirm()
	{
		return false;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public void setCardLink(CardLink cardLink)
	{
		this.cardLink = cardLink;
	}

	public void setAutoPaymentLinkId(String autoPaymentLinkId)
	{
		this.autoPaymentLinkId = autoPaymentLinkId;
	}
}
