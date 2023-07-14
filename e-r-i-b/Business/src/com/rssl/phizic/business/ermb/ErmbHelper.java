package com.rssl.phizic.business.ermb;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.ermb.card.ErmbPaymentCardLinkFilter;
import com.rssl.phizic.business.ermb.card.PrimaryCardResolver;
import com.rssl.phizic.business.ermb.products.ErmbNotificationSettingsController;
import com.rssl.phizic.business.ermb.profile.ErmbProfileListener;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.ActiveNotVirtualNotCreditOwnCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.ermb.ErmbProfileService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mobilebank.MobileBankCardInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.ErmbProfile;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.*;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.Time;
import java.util.*;

/**
 * ������ ��� ������ � ����-�������� (�������� ������� ������� � ������� � �������� ������)
 *
 @ author: Egorovaa
 @ created: 15.10.2012
 @ $Author$
 @ $Revision$
 */
@SuppressWarnings("OverlyComplexClass")
public class ErmbHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ErmbTariffService tariffService = new ErmbTariffService();
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private static final ErmbClientPhoneService phoneService = new ErmbClientPhoneService();
	private static final PersonService personService = new PersonService();
	private static final ServiceProviderService providerService = new ServiceProviderService();

	public static final String NO_PHONE_BLOCK_DESCRIPTION = "������� ������������ ��-�� ���������� ���������.";

	/**
	 * ���������, ��������� �� ������ � ����
	 * @return true, ���� ����� ������� ����
	 */
	@SuppressWarnings({"ReuseOfLocalVariable"})
	public static boolean isERMBConnectedPerson()
	{
		if (!PersonContext.isAvailable())
		{
			return false;
		}

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData.isGuest())
		{
			return false;
		}

		Boolean hasErmbProfile = personData.isErmbSupported();
		// ���� ������� ������� ����-������� �� ����������
		if (hasErmbProfile == null)
		{
			// ���������, ���� ���� �� � ������� ����-�������
			hasErmbProfile = isERMBConnected(personData.getPerson());
			personData.setErmbSupported(hasErmbProfile);
			return hasErmbProfile;
		}
		// ����� - ������������ ��������, ������������� � PersonData
		else
			return hasErmbProfile;
	}

	/**
	 * @param person �������� ������������
	 * @return ������� ���� �� ������� ����
	 */
	public static boolean isERMBConnected(Person person)
	{
		ErmbProfileImpl profile = getErmbProfileByPerson(person);
		return profile != null && !profile.isMigrationConflict();
	}

	/**
	 * @param person    �������
	 * @return ������� ���� �� �������
	 */
	public static ErmbProfileImpl getErmbProfileByPerson(Person person)
	{
		if (person == null)
			return null;

		try
		{
			return profileService.findByUser(person);
		}
		catch (BusinessException e)
		{
			log.info("������ ��� ������ ������� ���� � id=" + person.getId(), e);
			return null;
		}
	}

	/**
	 * @param cardNumber    ����� �����
	 * @return ������� ���� �� ������ �����
	 */
	public static List<ErmbProfile> getErmbProfilesByCardNumber(String cardNumber)
	{
		ErmbProfileService ermbProfileService = GateSingleton.getFactory().service(ErmbProfileService.class);
		try
		{
			return ermbProfileService.getErmbProfiles(cardNumber);
		}
		catch (GateException e)
		{
			log.error("������ ��� ��������� �������� ���� �� ������ ����� " + MaskUtil.getCutCardNumberForLog(cardNumber), e);
			return null;
		}
	}

	/**
	 * @param login    �����
	 * @return ������� ���� �� ������
	 */
	public static ErmbProfileImpl getErmbProfileByLogin(Login login)
	{
		try
		{
			return profileService.findByLogin(login);
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� ������� ���� �� ������ � id=" + login.getId(), e);
			return null;
		}
	}

	/**
	 * ������� ����� ������� (��� ���������)
	 * @param person   person
	 * @param serviceStatus ��������� ������
	 * @param tariff ����� ����������� (null - ���������� ������ ����������)
	 * @param setConnectedDepartment id ������������ �����������
	 * @return ������� ERMB
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static ErmbProfileImpl createErmbProfile(Person person, boolean serviceStatus, ErmbTariff tariff, Long setConnectedDepartment) throws BusinessException
	{
	    ErmbProfileImpl ermbProfile = new ErmbProfileImpl();
	    ermbProfile.setPerson(person);
		if (!ermbProfile.isServiceStatus())
		{
			ermbProfile.setServiceStatus(serviceStatus);
			ermbProfile.setConnectionDate(DateHelper.getCurrentDate());
		}
		if (tariff == null)
		{
			List<ErmbTariff> tariffs = tariffService.getAllTariffs();
			if (tariffs.isEmpty())
				throw new BusinessException("���������� �������� ���� �� ���� ����� ����������� ����");
			tariff = tariffs.get(0);
		}
		//����� ���������� ������
		updateErmbTariff(ermbProfile, tariff);
	    //��������� ��������� ����  ������
		ermbProfile.setTimeZone(com.rssl.phizic.common.type.TimeZone.MOSCOW.getCode());
		ermbProfile.setConfirmProfileVersion(0L);
		ermbProfile.setConnectedDepartment(setConnectedDepartment);
		return ermbProfile;
	}

    /**
     *
     * @param phoneNumbers �������� �������
     * @param ermbProfile ������� ERMB
     * @return Set<ErmbClientPhone>
     * @throws BusinessException
     */
    public static Set<ErmbClientPhone> getAllErmbClientPhones(Set<String> phoneNumbers, ErmbProfileImpl ermbProfile) throws BusinessException
    {
        Set<ErmbClientPhone> ermbPhones = new HashSet<ErmbClientPhone>();
        for (String phone:phoneNumbers)
        {
            String resPhone = getValidPhoneNumber(phone);
            if (!StringHelper.isEmpty(resPhone))
                ermbPhones.add(new ErmbClientPhone(resPhone, ermbProfile));
        }
        return ermbPhones;
    }

	/**
	 * ������� ������ ��������� �� ������ ���
	 * @param registrations ����� ������
	 * @return ��� ������ ���������
	 */
	public static Set<String> getPhonesFromMbkRegistrations(Collection<MobileBankRegistration> registrations)
	{
		Set<String> clientPhones = new HashSet<String>();

		for (MobileBankRegistration reg : registrations)
		{
			MobileBankCardInfo mobileBankCardInfo = reg.getMainCardInfo();
			if (mobileBankCardInfo != null)
			{
				String phone = mobileBankCardInfo.getMobilePhoneNumber();
				if (!StringHelper.isEmpty(phone))
				{
					String validPhone = getValidPhoneNumber(phone);
					if (!StringHelper.isEmpty(validPhone))
						clientPhones.add(validPhone);
				}

			}
		}

		return clientPhones;
	}

	/**
	 * ��������� ��������� �������
	 * @param profile - ���� �������
	 * @param sendSms ���������� ��� � ����������� (� �.�. ��������������)
	 * @throws BusinessException
	 */
	public static void saveCreatedProfile(ErmbProfileImpl profile, boolean sendSms) throws BusinessException
	{
		saveOrUpdateProfile(null,profile,sendSms);
	}

	/**
	 * ��������� �������
	 * @param oldProfile - ������ ������� ����
	 * @param profile - ���� �������
	 * @param sendSms ���������� ��� � ����������� (� �.�. ��������������)
	 * @throws BusinessException
	 */
	public static void saveOrUpdateProfile(ErmbProfileImpl oldProfile, ErmbProfileImpl profile, boolean sendSms) throws BusinessException
	{
		ErmbProfileEvent profileEvent = new ErmbProfileEvent(oldProfile);
		profileEvent.setSendSms(sendSms);
		profileEvent.setNewProfile(profile);
		ErmbProfileListener profileListener = ErmbUpdateListener.getListener();
		profileListener.beforeProfileUpdate(profileEvent);
		profileService.addOrUpdate(profile);
		profileListener.afterProfileUpdate(profileEvent);
	}

	/**
	 * ����������� �������� ����� �������� � ������������ � ��������(�������������) ��������
	 * @param phoneNumber ����� ��������
	 * @return ������ � ������� �������� � �������� �������
	 */
	public static String getValidPhoneNumber(String phoneNumber)
	{
		if (!StringHelper.isEmpty(phoneNumber)&& PhoneNumberFormat.check(phoneNumber))
			return PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phoneNumber);
		else
			return null;
	}

	/**
	 * ������� ������������ � ����������� �� � �������
	 * @param phoneNumbers ������ ���������
	 * @param profile ������� ����
	 * @return ����-��������
	 */
	public static Set<ErmbClientPhone> getErmbPhones(Set<String> phoneNumbers, ErmbProfileImpl profile)
	{
		Set<ErmbClientPhone> result = new HashSet<ErmbClientPhone>();
		for (String number: phoneNumbers)
		{
			ErmbClientPhone phone = new ErmbClientPhone(number, profile);
			result.add(phone);
		}
		return result;
	}

    /**
     * @param phoneNumber ����� ��������
     * @param profile �������
     * @return
     */
    public static ErmbClientPhone getErmbClientPhone(String phoneNumber, ErmbProfileImpl profile)
    {
        Set<ErmbClientPhone> phones = profile.getPhones();
        for (ErmbClientPhone phone:phones)
        {
            if (StringHelper.equals(phone.getNumber(),phoneNumber))
                return phone;
        }
        return new ErmbClientPhone(phoneNumber,profile);
    }

    /**
     * ����������� �� ������ ������� ������� ������� ����.
     * @param phoneNumber ����� ��������
     * @param profile ������� ���
     * @return true ���� ����� ����������� ������� �������
     */
    public static boolean isErmbEngagedPhones(String phoneNumber,ErmbProfileImpl profile) throws BusinessException
    {
        if (profile == null)
            throw new IllegalArgumentException("�������� 'profile' �� ����� ���� ������.");

        if (profile.getId() == null)
            throw new IllegalArgumentException("�������� 'profile' �� ����� �� �������������");

        if (StringHelper.isEmpty(phoneNumber))
            throw new IllegalArgumentException("�������� 'phoneNumber' �� ����� �� �������������");

		ErmbClientPhone clientPhone = phoneService.findPhoneByNumber(phoneNumber);
	    return clientPhone != null && !clientPhone.getProfile().getId().equals(profile.getId());
    }

	/**
	 * �������� ���� ������� �� ������ ��������
	 * @param phoneNumber
	 * @return
	 * @throws BusinessException
	 */
	public static ErmbProfileImpl getErmbProfileByPhone(String phoneNumber) throws BusinessException
	{
		if (StringHelper.isEmpty(phoneNumber))
			throw new IllegalArgumentException("�������� 'phoneNumber' �� ����� �� �������������");
		//����� �������� ������� ����
		ErmbClientPhone clientPhone = phoneService.findPhoneByNumber(phoneNumber);
		return clientPhone != null ? clientPhone.getProfile() : null;
	}

	/**
	 * @param phone ������� Ermb
	 * @param profile ������� Ermb
	 * @throws DublicatePhoneException ���������� � ������  ����������� �������� � ������� �������
	 * @throws BusinessException
	 */
	public static void addOrUpdateErmbClientPhones(ErmbClientPhone phone, ErmbProfileImpl profile) throws BusinessException, DublicatePhoneException
	{
		try
		{
			phoneService.addOrUpdate(phone);
			profile.getPhones().add(phone);
		}
		catch (DublicatePhoneException ex)
		{
			String phoneNumber = phone.getNumber();
			ErmbClientPhone duplicatePhone = phoneService.findPhoneByNumber(phoneNumber);
			Person dPerson = duplicatePhone.getProfile().getPerson();
			String dName = dPerson.getFirstName();
			String dSurname = dPerson.getSurName();
			String dPatName = dPerson.getPatrName();
			String FIO = (dSurname + " " + dName + " " + StringHelper.getEmptyIfNull(dPatName));
			String birthday = DateHelper.formatDateToStringWithPoint(dPerson.getBirthDay());
			String doc = "";
			PersonDocument personDocument = duplicatePhone.getProfile().getMainPersonDocument();
			if (personDocument != null)
			{
				doc = StringHelper.getEmptyIfNull(personDocument.getDocumentSeries()) +
						StringHelper.getEmptyIfNull(personDocument.getDocumentNumber());
			}
			throw new DublicatePhoneException("�����:" + phoneNumber + " ��� ������������ � �������." +
					"������ �������, ������������� ������� �����:" + FIO + " " + doc +
					" " + birthday, ex,phoneNumber);
		}
	}

	/**
	 * @param profile ������� ERMB
	 * @return ��������� ������� ���� ������ ������ ���� ��������� ���
	 */

	public static String getCleintSegmentCode(ErmbProfileImpl profile) throws BusinessException
	{

		SegmentCodeType segmentCodeType =  ((ActivePerson)profile.getPerson()).asClient().getSegmentCodeType();
		if  (segmentCodeType!=null)
			return segmentCodeType.getDescription();
		else
			return SegmentCodeType.NOTEXISTS.getDescription();
	}

	/**
	 * @param profile ������� ERMB
	 * @param tariff ����� ����������� �����
	 * @return ��������� ������ (��� ����� ������)
	 */
	public static Money getErmbCostNoGrace(ErmbProfileImpl profile, ErmbTariff tariff)
	{
		try
		{
			ErmbCostCalculator calculator = new ErmbCostCalculator(profile, tariff);
			return calculator.calculateNoGrace();
		}
		catch (Exception e)
		{
			log.error(e);
			return null;
		}
	}

	/**
	 * @param profile ������� ERMB
	 * @param tariff ����� ����������� �����
	 * @return ������������ ����� ��������, ��� �������� �������������� ������ ����������� �����
	 */
	public static String getClientProductClassName(ErmbProfileImpl profile, ErmbTariff tariff)
	{
		try
		{
			ErmbCostCalculator calculator = new ErmbCostCalculator(profile, tariff);
			return calculator.getProductClass().getValue();
		}
		catch (Exception e)
		{
			log.error(e);
			return "";
		}
	}

	/**
	 * ����������� ����-�������
	 * @param profile - ������� ��� �����������
	 * @return ����� ����������� �������
	 */
	public static ErmbProfileImpl copyProfile(ErmbProfileImpl profile) throws BusinessException
	{
		if (profile == null)
			throw new IllegalArgumentException("���������� ������� �� ����� ���� null");

		Map<Class, Class> types = new HashMap<Class,Class>();
		types.put(ErmbProfileImpl.class, ErmbProfileImpl.class);
		try
		{
			Set<ErmbClientPhone> phones = copyPhones(profile.getPhones());
			ErmbProfileImpl copy = BeanHelper.copyObject(profile, types);
			copy.setPhones(phones);
			return copy;
		}
		catch (Exception e)
		{
			throw new BusinessException("������ ��������� ���������� � ����-������� �������.", e);
		}
	}

	/**
	 * ������������ ����������� ��������, ����������� � ����-������� �������
	 * @param phones - �������� ��� �����������
	 * @return - �����
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static Set<ErmbClientPhone> copyPhones(Set<ErmbClientPhone> phones) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
	{
		if (phones == null)
			return new HashSet<ErmbClientPhone>();
		BeanUtilsBean utils = new BeanUtilsBean();
		Set<ErmbClientPhone> copyPhones = new HashSet<ErmbClientPhone>(phones.size());
		for (ErmbClientPhone phone : phones)
		{
			ErmbClientPhone copy = (ErmbClientPhone) utils.cloneBean(phone);
			copyPhones.add(copy);
		}
		return copyPhones;
	}

	/**
     * @param profile ������� ����.
     * @return ���� ������� � ���� ��� ��� ��������.
     */
    public static Map<ErmbFields,Object> getErmbProfileFields(ErmbProfileImpl profile)
    {
        if (profile == null)
            throw new IllegalArgumentException("�������� 'profile' �� ����� ���� null");

        Map<ErmbFields,Object> resultMap = new LinkedHashMap<ErmbFields, Object>();
        resultMap.put(ErmbFields.SUPPRESS_ADVERTISING, profile.isSuppressAdv());
        resultMap.put(ErmbFields.CLIENT_PHONES, profile.getPhones());
        resultMap.put(ErmbFields.INTERNET_CLIENT_SERVICE, profile.getInternetProduct());
        resultMap.put(ErmbFields.MOBILE_CLIENT_SERVICE, profile.getMobileProduct());
        resultMap.put(ErmbFields.ATM_CLIENT_SERVICE, profile.getAtmProduct());
        resultMap.put(ErmbFields.SERVICE_STATUS, profile.isServiceStatus());
        resultMap.put(ErmbFields.CLIENT_BLOCKED, profile.isClientBlocked());
        resultMap.put(ErmbFields.PAYMENT_BLOCKED, profile.isPaymentBlocked());
        resultMap.put(ErmbFields.START_SERVICE_DATE, profile.getConnectionDate());
        resultMap.put(ErmbFields.END_SERVICE_DATE, profile.getEndServiceDate());
        resultMap.put(ErmbFields.TARIFF, profile.getTarif());
        resultMap.put(ErmbFields.QUICK_SERVICES, profile.getFastServiceAvailable());
        resultMap.put(ErmbFields.ACTIVE_PHONE, profile.getMainPhoneNumber());
        resultMap.put(ErmbFields.SMS_CLIENT_SERVICE, profile.getSmsProduct());
        resultMap.put(ErmbFields.INFORM_RESOURCES, profile.getErmbNotificationProduct());
        resultMap.put(ErmbFields.CHARHE_CARD,ErmbHelper.getForeginProduct(profile));
        resultMap.put(ErmbFields.NEW_PRODUCT_NOTIFICATION,profile.getNewProductNotification());
	    resultMap.put(ErmbFields.NEW_PRODUCT_SHOW_IN_SMS, profile.getNewProductShowInSms());
        resultMap.put(ErmbFields.INFORM_PERION_DAY,profile.getDaysOfWeek());
        resultMap.put(ErmbFields.INFORM_PERION_BEGIN,profile.getNotificationStartTime());
        resultMap.put(ErmbFields.INFORM_PERION_END,profile.getNotificationEndTime());
        resultMap.put(ErmbFields.INFORM_PERION_TIME_ZONE,profile.getTimeZone());
        return resultMap;

    }
    /**
     * @param fileds1 ���� ������� ���� 1
     * @param fileds2 ���� ������� ���� 2
     * @return ������ ���� �� ������� 1 ������� ��� ��� ������� ���������� � ������� 2.
     */
    public static Map<ErmbFields,Object> getDifferentErmbProfileFields(Map<ErmbFields,Object> fileds1,Map<ErmbFields,Object> fileds2)
    {
        Map<ErmbFields,Object> resultMap = new LinkedHashMap<ErmbFields, Object>();

        for (Map.Entry<ErmbFields,Object> field: fileds1.entrySet())
        {
            ErmbFields key = field.getKey();
            Object obj1 = field.getValue();
            Object obj2 = fileds2.get(key);

            if (obj1 instanceof Calendar ||
                obj1 instanceof Long ||
                obj1 instanceof Time ||
                obj1 instanceof DaysOfWeek ||
                obj1 instanceof Boolean ||
                obj1 instanceof CardLink ||
                obj1 instanceof ErmbTariff)
            {
              if (!obj1.equals(obj2))
                 resultMap.put(key,obj1);
            }
            else if (obj1 instanceof String)
            {
                String str1 = (String) obj1;
                String str2 = (String) obj2;
                if (!StringHelper.equals(str1,str2))
                    resultMap.put(key,obj1);
            }
            //�������� ������� �������������  ����� key
            else if(key == ErmbFields.CLIENT_PHONES)
            {
                Set<ErmbClientPhone> phone1 = new HashSet<ErmbClientPhone>((Set)obj1);
                Set<ErmbClientPhone> phone2 = new HashSet<ErmbClientPhone>((Set)obj2);
                if (!phone1.containsAll(phone2))
                    resultMap.put(key,phone1);
            }
            else if (key == ErmbFields.INTERNET_CLIENT_SERVICE ||
                     key == ErmbFields.MOBILE_CLIENT_SERVICE ||
                     key == ErmbFields.ATM_CLIENT_SERVICE ||
                     key == ErmbFields.SMS_CLIENT_SERVICE ||
                     key == ErmbFields.INFORM_RESOURCES)
            {
                List<ErmbProductLink> product1 = (List<ErmbProductLink>) obj1;
                List<ErmbProductLink> product2 = (List<ErmbProductLink>) obj2;
                if (!product1.containsAll(product2))
                    resultMap.put(key,product1);
            }
            else
                if (obj1 != obj2)
                    resultMap.put(key,obj1);
        }
        return resultMap;
    }
    /**
     * @param ermbFields ���� ������� ����
     * @return ���� ����� ������� ���� � String ���
     */
    public static Map<String,String> ermbFieldsToStringView (Map<ErmbFields, Object> ermbFields)
    {
        Map<String,String> resultMap = new LinkedHashMap<String, String>();

        for (Map.Entry<ErmbFields,Object> field: ermbFields.entrySet())
        {
            ErmbFields key = field.getKey();
            String keyStr = field.getKey().toValue();
            Object value = field.getValue();

            if (value == null)
                resultMap.put(key.toValue(),"");
            else if (key == ErmbFields.INTERNET_CLIENT_SERVICE ||
                    key == ErmbFields.MOBILE_CLIENT_SERVICE ||
                    key == ErmbFields.ATM_CLIENT_SERVICE ||
                    key == ErmbFields.SMS_CLIENT_SERVICE ||
                    key == ErmbFields.INFORM_RESOURCES)
            {
                List<ErmbProductLink> productLinks =  (List<ErmbProductLink>)value;
                String[]  linkArr = null;
                for (ErmbProductLink link:productLinks)
                    linkArr = (String[]) ArrayUtils.add(linkArr,MaskUtil.getCutCardNumber(link.getNumber()));
                resultMap.put(keyStr,StringUtils.join(linkArr, ","));
            }
            else if(key == ErmbFields.CLIENT_PHONES)
            {
                Set<ErmbClientPhone> phoneSet = (Set<ErmbClientPhone>)value;
                String[]  phoneArr = null;
                for (ErmbClientPhone phone:phoneSet)
                    phoneArr = (String[]) ArrayUtils.add(phoneArr,phone.getNumber());
                resultMap.put(keyStr,StringUtils.join(phoneArr, ","));
            }
            else if(key == ErmbFields.INFORM_PERION_DAY)
            {
                DaysOfWeek daysOfWeek = (DaysOfWeek) value;
                String[] daysRus = daysOfWeek.getFullNameStrDaysRus();
                resultMap.put(keyStr,StringUtils.join(daysRus, ","));
            }
            else if (value instanceof Boolean)
            {
                Boolean val = (Boolean) value;
                resultMap.put(keyStr,val == true ? "���." : "����.");
            }
            else if (value instanceof Calendar)
            {
	            Calendar date = (Calendar) value;
	            resultMap.put(keyStr,DateHelper.formatDateWithStringMonth(date));
            }
            else
                resultMap.put(keyStr,value.toString());
        }
        return resultMap;
    }

	/**
	 * @param f1 ������� 1
	 * @param s1 ��� 1
	 * @param p1 �������� 1
	 * @param b1 �� 1
	 * @param f2 ������� 2
	 * @param s2 ��� 2
	 * @param p2 �������� 2
	 * @param b2 �� 2
	 * @return ��������� ���������� �� ��� ��
	 */
	public static boolean isSameFIOAndBirthday(String f1,String s1, String p1, Calendar b1, String f2, String s2, String p2, Calendar b2)
	{
		String clientFIO1 = (f1 + s1 + p1).toUpperCase();
		String clientFIO2 = (f2 + s2 + p2).toUpperCase();

		if (!StringHelper.equals(clientFIO1,clientFIO2))
			return false;

		if (b1.compareTo(b2) != 0)
			return false;

		return true;
	}

	/**
	 * true = �������� ������������� ������ �������� ��� ����������� ��������� � ���-������
	 * @return
	 */
	public static boolean isCommonAttributeUseInProductsAvailable()
	{
		return ConfigFactory.getConfig(ErmbConfig.class).getProductAvailabilityCommonAttribute();
	}

	/**
	 * true = �������� ���������� �� �������� � ���-������
	 * @return
	 */
	public static boolean isLoanSmsNotificationAvailable()
	{
		return ConfigFactory.getConfig(ErmbConfig.class).getLoanNotificationAvailability();
	}

	/**
	 * @param userInfo ���� �� �������
	 * @return ���� �� �������� ���� ���������� � ���
	 */
	public static boolean isCsaErmbConnected(UserInfo userInfo) throws BusinessException
	{
		try
		{
			Document response = CSABackRequestHelper.getErmbConnectorInfoRq(userInfo);
			return Boolean.valueOf(XmlHelper.getSimpleElementValue(response.getDocumentElement(), CSAResponseConstants.ERMB_CONNECTED));
		}
		catch (BackLogicException e)
		{
			throw new BusinessException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ������ � ��������� ���
	 * ��������� ��������:
	 *  � active � �������
	 *  � blocked �  �������������
	 *  � nonpayed � �� ��������
	 * @param profile ���� �������
	 * @return ������ ������ (�� ���������� - null)
	 */
	public static String getServiceStatus(ErmbProfileImpl profile)
	{
		if (!profile.isServiceStatus())
			return null;

		if (profile.isClientBlocked())
			return "blocked";
		else if (profile.isPaymentBlocked())
			return "nonpayed";
		else
			return "active";
	}

	/**
	 * ���������� ����� ����
	 * ��������� ���������� �� ����������� ����� � ������ ��������� ������
	 * @param profile ����-�������
	 * @param tariff �����
	 */
	public static void updateErmbTariff(ErmbProfileImpl profile, ErmbTariff tariff) throws BusinessException
	{
		if (tariff != null && !tariff.equals(profile.getTarif()))
		{
			//1. ���������� �����
			profile.setTarif(tariff);

			//2. ���������� ��������� �����-������� ��� ������� �����������
			if (profile.getGracePeriodEnd() == null)
				profile.setGracePeriodEnd(getGracePeriodEnding(profile, tariff));

			//3. �������� ���������� � �������� ����������� �����
			ErmbChargeDateUpdater updater = new ErmbChargeDateUpdater();
			updater.initChargeDate(profile);

			//4. �������� �������� ���-����������� �� ���������
			ErmbNotificationSettingsController notificationController = new ErmbNotificationSettingsController(profile);
			notificationController.resetAll();
		}
	}

	/**
	 * �������� �� ��� ����-������ ���-����������� �� ������
	 * @param profile �������
	 * @param tariff  ����-�����
	 * @return
	 */
	public static boolean isTariffAllowCardSmsNotification(ErmbProfileImpl profile, ErmbTariff tariff)
	{
		ErmbPermissionCalculator permissionCalculator = new ErmbPermissionCalculator(profile, tariff);
		return permissionCalculator.impliesCardNotification();
	}

	/**
	 * �������� �� ��� ����-������ ���-����������� �� ������
	 * @param profile �������
	 * @param tariff  ����-�����
	 * @return
	 */
	public static boolean isTariffAllowAccountSmsNotification(ErmbProfileImpl profile, ErmbTariff tariff)
	{
		ErmbPermissionCalculator permissionCalculator = new ErmbPermissionCalculator(profile, tariff);
		return permissionCalculator.impliesAccountNotification();
	}

	private static Calendar getGracePeriodEnding(ErmbProfileImpl ermbProfile, ErmbTariff tariff)
	{
		Calendar connectionDate = ermbProfile.getConnectionDate();
		int gracePeriodLength = tariff.getGracePeriod();

		return DateHelper.addMonths(DateHelper.getOnlyDate(connectionDate), gracePeriodLength);
	}

	/**
	 * ���������� ���������� ����������
	 * @param profile ����������� �������
	 * @param description ������� ����������
	 */
	public static void blockProfile(ErmbProfileImpl profile, String description)
	{
		profile.setClientBlocked(true);
		profile.setLockDescription(description);
		profile.setLockTime(Calendar.getInstance());
	}

	/**
	 * ������������� �������� ������ ����-������� � ����������� ���
	 * @param newProfile ������ ��������� �������
	 * @param oldProfile ����� ��������� �������
	 * @param updateAction �������� ����������� �� ����������
	 */
	public static void saveErmbDataWithCsaUpdate(ErmbProfileImpl newProfile, ErmbProfileImpl oldProfile, HibernateAction<Void> updateAction)
			throws BusinessLogicException, BusinessException
	{
		List<String> addPhones = new ArrayList<String>(newProfile.getPhoneNumbers());
		addPhones.removeAll(oldProfile.getPhoneNumbers());
		List<String> removePhones = new ArrayList<String>(oldProfile.getPhoneNumbers());
		removePhones.removeAll(newProfile.getPhoneNumbers());

		boolean mainNumberNotChanged = StringHelper.equalsNullIgnore(oldProfile.getMainPhoneNumber(), newProfile.getMainPhoneNumber());
		if (addPhones.isEmpty() && removePhones.isEmpty() && mainNumberNotChanged)
			//nothing to do
			return;

		UserInfo userInfo = PersonHelper.buildUserInfo(newProfile.getPerson());
		try
		{
			CSABackRequestHelper.sendUpdatePhoneRegRemoveDuplicateRq(
					newProfile.getMainPhoneNumber(),
					userInfo,
					addPhones,
					removePhones
			);

			try
			{
				HibernateExecutor.getInstance().execute(updateAction);
			}
			//��� ������� ���������� �������� ���
			catch (Exception e)
			{
				CSABackRequestHelper.sendUpdatePhoneRegRemoveDuplicateRq(
						oldProfile.getMainPhoneNumber(),
						userInfo,
						removePhones,
						addPhones
				);
				throw new BusinessException("������ ���������� ������ ����.", e);
			}
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��� ��������� ������� � ��������� ������������� ���� - ���
	 * ��������� �������:
	 * 0 � VIP,
	 * 1 - ���,
	 * 2 � �������,
	 * 3 - ���������
	 * @param person ������� � ����-��������
	 * @return ��� ��������� �������
	 */
	public static BigInteger getMssClientCategoryCode(Person person)
	{
		SegmentCodeType segmentCodeType =  person.getSegmentCodeType();
		if (SegmentCodeType.VIP == segmentCodeType)
			return BigInteger.valueOf(0);
		else if (SegmentCodeType.MVC == segmentCodeType)
			return BigInteger.valueOf(1);
		else if (PersonHelper.isPensioner(person))
			return BigInteger.valueOf(3);
		else
			return BigInteger.valueOf(2);
	}

	/**
	 * @param ermbProfile ������� ����.
	 * @return ���������� ���� ����  ��������� ��� ��������, ���� null.
	 * �� ���������� ����� � �������� ���������:
	 *	����� ������ ����� : +, H, R, X
	 *		��������� �����
	 *		��������������, ������������� �����
	 */
	public static CardLink getForeginProduct(ErmbProfileImpl ermbProfile)
	{
		CardLink fLink = ermbProfile.getForeginProduct();
		ErmbPaymentCardLinkFilter filter = new ErmbPaymentCardLinkFilter();
		if (fLink == null || !filter.evaluate(fLink))
		{
			return null;
		}

		return fLink;
	}

	/**
	 * ���������� ������� ���� ������� �� ������� � ������ ������������ �����������.
	 * @param person - ������
	 * @return ���� �� ������� ����.
	 * @throws BusinessException
	 */
	public static boolean hasErmbProfileByPerson(ActivePerson person) throws BusinessException
	{

		UserInfo userInfo = new UserInfo();
		userInfo.setFirstname(person.getFirstName());
		userInfo.setSurname(person.getSurName());
		userInfo.setPatrname(person.getPatrName());
		userInfo.setBirthdate(person.getBirthDay());
		String passport = null;
		for (PersonDocument doc:person.getPersonDocuments())
			if (doc.getDocumentMain())
				passport = StringHelper.getEmptyIfNull(doc.getDocumentSeries()) + StringHelper.getEmptyIfNull(doc.getDocumentNumber());
		if (StringHelper.isEmpty(passport))
		{
			log.error("�� ������� ���������� ������� ������� � login id:"+person.getLogin().getId());
			return false;
		}
		userInfo.setPassport(passport);
		return ErmbHelper.isCsaErmbConnected(userInfo);
	}

	/**
	 * ���������� ������� ���� ������� �� ������ � ������ ������������ �����������.
	 * @param loginId �����
	 * @return ���� �� � ������� ���� �������
	 * @throws BusinessException
	 */
	public static boolean hasErmbProfileByLogin(Long loginId) throws BusinessException
	{
		boolean result = profileService.isErmbProfileExistsByLoginId(loginId);
		if (result)
			return result;
		ActivePerson person = personService.findByLogin(loginId);
		if (person == null)
			throw new BusinessException("�� ������ ������ �� ����� id = " + loginId);
		return hasErmbProfileByPerson(person);
	}

	/**
	 * ���������� ������� ���� ������� �� ��������� ������� � ������ ������������ �����������.
	 * @param client ������
	 * @return ���� �� � ������� ���� �������
	 * @throws BusinessException
	 */
	public static boolean hasErmbProfileByClient(Client client) throws BusinessException
	{
		Office office = client.getOffice();
		String tb = office != null ? office.getCode().getFields().get("region") : null;

		List<? extends ClientDocument> clientDocuments = new ArrayList<ClientDocument>(client.getDocuments());
		Collections.sort(clientDocuments, new DocumentTypeComparator());
		ClientDocument clientDocument = client.getDocuments().get(0);

		List<ActivePerson> persons = personService.getByFIOAndDoc(
				client.getSurName(),
				client.getFirstName(),
				client.getPatrName(),
				clientDocument.getDocSeries(),
				clientDocument.getDocNumber(),
				client.getBirthDay(),
				tb
		);
		for (ActivePerson person : persons)
		{
			ErmbProfileImpl ermbProfile = getErmbProfileByPerson(person);
			if (ermbProfile != null)
				return ermbProfile.isServiceStatus();
		}

		//���� ������� �� ������ � ������� �����, ����� ���� � ������
		//���� ��������� � ���
		String clientDocumentNumber = StringHelper.getEmptyIfNull(clientDocument.getDocNumber()) +
				StringHelper.getEmptyIfNull(clientDocument.getDocSeries());
		UserInfo userInfo = new UserInfo(tb, client.getFirstName(), client.getSurName(), client.getPatrName(),
				clientDocumentNumber, client.getBirthDay());

		return isCsaErmbConnected(userInfo);
	}

	/**
	 * �������� �������� ���������� ������� ����� ��� ��������� �������� �������
	 * @param profile ���� �������
	 * @return �������� ����������
	 */
	public static String getMobileOperatorNameByProfile(ErmbProfile profile) throws BusinessException
	{
		String mainPhoneNumber = profile.getMainPhoneNumber();
		if (StringUtils.isEmpty(mainPhoneNumber))
			return StringUtils.EMPTY;

		BillingServiceProvider provider = providerService.getMobileOperatorByPhoneNumber(PhoneNumber.fromString(mainPhoneNumber));
		if (provider == null)
		{
			log.error("�� ������ �������� ������� ����� ��� �������� " + mainPhoneNumber);
			return "����������";
		}
		return provider.getName();
	}

	/**
	 * ����� ������������ �����
	 *
	 * @param amount ����� ��������
	 * @return ������������ ���� ����
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static CardLink getPriorityCardLink(Money amount) throws BusinessLogicException, BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<CardLink> cardLinks = new LinkedList<CardLink>();
		cardLinks.addAll(personData.getCards());
		return PrimaryCardResolver.getPrimaryLink(cardLinks, amount);
	}

	/**
	 * ����� ������������ �����
	 * @return ������������ ���� ����
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static CardLink getPriorityCardLink() throws BusinessLogicException, BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<CardLink> cardLinks = new LinkedList<CardLink>();
		cardLinks.addAll(personData.getCards(new ActiveNotVirtualNotCreditOwnCardFilter()));
		return PrimaryCardResolver.getPrimaryLink(cardLinks);
	}

	/**
	 * @return �������� �� ������� ���������� ������������ ���->����
	 */
	public static boolean isErmbMigration()
	{
		Application currentApplication = ApplicationInfo.getCurrentApplication();
		return currentApplication == Application.ERMBListMigrator
				|| currentApplication == Application.ASFilialListener
				|| currentApplication == Application.ErmbMbkListener;
	}

	/**
	 * @param tariff ����� ����
	 * @return �������� �� ����� ����������
	 */
	public static boolean isFreeTariff(ErmbTariff tariff)
	{
		return tariff.getGracePeriodCost().isZero()
				&& tariff.getGraceClass().isZero()
				&& tariff.getPremiumClass().isZero()
				&& tariff.getSocialClass().isZero()
				&& tariff.getStandardClass().isZero();
	}
}
