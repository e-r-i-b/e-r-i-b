package com.rssl.phizic.business.profile;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.common.types.Entity;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;

/**
 * @author gulov
 * @ created 27.05.2010
 * @ $Authors$
 * @ $Revision$
 */
public class Profile implements ConfirmableObject, Entity
{
	private Long id;
	private Long loginId;
	private String regionCode;  //��� �������, � �������� �������� ������
	private Skin skin; //������������ ����� ������������
    private Boolean showPersonalFinance = false;  //������� ����������� ������������ ��������
    private Calendar startUsePersonalFinance;  //���� ������ ������������� ������������ ��������
	private boolean regionSelected = false;
	private Money mobileWallet;                     //��������� �������
	private String tariffPlanCode;    //���������� �������� ���� ������� (� ��������� ����������� �� ������ "ReducedRateService")
	private String stash; //"���������" � ��������� ����������
	private Integer registrationWindowShowCount;  //���������� ����������� ����������� ���� � ������������ � �����������
	private Calendar lastUsingFinancesDate; // ���� ���������� ������������� ���
	private Integer usingFinancesCount; // ���������� ���� ������������� ��� (� ��������� �� ����� N ����, ��� N �������� � ����������)
	private Integer usingFinancesEveryThreeDaysCount; // ���������� ���� ������������� ��� (� ��������� �� ����� 3-� ����)
	private Calendar lastUpdateCardOperationClaimsDate;
	private Long avatarImageId; //������������� �������.

	public void selectRegion(Region region)
	{
		String code = region == null ? null : (String)region.getSynchKey();
		setRegionCode(code);
		setRegionSelected(true);
	}

	public boolean isRegionSelected()
	{
		return regionSelected;
	}

	/**
	 * ������ ������������ ���� �������!
	 * @see #selectRegion(Region)
	 * ���� ����� ������������ ������ ��� hibernate
	 */
	private void setRegionSelected(boolean regionSelected)
	{
		this.regionSelected = regionSelected;
	}

	public Long getId()
	{
		return id;
	}

	public byte[] getSignableObject()
	{
		return new byte[0];
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Skin getSkin()
	{
		return skin;
	}

	public void setSkin(Skin skin)
	{
		this.skin = skin;
	}

	public Boolean isShowPersonalFinance()
	{
		return showPersonalFinance;
	}

	public void setShowPersonalFinance(Boolean showPersonalFinance)
	{
		this.showPersonalFinance = showPersonalFinance;
	}

	public Money getMobileWallet()
	{
		return mobileWallet;
	}

	public void setMobileWallet(Money mobileWallet)
	{
		this.mobileWallet = mobileWallet;
	}

	public String getTariffPlanCode()
	{
		return tariffPlanCode;
	}

	public void setTariffPlanCode(String tariffPlanCode)
	{
		this.tariffPlanCode = tariffPlanCode;
	}

	public String getStash()
	{
		return stash;
	}

	public void setStash(String stash)
	{
		this.stash = stash;
	}

	public Integer getRegistrationWindowShowCount()
	{
		return registrationWindowShowCount;
	}

	public void setRegistrationWindowShowCount(Integer registrationWindowShowCount)
	{
		this.registrationWindowShowCount = registrationWindowShowCount;
	}

	public Calendar getStartUsePersonalFinance()
	{
		return startUsePersonalFinance;
	}

	public void setStartUsePersonalFinance(Calendar startUsePersonalFinance)
	{
		this.startUsePersonalFinance = startUsePersonalFinance;
	}

	/**
	 * @return ���� ���������� ������������� ���
	 */
	public Calendar getLastUsingFinancesDate()
	{
		return lastUsingFinancesDate;
	}

	/**
	 * @param lastUsingFinancesDate - ���� ���������� ������������� ���
	 */
	public void setLastUsingFinancesDate(Calendar lastUsingFinancesDate)
	{
		this.lastUsingFinancesDate = lastUsingFinancesDate;
	}

	/**
	 * @return ���������� ���� ������������� ��� (� ��������� �� ����� N ����, ��� N �������� � ����������)
	 */
	public Integer getUsingFinancesCount()
	{
		return usingFinancesCount;
	}

	/**
	 * @param usingFinancesCount - ���������� ���� ������������� ��� (� ��������� �� ����� N ����, ��� N �������� � ����������)
	 */
	public void setUsingFinancesCount(Integer usingFinancesCount)
	{
		this.usingFinancesCount = usingFinancesCount;
	}

	/**
	 * @return ���������� ���� ������������� ��� (� ��������� �� ����� 3-� ����)
	 */
	public Integer getUsingFinancesEveryThreeDaysCount()
	{
		return usingFinancesEveryThreeDaysCount;
	}

	/**
	 * @param usingFinancesEveryThreeDaysCount - ���������� ���� ������������� ��� (� ��������� �� ����� 3-� ����)
	 */
	public void setUsingFinancesEveryThreeDaysCount(Integer usingFinancesEveryThreeDaysCount)
	{
		this.usingFinancesEveryThreeDaysCount = usingFinancesEveryThreeDaysCount;
	}

	/**
	 * @return ���� ���������� ���������� ������ �� �������� ��������� �������� ������
	 */
	public Calendar getLastUpdateCardOperationClaimsDate()
	{
		return lastUpdateCardOperationClaimsDate;
	}

	/**
	 * @param lastUpdateCardOperationClaimsDate - ���� ���������� ���������� ������ �� �������� ��������� �������� ������
	 */
	public void setLastUpdateCardOperationClaimsDate(Calendar lastUpdateCardOperationClaimsDate)
	{
		this.lastUpdateCardOperationClaimsDate = lastUpdateCardOperationClaimsDate;
	}

	public Long getAvatarImageId()
	{
		return avatarImageId;
	}

	public void setAvatarImageId(Long avatarImageId)
	{
		this.avatarImageId = avatarImageId;
	}

	/**
	 * ��������� ���� �������, � �������� �������� ������.
	 * @return ��� ������� ��� null, ���� ������ �� �������� � ����������� �������.
	 */
	public String getRegionCode()
	{
		return regionCode;
	}

	/**
	 * ��������� ���� �������, � �������� �������� ������.
	 * ��� ��������� ������� ������ ������������ ���� �������!
	 * ���� ����� ������������ ������ ��� hibernate
	 * @see #selectRegion(Region)
	 */
	public void setRegionCode(String regionCode)
	{
		this.regionCode = regionCode;
	}
}
