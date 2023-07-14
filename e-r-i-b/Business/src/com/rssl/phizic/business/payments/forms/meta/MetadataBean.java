package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.template.PackageTemplate;

import java.io.Serializable;
import java.util.Map;
import java.util.List;

/**
 * @author Roshka
 * @ created 11.07.2006
 * @ $Author$
 * @ $Revision$
 */

public class MetadataBean implements Serializable
{
	private Long                 id;
	private String               name;
	private String               description;
	private String               definition;
	private String               xmlTransformation;
	private Map<String, Object>  additionalAttributes;
	private List<DocTemplate>    templates;
	private List<PackageTemplate> packages;
	private String               stateMachine;
	private String               templateStateMachine;

	/**
	 * @return ID ����������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ID ���������� (for hibernate)
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� �����
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name ��� �����
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return �������� �����
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description ������� �����
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ������ XML - �������� ����� - PFD
	 */
	public String getDefinition()
	{
		return definition;
	}

	/**
	 * @param definition ������ XML - �������� ����� - PFD
	 */
	public void setDefinition(String definition)
	{
		this.definition = definition;
	}

	/**
	 * @return XML XSLT
	 */
	public String getXmlTransformation()
	{
		return xmlTransformation;
	}

	/**
	 * @param xmlTransformation XML XSLT
	 */
	public void setXmlTransformation(String xmlTransformation)
	{
		this.xmlTransformation = xmlTransformation;
	}

	public Map<String, Object> getAdditionalAttributes()
	{
		return additionalAttributes;
	}

	public void setAdditionalAttributes(Map<String, Object> additionalAttributes)
	{
		this.additionalAttributes = additionalAttributes;
	}

	public Object getAdditionalAttribute(String key)
	{
		return additionalAttributes.get(key);
	}

	public List<DocTemplate> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<DocTemplate> templates)
	{
		this.templates = templates;
	}

	public List<PackageTemplate> getPackages()
	{
		return packages;
	}

	public void setPackages(List<PackageTemplate> packages)
	{
		this.packages = packages;
	}

	/**
	 * @return  ������ ��������� ��� ������� ���������
	 */
	public String getStateMachine()
	{
		return stateMachine;
	}

	/**
	 *  ������ ������ ��������� ��� ������� ���������
	 */
	public void setStateMachine(String stateMachine)
	{
		this.stateMachine = stateMachine;
	}

	public String getTemplateStateMachine()
	{
		return templateStateMachine;
	}

	public void setTemplateStateMachine(String templateStateMachine)
	{
		this.templateStateMachine = templateStateMachine;
	}
}
