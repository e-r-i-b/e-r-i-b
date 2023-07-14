package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.business.ermb.migration.list.task.service.TaskWebService;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.OperationBase;

import java.util.Calendar;

/**
 * @author Nady
 * @ created 11.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditSMSDeliveryOperation extends OperationBase
{
	private int numRecipClients;
	private String status;
	private final ClientService clientService = new ClientService();

	public void send(String[] toSendSegments, String[] notSendSegments, String messageText) throws BusinessException
	{
		Calendar startTime = Calendar.getInstance();

		TaskWebService service = new TaskWebService();
		numRecipClients = clientService.countClientsBySegmentsForSMS(toSendSegments, notSendSegments);
		status = service.smsDelivery(toSendSegments,notSendSegments,messageText);

		writeLog(startTime);
	}

	public int getNumRecipClients()
	{
		return numRecipClients;
	}

	public String getStatus()
	{
		return status;
	}

	private void writeLog(Calendar startTime) throws BusinessException
	{
		try
		{
			LogWriter logWriter = OperationLogFactory.getLogWriter();
			DefaultLogDataReader reader = new DefaultLogDataReader("Задача СМС-рассылка в АС Мигратор");
			SimpleLogParametersReader parametersReader = new SimpleLogParametersReader("Клиентов для отправки сообщения: " + numRecipClients);
			reader.addParametersReader(parametersReader);
			logWriter.writeActiveOperation(reader, startTime, Calendar.getInstance());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
