package com.rssl.phizic.business.resources;

import com.rssl.phizic.gate.OTPRestriction;

/**
 * @author lukina
 * @ created 11.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class OTPRestrictionImpl   implements OTPRestriction
{
	private Boolean OTPGet;
	private Boolean OTPUse;

	public Boolean isOTPGet()
	{
		return OTPGet;
	}

	public void setOTPGet(Boolean OTPGet)
	{
		this.OTPGet = OTPGet;
	}

	public Boolean isOTPUse()
	{
		return OTPUse;
	}

	public void setOTPUse(Boolean OTPUse)
	{
		this.OTPUse = OTPUse;
	}
}
