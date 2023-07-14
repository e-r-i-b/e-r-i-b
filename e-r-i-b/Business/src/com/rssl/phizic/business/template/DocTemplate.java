package com.rssl.phizic.business.template;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 22.01.2007 Time: 13:12:46 To change this template use File
 * | Settings | File Templates.
 */
public class DocTemplate implements DocTemplateBase
{
	private Long     id;
	private String   name;
	private String   description;
	private Calendar update;
	private Long     departmentId;
	private byte[]   data;
	private String   fileName;
	private String   fileType;

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

	public Long getDepartmentId()
	{
	   return departmentId;
	}

	public byte[] getData()
	{
	   return data;
	}

	public void setData(byte[] data)
	{
	   this.data = data;
	}

	public void setDepartmentId(Long departmentId)
	{
	   this.departmentId = departmentId;
	}

	public Calendar getUpdate()
    {
       if (update == null)
			update = Calendar.getInstance();	    
	   return update;
	}

	public void setUpdate(Calendar update)
	{
	  this.update = update;
	}

	public String getDescription()
    {
	   return description;
	}

	public void setDescription(String description)
	{
	  this.description = description;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	public String getUpdate2String()
	{
		return String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", update);
	}
}
