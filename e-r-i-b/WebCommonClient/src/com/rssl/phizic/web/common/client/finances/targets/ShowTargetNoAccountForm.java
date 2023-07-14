package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.phizic.business.finances.targets.AccountTarget;

/**
 * @author lepihina
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowTargetNoAccountForm extends EditTargetForm
{
	AccountTarget target;

	public AccountTarget getTarget()
	{
		return target;
	}

	public void setTarget(AccountTarget target)
	{
		this.target = target;
	}
}
