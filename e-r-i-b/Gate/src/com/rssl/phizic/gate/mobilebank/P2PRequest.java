package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;
import com.rssl.phizic.utils.jaxb.MobileInternationalPhoneNumberXmlAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Gulov
 * @ created 17.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �� ����������� ����� �� ������ �������� � �������� (P2P).
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
@XmlType(name = "MBK_P2P_RequestType")
@XmlRootElement(name = "MBK_P2P_Request")
@XmlAccessorType(XmlAccessType.FIELD)
public final class P2PRequest
{
	/**
	 * ������������� ������
	 */
	public int id;

	/**
	 * ����� �������� �������, �� �������� ���� ������ ���������� �����-����������
	 */
	@XmlJavaTypeAdapter(MobileInternationalPhoneNumberXmlAdapter.class)
	public PhoneNumber phone;

	/**
	 * ��� ����� �����-����������� ������� (������������ � ��������� ������ �����)
	 */
	public int tb;

	/**
	 * ����� ��������� �����-����������� ������� (������������ � ��������� ������ �����)
	 */
	public int branch;

	/**
	 * �����, ����� ��� ������� ������ � ���������
	 */
	@XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
	public Calendar creation;


	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public PhoneNumber getPhone()
	{
		return phone;
	}

	public void setPhone(PhoneNumber phone)
	{
		this.phone = phone;
	}

	public int getTb()
	{
		return tb;
	}

	public void setTb(int tb)
	{
		this.tb = tb;
	}

	public int getBranch()
	{
		return branch;
	}

	public void setBranch(int branch)
	{
		this.branch = branch;
	}

	public Calendar getCreation()
	{
		return creation;
	}

	public void setCreation(Calendar creation)
	{
		this.creation = creation;
	}
}
