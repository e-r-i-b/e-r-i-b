package com.rssl.phizic.web.certification;

import com.rssl.phizic.security.certification.CertOwn;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Omeliyanchuk
 * @ created 27.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditCertificateForm extends EditFormBase
{
	CertOwn certOwn;

	public CertOwn getCertOwn()
	{
		return certOwn;
	}

	public void setCertOwn(CertOwn certOwn)
	{
		this.certOwn = certOwn;
	}
}
