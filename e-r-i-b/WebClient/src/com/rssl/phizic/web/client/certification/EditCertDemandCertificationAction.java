package com.rssl.phizic.web.client.certification;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.business.certification.CertDemandStatus;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.certification.ConfirmCertDemandClientOperation;
import com.rssl.phizic.operations.certification.EditCertDemandCertificationOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 17.11.2006 Time: 17:01:38 To change this template use
 * File | Settings | File Templates.
 */
public class EditCertDemandCertificationAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_CONFIRM = "Confirm";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
        map.put("button.confirm", "confirm");
	    map.put("button.install","install");
	    map.put("button.save","save");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception
    {
	    //edit,confirm,view

	    EditCertDemandCertificationForm frm = (EditCertDemandCertificationForm) form;

	    EditCertDemandCertificationOperation operation =
			    createOperation( EditCertDemandCertificationOperation.class);

	    if(frm.getId() == null)
	    {
		    //создание запроса на сертификат, данные из системы
		    ActivePerson person = operation.getCurrentPerson( AuthenticationContext.getContext().getLogin());
		    frm.setPerson( person );

			Date date = Calendar.getInstance().getTime();
			frm.setDate( date );
			frm.setDateString( DateHelper.toStringTime( date ) );
			frm.setPersonId( frm.getPerson().getId() );

		    addLogParameters(new CompositeLogParametersReader(
				        new SimpleLogParametersReader("Запрос на сертификат", "Ф.И.О. клиента", person.getFullName()),
				        new SimpleLogParametersReader("Дата создания запроса", String.format("%1$td.%1$tm.%1$tY", date))
				    ));
	    }
	    else
	    {
		    operation.initialize(frm.getId());

		    CertDemand certDemand = operation.getCertDemand();
		    frm.setDate( certDemand.getIssueDate().getTime() );
			frm.setPerson( operation.getCertPerson() );
		    frm.setDemand( certDemand);

		    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", certDemand));
	    }

	    return mapping.findForward(FORWARD_START);
    }

	public ActionForward install(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                             HttpServletResponse response)
            throws Exception
	{
		EditCertDemandCertificationForm frm = (EditCertDemandCertificationForm) form;

		if(frm.getId() == null)
			throw new BusinessException("Не установлен запрос на сертификат");
		
		EditCertDemandCertificationOperation operation =
		    createOperation( EditCertDemandCertificationOperation.class);
		operation.initialize(frm.getId());
		CertDemand certDemand = operation.getCertDemand();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", certDemand));

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

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                             HttpServletResponse response) throws Exception
	{
		EditCertDemandCertificationForm frm = (EditCertDemandCertificationForm) form;
	    ConfirmCertDemandClientOperation operation =
			    createOperation( ConfirmCertDemandClientOperation.class);
		
		operation.createConfirmRequest(request.getSession().getId());
		operation.confirm();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		return start(mapping, form, request, response);
	}

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                             HttpServletResponse response)
            throws Exception
    {
	    EditCertDemandCertificationForm frm = (EditCertDemandCertificationForm) form;
	    EditCertDemandCertificationOperation operation =
			    createOperation( EditCertDemandCertificationOperation.class);

	    operation.intializeNew();
	    operation.setPerson(frm.getPersonId());
	    operation.setDate( DateHelper.parseStringTime( frm.getDateString() ) );
	    operation.setRequest( frm.getCertRequest(), frm.getCertRequestFile() );
	    CertDemand demand = operation.add();

	    addLogParameters(new BeanLogParemetersReader("Сохраняемая сущность", demand));

	    return new ActionForward( mapping.findForward(FORWARD_CONFIRM).getPath() + "?id=" + demand.getId(), true);
    }
}
