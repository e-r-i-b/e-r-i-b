package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.ima.IMAProduct;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Цель репликации справочника ОМС
 * @author Pankin
 * @ created 18.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAProductReplicaDestinations extends QueryReplicaDestinationBase<IMAProduct>
{
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	protected IMAProductReplicaDestinations()
	{
		super("com.rssl.phizic.business.ima.IMAProduct.getAll");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public void remove(IMAProduct oldValue) throws GateException
	{
		// не удаляем
	}

	@Override
	public void add(IMAProduct newValue) throws GateException
	{
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try
		{
			super.add(newValue);
			addToLog(session,newValue,ChangeType.update);
			transaction.commit();
		}
		catch (Exception e)
		{
			transaction.rollback();
			throw new GateException(e);
		}
	}

	@Override
	public void update(IMAProduct oldValue, IMAProduct newValue) throws GateException
	{
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try
		{
			super.update(oldValue, newValue);
			addToLog(session,oldValue,ChangeType.update);
			transaction.commit();
		}
		catch (Exception e)
		{
			transaction.rollback();
			throw new GateException(e);
		}
	}

	private void addToLog(Session session, MultiBlockDictionaryRecord record, ChangeType changeType)
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, record, changeType);
	}

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
