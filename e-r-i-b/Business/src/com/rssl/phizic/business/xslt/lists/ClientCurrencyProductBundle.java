package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Содержит списки продуктов для построения справочников валют.
 * @author Gololobov
 * @ created 11.01.2012
 * @ $Author$
 * @ $Revision$
 */

class ClientCurrencyProductBundle
{
	private static final DepositProductService depositProductService = new DepositProductService();

	private List<CardLink> cards;
	private List<AccountLink> accounts;
	private List<IMAccountLink> imAccounts;
	private List<DepositProduct> depositProducts;
	private Long depositType;

	List<CardLink> getCards()
	{
		return cards;
	}

	void setCards(List<CardLink> cards)
	{
		this.cards = cards;
	}

	List<AccountLink> getAccounts()
	{
		return accounts;
	}

	void setAccounts(List<AccountLink> accounts)
	{
		this.accounts = accounts;
	}

	List<IMAccountLink> getImAccounts()
	{
		return imAccounts;
	}

	void setImAccounts(List<IMAccountLink> imAccounts)
	{
		this.imAccounts = imAccounts;
	}

	List<DepositProduct> getDepositProducts()
	{
		return depositProducts;
	}

	void setDepositProducts(List<DepositProduct> depositProducts)
	{
		this.depositProducts = depositProducts;
	}

	Long getDepositType()
	{
		return depositType;
	}

	void setDepositType(Long depositType)
	{
		this.depositType = depositType;
	}

	Set<Currency> getCurrenciesSet() throws BusinessException
	{
		//Список из валют
		Set<Currency> currencySet = new HashSet<Currency>();
		//Валюты карт
		if (!CollectionUtils.isEmpty(cards))
		{
			for (CardLink cardLink : cards)
				currencySet.add(cardLink.getCurrency());
		}
		//Валюты счетов
		if (!CollectionUtils.isEmpty(accounts))
		{
			for (AccountLink accountLink : accounts)
				currencySet.add(accountLink.getCurrency());
		}
		//Валюты ОМС
		if (!CollectionUtils.isEmpty(imAccounts))
		{
			for (IMAccountLink imAccountLink : imAccounts)
				currencySet.add(imAccountLink.getCurrency());
		}
		//Валюты депозитных продуктов
		if (!CollectionUtils.isEmpty(depositProducts))
		{
			CurrencyService currencyService    = GateSingleton.getFactory().service(CurrencyService.class);

			for (DepositProduct depositProduct : depositProducts)
			{
				try
				{
					Document document = XmlHelper.parse(depositProduct.getDescription());
					Element doc = document.getDocumentElement();
					NodeList nodeList = doc.getElementsByTagName("currencyCode");
					for (int i = 0; i < nodeList.getLength(); i++)
					{
						Element node = (Element) nodeList.item(i);
						String val = XmlHelper.getElementText(node);
						Currency curr = currencyService.findByAlphabeticCode(val);
						currencySet.add(curr);
					}
				}
				catch (IOException e)
				{
					throw new BusinessException(e);
				}
				catch (SAXException e)
				{
					throw new BusinessException(e);
				}
				catch (GateException e)
				{
					throw new BusinessException(e);
				}
				catch (ParserConfigurationException e)
				{
					throw new BusinessException(e);
				}
			}
		}
		if (depositType != null)
		{
			List<String> depositCurrencies = depositProductService.getDepositCurrencies(depositType);
			if (CollectionUtils.isNotEmpty(depositCurrencies))
			{
				CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
				for (String depositCurrency : depositCurrencies)
				{
					try
					{
						currencySet.add(currencyService.findByNumericCode(depositCurrency));
					}
					catch (GateException e)
					{
						throw new BusinessException(e);
					}
				}
			}
		}
		return currencySet;
	}
}