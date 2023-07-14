package com.rssl.phizic.web.locale;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import org.apache.struts.action.*;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ������� ����� ��� �������������� ������� ���������
 * @author koptyaev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("UnusedParameters")
public abstract class  EditLanguageResourcesBaseAction extends AsyncOperationalActionBase
{
	private static final String FORWARD_SUCCESS = "Success";

	protected Map<String, String> getKeyMethodMap()
	{
		return Collections.singletonMap("button.save", "save");
	}
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditLanguageResourcesBaseForm frm = (EditLanguageResourcesBaseForm) form;
		EditLanguageResourcesOperation operation = createEditOperation(frm);
		initializeOperation(operation,frm);
		frm.setLocale(operation.getLocale());
		updateForm(frm, operation.getEntity());
		updateFormOperationalData(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	protected void initializeOperation(EditLanguageResourcesOperation operation, EditLanguageResourcesBaseForm frm) throws BusinessLogicException, BusinessException
	{
		//noinspection unchecked
		operation.initialize(frm.getId(), frm.getLocaleId());
	}

	/**
	 * �������� �������� ������� � �����
	 * @param frm �����
	 * @param entity ��������
	 * @throws Exception
	 */
	protected abstract void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception;

	/**
	 * �������� ����� ������� ��������
	 * @param frm �����
	 * @param operation ��������
	 * @throws Exception
	 */
	protected abstract void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception;
	/**
	 * ��������� ��������� ��� ��������� ������
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditLanguageResourcesBaseForm frm = (EditLanguageResourcesBaseForm) form;
		EditLanguageResourcesOperation operation = createEditOperation(frm);
		initializeOperation(operation, frm);
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		Form editLocaleForm = getEditForm(frm);

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editLocaleForm);
		if (processor.process())
		{
			try
			{
				updateEntity(operation.getEntity(), processor.getResult());
				updateEntityOperationalData(operation, processor.getResult());
				operation.save();
			}
			catch (BusinessLogicException e)
			{
				ActionMessages msg = new ActionMessages();
				msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				saveErrors(request, msg);
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}

	/**
	 * ��������� �������� ������� �� ��������������
	 * @param entity ��������
	 * @param data ������
	 * @throws Exception
	 */
	protected abstract void updateEntity(LanguageResources entity ,Map<String, Object> data) throws Exception;

	/**
	 * �������� �������� ������� ��������������
	 * @param operation ��������
	 * @param data ������ ��������������
	 * @throws Exception
	 */
	protected abstract void updateEntityOperationalData(EditEntityOperation operation ,Map<String, Object> data) throws Exception;

	/**
	 * �������� ���������� �����
	 * @param frm ���������� �����
	 * @return ���������� �����
	 */
	protected abstract Form getEditForm(EditLanguageResourcesBaseForm frm);

	/**
	 * ������� �������� ��������������
	 * @param frm ���������� �����
	 * @return ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected abstract EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException;

	protected boolean isAjax()
	{
		return true;
	}

}
