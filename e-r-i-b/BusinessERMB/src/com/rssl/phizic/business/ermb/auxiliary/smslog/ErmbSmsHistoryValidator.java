package com.rssl.phizic.business.ermb.auxiliary.smslog;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author Gulov
 * @ created 17.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������� ������� ��� ��������� ����.
 * ������ ������ ����� ���� �������� ���� �� ������ ��������, ���� �� �������.
 * ���������� true, � ����� �� �������:
 * 1) ��������� ����: �������, ���, ��� ��������, ���� ���������
 * 2) ��������� ����: �������, ���, ��� ��������, ���� ��������
 * 3) ��������� ����: �������
 * ��������� �������� ������ ������� �� �����������.
 */
public class ErmbSmsHistoryValidator extends MultiFieldsValidatorBase
{
	public boolean validate(Map values) throws TemporalDocumentException
	{
		String fio = (String) retrieveFieldValue(FilterField.FIO.value(), values);
		String documentType = (String) retrieveFieldValue(FilterField.DOCUMENT_TYPE.value(), values);
		String documentNumber = (String) retrieveFieldValue(FilterField.DOCUMENT_NUMBER.value(), values);
		String documentSeries = (String) retrieveFieldValue(FilterField.DOCUMENT_SERIES.value(), values);
		Date birthDay = (Date) retrieveFieldValue(FilterField.BIRTHDAY.value(), values);
		String phone = (String) retrieveFieldValue(FilterField.PHONE.value(), values);
		String tb = (String) retrieveFieldValue(FilterField.TB.value(), values);

		boolean clientFilled = StringUtils.isNotBlank(fio) || StringUtils.isNotBlank(tb) || birthDay != null
				|| StringUtils.isNotBlank(documentNumber) || StringUtils.isNotBlank(documentSeries);
		boolean phoneFilled = StringUtils.isNotBlank(phone);

		boolean phoneValid = phoneValid(phone);
		boolean clientValid = nameValid(fio) && isTbValid(tb) && (documentValid(documentType, documentNumber, documentSeries) || birthDayValid(birthDay));

		return (phoneValid && !clientFilled) ^ (clientValid && !phoneFilled);
	}

	private boolean nameValid(String fio)
	{
		if (StringUtils.trimToNull(fio) == null)
			return false;
		String[] name = fio.split(" ");
		return name.length > 1;
	}

	private boolean documentValid(String documentType, String documentNumber, String documentSeries)
	{
		// ��������� ���� ��� ���������
		boolean result = StringUtils.trimToNull(documentType) != null;
		// ��������� ���� ����� ���������
		result = result && (StringUtils.trimToNull(documentNumber) != null);
		// ��������� ���� ����� ���������
		result = result && (StringUtils.trimToNull(documentSeries) != null);
		return result;
	}

	private boolean birthDayValid(Date birthDay)
	{
		return birthDay != null; 
	}

	private boolean phoneValid(String phone)
	{
		return StringUtils.trimToNull(phone) != null;
	}

	private boolean isTbValid(String tb)
	{
		return StringUtils.isNotBlank(tb);
	}
}
