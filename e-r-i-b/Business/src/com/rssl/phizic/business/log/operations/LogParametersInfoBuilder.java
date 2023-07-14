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
     * ������������ ������ ������� (����� ������)
     * @param person - ������
	 * @return logInfo - �������������� "��������" (� ������) ������
     */
	public StringBuilder getPersonLogInfo(PersonBase person) throws BusinessException
	{
		logInfo.delete(0,logInfo.length());

		logInfo.append("<br/><b>������������</b>:").append(person.getId());
		logInfo.append("<br/><b>�������������</b>:").append(person.getClientId());
		logInfo.append("<br/><b>�.�.�</b>:").append(person.getFullName());
		appendString("���", person.getGender());
		if(person.getBirthDay()!=null) logInfo.append("<br/><b>���� ��������:</b>"+person.getBirthDay());
		appendString("����� ��������", person.getBirthPlace());
		//���������
		if (person.getPersonDocuments() != null)
		{
			for (PersonDocument personDocument : person.getPersonDocuments())
			{
				appendString("��� ���������", getTextIdentityType(personDocument.getDocumentType().toString()), personDocument.getDocumentType().toString());
				appendString("������������ ���������", personDocument.getDocumentName());
				appendString("C����", personDocument.getDocumentSeries());
				appendString("�����", personDocument.getDocumentNumber());
				if ( personDocument.getDocumentIssueDate()!= null) logInfo.append("<br/><b>���� ������</b>:").append(personDocument.getDocumentIssueDate().getTime());
				appendString("��� �����", personDocument.getDocumentIssueBy());
				appendString("��� �������������", personDocument.getDocumentIssueByCode());
			}
		}
		appendString("�����������", person.getCitizenship());
		appendString("���", person.getInn());

			//���������� ����������
		if (!person.getRegistrationAddress().isEmpty()) logInfo.append("<br/><b>����� �����������</b>:").append(person.getRegistrationAddress().toString());
		if (!person.getResidenceAddress().isEmpty()) logInfo.append("<br/><b>����� ������������ ����������</b>:").append(person.getResidenceAddress().toString());
		if (person.getIsResident()!=null)
		    if (person.getIsResident()) logInfo.append("<br/>��������");
			else logInfo.append("<br/>����������");
		logInfo.append("<br/><b>������ �������� ����������</b>:");
		appendString("����� ����������� �����", person.getEmail());
		appendString("�������� �������", person.getHomePhone());
		appendString("������� �������", person.getJobPhone());
		appendString("��������� �������", person.getMobilePhone());
		appendString("��������� ��������", person.getMobileOperator());
		//�������
		appendString("����� ��������", person.getAgreementNumber());
		if (person.getDepartmentId()!=null) logInfo.append("<br/><b>�������������</b>:").append(person.getDepartmentId());
		appendString("������� �����", person.getSecretWord());
		if(person.getLogin()!=null && !isEmpty(person.getLogin().getUserId())) logInfo.append("<br/><b>����� ���-��������</b>:").append(person.getLogin().getUserId());
		if (person.getServiceInsertionDate()!=null) logInfo.append("<br/><b>���� ������ ������������</b>:").append(person.getServiceInsertionDate().getTime());
		if (person.getProlongationRejectionDate()!=null) logInfo.append("<br/><b>���� ����������� ��������</b>:").append(person.getProlongationRejectionDate().getTime());
		if (!isEmpty( person.getContractCancellationCouse()))logInfo.append("<br/><b>������� ����������� ��������</b>:").append(getTextCancellationCouse(person.getContractCancellationCouse().charAt(0)));		
		//�������� ���� ����
		AccessScheme simpleSheme = shemeService.findScheme(person.getLogin(), AccessType.simple);
		if(simpleSheme!=null) appendString("����� ����", getShemeName(simpleSheme.getName()));
		AccessScheme secureSheme = shemeService.findScheme(person.getLogin(), AccessType.secure);
		if(secureSheme!=null) appendString("����� ����.���������� ������", getShemeName(secureSheme.getName()));
		AccessScheme smsBankingScheme = shemeService.findScheme(person.getLogin(), AccessType.smsBanking);
		if (smsBankingScheme != null)
		{
			appendString("����� ����. SMS Banking", getShemeName(smsBankingScheme.getName()));
		}
		return logInfo;
	}

   	/**
     * ������������ ���������������� ������ � �������
     * @param person - ������
	 * @return logInfo - �������������� "��������" (� ������) ������
     */
	public StringBuilder getShortPersonLogInfo(PersonBase person) throws BusinessException
	{
		logInfo.delete(0,logInfo.length());
		logInfo.append("<br/><b>�������������</b>:").append(person.getClientId());
		logInfo.append("<br/><b>�.�.�</b>:").append(person.getFullName());
		return logInfo;
	}

	/**
     * ������������ ������ � ����������
     * @param employee - ���������
	 * @return logInfo - �������������� "��������" (� ������) ������
     */
	public StringBuilder getEmployeeLogInfo(EmployeeImpl employee) throws BusinessException
	{
		logInfo.delete(0,logInfo.length());
		logInfo.append("<br/><b>ID ����������</b>:").append(employee.getId());
		appendString("�.�.�.", employee.getFullName());
		appendString("����� ����������� �����", employee.getEmail());
		appendString("��������� �������", employee.getMobilePhone());
		if (employee.getSMSFormat() == TranslitMode.DEFAULT) appendString("SMS ������","�������");
		if (employee.getSMSFormat() == TranslitMode.TRANSLIT) appendString("SMS ������","��������������");
		appendString("���. ����������", employee.getInfo());
		if(employee.getLogin()!=null) appendString("�����", employee.getLogin().getUserId());
	    if(employee.getDepartmentId()!=null)appendString("�������������", employee.getDepartmentId().toString());
		AccessScheme accessScheme = shemeService.findScheme(employee.getLogin(), AccessType.employee);
		if(accessScheme!=null) appendString("����� ����", getShemeName(accessScheme.getName()));
		return logInfo;
	}

	/**
     * ������������ ������ � ������ ����������
     * @param employeeLogin - ����� ����������
	 * @return logInfo - �������������� "��������" (� ������) ������
     */
	public StringBuilder getEmployeeLoginLogInfo(Long employeeLogin) throws BusinessException
	{
		logInfo.delete(0,logInfo.length());
		logInfo.append("<br/><b>ID ����������</b>:").append(employeeLogin);
		return logInfo;
    }

	/**
     * ������������ ���������������� ������ � ����������
     * @param employee - ���������
	 * @return logInfo - �������������� "��������" (� ������) ������
     */
	public StringBuilder getShortEmployeeLogInfo(EmployeeImpl employee) throws BusinessException
	{
		logInfo.delete(0,logInfo.length());
		logInfo.append("<br/><b>ID ����������</b>:").append(employee.getId());
		appendString("�.�.�.", employee.getFullName());		
		return logInfo;
	}

	private static Boolean isEmpty(String parameter)
	{
		return (parameter==null || parameter.length()==0);
	}

	/*
	* ������������ ��� ��������� � ���������� ��� ��������� ��������
	* todo: ���� ����� ���������� � jsp, ����� ���� ���������
	* */
	private static String getTextIdentityType(String type)
	{
		if (type.equals("REGULAR_PASSPORT_RF")) return "��������������� ������� ��";
		if (type.equals("MILITARY_IDCARD")) return "������������� �������� ���������������";
		if (type.equals("SEAMEN_PASSPORT")) return "������� ������";
		if (type.equals("RESIDENTIAL_PERMIT_RF")) return "��� �� ���������� ��";
		if (type.equals("FOREIGN_PASSPORT_RF")) return "����������� ������� ��";
		if (type.equals("OTHER")) return "���� �������";
		return "����������� ��� ���������";

	}

	/*
	* ������������ ��� ������� ������ � ���������� �� ��������� ��������
	* todo: ���� ����� ���������� � jsp, ����� ���� ���������
	* */
	private static String getTextCancellationCouse(char couse)
	{
		switch (couse){
			case 'C': return "�� ���������� �������";
			case 'B': return "�� ���������� �����";
			case 'W': return "�� ���������� ����� ��� �������� �����";
			default: return "����������� �������";
		}
	}

	public static String getShemeName(String name)
	{
		if (name.equals("personal")) return "�������������� �����";
		if (name.length()==0) return "��� ����� ����";
		else return name;
	}

	/**
     * ������������ ������ � ����������
     * @param parameterName - �������� �������������� ���������
	 * @param destinationString - ������ ��� ����������
	 * ��������� ����������
	 * @param reasonType - ���
	 * @param reasonDescription - �������
	 * @param from - ���� ������
	 * @param until - ���� ���������
	 * @param employeeLogin - ����� ���������������� ����������
	 * @return destinationString - �������������� "��������" (� ������) ������
     */
	public StringBuilder appendBlockingInfo(String parameterName, StringBuilder destinationString, BlockingReasonType reasonType, String reasonDescription, Date from, Date until, Long employeeLogin)  throws BusinessException
	{
		destinationString.append("<br/><br/><b><u>"+parameterName+"</u></b>");
		destinationString.append(stringBuilder("���", BlockingReasonDescriptor.getReasonText(reasonType)));
		destinationString.append(stringBuilder("�������", reasonDescription));
		destinationString.append("<br/><b>���� ������:</b> "+ from);
		if (until!=null)
			destinationString.append("<br/><b>���� ���������:</b> "+until);	
		if (employeeLogin!=null)
		{
			Employee employee = employeeService.findById(Long.valueOf(employeeLogin));
			if (employee != null){
				destinationString.append("<br/><b>��������������� ���������</b> "+getEmployeeLogInfo((EmployeeImpl) employee));
			}
			else
				destinationString.append("<br/><b>��������������� ���������</b> "+getEmployeeLoginLogInfo(employeeLogin));
		}
		return destinationString;
	}

	/**
     *��������� � ������������ ������ ������� ��� ���������� �� ������
     * @param login - �����
	 * @param operationName - �������� ����������� ���������
	 * @return stringBuilder - �������������� "��������" (� ������) ������
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
     *������������ ������, ��� ���������� � 2 �� ����������, �������� ��� ��������
     * @param parameterName - �������� ���������
	 * @param parameterValueOld - ������ �������� ���������
	 * @param parameterValueNew - ����� �������� ���������
	 * @return stringBuilder - �������������� "��������" (� ������) ������
     */
	public StringBuilder stringSettingsBuilder(String parameterName, String parameterValueOld, String parameterValueNew)
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (!parameterValueNew.equals(parameterValueOld))
			stringBuilder.append("<br/><b>"+parameterName+"</b>: ������ = ["+parameterValueOld+"] ����� = ["+parameterValueNew+"]");
		return stringBuilder;
	}

	/**
     *��������������� ������� ��� ������������ ����������� ������ ���������
     * @param parameterName - �������� ���������
	 * @param parameterValue - �������� ���������
	 * @return stringBuilder - �������������� "��������" (� ������) ������
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
