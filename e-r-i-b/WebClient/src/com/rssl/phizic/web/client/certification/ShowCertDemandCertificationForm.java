package com.rssl.phizic.web.client.certification;

import java.util.List;

import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.business.certification.CertDemandStatus;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 17.11.2006 Time: 12:16:10 To change this template use
 * File | Settings | File Templates.
 */
public class ShowCertDemandCertificationForm extends ListFormBase
{
	private Boolean needChange = false;

	public Boolean getNeedChange()
	{
		return needChange;
	}

	public void setNeedChange(Boolean needChange)
	{
		this.needChange = needChange;
	}

	public boolean getIsProceded()
	{
		for (CertDemand certDemand : (List<CertDemand>) getData())
		{
			if(certDemand.getStatus().equals(CertDemandStatus.STATUS_PROCESSING) || certDemand.getStatus().equals( CertDemandStatus.STATUS_SENDED))
				return true;
		}
		return false;
	}
}
