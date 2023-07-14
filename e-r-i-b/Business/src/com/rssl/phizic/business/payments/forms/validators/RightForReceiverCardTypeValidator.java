package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * ¬алидатор проверки прав клиента совершени€ перевода на определенные типы карт
 * @ author: Gololobov
 * @ created: 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class RightForReceiverCardTypeValidator extends FieldValidatorBase
{
	private static final String VISA_MONEY_TRANSFER_SERVICE =  "VisaMoneyTransferService";
	private static final String MASTERCARD_MONEY_TRANSFER_SERVICE = "MasterCardMoneyTransferService";
	private String message = "¬ам недоступна операци€ перевода на данную карту. ќбратитесь в банк.";

	public String getMessage()
	{
		return message;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (!PersonContext.isAvailable() || !ApplicationUtil.isMobileApi())
			return true;
		//≈сли перевод на карту VISA в другом банке
		if (value.equalsIgnoreCase(RurPayment.VISA_EXTERNAL_CARD_TRANSFER_TYPE_VALUE) && !PermissionUtil.impliesServiceRigid(VISA_MONEY_TRANSFER_SERVICE))
		{
			message = ConfigFactory.getConfig(PaymentsConfig.class).getVisaErrorMessage();
			return false;
		}
		//≈сли перевод на карту MASTERCARD в другом банке
		else if (value.equalsIgnoreCase(RurPayment.MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE) && !PermissionUtil.impliesServiceRigid(MASTERCARD_MONEY_TRANSFER_SERVICE))
		{
			message = ConfigFactory.getConfig(PaymentsConfig.class).getMastercardErrorMessage();
			return false;
		}
		return true;
	}
}
