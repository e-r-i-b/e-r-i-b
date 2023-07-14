package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.EditDeletedCategoryAbstractOperation;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.AbstractFinanceConstructor;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.RecategorizationOperation;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.AlfEditOperationGroupRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.AlfEditOperationGroupRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.SimpleResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Редактирование группы операций
 * @author Jatsky
 * @ created 14.08.14
 * @ $Author$
 * @ $Revision$
 */

public class AlfEditOperationGroupConstructor extends AbstractFinanceConstructor<AlfEditOperationGroupRequest, SimpleResponse>
{

	public static final String OLD_OPERATION_CATEGORY_ID = "oldOperationCategoryId";
	public static final String GENERAL_CATEGORY_ID = "generalCategoryId";
	public static final String OPERATIONS = "operations";

	@Override
	protected Form getForm()
	{
		return AlfEditOperationGroupRequestBody.EDIT_FORM;
	}

	@Override
	protected MapValuesSource getMapValueSource(AlfEditOperationGroupRequest request)
	{
		AlfEditOperationGroupRequestBody body = request.getBody();

		Map<String, Object> sources = new HashMap<String, Object>();
		sources.put(OLD_OPERATION_CATEGORY_ID, body.getOldOperationCategoryId());
		sources.put(GENERAL_CATEGORY_ID, body.getGeneralCategoryId());
		sources.put(OPERATIONS, body.getOperations());

		return new MapValuesSource(sources);
	}

	@Override protected SimpleResponse doMakeResponse(Map<String, Object> sources) throws Exception
	{
		Object categoryId = sources.get(OLD_OPERATION_CATEGORY_ID);
		if (categoryId == null)
			throw new BusinessException("Не указан categoryId");

		EditDeletedCategoryAbstractOperation operation = createOperation(EditDeletedCategoryAbstractOperation.class);
		operation.initialize((Long) categoryId);

		Object generalCategoryId = sources.get(GENERAL_CATEGORY_ID);
		if (generalCategoryId != null && (Long) generalCategoryId > 0)
			operation.setGeneralCategory((Long) generalCategoryId);
		else
		{
			Object operations = sources.get(OPERATIONS);
			if (operations == null)
				throw new BusinessException("не заданы перепривязываемые операции");
			List<RecategorizationOperation> operationList = ((List<RecategorizationOperation>) operations);
			for (RecategorizationOperation recategorizationOperation : operationList)
			{
				if (recategorizationOperation.getNewOperationCategoryId() > 0)
					operation.addChangedOperation(recategorizationOperation.getId(), recategorizationOperation.getNewOperationCategoryId());
				else
				{
					throw new BusinessLogicException("Измените тип категории у всех операций!");
				}
			}
			operation.saveOperations();
		}
		return new SimpleResponse();
	}
}
