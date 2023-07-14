package com.rssl.phizic.business.persons.xmlserialize;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.MultiInstancePaymentReceiverService;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * @author Omeliyanchuk
 * @ created 26.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class PersonXMLSerializer
{
	private final static DepartmentService departmentService = new DepartmentService();
	private final static PersonService personService = new PersonService();
	private static final PersonXMLSerializerService serializeService = new PersonXMLSerializerService();
	private final static MultiInstancePaymentReceiverService paymentReceiverService = new MultiInstancePaymentReceiverService();

	/**
	 * Построение xml представления
	 * @param person пользователь для которого строися представвление
	 * @return xml представление
	 */
	public static XMLPersonRepresentation createXMLRepresentation(ActivePerson person, String instanceName) throws BusinessException, BusinessLogicException
	{
		XMLPersonRepresentation res = serializeService.findRepresentationByUserId(person.getLogin().getUserId());
		if(res == null)res = new XMLPersonRepresentation();

		res.setUserId(person.getLogin().getUserId());
		res.setXMLString( getPersonXMLString(person, instanceName) );
		return res;
	}

	public static XMLPersonRepresentation createXMLRepresentation(ActivePerson person) throws BusinessException, BusinessLogicException
	{
		return createXMLRepresentation(person, null);
	}

	/**
	 * Получение xml документа в виде строки
	 * @return
	 * @throws BusinessException
	 */
	public static String getPersonXMLString(ActivePerson person, String instanceName) throws BusinessException, BusinessLogicException
	{
		TransformerFactory tFactory =TransformerFactory.newInstance();
		try
		{
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource( getPersonXMl(person,instanceName) );
			StringWriter sw=new StringWriter();
			StreamResult result = new StreamResult(sw);
			transformer.transform(source, result);
			return sw.toString();
		}
		catch(TransformerConfigurationException ex)
		{
			throw new BusinessException("Неверная конфигурация xml-инфраструктуры.", ex);
		}
		catch(TransformerException ex)
		{
			throw new BusinessException("Неверный формат xml.", ex);
		}
	}

	public static Document getPersonXMl(ActivePerson person) throws BusinessException, BusinessLogicException
	{
		return getPersonXMl(person, null);
	}
	 /**
	 * Получение xml документа в виде строки
	 * @return
	 * @throws BusinessException
	 */
	public static Document getPersonXMl(ActivePerson person, String instanceName) throws BusinessException, BusinessLogicException
	 {
		if(person==null)throw new BusinessException("Не задан клиент для построения xml");
		Department department = departmentService.findById(person.getDepartmentId());
		if(department==null)throw new BusinessException("Не найден департамент клиента");

		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();

		Document document = documentBuilder.newDocument();
		Element root = document.createElement("personFullInfo");
		document.appendChild(root);

		Element general = XmlHelper.appendSimpleElement(root, "general");

		buildGeneralInfo(person, department, general, instanceName);

		XmlHelper.appendSimpleElement(general, "id", person.getClientId());

		CODRequestHelper.BuildOwnerTag(person, general, true);

		XmlHelper.appendSimpleElement(general, "login", person.getLogin().getUserId());

		CODRequestHelper.BuildAllowedOperationsTag(person, root, instanceName);

		buildAccountInfo(person, root,instanceName);

		buildReceivers(person, root,instanceName);

		buildAccountsWithFee(person, root, department,instanceName);

		buildEmpoweredPersons(person, root, department,instanceName);

		buildMobilePhone(person, root, instanceName);

		return document;

	}

	//добавление в xml мобильного телефона клиента
	private static void buildMobilePhone(ActivePerson person, Element root, String instanceName)
	{
		XmlHelper.appendSimpleElement(root, "mobilePhone", person.getMobilePhone());
	}

	//построение отдельных элементов xml
	private static void buildGeneralInfo(ActivePerson person,Department department, Element root, String instance) throws BusinessException
	{
		CODRequestHelper.buildGeneralInfo(root,person, department,instance);
		if (( department != null ) && ( department.getMonthlyCharge() != null ))
			XmlHelper.appendSimpleElement(root, "feeValue", department.getMonthlyCharge().toString());
	}

	private static void buildAccountInfo(ActivePerson person, Element root, String instanceName) throws BusinessException, BusinessLogicException
	{
		Element deposits = XmlHelper.appendSimpleElement(root, "deposits");
		CODRequestHelper.buildAccounts(person, deposits, instanceName);
	}

	/**
	 * Построение списка получателей.
	 * @throws BusinessException
	 */
	private static void buildReceivers(ActivePerson person, Element root, String instanceName) throws BusinessException
	{
		Element recipients = XmlHelper.appendSimpleElement(root, "recipients");
		List<PaymentReceiverBase> receivers = paymentReceiverService.findBaseListReceiver(person.getLogin().getId(),instanceName);
		for (PaymentReceiverBase receiver: receivers)
		{
			Element recipient = XmlHelper.appendSimpleElement(recipients, "recipient");
			receiver.getXmlSerializer().buildXmlRepresentation(recipient);
		}

	}

	private static void buildEmpoweredPersons(ActivePerson person, Element root, Department department, String instanceName)
			throws BusinessException, BusinessLogicException
	{
		List<ActivePerson> empoweredList = personService.getEmpoweredPersons(person);
		Element empoweredPersonRoot = XmlHelper.appendSimpleElement(root, "empoweredPersons");
		for ( ActivePerson empoweredPerson: empoweredList )
		{
			buildEmpowerePerson(empoweredPersonRoot, empoweredPerson, department,instanceName);
		}
	}

	private static void buildEmpowerePerson(Element root, ActivePerson empoweredPerson, Department department,
	                                        String instanceName) throws BusinessException, BusinessLogicException
	{
		if (empoweredPerson.getStatus().equals(ActivePerson.ACTIVE)) {

		Element empoweredPersonRoot = XmlHelper.appendSimpleElement(root, "empoweredPerson");

		Element full = XmlHelper.appendSimpleElement(empoweredPersonRoot, "general");

		buildGeneralInfo(empoweredPerson, department, full, instanceName);
		XmlHelper.appendSimpleElement(full, "id", empoweredPerson.getClientId());
		CODRequestHelper.BuildOwnerTag(empoweredPerson, full, true);
		XmlHelper.appendSimpleElement(full, "login", empoweredPerson.getLogin().getUserId());
		XmlHelper.appendSimpleElement(full, "trustingClient", empoweredPerson.getClientId());
		if(empoweredPerson.getProlongationRejectionDate() != null)
			XmlHelper.appendSimpleElement(full, "stopDate",
					DateHelper.toXMLDateFormat(empoweredPerson.getProlongationRejectionDate().getTime()));


		CODRequestHelper.BuildAllowedOperationsTag(empoweredPerson, empoweredPersonRoot,instanceName);

		buildAccountInfo(empoweredPerson, empoweredPersonRoot,instanceName);

		}
	}

	private static void buildAccountsWithFee(ActivePerson person, Element root, Department department, String instanceName)
			throws BusinessException, BusinessLogicException
	{
		Element feeAccounts = XmlHelper.appendSimpleElement(root, "feeAccounts");
		CODRequestHelper.buildFeeAccounts(person,feeAccounts,true,instanceName);
	}
}
