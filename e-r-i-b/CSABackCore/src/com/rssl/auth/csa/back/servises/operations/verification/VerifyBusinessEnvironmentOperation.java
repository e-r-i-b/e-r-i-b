package com.rssl.auth.csa.back.servises.operations.verification;

import com.rssl.auth.csa.back.integration.dasreda.BusinessEnvironmentDataSource;
import com.rssl.auth.csa.back.integration.dasreda.BusinessEnvironmentService;
import com.rssl.auth.csa.back.integration.erib.ERIBService;
import com.rssl.auth.csa.back.integration.erib.types.ClientInformation;
import com.rssl.auth.csa.back.integration.mobilebank.MobileBankService;
import com.rssl.auth.csa.back.servises.operations.ConfirmableOperationBase;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.GetRegistrationMode;
import org.hibernate.Session;

import java.util.Collection;

/**
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� ����������� ������� � ������� �����
 */

public class VerifyBusinessEnvironmentOperation extends ConfirmableOperationBase
{
	private static final String CLIENT_ID_PARAM_NAME                = "clientExternalId";
	private static final String CARD_NUMBER_PARAM_NAME              = "cardNumber";
	private static final String CONFIRMATION_CARD_NUMBER_PARAM_NAME = "confirmationCardNumber";

	private static final BusinessEnvironmentService businessEnvironmentService = new BusinessEnvironmentService();
	private static final ERIBService eribService = new ERIBService();

	/**
	 * ����������� (��� ���������)
	 */
	public VerifyBusinessEnvironmentOperation()
	{}

	/**
	 * �����������
	 * @param identificationContext �������� �������
	 * @throws Exception
	 */
	public VerifyBusinessEnvironmentOperation(IdentificationContext identificationContext) throws Exception
	{
		super(identificationContext);
		addParameter(CARD_NUMBER_PARAM_NAME, identificationContext.getCardNumber());
	}

	public String getCardNumberFoSendConfirm()
	{
		return getParameter(CONFIRMATION_CARD_NUMBER_PARAM_NAME);
	}

	private String getClientCardNumber()
	{
		return getParameter(CARD_NUMBER_PARAM_NAME);
	}

	/**
	 * �������� ������� ������������� ������������
	 * @return ������� ������������� ������������
	 */
	public String getClientExternalId()
	{
		return getParameter(CLIENT_ID_PARAM_NAME);
	}

	/**
	 * ������������ ������������� �����������
	 * @param clientExternalId ������������� ������� � ��
	 * @param confirmStrategyType ������ �������������
	 * @param cardNumber ����� ����� �������������
	 * @throws Exception
	 */
	public void initialize(String clientExternalId, ConfirmStrategyType confirmStrategyType, String cardNumber) throws Exception
	{
		//��������� ���������
		addParameter(CLIENT_ID_PARAM_NAME,                clientExternalId);
		if (ConfirmStrategyType.sms == confirmStrategyType || ConfirmStrategyType.push == confirmStrategyType)
			addParameter(CONFIRMATION_CARD_NUMBER_PARAM_NAME, getClientCardNumber());
		else
			addParameter(CONFIRMATION_CARD_NUMBER_PARAM_NAME, cardNumber);
		//���������� ������� �������������
		super.initialize(confirmStrategyType);
	}

	private ClientInformation getERIBClientInformation()
	{
		try
		{
			return eribService.getClientInformation(getProfile());
		}
		catch (Exception e)
		{
			log.error("������ ��������� ���������� � ������ �� ����.", e);
			return null;
		}
	}

	private Collection<String> getMobilePhone() throws GateException, GateLogicException
	{
		return MobileBankService.getInstance().getClientPhonesByCard(getClientCardNumber(), GetRegistrationMode.SOLF);
	}

	/**
	 * �������������� ������ � ������� �����
	 * @throws Exception
	 */
	public void execute() throws Exception
	{
		execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				//�������� �������� ������ ��� �����������
				BusinessEnvironmentDataSource dataSource = new BusinessEnvironmentDataSource(getClientExternalId(), getProfile(), getERIBClientInformation(), getMobilePhone());
				businessEnvironmentService.sendData(dataSource);
				return null;
			}
		});
	}
}
