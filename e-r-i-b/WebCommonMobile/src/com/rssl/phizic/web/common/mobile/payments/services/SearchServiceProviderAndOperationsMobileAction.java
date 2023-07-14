package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.common.forms.parsers.DateParser;
import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.payment.GetCommonPaymentListOperation;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.IndexForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Объединение поиска по истории операций и по списку поставщиков услуг (РО 14 5.2.9)
 * @author sergunin
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 */
public class SearchServiceProviderAndOperationsMobileAction extends SearchServiceProviderMobileAction
{
    public static final String MAX_SEARCH_PERIOD = "Максимальный интервал между датами поиска не должен превышать %s дней";

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        SearchServiceProviderAndOperationMobileForm frm = (SearchServiceProviderAndOperationMobileForm) form;

        if(isInputCorrect(frm))
        {
            return searchResult(frm);
        }

        frm.setServicePaymentResult(Collections.EMPTY_LIST);
        frm.setOperationsResult(Collections.EMPTY_LIST);
        return mapping.findForward(FORWARD_START);
    }

    private boolean isInputCorrect(SearchServiceProviderAndOperationMobileForm frm) throws ParseException
    {
        boolean result = false;
        String search = StringHelper.getEmptyIfNull(frm.getSearch());

        if (search.trim().length() >= MIN_SEARCH_STRING)
        {
            DateParser dateParser = new DateParser();
            int dayPeriod = ConfigFactory.getConfig(MobileApiConfig.class).getMaxSearchPeriodInDays();
            if(frm.getFrom() != null && frm.getTo() != null
                    && DateHelper.daysDiff(DateHelper.toCalendar(dateParser.parse(frm.getFrom())), DateHelper.toCalendar(dateParser.parse(frm.getTo()))) > dayPeriod)
            {
                result = false;
                saveMessage(currentRequest(), String.format(MAX_SEARCH_PERIOD,dayPeriod));
            }
            else
            {
                result = true;
            }
        }
        else
        {
            saveMessage(currentRequest(), MIN_SEARCH_STRING_MESSAGE);
        }

        return result;
    }

    protected ActionForward searchResult(SearchServiceProviderAndOperationMobileForm frm) throws Exception
    {
		super.search(frm);

        GetCommonPaymentListOperation paymentListOperation = createOperation(GetCommonPaymentListOperation.class, "PaymentList");
        Query query = paymentListOperation.createQuery("clientHistory");
        fillPaymentsOperationListQuery(query, frm);
        frm.setOperationsResult(query.executeList());

        return getCurrentMapping().findForward(FORWARD_START);
    }

	protected void fillSearchPagination(Query query, IndexForm form)
	{
		SearchServiceProviderAndOperationMobileForm frm = (SearchServiceProviderAndOperationMobileForm) form;
		int currentPage = frm.getProviderPage();
		int itemsPerPage = frm.getProviderPerPage();

		if(itemsPerPage > 0)
			query.setMaxResults(itemsPerPage);
		query.setFirstResult(0);
		if (currentPage > 0)
			query.setFirstResult(itemsPerPage*currentPage);
	}

	private void fillPaymentsOperationListQuery(Query query, SearchServiceProviderAndOperationMobileForm frm) throws ParseException
    {
        Period period = checkDate(frm);
        query.setParameter("fromDate", period.getFromDate());
        query.setParameter("toDate", period.getToDate());
        query.setParameter("showPfp", !ApplicationUtil.isApi());
        query.setParameter("showIssueCard", !ApplicationUtil.isApi());
        query.setParameter("pfpSelected", false);
        query.setParameter("issueCardSelected", false);
	    query.setParameter("showFir", !ApplicationUtil.isApi());
	    query.setParameter("firSelected", false);
        query.setParameter("clientState", null);
        query.setListParameters("formId", (Object[]) null, 10);
        query.setParameter("creationType", null);
        query.setParameter("autoPayment", null);
        query.setParameter("fromAmount", null);
        query.setParameter("toAmount", null);
        query.setParameter("amountCurrency", null);
        query.setParameter("account", null);
        query.setParameter("receiverName", frm.getSearch());
        query.setParameter("paymentStatus", null);
        query.setParameter("extLoanClaimSelected", false);
	    query.setParameter("loanCardClaimSelected", false);

        // Paging

        int currentPage = frm.getOpPage();
        int itemsPerPage = frm.getOpPerPage();

        if(itemsPerPage > 0)
                    query.setMaxResults(itemsPerPage);
        query.setFirstResult(0);
        if (currentPage > 0)
            query.setFirstResult(itemsPerPage*currentPage);
    }

    private Period checkDate(SearchServiceProviderAndOperationMobileForm frm) throws ParseException
    {
        Calendar from = null;
        Calendar to = null;
        DateParser dateParser = new DateParser();

        if (frm.getTo() != null && frm.getFrom() != null)
        {
            from = DateHelper.toCalendar(dateParser.parse(frm.getFrom()));
            to = DateHelper.toCalendar(dateParser.parse(frm.getTo()));
        }
        else
        {
            to = Calendar.getInstance();
            from = (Calendar) to.clone();
            from.add(Calendar.MONTH, -1);
        }

        from = DateHelper.startOfDay(from);
        to = DateHelper.endOfDay(to);
        return new Period(from, to);
    }
}
