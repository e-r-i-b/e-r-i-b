package com.rssl.phizic.operations.forms;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.business.BusinessException;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 25.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class UpdateFormOperation extends OperationBase implements EditEntityOperation
{
	private static PaymentFormService formService = new PaymentFormService();	

	private MetadataBean form;

	public void initialize(String formName) throws BusinessException
	{
		MetadataBean temp = formService.findByName(formName);
		if (temp == null)
			throw new BusinessException("форма с именем " + formName + " не найдена");

		form = temp;
	}

	public MetadataBean getEntity()
	{
		return form;
	}

	public void save() throws BusinessException
	{
		formService.update(form);
	}
}
