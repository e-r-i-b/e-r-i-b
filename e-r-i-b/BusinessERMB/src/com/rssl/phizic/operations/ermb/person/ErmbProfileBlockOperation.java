package com.rssl.phizic.operations.ermb.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ermb.ErmbChargeDateUpdater;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.profile.ErmbProfileListener;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.operations.OperationBase;

/**
 * User: Moshenko
 * Date: 13.06.2013
 * Time: 16:30:59
 *  Блокировка профиля ЕРМБ
 */
public class ErmbProfileBlockOperation extends OperationBase
{
	private final static SimpleService simpleService = new SimpleService();
	private ErmbProfileListener listener  = ErmbUpdateListener.getListener();
	private ErmbChargeDateUpdater updater = new ErmbChargeDateUpdater();

	private ErmbProfileEvent event;
	private ErmbProfileImpl profile;

	public void initialize(Long profileId) throws BusinessException,BusinessLogicException
	{
		profile = simpleService.findById(ErmbProfileImpl.class,profileId);

		if( profile == null)
			throw new BusinessException("Профиль с id " + profileId + " не найден в системе");

		event = new ErmbProfileEvent(ErmbHelper.copyProfile(profile));
	}

	/** Заблокировать или разблокировать клиента(клиентская блокировка)
	 * @param description причина блокировки, если разблокируем то пустая строка
	 * @return Версия профиля
	 */
	public Long udateProfileStatus(String description) throws BusinessException
	{
		if (profile.isClientBlocked())
		{
			profile.setClientBlocked(false);
			updater.initChargeDate(profile);
		}
		else
		{
			ErmbHelper.blockProfile(profile, description);
		}

		event.setNewProfile(profile);
		listener.beforeProfileUpdate(event);
		simpleService.update(profile);
		listener.afterProfileUpdate(event);
		return profile.getProfileVersion();
	}
}
