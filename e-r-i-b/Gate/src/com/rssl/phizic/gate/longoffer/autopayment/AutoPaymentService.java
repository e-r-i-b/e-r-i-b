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
 * ������ ��������� ���������� �� �����������
 */
public interface AutoPaymentService extends Service
{
	/**
	 * ��������� ������ ������������
	 * �� ������ ������ �������� ������ �� ��� ���� � ����� ��, �.�. ID, �����, ���� ���������.
	 * @param cards ������ ����
	 * @return ������ ������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = ListCardCacheKeyComposer.class, name = "AutoPayment.clientAutoPayments", cachingWithWaitInvoke = true)
	public List<AutoPayment> getClientsAutoPayments(List<Card> cards) throws GateException, GateLogicException;

	/**
	 * ��������� �����������
	 * @param externalId �������������
	 * @return ����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = AutoPaymentExternalIdCacheKeyComposer.class, name = "AutoPayment.autoPayment")
	public GroupResult<String, AutoPayment> getAutoPayment(String... externalId);

	/**
	 * �������� ��������� �����������
	 * @param externalId �������������
	 * @return ������ �������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Deprecated
	public AutoPaymentStatus checkPaymentPossibilityExecution(String externalId) throws GateException, GateLogicException;

	/**
	 * ��������� ������� ���������� ������� �� ��������� ����
	 * @param autoPayment ����������
	 * @param fromDate ���� ������ �������
	 * @param toDate ���� ��������� �������
	 * @return ������ ����������
	 */
	@Cachable(keyResolver = FullAbstractCacheKeyComposer.class, name = "AutoPayment.sheduleReportPeriod")
	public List<ScheduleItem> getSheduleReport(AutoPayment autoPayment, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException;

	/**
	 * ��������� ����� ������� ���������� �������
	 * @param autoPayment ����������
	 * @return ���� ������ ���������� �������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = AutoPaymentCacheKeyComposer.class, name = "AutoPayment.sheduleReport")
	public List<ScheduleItem> getSheduleReport(AutoPayment autoPayment) throws GateException, GateLogicException;

	/**
	 * ��������� ����������� ����� ������������
	 * @param cardNumber ����� �����
	 * @param requisite ������� ����
	 * @param routeCode ��� �������
	 * @return ������ ����������� �����
	 */
	@Cachable(keyResolver = AllowedAutoPaymentTypesComposer.class, name = "AutoPayment.allowedAutoPaymentTypes")
	public List<ExecutionEventType> getAllowedAutoPaymentTypes(String cardNumber, String requisite, String routeCode) throws GateException, GateLogicException;
}
