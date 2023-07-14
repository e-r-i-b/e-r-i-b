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
 * ��������� �������� ���� ������� ���������� �������� �� ������������ ���� ����
 * @ author: Gololobov
 * @ created: 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class RightForReceiverCardTypeValidator extends FieldValidatorBase
{
	private static final String VISA_MONEY_TRANSFER_SERVICE =  "VisaMoneyTransferService";
	private static final String MASTERCARD_MONEY_TRANSFER_SERVICE = "MasterCardMoneyTransferService";
	private String message = "��� ���������� �������� �������� �� ������ �����. ���������� � ����.";

	public String getMessage()
	{
		return message;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (!PersonContext.isAvailable() || !ApplicationUtil.isMobileApi())
			return true;
		//���� ������� �� ����� VISA � ������ �����
		if (value.equalsIgnoreCase(RurPayment.VISA_EXTERNAL_CARD_TRANSFER_TYPE_VALUE) && !PermissionUtil.impliesServiceRigid(VISA_MONEY_TRANSFER_SERVICE))
		{
			message = ConfigFactory.getConfig(PaymentsConfig.class).getVisaErrorMessage();
			return false;
		}
		//���� ������� �� ����� MASTERCARD � ������ �����
		else if (value.equalsIgnoreCase(RurPayment.MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE) && !PermissionUtil.impliesServiceRigid(MASTERCARD_MONEY_TRANSFER_SERVICE))
		{
			message = ConfigFactory.getConfig(PaymentsConfig.class).getMastercardErrorMessage();
			return false;
		}
		return true;
	}
}
