package com.rssl.phizic.web.loans.loanOffer.autoLoad;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.loanOffer.LoanOfferLoadOperation;
import com.rssl.phizic.operations.loanOffer.OfferLoanOperationBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Moshenko
 * Date: 16.06.2011
 * Time: 15:15:23
 * Обзщий экшен загрузки предодобренных предложений
 */
public abstract class OfferActionBase extends OperationalActionBase
{
    protected static final String FORWARD_START = "Start";
    protected static final String REPORT = "Report";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("button.replic", "replic");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LoanOfferLoadForm frm = (LoanOfferLoadForm) form;
	    frm.setFileName(getFileName());

	    return mapping.findForward(FORWARD_START);
    }

    protected boolean loadFile(LoanOfferLoadForm frm,HttpServletRequest request) throws BusinessException, BusinessLogicException
    {
	    frm.setFileName(getFileName());

	    if (StringHelper.isEmpty(frm.getFileName()))
	    {
			ActionMessages msgs = new ActionMessages();
		    ActionMessage message = new ActionMessage("com.rssl.phizic.web.validators.error.fileNotSelected");
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		    saveErrors(request, msgs);
            return false;
	    }
        return true;
    }

    protected void saveError(LoanOfferLoadForm frm, OfferLoanOperationBase operation)
    {
        frm.setCommonErrors(operation.getCommonErrors());
        frm.setPersonErrors(operation.getPersonsErrors());

        //заносим в систем лог информацию об общих ошибках, и ошибках по пользователю
        for (String toLog : operation.getCommonErrors())
        {
            log.info(toLog);
        }
        for (String toLog : operation.getPersonsErrors())
        {
            log.info(toLog);
        }

        frm.setLoadCount(operation.getLoadCount());
        frm.setTotalCount(operation.getTotalCount());

        addLogParameters(new SimpleLogParametersReader("Загрузка  предложений банка"));
    }

	public  String getFileName() throws BusinessException
	{
		LoanOfferLoadOperation operation = createOperation(LoanOfferLoadOperation.class);
	    return operation.getPathToFile();
	}

}