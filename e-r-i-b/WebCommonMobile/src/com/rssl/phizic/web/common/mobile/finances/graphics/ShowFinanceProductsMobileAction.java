package com.rssl.phizic.web.common.mobile.finances.graphics;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.graphics.ViewFinanceMobileOperation;
import com.rssl.phizic.operations.graphics.ViewFinanceOperation;
import com.rssl.phizic.operations.ima.GetIMAccountOperation;
import com.rssl.phizic.web.common.graphics.ShowFinancesAction;
import com.rssl.phizic.web.common.graphics.ShowFinancesForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Collections;
import java.util.List;

/**
 * @ author: Gololobov
 * @ created: 24.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Получение доступных средств в mAPI
 */
public class ShowFinanceProductsMobileAction extends ShowFinancesAction
{
	protected static final String EMPTY_RESOURCE_MESSAGE = "У Вас нет активных карт, вкладов и металлических счетов.";
	private static final String VIEW_FINANCE_SERVICE_KEY = "ViewFinanceMobileService";

	protected String getEmptyResourceMessage()
	{
		return EMPTY_RESOURCE_MESSAGE;
	}

	protected ViewFinanceOperation createOperation() throws BusinessException
	{
		return createOperation(ViewFinanceMobileOperation.class, VIEW_FINANCE_SERVICE_KEY);
	}

	/**
	 * Получение активных вкладов, доступных для mAPI
	 * @param msgs
	 * @return
	 * @throws BusinessException
	 */
	protected List<AccountLink> getAccountLinks(ActionMessages msgs) throws BusinessException
	{
		if (!checkAccess(GetAccountsOperation.class))
			return Collections.emptyList();

		GetAccountsOperation operation = createOperation(GetAccountsOperation.class);
		List<AccountLink> accountLinks = operation.getActiveShowInMobileAccounts();

		if (operation.isBackError())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(NO_ACCOUNT_PRODUCT_MESSAGE, false));
			return Collections.emptyList();
		}
		return accountLinks;
	}

	/**
	 * Получение активных ОМС, доступных для mAPI
	 * @param msgs
	 * @return
	 * @throws BusinessException
	 */
	protected List<IMAccountLink> getIMAccountLinks(ActionMessages msgs) throws BusinessException
	{
		if (!checkAccess(GetIMAccountOperation.class))
			return Collections.emptyList();

		GetIMAccountOperation operation = createOperation(GetIMAccountOperation.class);
		List<IMAccountLink> imAccountLinks = operation.getActiveShowInMobileIMAccounts();

		if (operation.isBackError())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(NO_IMA_PRODUCT_MESSAGE, false));
			return Collections.emptyList();
		}

		return imAccountLinks;
	}

	/**
	 * Получение активных сберегательных сертификатов, доступных для mAPI
	 * @param frm
	 * @param msgs
	 * @return
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected List<SecurityAccountLink> getSecurityAccountLinks(ShowFinancesForm frm, ActionMessages msgs) throws BusinessException, BusinessLogicException
	{
		//В mAPI сберегательные сертификаты не отображать для диаграммы "доступные средста", возвращаем пустой список.
		return Collections.emptyList();
	}
}
