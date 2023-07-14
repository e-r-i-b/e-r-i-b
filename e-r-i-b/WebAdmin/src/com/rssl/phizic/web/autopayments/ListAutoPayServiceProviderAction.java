package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentSearchOperation;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceActionBase;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшн дл€ поиска и вывода списка постащиков поддерживающих автоплатеж
 * @author niculichev
 * @ created 26.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListAutoPayServiceProviderAction extends ListPaymentServiceActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.filter", "filter");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListAutoPayServiceProviderForm frm = (ListAutoPayServiceProviderForm) form;
		frm.setFromStart(true);

		ListServicesPaymentSearchOperation operation = createSearchOperation();
		operation.initialize();

		frm.setActivePerson(operation.getPerson());

		return getCurrentMapping().findForward(FORWARD_START);
	}

	public ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListAutoPayServiceProviderForm frm = (ListAutoPayServiceProviderForm) form;

		ListServicesPaymentSearchOperation operation = createSearchOperation();
		operation.initialize();

		doFilter(operation, frm);

		frm.setActivePerson(operation.getPerson());

		return mapping.findForward(FORWARD_START);
	}

	private void doFilter(ListServicesPaymentSearchOperation operation, ListAutoPayServiceProviderForm frm) throws BusinessException
	{
		try
		{
			Query query = createQuery(operation);
			frm.setField(REGION_ID_FIELD, frm.getFilter(REGION_ID_FIELD));
			fillQuery(query, frm, operation);
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	protected ListServicesPaymentSearchOperation createSearchOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation("ListServicesPaymentSearchOperation", "CreateEmployeeAutoPayment");
	}

	protected boolean isAutoPayProvider(ListPaymentServiceFormBase form)
	{
		return true;
	}

	protected Query createQuery(ListServicesPaymentSearchOperation operation)
	{
		return operation.createQuery("searchProvider");
	}
}
