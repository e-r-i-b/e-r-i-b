package com.rssl.phizic.web.validators;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import com.rssl.phizic.business.BusinessException;

/**
 * @author akrenev
 * @ created 23.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * Валидатор файлов
 */

public interface FileValidator
{
	/**
	 * выполнить валидацию файла
	 * @param file валидируемый файл
	 * @return сообщения об ошибке (если ошибок нет, то вернется new ActionMessages())
	 * @throws BusinessException
	 */
	public ActionMessages validate(FormFile file) throws BusinessException;
}
