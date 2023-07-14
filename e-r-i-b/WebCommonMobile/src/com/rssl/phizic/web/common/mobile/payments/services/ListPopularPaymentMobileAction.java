package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.phizic.operations.ext.sbrf.payment.ListPopularPaymentsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceShort;
import com.rssl.phizic.dataaccess.query.Query;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 10.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListPopularPaymentMobileAction extends OperationalActionBase
{
    protected Map<String, String> getKeyMethodMap()
    {
        return new HashMap<String, String>();
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ListPopularPaymentMobileForm frm = (ListPopularPaymentMobileForm) form;
        ListPopularPaymentsOperation operation =  createOperation("ListPopularPaymentsOperation", "RurPayJurSB");

        frm.setPopularPayments(getPopularServices(operation));
        return mapping.findForward(FORWARD_START);
    }

	private List<PaymentServiceShort> getPopularServices(ListPopularPaymentsOperation operation) throws Exception
	{
		//получаем список популярных услуг
		Query query = operation.createQuery("serviceList");

		query.setParameter("isATMApi", Boolean.toString(false));

		return query.executeList();
	}
}
