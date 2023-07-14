package com.rssl.phizgate.mobilebank.cache.techbreak;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Puzikov
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class GetRegistrationsCacheEntryBase extends MbkCacheEntry
{
	private String strCardNumber;
	private String strRetStr;

	@Override
	public String getCacheKey()
	{
		return strCardNumber;
	}

	public String getStrCardNumber()
	{
		return strCardNumber;
	}

	public void setStrCardNumber(String strCardNumber)
	{
		this.strCardNumber = strCardNumber;
	}

	public String getStrRetStr()
	{
		return strRetStr;
	}

	public void setStrRetStr(String strRetStr)
	{
		this.strRetStr = strRetStr;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof GetRegistrationsCacheEntryBase))
			return false;

		GetRegistrationsCacheEntryBase that = (GetRegistrationsCacheEntryBase) obj;
		return new EqualsBuilder()
				.append(this.getStrCardNumber(), that.getStrCardNumber())
				.append(this.getStrRetStr(), that.getStrRetStr())
				.isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
				.append(this.getStrCardNumber())
				.append(this.getStrRetStr())
				.toHashCode();
	}
}
