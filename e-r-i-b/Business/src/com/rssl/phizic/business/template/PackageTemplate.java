package com.rssl.phizic.business.template;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * TODO Что это такое? НИче непонимаю
 * Created by IntelliJ IDEA. User: Novikov_A Date: 22.01.2007 Time: 13:12:46 To change this template use File
 * | Settings | File Templates.
 */
public class PackageTemplate implements Serializable
{
	private Long id;
	private String name;
	private String description;
	private Long departmentId;
    private List<DocTemplate> templates;

	public Long getId()
	{
	   return id;
	}

	public void setId(Long id)
	{
	   this.id = id;
	}

	public String getName()
    {
	   return name;
	}

	public void setName(String name)
	{
	  this.name = name;
	}

	public void setDepartmentId(Long departmentId)
	{
	   this.departmentId = departmentId;
	}

    public Long getDepartmentId()
	{
	   return departmentId;
	}

	public String getDescription()
    {
	   return description;
	}

	public void setDescription(String description)
	{
	  this.description = description;
	}

	public List getTemplates()
	{
	    if (templates == null)
	        return new ArrayList<DocTemplate>();
		return templates;
	}

	public void setTemplates(List<DocTemplate> templates)
	{
	   this.templates = templates;
	}

}
