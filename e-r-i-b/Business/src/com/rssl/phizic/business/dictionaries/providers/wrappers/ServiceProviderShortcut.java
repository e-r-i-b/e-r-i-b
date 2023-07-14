package com.rssl.phizic.business.dictionaries.providers.wrappers;

import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;

/**
 * Обертка ПУ
 *
 * @author khudyakov
 * @ created 08.08.14
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderShortcut
{
	private Long id;
	private String kind;
	private String synchKey;
	private ServiceProviderState state;
	private boolean planingForDeactivate;
	private boolean editPaymentSupported;
	private Integer versionAPI;

	private boolean webPaymentVisibility;
	private boolean webPaymentAvailability;
	private boolean webAutoPaymentVisibility;
	private boolean webAutoPaymentAvailability;

	private boolean MAPIPaymentVisibility;
	private boolean MAPIPaymentAvailability;
	private boolean MAPIAutoPaymentVisibility;
	private boolean MAPIAutoPaymentAvailability;

	private boolean ATMPaymentVisibility;
	private boolean ATMPaymentAvailability;
	private boolean ATMAutoPaymentVisibility;
	private boolean ATMAutoPaymentAvailability;

	private boolean ERMBPaymentAvailability;
	private boolean ERMBAutoPaymentVisibility;
	private boolean ERMBAutoPaymentAvailability;

	private boolean socialPaymentVisibility;
	private boolean socialPaymentAvailability;
	private boolean socialAutoPaymentVisibility;
	private boolean socialAutoPaymentAvailability;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getKind()
	{
		return kind;
	}

	public void setKind(String kind)
	{
		this.kind = kind;
	}

	public String getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(String synchKey)
	{
		this.synchKey = synchKey;
	}

	public ServiceProviderState getState()
	{
		return state;
	}

	public void setState(ServiceProviderState state)
	{
		this.state = state;
	}

	public boolean isPlaningForDeactivate()
	{
		return planingForDeactivate;
	}

	public void setPlaningForDeactivate(boolean planingForDeactivate)
	{
		this.planingForDeactivate = planingForDeactivate;
	}

	public boolean isEditPaymentSupported()
	{
		return editPaymentSupported;
	}

	public void setEditPaymentSupported(boolean editPaymentSupported)
	{
		this.editPaymentSupported = editPaymentSupported;
	}

	public Integer getVersionAPI()
	{
		return versionAPI;
	}

	public void setVersionAPI(Integer versionAPI)
	{
		this.versionAPI = versionAPI;
	}

	public boolean isWebPaymentVisibility()
	{
		return webPaymentVisibility;
	}

	public void setWebPaymentVisibility(boolean webPaymentVisibility)
	{
		this.webPaymentVisibility = webPaymentVisibility;
	}

	public boolean isWebPaymentAvailability()
	{
		return webPaymentAvailability;
	}

	public void setWebPaymentAvailability(boolean webPaymentAvailability)
	{
		this.webPaymentAvailability = webPaymentAvailability;
	}

	public boolean isWebAutoPaymentVisibility()
	{
		return webAutoPaymentVisibility;
	}

	public void setWebAutoPaymentVisibility(boolean webAutoPaymentVisibility)
	{
		this.webAutoPaymentVisibility = webAutoPaymentVisibility;
	}

	public boolean isWebAutoPaymentAvailability()
	{
		return webAutoPaymentAvailability;
	}

	public void setWebAutoPaymentAvailability(boolean webAutoPaymentAvailability)
	{
		this.webAutoPaymentAvailability = webAutoPaymentAvailability;
	}

	public boolean isMAPIPaymentVisibility()
	{
		return MAPIPaymentVisibility;
	}

	public void setMAPIPaymentVisibility(boolean MAPIPaymentVisibility)
	{
		this.MAPIPaymentVisibility = MAPIPaymentVisibility;
	}

	public boolean isMAPIPaymentAvailability()
	{
		return MAPIPaymentAvailability;
	}

	public void setMAPIPaymentAvailability(boolean MAPIPaymentAvailability)
	{
		this.MAPIPaymentAvailability = MAPIPaymentAvailability;
	}

	public boolean isMAPIAutoPaymentVisibility()
	{
		return MAPIAutoPaymentVisibility;
	}

	public void setMAPIAutoPaymentVisibility(boolean MAPIAutoPaymentVisibility)
	{
		this.MAPIAutoPaymentVisibility = MAPIAutoPaymentVisibility;
	}

	public boolean isMAPIAutoPaymentAvailability()
	{
		return MAPIAutoPaymentAvailability;
	}

	public void setMAPIAutoPaymentAvailability(boolean MAPIAutoPaymentAvailability)
	{
		this.MAPIAutoPaymentAvailability = MAPIAutoPaymentAvailability;
	}

	public boolean isATMPaymentVisibility()
	{
		return ATMPaymentVisibility;
	}

	public void setATMPaymentVisibility(boolean ATMPaymentVisibility)
	{
		this.ATMPaymentVisibility = ATMPaymentVisibility;
	}

	public boolean isATMPaymentAvailability()
	{
		return ATMPaymentAvailability;
	}

	public void setATMPaymentAvailability(boolean ATMPaymentAvailability)
	{
		this.ATMPaymentAvailability = ATMPaymentAvailability;
	}

	public boolean isATMAutoPaymentVisibility()
	{
		return ATMAutoPaymentVisibility;
	}

	public void setATMAutoPaymentVisibility(boolean ATMAutoPaymentVisibility)
	{
		this.ATMAutoPaymentVisibility = ATMAutoPaymentVisibility;
	}

	public boolean isATMAutoPaymentAvailability()
	{
		return ATMAutoPaymentAvailability;
	}

	public void setATMAutoPaymentAvailability(boolean ATMAutoPaymentAvailability)
	{
		this.ATMAutoPaymentAvailability = ATMAutoPaymentAvailability;
	}

	public boolean isERMBPaymentAvailability()
	{
		return ERMBPaymentAvailability;
	}

	public void setERMBPaymentAvailability(boolean ERMBPaymentAvailability)
	{
		this.ERMBPaymentAvailability = ERMBPaymentAvailability;
	}

	public boolean isERMBAutoPaymentVisibility()
	{
		return ERMBAutoPaymentVisibility;
	}

	public void setERMBAutoPaymentVisibility(boolean ERMBAutoPaymentVisibility)
	{
		this.ERMBAutoPaymentVisibility = ERMBAutoPaymentVisibility;
	}

	public boolean isERMBAutoPaymentAvailability()
	{
		return ERMBAutoPaymentAvailability;
	}

	public void setERMBAutoPaymentAvailability(boolean ERMBAutoPaymentAvailability)
	{
		this.ERMBAutoPaymentAvailability = ERMBAutoPaymentAvailability;
	}

	public boolean isSocialPaymentVisibility()
	{
		return socialPaymentVisibility;
	}

	public void setSocialPaymentVisibility(boolean socialPaymentVisibility)
	{
		this.socialPaymentVisibility = socialPaymentVisibility;
	}

	public boolean isSocialPaymentAvailability()
	{
		return socialPaymentAvailability;
	}

	public void setSocialPaymentAvailability(boolean socialPaymentAvailability)
	{
		this.socialPaymentAvailability = socialPaymentAvailability;
	}

	public boolean isSocialAutoPaymentVisibility()
	{
		return socialAutoPaymentVisibility;
	}

	public void setSocialAutoPaymentVisibility(boolean socialAutoPaymentVisibility)
	{
		this.socialAutoPaymentVisibility = socialAutoPaymentVisibility;
	}

	public boolean isSocialAutoPaymentAvailability()
	{
		return socialAutoPaymentAvailability;
	}

	public void setSocialAutoPaymentAvailability(boolean socialAutoPaymentAvailability)
	{
		this.socialAutoPaymentAvailability = socialAutoPaymentAvailability;
	}
}
