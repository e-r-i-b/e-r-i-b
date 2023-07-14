package com.rssl.phizic.web.certification;

import com.rssl.phizic.security.crypto.Certificate;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Omeliyanchuk
 * @ created 21.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmLoadCertificateForm extends ActionFormBase
{
	private Long id;
	private String cert;//название временного файла с сертификатом
	private String certAns;//название временного файла с ответом на запрос
	private Certificate certificate;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCert()
	{
		return cert;
	}

	public void setCert(String cert)
	{
		this.cert = cert;
	}

	public String getCertAns()
	{
		return certAns;
	}

	public void setCertAns(String certAns)
	{
		this.certAns = certAns;
	}

	public Certificate getCertificate()
	{
		return certificate;
	}

	public void setCertificate(Certificate certificate)
	{
		this.certificate = certificate;
	}
}
