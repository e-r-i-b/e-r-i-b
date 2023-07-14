package com.rssl.phizic.business.ermb;

import java.util.Calendar;

/**
 * ������������ ������ � �����������/��������� ��������� ����� ����
 * @author Rtischeva
 * @ created 29.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbClientTariffChangeStatisticRecord
{
	private Long id;  //�������������

	private Long ermbProfileId; //id ����-�������

	private Long ermbTariffId; //id ������

	private String ermbTariffName; //�������� ������

	private Calendar changeDate; //���� ����������� ������

	private String tb; //�� �������

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getErmbProfileId()
	{
		return ermbProfileId;
	}

	public void setErmbProfileId(Long ermbProfileId)
	{
		this.ermbProfileId = ermbProfileId;
	}

	public Long getErmbTariffId()
	{
		return ermbTariffId;
	}

	public void setErmbTariffId(Long ermbTariffId)
	{
		this.ermbTariffId = ermbTariffId;
	}

	public String getErmbTariffName()
	{
		return ermbTariffName;
	}

	public void setErmbTariffName(String ermbTariffName)
	{
		this.ermbTariffName = ermbTariffName;
	}

	public Calendar getChangeDate()
	{
		return changeDate;
	}

	public void setChangeDate(Calendar changeDate)
	{
		this.changeDate = changeDate;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
