package com.rssl.phizic.web.certification;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.certification.InvalidAnswerFileName;
import com.rssl.phizic.operations.certification.InvalidCertificateFileName;
import com.rssl.phizic.operations.certification.LoadCertificateOperation;
import com.rssl.phizic.operations.certification.WrongCertificateOwner;
import com.rssl.phizic.security.crypto.Certificate;
import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

/**
 * @author Omeliyanchuk
 * @ created 21.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmLoadCertificateAction extends OperationalActionBase
{
    public static final String FORWARD_START = "Start";
	public static final String FORWARD_LIST = "List";

    protected Map getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.register", "register");
	    map.put("button.cancel", "cancel");

        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception
    {
	    try
	    {
			ActionMessages messages = new ActionMessages();
			ConfirmLoadCertificateForm frm = (ConfirmLoadCertificateForm) form;
			LoadCertificateOperation operation = createOperation(LoadCertificateOperation.class);
		    operation.initialize();

			if( ( frm.getCert() == null )||( frm.getCert().equals("") )
					|| ( frm.getCertAns() == null )||( frm.getCertAns().equals("") ) )
			{
				showError(messages, "com.rssl.phizic.web.certificate.error.filesNotFound");
			}

		    ServletContext servletContext = currentServletContext();
		    InputStream certIn = TemporyFileHelper.getTempFileStream(servletContext, frm.getCert());
		    InputStream certAnsIn = TemporyFileHelper.getTempFileStream(servletContext, frm.getCertAns());

			byte[] certData = FileHelper.readFile( certIn );
		    byte[] certAnsData = FileHelper.readFile(certAnsIn);

		    certIn.close();
		    certAnsIn.close();

			operation.setDemand(frm.getId());
		    operation.setCeritficateAnswerFromFile(certAnsData);
		    operation.setCertificateFromFile(certData);
		    operation.setCertAnswFileName( frm.getCertAns() );
		    operation.setCertFileName( frm.getCert());
			Certificate cert = operation.getCertificate();
			try
			{
				operation.validateCertificate();
			}
			catch(WrongCertificateOwner ex)
			{
				showError(messages, "com.rssl.phizic.web.certificate.error.wrongCertName");
			}
		    catch(InvalidCertificateFileName ex)
		    {
			    showError(messages, "com.rssl.phizic.web.certificate.error.certFileNameNotMatch");
		    }
		    catch(InvalidAnswerFileName ex)
		    {
				showError(messages, "com.rssl.phizic.web.certificate.error.certAnswFileNameNotMatch");
		    }

			frm.setCertificate(cert);

		    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", cert));

		    if( messages.size()!=0)
		        saveMessages(request, messages);
	    }
		catch(Exception ex)
		{
			cleanUp(request, (ConfirmLoadCertificateForm)form);
			throw ex;
		}

	    return mapping.findForward(FORWARD_START);
    }

    public ActionForward register(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception
    {
	    try
	    {
			ActionMessages messages = new ActionMessages();
			ConfirmLoadCertificateForm frm = (ConfirmLoadCertificateForm) form;
			LoadCertificateOperation operation = createOperation(LoadCertificateOperation.class);
		    operation.initialize();

		    ServletContext servletContext = currentServletContext();
		    InputStream certIn = TemporyFileHelper.getTempFileStream(servletContext, frm.getCert());
			byte[] certData = FileHelper.readFile(certIn);
		    certIn.close();

			InputStream certAnsIn = TemporyFileHelper.getTempFileStream(servletContext, frm.getCertAns());
			byte[] certAnsData = FileHelper.readFile( certAnsIn );
		    certAnsIn.close();

			try
			{
				operation.setCertAnswFileName( frm.getCertAns() );
		        operation.setCertFileName( frm.getCert());
				operation.setDemand(frm.getId());
				operation.setCertificateFromFile(certData);
				operation.setCertFileName( frm.getCertAns() );
				operation.load( certAnsData );

				addLogParameters(new BeanLogParemetersReader("Регистрируемая сущность", operation.getCertificate()));
			}
			catch(BusinessLogicException ex)
			{
				showError(messages, ex.getMessage());
			}
			cleanUp(request, (ConfirmLoadCertificateForm)form);
		    
		    if( messages.size()!=0)
		        saveMessages(request, messages);
	    }
	    catch(Exception ex)
	    {
			cleanUp(request, (ConfirmLoadCertificateForm)form);
		    throw ex;
	    }
	    return mapping.findForward( FORWARD_LIST );

    }

    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception
    {
	    ConfirmLoadCertificateForm frm = (ConfirmLoadCertificateForm) form;
		cleanUp(request, (ConfirmLoadCertificateForm)form);
	    return mapping.findForward( FORWARD_LIST );
    }

	private void showError(ActionMessages messages, String err)
	{
		ActionMessage message = new ActionMessage(err);
		messages.add("certification", message);
	}

	private void cleanUp(HttpServletRequest request, ConfirmLoadCertificateForm frm) throws BusinessException
	{
		ServletContext servletContext = currentServletContext();
		TemporyFileHelper.deleteTempFile(servletContext, frm.getCert());
	    TemporyFileHelper.deleteTempFile(servletContext, frm.getCertAns());
	}
	

}
