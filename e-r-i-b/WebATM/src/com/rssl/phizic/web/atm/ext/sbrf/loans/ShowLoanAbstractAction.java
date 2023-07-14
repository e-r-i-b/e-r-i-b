package com.rssl.phizic.web.atm.ext.sbrf.loans;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.operations.ext.sbrf.loans.ClosedLoanException;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.web.atm.common.FilterFormBase;
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
 * @author Rydvanskiy
 * @ created 03.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanAbstractAction  extends ShowLoanInfoAction
{
	protected Map<String, String> getKeyMethodMap()
    {
        return new HashMap<String, String>();
    }

	//формируем поля фильтрации для валидации
	private MapValuesSource getMapSource(FilterFormBase frm)
	{
		//формируем поля фильтрации для валидации
	    Map<String,Object> filter = new HashMap<String,Object>();
	    filter.put(FilterFormBase.FROM_DATE_NAME, frm.getFrom());
	    filter.put(FilterFormBase.TO_DATE_NAME, frm.getTo());
	    filter.put(FilterFormBase.COUNT_NAME, frm.getCount());
	    return new MapValuesSource(filter);
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
	    ShowLoanInfoForm frm = (ShowLoanInfoForm) form;
	    Long linkId = frm.getId();
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

	    FieldValuesSource valuesSource = getMapSource(frm);

		Form filterForm = FilterFormBase.FILTER_DATE_COUNT_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, filterForm);
	    if(processor.process())
		{
			Map<String, Object> result = processor.getResult();
			Date from = (Date) result.get(FilterFormBase.FROM_DATE_NAME);
			Date to  = (Date) result.get(FilterFormBase.TO_DATE_NAME);
			Long count = (Long) result.get(FilterFormBase.COUNT_NAME);

			operation.initialize(linkId);

			GetLoanAbstractOperation abstractOperation = createOperation(GetLoanAbstractOperation.class);
			abstractOperation.initialize(link);

			if (count == null)
				getLoneAbstract (from, to, abstractOperation, link, frm);
			else
				getLoneAbstract (count, abstractOperation, link, frm);

			frm.setError(abstractOperation.isError());
			frm.setLoanLink(link);
		}
		else
	        saveErrors(request, processor.getErrors());

	    return mapping.findForward(FORWARD_SHOW);
    }
}
