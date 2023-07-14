package com.rssl.phizic.web.client.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.pfr.PFRStatementBaseOperation;
import com.rssl.phizic.operations.pfr.PrintPFRStatementOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gulov
 * @ created 09.03.2011
 * @ $Authors$
 * @ $Revision$
 */
public class PrintPFRStatementAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PrintPFRStatementForm frm = (PrintPFRStatementForm) form;

		PrintPFRStatementOperation operation =  createOperation(frm.getId());

		frm.setHtml(operation.getHtml(PFRStatementBaseOperation.PRINT_MODE));

		return mapping.findForward(FORWARD_START);
	}

	private PrintPFRStatementOperation createOperation(Long claimId) throws BusinessException, BusinessLogicException
	{
		PrintPFRStatementOperation result = new PrintPFRStatementOperation();//createOperation(PrintPFRStatementOperation.class);

		result.initialize(claimId);

		return result;
	}
}
