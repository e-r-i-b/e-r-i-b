package com.rssl.phizgate.common.profile;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.profile.MBKPhone;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

/**
 * Сервис по работе со слепком МБК.
 *
 * @author bogdanov
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 */

public class MBKCastService
{
	private Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final String CSA_ADMIN = "CSAAdmin";
	private final MBKPhoneLoaderCallBack phoneLoaderCallBack;
	public static final String START_ACTUALIZE_MESSAGE_NAME = "StartActualizeMBKPhones";

	public MBKCastService(MBKPhoneLoaderCallBack phoneLoaderCallBack)
	{
		this.phoneLoaderCallBack = phoneLoaderCallBack;
	}

	private List<MBKPhone> getPhonesByDelta(final Long beforeId, final Long phoneId) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_ADMIN).execute(new HibernateAction<List<MBKPhone>>()
			{
				public List<MBKPhone> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.gate.profile.MBKPhone.getDelta")
						.setParameter("beforeId", beforeId)
						.setParameter("phoneId", phoneId)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Обновление списка телефонов между указанными датами.
	 *
	 * @param beforeId начальный индекс.
	 */
	public void loadByDelta(final Long beforeId) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Long lastPhoneId = Long.MIN_VALUE;
					MBKPhoneLoader loader = new MBKPhoneLoader(phoneLoaderCallBack);
					long before = beforeId;
					long phoneId = before + ConfigFactory.getConfig(AddressBookConfig.class).getSberclietnSyncPackSize();
					while (true)
					{
						List<MBKPhone> phones = getPhonesByDelta(before, phoneId);
						for (MBKPhone phone : phones)
						{
							try
							{
								if (!phone.isAdded())
									loader.deletePhone(phone.getPhone());
								else
									loader.addPhone(phone.getPhone());
							}
							catch (Exception e)
							{
								log.warn("Ошибка при изменение номера телефона " + phone.getPhone(), e);
							}
							lastPhoneId = Math.max(phone.getId(), lastPhoneId);
						}
						if (phones.isEmpty())
							break;
						before = phoneId;
						phoneId = before + ConfigFactory.getConfig(AddressBookConfig.class).getSberclietnSyncPackSize();
					}

					loader.flush();

					if (lastPhoneId > Long.MIN_VALUE)
					{
						updatePhoneLoadInfo(lastPhoneId);
					}

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * @param phone телефон для проверки.
	 * @return является ли указанный телефон телефоном клиента Сбербанка.
	 */
	public boolean isSberbankClient(final String phone) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizgate.common.profile.MBKPhoneCast.getPhone")
							.setParameter("phone", phone)
							.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * @return Информация о последней записи.
	 */
	public PhoneUpdate getLastPhoneInfo() throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PhoneUpdate>()
			{
				public PhoneUpdate run(Session session) throws Exception
				{
					List<PhoneUpdate> updates = session.getNamedQuery("com.rssl.phizgate.common.profile.PhoneUpdate.lastUpdate").list();
					if (updates.isEmpty())
						return null;
					return updates.get(0);
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Обновляем данные
	 *
	 * @param lastUpdatedIndex последний обновленный индекс.
	 * @throws GateException
	 */
	public void updatePhoneLoadInfo(final Long lastUpdatedIndex) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizgate.common.profile.PhoneUpdate.setUpdate")
							.setParameter("updatedId", lastUpdatedIndex)
							.executeUpdate();

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Добавление записи об обновлении.
	 *
	 * @param phoneUpdate информация по обновлению.
	 * @throws GateException
	 */
	public void addLastPhoneUpdateInfo(final PhoneUpdate phoneUpdate) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					PhoneUpdate lastPhoneUpdate = getLastPhoneInfo();
					if (lastPhoneUpdate != null)
						session.delete(lastPhoneUpdate);

					phoneUpdate.setNewItem(true);
					session.save(phoneUpdate);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
