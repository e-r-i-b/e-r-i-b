package com.rssl.phizic.business.ermb.migration.list.task.hibernate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.MigrationStatus;
import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.*;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * ������ ��� ������ � �������� ����� ��������
 * @author Puzikov
 * @ created 24.12.13
 * @ $Author$
 * @ $Revision$
 */

public class ClientService
{
	/**
	 * �������� ���� ��������, ���������������� � ������ ��������, � �������� � �����
	 * @param segments ��������, �� ������� ���������� ��������
	 * @param block ����� �����
	 * @return ���������� ��������, ���������� � �������� � ���� ��������
	 */
	public int markClientsBlockBySegments(final List<Segment> segments, final Long block) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Integer>()
			{

				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.Client.markNonMigratedClientsBlockBySegments");
					String[] segmentNames = new String[segments.size()];
					for (int i=0; i<segments.size(); i++)
						segmentNames[i] = segments.get(i).getValue();
					query.setParameterList("segments", segmentNames);
					query.setParameter("block", block);
					return query.executeUpdate();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ���� ������� �� id
	 * @param id �������
	 * @param nextBlock ��������� ����
	 */
	public void markClientBlockById(final Long id, final Long nextBlock) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.Client.markClientBlockById");
					query.setParameter("id", id);
					query.setParameter("nextBlock", nextBlock, Hibernate.LONG);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ����� ������� � ���� ��������� �� ���+���+��+��
	 * @param client ������, �������� ������
	 * @return ����� �������
	 */
	public Client findByFioDulDrTb(final Client client) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Client>()
			{
				@SuppressWarnings("JpaQueryApiInspection")
				public Client run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.Client.findByFioDulDrTb");
					query.setParameter("fio", client.getNormalizedName());
					query.setParameter("tb", client.getTb());
					query.setParameter("document", client.getDocument().replace(" ", ""));
					query.setParameter("birthDay", client.getBirthday());

					return (Client) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ����� �������������� ������� � ���� ��������� �� ���+���+��+��
	 * @param client ������, �������� ������
	 * @return ����� �������
	 */
	public ConflictedClient findByFioDulDrTb(final ConflictedClient client) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<ConflictedClient>()
			{
				@SuppressWarnings("JpaQueryApiInspection")
				public ConflictedClient run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.ConflictedClient.findByFioDulDrTb");
					query.setParameter("fio", client.getNormalizedName());
					query.setParameter("tb", client.getTb());
					query.setParameter("document", client.getDocument().replace(" ", ""));
					query.setParameter("birthDay", client.getBirthday());

					return (ConflictedClient) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ������ ��� ������ ����������� call ������
	 * @param segments ������ ���������
	 * @return ������ ������
	 */
	public List findCallCentreData(final Segment[] segments) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<List>()
			{
				public List run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.findCallCentreData");
					String[] segmentNames = new String[segments.length];
					int i = 0;
					for (Segment segment : segments)
						segmentNames[i++] = segment.getValue();
					query.setParameterList("segments", segmentNames);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}


	/**
	 * ����� �������� �� ���������
	 * @param segments ��������
	 * @param departmentCodeList ������ ��+���+���, �� �������� ������
	 * @return ������ ��������
	 */
	public List<Client> findClientsBySegments(final Segment[] segments, final List<String> departmentCodeList) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<List<Client>>()
			{
				public List<Client> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.Client.findClientsBySegments");
					String[] segmentNames = new String[segments.length];
					int i = 0;
					for (Segment segment : segments)
						segmentNames[i++] = segment.getValue();
					query.setParameterList("segments", segmentNames);
					query.setParameterList("departmentCodeList", departmentCodeList);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ������ ����������, ����������� �������, �� id �������
	 * @param clientId id � ������� csv ��������
	 * @param onlyManually ������� ������ ����������� ������� ���������
	 * @return ���������
	 * @throws BusinessException
	 */
	public List<Conflict> findConflictsByClient(final Long clientId, final boolean onlyManually) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<List<Conflict>>()
			{
				public List<Conflict> run(Session session) throws Exception
				{
					Query query;
					if (onlyManually)
						query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Conflict.findManuallyByCsvClientId");
					else
						query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Conflict.findByCsvClientId");

					query.setParameter("clientId", clientId);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ��������
	 * @param conflict ��������
	 */
	public void saveOrUpdateConflict(final Conflict conflict) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.saveOrUpdate(conflict);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ��������
	 * @param conflict ��������
	 */
	public void deleteConflict(final Conflict conflict) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(conflict);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� �������� (�������� ��������� ���������� ���������)
	 * @param conflict ��������
	 */
	public void setConflictDecision(final Conflict conflict) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
			{

				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Conflict.setDecision");
					query.setParameter("phone", conflict.getPhone());
					query.setParameter("status", conflict.getStatus().name());
					query.setParameter("employeeInfo", conflict.getEmployeeInfo());
					query.setParameter("ownerId", conflict.getOwnerId(), Hibernate.LONG);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� �������� ������� � �������������� ������� �����������
	 * @param client ������
	 * @return ������ ������� ��������
	 */
	public List<String> findUnresolvedPhones(final Client client) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Conflict.findUnresolvedPhonesByClient");
					query.setParameter("clientId", client.getId());
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� �������� �� ������ ��������
	 * @param phone ����� ��������
	 * @return ��������
	 */
	public Conflict findConflictByPhone(final Phone phone) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Conflict>()
			{
				public Conflict run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Conflict.findByPhone");
					query.setParameter("phone", phone.getPhoneNumber());

					return (Conflict)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������� ��� �������� ���������������
	 * @param client ������
	 */
	public void setMigrationError(final Client client) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client.setMigrationErrorByClientId");
					query.setParameter("clientId", client.getId());
					query.executeUpdate();

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������ �������� �������
	 * @param client ������
	 * @param status ������
	 */
	public void setMigrationStatus(final Client client, final MigrationStatus status) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client.updateStatusByClientId");
					query.setParameter("clientId", client.getId());
					query.setParameter("status", status.name());
					query.executeUpdate();

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������ �������� ��� ���-��������.
	 * @param toSend - �������� ��� �������� ���-��������
	 * @param notSend - �������� ��� ������� �� ���������� ���-��������
	 * @param start - ����� ������� � �������, ������� � �������� �������� ��������
	 * @param maxsize - ������������ ���������� ��������
	 * @return ������ �������� ��� ���-��������.
	 */
	public List<Client> findClientsBySegmentsForSMS(final List<Segment> toSend, final List<Segment> notSend, final int start, final int maxsize) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<List<Client>>()
			{

				public List<Client> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.Client.findClientsBySegmentsForSMS");
					String[] segmentsToSend = new String[toSend.size()];
					String[] segmentsNotSend = new String[notSend.size()];
					for (int i=0; i<toSend.size(); i++)
						segmentsToSend[i] = toSend.get(i).getValue();
					for (int i=0; i<notSend.size(); i++)
						segmentsNotSend[i] = notSend.get(i).getValue();
					query.setParameterList("toSend", segmentsToSend);
					query.setParameterList("notSend", segmentsNotSend);
					query.setFirstResult(start);
					query.setMaxResults(maxsize);

					return (List<Client>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� �������� ��� ���-��������.
	 * @param toSend - �������� ��� �������� ���-��������
	 * @param notSend - �������� ��� ������� �� ���������� ���-��������
	 * @return ������ �������� ��� ���-��������.
	 */
	public int countClientsBySegmentsForSMS(final String[] toSend, final String[] notSend) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Integer>()
			{

				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.Client.countClientsBySegmentsForSMS");
					query.setParameterList("toSend", toSend);
					if (notSend.length==0)
						query.setParameterList("notSend", new String[]{"null"});
					else
						query.setParameterList("notSend", notSend);

					return (Integer) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� id ��������, ����������� ����� ����
	 * @param rollbackDate ����, ����� ������� ����� �������� ��� ��������
	 * @return ���������� ��������
	 * @throws BusinessException
	 */
	public List<MigrationInfo> findMigrationIdsAfterDate(final Calendar rollbackDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<List<MigrationInfo>>()
			{
				public List<MigrationInfo> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationInfo.findMigrationInfoAfterDate");
					query.setParameter("date", rollbackDate);

					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� � ������� ��������
	 * @param info ���������� � ��������
	 * @throws BusinessException
	 */
	public void saveMigrationInfo(final MigrationInfo info) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.saveOrUpdate(info);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ������ �� �������� ����� ������
	 * @param migrationInfo ������ �� ��������
	 */
	public void deleteMigrationInfo(final MigrationInfo migrationInfo) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(migrationInfo);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ���������� �� ������ ������ ��������
	 * @param migrationInfo ������ �� ��������
	 */
	public void setRollbackError(final MigrationInfo migrationInfo) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationInfo.setMigrationError");
					query.setParameter("infoId", migrationInfo.getId());
					query.executeUpdate();

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
