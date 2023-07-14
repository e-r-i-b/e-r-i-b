package com.rssl.phizic.web.certification;

import com.rssl.phizic.operations.certification.LoadCertificateOperation;
import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.validators.FileNotEmptyValidator;
import com.rssl.phizic.web.validators.FileSizeLimitValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 21.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoadCertificateAction extends OperationalActionBase
{
    public static final String FORWARD_START = "Start";
	public static final String FORWARD_CONFIRM = "Confirm";
	private static final int MAX_FILE_SIZE = 100*1000;

    protected Map getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.upload", "upload");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception
    {
	    LoadCertificateForm frm = (LoadCertificateForm) form;

	    return mapping.findForward(FORWARD_START);
    }

    public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception
    {

	    ActionMessages messages =  new ActionMessages();

	    LoadCertificateForm frm = (LoadCertificateForm)form;
	    LoadCertificateOperation operation = createOperation(LoadCertificateOperation.class);
	    operation.initialize();

	    messages.add(validate(frm));

	    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getCertificate()));

	    if(messages.size()>0)
	    {
		    saveErrors(request, messages);
	        return mapping.findForward(FORWARD_START);
	    }
	    else
	    {

		    //записываем во временную директорию для дальнейшей загрузки 
		    writeToTempFile(frm.getCertFilePath() );
		    writeToTempFile(frm.getCertAnswerFilePath() );

		    String path = mapping.findForward(FORWARD_CONFIRM).getPath()
				    + "?cert=" + frm.getCertFilePath().getFileName()
				    + "&certAns=" + frm.getCertAnswerFilePath().getFileName()
				    + "&id=" + frm.getId();

		    return new ActionForward(path,true);
	    }
    }

	private ActionMessages validate(LoadCertificateForm frm)
	{
	    ActionMessages messages = new ActionMessages();

	    ActionMessages resMess = FileNotEmptyValidator.validate(frm.getCertAnswerFilePath());
		resMess.add(FileSizeLimitValidator.validate(frm.getCertAnswerFilePath(), MAX_FILE_SIZE));
	    if(resMess.size() > 0)
	    {
			messages.add(resMess);
	    }

	    resMess = FileNotEmptyValidator.validate(frm.getCertFilePath());
		resMess.add(FileSizeLimitValidator.validate(frm.getCertFilePath(), MAX_FILE_SIZE));
	    if(resMess.size() > 0)
	    {
			messages.add(resMess);
	    }		
		return messages;
	}

	private void writeToTempFile(FormFile file) throws IOException
	{
	    String dirPath = TemporyFileHelper.getTemporaryDirPath(currentServletContext());
		String fullPath = dirPath + file.getFileName();

		FileHelper.write(file.getInputStream(), fullPath);
	}
}
