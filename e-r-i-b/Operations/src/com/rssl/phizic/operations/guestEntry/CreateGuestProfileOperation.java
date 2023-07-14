package com.rssl.phizic.operations.guestEntry;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.LoginAlreadyRegisteredException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.GuestPersonData;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.login.GuestHelper;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.claims.sbnkd.IssueCardService;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author usachev
 * @ created 30.01.15
 * @ $Author$
 * @ $Revision$
 * [Гостевой вход] Операция регистрации гостя в ЕРИБ
 */
public class CreateGuestProfileOperation extends OperationBase
{
	private static final IssueCardService issueCardService = new IssueCardService();
	private static final BusinessDocumentService businessDocmentService = new BusinessDocumentService();

	public void execute(Long id, String type, String login, String password) throws GateException, BusinessException, BusinessLogicException, LoginAlreadyRegisteredException
	{
		if(id == null)
			throw new IllegalArgumentException("Идентификатор заявки не может быть null");

		if(type == null)
			throw new IllegalArgumentException("Тип заявки не может быть null");

		if(!PersonContext.isAvailable())
			throw new BusinessException("Не доступен контекст гостя");

		PersonData guestData = PersonContext.getPersonDataProvider().getPersonData();
		GuestLogin guestLogin = (GuestLogin) guestData.getPerson().getLogin();

		IssueCardDocumentImpl document = null;
		ExtendedLoanClaim loanClaim = null;
		LoanCardClaim loanCardClaim = null;
		if (FormConstants.EXTENDED_LOAN_CLAIM.equals(type))
			loanClaim = (ExtendedLoanClaim) businessDocmentService.findById(id);
		else if (FormConstants.LOAN_CARD_CLAIM.equals(type))
			loanCardClaim = (LoanCardClaim) businessDocmentService.findById(id);
		else
			document = getCardDocument(id);
		if(document == null && loanClaim == null && loanCardClaim == null)
			throw new BusinessException("Не найдена заявка с id = " + id + " type = " + type);

		UserInfo userInfo = null;
		if (loanClaim != null)
			userInfo = buildUserInfo(loanClaim);
		else if (loanCardClaim != null)
			userInfo = buildUserInfo(loanCardClaim);
		else
			userInfo = buildUserInfo(document);

		Long guestProfileId = registration(guestLogin, login, password, userInfo);
		//обновляем данные гостя
		GuestPerson guestPerson;
		if (loanClaim != null)
			guestPerson = GuestHelper.synchronize(userInfo, guestLogin, loanClaim.getOwnerIdCardSeries(), loanClaim.getOwnerIdCardNumber());
		else if (loanCardClaim != null)
			guestPerson =  GuestHelper.synchronize(userInfo, guestLogin, loanCardClaim.getOwnerIdCardSeries(), loanCardClaim.getOwnerIdCardNumber());
		else
			guestPerson = GuestHelper.synchronize(userInfo, guestLogin, document.getIdentityCardSeries(), document.getIdentityCardNumber());
		PersonContext.getPersonDataProvider().setPersonData(new GuestPersonData(guestLogin, guestPerson, guestData.isMB(), guestData.getCSAProfileId(), guestProfileId, guestData.getGuestLoginAlias()));
	}

	/**
	 * Регистрация гостя
	 * @param guestLogin Сущность "Логин"
	 * @param login Логин, по которому клиент будет авторизоываться
	 * @param password Пароль, для входа пользователя
	 * @param userInfo Личная данные клиента
	 * @return ИД гостевой учётной записи в ЦСА
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 * @throws LoginAlreadyRegisteredException
	 */
	private Long registration(GuestLogin guestLogin, String login, String password, UserInfo userInfo) throws BusinessException, BusinessLogicException, LoginAlreadyRegisteredException
	{
		try
		{
			Document document = CSABackRequestHelper.sendGuestRegistrationRq(
					guestLogin.getAuthPhone(), guestLogin.getGuestCode(), login, password, getCurrentNode(), userInfo);
			String guestProfileId = XmlHelper.getSimpleElementValue(document.getDocumentElement(), CSAResponseConstants.GUEST_PROFILE_ID_TAG);
			return Long.parseLong(guestProfileId);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (LoginAlreadyRegisteredException e)
		{
			throw new LoginAlreadyRegisteredException("Введенный вами логин занят. Придумайте уникальный логин для авторизации в Сбербанк Онлайн");
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private UserInfo buildUserInfo(IssueCardDocumentImpl claim)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setFirstname(claim.getPersonFirstName());
		userInfo.setSurname(claim.getPersonLastName());
		userInfo.setPatrname(claim.getPersonMiddleName());
		userInfo.setBirthdate(claim.getPersonBirthday());
		userInfo.setPassport(getPassport(claim));
		userInfo.setCbCode(claim.getTb());
		return userInfo;
	}
	private UserInfo buildUserInfo(ExtendedLoanClaim claim)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setFirstname(claim.getOwnerFirstName());
		userInfo.setSurname(claim.getOwnerLastName());
		userInfo.setPatrname(claim.getOwnerMiddleName());
		userInfo.setBirthdate(claim.getOwnerBirthday());
		userInfo.setPassport(getPassport(claim));
		userInfo.setCbCode(claim.getTb());
		return userInfo;
	}

	private UserInfo buildUserInfo(LoanCardClaim claim)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setFirstname(claim.getOwnerFirstName());
		userInfo.setSurname(claim.getOwnerLastName());
		userInfo.setPatrname(claim.getOwnerMiddleName());
		userInfo.setBirthdate(claim.getOwnerBirthday());
		userInfo.setPassport(getPassport(claim));
		userInfo.setCbCode(claim.getTb());
		return userInfo;
	}

	private String getPassport(IssueCardDocumentImpl claim)
	{
		String series = StringHelper.getEmptyIfNull(claim.getIdentityCardSeries());
		String number = StringHelper.getEmptyIfNull(claim.getIdentityCardNumber());

		return series + number;
	}
	private String getPassport(ExtendedLoanClaim claim)
	{
		String series = StringHelper.getEmptyIfNull(claim.getOwnerIdCardSeries());
		String number = StringHelper.getEmptyIfNull(claim.getOwnerIdCardNumber());

		return series + number;
	}
	private String getPassport(LoanCardClaim claim)
	{
		String series = StringHelper.getEmptyIfNull(claim.getOwnerIdCardSeries());
		String number = StringHelper.getEmptyIfNull(claim.getOwnerIdCardNumber());

		return series + number;
	}

	private Long getCurrentNode()
	{
		ApplicationAutoRefreshConfig config = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
		return config.getNodeNumber();
	}

	private IssueCardDocumentImpl getCardDocument(Long id) throws BusinessException
	{
		try
		{
			return issueCardService.getClaim(id);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
