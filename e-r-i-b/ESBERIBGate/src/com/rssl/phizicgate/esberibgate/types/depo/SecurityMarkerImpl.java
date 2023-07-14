package com.rssl.phizicgate.esberibgate.types.depo;

import com.rssl.phizic.gate.depo.SecurityMarker;

/**
 * @author mihaylov
 * @ created 20.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class SecurityMarkerImpl implements SecurityMarker
{
	private String marker;
	private Long remainder;

	public String getMarker()
	{
		return marker;
	}

	public void setMarker(String marker)
	{
		this.marker = marker;
	}

	public Long getRemainder()
	{
		return remainder;
	}

	public void setRemainder(Long remainder)
	{
		this.remainder = remainder;
	}
}
