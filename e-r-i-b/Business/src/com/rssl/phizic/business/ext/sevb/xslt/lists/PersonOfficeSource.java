package com.rssl.phizic.business.ext.sevb.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.xslt.lists.EntityListBuilder;
import com.rssl.phizic.business.xslt.lists.EntityListSource;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Egorova
 * @ created 31.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class PersonOfficeSource implements EntityListSource
{
	public Source getSource( final Map<String,String> params ) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder();
		return new StreamSource(new StringReader(builder.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder();
		try
		{
			return XmlHelper.parse(builder.toString());
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private EntityListBuilder getEntityListBuilder() throws BusinessException
	{
		ActivePerson curPerson = getCurrentPerson();

        if(curPerson==null)
            throw new BusinessException("Ќевозможно найти текущего пользовател€");

		EntityListBuilder builder = new EntityListBuilder();

		builder.openEntityListTag();
	    builder.openEntityTag("personsOffice");

		builder.appentField("fullName", curPerson.getFullName());
		builder.appentField("isResident", nullSafeBooleanToString(curPerson.getIsResident()) );

	    Department department = new DepartmentService().findById(curPerson.getDepartmentId());
	    if (department != null)
	    {
	        builder.appentField("currentDepartmentId", Long.toString( department.getId() ) );
	        appendOfficeFields(builder, department,"office");
	    }
		builder.closeEntityTag();
		builder.closeEntityListTag();
		return builder;
	}

	private ActivePerson getCurrentPerson()
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
        return provider.getPersonData().getPerson();
	}

	private void appendOfficeFields(EntityListBuilder builder, Department department,String prefix)
	{
		if (department.getSynchKey() == null)
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
