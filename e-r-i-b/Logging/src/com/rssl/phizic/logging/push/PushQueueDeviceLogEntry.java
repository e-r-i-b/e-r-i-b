package com.rssl.phizic.logging.push;

import java.util.Calendar;

/**
 * ������ � ������ ���������� �� ������������ ��������� ��� push-���������
 * @author basharin
 * @ created 31.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushQueueDeviceLogEntry implements StatisticLogEntry
{
	private Long id;
	private Calendar creationDate;                       //����� �������� ���������
	private String clientId;                             //������������� �������
	private String mguidHash;                            //Hash ������� SHA-1 �� �������� MGUID
	private PushDeviceStatus type;                       //������ ����������� push ����������� � ����������

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getMguidHash()
	{
		return mguidHash;
	}

	public void setMguidHash(String mguidHash)
	{
		this.mguidHash = mguidHash;
	}

	public PushDeviceStatus getType()
	{
		return type;
	}

	public void setType(PushDeviceStatus type)
	{
		this.type = type;
	}
}
