package com.rssl.phizic.web.client;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.web.client.component.WebPageAction;
import com.rssl.phizic.web.log.ArrayLogParametersReader;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 13.10.2005 Time: 13:59:13 */
public abstract class ShowAccountsAction extends WebPageAction
{
    public static final String FORWARD_SHOW = "Show";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = super.getKeyMethodMap();
        map.put("button.showAccountCertificate", "buildAccountCertificate");
	    map.put("button.showAccountInformation", "buildAccountInformation");
        return map;
    }

    public abstract ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ActionForward buildAccountCertificate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        String[] selectedAccountsIds = (String[]) PropertyUtils.getSimpleProperty(form, "selectedAccountsIds");
        String[] selectedCardsIds = (String[]) PropertyUtils.getSimpleProperty(form, "selectedCardsIds");
	    String[] selectedDepositsIds = (String[]) PropertyUtils.getSimpleProperty(form, "selectedDepositsIds");

	    ActionForward result = new ActionForward(mapping.findForward("Abstract"));
	    if (selectedAccountsIds.length == 0 && selectedCardsIds.length ==0 && selectedDepositsIds.length == 0){
		    result.setPath(result.getPath() + "?list=all");
		    return result;
	    }

	    StringBuilder builder = new StringBuilder();
	    builder.append(result.getPath());
	    builder.append("?list=selected");
	    if(selectedAccountsIds!=null && selectedAccountsIds.length!=0)
	    {
			builder.append("&");
			builder.append(buildRequestString("accountIds", selectedAccountsIds));

		    addLogParameters(new ArrayLogParametersReader("Выбранные счета", selectedAccountsIds));
	    }
	    if(selectedCardsIds != null && selectedCardsIds.length!=0)
	    {
			builder.append("&");
			builder.append(buildRequestString("cardIds", selectedCardsIds));

		    addLogParameters(new ArrayLogParametersReader("Выбранные карты", selectedCardsIds));
	    }
	    if(selectedDepositsIds != null && selectedDepositsIds.length!=0)
	    {
			builder.append("&");
			builder.append(buildRequestString("depositIds", selectedDepositsIds));

		    addLogParameters(new ArrayLogParametersReader("Выбранные вклады", selectedDepositsIds));
	    }

        result.setPath(builder.toString());
        return result;
    }

	/**
	 * перобразование массива в строку request
	 * @param paramName
	 * @param ids
	 * @return
	 */
	private String buildRequestString(String paramName, String[] ids)
	{
		StringBuilder builder = new StringBuilder();
		int count =1;		 
		for (String id : ids)
		{
			builder.append(paramName);
			builder.append("=");
			builder.append(id);
			if(ids.length != count)
				builder.append("&");
			count++;
		}
		return builder.toString();
	}

	protected Map<String, ScheduleItem> buildNextScheduleItemMap
			(GetLoanListOperation operation, List<LoanLink> loans)
	{
		Map<String, ScheduleItem> scheduleItems = new HashMap<String, ScheduleItem>();
		for (LoanLink loanLink : loans)
		{
			Loan loan = loanLink.getLoan();
			try
			{				
				scheduleItems.put(loan.getId(), operation.getNextScheduleItem(loan));
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
				scheduleItems.put(loan.getId(), null);
			}
			catch (BusinessLogicException e)
			{
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				saveErrors(currentRequest(), msgs);
				scheduleItems.put(loan.getId(), null);
			}
		}

		return scheduleItems;
	}
}

