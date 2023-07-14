package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.task.TaskLog;
import com.rssl.phizic.business.ermb.migration.list.task.TaskType;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * �������� ������� �������� �������� �� ���/��� � ����
 * @author Puzikov
 * @ created 05.12.13
 * @ $Author$
 * @ $Revision$
 */

public class StartListMigrationOperation extends OperationBase
{
	private static final String SUCCESS_MESSAGE = "������� �������� �������. ���� ������: %s";

	private final ClientService service = new ClientService();
	private TaskLog log;

	//��������� ��� �������� ��������
	private List<Segment> segments;

	public void initialize(List<Segment> segments)
	{
		this.segments = segments;
	}

	/**
	 * ��������� ������� ��������
	 * @param segments ��������, �� ������� ���������� ����������� ��������
	 * @return ������ ������
	 * @throws BusinessException
	 */
	public String migrate(List<Segment> segments) throws BusinessException
	{
		try
		{
			log = new TaskLog(TaskType.START_MIGRATION);
			final Long firstBlock = ConfigFactory.getConfig(ErmbListMigrationConfig.class).getMigrationBlockSequence().get(0);

			int clientsCount = service.markClientsBlockBySegments(segments, firstBlock);

			log.add("�������� �������� ��� ���������: " + StringUtils.join(segments, ", "));
			log.add("���������� ��������, ������������ �� �������� � ���� �" + firstBlock + ": " + clientsCount);
			log.add("�������� ����� ����������� ������� � ������ ����� �� �����������. ��������� �������� ����� �������� ��������.");

			return String.format(SUCCESS_MESSAGE, log.getLogFileName());
		}
		catch (Exception e)
		{
			if (log != null)
				log.add(e);

			throw new BusinessException(e);
		}
		finally
		{
			if (log != null)
				log.flush();
		}
	}

	public List<Segment> getSegments()
	{
		return segments;
	}
}
