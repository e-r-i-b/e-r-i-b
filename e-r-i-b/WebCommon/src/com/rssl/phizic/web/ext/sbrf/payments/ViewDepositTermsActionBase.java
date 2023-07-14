package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.operations.ext.sbrf.deposits.ViewDepositTermsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pankin
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class ViewDepositTermsActionBase extends OperationalActionBase
{
	protected static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return null;
	}

	protected abstract ViewDepositTermsOperation getViewDepositTermsOperation() throws BusinessException, BusinessLogicException;

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDepositTermsForm frm = (ViewDepositTermsForm) form;

		ViewDepositTermsOperation operation = getViewDepositTermsOperation();
		operation.initialize(frm.getId());

		frm.setHtml(operation.getHtml(frm.getTermsType(), frm.isLoadInDiv()));
		return mapping.findForward(FORWARD_START);
	}
}
