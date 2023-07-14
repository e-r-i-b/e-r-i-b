package com.rssl.phizic.web.common.graphics;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.graphics.ViewFinanceOperation;
import com.rssl.phizic.operations.ima.GetIMAccountOperation;
import com.rssl.phizic.operations.securities.ListSecuritiesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author rydvanskiy
 * @ created 11.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowFinancesAction  extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String VIEW_FINANCE_SERVICE_KEY = "ViewFinance";
	private static final String EMPTY_RESOURCE_MESSAGE = "У вас нет активных карт, вкладов, металлических счетов и сберегательных сертификатов.";
	private static final String PRODUCT_INFORM_NOT_EXISTS = "Информация по %s из АБС временно недоступна. Повторите операцию позже.";
	protected static final String NO_ACCOUNT_PRODUCT_MESSAGE = String.format(PRODUCT_INFORM_NOT_EXISTS, "счетам");
	private static final String NO_CARD_PRODUCT_MESSAGE = String.format(PRODUCT_INFORM_NOT_EXISTS, "картам");
	protected static final String NO_IMA_PRODUCT_MESSAGE = String.format(PRODUCT_INFORM_NOT_EXISTS, "ОМС");
	private static final String NO_SECURITY_ACC_PRODUCT_MESSAGE = String.format(PRODUCT_INFORM_NOT_EXISTS, "сберегательным сертификатам");

    protected Map<String, String> getKeyMethodMap()
    {
        return new HashMap<String, String>();
    }

	protected String getEmptyResourceMessage()
	{
		return EMPTY_RESOURCE_MESSAGE;
	}
	protected List<AccountLink> getAccountLinks(ActionMessages msgs) throws BusinessException
	{
		if (!checkAccess(GetAccountsOperation.class))
			return Collections.emptyList();

		GetAccountsOperation operation = createOperation(GetAccountsOperation.class);
		List<AccountLink> accountLinks = operation.getActiveAccounts();

		if (operation.isBackError())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(NO_ACCOUNT_PRODUCT_MESSAGE, false));
			return Collections.emptyList();
		}

		return accountLinks;
	}

	private List<CardLink> getCardLinks(ActionMessages msgs) throws BusinessException
	{
		if (!checkAccess(GetCardsOperation.class))
			return Collections.emptyList();

		GetCardsOperation operation = createOperation(GetCardsOperation.class);
		List<CardLink> cardLinks = operation.getPersonCardLinks(false, true);

		if (operation.isBackError())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(NO_CARD_PRODUCT_MESSAGE, false));
			return Collections.emptyList();
		}

        List<String> mainCardNumbers = new ArrayList<String>();
        List<CardLink> links = new ArrayList<CardLink>();

        for (CardLink lnk:cardLinks)
            if (lnk.isMain() && lnk.isActive())
                mainCardNumbers.add(lnk.getCard().getNumber());

        for (CardLink lnk:cardLinks)
        {
            AdditionalCardType type = lnk.getCard().getAdditionalCardType();
            if ((AdditionalCardType.CLIENTTOCLIENT != type  &&
                    AdditionalCardType.CLIENTTOOTHER != type) ||
                    !mainCardNumbers.contains(lnk.getMainCardNumber()))
                links.add(lnk);
        }
		return links;
	}

	protected List<IMAccountLink> getIMAccountLinks(ActionMessages msgs) throws BusinessException
	{
		if (!checkAccess(GetIMAccountOperation.class))
			return Collections.emptyList();

		GetIMAccountOperation operation = createOperation(GetIMAccountOperation.class);
		List<IMAccountLink> accountLinks = operation.getActiveIMAccounts();

		if (operation.isBackError())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(NO_IMA_PRODUCT_MESSAGE, false));
			return Collections.emptyList();
		}

		return accountLinks;
	}

	protected List<SecurityAccountLink> getSecurityAccountLinks(ShowFinancesForm frm, ActionMessages msgs) throws BusinessException, BusinessLogicException
	{
		if (!checkAccess(ListSecuritiesOperation.class))
			return Collections.emptyList();

		ListSecuritiesOperation operation = createOperation(ListSecuritiesOperation.class);
		operation.initialize();
		List<SecurityAccountLink> securityAccountLinks = operation.getSecurityAccountLinksInSystem();
		frm.setSecurityAccountLinks(securityAccountLinks);
		frm.setSecurityAccounts(operation.getSecurityAccounts());
		if (operation.isBackError())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(NO_SECURITY_ACC_PRODUCT_MESSAGE, false));
			return Collections.emptyList();
		}

		return securityAccountLinks;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		loadProducts(form, request);
		return mapping.findForward(FORWARD_START);
	}

	protected ViewFinanceOperation createOperation() throws BusinessException
	{
		return createOperation(ViewFinanceOperation.class, VIEW_FINANCE_SERVICE_KEY);
	}

	/**
	 * Загрузка ресурсов клиента для диаграммы "Доступные средства" и удалось ли это сделать
	 * @param form
	 * @param request
	 * @return далось ли получить доступные средства клиенты для диаграммы
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void loadProducts(ActionForm form, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		ViewFinanceOperation operation = createOperation();
		ShowFinancesForm frm = (ShowFinancesForm) form;
		ActionMessages msgs = new ActionMessages();

		List<AccountLink> accountLinks = getAccountLinks(msgs);
		List<CardLink> cardLinks = getCardLinks(msgs);
		List<IMAccountLink> imAccountLinks = getIMAccountLinks(msgs);
		List<SecurityAccountLink> securityAccountLinks = getSecurityAccountLinks(frm, msgs);

		operation.setAccountLinks(accountLinks);
		operation.setCardLinks(cardLinks);
		operation.setImAccountLinks(imAccountLinks);
		operation.setSecurityAccountLinks(securityAccountLinks);
		operation.setSecurityAccounts(frm.getSecurityAccounts());

		frm.setCards(cardLinks);
		frm.setAccounts(accountLinks);
		frm.setImAccounts(imAccountLinks);
		frm.setCurrencyRateByCB(operation.getCurrencyRateByCentralBank());
		frm.setCurrencyRateByRemote(operation.getCurrencyRateByRemoteBuy());
		frm.setFillCurrencyRate(!operation.isBackError());

		boolean dontHaveProducts = CollectionUtils.isEmpty(frm.getCards()) && CollectionUtils.isEmpty(frm.getAccounts()) &&
				CollectionUtils.isEmpty(frm.getImAccounts()) && CollectionUtils.isEmpty(frm.getSecurityAccountLinks()) && msgs.isEmpty();
		if (dontHaveProducts)
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getEmptyResourceMessage(), false));

		if(!msgs.isEmpty())
			saveMessages(request, msgs);
	}
}
