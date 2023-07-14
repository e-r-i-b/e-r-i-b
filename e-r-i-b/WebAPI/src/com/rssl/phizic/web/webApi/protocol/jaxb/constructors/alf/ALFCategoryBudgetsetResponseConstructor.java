package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.MapErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.finances.Budget;
import com.rssl.phizic.operations.finances.EditBudgetOperation;
import com.rssl.phizic.web.webApi.exceptions.FormValidationException;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.ALFCategoryBudgetsetRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.ALFCategoryBudgetsetRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.SimpleResponse;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Заполняет ответ на запрос установки бюджета для расходной категории
 * @author Jatsky
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ALFCategoryBudgetsetResponseConstructor extends ALFBaseResponseConstructor<ALFCategoryBudgetsetRequest, SimpleResponse>
{
	@Override protected SimpleResponse makeResponse(ALFCategoryBudgetsetRequest request) throws Exception
	{
		EditBudgetOperation operation = createOperation(EditBudgetOperation.class);
		ALFCategoryBudgetsetRequestBody requestBody = request.getBody();
		operation.initialize(requestBody.getId());

		MapErrorCollector mapErrorCollector = new MapErrorCollector();
		FormProcessor<Map<String, String>, MapErrorCollector> processor = new FormProcessor<Map<String, String>, MapErrorCollector>(getMapValueSource(request), ALFCategoryBudgetsetRequestBody.SAVE_BUDGET_FORM, mapErrorCollector, DefaultValidationStrategy.getInstance());
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			Budget budget = operation.getEntity();
			BigDecimal summ = (BigDecimal) result.get("budget");

			if (summ != null)
				budget.setBudget(summ.doubleValue());
			result.put("openPageDate", Calendar.getInstance().getTime());
			operation.save(result);
		}
		else
		{
			throw new FormValidationException(processor.getErrors());
		}
		return new SimpleResponse();
	}

	protected MapValuesSource getMapValueSource(ALFCategoryBudgetsetRequest request)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		ALFCategoryBudgetsetRequestBody requestBody = request.getBody();
		map.put("id", requestBody.getId());
		map.put("budget", requestBody.getBudget());
		return new MapValuesSource(map);
	}
}
