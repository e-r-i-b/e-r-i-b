package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 17:55:07
 */
public class ResidentBanksReplicaDestination extends BaseDependendReplicaDestinationBase<ResidentBank>
{
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

    ResidentBanksReplicaDestination()
    {
        super("com.rssl.phizic.business.getResidentBankBySynchKey");
    }

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	public void add(ResidentBank newValue) throws GateException
	{
		Transaction tx = null;
		try
		{
			Session session = getSession();
			tx = session.beginTransaction();
			super.add(newValue);
			addToLog(session, newValue, ChangeType.update);
			tx.commit();
		}
		catch (Exception e)
		{
			if(tx != null)
				tx.rollback();
			throw new GateException(e);
		}

	}

	@Override
	public void remove(ResidentBank oldValue) throws GateException
	{
		Transaction tx = null;
		try
		{
			Session session = getSession();
			tx = session.beginTransaction();
			super.remove(oldValue);
			addToLog(session, oldValue, ChangeType.delete);
			tx.commit();
		}
		catch (Exception e)
		{
			if(tx != null)
				tx.rollback();
			throw new GateException(e);
		}
	}

	@Override
	public void update(ResidentBank oldValue, ResidentBank newValue) throws GateException
	{
		Transaction tx = null;
		try
		{
			Session session = getSession();
			tx = session.beginTransaction();
			super.update(oldValue, newValue);
			addToLog(session,oldValue, ChangeType.update);
			tx.commit();
		}
		catch (Exception e)
		{
			if(tx != null)
				tx.rollback();
			throw new GateException(e);
		}
	}

	private <T> void addToLog(Session session, MultiBlockDictionaryRecord record, ChangeType changeType)
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, record, changeType);
	}
}
