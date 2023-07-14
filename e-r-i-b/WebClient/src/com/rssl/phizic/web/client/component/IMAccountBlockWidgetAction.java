package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import static com.rssl.phizic.logging.Constants.SHOW_ACCOUNTS_ON_MAIN_PAGE_KEY;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.operations.widget.IMAccountBlockWidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;
import com.rssl.phizic.web.common.client.Constants;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * Экшн виджета "Мет. счета"
 * @author lukina
 * @ created 12.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountBlockWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(IMAccountBlockWidgetOperation.class);
	}

	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);

		IMAccountBlockWidgetForm frm = (IMAccountBlockWidgetForm)form;
		IMAccountBlockWidgetOperation oper = (IMAccountBlockWidgetOperation)operation;

		Calendar start = GregorianCalendar.getInstance();

		List<IMAccountLink> imAccountLinks = oper.getIMAccountLinks();
		Map<IMAccountLink, IMAccountAbstract> imAccountAbstract = oper.getIMAccountAbstract(Constants.MAX_COUNT_OF_TRANSACTIONS);

		frm.setImAccounts(imAccountLinks);
		frm.setIMAccountAbstract(imAccountAbstract);
		frm.setAllIMAccountsDown(oper.isAllAccountDown());

		List<Long> showIMAccountLinkIds = oper.getShowProducts();
		frm.setShowIMAccountLinkIds(showIMAccountLinkIds);

		if (!CollectionUtils.isEmpty(imAccountLinks))
			writeLogInformation(start, "Получение остатка по мет. счетам", SHOW_ACCOUNTS_ON_MAIN_PAGE_KEY);
	}
}