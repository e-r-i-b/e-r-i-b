package com.rssl.phizic.gate.mobilebank;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * ���������� � ��������� �������� ����������� ����� �������
 * @author Puzikov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "client")
@XmlAccessorType(XmlAccessType.NONE)
public class ClientTariffInfo
{
	//1 � ������ �����; 0 � ��������� �����
	@XmlElement(name = "LinkTariff", required = true)
	private int linkTariff;

	//1 � ����������� ������������� �� ��������; 0 - ����������� �� �������������
	@XmlElement(name = "LinkPaymentBlockID", required = true)
	private int linkPaymentBlockID;

	//���� ������ �����������
	//�� ���������� � ���
	private Calendar firstRegistrationDate;

//	����� � ��� ��������� �����-�������, ���� �� ��� �� ��������
//	���
//	�����, ���� ����������� ������������� (������ ���������� ������� ���) ��� ���������� �� ������ ������������, �� ����� �� WAY4 �� �������
//	���
//	����� � ��� ���������� ������� ������,
	@XmlElement(name = "NextPaidPeriod", required = true)
	@XmlJavaTypeAdapter(MbkDateAdapter.class)
	private Calendar nextPaidPeriod;

	//����� �������� ����������� �����
	@XmlElement(name = "PayDate", required = true)
	@XmlJavaTypeAdapter(MbkDayAdapter.class)
	private int payDate;

	public int getLinkTariff()
	{
		return linkTariff;
	}

	public void setLinkTariff(int linkTariff)
	{
		this.linkTariff = linkTariff;
	}

	public int getLinkPaymentBlockID()
	{
		return linkPaymentBlockID;
	}

	public void setLinkPaymentBlockID(int linkPaymentBlockID)
	{
		this.linkPaymentBlockID = linkPaymentBlockID;
	}

	public Calendar getFirstRegistrationDate()
	{
		return firstRegistrationDate;
	}

	public void setFirstRegistrationDate(Calendar firstRegistrationDate)
	{
		this.firstRegistrationDate = firstRegistrationDate;
	}

	public Calendar getNextPaidPeriod()
	{
		return nextPaidPeriod;
	}

	public void setNextPaidPeriod(Calendar nextPaidPeriod)
	{
		this.nextPaidPeriod = nextPaidPeriod;
	}

	public int getPayDate()
	{
		return payDate;
	}

	public void setPayDate(int payDate)
	{
		this.payDate = payDate;
	}
}
