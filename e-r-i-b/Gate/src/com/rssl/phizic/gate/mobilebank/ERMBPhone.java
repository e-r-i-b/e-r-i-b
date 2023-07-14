package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.utils.PhoneNumber;

import java.util.Calendar;

/**
 * @author Nady
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� � ����, ������� ���� ��� �����-���� ��� � ������� ���� (��� ������ ������������� ���� ��������� � ���)
 */
public class ERMBPhone
{
	private PhoneNumber phoneNumber; //����� ��������
	private boolean phoneUsage; //������ �������� (������������ ������� ��� ���): 1- ������� ������������ � ����, 0 - ������� �� ������������ � ����
	private Calendar lastModified; //����� ���������� ��������� (���� ����� �������)
	private Calendar lastUpload; //����� ��������� �������� �������� �������� � ��� (���� ������� �����)

	public ERMBPhone()
	{
	}

	public ERMBPhone(PhoneNumber phoneNumber, boolean phoneUsage, Calendar lastModified, Calendar lastUpload)
	{
		this.phoneNumber = phoneNumber;
		this.phoneUsage = phoneUsage;
		this.lastModified = lastModified;
		this.lastUpload = lastUpload;
	}

	public PhoneNumber getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public boolean isPhoneUsage()
	{
		return phoneUsage;
	}

	public void setPhoneUsage(boolean phoneUsage)
	{
		this.phoneUsage = phoneUsage;
	}

	public Calendar getLastModified()
	{
		return lastModified;
	}

	public void setLastModified(Calendar lastModified)
	{
		this.lastModified = lastModified;
	}

	public Calendar getLastUpload()
	{
		return lastUpload;
	}

	public void setLastUpload(Calendar lastUpload)
	{
		this.lastUpload = lastUpload;
	}
}
