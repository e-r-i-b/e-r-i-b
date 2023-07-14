package com.rssl.phizicgate.mobilebank;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Jatsky
 * @ created 03.08.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Атрибуты регистрации
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "LINK")
public class RegistrationInfoPack
{
	/**
	 * Номер основной карты
	 */
	@XmlAttribute(name = "RC")
	private String cardNumber;

	/**
	 * Статус регистрации
	 */
	@XmlAttribute(name = "RST")
	private String status;

	/**
	 * Тариф
	 */
	@XmlAttribute(name = "TF")
	private String tariff;

	/**
	 * дата последней регистрации
	 */
	@XmlElement(name = "DATE", required = false)
	private String date;

	/**
	 * источник регистрации
	 */
	@XmlElement(name = "SRC", required = false)
	private String src;

	/**
	 * Номер платежной карты в связке
	 */
	@XmlElement(name = "PAYCARD", required = true)
	private String payCard;

	/**
	 * Номер телефона
	 */
	@XmlElement(name = "PHONE", required = true)
	private RegistrationInfoPhone phone;

	/**
	 * Коллекция информационных карт
	 */
	@XmlElementWrapper(name = "CARDS", required = true)
	@XmlElement(name = "CARD", required = true)
	private List<RegistrationInfoCard> cardList;

	public RegistrationInfoPack()
	{
	}

	///////////////////////////////////////////////////////////////////////////
	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getTariff()
	{
		return tariff;
	}

	public void setTariff(String tariff)
	{
		this.tariff = tariff;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getSrc()
	{
		return src;
	}

	public void setSrc(String src)
	{
		this.src = src;
	}

	public String getPayCard()
	{
		return payCard;
	}

	public void setPayCard(String payCard)
	{
		this.payCard = payCard;
	}

	public RegistrationInfoPhone getPhone()
	{
		return phone;
	}

	public void setPhone(RegistrationInfoPhone phone)
	{
		this.phone = phone;
	}

	public List<RegistrationInfoCard> getCardList()
	{
		return cardList;
	}

	public void setCardList(List<RegistrationInfoCard> cardList)
	{
		this.cardList = cardList;
	}
}

