package com.rssl.phizic.web.loans.loanOffer.load;

import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.loanOffer.OfferLoanOperationBase;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.validators.FileNotEmptyValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

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

    protected FormFile file;

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("button.replic", "replic");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return mapping.findForward(FORWARD_START);
    }

    protected boolean loadFile(LoanOfferLoadForm frm,HttpServletRequest request)
    {
        file = frm.getFile();
        ActionMessages messages = FileNotEmptyValidator.validate(file);
        if (!messages.isEmpty())
        {
            saveErrors(request, messages);
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

        addLogParameters(new SimpleLogParametersReader("Загразука  предложений банка"));
    }

}
