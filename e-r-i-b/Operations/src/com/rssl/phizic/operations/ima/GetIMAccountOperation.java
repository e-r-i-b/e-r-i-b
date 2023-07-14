package com.rssl.phizic.operations.ima;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.link.GetExternalResourceLinkOperation;
import com.rssl.phizic.utils.MockHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author Balovtsev
 * @ created 30.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class GetIMAccountOperation extends GetExternalResourceLinkOperation
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	//была ли заглушена ошибка
	private boolean isBackError = false;

	public boolean isBackError()
	{
		return isBackError;
	}

	public List<IMAccountLink> getIMAccounts()
    {
        return filter(null);
    }

    public List<IMAccountLink> getActiveIMAccounts()
    {
        return filter(new ActiveIMAccountFilter());
    }

    private List<IMAccountLink> filter(IMAccountFilter imAccountFilter)
	{
		try
		{
            if(imAccountFilter == null)
			    return getPersonData().getIMAccountLinks();
            else
                return getPersonData().getIMAccountLinks(imAccountFilter);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения списка ОМС из БД", e);
			isBackError = true;
			return null;
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка получения списка ОМС из БД", e);
			isBackError = true;
			return null;
		}
	}

	public List<IMAccountLink> getPersonShowsInMainIMAccountLinks()
	{
		List<IMAccountLink> clientIma  = getIMAccounts();
		return getShowInMainLinks(clientIma);
	}

	public List<IMAccountLink> getAllIMAccounts()
	{
		return getIMAccounts(false);
	}

	/**
	 * Получение списка ОМС
	 * @param onlyShowInMain флаг отыечающий за необхдимость получения только тех продуктов, которые нужно отображать на главной странице
	 * @return
	 */
	private List<IMAccountLink> getIMAccounts(boolean onlyShowInMain)
	{
		List<IMAccountLink> accounts = new ArrayList<IMAccountLink>();

		for (IMAccountLink accountLink :getIMAccounts())
		{
			// Если не нужно отображать информацию по этому линку, даже не пытаемся получать ее
			if (accountLink.getShowInMain() || !onlyShowInMain)
			{
				IMAccount account = accountLink.getImAccount();
				if (MockHelper.isMockObject(account))
					isBackError = true;

				if (!isUseStoredResource())
				{
					setUseStoredResource(account instanceof StoredIMAccount);
				}

				accounts.add(accountLink);
			}
		}
		return accounts;
	}

	/**
	 * Получение линков активных ОМС, учитывая их видимость в mAPI
	 * @return
	 */
	public List<IMAccountLink> getActiveShowInMobileIMAccounts()
	{
		ArrayList<IMAccountLink> imAccountLinks = new ArrayList<IMAccountLink>();
		for (IMAccountLink imAccountLink: getIMAccounts())
		{
			if ( (new ActiveAndShowInMobileLinkFilter()).accept(imAccountLink) )
				imAccountLinks.add(imAccountLink);
		}
		return imAccountLinks;
	}
}
