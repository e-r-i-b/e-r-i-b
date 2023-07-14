package com.rssl.phizic.gate;

import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.BilingCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.OfficeCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.StringCacheKeyComposer;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.InputMode;

/**
 * @author Krenev
 * @ created 22.04.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Получение информации о возможностях шлюза.
 */
public interface GateInfoService extends Service
{
	/**
	 * Идентификатор внешней системы
	 *
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return строка, идентифицирующая внешнюю систему
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.UID")
	String getUID(Office office) throws GateException, GateLogicException;

	/**
	 * Может ли внешняя система списывать плату за обслуживание.
	 *
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return true - плата списывается гейтом
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.needChargeOff")
	Boolean isNeedChargeOff(Office office) throws GateException, GateLogicException;

	/**
	 * Может ли внешняя система предоставить информацию о пользователе для импорта.
	 *
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return true - пользователь импортируется
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.clientImportEnable")
	Boolean isClientImportEnable(Office office) throws GateException, GateLogicException;

	/**
	 * Требуется ли регистрация пользователя, изменений в профиле пользователя во внешней системе
	 *
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return true - после введения данных необходимо зарегистрировать(активировать пользователя) во внешней системе
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.registrationEnable")
	Boolean isRegistrationEnable(Office office) throws GateException, GateLogicException;


	/**
	 * Требуется ли расторжение договора во внешней системе перед удалением активного клиента.
	 *
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return true - необходимо расторгнуть договор перед удалением активного клиента
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.needAgrementCancellation")
	Boolean isNeedAgrementCancellation(Office office) throws GateException, GateLogicException;

	/**
	 * Внешняя система может предоставить информация о курсах валют.
	 *
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return true - доступно получение курсов валют.
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.currencyRateAvailable")
	Boolean isCurrencyRateAvailable(Office office) throws GateException, GateLogicException;

	/**
	 * Внешняя система может рассчитать комиссии.
	 *
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return true - шлюз подерживает комиссии.
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.paymentCommissionAvailable")
	Boolean isPaymentCommissionAvailable(Office office) throws GateException, GateLogicException;

	/**
	  * Биллинговая система может рассчитать комиссии.
	  * @param billing биллинг, для которого необходимо получить информацию.
	  * @return true - подерживает комиссии.
	  * @exception GateException
	  * @exception GateLogicException
	  */
	 @Cachable(keyResolver= BilingCacheKeyComposer.class,linkable = false, name = "GateInfo.paymentCommissionAvailableForBilling")
	 Boolean isPaymentCommissionAvailable(Billing billing) throws GateException, GateLogicException;

	/**
	 * Внешняя система может предоставить информацию о рабочем календаре.
	 *
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return true - возможно получение рабочего календаря. (по сути, реализован ли CalendarGateService в шлюзе)
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.calendarAvailable")
	Boolean isCalendarAvailable(Office office) throws GateException, GateLogicException;

	/**
	 * Cпособ получения счетов пользователя (IMPORT - счета могут быть получены из внешней системы, MANUAL - счета вводятся в ИКФЛ, OFF - счета неподдерживаются)
	 *
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return способ получения счетов (IMPORT - импорт, MANUAL - ввод вручную, OFF - счета непредусмотрены)
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.accountInputMode")
	InputMode getAccountInputMode(Office office) throws GateException, GateLogicException;

	/**
	 * Cпособ получения карт пользователя (IMPORT - карты могут быть получены из внешней системы, MANUAL - карты вводятся в ИКФЛ, OFF - карты неподдерживаются)
	 *
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return способ получения карт (IMPORT - импорт, MANUAL - ввод вручную, OFF - карты непредусмотрены)
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.cardInputMode")
	InputMode getCardInputMode(Office office) throws GateException, GateLogicException;

	/**
	 * Нужно ли сразу отправить платеж во внешнюю систему
	 *
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return true - отправить платеж сразу во внешнюю систему
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.delayedPaymentNeedSend")
	Boolean isDelayedPaymentNeedSend(Office office) throws GateException, GateLogicException;

	/**
	 * Поддерживает ли шлюз иерархию офисов
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return true - поддерживает
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.officesHierarchySupported")
	Boolean isOfficesHierarchySupported(Office office) throws GateException, GateLogicException;

	/**
	 * Поддерживает ли шлюз отзыв непроведенных платежей при удалении клиента
	 * @param office Офис, для которого необходимо получить информацию.
	 * @return true - поддерживает
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.paymentRecallSupported")
	Boolean isPaymentsRecallSupported(Office office) throws GateException, GateLogicException;

   /**
     * Поддерживает ли биллинг получение расширенных атрибутов поставщика.
    *
     * @param billing биллинг, для которого необходимо получить информацию.
     * @return true - подерживает получение расширенных атрибутов поставщика.
     * @exception GateException
     * @exception GateLogicException
     */
    @Cachable(keyResolver= BilingCacheKeyComposer.class,linkable = false, name = "GateInfo.recipientExtendedAttrAvailable")
    Boolean isRecipientExtedendAttributesAvailable(Billing billing) throws GateException, GateLogicException;

    /**
     * Получить системные настройки подключения к внешней системе
     *
     * @param billing биллинг, для которого необходимо получить информацию.
     * @return системные настройки подключения к внешней системе
     * @exception GateException
     * @exception GateLogicException
     */
    @Cachable(keyResolver= BilingCacheKeyComposer.class,linkable = false, name = "GateInfo.configuration")
    GateConfiguration getConfiguration(Billing billing)  throws GateException, GateLogicException;

	/**
	 * Возвращает признак необходимости вести двухфазную транзакцию между биллнгом и АБС
	 * @param billing биллинг
	 * @return true - нужно, false - не нужно: билллинг сам производит списание средств.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= BilingCacheKeyComposer.class,linkable = false, name = "GateInfo.needTwoPhaseTransactionForBilling")
	Boolean needTwoPhaseTransaction(Billing billing)  throws GateException, GateLogicException;

	/**
	 * Возвращает признак необходимости вести двухфазную транзакцию между биллнгом и АБС
	 * @param receiverCodePoint идентификатор получателя в биллинге
	 * @return true - нужно, false - не нужно: билллинг сам производит списание средств.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= StringCacheKeyComposer.class,linkable = false, name = "GateInfo.neeadTwoPhaseTransaction")
	Boolean needTwoPhaseTransaction(String receiverCodePoint)  throws GateException, GateLogicException;

	/**
	 * Поддерживает ли биллинг персональных получателей
	 *
	 * @param billing биллинг, для которого необходимо получить информацию.
	 * @return true - подерживает получение персональных получателей
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= BilingCacheKeyComposer.class,linkable = false, name = "GateInfo.personalRecipientAvailable")
	Boolean isPersonalRecipientAvailable(Billing billing) throws GateException, GateLogicException;
}
