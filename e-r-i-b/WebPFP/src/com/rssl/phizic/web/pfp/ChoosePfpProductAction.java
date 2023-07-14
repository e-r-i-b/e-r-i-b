package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import static com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType.INSURANCE;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.pfp.ChoosePfpProductOperation;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import org.apache.struts.action.ActionForward;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 15.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ChoosePfpProductAction extends PassingPFPActionBase
{

	protected Map<String, String> getKeyMethodMap()
	{
		return null;
	}

	protected ChoosePfpProductOperation getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return getOperation(ChoosePfpProductOperation.class, form);
	}

	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase op) throws BusinessException, BusinessLogicException
	{
		ChoosePfpProductForm frm = (ChoosePfpProductForm) form;
		ChoosePfpProductOperation operation = (ChoosePfpProductOperation) op;

		Query query = operation.createQuery(getQueryName(frm));
		try
		{
			frm.setData(query.executeList());
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	private String getQueryName(ChoosePfpProductForm form)
	{
		return form.getDictionaryProductType();
	}
}
