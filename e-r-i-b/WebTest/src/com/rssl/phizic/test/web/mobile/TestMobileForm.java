package com.rssl.phizic.test.web.mobile;

import com.rssl.phizic.test.web.common.TestAbstractForm;

/**
 * @author Erkin
 * @ created 06.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileForm extends TestAbstractForm
{
	private String version;
	private String mobileSdkData;


	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getMobileSdkData()
	{
		return mobileSdkData;
	}

	public void setMobileSdkData(String mobileSdkData)
	{
		this.mobileSdkData = mobileSdkData;
	}
}
