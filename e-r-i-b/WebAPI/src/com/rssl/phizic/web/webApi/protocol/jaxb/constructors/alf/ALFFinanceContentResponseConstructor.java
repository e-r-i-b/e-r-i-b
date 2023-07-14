package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.phizic.business.profile.ProfileUtils;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.util.WebAPIUtil;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.JAXBResponseConstructor;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.utils.LinkUtils;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.Tab;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.ALFFinanceContentResponse;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Заполняет ответ на запрос содержимого страницы "Мои финансы"
 * @author Pankin
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */
public class ALFFinanceContentResponseConstructor extends JAXBResponseConstructor<Request, ALFFinanceContentResponse>
{
	private static final String DESCRIPTION = "На этой странице Вы можете посмотреть структуру Ваших денежных средств на вкладах," +
			" картах и других продуктах и выполнить анализ расходов по разным категориям. Также с помощью финансового планирования" +
			" Вы сможете подобрать наиболее подходящие для Вас продукты.";

	private static final String SHORT_DESCRIPTION = "На этой странице Вы можете посмотреть структуру Ваших денежных средств на" +
			" вкладах, картах и других продуктах, а также выполнить анализ расходов по разным категориям.";
	private static final String PFP_DESCRIPTION = "Финансовое планирование способствует эффективному распределению Ваших денежных" +
			" средств. В результате анализа Ваших финансовых целей мы поможем подобрать наиболее выгодные для Вас продукты.";

	private static final String OPERATION_CATEGORIES_URL = LinkUtils.createRedirectUrl("/private/finances/operationCategories.do", null);
	private static final String OPERATION_CATEGORIES_URL_POSTFIX = "operation.categories";
	private static final String OPERATIONS_URL = LinkUtils.createRedirectUrl("/private/finances/operations.do", null);
	private static final String OPERATIONS_URL_POSTFIX = "operations";
	private static final String FINANCE_URL = LinkUtils.createRedirectUrl("/private/graphics/finance.do", null);
	private static final String FINANCE_URL_POSTFIX = "graphics.finance";
	private static final String TARGETS_URL = LinkUtils.createRedirectUrl("/private/finances/targets/targetsList.do", null);
	private static final String CALENDAR_URL = LinkUtils.createRedirectUrl("/private/finances/financeCalendar.do", null);
	private static final String PFP_URL = LinkUtils.createRedirectUrl("/private/pfp/edit.do", null);

	protected ALFFinanceContentResponse makeResponse(Request request) throws Exception
	{
		ALFFinanceContentResponse response = new ALFFinanceContentResponse();
		response.setDescription(DESCRIPTION);
		List<Tab> tabs = new ArrayList<Tab>();

		boolean operationsServiceAvailable = PermissionUtil.impliesService("FinanceOperationsService");
		boolean categoriesServiceAvailable = PermissionUtil.impliesService("CategoriesCostsService");
		boolean targetsServiceAvailable = PermissionUtil.impliesService("TargetsService");
		boolean financeCalendarServiceAvailable = PermissionUtil.impliesService("FinanceCalendarService");
		boolean addFinanceOperationServiceAvailable = PermissionUtil.impliesService("AddFinanceOperationsService");
		boolean clientPfpEditServiceAvailable = SecurityUtil.hasAccessToPFP();
		boolean personalFinanceUsed = ProfileUtils.isPersonalFinanceEnabled();
		boolean viewFinanceAvailable = PermissionUtil.impliesService("ViewFinance");

		if (clientPfpEditServiceAvailable && (((operationsServiceAvailable || categoriesServiceAvailable) && (personalFinanceUsed || addFinanceOperationServiceAvailable)) || targetsServiceAvailable))
			response.setDescription(DESCRIPTION);
		else if (!clientPfpEditServiceAvailable && (((operationsServiceAvailable || categoriesServiceAvailable) && (personalFinanceUsed || addFinanceOperationServiceAvailable)) || targetsServiceAvailable))
			response.setDescription(SHORT_DESCRIPTION);
		else
			response.setDescription(PFP_DESCRIPTION);

		int i = 0;
		if (categoriesServiceAvailable && (personalFinanceUsed || addFinanceOperationServiceAvailable))
			tabs.add(new Tab(i++, "Расходы", PermissionUtil.impliesService("UseWebAPIService") ? WebAPIUtil.getWebAPIUrl(OPERATION_CATEGORIES_URL_POSTFIX) : OPERATION_CATEGORIES_URL));
		if (operationsServiceAvailable && (personalFinanceUsed || addFinanceOperationServiceAvailable))
			tabs.add(new Tab(i++, "Операции", PermissionUtil.impliesService("UseWebAPIService") ? WebAPIUtil.getWebAPIUrl(OPERATIONS_URL_POSTFIX) : OPERATIONS_URL));
		if (viewFinanceAvailable)
			tabs.add(new Tab(i++, "Доступные средства", PermissionUtil.impliesService("UseWebAPIService") ? WebAPIUtil.getWebAPIUrl(FINANCE_URL_POSTFIX) : FINANCE_URL));
		if (targetsServiceAvailable)
			tabs.add(new Tab(i++, "Мои цели", TARGETS_URL));
		if (financeCalendarServiceAvailable)
			tabs.add(new Tab(i++, "Календарь", CALENDAR_URL));
		if (clientPfpEditServiceAvailable)
			tabs.add(new Tab(i++, "Финансовое планирование", PFP_URL));

		if (CollectionUtils.isNotEmpty(tabs))
			response.setTabs(tabs);

		return response;
	}
}
