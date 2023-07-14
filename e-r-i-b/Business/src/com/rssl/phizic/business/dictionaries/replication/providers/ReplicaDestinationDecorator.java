package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.phizic.business.dictionaries.ReplicaDestinationBase;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.operations.background.ReplicationTaskResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.event.EventSource;

import java.util.Iterator;

/**
 * декоратор для репликации поставщиков услуг, выполняет все действия в отдельных транзакциях
 * @author niculichev
 * @ created 03.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class ReplicaDestinationDecorator extends ReplicaDestinationBase
{
	private static final String ERROR_UPDATE = "При обновлении поставщика %s произошла ошибка\n";
	private static final String ERROR_DELETE = "При удалении поставщика %s произошла ошибка\n";
	private static final String ERROR_ADD = "При добавлении поставщика %s произошла ошибка\n";


	private ServiceProviderReplicaDestination original;
	private ReplicationTaskResult result;

	public ReplicaDestinationDecorator(ServiceProviderReplicaDestination original, ReplicationTaskResult result)
	{
		this.original = original;
		this.result = result;
	}

	public void initialize(GateFactory factory) throws GateException
	{
		original.initialize(factory);
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		return original.iterator();
	}

	public void add(DictionaryRecord newValue) throws GateException
	{
		Session session = original.getSession();
		Transaction transaction = null;
		try
		{
			transaction = session.beginTransaction();
			original.add(newValue);
			transaction.commit();
		}
		catch(Exception e)
		{
			// откатываем как было
		    transaction.rollback();
			BillingServiceProvider provider = ((ServiceProviderForReplicationWrapper) newValue).getProvider();
		    // удаляем объект из кеэша сессии
		    session.evict(newValue);
		    session.evict(provider);
		    // удаляем из очереди действия, которые должны были выполнится
		    ((EventSource)session).getActionQueue().clear();
			// уменьшаем счетчик для статистики
			result.decreaseDestinationInseredRecords();
			// добавляем ошибку
			result.addToReport(String.format(ERROR_ADD, provider.getCode()));
		    log.error(String.format(ERROR_ADD, newValue.getSynchKey()), e);
		}
	}

    public void remove(DictionaryRecord oldValue) throws GateException
    {
	    Session session = original.getSession();
	    Transaction transaction = null;
	    try
	    {
			transaction = session.beginTransaction();
			original.remove(oldValue);
			transaction.commit();
	    }
	    catch(Exception e)
	    {
		    // откатываем как было
		    transaction.rollback();
		    BillingServiceProvider provider = ((ServiceProviderForReplicationWrapper) oldValue).getProvider();
		    // удаляем объект из кеэша сессии
		    session.evict(oldValue);
		    session.evict(provider);
		    // удаляем из очереди действия, которые должны были выполнится
		    ((EventSource)session).getActionQueue().clear();
		    // уменьшаем счетчик для статистики
			result.decreaseDestinationDeletedRecords();
		    // добавляем ошибку
		    result.addToReport(String.format(ERROR_DELETE, provider.getCode()));
		    log.error(String.format(ERROR_DELETE, oldValue.getSynchKey()), e);
	    }
    }

    public void update(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException
    {
	    Session session = original.getSession();
	    Transaction transaction = null;
	    try
	    {
			transaction = session.beginTransaction();
			original.update(oldValue, newValue);
			transaction.commit();
	    }
	    catch(Exception e)
	    {
		    // откатываем как было
		    transaction.rollback();
		    BillingServiceProvider provider = ((ServiceProviderForReplicationWrapper) oldValue).getProvider();
		    // удаляем объект из кеэша сессии
		    session.evict(oldValue);
		    session.evict(provider);
		    // удаляем из очереди действия, которые должны были выполнится
		    ((EventSource)session).getActionQueue().clear();
		    // уменьшаем счетчик для статистики
			result.decreaseDestinationUpdatedRecords();
		    // добавляем ошибку
		    result.addToReport(String.format(ERROR_UPDATE, provider.getCode()));
		    log.error(String.format(ERROR_UPDATE, oldValue.getSynchKey()), e);
	    }
    }

	public void close()
	{
		original.close();
	}
}
