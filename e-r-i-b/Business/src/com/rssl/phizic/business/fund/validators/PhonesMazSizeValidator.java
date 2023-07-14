package com.rssl.phizic.business.fund.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.common.types.fund.Constants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;

/**
 * @author osminin
 * @ created 17.04.15
 * @ $Author$
 * @ $Revision$
 *
 * �������� �� ������������ ������ ������ ���������
 */
public class PhonesMazSizeValidator extends FieldValidatorBase
{
	private static final String MESSAGE = "�� ��������� ������������ ���������� ��������� � �������. ����������, ������� �� ����� %s ���������.";

	private int maxSize;

	/**
	 * ctor
	 */
	public PhonesMazSizeValidator()
	{
		maxSize = ConfigFactory.getConfig(MobileApiConfig.class).getFundSenderCountDailyLimit();
		setMessage(String.format(MESSAGE, maxSize));
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		String[] phonesArray = value.split(Constants.INITIATOR_PHONES_DELIMITER);
		return phonesArray.length <= maxSize;
	}
}
