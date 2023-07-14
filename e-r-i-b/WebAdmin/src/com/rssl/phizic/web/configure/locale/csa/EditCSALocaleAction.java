package com.rssl.phizic.web.configure.locale.csa;


import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.locale.EditLocaleOperation;
import com.rssl.phizic.operations.locale.csa.EditCSALocaleOperation;
import com.rssl.phizic.web.configure.locale.EditLocaleAction;
import com.rssl.phizic.web.configure.locale.EditLocaleForm;
import com.rssl.phizic.web.image.ImageEditFormBase;
import org.apache.struts.action.ActionMessages;



/**
 * Редактирование локали в цса
 * @author koptyaev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditCSALocaleAction extends EditLocaleAction
{
	protected EditLocaleOperation createOperation(EditLocaleForm form) throws BusinessException
	{
		form.setIsCSA(true);
		return createOperation(EditCSALocaleOperation.class);
	}

	protected ActionMessages validate(ImageEditFormBase form, String imageId)
	{
		return null;
	}
}
