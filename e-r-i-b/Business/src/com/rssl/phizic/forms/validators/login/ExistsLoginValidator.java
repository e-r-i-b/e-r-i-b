package com.rssl.phizic.forms.validators.login;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.LoginAlreadyRegisteredException;
import com.rssl.auth.csa.wsclient.requests.info.ValidateLoginInfo;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.auth.modes.AuthenticationContext;

/**
 * ��������� �� �������� ����, ��������������� �� ����� ����� � ������� ��� ���,
 * �� ����������� �������, ������� ����������� �������� ������������.
 *
 * @author bogdanov
 * @ created 08.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class ExistsLoginValidator extends FieldValidatorBase
{
	public ExistsLoginValidator()
	{
		setMessage("��������� ���� ����� �����. ���������� ���������� ����� ��� ����������� � �������� ������");
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			ValidateLoginInfo validateLoginInfo = new ValidateLoginInfo(AuthenticationContext.getContext().getCSA_SID(), value, true);
			CSABackRequestHelper.sendValidateLoginRq(validateLoginInfo);
		}
		catch (BackException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (LoginAlreadyRegisteredException e)
		{
			return false;
		}
		catch (BackLogicException e) {
			throw new TemporalDocumentException(e);
		}
		return true;
	}
}
