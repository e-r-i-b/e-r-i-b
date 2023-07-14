package com.rssl.phizic.business.bannedAccount;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import org.hibernate.Session;

/**
 * @author komarov
 * @ created 20.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceBannedAccountService
{
	protected static final SimpleService simpleService = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * Получение маски счета(БИК) по id
	 * @param id - идентификатор записи
	 * @return найденая по id маска счета
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public BannedAccount getById(Long id, String instance) throws BusinessException
	{
		return simpleService.findById(BannedAccount.class,id, instance);
	}

	/**
	 * Добавление/изменение маски счета для запрета пееревода
	 * @param bannedAccount - сущность
	 * @throws BusinessException
	 */
	public void addOrUpdate(final BannedAccount bannedAccount, final String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instance);
			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.addOrUpdate(bannedAccount, instance);
					addToLog(bannedAccount, ChangeType.update);
					return null;
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Удаление записи маски счета(БИК) для запретов для переводов
	 * @param bannedAccount - сущность
	 * @throws BusinessException
	 */
	public void remove(final BannedAccount bannedAccount, final String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instance);
			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.remove(bannedAccount, instance);
					addToLog(bannedAccount, ChangeType.delete);
					return null;
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	private void addToLog(MultiBlockDictionaryRecord record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(record, changeType);
	}
}
