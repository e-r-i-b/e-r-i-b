package com.rssl.phizic.web.payments;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.payment.GetEmployeePaymentListOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.claims.ClaimListAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Egorova
 * @ created 11.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentListAction extends ClaimListAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map=new HashMap<String, String>();
	    map.put("button.filter", "filter");
	    return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PaymentListForm         frm         = (PaymentListForm) form;
		createDefaultParameters(frm);
		return doFilter(mapping, frm, request);
	}

	public ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PaymentListForm         frm         = (PaymentListForm) form;
		return doFilter(mapping, frm, request);
	}

	private ActionForward doFilter(ActionMapping mapping, PaymentListForm frm, HttpServletRequest request) throws Exception
	{
		Map<String, Object> filters         = frm.getFilters();
		MapValuesSource mapValuesSource = new MapValuesSource(filters);
		Form filterForm      = createForm();
		FormProcessor<ActionMessages, ?> formProcessor   = createFormProcessor(mapValuesSource, filterForm);

		if ( formProcessor.process() )
		{
			GetEmployeePaymentListOperation operation = createOperation(GetEmployeePaymentListOperation.class);
			Query query = operation.createQuery("list");

			Map<String, Object>   parameters = createParameters(formProcessor.getResult());

			query.setParameters(parameters);
			if (parameters.get("state")!=null)
			{
				String resultState = filters.get("state").toString();
				if (resultState.length()==0)
				{
					query.setParameter("state",resultState);
				}
				else
				{
					query.setParameterList("state", resultState.split(","));
					query.setParameter("state_list", resultState);
				}
			}
			if (parameters.get("toDate")!=null)
			{
				Calendar toDate = DateHelper.getCurrentDate();;
				toDate.add(Calendar.DAY_OF_MONTH,1);
				toDate.add(Calendar.MILLISECOND, -1);
				toDate.get(Calendar.MONTH);
				query.setParameter("toDate", toDate);
			}
			List<BusinessDocument> payments = query.setMaxResults(webPageConfig().getListLimit()+1).executeList();
			frm.setListLimit(webPageConfig().getListLimit());
			frm.setPayments(payments);
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
		}

		PaymentFormService paymentFormService = new PaymentFormService();
		List forms = paymentFormService.getAllFormsLight();
		frm.setForms(forms);

		return mapping.findForward(FORWARD_SHOW);
	}

	private Map<String, Object> createDefaultParameters(PaymentListForm frm)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();

		Calendar currentDate = DateHelper.getCurrentDate();

		frm.setFilter("fromDate", String.format("%1$td.%1$tm.%1$tY",currentDate));
		frm.setFilter("toDate", String.format("%1$td.%1$tm.%1$tY",currentDate));
		frm.setFilter("type", "GoodsAndServicesPayment");

		return parameters;
	}

}
