package com.rssl.phizic.business.profile.smartaddressbook;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.gate.profile.MBKPhone;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Загрузчик телефонов
 *
 * @author bogdanov
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */

public class MBKPhoneCSAAdminLoader
{
	private static final SimpleService SIMPLE_SERVICE = new SimpleService();
	private Set<MBKPhone> phones = new HashSet<MBKPhone>();
	private Long lastUpdateIndex = Long.MAX_VALUE;

	public void addPhone(MBKPhone phone) throws BusinessException
	{
		phones.add(phone);
		if (phones.size() >= ConfigFactory.getConfig(AddressBookConfig.class).getSberclietnSyncPackSize())
		{
			flush();
		}
	}

	public void flush() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					for (MBKPhone phone : phones)
						lastUpdateIndex = Math.min(SIMPLE_SERVICE.add(phone).getId(), lastUpdateIndex);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		phones.clear();
	}

	/**
	 * @return номер первой обновленной записи.
	 */
	public Long getLastUpdateIndex()
	{
		return lastUpdateIndex < Long.MAX_VALUE ? lastUpdateIndex - 1 : null;
	}
}
