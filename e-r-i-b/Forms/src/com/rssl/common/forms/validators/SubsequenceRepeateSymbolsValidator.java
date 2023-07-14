package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;

/**
 * ���������, �����������, ��� ���������� ������ ������ �������� �� ��������� �������� �����������
 * @author niculichev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class SubsequenceRepeateSymbolsValidator extends FieldValidatorBase
{
	private static final String MAX_COUNT_REPEATE = "maxCountRepeate";
	private static final String CASE_SENSITIVE = "caseSensitive";

	private int maxCountRepeate;
	private boolean caseSensitive;

	public void setParameter(String name, String value)
	{
		if (name.equalsIgnoreCase(MAX_COUNT_REPEATE))
		{
			maxCountRepeate = Integer.valueOf(value);
		}
		else if(name.equalsIgnoreCase(CASE_SENSITIVE))
		{
			caseSensitive = Boolean.parseBoolean(value);
		}
	}

    public String getParameter(String name)
    {
	    if (name.equalsIgnoreCase(MAX_COUNT_REPEATE))
	    {
		    return Integer.toString(maxCountRepeate);
	    }

		if(name.equalsIgnoreCase(CASE_SENSITIVE))
		{
			return Boolean.toString(caseSensitive);
		}

        return null;
    }

	/**
	 * ����������� �� ���������
	 */
	public SubsequenceRepeateSymbolsValidator()
	{
	}

	/**
	 * @param maxCountRepeate ����������� ���������� ���������� ����������
	 * @param message ��������� �� ������
	 */
	public SubsequenceRepeateSymbolsValidator(int maxCountRepeate, String message)
	{
		this(maxCountRepeate, message, false);
	}

	/**
	 * @param maxCountRepeate ����������� ���������� ���������� ����������
	 * @param message ��������� �� ������
	 * @param caseSensitive true - �������������� � ��������
	 */
	public SubsequenceRepeateSymbolsValidator(int maxCountRepeate, String message, boolean caseSensitive)
	{
		this.maxCountRepeate = maxCountRepeate;
		this.caseSensitive = caseSensitive;
		setMessage(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if(StringHelper.isEmpty(value))
			return true;

		// �� ����������, ��� �������� ������� ���������� ��������
		if(maxCountRepeate <= 1)
			return true;

		return StringHelper.maxCountRepeateChar(value, caseSensitive) <= maxCountRepeate;
	}
}
