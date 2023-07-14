package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.finances.EditCardOperationForm;
import com.rssl.phizic.operations.finances.EditCategoryAbstractOperation;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.AbstractFinanceConstructor;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.NewOperation;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.AlfEditOperationRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.AlfEditOperationRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.SimpleResponse;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Редактирование и разбивка АЛФ операций
 *
 * @author Balovtsev
 * @since 15.05.2014
 */
public class AlfEditOperationConstructor extends AbstractFinanceConstructor<AlfEditOperationRequest, SimpleResponse>
{
	public static final String FIELD_OPERATION_ID        = "operationId";
	public static final String FIELD_CATEGORY_ID         = "categoryId";
	public static final String FIELD_DESCRIPTION         = "description";
	public static final String FIELD_NEW_CARD_OPERATIONS = "newCardOperations";

	@Override
	protected MapValuesSource getMapValueSource(AlfEditOperationRequest request)
	{
		AlfEditOperationRequestBody body = request.getBody();

		Map<String, Object> sources = new HashMap<String, Object>();
		sources.put(FIELD_OPERATION_ID, body.getOperationId());
		sources.put(FIELD_CATEGORY_ID,  body.getOperationCategoryId());
		sources.put(FIELD_DESCRIPTION,  body.getOperationTitle());

		List<NewOperation> operations = body.getNewOperation();
		if (CollectionUtils.isNotEmpty(operations))
		{
			List<EditCardOperationForm> operationForms = new ArrayList<EditCardOperationForm>(operations.size());

			for (NewOperation operation : operations)
			{
				EditCardOperationForm form = new EditCardOperationForm();
				form.setAmount(new BigDecimal(operation.getNewOperationSum()));
				form.setCategoryId(operation.getNewOperationCategoryId());
				form.setDescription(operation.getNewOperationTitle());
				operationForms.add(form);
			}

			sources.put(FIELD_NEW_CARD_OPERATIONS, operationForms);
		}

		return new MapValuesSource(sources);
	}

	@Override
	protected SimpleResponse doMakeResponse(Map<String, Object> sources) throws Exception
	{
		EditCategoryAbstractOperation operation = createOperation(EditCategoryAbstractOperation.class, "EditOperationsService");
		operation.initialize((Long) sources.get(FIELD_OPERATION_ID));

		operation.setCardOperationNewCategoryId ((Long)   sources.get(FIELD_CATEGORY_ID));
		operation.setCardOperationNewDescription((String) sources.get(FIELD_DESCRIPTION));
		if (sources.containsKey(FIELD_NEW_CARD_OPERATIONS))
		{
			operation.setNewCardOperations((List<EditCardOperationForm>) sources.get(FIELD_NEW_CARD_OPERATIONS));
		}
		operation.saveOperations(false);

		return new SimpleResponse();
	}
}
