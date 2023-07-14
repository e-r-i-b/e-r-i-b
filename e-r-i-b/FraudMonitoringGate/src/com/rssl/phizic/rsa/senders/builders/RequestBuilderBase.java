package com.rssl.phizic.rsa.senders.builders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.NO;
import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.OFFLINE_LOAD_FIELD_NAME;

/**
 * ������� ����� ������� �������� �� �� ��
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class RequestBuilderBase<RQ extends GenericRequest> implements RequestBuilder<RQ>
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public RQ build() throws GateLogicException, GateException
	{
		RQ request = createRequest();
		request.setActionTypeList(new GenericActionTypeList(new GenericActionType[]{GenericActionType.SET_USER_STATUS}));
		request.setMessageHeader(FraudMonitoringRequestHelper.createMessageHeader(getRequestType()));
		request.setSecurityHeader(FraudMonitoringRequestHelper.createSecurityHeader());
		request.setIdentificationData(createIdentificationData());
		request.setDeviceRequest(getDeviceRequest());
		return request;
	}

	/**
	 * @return ������
	 */
	protected abstract RQ createRequest();

	/**
	 * @return - ��� ������� (������ ��� ����������� � ������� ����������)
	 */
	protected abstract RequestType getRequestType();

	/**
	 * ��� �������
	 * @return �������
	 */
	protected abstract EventsType getEventsType();

	/**
	 * @return IdentificationData
	 */
	protected abstract IdentificationData createIdentificationData() throws GateException;

	protected DeviceRequest getDeviceRequest()
	{
		return FraudMonitoringRequestHelper.createDeviceRequest();
	}

	/**
	 * ��������� ��������� � ������� ������� (������ ��������������� ������)
	 * @return EventData
	 */
	protected EventData createEventData() throws GateException, GateLogicException
	{
		EventData eventData = new EventData();
		eventData.setEventType(EventType.fromValue(getEventsType().getDescription()));
		eventData.setTimeOfOccurrence(DateHelper.toISO8601_24HourDateFormat(Calendar.getInstance()));
		eventData.setClientDefinedAttributeList(getBuilder().build());
		return eventData;
	}

	protected ClientDefinedFactBuilder getBuilder()
	{
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder();
		builder.append(OFFLINE_LOAD_FIELD_NAME, NO, DataType.STRING);
		return builder;
	}
}
