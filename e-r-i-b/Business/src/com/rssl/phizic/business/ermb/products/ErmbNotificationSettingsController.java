package com.rssl.phizic.business.ermb.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ermb.ErmbPermissionCalculator;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.resources.external.LoanLink;

import java.util.List;

/**
 * Управление настройками оповещения по продуктам в мобильном банке
 * @author Puzikov
 * @ created 30.07.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbNotificationSettingsController
{
	private final static SimpleService simpleService = new SimpleService();

	private final ErmbPermissionCalculator permissionCalculator;
	private final List<CardLink> cardLinks;
	private final List<AccountLink> accountLinks;
	private final List<LoanLink> loanLinks;

	/**
	 * ctor
	 * @param profile ермб-профиль
	 */
	public ErmbNotificationSettingsController(ErmbProfileImpl profile)
	{
		this.permissionCalculator = new ErmbPermissionCalculator(profile);
		this.cardLinks = profile.getCardLinks();
		this.accountLinks = profile.getAccountLinks();
		this.loanLinks = profile.getLoanLinks();
	}

	/**
	 * Обновить настройки оповещения по всем продуктам.
	 * Установленные вручную значения сбросятся
	 */
	public void resetAll() throws BusinessException
	{
		resetProductNotification(cardLinks, permissionCalculator.impliesCardNotification());
		resetProductNotification(accountLinks, permissionCalculator.impliesAccountNotification());
		resetProductNotification(loanLinks, permissionCalculator.impliesLoanNotification());
	}

	/**
	 * Отключить неподдерживаемые в текущем состоянии оповещения
	 */
	public void disableUnsupported() throws BusinessException
	{
		if (!permissionCalculator.impliesCardNotification())
			resetProductNotification(cardLinks, false);
		if (!permissionCalculator.impliesAccountNotification())
			resetProductNotification(accountLinks, false);
		if (!permissionCalculator.impliesAccountNotification())
			resetProductNotification(loanLinks, false);
	}

	private void resetProductNotification(List<? extends ErmbProductLink> products, boolean notify) throws BusinessException
	{
		boolean needUpdate = false;
		for (ErmbProductLink product : products)
		{
			if (product.getErmbNotification() != notify)
			{
				product.setErmbNotification(notify);
				needUpdate = true;
			}
		}

		if (needUpdate)
			simpleService.addOrUpdateList(products);
	}
}
