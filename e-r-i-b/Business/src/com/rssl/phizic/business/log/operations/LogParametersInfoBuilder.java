package com.rssl.phizic.business.log.operations;

import com.rssl.phizic.auth.*;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeImpl;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.persons.PersonBase;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.person.PersonDocument;

import java.util.Date;

/**
 * @author Egorova
 * @ created 25.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class LogParametersInfoBuilder
{
	private StringBuilder logInfo = new StringBuilder();
	private SchemeOwnService shemeService = new SchemeOwnService();
	private static MultiInstancePersonService personService = new MultiInstancePersonService();
	private static final EmployeeService employeeService = new EmployeeService();

	/**
     * Формирование анкеты клиента (общих данных)
     * @param person - клиент
	 * @return logInfo - сформированную "красивую" (с тегами) строку
     */
	public StringBuilder getPersonLogInfo(PersonBase person) throws BusinessException
	{
		logInfo.delete(0,logInfo.length());

		logInfo.append("<br/><b>Пользователь</b>:").append(person.getId());
		logInfo.append("<br/><b>Идентификатор</b>:").append(person.getClientId());
		logInfo.append("<br/><b>Ф.И.О</b>:").append(person.getFullName());
		appendString("Пол", person.getGender());
		if(person.getBirthDay()!=null) logInfo.append("<br/><b>Дата рождения:</b>"+person.getBirthDay());
		appendString("Место рождения", person.getBirthPlace());
		//документы
		if (person.getPersonDocuments() != null)
		{
			for (PersonDocument personDocument : person.getPersonDocuments())
			{
				appendString("Вид документа", getTextIdentityType(personDocument.getDocumentType().toString()), personDocument.getDocumentType().toString());
				appendString("Наименование документа", personDocument.getDocumentName());
				appendString("Cерия", personDocument.getDocumentSeries());
				appendString("Номер", personDocument.getDocumentNumber());
				if ( personDocument.getDocumentIssueDate()!= null) logInfo.append("<br/><b>Дата выдачи</b>:").append(personDocument.getDocumentIssueDate().getTime());
				appendString("Кем выдан", personDocument.getDocumentIssueBy());
				appendString("Код подразделения", personDocument.getDocumentIssueByCode());
			}
		}
		appendString("Гражданство", person.getCitizenship());
		appendString("ИНН", person.getInn());

			//Контактная информация
		if (!person.getRegistrationAddress().isEmpty()) logInfo.append("<br/><b>Адрес регистрации</b>:").append(person.getRegistrationAddress().toString());
		if (!person.getResidenceAddress().isEmpty()) logInfo.append("<br/><b>Адрес фактического проживания</b>:").append(person.getResidenceAddress().toString());
		if (person.getIsResident()!=null)
		    if (person.getIsResident()) logInfo.append("<br/>Резидент");
			else logInfo.append("<br/>Нерезидент");
		logInfo.append("<br/><b>Способ доставки оповещений</b>:");
		appendString("Адрес электронной почты", person.getEmail());
		appendString("Домашний телефон", person.getHomePhone());
		appendString("Рабочий телефон", person.getJobPhone());
		appendString("Мобильный телефон", person.getMobilePhone());
		appendString("Мобильный оператор", person.getMobileOperator());
		//Договор
		appendString("Номер договора", person.getAgreementNumber());
		if (person.getDepartmentId()!=null) logInfo.append("<br/><b>Подразделение</b>:").append(person.getDepartmentId());
		appendString("Кодовое слово", person.getSecretWord());
		if(person.getLogin()!=null && !isEmpty(person.getLogin().getUserId())) logInfo.append("<br/><b>Номер ПИН-конверта</b>:").append(person.getLogin().getUserId());
		if (person.getServiceInsertionDate()!=null) logInfo.append("<br/><b>Дата начала обслуживания</b>:").append(person.getServiceInsertionDate().getTime());
		if (person.getProlongationRejectionDate()!=null) logInfo.append("<br/><b>Дата расторжения договора</b>:").append(person.getProlongationRejectionDate().getTime());
		if (!isEmpty( person.getContractCancellationCouse()))logInfo.append("<br/><b>Причина расторжения договора</b>:").append(getTextCancellationCouse(person.getContractCancellationCouse().charAt(0)));		
		//название схем прав
		AccessScheme simpleSheme = shemeService.findScheme(person.getLogin(), AccessType.simple);
		if(simpleSheme!=null) appendString("Схема прав", getShemeName(simpleSheme.getName()));
		AccessScheme secureSheme = shemeService.findScheme(person.getLogin(), AccessType.secure);
		if(secureSheme!=null) appendString("Схема прав.Защищенный доступ", getShemeName(secureSheme.getName()));
		AccessScheme smsBankingScheme = shemeService.findScheme(person.getLogin(), AccessType.smsBanking);
		if (smsBankingScheme != null)
		{
			appendString("Схема прав. SMS Banking", getShemeName(smsBankingScheme.getName()));
		}
		return logInfo;
	}

   	/**
     * Формирование идентифицирующих данных о клиенте
     * @param person - клиент
	 * @return logInfo - сформированную "красивую" (с тегами) строку
     */
	public StringBuilder getShortPersonLogInfo(PersonBase person) throws BusinessException
	{
		logInfo.delete(0,logInfo.length());
		logInfo.append("<br/><b>Идентификатор</b>:").append(person.getClientId());
		logInfo.append("<br/><b>Ф.И.О</b>:").append(person.getFullName());
		return logInfo;
	}

	/**
     * Формирование данных о сотруднике
     * @param employee - сотрудник
	 * @return logInfo - сформированную "красивую" (с тегами) строку
     */
	public StringBuilder getEmployeeLogInfo(EmployeeImpl employee) throws BusinessException
	{
		logInfo.delete(0,logInfo.length());
		logInfo.append("<br/><b>ID сотрудника</b>:").append(employee.getId());
		appendString("Ф.И.О.", employee.getFullName());
		appendString("Адрес электронной почты", employee.getEmail());
		appendString("Мобильный телефон", employee.getMobilePhone());
		if (employee.getSMSFormat() == TranslitMode.DEFAULT) appendString("SMS формат","русский");
		if (employee.getSMSFormat() == TranslitMode.TRANSLIT) appendString("SMS формат","транслитерация");
		appendString("Доп. информация", employee.getInfo());
		if(employee.getLogin()!=null) appendString("Логин", employee.getLogin().getUserId());
	    if(employee.getDepartmentId()!=null)appendString("Подразделение", employee.getDepartmentId().toString());
		AccessScheme accessScheme = shemeService.findScheme(employee.getLogin(), AccessType.employee);
		if(accessScheme!=null) appendString("Схема прав", getShemeName(accessScheme.getName()));
		return logInfo;
	}

	/**
     * Формирование данных о логине сотрудника
     * @param employeeLogin - логин сотрудника
	 * @return logInfo - сформированную "красивую" (с тегами) строку
     */
	public StringBuilder getEmployeeLoginLogInfo(Long employeeLogin) throws BusinessException
	{
		logInfo.delete(0,logInfo.length());
		logInfo.append("<br/><b>ID сотрудника</b>:").append(employeeLogin);
		return logInfo;
    }

	/**
     * Формирование идентифицирующих данных о сотруднике
     * @param employee - сотрудник
	 * @return logInfo - сформированную "красивую" (с тегами) строку
     */
	public StringBuilder getShortEmployeeLogInfo(EmployeeImpl employee) throws BusinessException
	{
		logInfo.delete(0,logInfo.length());
		logInfo.append("<br/><b>ID сотрудника</b>:").append(employee.getId());
		appendString("Ф.И.О.", employee.getFullName());		
		return logInfo;
	}

	private static Boolean isEmpty(String parameter)
	{
		return (parameter==null || parameter.length()==0);
	}

	/*
	* Сопоставляет тип документа и возвращает его строковое описание
	* todo: если будет поправлено в jsp, здесь тоже исправить
	* */
	private static String getTextIdentityType(String type)
	{
		if (type.equals("REGULAR_PASSPORT_RF")) return "Общегражданский паспорт РФ";
		if (type.equals("MILITARY_IDCARD")) return "Удостоверение личности военнослужащего";
		if (type.equals("SEAMEN_PASSPORT")) return "Паспорт моряка";
		if (type.equals("RESIDENTIAL_PERMIT_RF")) return "Вид на жительство РФ";
		if (type.equals("FOREIGN_PASSPORT_RF")) return "Заграничный паспорт РФ";
		if (type.equals("OTHER")) return "Иной докумен";
		return "неизвестный тип документа";

	}

	/*
	* Сопоставляет тип причины отказа и возвращает ее строковое описание
	* todo: если будет поправлено в jsp, здесь тоже исправить
	* */
	private static String getTextCancellationCouse(char couse)
	{
		switch (couse){
			case 'C': return "По инициативе клиента";
			case 'B': return "По инициативе банка";
			case 'W': return "По инициативе банка без взимания платы";
			default: return "Неизвестная причина";
		}
	}

	public static String getShemeName(String name)
	{
		if (name.equals("personal")) return "Индивидуальные права";
		if (name.length()==0) return "Нет схемы прав";
		else return name;
	}

	/**
     * Формирование данных о блокировке
     * @param parameterName - название запрашивающего параметра
	 * @param destinationString - строка для дополнения
	 * Параметры блокировки
	 * @param reasonType - тип
	 * @param reasonDescription - причина
	 * @param from - дата начала
	 * @param until - дата окончания
	 * @param employeeLogin - логин заблокировавшего сотрудника
	 * @return destinationString - сформированную "красивую" (с тегами) строку
     */
	public StringBuilder appendBlockingInfo(String parameterName, StringBuilder destinationString, BlockingReasonType reasonType, String reasonDescription, Date from, Date until, Long employeeLogin)  throws BusinessException
	{
		destinationString.append("<br/><br/><b><u>"+parameterName+"</u></b>");
		destinationString.append(stringBuilder("Тип", BlockingReasonDescriptor.getReasonText(reasonType)));
		destinationString.append(stringBuilder("Причина", reasonDescription));
		destinationString.append("<br/><b>Дата начала:</b> "+ from);
		if (until!=null)
			destinationString.append("<br/><b>Дата окончания:</b> "+until);	
		if (employeeLogin!=null)
		{
			Employee employee = employeeService.findById(Long.valueOf(employeeLogin));
			if (employee != null){
				destinationString.append("<br/><b>Заблокировавший сотрудник</b> "+getEmployeeLogInfo((EmployeeImpl) employee));
			}
			else
				destinationString.append("<br/><b>Заблокировавший сотрудник</b> "+getEmployeeLoginLogInfo(employeeLogin));
		}
		return destinationString;
	}

	/**
     *Получение и формирование данных клиенте или сотруднике по логину
     * @param login - логин
	 * @param operationName - название вызывающего параметра
	 * @return stringBuilder - сформированную "красивую" (с тегами) строку
     */
	public StringBuilder stringUserLoginInfo(CommonLogin login, String operationName) throws BusinessException
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (login instanceof BankLogin)
		{
			Employee employee = employeeService.findByLogin((BankLogin) login);
			stringBuilder.append("<br/><br/><b><u>"+operationName+"</u></b>"+ getEmployeeLogInfo((EmployeeImpl) employee));
		}
		else
		{
			ActivePerson person = personService.findByLogin((Login) login, null);
			stringBuilder.append("<br/><b><u>"+operationName+"</u></b>"+getPersonLogInfo(person));
		}
		return stringBuilder;
	}

	/**
     *Формирование строки, для параметров с 2 мя значениями, например для настроек
     * @param parameterName - название параметра
	 * @param parameterValueOld - старое значение параметра
	 * @param parameterValueNew - новое значение параметра
	 * @return stringBuilder - сформированную "красивую" (с тегами) строку
     */
	public StringBuilder stringSettingsBuilder(String parameterName, String parameterValueOld, String parameterValueNew)
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (!parameterValueNew.equals(parameterValueOld))
			stringBuilder.append("<br/><b>"+parameterName+"</b>: Старое = ["+parameterValueOld+"] Новое = ["+parameterValueNew+"]");
		return stringBuilder;
	}

	/**
     *Вспомогательная функция для формирования стандартной строки параметра
     * @param parameterName - название параметра
	 * @param parameterValue - значение параметра
	 * @return stringBuilder - сформированную "красивую" (с тегами) строку
     */
	public StringBuilder stringBuilder(String parameterName, String parameterValue)
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (!isEmpty(parameterValue)) stringBuilder.append("<br/><b>"+parameterName+":</b> "+parameterValue);
		return stringBuilder;
	}

	private void appendString(String parameterName, String parameterValue)
	{
		appendString(parameterName, parameterValue, parameterValue);
	}

	private void appendString(String parameterName, String parameterValue, String parameterValueForCheck)
	{
		if (!isEmpty(parameterValueForCheck)) logInfo.append("<br/><b>"+parameterName+":</b> "+parameterValue);
	}

}
