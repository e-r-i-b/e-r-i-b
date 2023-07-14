package com.rssl.phizic.web.client.ext.sbrf.loans;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.loans.LoanServiceHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 01.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class PrintLoanPaymentsAction extends OperationalActionBase
{
	public static final String FORWARD_PRINT = "Print";

	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = new HashMap<String, String>();
        return keyMap;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
	    PrintLoanPaymentsForm frm = (PrintLoanPaymentsForm) form;
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFilter());

	    FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(valuesSource, frm.FILTER_FORM);
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_PRINT);
		}

	    GetLoanInfoOperation loanInfoOperation = createOperation(GetLoanInfoOperation.class);
	    loanInfoOperation.initialize(frm.getId());
	    LoanLink link = loanInfoOperation.getLoanLink();

	    GetLoanAbstractOperation loanAbstractOperation = createOperation(GetLoanAbstractOperation.class);
	    loanAbstractOperation.initialize(link);
	    Calendar fromDate = DateHelper.fromDMYDateToDate(valuesSource.getValue("fromPeriod"));
		Calendar toDate = DateHelper.fromDMYDateToDate(valuesSource.getValue("toPeriod"));

	    Pair<Long, Long> countRec = LoanServiceHelper.getPaymentCountByDates(link.getLoan().getNextPaymentDate(), fromDate, toDate);
		long startNumber = countRec.getFirst(); // номер платежа в графике, начиная с которого строим график
		long count = countRec.getSecond();       // количество платеже в графике
		Map<LoanLink, ScheduleAbstract> scheduleAbstract = loanAbstractOperation.getScheduleAbstract(startNumber,count, false).getFirst();

	    Map<String, Object> filter = new HashMap<String, Object>();
	    filter.put("fromDate", fromDate.getTime());
	    filter.put("toDate", toDate.getTime());

	    frm.setLoanLink(link);
	    frm.setFilter(filter);
	    frm.setScheduleAbstract(scheduleAbstract.get(link));

	    PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    ActivePerson user = personData.getPerson();
		frm.setUser(user);

	    return mapping.findForward(FORWARD_PRINT);
    }
}
