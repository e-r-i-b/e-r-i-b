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
 * ��������� ������
 */

public interface FileValidator
{
	/**
	 * ��������� ��������� �����
	 * @param file ������������ ����
	 * @return ��������� �� ������ (���� ������ ���, �� �������� new ActionMessages())
	 * @throws BusinessException
	 */
	public ActionMessages validate(FormFile file) throws BusinessException;
}
