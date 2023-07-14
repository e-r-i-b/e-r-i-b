package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.claims.sbnkd.ConcludeEDBODocument;
import com.rssl.phizic.gate.claims.sbnkd.ContactData;
import com.rssl.phizic.gate.claims.sbnkd.FullAddress;
import com.rssl.phizic.gate.claims.sbnkd.IssueCardStatus;
import com.rssl.phizic.gate.claims.sbnkd.impl.CardInfoImpl;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * документ для удаленного подключения УДБО
 * @author basharin
 * @ created 16.12.14
 * @ $Author$
 * @ $Revision$
 */

public class RemoteConnectionUDBOClaim extends AbstractPaymentDocument implements ConcludeEDBODocument
{
	public static final String SOURCE_OFFICE_REGION = "source-office-region";
	public static final String SOURCE_OFFICE_BRANCH = "source-office-branch";
	public static final String SOURCE_OFFICE_OFFICE = "source-office-office";

	public static final String OFFICE_INFO = "office-info";
	public static final String EDBO_BRANCH_ATTRIBUTE_NAME = "office";
	public static final String EDBO_AGENCY_ID_ATTRIBUTE_NAME = "branch";
	public static final String EDBO_PHONE_ATTRIBUTE_NAME = "mobilePhone";
	public static final String EDBO_PHONE_OPERATOR_ATTRIBUTE_NAME = "mobileOperator";
	public static final String EDBO_ORDER_NUMBER_ATTRIBUTE_NAME = "edbo-order-number";
	public static final String CONVERTED_RB_TB_BRANCH_ATTRIBUTE_NAME = "rbTbBranchId";
	public static final String LOGIN_ATTRIBUTE_NAME = "login";
	public static final String ENTER_PHONE_ATTRIBUTE_NAME = "enter-phone";
	public static final String UID_ATTRIBUTE_NAME = "uid";
	public static final String RB_TB_BRANCH_ATTRIBUTE_NAME = "region";
	public static final String IP_ADDRESS_ATTRIBUTE_NAME = "ipAddress";
	public static final String CARD_NUMBER_ATTRIBUTE_NAME = "cardNumber";
	public static final String CREATION_DATE_ATTRIBUTE_NAME = "creationDate";
	public static final String DESCRIPTION_ATTRIBUTE_NAME = "description";
	public static final String PERSON_BIRTHDAY_ATTRIBUTE_NAME = "birthDay";
	public static final String PERSON_BIRTHPLACE_ATTRIBUTE_NAME = "birthPlace";
	public static final String PERSON_TAX_ID_ATTRIBUTE_NAME = "inn";
	public static final String PERSON_ADDRESS_ATTRIBUTE_NAME = "address";
	public static final String PERSON_CITY_ATTRIBUTE_NAME = "registrationAddress-city";
	public static final String PERSON_CITIZENSHIP_ATTRIBUTE_NAME = "citizenship";
	public static final String PERSON_GENDER_ATTRIBUTE_NAME = "gender";
	public static final String PERSON_RESIDENT_ATTRIBUTE_NAME = "isResident";
	public static final String PERSON_LAST_NAME_ATTRIBUTE_NAME = "surName";
	public static final String PERSON_FIRST_NAME_ATTRIBUTE_NAME = "firstName";
	public static final String PERSON_MIDDLE_NAME_ATTRIBUTE_NAME = "patrName";
	public static final String IDENTITY_CARD_SERIES_ATTRIBUTE_NAME = "passportSeries";
	public static final String IDENTITY_CARD_NUMBER_ATTRIBUTE_NAME = "passportNumber";
	public static final String IDENTITY_CARD_ISSUED_BY_ATTRIBUTE_NAME = "passportIssuedBy";
	public static final String IDENTITY_CARD_ISSUED_CODE_ATTRIBUTE_NAME = "identity-card-issued-code";
	public static final String IDENTITY_CARD_ISSUE_DATE_ATTRIBUTE_NAME = "passportIssueDate";
	public static final String IDENTITY_TYPE_ATTRIBUTE_NAME = "identityType";
	public static final String IDENTITY_CARD_EXP_DATE_ATTRIBUTE_NAME = "identity-card-exp-date";
	public static final String EMAIL_ATTRIBUTE_NAME = "email";
	public static final String CLIENT_FOUND_ATTRIBUTE_NAME = "client-found";
	public static final String STATE_ISSUE_CARD_ATTRIBUTE_NAME = "state-issue-card";

	public String getOfficeInfo()
	{
		return getNullSaveAttributeStringValue(OFFICE_INFO);
	}

	public void setOfficeInfo(String officeInfo)
	{
		setNullSaveAttributeStringValue(OFFICE_INFO, officeInfo);
	}

	public Code getBankInfoCode()
	{
		ExtendedCodeImpl code = new ExtendedCodeImpl();

		code.setRegion(getNullSaveAttributeStringValue(SOURCE_OFFICE_REGION));
		code.setBranch(getNullSaveAttributeStringValue(SOURCE_OFFICE_BRANCH));
		code.setOffice(getNullSaveAttributeStringValue(SOURCE_OFFICE_OFFICE));

		return code;
	}

	public void setBankInfoCode(Code bankInfoCode)
	{
		ExtendedCodeImpl code = new ExtendedCodeImpl(bankInfoCode);

		setNullSaveAttributeStringValue(SOURCE_OFFICE_REGION, code.getRegion());
		setNullSaveAttributeStringValue(SOURCE_OFFICE_BRANCH, code.getBranch());
		setNullSaveAttributeStringValue(SOURCE_OFFICE_OFFICE, code.getOffice());
	}


	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.payments.RemoteConnectionUDBOClaim.class;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public void setEDBOBranchId(String eDBOBranchId)
	{
		setNullSaveAttributeStringValue(EDBO_BRANCH_ATTRIBUTE_NAME, eDBOBranchId);
	}

	public String getEDBOBranchId()
	{
		return getNullSaveAttributeStringValue(EDBO_BRANCH_ATTRIBUTE_NAME);
	}

	public void setEDBOAgencyId(String eDBOAgencyId)
	{
		setNullSaveAttributeStringValue(EDBO_AGENCY_ID_ATTRIBUTE_NAME, eDBOAgencyId);
	}

	public String getEDBOAgencyId()
	{
		return getNullSaveAttributeStringValue(EDBO_AGENCY_ID_ATTRIBUTE_NAME);
	}

	public void setEDBOPhone(String eDBOPhone)
	{
		setNullSaveAttributeStringValue(EDBO_PHONE_ATTRIBUTE_NAME, eDBOPhone);
	}

	public String getEDBOPhone()
	{
		return getNullSaveAttributeStringValue(EDBO_PHONE_ATTRIBUTE_NAME);
	}

	public void setEDBOPhoneOperator(String eDBOPhoneOperator)
	{
		setNullSaveAttributeStringValue(EDBO_PHONE_OPERATOR_ATTRIBUTE_NAME, eDBOPhoneOperator);
	}

	public String getEDBOPhoneOperator()
	{
		return StringHelper.isEmpty(getNullSaveAttributeStringValue(EDBO_PHONE_OPERATOR_ATTRIBUTE_NAME))  ? "МТС" : getNullSaveAttributeStringValue(EDBO_PHONE_OPERATOR_ATTRIBUTE_NAME);
	}

	public void setEDBOOrderNumber(Long fieldNum)
	{
		setNullSaveAttributeLongValue(EDBO_ORDER_NUMBER_ATTRIBUTE_NAME, fieldNum);
	}

	public Long getEDBOOrderNumber()
	{
		return getNullSaveAttributeLongValue(EDBO_ORDER_NUMBER_ATTRIBUTE_NAME);
	}

	public void setEDBO_TB(String tb)
	{
		setNullSaveAttributeStringValue(RB_TB_BRANCH_ATTRIBUTE_NAME, tb);
	}

	public String getEDBO_TB()
	{
		return getNullSaveAttributeStringValue(RB_TB_BRANCH_ATTRIBUTE_NAME);
	}

	public String getEmail()
	{
		String result = getNullSaveAttributeStringValue(EMAIL_ATTRIBUTE_NAME);
		if (result == null || result.equals(""))
			return "0";
		return result;
	}

	public void setEmail(String email)
	{
		setNullSaveAttributeStringValue(EMAIL_ATTRIBUTE_NAME, email);
	}

	public String getLogin()
	{
		return getNullSaveAttributeStringValue(LOGIN_ATTRIBUTE_NAME);
	}

	public void setLogin(String login)
	{
		setNullSaveAttributeStringValue(LOGIN_ATTRIBUTE_NAME, login);
	}

	public String getEnterPhone()
	{
		return getNullSaveAttributeStringValue(ENTER_PHONE_ATTRIBUTE_NAME);
	}

	public void setEnterPhone(String phone)
	{
		setNullSaveAttributeStringValue(ENTER_PHONE_ATTRIBUTE_NAME, phone);
	}

	public Long getLoginId() throws BusinessException
	{
		BusinessDocumentOwner documentOwner = getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
		return documentOwner.getLogin().getId();
	}

	public void setLoginId(Long loginId)
	{
	}

	public List<CardInfoImpl> getCardInfos()
	{
		return Collections.emptyList();
	}

	public void setCardInfos(List<CardInfoImpl> cardInfo)
	{
	}

	public void setUID(String uID)
	{
		setNullSaveAttributeStringValue(UID_ATTRIBUTE_NAME, uID);
	}

	public Calendar getCreationDate()
	{
		return getNullSaveAttributeCalendarValue(CREATION_DATE_ATTRIBUTE_NAME);
	}

	public void setCreationDate(Calendar creationDate)
	{
		setNullSaveAttributeCalendarValue(CREATION_DATE_ATTRIBUTE_NAME, creationDate);
	}

	public String getDescription()
	{
		return getNullSaveAttributeStringValue(DESCRIPTION_ATTRIBUTE_NAME);
	}

	public void setDescription(String description)
	{
		setNullSaveAttributeStringValue(DESCRIPTION_ATTRIBUTE_NAME, description);
	}

	public boolean isGuest()
	{
		return false;
	}

	public void setGuest(boolean guest)
	{
	}

	public Long getOwnerId()
	{
		try
		{
			return getLoginId();
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void setOwnerId(Long ownerId)
	{
	}

	public String getPhone()
	{
		return getNullSaveAttributeStringValue(EDBO_PHONE_ATTRIBUTE_NAME);
	}

	public void setPhone(String phone)
	{
	}

	public String getUID()
	{
		return getNullSaveAttributeStringValue(UID_ATTRIBUTE_NAME);
	}

	public void setClientFound(boolean found)
	{
	}

	public boolean isClientFound()
	{
		return false;
	}

	public String getOsb()
	{
		return "0000";
	}

	public void setOsb(String osb)
	{
	}

	public String getTb()
	{
		return getRbTbBranch().substring(0, 3);
	}

	public void setTb(String tb)
	{
	}

	public String getVsp()
	{
		return "00";
	}

	public void setVsp(String vsp)
	{
	}

	public void setRbTbBranch(String rbTbBranch)
	{
		setNullSaveAttributeStringValue(RB_TB_BRANCH_ATTRIBUTE_NAME, rbTbBranch);
	}

	public String getRbTbBranch()
	{
		return  getNullSaveAttributeStringValue(RB_TB_BRANCH_ATTRIBUTE_NAME)
				+ StringUtils.leftPad(StringHelper.getEmptyIfNull(getNullSaveAttributeStringValue(EDBO_AGENCY_ID_ATTRIBUTE_NAME)), 4, '0')
				+ StringUtils.leftPad(StringHelper.getEmptyIfNull(getNullSaveAttributeStringValue(EDBO_BRANCH_ATTRIBUTE_NAME)), 2, '0');
	}

	public void setIpAddress(String ipAddress)
	{
		setNullSaveAttributeStringValue(IP_ADDRESS_ATTRIBUTE_NAME, ipAddress);
	}

	public String getIpAddress()
	{
		return getNullSaveAttributeStringValue(IP_ADDRESS_ATTRIBUTE_NAME);
	}

	public void setCardNumber(String cardNumber)
	{
		setNullSaveAttributeStringValue(CARD_NUMBER_ATTRIBUTE_NAME, cardNumber);
	}

	public String getCardNumber()
	{
		return getNullSaveAttributeStringValue(CARD_NUMBER_ATTRIBUTE_NAME);
	}

	public void setPersonBirthday(Calendar personBirthday)
	{
	}

	public Calendar getPersonBirthday()
	{
		String value = getNullSaveAttributeStringValue(PERSON_BIRTHDAY_ATTRIBUTE_NAME);
		if (value == null || value.equals(""))
			return null;
		try
		{
			return DateHelper.toCalendar(DateHelper.fromStringToDate(value));
		}
		catch (ParseException ignore)
		{
			return null;
		}
	}

	public void setPersonBirthplace(String personBirthplace)
	{
		setNullSaveAttributeStringValue(PERSON_BIRTHPLACE_ATTRIBUTE_NAME, personBirthplace);
	}

	public String getPersonBirthplace()
	{
		return getNullSaveAttributeStringValue(PERSON_BIRTHPLACE_ATTRIBUTE_NAME);
	}

	public void setPersonTaxId(String personTaxId)
	{
		setNullSaveAttributeStringValue(PERSON_TAX_ID_ATTRIBUTE_NAME, personTaxId);
	}

	public String getPersonTaxId()
	{
		return getNullSaveAttributeStringValue(PERSON_TAX_ID_ATTRIBUTE_NAME);
	}

	public void setPersonCitizenship(String personCitizenship)
	{
		setNullSaveAttributeStringValue(PERSON_CITIZENSHIP_ATTRIBUTE_NAME, personCitizenship);
	}

	public String getPersonCitizenship()
	{
		return getNullSaveAttributeStringValue(PERSON_CITIZENSHIP_ATTRIBUTE_NAME);
	}

	public void setPersonGender(String personGender)
	{
		setNullSaveAttributeStringValue(PERSON_GENDER_ATTRIBUTE_NAME, personGender);
	}

	public String getPersonGender()
	{
		return StringHelper.isEmpty(getNullSaveAttributeStringValue(PERSON_GENDER_ATTRIBUTE_NAME)) ? "Male" : getNullSaveAttributeStringValue(PERSON_GENDER_ATTRIBUTE_NAME);
	}

	public void setPersonResident(Boolean personResident)
	{
		setNullSaveAttributeStringValue(PERSON_RESIDENT_ATTRIBUTE_NAME, String.valueOf(personResident));
	}

	public Boolean isPersonResident()
	{
		return Boolean.valueOf(getNullSaveAttributeStringValue(PERSON_RESIDENT_ATTRIBUTE_NAME));
	}

	public void setPersonLastName(String personLastName)
	{
		setNullSaveAttributeStringValue(PERSON_LAST_NAME_ATTRIBUTE_NAME, personLastName);
	}

	public String getPersonLastName()
	{
		return getNullSaveAttributeStringValue(PERSON_LAST_NAME_ATTRIBUTE_NAME);
	}

	public void setPersonFirstName(String personFirstName)
	{
		setNullSaveAttributeStringValue(PERSON_FIRST_NAME_ATTRIBUTE_NAME, personFirstName);
	}

	public String getPersonFirstName()
	{
		return getNullSaveAttributeStringValue(PERSON_FIRST_NAME_ATTRIBUTE_NAME);
	}

	public void setPersonMiddleName(String personMiddleName)
	{
		setNullSaveAttributeStringValue(PERSON_MIDDLE_NAME_ATTRIBUTE_NAME, personMiddleName);
	}

	public String getPersonMiddleName()
	{
		return getNullSaveAttributeStringValue(PERSON_MIDDLE_NAME_ATTRIBUTE_NAME);
	}

	public void setIdentityCardType(String identityCardType)
	{
	}

	public String getIdentityCardType()
	{
		String type = getNullSaveAttributeStringValue(IDENTITY_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(type))
			return null;
		return PassportTypeWrapper.getPassportType(ClientDocumentType.valueOf(type));
	}

	public void setIdentityCardSeries(String identityCardSeries)
	{
		setNullSaveAttributeStringValue(IDENTITY_CARD_SERIES_ATTRIBUTE_NAME, identityCardSeries);
	}

	public String getIdentityCardSeries()
	{
		return getNullSaveAttributeStringValue(IDENTITY_CARD_SERIES_ATTRIBUTE_NAME);
	}

	public void setIdentityCardNumber(String identityCardNumber)
	{
		setNullSaveAttributeStringValue(IDENTITY_CARD_NUMBER_ATTRIBUTE_NAME, identityCardNumber);
	}

	public String getIdentityCardNumber()
	{
		return getNullSaveAttributeStringValue(IDENTITY_CARD_NUMBER_ATTRIBUTE_NAME);
	}

	public void setIdentityCardIssuedBy(String identityCardIssuedBy)
	{
		setNullSaveAttributeStringValue(IDENTITY_CARD_ISSUED_BY_ATTRIBUTE_NAME, identityCardIssuedBy);
	}

	public String getIdentityCardIssuedBy()
	{
		return getNullSaveAttributeStringValue(IDENTITY_CARD_ISSUED_BY_ATTRIBUTE_NAME);
	}

	public String getIdentityCardIssuedCode()
	{
		return getNullSaveAttributeStringValue(IDENTITY_CARD_ISSUED_CODE_ATTRIBUTE_NAME);
	}

	public void setLastLogonCardNumber(String cardNumber)
	{
		setNullSaveAttributeStringValue(CARD_NUMBER_ATTRIBUTE_NAME, cardNumber);
	}

	public String getLastLogonCardNumber()
	{
		return getNullSaveAttributeStringValue(CARD_NUMBER_ATTRIBUTE_NAME);
	}

	public void setIdentityCardIssuedCode(String identityCardIssuedCode)
	{
		setNullSaveAttributeStringValue(IDENTITY_CARD_ISSUED_CODE_ATTRIBUTE_NAME, identityCardIssuedCode);
	}

	public void setIdentityCardIssueDate(Calendar identityCardIssueDate)
	{
	}

	public Calendar getIdentityCardIssueDate()
	{
		String value = getNullSaveAttributeStringValue(IDENTITY_CARD_ISSUE_DATE_ATTRIBUTE_NAME);
		if (value == null || value.equals(""))
			return null;
		try
		{
			return DateHelper.toCalendar(DateHelper.fromStringToDate(value));
		}
		catch (ParseException ignore)
		{
			return null;
		}
	}

	public void setIdentityCardExpDate(Calendar identityCardExpDate)
	{
		Calendar cal = identityCardExpDate;
		setNullSaveAttributeDateTimeValue(IDENTITY_CARD_EXP_DATE_ATTRIBUTE_NAME, cal);
	}

	public Calendar getIdentityCardExpDate()
	{
		String value = getNullSaveAttributeStringValue(IDENTITY_CARD_EXP_DATE_ATTRIBUTE_NAME);
		if (value == null || value.equals(""))
			return null;
		try
		{
			return DateHelper.toCalendar(DateHelper.fromStringToDate(value));
		}
		catch (ParseException ignore)
		{
			return null;
		}
	}

	public void setVerified(boolean verified)
	{
	}

	public boolean isVerified()
	{
		return true;
	}

	public void setContactData(ContactData[] contactData)
	{
	}

	public ContactData[] getContactData()
	{
		ContactData contactData = new ContactData();
		contactData.setContactType(ContactData.ContactType.HOME);
		contactData.setContactNum(getNullSaveAttributeStringValue(EDBO_PHONE_ATTRIBUTE_NAME));
		contactData.setPhoneOperName(getNullSaveAttributeStringValue(EDBO_PHONE_OPERATOR_ATTRIBUTE_NAME));
		return new ContactData[]{contactData};
	}

	public void setAddress(FullAddress[] address)
	{
	}

	public FullAddress[] getAddress()
	{
		FullAddress fullAddress = new FullAddress();
		fullAddress.setAfterSityAdress(getNullSaveAttributeStringValue(PERSON_ADDRESS_ATTRIBUTE_NAME));
		fullAddress.setCity(getNullSaveAttributeStringValue(PERSON_CITY_ATTRIBUTE_NAME));
		return new FullAddress[]{fullAddress};
	}

	public void setStatus(IssueCardStatus state)
	{
		if (state == IssueCardStatus.EXECUTED)
			setState(new State("EXECUTED", "Заявка успешно выполнена в банке."));
		else if (state == IssueCardStatus.ERROR)
			setState(new State("REFUSED", "Вам отказано в исполнении заявка по какой-либо причине."));
		setNullSaveAttributeStringValue(STATE_ISSUE_CARD_ATTRIBUTE_NAME, state.name());
	}

	public IssueCardStatus getStatus()
	{
		String status = getNullSaveAttributeStringValue(STATE_ISSUE_CARD_ATTRIBUTE_NAME);
		if (status == null)
			return null;
		return IssueCardStatus.valueOf(status);
	}

	public String getConvertedAgencyId()
	{
		return null;
	}

	public void setConvertedRegionId(String сonvertedRegionId)
	{}

	public String getConvertedRegionId()
	{
		return null;
	}

	public void setConvertedBranchId(String convertedBranchId)
	{}

	public String getConvertedBranchId()
	{
		return null;
	}

	public void setConvertedAgencyId(String convertedAgencyId)
	{}

	public String getConvertedRbTbBranchId()
	{
		return getNullSaveAttributeStringValue(CONVERTED_RB_TB_BRANCH_ATTRIBUTE_NAME);
	}

	public void setConvertedRbTbBranchId(String convertedRbTbBranchId)
	{
		setNullSaveAttributeStringValue(CONVERTED_RB_TB_BRANCH_ATTRIBUTE_NAME, convertedRbTbBranchId);
	}

	@Override
	public boolean isAlwaysIMSICheck()
	{
		return true;
	}

	@Override
	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		if(AuthenticationContext.getContext() != null)
		{
			setConvertedRbTbBranchId(AuthenticationContext.getContext().getCB_CODE());
		}
	}
}
