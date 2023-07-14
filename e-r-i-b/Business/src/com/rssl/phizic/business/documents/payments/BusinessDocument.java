package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.NodeInfoContainer;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.logging.operations.context.PossibleAddingOperationUIDObject;
import com.rssl.phizic.person.DeviceInfoObject;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.security.ConfirmableObject;
import org.w3c.dom.Document;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 09.11.2006
 * @ $Author: khudyakov $
 * @ $Revision: 82714 $
 */
public interface BusinessDocument<D> extends Serializable, ConfirmableObject, StateObject, PossibleAddingOperationUIDObject, NodeInfoContainer, DeviceInfoObject, FraudAuditedObject
{
	/**
	 * Шаблон источника расширенного поля
	 */
	public static final String EXTENDED_FIELD_SOURCE_PATTERN = "extra-parameters/parameter[@name='%s']/@value";

	/**
	 * @return ID документа
	 */
	Long getId();

	/**
	 * @return имя формы платежа
	 */
	String getFormName();

	/**TODO REMOVE
	 * @param formName имя формы платежа
	 */
	void setFormName(String formName);

	/**
	 * @return тип формы
	 */
	FormType getFormType();

	/**
	 * @return Владелец
	 */
	BusinessDocumentOwner getOwner() throws BusinessException;

	/**
	 * @param owner владелец
	 */
	void setOwner(BusinessDocumentOwner owner) throws BusinessException;

	/**
	 * @return номер документа
	 */
	String getDocumentNumber();

	/**
	 * Установка номера документа
	 * @param documentNumber номер документа
	 */
	void setDocumentNumber(String documentNumber);

	/**
	 * Инициализация документа
	 * @param document XML представление
	 * @param messageCollector хранилище ошибок, может быть null
	 */
	void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException;

	/**
	 * Инициализация документа
	 * @param template шаблон
	 */
	void initialize(TemplateDocument template) throws DocumentException;

	/**
	 * Создание копии документа
	 * @return deep copy
	 */
	BusinessDocument createCopy() throws DocumentException, DocumentLogicException;

	/**
	 * Создать копию в качестве указанного класса
	 * @param instanceClass класс копии или null если копия вызванного класса
	 * @return копия
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	public BusinessDocument createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException;

	/**
	 * Прообразование документа в XML представление
	 * @return XML представление
	 */
	Document convertToDom() throws DocumentException;

	/**
	 * Восстановление (обновление) документа из XML представления
	 * @param document XML представление платежа
	 * @param messageCollector хранилище ошибок, может быть null
	 * @throws DocumentLogicException ошибка при чтении данных. Ошибка ввода
	 */
	void readFromDom(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException;

	/**
	 * @return гейтовый тип документа
	 */
	Class <? extends GateDocument> getType();

	/**
	 * Установить тип создания документа
	 * @param type - тип документа
	 */
	void setCreationType(CreationType type);

	/**
	 * @return тип создания документа
	 */
	CreationType getCreationType();

	/**
	 * @return канал подтверждения клиентом
	 */
	public CreationType getClientOperationChannel();

	/**
	 * Установить канал подтверждения клиентом
	 * @param clientOperationChannel канал подтверждения клиентом
	 */
	public void setClientOperationChannel(CreationType clientOperationChannel);

	/**
	 * @return статус документа
	 */
	State getState();

	/**
	 * @param state статус документа
	 */
	void setState(State state);

	/**
	 * @return ЭЦП документа
	 */
	DocumentSignature getSignature();

	/**
	 * @param signature ЭЦП документа
	 */
	void setSignature(DocumentSignature signature);

	/**
	 * @return подразделение, которому пренадлежит документ
	 */
	D getDepartment() throws BusinessException;

	/**
	 * Установка подразделения
	 * @param department подразделение, которому пренадлежит документ
	 */
	void setDepartment(D department);

	/**
	 * Вернуть код мобильного банка
	 * @return код мобильного банка
	 */
	String getMbOperCode();

	/**
	 * Установить код мобильного банка
	 * @param mbOperCode - код мобильного банка
	 */
	void setMbOperCode(String mbOperCode);

	//TODO с датами полный бардак!
	/**TODO rename creationDate
	 * @return дата создания документа
	 */
	Calendar getDateCreated();

	/**
	 * @param dateCreated дата создания документа
	 */
	void setDateCreated(Calendar dateCreated);

	/**TODO rename creationDate
	 * @return дата документа(заполняется пользователем)
	 */
	Calendar getDocumentDate();

	/**
	 * @param documentDate дата документа(заполняется пользователем)
	 */
	void setDocumentDate(Calendar documentDate);

	/**
	 * @return дата совершения операции, которая заполняется в момент подтверждения документа.
	 */
	Calendar getOperationDate();

	/**
	 * @return причина отказа документа.
	 */
	String getRefusingReason();

	/**
	 * Установка даты советшения операции.
	 * @param operationDate дата совершения операции.
	 */
	void setOperationDate(Calendar operationDate);

	/**
	 * Дата приема документа банком, т.е. когда бэк-офис принял к исполнению этот платеж.
	 * Например, если документ отправлен вне опер. дня, то исполнен он будет на следующий.
	 * @return дата приема
	 */
	public Calendar getAdmissionDate();

	/**
	 * Установить дату приема документа банком.
	 * @param admissionDate дата приема
	 */
	public void setAdmissionDate(Calendar admissionDate);

	/**
	 * @return дата исполнения документа
	 */
	Calendar getExecutionDate();

	/**
	 * Установка даты исполнения документа
	 * @param executionDate дата исполнения документа
	 */
	void setExecutionDate(Calendar executionDate);

	/**
	 * Установить признак помещения заявки в архив
	 * @param archive true - поместить в архив
	 */
	void setArchive(boolean archive);

	/**
	 * @return true - заявка помещена в архив
	 */
	boolean isArchive();

	/**
	 * Установка причины отказа документа
	 * @param refusingReason причина отказа документа
	 */
	void setRefusingReason(String refusingReason);

	/**
	 * Установить название машины состояний
	 * @param stateMachineName название машины состояний
	 */
	void setStateMachineName(String stateMachineName);

	/**
	 * Возвращает количество системных ошибок, возникших при проведении платежа
	 * @return количество ошибок
	 */
	public Long getCountError();

	/**
	 * Устанавливает новое значение количества системных ошибок при проведении платежа
	 * @param countError - кол-во ошибок
	 */
	public  void setCountError(Long countError);

	/**
	 * Прибавление 1 к количеству ошибок
	 */
	public  void  incrementCountError();

	/**
	 * @return тип создания документа (поля заполнены пользователем/из шаблона)
	 */
	public CreationSourceType getCreationSourceType();

	/**
	 * Устанавливает тип создания документа
	 * @param creationSourceType - тип создания документа
	 */
	public void setCreationSourceType(CreationSourceType creationSourceType);

	/**
	 * Устанавливает имя системы в которой находится документ
	 * @param name имя системы
	 */
	void setSystemName(String name);

	/**
	 * тип стратегии, по кот. был подтвержден документ
	 * @return
	 */
	public ConfirmStrategyType getConfirmStrategyType();

	/**
	 * @return промо-код.
	 */
	String getPromoCode();

	/**
	 * @return Id логина создавшего дакумент
	 */
	Long getCreatedEmployeeLoginId();

	/**
	 * Установить id логина создавшего документ
	 * @param loginId id логина
	 */
	void setCreatedEmployeeLoginId(Long loginId);

	/**
	 * @return Id логина подтвердившего дакумент
	 */
	Long getConfirmedEmployeeLoginId();

	/**
	 * Установить id логина подтвердившего документ
	 * @param loginId id логина
	 */
	void setConfirmedEmployeeLoginId(Long loginId);

	/**
	 * @return
	 */
	Long getAdditionalConfirmedEmployeeLoginId();

	/**
	 *
	 * @param additionalConfirmedEmployeeLoginId
	 */
	void setAdditionalConfirmedEmployeeLoginId(Long additionalConfirmedEmployeeLoginId);

	/**
	 * Id сессии, в которой подтвержден документ
	 * @return sessionId
	 */
	String getSessionId();

	/**
	 * Устанавливает id сессии, в которой подтвержден документ
	 * @param sessionId  - id сессии
	 */
	public void setSessionId(String sessionId);

	/**
	 * @return  номер УС, с которого вошел клиент
	 */
	public String getCodeATM();

	/**
	 * @param codeATM - номер УС, с которого вошел клиент
	 */
	public void setCodeATM(String codeATM);

	/**
	 * Тип оплаты по отношению к клиенту: перевод на внешние счета/карты либо свои счета карты
	 * @return тип оплаты по отношению к клиенту
	 */
	TypeOfPayment getTypeOfPayment();

	/**
	 * @return  причина для перевода документа в статус "требуется дополнительное подтверждение"
	 */
	String getReasonForAdditionalConfirm();

	/**
	 * @param reasonForAdditionalConfirn - причина для перевода документа в статус "требуется дополнительное подтверждение"
	 */
	void setReasonForAdditionalConfirm(String reasonForAdditionalConfirn);

	/**
	 * @return тип логина, под которым был создан платеж
	 */
	LoginType getLoginType();

	/**
	 * установить тип логина, под которым был создан платеж
	 * @param loginType тип логина, под которым был создан платеж
	 */
	void setLoginType(LoginType loginType);

	/**
	 * @return способ доп. подтверждения платежа
	 */
	CreationType getAdditionalOperationChannel();

	/**
	 * @param additionalOperationChannel способ доп. подтверждения платежа
	 */
	void setAdditionalOperationChannel(CreationType additionalOperationChannel);

	/**
	 * @return дата доп. подтверждения платежа
	 */
	Calendar getAdditionalOperationDate();

	/**
	 * @param date дата доп. подтверждения платежа
	 */
	void setAdditionalOperationDate(Calendar date);

	/**
	 * Флаг, обозначающий превышение допустимого значения проверок статуса платежа во ВС
	 * @return true - превышен
	 */
	boolean getCheckStatusCountResult();

	/**
	 * Установить флаг, обозначающий превышение допустимого значения проверок статуса платежа во ВС
	 * @param result - флаг
	 */
	void setCheckStatusCountResult(boolean result);

	/**
	 * @return плановая дата исполнения, при переводе в offline
	 */
	Calendar getOfflineDelayedDate();

	/**
	 * Является ли данный документ документом по шаблону
	 * @return true - является документом по шаблону
	 */
	boolean isByTemplate();

	/**
	 * Является ли данный документ документом по шаблону
	 * @return true - является документом по шаблону
	 */
	boolean isByMobileTemplate();

	/**
	 * Является ли данный документ копией
	 * @return true - является копией
	 */
	boolean isCopy();

	/**
	 * @return линк списания
	 */
	PaymentAbilityERL getChargeOffResourceLink() throws DocumentException;

	/**
	 * @return линк списания
	 */
	ExternalResourceLink getDestinationResourceLink() throws DocumentException;

	/**
	 * установить тип платежа в ермб
	 * @param ermbPaymentType - тип платежа в ермб
	 */
	void setErmbPaymentType(String ermbPaymentType);

	/**
	 * возвращает тип платежа в ермб
	 * @return тип платежа в ермб
	 */
	String getErmbPaymentType();

	/**
	 * установить название шаблона, по которому создан документ
	 * @param templateName - название шаблона
	 */
	void setTemplateName(String templateName);

	/**
	 * возвращает название шаблона, по которому создан документ
	 * @return название шаблона
	 */
	String getTemplateName();

	/**
	 * устанавливает смс-алиас поставщика
	 * @param receiverSmsAlias  смс-алиас поставщика
	 */
	void setReceiverSmsAlias(String receiverSmsAlias);

	/**
	 * возвращает смс-алиас поставщика
	 * @return смс-алиас поставщика
	 */
	String getReceiverSmsAlias();

	/**
	 * установить идентификатор смс-запроса в ЕРМБ
	 * @param ermbSmsRequestId
	 */
	void setErmbSmsRequestId(String ermbSmsRequestId);

	/**
	 * возвращает идентификатор смс-запроса в ЕРМБ
	 * @return идентификатор смс-запроса в ЕРМБ
	 */
	String getErmbSmsRequestId();

	/**
	 * установить номер пополняемого телефона
	 * @param rechargePhoneNumber - номер пополняемого телефона
	 */
	void setRechargePhoneNumber(String rechargePhoneNumber);

	/**
	 * вернуть номер пополняемого телефона
	 * @return - номер пополняемого телефона
	 */
	String getRechargePhoneNumber();

	/**
	 * установить название мобильного оператора
	 * @param mobileOperatorName - название мобильного оператора
	 */
	void setMobileOperatorName(String mobileOperatorName);

	/**
	 * вернуть название мобильного оператора
	 * @return -  название мобильного оператора
	 */
	String getMobileOperatorName();

	/**
	 * @return сумма платежа
	 */
	Money getExactAmount();

	/**
	 * @param name - имя ключа сообщения об исполнении платежа/перевода
	 */
	void setEventMessageKey(String name);

	/**
	 * @return имя ключа сообщения об исполнении платежа/перевода
	 */
	String getEventMessageKey();

	/**
	 * @return создать название шаблона
	 */
	String getDefaultTemplateName() throws BusinessException, BusinessLogicException;

	/**
	 * Получение идентификатора шаблона на основе которого создан шаблон
	 * @return идентификатор шиблона или null, если платеж создан не на основе шаблона
	 */
	Long getTemplateId();

	/**
	 * Задание идентификатора шаблона, на основе которого создан платеж
	 * @param templateId - идентификатор шаблона
	 */
	void setTemplateId(Long templateId);

	/**
	 * @return true - платеж по дополнительно подтвержденному платежу
	 */
	boolean isPaymentByConfirmTemplate() throws DocumentException;

	/**
	 * Сравнение основных (ключевых) реквизитов шаблона и платежа по шаблону
	 * @param template шаблон
	 * @return true - изменений нет
	 */
	boolean equalsKeyEssentials(TemplateDocument template) throws BusinessException;

	/**
	 * @return флаг необходимости сохранения шаблона платежа
	 */
	boolean isNeedSaveTemplate();

	/**
	 * Установить/сбросить флаг необходимости сохранения шаблона платежа
	 * @param needSaveTemplate необходимости сохранения шаблона платежа
	 */
	void setNeedSaveTemplate(boolean needSaveTemplate);

	/**
	 * @return true - автолатеж/длительное поручение/автоподписка
	 */
	boolean isLongOffer();

	/**
	 * @return тарифный план
	 */
	String getTarifPlanCodeType() throws DocumentException;

	/**
	 * @return true - платеж создал посредством оплаты напоминания
	 */
	boolean isMarkReminder();

	/**
	 * Установить признак оплаты напоминания
	 * @param reminder true - посредством оплаты напоминания
	 */
	void setMarkReminder(boolean reminder);

	/**
	 * Признак поддержки актуализации
	 * @return true - поддерживает, false - нет
	 */
	boolean isUpgradable();

	/**
	 * Всегда проверять IMSI при подтверждении документа
	 * @return
	 */
	boolean isAlwaysIMSICheck();

	/**
	 * Получение объекта-сообщения о смене статуса
	 * @return объект-сообщение
	 */
	DocumentNotice getNotice();

	/**
	 * Удаляет объект-сообщение
	 */
	void removeNotice();

	/**
	 * Установить объект-сообщения об уведомлении клиента
	 * @param notice объект-сообщения
	 */
	void setNotice(DocumentNotice notice);

	/**
	 * Проверка доступна ли работа с данным документом в текущем приложении
	 * @return true - доступна
	 */
	boolean checkApplicationRestriction();

	/**
	 * Признак "Документ поддерживает историю статусов"
	 * @return true - поддерживает
	 */
	boolean supportStatusHistory();

	/**
	 * Возвращает историю смены статусов документа (только для документов, которые поддерживают историю статусов)
	 * Для документов, которые не поддерживают историю статусов, вовзращается пустой список
	 * @return
	 */
	List<BusinessDocumentStatusEntry> getStatusHistory();

	/**
	 * Является ли причиной дополнительного подтверждения вердикт системы фрод-мониторинга?
	 * @return true - если да, false - если нет, или если документ подтверждения не требует.
	 */
	boolean isFraudReasonForAdditionalConfirm();

	/**
	 * Получить текст, отправленный офицером безопасности (фрод)
	 * @return текст
	 */
	String getSecurityOfficerText();

	/**
	 * Положить в документ текст офицера безопасности
	 * @param text
	 */
	void setSecurityOfficerText(String text);
}
