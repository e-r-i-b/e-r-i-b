package com.rssl.phizgate.mobilebank.cache.techbreak;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Calendar;

/**
 * @author Puzikov
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 */

abstract class GetClientCacheEntryBase extends MbkCacheEntry
{
	private String cardNumber;
	private String firstName;
	private String fathersName;
	private String lastName;
	private String regNumber;
	private Calendar birthDate;
	private String cbCode;
	private String authIdt;
	private int contrStatus;
	private int addInfoCn;

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getFathersName()
	{
		return fathersName;
	}

	public void setFathersName(String fathersName)
	{
		this.fathersName = fathersName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getRegNumber()
	{
		return regNumber;
	}

	public void setRegNumber(String regNumber)
	{
		this.regNumber = regNumber;
	}

	public Calendar getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Calendar birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getCbCode()
	{
		return cbCode;
	}

	public void setCbCode(String cbCode)
	{
		this.cbCode = cbCode;
	}

	public String getAuthIdt()
	{
		return authIdt;
	}

	public void setAuthIdt(String authIdt)
	{
		this.authIdt = authIdt;
	}

	public int getContrStatus()
	{
		return contrStatus;
	}

	public void setContrStatus(int contrStatus)
	{
		this.contrStatus = contrStatus;
	}

	public int getAddInfoCn()
	{
		return addInfoCn;
	}

	public void setAddInfoCn(int addInfoCn)
	{
		this.addInfoCn = addInfoCn;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof GetClientCacheEntryBase))
			return false;

		GetClientCacheEntryBase that = (GetClientCacheEntryBase) obj;
		return new EqualsBuilder()
				.append(this.getAddInfoCn(), that.getAddInfoCn())
				.append(this.getAuthIdt(), that.getAuthIdt())
				.append(this.getBirthDate(), that.getBirthDate())
				.append(this.getCardNumber(), that.getCardNumber())
				.append(this.getCbCode(), that.getCbCode())
				.append(this.getContrStatus(), that.getContrStatus())
				.append(this.getFathersName(), that.getFathersName())
				.append(this.getFirstName(), that.getFirstName())
				.append(this.getLastName(), that.getLastName())
				.append(this.getRegNumber(), that.getRegNumber())
				.isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
				.append(this.getAddInfoCn())
				.append(this.getAuthIdt())
				.append(this.getBirthDate())
				.append(this.getCardNumber())
				.append(this.getCbCode())
				.append(this.getContrStatus())
				.append(this.getFathersName())
				.append(this.getFirstName())
				.append(this.getLastName())
				.append(this.getRegNumber())
				.toHashCode();
	}
}
