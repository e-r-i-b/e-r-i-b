package com.rssl.phizic.gate.mobilebank;

/**
 * ���������, ������������ �� ������� ������� �������� � ���.
 * @author Puzikov
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */

public class CommitMigrationResult
{
	//�� ����������� ���
	private String linkId;
	//����� �������� ����������� � 11-������� �������.
	private String phoneNumber;
	//����� �������� ����� �����������
	private String paymentCard;
	//���������� �� ����������� �����
	private ClientTariffInfo tariffInfo;

	public String getLinkId()
	{
		return linkId;
	}

	public void setLinkId(String linkId)
	{
		this.linkId = linkId;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getPaymentCard()
	{
		return paymentCard;
	}

	public void setPaymentCard(String paymentCard)
	{
		this.paymentCard = paymentCard;
	}

	public ClientTariffInfo getTariffInfo()
	{
		return tariffInfo;
	}

	public void setTariffInfo(ClientTariffInfo tariffInfo)
	{
		this.tariffInfo = tariffInfo;
	}
}
