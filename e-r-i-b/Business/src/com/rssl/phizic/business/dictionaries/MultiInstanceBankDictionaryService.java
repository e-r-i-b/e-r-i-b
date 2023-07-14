package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * @author komarov
 * @ created 30.01.2014
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceBankDictionaryService
{
	protected static final SimpleService simpleService = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * Обновление банка.
	 * Поскольку synchKey = БИК и synchKey является идентифицирующим полем, то в случае изменения БИКа старая запись удаляется и добавляется новая.
	 * @param bank банк
	 * @param instance инстанс
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void updateResidentBank(final ResidentBank bank, final String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{

					if(bank.getSynchKey().equals(bank.getBIC()))
					{
						simpleService.update(bank, instance);
						addToLog(bank, ChangeType.update);
					}
					else
					{
						simpleService.remove(bank, instance);
						bank.setSynchKey(bank.getBIC());
						simpleService.add(bank, instance);
						addToLog(bank, ChangeType.update);
					}
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
	 * Добавление банка
	 * @param bank банк
	 * @param instance инстанс
	 * @throws BusinessException
	 */
	public void addResidentBank(final ResidentBank bank, final String instance) throws BusinessException
	{
		try {
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					bank.setSynchKey(bank.getBIC());
					simpleService.add(bank, instance);
					addToLog(bank, ChangeType.update);
					return null;
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	public void remove(final ResidentBank bank, final String instance) throws Exception
	{
		HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				session.delete(bank);
				addToLog(session, bank, ChangeType.delete);
				session.flush();
				return null;
			}
		});
	}

	/**
	 *
	 * @param key synchKey банка
	 * @param instance инстанс
	 * @return  соответствующий банк из базы
	 * @throws BusinessException
	 */
	public ResidentBank findBySynchKey(final Comparable key, String instance) throws BusinessException
	{
		List<ResidentBank> banks;

		try {
			banks = HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<ResidentBank>>()
			{
				public List<ResidentBank> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.getOneResidentBankBySynchKey");
					query.setParameter("SYNCH_KEY", key);
					query.setParameter("CUR_DATE", Calendar.getInstance());
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		return banks.size() > 0 ? banks.get(0) : null;
	}


	/**
	 *
	 * @param bic БИК банка
	 * @return банк с заданным БИК
	 * @throws BusinessException
	 */
	public ResidentBank findByBIC(final String bic, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<ResidentBank>()
			{
				public ResidentBank run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.getBankByBIC");
					query.setParameter("BIC", bic);
					query.setParameter("CUR_DATE", Calendar.getInstance());
					//noinspection unchecked
					List<ResidentBank> banks = query.list();
					if (banks == null || banks.size() <= 0)
						return null;

					return banks.get(0);
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
}
