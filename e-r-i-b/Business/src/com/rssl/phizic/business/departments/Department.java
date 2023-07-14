package com.rssl.phizic.business.departments;

import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Map;

/**
 * Описание смотрите <a href="http://oziris/interbank/devi/DocLib4/Аналитика/Требования Сбербанка России.doc">тут</a>
 * @author Evgrafov
 * @ created 15.08.2006
 * @ $Author: egorovaav $
 * @ $Revision: 62128 $
 */
public abstract class Department implements Office, Routable, MultiBlockDictionaryRecord
{
	private Long id;

	private Comparable synchKey;
	private String BIC;
	protected Code code;

	private String name;
	private String city;
	private String address;
	private String location;
	private String telephone;
	private boolean main;
	//обслуживается или нет подразделение (можно заводить клиентов)
	private boolean service;

	private Time weekOperationTimeBegin;
	private Time weekOperationTimeEnd;

	private Time weekendOperationTimeBegin;
	private Time weekendOperationTimeEnd;

	private Time fridayOperationTimeBegin;
	private Time fridayOperationTimeEnd;

	private String timeScale;
	private Long notifyContractCancelation;

	private BigDecimal connectionCharge;
	private BigDecimal monthlyCharge;
	private BigDecimal reconnectionCharge;
	//часовой пояс
	private int timeZone;
	//id адаптера
	private String adapterUUID;
	//id биллинга
	private Long billingId;

	private boolean creditCardOffice;    // возможность получать кредитную карту в офисе
	private boolean openIMAOffice; // возможность выдачи драгметаллов в офисе
	private boolean needUpdateCreditCardOffice;
	private boolean possibleLoansOperation;
	private boolean active;

	/**
	 * @deprecated будет удален
	 */
	public boolean getMain()
	{
		return main;
	}

	/**
	 * @deprecated будет удален
	 */
	public void setMain(boolean main)
	{
		this.main = main;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public Time getFridayOperationTimeBegin()
	{
		return fridayOperationTimeBegin;
	}

	public void setFridayOperationTimeBegin(Time fridayOperationTimeBegin)
	{
		this.fridayOperationTimeBegin = fridayOperationTimeBegin;
	}

	public Time getFridayOperationTimeEnd()
	{
		return fridayOperationTimeEnd;
	}

	public void setFridayOperationTimeEnd(Time fridayOperationTimeEnd)
	{
		this.fridayOperationTimeEnd = fridayOperationTimeEnd;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Time getWeekendOperationTimeBegin()
	{
		return weekendOperationTimeBegin;
	}

	public void setWeekendOperationTimeBegin(Time weekendOperationTimeBegin)
	{
		this.weekendOperationTimeBegin = weekendOperationTimeBegin;
	}

	public Time getWeekendOperationTimeEnd()
	{
		return weekendOperationTimeEnd;
	}

	public void setWeekendOperationTimeEnd(Time weekendOperationTimeEnd)
	{
		this.weekendOperationTimeEnd = weekendOperationTimeEnd;
	}

	public Time getWeekOperationTimeBegin()
	{
		return weekOperationTimeBegin;
	}

	public void setWeekOperationTimeBegin(Time weekOperationTimeBegin)
	{
		this.weekOperationTimeBegin = weekOperationTimeBegin;
	}

	/**
	 * @return конец операционного дня
	 */
	public Time getWeekOperationTimeEnd()
	{
		return weekOperationTimeEnd;
	}

	public void setWeekOperationTimeEnd(Time weekOperationTimeEnd)
	{
		this.weekOperationTimeEnd = weekOperationTimeEnd;
	}

	/**
	 * @return Уведомлять о расторжении договора за Y дней
	 */
	public Long getNotifyContractCancelation()
	{
		return notifyContractCancelation;
	}

	/**
	 * Уведомлять о расторжении договора за Y дней
	 */
	public void setNotifyContractCancelation(Long notifyContractCancelation)
	{
		this.notifyContractCancelation = notifyContractCancelation;
	}

	/**
	 * @return шкала времени
	 */
	public String getTimeScale()
	{
		return timeScale;
	}

	/**
	 * шкала времени
	 */
	public void setTimeScale(String timeScale)
	{
		this.timeScale = timeScale;
	}

	public BigDecimal getConnectionCharge()
	{
		return connectionCharge;
	}

	public void setConnectionCharge(BigDecimal connectionCharge)
	{
		this.connectionCharge = connectionCharge;
	}

	public BigDecimal getMonthlyCharge()
	{
		return monthlyCharge;
	}

	public void setMonthlyCharge(BigDecimal monthlyCharge)
	{
		this.monthlyCharge = monthlyCharge;
	}

	@Deprecated
	public BigDecimal getReconnectionCharge()
	{
		return reconnectionCharge;
	}

	@Deprecated
	public void setReconnectionCharge(BigDecimal reconnectionCharge)
	{
		this.reconnectionCharge = reconnectionCharge;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String toString()
	{
		return (String) synchKey;
	}

	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	public String getBIC()
	{
		return BIC;
	}

	public void setBIC(String BIC)
	{
		this.BIC = BIC;
	}

	public abstract Code getCode();

	public abstract void setCode(Code code);

	public abstract void buildCode(Map<String, Object> codeFields);

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Department that = (Department) o;
		if (address != null ? !address.equals(that.address) : that.address != null)
			return false;
		if (code != null ? !code.equals(that.code) : that.code != null)
			return false;
		if (name != null ? !name.equals(that.name) : that.name != null)
			return false;
		if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null)
			return false;
		if (synchKey != null ? !synchKey.equals(that.synchKey) : that.synchKey != null)
			return false;
		if (creditCardOffice != that.isCreditCardOffice())
			return false;
		if (openIMAOffice != that.isOpenIMAOffice())
			return false;

		return true;
	}

	public int hashCode()
	{
		int result = (synchKey != null ? synchKey.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
		result = 31 * result + (code != null ? code.hashCode() : 0);
		result = 31 * result + (creditCardOffice ? 1 : 0);
		result = 31 * result + (openIMAOffice ? 1 : 0);

		return result;
	}

	public void updateFrom(DictionaryRecord office)
	{
		BeanHelper.copyProperties(this, office);
	}

	public abstract String getFullName();

	public int getTimeZone()
	{
		return timeZone;
	}

	public void setTimeZone(int timeZone)
	{
		this.timeZone = timeZone;
	}

	public boolean isService()
	{
		return service;
	}

	public void setService(boolean service)
	{
		this.service = service;
	}

	public String getAdapterUUID()
	{
		return adapterUUID;
	}

	public void setAdapterUUID(String adapterUUID)
	{
		this.adapterUUID = adapterUUID;
	}

	public Long getBillingId()
	{
		return billingId;
	}

	public void setBillingId(Long billingId)
	{
		this.billingId = billingId;
	}

	public void storeRouteInfo(String info)
	{
		setSynchKey(IDHelper.storeRouteInfo((String) getSynchKey(), info));
	}

	public String restoreRouteInfo()
	{
		return IDHelper.restoreRouteInfo((String) getSynchKey());
	}

	public String removeRouteInfo()
	{
		String info = IDHelper.restoreRouteInfo((String) synchKey);
		setSynchKey(IDHelper.restoreOriginalId((String) synchKey));

		return info;
	}

	public void setCreditCardOffice(boolean creditCardOffice)
	{
		this.creditCardOffice = creditCardOffice;
	}

	public boolean isCreditCardOffice()
	{
		return creditCardOffice;
	}

	public boolean isNeedUpdateCreditCardOffice()
	{
		return needUpdateCreditCardOffice;
	}

	public void setNeedUpdateCreditCardOffice(boolean needUpdateCreditCardOffice)
	{
		this.needUpdateCreditCardOffice = needUpdateCreditCardOffice;
	}

	public boolean isOpenIMAOffice()
	{
		return openIMAOffice;
	}

	public void setOpenIMAOffice(boolean openIMAOffice)
	{
		this.openIMAOffice = openIMAOffice;
	}

	/**
	 * @return возвращает номер тербанка подразделения
	 */
	public String getRegion()
	{
		return new SBRFOfficeCodeAdapter(getCode()).getRegion();
	}

	/**
	 * @return ОСБ подразделения
	 */
	public String getOSB()
	{
		return new SBRFOfficeCodeAdapter(getCode()).getBranch();
	}

	/**
	 * @return Офис подразделения
	 */
	public String getVSP()
	{
		return new SBRFOfficeCodeAdapter(getCode()).getOffice();
	}

	public String getMultiBlockRecordId()
	{
		return getSynchKey().toString();
	}

	/**
	 * @return Возможны операции по кредитованию физ. лиц
	 */
	public boolean isPossibleLoansOperation()
	{
		return possibleLoansOperation;
	}

	public void setPossibleLoansOperation(boolean possibleLoansOperation)
	{
		this.possibleLoansOperation = possibleLoansOperation;
	}

	/**
	 * @return Признак активности подразделения (подразделение есть в БД ЕРИБ и справочнике подразделений)
	 */
	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}
}