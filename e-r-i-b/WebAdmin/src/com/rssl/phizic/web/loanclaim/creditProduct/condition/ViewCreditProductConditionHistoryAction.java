package com.rssl.phizic.web.loanclaim.creditProduct.condition;

import com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition.ViewHistoryCreditProductConditionOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Moshenko
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 * Ёкшен просмотра истории условий по кредитным продуктам.
 */
public class ViewCreditProductConditionHistoryAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewCreditProductConditionHistoryForm frm = (ViewCreditProductConditionHistoryForm) form;
		ViewHistoryCreditProductConditionOperation op = createOperation(ViewHistoryCreditProductConditionOperation.class);
		op.initialize(frm.getId(),frm.getCurrCode());
		frm.setHisrotyCurrCoditions(op.getEntity());
		return mapping.findForward(FORWARD_START);
	}
}
