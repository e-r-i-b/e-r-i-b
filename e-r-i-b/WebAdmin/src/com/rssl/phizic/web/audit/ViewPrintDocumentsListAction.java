package com.rssl.phizic.web.audit;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.log.CollectionLogParametersReader;
import com.rssl.phizic.operations.forms.GetPaymentFormListOperation;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.business.template.DocTemplate;

import java.util.*;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 02.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class ViewPrintDocumentsListAction extends OperationalActionBase//TODO operationQuery??
{
	private static final String FORWARD_START = "Start";

	protected Map getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
        return map;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception
    {
	    ViewPrintDocumentsListForm frm = (ViewPrintDocumentsListForm) form;
	    GetPaymentFormListOperation operation = createOperation(GetPaymentFormListOperation.class);
	    String[] paymentsId = frm.getPaymentsId();
	    Map packages = new HashMap<Long,PackageTemplate>();
	    Map templates = new HashMap<Long,DocTemplate>();
	    for(int i=0;i<paymentsId.length;i++)
	    {
	        MetadataBean paymentForm = operation.getFormByPaymentId(Long.parseLong(paymentsId[i]));
		    for(PackageTemplate packageTemp :paymentForm.getPackages())
			    if(!packages.containsKey(packageTemp.getId()))
			        packages.put(packageTemp.getId(),packageTemp);
		    for(DocTemplate temp: paymentForm.getTemplates())
		        if(!templates.containsKey(temp.getId()))
		            templates.put(temp.getId(),temp);
        }
	    List<PackageTemplate> packagesList = new ArrayList<PackageTemplate>();
	    for(PackageTemplate pt: (Collection<PackageTemplate>)packages.values())
	        packagesList.add(pt);
	    frm.setPackages(packagesList);
	    List<DocTemplate> templatesList = new ArrayList<DocTemplate>();
	    for(DocTemplate dt: (Collection<DocTemplate>)templates.values())
	        templatesList.add(dt);
	    frm.setTemplates(templatesList);

	    addLogParameters(new CollectionLogParametersReader("Пакет шаблонов", packagesList));
	    addLogParameters(new CollectionLogParametersReader("Шаблоны документов", templatesList));

	    return mapping.findForward(FORWARD_START);
    }

}
