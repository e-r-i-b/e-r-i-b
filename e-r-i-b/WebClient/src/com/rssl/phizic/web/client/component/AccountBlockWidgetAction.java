package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import static com.rssl.phizic.logging.Constants.SHOW_ACCOUNTS_ON_MAIN_PAGE_KEY;
import com.rssl.phizic.operations.widget.AccountBlockWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;
import com.rssl.phizic.web.common.client.Constants;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * @author gulov
 * @ created 20.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class AccountBlockWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(AccountBlockWidgetOperation.class);
	}

	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);

		AccountBlockWidgetForm frm = (AccountBlockWidgetForm)form;
		AccountBlockWidgetOperation oper = (AccountBlockWidgetOperation)operation;

		Calendar start = GregorianCalendar.getInstance();

		List<AccountLink> accountLinks = oper.getAccountLinks();
		Map<AccountLink, AccountAbstract> accountAbstract = oper.getAccountAbstract(Constants.MAX_COUNT_OF_TRANSACTIONS);
		
		frm.setAccounts(accountLinks);
		frm.setAccountAbstract(accountAbstract);
		frm.setAllAccountDown(oper.isAllAccountDown());

		List<Long> showAccountLinkIds = oper.getShowProducts();
		frm.setShowAccountLinkIds(showAccountLinkIds);

		if (!CollectionUtils.isEmpty(accountLinks))
			writeLogInformation(start, "Получение остатка по счетам", SHOW_ACCOUNTS_ON_MAIN_PAGE_KEY);
	}
}
