package com.rssl.phizic.business.ext.sbrf.commissions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.AccountOpeningClaim;
import com.rssl.phizic.gate.claims.IMAOpeningClaim;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.security.util.MobileApiUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import com.rssl.phizic.gate.impl.AbstractService;

import java.util.*;

/**
 * @author vagin
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ����������� ����������� ��������� ��� �������� � ������� ��.
 */
public class CommissionTBSettingService extends AbstractService implements BackRefCommissionTBSettingService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final ServiceService serviceService = new ServiceService();
	private static final Map<String, Pair<String, String>> synonyms = new HashMap<String, Pair<String, String>>();
	private static final Map<String, Pair<String, String>> rurSynonyms = new HashMap<String, Pair<String, String>>();

	static
	{
		//��� �������� �������� �������� �� ��������.
		synonyms.put(ClientAccountsTransfer.class.getName(), new Pair<String, String>("AcctToAcct", "������� ����� - �����"));  //�����-�����
		synonyms.put(AccountToIMATransfer.class.getName(), new Pair<String, String>("AcctToIMA", "������� ����� - ���"));       //�����-���
		synonyms.put(AccountToCardTransfer.class.getName(), new Pair<String, String>("AcctToCard", "������� ����� - �����"));    //�����-�����
		synonyms.put(AccountOpeningClaim.class.getName(), new Pair<String, String>("AcctToNewAcct", "�������� ������"));         //�������� ������
		synonyms.put(AccountClosingPaymentToAccount.class.getName(), new Pair<String, String>("AcctCloseToAcct", "�������� ������ � ��������� �� ����"));    //�������� ������ � ��������� �� ����
		synonyms.put(AccountClosingPaymentToCard.class.getName(), new Pair<String, String>("AcctCloseToCard", "�������� ������ � ��������� �� �����"));       //�������� ������ � ��������� �� �����
		synonyms.put(CardRUSPayment.class.getName(), new Pair<String, String>("CardToOtherBank", "������� � ����� �� ���� � ������ �����"));                  //������� � ����� �� ���� � �� �����.
		synonyms.put(CardIntraBankPayment.class.getName(), new Pair<String, String>("CardToOurBank", "������� � ����� �� ���� � ��������� ������"));          //������� � ����� �� ���� � ��������� ������.
		synonyms.put(IMAOpeningClaim.class.getName(), new Pair<String, String>("IMAOpening", "�������� ��� � ������������� ������� �� ������"));          //������� � ����� �� ���� � ��������� ������.
		//BUG068866
		synonyms.put("AccountOpeningClaimWithClose", new Pair<String, String>("AcctToNewAcctWithClose", "�������� ������ � ��������� ����� ��������"));  //�������� ������(������� ���� ��������)
		// ��� �������� �������� ��������.
		rurSynonyms.put(ClientAccountsTransfer.class.getName(), new Pair<String, String>("RurAcctToAcct", "������� ����� - �����"));  //�����-�����
		rurSynonyms.put(AccountToIMATransfer.class.getName(), new Pair<String, String>("RurAcctToIMA", "������� ����� - ���"));       //�����-���
		rurSynonyms.put(AccountToCardTransfer.class.getName(), new Pair<String, String>("RurAcctToCard", "������� ����� - �����"));    //�����-�����
		rurSynonyms.put(AccountOpeningClaim.class.getName(), new Pair<String, String>("RurAcctToNewAcct", "�������� ������"));         //�������� ������
		rurSynonyms.put(AccountClosingPaymentToAccount.class.getName(), new Pair<String, String>("RurAcctCloseToAcct", "�������� ������ � ��������� �� ����"));    //�������� ������ � ��������� �� ����
		rurSynonyms.put(AccountClosingPaymentToCard.class.getName(), new Pair<String, String>("RurAcctCloseToCard", "�������� ������ � ��������� �� �����"));       //�������� ������ � ��������� �� �����
		rurSynonyms.put(CardRUSPayment.class.getName(), new Pair<String, String>("RurCardToOtherBank", "������� � ����� �� ���� � ������ �����"));                  //������� � ����� �� ���� � �� �����.
		rurSynonyms.put(CardIntraBankPayment.class.getName(), new Pair<String, String>("RurCardToOurBank", "������� � ����� �� ���� � ��������� ������"));          //������� � ����� �� ���� � ��������� ������.
		rurSynonyms.put(IMAOpeningClaim.class.getName(), new Pair<String, String>("RurIMAOpening", "�������� ��� � ������������� ������� �� ������"));          //������� � ����� �� ���� � ��������� ������.
		rurSynonyms.put("AccountOpeningClaimWithClose", new Pair<String, String>("RurAcctToNewAcctWithClose", "�������� ������ � ��������� ����� ��������"));  //�������� ������(������� ���� ��������)
	}

	protected CommissionTBSettingService(GateFactory factory)
	{
		super(factory);
	}

	public boolean isCalcCommissionSupport(GateDocument payment) throws GateException
	{
		return isCalcCommissionSupport(payment.getOffice(), payment);
	}

	public boolean isCalcCommissionSupport(Office office, GateDocument payment) throws GateException
	{
		if (office == null || payment == null || payment.getType() == null || PersonHelper.isGuest())
			return false;

		//���� ������� �� ��������� ������ � ����������-������ �� ����������.
		Long loginId = payment.getInternalOwnerId();

		if (ApplicationConfig.getIt().getApplicationInfo().isMobileApi())
		{
			//������� �������� �������� ������ ������� � ������ 7.0 BUG075877
			VersionNumber version = MobileApiUtil.getApiVersionNumber();
			//���� �� ������� ���������� ������ ��� ������ �������� ����������� ��� ��������� ��������������, ��������� �������� ��� ������ �������-���������.
			if (version == null || version.lt(new VersionNumber(7, 0)))
				return false;
		}
		try
		{
			if (!serviceService.isPersonServices(loginId, "ClientCommissionRequestService"))
				return false;

			Boolean isRubCurrency = isRurCurrency(payment);
			if (isRubCurrency == null)
				return false;

			DetachedCriteria criteria = DetachedCriteria.forClass(CommissionsTBSetting.class);
			criteria.add(Expression.eq("TB", office.getCode().getFields().get("region")));
			criteria.add(Expression.eq("paymentType", getPaymentType(payment)));
			CommissionsTBSetting setting = simpleService.findSingle(criteria);
			return setting != null && (isRubCurrency ? setting.isShowRub() : setting.isShow());
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� �� ������ �������� � ��������� ������/�����.
	 * @param payment - ������.
	 * @return true/false/null - � ��������� �������/� ���������/�� ���������� ������
	 */
	private Boolean isRurCurrency(GateDocument payment) throws GateException
	{
		if(payment instanceof AbstractAccountsTransfer)
		{
			AbstractAccountsTransfer transfer = (AbstractAccountsTransfer) payment;
			Currency fromResourceCurrency = transfer.getChargeOffCurrency();
			return fromResourceCurrency != null ? fromResourceCurrency.getCode().equals("RUB") || fromResourceCurrency.getCode().equals("RUR") : null;
		}
		return null;
	}

	private String getPaymentType(GateDocument payment)
	{
		//��� �������� ����� ���� �������� ��� �� ��� ���� ��������(�������� � �������� � ���������). �� ���������� ��������. BUG068866
		if (payment instanceof AccountOpeningClaim && ((AccountOpeningClaim) payment).isWithClose())
			return "AccountOpeningClaimWithClose";
		return payment.getType().getName();
	}

	/** ��������� ���� �������� ����������, ��� ������� �������� ������� �������� � ���.
	 * @return - ��� ��������� ����������.
	 */
	public static Map<String, Pair<String, String>> getCommissionPaymentsSynonyms()
	{
		return Collections.unmodifiableMap(synonyms);
	}

	/** ��������� ���� �������� ���������� � �������� ������ ��������, ��� ������� �������� ������� �������� � ���.
	 * @return - ��� ��������� ����������.
	 */
	public static Map<String, Pair<String, String>> getCommissionPaymentsRurSynonyms()
	{
		return Collections.unmodifiableMap(rurSynonyms);
	}

	/**
	 * ��������� ������ �������� �������� �������� ��� ��������� �������������.
	 * @param office - �������������
	 * @return - ������ ��������.
	 * @throws BusinessException
	 */
	public static List<CommissionsTBSetting> getDepartmentCommissionSettings(Office office) throws BusinessException
	{
		if (office == null)
			return Collections.emptyList();

		String tb = office.getCode().getFields().get("region");
		DetachedCriteria criteria = DetachedCriteria.forClass(CommissionsTBSetting.class);
		criteria.add(Expression.eq("TB", tb));
		return simpleService.find(criteria);
	}
}
