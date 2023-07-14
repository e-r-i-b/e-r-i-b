package com.rssl.phizic.payment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.ermb.ErmbPaymentType;

import java.util.Collections;

/**
 * Реализация платежной задачи "Регистрация программы лояльности Спасибо от Сбербанка"
 * @author Puzikov
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyRegistrationPaymentTaskImpl extends PaymentTaskBase implements LoyaltyRegistrationPaymentTask
{
	private String phoneNumber;

	protected String getFormName()
	{
		return FormConstants.LOYALTY_PROGRAM_REGISTRATION_CLAIM;
	}

	protected FieldValuesSource createRequestFieldValuesSource()
	{
		String phoneHash = String.valueOf(phoneNumber.hashCode());
		return new MapValuesSource(Collections.singletonMap("phone", phoneHash));
	}

	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.LOYALTY_PROGRAM_REGISTRATION_CLAIM;
	}

	public void setPhone(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	protected boolean needConfirm()
	{
		return false;
	}

	@Override
	public void confirmGranted()
	{

	}
}
