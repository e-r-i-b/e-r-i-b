package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile.documents;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.operations.userprofile.EditIdentifierBasketOperation;
import com.rssl.phizic.operations.userprofile.RemoveIdentifierBasketOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Базовый экшн для добавления, редактирования и удаления дополнительных документов клиента
 *
 * @author EgorovaA
 * @ created 19.06.14
 * @ $Author$
 * @ $Revision$
 */
public class AdditionalDocumentInfoAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>(3);
		keyMethodMap.put("add", "add");
		keyMethodMap.put("remove", "remove");
		keyMethodMap.put("update", "update");
		return keyMethodMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * Добавление нового дополнительного документа
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AdditionalDocumentInfoForm frm = (AdditionalDocumentInfoForm) form;

		EditIdentifierBasketOperation operation = createOperation(EditIdentifierBasketOperation.class);
		operation.initializeNew(getDocumentType().toString());

		FormProcessor<ActionMessages, ?> processor = getProcessor(frm);
		if (!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}
		Map<String, Object> result = processor.getResult();
		updateOperation(operation, result);
		operation.save();

		return start(mapping, form, request, response);
	}

	/**
	 * Редактирование дополнительного документа
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AdditionalDocumentInfoForm frm = (AdditionalDocumentInfoForm) form;

		EditIdentifierBasketOperation operation = createOperation(EditIdentifierBasketOperation.class);
		operation.initialize(frm.getId(), getDocumentType());

		FormProcessor<ActionMessages, ?> processor = getProcessor(frm);
		if (!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}
		Map<String, Object> result = processor.getResult();
		updateOperation(operation, result);
		operation.save();

		return start(mapping, form, request, response);
	}

	/**
	 * Удаление дополнительного документа
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AdditionalDocumentInfoForm frm = (AdditionalDocumentInfoForm) form;
		RemoveIdentifierBasketOperation operation = createOperation(RemoveIdentifierBasketOperation.class);
		operation.initialize(frm.getId(), getDocumentType());
		operation.save();
		return start(mapping, form, request, response);
	}

	/**
	 * Определение типа документа
	 * @return строка с типом документа
	 */
	protected DocumentType getDocumentType()
	{
		return null;
	}

	protected FormProcessor<ActionMessages, ?> getProcessor(AdditionalDocumentInfoForm frm)
	{
		return FormHelper.newInstance(getValuesSource(frm), AdditionalDocumentInfoForm.FORM);
	}

	protected FieldValuesSource getValuesSource(AdditionalDocumentInfoForm frm)
	{
		Map<String, String> source = new HashMap<String, String>();
		source.put(DrivingLicenceInfoForm.NUMBER, frm.getNumber());

		return new MapValuesSource(source);
	}

	/**
	 * Обновить данными операцию редактирования документа
	 * @param operation - операция редактирования документа
	 * @param result - результат обработки формпроцессора
	 */
	protected void updateOperation(EditIdentifierBasketOperation operation, Map<String, Object> result)
	{
		operation.setNumber((String) result.get(DrivingLicenceInfoForm.NUMBER));
	}
}
