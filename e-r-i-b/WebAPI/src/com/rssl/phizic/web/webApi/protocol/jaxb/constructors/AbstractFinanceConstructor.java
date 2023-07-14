package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.Response;

/**
 * Базовый класс для наследников, использующих в качестве источника данных операции типа FinancesOperationBase
 *
 * @author Balovtsev
 * @since 13.05.2014
 */
public abstract class AbstractFinanceConstructor<RequestType extends Request, ResponseType extends Response> extends AbstractConstructorWithRequestValidation<RequestType, ResponseType>
{
	protected static final String PRODUCT_INFORM_NOT_EXISTS  = "Информация по %s из АБС временно недоступна. Повторите операцию позже.";
	protected static final String EMPTY_RESOURCE_MESSAGE     = "У вас нет активных карт, вкладов, металлических счетов и сберегательных сертификатов.";
	protected static final String NO_ACCOUNT_PRODUCT_MESSAGE = String.format(PRODUCT_INFORM_NOT_EXISTS, "счетам");
	protected static final String NO_CARD_PRODUCT_MESSAGE    = String.format(PRODUCT_INFORM_NOT_EXISTS, "картам");
	protected static final String NO_IMA_PRODUCT_MESSAGE     = String.format(PRODUCT_INFORM_NOT_EXISTS, "ОМС");
	protected static final String NO_SECURITY_ACCOUNTS_MESSAGE = String.format(PRODUCT_INFORM_NOT_EXISTS, "сберегательным сертификатам");

	protected <T extends FinancesOperationBase>T createFinanceOperation(Class<T> clazz) throws BusinessException, BusinessLogicException
	{
		T operation = createLazyFinanceOperation(clazz);
		operation.initialize();
		return operation;
	}

	protected <T extends FinancesOperationBase>T createLazyFinanceOperation(Class<T> clazz) throws BusinessException, BusinessLogicException
	{
		T operation = null;
		if (checkAccess(clazz, "FinanceOperationsService"))
		{
			operation = createOperation(clazz, "FinanceOperationsService");
		}
		else
		{
			operation = createOperation(clazz, "CategoriesCostsService");
		}

		return operation;
	}
}
