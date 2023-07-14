package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.ChangePersonalFinanceAccessOperation;
import com.rssl.phizic.operations.finances.FinancesStatus;
import com.rssl.phizic.operations.finances.ShowFinanceOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author rydvanskiy
 * @ created 28.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowFinanceStructureAction extends FinanceActionBase
{
	private static final String FORWARD_WAITING_CLAIMS = "WaitingClaims";
	private static final String FORWARD_NOT_CONNECT = "NotConnected";
	private static final String FORWARD_CONNECTED = "Connected";
	private static final String FORWARD_NO_PRODUCTS = "NoProducts";
	private static final String FORWARD_ALL_OK = "AllOk";

	protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = new HashMap<String, String>();
	    map.put("button.connect", "connect");
	    return map;
    }

	protected ShowFinanceOperation createFinancesOperation (FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		ShowFinanceOperation operation = null;
		if (checkAccess(ShowFinanceOperation.class, "FinanceOperationsService"))
			operation = createOperation(ShowFinanceOperation.class, "FinanceOperationsService");
		else
			operation = createOperation(ShowFinanceOperation.class, "CategoriesCostsService");

		operation.initialize();
		return operation;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				throws Exception
	{
		FinanceFormBase frm = (FinanceFormBase) form;
		ShowFinanceOperation op = createFinancesOperation(frm);
		frm.setLastModified(op.getLastModified());
		frm.setActivePagesCategory(mapping.getParameter());
		return nextStepShowFinancesStatus(mapping, frm, request, response, op);
	}

	/**
	 * подключить АЛФ
	 * @param mapping текущий стратс-маппинг
	 * @param form текущая форма
	 * @param request текущий реквест
	 * @param response текущий респунс
	 * @return экшен-форвард
	 * @throws Exception
	 */
	public ActionForward connect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				throws Exception
	{
		ChangePersonalFinanceAccessOperation accessOperation = createOperation(ChangePersonalFinanceAccessOperation.class, "AddFinanceOperationsService");
		// подключаем клиента
		accessOperation.initialize();
		accessOperation.setShowPersonalFinance(true);
		accessOperation.save();

		FinanceFormBase frm = (FinanceFormBase) form;
		// обновляем заявки
        ShowFinanceOperation operation = createFinancesOperation(frm);
        return nextStepConnect(mapping, frm, request, response, operation);
	}

	protected  ActionForward nextStepConnect(ActionMapping mapping,  FinanceFormBase form, HttpServletRequest request, HttpServletResponse response, ShowFinanceOperation operation)
			throws Exception
	{
		return mapping.findForward(FORWARD_CONNECTED);
	}

	protected ActionForward nextStepShowFinancesStatus(ActionMapping mapping, FinanceFormBase frm,
	                                                   HttpServletRequest request, HttpServletResponse response,
	                                                   ShowFinanceOperation operation) throws Exception
	{
        frm.setLastModified(operation.getLastModified());

        FinancesStatus opStatus = operation.getStatus();
		//проверяем есть ли проваленные заявки
		checkFailedClaims(operation, request);

        if (opStatus == FinancesStatus.notConnected)
            return mapping.findForward(FORWARD_NOT_CONNECT);

        if (opStatus == FinancesStatus.waitingClaims)
            return mapping.findForward(FORWARD_WAITING_CLAIMS);

        if (opStatus == FinancesStatus.noProducts)
            return mapping.findForward(FORWARD_NO_PRODUCTS);

        return mapping.findForward(FORWARD_ALL_OK);
	}
}
