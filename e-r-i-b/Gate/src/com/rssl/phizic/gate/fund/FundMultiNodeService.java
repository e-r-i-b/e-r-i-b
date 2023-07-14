package com.rssl.phizic.gate.fund;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author osminin
 * @ created 17.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с запросами и результами обработки запросов на сбор средств между блоками
 */
public interface FundMultiNodeService extends Service
{
	/**
	 * Создать заявки на сбор средств
	 * @param fundInfoList список информации о сборах средств
	 * @param nodeNumber номер блока
	 * @return список внешних идентификаторов созданных ответов
	 * @throws GateException
	 * @throws GateLogicException
	 */
	List<String> createFundSenderResponses(List<FundInfo> fundInfoList, Long nodeNumber) throws GateException, GateLogicException;

	/**
	 * Обновить статус обработки запроса на сбор средств
	 * @param fundResponse ответ отправителя денег
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void updateResponseInfo(Response fundResponse) throws GateException, GateLogicException;

	/**
	 * Обновить статус обработки запроса на сбор средств с учетом необходимой набранной суммы
	 * @param fundResponse ответ отправителя денег
	 * @param requiredSum необходимая сумма
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void updateResponseInfoBySum(Response fundResponse, BigDecimal requiredSum) throws GateException, GateLogicException;

	/**
	 * Закрыть запросы на сбор средств
	 * @param requests список запросов на закрытие
	 * @param nodeNumber номер блока
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void closeRequests(List<Request> requests, Long nodeNumber) throws GateException, GateLogicException;

	/**
	 * Получить статус запроса
	 * @param externalResponseId внешний идентификатор ответа
	 * @return статус
	 * @throws GateException
	 * @throws GateLogicException
	 */
	RequestInfo getRequestInfo(String externalResponseId) throws GateException, GateLogicException;
}
