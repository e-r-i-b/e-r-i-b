package com.rssl.phizic.test.wsgateclient.asfilial;

import com.google.gson.Gson;
import com.rssl.phizic.common.types.DaysOfWeek;
import com.rssl.phizic.test.web.asfilial.ASFilialForm;
import com.rssl.phizic.test.wsgateclient.asfilial.generated.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.json.BasicGsonSingleton;
import org.apache.axis.types.Time;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.*;
import javax.xml.rpc.ServiceException;

/**
 * User: moshenko
 * Date: 18.12.2012
 * Time: 12:10:33
 * Врапер для целей составления запросов к слушателю AC Филиала в ЕРИБ
 */
public class ASFilialClientReqWrapper
{
	private static final String SNAME = "АС_ФИЛИАЛ";
	private static final String SPLT = "\\|";

	private static final Gson gson = BasicGsonSingleton.getGson();

	private ASFilialInfoService stub;
	/**
	 * @param url
	 * @throws javax.xml.rpc.ServiceException
	 */
	public ASFilialClientReqWrapper(String url) throws ServiceException
	{
		ASFilialInfoServiceImplLocator locator = new ASFilialInfoServiceImplLocator();
		try
		{
			stub = locator.getASFilialInfoServicePort(new URL(url));
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param frm форма
	 * @return MessageContext  контекст сообщения
	 * @throws RemoteException
	 */
	public String queryProfile(ASFilialForm frm) throws RemoteException, ParseException
	{
		QueryProfileRqType queryProfileRqType = new QueryProfileRqType();
		queryProfileRqType.setRqUID(new RandomGUID().getStringValue());
		queryProfileRqType.setRqTm(Calendar.getInstance());
		queryProfileRqType.setOperUID(new RandomGUID().getStringValue());
		queryProfileRqType.setSName(SNAME);
		queryProfileRqType.setBankInfo(getBankInfo(frm));
		queryProfileRqType.setCreateIfNone(frm.isCreateIfNone());
		queryProfileRqType.setClientIdentity(getClientIdentity(frm));
		queryProfileRqType.setClientOldIdentity(getOldClientIdentity(frm));
		//oldIdentity

		QueryProfileRsType profile = stub.queryProfile(queryProfileRqType);

		ResponseType response = profile.getResponse();
		if (response != null)
		sortResourcesByType(response.getClientResources(), response.getATMClientService(),
				response.getInternetClientService(), response.getMobileClientService());

		return gson.toJson(profile);
	}

	private IdentityType[] getOldClientIdentity(ASFilialForm frm) throws ParseException
	{
		if (frm.getOldFirstName() != null)
		{
			int     count = frm.getOldFirstName().length;
			IdentityType[] oldIdentitys = new IdentityType[count];
			if (count >= 1)
				for (int i = 0 ; i < count; i++  )
				{
					IdentityType oldIdentity = new IdentityType();
					oldIdentity.setFirstName(frm.getOldFirstName()[i]);
					oldIdentity.setLastName(frm.getOldLastName()[i]);
					oldIdentity.setMiddleName(frm.getOldMiddleName()[i]);
					oldIdentity.setRegionId(frm.getRegionId());
					oldIdentity.setBirthday(DateHelper.parseDate(frm.getOldBirthday()[i]));
					IdentityCardType identityCardType = new IdentityCardType();
					identityCardType.setIdNum(frm.getOldIdNum()[i]);
					identityCardType.setIdSeries(frm.getOldIdSeries()[i]);
					identityCardType.setIdType(frm.getOldIdType()[i]);
					oldIdentity.setIdentityCard(identityCardType);
					oldIdentitys[i] = oldIdentity;
				}
			return oldIdentitys;
		}
		return null;

	}


	/**
	 * @param frm форма
	 * @return MessageContext  контекст сообщения
	 * @throws RemoteException
	 */
	public String updateProfile(ASFilialForm frm) throws RemoteException, ParseException
	{
		UpdateProfileRqType updateProfileRqType = new UpdateProfileRqType();
		updateProfileRqType.setRqUID(new RandomGUID().getStringValue());
		updateProfileRqType.setRqTm(Calendar.getInstance());
		updateProfileRqType.setOperUID(new RandomGUID().getStringValue());
		updateProfileRqType.setSName(SNAME);
		updateProfileRqType.setBankInfo(getBankInfo(frm));
		updateProfileRqType.setClientIdentity(getClientIdentity(frm));
		if (frm.isClientDataChange())
		{
			String[] phoneNumbers = frm.getPhoneNumber();
			if (phoneNumbers != null)
			{
				String[] mobilePhoneOperators = frm.getMobilePhoneOperator();
				String[] phoneConfirmHolderCodes = frm.getPhoneConfirmHolderCode();

				ClientPhonesType[] clientPhonesTypes = new ClientPhonesType[phoneNumbers.length];

				for (int i = 0; i < phoneNumbers.length; i++)
				{
					ClientPhonesType clientPhone = new ClientPhonesType();

					PhoneNumberType phoneNumber = new PhoneNumberType();
					phoneNumber.setPhoneNumberN(phoneNumbers[i]);
					phoneNumber.setMobilePhoneOperator(mobilePhoneOperators[i]);

					clientPhone.setPhone(phoneNumber);
					clientPhone.setConfirmCode(phoneConfirmHolderCodes[i]);
					clientPhonesTypes[i] = clientPhone;
				}
				updateProfileRqType.setClientData(clientPhonesTypes);
			}
			else
				updateProfileRqType.setClientData(new ClientPhonesType[0]);
		}
		if (frm.isInternetClientServiceChange())
		{
			String clientInternetsRes = frm.getInternetVisibleResources();
			if (!StringHelper.isEmpty(clientInternetsRes))
				updateProfileRqType.setInternetClientService(getResources(clientInternetsRes));
			else
				updateProfileRqType.setInternetClientService(new ResourcesType[0]);
		}
		if (frm.isMobileClientServiceChange())
		{
			String mobileRes = frm.getMobileVisibleResources();
			if (!StringHelper.isEmpty(mobileRes))
				updateProfileRqType.setMobileClientService(getResources(mobileRes));
			else
				updateProfileRqType.setMobileClientService(new ResourcesType[0]);
		}
		if (frm.isATMClientServiceChange())
		{
			String atmRes = frm.getATMVisibleResources();
			if (!StringHelper.isEmpty(atmRes))
				updateProfileRqType.setATMClientService(getResources(atmRes));
			else
				updateProfileRqType.setATMClientService(new ResourcesType[0]);
		}
		if (frm.isMobileBankService())
		{
			MobileBankServiceUpdateProfileRqType mobileBankService = new MobileBankServiceUpdateProfileRqType();
			mobileBankService.setRegistrationStatus(frm.isRegistrationStatus());
			if (frm.isMobileBankServiceParams())
			{
				ServiceParamsType serviceParamsType = new ServiceParamsType();
				serviceParamsType.setTariffId(frm.getTariffId());
				serviceParamsType.setQuickServices(frm.isQuickServices());
				if (!StringHelper.isEmpty(frm.getActivePhoneNumber()))
				{
					PhoneNumberType activePhone = new PhoneNumberType();
					activePhone.setPhoneNumberN(frm.getActivePhoneNumber());

					if (!StringHelper.isEmpty(frm.getActiveMobilePhoneOperator()))
						activePhone.setMobilePhoneOperator(frm.getActiveMobilePhoneOperator());

					serviceParamsType.setActivePhone(activePhone);
				}

                String visibleResources  = frm.getVisibleResources();
                if (!StringHelper.isEmpty(visibleResources))
                    serviceParamsType.setVisibleResources(getResources(visibleResources));
                else
                    serviceParamsType.setVisibleResources(new ResourcesType[0]);

				if (!StringHelper.isEmpty(frm.getInformResources()))
					serviceParamsType.setInformResources(getResources(frm.getInformResources()));
				serviceParamsType.setChargeOffCard(frm.getChargeOffCard());
				serviceParamsType.setInformNewResource(frm.isInformNewResource());
				if (frm.isInformPeriodChange())
				{
					DaytimePeriodType daytimePeriodType = new DaytimePeriodType();
					org.apache.axis.types.Time axisBeginTime = new Time(DateHelper.makeUTCCalendar(frm.getNtfStartTimeString()));
					org.apache.axis.types.Time axisЕndTime = new Time(DateHelper.makeUTCCalendar(frm.getNtfEndTimeString()));
					daytimePeriodType.setBegin(axisBeginTime);
					daytimePeriodType.setEnd(axisЕndTime);
					daytimePeriodType.setTimeZone(frm.getTimeZone());
					DaysOfWeek daysOfWeek = new DaysOfWeek(frm.getNtfDays(), false);
					daytimePeriodType.setDay(daysOfWeek.getFullNameStrDays());
					serviceParamsType.setInformPeriod(daytimePeriodType);
				}
				serviceParamsType.setSuppressAdvertising(frm.isSuppressAdvertising());
				mobileBankService.setServiceParams(serviceParamsType);
			}
			updateProfileRqType.setMobileBankService(mobileBankService);
		}

		UpdateProfileRsType updateProfile = stub.updateProfile(updateProfileRqType);
		return gson.toJson(updateProfile);
	}

	/**
	 * @param frm форма
	 * @return MessageContext  контекст сообщения
	 * @throws RemoteException
	 */
	public String confirmPhoneHolder(ASFilialForm frm) throws RemoteException, ParseException
	{
		ConfirmPhoneHolderRqType confirmPhoneHolderRqType = new ConfirmPhoneHolderRqType();
		confirmPhoneHolderRqType.setRqUID(new RandomGUID().getStringValue());
		confirmPhoneHolderRqType.setRqTm(Calendar.getInstance());
		confirmPhoneHolderRqType.setOperUID(new RandomGUID().getStringValue());
		confirmPhoneHolderRqType.setSName(SNAME);

		String[] phoneNumbers = frm.getPhoneNumber();
		String[] mobilePhoneOperators = frm.getMobilePhoneOperator();

		PhoneNumberType[] phoneNumberTypes = new PhoneNumberType[phoneNumbers.length];

		for (int i = 0; i < phoneNumbers.length; i++)
		{

			PhoneNumberType phoneNumber = new PhoneNumberType();
			phoneNumber.setPhoneNumberN(phoneNumbers[i]);
			phoneNumber.setMobilePhoneOperator(mobilePhoneOperators[i]);

			phoneNumberTypes[i] = phoneNumber;
		}

		confirmPhoneHolderRqType.setPhones(phoneNumberTypes);

		ConfirmPhoneHolderRsType confirmPhoneHolderRsType = stub.confirmPhoneHolder(confirmPhoneHolderRqType);
		return gson.toJson(confirmPhoneHolderRsType);
	}

	/**
	 * @param frm форма
	 * @return MessageContext  контекст сообщения
	 * @throws RemoteException
	 */
	public String requestPhoneHolder(ASFilialForm frm) throws RemoteException, ParseException
	{
		RequestPhoneHolderRqType requestPhoneHolderRqType = new RequestPhoneHolderRqType();
		requestPhoneHolderRqType.setRqUID(new RandomGUID().getStringValue());
		requestPhoneHolderRqType.setRqTm(Calendar.getInstance());
		requestPhoneHolderRqType.setOperUID(new RandomGUID().getStringValue());
		requestPhoneHolderRqType.setSName(SNAME);

		String[] phoneNumbers = frm.getPhoneNumber();
		String[] mobilePhoneOperators = frm.getMobilePhoneOperator();

		PhoneNumberType[] phoneNumberTypes = new PhoneNumberType[phoneNumbers.length];

		for (int i = 0; i < phoneNumbers.length; i++)
		{

			PhoneNumberType phoneNumber = new PhoneNumberType();
			phoneNumber.setPhoneNumberN(phoneNumbers[i]);
			phoneNumber.setMobilePhoneOperator(mobilePhoneOperators[i]);

			phoneNumberTypes[i] = phoneNumber;
		}

		requestPhoneHolderRqType.setPhones(phoneNumberTypes);

		RequestPhoneHolderRsType requestPhoneHolderRsType = stub.requestPhoneHolder(requestPhoneHolderRqType);
		return gson.toJson(requestPhoneHolderRsType);
	}

	private ResourcesType[] getResources(String resourcesStr) throws ParseException
	{
		String[] resources = resourcesStr.split(SPLT);
		ResourcesType[] resourcesTypes = new ResourcesType[resources.length];
		int i = 0;
		for (String res : resources)
		{
			resourcesTypes[i] = new ResourcesType();
			String[] typeAndNumber = res.split(":");
			if (typeAndNumber.length != 2)
			{
				throw new ParseException("Ошибка в формате ресурса:" + resourcesStr, 0);
			}

			String type = typeAndNumber[0];
			String nubmer = typeAndNumber[1];

			resourcesTypes[i].setType(type);

			if (StringHelper.equals(type, "card"))
            {
                CardType cardType = new CardType();
                cardType.setNumber(nubmer);
                resourcesTypes[i].setCard(cardType);
            }
			else if (StringHelper.equals(type, "account"))
				resourcesTypes[i].setAccount(nubmer);
			else if (StringHelper.equals(type, "loan"))
				resourcesTypes[i].setCredit(nubmer);

            resourcesTypes[i].setName("Name");
			i++;
		}
		return resourcesTypes;
	}

	private BankInfoType getBankInfo(ASFilialForm frm)
	{
		BankInfoType bankInfo = new BankInfoType();
		bankInfo.setRegionId(frm.getBankInfoRegionId());

		String agencyId = frm.getBankInfoAgencyId();
		if (!StringHelper.isEmpty(agencyId))
			bankInfo.setAgencyId(agencyId);

		String branchId = frm.getBankInfoBranchId();
		if (!StringHelper.isEmpty(branchId))
			bankInfo.setBranchId(branchId);

		return bankInfo;
	}

    private IdentityType getClientIdentity(ASFilialForm frm) throws ParseException
    {
        IdentityType identityType = new IdentityType();
        identityType.setFirstName(frm.getFirstName());
        identityType.setLastName(frm.getLastName());

        String middelName = frm.getMiddleName();
        if (!StringHelper.isEmpty(middelName))
            identityType.setMiddleName(middelName);

        identityType.setBirthday(DateHelper.parseDate(frm.getBirthday()));
        identityType.setRegionId(StringHelper.addLeadingZeros(frm.getRegionId(), 3));
        IdentityCardType identityCardType = new IdentityCardType();
        identityCardType.setIdType(frm.getIdType());

        String series = frm.getIdSeries();
        if (!StringHelper.isEmpty(series))
            identityCardType.setIdSeries(series);

        identityCardType.setIdNum(frm.getIdNum());

        String issuedBy = frm.getIssuedBy();
        if (!StringHelper.isEmpty(issuedBy))
            identityCardType.setIssuedBy(issuedBy);

        String issueDt = frm.getIssueDt();
        if (!StringHelper.isEmpty(issueDt))
            identityCardType.setIssueDt(DateHelper.parseDate(issueDt));

        identityType.setIdentityCard(identityCardType);
        return identityType;
    }

	private void sortResourcesByType(ResourcesType[]... unsortedResources)
	{
		Comparator typeComparator = new Comparator<ResourcesType>()
		{
			public int compare(ResourcesType o1, ResourcesType o2)
			{
				return o1.getType().compareTo(o2.getType());
			}
		};

		for (ResourcesType[] resources: unsortedResources)
		{
			if (resources != null)
				Arrays.sort(resources, typeComparator);
		}
	}
}
