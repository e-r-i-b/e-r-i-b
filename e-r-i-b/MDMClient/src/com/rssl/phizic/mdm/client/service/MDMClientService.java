package com.rssl.phizic.mdm.client.service;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.mdm.client.service.config.MDMGateConfig;
import com.rssl.phizic.mdm.client.service.generated.*;
import com.rssl.phizic.mdm.client.service.log.MDMClientLogHelper;
import com.rssl.phizic.mdm.client.service.types.ClientInformation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.apache.axis.client.Stub;

import java.rmi.RemoteException;
import java.util.Calendar;

/**
 * @author akrenev
 * @ created 08.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * клиентская часть сервиса работы с приложением МДМ
 */

public class MDMClientService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final long OK_STATUS = 0;

	/**
	 * получить идентификатор мдм из БД
	 * @param profileId идентификатор профиля
	 * @return идентификатор мдм
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public String getStoredMdmId(Long profileId) throws GateException, GateLogicException
	{
		RequestData requestData = new RequestData();
		GetStoredMDMIdParametersType parameters = new GetStoredMDMIdParametersType();
		parameters.setInnerId(profileId);
		requestData.setGetStoredMDMIdRq(parameters);

		ResponseData response = process(requestData);
		return response.getGetStoredMDMIdRs().getMdmId();
	}

	/**
	 * получить идентификатор мдм из внешней системы
	 * @param clientInformation информация по клиенту
	 * @return идентификатор мдм
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public String getExternalSystemMdmId(ClientInformation clientInformation) throws GateException, GateLogicException
	{
		RequestData requestData = new RequestData();
		GetExternalSystemMDMIdParametersType parameters = new GetExternalSystemMDMIdParametersType();
		parameters.setInnerId(clientInformation.getInnerId());
		parameters.setLastName(clientInformation.getLastName());
		parameters.setFirstName(clientInformation.getFirstName());
		parameters.setMiddleName(clientInformation.getMiddleName());
		parameters.setBirthday(getStringDateTime(clientInformation.getBirthday()));
		parameters.setCardNum(clientInformation.getCardNum());
		parameters.setDocumentSeries(clientInformation.getDocumentSeries());
		parameters.setDocumentNumber(clientInformation.getDocumentNumber());
		parameters.setDocumentType(clientInformation.getDocumentType().name());
		requestData.setGetExternalSystemMDMIdRq(parameters);

		ResponseData response = process(requestData);
		return response.getGetExternalSystemMDMIdRs().getMdmId();
	}


	protected static String getStringDateTime(Calendar date)
	{
		return date == null ? null : XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	private RequestType createRequest(RequestData data)
	{
		RequestType request = new RequestType();
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(getStringDateTime(Calendar.getInstance()));
		request.setOperUID(OperationContext.getCurrentOperUID());
		request.setData(data);
		return request;
	}

	private void processError(StatusType status) throws GateException, GateLogicException
	{
		throw new GateException(status.getStatusDesc());
	}

	private ResponseData process(RequestData data) throws GateException, GateLogicException
	{
		ResponseType response = doRequest(createRequest(data));
		if (response.getStatus().getStatusCode() != OK_STATUS)
			processError(response.getStatus());
		return response.getData();
	}

	private MDMServiceSoapBindingStub getTransport() throws GateException
	{
		try
		{
			MDMService_ServiceLocator service = new MDMService_ServiceLocator();
			MDMServiceSoapBindingStub stub = (MDMServiceSoapBindingStub) service.getMDMService();
			MDMGateConfig config = ConfigFactory.getConfig(MDMGateConfig.class);
			String url = config.getMDMAppUrl() + "/MDMService";
			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
			stub.setTimeout(config.getMDMAppTimeout());
			return stub;
		}
		catch (javax.xml.rpc.ServiceException e)
		{
			throw new GateException(e);
		}
	}

	private ResponseType doRequest(RequestType request) throws GateException, GateLogicException
	{
		MDMServiceSoapBindingStub stub = getTransport();
		Calendar start = Calendar.getInstance();
		try
		{
			return stub.exec(request);
		}
		catch (RemoteException e)
		{
			if (e.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
			{
				log.error("Получена ошибка таймаута при отправке сообщения в приложение МДМ.");
				throw new GateTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, e);
			}
			//Возникновение таймаута
			log.error("Получена техническая ошибка при отправке сообщения в приложение МДМ.",e);
			throw new GateTimeOutException(GateWrapperTimeOutException.GATE_TECHNICAL_EXCEPTION_TIMEOUT_MESSAGE, e);
		}
		finally
		{
			MDMClientLogHelper.writeToLog(stub._getCall().getMessageContext(), DateHelper.diff(Calendar.getInstance(), start));
		}
	}
}
