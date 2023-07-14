package com.rssl.phizic.forms.validators.password;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.auth.modes.AuthenticationContext;

/**
 * валидатор на проверку того, повторяется ли пароль за последние 3 месяца.
 * @author basharin
 * @ created 17.03.14
 * @ $Author$
 * @ $Revision$
 */

public class RepeatOldPasswordValidator extends FieldValidatorBase
{
	private ThreadLocal<String> message = new ThreadLocal<String>();

	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			CSABackRequestHelper.sendValidatePasswordRq(AuthenticationContext.getContext().getCSA_SID(), value);
		}
		catch (BackException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (BackLogicException e)
		{
			setMessage(e.getMessage());
			return false;
		}
		return true;
	}

	public String getMessage()
	{
		return message.get();
	}

	public void setMessage(String value)
	{
		message.set(value);
	}
}
