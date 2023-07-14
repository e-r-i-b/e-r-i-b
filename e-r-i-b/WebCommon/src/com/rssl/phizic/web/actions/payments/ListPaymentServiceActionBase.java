package com.rssl.phizic.web.actions.payments;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.dictionaries.regions.RegionsListOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentSearchOperation;
import com.rssl.phizic.operations.payment.CreateAutoPaymentOperation;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 20.02.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class ListPaymentServiceActionBase extends OperationalActionBase
{

	protected static final String INVOICE_PROVIDER_PARAMETER_NAME = "invoiceProvider";
	protected static final String IQ_WAVE_UUID_PARAMETER_NAME     = "IQWaveUUID";
	protected static final String REGION_ID_FIELD = "regionId";
	public static final int MIN_SEARCH_STRING = 3;
	public static final String MIN_SEARCH_STRING_MESSAGE = "ѕожалуйста, введите в поле дл€ поиска организации не менее 3-х символов.";
	protected static final String REGION_NAME_FIELD = "regionName";
	protected static final Pattern DIGIT_PATTERN = Pattern.compile("^\\d+$");
	protected static final Pattern QUOTE_PATTERN = Pattern.compile("( +|'|\"|Ђ|ї|С|Т|В|У|Ф|Д)");
	protected static final String QUOTE = "%";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.search", "start");
		map.put("button.saveRegion", "saveRegion");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected void search(ListPaymentServiceFormBase frm) throws Exception
	{
		ListServicesPaymentSearchOperation operation = createSearchOperation();
		Query query = createQuery(operation);
		fillQuery(query, frm, operation);
	}

	protected boolean isMobileBank()
	{
		return false;
	}

	protected boolean isMobileApi()
	{
		return false;
	}

	protected boolean isATMApi()
	{
		return false;
	}

	protected boolean isInternetBank()
	{
		return false;
	}

	/**
	 * @return разрешено ли клиенту создавать автоплатежи через iqwave.
	 * @throws BusinessException
	 */
	protected boolean isIQWaveAutoPaymentPermit() throws BusinessException
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
		{
			return false; //дл€ сотрудника запрещено создавать iqwave.
		}
		return checkAccess(CreateAutoPaymentOperation.class, "CreateAutoPaymentPayment");
	}

	/**
	 * @return разрешено ли клиенту/сотруднику создавать шинные автоплатежи.
	 * @throws BusinessException
	 */
	protected boolean isESBAutoPaymentPermit() throws BusinessException
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
		{
			return checkAccess(CreateESBAutoPayOperation.class, "CreateEmployeeAutoPayment");
		}
		return checkAccess(CreateESBAutoPayOperation.class, "ClientCreateAutoPayment");
	}

	protected ListServicesPaymentSearchOperation createSearchOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation("ListServicesPaymentSearchOperation", "RurPayJurSB");
	}

	protected void fillQuery(Query query, ListPaymentServiceFormBase frm, ListServicesPaymentSearchOperation operation) throws BusinessException, DataAccessException
	{
		if (isAutoPayProvider(frm) && !isESBAutoPaymentPermit() && !isIQWaveAutoPaymentPermit())
		{
			frm.setSearchResults(new ArrayList<Object>());
			return;
		}
		Long regionId = getRegionId(frm);
		query.setParameter("region_id", RegionHelper.isAllRegionsSelected(regionId) ? null : regionId);

		String search = frm.getSearchServices().trim();
		if (DIGIT_PATTERN.matcher(search).matches())
		{
			query.setParameter("search_long", search);
			query.setParameter("search", search);
		}
		else
		{
			query.setParameter("search_long", null);
			query.setParameter("search", QUOTE_PATTERN.matcher(search).replaceAll(QUOTE));
		}
		query.setParameter("serviceId", frm.getServiceId());
		query.setParameter("categoryId", frm.getCategoryId());

		// Paging
		int currentPage = frm.getSearchPage();
		int itemsPerPage = frm.getItemsPerPage();

		query.setMaxResults(itemsPerPage + 1);
		query.setFirstResult(0);
		if (currentPage > 0)
			query.setFirstResult(itemsPerPage*currentPage);

		query.setParameter("isInternetBank",Boolean.toString(isInternetBank()));
		query.setParameter("isMobilebank", Boolean.toString(isMobileBank()));
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
		query.setParameter("onlyTemplateSupported", Boolean.toString(onlyTemplateSupportedProvider()));
		query.setParameterList("paymentList", getPaymenyList());
		query.setParameter("loginId", operation.getLogin().getId());
		query.setParameter("needSearchPayments", Boolean.toString(needSearchPayments()));
		additionalParametersSetting(query);
		frm.setSearchResults(query.executeList());
	}

	/**
	 * @return список платежей и за€вок, которые необходимо включать в результаты поиска
	 */
	protected List<String> getPaymenyList()
	{
		return null;
	}

	/**
	 * @return нужно ли включать в результаты поиска поставщиков платежи и за€вки
	 */
	protected boolean needSearchPayments()
	{
		return false;
	}
	/**
	 * @param form форма
	 * @return производить ли поиск поставщиков с поддержкой создани€ автоплатеж
	 * (true - с поддержкой автоплатежа, false - без поддержки, null - и те и другие)
	 */
	protected boolean isAutoPayProvider(ListPaymentServiceFormBase form)
	{
		return false;
	}

	/**
	 * @param form
	 * @return признак, отвечающий за необходимость получени€ подкатегорий
	 */
	protected boolean isIncludeServices(ListPaymentServiceFormBase form)
	{
		return false;
	}

	/**
	 * @return поиск только поставщиков поддерживающих создание шаблона
	 */
	protected boolean onlyTemplateSupportedProvider()
	{
		return false;
	}

	protected Long getRegionId(ListPaymentServiceFormBase form) throws BusinessException
	{
		return getRegionId((String)form.getField(REGION_ID_FIELD));
	}

	protected Long getRegionId(String regionId) throws BusinessException
	{
		if (StringHelper.isNotEmpty(regionId))
		{
			Long regId = Long.parseLong(regionId);
			return RegionHelper.isAllRegionsSelected(regId) ? null : regId;
		}
		Region region = RegionHelper.getCurrentRegion();
		if (region == null)
			return null;
		else
			return region.getId();
	}

	protected void updateRegion(ListPaymentServiceFormBase form) throws BusinessException
	{
		String regionId = (String)form.getField(REGION_ID_FIELD);
		String regionName = (String)form.getField(REGION_NAME_FIELD);

		if (!StringHelper.isEmpty(regionId))
		{
			RegionsListOperation operation = createOperation("RegionsListOperation", "RegionsList");
			operation.initialize(Long.parseLong( regionId ));

			regionName = operation.getRegion().getName();
		}

		if (StringHelper.isEmpty(regionId) || StringHelper.isEmpty(regionName))
		{
			Region region = RegionHelper.getCurrentRegion();
			if (region != null && region.getId()!=null)
			{
				form.setField(REGION_ID_FIELD, region.getId().toString());
				form.setField(REGION_NAME_FIELD, region.getName());
			}
		}
	}

	/**
	 * —охранение выбранного региона в профиль пользовател€
	 * @param mapping  mapping
	 * @param form     form
	 * @param request  request
	 * @param response response
	 * @return ActionForward
	 */
	public ActionForward saveRegion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RegionsListOperation operation = createOperation("RegionsListOperation", "RurPayJurSB");
		ListPaymentServiceFormBase frm = (ListPaymentServiceFormBase) form;
		String regionId = (String) frm.getField(REGION_ID_FIELD);
		if (StringHelper.isEmpty(regionId) || regionId.equals("-1"))
			operation.initialize();
		else
			operation.initialize(Long.parseLong(regionId));
		
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		operation.saveRegion(login);
		return start(mapping, form, request, response);
	}

	protected Query createQuery(ListServicesPaymentSearchOperation operation)
	{
		return operation.createQuery("search");
	}

	protected void additionalParametersSetting(Query query) throws BusinessException
	{
		query.setParameter(INVOICE_PROVIDER_PARAMETER_NAME,Boolean.toString(false));
	}
}
