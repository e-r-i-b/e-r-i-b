package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile.documents;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.operations.userprofile.EditIdentifierBasketOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionMessages;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Экшн для добавления, редактирования и удаления водительского удостоверения клиента
 *
 * @author EgorovaA
 * @ created 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class DrivingLicenceInfoAction extends AdditionalDocumentInfoAction
{
	protected DocumentType getDocumentType()
	{
		return DocumentType.DL;
	}

	protected FormProcessor<ActionMessages, ?> getProcessor(AdditionalDocumentInfoForm frm)
	{
		DrivingLicenceInfoForm form = (DrivingLicenceInfoForm) frm;
		return FormHelper.newInstance(getValuesSource(form), DrivingLicenceInfoForm.FORM);
	}

	protected FieldValuesSource getValuesSource(AdditionalDocumentInfoForm frm)
	{
		DrivingLicenceInfoForm form = (DrivingLicenceInfoForm) frm;
		Map<String, String> source = new HashMap<String, String>();

		source.put(DrivingLicenceInfoForm.NUMBER, form.getNumber());
		source.put(DrivingLicenceInfoForm.SERIES, form.getSeries());
		source.put(DrivingLicenceInfoForm.ISSUE_BY, form.getIssueBy());
		source.put(DrivingLicenceInfoForm.EXPIRE_DATE, form.getExpireDate());
		source.put(DrivingLicenceInfoForm.ISSUE_DATE, form.getIssueDate());

		return new MapValuesSource(source);
	}

	protected void updateOperation(EditIdentifierBasketOperation operation, Map<String, Object> result)
	{
		operation.setNumber((String) result.get(DrivingLicenceInfoForm.NUMBER));
		operation.setSeries((String) result.get(DrivingLicenceInfoForm.SERIES));
		operation.setIssueBy((String) result.get(DrivingLicenceInfoForm.ISSUE_BY));
		operation.setExpireDate(DateHelper.toCalendar((Date) result.get(DrivingLicenceInfoForm.EXPIRE_DATE)));
		operation.setIssueDate(DateHelper.toCalendar((Date) result.get(DrivingLicenceInfoForm.ISSUE_DATE)));
	}
}
