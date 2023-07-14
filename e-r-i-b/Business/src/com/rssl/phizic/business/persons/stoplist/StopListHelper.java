package com.rssl.phizic.business.persons.stoplist;

import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.stoplist.ClientStopListState;
import com.rssl.phizic.gate.clients.stoplist.StopListService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * �������� �� ����-�����.
 * @author Roshka
 * @ created 15.02. 2007
 * @ $Author$
 * @ $Revision$
 */

public class StopListHelper
{

	private static final String ERROR_CHECKING_RECIPIENT = "���� ��� ������� ��������� ���������� ������-��������� �� ����-�����.";
	private static final String CONDITION_ON_SEND_DATA = "��� ������ ���������� ������� ������ �� ��������� ���������:\\n\\n 1.\\t�.�.�.\\n 2.\\t���� ��������\\n 3.\\t����� ��� ����� ���������";
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 *
	 * @param firstName ���
	 * @param secondName �������
	 * @param fatherName ��������
	 * @param docSeries ����� ���������
	 * @param docNumber ����� ���������
	 * @param birthday ���� ��������
	 * @param inn ���
	 * @return ������ ������� � ���� �����.
	 * @throws StopListLogicException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static ClientStopListState checkPerson(String firstName, String secondName, String fatherName, String docSeries, String docNumber, Calendar birthday, String inn) throws StopListLogicException
	{
		ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);
		if (!clientConfig.getNeedStopListCheck())
			return ClientStopListState.trusted;

		if (StringHelper.isEmpty(docNumber) || StringHelper.isEmpty(firstName) || StringHelper.isEmpty(secondName) || StringHelper.isEmpty(fatherName))
		{
			throw new StopListLogicException(CONDITION_ON_SEND_DATA);
		}

		try
		{
			StopListService service = GateSingleton.getFactory().service(StopListService.class);
			return service.check(inn, firstName, fatherName, secondName, docSeries, docNumber, birthday);

		}
		catch (Exception e)
		{
			log.error(StopListHelper.ERROR_CHECKING_RECIPIENT, e);
			// ������ �� �������������, �.�. ������ ����-����� �� ������ ������ �������� ���������
			return ClientStopListState.trusted;
		}
	}
}
