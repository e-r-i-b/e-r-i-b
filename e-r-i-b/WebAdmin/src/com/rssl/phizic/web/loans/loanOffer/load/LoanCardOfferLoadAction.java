package com.rssl.phizic.web.loans.loanOffer.load;

import com.rssl.phizic.operations.loanOffer.LoanCardOfferLoadOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 16.06.2011
 * Time: 9:54:09
 */
public class LoanCardOfferLoadAction extends OfferActionBase
{
	public ActionForward replic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.info("Ќачло загрузки предодобренных предложений по кредитным картам");
		LoanOfferLoadForm frm = (LoanOfferLoadForm) form;
		LoanCardOfferLoadOperation operation = createOperation(LoanCardOfferLoadOperation.class);

		if (!loadFile(frm, request))
			return mapping.findForward(FORWARD_START);

		InputStream stream = file.getInputStream();
		try
		{
			operation.initialize(stream);
			operation.updateLoanCardOffer();
		}
		finally
		{
			stream.close();
		}

		saveError(frm, operation);

		log.info(" онец загрузки предодобренных предложений по кредитным картам");
		return mapping.findForward(REPORT);
	}
}
