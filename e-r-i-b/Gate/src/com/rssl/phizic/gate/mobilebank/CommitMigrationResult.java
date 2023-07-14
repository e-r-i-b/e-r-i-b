package com.rssl.phizic.gate.mobilebank;

/**
 * Результат, возвращаемый по запросу коммита миграции в мбк.
 * @author Puzikov
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */

public class CommitMigrationResult
{
	//ИД Подключения МБК
	private String linkId;
	//Номер телефона Подключения в 11-значном формате.
	private String phoneNumber;
	//Номер Платёжной карты Подключения
	private String paymentCard;
	//Информация по абонентской плате
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
