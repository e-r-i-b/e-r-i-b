package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.DaysOfWeek;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.ErmbProfile;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.DateHelper;

import java.sql.Time;
import java.util.*;

/**
 * User: Moshenko
 * Date: 02.10.12
 * Time: 12:44
 * �������� ������� ����
 */
public class ErmbProfileImpl implements ErmbProfile
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	
	private Long id;
	private Person person;
	private boolean serviceStatus = false; //��������� ������(true - �������, false - �� ���������� )
	private boolean clientBlocked = false; //���������� �����������(true - ������������� ��������, false - �� ������������� )
	private boolean paymentBlocked = false; //����������� �� �� ������ (true - ������������� �� �� ������, false - �� ������������� )
	private Calendar endServiceDate;
	private CardLink foreginProduct;
	private Calendar connectionDate;    //���� ������� ����������� ������
	private boolean newProductNotification = false;
	private boolean newProductShowInSms = false;
	private Time notificationStartTime =  Time.valueOf("09:00:00");
	private Time notificationEndTime = Time. valueOf("22:00:00");
	private long timeZone;
	private DaysOfWeek daysOfWeek = new DaysOfWeek(false);
	private boolean suppressAdv = false;
	private boolean migrationConflict = false;
	private boolean depositsTransfer = true;
	private ErmbTariff tarif;
	private boolean fastServiceAvailable = false;
	private Set<ErmbClientPhone> phones;
	private Long profileVersion;
	private Long confirmProfileVersion;
	private Calendar lockTime;
	private String lockDescription;
	private Calendar fppUnloadDate;
	private Calendar chargeNextDate = DateHelper.getCurrentDate();
	private Integer chargeDayOfMonth;
	private boolean fppInProgress = false;
	private boolean codActivated = false;
	private boolean wayActivated = false;
	private Calendar activationTryDate = DateHelper.getCurrentDate();
	private Long incompleteSmsPayment;
	private boolean fppBlocked; //������ "������� ���������� ��� �������� ����������� �����"
	private Calendar gracePeriodEnd;        //���� ��������� �����-�������. ������������ ��� ������ ����������� � �� �������� �����.
	private boolean mbkPaymentBlocked;      //������� ����������� �� ��� ��� ��������������� �� ��������.
	/**
	 * Id  ������������ � ������� ������������ �����������
	 */
	private Long connectedDepartment ;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
		// TODO: (����) �������� �� one-to-one �������. ����������� ����� �.
		person.setErmbProfile(this);
	}

	/**
	 * @return ���� ��������� �������������� ������.����, �� ������� ������ ��������
	 */
	public Calendar getEndServiceDate()
	{
		return endServiceDate;
	}

	public void setEndServiceDate(Calendar endServiceDate)
	{
		this.endServiceDate = endServiceDate;
	}

	/**
	 * @return ������������ ������� ��� �������� ����������� �����(���� ����)
	 */
	public CardLink getForeginProduct()
	{
		return foreginProduct;
	}

	public void setForeginProduct(CardLink foreginProduct)
	{
		this.foreginProduct = foreginProduct;
	}

	/**
	 * @return ���� ������� ����������� ������
	 */
	public Calendar getConnectionDate()
	{
		return connectionDate;
	}

	public void setConnectionDate(Calendar connectionDate)
	{
		this.connectionDate = connectionDate;
	}

	/**
	 * @return ������� �������� ����������� ��� ����� ���������
	 */
	public boolean getNewProductNotification()
	{
		return newProductNotification;
	}

	public void setNewProductNotification(boolean newProductNotification)
	{
		this.newProductNotification = newProductNotification;
		if (ConfigFactory.getConfig(ErmbConfig.class).getProductAvailabilityCommonAttribute())
			this.newProductShowInSms = newProductNotification;
	}

	/**
	 * @return ������� ��������� � ���-������ ��� ����� ���������
	 */
	public boolean getNewProductShowInSms()
	{
		if (ConfigFactory.getConfig(ErmbConfig.class).getProductAvailabilityCommonAttribute())
			return newProductNotification;
        return newProductShowInSms;
	}

	public void setNewProductShowInSms(boolean newProductShowInSms)
	{
		this.newProductShowInSms = newProductShowInSms;
		if (ConfigFactory.getConfig(ErmbConfig.class).getProductAvailabilityCommonAttribute())
         this.newProductNotification = newProductShowInSms;
	}

	/**
	 * @return ����� ������ �������� �����������
	 */
	public Time getNotificationStartTime()
	{
		return notificationStartTime;
	}

	public void setNotificationStartTime(Time notificationStartTime)
	{
		this.notificationStartTime = notificationStartTime;
	}

	/**
	 * @return ����� ��������� �������� �����������
	 */
	public Time getNotificationEndTime()
	{
		return notificationEndTime;
	}

	public void setNotificationEndTime(Time notificationEndTime)
	{
		this.notificationEndTime = notificationEndTime;
	}

	/**
	 * @return ������� ����
	 */
	public long getTimeZone()
	{
		return timeZone;
	}

	public void setTimeZone(long timeZone)
	{
		this.timeZone = timeZone;
	}

	/**
	 * @return ��� ������ � ������� ���������� �����������.
	 */
	public DaysOfWeek getDaysOfWeek()
	{
		return daysOfWeek;
	}

	public void setDaysOfWeek(DaysOfWeek daysOfWeek)
	{
		this.daysOfWeek = daysOfWeek;
	}

	/**
	 * @return ������� ������� ��������� ��������
	 */
	public boolean isSuppressAdv()
	{
		return suppressAdv;
	}

	public void setSuppressAdv(boolean suppressAdv)
	{
		this.suppressAdv = suppressAdv;
	}

	/**
	 * @return �����
	 */
	public ErmbTariff getTarif()
	{
		return tarif;
	}

	/**
	 * ������ ��� hibernate, ��� ��������� ������������ ErmbHelper.updateTariff
	 * @param tarif �����
	 */
	public void setTarif(ErmbTariff tarif)
	{
		this.tarif = tarif;
	}

	/**
	 * @return  ������� ��������� ��� ������� ����������� ������ ������ �������� � ��������� �� ������ ��������
	 */
	public boolean getFastServiceAvailable()
	{
		return fastServiceAvailable;
	}

	public void setFastServiceAvailable(boolean fastServiceAvailable)
	{
		this.fastServiceAvailable = fastServiceAvailable;
	}

	/**
	 * @return �������� ����������� � �������
	 */
	public Set<ErmbClientPhone> getPhones()
	{
		return phones;
	}

	public void setPhones(Set<ErmbClientPhone> phones)
	{
		this.phones = phones;
	}

	/**
	 * @return ������� �������� ����������� � ������������� ��������� �� �������
	 */
	public boolean getDepositsTransfer()
	{
		return depositsTransfer;
	}

	public void setDepositsTransfer(boolean depositsTransfer)
	{
		this.depositsTransfer = depositsTransfer;
	}

	/**
	 * @return CardLink-� �������
	 */
	public List<CardLink> getCardLinks()
	{
		MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
		List<CardLink> cardLinks = new ArrayList<CardLink>();
		try
		{
			cardLinks = externalResourceService.getLinks(getPerson().getLogin(), CardLink.class,null);
		}
		catch (BusinessException e)
		{
			log.error("����. ������ ��� ��������� ���� ������� id" + person.getId(), e);
		}
		catch (BusinessLogicException e)
		{
			log.error("����. ������ ��� ��������� ���� ������� id"+person.getId(),e);
		}
		return cardLinks;
	}

	/**
	 * @return  AccountLink-� �������
	 */
	public List<AccountLink> getAccountLinks()
	{
		MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
		List<AccountLink> accountLinks = new ArrayList<AccountLink>();
		try
		{
			accountLinks = externalResourceService.getLinks(getPerson().getLogin(), AccountLink.class,null);
		}
		catch (BusinessException e)
		{
			log.error("����. ������ ��� ��������� ������ ������� id"+person.getId(),e);
		}
		catch (BusinessLogicException e)
		{
			log.error("����. ������ ��� ��������� ������ ������� id"+person.getId(),e);
		}
		return accountLinks;
	}

	/**
	 * @return  LoanLink-� �������
	 */
	public List<LoanLink> getLoanLinks()
	{
		MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
		List<LoanLink> loanLinks = new ArrayList<LoanLink>();
		try
		{
			loanLinks = externalResourceService.getLinks(getPerson().getLogin(), LoanLink.class,null);
		}
		catch (BusinessException e)
		{
			log.error("����. ������ ��� ��������� �������� ������� id"+person.getId(),e);
		}
		catch (BusinessLogicException e)
		{
			log.error("����. ������ ��� ��������� �������� ������� id"+person.getId(),e);
		}
		return loanLinks;
	}

    private List<ErmbProductLink> getLink()
    {
        List<ErmbProductLink> result = new ArrayList<ErmbProductLink>();
        result.addAll(getCardLinks());
        result.addAll(getAccountLinks());
        result.addAll(getLoanLinks());
        return result;
    }
    /**
     * @return ��������, ��������� � ��������-����������
     */
    public List<ErmbProductLink> getInternetProduct()
    {
        List<ErmbProductLink> result = new ArrayList<ErmbProductLink>();
        for (ErmbProductLink link:getLink())
            if (link.getShowInSystem())
                result.add(link);
        return result;
    }
    /**
     * @return ��������, ��������� � ��������� ����������.
     */
    public List<ErmbProductLink> getMobileProduct()
    {
        List<ErmbProductLink> result = new ArrayList<ErmbProductLink>();
        for (ErmbProductLink link:getLink())
            if (link.getShowInMobile())
                result.add(link);
        return result;
    }
    /**
     * @return ��������, ��������� � ����������� ����������������.
     */
    public List<ErmbProductLink> getAtmProduct()
    {
        List<ErmbProductLink> result = new ArrayList<ErmbProductLink>();
        for (ErmbProductLink link:getLink())
            if (link.getShowInATM())
                result.add(link);
        return result;
    }
    /**
     * @return ��������, ��������� � ���-������.
     */
    public List<ErmbProductLink> getSmsProduct()
    {
        List<ErmbProductLink> result = new ArrayList<ErmbProductLink>();
        for (ErmbProductLink link:getLink())
            if (link.getShowInSms())
                result.add(link);
        return result;
    }

	/**
	 * @return �����, ��������� ��� ������ � �������� ��������� ��� ������ ����
	 */
	public List<CardLink> getErmbAvailablePaymentCards()
	{
		List<CardLink> cardsAll = getCardLinks();
		return CardsUtil.filterPotentialErmbPaymentCards(cardsAll);
	}

    /**
     * @return �������� �������, �� ������� ������ ������������ ����������.
     */
    public List<ErmbProductLink> getErmbNotificationProduct()
    {
        List<ErmbProductLink> result = new ArrayList<ErmbProductLink>();
        for (ErmbProductLink link:getLink())
            if (link.getErmbNotification())
                result.add(link);
        return result;
    }



	public PersonDocument getMainPersonDocument()
	{
		return PersonHelper.getMainPersonDocument(person);
	}

	/**
	 * �������� ������� ����� ���. ��������
	 * @return ����� ��������
	 */
	public String getMainPhoneNumber()
	{
		if (phones == null)
			return null;
		for (ErmbClientPhone phone : phones)
		{
			if (phone.isMain())
				return phone.getNumber();
		}
		return null;
	}

	/**
	 * �������� ������ � �������� ���. ��������� �������
	 * @return ������ �������
	 */
	public Set<String> getPhoneNumbers()
	{
		Set<String> phoneNumbers = new HashSet<String>();
		for (ErmbClientPhone phone : phones)
		{
			phoneNumbers.add(phone.getNumber());
		}
		return phoneNumbers;
	}

	public Long getProfileVersion()
	{
		return profileVersion;
	}

	public void setProfileVersion(Long profileVersion)
	{
		this.profileVersion = profileVersion;
	}

	public Long getConfirmProfileVersion()
	{
		return confirmProfileVersion;
	}

	public void setConfirmProfileVersion(Long confirmProfileVersion)
	{
		this.confirmProfileVersion = confirmProfileVersion;
	}

	public String getLockDescription()
	{
		return lockDescription;
	}

	public void setLockDescription(String lockDescription)
	{
		this.lockDescription = lockDescription;
	}

	public Calendar getLockTime()
	{
		return lockTime;
	}

	public void setLockTime(Calendar lockTime)
	{
		this.lockTime = lockTime;
	}

	public boolean isFppInProgress()
	{
		return fppInProgress;
	}

	public void setFppInProgress(boolean fppInProgress)
	{
		this.fppInProgress = fppInProgress;
	}

	public Calendar getChargeNextDate()
	{
		return chargeNextDate;
	}

	/**
	 * ������ ���������� �������
	 * @see ErmbChargeDateUpdater
	 * @param chargeNextDate
	 */
	public void setChargeNextDate(Calendar chargeNextDate)
	{
		this.chargeNextDate = chargeNextDate;
	}

	public Calendar getFppUnloadDate()
	{
		return fppUnloadDate;
	}

	public void setFppUnloadDate(Calendar fppUnloadDate)
	{
		this.fppUnloadDate = fppUnloadDate;
	}

	public boolean isServiceStatus()
	{
		return serviceStatus;
	}

	public void setServiceStatus(boolean serviceStatus)
	{
		this.serviceStatus = serviceStatus;
	}

	public boolean isClientBlocked()
	{
		return clientBlocked;
	}

	public void setClientBlocked(boolean clientBlocked)
	{
		this.clientBlocked = clientBlocked;
	}

	public boolean isPaymentBlocked()
	{
		return paymentBlocked;
	}

	public void setPaymentBlocked(boolean paymentBlocked)
	{
		this.paymentBlocked = paymentBlocked;
	}

	public Integer getChargeDayOfMonth()
	{
		return chargeDayOfMonth;
	}

	/**
	 * ������ ���������� �������
	 * @see ErmbChargeDateUpdater
	 * @param chargeDayOfMonth
	 */
	public void setChargeDayOfMonth(Integer chargeDayOfMonth)
	{
		this.chargeDayOfMonth = chargeDayOfMonth;
	}

	/**
	 * @return ������� ���������������� ���� � ���
	 */
	public boolean isCodActivated()
	{
		return codActivated;
	}

	public void setCodActivated(boolean codActivated)
	{
		this.codActivated = codActivated;
	}

	/**
	 * @return ������� ���������������� ���� � WAY
	 */
	public boolean isWayActivated()
	{
		return wayActivated;
	}

	public void setWayActivated(boolean wayActivated)
	{
		this.wayActivated = wayActivated;
	}

	/**
	 * @return ���� ��������� ������� ��������� ������� � ���/WAY
	 */
	public Calendar getActivationTryDate()
	{
		return activationTryDate;
	}

	public void setActivationTryDate(Calendar activationTryDate)
	{
		this.activationTryDate = activationTryDate;
	}

	/**
	 * @return ��������� ������������� (DRAFT) ������ � ���-������
	 */
	public Long getIncompleteSmsPayment()
	{
		return incompleteSmsPayment;
	}

	public void setIncompleteSmsPayment(Long incompleteSmsPayment)
	{
		this.incompleteSmsPayment = incompleteSmsPayment;
	}

	/**
	 * true, ���� ��� �������� ����. ����� ������� �������� "���������" (��������, � ������� ��� �� ����� �����)
	 * @return
	 */
	public boolean isFppBlocked()
	{
		return fppBlocked;
	}

	public void setFppBlocked(boolean fppBlocked)
	{
		this.fppBlocked = fppBlocked;
	}

	public Calendar getGracePeriodEnd()
	{
		return gracePeriodEnd;
	}

	public void setGracePeriodEnd(Calendar gracePeriodEnd)
	{
		this.gracePeriodEnd = gracePeriodEnd;
	}

	public Long getConnectedDepartment()
	{
		return connectedDepartment;
	}

	public void setConnectedDepartment(Long connectedDepartment)
	{
		this.connectedDepartment = connectedDepartment;
	}

	public boolean isMigrationConflict()
	{
		return migrationConflict;
	}

	public void setMigrationConflict(boolean migrationConflict)
	{
		this.migrationConflict = migrationConflict;
	}

	public boolean isMbkPaymentBlocked()
	{
		return mbkPaymentBlocked;
	}

	public void setMbkPaymentBlocked(boolean mbkPaymentBlocked)
	{
		this.mbkPaymentBlocked = mbkPaymentBlocked;
	}
}
