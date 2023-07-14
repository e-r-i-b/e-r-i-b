package com.rssl.phizic.web.audit;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.operations.templates.EditTemplateDocumentOperation;
import com.rssl.phizic.operations.templates.EditTemplatePackageOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.operations.forms.GetPaymentFormListOperation;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 03.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class PrintDocumentsAction extends OperationalActionBase
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

	    PrintDocumentsForm frm = (PrintDocumentsForm) form;
	    String[] params = frm.getParams().split("endofpack;");
	    String[] paymentsId = frm.getPaymentsId();

	    List<PackageTemplate> packagesList = getPackageList(params[0]);
	    List<DocTemplate> templateList = new ArrayList<DocTemplate>();
	    if(params.length>1)
			templateList = getTemplateList(params[1]);

	    GetPaymentFormListOperation getFormOperation = createOperation(GetPaymentFormListOperation.class);

	    List<BusinessDocument> businessDocumentList = new ArrayList<BusinessDocument>();
	    List<Map<String, String>> fieldValues = new ArrayList<Map<String, String>>();
	    List<List<PackageTemplate>> packagesForBusinessDocument = new ArrayList<List<PackageTemplate>>();
	    List<List<DocTemplate>> templatesForBusinessDocument = new ArrayList<List<DocTemplate>>();

	    for(String id: paymentsId)
	    {
		    ExistingSource source = new ExistingSource(Long.parseLong(id), new NullDocumentValidator());
	        ViewDocumentOperation operation = createOperation(ViewDocumentOperation.class,"ViewPaymentList");
	        operation.initialize(source);
		    businessDocumentList.add(operation.getEntity());
		    fieldValues.add(operation.getFieldValues());
		    packagesForBusinessDocument.add(selectPackagesForBusinessDocument(packagesList,Long.parseLong(id),getFormOperation));
		    templatesForBusinessDocument.add(selectTemplatesForBusinessDocument(templateList,Long.parseLong(id),getFormOperation));

		    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
	    }
	    
	    frm.setBusinessDocuments(businessDocumentList);
	    frm.setFieldValues(fieldValues);
	    frm.setPackages(packagesForBusinessDocument);
	    frm.setTemplates(templatesForBusinessDocument);
	    return mapping.findForward(FORWARD_PRINT);
    }

	private List<PackageTemplate> selectPackagesForBusinessDocument(List<PackageTemplate> packages, Long paymentId,
	                                                                            GetPaymentFormListOperation operation) throws Exception
	{
		List<PackageTemplate> packageList = new ArrayList<PackageTemplate>();
		MetadataBean paymentForm = operation.getFormByPaymentId(paymentId);
		List<PackageTemplate> formPackages = paymentForm.getPackages();
		for(PackageTemplate packageTemp: packages)
		{
			for(PackageTemplate formPack: formPackages)
			{
				if(packageTemp.getId().equals(formPack.getId()))
					packageList.add(packageTemp);
			}
		}
		return packageList;
	}

	private List<DocTemplate> selectTemplatesForBusinessDocument(List<DocTemplate> templates, Long paymentId,
																				GetPaymentFormListOperation operation) throws Exception
	{
		List<DocTemplate> templateList = new ArrayList<DocTemplate>();
		MetadataBean paymentForm = operation.getFormByPaymentId(paymentId);
		List<DocTemplate> formTemplates = paymentForm.getTemplates();
		for(DocTemplate template: templates)
		{
			for(DocTemplate formTemplate: formTemplates)
			{
				if(template.getId().equals(formTemplate.getId()))
					templateList.add(template);
			}
		}
		return templateList;
	}

	private List<PackageTemplate> getPackageList(String params) throws Exception
	{
		EditTemplatePackageOperation operation = createOperation(EditTemplatePackageOperation.class);
		List<PackageTemplate> packageList = new ArrayList<PackageTemplate>();
		String[] pack = params.split("package:");
		for(int i=1;i<pack.length;i++)
		{
			String[] temp = pack[i].split("templates:");
			if(temp.length>1)
			{
				//operation.getTemplateById на самом деле должно называться operation.getPackageTemplateById
				PackageTemplate packageTemplate = operation.getTemplateById(Long.decode(temp[0]));
				List<DocTemplate> templateList = getTemplateList(temp[1]);
				packageTemplate.setTemplates(templateList);
				packageList.add(packageTemplate);
			}
		}
		return packageList;
	}

	private List<DocTemplate> getTemplateList(String params) throws Exception
	{
		EditTemplateDocumentOperation operation = createOperation(EditTemplateDocumentOperation.class);
		List<DocTemplate> templateList = new ArrayList<DocTemplate>();
		String[] temp = params.split("temp:");
		for(int i=1;i<temp.length;i++)
		{
			operation.initialize(Long.decode(temp[i]));
			DocTemplate template = operation.getEntity();
			templateList.add(template);
		}
		return templateList;
	}
		
}
