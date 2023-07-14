package com.rssl.auth.csa.wsclient.responses;

import java.util.Calendar;

/**
 * ���������� � ������
 * @author niculichev
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class SessionInfo
{
	private String SID;                 // ������������� �������� ������.
	private Calendar creationDate;      // ���� �������� ������
	private Calendar expireDate;        // ���� ���������� ������ (����� ��������� ���� ���� ������� �� ������ �� ����� �����������)
	private Calendar prevSessionDate;   // ���� �������� ���������� ������
	private String prevSID;             // ������������� ���������� ������

	public String getSID()
	{
		return SID;
	}

	public void setSID(String SID)
	{
		this.SID = SID;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public Calendar getExpireDate()
	{
		return expireDate;
	}

	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}

	public Calendar getPrevSessionDate()
	{
		return prevSessionDate;
	}

	public void setPrevSessionDate(Calendar prevSessionDate)
	{
		this.prevSessionDate = prevSessionDate;
	}

	public String getPrevSID()
	{
		return prevSID;
	}

	public void setPrevSID(String prevSID)
	{
		this.prevSID = prevSID;
	}
}
