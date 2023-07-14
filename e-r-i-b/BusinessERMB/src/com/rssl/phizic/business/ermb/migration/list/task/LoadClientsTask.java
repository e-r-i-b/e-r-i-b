package com.rssl.phizic.business.ermb.migration.list.task;

import com.csvreader.CsvReader;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.migration.list.MigrationStatus;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.*;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ArchiveClientService;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.business.ermb.migration.list.task.segmentation.Segmentator;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import sun.nio.cs.StandardCharsets;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CancellationException;

import static com.rssl.phizic.business.ermb.migration.list.task.TaskType.LOAD_CLIENTS;
import static com.rssl.phizic.business.ermb.migration.list.task.TaskType.START_MIGRATION;

/**
 * ������-�������� ��� �������� ������ ��������
 * ��������� csv-�����:
	 �	�������;
	 �	���;
	 �	��������;
	 �	����� ���������, ��������������� ��������
	 �	����� ���������, ��������������� ��������
	 �	���� �������� ������� (� ������� ��.��.����);
	 �	��� ���������������� �����.
 *
 * @author Puzikov
 * @ created 17.12.13
 * @ $Author$
 * @ $Revision$
 */

public class LoadClientsTask extends LoggedTask
{
	private static final char DELIMITER = ',';
	private CsvReader csvReader;
	private final LoadClientsStats stats;
	private final String tbCurrent;

	private ClientService service = new ClientService();
	private ArchiveClientService archiveService = new ArchiveClientService();

	private String filePath;
	private int nonActivePeriod;

	/**
	 * ctor
	 * @param filePath ���� �� ������������ �������� � csv-������
	 * @param nonActivePeriod ������ ���������� ������������ ��������
	 */
	public LoadClientsTask(String filePath, int nonActivePeriod)
	{
		this.filePath = filePath;
		this.nonActivePeriod = nonActivePeriod;

		if (!new File(filePath).exists())
			throw new IllegalArgumentException("���� ��� �������� �� ������: " + filePath);

		this.tbCurrent = ConfigFactory.getConfig(ErmbListMigrationConfig.class).getTb();
		if (tbCurrent == null)
			throw new ConfigurationException("������: �� �������� �� ����� ���� null");
		this.stats = new LoadClientsStats();

		setStatus("������ ����������������");
	}

	public TaskType getType()
	{
		return LOAD_CLIENTS;
	}

	public EnumSet<TaskType> getIllegalTasks()
	{
		return EnumSet.of(START_MIGRATION);
	}

	@Override
	protected void start() throws Exception
	{
		loadFile();
	}

	@Override
	protected void stop()
	{
		if (csvReader != null)
			csvReader.close();

		toLog(stats.getInfo());
		super.stop();
	}

	private void loadFile() throws Exception
	{
		setStatus("����������� �������� ������ �������� �� csv");

		final Segmentator segmentator = new Segmentator();

		//noinspection OverlyComplexAnonymousInnerClass
		HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				csvReader = new CsvReader(filePath, DELIMITER,
						new StandardCharsets().charsetForName("windows-1251"));
				while (csvReader.readRecord())
				{
					try
					{
						stats.read();
						if (isInterrupted())
							throw new CancellationException();

						Client client = parseRowClient(csvReader);
						Client existingClient = service.findByFioDulDrTb(client);

						if (existingClient != null)
						{
							if (existingClient.getStatus() == MigrationStatus.MIGRATED)
							{
								toLog("����������� �� csv ������ ��� ���������� id=" + existingClient.getId());
								continue;
							}
							else
							{
								toLog("WARN ����������� �� csv ������ ��� ���� � �� ��������� id=" + existingClient.getId() + " � ������� " +
										existingClient.getStatus() + ". ������ ����� ���������.");
							}
						}

						loadAdditionalData(client);
						segmentator.exec(client);
						saveOrUpdateConflicts(client);

						if (existingClient != null)
							session.delete(existingClient);
						session.saveOrUpdate(client);
						session.flush();
						stats.ok(client);
					}
					catch (BusinessLogicException e)
					{
						toLog(e.getMessage() + " ����� ������� � ������ = " + (csvReader.getCurrentRecord() + 1));
					}
					catch (BusinessException e)
					{
						toLog("������ ��� �������� ������� �� ������. ����� � ������: " + (csvReader.getCurrentRecord() + 1));
						toLog(e);
					}
					finally
					{
						flushLog();
					}
				}

				return null;
			}
		});
	}

	private Client parseRowClient(CsvReader csvReader) throws BusinessException, IOException, BusinessLogicException
	{
		Client result = new Client();

		Calendar birthday = Calendar.getInstance();
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			birthday.setTime(format.parse(csvReader.get(5)));

		}
		catch (ParseException e)
		{
			throw new BusinessLogicException("���������� ��������� ������ csv-�����", e);
		}

		String tb = csvReader.get(6);
		if (!tbCurrent.equals(tb))
			throw new BusinessLogicException("�������� �������� ��� �������� " + tbCurrent + ". � csv ������ ������ � tb=" + tb);

		result.setLastName(csvReader.get(0));
		result.setFirstName(csvReader.get(1));
		result.setMiddleName(csvReader.get(2));
		result.setDocument(csvReader.get(3) + csvReader.get(4));
		result.setBirthday(birthday);
		result.setTb(tb);

		return result;
	}

	private void loadAdditionalData(Client client) throws BusinessException, BusinessLogicException
	{
		archiveService.loadAdditionalData(client, nonActivePeriod);

		if (client.getPhones().isEmpty())
			throw new BusinessLogicException("��� ������������ ������� �� ������� ��������� � �������� �����");

		archiveService.loadConflictedClients(client.getPhones(), nonActivePeriod);

		for (Phone phone : client.getPhones())
		{
			setPhoneFlags(phone);
		}

		setBelongClientRegistration(client);
	}

	private void setBelongClientRegistration(Client client) throws BusinessException
	{
		List<String> belongClientRegistration = archiveService.findBelongClientRegistration(client);
		for (Phone phone : client.getPhones())
			if (belongClientRegistration.contains(phone.getPhoneNumber()))
				phone.setBelongClientRegistration(true);
	}

	private void saveOrUpdateConflicts(Client client) throws BusinessException
	{
		for (Phone phone : client.getPhones())
		{
			if (!phone.getUnique())
				saveOrUpdateConflictsByPhone(client, phone);
		}
	}

	private void setPhoneFlags(Phone phone) throws BusinessLogicException
	{
		Set<ConflictedClient> conflictedClients = phone.getConflictedClients();
		if (CollectionUtils.isEmpty(conflictedClients))
			throw new BusinessLogicException("�� ������� ���������� ��� �������� � �������� ��. " + phone.getPhoneNumber());

		phone.setUnique(conflictedClients.size() == 1);

		int smsActiveClientsCount = 0;
		for (ConflictedClient conflictedClient : conflictedClients)
		{
			if (conflictedClient.getVipOrMvs())
				phone.setVipOrMbc(true);

			if (conflictedClient.isSmsActive())
				smsActiveClientsCount++;
		}

		if (smsActiveClientsCount == 0)
			phone.setSmsActivity(SmsActivity.NONE_PHONE);
		else if (smsActiveClientsCount == 1)
			phone.setSmsActivity(SmsActivity.ONE_PHONE);
		else
			phone.setSmsActivity(SmsActivity.SOME_PHONE);
	}

	private void saveOrUpdateConflictsByPhone(Client client, Phone phone) throws BusinessException
	{
		Set<ConflictedClient> conflictedClients = phone.getConflictedClients();

		Conflict conflict = service.findConflictByPhone(phone);
		Set<ConflictedClient> existingClients = new HashSet<ConflictedClient>();

		//���� ����������� �� ���������� = ��� ��
		if (conflict != null)
		{
			existingClients = conflict.getClients();
			if (existingClients.equals(conflictedClients))
			{
				//���� �������� �� ��������� ������, ��������� � ������� ���������� ������� ������� ��� ��������
				if (phone.isUnresolvable() && conflict.getStatus() != ConflictStatus.RESOLVED_TO_DELETE)
				{
					conflict.setStatus(ConflictStatus.RESOLVED_TO_DELETE);
					service.setConflictDecision(conflict);
				}

				return;
			}
		}

		stats.newConflict();
		if (conflict == null)
		{
			conflict = new Conflict();
			conflict.setPhone(phone.getPhoneNumber());
			//�������� ��� ������������
			for (ConflictedClient oldClient : existingClients)
			{
				if (conflictedClients.contains(oldClient))
					conflict.getClients().add(oldClient);
			}
		}

		conflict.setClients(new HashSet<ConflictedClient>());
		//�������� �����
		for (ConflictedClient newClient : conflictedClients)
		{
			ConflictedClient existingClient = service.findByFioDulDrTb(newClient);
			if (existingClient != null)
				conflict.getClients().add(existingClient);
			else
				conflict.getClients().add(newClient);
		}

		if (phone.isUnresolvable())
			//���� �������� �� ��������� ������, ��������� � ������� ���������� ������� ������� ��� ��������
			conflict.setStatus(ConflictStatus.RESOLVED_TO_DELETE);
		else
			conflict.setStatus(ConflictStatus.UNRESOLVED);

		conflict.setManually(phone.isManually());

		service.saveOrUpdateConflict(conflict);

		if (client.getSegment_3_2_1() && !phone.isUnresolvable())
		{
			resolvePhoneConflict(conflict, conflictedClients);
		}

		if (conflict.getStatus() != ConflictStatus.UNRESOLVED)
			stats.resolveConflict();
	}

	private void resolvePhoneConflict(Conflict conflict, Set<ConflictedClient> conflictedClients) throws BusinessException
	{
		ConflictedClient decisionClient = null;
		for (ConflictedClient conflictedClient : conflictedClients)
		{
			if (conflictedClient.isSmsActive())
			{
				decisionClient = conflictedClient;
				break;
			}
		}

		if (decisionClient == null)
		{
			toLog("������ ��������������� ���������� ���������. �� ������ ���-�������� ������ ��� �������� 3_2_1");
			return;
		}

		for (ConflictedClient client : conflict.getClients())
		{
			if (decisionClient.equals(client))
			{
				conflict.setOwnerId(client.getId());
				conflict.setStatus(ConflictStatus.RESOLVED_TO_OWNER);
				service.setConflictDecision(conflict);
				break;
			}
		}
	}
}
