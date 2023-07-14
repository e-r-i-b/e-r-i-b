package com.rssl.phizic.web.skins;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.skins.EditPersonUIOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Balovtsev
 * Date: 23.05.2011
 * Time: 14:55:16
 */
public class EditPersonUIAction extends EditActionBase
{
	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditPersonUIForm form = (EditPersonUIForm) frm;
		EditPersonUIOperation operation = createOperation("ViewPersonUIOperation");
		operation.init(form.getPerson());
		return operation;
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditPersonUIForm form = (EditPersonUIForm) frm;
		EditPersonUIOperation operation = createOperation("EditPersonUIOperation");
		operation.init(form.getPerson());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditPersonUIForm.UI_PERSON_FORM;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.save(mapping, form, request, response);
		String url = request.getServletPath() + "?" + request.getQueryString();
		return new ActionForward(url, true);
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception {}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception	{}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{   
		EditPersonUIForm uiForm = (EditPersonUIForm) frm;
		EditPersonUIOperation uiOperation = (EditPersonUIOperation) operation;

		uiForm.setSkins( uiOperation.getPersonSkins() );
		uiForm.setSkin(uiOperation.getPersonSkin());
		uiForm.setActivePerson( uiOperation.getEntity() );
		uiForm.setField("currentSkin",uiOperation.getPersonActiveSkin().getName());
	}

	protected void updateOperationAdditionalData(EditEntityOperation op, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditPersonUIOperation operation = (EditPersonUIOperation) op;
		if (validationResult.get("skinType").equals("personal"))
		{
			operation.updatePersonSkin( (Long) validationResult.get("skin") );
		}
		else
		{
			operation.updatePersonSkin(null);
		}

		Person person = operation.getEntity();
		Skin skin = operation.getPersonSkin();
		if(skin == null)
		{
			addLogParameters(new SimpleLogParametersReader("ѕользователю ID="+ person.getId() +" присвоен стандартный стиль", null));
		}
		else
		{
			addLogParameters(new SimpleLogParametersReader("ѕользователю ID="+ person.getId() +" присвоен персональный стиль с ID=", skin.getId()));
		}
	}
}
