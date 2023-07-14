package com.rssl.phizic.business.departments.froms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;

import java.util.Map;

/**
 * @author akrenev
 * @ created 19.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * ¬алидатор, провер€ющий доступ текущего сотрудника к подразделению с заданными тб, осб и всп
 */

public class DepartmentByCodeValidator extends MultiFieldsValidatorBase
{
	public static final String TB_BINDING_NAME  = "tb";
	public static final String OSB_BINDING_NAME = "osb";
	public static final String VSP_BINDING_NAME = "vsp";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String tb  = (String) retrieveFieldValue(TB_BINDING_NAME, values);
		String osb = (String) retrieveFieldValue(OSB_BINDING_NAME, values);
		String vsp = (String) retrieveFieldValue(VSP_BINDING_NAME, values);
		try
		{
			return AllowedDepartmentsUtil.isDepartmentsAllowedByCode(tb, osb, vsp);
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException("ќшибка определени€ доступности подразделени€ " + tb + "|" + osb + "|" + vsp, e);
		}
	}
}
