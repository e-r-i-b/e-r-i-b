package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import static com.rssl.phizic.logging.Constants.SHOW_LOANS_ON_MAIN_PAGE_KEY;
import com.rssl.phizic.operations.widget.LoanBlockWidgetOperation;
import com.rssl.phizic.operations.widget.LoanDescriptor;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;
import com.rssl.phizic.web.common.client.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн виджета "Ваши кредиты"
 * @author gulov
 * @ created 23.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class LoanBlockWidgetAction extends WidgetActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("stopBlinking", "stopBlinking");
		return map;
	}

	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(LoanBlockWidgetOperation.class);
	}

	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);

		LoanBlockWidgetForm frm = (LoanBlockWidgetForm)form;
		LoanBlockWidgetOperation oper = (LoanBlockWidgetOperation)operation;

		Calendar start = GregorianCalendar.getInstance();

		List<LoanDescriptor> loanDescriptors = oper.getLoanDescriptors(Constants.MAX_COUNT_OF_TRANSACTIONS);

		frm.setLoanDescriptors(loanDescriptors);
		frm.setWidgetOverdue(oper.isWidgetOverdue());
		frm.setAllLoanDown(oper.isAllLoanDown());
		frm.setBlinkingStopped(oper.getWidget().isBlinkingStoppedByUser());

		List<Long> showLoanLinkIds = oper.getShowProducts();
		frm.setShowLoanLinkIds(showLoanLinkIds);

		if (!CollectionUtils.isEmpty(loanDescriptors))
			writeLogInformation(start, "Получение остатка по счетам", SHOW_LOANS_ON_MAIN_PAGE_KEY);
	}

	/**
	 * Вызывается, когда пользователь кликнул на виджет (остановил мигание)
	 * @return
	 * @throws Exception
	 */
	public ActionForward stopBlinking(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanBlockWidgetForm frm = (LoanBlockWidgetForm)form;
		LoanBlockWidgetOperation oper = (LoanBlockWidgetOperation) createWidgetOperation();
		oper.initialize(frm.getCodename());
		oper.stopBlinking(true);
		return start(mapping, frm, request, response);
	}
}
