package com.rssl.phizic.business.persons.xmlserialize;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.dictionaries.receivers.personal.PaymentPersonalReceiversDictionary;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 27.09.2006
 * Time: 11:35:31
 * To change this template use File | Settings | File Templates.
 */
public class PersonXMLComparator
{
	private final static MultiInstancePersonService personService = new MultiInstancePersonService();
	private final static PersonXMLSerializerService xmlService = new PersonXMLSerializerService();
	private static final SimpleService simpleService = new SimpleService();

	private static Map<String,String> keyFields = new HashMap<String,String>();

	private String instanceName;
	private Person changedPerson;

	public PersonXMLComparator()
	{
		this(null, null);
	}
	public PersonXMLComparator(String instanceName, Person changedPerson)
	{
		keyFields.put("recipient","alias");
		keyFields.put("deposit","account");
		keyFields.put("feeAccount","account");
		keyFields.put("empoweredPerson","general/id");

		this.instanceName   = instanceName;
		this.changedPerson = changedPerson;
	}

	public PersonChanges compare(Login login) throws BusinessException, BusinessLogicException
	{
		ActivePerson person = personService.findByLogin(login,instanceName);
		if(person==null)
			throw new BusinessException("Не найден указаный пользователь");

		Document document = PersonXMLSerializer.getPersonXMl(person, instanceName);

		Document srcDocument = null;

		if(changedPerson == null)
		{
			XMLPersonRepresentation representation = xmlService.findRepresentationByUserId(login.getUserId());

			//если такого пользователя еще не сохраняли возвращаем все как изменения
			if(representation == null)
			{
				return createFull(person);
			}

			srcDocument = representation.getXML();
		}
		else
		{
			srcDocument = PersonXMLSerializer.getPersonXMl((ActivePerson) changedPerson);
		}

		return startCompare(srcDocument, document);
	}
	private PersonChanges startCompare(Document docNow,Document docSaved) throws BusinessException
	{
		PersonChanges changes = new PersonChanges();
		Element rootNow = docNow.getDocumentElement();
		Element rootSaved = docSaved.getDocumentElement();

		if(!compareChildsOnPath(rootNow,rootSaved,"general"))changes.setIsChanged();
		if(!compareChildsOnPath(rootNow,rootSaved,"allowedOperations"))changes.setIsChanged();
		try
		{
			if (XmlHelper.selectSingleNode(rootNow, "mobilePhone") != null)
			{
				if (XmlHelper.selectSingleNode(rootSaved, "mobilePhone") != null)
				{
					if (!XmlHelper.selectSingleNode(rootSaved, "mobilePhone").getTextContent().equals(XmlHelper.selectSingleNode(rootNow, "mobilePhone").getTextContent()))
					{
						changes.setNewMobilePhone(XmlHelper.selectSingleNode(rootNow, "mobilePhone").getTextContent());
						changes.setOldMobilePhone(XmlHelper.selectSingleNode(rootSaved, "mobilePhone").getTextContent());
					}
				}
				else
					changes.setNewMobilePhone(XmlHelper.selectSingleNode(rootNow, "mobilePhone").getTextContent());
			}
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
		changes = compareListsOnPath(rootNow,rootSaved,"recipients", changes);
		changes = compareListsOnPath(rootNow,rootSaved,"deposits", changes);
		changes = compareListsOnPath(rootNow,rootSaved,"feeAccounts", changes);
		changes = compareListsOnPath(rootNow,rootSaved,"empoweredPersons", changes);

		return changes;
	}

	private PersonChanges compareListsOnPath( Element rootNow, Element rootSaved, String path,PersonChanges changes)
			throws BusinessException
	{
		Element subNow = getElementByPath(rootNow,path);
		Element subSaved = getElementByPath(rootSaved,path);
		return compareLists(subNow, subSaved,changes);
	}

	private PersonChanges compareLists( Element rootNow, Element rootSaved,PersonChanges changes)
			throws BusinessException
	{
		Map<String,String> old = new HashMap<String,String>();
		/*
			ищем в старом xml элементы нового
			 если есть то обновляем
			 если нет то добавляем
		*/
		NodeList listNow = rootNow.getChildNodes();
		for ( int i = 0; i < listNow.getLength(); i++ )
		{
			Element el = (Element) listNow.item(i);

			String key = getKeyValue(el.getNodeName());
			Element elemNow = getElementByPath(el,key);

			String elemNowValue = elemNow.getTextContent();
			//ищем такой же элемент в другом xml
			String path = el.getNodeName() + "[" + key + "='" + elemNowValue + "']";
			Element elemSaved  = getElementByPath(rootSaved,path);
			if(elemSaved!=null)
			{
				if(el.getNodeName().equals("empoweredPerson"))
				{
					changes = compareEmpoweredPersonsXML(el, elemSaved, changes);
				}
				else
				{
					if( !compareChilds(el,elemSaved) )
					{
						changes = addValue(el,changes);
						changes = deleteValue(elemSaved,changes);
					}
				}
			}
			else
			{
				changes = addValue(el,changes);
			}
			old.put(elemNowValue, key);
		}
		/*
			ищем те элементы которые надо удалить
		*/
		NodeList listSaved = rootSaved.getChildNodes();
		for ( int i = 0; i < listSaved.getLength(); i++ )
		{
			Element elSaved = (Element) listSaved.item(i);
			String key = getKeyValue(elSaved.getNodeName());
			Element elem = getElementByPath(elSaved,key);
			String elemValue = elem.getTextContent();
			if(!old.containsKey(elemValue))
			{
				changes = deleteValue(elSaved,changes);
			}

		}

		return changes;
	}

	private PersonChanges compareEmpoweredPersonsXML(Element elemNow, Element elemSaved, PersonChanges changes)
			throws BusinessException
	{
		Element generalNow = getElementByPath(elemNow,"general");
		Element generalSaved = getElementByPath(elemSaved,"general");
		Element operationNow = getElementByPath(elemNow,"allowedOperations");
		Element operationSaved = getElementByPath(elemSaved,"allowedOperations");
		Element depositsNow = getElementByPath(elemNow,"deposits");
		Element depositsSaved = getElementByPath(elemSaved,"deposits");

		if(    !compareChilds(generalNow, generalSaved)
			|| !compareChilds(operationNow, operationSaved)
			|| !compareChilds(depositsNow, depositsSaved) )
		{
			changes = modifyEmpoweredPersonValue(elemNow,changes);
		}
		return changes;
	}

	private String getKeyValue(String type) throws BusinessException
	{
		if(!keyFields.containsKey(type))
			throw new BusinessException("Не известный элемент");

		return keyFields.get(type);
	}

	private PersonChanges createFull(ActivePerson person) throws BusinessException, BusinessLogicException
	{//на случай если нет информации о клиенте в базе. в идеале такой ситуации не может быть		 
		XMLPersonRepresentation represent = PersonXMLSerializer.createXMLRepresentation(person);
		simpleService.addOrUpdate(represent);
		return new PersonChanges();
	}

	private PersonChanges modifyEmpoweredPersonValue(Element root,PersonChanges changes) throws BusinessException
	{
		String elemName = root.getNodeName();
		if(elemName.equals("empoweredPerson"))changes.addEmpoweredPersonModified(parseEmpoweredPersonToAdd(root));
		return changes;
	}

	private PersonChanges addValue(Element root,PersonChanges changes) throws BusinessException
	{
		String elemName = root.getNodeName();
		if(elemName.equals("recipient"))changes.addReceiverToAdd(parsePaymentReceiver(root));
		if(elemName.equals("deposit"))changes.addAccountToAdd(getElementValueByPath(root,"account"));
		if(elemName.equals("feeAccount"))changes.addFeeAccountToAdd(getElementValueByPath(root,"account"));
		if(elemName.equals("empoweredPerson"))changes.addEmpoweredPersonAdded(parseEmpoweredPersonToAdd(root));
		return changes;
	}

	private PersonChanges deleteValue(Element root,PersonChanges changes) throws BusinessException
	{
		String elemName = root.getNodeName();
		if(elemName.equals("recipient"))changes.addReceiverToDelete(parsePaymentReceiver(root));
		if(elemName.equals("deposit"))changes.addAccountToDelete(getElementValueByPath(root,"account"));
		if(elemName.equals("feeAccount"))changes.addFeeAccountToDelete(getElementValueByPath(root,"account"));
		if(elemName.equals("empoweredPerson"))changes.addEmpoweredPersonDeleted(parseEmpoweredPersonToDelete(root));
		return changes;
	}

	/*
	Преобразование получателя из xml в объект соответсвующего типом, определяемого по kind
	 */
	private PaymentReceiverBase parsePaymentReceiver(Element root) throws BusinessException
	{
		PaymentPersonalReceiversDictionary dictionary = new PaymentPersonalReceiversDictionary();
		ReceiverDescriptor receiverDescriptor = dictionary.getReceiverDescriptor(getElementValueByPath(root,"kind"));
		try
		{
			Class receiverClass = ClassHelper.loadClass(receiverDescriptor.getClassName());
			PaymentReceiverBase receivers = (PaymentReceiverBase)receiverClass.newInstance();
			return receivers.getXmlSerializer().fillFromXml(root);
		}
		catch(Exception e)
		{
			throw new BusinessException("Ошибка преобразования получателя из xml. "+e);
		}
	}

	private  String parseEmpoweredPersonToAdd(Element root) throws BusinessException
	{
		return getElementValueByPath(root,"general/id");
	}

	private Date ParseDate(String date) throws ParseException
	{
		if(date==null)return null;
		return DateHelper.fromXMlDateToDate(date);
	}

	private Calendar ParseCalendar(String date) throws ParseException
	{
		if(date==null)return null;
		return DateHelper.toCalendar(DateHelper.fromXMlDateToDate(date));
	}

	private  ActivePerson parseEmpoweredPersonToDelete(Element root) throws BusinessException
	{
		ActivePerson res = new ActivePerson();
		Set<PersonDocument> personDocuments = new HashSet<PersonDocument>();
		PersonDocument document = new PersonDocumentImpl();
		try
		{
			res.setAgreementDate(ParseCalendar(getElementValueByPath(root,"general/agreementDate")));
			res.setServiceInsertionDate(ParseCalendar(getElementValueByPath(root,"general/insertionOfService")));
			res.setProlongationRejectionDate(ParseCalendar(getElementValueByPath(root,"general/stopDate")));
			res.setBirthDay(ParseCalendar(getElementValueByPath(root,"general/birthdate")));
			document.setDocumentIssueDate(ParseCalendar(getElementValueByPath(root,"general/dateOfIssue")));
		}
		catch(ParseException ex)
		{
			throw new BusinessException("Неверный формат даты",ex);
		}

		res.setAgreementNumber(getElementValueByPath(root,"general/agreementNumber"));
		res.setClientId(getElementValueByPath(root,"general/id"));
		String fullname = getElementValueByPath(root,"general/fullName");
			int pos = fullname.indexOf(" ",0);
			res.setSurName(fullname.substring(0,pos));
			int oldPos=pos+1;
			pos = fullname.indexOf(" ",pos+1);
			res.setFirstName(fullname.substring(oldPos,pos));
			oldPos=pos+1;
			res.setPatrName(fullname.substring(oldPos,fullname.length()));
		res.setGender(getElementValueByPath(root,"general/gender"));
		res.setCitizenship(getElementValueByPath(root,"general/citizenRF"));

		document.setDocumentType(PersonDocumentType.REGULAR_PASSPORT_RF);
		document.setDocumentName(getElementValueByPath(root,"general/typeName"));
		document.setDocumentSeries(getElementValueByPath(root,"general/series"));
		document.setDocumentNumber(getElementValueByPath(root,"general/number"));
		document.setDocumentIssueBy(getElementValueByPath(root,"general/authority"));
		document.setDocumentMain(true);
		personDocuments.add(document);
		res.setPersonDocuments(personDocuments);
		return res;
	}

	//specific xmlHelpers
	private Boolean compareChildsOnPath( Element rootNow, Element rootSaved, String path) throws BusinessException
	{
		Element subNow = getElementByPath(rootNow,path);
		Element subSaved = getElementByPath(rootSaved,path);
		return compareChilds(subNow, subSaved);
	}

	private Boolean compareChilds( Element rootNow, Element rootSaved)
	{
		NodeList listNow = rootNow.getChildNodes();
		NodeList listSaved = rootSaved.getChildNodes();

		if(listSaved.getLength() != listNow.getLength())return false;

		for ( int i = 0; i < listNow.getLength(); i++ )
		{
			if(!compareElements((Element) listNow.item(i), (Element) listSaved.item(i)))return false;
		}
		return true;
	}

	private Element getElementByPath(Element rootNow, String pathNow) throws BusinessException
	{
		Element elNow;
		try
		{
			elNow = XmlHelper.selectSingleNode(rootNow, pathNow);
		}
		catch(TransformerException ex)
		{
			throw new BusinessException("Ошибка преобразования",ex);
		}
		return elNow;
	}

	private String getElementValueByPath(Element rootNow, String pathNow) throws BusinessException
	{
		try
		{
			return XmlHelper.getElementValueByPath(rootNow, pathNow);
		}
		catch(TransformerException ex)
		{
			throw new BusinessException("Ошибка преобразования",ex);
		}
	}

	private Boolean compareElements(Element elNow, Element elSave)
	{
		if(elNow==null && elSave==null)return true;
		if(elNow!=null && elSave!=null)
		{
			String strNow = elNow.getTextContent();
			String strSave = elSave.getTextContent();
			if(strNow==null && strSave==null)return true;
			if(strNow!=null && strSave!=null)
				return strNow.equals(strSave);
			return false;
		}
		return false;
	}
}
