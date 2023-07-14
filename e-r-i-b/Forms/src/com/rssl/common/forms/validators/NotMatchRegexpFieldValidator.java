package com.rssl.common.forms.validators;

/**
 * ��������� �� ��, ��� �������� �� ������������� ���������
 * ������������, ����� ����� ���������, ��� ��������� ������� �� �����������
 *
 * @author egorova
 * @ created 24.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class NotMatchRegexpFieldValidator  extends RegexpFieldValidator
{
	public NotMatchRegexpFieldValidator()
	{
		super();
	}
	
	public NotMatchRegexpFieldValidator(String regexp)
	{
		super(regexp);
	}

    public NotMatchRegexpFieldValidator(String regexp, String message)
	{
		super(regexp, message);
	}

   public boolean validatePattern(String value)
    {
        return !super.validatePattern(value);
    }
}
