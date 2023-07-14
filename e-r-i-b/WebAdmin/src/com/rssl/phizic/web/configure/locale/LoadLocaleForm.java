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
	 * @return загружаемый файл
	 */
	public FormFile getContent()
	{
		return content;
	}

	/**
	 * Установить загружаемый файл
	 * @param content загружаемый файл
	 */
	public void setContent(FormFile content)
	{
		this.content = content;
	}

	/**
	 * @return идентификатор локали
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * Установить идентификатор локали
	 * @param localeId идентификатор
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}


	/**
	 * @return признак того, что редактируем CSA сущность
	 */
	public boolean getIsCSA()
	{
		return isCSA;
	}

	/**
	 * @param CSA признак того, что редактируем CSA сущность
	 */
	public void setIsCSA(boolean CSA)
	{
		isCSA = CSA;
	}
}
