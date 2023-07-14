package com.rssl.phizic.web.actions.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.aggr.AggregationService;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ext.sbrf.payment.ListCategoryOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentSearchOperation;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krenev
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 * Базовый класс каталога услуг
 */
public abstract class CatalogActionBase extends ListPaymentServiceActionBase
{
	public static final String PAYMENTS_FUNCTIONALITY = "PAYMENTS";
	public static final String ESB_AUTOPAYMENTS_FUNCTIONALITY = "ESB_AUTOPAYMENTS";
	public static final String IQW_AUTOPAYMENTS_FUNCTIONALITY = "IQW_AUTOPAYMENTS";

	/**
	 * @return ключ текущей секции, из которой требуется строить каталог услуг по агрегированным данным.
	 */
	public String getCurrentPartition() throws BusinessException
	{
		try
		{
			return AggregationService.getCurrentPartition();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		IndexForm frm = (IndexForm) form;
		updateRegion(frm);
		String search = StringHelper.getEmptyIfNull(frm.getSearchServices()).trim();
		if (search.length() >= MIN_SEARCH_STRING)
		{
			return search(frm);
		}

		frm.setField("isSearch", false);
		if (search.length() > 0 )
			saveMessage(request, MIN_SEARCH_STRING_MESSAGE);

		Long parentServiceId = frm.getServiceId();
		if (parentServiceId == null)
		{
			//если не передан родительский сервис - возвращаем верхнеуровневые группы услуг.
			frm.setCategories(getTopLevelServices(frm));
			return getCurrentMapping().findForward(FORWARD_START);
		}
		List<Object[]> services= getServices(frm);
		if (!services.isEmpty())
		{
			//если есть дочерние услуги - возвращаем их
			frm.setServices(services);
			return getCurrentMapping().findForward(FORWARD_START);
		}
		//возвращаем поставщиков
		frm.setProviders(getProviders(frm));
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected ActionForward search(IndexForm frm) throws Exception
	{
		frm.setField("isSearch", true);
		frm.setSearchResults(getSearchResult(frm));
		return getCurrentMapping().findForward(FORWARD_START);
	}

	private List<Object> getSearchResult(IndexForm frm) throws Exception
	{
		String[] functionality = getFunctionality(frm);
		if (functionality.length == 0)
		{
			return Collections.emptyList();
		}
		ListServicesPaymentSearchOperation operation = createSearchOperation();
		Query query = operation.createQuery(getSearchResultQueryName(frm));
		fillSearchQuery(query, frm);
		return query.executeList();
	}

	protected void fillSearchQuery(Query query, IndexForm frm) throws BusinessException
	{
		query.setParameterList("functionality", getFunctionality(frm));
		query.setParameter("pkey", getCurrentPartition());
		query.setParameter("chanel", getChanel());
		query.setParameter("region_id", getRegion(frm));
		query.setParameter("service_id", frm.getServiceId() == null ? -1 : frm.getServiceId());

		String search = getSearchString(frm);
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
		fillSearchPagination(query, frm);

	}

	protected String getSearchString(IndexForm frm)
	{
		return frm.getSearchServices().trim();
	}

	protected void fillSearchPagination(Query query, IndexForm frm)
	{
		int currentPage = frm.getSearchPage();
		int itemsPerPage = frm.getItemsPerPage();
		query.setMaxResults(itemsPerPage + 1);
		query.setFirstResult(0);
		if (currentPage > 0)
			query.setFirstResult(itemsPerPage * currentPage);
	}

	protected void fillPagination(Query query, IndexForm frm)
	{
		int currentPage = frm.getCurrentPage();
		int itemsPerPage = frm.getItemsPerPage();
		query.setMaxResults(itemsPerPage + 1);
		query.setFirstResult(0);
		if (currentPage > 0)
			query.setFirstResult(itemsPerPage * currentPage);
	}

	protected List<Object[]> getProviders(IndexForm frm) throws Exception
	{
		String[] functionality = getFunctionality(frm);
		if (functionality.length == 0)
		{
			return Collections.emptyList();
		}
		ListCategoryOperation operation = createOperation("ListCategoryOperation", "RurPayJurSB");
		Query query = operation.createQuery(getProvidersQueryName(frm));
		query.setParameterList("functionality", functionality);
		query.setParameter("chanel", getChanel());
		query.setParameter("pkey", getCurrentPartition());
		query.setParameter("region_id", getRegion(frm));
		query.setParameter("service_id", frm.getServiceId());
		fillPagination(query, frm);
		return query.executeList();
	}

	protected List<Object[]> getServices(IndexForm frm) throws Exception
	{
		String[] functionality = getFunctionality(frm);
		if (functionality.length == 0)
		{
			return Collections.emptyList();
		}
		ListCategoryOperation operation = createOperation("ListCategoryOperation", "RurPayJurSB");
		Query query = operation.createQuery(getServicesQueryName(frm));
		query.setParameterList("functionality", functionality);
		query.setParameter("pkey", getCurrentPartition());
		query.setParameter("chanel", getChanel());
		query.setParameter("region_id", getRegion(frm));
		query.setParameter("parent_id", frm.getServiceId());
		fillPagination(query, frm);
		return query.executeList();
	}

	protected List<Object[]> getTopLevelServices(IndexForm frm) throws Exception
	{
		String[] functionality = getFunctionality(frm);
		if (functionality.length == 0)
		{
			return Collections.emptyList();
		}
		ListCategoryOperation operation = createOperation("ListCategoryOperation", "RurPayJurSB");
		Query query = operation.createQuery(getTopLevelServicesQueryName(frm));
		query.setParameterList("functionality", functionality);
		query.setParameter("pkey", getCurrentPartition());
		query.setParameter("chanel", getChanel());
		query.setParameter("region_id", getRegion(frm));
		query.setParameter("parent_id", -1);
		return query.executeList();
	}

	private Long getRegion(IndexForm frm) throws BusinessException
	{
		Long regionId = getRegionId(frm);
		return RegionHelper.isAllRegionsSelected(regionId) ? -1 : regionId;
	}

	/**
	 * @return канал (возможные значения: WEB, MAPI, ATMAPI)
	 */

	protected abstract String getChanel();

	/**
	 * @return список функциональности, в рамках которой выполняются запросы(
	 *      платежи(PAYMENTS), шинные автоплатежи(ESB_AUTOPAYMENTS), автоплатежи iqw(IQW_AUTOPAYMENTS),
	 *      шаблоны(TEMPLATES), шаблоны МБ(MB_TEMPLATES), корзина платежей(BASKET).
	 */
	protected abstract String[] getFunctionality(IndexForm frm) throws BusinessException;


	/**
	 * @return имя запроса для верхнего уровня услуг.
	 */
	protected String getTopLevelServicesQueryName(IndexForm frm)
	{
		return "toplist.available";
	}

	/**
	 * @return имя запроса для дочерних услуг.
	 */
	protected String getServicesQueryName(IndexForm frm)
	{
		return "services.available";
	}

	/**
	 * @return имя запроса для поставщиков услуги.
	 */
	protected String getProvidersQueryName(IndexForm frm)
	{
		return "providers.available";
	}

	/**
	 * @return имя запроса для поиска
	 */
	protected String getSearchResultQueryName(IndexForm frm)
	{
		return "searchResult";
	}
}
