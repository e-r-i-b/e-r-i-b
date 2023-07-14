package com.rssl.phizic.web.common.download;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author akrenev
 * @ created 19.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Форма для выгрузки файлов
 */

public class DownloadForm extends ActionFormBase
{
	private String fileType;
	private String clientFileName;
	private String contentType;

	/**
	 * @return тип файла
	 */
	public String getFileType()
	{
		return fileType;
	}

	/**
	 * задать тип файла
	 * @param fileType тип файла
	 */
	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	/**
	 * @return имя файла (для клиента)
	 */
	public String getClientFileName()
	{
		return clientFileName;
	}

	/**
	 * задать имя файла (для клиента)
	 * @param clientFileName имя файла (для клиента)
	 */
	public void setClientFileName(String clientFileName)
	{
		this.clientFileName = clientFileName;
	}

	/**
	 * @return тип контента
	 */
	public String getContentType()
	{
		return contentType;
	}

	/**
	 * задать тип контента
	 * @param contentType тип контента
	 */
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
}
