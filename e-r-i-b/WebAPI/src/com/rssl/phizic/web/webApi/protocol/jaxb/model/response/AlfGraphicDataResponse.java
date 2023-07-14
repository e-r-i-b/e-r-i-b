package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.AccountTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.CardTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.IMAccountTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.SecurityAccountTag;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ответ содержащий информацию для отображения диаграммы «Доступные средства»
 *
 * @author Balovtsev
 * @since 16.05.2014
 */
@XmlType(propOrder = {"status", "cards", "accounts", "imaccounts", "securityAccounts"})
@XmlRootElement(name = "message")
public class AlfGraphicDataResponse extends Response
{
	private List<CardTag>      cards;
	private List<AccountTag>   accounts;
	private List<IMAccountTag> imaccounts;
	private List<SecurityAccountTag> securityAccounts;

	/**
	 * @return Карты
	 */
	@XmlElementWrapper(name = "cards", required = false)
	@XmlElement(name = "card", required = true)
	public List<CardTag> getCards()
	{
		return cards;
	}

	/**
	 * @return Счета/вклады
	 */
	@XmlElementWrapper(name = "accounts", required = false)
	@XmlElement(name = "account", required = true)
	public List<AccountTag> getAccounts()
	{
		return accounts;
	}

	/**
	 * @return Обезличенные металлические счета(ОМС)
	 */
	@XmlElementWrapper(name = "imaccounts", required = false)
	@XmlElement(name = "imaccount", required = true)
	public List<IMAccountTag> getImaccounts()
	{
		return imaccounts;
	}

	/**
	 * @return Сберегательные сертификаты
	 */
	@XmlElementWrapper(name = "securityAccounts", required = false)
	@XmlElement(name = "securityAccount", required = true)
	public List<SecurityAccountTag> getSecurityAccounts()
	{
		return securityAccounts;
	}

	public void setSecurityAccounts(List<SecurityAccountTag> securityAccounts)
	{
		this.securityAccounts = securityAccounts;
	}

	public void setCards(List<CardTag> cards)
	{
		this.cards = cards;
	}

	public void setAccounts(List<AccountTag> accounts)
	{
		this.accounts = accounts;
	}

	public void setImaccounts(List<IMAccountTag> imaccounts)
	{
		this.imaccounts = imaccounts;
	}
}
