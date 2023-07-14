package com.rssl.phizic.business.mobileDevices;

/**
 * Мобильная платформа
 * @author Jatsky
 * @ created 29.07.13
 * @ $Author$
 * @ $Revision$
 */

public class MobilePlatform
{
	private long id;
	private String platformId;
	private String platformName;
	private Long version;
	private String errorText;
	private Long platformIcon;
	private boolean downloadFromSBRF;
	private String bankURL;
	private String externalURL;
	private boolean useQR;
	private boolean showInApps;
	private String qrName;
	private boolean lightScheme;
	//Подтверждение одноразовым паролем
	private boolean passwordConfirm;
	private boolean social;
	private String unallowedBrowsers;
	private boolean useCaptcha;
	private boolean showSbAttribute;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPlatformId()
	{
		return platformId;
	}

	public void setPlatformId(String platformId)
	{
		this.platformId = platformId;
	}

	public String getPlatformName()
	{
		return platformName;
	}

	public void setPlatformName(String platformName)
	{
		this.platformName = platformName;
	}

	public Long getVersion()
	{
		return version;
	}

	public void setVersion(Long version)
	{
		this.version = version;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getErrorText()
	{
		return errorText;
	}

	public void setErrorText(String errorText)
	{
		this.errorText = errorText;
	}

	public Long getPlatformIcon()
	{
		return platformIcon;
	}

	public void setPlatformIcon(Long platformIcon)
	{
		this.platformIcon = platformIcon;
	}

	public boolean isDownloadFromSBRF()
	{
		return downloadFromSBRF;
	}

	public void setDownloadFromSBRF(boolean downloadFromSBRF)
	{
		this.downloadFromSBRF = downloadFromSBRF;
	}

	public String getBankURL()
	{
		return bankURL;
	}

	public void setBankURL(String downloadURL)
	{
		this.bankURL = downloadURL;
	}

	public String getExternalURL()
	{
		return externalURL;
	}

	public void setExternalURL(String externalURL)
	{
		this.externalURL = externalURL;
	}

	public boolean isUseQR()
	{
		return useQR;
	}

	public void setUseQR(boolean useQR)
	{
		this.useQR = useQR;
	}

	public String getQrName()
	{
		return qrName;
	}

	public void setQrName(String qrName)
	{
		this.qrName = qrName;
	}

	public boolean isLightScheme()
	{
		return lightScheme;
	}

	public void setLightScheme(boolean lightScheme)
	{
		this.lightScheme = lightScheme;
	}

	public boolean isShowInApps()
	{
		return showInApps;
	}

	public void setShowInApps(boolean showInApps)
	{
		this.showInApps = showInApps;
	}

	public boolean isPasswordConfirm()
	{
		return passwordConfirm;
	}

	public void setPasswordConfirm(boolean passwordCofirm)
	{
		this.passwordConfirm = passwordCofirm;
	}

	public boolean isSocial()
	{
		return social;
	}

	public void setSocial(boolean social)
	{
		this.social = social;
	}

	public String getUnallowedBrowsers()
	{
		return unallowedBrowsers;
	}

	public void setUnallowedBrowsers(String unallowedBrowsers)
	{
		this.unallowedBrowsers = unallowedBrowsers;
	}

	public boolean isUseCaptcha()
	{
		return useCaptcha;
	}

	public void setUseCaptcha(boolean useCaptcha)
	{
		this.useCaptcha = useCaptcha;
	}

	public boolean isShowSbAttribute()
	{
		return showSbAttribute;
	}

	public void setShowSbAttribute(boolean showSbAttribute)
	{
		this.showSbAttribute = showSbAttribute;
	}
}
