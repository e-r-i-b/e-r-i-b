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
 * Операция получения данных о способах подтверждения операций клиентом
 */

public class GetConfirmationInfoOperation extends Operation
{
	private static final String CARD_NUMBER_PARAM_NAME = "cardNumber";
	private static final ERIBService eribService = new ERIBService();

	private ConfirmationInformation confirmationInformation;

	/**
	 * конструктор (для гибернета)
	 */
	public GetConfirmationInfoOperation()
	{}

	/**
	 * конструктор
	 * @param identificationContext контекст клиента
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
	 * инициализация операции
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

				//из ЕРИБа не смогли ничего потянуть забиваем результат дефолтными значениями:
				//способ подтверждения -- смс
				//карта подтверждения -- карта входа
				confirmationInformation = new ConfirmationInformation();
				confirmationInformation.setConfirmStrategyType(ConfirmStrategyType.sms);
				confirmationInformation.setPushAllowed(false);
				confirmationInformation.setConfirmationSources(Collections.singletonList(new CardInformation(getClientCardNumber())));
				return null;
			}
		});
	}

	/**
	 * @return данные о способах подтверждения операций клиентом
	 */
	public ConfirmationInformation getConfirmationInformation()
	{
		return confirmationInformation;
	}
}
