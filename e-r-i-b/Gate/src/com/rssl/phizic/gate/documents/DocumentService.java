package com.rssl.phizic.gate.documents;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Сервис для работы с документами
 *
 * @author Krenev
 * @ created 15.06.2007
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentService extends Service
{
   /**
    * получить комиссию для платежа.
    *
    * @param document документ
    * @throws GateException
    * @exception GateLogicException
    */
   void calcCommission(GateDocument document) throws GateException, GateLogicException;
   /**
    * Отправить документ в Бэк-офис.
    *
    * @param document документ
    */
   void send(GateDocument document) throws GateException, GateLogicException;

	/**
	 * повторная отправка документа в Бэк-офис.
	 * @param document документ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void repeatSend(GateDocument document) throws GateException, GateLogicException;

	/**
	 * Проверить обновление документа. Реализуем в гейте. Т.е. для тех случаев, когда бизнес инициализирует обновление.
	 *
	 * @param document документ
	 * @return StateUpdateInfo состояние документа
	 */
	StateUpdateInfo update(GateDocument document) throws GateException, GateLogicException;

	/**
	 * Подготовка документа.
	 * Подготовительные действия над документом, которые необходимо произвести в бэк-офисе,
	 * разнесенные по времени и месту вызова с методом send.
	 * Например, когда требуется пересчет полей в бэк-офисе.
	 *
	 * @param document документ для отправки
	 */
	void prepare(GateDocument document) throws GateException, GateLogicException;

	/**
	 * Подтвердить платеж. Используется в двухшаговых транзакциях.
	 * 
	 * @param document платеж для подтверждения
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void confirm(GateDocument document) throws GateException, GateLogicException;

	/**
	 * Валидация платежа в бэк-офисе.
	 *
	 * @param document  платеж
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void validate(GateDocument document) throws GateException, GateLogicException;

	/**
	 * Отозвать документ
	 *
	 * @param document документ для отзыва
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void recall(GateDocument document) throws GateException, GateLogicException;
}