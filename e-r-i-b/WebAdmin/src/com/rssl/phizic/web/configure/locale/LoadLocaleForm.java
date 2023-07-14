package com.rssl.phizic.web.configure.locale;

import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.upload.FormFile;

/**
 * @author koptyaev
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class LoadLocaleForm extends EditFormBase
{
	private String localeId;
	private FormFile content;
	private boolean isCSA;

	/**
	 * @return ����������� ����
	 */
	public FormFile getContent()
	{
		return content;
	}

	/**
	 * ���������� ����������� ����
	 * @param content ����������� ����
	 */
	public void setContent(FormFile content)
	{
		this.content = content;
	}

	/**
	 * @return ������������� ������
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * ���������� ������������� ������
	 * @param localeId �������������
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}


	/**
	 * @return ������� ����, ��� ����������� CSA ��������
	 */
	public boolean getIsCSA()
	{
		return isCSA;
	}

	/**
	 * @param CSA ������� ����, ��� ����������� CSA ��������
	 */
	public void setIsCSA(boolean CSA)
	{
		isCSA = CSA;
	}
}
