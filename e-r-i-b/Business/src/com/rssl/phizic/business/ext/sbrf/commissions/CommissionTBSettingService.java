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
 * Сервис для работы с настройками отображения коммисиий для платежей в разрезе ТБ.
 */
public class CommissionTBSettingService extends AbstractService implements BackRefCommissionTBSettingService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final ServiceService serviceService = new ServiceService();
	private static final Map<String, Pair<String, String>> synonyms = new HashMap<String, Pair<String, String>>();
	private static final Map<String, Pair<String, String>> rurSynonyms = new HashMap<String, Pair<String, String>>();

	static
	{
		//для ресурсов списания отличных от рублевых.
		synonyms.put(ClientAccountsTransfer.class.getName(), new Pair<String, String>("AcctToAcct", "Перевод вклад - вклад"));  //вклад-вклад
		synonyms.put(AccountToIMATransfer.class.getName(), new Pair<String, String>("AcctToIMA", "Перевод вклад - ОМС"));       //вклад-ОМС
		synonyms.put(AccountToCardTransfer.class.getName(), new Pair<String, String>("AcctToCard", "Перевод вклад - карта"));    //вклад-карта
		synonyms.put(AccountOpeningClaim.class.getName(), new Pair<String, String>("AcctToNewAcct", "Открытие вклада"));         //открытие вклада
		synonyms.put(AccountClosingPaymentToAccount.class.getName(), new Pair<String, String>("AcctCloseToAcct", "Закрытие вклада с переводом на счет"));    //закрытие вклада с переводом на счет
		synonyms.put(AccountClosingPaymentToCard.class.getName(), new Pair<String, String>("AcctCloseToCard", "Закрытие вклада с переводом на карту"));       //закрытие вклада с переводом на карту
		synonyms.put(CardRUSPayment.class.getName(), new Pair<String, String>("CardToOtherBank", "Перевод с карты на счет в другом банке"));                  //перевод с карты на счет в др банке.
		synonyms.put(CardIntraBankPayment.class.getName(), new Pair<String, String>("CardToOurBank", "Перевод с карты на счет в Сбербанке России"));          //перевод с карты на счет в Сбербанке России.
		synonyms.put(IMAOpeningClaim.class.getName(), new Pair<String, String>("IMAOpening", "Открытие ОМС с перечислением средств со вклада"));          //перевод с карты на счет в Сбербанке России.
		//BUG068866
		synonyms.put("AccountOpeningClaimWithClose", new Pair<String, String>("AcctToNewAcctWithClose", "Открытие вклада с закрытием счета списания"));  //открытие вклада(закрыть счет списания)
		// для рублевых ресурсов списания.
		rurSynonyms.put(ClientAccountsTransfer.class.getName(), new Pair<String, String>("RurAcctToAcct", "Перевод вклад - вклад"));  //вклад-вклад
		rurSynonyms.put(AccountToIMATransfer.class.getName(), new Pair<String, String>("RurAcctToIMA", "Перевод вклад - ОМС"));       //вклад-ОМС
		rurSynonyms.put(AccountToCardTransfer.class.getName(), new Pair<String, String>("RurAcctToCard", "Перевод вклад - карта"));    //вклад-карта
		rurSynonyms.put(AccountOpeningClaim.class.getName(), new Pair<String, String>("RurAcctToNewAcct", "Открытие вклада"));         //открытие вклада
		rurSynonyms.put(AccountClosingPaymentToAccount.class.getName(), new Pair<String, String>("RurAcctCloseToAcct", "Закрытие вклада с переводом на счет"));    //закрытие вклада с переводом на счет
		rurSynonyms.put(AccountClosingPaymentToCard.class.getName(), new Pair<String, String>("RurAcctCloseToCard", "Закрытие вклада с переводом на карту"));       //закрытие вклада с переводом на карту
		rurSynonyms.put(CardRUSPayment.class.getName(), new Pair<String, String>("RurCardToOtherBank", "Перевод с карты на счет в другом банке"));                  //перевод с карты на счет в др банке.
		rurSynonyms.put(CardIntraBankPayment.class.getName(), new Pair<String, String>("RurCardToOurBank", "Перевод с карты на счет в Сбербанке России"));          //перевод с карты на счет в Сбербанке России.
		rurSynonyms.put(IMAOpeningClaim.class.getName(), new Pair<String, String>("RurIMAOpening", "Открытие ОМС с перечислением средств со вклада"));          //перевод с карты на счет в Сбербанке России.
		rurSynonyms.put("AccountOpeningClaimWithClose", new Pair<String, String>("RurAcctToNewAcctWithClose", "Открытие вклада с закрытием счета списания"));  //открытие вклада(закрыть счет списания)
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

		//если клиенту не подключен запрос с комиссиями-значит не отображаем.
		Long loginId = payment.getInternalOwnerId();

		if (ApplicationConfig.getIt().getApplicationInfo().isMobileApi())
		{
			//рассчет комиссий доступен только начиная с версии 7.0 BUG075877
			VersionNumber version = MobileApiUtil.getApiVersionNumber();
			//если не удается определить версию АПИ значит проверка выполняется вне контекста аутентификации, получение комиссий без ведома клиента-запрещено.
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
	 * Является ли платеж платежом с рублевого вклада/карты.
	 * @param payment - платеж.
	 * @return true/false/null - с рублевого ресурса/с валютного/не определена валюта
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
		//для открытия счета один гейтовый тип на два вида операций(открытие и открытие с закрытием). их необходимо рзделять. BUG068866
		if (payment instanceof AccountOpeningClaim && ((AccountOpeningClaim) payment).isWithClose())
			return "AccountOpeningClaimWithClose";
		return payment.getType().getName();
	}

	/** Получение мапа синоимов документов, для которых доступен рассчет комиссии в ЦОД.
	 * @return - мап синонимов документов.
	 */
	public static Map<String, Pair<String, String>> getCommissionPaymentsSynonyms()
	{
		return Collections.unmodifiableMap(synonyms);
	}

	/** Получение мапа синоимов документов с рублевым счетом списания, для которых доступен рассчет комиссии в ЦОД.
	 * @return - мап синонимов документов.
	 */
	public static Map<String, Pair<String, String>> getCommissionPaymentsRurSynonyms()
	{
		return Collections.unmodifiableMap(rurSynonyms);
	}

	/**
	 * Получение списка настроек рассчета комиссий для заданного подразделения.
	 * @param office - подразделение
	 * @return - список настроек.
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
