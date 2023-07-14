package com.rssl.phizicgate.manager.services.routable.documents;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.manager.GateManager;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

import java.util.Calendar;
import java.util.List;

/**
 * @author niculichev
 * @ created 04.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentServiceImpl extends RoutableServiceBase implements AutoPaymentService
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
		return getDelegate().getClientsAutoPayments(cards);
	}

	/**
	 * ѕолучение автоплатежа
	 * @param externalId идентификатор
	 * @return автоплатеж
	 */
	public GroupResult<String, AutoPayment> getAutoPayment(String... externalId)
	{
		try
		{
			return getDelegate().getAutoPayment(externalId);
		}
		catch (GateLogicException e)
		{
			return GroupResultHelper.getOneErrorResult(externalId, e);
		}
		catch (GateException e)
		{
			return GroupResultHelper.getOneErrorResult(externalId, e);
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
		return getDelegate().checkPaymentPossibilityExecution(externalId);
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
		return getDelegate().getSheduleReport(autoPayment, fromDate, toDate);
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
		return getDelegate().getSheduleReport(autoPayment);
	}

	/**
	 * ѕолучение разрешенных типов автоплатежей
	 * @param cardNumber номер карты
	 * @param requisite лицевой счет
	 * @param routeCode код сервиса
	 * @return список разрешенных типов
	 */
	public List<ExecutionEventType> getAllowedAutoPaymentTypes(String cardNumber, String requisite, String routeCode) throws GateLogicException, GateException
	{
		return getDelegate().getAllowedAutoPaymentTypes(cardNumber, requisite, routeCode);
	}

	private AutoPaymentService getDelegate() throws GateLogicException, GateException
	{
		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter adapter = config.getCardTransfersAdapter();
		if (adapter == null)
		{
			throw new GateLogicException("Ќе задана внешн€€ система дл€ карточных переводов");
		}

		//при получении uuid адаптера делаем проверку на активность внешней системы
		String uuid = ExternalSystemHelper.getCode(adapter.getUUID());
		return GateManager.getInstance().getService(uuid, AutoPaymentService.class);
	}
}
