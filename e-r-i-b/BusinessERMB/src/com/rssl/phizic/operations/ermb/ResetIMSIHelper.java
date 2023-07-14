package com.rssl.phizic.operations.ermb;

import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.messaging.MessageLogger;
import com.rssl.phizic.messaging.ermb.ErmbJndiConstants;
import com.rssl.phizic.person.ClientData;
import com.rssl.phizic.synchronization.SOSMessageHelper;
import com.rssl.phizic.utils.jms.JmsService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * User: Moshenko
 * Date: 12.05.14
 * Time: 15:42
 * Cброс IMSI
 */
public class ResetIMSIHelper
{
	private static final JmsService jmsService = new JmsService();

	private static final String LOG_MESSAGE_TEMPLATE = "Телефон: %s; ФИО: %s; ДУЛ: %s; ДР: %s; ТБ: %s";

	/**
	 * @param phoneNumbers номера телефонов.
	 */
	public static void  resetIMSI(List<String> phoneNumbers, ErmbProfileImpl profile) throws Exception
	{
		String text = null;
		text = SOSMessageHelper.getResetIMSIRqStr(phoneNumbers);
		jmsService.sendMessageToQueue(text, ErmbJndiConstants.AUX_RESET_IMSI_QUEUE, ErmbJndiConstants.AUX_RESET_IMSI_CQF, null, null);
		new MessageLogger(new TextMessage(text,text), Application.ErmbAuxChannel,"ermb-ResetIMSI").makeAndWrite();
		if (profile!=null)
		{
			writeUserLog(Calendar.getInstance(), profile.getPerson().asClientData(), phoneNumbers.get(0));
		}
	}

	private static void writeUserLog(Calendar startTime, ClientData clientData, String phone) throws Exception
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		LogWriter logWriter = OperationLogFactory.getLogWriter();
		DefaultLogDataReader reader = new DefaultLogDataReader("Управление телефонами ЕРМБ: Сброс IMSI по номеру телефона.");
		SimpleLogParametersReader parametersReader = new SimpleLogParametersReader("Результат обработки", String.format(LOG_MESSAGE_TEMPLATE, phone,
				clientData.getSurName() + " " + clientData.getFirstName() + " " + clientData.getPatrName(),
				clientData.getDocumentSeriesNumber(),
				dateFormat.format(clientData.getBirthDay().getTime()),
				clientData.getTb()));
		reader.addParametersReader(parametersReader);
		logWriter.writeActiveOperation(reader, startTime, Calendar.getInstance());
	}
}
