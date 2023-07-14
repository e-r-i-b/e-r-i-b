package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.ConfirmRequest;

/**
 * @ author: Vagin
 * @ created: 04.06.2013
 * @ $Author
 * @ $Revision
 * Форма для сохранения шаблона из истории операций/ исполненого платежа.
 */
public class ConfirmCreateTemplateForm extends EditTemplateForm
{
	private ConfirmableObject confirmableObject;
	private ConfirmStrategy confirmStrategy;
	private ConfirmRequest confirmRequest;


	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmRequest getConfirmRequest()
	{
		return confirmRequest;
	}

	public void setConfirmRequest(ConfirmRequest confirmRequest)
	{
		this.confirmRequest = confirmRequest;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}
}
