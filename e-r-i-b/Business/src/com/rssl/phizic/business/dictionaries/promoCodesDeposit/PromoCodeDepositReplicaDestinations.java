package com.rssl.phizic.business.dictionaries.promoCodesDeposit;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author Pankin
 * @ created 12.01.15
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodeDepositReplicaDestinations extends QueryReplicaDestinationBase<PromoCodeDeposit>
{
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	public PromoCodeDepositReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit.getAll");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public void remove(PromoCodeDeposit oldValue) throws GateException
	{
		// не удаляем
	}

	@Override
	public void add(PromoCodeDeposit newValue) throws GateException
	{
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try
		{
			super.add(newValue);
			addToLog(session, newValue, ChangeType.update);
			transaction.commit();
		}
		catch (Exception e)
		{
			transaction.rollback();
			throw new GateException(e);
		}
	}

	@Override
	public void update(PromoCodeDeposit oldValue, PromoCodeDeposit newValue) throws GateException
	{
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try
		{
			super.update(oldValue, newValue);
			addToLog(session, oldValue, ChangeType.update);
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
