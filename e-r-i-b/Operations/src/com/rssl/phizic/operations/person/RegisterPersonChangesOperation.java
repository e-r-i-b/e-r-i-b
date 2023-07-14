package com.rssl.phizic.operations.person;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.persons.cancelation.CancelationCallBackLink;
import com.rssl.phizic.business.persons.cancelation.CancelationCallBackLinkService;
import com.rssl.phizic.business.persons.xmlserialize.*;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.IKFLMessagingLogicException;
import com.rssl.phizic.messaging.MessagingService;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.List;


/**
 * @author Omeliyanchuk
 * @ created 29.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class RegisterPersonChangesOperation extends PersonOperationBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    private static final DepartmentService departmentService = new DepartmentService();
	private final static PersonXMLSerializerService xmlService = new PersonXMLSerializerService();
	private final MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();

	private PersonChanges changes=null;
	private Person changedPerson =null;

	public Department getDepartment() throws BusinessException
	{
		if (getPerson() == null)
			throw new BusinessException("Не установлен пользователь");
		return departmentService.findById(getPerson().getDepartmentId());
	}

	public void setChangedPerson(Person changedPerson)
	{
		this.changedPerson = changedPerson;
	}

	private void calculateChanges() throws BusinessException, BusinessLogicException
	{
		if(getPerson() == null)
			throw new BusinessException("Не установлен пользователь");
		if(changes==null)
		{
			PersonXMLComparator comparator = new PersonXMLComparator(getInstanceName(), changedPerson);
			changes = comparator.compare(getPerson().getLogin());
		}
	}


	public void checkMobileChanges() throws BusinessException, BusinessLogicException
	{
		calculateChanges();
		if (isMobileChanged())
			sendSms(changes);
		
		XMLPersonRepresentation repres = PersonXMLSerializer.createXMLRepresentation(getPerson(),getInstanceName());
		xmlService.saveOrUpdateRepresentation(repres);
	}

	/**
	 * Изменился ли мобильный номер
	 * @return boolean true-изменился
	 * @throws BusinessException
	 */
	private boolean isMobileChanged() throws BusinessException, BusinessLogicException
	{
		if (changes == null)
			calculateChanges();
		if (!StringHelper.isEmpty(changes.getNewMobilePhone()) && !StringHelper.isEmpty(changes.getOldMobilePhone()))
		{
			//Пытаемся привести номер телефона к виду +7(xxx)xxxxxxx
			String newMobilePhone = getIKFLPhoneNumberFormat(changes.getNewMobilePhone());
			String oldMobilePhone = getIKFLPhoneNumberFormat(changes.getOldMobilePhone());
			return !newMobilePhone.equals(oldMobilePhone);
		}
		return false;
	}

	/**
	 * Приводит номер телефона к виду +7(xxx)xxxxxxx, если не получается, возвращаем как есть
	 * @param phoneNumber - номер телефона
	 * @return номер телефона в формате +7(xxx)xxxxxxx или как пришло в случае ошибки
	 */
	private String getIKFLPhoneNumberFormat(String phoneNumber)
	{
		try
		{
			if (StringHelper.isEmpty(phoneNumber))
				return "";
			return PhoneNumberFormat.IKFL.translate(phoneNumber);
		}
		catch (NumberFormatException ignored)
		{
			return phoneNumber;
		}
	}

	private void sendSms(PersonChanges changes) throws BusinessException, BusinessLogicException
	{
		Person person = getPerson();
		Login login = person.getLogin();
		Department dep = departmentService.findById(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getDepartmentId());
		String messageText = "Интернет-банк СБРФ: Ваш номер для паролей доступа изменен на "+  changes.getNewMobilePhone() +
				" Если Вы не меняли номер телефона, срочно свяжитесь с банком для блокирования услуги по т. " + dep.getTelephone();
		try
		{
			// информацию об изменениях отправляем на прежний мобильный телефон
			IKFLMessage message = new IKFLMessage(login.getId(), messageText, ErmbHelper.hasErmbProfileByLogin(login.getId()));
			message.setRecipientMobilePhone(changes.getOldMobilePhone());
			messagingService.sendSms(message);
		}
		catch(IKFLMessagingException ex)
		{
			throw new BusinessException(ex.getMessage(), ex);
		}
		catch (IKFLMessagingLogicException ex)
		{
			throw new BusinessLogicException(ex.getMessage(), ex);
		}
	}

	public Boolean isChanged() throws BusinessException, BusinessLogicException
	{
		calculateChanges();
		return changes.getIsChanged();
	}

	public Boolean isAccountsChanged() throws BusinessException, BusinessLogicException
	{
		calculateChanges();
		return ((changes.getAccountAdded().size()!=0)||(changes.getAccountDeleted().size()!=0));
	}

	public Boolean isFeeAccountsChanged() throws BusinessException, BusinessLogicException
	{
		calculateChanges();
		return ((changes.getFeeAccountAdded().size()!=0)||(changes.getFeeAccountDeleted().size()!=0));
	}

	public Boolean isEmpoweredPersonsChanged() throws BusinessException, BusinessLogicException
	{
		calculateChanges();
		return ((changes.getEmpoweredPersonAdded().size()!=0)||(changes.getEmpoweredPersonDeleted().size()!=0)
				||(changes.getEmpoweredPersonModified().size()!=0));
	}
}
