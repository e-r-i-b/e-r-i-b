package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile.documents;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.operations.userprofile.EditIdentifierBasketOperation;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;

/**
 * Экшн для добавления, редактирования и удаления Свидетельства о регистрации транспортного средства клиента
 *
 * @author muhin
 * @ created 04.06.15
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationCertificateInfoAction extends AdditionalDocumentInfoAction
{
	protected DocumentType getDocumentType()
	{
		return DocumentType.RC;
	}

	protected FormProcessor<ActionMessages, ?> getProcessor(AdditionalDocumentInfoForm frm)
	{
		RegistrationCertificateInfoForm form = (RegistrationCertificateInfoForm) frm;
		return FormHelper.newInstance(getValuesSource(form), RegistrationCertificateInfoForm.FORM);
	}

	protected FieldValuesSource getValuesSource(AdditionalDocumentInfoForm frm)
	{
		RegistrationCertificateInfoForm form = (RegistrationCertificateInfoForm) frm;
		Map<String, String> source = new HashMap<String, String>();

		source.put(RegistrationCertificateInfoForm.NUMBER, form.getNumber());
		source.put(RegistrationCertificateInfoForm.SERIES, form.getSeries());
		source.put(RegistrationCertificateInfoForm.NAME_DOC, form.getNameDoc());

		return new MapValuesSource(source);
	}

	protected void updateOperation(EditIdentifierBasketOperation operation, Map<String, Object> result)
	{
		operation.setNumber((String) result.get(RegistrationCertificateInfoForm.NUMBER));
		operation.setSeries((String) result.get(RegistrationCertificateInfoForm.SERIES));
		operation.setName((String) result.get(RegistrationCertificateInfoForm.NAME_DOC));
	}
}
