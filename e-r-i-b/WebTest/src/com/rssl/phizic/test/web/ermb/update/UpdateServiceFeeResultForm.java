package com.rssl.phizic.test.web.ermb.update;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * Заглушка отправки сообщения списания абонентской платы (СОС - ЕРИБ)
 * @author Puzikov
 * @ created 04.06.14
 * @ $Author$
 * @ $Revision$
 */

public class UpdateServiceFeeResultForm extends ActionForm
{
	/**
	 * Файл ФПП
	 */
	private FormFile file;

	/**
	 * Статус задачи
	 */
	private String status;

	/**
	 * Признак успешности списания
	 */
	private boolean success = true;

	public FormFile getFile()
	{
		return file;
	}

	public void setFile(FormFile file)
	{
		this.file = file;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}
}
