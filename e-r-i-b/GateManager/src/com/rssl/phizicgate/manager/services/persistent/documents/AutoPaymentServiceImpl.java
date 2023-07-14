package com.rssl.phizicgate.manager.services.persistent.documents;

import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

/**
 * @author niculichev
 * @ created 04.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentServiceImpl extends PersistentServiceBase<AutoPaymentService> implements AutoPaymentService
{
	public AutoPaymentServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * ѕолучение списка автоплатежей
	 * @param cards список карт
	 * @return список автоплатежей
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public List<AutoPayment> getClientsAutoPayments(List<Card> cards) throws GateException, GateLogicException
	{
		List<AutoPayment> rezult = new ArrayList<AutoPayment>(); 
		for (AutoPayment  payment : delegate.getClientsAutoPayments(cards))
		{
			rezult.add(storeRouteInfo(payment));
		}
		return rezult;
	}

	/**
	 * ѕолучение автоплатежа
	 * @param externalIds идентификаторы
	 * @return автоплатеж
	 */
	public GroupResult<String, AutoPayment> getAutoPayment(String... externalIds)
	{
		GroupResult<String, AutoPayment> rezult = delegate.getAutoPayment(removeRouteInfo(externalIds));
		try
		{
			return storeRouteInfo(rezult);
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(externalIds, e);
		}
	}

	/**
	 * ѕроверка состо€ни€ автоплатежа
	 * @param externalId идентификатор
	 * @return статус платежа
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public AutoPaymentStatus checkPaymentPossibilityExecution(String externalId) throws GateException, GateLogicException
	{
		return delegate.checkPaymentPossibilityExecution(removeRouteInfo(externalId));
	}

	/**
	 * ѕолучение графика исполнени€ платежа
	 * @param autoPayment автоплатеж
	 * @param fromDate дата начала периода
	 * @param toDate дата окончани€ периода
	 * @return график исполнени€
	 */
	public List<ScheduleItem> getSheduleReport(AutoPayment autoPayment, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		return delegate.getSheduleReport(removeRouteInfo(autoPayment), fromDate, toDate);
	}

	/**
	 * ѕолучение всего графика исполнени€ платежа
	 * @param autoPayment автоплатеж
	 * @return весь график исполнени€ платежа
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public List<ScheduleItem> getSheduleReport(AutoPayment autoPayment) throws GateException, GateLogicException
	{
		return delegate.getSheduleReport(removeRouteInfo(autoPayment));
	}

	/**
	 * ѕолучение разрешенных типов автоплатежей
	 * @param cardNumber номер карты
	 * @param requisite лицевой счет
	 * @param routeCode код сервиса
	 * @return список разрешенных типов
	 */
	public List<ExecutionEventType> getAllowedAutoPaymentTypes(String cardNumber, String requisite, String routeCode) throws GateException, GateLogicException
	{
		String routeCodeWithoutRouteInfo = IDHelper.restoreOriginalIdWithAdditionalInfo(removeRouteInfo(routeCode));
		return delegate.getAllowedAutoPaymentTypes(cardNumber, requisite, routeCodeWithoutRouteInfo);
	}
}