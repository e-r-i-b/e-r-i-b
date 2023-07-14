package com.rssl.phizic.business.bannedAccount;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author: vagin
 * @ created: 01.02.2012
 * @ $Author$
 * @ $Revision$
 * Сервис для работы с масками счетов для запрета переводов
 */
public class BannedAccountService extends MultiInstanceBannedAccountService
{

	/**
	 * Получение маски счета(БИК) по id
	 * @param id - идентификатор записи
	 * @return найденая по id маска счета
	 * @throws BusinessException
	 */
	public BannedAccount getById(Long id) throws BusinessException
	{
		return super.getById(id, null);
	}

	/**
	 * Добавление/изменение маски счета для запрета пееревода
	 * @param bannedAccount - сущность
	 * @throws BusinessException
	 */
	public void addOrUpdate(BannedAccount bannedAccount) throws BusinessException
	{
		super.addOrUpdate(bannedAccount, null);
	}

	/**
	 * Удаление записи маски счета(БИК) для запретов для переводов
	 * @param bannedAccount - сущность
	 * @throws BusinessException
	 */
	public void remove(BannedAccount bannedAccount) throws BusinessException
	{
		super.remove(bannedAccount, null);
	}

	/**
	 * Получения маски из списка в БД запрещенных счетов
	 * @param number - номер по которому ищется маска
	 * @param banType - тип запрета(PHIZ,JUR,ALL)
	 * @return список подходящих масок
	 * @throws BusinessException
	 */
	public List<BannedAccount> getMaskByNumber(final String number, final AccountBanType banType) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<List<BannedAccount>>()
		    {
		        public List<BannedAccount> run(Session session) throws Exception
		        {
			        String queryName = "com.rssl.phizic.business.bannedAccount.BannedAccountService.getMaskByNumber";
		            Query query = session.getNamedQuery(queryName);
		            query.setParameter("number", number);
			        query.setParameter("banType",banType);
		            return (List<BannedAccount>) query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}

	}
}
