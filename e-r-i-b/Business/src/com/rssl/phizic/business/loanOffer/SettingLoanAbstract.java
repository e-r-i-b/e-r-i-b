package com.rssl.phizic.business.loanOffer;

/**
 * @author Mescheryakova
 * @ created 08.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class SettingLoanAbstract
{
	private Long id;
	private String directory;
	private String fileName;

	public SettingLoanAbstract()
	{
		
	}

	public SettingLoanAbstract(String dir, String file)
	{
		directory = dir;
		fileName = file;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDirectory()
	{
		return directory;
	}

	public void setDirectory(String directory)
	{
		this.directory = directory;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
}
