package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.replication.providers.ReplicationType;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaySchemeBase;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Поставщик услуг
 *
 * @author khudyakov
 * @ created 20.05.2010
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"OverlyComplexClass"})
public class BillingServiceProvider extends BillingServiceProviderBase
{
	private BigDecimal maxComissionAmount;
	private BigDecimal minComissionAmount;
	private BigDecimal comissionRate;
	private boolean deptAvailable;
	private String  companyName;
	private boolean popular;
	private boolean propsOnline;                        //запрос реквизитов в on-line
	private boolean bankomatSupported;                  //Возможно регистрировать подписки через УС без буквенной клавиатуры
	private boolean mobilebank;    //Доступен ли для создания СМС-шаблонов
	private String mobilebankCode;
	private boolean autoPaymentSupported; // доступны ли автоплатежи
	private boolean autoPaymentSupportedInApi;
	private boolean autoPaymentSupportedInATM;
	private boolean autoPaymentSupportedInSocialApi;
	private boolean autoPaymentSupportedInERMB;
	private boolean needAutoPaymentVisibleApply = true; //необходимо ли при обновлении изменять настройки видимости автоплатежей.
	private boolean autoPaymentVisible; // настройки видимости автоплатежей
	private boolean autoPaymentVisibleInApi;
	private boolean autoPaymentVisibleInATM;
	private boolean autoPaymentVisibleInSocialApi;
	private boolean autoPaymentVisibleInERMB;
	private Integer versionAPI;      // версия API, с которой отображается поставщик (в формате без разделителя: 500, 510, 520)
	private boolean templateSupported; //поддерживает ли создание шаблона

	private Map<AutoSubType, AutoPaySchemeBase> autoPaySchemes = new HashMap<AutoSubType, AutoPaySchemeBase>();
	private Boolean planingForDeactivate;

	/**
	 * Максимальная сумма комиссии
	 * @return - комиссия
	 */
	public BigDecimal getMaxComissionAmount()
	{
		return maxComissionAmount;
	}

	public void setMaxComissionAmount(BigDecimal maxComissionAmount)
	{
		this.maxComissionAmount = maxComissionAmount;
	}

	/**
	 * Минимальная сумма комиссии
	 * @return - комиссия
	 */
	public BigDecimal getMinComissionAmount()
	{
		return minComissionAmount;
	}

	public void setMinComissionAmount(BigDecimal minComissionAmount)
	{
		this.minComissionAmount = minComissionAmount;
	}

	/**
	 * Процентная ставка комиссии
	 * @return - ставка
	 */
	public BigDecimal getComissionRate()
	{
		return comissionRate;
	}

	public void setComissionRate(BigDecimal comissionRate)
	{
		this.comissionRate = comissionRate;
	}


	/**
	 * Признак того, что клиенту для данного поставщика услуг
	 * может быть возвращена сумма задолженности
	 * @return - true (может быть возвращена)
	 */
	public boolean isDeptAvailable()
	{
		return deptAvailable;
	}

	public void setDeptAvailable(boolean deptAvailable)
	{
		this.deptAvailable = deptAvailable;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	/**
	 * Признак того, показывать в популярных платежах
	 * клиентам или нет
	 * @return - true (показывать)
	 */
	public boolean isPopular()
	{
		return popular;
	}

	public void setPopular(boolean popular)
	{
		this.popular = popular;
	}

	/**
	 * Запромс реквизитов on-line
	 * @return - true (да)
	 */
	public boolean isPropsOnline()
	{
		return propsOnline;
	}

	public void setPropsOnline(boolean propsOnline)
	{
		this.propsOnline = propsOnline;
	}

	public void updateFrom(DictionaryRecord that, ReplicationType replicationType)
	{
		//в данном случае необходимо выборочно обновлять сущьность
		BillingServiceProvider provider = (BillingServiceProvider) that;

		setBilling(provider.getBilling());

		setCode(provider.getCode());
		if (getState() != ServiceProviderState.MIGRATION)//при загрузке поставщиков услуг статус "заблокирован для новых платежей" не должен меняться, т.к. в биллингах этой информации нет
			setState(provider.getState());

		setCodeService(provider.getCodeService());
		setCodeRecipientSBOL(provider.getCodeRecipientSBOL());
	    setName(provider.getName());

		setBankDetails(provider.isBankDetails());
		setINN(provider.getINN());
		setKPP(provider.getKPP());
		setAccount(provider.getAccount());
		setBIC(provider.getBIC());
		setBankName(provider.getBankName());
		setCorrAccount(provider.getCorrAccount());

		setPhoneNumber(provider.getPhoneNumber());

		setDescription(provider.getDescription());
		setPopular(provider.isPopular());
		setDepartmentId(provider.getDepartmentId());
		if(provider.isBarSupportedRep())
			setBarSupported(provider.isBarSupported());

		if (replicationType == ReplicationType.ESB || replicationType == ReplicationType.DEFAULT)
		{
			setNameService(provider.getNameService());
			setAlias(provider.getAlias());
			setLegalName(provider.getLegalName());
			setNameOnBill(provider.getNameOnBill());
		}

		if(replicationType == ReplicationType.TB || replicationType == ReplicationType.DEFAULT)
		{
			setPropsOnline(provider.isPropsOnline());

			setTransitAccount(provider.getTransitAccount());

			setNSICode(provider.getNSICode());
			setDeptAvailable(provider.isDeptAvailable());

			setMinComissionAmount(provider.getMinComissionAmount());
			setMaxComissionAmount(provider.getMaxComissionAmount());
			setComissionRate(provider.getComissionRate());

			setAttrDelimiter(provider.getAttrDelimiter());
			setAttrValuesDelimiter(provider.getAttrValuesDelimiter());

			setGround(provider.isGround());

			setMobilebankCode(provider.getMobilebankCode());
			setMobilebank(provider.isMobilebank());
		}
		if (replicationType == ReplicationType.DEFAULT)
		{
            setVersionAPI(provider.getVersionAPI());
			setBankomatSupported(provider.isBankomatSupported());
			setTemplateSupported(provider.isTemplateSupported());
			setEditPaymentSupported(provider.isEditPaymentSupported());
			setCreditCardSupported(provider.isCreditCardSupported());
			setVisiblePaymentsForInternetBank(provider.isVisiblePaymentsForInternetBank());
			setVisiblePaymentsForMApi(provider.isVisiblePaymentsForMApi());
			setVisiblePaymentsForAtmApi(provider.isVisiblePaymentsForAtmApi());
            setVisiblePaymentsForSocialApi(provider.isVisiblePaymentsForSocialApi());
			setAvailablePaymentsForInternetBank(provider.isAvailablePaymentsForInternetBank());
			setAvailablePaymentsForAtmApi(provider.isAvailablePaymentsForAtmApi());
			setAvailablePaymentsForSocialApi(provider.isAvailablePaymentsForSocialApi());
			setAvailablePaymentsForMApi(provider.isAvailablePaymentsForMApi());
			setAvailablePaymentsForErmb(provider.isAvailablePaymentsForErmb());
			setPlaningForDeactivate(provider.getPlaningForDeactivate());
		}
		if (replicationType == ReplicationType.ESB)
		{
			setPropsOnline(provider.isPropsOnline());
			setMobilebank(provider.isMobilebank());
			setDeptAvailable(provider.isDeptAvailable());
			setGround(provider.isGround());
			setBankomatSupported(provider.isBankomatSupported());

			if (provider.isCreditCardSupportedTemp() != null)
			{
				setCreditCardSupported(provider.isCreditCardSupportedTemp());
			}
		}

		if (getAccountType() == null)
			setAccountType(provider.getAccountType());

		//при репликации п.у. по новому формату (шинный поставщик услуг)
		//мы должны выставить тип как общий, если поставщик в базе имел тип AcountType.DEPOSIT
		if (getAccountType() == AccountType.DEPOSIT &&
				provider.getAccountType() == AccountType.CARD)
			setAccountType(AccountType.ALL);

		if (getAccountType() == AccountType.CARD &&
				provider.getAccountType() == AccountType.DEPOSIT)
			setAccountType(AccountType.ALL);

		List<FieldDescription> fDescriptions = provider.getFieldDescriptions();
		if (fDescriptions != null && fDescriptions.size() != 0)
			setFieldDescriptions(provider.getFieldDescriptions());

		Set<Region> regions = provider.getRegions();
		if (regions != null)
			setRegions(provider.getRegions());

		if ((replicationType == ReplicationType.DEFAULT || replicationType == ReplicationType.ESB))
		{
			setAutoPaymentSupported(provider.isAutoPaymentSupported());
			setAutoPaymentSupportedInApi(provider.isAutoPaymentSupportedInApi());
			setAutoPaymentSupportedInATM(provider.isAutoPaymentSupportedInATM());
			setAutoPaymentSupportedInSocialApi(provider.isAutoPaymentSupportedInSocialApi());
			setAutoPaymentSupportedInERMB(provider.isAutoPaymentSupportedInERMB());

			if (provider.isNeedAutoPaymentVisibleApply()) //если необходимо изменять настройки видимости.
			{
				setAutoPaymentVisible(provider.isAutoPaymentVisible());
				setAutoPaymentVisibleInApi(provider.isAutoPaymentVisibleInApi());
				setAutoPaymentVisibleInATM(provider.isAutoPaymentVisibleInATM());
				setAutoPaymentVisibleInSocialApi(provider.isAutoPaymentVisibleInSocialApi());
				setAutoPaymentVisibleInERMB(provider.isAutoPaymentVisibleInERMB());
			}
			if (provider.isAutoPaymentSupported() || provider.isAutoPaymentSupportedInApi() ||
                    provider.isAutoPaymentSupportedInATM() || provider.isAutoPaymentSupportedInERMB() ||
                    provider.isAutoPaymentSupportedInSocialApi())
			{
				if (provider.getAlwaysAutoPayScheme() != null)
					setAlwaysAutoPayScheme(provider.getAlwaysAutoPayScheme());
				else
					setAlwaysAutoPayScheme(null);
				if (provider.getThresholdAutoPayScheme() != null)
					setThresholdAutoPayScheme(provider.getThresholdAutoPayScheme());
				else
					setThresholdAutoPayScheme(null);
				if (provider.getInvoiceAutoPayScheme() != null)
					setInvoiceAutoPayScheme(provider.getInvoiceAutoPayScheme());
				else
					setInvoiceAutoPayScheme(null);
			}
			else
			{
				setAlwaysAutoPayScheme(null);
				setThresholdAutoPayScheme(null);
				setInvoiceAutoPayScheme(null);
			}
		}

		if (ReplicationType.DEFAULT == replicationType || ReplicationType.ESB == replicationType || replicationType == ReplicationType.TB)
		{
			if (getGroupRisk() == null && provider.getGroupRisk() != null)
				setGroupRisk(provider.getGroupRisk());
		}

		setSynchKey(provider.getSynchKey());
		if (getCreationDate() == null)
			setCreationDate(provider.getCreationDate());
	}

	public void updateFrom(DictionaryRecord that)
	{
		BillingServiceProvider provider = (BillingServiceProvider) that;
		provider.setId(getId());
		Long oldImageId = getImageId();
		ServiceProviderState oldState = getState();
		BeanHelper.copyPropertiesFull(this, provider);
		setImageId(oldImageId);  //BeanHelper null превращает в 0 - по формату данных xml не мождет быть передано, следовательно оставляем как есть
		if (oldState == ServiceProviderState.MIGRATION)
			setState(oldState);//при загрузке поставщиков услуг статус "заблокирован для новых платежей" не должен меняться, т.к. в биллингах этой информации нет
	}

	public ServiceProviderType getType()
	{
		return ServiceProviderType.BILLING;
	}

	/**
	 * Признак того, что можно регистрировать подписки через УС без буквенной клавиатуры
	 * @return - true (есть)
	 */
	public boolean isBankomatSupported()
	{
		return bankomatSupported;
	}

	public void setBankomatSupported(boolean bankomatSupported)
	{
		this.bankomatSupported = bankomatSupported;
	}


	/**
	 * @return флажок "Доступен для создания СМС-шаблонов" (CHG059689: Убрать галку в настройках ПУ "Поставщик МБ".)
	 */
	public boolean isMobilebank()
	{
		return mobilebank;
	}

	public void setMobilebank(boolean mobilebank)
	{
		this.mobilebank = mobilebank;
	}

	/**
	 * Код поставщика в МБК
	 * @return - код или пусто, если поставщик не поддерживается МБК
	 */
	public String getMobilebankCode()
	{
		return mobilebankCode;
	}

	public void setMobilebankCode(String mobilebankCode)
	{
		this.mobilebankCode = mobilebankCode;
	}

	/**
	 * @return схема порогового автоплатежа
	 */
	public ThresholdAutoPayScheme getThresholdAutoPayScheme()
	{
		return (ThresholdAutoPayScheme) autoPaySchemes.get(AutoSubType.THRESHOLD);
	}

	/**
	 * Установить схему для порогового автоплатежа
	 * @param thresholdAutoPayScheme схема порогового автоплатежа
	 */
	public void setThresholdAutoPayScheme(ThresholdAutoPayScheme thresholdAutoPayScheme)
	{
		if (thresholdAutoPayScheme == null)
			autoPaySchemes.remove(AutoSubType.THRESHOLD);
		else
			autoPaySchemes.put(AutoSubType.THRESHOLD, thresholdAutoPayScheme);
	}

	/**
	 * @return схема регулярного автоплатежа
	 */
	public AlwaysAutoPayScheme getAlwaysAutoPayScheme()
	{
		return (AlwaysAutoPayScheme) autoPaySchemes.get(AutoSubType.ALWAYS);
	}

	/**
	 * Установить схему для регулярного автоплатежа
	 * @param alwaysAutoPayScheme схема регулярного автоплатежа
	 */
	public void setAlwaysAutoPayScheme(AlwaysAutoPayScheme alwaysAutoPayScheme)
	{
		if (alwaysAutoPayScheme == null)
			autoPaySchemes.remove(AutoSubType.ALWAYS);
		else
			autoPaySchemes.put(AutoSubType.ALWAYS, alwaysAutoPayScheme);
	}

	/**
	 * @return схема автоплатежа по выставленному счету
	 */
	public InvoiceAutoPayScheme getInvoiceAutoPayScheme()
	{
		return (InvoiceAutoPayScheme) autoPaySchemes.get(AutoSubType.INVOICE);
	}

	/**
	 * Уставновить схему для автоплатежа по выставленному счету
	 * @param invoiceAutoPayScheme
	 */
	public void setInvoiceAutoPayScheme(InvoiceAutoPayScheme invoiceAutoPayScheme)
	{
		if (invoiceAutoPayScheme == null)
			autoPaySchemes.remove(AutoSubType.INVOICE);
		else
			autoPaySchemes.put(AutoSubType.INVOICE, invoiceAutoPayScheme);
	}

	public boolean isAutoPaymentSupported()
	{
		return autoPaymentSupported;
	}

	public void setAutoPaymentSupported(boolean autoPaymentSupported)
	{
		this.autoPaymentSupported = autoPaymentSupported;
	}

	public boolean isAutoPaymentSupportedInApi()
	{
		return autoPaymentSupportedInApi;
	}

	public void setAutoPaymentSupportedInApi(boolean autoPaymentSupportedInApi)
	{
		this.autoPaymentSupportedInApi = autoPaymentSupportedInApi;
	}

	public boolean isAutoPaymentSupportedInATM()
	{
		return autoPaymentSupportedInATM;
	}

	public void setAutoPaymentSupportedInATM(boolean autoPaymentSupportedInATM)
	{
		this.autoPaymentSupportedInATM = autoPaymentSupportedInATM;
	}

	public boolean isAutoPaymentSupportedInERMB()
	{
		return autoPaymentSupportedInERMB;
	}

	public void setAutoPaymentSupportedInERMB(boolean autoPaymentSupportedInERMB)
	{
		this.autoPaymentSupportedInERMB = autoPaymentSupportedInERMB;
	}

	public boolean isAutoPaymentVisible()
	{
		return autoPaymentVisible;
	}

	public void setAutoPaymentVisible(boolean autoPaymentVisible)
	{
		this.autoPaymentVisible = autoPaymentVisible;
	}

	public boolean isAutoPaymentVisibleInApi()
	{
		return autoPaymentVisibleInApi;
	}

	public void setAutoPaymentVisibleInApi(boolean autoPaymentVisibleInApi)
	{
		this.autoPaymentVisibleInApi = autoPaymentVisibleInApi;
	}

	public boolean isAutoPaymentVisibleInATM()
	{
		return autoPaymentVisibleInATM;
	}

	public void setAutoPaymentVisibleInATM(boolean autoPaymentVisibleInATM)
	{
		this.autoPaymentVisibleInATM = autoPaymentVisibleInATM;
	}

	public boolean isAutoPaymentVisibleInERMB()
	{
		return autoPaymentVisibleInERMB;
	}

	public void setAutoPaymentVisibleInERMB(boolean autoPaymentVisibleInERMB)
	{
		this.autoPaymentVisibleInERMB = autoPaymentVisibleInERMB;
	}

	public Integer getVersionAPI()
	{
		return versionAPI;
	}

	public void setVersionAPI(Integer versionAPI)
	{
		this.versionAPI = versionAPI;
	}

	/**
	 * @return версия mAPI в виде объекта VersionNumber или null, если не заполнена
	 */
	public VersionNumber getMapiVersionNumber() throws MalformedVersionFormatException
	{
		return versionAPI == null ? null : VersionNumber.fromSolid(versionAPI);
	}

	public boolean isTemplateSupported()
	{
		return templateSupported;
	}

	public void setTemplateSupported(boolean templateSupported)
	{
		this.templateSupported = templateSupported;
	}

	/**
	 * @return мап подерживаемых схем автоплатежей
	 */
	public Map<AutoSubType, AutoPaySchemeBase> getAutoPaySchemes()
	{
		return autoPaySchemes;
	}

	/**
	 * Установить мап поддерживаемых автоплатежей
	 * @param autoPaySchemes мап поддерживаемых автоплатежей
	 */
	public void setAutoPaySchemes(Map<AutoSubType, AutoPaySchemeBase> autoPaySchemes)
	{
		this.autoPaySchemes = autoPaySchemes;
	}

	/**
	 * Установить признак необходимости обновления(!) полей видимости автоплатежей.
	 * @param needAutoPaymentVisibleApply true/false
	 */
	public void setNeedAutoPaymentVisibleApply(boolean needAutoPaymentVisibleApply)
	{
		this.needAutoPaymentVisibleApply = needAutoPaymentVisibleApply;
	}

	public boolean isNeedAutoPaymentVisibleApply()
	{
		return this.needAutoPaymentVisibleApply;
	}

	/**
	 * @param planingForDeactivate планируется к отключению.
	 */
	public void setPlaningForDeactivate(Boolean planingForDeactivate)
	{
		this.planingForDeactivate = planingForDeactivate;
	}

	/**
	 * @return планируется к отключению.
	 */
	public Boolean getPlaningForDeactivate()
	{
		return planingForDeactivate;
	}

    public boolean isAutoPaymentSupportedInSocialApi()
    {
        return autoPaymentSupportedInSocialApi;
    }

    public void setAutoPaymentSupportedInSocialApi(boolean autoPaymentSupportedInSocialApi)
    {
        this.autoPaymentSupportedInSocialApi = autoPaymentSupportedInSocialApi;
    }

    public boolean isAutoPaymentVisibleInSocialApi()
    {
        return autoPaymentVisibleInSocialApi;
    }

    public void setAutoPaymentVisibleInSocialApi(boolean autoPaymentVisibleInSocialApi)
    {
        this.autoPaymentVisibleInSocialApi = autoPaymentVisibleInSocialApi;
    }
}
