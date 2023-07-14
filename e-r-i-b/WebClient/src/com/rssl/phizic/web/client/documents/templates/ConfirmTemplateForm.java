package com.rssl.phizic.web.client.documents.templates;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.web.documents.templates.TemplateFormBase;

/**
 * Форма подтверждения шаблона документа
 *
 * @author khudyakov
 * @ created 20.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmTemplateForm extends TemplateFormBase
{
	private ConfirmStrategyType confirmStrategyType;
	private ConfirmStrategy confirmStrategy;
	private boolean anotherStrategyAvailable;


	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public boolean isAnotherStrategyAvailable()
	{
		return anotherStrategyAvailable;
	}

	public void setAnotherStrategyAvailable(boolean anotherStrategyAvailable)
	{
		this.anotherStrategyAvailable = anotherStrategyAvailable;
	}
}
