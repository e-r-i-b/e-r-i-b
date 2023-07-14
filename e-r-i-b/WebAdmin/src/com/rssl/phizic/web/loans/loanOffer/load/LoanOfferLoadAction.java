package com.rssl.phizic.web.loans.loanOffer.load;

import com.rssl.phizic.operations.loanOffer.LoanOfferLoadOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 10.06.2011
 * Time: 15:15:36
 */
public class LoanOfferLoadAction extends OfferActionBase
{
	public ActionForward replic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.info("������ �������� �������������� ��������� �����������");
		LoanOfferLoadForm frm = (LoanOfferLoadForm) form;
		LoanOfferLoadOperation operation = createOperation(LoanOfferLoadOperation.class);

		if (!loadFile(frm, request))
			return mapping.findForward(FORWARD_START);

		InputStream stream = file.getInputStream();
		try
		{
			operation.initialize(stream);
			operation.updateLoanOffer();
		}
		finally
		{
			stream.close();
		}
		saveError(frm, operation);

		log.info("����� �������� �������������� ��������� �����������");
		return mapping.findForward(REPORT);
	}
}
