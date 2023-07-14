package com.rssl.phizic.business.connectUdbo;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * ������ ��� ������ � ��������� ��������� �� ����������� ���� � ������������ ������
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceUDBOClaimRulesService
{
	protected static final SimpleService simpleService = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	
	/**
	 * @return ����� ����������� ������� ���������
	 * @throws BusinessException
	 */
	public static String findActiveRulesText() throws BusinessException
	{
		try
		{
			UDBOClaimRules rules = HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<UDBOClaimRules>()
			{
				public UDBOClaimRules run(Session session) throws Exception
				{
					return (UDBOClaimRules) session.getNamedQuery("com.rssl.phizic.business.connectUdbo.UDBOClaimRules.getActiveRules")
							.uniqueResult();
				}
			});
			return rules == null ? "" : rules.getRulesText();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return ����� ������� ��� ��������� � ����������� ����
	 * @throws BusinessException
	 */
	public static String getTermTextForConnectionUdbo()
	{
		return ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).getTermTextForConnectionUdbo();
	}

	private static String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	/**
	 * �������� ������ �������
	 * @param instanceName - ������� ��
	 * @return ������ �������
	 */
	public List<UDBOClaimRules> getRulesList(String instanceName) throws BusinessException
	{
		try
		{
			List<Object[]> result = HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Object[]>>()
			{
				public List<Object[]> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.connectUdbo.UDBOClaimRules.list")
							.list();
				}
			});
			return setRulesStatuses(result);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ������� �� id
	 * @param id - �������������
	 * @param instanceName - ������� ��
	 * @return �������
	 * @throws BusinessException
	 */
	public UDBOClaimRules findById(final Long id, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<UDBOClaimRules>()
			{
				public UDBOClaimRules run(Session session) throws Exception
				{
					return (UDBOClaimRules) session.getNamedQuery("com.rssl.phizic.business.connectUdbo.UDBOClaimRules.findById")
							.setParameter("id", id)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������� �������
	 * @param list - ������ �������
	 * @return ������ � �������������� ���������
	 */
	private List<UDBOClaimRules> setRulesStatuses(List<Object[]> list)
	{
		boolean activeRulesFound = false;
		List<UDBOClaimRules> resultList = new ArrayList<UDBOClaimRules>();
		for (int i=list.size()-1;i>=0;i--)
		{
			UDBOClaimRules rules = new UDBOClaimRules();
			rules.setId((Long)list.get(i)[0]);
			rules.setUuid((String)list.get(i)[1]);
			rules.setStartDate((Calendar)list.get(i)[2]);

			if (rules.getStartDate().after(Calendar.getInstance()))
				rules.setStatus(UDBOClaimRulesStatus.ENTERED);
			else
			{
				if (!activeRulesFound)
				{
					rules.setStatus(UDBOClaimRulesStatus.ACTIVE);
					activeRulesFound = true;
				}
				else
					rules.setStatus(UDBOClaimRulesStatus.ARCHIVAL);
			}
			resultList.add(0, rules);
		}
		return resultList;
	}

	/**
	 * ��������� �������� �������
	 * @param claimRules - �������
	 * @param instanceName - ��� ��������
	 * @throws BusinessException
	 */
	public void add(final UDBOClaimRules claimRules, final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.add(claimRules, instanceName);
					addToLog(claimRules, ChangeType.update);
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
	 * ��������� �������� �������
	 * @param claimRules - �������
	 * @param instanceName - ��� ��������
	 * @throws BusinessException
	 */
	public void update(final UDBOClaimRules claimRules, final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.update(claimRules, instanceName);
					addToLog(claimRules, ChangeType.update);
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
	 * �������� �������
	 * @param claimRules - �������
	 * @param instance - ��� ��������
	 * @throws BusinessException
	 */
	public void remove(final UDBOClaimRules claimRules, final String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(claimRules);
					addToLog(session, claimRules, ChangeType.delete);
					session.flush();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void addToLog(MultiBlockDictionaryRecord record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(record, changeType);
	}

	private void addToLog(Session session, MultiBlockDictionaryRecord record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, record, changeType);
	}

	/**
	 * �������� ������ ��� ������� � ������� "�����"
	 * @param instanceName - ������� ��
	 * @return ������ ���
	 */
	public List<Calendar> getEnteredRulesDateList(String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Calendar>>()
			{
				public List<Calendar> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.connectUdbo.UDBOClaimRules.enteredRulesDatelist")
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
