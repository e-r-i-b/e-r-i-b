package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author eMakarov
 * @ created 17.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class SmsPasswordConfirmRequest extends OneTimePasswordConfirmRequest
{
	public SmsPasswordConfirmRequest() {}

	public SmsPasswordConfirmRequest(ConfirmableObject confirmableObject)
	{
		super(confirmableObject);
	}

	public ConfirmStrategyType getStrategyType()
	{
		return ConfirmStrategyType.sms;
	}

}
