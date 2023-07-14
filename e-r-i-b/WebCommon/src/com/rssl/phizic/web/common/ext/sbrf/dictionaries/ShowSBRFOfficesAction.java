package com.rssl.phizic.web.common.ext.sbrf.dictionaries;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeAdapter;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.GetSBRFOfficesOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.dictionaries.ShowMultiDepartmentOfficesAction;

import java.util.Map;

/**
 * @author Kosyakov
 * @ created 15.11.2005
 * @ $Author: hudyakov $
 * @ $Revision: 12452 $
 */
public class ShowSBRFOfficesAction extends ShowMultiDepartmentOfficesAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ShowSBRFOfficesForm form = (ShowSBRFOfficesForm) frm;
		GetSBRFOfficesOperation operation = createOperation(GetSBRFOfficesOperation.class);
	    operation.initialize(form.getExternalSystemId());
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowSBRFOfficesForm.FILTER_FORM;
	}

	protected Office createTemplate(Map<String, Object> filterParams)
	{
		SBRFOfficeAdapter template = new SBRFOfficeAdapter();
		template.setName((String) filterParams.get("name"));
		SBRFOfficeCodeAdapter codeTemplate = new SBRFOfficeCodeAdapter(
								(String) filterParams.get("region"),
								(String) filterParams.get("branch"),
								(String) filterParams.get("office")
							);
		template.setCode(codeTemplate);

		return template;
	}
}
