package com.rssl.phizic.web.common.mobile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.finances.targets.GetTargetOperation;
import com.rssl.phizic.operations.ima.GetIMAccountOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.util.MobileApiWebUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Получение списка продуктов
 * @author Omeliyanchuk
 * @ created 09.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowAccountsExtendedAction extends OperationalActionBase
{
	private static final String PRODUCT_TYPE_DELIMETER = ",";

	// продукты
	static enum Products {
		cards,
		accounts,
		loans,
		imaccounts;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        ActionMessages msgs = new ActionMessages();
	    ShowAccountsForm frm = (ShowAccountsForm) form;
        PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    ActivePerson person = personData.getPerson();

	    String typeRequestParam = frm.getShowProductType();
	    String[] productTypeArray = StringHelper.isEmpty(typeRequestParam) ? ArrayUtils.EMPTY_STRING_ARRAY : typeRequestParam.split(PRODUCT_TYPE_DELIMETER);
	    Set<Products> products = EnumSet.noneOf(Products.class);
	    for (String productType : productTypeArray)
	    {
		    try
		    {
			    products.add(Products.valueOf(productType));
		    }
			catch (IllegalArgumentException ignore)
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(productType+" - недопустимый тип продукта", false));
			}
	    }

	    if (checkAccess(GetLoanListOperation.class) && showProduct(Products.loans, products))
            frm.setLoans(getPersonLoanLinks(frm));

	    if (checkAccess(GetAccountAbstractExtendedOperation.class) && checkAccess(GetAccountsOperation.class) && showProduct(Products.accounts, products))
	    {
		    frm.setAccounts(getPersonAccountLinks(frm));

		    if (checkAccess(GetTargetOperation.class))
		    {
				setTargets(frm);
		    }
	    }

	    if (checkAccess(GetCardsOperation.class) && showProduct(Products.cards, products))
	        setCardsInfo(frm);

	    if (checkAccess("GetIMAccountShortAbstractOperation") && checkAccess("GetIMAccountOperation") && showProduct(Products.imaccounts, products))
	        frm.setImAccounts(getPersonIMAccountLinks(frm));

        frm.setUser(person);

	    if(frm.isAllCardDown() && showProduct(Products.cards, products))
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по картам из АБС временно недоступна. Повторите операцию позже.", false));

	    if(frm.isAllAccountDown() && showProduct(Products.accounts, products))
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по вкладам из АБС временно недоступна. Повторите операцию позже.", false));

	    if(frm.isAllLoanDown() && showProduct(Products.loans, products))
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по кредитам из АБС временно недоступна. Повторите операцию позже.", false));

	    if(frm.isAllIMAccountDown() && showProduct(Products.imaccounts, products))
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по обезличенным металлическим счетам из АБС временно недоступна. Повторите операцию позже.", false));

	    if(msgs.size()!=0)
	        saveErrors(request, msgs);
	    
        return mapping.findForward(FORWARD_SHOW);
    }

	/**
	 * @param type тип продукта
	 * @param products сет продуктов
	 * @return true если необходимо получить данные по этому типу продукта: как явным указанием данного типа, так и получением всех типов продуктов
	 */
	private boolean showProduct(Products type, Set<Products> products)
	{
		return products.isEmpty() || products.contains(type);
	}

	private void setCardsInfo(ShowAccountsForm form) throws BusinessException, BusinessLogicException
	{
		GetCardsOperation operationCards = createOperation(GetCardsOperation.class);
		//получаем все кардлинки клиента. Те кардлинки, которые прописаны в PersonContext.
		List<CardLink> personCardLinks = getPersonCardLinks(operationCards);
		form.setCards(personCardLinks);

		List<CardLink> personMainCardLinks = operationCards.getPersonMainCardLinks(personCardLinks);
		Map<Long, Long> cardsAdditionalInfo = new HashMap<Long, Long>();
		for(CardLink mainCard : personMainCardLinks)
		{
			String mainCardNumber = mainCard.getNumber();
			for (CardLink card : personCardLinks)
			{
				if(StringHelper.equalsNullIgnore(card.getMainCardNumber(), mainCardNumber))
					cardsAdditionalInfo.put(card.getId(), mainCard.getId());
			}
		}

		form.setMainCardIds(cardsAdditionalInfo);
		form.setAllCardDown(personCardLinks.size()==0 && operationCards.isBackError());
	}

	protected List<CardLink> getPersonCardLinks(GetCardsOperation operationCards)
	{
		if(MobileApiWebUtil.isMobileApiRq(getCurrentMapping()))
			return operationCards.getPersonCardLinks();
		return operationCards.getPersonShowsCardLinks();
	}

	protected List<LoanLink> getPersonLoanLinks(ShowAccountsForm form) throws BusinessException, BusinessLogicException
	{
		GetLoanListOperation operationLoans = createOperation(GetLoanListOperation.class);
		List<LoanLink> personLoanLinks = operationLoans.getAllLoans();
		form.setAllAccountDown(personLoanLinks.size()==0 && operationLoans.isBackError());
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
		form.setAllAccountDown(personIMAccountLinks.size()==0 && operation.isBackError());
		return personIMAccountLinks;
	}

	protected void setTargets(ShowAccountsForm form) throws BusinessException, BusinessLogicException
	{
		GetTargetOperation  operation    = createOperation(GetTargetOperation.class);
		List<AccountTarget>	targets      = operation.getShowInMainTargets();
		List<AccountLink>   accountLinks = form.getAccounts();

		for(AccountTarget target : targets)
		{
			accountLinks.remove(target.getAccountLink());
		}

		form.setTargets(targets);
		form.setAccounts(accountLinks);
	}
}
