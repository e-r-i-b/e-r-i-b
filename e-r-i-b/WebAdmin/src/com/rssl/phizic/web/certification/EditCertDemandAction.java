package com.rssl.phizic.web.certification;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.operations.certification.EditCertDemandAdminOperation;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.business.certification.CertDemandStatus;
import com.rssl.common.forms.Form;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 20.11.2006 Time: 17:20:50 To change this template use
 * File | Settings | File Templates.
 */
public class EditCertDemandAction extends EditActionBase
{
    protected Map getAdditionalKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.download", "download");
        return map;
    }

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		if(frm.getId() == null)
	    {
			throw new BusinessException("Не установлен запрос для отображения");
	    }

		EditCertDemandAdminOperation operation = createOperation( EditCertDemandAdminOperation.class);
		operation.initialize(frm.getId());

		return operation;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		CertDemand demand = (CertDemand) entity;

		frm.setField("id", demand.getId());
		frm.setField("date", demand.getIssueDate().getTime());
		frm.setField("status", demand.getStatus());
		frm.setField("refuseReason", demand.getRefuseReason());
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation)
	{
		EditCertDemandAdminOperation op = (EditCertDemandAdminOperation) operation;
		ActivePerson person = op.getPerson();

		frm.setField("surName",   person.getSurName());
		frm.setField("firstName", person.getFirstName());
		frm.setField("patrName",  person.getPatrName());
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditCertDemandForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data){}

	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                             HttpServletResponse response)
            throws Exception
	{
	    EditCertDemandForm frm = (EditCertDemandForm) form;

	    if(frm.getId() == null)
	    {
			throw new BusinessException("Не установлен запрос для выгрузки");
	    }
	    else
	    {
		    EditCertDemandAdminOperation operation =
			    createOperation( EditCertDemandAdminOperation.class);
		    operation.initialize(frm.getId());

		    CertDemand demand = operation.getEntity();

		    updateForm(frm, demand);
		    updateFormAdditionalData(frm, operation);

		    /*
		        для обновления статуса при загрузке файла.
		        Сначала обновляем статус,
		        потом с помощью javascript еще раз отправяем форму
		         и в ответ посылаем файл.
		     */
		    if( demand.getStatus().equals(CertDemandStatus.STATUS_SENDED) )
		    {
			    frm.setRefresh(true);
			    operation.setDemandToProcesed();
			    return mapping.findForward(FORWARD_START);
		    }
		    else
		        writeCertToOutput(demand.getCertRequestFile(), demand.getCertRequest(), response);

		    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
	    }

	    return null;
	}

	private void writeCertToOutput(String certFileName, String certRequest, HttpServletResponse response)
			throws IOException
	{
		response.setContentType("application/x-file-download");
		response.setHeader("Content-disposition", "attachment; filename=" + certFileName);

		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.print(certRequest);
		outputStream.flush();
		outputStream.close();

	}
}
