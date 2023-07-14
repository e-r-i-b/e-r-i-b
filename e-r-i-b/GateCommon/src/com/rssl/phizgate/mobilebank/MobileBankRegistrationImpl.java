package com.rssl.phizgate.mobilebank;

import com.rssl.phizic.gate.mobilebank.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Erkin
 * @ created 22.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class MobileBankRegistrationImpl implements MobileBankRegistration
{
	private MobileBankCardInfo mainCardInfo;

	private List<MobileBankCardInfo> linkedCards;

	private MobileBankRegistrationStatus status;

	private MobileBankTariff tariff;

	public MobileBankCardInfo getMainCardInfo()
	{
		return mainCardInfo;
	}

	public void setMainCardInfo(MobileBankCardInfo mainCardInfo)
	{
		this.mainCardInfo = mainCardInfo;
	}

	public List<MobileBankCardInfo> getLinkedCards()
	{
		if (linkedCards == null)
			return null;
		return new ArrayList<MobileBankCardInfo>(linkedCards);
	}

	public void setLinkedCards(List<MobileBankCardInfo> linkedCards)
	{
		if (linkedCards == null)
			this.linkedCards = null;
		else this.linkedCards =
				new ArrayList<MobileBankCardInfo>(linkedCards);
	}

	public MobileBankRegistrationStatus getStatus()
	{
		return status;
	}

	public void setStatus(MobileBankRegistrationStatus status)
	{
		this.status = status;
	}

	public void setStatus(String status)
	{
		if (status == null || status.trim().length() == 0)
			return;
		this.status = Enum.valueOf(MobileBankRegistrationStatus.class, status);
	}

	public MobileBankTariff getTariff()
	{
		return tariff;
	}

	public void setTariff(MobileBankTariff tariff)
	{
		this.tariff = tariff;
	}

	public void setTariff(String tariff)
	{
		if (tariff == null || tariff.trim().length() == 0)
			return;
		this.tariff = Enum.valueOf(MobileBankTariff.class, tariff);
	}

	public int hashCode()
	{
		return mainCardInfo != null ? mainCardInfo.hashCode() : 0;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MobileBankRegistrationImpl that = (MobileBankRegistrationImpl) o;

		if (mainCardInfo == null || that.mainCardInfo == null)
			return false;

		return mainCardInfo.equals(that.mainCardInfo);
	}

	public String toString()
	{
		return "MobileBankRegistrationImpl{" +
				"mainCardInfo=" + mainCardInfo +
				", linkedCards=" + (linkedCards!=null ? Arrays.toString(linkedCards.toArray()) : "empty") +
				", tariff=" + tariff +
				", status=" + status +
				'}';
	}
}