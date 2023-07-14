package com.rssl.phizicgate.uec;

import com.rssl.phizic.common.types.shop.ShopConstants;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.shopclient.UECPayOrder;
import com.rssl.phizicgate.uec.generated.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.xml.rpc.ServiceException;

/**
 * @author Erkin
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �����-������ ��� ���-�������� ���
 */
public class UECWebService
{
	private final String endpointAddress;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param endpointAddress - URL ���-�������
	 */
	public UECWebService(String endpointAddress)
	{
		this.endpointAddress = endpointAddress;
	}

	/**
	 * ���������� ��� �� ��������� ������� �������� ���������
	 * @param spName - ��������� ��� ���������� � ����
	 * @param orders - �������� �������� ���������
	 */
	public void notifyUECOrders(String spName, Collection<UECPayOrder> orders) throws GateException
	{
		if (StringHelper.isEmpty(spName))
			throw new IllegalArgumentException("�������� 'spName' �� ����� ���� ������");
		if (CollectionUtils.isEmpty(orders))
			throw new IllegalArgumentException("�������� 'orders' �� ����� ���� ������");

		DateFormat simpleDateFormat = ShopConstants.getDateFormat();

		// 1. ��������� ������
		String rqUID = new RandomGUID().getStringValue();
		Date rqTm = Calendar.getInstance().getTime();

		StatNotRqType request = new StatNotRqType();
		request.setRqUID(rqUID);
		request.setRqTm(simpleDateFormat.format(rqTm));
		request.setSPName(spName);
		request.setDocuments(convertOrdersToRequest(orders));

		// 2. ��������� ������
		StatNotRsType response = runWebService(request);

		// 3. ��������� �����
		for (ResultType result : response.getDocuments()) {
			for (UECPayOrder order : orders) {
				if (StringUtils.equals(order.getDocUID(), result.getDocUID())) {
					StatusType status = result.getStatus();
					order.setNotifyStatusCode(status.getStatusCode().getValue());
					order.setNotifyStatusDescr(status.getStatusDesc());
				}
			}
		}
	}

	private StatNotRqDocumentType[] convertOrdersToRequest(Collection<UECPayOrder> orders)
	{
		StatNotRqDocumentType[] rqDocuments = new StatNotRqDocumentType[orders.size()];
		int i = 0;
		for (UECPayOrder order : orders)
		{
			StateType orderUecState = UECOrderStates.convertPaymentStateToUEC(order.getPaymentState());
			rqDocuments[i] = new StatNotRqDocumentType(order.getDocUID(), orderUecState);
			i++;
		}
		return rqDocuments;
	}

	private StatNotRsType runWebService(StatNotRqType request) throws GateException
	{
		try
		{
			String rqUID = request.getRqUID();
			UECService webServiceStub = getWebServiceStub();
			StatNotRsType response = webServiceStub.UECDocStatNotRq(request);

			if (response == null) {
				throw new GateException("���-������ ������ null, � ��� �������");
			}

			if (!rqUID.equals(response.getRqUID())) {
				throw new GateException("rqUID ������� � ������ �� �������. ��������: " + rqUID + " � " + response.getRqTm());
			}

			return response;
		}
		catch (RemoteException e)
		{
			throw new GateException("������ ������� ���-�������", e);
		}
	}

	private UECService getWebServiceStub()
	{
		try
		{
			UECServiceImplLocator locator = new UECServiceImplLocator();
			locator.setUECServicePortEndpointAddress(endpointAddress);

			return locator.getUECServicePort();
		}
		catch (ServiceException e)
		{
			throw new ConfigurationException("�� ������� ������������ � ���-������� " + endpointAddress, e);
		}
	}
}
