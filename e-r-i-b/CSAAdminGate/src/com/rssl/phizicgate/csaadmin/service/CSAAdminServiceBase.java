package com.rssl.phizicgate.csaadmin.service;

import com.Ostermiller.util.Base64;
import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.employee.ContactCenterEmployeeFilterParameters;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.gate.employee.EmployeeListFilterParameters;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.gate.login.LoginBlock;
import com.rssl.phizic.gate.schemes.AccessScheme;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.csaadmin.service.generated.*;
import com.rssl.phizicgate.csaadmin.service.types.LoginBlockImpl;

import java.util.*;

/**
 * @author akrenev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовый сервис клиентстой части общения с ЦСА Админ
 */

public abstract class CSAAdminServiceBase extends AbstractService
{
	private static final int OK_STATUS = 0;
	private static final int LOGIC_ERROR_STATUS = 100;

	protected CSAAdminServiceBase(GateFactory factory)
	{
		super(factory);
	}

	protected static Calendar parseCalendar(String date)
	{
		if (StringHelper.isEmpty(date))
			return null;
		return XMLDatatypeHelper.parseDateTime(date);
	}

	protected static String getStringDateTime(Calendar date)
	{
		return date==null ? null : XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	protected EmployeeListFilterParametersType toGate(EmployeeListFilterParameters source)
	{
		EmployeeListFilterParametersType parameters = new EmployeeListFilterParametersType();
		parameters.setFirstResult(source.getFirstResult());
		parameters.setMaxResults(source.getMaxResults());
		parameters.setSeekerLogin(source.getSeekerLogin());
		parameters.setSeekerAllDepartments(source.isSeekerAllDepartments());
		parameters.setSoughtId(source.getSoughtId());
		parameters.setSoughtLogin(source.getSoughtLogin());
		parameters.setSoughtFIO(source.getSoughtFIO());
		parameters.setSoughtBlockedState(source.getSoughtBlockedState());
		parameters.setSoughtBlockedUntil(getStringDateTime(source.getSoughtBlockedUntil()));
		parameters.setSoughtInfo(source.getSoughtInfo());
		parameters.setSoughtTB(source.getSoughtTB());
		parameters.setSoughtBranchCode(source.getSoughtBranchCode());
		parameters.setSoughtDepartmentCode(source.getSoughtDepartmentCode());
		return parameters;
	}

	protected EmployeeMailManagerFilterParametersType toGate(ContactCenterEmployeeFilterParameters source)
	{
		EmployeeMailManagerFilterParametersType parameters = new EmployeeMailManagerFilterParametersType();
		parameters.setFirstResult(source.getFirstResult());
		parameters.setMaxResults(source.getMaxResults());
		parameters.setSoughtBlockedUntil(getStringDateTime(source.getSoughtBlockedUntil()));
		parameters.setSoughtId(source.getSoughtId());
		parameters.setSoughtFIO(source.getSoughtFIO());
		parameters.setSoughtArea(source.getSoughtArea());
		return parameters;
	}

	protected LoginBlockType toGate(LoginBlock block)
	{
		LoginBlockType blockType = new LoginBlockType();
		blockType.setReasonType(block.getReasonType());
		blockType.setReasonDescription(block.getReasonDescription());
		blockType.setBlockedFrom(getStringDateTime(block.getBlockedFrom()));
		blockType.setBlockedUntil(getStringDateTime(block.getBlockedUntil()));
		return blockType;
	}

	protected DepartmentType toGate(Office department)
	{
		if (department == null)
			return null;
		ExtendedCodeGateImpl code = new ExtendedCodeGateImpl(department.getCode());
		return new DepartmentType(code.getRegion(), code.getBranch(), code.getOffice());
	}

	protected DepartmentType toGate(AllowedDepartment department)
	{
		if (department == null)
			return null;
		return new DepartmentType(department.getTb(), department.getOsb(), department.getVsp());
	}

	protected DepartmentType[] toGate(List<AllowedDepartment> source)
	{
		DepartmentType[] departments = new DepartmentType[source.size()];
		int i = 0;
		for (AllowedDepartment department : source)
			departments[i++] = toGate(department);
		return departments;
	}

	protected AccessSchemeType toGate(AccessScheme scheme)
	{
		AccessSchemeType accessSchemeType = new AccessSchemeType();
		accessSchemeType.setExternalId(scheme.getExternalId());
		accessSchemeType.setName(scheme.getName());
		accessSchemeType.setCategory(scheme.getCategory());
		accessSchemeType.setCAAdminScheme(scheme.isCAAdminScheme());
		accessSchemeType.setVSPEmployeeScheme(scheme.isVSPEmployeeScheme());
		accessSchemeType.setMailManagement(scheme.isMailManagement());
		return accessSchemeType;
	}

	protected EmployeeType toGate(Employee source)
	{
		EmployeeType destination = new EmployeeType();
		destination.setExternalId(source.getExternalId());
		LoginType login = new LoginType();
		Login sourceLogin = source.getLogin();
		login.setName(sourceLogin.getUserId());
		login.setPassword(sourceLogin.getPassword());
		if (source.getScheme() != null)
			login.setAccessScheme(toGate(source.getScheme()));
		destination.setLogin(login);
		destination.setSurname(source.getSurName());
		destination.setFirstName(source.getFirstName());
		destination.setPatronymic(source.getPatrName());
		destination.setInfo(source.getInfo());
		destination.setEmail(source.getEmail());
		destination.setMobilePhone(source.getMobilePhone());
		destination.setDepartment(toGate(source.getDepartment()));
		destination.setCaAdmin(source.isCAAdmin());
		destination.setVspEmployee(source.isVSPEmployee());
		destination.setManagerId(source.getManagerId());
		destination.setManagerPhone(source.getManagerPhone());
		destination.setManagerEMail(source.getManagerEMail());
		destination.setManagerLeadEMail(source.getManagerLeadEMail());
		destination.setManagerChannel(source.getManagerChannel());
		return destination;
	}

	protected LoginBlock fromGate(LoginBlockType source)
	{
		LoginBlockImpl block = new LoginBlockImpl();
		block.setReasonType(source.getReasonType());
		block.setReasonDescription(block.getReasonDescription());
		block.setBlockedFrom(parseCalendar(source.getBlockedFrom()));
		block.setBlockedUntil(parseCalendar(source.getBlockedUntil()));
		return block;
	}

	protected MapEntryType[] toGate(Map<String,String> parameters)
	{
		if(parameters == null)
			return new MapEntryType[0];
		ArrayList<MapEntryType> mapEntryList = new ArrayList<MapEntryType>();
		for(Map.Entry<String,String> entry: parameters.entrySet())
		{
			mapEntryList.add(new MapEntryType(entry.getKey(),entry.getValue()));
		}
		return mapEntryList.toArray(new MapEntryType[mapEntryList.size()]);
	}

	protected Map<String,String> fromGate(MapEntryType[] parameters)
	{
		Map<String,String> result = new HashMap<String,String>();
		if(parameters == null)
			return result;
		for(MapEntryType mapEntry: parameters)
		{
			result.put(mapEntry.getKey(),mapEntry.getValue());
		}
		return result;
	}

	protected void processError(StatusType status) throws GateException, GateLogicException
	{
		if (status.getStatusCode() == LOGIC_ERROR_STATUS)
			throw new GateLogicException(status.getStatusDesc());

		throw new GateException(status.getStatusDesc());
	}

	private RequestType createRequest(RequestData data)
	{
		RequestType request = new RequestType();
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(getStringDateTime(Calendar.getInstance()));
		request.setOperUID(OperationContext.getCurrentOperUID());
		request.setSessionID(AuthenticationContext.getContext().getCSA_SID());
		request.setData(data);
		return request;
	}

	protected final ResponseData process(RequestData data) throws GateException, GateLogicException
	{
		ResponseType response = CSAAdminTransportProvider.process(createRequest(data));
		if (response.getStatus().getStatusCode() != OK_STATUS)
			processError(response.getStatus());
		return response.getData();
	}

	/**
	 * раскодить данные
	 * @param data данные
	 * @return данные
	 */
	public static String decodeData(String data)
	{
		return Base64.decode(data);
	}
}
