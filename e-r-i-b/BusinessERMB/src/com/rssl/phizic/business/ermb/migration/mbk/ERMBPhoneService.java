package com.rssl.phizic.business.ermb.migration.mbk;

/**
 * @author Nady
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.mobilebank.ERMBPhone;
import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * Сервис для работы с таблицой ERMB_PHONES
 */
public class ERMBPhoneService
{
	private static final String PHIZ_PROXY_MBK_INSTANCE_NAME = "PhizProxyMBK";

	private static final GregorianCalendar TIME0 = new GregorianCalendar(1970, 1, 1);

	private final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();

	private List<ERMBPhone> findByPhoneNumbers(final List<PhoneNumber> phoneNumbers) throws BusinessException
	{
		if (CollectionUtils.isEmpty(phoneNumbers))
			return Collections.emptyList();

		try
		{
			return HibernateExecutor.getInstance(PHIZ_PROXY_MBK_INSTANCE_NAME).execute(new HibernateAction<List<ERMBPhone>>()
			{
				public List<ERMBPhone> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.mbk.ERMBPhoneService.findByPhoneNumbers");
					query.setParameterList("phoneNumbers", phoneNumbers);
					return (List<ERMBPhone>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Обновить или добавить телефоны
	 * @param ermbPhones - список телефонов
	 * @throws BusinessException
	 */
	public void saveOrUpdateERMBPhones(final List<ERMBPhone> ermbPhones) throws BusinessException
	{
		simpleService.addOrUpdateList(ermbPhones, PHIZ_PROXY_MBK_INSTANCE_NAME);
	}

	/**
	 * добавить или обновить телефоны
	 * @param addedPhoneStrings - список телефонов, которые используются в ЕРМБ
	 * @param removedPhoneStrings - список телефонов, которые не используются в ЕРМБ (удалены)
	 */
	public void saveOrUpdateERMBPhones(Collection<String> addedPhoneStrings, Collection<String> removedPhoneStrings) throws BusinessException
	{
		Set<PhoneNumber> addedPhones = PhoneNumber.fromString(addedPhoneStrings);
		Set<PhoneNumber> removedPhones = PhoneNumber.fromString(removedPhoneStrings);
		if (CollectionUtils.isEmpty(addedPhones) && CollectionUtils.isEmpty(removedPhones))
			return;

		List<PhoneNumber> allPhones = new ArrayList<PhoneNumber>(addedPhones);
		allPhones.addAll(removedPhones);

		List<ERMBPhone> ermbPhones = findByPhoneNumbers(allPhones);
		List<ERMBPhone> createdErmbPhones = new ArrayList<ERMBPhone>();

		Calendar now = Calendar.getInstance();

		for (PhoneNumber phoneNumber : addedPhones)
		{
			boolean phoneExists = false;
			for (ERMBPhone ermbPhone : ermbPhones)
			{
				if (ermbPhone.getPhoneNumber().equals(phoneNumber))
				{
					ermbPhone.setPhoneUsage(true);
					ermbPhone.setLastModified(now);
					phoneExists = true;
					break;
				}
			}
			if (!phoneExists)
			{
				//создаем новый ермб-телефон, у которого время успешной выгрузки в МБК = нулевому времени)
				ERMBPhone ermbPhone = new ERMBPhone(phoneNumber, true, now, TIME0);
				createdErmbPhones.add(ermbPhone);
			}
		}

		for (PhoneNumber phoneNumber : removedPhones)
		{
			boolean phoneExists = false;
			for (ERMBPhone ermbPhone : ermbPhones)
			{
				if (ermbPhone.getPhoneNumber().equals(phoneNumber))
				{
					ermbPhone.setPhoneUsage(false);
					ermbPhone.setLastModified(now);
					phoneExists = true;
					break;
				}
			}
			if (!phoneExists)
			{
				//создаем новый ермб-телефон, у которого время успешной выгрузки в МБК = нулевому времени)
				ERMBPhone ermbPhone = new ERMBPhone(phoneNumber, false, now, TIME0);
				createdErmbPhones.add(ermbPhone);
			}
		}

		ermbPhones.addAll(createdErmbPhones);
		saveOrUpdateERMBPhones(ermbPhones);
	}

	/**
	 * обновить телефоны
	 * @param ermbPhones - список ермб-телефонов
	 * @throws BusinessException
	 */
	public void updateERMBPhones(final List<ERMBPhone> ermbPhones) throws BusinessException
	{

		try
        {
            HibernateExecutor.getInstance(PHIZ_PROXY_MBK_INSTANCE_NAME).execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
	                for (ERMBPhone ermbPhone : ermbPhones)
	                {
		               session.update(ermbPhone);
	                }
                    return null;
                }
            });
        }
        catch(BusinessException e)
        {
            throw e;
        }
        catch (Exception e)
        {
           throw new BusinessException(e);
        }
	}

	/**
	 * Получить ЕРМБ-телефоны, для которых время последнего изменения больше времени последней успешной выгрузки в МБК
	 * @param maxResults - размер пачки
	 * @return
	 * @throws BusinessException
	 */
	public List<ERMBPhone> getERMBPhones(final int maxResults) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(PHIZ_PROXY_MBK_INSTANCE_NAME).execute(new HibernateAction<List<ERMBPhone>>()
			{
				public List<ERMBPhone> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.mbk.ERMBPhoneService.getERMBPhones");
					query.setParameter("maxResults", maxResults);
					return (List<ERMBPhone>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
