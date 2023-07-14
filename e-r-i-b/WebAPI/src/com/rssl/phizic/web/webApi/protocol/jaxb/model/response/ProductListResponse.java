package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist.AccountsTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist.CardsTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ответ на запрос списка продуктов
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "cards", "accounts"})
@XmlRootElement(name = "message")
public class ProductListResponse extends Response
{
	private CardsTag cards;
	private AccountsTag accounts;

	@XmlElement(name = "cards", required = true)
	public CardsTag getCards()
	{
		return cards;
	}

	public void setCards(CardsTag cards)
	{
		this.cards = cards;
	}

	@XmlElement(name = "accounts", required = true)
	public AccountsTag getAccounts()
	{
		return accounts;
	}

	public void setAccounts(AccountsTag accounts)
	{
		this.accounts = accounts;
	}
}
