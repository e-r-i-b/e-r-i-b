package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.operations.finances.ShowFinanceOperation;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.JAXBResponseConstructor;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.ALFStatusBaseResponse;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.Response;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.ALFServiceStatus;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.ALFStatus;

/**
 * Базовый конструктор запросов АЛФ
 * @author Jatsky
 * @ created 13.05.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class ALFBaseResponseConstructor<RequestType extends Request, ResponseType extends Response> extends JAXBResponseConstructor<RequestType, ResponseType>
{
	protected ShowFinanceOperation createFinancesOperation() throws BusinessException, BusinessLogicException
	{
		ShowFinanceOperation operation = null;
		if (checkAccess(ShowFinanceOperation.class, "FinanceOperationsService"))
			operation = createOperation(ShowFinanceOperation.class, "FinanceOperationsService");
		else
			operation = createOperation(ShowFinanceOperation.class, "CategoriesCostsService");

		operation.initialize();
		return operation;
	}

	protected void fillALFStatus(ALFStatusBaseResponse response, FinancesOperationBase operation)
	{
		ALFServiceStatus status = new ALFServiceStatus();

		status.setStatus(ALFStatus.getTypeByFinancesStatus(operation.getStatus().name()));
		status.setLastModified(operation.getLastModified());
		response.setAlfServiceStatus(status);

		if (operation.hasFailedClaims())
		{
			response.setStatus(new Status("По некоторым Вашим картам информация недоступна."));
		}
	}

	protected void fillALFStatus(ALFStatusBaseResponse response) throws BusinessLogicException, BusinessException
	{
		ShowFinanceOperation operation = createFinancesOperation();
		fillALFStatus(response, operation);
	}
}
