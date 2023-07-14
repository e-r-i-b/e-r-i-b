package com.rssl.phizicgate.iqwave.autopayment;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 31.01.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentServiceImpl extends AbstractService implements AutoPaymentService
{
	private static AutoPaymentRequestHelper requestHelper = new AutoPaymentRequestHelper();
	private static AutoPaymentResponseSerializer responseSerializer = new AutoPaymentResponseSerializer();

	public AutoPaymentServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * ��������� ������ ������������
	 * @param cards ������ ����
	 * @return ������ ������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<AutoPayment> getClientsAutoPayments(List<Card> cards) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.AUTO_PAY_LIST_REQUEST);
		requestHelper.fillAutoPayListRequest(cards, message);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		return responseSerializer.fillAutoPayments(response);
	}

	/**
	 * ��������� �����������
	 * @param externalIds �������������
	 * @return ����������
	 */
	public GroupResult<String, AutoPayment> getAutoPayment(String... externalIds)
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GroupResult<String, AutoPayment> autoPayment = new GroupResult<String, AutoPayment>();
		for (String externalId: externalIds)
		{
			try
			{
				GateMessage message = serviceFacade.createRequest(Constants.AUTO_PAY_REPORT_REQUEST);
				requestHelper.fillBaseRequest(externalId, message);
				Document response = serviceFacade.sendOnlineMessage(message, null);
				autoPayment.putResult(externalId, responseSerializer.fillAutoPayment(response.getDocumentElement()));
			}
			catch (GateException e)
			{
				autoPayment.putException(externalId, e);
			}
			catch (GateLogicException e)
			{
				autoPayment.putException(externalId, e);
			}
		}
		return autoPayment;
	}

	/**
	 * �������� ��������� �����������
	 * @param externalId �������������
	 * @return ������ �������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public AutoPaymentStatus checkPaymentPossibilityExecution(String externalId) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.AUTO_PAY_STATE_REQUEST);
		requestHelper.fillBaseRequest(externalId, message);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		return responseSerializer.getStatus(response.getDocumentElement());
	}

	/**
	 * ��������� ������� ���������� ������� �� ��������� ������
	 * @param autoPayment ����������
	 * @param fromDate ���� ������ �������
	 * @param toDate ���� ��������� �������
	 * @return ������ ����������
	 */
	public List<ScheduleItem> getSheduleReport(AutoPayment autoPayment, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		List<ScheduleItem> result = new ArrayList<ScheduleItem>();
		
		for (ScheduleItem item : getSheduleReport(autoPayment))
			if (item.getDate().compareTo(fromDate) >= 0 && item.getDate().compareTo(toDate) <= 0)
				result.add(item);

		return result;
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
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.AUTO_PAY_REPORT_REQUEST);
		requestHelper.fillBaseRequest(autoPayment.getExternalId(), message);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		return responseSerializer.fillSceduleItems(response);
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
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.AUTO_PAY_GET_CLIENT_TYPE_REQUEST);
		requestHelper.fillBaseRequest(message, cardNumber, requisite, routeCode);
		Document response = serviceFacade.sendOnlineMessage(message, null);
		return responseSerializer.fillAllowedAutoPayTypes(response);
	}
}
