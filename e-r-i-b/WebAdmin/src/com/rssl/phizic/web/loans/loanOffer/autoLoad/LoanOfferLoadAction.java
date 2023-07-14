package com.rssl.phizic.web.loans.loanOffer.autoLoad;

import com.rssl.phizic.operations.loanOffer.LoanOfferLoadOperation;
import org.apache.struts.action.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
		log.info("Начало загрузки предодобренных кредитных предложений");
		LoanOfferLoadForm frm = (LoanOfferLoadForm) form;
		LoanOfferLoadOperation operation = createOperation(LoanOfferLoadOperation.class);

		if (!loadFile(frm, request))
			return mapping.findForward(FORWARD_START);

		File file = new File(frm.getFileName());

		InputStream stream = null;
		try
		{
			stream = new FileInputStream(file);
		}
		catch (FileNotFoundException e)
		{
			ActionMessages msgs = new ActionMessages();
		    ActionMessage message = new ActionMessage("com.rssl.phizic.web.validators.error.fileNotFound");
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		    saveErrors(request, msgs);

			return mapping.findForward(FORWARD_START);
		}

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

		log.info("Конец загрузки предодобренных кредитных предложений");
		return mapping.findForward(REPORT);
	}
}