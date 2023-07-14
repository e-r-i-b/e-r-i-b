package com.rssl.phizic.web.atm.payments.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceShort;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShortForATM;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ext.sbrf.payment.ListPopularPaymentsOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 10.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListPopularPaymentATMAction extends ListATMActionBase
{
    protected Map<String, String> getKeyMethodMap()
    {
        return new HashMap<String, String>();
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    ListPopularPaymentsOperation operation =  createOperation("ListPopularPaymentsOperation", "RurPayJurSB");
	    ListPopularPaymentATMForm    frm = (ListPopularPaymentATMForm) form;

	    if ("service".equals(frm.getType()))
	    {
		    frm.setPopularPayments( getPopularServices(operation) );
            return mapping.findForward(FORWARD_START);
	    }

	    if ("provider".equals(frm.getType()))
	    {
		    frm.setPopularPayments( getPopularProviders(frm, operation) );
		    return mapping.findForward(FORWARD_START);
	    }

	    throw new BusinessException("Ќе установлен тип запрашиваемой информации.");
    }

	private List<PaymentServiceShort> getPopularServices(ListPopularPaymentsOperation operation) throws Exception
	{
		//получаем список попул€рных услуг
		Query query = operation.createQuery("serviceList");

		query.setParameter("isATMApi", Boolean.toString(true));

		return query.executeList();
	}

	private List<ServiceProviderShortForATM> getPopularProviders(ListPopularPaymentATMForm frm, ListPopularPaymentsOperation operation) throws Exception
	{
		Long regionId = getRegionIdWithGuid(frm);

		//получаем список попул€рных поставщиков
		Query query = operation.createQuery("providerList");

		query.setParameter("region_id",             regionId);
		query.setParameter("isRegion",              Boolean.toString(regionId != null));

		int cardProviderAllowed = operation.getCardProvidersAllowed();
		query.setParameter("isCardAccountType",     Boolean.toString(cardProviderAllowed == 1));
		query.setParameter("isDepositType",         Boolean.toString(operation.getAccountProvidersAllowed() == 1));
		query.setParameter("isFederalProvider",     Boolean.toString(cardProviderAllowed == 2));
		query.setParameter("clientType",            operation.getClientType());
		query.setParameter("isInternetBank",        Boolean.toString(false));
		query.setParameter("isATMOnly",             Boolean.toString(true));

		Long parentRegionId = operation.getRegionParentId(regionId);
		query.setParameter("isParentRegionId",      parentRegionId == null ? "false" : "true");
		query.setParameter("parent_region_id",      parentRegionId);
		query.setParameter("parent_region_id",      parentRegionId);

		return query.executeList();
	}
}
