package com.rssl.phizic.web.templates;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.templates.TemplatePackageListOperation;
import com.rssl.phizic.operations.templates.DictionaryPackagesListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.template.ClientsPackageTemplate;
import com.rssl.phizic.business.template.PackageService;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

/**
 * User: Novikov_A
 * Date: 16.02.2007
 * Time: 16:06:16
 */
public class ShowDictionaryPackageAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap ()
	{
	    Map<String,String> map=new HashMap<String, String>();
		map.put("button.dict.choose", "save");

	    return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(TemplatePackageListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		ShowDictionaryPackageForm       frm                 = (ShowDictionaryPackageForm) form;
		DictionaryPackagesListOperation operationDictionary = createOperation(DictionaryPackagesListOperation.class);
		operationDictionary.initialize(frm.getPerson());

	    List<ClientsPackageTemplate> listClientsPackage = operationDictionary.getClientsPackageTemplates();
	    List<String> listId = new ArrayList<String>();

	    for (ClientsPackageTemplate packageTemplate : listClientsPackage)
		    listId.add(packageTemplate.getPackageTemplate().getId().toString());

        frm.setSelectedIds(listId.toArray(new String[listId.size()]));
		frm.setActivePerson(operationDictionary.getActivePerson());
    }

	public ActionForward save ( ActionMapping mapping, ActionForm form,
	                            HttpServletRequest request,
	                            HttpServletResponse response ) throws Exception
	{
		ShowDictionaryPackageForm frm = (ShowDictionaryPackageForm)form;
		DictionaryPackagesListOperation operationDictionary = new DictionaryPackagesListOperation();
	    operationDictionary.initialize( frm.getPerson() );
	    ActivePerson person = operationDictionary.getActivePerson();
		List<ClientsPackageTemplate> listClientsPackage = operationDictionary.getClientsPackageTemplates();

		PackageService packageService = new PackageService();
		PackageTemplate packageTemplate = null;
		String[] list = frm.getSelectedIds();

		if (listClientsPackage.size() == 0)
		{
		   ClientsPackageTemplate clientsPackageTemplate = new ClientsPackageTemplate();
		   clientsPackageTemplate.setLogin(person.getLogin());

		   for (String id : list)
		   {
			  packageTemplate =  packageService.getTemplateById(Long.parseLong(id));
			  if (packageTemplate != null)
				  clientsPackageTemplate.setPackageTemplate(packageTemplate);

              operationDictionary.addClientsPackage(clientsPackageTemplate);
		   }
		}
		else
		{
		   for ( ClientsPackageTemplate packages : listClientsPackage)
		   {
			   for (String id : list)
			   {
				  packageTemplate =  packageService.getTemplateById(Long.parseLong(id));
				  if (packageTemplate != null)
					  packages.setPackageTemplate(packageTemplate);
				   
			      operationDictionary.editClientsPackage(packages);
			   }
		   }
		}

       return start(mapping, form, request, response);
	}
}
