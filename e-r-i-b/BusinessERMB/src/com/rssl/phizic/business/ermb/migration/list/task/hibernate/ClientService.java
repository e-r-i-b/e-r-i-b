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
 * Сервис для работы с основной базой миграции
 * @author Puzikov
 * @ created 24.12.13
 * @ $Author$
 * @ $Revision$
 */

public class ClientService
{
	/**
	 * Пометить всех клиентов, сегментированных в данные сегменты, к миграции в блоке
	 * @param segments сегменты, по которым проводится миграция
	 * @param block номер блока
	 * @return количество клиентов, помеченных к миграции в этом сегменте
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
	 * Пометить блок клиента по id
	 * @param id клиента
	 * @param nextBlock следующий блок
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
	 * Найти дубль клиента в базе мигратора по фио+дул+др+тб
	 * @param client клиент, которого искать
	 * @return дубль клиента
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
	 * Найти дубль конфликтующего клиента в базе мигратора по фио+дул+др+тб
	 * @param client клиент, которого искать
	 * @return дубль клиента
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
	 * Найти данные для отчета сотрудников call центра
	 * @param segments список сегментов
	 * @return список данных
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
	 * Найти клиентов по сегментам
	 * @param segments сегменты
	 * @param departmentCodeList список тб+осб+всп, по которому искать
	 * @return список клиентов
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
	 * Найти список конфликтов, разрешаемых вручную, по id клиента
	 * @param clientId id в таблице csv клиентов
	 * @param onlyManually вернуть только разрешаемые вручную конфликты
	 * @return конфликты
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
	 * Сохранить конфликт
	 * @param conflict конфликт
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
	 * Удалить конфликт
	 * @param conflict конфликт
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
	 * Сохранить конфликт (обновить результат разрешения конфликта)
	 * @param conflict конфликт
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
	 * Найти телефоны клиента с неразрешенными вручную конфликтами
	 * @param client клиент
	 * @return список номеров телефона
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
	 * Найти конфликт по номеру телефона
	 * @param phone номер телефона
	 * @return конфликт
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
	 * Пометить клиента как неудачно смигрировавшего
	 * @param client клиент
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
	 * Установить статус миграции клиента
	 * @param client клиент
	 * @param status статус
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
	 * список клиентов для смс-рассылки.
	 * @param toSend - Сегменты для отправки СМС-рассылки
	 * @param notSend - Сегменты для которых не отправлять СМС-рассылку
	 * @param start - номер клиента в выборке, начиная с которого получаем клиентов
	 * @param maxsize - максимальное количество клиентов
	 * @return список клиентов для смс-рассылки.
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
	 * количество клиентов для смс-рассылки.
	 * @param toSend - Сегменты для отправки СМС-рассылки
	 * @param notSend - Сегменты для которых не отправлять СМС-рассылку
	 * @return список клиентов для смс-рассылки.
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
	 * Найти id миграций, совершенных после даты
	 * @param rollbackDate дата, после которой нужно откатить все миграции
	 * @return результаты миграции
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
	 * Сохранить в таблицу миграций
	 * @param info информация о миграции
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
	 * Удалить данные по миграции после отката
	 * @param migrationInfo данные по миграции
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
	 * Отметить информацию об ошибке отката миграции
	 * @param migrationInfo данные по миграции
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
