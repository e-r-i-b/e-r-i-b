package com.rssl.phizic.business.loyaltyProgram;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoyaltyProgramLink;
import com.rssl.phizic.business.resources.external.LoyaltyProgramState;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.util.ApplicationUtil;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 08.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyProgramHelper
{
	private  static final SimpleService simpleService  = new SimpleService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ��������� ��������� � ��������� ���������� ��� �������
	 * @throws BusinessException
	 */
	public static void updateLoyaltyProgram() throws BusinessException, BusinessLogicException
	{
	    LoyaltyProgramService service = GateSingleton.getFactory().service(LoyaltyProgramService.class);
		PersonData personData  =  PersonContext.getPersonDataProvider().getPersonData();
	    Login login = personData.getPerson().getLogin();
		LoyaltyProgramLink link = personData.getLoyaltyProgram();
		if (link == null)
		{
			link = new LoyaltyProgramLink();
			link.setLoginId(login.getId());
			link.setShowInMain(true);
		}

		String cardNumber = "";
		if (ApplicationUtil.isErmbSms())
		{
			CardLink cardLink = CardsUtil.getLongTermCard(personData.getCards());
			if (cardLink != null)
				cardNumber = cardLink.getNumber();
		}
		else
		{
			cardNumber = login.getLastLogonCardNumber();
		}

		if (!cardNumber.equals(""))
		{
			try
			{
				if (link.getExternalId() != null)
					link.reset();
				LoyaltyProgram program = service.getClientLoyaltyProgram(cardNumber);
				if (program != null)
				{
					//���� �������� ��������� ����������, ��������� ��� ��������� ���� �� ���
					link.setExternalId(program.getExternalId());
					link.setState(LoyaltyProgramState.ACTIVE);
					link.setBalance(program.getBalance());
					link.setUpdateTime(Calendar.getInstance());
				}
				else
				{
					//������ ������ �����������������
					link.setExternalId(null);
					link.setBalance(null);
					link.setUpdateTime(null);
					link.setState(LoyaltyProgramState.UNREGISTERED);
				}
			}
			catch (GateException ex)
			{
				log.error("�� ������� �������� ���������� � ��������� ���������� ", ex);
				throw new BusinessException(ex) ;
			}
			catch (GateLogicException e)
			{
				log.error("�� ������� �������� ���������� � ��������� ���������� ", e);
				throw new BusinessLogicException(e);
			}
		}
		else //�� �������� ����� �����, ������ ������ INACTIVE
		{
			link.setState(LoyaltyProgramState.INACTIVE);
			log.error("������ ������ ��������� ������ ����� ���������� �����.");
		}

		simpleService.addOrUpdate(link);
	}

	/**
	 * @return ���� ��������� ���������� ��� �������� ������������
	 */
	public static LoyaltyProgramLink getLoyaltyProgramLink()
	{
		if (!PersonContext.isAvailable())
			return null;

		PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		try
		{
			return data.getLoyaltyProgram();
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� ��������� ���������� ��� ������������ loginId=" + data.getPerson().getLogin().getId(), e);
			return null;
		}
	}

}
