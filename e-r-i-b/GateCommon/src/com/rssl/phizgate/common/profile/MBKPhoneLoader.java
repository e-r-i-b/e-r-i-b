package com.rssl.phizgate.common.profile;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Session;

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

public class MBKPhoneLoader
{
	private static final MBKCastService MBK_CAST_SERVICE = new MBKCastService(null);
	private final MBKPhoneLoaderCallBack callBack;
	private Set<String> addedPhones = new HashSet<String>();
	private Set<String> deletedPhones = new HashSet<String>();

	public MBKPhoneLoader(MBKPhoneLoaderCallBack callBack)
	{
		this.callBack = callBack;
	}

	public void addPhone(String phone) throws GateException
	{
		addedPhones.add(phone);
		deletedPhones.remove(phone);
		if (addedPhones.size() >= ConfigFactory.getConfig(AddressBookConfig.class).getSberclietnSyncPackSize())
		{
			flush(addedPhones);
		}
	}

	public void deletePhone(String phone) throws GateException
	{
		deletedPhones.add(phone);
		addedPhones.remove(phone);
		if (deletedPhones.size() >= ConfigFactory.getConfig(AddressBookConfig.class).getSberclietnSyncPackSize())
		{
			flush(deletedPhones);
		}
	}

	public void flush() throws GateException
	{
		flush(addedPhones);
		flush(deletedPhones);
	}

	private void flush(final Set<String> phns) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{

					if (phns == addedPhones)
						for (String phone : phns)
						{
							addPhoneBD(phone);
							if (callBack != null)
								callBack.onAdd(phone);
						}
					else
						for (String phone : phns) {
							delete(phone);
							if (callBack != null)
								callBack.onDelete(phone);
						}

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		phns.clear();
	}


	/**
	 * @param phone добавляемый номер телефона.
	 * @return добавленный номер.
	 * @throws GateException
	 */
	public void addPhoneBD(final String phone) throws GateException
	{
		final MBKPhoneCast cast = new MBKPhoneCast();
		cast.setPhone(phone);
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					if (!MBK_CAST_SERVICE.isSberbankClient(phone))
						session.save(cast);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private void delete(final String phone) throws GateException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizgate.common.profile.MBKPhoneCast.deletePhone");
			query.setParameter("phone", phone);
			query.executeUpdate();
		}
		catch(DataAccessException e)
		{
			throw new GateException(e);
		}
	}
}
