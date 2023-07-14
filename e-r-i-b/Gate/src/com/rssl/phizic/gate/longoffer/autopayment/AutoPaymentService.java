package com.rssl.phizic.gate.longoffer.autopayment;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;

import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 31.01.2011
 * @ $Author$
 * @ $Revision$
 * Сервис получения информации об автоплатеже
 */
public interface AutoPaymentService extends Service
{
	/**
	 * Получение списка автоплатежей
	 * НА ДАННЫЙ МОМЕНТ ПЕРЕДАЕМ ТОЛЬКО ТО ЧТО ЕСТЬ В НАШЕЙ БД, Т.Е. ID, НОМЕР, ДАТА ОКОНЧАНИЯ.
	 * @param cards список карт
	 * @return список автоплатежей
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = ListCardCacheKeyComposer.class, name = "AutoPayment.clientAutoPayments", cachingWithWaitInvoke = true)
	public List<AutoPayment> getClientsAutoPayments(List<Card> cards) throws GateException, GateLogicException;

	/**
	 * Получение автоплатежа
	 * @param externalId идентификатор
	 * @return автоплатеж
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = AutoPaymentExternalIdCacheKeyComposer.class, name = "AutoPayment.autoPayment")
	public GroupResult<String, AutoPayment> getAutoPayment(String... externalId);

	/**
	 * Проверка состояния автоплатежа
	 * @param externalId идентификатор
	 * @return статус платежа
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Deprecated
	public AutoPaymentStatus checkPaymentPossibilityExecution(String externalId) throws GateException, GateLogicException;

	/**
	 * Получение графика исполнения платежа за указанный срок
	 * @param autoPayment автоплатеж
	 * @param fromDate дата начала периода
	 * @param toDate дата окончания периода
	 * @return график исполнения
	 */
	@Cachable(keyResolver = FullAbstractCacheKeyComposer.class, name = "AutoPayment.sheduleReportPeriod")
	public List<ScheduleItem> getSheduleReport(AutoPayment autoPayment, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException;

	/**
	 * Получение всего графика исполнения платежа
	 * @param autoPayment автоплатеж
	 * @return весь график исполнения платежа
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = AutoPaymentCacheKeyComposer.class, name = "AutoPayment.sheduleReport")
	public List<ScheduleItem> getSheduleReport(AutoPayment autoPayment) throws GateException, GateLogicException;

	/**
	 * Получение разрешенных типов автоплатежей
	 * @param cardNumber номер карты
	 * @param requisite лицевой счет
	 * @param routeCode код сервиса
	 * @return список разрешенных типов
	 */
	@Cachable(keyResolver = AllowedAutoPaymentTypesComposer.class, name = "AutoPayment.allowedAutoPaymentTypes")
	public List<ExecutionEventType> getAllowedAutoPaymentTypes(String cardNumber, String requisite, String routeCode) throws GateException, GateLogicException;
}
