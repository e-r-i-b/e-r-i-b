package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.dictionaries.synchronization.DictionarySynchronizer;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.SynchronizeException;
import com.rssl.phizic.business.xslt.lists.cache.event.XmlDictionaryCacheClearEvent;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.Iterator;
import java.util.Set;

/**
 * @author akrenev
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���� ������������� ������������ � ������������ ������
 */

public class DictionarySynchronizationJob extends BaseJob implements StatefulJob
{
	private static final DictionarySynchronizer synchronizer = new DictionarySynchronizer();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final String CACHE_CLEAR_ERROR_MESSAGE = "�� ������� ��������� ��������� �� ������� ���� �����������: \"%s\"";

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		if (!MultiBlockModeDictionaryHelper.isMultiBlockMode())
			return;

		try
		{
			synchronizer.synchronize();
			//�������� ��������� �� ������� ���� xml-������������
			clearCacheDictionaries();
		}
		catch (SynchronizeException e)
		{
			throw new JobExecutionException(e);
		}
	}

	/**
	 * �������� ��������� �� ������� ���� xml-������������ ����� �������������
	 */
	private void clearCacheDictionaries()
	{
		Set<Class> clearCacheDictionariesSet = synchronizer.getClearCacheDictionariesSet();
		//���� ���� ����������� �� ������� ���������� �������� ���
		if (CollectionUtils.isNotEmpty(clearCacheDictionariesSet))
		{
			Iterator<Class> clearCacheDictionariesIterator = clearCacheDictionariesSet.iterator();
			while (clearCacheDictionariesIterator.hasNext())
			{
				Class clazz =  clearCacheDictionariesIterator.next();
				//���� ������ ��� ���������� ���������
				if (clazz.equals(DepositProduct.class))
				{
					try
					{
						EventSender.getInstance().sendEvent(new XmlDictionaryCacheClearEvent(null, clazz));
					}
					catch (Exception e)
					{
						log.error(String.format(CACHE_CLEAR_ERROR_MESSAGE, clazz.getName()), e);
					}
				}
			}
			clearCacheDictionariesSet.clear();
		}
	}
}
