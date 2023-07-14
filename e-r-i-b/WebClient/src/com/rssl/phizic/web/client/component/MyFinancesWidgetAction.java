package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.graphics.ViewFinanceOperation;
import com.rssl.phizic.operations.ima.GetIMAccountOperation;
import com.rssl.phizic.operations.widget.MyFinancesWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * Экшн для виджета "Доступные средства"
 * @ author Rtischeva
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class MyFinancesWidgetAction extends WidgetActionBase
{
	private static final String EMPTY_RESOURCE_MESSAGE = "У вас нет активных карт, вкладов и металлических счетов.";
	private static final String PRODUCT_INFORM_NOT_EXISTS = "Информация по %s из АБС временно недоступна. Повторите операцию позже.";
	private static final String NO_ACCOUNT_PRODUCT_MESSAGE = String.format(PRODUCT_INFORM_NOT_EXISTS, "счетам");
	private static final String NO_CARD_PRODUCT_MESSAGE = String.format(PRODUCT_INFORM_NOT_EXISTS, "картам");
	private static final String NO_IMA_PRODUCT_MESSAGE = String.format(PRODUCT_INFORM_NOT_EXISTS, "ОМС");

	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(MyFinancesWidgetOperation.class);
	}


	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		HttpServletRequest request = currentRequest();
		super.updateForm(form, operation);

		MyFinancesWidgetForm frm = (MyFinancesWidgetForm)form;
		ActionMessages msgs = new ActionMessages();
		
		List<AccountLink> accountLinks = getAccountLinks(msgs);
		List<CardLink> cardLinks = getCardLinks(msgs);
		List<IMAccountLink> imAccountLinks = getIMAccountLinks(msgs);

		frm.setAccounts(accountLinks);
		frm.setCards(cardLinks);
		frm.setImAccounts(imAccountLinks);

		ViewFinanceOperation financeOperation = createOperation(ViewFinanceOperation.class, "ViewFinance");
		financeOperation.setAccountLinks(accountLinks);
		financeOperation.setCardLinks(cardLinks);
		financeOperation.setImAccountLinks(imAccountLinks);

		frm.setFillCurrencyRate(!financeOperation.isBackError());
		frm.setCurrencyRateByCB(financeOperation.getCurrencyRateByCentralBank());
		frm.setCurrencyRateByRemote(financeOperation.getCurrencyRateByRemoteBuy());

		if (CollectionUtils.isEmpty(frm.getCards()) && CollectionUtils.isEmpty(frm.getAccounts()) && CollectionUtils.isEmpty(frm.getImAccounts()) && msgs.isEmpty())
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_RESOURCE_MESSAGE, false));
		if(!msgs.isEmpty())
			saveMessages(request, msgs);

	}

	private List<AccountLink> getAccountLinks(ActionMessages msgs) throws BusinessException
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

		return cardLinks;
	}

	private List<IMAccountLink> getIMAccountLinks(ActionMessages msgs) throws BusinessException
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

}
