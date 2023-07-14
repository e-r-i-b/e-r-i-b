package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.extendedattributes.Attributable;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;

/**
 * @author Omeliyanchuk
 * @ created 01.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class DepartmentDocumentHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof Attributable))
			throw new DocumentException("document должен реализовывать Attributable");

		DepartmentService departmentService = new DepartmentService();

		Attributable attributeDocument = (Attributable)document;

		ExtendedAttribute attribute = attributeDocument.getAttribute("department");

		if(attribute == null)
			throw new DocumentException("Не установлен атрибут department");

		Long departmentId = Long.parseLong( attribute.getStringValue() );
		try
		{
			Department department = departmentService.findById( departmentId );
			if(department!= null)
			{
				((BusinessDocument) document).setDepartment( department );
			}
			else
				throw new DocumentException("Не найдено подразделение указанное в документе.");
		}
		catch(BusinessException ex)
		{
			throw new DocumentException(ex);
		}
	}
}
