package com.rssl.phizic.business.loanOffer;

/**
 * @author Mescheryakova
 * @ created 11.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class SettingLoanCardOfferLoad extends SettingLoanAbstract
{
	private String automaticLoadDirectory;
	private String automaticLoadFileName;

	public SettingLoanCardOfferLoad(String dir, String file, String automaticLoadDirectory, String automaticLoadFileName)
	{
		super(dir, file);
		this.automaticLoadDirectory = automaticLoadDirectory;
		this.automaticLoadFileName = automaticLoadFileName;
	}

	public SettingLoanCardOfferLoad()
	{
		super();
	}

	public String getAutomaticLoadDirectory()
	{
		return automaticLoadDirectory;
	}

	public void setAutomaticLoadDirectory(String automaticLoadDirectory)
	{
		this.automaticLoadDirectory = automaticLoadDirectory;
	}

	public String getAutomaticLoadFileName()
	{
		return automaticLoadFileName;
	}

	public void setAutomaticLoadFileName(String automaticLoadFileName)
	{
		this.automaticLoadFileName = automaticLoadFileName;
	}
}
