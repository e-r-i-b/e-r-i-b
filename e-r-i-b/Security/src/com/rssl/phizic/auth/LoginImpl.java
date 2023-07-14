package com.rssl.phizic.auth;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.common.types.client.CSAType;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 20:42:01
 */
public class LoginImpl extends LoginBase implements Login
{
	private Long pinEnvelopeId;

	private UserVisitingMode lastUserVisitingMode;
	private CSAType csaType;

	///////////////////////////////////////////////////////////////////////////

	public Long getPinEnvelopeId()
	{
		return pinEnvelopeId;
	}

	public void setPinEnvelopeId(Long pinEnvelopeId)
	{
		this.pinEnvelopeId = pinEnvelopeId;
	}

	public UserVisitingMode getLastUserVisitingMode()
	{
		return lastUserVisitingMode;
	}

	public void setLastUserVisitingMode(UserVisitingMode lastUserVisitingMode)
	{
		this.lastUserVisitingMode = lastUserVisitingMode;
	}

	public CSAType getCsaType()
	{
		return csaType;
	}

	public void setCsaType(CSAType csaType)
	{
		this.csaType = csaType;
	}
}
