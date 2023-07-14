package com.rssl.phizic.web.common.socialApi.mail;

import com.rssl.phizic.web.common.client.ext.sbrf.mail.EditMailForm;
import org.apache.struts.upload.FormFile;

/**
 * Форма редактирования письма.
 * @author Dorzhinov
 * @ created 13.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditMailMobileForm extends EditMailForm
{
	//in
	private Long parentId; //id письма, на которое отвечаем
	private Long themeId; //id тематики обращения
	private String responseMethod; //способ получения ответа
	private String phone; //телефон для получения ответа
	private String eMail; //e-mail для получения ответа
	private String subject; //тема
	private String body; //сообщение
	private String fileName; //имя прикрепленного файла
	private FormFile attach; //прикрепленный файл

	//in. заполняем сами
	private String newMailState; //статус письма

	//out
	private String fileOutput; //байтовый массив файла в формате base64

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}


	public Long getThemeId()
	{
		return themeId;
	}

	public void setThemeId(Long themeId)
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

	public String geteMail()
	{
		return eMail;
	}

	public void seteMail(String eMail)
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

	public FormFile getAttach()
	{
		return attach;
	}

	public void setAttach(FormFile attach)
	{
		this.attach = attach;
	}

	public String getNewMailState()
	{
		return newMailState;
	}

	public void setNewMailState(String newMailState)
	{
		this.newMailState = newMailState;
	}

	public String getFileOutput()
	{
		return fileOutput;
	}

	public void setFileOutput(String fileOutput)
	{
		this.fileOutput = fileOutput;
	}
}
