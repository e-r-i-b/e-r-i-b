package com.rssl.phizic.business.ermb.migration.list.task.service;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.task.TaskType;
import com.rssl.phizic.business.ermb.migration.list.task.service.generated.LoadClientsActionRq;
import com.rssl.phizic.business.ermb.migration.list.task.service.generated.SmsDeliveryActionRq;
import com.rssl.phizic.business.ermb.migration.list.task.service.generated.TaskWebService_PortType;
import com.rssl.phizic.business.ermb.migration.list.task.service.generated.TaskWebService_ServiceLocator;
import com.rssl.phizic.config.ConfigFactory;
import org.apache.commons.lang.StringUtils;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * Сервис для отправки задач для менеджера задач
 * @author Puzikov
 * @ created 19.12.13
 * @ $Author$
 * @ $Revision$
 */

public class TaskWebService
{
	private static final String CANNOT_SEND_TASK = "Не удалось отправить задачу: %s. Ошибка: '%s'";
	private static final String EXCEPTION = "Exception:";

	/**
	 * Загрузка списка клиентов миграции
	 * @param filePath путь csv-файла с клиентами
	 * @param nonActivePeriod число допустимых месяцев неактивности
	 * @return статус задачи
	 * @throws BusinessException
	 */
	public String loadClients(String filePath, int nonActivePeriod) throws BusinessException
	{
		TaskWebService_PortType stub = getStub();
		LoadClientsActionRq request = new LoadClientsActionRq(filePath, nonActivePeriod);
		String response = invokeLoadClients(request, stub);

		return response;
	}

	/**
	 * Запуск задачи смс-рассылки
	 * @param sendSegments - сегменты клиентов для рассылки
	 * @param notSendSegments - сегменты клиентов для которых не делать рассылку
	 * @param smsText - текст сообщения
	 * @return статус задачи
	 * @throws BusinessException
	 */
	public String smsDelivery(String[] sendSegments, String[] notSendSegments, String smsText) throws BusinessException
	{
		TaskWebService_PortType stub = getStub();
		SmsDeliveryActionRq request = new SmsDeliveryActionRq(sendSegments, notSendSegments, smsText);

		String response = invokeSMSDelivery(request, stub);

		return response;
	}

	private String invokeLoadClients(LoadClientsActionRq request, TaskWebService_PortType stub) throws BusinessException
	{
		try
		{
			return stub.loadClients(request).getStatus();
		}
		catch (RemoteException e)
		{
			return createResponseMessage(e, TaskType.LOAD_CLIENTS);
		}
	}

	private String invokeSMSDelivery(SmsDeliveryActionRq request, TaskWebService_PortType stub) throws BusinessException
	{
		try
		{
			return  stub.smsDelivery(request).getStatus();
		}
		catch (RemoteException e)
		{
			return createResponseMessage(e, TaskType.SMS_BROADCAST);
		}
	}

	private String createResponseMessage(RemoteException e, TaskType taskType)
	{
		String exceptionMessage = e.getMessage();
		String message = StringUtils.contains(exceptionMessage, EXCEPTION) ? StringUtils.substringAfterLast(e.getMessage(), EXCEPTION) : exceptionMessage;
		return String.format(CANNOT_SEND_TASK, taskType.getDescription(), message);
	}

	private TaskWebService_PortType getStub() throws BusinessException
	{
		try
		{
			TaskWebService_ServiceLocator locator = new TaskWebService_ServiceLocator();
			locator.setTaskWebServicePortEndpointAddress(ConfigFactory.getConfig(ErmbListMigrationConfig.class).getServiceUrl());
			return locator.getTaskWebServicePort();
		}
		catch (ServiceException e)
		{
			throw new BusinessException(e);
		}
	}
}
