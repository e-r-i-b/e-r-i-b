package com.rssl.phizic.web.pfp;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author mihaylov
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditPersonalFinanceProfileForm extends EditFormBase implements PassingPFPFormInterface
{
	private boolean hasPFP; // у клиента пройдено ПФП (возможно незавершенно)

	public boolean getHasPFP()
	{
		return hasPFP;
	}

	public void setHasPFP(boolean hasPFP)
	{
		this.hasPFP = hasPFP;
	}
}
