package com.rssl.phizic.business.dictionaries.synchronization;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.information.DictionaryInformationService;
import com.rssl.phizic.business.dictionaries.synchronization.information.SynchronizationState;
import com.rssl.phizic.business.dictionaries.synchronization.information.SynchronizationStateEntity;
import com.rssl.phizic.business.dictionaries.synchronization.information.SynchronizationStateService;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryEntityChangeInfo;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorDictionary;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ExceptionUtil;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Синхронизатор справочников
 */

public class DictionarySynchronizer
{
	private static final SynchronizationStateService synchronizationStateService = new SynchronizationStateService();

	private static final DictionaryInformationService dictionaryInformationService = new DictionaryInformationService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private DictionaryEntityChangeInfo lastSynchronizedEntity = null;
	private Set<Class> clearCacheDictionariesSet = new HashSet<Class>();

	/**
	 * синхронизация справочников
	 */
	public void synchronize() throws SynchronizeException
	{
		if (!lock())
			return;

		try
		{
			doSynchronize();
			unlock(SynchronizationState.UPDATED);
		}
		catch (SynchronizeException e)
		{
			unlock(SynchronizationState.ERROR);
			throw e;
		}
	}

	private boolean lock() throws SynchronizeException
	{
		try
		{
			return synchronizationStateService.doProcess();
		}
		catch (BusinessException e)
		{
			throw new SynchronizeException(e.getMessage(), e);
		}
	}

	private void unlock(SynchronizationState state) throws SynchronizeException
	{
		try
		{
			synchronizationStateService.doEnd(state);
		}
		catch (BusinessException e)
		{
			throw new SynchronizeException(e.getMessage(), e);
		}
	}

	private void doSynchronize() throws SynchronizeException
	{
		try
		{
			saveStartSynchronization();
			Iterator<DictionaryEntityChangeInfo> changeInfoIterator = getChanged();
			while (changeInfoIterator.hasNext())
			{
				processEntity(changeInfoIterator.next());
			}
			saveUpdatedResult();
		}
		catch (Exception e)
		{
			saveErrorResult(ExceptionUtil.printStackTrace(e));
			throw new SynchronizeException(e.getMessage(), e);
		}
	}

	private void processEntity(DictionaryEntityChangeInfo entityChangeInfo) throws SynchronizeException
	{
		try
		{
			ProcessorBase processorBase = ProcessorDictionary.getProcessor(entityChangeInfo.getDictionaryType());
			processorBase.process(entityChangeInfo);
			//Запоминаем справочники для которых необходимо обновить кеш
			clearCacheDictionariesSet.add(processorBase.getClearCacheKey());
			lastSynchronizedEntity = entityChangeInfo;
		}
		catch (SkipEntitySynchronizationException e)
		{
			log.error(e.getMessage(),e);
		}
	}

	private Iterator<DictionaryEntityChangeInfo> getChanged() throws SynchronizeException
	{
		try
		{
			SynchronizationStateEntity lastSynchronizationInfo = synchronizationStateService.getLastSynchronizationInfo();
			return dictionaryRecordChangeInfoService.getChanged(lastSynchronizationInfo);
		}
		catch (BusinessException e)
		{
			throw new SynchronizeException(e.getMessage(), e);
		}
	}

	private Long getCurrentNodeId()
	{
		return ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
	}

	private void saveStartSynchronization() throws SynchronizeException
	{
		try
		{
			dictionaryInformationService.saveStartSynchronization(getCurrentNodeId());
		}
		catch (BusinessException e)
		{
			throw new SynchronizeException(e.getMessage(), e);
		}
	}

	private void saveUpdatedResult() throws SynchronizeException
	{
		try
		{
			Calendar lastUpdateDate = null;
			if (lastSynchronizedEntity != null)
				lastUpdateDate = lastSynchronizedEntity.getUpdateDate();

			dictionaryInformationService.saveUpdatedResult(getCurrentNodeId(), lastUpdateDate);
		}
		catch (BusinessException e)
		{
			throw new SynchronizeException(e.getMessage(), e);
		}
	}

	private void saveErrorResult(String errorDetail) throws SynchronizeException
	{
		try
		{
			Calendar lastUpdateDate = null;
			if (lastSynchronizedEntity != null)
				lastUpdateDate = lastSynchronizedEntity.getUpdateDate();

			dictionaryInformationService.saveErrorResult(getCurrentNodeId(), lastUpdateDate, errorDetail);
		}
		catch (BusinessException e)
		{
			throw new SynchronizeException(e.getMessage(), e);
		}
	}

	/**
	 * Хранит справочники, для которых необходимо почистить кеш
	 * @return
	 */
	public Set<Class> getClearCacheDictionariesSet()
	{
		return clearCacheDictionariesSet;
	}
}
