package com.rssl.phizic.business.loanclaim.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;

import java.util.Map;

/**
 * @author Rtischeva
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 */
public class CheckAccountDepartmentValidator extends MultiFieldsValidatorBase
{
	public static final String PARAMETER_ACCOUNT_ID_FIELD_NAME = "accountIdFieldName";
	public static final String PARAMETER_DEPARTMENT_TB_FIELD_NAME = "departmentTBFieldName";

	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String accountIdField = getParameter(PARAMETER_ACCOUNT_ID_FIELD_NAME);
		String departmentTBField = getParameter(PARAMETER_DEPARTMENT_TB_FIELD_NAME);

		String accountIdString = (String) values.get(accountIdField);
		String departmentTB = (String) values.get(departmentTBField);

		String idString = accountIdString.substring(accountIdString.indexOf(":") + 1, accountIdString.length());
		Long accountId = Long.parseLong(idString);

		try
		{
			AccountLink link = externalResourceService.findLinkById(AccountLink.class, accountId);
			if (link != null)
			{
				String accountTb = link.getOffice().getCode().getFields().get("region");
				return departmentTB.equals(accountTb);
			}
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		return false;
	}
}
