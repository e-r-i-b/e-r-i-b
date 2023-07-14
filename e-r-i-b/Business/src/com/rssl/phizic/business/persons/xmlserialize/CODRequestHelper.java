package com.rssl.phizic.business.persons.xmlserialize;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.PaymentReceiverJur;
import com.rssl.phizic.business.dictionaries.MultiInstancePaymentReceiverService;
import com.rssl.phizic.business.ext.sbrf.receivers.PaymentReceiverPhizSBRF;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.business.resources.external.PaymentSystemIdLink;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.MultiInstanceServiceService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.gate.payments.PaymentsConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import javax.xml.parsers.DocumentBuilder;

/**
 * todo: Разнести сериализацию пользователя и отсылку сообщения о регистрации в ЦОД
 * @author Kosyakova
 * @ created 26.09.2006
 * @ $Author$
 * @ $Revision$
 */
public class CODRequestHelper
{
	private final static MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
	private final static MultiInstanceServiceService serviceService = new MultiInstanceServiceService();
	protected static final MultiInstancePersonService personService = new MultiInstancePersonService();
	private final static MultiInstancePaymentReceiverService paymentReceiverService = new MultiInstancePaymentReceiverService();

	public static void BuildOwnerTag(ActivePerson person, Element owner, boolean isModification)
	{
		XmlHelper.appendSimpleElement(owner, "fullName", person.getSurName() + " " + person.getFirstName() + " " +  person.getPatrName());
		if(!StringHelper.isEmpty(person.getGender()))
		{
			if ("M".equals(person.getGender()))
			  XmlHelper.appendSimpleElement(owner, "gender", "MALE");
			else
			  XmlHelper.appendSimpleElement(owner, "gender", "FEMALE");
		}
		XmlHelper.appendSimpleElement(owner, "birthdate", DateHelper.toXMLDateFormat(DateHelper.toDate(person.getBirthDay())));
		XmlHelper.appendSimpleElement(owner, isModification ? "citizen" : "citizenRF", person.getCitizenship());

		Element identityCard = XmlHelper.appendSimpleElement(owner, "identityCard");

		Set<PersonDocument> personDocuments = person.getPersonDocuments();

		if (personDocuments != null)
		{
			for(PersonDocument document: personDocuments)
			{
				if (document.getDocumentMain())
				{
					if( PersonDocumentType.OTHER.equals(document.getDocumentType()))
					{
						XmlHelper.appendSimpleElement(identityCard, "type",parseDocumentType(document.getDocumentType().toString()));
		                XmlHelper.appendSimpleElement(identityCard, "typeName",document.getDocumentName());
					}
					else
		                XmlHelper.appendSimpleElement(identityCard, "type",parseDocumentType(document.getDocumentType().toString()));

					if (!StringHelper.isEmpty(document.getDocumentSeries()))
						XmlHelper.appendSimpleElement(identityCard, "series", document.getDocumentSeries());
					if (!StringHelper.isEmpty(document.getDocumentNumber()))
						XmlHelper.appendSimpleElement(identityCard, "number", document.getDocumentNumber());
					XmlHelper.appendSimpleElement(identityCard, "dateOfIssue",
						DateHelper.toXMLDateFormat(DateHelper.toDate(document.getDocumentIssueDate())));
					XmlHelper.appendSimpleElement(identityCard, "authority", document.getDocumentIssueBy());
				}
			}
		}
	}

	public static void BuildAllowedOperationsTag(ActivePerson person, Element owner, String instanceName) throws BusinessException
	{
		Element allowedOperations = XmlHelper.appendSimpleElement(owner, "allowedOperations");

		List<Service> services = serviceService.getPersonServices(person,instanceName);

		for ( Service service : services )  {
			if( "RurPayJurSB".equals(service.getKey()))
			  XmlHelper.appendSimpleElement(allowedOperations, "operation", "EXTERNAL_PAYMENT");
			if( "AccountAndCardInfo".equals(service.getKey()))
			  XmlHelper.appendSimpleElement(allowedOperations, "operation", "DEPOSIT_BALANCE");
			if( "Abstract".equals(service.getKey()))
			  XmlHelper.appendSimpleElement(allowedOperations, "operation", "DEPOSIT_BILLING");
			if( "ShowLongOffer".equals(service.getKey()))
			  XmlHelper.appendSimpleElement(allowedOperations, "operation", "FORM190_BILLING");
			if( "LossPassbookApplication".equals(service.getKey()))
			  XmlHelper.appendSimpleElement(allowedOperations, "operation", "LOSS_PASSBOOK");
			if( "InternalPayment".equals(service.getKey()))
			  XmlHelper.appendSimpleElement(allowedOperations, "operation", "INTERNAL_TRANSFER");
			if( "RurPayment".equals(service.getKey()))
				XmlHelper.appendSimpleElement(allowedOperations, "operation", "TRANSFER_OTHER_OSB");
			if( "PurchaseCurrencyPayment".equals(service.getKey()))
			  XmlHelper.appendSimpleElement(allowedOperations, "operation", "CURRENCY_PURCHASE");
			if( "SaleCurrencyPayment".equals(service.getKey()))
			  XmlHelper.appendSimpleElement(allowedOperations, "operation", "CURRENCY_SALE");
			if( "ConvertCurrencyPayment".equals(service.getKey()))
			  XmlHelper.appendSimpleElement(allowedOperations, "operation", "CURRENCY_CONVERTION");
		}
		XmlHelper.appendSimpleElement(allowedOperations, "allAllowed", "false");

	}

	/**
	 * Формирование запроса на Регистрацию договора в ЦОД
	 * @return
	 * @throws BusinessException
	 */
	public static Document buildClientRegisterRequest(ActivePerson person, Department department, String instanceName) throws BusinessException, BusinessLogicException
	{
		// не смущает что это повторяется четыре раза
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element root = document.createElement("agreementRegistration_q");
		document.appendChild(root);

		buildGeneralInfo(root, person, department,instanceName);

		buildAccounts(person, root, instanceName);

		Element owner = XmlHelper.appendSimpleElement(root, "owner");
		XmlHelper.appendSimpleElement(owner, "id", person.getClientId());

		BuildOwnerTag(person, owner, false);

		XmlHelper.appendSimpleElement(owner, "login", person.getLogin().getUserId());

		BuildAllowedOperationsTag(person, owner, instanceName);

		ActivePerson principalPerson = getPrincipalPerson(person, instanceName);
		//BUG016357:Не удаляется получатель (юр.лицо) - теперь в цоде не регистрируем
/*
		if ( principalPerson == null )
		{
			buildPaymentReceiverPhis(person, root, instanceName);

			buildPaymentReceiverJur(person, root, false, instanceName);
		}
*/
		List<PaymentSystemIdLink> links = externalResourceService.getLinks(person.getLogin(), PaymentSystemIdLink.class, instanceName);
		if(links != null && links.size() > 0)
			XmlHelper.appendSimpleElement(root, "billingCard", links.get(0).getValue());
		PaymentsConfig propertiesPaymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);
		if ( (principalPerson == null) && (propertiesPaymentsConfig.getNeedAccount()))
		{
			buildFeeAccounts(person, root, false, instanceName);

			if (( department != null ) && ( department.getMonthlyCharge() != null ))
				XmlHelper.appendSimpleElement(root, "feeValue", department.getMonthlyCharge().toString());
		}
		if ( principalPerson != null )
			XmlHelper.appendSimpleElement(root, "trustingClient", principalPerson.getClientId());

		return document;

	}

	/**
	 * Построеи общей информации.
	 */
	public static void buildGeneralInfo(Element root, ActivePerson person, Department department,String instanceName) throws BusinessException
	{
		XmlHelper.appendSimpleElement(root, "agreementNumber", person.getAgreementNumber());
		Calendar agreementDate = person.getAgreementDate();
		if(person.getTrustingPersonId()!=null)
		{
			Person trunstingPerson = personService.findById(person.getTrustingPersonId(),instanceName);
			agreementDate = trunstingPerson.getAgreementDate();
		}
		XmlHelper.appendSimpleElement(root, "agreementDate", DateHelper.toXMLDateFormat(getNullSafeDate(agreementDate)));
		XmlHelper.appendSimpleElement(root, "insertionOfService", DateHelper.toXMLDateFormat(getNullSafeDate(person.getServiceInsertionDate())));
		if (!department.getCode().getFields().isEmpty())
		{
			String branch = department.getCode().getFields().get("branch");
			XmlHelper.appendSimpleElement(root, "agreementOwner", StringHelper.appendLeadingZeros(branch, 4));			
		}
}

	private static Date getNullSafeDate(Calendar serviceInsertionDate)
	{
		return serviceInsertionDate == null ? null : serviceInsertionDate.getTime();
	}

	/**
	 * Построение списка счетов с которых можно совершать оплату.
	 * @param person
	 * @param root
	 * @param serializer true- для сериалайзера, false - для отправки в ЦОД
	 * @throws BusinessException
	 */
	public static void buildFeeAccounts(ActivePerson person, Element root, boolean serializer, String instanceName) throws BusinessException, BusinessLogicException
	{
		List<AccountLink> accountLinks = loadAccountLinks(person, instanceName);
		for (AccountLink accountLink : accountLinks)
			if( accountLink.getPaymentAbility())
			{
				if(!serializer)
				{
					XmlHelper.appendSimpleElement(root, "feeAccount", accountLink.getNumber());
				}
				else
				{
					Element deposit = XmlHelper.appendSimpleElement(root, "feeAccount");
			        XmlHelper.appendSimpleElement(deposit, "account", accountLink.getNumber());
				}
			}
	}

	/**
	 * Построение списка счетов
	 * @throws BusinessException
	 */
	public static void buildAccounts(ActivePerson person, Element root, String instanceName) throws BusinessException, BusinessLogicException
	{
		List<AccountLink> accountLinks = loadAccountLinks(person, instanceName);
		for (AccountLink accountLink : accountLinks)  {
			Element deposit = XmlHelper.appendSimpleElement(root, "deposit");
			XmlHelper.appendSimpleElement(deposit, "account", accountLink.getNumber());
		}
	}

	/**
	 * Формирование запроса на Изменение договора в ЦОД
	 * @return
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static Document buildClientUpdateRequest(ActivePerson person, PersonChanges change, String instanceName) throws BusinessException, BusinessLogicException
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element root = document.createElement("agreementModification_q");
		document.appendChild(root);

		buildGeneralUpdateRequest(person,root, instanceName);

		buildSpecificUpdateRequest(person, change,root, instanceName);

		ActivePerson principalPerson = getPrincipalPerson(person, instanceName);
		List<PaymentSystemIdLink> links = externalResourceService.getLinks(person.getLogin(), PaymentSystemIdLink.class, instanceName);
		if(links != null && links.size() > 0)
			XmlHelper.appendSimpleElement(root, "billingCard", links.get(0).getValue());
		if ( principalPerson != null )
			XmlHelper.appendSimpleElement(root, "trustingClient", principalPerson.getClientId());

		return document;
	}

	/**
	 * Построение запроса на изменения договора с клиентом, для представителя
	 * @throws BusinessException
	 */
	public static Document buildClientUpdateRequest(ActivePerson person, String instanceName) throws BusinessException, BusinessLogicException
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element root = document.createElement("agreementModification_q");
		document.appendChild(root);


		buildGeneralUpdateRequest(person,root, instanceName);

		ActivePerson principalPerson = getPrincipalPerson(person, instanceName);
		List<PaymentSystemIdLink> links = externalResourceService.getLinks(person.getLogin(), PaymentSystemIdLink.class, instanceName);
		if(links != null && links.size() > 0)
			XmlHelper.appendSimpleElement(root, "billingCard", links.get(0).getValue());
		if ( principalPerson != null )
			XmlHelper.appendSimpleElement(root, "trustingClient", principalPerson.getClientId());

		return document;
	}

	private static void buildSpecificUpdateRequest(ActivePerson person,PersonChanges change,Element root, String instanceName)
						throws BusinessException
	{
		ActivePerson principalPerson = getPrincipalPerson(person,instanceName);
		if(principalPerson == null)
		{
			if( (change.getAccountDeleted()!=null && change.getAccountDeleted().size()>0)  ||
					(change.getFeeAccountDeleted()!=null && change.getFeeAccountDeleted().size() > 0) )
			{
				Element removed = XmlHelper.appendSimpleElement(root, "removed");
				fillAccounts( removed, change.getAccountDeleted() ,"deposit");
				fillAccounts( removed, change.getFeeAccountDeleted() ,"feeAccount");
				//BUG016357:Не удаляется получатель (юр.лицо) - теперь в цоде не регистрируем
				//fillReciversPhiz(removed, change.getReceiverPhizDeleted(),false);
				//fillReciversJur(removed, change.getReceiverJurDeleted(),false);
			}

			if( (change.getAccountAdded()!=null && change.getAccountAdded().size() > 0) ||
					(change.getFeeAccountAdded()!=null && change.getFeeAccountAdded().size() > 0))
			{
				Element added = XmlHelper.appendSimpleElement(root, "added");
				fillAccounts(added, change.getAccountAdded(), "deposit");
				fillAccounts(added, change.getFeeAccountAdded(), "feeAccount");
				 //BUG016357:Не удаляется получатель (юр.лицо) - теперь в цоде не регистрируем
				//fillReciversPhiz(added, change.getReceiverPhizAdded(),true);
				//fillReciversJur(added, change.getReceiverJurAdded(),true);
			}
		}
	}

	private static void buildGeneralUpdateRequest(ActivePerson person,Element root, String instanceName) throws BusinessException
	{
		XmlHelper.appendSimpleElement(root, "clientId", person.getClientId());
		Element owner = XmlHelper.appendSimpleElement(root, "owner");

		BuildOwnerTag(person, owner, true);

		BuildAllowedOperationsTag(person, owner, instanceName);
	}

	private static void fillAccounts(Element root, List<String> accounts, String name)
	{
		if(accounts!=null)
		{
			for (String account : accounts)
			{
				XmlHelper.appendSimpleElement(root, name, account);
			}
		}
	}

	/**
	 * Формирование запроса на Расторжение договора об обслуживании в ЦОД
	 * @return
	 * @throws BusinessException
	 */
	public static Document buildClientCancellationRequest(String clientId, Calendar prolongationRejectionDate) throws BusinessException
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();

		Document document = documentBuilder.newDocument();
		Element  root     = document.createElement("agreementCancellation_q");

		document.appendChild(root);
		XmlHelper.appendSimpleElement(root, "stopDate", DateHelper.toXMLDateFormat(prolongationRejectionDate.getTime()));
		XmlHelper.appendSimpleElement(root, "id", clientId);

		return document;
	}
	//helpers
	private static List<AccountLink> loadAccountLinks(ActivePerson person, String instanceName) throws BusinessException, BusinessLogicException
	{
		return externalResourceService.getLinks(person.getLogin(), AccountLink.class, instanceName);
	}

	private static ActivePerson getPrincipalPerson(ActivePerson person, String instanceName) throws BusinessException
	{
		return (ActivePerson) personService.findById(person.getTrustingPersonId(), instanceName);
	}

	private static List<PaymentReceiverPhizSBRF> getPaymentReceiversPhisSBRF (ActivePerson person, String instanceName) throws BusinessException
	{
		return paymentReceiverService.findListReceiver(PaymentReceiverPhizSBRF.class, person.getLogin().getId(),instanceName);
	}

	private static List<PaymentReceiverJur> getPaymentReceiversJur (ActivePerson person, String instanceName) throws BusinessException
	{
		return paymentReceiverService.findListReceiver(PaymentReceiverJur.class, person.getLogin().getId(), instanceName);
	}

	private static String parseDocumentType(String documentType)
	{
		return documentType.equals("RESIDENTIAL_PERMIT_RF") ? "RESIDENСЕ_PERMIT_RF": documentType;
	}
}
