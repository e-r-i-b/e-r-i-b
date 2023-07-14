package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * @author Mescheryakova
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������� ������������ ����� ����� ������ ��������� �����
 * ����� ������ ������ ���� �������, ���� � ������� ���� ����� �� ����� ����� ������ ����� � ���������� �� �������� ��������� API
 * ���������� �����, �.�. � forms ��� ������� �  SecurityUtil
 */

public class CreditCardOfficeRequiredFieldValidator extends RequiredFieldValidator
{
	private final static String KEY  = "CreditCardOfficeOperation";
	private final static String SERVICE = "CreditCardOfficeService";

	public CreditCardOfficeRequiredFieldValidator()
	{
	}

	public CreditCardOfficeRequiredFieldValidator(String message)
	{
		super(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
    {
	    boolean isImpliesOperation = PermissionUtil.impliesOperation(KEY, SERVICE);
	    // �������� ���� � ������ ����������
	    if (!isImpliesOperation || ApplicationUtil.isMobileApi())
		    return true;
	    return super.validate(value);
    }
}
