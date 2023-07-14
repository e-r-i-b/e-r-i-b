package com.rssl.phizic.web.audit;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.templates.DownloadTemplateOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.business.template.DocTemplate;

import java.util.Map;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 11.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class PrintHTMLTemplateAction extends OperationalActionBase
{
	private static final String FORWARD_PRINT = "Print";

	protected Map getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
        return map;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception
    {
	    PrintHTMLTemplateForm frm = (PrintHTMLTemplateForm) form;
	    DownloadTemplateOperation downloadTemplateOperation = createOperation(DownloadTemplateOperation.class, "TemplatesDocumentsManagement");
		downloadTemplateOperation.initialize(frm.getTemplateId());

	    ExistingSource source = new ExistingSource(frm.getDocumentId(), new NullDocumentValidator());
	    ViewDocumentOperation viewDocumentOperation = createOperation(ViewDocumentOperation.class,"ViewPaymentList");
	    viewDocumentOperation.initialize(source);

	    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", viewDocumentOperation.getEntity()));

	    DocTemplate template = downloadTemplateOperation.getTemplate();
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		buffer.write(template.getData());
	    String html = buffer.toString();	    

	    Map<String, String> fieldValues = viewDocumentOperation.getFieldValues();
	    for(String key: fieldValues.keySet())
	    {
		    if(!"state".equals(key))
				html = html.replaceAll("<!--#"+key+"#-->",fieldValues.get(key));
		    else
		    switch(fieldValues.get(key).toCharArray()[0]){
			    case 'I':html = html.replaceAll("<!--#"+key+"#-->","Введен");
          		case 'W':html = html.replaceAll("<!--#"+key+"#-->","Обрабатывается");
				case 'S':html = html.replaceAll("<!--#"+key+"#-->","Исполнен");
				case 'E':html = html.replaceAll("<!--#"+key+"#-->","Отказан");
          		case 'D':html = html.replaceAll("<!--#"+key+"#-->","Отказан");
				case 'V':html = html.replaceAll("<!--#"+key+"#-->","Отозван");
		    }
	    }
	    frm.setHtml(html);
	    return mapping.findForward(FORWARD_PRINT);
    }
}
