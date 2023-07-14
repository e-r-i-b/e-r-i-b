package com.rssl.phizic.web.loans.loanOffer.autoLoad;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.loanOffer.LoanCardOfferLoadOperation;
import org.apache.struts.action.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	public  String getFileName() throws BusinessException
	{
		LoanCardOfferLoadOperation operation = createOperation(LoanCardOfferLoadOperation.class);
	    return operation.getPathToFile();
	}
}