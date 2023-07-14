package com.rssl.phizic.business.ext.sbrf.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.xslt.lists.CachedEntityListSourceBase;
import com.rssl.phizic.business.xslt.lists.EntityListBuilder;
import com.rssl.phizic.business.xslt.lists.EntityListDefinition;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;
import java.util.Map;

/**
 * @author Egorova
 * @ created 31.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class PersonOfficeSource extends CachedEntityListSourceBase
{
	public PersonOfficeSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException
	{
		ActivePerson curPerson = getCurrentPerson();

        if(curPerson==null)
            throw new BusinessException("Ќевозможно найти текущего пользовател€");

		EntityListBuilder builder = new EntityListBuilder();

		builder.openEntityListTag();
	    builder.openEntityTag("personsOffice");

		Client client = curPerson.asClient();
		builder.appentField("fullName", curPerson.getFullName());
		builder.appentField("formatedPersonName", PersonHelper.getFormattedPersonName(curPerson));
		builder.appentField("isResident", nullSafeBooleanToString(curPerson.getIsResident()) );
		builder.appentField("homePhone", client.getHomePhone());
		builder.appentField("birthDay", String.format("%1$te.%1$tm.%1$tY", DateHelper.toDate(curPerson.getBirthDay())));
		builder.appentField("birthDayString", DateHelper.customFormatDateToMonthInWords(curPerson.getBirthDay(), "d MMMM yyyy") + " года");
		builder.appentField("jobPhone", client.getJobPhone());
		builder.appentField("mobilePhone", client.getMobilePhone());
		builder.appentField("address", client.getLegalAddress().getUnparseableAddress());

		String tarifPlanCodeType = PersonHelper.getActivePersonTarifPlanCode();
		builder.appentField("tarifPlanCodeType", tarifPlanCodeType);

		List<ClientDocument> docs = (List<ClientDocument>) client.getDocuments();
		if (!docs.isEmpty()) {
			int i = 0;
			ClientDocument doc = docs.get(i++);
			while (doc.getDocumentType() != ClientDocumentType.REGULAR_PASSPORT_RF && i < docs.size())
			{
				doc = docs.get(i++);
			}

			if (doc.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF)
			{
				String docSeries = doc.getDocSeries();
				if (StringHelper.isNotEmpty(docSeries))
				{
					if (docSeries.length() >= 4)
						docSeries = docSeries.substring(0, 2) + " " + docSeries.substring(2);
				}
				builder.appentField("documentSeries", docSeries.replaceAll("[0-9]", "*"));
				builder.appentField("documentNumber", StringHelper.isEmpty(doc.getDocNumber()) ? "" : doc.getDocNumber().replaceAll("[0-9]", "*"));
				builder.appentField("documentGivePlace", doc.getDocIssueBy());
				builder.appentField("documentGiveDate",  doc.getDocIssueDate() == null ? "" : String.format("%1$te.%1$tm.%1$tY", DateHelper.toDate(doc.getDocIssueDate())));
			}
		}

	    Department department = new DepartmentService().findById(curPerson.getDepartmentId());
	    if (department != null)
	    {
	        builder.appentField("currentDepartmentId", Long.toString( department.getId() ) );
	        appendOfficeFields(builder, department,"office");
	    }
		builder.closeEntityTag();
		builder.closeEntityListTag();
		return convertToReturnValue(builder.toString(), curPerson);
	}

	private ActivePerson getCurrentPerson()
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
        return provider.getPersonData().getPerson();
	}

	private void appendOfficeFields(EntityListBuilder builder, Department department,String prefix)
	{
		if (department == null)
			return;
		builder.appentField(prefix+"-name", department.getName());
		builder.appendObject(department.getCode(), prefix);
	}
	private String nullSafeBooleanToString(Boolean aBoolean)
	{
		if (aBoolean == null)
		{
			return null;
		}
		return aBoolean.toString();
	}
}
