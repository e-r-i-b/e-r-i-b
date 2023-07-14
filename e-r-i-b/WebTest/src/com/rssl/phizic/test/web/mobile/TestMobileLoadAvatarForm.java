package com.rssl.phizic.test.web.mobile;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.upload.FormFile;

/**
 * @author EgorovaA
 * @ created 26.06.14
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileLoadAvatarForm extends TestMobileForm
{
	private FormFile file;
	private String fileName;

	public FormFile getFile()
	{
		return file;
	}

	public void setFile(FormFile file)
	{
		this.file = file;
		if (StringHelper.isEmpty(fileName))
			setFileName(file.getFileName());
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
}
