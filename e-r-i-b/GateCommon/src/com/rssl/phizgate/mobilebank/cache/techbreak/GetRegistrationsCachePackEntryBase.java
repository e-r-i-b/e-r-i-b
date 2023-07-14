package com.rssl.phizgate.mobilebank.cache.techbreak;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Jatsky
 * @ created 03.08.15
 * @ $Author$
 * @ $Revision$
 */

public abstract class GetRegistrationsCachePackEntryBase extends MbkCacheEntry
{
	private String strCards;
	private String strRetVal;

	@Override
	public String getCacheKey()
	{
		return strCards;
	}

	public String getStrCards()
	{
		return strCards;
	}

	public void setStrCards(String strCards)
	{
		this.strCards = strCards;
	}

	public String getStrRetVal()
	{
		return strRetVal;
	}

	public void setStrRetVal(String strRetVal)
	{
		this.strRetVal = strRetVal;
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
				.append(this.getStrCards(), that.getStrCardNumber())
				.append(this.getStrRetVal(), that.getStrRetStr())
				.isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
				.append(this.getStrCards())
				.append(this.getStrRetVal())
				.toHashCode();
	}
}
