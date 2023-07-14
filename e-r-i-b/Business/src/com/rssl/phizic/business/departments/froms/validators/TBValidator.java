package com.rssl.phizic.business.departments.froms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Проверяет является ли переданный номер ТБ номером тербанка.
 * @author komarov
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class TBValidator extends FieldValidatorBase
{
	/**
	 * конструктор
	 */
	public TBValidator(){}

	public boolean validate(String ids) throws TemporalDocumentException
	{
		List<String> idsList = Arrays.asList(ids.split(","));
		return isIdsValid(idsList);
	}

	protected boolean isIdsValid(List<String> ids) throws TemporalDocumentException
	{
		try
		{
			return AllowedDepartmentsUtil.isAllowedTerbanksNumbers(ids);
		}
		catch (BusinessException be)
		{
			throw new TemporalDocumentException(be);
		}
	}
}
