package com.rssl.phizic.business.ermb.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.ermb.ErmbPermissionCalculator;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;

/**
 * Ќастройки видимости/оповещени€ продуктов в контексте мобильного банка
 *
 * @author Puzikov
 * @ created 31.07.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbProductSettings
{
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	private final boolean showInSms;
	private final boolean notification;

	private final boolean cardNotificationAllowed;
	private final boolean accountNotificationAllowed;
	private final boolean loanNotificationAllowed;

	public static final ErmbProductSettings ON = new ErmbProductSettings(true, true, true, true, true);
	public static final ErmbProductSettings OFF = new ErmbProductSettings(false, false, false, false, false);

	private ErmbProductSettings(boolean showInSms, boolean notification, boolean cardNotificationAllowed, boolean accountNotificationAllowed, boolean loanNotificationAllowed)
	{
		this.showInSms = showInSms;
		this.notification = notification;
		this.cardNotificationAllowed = cardNotificationAllowed;
		this.accountNotificationAllowed = accountNotificationAllowed;
		this.loanNotificationAllowed = loanNotificationAllowed;
	}

	/**
	 * @param personId персона ериб
	 * @return настройки
	 * @throws BusinessException
	 */
	public static ErmbProductSettings get(long personId) throws BusinessException
	{
		ErmbProfileImpl profile = profileService.findByPersonId(personId);
		if (profile == null || !profile.isServiceStatus())
		{
			return OFF;
		}

		boolean showInSms = profile.getNewProductShowInSms();
		boolean notification = profile.getNewProductNotification();

		ErmbPermissionCalculator permissionCalculator = new ErmbPermissionCalculator(profile);
		boolean cardNotificationAllowed = permissionCalculator.impliesCardNotification();
		boolean accountNotificationAllowed = permissionCalculator.impliesAccountNotification();
		boolean loanNotificationAllowed = permissionCalculator.impliesLoanNotification();

		return new ErmbProductSettings(showInSms, notification, cardNotificationAllowed, accountNotificationAllowed, loanNotificationAllowed);
	}

	/**
	 * @return доступно по смс
	 */
	public boolean isShowInSms()
	{
		return showInSms;
	}

	/**
	 * @param linkType тип ресурса
	 * @return отправл€ть уведомлени€
	 */
	public boolean isNotification(ResourceType linkType)
	{
		if (!notification)
		{
			return false;
		}

		switch (linkType)
		{
			case ACCOUNT:
				return accountNotificationAllowed;
			case CARD:
				return cardNotificationAllowed;
			case LOAN:
				return loanNotificationAllowed;
			default:
				throw new IllegalArgumentException();
		}
	}

}
