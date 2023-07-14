package com.rssl.phizgate.mobilebank;

import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration3;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 17.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankRegistration3Impl extends MobileBankRegistrationImpl implements MobileBankRegistration3
{
	private Calendar lastRegistrationDate;
	private ChannelType channelType;

	public MobileBankRegistration3Impl()
	{
	}

	public MobileBankRegistration3Impl(MobileBankRegistration registration)
	{
		setLinkedCards(registration.getLinkedCards());
		setMainCardInfo(registration.getMainCardInfo());
		setStatus(registration.getStatus());
		setTariff(registration.getTariff());
	}

	public Calendar getLastRegistrationDate()
	{
		return lastRegistrationDate;
	}

	public void setLastRegistrationDate(Calendar lastRegistrationDate)
	{
		this.lastRegistrationDate = lastRegistrationDate;
	}

	public ChannelType getChannelType()
	{
		return channelType;
	}

	public void setChannelType(ChannelType channelType)
	{
		this.channelType = channelType;
	}

	public void setChannelType(String channelType)
	{
		if (channelType == null || channelType.trim().length() == 0)
			return;
		this.channelType = Enum.valueOf(ChannelType.class, channelType);
	}
}
