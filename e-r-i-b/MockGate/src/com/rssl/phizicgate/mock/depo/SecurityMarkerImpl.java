package com.rssl.phizicgate.mock.depo;

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

	public SecurityMarkerImpl(String marker, Long remainder)
	{
		this.marker = marker;
		this.remainder = remainder;
	}

	public String getMarker()
	{
		return marker;
	}

	public Long getRemainder()
	{
		return remainder;
	}
}
