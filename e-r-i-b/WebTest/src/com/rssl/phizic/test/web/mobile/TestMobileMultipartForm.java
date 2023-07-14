package com.rssl.phizic.test.web.mobile;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.upload.FormFile;

/**
 * Тест-кейс upload-а файла на примере сохранения письма.
 * @author Dorzhinov
 * @ created 20.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileMultipartForm extends TestMobileForm
{
	private String id;
	private String parentId;
	private String type;
	private String themeId;
	private String responseMethod;
	private String phone;
	private String eMail;
	private String subject;
	private String body;
	private String fileName;
	private FormFile file;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getThemeId()
	{
		return themeId;
	}

	public void setThemeId(String themeId)
	{
		this.themeId = themeId;
	}

	public String getResponseMethod()
	{
		return responseMethod;
	}

	public void setResponseMethod(String responseMethod)
	{
		this.responseMethod = responseMethod;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getEMail()
	{
		return eMail;
	}

	public void setEMail(String eMail)
	{
		this.eMail = eMail;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

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
}
