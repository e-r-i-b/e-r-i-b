package com.rssl.phizic.web.atm.ext.sbrf.accounts;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.atm.common.FilterFormBase;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rydvanskiy
 * @ created 22.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountsAbstractAction extends ShowAccountInfoAction
{
	private static final String MAX_COUNT_MSG = " оличество последних операций по выписке, не может превышать 10";
    public static final int MAX_COUNT = 10;
    public static final int THREE_MONTH_AGO = -3;

	protected Map<String, String> getKeyMethodMap()
    {
        return new HashMap<String, String>();
    }

	//формируем пол€ фильтрации дл€ валидации
	private MapValuesSource getMapSource(FilterFormBase frm)
	{
		//формируем пол€ фильтрации дл€ валидации
	    Map<String,Object> filter = new HashMap<String,Object>();
	    filter.put(FilterFormBase.FROM_DATE_NAME, frm.getFrom());
	    filter.put(FilterFormBase.TO_DATE_NAME, frm.getTo());
	    filter.put(FilterFormBase.COUNT_NAME, frm.getCount());
	    return new MapValuesSource(filter);
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
        ShowAccountAbstractForm frm = (ShowAccountAbstractForm) form;
	    Long linkId = frm.getId();
	    GetAccountAbstractExtendedOperation operation = createOperation(GetAccountAbstractExtendedOperation.class);
	    
	    FieldValuesSource valuesSource = getMapSource(frm);

		Form filterForm = FilterFormBase.FILTER_DATE_COUNT_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, filterForm);
	    if(processor.process())
		{
			//todo. 10 последних операций не сделано!!! ѕоправить и здесь в рамках запроса CHG024238
			Map<String, Object> result = processor.getResult();
			Date from = (Date) result.get(FilterFormBase.FROM_DATE_NAME);
			Date to  = (Date) result.get(FilterFormBase.TO_DATE_NAME);
			if (from == null || to == null)
			{
				if (Integer.parseInt(frm.getCount()) > MAX_COUNT)
                {
                    ActionMessages msgs = new ActionMessages();
                    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MAX_COUNT_MSG, false));
                    saveErrors(currentRequest(), msgs);
                    return mapping.findForward(FORWARD_SHOW);
                }

                Calendar currentDate = Calendar.getInstance();
                to = currentDate.getTime();
                currentDate.add(Calendar.MONTH, THREE_MONTH_AGO);
                from = currentDate.getTime();
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

    protected void updateFormData(ShowAccountAbstractForm form, GetAccountAbstractExtendedOperation operation) throws BusinessException, BusinessLogicException
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
            log.error("Ќевозможно получить информацию по счету " + accountLink.getNumber(), ex);
        }

        form.setAccountLink(accountLink);

	    List<TransactionBase> transactions = accountAbstract.getTransactions();
	    if (StringHelper.isNotEmpty(form.getCount()))
	    {
		    form.setTransactions(getLastTransactions(transactions, Integer.parseInt(form.getCount())));
	    }
	    else
	    {
		    form.setTransactions(transactions);
	    }

        form.setAccountAbstract(accountAbstract);
        form.setField("accountName",accountLink.getName());
    }

    private List<TransactionBase> getLastTransactions(List<TransactionBase> transactions, int count)
    {
        Collections.sort(transactions, new Comparator<TransactionBase>()
        {
            public int compare(TransactionBase o1, TransactionBase o2)
            {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return transactions.subList(0, count < transactions.size() ? count : transactions.size());
    }
}
