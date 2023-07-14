package com.rssl.phizic.gate.ermb;

import com.rssl.phizic.common.types.ermb.ErmbStatus;
import org.apache.commons.lang.StringUtils;

/**
 * @author Puzikov
 * @ created 10.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ErmbInfoImpl implements ErmbInfo
{
	private String activePhone;
	private ErmbStatus status;

	public ErmbInfoImpl()
	{
	}

	public ErmbInfoImpl(String activePhone, ErmbStatus status)
	{
		this.activePhone = activePhone;
		this.status = status;
	}

	public String getActivePhone()
	{
		return activePhone;
	}

	public void setActivePhone(String activePhone)
	{
		this.activePhone = activePhone;
	}

	public ErmbStatus getStatus()
	{
		return status;
	}

	public void setStatus(ErmbStatus status)
	{
		this.status = status;
	}

	public void setStatus(String status)
	{
		if (StringUtils.isBlank(status))
		{
			return;
		}
		this.status = ErmbStatus.valueOf(status);
	}
}
