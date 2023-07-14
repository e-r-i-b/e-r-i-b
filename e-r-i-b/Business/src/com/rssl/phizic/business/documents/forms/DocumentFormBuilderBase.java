package com.rssl.phizic.business.documents.forms;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.StringHelper;

import javax.xml.transform.Source;

/**
 * @author khudyakov
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class DocumentFormBuilderBase implements DocumentFormBuilder
{
	protected XmlConverter converter;

	public DocumentFormBuilderBase(Metadata metadata, TransformInfo transformInfo, FormInfo formInfo)
	{
		converter = metadata.createConverter(transformInfo.getTransformMode());
		converter.setParameter("mode",                  transformInfo.getTemplateMode());
		converter.setParameter("personAvailable",       PersonContext.isAvailable());
		converter.setParameter("application",           ApplicationInfo.getCurrentApplication());
		converter.setParameter("isAjax",                formInfo.isAjax());
		converter.setParameter("userVisitingMode",      AuthenticationContext.getContext().getVisitingMode().name());

		if (StringHelper.isNotEmpty(formInfo.getWebRoot()))
		{
			converter.setParameter("webRoot",               formInfo.getWebRoot());
		}
		if (StringHelper.isNotEmpty(formInfo.getResourceRoot()))
		{
			converter.setParameter("resourceRoot",          formInfo.getResourceRoot());
		}
		if (StringHelper.isNotEmpty(formInfo.getSkinUrl()))
		{
			converter.setParameter("skinUrl",   formInfo.getSkinUrl());
		}

		boolean isGuest = false;
		if ( PersonContext.isAvailable())
		{
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			if (person instanceof GuestPerson)
				isGuest = true;
		}
		converter.setParameter("isGuest", isGuest);
	}

	public String build(Source source)
	{
		converter.setData(source);
		return converter.convert().toString();
	}
}

