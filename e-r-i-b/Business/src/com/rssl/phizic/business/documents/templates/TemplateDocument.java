package com.rssl.phizic.business.documents.templates;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.security.ConfirmableObject;

import java.util.Calendar;
import java.util.Map;

/**
 * Интерфейс шаблона платежа (бизнес реализация)
 *
 * @author khudyakov
 * @ created 30.01.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateDocument extends ConfirmableObject, StateObject, GateTemplate, FraudAuditedObject
{
	/**
	 * Проинициализировать шаблон первоначальными данными
	 */
	void initialize() throws BusinessException, BusinessLogicException;

	/**
	 * Проинициализировать шаблон первоначальными данными
	 * @param document бизнес документ
	 */
	void initialize(BusinessDocument document) throws BusinessException;

	/**
	 * @return информация по активности шаблона
	 * Важно: используется при постороении правого меню, расчитывается 1 раз
	 */
	ActivityInfo getActivityInfo();

	/**
	 * Установить информацию по активности шаблона
	 * @param activityInfo информация по активности шаблона
	 */
	void setActivityInfo(ActivityInfo activityInfo);

	/**
	 * @return данные (ключ-значение)
	 * @throws BusinessException
	 */
	public Map<String, String> getFormData() throws BusinessException;

	/**
	 * Обновить шаблон
	 * @param source ресурс получения данных
	 * @throws BusinessException
	 */
	void setFormData(FieldValuesSource source) throws BusinessException, BusinessLogicException;

	/**
	 * @param metadata метаданные
	 * @return название шаблона поумолчанию
	 */
	String generateDefaultName(Metadata metadata) throws BusinessException;

	/**
	 * Владелец шаблона (бизнес)
	 * @return персона
	 */
	Person getOwner() throws BusinessException;

	/**
	 * Установить канал создания шаблона
	 * @param channel канал создания шаблона
	 */
	void setClientCreationChannel(CreationType channel);

	/**
	 * Установить канал подтверждения(всегда internet)
	 * @param channel - канал
	 */
	void setClientOperationChannel(CreationType channel);

	/**
	 * Установить канал подтверждения(всегда internet)
	 * @param channel - канал
	 */
	void setAdditionalOperationChannel(CreationType channel);

	/**
	 * Установить дату дополнительного подтверждения шаблона
	 * @param date дата дополнительного подтверждения
	 */
	void setAdditionalOperationDate(Calendar date);

	/**
	 * Установить в шаблон информацию о создавшем сотруднике.
	 * @param employeeInfo
	 */
	void setCreatedEmployeeInfo(EmployeeInfo employeeInfo);

	/**
	 * Установить в шаблон информацию о подтвердившем сотруднике.
	 * @param employeeInfo - инфо сотрудника
	 */
	void setConfirmedEmployeeInfo(EmployeeInfo employeeInfo);

	/**
	 * Установить транзитный банк получателя
	 * @param receiverTransitBank транзитный банк получателя
	 */
	void setReceiverTransitBank(ResidentBank receiverTransitBank);

	/**
	 * @return тип счета списания
	 */
	ResourceType getChargeOffResourceType();

	/**
	 * @return линк ресурса списания
	 * @throws BusinessException
	 */
	PaymentAbilityERL getChargeOffResourceLink() throws BusinessException;

	/**
	 * @return тип счета зачисления
	 */
	ResourceType getDestinationResourceType();

	/**
	 * @return линк ресурса зачисления
	 * (для внешних операций отсутствует)
	 *
	 * @throws BusinessException
	 */
	PaymentAbilityERL getDestinationResourceLink() throws BusinessException;

	/**
	 * @return сумма шаблона
	 */
	Money getExactAmount();

	/**
	 * @return тип лолучателя
	 */
	String getReceiverType();

	/**
	 * @return подтип получателя шаблона платежа
	 */
	String getReceiverSubType();

	/**
	 * Установить флаг ограничения на подбор ФИО
	 * @param value флаг
	 */
	void setRestrictReceiverInfoByPhone(boolean value);

	/**
	 * @return сработало ограничение на подбор ФИО п номеру телефона
	 */
	boolean hasRestrictReceiverInfoByPhone();

	/**
	 * обновить информацию о получателе платежа по карте
	 * @param card карта
	 */
	void fillReceiverInfoByCardOwner(Card card);

	/**
	 * Установить имя получателя
	 * @param value имя получателя
	 */
	void setReceiverFirstName(String value);

	/**
	 * Установить фимилию получателя
	 * @param value фамилия получателя
	 */
	void setReceiverSurName(String value);

	/**
	 * установить отчество получателя
	 * @param value отчество
	 */
	void setReceiverPatrName(String value);

	/**
	 * установить внешний идентификатор получателя
	 * @param id идентификатор
	 */
	void setReceiverInternalId(Long id);

	/**
	 * @return идентификатор линка кредита
	 */
	String getLoanLinkId();

	/**
	 * @return тарифный план
	 */
	String getTarifPlanCodeType() throws DocumentException;
}
