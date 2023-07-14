package com.rssl.phizic.web.atm;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.deposits.GetDepositListOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.log.ArrayLogParametersReader;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 13.10.2005 Time: 13:59:13 */
public class ShowAccountsAction extends OperationalActionBase
{
    public static final String FORWARD_SHOW = "Show";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
        map.put("button.showAccountCertificate", "buildAccountCertificate");
	    map.put("button.showAccountInformation", "buildAccountInformation");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        ShowAccountsForm frm = (ShowAccountsForm) form;
        PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

        if(checkAccess(GetAccountsOperation.class))
        {
            GetAccountsOperation operationAccounts = createOperation(GetAccountsOperation.class);
            frm.setAccounts(operationAccounts.getAccounts());
        }

        if(checkAccess(GetCardsOperation.class))
        {
            GetCardsOperation operationCards = createOperation(GetCardsOperation.class);

	        List<CardLink> personCardLinks = operationCards.getPersonCardLinks();
	        List<CardLink> personMainCardLinks = operationCards.getPersonMainCardLinks(personCardLinks);
	        List<CardLink> personAddCards = operationCards.getPersonAdditionalCards(personCardLinks);
	        List<CardLink> result = new ArrayList<CardLink>();

	        List<CardLink> additionalCards = operationCards.getAdditionalCards(personMainCardLinks);
	        result.addAll(personMainCardLinks);
	        result.addAll(additionalCards);
	        personAddCards.removeAll(additionalCards);
	        result.addAll(personAddCards);

	        frm.setCards(result);
	        CardsConfig cardsConfig = ConfigFactory.getConfig(CardsConfig.class);
	        frm.setWarningPeriod(cardsConfig.getWarningPeriod());
        }

        if (checkAccess(GetDepositListOperation.class))
        {
            GetDepositListOperation operation = createOperation(GetDepositListOperation.class);
            frm.setDeposits(operation.getList());
        }


	    if (checkAccess(GetLoanListOperation.class))
        {
            GetLoanListOperation operation = createOperation(GetLoanListOperation.class);
	        List<LoanLink> loans = operation.getLoans();
            frm.setLoans(loans);
            frm.setScheduleItems(buildNextScheduleItemMap(operation, loans));
        }

	    frm.setUser(personData.getPerson());

        return mapping.findForward(FORWARD_SHOW);
    }

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

