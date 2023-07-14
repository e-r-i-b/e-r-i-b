package com.rssl.phizicgate.mdm.integration.mdm;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.mdm.common.*;
import com.rssl.phizicgate.mdm.integration.mdm.config.MDMIntegrationConfig;
import com.rssl.phizicgate.mdm.integration.mdm.message.*;
import com.rssl.phizicgate.mdm.integration.mdm.processors.CreateProfileNotificationProcessor;
import com.rssl.phizicgate.mdm.integration.mdm.processors.SearchProfileProcessor;
import com.rssl.phizicgate.mdm.integration.mdm.processors.UpdateProfileAndProductsTicketProcessor;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ���
 */

public class MDMClientService
{
	/**
	 * ����� ���������� � ���
	 * @param searchClientInfo ��������� ������
	 * @param mdmId ������������� ���
	 * @return ��������� ������
	 * @throws GateException
	 */
	public ClientWithProductsInfo searchProfile(SearchClientInfo searchClientInfo, String mdmId) throws GateException, GateLogicException
	{
		SearchProfileProcessor processor = new SearchProfileProcessor(searchClientInfo, mdmId);
		processOnline(processor);
		return processor.getResult();
	}

	/**
	 * ����� ���������� � ���
	 * @param clientInfo ��������� ��������
	 * @throws GateException
	 */
	public void createProfileNotification(CreateNotificationClientInfo clientInfo) throws GateException, GateLogicException
	{
		processOffline(new CreateProfileNotificationProcessor(clientInfo));
	}

	/**
	 * ������� ������
	 * @param ticketType ��� ������
	 * @param updateClientInfoResult ������ ����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void sendTicket(ClientInfoUpdateType ticketType, UpdateClientInfoResult updateClientInfoResult) throws GateException, GateLogicException
	{
		switch (ticketType)
		{
			case updateProfileAndProducts:
				sendTicket(new UpdateProfileAndProductsTicketProcessor(updateClientInfoResult));
				return;

			default:
				throw new IllegalArgumentException("����������� ��� ������.");
		}
	}

	private void sendTicket(TicketMessageProcessor processor) throws GateException, GateLogicException
	{
		Request request = processor.makeRequest();
		//noinspection unchecked
		MDMTransportProvider.getInstance().sendTicket(request);
	}

	private <RsType> void processOnline(OnlineMessageProcessor<RsType> processor) throws GateException, GateLogicException
	{
		Request request = processor.makeRequest();
		//noinspection unchecked
		Response<RsType> response = MDMTransportProvider.getInstance().processOnline(request, getTimeout());
		processor.processResponse(response);
	}

	private void processOffline(OfflineMessageProcessor processor) throws GateException, GateLogicException
	{
		Request request = processor.makeRequest();
		//noinspection unchecked
		MDMTransportProvider.getInstance().processOffline(request);
	}

	private int getTimeout()
	{
		return ConfigFactory.getConfig(MDMIntegrationConfig.class).getMDMTimeout();
	}
}
