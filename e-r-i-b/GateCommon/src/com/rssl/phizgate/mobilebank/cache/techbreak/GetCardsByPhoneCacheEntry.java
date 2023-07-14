package com.rssl.phizgate.mobilebank.cache.techbreak;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author Puzikov
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 */

public class GetCardsByPhoneCacheEntry extends MbkCacheEntry
{
	public static final String CACHE_NAME = "mobilebank-cardsbyphone-cache";

	private String phoneNumber;
	private String resultSet;

	@Override
	String getCacheKey()
	{
		return phoneNumber;
	}

	@Override
	String getAppServerCacheName()
	{
		return CACHE_NAME;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getResultSet()
	{
		return resultSet;
	}

	public void setResultSet(String resultSet)
	{
		this.resultSet = resultSet;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof GetCardsByPhoneCacheEntry))
			return false;

		GetCardsByPhoneCacheEntry that = (GetCardsByPhoneCacheEntry) obj;
		return new EqualsBuilder()
				.append(this.getPhoneNumber(), that.getPhoneNumber())
				.append(this.getResultSet(), that.getResultSet())
				.isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
				.append(this.getPhoneNumber())
				.append(this.getResultSet())
				.toHashCode();
	}
}
