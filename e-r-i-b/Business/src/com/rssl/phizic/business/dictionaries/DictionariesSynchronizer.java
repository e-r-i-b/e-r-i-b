package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.dictionaries.OneWayReplicator;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.gate.GateFactory;

import java.util.Comparator;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 03.07.2006
 * @ $Author: jatsky $
 * @ $Revision: 77656 $
 */

public class DictionariesSynchronizer
{
    private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
    private List<String> errors;
	private List<DictionaryDescriptor> dictionaryDescriptors = ConfigFactory.getConfig(DictionaryConfig.class).getDescriptors();

	private GateFactory factory;

	public DictionariesSynchronizer(GateFactory factory)
	{
		this.factory = factory;
	}

	public void synchronizeAll () throws BusinessException, BusinessLogicException
	{
		for (DictionaryDescriptor descriptor : dictionaryDescriptors)
		{
            log.info("Начата синхронизация справочника " + descriptor.getDescription());
            synchronize(descriptor);
            log.info("Завершена синхронизация справочника " + descriptor.getDescription());
        }
	}

	public void synchronize ( DictionaryDescriptor descriptor ) throws BusinessException, BusinessLogicException
	{
		replicate(descriptor.getSource(),
				  descriptor.getDestination(),
				  descriptor.getComparator());
	}

	public void synchronizeFromTemporary(DictionaryDescriptor descriptor) throws BusinessException, BusinessLogicException
	{
		replicate(descriptor.getTemporarySource(),
				  descriptor.getDestinationDictionary(),
				  descriptor.getComparator());
	}

	private void replicate(ReplicaSource source, ReplicaDestination destination, Comparator comparator) throws BusinessException, BusinessLogicException
	{
		try
		{
			source.initialize(factory);
			destination.initialize(factory);

			OneWayReplicator replicator = new OneWayReplicator(source, destination, comparator);
			replicator.replicate();
			setErrors(destination.getErrors());
		}
		catch (GateException e)
		{
			throw new BusinessException("source: " +
					source.getClass().getName() + " destination: "+destination.getClass().getName()+
					" comparator: " + comparator.getClass().getName(), e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private void setErrors(List<String> err)
	{
		errors = err;
	}
	public List<String> getErrors()
	{
		return errors;
	}
}
