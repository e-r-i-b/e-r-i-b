package com.rssl.phizic.web.common.client.ext.sbrf.payment.services;

import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ext.sbrf.payment.ListCategoryPaymentsOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceActionBase;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * —писок групп услуг или список услуг. ƒл€ старой иерархии услуг (< 11 релиз).
 * Ќа данный момент используетс€ только в mAPI < 5.20
 * @author lukina
 * @ created 09.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListCategoryPaymentsAction extends ListPaymentServiceActionBase
{
	private static  final int TRANSFER_COUNT = 4;
	private static  final int DEPOSITS_AND_LOANS_COUNT = 3;
	private static  final int PFR_COUNT = 1;
	private static  final int TAX_PAYMENT_COUNT = 1;

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListCategoryPaymentsOperation operation = createOperation("ListCategoryPaymentsOperation", "RurPayJurSB");
		ListCategoryPaymentsForm frm = (ListCategoryPaymentsForm) form;
		updateRegion(frm);
		String search = frm.getSearchServices();
		if (search != null && search.trim().length() > 0)
		{
			search(frm);
		}
		else
		{
			updatePaymentServicesList(operation, frm);
		}

		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected void updatePaymentServicesList(ListCategoryPaymentsOperation operation, ListCategoryPaymentsForm frm) throws Exception
	{
		if (isAutoPayProvider(frm) && !isESBAutoPaymentPermit() && !isIQWaveAutoPaymentPermit())
		{
			frm.setServices(new ArrayList<Object>());
			return;
		}

		String categoryId = frm.getCategoryId();
		//учитываем количество статических платежей
		int paymentCount = 0;
		if (!isMobileBank())
		{
			if (categoryId.equals("TRANSFER"))
			{
				paymentCount = TRANSFER_COUNT;
			}
			else if (categoryId.equals("DEPOSITS_AND_LOANS"))
			{
				paymentCount = DEPOSITS_AND_LOANS_COUNT;
			}
			else if (categoryId.equals("PFR"))
			{
				paymentCount = PFR_COUNT;
			}
			else if (categoryId.equals("TAX_PAYMENT"))
			{
				paymentCount = TAX_PAYMENT_COUNT;
			}
		}

		Long regionId = getRegionId(frm);

		// Paging
		int currentPage = frm.getCurrentPage();
		int itemsPerPage = frm.getItemsPerPage();
		int firstResult = itemsPerPage*currentPage - paymentCount;
		int maxResults = firstResult + itemsPerPage + ((isMobileApi() || isATMApi()) ? 0 : 1); //один элемент сверх itemsPerPage нужен дл€ стрелки пагинации в основной версии, но в mobileApi этот элемент лишний

		final boolean isMApiLT52 = MobileApiUtil.isMobileApiLT(MobileAPIVersions.V5_20); //это mAPI » его верси€ < 5.20

		//получаем количество групп услуг, прив€занных к данной категории
		Query query = operation.createQuery(isMApiLT52 ? "countGroupMApiLT52" : "countGroup");
		query.setParameter("categoryId", categoryId);
		query.setParameter("region_id", RegionHelper.isAllRegionsSelected(regionId) ? null : regionId);

		query.setParameter("isInternetBank", Boolean.toString(isInternetBank()));
		query.setParameter("isATMApi",Boolean.toString(isATMApi()));
		query.setParameter("isMobilebank", Boolean.toString(isMobileBank()));
		query.setParameter("isMobileApi", Boolean.toString(isMobileApi()));
        query.setParameter("isAutoPayProvider", Boolean.toString(isAutoPayProvider(frm)));
		query.setParameter("isIQWaveAutoPaymentPermit", Boolean.toString(isIQWaveAutoPaymentPermit()));
		query.setParameter("isESBAutoPaymentPermit", Boolean.toString(isESBAutoPaymentPermit()));
		query.setParameter("isRegion", Boolean.toString(regionId != null));
		int cardProviderAllowed = operation.getCardProvidersAllowed();
		query.setParameter("isCardAccountType", Boolean.toString(cardProviderAllowed == 1));
		query.setParameter("isDepositType", Boolean.toString(operation.getAccountProvidersAllowed() == 1));
		query.setParameter("isFederalProvider", Boolean.toString(cardProviderAllowed == 2));
		query.setParameter("clientType", operation.getClientType());
		Long parentRegionId = operation.getRegionParentId(regionId);
		query.setParameter("isParentRegionId", parentRegionId == null ? "false" : "true");
		query.setParameter("parent_region_id", parentRegionId);
		Object count =  query.executeUnique();
		Integer countServGroup = Integer.decode(count.toString());

		if (countServGroup+paymentCount > itemsPerPage*currentPage)
		{
			//получаем список групп услуг
			query = operation.createQuery(isMApiLT52 ? "groupListMApiLT52" : "groupList");
			query.setParameter("categoryId", categoryId);
			query.setParameter("region_id", regionId);
			query.setParameter("first",firstResult);
			query.setParameter("last",maxResults);

			query.setParameter("isInternetBank", Boolean.toString(isInternetBank()));
			query.setParameter("isATMApi",Boolean.toString(isATMApi()));
			query.setParameter("isMobilebank", Boolean.toString(isMobileBank()));
			query.setParameter("isMobileApi", Boolean.toString(isMobileApi()));
			query.setParameter("isAutoPayProvider", Boolean.toString(isAutoPayProvider(frm)));
			query.setParameter("isIQWaveAutoPaymentPermit", Boolean.toString(isIQWaveAutoPaymentPermit()));
			query.setParameter("isESBAutoPaymentPermit", Boolean.toString(isESBAutoPaymentPermit()));
			query.setParameter("isRegion", Boolean.toString(regionId != null));
			cardProviderAllowed = operation.getCardProvidersAllowed();
			query.setParameter("isCardAccountType", Boolean.toString(cardProviderAllowed == 1));
			query.setParameter("isDepositType", Boolean.toString(operation.getAccountProvidersAllowed() == 1));
			query.setParameter("isFederalProvider", Boolean.toString(cardProviderAllowed == 2));
			query.setParameter("clientType", operation.getClientType());
			parentRegionId = operation.getRegionParentId(regionId);
			query.setParameter("isParentRegionId", parentRegionId == null ? "false" : "true");
			query.setParameter("parent_region_id", parentRegionId);
			frm.setGroupServices(query.executeList());
		}

		if (countServGroup < itemsPerPage*(currentPage+1))
		{
			//получаем список услуг
			query = operation.createQuery(isMApiLT52 ? "serviceListMApiLT52" : "serviceList");
			query.setParameter("categoryId", categoryId);
			query.setParameter("region_id", regionId);
			query.setParameter("first", firstResult - countServGroup);
			query.setParameter("last", maxResults - countServGroup);

			query.setParameter("isInternetBank", Boolean.toString(isInternetBank()));
			query.setParameter("isATMApi",Boolean.toString(isATMApi()));
			query.setParameter("isMobilebank", Boolean.toString(isMobileBank()));
			query.setParameter("isMobileApi", Boolean.toString(isMobileApi()));
			query.setParameter("isAutoPayProvider", Boolean.toString(isAutoPayProvider(frm)));
			query.setParameter("isIQWaveAutoPaymentPermit", Boolean.toString(isIQWaveAutoPaymentPermit()));
			query.setParameter("isESBAutoPaymentPermit", Boolean.toString(isESBAutoPaymentPermit()));
			query.setParameter("isRegion", Boolean.toString(regionId != null));
			cardProviderAllowed = operation.getCardProvidersAllowed();
			query.setParameter("isCardAccountType", Boolean.toString(cardProviderAllowed == 1));
			query.setParameter("isDepositType", Boolean.toString(operation.getAccountProvidersAllowed() == 1));
			query.setParameter("isFederalProvider", Boolean.toString(cardProviderAllowed == 2));
			query.setParameter("clientType", operation.getClientType());
			parentRegionId = operation.getRegionParentId(regionId);
			query.setParameter("isParentRegionId", parentRegionId == null ? "false" : "true");
			query.setParameter("parent_region_id", parentRegionId);
			frm.setServices(query.executeList());
		 }
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		ListCategoryPaymentsForm frm = (ListCategoryPaymentsForm) form;

		return mapping.getPath() + "/" + frm.getCategoryId();
	}

	protected boolean isInternetBank(){
		return true;
	}
}
