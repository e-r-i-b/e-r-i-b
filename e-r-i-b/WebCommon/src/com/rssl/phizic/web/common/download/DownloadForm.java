package com.rssl.phizic.web.common.download;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author akrenev
 * @ created 19.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��� �������� ������
 */

public class DownloadForm extends ActionFormBase
{
	private String fileType;
	private String clientFileName;
	private String contentType;

	/**
	 * @return ��� �����
	 */
	public String getFileType()
	{
		return fileType;
	}

	/**
	 * ������ ��� �����
	 * @param fileType ��� �����
	 */
	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	/**
	 * @return ��� ����� (��� �������)
	 */
	public String getClientFileName()
	{
		return clientFileName;
	}

	/**
	 * ������ ��� ����� (��� �������)
	 * @param clientFileName ��� ����� (��� �������)
	 */
	public void setClientFileName(String clientFileName)
	{
		this.clientFileName = clientFileName;
	}

	/**
	 * @return ��� ��������
	 */
	public String getContentType()
	{
		return contentType;
	}

	/**
	 * ������ ��� ��������
	 * @param contentType ��� ��������
	 */
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
}
