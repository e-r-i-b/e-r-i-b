package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.MapErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.operations.finances.CategoriesListOperation;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.web.webApi.exceptions.FormValidationException;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.ALFCategoryListRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.ALFCategoryListRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.ALFCategoryListResponse;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.CategoryTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.IncomeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Заполняет ответ на запрос получения справочника категорий АЛФ
 * @author Jatsky
 * @ created 14.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ALFCategoryListResponseConstructor extends ALFBaseResponseConstructor<ALFCategoryListRequest, ALFCategoryListResponse>
{

	protected ALFCategoryListResponse makeResponse(ALFCategoryListRequest request) throws Exception
	{
		ALFCategoryListResponse response = new ALFCategoryListResponse();
		fillALFStatus(response);

		MapErrorCollector mapErrorCollector = new MapErrorCollector();
		FormProcessor<Map<String, String>, MapErrorCollector> processor = new FormProcessor<Map<String, String>, MapErrorCollector>(getMapValueSource(request), ALFCategoryListRequestBody.LIST_FORM, mapErrorCollector, DefaultValidationStrategy.getInstance());
		if (!processor.process())
		{
			throw new FormValidationException(processor.getErrors());
		}

		CategoriesListOperation operation = createOperation(CategoriesListOperation.class);
		operation.initialize();
		FinanceFilterData filterData = new FinanceFilterData();
		ALFCategoryListRequestBody requestBody = request.getBody();
		filterData.setIncome(requestBody.getIncomeType().equals(IncomeType.income));
		List<CardOperationCategory> operationCategories = operation.getCategories(filterData);
		int start = 0;
		if (requestBody.getPaginationOffset() != null)
			start = requestBody.getPaginationOffset().intValue();
		int end = operationCategories.size();
		if (requestBody.getPaginationSize() != null && start + requestBody.getPaginationSize() < operationCategories.size())
			end = start + requestBody.getPaginationSize().intValue();
		List<CategoryTag> categoryTags = new ArrayList<CategoryTag>();
		for (int i = start; i < end; i++)
		{
			CardOperationCategory category = operationCategories.get(i);
			CategoryTag categoryTag = new CategoryTag();
			categoryTag.setId(category.getId());
			categoryTag.setName(category.getName());
			categoryTag.setIncomeType(category.isIncome() ? IncomeType.income : IncomeType.outcome);
			categoryTag.setCanEdit(PermissionUtil.impliesService("EditCategoriesService") && category.getOwnerId() != null);
			categoryTag.setHasLinkedOperations(operation.hasRecategorisationRules(category));
			categoryTag.setColor(category.getColor());
			categoryTags.add(categoryTag);
		}
		response.setCategories(categoryTags);
		return response;
	}

	protected MapValuesSource getMapValueSource(ALFCategoryListRequest request) throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		ALFCategoryListRequestBody alfCategoryListRequestBody = request.getBody();
		map.put("incomeType", alfCategoryListRequestBody.getIncomeType());
		map.put("paginationSize", alfCategoryListRequestBody.getPaginationSize());
		map.put("paginationOffset", alfCategoryListRequestBody.getPaginationOffset());
		return new MapValuesSource(map);
	}
}
