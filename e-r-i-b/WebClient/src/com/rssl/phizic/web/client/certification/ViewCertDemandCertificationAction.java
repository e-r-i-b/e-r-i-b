package com.rssl.phizic.web.client.certification;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.certification.ViewCertDemandClientOperation;
import com.rssl.phizic.operations.certification.WrongCertificateStatusException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.business.certification.CertDemandStatus;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 22.05.2007
 * @ $Author$
 * @ $Revision$
 */

public class ViewCertDemandCertificationAction extends ViewActionBase
{
    protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.install","install");

        return map;
    }

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, WrongCertificateStatusException
	{
		ViewCertDemandClientOperation operation = createOperation(ViewCertDemandClientOperation.class);
		operation.initialize(frm.getId());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException
	{
		ViewCertDemandCertificationForm frm = (ViewCertDemandCertificationForm) form;
		ViewCertDemandClientOperation   op  = (ViewCertDemandClientOperation) operation;

		frm.setPerson(op.getCertPerson());
		frm.setDemand(op.getEntity());
	}

	public ActionForward install(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                             HttpServletResponse response)
            throws Exception
	{
		ViewCertDemandCertificationForm frm = (ViewCertDemandCertificationForm) form;

		if(frm.getId() == null)
			throw new BusinessException("Не установлен запрос на сертификат");

		ViewCertDemandClientOperation operation = (ViewCertDemandClientOperation) createViewEntityOperation(frm);
		CertDemand certDemand = operation.getEntity();

		addLogParameters(new BeanLogParemetersReader("Устанавливаемая сущность", operation.getEntity()));

		if(certDemand.getStatus().equals(CertDemandStatus.STATUS_CERT_GIVEN) || certDemand.getStatus().equals(CertDemandStatus.STATUS_CERT_INSTALED))
		{
		    frm.setCertAnswer( certDemand.getCertRequestAnswer() );
		    frm.setCertAnswerFile( certDemand.getCertRequestAnswerFile() );
			frm.setInstall(true);
			frm.setDemand( certDemand);
			operation.install();
			return start(mapping, form, request, response);
		}
		else throw new BusinessException("Не соответсвующий статус запроса c id:"+frm.getId());

	}
}

