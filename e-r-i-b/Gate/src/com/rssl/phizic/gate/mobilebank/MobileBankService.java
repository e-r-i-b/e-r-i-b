package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.profile.MBKPhone;

import java.sql.ResultSet;
import java.util.*;

/**
 * @author Erkin
 * @ created 14.04.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */

/**
 * Сервис для получения и редактирования информации о шаблонах платежей
 * из АС "Мобильный Банк" (МБ)
 */
public interface MobileBankService extends Service
{

	/**
	 * Получение регистраций карты в АС МБ
	 * @param alternative использовать ли альтернативный вариант получения регистраций
	 * false - используется GetRegistrations, true - используются GetRegistrations и GetRegistrations2, null - используется GetRegistrations2
	 * @param cardNumbers список номеров карт,
	 *  по которым необходимо получить регистрации
	 * @return регистрации карты в АС МБ
	 */
	GroupResult<String, List<MobileBankRegistration>> getRegistrations(Boolean alternative, String... cardNumbers);

	/**
	 * Получение регистраций карты в АС МБ. Используется хранимая процедура getRegistrations3
	 * @param cardNumber номер карты
	 * @return список регистраций карты
	 */
	List<MobileBankRegistration3> getRegistrations3(String cardNumber) throws GateException, GateLogicException;

	/**
	 * Получение пачки регистраций по картам в АС МБ
	 * @param alternative использовать ли альтернативный вариант получения регистраций
	 * false - используется GetRegistrations, true - используются GetRegistrations и GetRegistrations2, null - используется GetRegistrations2
	 * @param cardNumbers список номеров карт,
	 *  по которым необходимо получить регистрации
	 * @return регистрации карты в АС МБ
	 */
	List<MobileBankRegistration> getRegistrationsPack(Boolean alternative, String... cardNumbers) throws GateException, GateLogicException;

	/**
	 * Получить список шаблонов платежей МБ карте и номеру телефона клиента
	 * @param count кол-во шаблонов (по каждой карте),
	 *  которое необходимо получить или null, если нужны все
	 * @param cardInfo карта и номер телефона
	 * @return список шаблонов платежей
	 */
	GroupResult<MobileBankCardInfo, List<MobileBankTemplate>>
	getTemplates(Long count, MobileBankCardInfo... cardInfo);

	/**
	 * Добавить шаблоны в список шаблонов платежей Мобильного Банка
	 * @param cardInfo карта и номер телефона
	 * @param templates список шаблонов платежей, которые нужно добавить
	 * @exception GateException
	 */
	void addTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws GateException, GateLogicException;

	/**
	 * Удалить шаблоны из списка шаблонов платежей Мобильного Банка
	 * @param cardInfo карта и номер телефона
	 * @param templates список шаблонов платежей, которые нужно удалить
	 * @exception GateException
	 */
	void removeTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws GateException, GateLogicException;

	/**
	 * Отправить СМС по номеру телефона
	 * @param text текст сообщения
	 * @param textToLog текст для гоирования
	 * @param id уникальный идентификатор сообщения
	 * @param phone номер телефона в формате
	 *  PhoneNumberValidator.REGEXP_FOR_PHONE_NUMBER
	 * @throws GateException
	 */
	void sendSMS(String text, String textToLog, int id, String phone) throws GateException, GateLogicException;

	/**
	 * Отправка смс с проверкой IMSI на номера toPhones
	 * @param messageInfo информация о сообщении
	 * @param toPhones телефоны
	 * @return мап (номер телефона - ошибка)
	 */
	Map<String, SendMessageError> sendSMSWithIMSICheck(MessageInfo messageInfo, String... toPhones) throws GateException, GateLogicException;

	/**
	 * Получить карту
	 * @param client клиент, который или в контексте которого запрашивается информация
	 * @param phone номер телефона (10 цифр, внутри метода добавится 7)
	 * @return карта, или null
	 */
	Card getCardByPhone(Client client,String phone)  throws GateException, GateLogicException;

	/**
	 * Получить информацию о клиенте по номеру телефона
	 * @param phone номер телефона (10 цифр, внутри метода добавится 7)
	 * @return информация о клиенте, или null
	 */
	UserInfo getClientByPhone(String phone)  throws GateException, GateLogicException;

	/**
	 * Используется для смены статуса Быстрых сервисов для указанного номера телефона.
	 * Может вернуть статусы QUICK_SERVICE_STATUS_REPEAT_QUERY или QUICK_SERVICE_STATUS_UNKNOWN что говорит об ошибке
	 * во время изменения статуса. 
	 *
	 * @param phoneNumber номер телефона
	 * @param status новый статус
	 * @return QuickServiceStatusCode
	 */
	QuickServiceStatusCode setQuickServiceStatus(String phoneNumber, QuickServiceStatusCode status) throws GateException, GateLogicException;

	/**
	 *
	 * Используется для получения статуса Быстрых сервисов для указанного номера телефона
	 *
	 * @param phoneNumber номер телефона для которого текущий статус
	 * @return QuickServiceStatusCode
	 */
	QuickServiceStatusCode getQuickServiceStatus(String phoneNumber) throws GateException, GateLogicException;

	/**
	 * подключение клиента к мобильному банку (создание регистрационного файла)
	 * @param client клиент
	 * @param cardNumber номер карты
	 * @param phoneNumber номер телефона
	 * @param packetType тип тарифа
	 * @param netType код оператора  (000 - МТС, 001 – Билайн, 002 - Мегафон)
	 * @throws GateException
	 */
	void addRegistration(Client client, String cardNumber, String phoneNumber, String netType, MobileBankTariff packetType) throws GateException, GateLogicException;

	/**
	 * Получение сведений о клиенте по номеру его карты
	 *
	 * @param cardNumber номер карты
	 * @return информация о пользователе
	 */
	UserInfo getClientByCardNumber(String cardNumber) throws GateException, GateLogicException;

	/**
	 * Получить информацию о пользователе по логину iPas
	 * @param userId логин iPas
	 * @return информация о пользоватeле или null, если пользователь не найден
	 * @throws GateException
	 */
	UserInfo getClientByLogin(String userId) throws GateException, GateLogicException;

	/**
	 * Определение подключения к МБ по номерам телефонов
	 * @param phoneNumbersToFind строка с телефонами, разделенными ";", которые необходимо проверить на подключение к МБ
	 * @return phoneNumbersFound строка с телефонами, разделенными ";", которые подключены к МБ
	 * @throws GateException
	 */
	String getMobileContact(String phoneNumbersToFind) throws GateException, GateLogicException;

	/**
	 * Отправка сообщения о выполнении интернет-заказа.
	 *
	 * @param text текст сообщения.
	 * @param textLog текст сообщения для логгирования.
	 * @param id уникальный идентификатор сообщения.
	 * @param phone номер телефона.
	 */
	void sendOfferMessageSMS(String text, String textLog, Long id, String phone) throws GateException, GateLogicException;

	/**
	 * @return список подтвержденных пользователями заказов.
	 * @throws GateException
	 */
	List<AcceptInfo> getOfferConfirm() throws GateException, GateLogicException;

	/**
	 * @param id уникальный идентификатор сообщения.
	 * @throws GateException
	 */
	void sendOfferQuit(Long id) throws GateException, GateLogicException;

	/**
	 * Получить карты по номеру телефона
	 * @param phone номер телефона (10 цифр, внутри метода добавится 7)
	 * @return карта, или null
	 */
	Set<String> getCardsByPhone(String phone) throws GateException, GateLogicException;

	/**
	 * Получить карты по номеру телефона
	 * @param phone номер телефона (10 цифр, внутри метода добавится 7)
	 * @return карта, или null
	 */
	Set<String> getCardsByPhoneViaReportDB(String phone) throws GateException, GateLogicException;

	/**
	 *  АС Мигратор при помощи этого метода
     •	запрашивает АС МБК о готовности выполнить миграцию для одного Профиля Клиента
     •	получает от АС МБК полную информацию о Подключениях МБК связанных с Профилем Клиента.
     •	запрашивает у АС МБК удаление части Подключений МБК, которые не предполагается мигрировать
     * @param cards Список всех карт Клиента
	 *      •	основные карты, держателем которых является клиент
	 *		•	доп. карты выпущенные клиентом другим держателям,
	 *		•	доп. карты выпущенные клиентом самому себе
	 *		•	доп. карты выпущенные другими клиентами мигрируемому клиенту
	 * @param phoneMigrateNumbers Список телефонов. Подключения содержащие эти телефоны подлежат миграции
	 * @param phoneDeleteNumbers Список телефонов для которых нужно отключить все Подключения. Передается только для списковой миграции.
	 * @param migrationType тип миграции
	 * @return id миграции и список связок мбк (может быть пустым)
	 * @throws GateException
	 */
	BeginMigrationResult beginMigrationErmb(Set<MbkCard> cards, Set<String> phoneMigrateNumbers, Set<String> phoneDeleteNumbers, MigrationType migrationType) throws GateException, GateLogicException;

	/**
	 * АС Мигратор при помощи этого метода уведомляет АС МБК, что заблокированные при операции BEGIN, при помощи Миграционной Блокировки, Подключения могут быть удалены из АС МБК и перенесены в Архив.
	 * @param migrationId Уникальный ИД запроса миграции
	 * @return информация по текущему состоянию абонентской платы по каждой связке МБК
	 */
	List<CommitMigrationResult> commitMigrationErmb(Long migrationId) throws GateException, GateLogicException;

	/**
	 * АС Мигратор при помощи этого интерфейса уведомляет АС МБК, что заблокированные ранее, при помощи Миграционной Блокировки, Подключения, могут быть разблокированы. Миграция для них возможно будет проведена в будущем
	 * @param migrationId  Уникальный ИД запроса миграции
	 * @throws GateException
	 */
	void rollbackMigration(Long migrationId) throws GateException, GateLogicException;

	/**
	 * АС Мигратор при помощи этого интерфейса может возвратить в АС МБК Подключения, для которых ранее им был запрошен COMMIT миграции.
	 * @param migrationId Уникальный ИД запроса миграции
	 * @param clientTariffInfo Информация о состоянии списания абонентской платы клиента
	 * @throws GateException
	 */
	void reverseMigration(Long migrationId, ClientTariffInfo clientTariffInfo) throws GateException, GateLogicException;

	/**
	 * Получить телефоны, отключенные ОСС
	 * @param maxResults максимальное количество записей
	 * @return - список отключенных телефонов
	 * @throws GateException
	 */
	List<DisconnectedPhoneResult> getDisconnectedPhones(int maxResults) throws GateException, GateLogicException;

	/**
	 * Обновить статус отключенных телефонов
	 * @param processedPhones - телефоны
	 */
	void updateDisconnectedPhonesState(List<Integer> processedPhones) throws GateException, GateLogicException;

	/**
	 * получение запросов МБК, по которым нужно определить карты клиентов (P2P)
	 * @throws GateException
	 */
	List<P2PRequest> getMobilePaymentCardRequests() throws GateException, GateLogicException;

	/**
	 * Отправка ответов на запрос МБК о получении номеров карт по номеру телефона
	 * @throws GateException
	 */
	void sendMobilePaymentCardResult(MobilePaymentCardResult cardResult) throws GateException, GateLogicException;

	/**
	 * Получить связки МБК->ЕРИБ по изменению параметров услуги "Мобильный Банк" в профиле ЕРМБ.
	 * Задача «Обработка подключений к МБК в ЕРМБ» (миграция на лету)
	 * @return резальтсет со связками МБК или null, если новых связок нет
	 */
	List<MBKRegistration> getMbkRegistrationsForErmb() throws GateException, GateLogicException;

	/**
	 * Подтверждение ЕРМБ получения подключений из МБК
	 * @param regIds - список id принятых подключений
	 * @throws GateException
	 */
	void confirmMbRegistrationsLoading(List<Long> regIds) throws GateException, GateLogicException;

	/**
	 * Отправить результат обработки в ЕРМБ подключения, полученного из МБК
	 * @param id - идентификатор подключения
	 * @param resultCode - код результата обработки
	 * @param errorDescr - описание ошибки
	 * @throws GateException
	 */
	void sendMbRegistrationProcessingResult(long id, MBKRegistrationResultCode resultCode, String errorDescr) throws GateException, GateLogicException;

	/**
	 * Обновление в БД МБК таблицы телефонов зарегистрированных в ЕРМБ
	 * @param ermbPhones - список обновляемых телефонов
	 * @throws GateException
	 */
	void updateErmbPhonesInMb(List<ERMBPhone> ermbPhones) throws GateException, GateLogicException;

	/**
	 * Получение списка измененых номеров телефонов за сутки.
	 *
	 * @param start дата начала.
	 * @return список измененых телефонов.
	 */
	List<MBKPhone> getPhonesForPeriod(Calendar start) throws GateException, GateLogicException;

	/**
	 * Получить номер карты по телефону
	 * @param client клиент
	 * @param phone номер телеофна
	 * @return номер карты
	 * @throws GateException
	 */
	String getCardNumberByPhone(Client client, String phone) throws GateException, GateLogicException;

	/**
	 * Получить сообщения унифицированного интерфейса МБК
	 * @return список сообщений
	 * @throws GateException
	 */
	List<UESIMessage> getUESIMessagesFromMBK() throws GateException, GateLogicException;

	/**
	 * Подтвердить получение сообщений унифицированного интерфейса МБК
	 * @param ids идентификаторы сообщений
	 * @throws GateException
	 */
	void confirmUESIMessages(List<Long> ids) throws GateException, GateLogicException;

	public Map<String, SendMessageError> sendIMSICheck(String... phones) throws GateException, GateLogicException;

	public Set<String> getRegPhonesByCardNumbers(List<String> cardNumbers, GetRegistrationMode mode) throws GateException, GateLogicException;

	public List<MobileBankRegistration> getRegistrations4(String cardNumber, GetRegistrationMode mode) throws GateException, GateLogicException, SystemException;

	public Boolean generatePassword(String cardNumber) throws GateException, GateLogicException;

	public Boolean generatePasswordForErmbClient(String cardNumber,  String  phoneNumber) throws GateException, GateLogicException;

	/**
	 * Возвращает список шаблонов по перечню карт
	 * @param cardNumbers - массив номеров карт, по которым нужно получить список шаблонов
	 * @return список шаблонов платежей
	 */
	GroupResult<String, List<GatePaymentTemplate>> getPaymentTemplates(String... cardNumbers);

	/**
	 * Возвращает шаблон платежа по его ID во внешней системе
	 * @param externalId - ID платежа во внешней системе
	 * @return шаблон платежа
	 *  или null, если не найден
	 */
	GatePaymentTemplate getPaymentTemplate(String externalId) throws GateException, GateLogicException;
}
