package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * @author gladishev
 * @ created 17.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class SBRFTaxPeriodValidator extends TaxPeriodValidator
{
	public static final String FIELD_PERIOD_TYPE = "periodType";
	public static final String FIELD_PERIOD = "period";

	private static final String DATE_PARTS_SEPARATOR = "/";

	///////////////////////////////////////////////////////////////////////////

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String periodType = (String) retrieveFieldValue(FIELD_PERIOD_TYPE, values);
		String period = ((String) retrieveFieldValue(FIELD_PERIOD, values));

		if ("��".equals(periodType))
			return checkPeriodAsMonth(period);
		if ("��".equals(periodType))
			return checkPeriodAsQuarter(period);
		if ("��".equals(periodType))
			return checkPeriodAsHalfYear(period);
		if ("��".equals(periodType))
			return checkPeriodAsYear(period);

		throw new TemporalDocumentException("Unknown period type: " + periodType);
	}

	private boolean checkPeriodAsMonth(String period)
	{
		String[] parts = period.split(DATE_PARTS_SEPARATOR);
		if (parts.length != 2)
			return false;
		return checkBorder(parts[0], 1, 12) && checkYear(parts[1]);
	}

	private boolean checkPeriodAsQuarter(String period)
	{
		String[] parts = period.split(DATE_PARTS_SEPARATOR);
		if (parts.length != 2)
			return false;
		return checkBorder(parts[0], 1, 4) && checkYear(parts[1]);
	}

	private boolean checkPeriodAsHalfYear(String period)
	{
		String[] parts = period.split(DATE_PARTS_SEPARATOR);
		if (parts.length != 2)
			return false;
		return checkBorder(parts[0], 1, 2) && checkYear(parts[1]);
	}

	private boolean checkPeriodAsYear(String period)
	{
		String[] parts = period.split(DATE_PARTS_SEPARATOR);
		switch (parts.length) {
			case 1:
				// ������ ������ � ���� ����
				return checkYear(parts[0]);
			case 2:
				// ������ ������ � ���� XX/����
				// XX ����������
				return checkYear(parts[1]);
		}
		return false;
	}

	private boolean checkYear(String yearAsString)
	{
		return checkBorder(yearAsString, minYear, maxYear);
	}
}
