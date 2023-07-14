package com.rssl.phizic.web.atm.ext.sbrf.accounts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.ClientResourceHelper;
import com.rssl.phizic.business.clients.ClientResourcesService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.ima.GetIMAccountOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.web.atm.ShowAccountsAction;
import com.rssl.phizic.web.atm.ShowAccountsForm;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 09.04.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * получение списка продуктов.
 */
public class ShowAccountsExtendedAction extends ShowAccountsAction
{
	private static final ClientResourcesService clientResourceService = new ClientResourcesService();
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	// продукты
	private static enum Products {
		cards,
		accounts,
		loans,
		imaccounts,
		error;
	}

	private static final Map<Products, Class> CLASS_BY_PRODUCT = new HashMap<Products, Class>();

	static
	{
		CLASS_BY_PRODUCT.put(Products.cards, Card.class);
		CLASS_BY_PRODUCT.put(Products.accounts, Account.class);
		CLASS_BY_PRODUCT.put(Products.imaccounts, IMAccount.class);
		CLASS_BY_PRODUCT.put(Products.loans, Loan.class);
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        ActionMessages msgs = new ActionMessages();
	    ShowAccountsForm frm = (ShowAccountsForm) form;
        PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    ActivePerson person = personData.getPerson();
		//Запрошенные типы продуктов
		String[] requestedProductTypes = frm.getShowProductType();

	    // Собираем сет с запрашиваемыми продуктами
		Set<Products> products = getProductTypes(requestedProductTypes, person);

	    // Чтобы не запрашивать по каждому продукту отдельно
	    if (products.size() > 1)
	    {
		    // Смотрим, надо ли запросить GFL по продукту. Если да, то собираем в массив.
		    Class[] loadProducts = collectLoadProducts(products);
		    if (ArrayUtils.isNotEmpty(loadProducts))
		    {
			    // Запрашиваем GFL по требуемым продуктам
			    clientResourceService.updateResources(person, false, loadProducts);
		    }
	    }

	    if (checkAccess(GetLoanListOperation.class) && showProduct(Products.loans, products, requestedProductTypes))
            frm.setLoans(getPersonLoanLinks(frm));

	    if (checkAccess(GetAccountAbstractExtendedOperation.class) && checkAccess(GetAccountsOperation.class) && showProduct(Products.accounts, products, requestedProductTypes))
		    frm.setAccounts(getPersonAccountLinks(frm));

	    if (checkAccess(GetCardsOperation.class) && showProduct(Products.cards, products, requestedProductTypes))
	        frm.setCards(getCards(frm));

	    if (checkAccess("GetIMAccountShortAbstractOperation") && checkAccess("GetIMAccountOperation") && showProduct(Products.imaccounts, products, requestedProductTypes))
	        frm.setImAccounts(getPersonIMAccountLinks(frm));

        frm.setUser(person);

	    if (frm.isAllCardDown() && showProduct(Products.cards, products, requestedProductTypes))
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по картам из АБС временно недоступна. Повторите операцию позже.", false));

	    if (frm.isAllAccountDown() && showProduct(Products.accounts, products, requestedProductTypes))
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по вкладам из АБС временно недоступна. Повторите операцию позже.", false));

	    if (frm.isAllLoanDown() && showProduct(Products.loans, products, requestedProductTypes))
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по кредитам из АБС временно недоступна. Повторите операцию позже.", false));

	    if (frm.isAllIMAccountDown() && showProduct(Products.imaccounts, products, requestedProductTypes))
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по обезличенным металлическим счетам из АБС временно недоступна. Повторите операцию позже.", false));

	    if (msgs.size() != 0)
	        saveErrors(request, msgs);

        return mapping.findForward(FORWARD_SHOW);
    }

	private Set<Products> getProductTypes(String[] requestedProductTypes, ActivePerson person) throws BusinessException
	{
		if (ArrayUtils.isNotEmpty(requestedProductTypes))
		{
			Set<Products> allowedClientProducts = null;
			for (String productType : requestedProductTypes)
			{
				if(Products.valueOf(productType) == Products.loans)
					allowedClientProducts = getAllowedClientProducts(person, false);
				else
					allowedClientProducts = getAllowedClientProducts(person, true);
			}
			Set<Products> products = EnumSet.noneOf(Products.class);
			for (String productType : requestedProductTypes)
			{
				try
				{
					Products product = Products.valueOf(productType);
					if (allowedClientProducts.contains(product))
						products.add(product);
				}
				catch (IllegalArgumentException e)
				{
					LOG.error(productType + " - недопустимый тип продукта", e);
					products.add(Products.error);
				}
			}
			return products;
		}
		else
		{
			return getAllowedClientProducts(person, true);
		}
	}

	/**
	 * Получить типы продуктов, доступные клиенту, в зависимости от типа подключения клиента
	 * @param person - клиент
	 * @return список типов продуктов
	 * @throws BusinessException
	 */
	private Set<Products> getAllowedClientProducts(ActivePerson person, boolean checkReceiveLoansOnLogin) throws BusinessException
	{
		List<Class> clientProductClassList = ClientResourceHelper.getClientsProducts(person, checkReceiveLoansOnLogin).getFirst();
		Set<Products> allowedProducts = new HashSet<Products>();
		for (Products product : EnumSet.allOf(Products.class))
		{
			if (clientProductClassList.contains(CLASS_BY_PRODUCT.get(product)))
				allowedProducts.add(product);
		}

		return allowedProducts;
	}

	/**
	 *
	 * @param type  тип продукта
	 * @param products список переданных продуктов
	 * @return true если необходимо получить данные по этому типу продукта
	 */

	private boolean showProduct(Products type, Set<Products> products, String[] requestedProductTypes)
	{
		return ArrayUtils.isEmpty(requestedProductTypes) || products.contains(type);
	}

	protected List<CardLink> getCards(ShowAccountsForm form) throws BusinessException, BusinessLogicException
	{
		GetCardsOperation operationCards = createOperation(GetCardsOperation.class);
		//получаем все кардлинки клиента. Те кардлинки, которые прописаны в PersonContext.
		List<CardLink> personCardLinks = getPersonCardLinks(operationCards);
		form.setAllCardDown(personCardLinks.size()==0 && operationCards.isBackError());
		return personCardLinks;
	}

	protected List<CardLink> getPersonCardLinks(GetCardsOperation operationCards)
	{
		return operationCards.getPersonCardLinks();
	}

	protected List<LoanLink> getPersonLoanLinks(ShowAccountsForm form) throws BusinessException, BusinessLogicException
	{
		GetLoanListOperation operationLoans = createOperation(GetLoanListOperation.class);
		List<LoanLink> personLoanLinks = operationLoans.getAllLoans();
		form.setAllLoanDown(personLoanLinks.size() == 0 && operationLoans.isBackError());
		return personLoanLinks;
	}

	protected List<AccountLink> getPersonAccountLinks(ShowAccountsForm form)  throws BusinessException, BusinessLogicException
	{
		GetAccountsOperation operationAccounts = createOperation(GetAccountsOperation.class);
		List<AccountLink> personAccountLinks = operationAccounts.getAllActiveAndClosedAccounts();
		form.setAllAccountDown(personAccountLinks.size()==0 && operationAccounts.isBackError());
		return personAccountLinks;
	}

	private List<IMAccountLink> getPersonIMAccountLinks(ShowAccountsForm form) throws BusinessException, BusinessLogicException
	{
		GetIMAccountOperation operation = createOperation("GetIMAccountOperation");
		List<IMAccountLink> personIMAccountLinks = operation.getAllIMAccounts();
		form.setAllIMAccountDown(personIMAccountLinks.size() == 0 && operation.isBackError());
		return personIMAccountLinks;
	}

	private Class[] collectLoadProducts(Set<Products> products)
	{
		List<Class> loadClasses = new ArrayList<Class>();
		for (Products product : products)
		{
			if (needLoad(product))
			{
				// Собираем продукты для запроса GFL
				loadClasses.add(CLASS_BY_PRODUCT.get(product));
				// Выставляем признак необходимости запросить GFL по продукту в false
				cancelLoadProduct(product);
			}
		}

		return loadClasses.toArray(new Class[loadClasses.size()]);
	}

	private void cancelLoadProduct(Products loadProduct)
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		switch (loadProduct)
		{
			case accounts:
				personData.setNeedLoadAccounts(false);
				return;
			case imaccounts:
				personData.setNeedLoadIMAccounts(false);
				return;
			case loans:
				personData.setNeedLoadLoans(false);
				return;
			default:
				return;
		}
	}

	private boolean needLoad(Products product)
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		switch (product)
		{
			case accounts:
				return personData.isNeedLoadAccounts();
			case imaccounts:
				return personData.isNeedLoadIMAccounts();
			case loans:
				return personData.isNeedLoadLoans();
			default:
				return false;
		}
	}
}
