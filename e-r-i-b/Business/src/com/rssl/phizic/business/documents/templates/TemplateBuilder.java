package com.rssl.phizic.business.documents.templates;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.FieldInitalValuesSource;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.xslt.FormDataBuilder;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.metadata.source.SimpleFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.counter.DefaultNamingStrategy;
import com.rssl.phizic.utils.counter.NamingStrategy;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import javax.xml.transform.dom.DOMResult;

/**
 * @author khudyakov
 * @ created 06.02.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateBuilder
{
	private final TemplateDocument template;
	private final Metadata metadata;
	private final Client client;
	private final NamingStrategy namingStrategy;

	private String templateName;


	public TemplateBuilder(Metadata metadata) throws BusinessException, BusinessLogicException
	{
		this.metadata = metadata;
		this.template = metadata.createTemplate();
		this.client   = PersonHelper.getContextPerson().asClient();
		this.namingStrategy = new DefaultNamingStrategy();

		initializeNew(client);
	}

	public TemplateBuilder(Metadata metadata, Person person, String templateName) throws BusinessException, BusinessLogicException
	{
		this.metadata = metadata;
		this.template = metadata.createTemplate();
		this.client   = new ClientImpl(person);
		this.namingStrategy = new DefaultNamingStrategy();

		initialize(client, templateName);
	}

	public TemplateBuilder(Metadata metadata, Person person, NamingStrategy namingStrategy) throws BusinessException, BusinessLogicException
	{
		this.metadata = metadata;
		this.template = metadata.createTemplate();
		this.client   = new ClientImpl(person);
		this.namingStrategy = namingStrategy;

		initializeNew(client);
	}

	public TemplateBuilder(Metadata metadata, BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		this.metadata = metadata;
		this.template = metadata.createTemplate();
		this.client   = PersonHelper.getContextPerson().asClient();
		this.namingStrategy = new DefaultNamingStrategy();

		initializeNew(client, document);
	}

	public TemplateBuilder setInitialValuesSource(FieldValuesSource initialValuesSource) throws BusinessException, BusinessLogicException
	{
		XmlConverter converter = metadata.createConverter("xml");
		converter.setData(getFormDataBuilder(getFieldValueSource(initialValuesSource)).getFormData());

		Document document = XmlHelper.getDocumentBuilder().newDocument();
		converter.convert(new DOMResult(document));

		template.setFormData(new SimpleFieldValueSource(metadata, document));
		return this;
	}

	public TemplateBuilder copyInitialValuesSource(FieldValuesSource initialValuesSource) throws BusinessException, BusinessLogicException
	{
		template.setFormData(new SimpleFieldValueSource(initialValuesSource));
		return this;
	}

	public TemplateBuilder setClientCreationChannel(CreationType creationType)
	{
		template.setClientCreationChannel(creationType);
		return this;
	}

	/**
	 * @return шаблон
	 */
	public TemplateDocument build() throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(templateName))
		{
			templateName = TemplateHelper.addCounter((client), getValidName(template.generateDefaultName(metadata)), namingStrategy);
		}
		template.setTemplateInfo(TemplateHelper.createTemplateInfo(client, templateName));
		return template;
	}

	private void initializeNew(Client client) throws BusinessException, BusinessLogicException
	{
		try
		{
			template.initialize();
			template.setClientOwner(client);
			template.setOffice(client.getOffice());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private void initializeNew(Client client, BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		initializeNew(client);
		template.initialize(document);
		copyInitialValuesSource(new DocumentFieldValuesSource(metadata, document));
	}

	private void initialize(Client client, String templateName) throws BusinessException, BusinessLogicException
	{
		initializeNew(client);
		this.templateName = templateName;
	}

	private FieldValuesSource getFieldValueSource(FieldValuesSource initialValuesSource)
	{
		FieldValuesSource initialXPathValuesSource = new FieldInitalValuesSource(metadata.getForm(), initialValuesSource.getAllValues());
		return new CompositeFieldValuesSource(initialValuesSource, initialXPathValuesSource);
	}

	private FormDataBuilder getFormDataBuilder(FieldValuesSource source)
	{
		return new FormDataBuilder().appentAllFields(metadata.getForm(), source);
	}

	private String getValidName(String name)
	{
		return name.length() <= Constants.MAX_TEMPLATE_NAME_SIZE ? name : name.substring(0, Constants.MAX_TEMPLATE_NAME_SIZE);
	}
}
