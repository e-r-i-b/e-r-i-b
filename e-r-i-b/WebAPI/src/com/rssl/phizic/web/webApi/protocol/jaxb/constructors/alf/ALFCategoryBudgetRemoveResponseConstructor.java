package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.MapErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.operations.finances.EditBudgetOperation;
import com.rssl.phizic.web.webApi.exceptions.FormValidationException;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.IdRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.IdRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.SimpleResponse;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Заполняет ответ на запрос удаления бюджета для расходной категории
 * @author Pankin
 * @ created 25.06.14
 * @ $Author$
 * @ $Revision$
 */
public class ALFCategoryBudgetRemoveResponseConstructor extends ALFBaseResponseConstructor<IdRequest, SimpleResponse>
{
	protected SimpleResponse makeResponse(IdRequest request) throws Exception
	{
		EditBudgetOperation operation = createOperation(EditBudgetOperation.class);

		MapErrorCollector mapErrorCollector = new MapErrorCollector();
		FormProcessor<Map<String, String>, MapErrorCollector> processor = new FormProcessor<Map<String, String>, MapErrorCollector>(getMapValueSource(request), IdRequestBody.ID_FORM, mapErrorCollector, DefaultValidationStrategy.getInstance());
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			operation.initialize((Long) result.get(IdRequestBody.ID));
			result.put("openPageDate", Calendar.getInstance().getTime());
			operation.remove(result);
		}
		else
		{
			throw new FormValidationException(processor.getErrors());
		}
		return new SimpleResponse();
	}

	protected MapValuesSource getMapValueSource(IdRequest request)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		IdRequestBody requestBody = request.getBody();
		map.put("id", requestBody.getId());
		return new MapValuesSource(map);
	}
}
