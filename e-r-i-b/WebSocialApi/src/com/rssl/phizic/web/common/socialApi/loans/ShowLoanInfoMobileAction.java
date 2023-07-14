package com.rssl.phizic.web.common.socialApi.loans;

import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.operations.ext.sbrf.loans.ClosedLoanException;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.web.common.client.Constants;
import com.rssl.phizic.web.common.client.loans.ShowLoanDetailAction;
import com.rssl.phizic.web.common.client.loans.ShowLoanInfoForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Детальная информация по кредиту
 * @author mihaylov
 * @ created 24.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanInfoMobileAction extends ShowLoanDetailAction
{
    private static final String FORWARD_SAVE_NAME = "SaveName";
    
    protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("saveName", "saveLoanName");
        return map;
    }
    
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ShowLoanInfoMobileForm frm = (ShowLoanInfoMobileForm) form;
        Long linkId = frm.getId();

        // 1. Получаем LoanLink
        GetLoanInfoOperation operation = createOperation(GetLoanInfoOperation.class);
        try
        {
            operation.initialize(linkId);
        }
        catch (ClosedLoanException e)
        {
            saveError(request, e);
            return mapping.findForward(FORWARD_SHOW);
        }
        LoanLink link = operation.getLoanLink();
        frm.setLoanLink(link);

        // 2. Получаем выписку
        if (checkAccess(GetLoanAbstractOperation.class))
        {
            GetLoanAbstractOperation abstractOperation = createOperation(GetLoanAbstractOperation.class);
            abstractOperation.initialize(link);
            Pair<Map<LoanLink, ScheduleAbstract>, Map<LoanLink, String>> scheduleAbstract = abstractOperation.getScheduleAbstract(-Constants.MAX_COUNT_OF_TRANSACTIONS, Constants.MAX_COUNT_OF_TRANSACTIONS, true, false);
            Map<LoanLink, ScheduleAbstract> abstractMap = scheduleAbstract.getFirst();
            frm.setScheduleAbstract(abstractMap.get(link));
        }

        return mapping.findForward(FORWARD_SHOW);
    }

    protected MapValuesSource getSaveLoanNameFieldValuesSource(ShowLoanInfoForm form)
    {
        ShowLoanInfoMobileForm frm = (ShowLoanInfoMobileForm) form;
        Map<String,Object> filter = new HashMap<String,Object>();
        filter.put("loanName", frm.getLoanName());
        return new MapValuesSource(filter);
    }

    protected ActionForward forwardSaveLoanName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return mapping.findForward(FORWARD_SAVE_NAME);
    }
}
