package com.rssl.phizic.web.common.socialApi.accounts;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.socialApi.common.FilterFormBase;
import org.apache.struts.action.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Выписка по вкладу
 * @author Rydvanskiy
 * @ created 22.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountAbstractMobileAction extends OperationalActionBase
{
	private static final String NOT_AVAILABLE_MSG = "В данной версии фильтр по количеству операций недоступен";

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
	    ShowAccountAbstractMobileForm frm = (ShowAccountAbstractMobileForm) form;
	    Long linkId = frm.getId();
	    GetAccountAbstractExtendedOperation operation = createOperation(GetAccountAbstractExtendedOperation.class);
	    
	    FieldValuesSource valuesSource = getMapSource(frm);

		Form filterForm = FilterFormBase.FILTER_DATE_COUNT_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, filterForm);
	    if(processor.process())
		{
			//фильтр реализован только для периода по запросу CHG024238
			Map<String, Object> result = processor.getResult();
			Date from = (Date) result.get(FilterFormBase.FROM_DATE_NAME);
			Date to  = (Date) result.get(FilterFormBase.TO_DATE_NAME);
			if (from == null || to == null)
			{
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(NOT_AVAILABLE_MSG, false));
				saveErrors(currentRequest(), msgs);
				return mapping.findForward(FORWARD_SHOW);
			}
			operation.setDateFrom(DateHelper.toCalendar( from ));
			operation.setDateTo(DateHelper.toCalendar( to ));
		}
		else
	    {			
			saveErrors(request, processor.getErrors());
		    return mapping.findForward(FORWARD_SHOW);
		}

		operation.initialize(linkId);

	    updateFormData(frm, operation);

	    return mapping.findForward(FORWARD_SHOW);
    }

    private void updateFormData(ShowAccountAbstractMobileForm form, GetAccountAbstractExtendedOperation operation)
    {
        AccountLink accountLink = operation.getAccount();
        AccountAbstract accountAbstract = null;
        try
        {
            accountAbstract = operation.getAccountAbstract();
            form.setError(operation.isBackError());
        }
        catch (BusinessLogicException ex)
        {
            log.error("Невозможно получить информацию по счету " + accountLink.getNumber(), ex);
        }

        form.setAccountAbstract(accountAbstract);
    }
}
