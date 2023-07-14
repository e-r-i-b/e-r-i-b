package com.rssl.phizicgate.csaadmin.service.access;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.schemes.AccessScheme;
import com.rssl.phizic.gate.schemes.AccessSchemeService;
import com.rssl.phizicgate.csaadmin.service.CSAAdminServiceBase;
import com.rssl.phizicgate.csaadmin.service.generated.*;
import com.rssl.phizicgate.csaadmin.service.types.AccessSchemeImpl;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Гейтовый сервис работы со схемами прав сотрудников
 */

public class AccessSchemeServiceImpl extends CSAAdminServiceBase implements AccessSchemeService
{
	/**
	 * конструктор
	 * @param factory фабрика гейта, в рамках которого происходит создание сервиса
	 */
	public AccessSchemeServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public AccessScheme getById(Long id) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		data.setGetAccessSchemeByIdRq(new GetAccessSchemeByIdParametersType(id));
		ResponseData response = process(data);
		AccessSchemeType result = response.getGetAccessSchemeByIdRs().getAccessScheme();
		return new AccessSchemeImpl(result);
	}

	public List<AccessScheme> getList(String[] categories) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		data.setGetAccessSchemeListRq(new GetAccessSchemeListParametersType(new AccessSchemeListFilterParametersType(categories)));
		ResponseData response = process(data);
		AccessSchemeType[] gateResult = response.getGetAccessSchemeListRs().getSchemes();
		if (ArrayUtils.isEmpty(gateResult))
			return Collections.emptyList();

		List<AccessScheme> schemes = new ArrayList<AccessScheme>(gateResult.length);
		for (AccessSchemeType accessScheme : gateResult)
			schemes.add(new AccessSchemeImpl(accessScheme));
		return schemes;
	}

	public AccessScheme save(AccessScheme schema) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		data.setSaveAccessSchemeRq(new SaveAccessSchemeParametersType(toGate(schema)));
		ResponseData response = process(data);
		AccessSchemeType result = response.getSaveAccessSchemeRs().getAccessScheme();
		return new AccessSchemeImpl(result);
	}

	public void delete(AccessScheme schema) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		data.setDeleteAccessSchemeRq(new DeleteAccessSchemeParametersType(toGate(schema)));
		process(data);
	}
}
