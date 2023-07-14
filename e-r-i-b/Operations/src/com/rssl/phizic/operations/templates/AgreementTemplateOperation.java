package com.rssl.phizic.operations.templates;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.template.ClientsPackageTemplatesService;
import com.rssl.phizic.business.template.ClientsPackageTemplate;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.ActivePerson;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 26.01.2007 Time: 13:37:52 To change this template use File
 * | Settings | File Templates.
 */
public class AgreementTemplateOperation extends OperationBase
{
	private Long packageId = null;
    protected static final PersonService service = new PersonService();
    private ActivePerson person;
	private List<DocTemplate> docTemplateList = new ArrayList<DocTemplate> ();

	public void setPerson(Long value) throws BusinessException
	{
		this.person   = null;

		if (value == null)
			return;

		ActivePerson temp = (ActivePerson) service.findById(value);

		if (temp == null)
			throw new BusinessException("Пользователь с id" + value + " не найден");

		this.person   = temp;

		ClientsPackageTemplatesService clientsPackageService = new ClientsPackageTemplatesService();
		List<ClientsPackageTemplate> listClientsPackage = clientsPackageService.getClientsPackageById(person.getLogin());

		PackageTemplate packageTmp;
        List<DocTemplate> list;

		if (listClientsPackage.size() != 0)
		{
           for (ClientsPackageTemplate packageTemplate : listClientsPackage)
           {
	           packageTmp = packageTemplate.getPackageTemplate();
	           if (packageTmp != null)
	           {
	              list = packageTmp.getTemplates();
		          for (DocTemplate doc : list)
		            docTemplateList.add(doc);
	           }
           }
		}
	}

	public ActivePerson getPerson()
    {
        return person;
    }

	public void setPackageId (Long packageId)
	{
		this.packageId = packageId;
	}

	public Long getPackageId ()
	{
		return this.packageId;
	}

	public List<DocTemplate> getDocTemplateList ()
	{
		return this.docTemplateList;
	}
}
