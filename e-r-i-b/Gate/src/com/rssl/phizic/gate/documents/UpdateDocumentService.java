package com.rssl.phizic.gate.documents;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * Обновление документов.
 *
 */
public interface UpdateDocumentService extends Service
{
   /**
    * Найти документ по его ExternalId. Если документ не найден возвращается null.
    *
    * @param externalId (Domain: ExternalID)
    * @retun документ
    */
   SynchronizableDocument find(String externalId) throws GateException, GateLogicException;
   /**
    * Обновить SynchronizableDocument.
    * Используется в случае, когда недостаточно просто обновить статус документа.
    *
    * @param document Документ, который надо обновить
    */
   void update(SynchronizableDocument document) throws GateException, GateLogicException;

	/**
	 * найти непроведенные документы по признаку
	 * @return список документов
	 * @param state статус платежей
	 */
	List<GateDocument> findUnRegisteredPayments(State state) throws GateException, GateLogicException;

	/**
	 * Обновление статуса документа
	 * @param document Документ, который надо обновить
	 * @param command Описание действий, которые необходимо произвести с документом
	 * @throws GateException
	 */
	void update(SynchronizableDocument document, DocumentCommand command) throws GateException, GateLogicException;

	/**
	 * найти заданное количество документов по статусу для заданной системы
	 * @param state статус документа
	 * @param uid внешней системы
	 * @param firstResult номер первого элемента
	 * @param listLimit количество возвращаемых документов
	 * @return список документов
	 * @throws GateException
	 * @throws GateLogicException
	 * @deprecated мусор с тяжелыми запросами к базе.
	 */
	@Deprecated
	List<GateDocument> findUnRegisteredPayments(State state, String uid, Integer firstResult, Integer listLimit) throws GateException, GateLogicException;

	void createDocumentOrder(String externalId, Long id, String orderUuid) throws GateException, GateLogicException;
}