package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.helpers.CommonElementsHelper;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.StatusCode;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.ProductListRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.ProductListResponse;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist.AccountTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist.AccountsTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist.CardTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist.CardsTag;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

/**
 * Заполняет ответ на запрос списка продуктов
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ProductListResponseConstructor extends JAXBResponseConstructor<Request, ProductListResponse>
{
	private enum UserProductTypes
	{
		cards,
		accounts;
	}

	@Override protected ProductListResponse makeResponse(Request request) throws Exception
	{
		ProductListRequest rqst = (ProductListRequest) request;
		List<String> productTypes = rqst.getBody().getProductType();
		ProductListResponse response = new ProductListResponse();
		if (productTypes == null || productTypes.isEmpty() || productTypes.contains(UserProductTypes.cards.name()))
			response.setCards(collectCards());
		if (productTypes == null || productTypes.isEmpty() || productTypes.contains(UserProductTypes.accounts.name()))
			response.setAccounts(collectAccounts());
		return response;
	}

	private CardsTag collectCards() throws BusinessLogicException, BusinessException, SAXException
	{
		CardsTag cardsTag = new CardsTag();
		GetCardsOperation operationCards = createOperation(GetCardsOperation.class);
		List<CardLink> personCardLinks = operationCards.getPersonCardLinks();
		List<CardLink> personMainCardLinks = operationCards.getPersonMainCardLinks(personCardLinks);
		List<CardLink> personAddCards = operationCards.getPersonAdditionalCards(personCardLinks);
		List<CardLink> cardLinks = new ArrayList<CardLink>();

		List<CardLink> additionalCards = operationCards.getAdditionalCards(personMainCardLinks);
		cardLinks.addAll(personMainCardLinks);
		cardLinks.addAll(additionalCards);
		personAddCards.removeAll(additionalCards);
		cardLinks.addAll(personAddCards);

		if (cardLinks.size() == 0 && operationCards.isBackError())
			cardsTag.setStatus(new Status(StatusCode.GROUP_OPERATION_ERROR, "Информация по картам веменно недоступна"));
		else
		{
			cardsTag.setStatus(new Status(StatusCode.SUCCESS));
			List<CardTag> cards = new ArrayList<CardTag>();
			for (CardLink cardLink : cardLinks)
			{
				CardTag cardTag = new CardTag();
				CommonElementsHelper.fillCardTag(cardLink, cardTag);
				cards.add(cardTag);
			}
			cardsTag.setCards(cards);
		}
		return cardsTag;
	}

	private AccountsTag collectAccounts() throws BusinessLogicException, BusinessException, SAXException
	{
		AccountsTag accountsTag = new AccountsTag();
		GetAccountsOperation operationAccounts = createOperation(GetAccountsOperation.class);
		List<AccountLink> accountLinks = operationAccounts.getAccounts();
		if (accountLinks.size() == 0 && operationAccounts.isBackError())
			accountsTag.setStatus(new Status(StatusCode.GROUP_OPERATION_ERROR, "Информация по счетам веменно недоступна"));
		else
		{
			accountsTag.setStatus(new Status(StatusCode.SUCCESS));
			List<AccountTag> accounts = new ArrayList<AccountTag>();
			for (AccountLink accountLink : accountLinks)
			{
				AccountTag accountTag = new AccountTag();
				CommonElementsHelper.fillAccountTag(accountLink, accountTag);
				accounts.add(accountTag);
			}
			accountsTag.setAccounts(accounts);
		}
		return accountsTag;
	}
}
