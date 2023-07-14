package com.rssl.phizic.web.common.socialApi.loans;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.operations.ext.sbrf.loans.ClosedLoanException;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.web.common.socialApi.common.FilterFormBase;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ������ �������� �� �������
 * @author Rydvanskiy
 * @ created 03.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanAbstractMobileAction extends OperationalActionBase
{
    //��������� ���� ���������� ��� ���������
    private MapValuesSource getMapSource(FilterFormBase frm)
    {
        //��������� ���� ���������� ��� ���������
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put(FilterFormBase.FROM_DATE_NAME, frm.getFrom());
        filter.put(FilterFormBase.TO_DATE_NAME, frm.getTo());
        filter.put(FilterFormBase.COUNT_NAME, frm.getCount());
        return new MapValuesSource(filter);
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ShowLoanAbstractMobileForm frm = (ShowLoanAbstractMobileForm) form;
        Long linkId = frm.getId();

        // 1. �������� LoanLink
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

        // 2. �������� �������
        FieldValuesSource valuesSource = getMapSource(frm);
        Form filterForm = FilterFormBase.FILTER_DATE_COUNT_FORM;
        FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, filterForm);
        if (processor.process())
        {
            Map<String, Object> result = processor.getResult();
            Date from = (Date) result.get(FilterFormBase.FROM_DATE_NAME);
            Date to = (Date) result.get(FilterFormBase.TO_DATE_NAME);
            Long count = (Long) result.get(FilterFormBase.COUNT_NAME);

            GetLoanAbstractOperation abstractOperation = createOperation(GetLoanAbstractOperation.class);
            abstractOperation.initialize(link);

            if (count == null)
                frm.setScheduleAbstract(abstractOperation.getLoanAbstract(from, to));
            else
                frm.setScheduleAbstract(abstractOperation.getLoanAbstract(count));

            frm.setError(abstractOperation.isError());
        }
        else
            saveErrors(request, processor.getErrors());

        return mapping.findForward(FORWARD_SHOW);
    }
}
