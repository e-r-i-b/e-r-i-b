package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.common.types.Entity;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.util.*;

/**
 * @author akrenev
 * @ created 14.12.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class ServiceProviderBase extends MultiBlockDictionaryRecordBase implements Recipient, RecipientInfo, Routable, DictionaryRecord, Entity
{
	protected Long  id;

	private Comparable  synchKey;
	private String  code;
	private String  name;
	private String  description;
	private String  tipOfProvider;
	private String  INN;
	private String  KPP;
	private String  BIC;
	private String  account;
	private String  bankName;
	private String  corrAccount;
	private String  NSICode;
	private Long  departmentId;
	private String  transitAccount;
	private Set<Region> regions;
	private ResidentBank    bank;
	private ServiceProviderState state;
    private boolean bankDetails;                //���������� ���������� ����������
	private String phoneNumber;                 //���������� �����
	private Calendar creationDate;              //���� ��������/����� ���������� �����
	private List<FieldDescription> fieldDescriptions = new ArrayList<FieldDescription>();
	private GroupRisk groupRisk;         //������ ����� ��������� ����������
	private boolean standartTemplate = true;
	private String templateFormat;
	private String templateExample;
	private String commissionMessage;   //��������� � ��������
	private Long imageId;       // ������������� ������
	private Long imageHelpId;       // ������������� ����������� ���������
	private SumRestrictions restrictions; //����������� �� ����� �������� ��� ����������.
	private boolean barSupported;
	private boolean barSupportedRep = true;
	private boolean offlineAvailable; // �������� �� ��������� ��� ������ ��� ������������ ������� �������
	private boolean availablePaymentsForInternetBank;//�������� �� ������� ����� �� � ��
	private boolean availablePaymentsForMApi;        //�������� �� ������� ����� �� � mApi
	private boolean availablePaymentsForAtmApi;      //�������� �� ������� ����� �� � atmApi
	private boolean availablePaymentsForSocialApi;   //�������� �� ������� ����� �� � socialApi
	private boolean availablePaymentsForErmb;        //�������� �� ������� ����� �� � ����
	private boolean visiblePaymentsForInternetBank;  //����� �� ������� ����� �� � �������� ��� ��
	private boolean visiblePaymentsForMApi;          //����� �� ������� ����� �� � �������� ��� mApi
	private boolean visiblePaymentsForSocialApi;     //����� �� ������� ����� �� � �������� ��� socialApi
	private boolean visiblePaymentsForAtmApi;        //����� �� ������� ����� �� � �������� ��� atmApi
	private boolean creditCardSupported = true;      //��������� �� ������ � ��������� ����
	private Boolean creditCardSupportedTemp = null;  //��������� �� ������ � ��������� ���� (���������� ����� ��. CHG061793)
	private boolean editPaymentSupported = true; //������������ �� �������������� �������, ��-���������, ��� ������������
	private ServiceProviderSubType subType;     // ������� ���������� (������� �����/�������� �������)

	/**
	 * ��������� ��� ����������.
	 */
	private Long sortPriority;

	/**
	 * @return ������ ����� ����������� ���-������
	 */
	public boolean isStandartTemplate()
	{
		return standartTemplate;
	}

	public void setStandartTemplate(boolean standartTemplate)
	{
		this.standartTemplate = standartTemplate;
	}

	/**
	 * @return ������ ���-������� (������ ��� ������������� ���-��������)
	 */
	public String getTemplateFormat()
	{
		return templateFormat;
	}

	public void setTemplateFormat(String templateFormat)
	{
		this.templateFormat = templateFormat;
	}

	/**
	 * @return ������ �������������� ���-�������
	 */
	public String getTemplateExample()
	{
		return templateExample;
	}

	public void setTemplateExample(String templateExample)
	{
		this.templateExample = templateExample;
	}

	/**
	 * ��� ���������� �����
	 * @return - ServiceProviderType
	 */
	public abstract ServiceProviderType getType();

	public void storeRouteInfo(String info)
	{
		removeRouteInfo();
		synchKey = IDHelper.storeRouteInfo((String) synchKey, info);
	}

	public String restoreRouteInfo()
	{
		return IDHelper.restoreRouteInfo((String) getSynchKey());
	}

	public String removeRouteInfo()
	{
		String info = IDHelper.restoreRouteInfo((String) synchKey);
		synchKey = IDHelper.restoreOriginalId((String) synchKey);

		return info;
	}

	/**
	 * ���������� ���������� �� ������
	 * @param info - ������������� ������
	 */
	public void storePaymentSystemInfo(String info)
	{
		removePaymentSystemInfo();
		synchKey = IDHelper.storeAdditionalInfo((String) synchKey, info);
	}

	/**
	 * ���������� ���������� �� ������
	 * @return
	 */
	public String restorePaymentSystemInfo()
	{
		return IDHelper.restoreAdditionalInfo((String) getSynchKey());
	}

	/**
	 * �������� ���������� �� ������
	 * @return - ����
	 */
	public String removePaymentSystemInfo()
	{
		String info = IDHelper.restoreAdditionalInfo((String) synchKey);
		synchKey = IDHelper.restoreOriginalIdWithAdditionalInfo((String) synchKey);

		return info;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getTipOfProvider()
	{
		return tipOfProvider;
	}

	public void setTipOfProvider(String tipOfProvider)
	{
		this.tipOfProvider = tipOfProvider;
	}

	public String getINN()
	{
		return INN;
	}

	public void setINN(String INN)
	{
		this.INN = INN;
	}

	public String getKPP()
	{
		return KPP;
	}

	public void setKPP(String KPP)
	{
		this.KPP = KPP;
	}

	public String getBIC()
	{
		return BIC;
	}

	public void setBIC(String BIC)
	{
		this.BIC = BIC;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getCorrAccount()
	{
		return corrAccount;
	}

	public void setCorrAccount(String corrAccount)
	{
		this.corrAccount = corrAccount;
	}

	public String getNSICode()
	{
		return NSICode;
	}

	public void setNSICode(String NSICode)
	{
		this.NSICode = NSICode;
	}

	/**
	 * @return ������������� �������������, � �������� �������� ���������
	 */
	public Long getDepartmentId()
	{
		return departmentId;
	}

	/**
	 * ������ ������������� �������������, � �������� �������� ���������
	 * @param departmentId ������������� �������������
	 */
	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public String getTransitAccount()
	{
		return transitAccount;
	}

	public void setTransitAccount(String transitAccount)
	{
		this.transitAccount = transitAccount;
	}

	public Set<Region> getRegions()
	{
		return regions;
	}

	public void setRegions(Set<Region> regions)
	{
		this.regions = regions;
	}

	public ResidentBank getBank()
	{
		return bank;
	}

	public void setBank(ResidentBank bank)
	{
		this.bank = bank;
	}

	/**
	 * ��������� ������� ���������� �����
	 * @return - ������
	 */
	public ServiceProviderState getState()
	{
		return state;
	}

	public void setState(ServiceProviderState state)
	{
		this.state = state;
	}

	public Boolean isMain()
	{
		return false;
	}

	public String getPayerAccount()
	{
		return null;
	}

	/**
	 * ������� ���������� ���������� ����������
	 * @return - true (���������)
	 */
	public boolean isBankDetails()
	{
		return bankDetails;
	}

	public void setBankDetails(boolean bankDetails)
	{
		this.bankDetails = bankDetails;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * �������������� ����
	 * @return - ������ ���. �����
	 */
	public List<FieldDescription> getFieldDescriptions()
	{
		return fieldDescriptions;
	}

	public void setFieldDescriptions(List<FieldDescription> fieldDescriptions)
	{
		this.fieldDescriptions = fieldDescriptions;
	}

	/**
	 * @return ������ �������� ���� ����������
	 */
	public List<FieldDescription> getKeyFields()
	{
		List<FieldDescription> keyFields = new ArrayList<FieldDescription>();
		for (FieldDescription fieldDescription : getFieldDescriptions())
		{
			if (fieldDescription.isKey())
			{
				keyFields.add(fieldDescription);
			}
		}
		return keyFields;
	}
	/**
	 * ���������� ���� (��������) �� ��� ���� �� ������� �������
	 * ���� null, ���� ��� ���� � ��������� ������
	 * @param externalId - ��� ���� �� ������� �������
	 * @return �������� ���� ���� null, ���� ���� �� �������
	 */
	public FieldDescription getField(String externalId)
	{
		for (FieldDescription fd : fieldDescriptions)
		{
			if (fd.getExternalId().equals(externalId))
				return fd;
		}
		return null;
	}

	public void addField(FieldDescription description)
	{
		description.setHolderId(getId());
		fieldDescriptions.add(description);
	}

	public void removeField(FieldDescription description)
	{
		for (Iterator<FieldDescription> iterator = fieldDescriptions.iterator(); iterator.hasNext();)
		{
			FieldDescription d = iterator.next();
			if (d.getId().equals(description.getId()))
			{
				iterator.remove();
				return;
			}
		}
	}

	/**
	 * @param commissionMessage ��������� � ��������.
	 */
	public void setCommissionMessage(String commissionMessage)
	{
		this.commissionMessage = commissionMessage;
	}

	/**
	 * @return ��������� � ��������.
	 */
	public String getCommissionMessage()
	{
		return commissionMessage;
	}

	public GroupRisk getGroupRisk()
	{
		return groupRisk;
	}

	public void setGroupRisk(GroupRisk groupRisk)
	{
		this.groupRisk = groupRisk;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public Long getImageHelpId()
	{
		return imageHelpId;
	}

	public void setImageHelpId(Long imageHelpId)
	{
		this.imageHelpId = imageHelpId;
	}

	public SumRestrictions getRestrictions()
	{
		return restrictions;
	}

	public void setRestrictions(SumRestrictions restrictions)
	{
		this.restrictions = restrictions;
	}

	public Long getSortPriority()
	{
		return sortPriority;
	}

	public void setSortPriority(Long sortPriority)
	{
		this.sortPriority = sortPriority;
	}

	public boolean isBarSupported()
	{
		return barSupported;
	}

	public void setBarSupported(boolean barSupported)
	{
		this.barSupported = barSupported;
	}

	public boolean isOfflineAvailable()
	{
		return offlineAvailable;
	}

	public void setOfflineAvailable(boolean offlineAvailable)
	{
		this.offlineAvailable = offlineAvailable;
	}

	public boolean isVisiblePaymentsForInternetBank()
	{
		return visiblePaymentsForInternetBank;
	}

	public void setVisiblePaymentsForInternetBank(boolean visiblePaymentsForInternetBank)
	{
		this.visiblePaymentsForInternetBank = visiblePaymentsForInternetBank;
	}

	public boolean isVisiblePaymentsForMApi()
	{
		return visiblePaymentsForMApi;
	}

	public void setVisiblePaymentsForMApi(boolean visiblePaymentsForMApi)
	{
		this.visiblePaymentsForMApi = visiblePaymentsForMApi;
	}

	public boolean isVisiblePaymentsForAtmApi()
	{
		return visiblePaymentsForAtmApi;
	}

	public void setVisiblePaymentsForAtmApi(boolean visiblePaymentsForAtmApi)
	{
		this.visiblePaymentsForAtmApi = visiblePaymentsForAtmApi;
	}

	public boolean isAvailablePaymentsForMApi()
	{
		return availablePaymentsForMApi;
	}

	public void setAvailablePaymentsForMApi(boolean availablePaymentsForMApi)
	{
		this.availablePaymentsForMApi = availablePaymentsForMApi;
	}

	public boolean isAvailablePaymentsForInternetBank()
	{
		return availablePaymentsForInternetBank;
	}

	public void setAvailablePaymentsForInternetBank(boolean availablePaymentsForInternetBank)
	{
		this.availablePaymentsForInternetBank = availablePaymentsForInternetBank;
	}

	public boolean isAvailablePaymentsForAtmApi()
	{
		return availablePaymentsForAtmApi;
	}

	public void setAvailablePaymentsForAtmApi(boolean availablePaymentsForAtmApi)
	{
		this.availablePaymentsForAtmApi = availablePaymentsForAtmApi;
	}

	public boolean isAvailablePaymentsForErmb()
	{
		return availablePaymentsForErmb;
	}

	public void setAvailablePaymentsForErmb(boolean availablePaymentsForErmb)
	{
		this.availablePaymentsForErmb = availablePaymentsForErmb;
	}

	/**
	 * @return ��������� �� ������ � ��������� ����
	 */
	public boolean isCreditCardSupported()
	{
		return creditCardSupported;
	}

	/**
	 * ���������� ������� ������ � ��������� ����.
	 * @param creditCardSupported ��������� �� ������ � ��������� ����
	 */
	public void setCreditCardSupported(boolean creditCardSupported)
	{
		this.creditCardSupported = creditCardSupported;
	}

	public Boolean isCreditCardSupportedTemp()
	{
		return creditCardSupportedTemp;
	}

	public void setCreditCardSupportedTemp(Boolean creditCardSupportedTemp)
	{
		//��. CHG061793
		if (creditCardSupportedTemp != null && !creditCardSupportedTemp)
		{
			creditCardSupported = false;
		}

		this.creditCardSupportedTemp = creditCardSupportedTemp;
	}

	public boolean isEditPaymentSupported()
	{
		return editPaymentSupported;
	}

	public void setEditPaymentSupported(boolean editPaymentSupported)
	{
		this.editPaymentSupported = editPaymentSupported;
	}

	public ServiceProviderSubType getSubType()
	{
		return subType;
	}

	public void setSubType(ServiceProviderSubType subType)
	{
		this.subType = subType;
	}

    public boolean isAvailablePaymentsForSocialApi()
    {
        return availablePaymentsForSocialApi;
    }

    public void setAvailablePaymentsForSocialApi(boolean availablePaymentsForSocialApi)
    {
        this.availablePaymentsForSocialApi = availablePaymentsForSocialApi;
    }

    public boolean isVisiblePaymentsForSocialApi()
    {
        return visiblePaymentsForSocialApi;
    }

    public void setVisiblePaymentsForSocialApi(boolean visiblePaymentsForSocialApi)
    {
        this.visiblePaymentsForSocialApi = visiblePaymentsForSocialApi;
    }

	public boolean isBarSupportedRep()
	{
		return barSupportedRep;
	}

	public void setBarSupportedRep(boolean barSupportedRep)
	{
		this.barSupportedRep = barSupportedRep;
	}
}