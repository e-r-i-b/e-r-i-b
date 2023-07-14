package com.rssl.phizic.web.common.asyncsearch;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.operations.asynchSearch.AsynchSearchOperationBase;
import com.rssl.phizic.operations.asynchSearch.AsynchSearchServiceProvidersOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentSearchOperation;
import com.rssl.phizic.operations.payment.CreateAutoPaymentOperation;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.web.struts.HttpServletRequestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @ author: Gololobov
 * @ created: 25.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncSearchServiceProvidersAction extends AsyncSearchActionBase
{
	//Все регионы
	private static final String ALL_REGION_ID = "-1";

	private static final String TEMPLATE_CONSTANT = "template";
	private static final String AUTOPAYMENT_CONSTANT = "autopayment";
	private static final String MOBILEBANK_CONSTANT = "mobilebank";

	protected static final Pattern DIGIT_PATTERN = Pattern.compile("^\\d+$");
	protected static final Pattern QUOTE_PATTERN = Pattern.compile("( +|'|\"|«|»|‘|’|‚|“|”|„)");
	protected static final String QUOTE = "%";

	/**
	 * Мапа с параметрами для выполнения запроса
	 * @param frm 
	 * @return Map<String, Object> - мапа с параметрами для выполнения запроса:
	 * key - название парметра, value - значение параметра
	 */
	protected Map<String, Object> getQueryParametersMap(AsyncSearchForm frm) throws BusinessException
	{
		//Мапа с параметрами для запроса
		Map<String, Object> queryParametersMap = new HashMap<String, Object>();
		//Регион
		queryParametersMap.put("regionId", frm.getRegionId());
		//Вводимое значение передаётся параметром "q"
		String search = (String) frm.getField(SEARCH_FIELD_NAME);
		String switchToEngSearch = null;
		String switchToRusSearch = null;
		String switchToEngRusSearch = null;
		String translitSearch = null;

		if (DIGIT_PATTERN.matcher(search).matches())
		{
			queryParametersMap.put("search_long", search);
			queryParametersMap.put("search", search);
		}
		else
		{
			queryParametersMap.put("search_long", "");
			queryParametersMap.put("search", QUOTE_PATTERN.matcher(search).replaceAll(QUOTE));
			switchToEngSearch = StringUtils.getSwitchToAnotherLayout(search, 0);
			switchToRusSearch = StringUtils.getSwitchToAnotherLayout(search, 1);
			//На случай если ввели текст, смешанный из рус. и англ. букв
			switchToEngRusSearch = StringUtils.getSwitchToAnotherLayout(search, null);

			translitSearch = StringUtils.translitToAnother(search);
		}

		//Попробуем также поискать в другой раскладке
		queryParametersMap.put("switchToEngSearch", switchToEngSearch);
		queryParametersMap.put("switchToRusSearch", switchToRusSearch);
		queryParametersMap.put("switchToEngRusSearch", switchToEngRusSearch);
		//И в транслите тоже порыщем
		queryParametersMap.put("translitSearch", translitSearch);
		ListServicesPaymentSearchOperation operation = createOperation(ListServicesPaymentSearchOperation.class, "RurPayJurSB");
		queryParametersMap.put("cardProvidersAllowed", operation.getCardProvidersAllowed());
		queryParametersMap.put("accountProvidersAllowed", operation.getAccountProvidersAllowed());

		queryParametersMap.put("IQWaveUUID", operation.getIQWaveUUID());

		String pageType = StringHelper.getEmptyIfNull(frm.getPageType());
		queryParametersMap.put("isTemplate", pageType.equals(TEMPLATE_CONSTANT) ? 1 : 0);
		queryParametersMap.put("isAutoPayment", pageType.equals(AUTOPAYMENT_CONSTANT) ? 1 : 0);
		queryParametersMap.put("isMobilebank", pageType.equals(MOBILEBANK_CONSTANT) ? 1 : 0);

		queryParametersMap.put("isIQWaveAutoPaymentPermit", checkAccess(CreateAutoPaymentOperation.class, "CreateAutoPaymentPayment") ? 0 : 1);
		queryParametersMap.put("isESBAutoPaymentPermit", checkAccess(CreateESBAutoPayOperation.class, "ClientCreateAutoPayment") ? 0 : 1);

		return queryParametersMap;
	}

	protected Long getRegionId(Map requestParameters) throws BusinessException
	{
		String regionId = HttpServletRequestUtils.getEmptyIfNull(requestParameters.get("regionId"));

		if (ALL_REGION_ID.equals(regionId))
			return null;

		if (!StringHelper.isEmpty(regionId))
			return Long.parseLong(regionId);
		Region region = RegionHelper.getCurrentRegion();
		if (region == null)
			return null;
		else
			return region.getId();
	}

	protected AsynchSearchOperationBase createSearchOperation()
	{
		try
		{
			//Проверка наличия права поиска поставщиков услуг
			createOperation(ListServicesPaymentSearchOperation.class, "RurPayJurSB");
			//Проверка доступности "живого" поиска.
			return createOperation(AsynchSearchServiceProvidersOperation.class, ASYNC_SEARCH_ACCESS);
		}
		catch (Exception e)
		{
			log.info("Интерактивный поиск поставщиков услуг не доступен",e);
			return null;
		}
	}
}
