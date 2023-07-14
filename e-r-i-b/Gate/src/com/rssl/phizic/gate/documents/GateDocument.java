/***********************************************************************
 * Module:  GateDocument.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface GateDocument
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;

import java.util.Calendar;
import java.util.List;

/**
 * Базовый интерфейс для всех документов
 */
public interface GateDocument
{
	/**
	 * Внутренний ID документа - уникален в для каждого конкретного типа документов
	 * @return внутренний идентификатор
	 */
	Long getId();

	/**
	 * Получить номер документа в ИКФЛ, который отображется пользователю.
	 *
	 * @return номер документа
	 */
	String getDocumentNumber();

	/**
	 * Дата-время создания документа
	 * Domain: DateTime
	 *
	 * @return дата создания
	 */
	Calendar getClientCreationDate();

	/**
	 * @return дата подтверждения клиентом
	 */
	Calendar getClientOperationDate();

	/**
	 * Установить дату подтверждения клиентом
	 * @param clientOperationDate дата подтверждения клиентом
	 */
	void setClientOperationDate(Calendar clientOperationDate);

	/**
	 * @return дата подтверждения сотрудником/в УС (не клиентом)
	 */
	Calendar getAdditionalOperationDate();

	/**
	 * Дата планового исполнения платежа банком
	 *
	 * @return дата планового исполнения платежа
	 */
	Calendar getAdmissionDate();

	/**
	 * возвращает офис, в который был отправлен документ
	 * Domain: ExternalID
	 *
	 * @return идентификатор офиса
	 */
	Office getOffice() throws GateException;

	/**
	 * Установить офис, в который был отправлен документ
	 * на тот случай, если гейт отправит документ в другой офис.
	 * Domain: ExternalID
	 * @param office офис
	 */
	void setOffice(Office office);

	/**
	 * Фактичский тип документа
	 *
	 * @return фактичский тип документа
	 */
	Class<? extends GateDocument> getType();

	/**
	 * @return тип формы шаблона платежа
	 */
	FormType getFormType();

	/**
	 * Комиссия взымаемая за отправку(обработку etc). Если комиссия отсутствует == null.
	 * @return комиссия
	 */
	Money getCommission();

	/**
	 * Установить комиссию. Комиссия, установленная перед отправкой может быть
	 * проигнорирована by underlying system.
	 * В первую очередь это свойство предназначено для информирования пользователя о размере комиссии.
	 *
	 * @param commission размер комиссии. Если комиссия не взимается == null
	 */
	void setCommission(Money commission);

	/**
	 * Параметры взымания комиссии.
	 * Конкретные платежи документы определяют ограничения на возможные варианты.
	 *
	 * По умолчанию поддерживается только взымание комиссии с указанного счета (other)
	 * @return комиссия
	 */
	CommissionOptions getCommissionOptions();

	/**
	 * Внутренний ID клиента-владельца (создателя) документа
	 *
	 * @return идентификатор плательщика
	 */
	Long getInternalOwnerId() throws GateException;

	/**
	 * Внешний ID клиента-владельца (создателя) документа
	 *
	 * @return идентификатор плательщика
	 */
	String getExternalOwnerId();

	/**
	 * Установить внешний идентификатор владельца
	 * @param externalOwnerId внешний идентификатор владельца
	 */
	void setExternalOwnerId(String externalOwnerId);

	/**
	 * @return информация создавшену операцию сотруднику
	 */
	EmployeeInfo getCreatedEmployeeInfo() throws GateException;

	/**
	 * @return информация по подтвердившему операцию (в АРМ сотрудника (автоплатежи)) сотруднику
	 */
	EmployeeInfo getConfirmedEmployeeInfo() throws GateException;

	/**
	 * @return канал создания клиентом
	 */
	CreationType getClientCreationChannel();

	/**
	 * @return канал подтверждения клиентом
	 */
	CreationType getClientOperationChannel();

	/**
	 * @return канал подтверждения сотрудником
	 */
	CreationType getAdditionalOperationChannel();

	/**
	 * Является ли документ шаблоном
	 * @return true является
	 */
	boolean isTemplate();

	/**
	 * @return Список микроопераций(комиссий)
	 */
	List<WriteDownOperation> getWriteDownOperations();

	/**
	 * Установить список микроопераций(комиссий)
	 * @param list - полученный список
	 */
	void setWriteDownOperations(List<WriteDownOperation> list);

	/**
	 * @return следующее состояние
	 */
	String getNextState();

	/**
	 * Установить следующее состояние
	 * @param nextState - следующее состояние
	 */
	void setNextState(String nextState);

	/**
	 * @return режим интеграции для документа
	 */
	ExternalSystemIntegrationMode getIntegrationMode();
}