package com.rssl.phizic.web.client.mobileApplications;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * @ author: Gololobov
 * @ created: 05.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileApplicationsQRCodeForm extends EditFormBase
{
	//Тип платформы, для которой необходимо скачать мобильное приложенрие
	private String mobileApplication;
	//URL мобильного приложенрия для скачивания
	private String mobileApplicationDistribURL;
	//URL картинки с QR-кодом
	private String mobileApplicationQRImgName;
	//название платформы
	private String mobilePlatformName;

	public String getMobileApplication()
	{
		return mobileApplication;
	}

	public void setMobileApplication(String mobileApplication)
	{
		this.mobileApplication = mobileApplication;
	}

	public String getMobileApplicationDistribURL()
	{
		return mobileApplicationDistribURL;
	}

	public void setMobileApplicationDistribURL(String mobileApplicationDistribURL)
	{
		this.mobileApplicationDistribURL = mobileApplicationDistribURL;
	}

	public String getMobileApplicationQRImgName()
	{
		return mobileApplicationQRImgName;
	}

	public void setMobileApplicationQRImgName(String mobileApplicationQRImgName)
	{
		this.mobileApplicationQRImgName = mobileApplicationQRImgName;
	}

	public String getMobilePlatformName()
	{
		return mobilePlatformName;
	}

	public void setMobilePlatformName(String mobilePlatformName)
	{
		this.mobilePlatformName = mobilePlatformName;
	}
}
