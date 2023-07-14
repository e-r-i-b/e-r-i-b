package com.rssl.phizic.business.ermb;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * User: moshenko
 * Date: 15.02.2013
 * Time: 11:22:35
 * Еще не привязанный к пользователю телефон ЕРМБ
 */
public class ErmbTmpPhone implements ConfirmableObject
{
   	private Long id;
	private String number;

	public ErmbTmpPhone()
	{
	}

	public ErmbTmpPhone(String number)
	{
		this.number = number;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return Номер телефона
	 */
	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}


	public byte[] getSignableObject()
	{
		return new byte[0];
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{

	}

	@Override
	public int hashCode()
	{
		if (number == null)
			return super.hashCode();

		return number.hashCode();
	}

	@Override
	public boolean equals (Object o)
    {
        if (this == o)
	        return true;

        if (o == null)
	        return false;

        if (!(o instanceof ErmbTmpPhone))
	        return false;

	    ErmbTmpPhone that = (ErmbTmpPhone) o;

	    PhoneNumber thisPhoneNumber = PhoneNumber.fromString(this.number);
	    PhoneNumber thatPhoneNumber = PhoneNumber.fromString(that.number);
	    return thisPhoneNumber.equals(thatPhoneNumber);
    }

}
