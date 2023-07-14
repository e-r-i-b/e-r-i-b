package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.integration.erib.ERIBService;
import com.rssl.auth.csa.back.integration.erib.types.ConfirmationInformation;
import com.rssl.auth.csa.back.integration.erib.types.CardInformation;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;

import java.util.Collections;

/**
 * @author akrenev
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������� ������ � �������� ������������� �������� ��������
 */

public class GetConfirmationInfoOperation extends Operation
{
	private static final String CARD_NUMBER_PARAM_NAME = "cardNumber";
	private static final ERIBService eribService = new ERIBService();

	private ConfirmationInformation confirmationInformation;

	/**
	 * ����������� (��� ���������)
	 */
	public GetConfirmationInfoOperation()
	{}

	/**
	 * �����������
	 * @param identificationContext �������� �������
	 */
	public GetConfirmationInfoOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
		addParameter(CARD_NUMBER_PARAM_NAME, identificationContext.getCardNumber());
	}

	private String getClientCardNumber()
	{
		return getParameter(CARD_NUMBER_PARAM_NAME);
	}

	/**
	 * ������������� ��������
	 * @throws Exception
	 */
	public void initialize() throws Exception
	{
		initialize(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				confirmationInformation = eribService.getConfirmationInformation(getProfile());
				if (confirmationInformation != null)
					return null;

				//�� ����� �� ������ ������ �������� �������� ��������� ���������� ����������:
				//������ ������������� -- ���
				//����� ������������� -- ����� �����
				confirmationInformation = new ConfirmationInformation();
				confirmationInformation.setConfirmStrategyType(ConfirmStrategyType.sms);
				confirmationInformation.setPushAllowed(false);
				confirmationInformation.setConfirmationSources(Collections.singletonList(new CardInformation(getClientCardNumber())));
				return null;
			}
		});
	}

	/**
	 * @return ������ � �������� ������������� �������� ��������
	 */
	public ConfirmationInformation getConfirmationInformation()
	{
		return confirmationInformation;
	}
}
