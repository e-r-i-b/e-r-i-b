package com.rssl.phizic.operations.fund.response;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.sender.FundSenderResponseService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.fund.FundMultiNodeService;
import com.rssl.phizic.gate.fund.RequestInfo;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.fund.sender.FundSenderResponse;
import com.rssl.phizic.utils.StringHelper;
import java.math.BigDecimal;

/**
 * @author usachev
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Операция для просмотра детальной информации об ответе на сбор средств
 */

public class ViewFundSenderResponseOperation extends OperationBase
{
	private FundSenderResponse response;
	private BigDecimal accumulatedSum;

	/**
	 * Инициализация сущности "Ответ на сбор средств" по id
	 * @param id Id сущности "Ответ на сбор средств"
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void init(String id) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(id))
		{
			throw new IllegalArgumentException("Недопустимый id");
		}
		FundSenderResponseService service = new FundSenderResponseService();
		response = service.getByExternalId(id);
		if (response == null)
		{
			throw new BusinessException("Не найден ответ на запрос на сбор средств с id = " + id);
		}
		//Получение собранной суммы в рамках запроса на сбор средств
		try
		{
			FundMultiNodeService multiNodeService = GateSingleton.getFactory().service(FundMultiNodeService.class);
			RequestInfo requestInfo = multiNodeService.getRequestInfo(response.getExternalId());
			if  (requestInfo == null){
				throw new BusinessLogicException("Ошибка получения собранной суммы для запроа с getExternalId = " + response.getExternalId());
			}
			accumulatedSum = requestInfo.getAccumulatedSum();
		}
		catch (GateException e)
		{
			throw new BusinessException("Ошибка получение собранной суммы для запроса с getExternalId = " + response.getExternalId(), e);
		}catch (GateLogicException e){
			throw new BusinessLogicException(e.getMessage(), e);
		}
	}

	/**
	 * Получение сущности "Ответ на сбор средств"
	 * @return Сущность "Ответ на сбор средств"
	 */
	public FundSenderResponse getResponse()
	{
		return response;
	}

	/**
	 * Получение текущей собранной суммы
	 * @return Собранная сумма
	 */
	public BigDecimal getAccumulatedSum()
	{
		return accumulatedSum;
	}
}
