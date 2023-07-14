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
	 * ��������� ������ ������������
	 * @param cards ������ ����
	 * @return ������ ������������
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
	 * ��������� �����������
	 * @param externalIds ��������������
	 * @return ����������
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
	 * �������� ��������� �����������
	 * @param externalId �������������
	 * @return ������ �������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public AutoPaymentStatus checkPaymentPossibilityExecution(String externalId) throws GateException, GateLogicException
	{
		return delegate.checkPaymentPossibilityExecution(removeRouteInfo(externalId));
	}

	/**
	 * ��������� ������� ���������� �������
	 * @param autoPayment ����������
	 * @param fromDate ���� ������ �������
	 * @param toDate ���� ��������� �������
	 * @return ������ ����������
	 */
	public List<ScheduleItem> getSheduleReport(AutoPayment autoPayment, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		return delegate.getSheduleReport(removeRouteInfo(autoPayment), fromDate, toDate);
	}

	/**
	 * ��������� ����� ������� ���������� �������
	 * @param autoPayment ����������
	 * @return ���� ������ ���������� �������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public List<ScheduleItem> getSheduleReport(AutoPayment autoPayment) throws GateException, GateLogicException
	{
		return delegate.getSheduleReport(removeRouteInfo(autoPayment));
	}

	/**
	 * ��������� ����������� ����� ������������
	 * @param cardNumber ����� �����
	 * @param requisite ������� ����
	 * @param routeCode ��� �������
	 * @return ������ ����������� �����
	 */
	public List<ExecutionEventType> getAllowedAutoPaymentTypes(String cardNumber, String requisite, String routeCode) throws GateException, GateLogicException
	{
		String routeCodeWithoutRouteInfo = IDHelper.restoreOriginalIdWithAdditionalInfo(removeRouteInfo(routeCode));
		return delegate.getAllowedAutoPaymentTypes(cardNumber, requisite, routeCodeWithoutRouteInfo);
	}
}