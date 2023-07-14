package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Conflict;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.ConflictedClient;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.business.ermb.migration.list.entity.migrator.ConflictStatus.RESOLVED_TO_DELETE;
import static com.rssl.phizic.business.ermb.migration.list.entity.migrator.ConflictStatus.RESOLVED_TO_OWNER;

/**
 * �������� ���������� ���������� �������� ��� �������
 * @author Puzikov
 * @ created 11.12.13
 * @ $Author$
 * @ $Revision$
 */

public class EditMigrationConflictsOperation extends OperationBase
{
	private static final String EDIT_CONFLICT_OPERATION_KEY = "EDIT_CONFLICT_OPERATION_KEY";
	private List<Conflict> conflicts;

	private final ClientService service = new ClientService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ������������� ��������
	 * @param clientIdString id �������, �� �������� ����������� ��������� ���������
	 * @throws BusinessException
	 */
	public void initialize(String clientIdString) throws BusinessException
	{
		if (StringUtils.isBlank(clientIdString))
			throw new BusinessException("�� ������ ID ������������");
		Long clientId = Long.valueOf(clientIdString);

		conflicts = service.findConflictsByClient(clientId, true);
	}

	/**
	 * ��������� ���������� ���������� ����������
	 * �.�. ��������� � ������� ��������� ����� ����������
	 * @param phonesToResult ��������� ���������� ����������, ���� id ������� �� id �� �������� �������� �������� (RESOLVED_TO_THIS - ��������, RESOLVED_TO_DELETE - �������)
	 * @param employeeFio ��� ����������, ������������ ��������
	 */
	public void save(Map<String, String> phonesToResult, String employeeFio) throws BusinessException
	{
		for (Conflict conflict : conflicts)
		{
			Calendar startTime = Calendar.getInstance();
			String result = phonesToResult.get(conflict.getPhone());
			if (StringUtils.isEmpty(result))
				throw new BusinessException("������������� �������� �� ��������");
			//������� ������� � ���� ������������� ��������
			else if (result.equals(RESOLVED_TO_DELETE.name()))
			{
				conflict.setStatus(RESOLVED_TO_DELETE);
				conflict.setOwnerId(null);
			}
			//�������� � �������, ���������� �����������
			else
			{
				conflict.setStatus(RESOLVED_TO_OWNER);
				conflict.setOwnerId(Long.parseLong(result));
			}
			conflict.setEmployeeInfo(employeeFio);
			writeLog(startTime, conflict);

			service.setConflictDecision(conflict);
		}
	}

	/**
	 * @return ��������� �� �������
	 */
	public List<Conflict> getConflicts()
	{
		return Collections.unmodifiableList(conflicts);
	}

	private void writeLog(Calendar startTime, Conflict conflict)
	{
		LogWriter logWriter = OperationLogFactory.getLogWriter();
		DefaultLogDataReader reader = new DefaultLogDataReader("�� ��������. �������� ���������� ����������.");
		reader.setKey(Constants.EDIT_ERMB_MIGRATION_CONFLICT_KEY);
		reader.setOperationKey(EDIT_CONFLICT_OPERATION_KEY);
		for (ConflictedClient client : conflict.getClients())
		{
			String clientName = String.format("%s %s %s (%s)", client.getFirstName(), client.getMiddleName(), client.getLastName(), client.getDocument());
			String clientChangesMessage = String.format(
					"������ %s %s ���������� �������� %s",
					clientName,
					client.getId().equals(conflict.getOwnerId()) ? "����(�)" : "������ �� ��������",
					conflict.getPhone()
			);
			reader.addParametersReader(new SimpleLogParametersReader(clientName, "���������� � ��������� �������", clientChangesMessage));
		}

		try
		{
			logWriter.writeActiveOperation(reader, startTime, Calendar.getInstance());
		}
		catch (Exception e)
		{
			log.error("������ ������ � ������ �������� ������������", e);
		}
	}
}
