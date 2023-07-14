package com.rssl.phizgate.mobilebank.cache.techbreak;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Puzikov
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ImsiCheckResultCacheEntry extends MbkCacheEntry
{
	static final String CACHE_NAME = "mobilebank-imsicheck-cache";

	private int messageId;
	private String phoneNumber;
	private Integer validationResult;

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

	public int getMessageId()
	{
		return messageId;
	}

	public void setMessageId(int messageId)
	{
		this.messageId = messageId;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Integer getValidationResult()
	{
		return validationResult;
	}

	public void setValidationResult(Integer validationResult)
	{
		this.validationResult = validationResult;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof ImsiCheckResultCacheEntry))
			return false;

		ImsiCheckResultCacheEntry that = (ImsiCheckResultCacheEntry) obj;
		return new EqualsBuilder()
				.append(this.getPhoneNumber(), that.getPhoneNumber())
				.append(this.getMessageId(), that.getMessageId())
				.append(this.getValidationResult(), that.getValidationResult())
				.isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
				.append(this.getPhoneNumber())
				.append(this.getMessageId())
				.append(this.getValidationResult())
				.toHashCode();
	}
}
