package com.rssl.phizic.web.certification;

import com.rssl.phizic.web.actions.ActionFormBase;
import org.apache.struts.upload.FormFile;

/**
 * @author Omeliyanchuk
 * @ created 21.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoadCertificateForm extends ActionFormBase
{
	private FormFile certAnswerFilePath; /*.aga - клиенту */
	private FormFile certFilePath; /*.agc - в банк */
	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public FormFile getCertAnswerFilePath()
	{
		return certAnswerFilePath;
	}
	             
	public void setCertAnswerFilePath(FormFile certAnswerFilePath)
	{
		this.certAnswerFilePath = certAnswerFilePath;
	}

	public FormFile getCertFilePath()
	{
		return certFilePath;
	}

	public void setCertFilePath(FormFile certFilePath)
	{
		this.certFilePath = certFilePath;
	}
}
