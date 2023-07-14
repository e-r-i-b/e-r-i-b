package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.gate.mobilebank.MobileBankTariff;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Erkin
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �� ����������� � ������ "��������� ����"
 */
public class MobileBankRegistrationClaim implements ConfirmableObject
{
	private Long id;

	/**
	 * ������������� ������ ������������, ����������� ������
	 */
	private Long loginId;

	/**
	 * ���� �����������
	 */
	private Calendar date;

	/**
	 * ����� �����������
	 */
	private MobileBankTariff tariff;

	/**
	 * �� �������, ����������� ������ �� �����������
	 */
	private String tb;

	/**
	 * ����� �������� � ������ �� �����������
	 */
	private String phoneNumber;

	/**
	 * ����� ����� � ������ �� �����������
	 */
	private String cardNumber;

	/**
	 * ������ "������ ���������" (������������)
	 */
	private boolean completed;

	///////////////////////////////////////////////////////////////////////////

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public MobileBankTariff getTariff()
	{
		return tariff;
	}

	public void setTariff(MobileBankTariff tariff)
	{
		this.tariff = tariff;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public boolean isCompleted()
	{
		return completed;
	}

	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}

	public byte[] getSignableObject()
	{
		return null;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{

	}

}
