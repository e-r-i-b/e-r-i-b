package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.gate.ima.IMAccountAbstract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Форма виджета "Мет. счета"
 * @author lukina
 * @ created 12.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountBlockWidgetForm extends ProductBlockWidgetForm
{
	private List<IMAccountLink> imAccounts = new ArrayList<IMAccountLink>();

	private Map<IMAccountLink, IMAccountAbstract> imAccountAbstract;

	private List<Long> showIMAccountLinkIds;

	private boolean isAllIMAccountsDown;

	///////////////////////////////////////////////////////////////////////////

	public List<IMAccountLink> getImAccounts()
	{
		return imAccounts;
	}

	public void setImAccounts(List<IMAccountLink> imAccounts)
	{
		this.imAccounts = imAccounts;
	}

	public Map<IMAccountLink, IMAccountAbstract> getIMAccountAbstract()
	{
		return imAccountAbstract;
	}

	public void setIMAccountAbstract(Map<IMAccountLink, IMAccountAbstract> imAccountAbstract)
	{
		this.imAccountAbstract = imAccountAbstract;
	}

   /**
	 * @return true - если не удалось получит информацию по всем мет. счетам, т.к. были ошибки бэк офиса.
	 */
	public boolean isAllIMAccountsDown()
	{
		return isAllIMAccountsDown;
	}

	public void setAllIMAccountsDown(boolean allIMAccountsDown)
	{
		isAllIMAccountsDown = allIMAccountsDown;
	}

	public List<Long> getShowIMAccountLinkIds()
	{
		return showIMAccountLinkIds;
	}

	public void setShowIMAccountLinkIds(List<Long> showIMAccountLinkIds)
	{
		this.showIMAccountLinkIds = showIMAccountLinkIds;
	}
}

