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
 * Класс-обёртка над веб-сервисом УЭК
 */
public class UECWebService
{
	private final String endpointAddress;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param endpointAddress - URL веб-сервиса
	 */
	public UECWebService(String endpointAddress)
	{
		this.endpointAddress = endpointAddress;
	}

	/**
	 * Уведомляет УЭК об изменении статуса платёжных поручений
	 * @param spName - системное имя поставщика в ЕРИБ
	 * @param orders - перечень платёжных поручений
	 */
	public void notifyUECOrders(String spName, Collection<UECPayOrder> orders) throws GateException
	{
		if (StringHelper.isEmpty(spName))
			throw new IllegalArgumentException("Аргумент 'spName' не может быть пустым");
		if (CollectionUtils.isEmpty(orders))
			throw new IllegalArgumentException("Перечень 'orders' не может быть пустым");

		DateFormat simpleDateFormat = ShopConstants.getDateFormat();

		// 1. Формируем запрос
		String rqUID = new RandomGUID().getStringValue();
		Date rqTm = Calendar.getInstance().getTime();

		StatNotRqType request = new StatNotRqType();
		request.setRqUID(rqUID);
		request.setRqTm(simpleDateFormat.format(rqTm));
		request.setSPName(spName);
		request.setDocuments(convertOrdersToRequest(orders));

		// 2. Выполняем запрос
		StatNotRsType response = runWebService(request);

		// 3. Разбираем ответ
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
				throw new GateException("Веб-сервис вернул null, а это странно");
			}

			if (!rqUID.equals(response.getRqUID())) {
				throw new GateException("rqUID запроса и ответа не совпали. Сравните: " + rqUID + " и " + response.getRqTm());
			}

			return response;
		}
		catch (RemoteException e)
		{
			throw new GateException("Ошибка запуска веб-сервиса", e);
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
			throw new ConfigurationException("Не удалось подключиться к веб-сервису " + endpointAddress, e);
		}
	}
}
