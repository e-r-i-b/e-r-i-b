package com.rssl.phizic.web.client.depo;

import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.gate.depo.DepoDebtInfo;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author lukina
 * @ created 31.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowDepoDebtInfoForm  extends EditFormBase
{
	private DepoAccountLink depoAccountLink;
	private DepoDebtInfo depoDebtInfo;
	private boolean refresh;

	public DepoDebtInfo getDepoDebtInfo()
	{
		return depoDebtInfo;
	}

	public void setDepoDebtInfo(DepoDebtInfo depoDebtInfo)
	{
		this.depoDebtInfo = depoDebtInfo;
	}

	public DepoAccountLink getDepoAccountLink()
	{
		return depoAccountLink;
	}

	public void setDepoAccountLink(DepoAccountLink depoAccountLink)
	{
		this.depoAccountLink = depoAccountLink;
	}

	public boolean isRefresh()
	{
		return refresh;
	}

	public void setRefresh(boolean refresh)
	{
		this.refresh = refresh;
	}
}
