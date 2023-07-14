package com.rssl.phizic.web.common.client.ext.sbrf.payment.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ѕолучение списка подуслуг группы услуг (если таковые имеютс€) либо списка поставщиков группы услуги.
 * @author lukina
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListServicesPaymentAction extends ListPaymentServiceActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListServicesPaymentForm frm = (ListServicesPaymentForm) form;
		ListServicesPaymentOperation operation = createOperation("ListServicesPaymentOperation", "RurPayJurSB");
		operation.initialize(frm.getServiceId());
		updateRegion(frm);
		String search = StringHelper.getEmptyIfNull(frm.getSearchServices());
		if (search.trim().length() > 0)
		{
			search(frm);
		}
		else
		{
			updateServicesList(operation, frm);
		}

		if (ApplicationUtil.isNotApi())
		{
			updateParentsServiceList(frm, operation);
			frm.setIsAutoPaySearch(isAutoPayProvider(frm));
		}
		return getCurrentMapping().findForward(FORWARD_START);
	}

	private void updateParentsServiceList(ListServicesPaymentForm frm, ListServicesPaymentOperation operation) throws Exception
	{
		//получаем иерархию услуг дл€ "хлебных крошек"
		if (StringHelper.isNotEmpty(frm.getParentIds()))
		{
			Query query = operation.createQuery("parentServicesList");
			query.setParameterList("parentIds",frm.getParentIds().split(","));
			List<Object[]> list = query.executeList();
			Map<String,String> parentServices = new HashMap<String,String>(0);
			for(Object[] el : list)
			{
				parentServices.put(el[0].toString(), el[1].toString());
			}
			frm.setParentServices(parentServices);
		}
	}

	protected void updateServicesList(ListServicesPaymentOperation operation, ListServicesPaymentForm frm) throws Exception
	{
		if (isAutoPayProvider(frm) && !isESBAutoPaymentPermit() && !isIQWaveAutoPaymentPermit())
		{
			frm.setProviders(new ArrayList<Object>());
			return;
		}

		Long regionId = getRegionId(frm);
		// Paging
		int currentPage = frm.getCurrentPage();
		int itemsPerPage = frm.getItemsPerPage();

		//ѕолучать сразу листь€, т.е. поставщиков
		boolean getFinalDescendants =  getFinalDescendants(operation);
		boolean isMApiLT52 = MobileApiUtil.isMobileApiLT(MobileAPIVersions.V5_20);
		if ((getFinalDescendants || (isMApiLT52 ? operation.isLeaf() : !operation.hasChildServices()))&&
			! isIncludeServices(frm))
		{
			//получаем список поставщиков, прив€занных к данной услуге
			frm.setProviders(getServiceProviderList(operation, frm));
		}
		else
		{
			//получаем список подуслуг данной услуги
			String queryName = "childServiceList";
			if (isMApiLT52)
				queryName = "childServiceListMApiLT52";

			Query query = operation.createQuery(queryName);
			query.setParameter("region_id", RegionHelper.isAllRegionsSelected(regionId) ? null : regionId);
			query.setParameter("parentId", frm.getServiceId());
			query.setParameter("first",itemsPerPage*currentPage);
			query.setParameter("last",itemsPerPage*(currentPage+1) + ((isMobileApi() || isATMApi()) ? 0 : 1)); //один элемент сверх itemsPerPage нужен дл€ стрелки пагинации в основной версии, но в mobileApi этот элемент лишний

			query.setParameter("onlyTemplateSupported", Boolean.toString(onlyTemplateSupportedProvider()));
			query.setParameter("isInternetBank", Boolean.toString(isInternetBank()));
			query.setParameter("isATMApi", Boolean.toString(isATMApi()));
			query.setParameter("isMobilebank", Boolean.toString(isMobileBank()));
			query.setParameter("isMobileApi", Boolean.toString(isMobileApi()));
			query.setParameter("isAutoPayProvider", Boolean.toString(isAutoPayProvider(frm)));
			query.setParameter("isIQWaveAutoPaymentPermit", Boolean.toString(isIQWaveAutoPaymentPermit()));
			query.setParameter("isESBAutoPaymentPermit", Boolean.toString(isESBAutoPaymentPermit()));
			query.setParameter("isRegion", Boolean.toString(regionId != null));
			Long parentRegionId = operation.getRegionParentId(regionId);
			query.setParameter("isParentRegionId", parentRegionId == null ? "false" : "true");
			query.setParameter("parent_region_id", parentRegionId);
			if (isMApiLT52)
			{
				int cardProviderAllowed = operation.getCardProvidersAllowed();
				query.setParameter("isCardAccountType", Boolean.toString(cardProviderAllowed == 1));
				query.setParameter("isDepositType", Boolean.toString(operation.getAccountProvidersAllowed() == 1));
				query.setParameter("isFederalProvider", Boolean.toString(cardProviderAllowed == 2));
				query.setParameter("clientType", operation.getClientType());
			}
			additionalParametersSetting(query);

			if (isInternetBank())
			{
				query.setMaxResults(itemsPerPage + 1);
				query.setFirstResult(0);
				if (currentPage > 0)
					query.setFirstResult(itemsPerPage*currentPage);
			}
			List<Object> childServiceList = query.executeList();
			//≈сли нет услуг, то пытемс€ получить поставщиков услуги (справедливо и дл€ mApi и ATM)
			if (childServiceList.isEmpty())
			{
				//—писок поставщиков, прив€занных к услуге
				frm.setProviders(getServiceProviderList(operation, frm));
			}
			else
				frm.setServices(childServiceList);
		}
	}

	/**
	 * ¬озвращает список поставщиков услуг, прив€занных к услуге
	 * @param operation
	 * @param frm
	 * @return
	 */
	private List<Object> getServiceProviderList(ListServicesPaymentOperation operation, ListServicesPaymentForm frm) throws BusinessException, DataAccessException
	{
		// Paging
		int currentPage = frm.getCurrentPage();
		int itemsPerPage = frm.getItemsPerPage();
		//ѕолучать сразу листь€, т.е. поставщиков
		boolean getFinalDescendants =  getFinalDescendants(operation);
		boolean isMApiLT52 = MobileApiUtil.isMobileApiLT(MobileAPIVersions.V5_20);
		Long regionId = getRegionId(frm);

		Query query = operation.createQuery(isMApiLT52 ? "serviceProviderListMApiLT52" : "serviceProviderList");
		query.setParameter("region_id", RegionHelper.isAllRegionsSelected(regionId) ? null : regionId);
		query.setParameter("serviceId", frm.getServiceId());

		query.setParameter("isInternetBank", Boolean.toString(isInternetBank()));
		query.setParameter("isATMApi", Boolean.toString(isATMApi()));
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
		query.setMaxResults(itemsPerPage + ((isMobileApi() || isATMApi()) ? 0 : 1)); //один элемент сверх itemsPerPage нужен дл€ стрелки пагинации в основной версии, но в mobileApi этот элемент лишний
		query.setFirstResult(0);
		if (currentPage > 0)
			query.setFirstResult(itemsPerPage*currentPage);

		query.setParameter("onlyTemplateSupported", Boolean.toString(onlyTemplateSupportedProvider()));
		query.setParameter("getFinalDescendants", getFinalDescendants);
		additionalParametersSetting(query);

		return query.executeList();
	}

	/**
	 * @param operation операци€
	 * @return получать листь€ дерева иерархии услуг, пропуска€ все промежуточные подуслуги, т.е. сразу поставщиков
	 */
	protected boolean getFinalDescendants(ListServicesPaymentOperation operation)
	{
		return false;
	}

	protected boolean isInternetBank(){
		return true;
	}
}
