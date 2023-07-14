package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.dataaccess.common.counters.Counter;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;

import java.util.*;

/**
 * @author Gulov
 * @ created 11.12.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция выгрузки отчета для сотрудников call-центра
 */
public class CallCentreMigrationOperation extends MigrationReportOperationBase
{
	@Override
	String getFileTemplate()
	{
		return "KЦ_%s_%s.xls";
	}

	@Override
	Counter getCounter()
	{
		return Counters.ERMB_MIGRATION_CC_REPORT_NUMBER;
	}

	@Override
	String[] getHeader()
	{
		return new String[] {
				"ФИО, ДУЛ, ДР клиента",
				"Телефон клиента",
				"ФИО, ДУЛ, ДР клиента, с которым есть конфликт по этому телефону",
				"Список остальных телефонов клиента, не входящих в этот конфликт"
		};
	}

	@Override
	List findData() throws Exception
	{
		return service.findCallCentreData(getSegments());
	}

	@Override
	protected void writeLog(Calendar startTime) throws Exception
	{
		LogWriter logWriter = OperationLogFactory.getLogWriter();
		DefaultLogDataReader reader = new DefaultLogDataReader("АС Мигратор. Операция выгрузка отчетов для КЦ.");
		SimpleLogParametersReader parametersReader = new SimpleLogParametersReader("Отчет сформирован по сегментам: ", StringUtils.join(getSegments(), ","));
		reader.addParametersReader(parametersReader);
		logWriter.writeActiveOperation(reader, startTime, Calendar.getInstance());
	}

	@Override
	void makeContent(List data, HSSFSheet sheet, int firstRow, int firstCell)
	{
		//noinspection unchecked
		ListIterator<Object[]> iterator = data.listIterator();
		Object[] record;
		boolean flag;
		int currentRow = firstRow;
		while (iterator.hasNext())
		{
			record = iterator.next();
			ClientData clientData = createClientData(record);
			setConflictingClient(clientData, record);
			flag = false;
			while (iterator.hasNext())
			{
				flag = true;
				record = iterator.next();
				if (!sameClient(record, clientData.getClient()))
					break;
				setConflictingClient(clientData, record);
			}
			currentRow = outClientData(clientData, sheet, currentRow, firstCell);
			if (flag && iterator.hasNext())
				iterator.previous();
		}
	}

	@Override
	String getSheetTitle()
	{
		return "Отчет для сотрудников call-центра";
	}

	private void setConflictingClient(ClientData clientData, Object[] record)
	{
		ClientData.ConflictingClientInfo conflictingClientInfo = new ClientData.ConflictingClientInfo();
		conflictingClientInfo.setPhone((String) record[5]);
		conflictingClientInfo.setLastName((String) record[6]);
		conflictingClientInfo.setFirstName((String) record[7]);
		conflictingClientInfo.setMiddleName((String) record[8]);
		conflictingClientInfo.setDocument((String) record[9]);
		conflictingClientInfo.setBirthDate((Calendar) record[10]);
		clientData.addConflictingClient(conflictingClientInfo);
	}

	private int outClientData(ClientData clientData, HSSFSheet sheet, int currentRow, int firstCell)
	{
		if (!clientData.hasConflict())
			return currentRow;
		ClientData.ClientInfo client = clientData.getClient();
		ListIterator<ClientData.ConflictingClientInfo> iterator = clientData.getConflictingClients().listIterator();
		ClientData.ConflictingClientInfo conflictingClient;
		ClientData.ConflictingClientInfo temp;
		boolean flag;
		while (iterator.hasNext())
		{
			conflictingClient = iterator.next();
			flag = false;
			if (hasConflictByPhone(clientData.getConflictingClients(), conflictingClient.getPhone()))
			{
				outRow(client, conflictingClient, getNoneConflictedPhone(clientData.getConflictingClients(), conflictingClient.getPhone()), sheet.createRow(currentRow++), firstCell);
				while (iterator.hasNext())
				{
					flag = true;
					temp = iterator.next();
					if (conflictingClient.getPhone().equals(temp.getPhone()))
						outRow(client, temp, getNoneConflictedPhone(clientData.getConflictingClients(), temp.getPhone()), sheet.createRow(currentRow++), firstCell);
				}
			}
			if (flag && iterator.hasNext())
				iterator.previous();
		}
		return currentRow;
	}

	private void outRow(ClientData.ClientInfo client, ClientData.ConflictingClientInfo conflictingClient, Set<String> phones, Row row, int firstCell)
	{
		row.createCell(firstCell++).setCellValue(getClientInfo(client));
		row.createCell(firstCell++).setCellValue(conflictingClient.getPhone());
		row.createCell(firstCell++).setCellValue(getClientInfo(conflictingClient));
		row.createCell(firstCell++).setCellValue(getPhones(phones));
	}

	private boolean hasConflictByPhone(List<ClientData.ConflictingClientInfo> conflictingClients, String phone)
	{
		for (ClientData.ConflictingClientInfo conflictingClient : conflictingClients)
			if (conflictingClient.getPhone().equals(phone) && StringHelper.isNotEmpty(conflictingClient.getLastName()))
				return true;
		return false;
	}

	private Set<String> getNoneConflictedPhone(List<ClientData.ConflictingClientInfo> conflictingClients, String conflictingPhone)
	{
		Set<String> result = new HashSet<String>();
		for (ClientData.ConflictingClientInfo conflictingClientInfo : conflictingClients)
			if (!conflictingClientInfo.getPhone().equals(conflictingPhone))
				result.add(conflictingClientInfo.getPhone());
		return result;
	}

	private boolean sameClient(Object[] record, ClientData.ClientInfo clientInfo)
	{
		return clientInfo.getLastName().equals(record[0]) && clientInfo.getFirstName().equals(record[1])
				&& clientInfo.getMiddleName().equals(record[2])
				&& clientInfo.getDocument().equals(record[3]) && clientInfo.getBirthDate().equals(record[4]);
	}

	private ClientData createClientData(Object[] record)
	{
		ClientData result = new ClientData();
		ClientData.ClientInfo clientInfo = new ClientData.ClientInfo();
		clientInfo.setLastName((String) record[0]);
		clientInfo.setFirstName((String) record[1]);
		clientInfo.setMiddleName((String) record[2]);
		clientInfo.setDocument((String) record[3]);
		clientInfo.setBirthDate((Calendar) record[4]);
		result.setClient(clientInfo);
		return result;
	}

	private String getPhones(Set<String> phones)
	{
		if (phones.size() == 0)
			return "";
		return StringUtils.join(phones, ", ");
	}

	private String getClientInfo(ClientData.ClientInfo client)
	{
		String[] result = new String[] {
				client.getLastName() + " " + client.getFirstName() + " " + client.getMiddleName(),
				client.getDocument(),
				DateHelper.formatDateToStringWithPoint(client.getBirthDate())
		};
		return StringUtils.join(result, ", ");
	}
}
