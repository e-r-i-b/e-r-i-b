package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.promoters.PromoterSessionHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.OfficeCodeReplacer;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.claims.AccountChangeInterestDestinationClaim;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.Report;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.promoters.PromoterContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.xml.transform.TransformerException;

/**
 * Заполняем поля с условиями по вкладу и проверяем, указан ли для выбранного подвида вклада шаблон
 * @author Pankin
 * @ created 01.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeniningInitTermsHandler extends BusinessDocumentHandlerBase
{
	private static final String TARIFF_PATH = "/product/data/options/element[tariffPlanCode = '%s']";
	private static final String TARIFF_PATH_WITH_GROUP = "/product/data/options/element[tariffPlanCode= '%s' and group/groupCode = '%s']";

	private static final String TEMPLATE_NOT_FOUND = "Для данного вида вклада не найден шаблон договора. " +
			"Обратитесь за помощью в службу поддержки банка";
	private static final String EMPTY_LOGON_CARD_MESSAGE = "Уважаемый клиент, вы зарегистрированы по недействующей карте. Для выполнения операции пройдите регистрацию в Сбербанк Онлайн по активной карте.";
	private static final String CURATOR_DEPARTMENT_NOT_FOUND_MSG = "Не найдено подразделение ТБ=%s ОСБ=%s ВСП=%s %s";
	private static final String DEPARTMENTS_IS_DIFFERENT = "ТБ %s не соответствует ТБ клиента %s";
	private static final String ATM_IS_NOT_IN_FILIAL = "Не в подразделении";
	private static final String PROMO_MANAGER_OFFICE_INFO_SERVICE_KEY = "AccountOpeningWithPromoOrManagerVSP";
	private static final String PROMO_MANAGER_OFFICE_INFO_IN_MAPI_SERVICE_KEY = "AccountOpeningWithPromoOrManagerVSPinAPI";
	private static final String ATM_OFFICE_INFO_SERVICE_KEY = "AccountOpeningWithAtmVSP";
	private static final String GATE_PROPERTIES_FILE = "gate.properties";
	private static final String GROUP_PREFIX = "com.rssl.phizic.gate.TB.group";
	private static final String SPLITER = ",";
	private static final String YES = "да";
	private static final String NO = "нет";
	private static final String CHARGE_OF_ACCOUNT = "Cчёт списания";

	protected static final DepositProductService depositProductService = new DepositProductService();
	private static final ExternalResourceService resourceService = new ExternalResourceService();
	private static final DepartmentService departmentService = new DepartmentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountChangeInterestDestinationClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + document.getId() +
					" (Ожидается AccountOpeningClaim или AccountChangeInterestDestinationClaim)");
		}

		try
		{
			//Сохранение информации по промоутеру для отчета
			if (PersonContext.isAvailable())
			{
				AccountChangeInterestDestinationClaim accountChangeInterestDestinationClaim = (AccountChangeInterestDestinationClaim) document;
				String promoterSession = PromoterContext.getShift();
				if (StringHelper.isNotEmpty(promoterSession))
				{
					//Информация по сессии промоутера
					String promoterId = PromoterContext.getPromoterID();
					if (StringHelper.isNotEmpty(promoterId))
					{
						Map<String, Object> promoterSessionInfoMap = PromoterSessionHelper.getPromoterSessionInfoByPromoterID(promoterId);
						String promoterName = (String) promoterSessionInfoMap.get(PromoterSessionHelper.PROMOID_FIELD_NAME);
						accountChangeInterestDestinationClaim.setPromoterId(promoterName);
					}
				}
				//Карта под которой залогинился клиент
				String lastLogonCardNumber = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getLastLogonCardNumber();
				accountChangeInterestDestinationClaim.setLogonCardNumber(lastLogonCardNumber);
			}
		}
		catch (UnsupportedEncodingException e)
		{
			throw new DocumentException(e);
		}
		//Для доп.соглашения больше ничего не проверяем
		if (!(document instanceof AccountOpeningClaim))
			return;

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
		Long accountType = accountOpeningClaim.getAccountType();
		Long accountSubType = accountOpeningClaim.getAccountSubType();

		//Сохранение информации по ТБ, ОСБ и ВСП открываемого вклада
		setOfficeInfo(accountOpeningClaim);

		Long templateId = null;
		try
		{
			templateId = depositProductService.findTemplateIdByType(accountType, accountSubType, 32L);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		if (templateId == null)
		{
			log.error("Для выбранного вклада не указан, либо не найден в справочнике договор. Номер документа: "
					+ accountOpeningClaim.getDocumentNumber() + ", вид вклада: " + accountType
					+ ", подвид вклада: " + accountSubType);
			throw new DocumentLogicException(TEMPLATE_NOT_FOUND);
		}

		String tariffCode = accountOpeningClaim.getTarifPlanCodeType();
		if (ApplicationUtil.isMobileApi() && !TariffPlanHelper.isUnknownTariffPlan(tariffCode))
		{
			if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
			{
				if (hasDepositTariffPlan(accountOpeningClaim, tariffCode))
					accountOpeningClaim.setDepositTariffPlanCode(tariffCode);
			}
			else
			{
				Document description = null;
				try
				{
					DepositProduct depositProduct = depositProductService.findByProductId(accountType);
					description = XmlHelper.parse(depositProduct.getDescription());
				}
				catch (Exception e)
				{
					throw new DocumentException(e);
				}

				if (hasDepositTariffPlan(description.getDocumentElement(), accountOpeningClaim.getAccountGroup(), tariffCode))
					accountOpeningClaim.setDepositTariffPlanCode(tariffCode);
			}
		}

		Long tariffPlanCode;
		if (StringHelper.isEmpty(accountOpeningClaim.getDepositTariffPlanCode()))
		{
			tariffPlanCode = 0L;
		}
		else
		{
			tariffPlanCode = Long.parseLong(accountOpeningClaim.getDepositTariffPlanCode());
		}

		Long tariffTemplateId = null;
		try
		{
			if (tariffPlanCode != 0 && accountType == 61)
			{
				DepositConfig depositConfig = ConfigFactory.getConfig(DepositConfig.class);
				tariffTemplateId = depositProductService.findTemplateIdByTypeAndCode(Long.valueOf(depositConfig.getType4Kind61()), depositConfig.getCode4Kind61());
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		if (tariffTemplateId == null)
		{
			log.warn("Для выбранного вклада не указан, либо не найден в справочнике договор по тарифному плану. Номер документа: "
					+ accountOpeningClaim.getDocumentNumber() + ", вид вклада: " + accountType
					+ ", подвид вклада: " + accountSubType);
		}

		accountOpeningClaim.setTermsTemplateId(templateId.toString());
		if (tariffTemplateId != null)
			accountOpeningClaim.setTariffTermsTemplateId(tariffTemplateId.toString());
		setTerms(accountOpeningClaim);

		//Если выбрали карту для перечисления процентов, то сохраняем её номер
		String cardCode = accountOpeningClaim.getPercentTransferCardSource();
		String cardNum = null;
		if (StringHelper.isNotEmpty(cardCode))
		{
			long cardLinkId = CardLink.getIdFromCode(cardCode);
			try
			{
				CardLink cardLink = resourceService.findLinkById(CardLink.class, cardLinkId);
				cardNum = cardLink != null ? cardLink.getNumber() : null;
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
		}
		accountOpeningClaim.setPercentCardNumber(cardNum);
	}

	/**
	 * Сохранение информации по ТБ, ОСБ и ВСП открываемого вклада
	 * @param accountOpeningClaim -собственно сам вклад
	 */
	private void setOfficeInfo(AccountOpeningClaim accountOpeningClaim) throws DocumentLogicException, DocumentException
	{
		ExtendedCodeImpl officeCode = getOfficeCode(accountOpeningClaim);

		accountOpeningClaim.setAccountTb(officeCode.getRegion());
		accountOpeningClaim.setAccountOsb(officeCode.getBranch());
		accountOpeningClaim.setAccountVsp(officeCode.getOffice());

		updateCuratorInfo(accountOpeningClaim, officeCode);
	}

	/**
	 * Код подразделения
	 * @param accountOpeningClaim - заявка на открытие вклада
	 * @return ExtendedCodeImpl - код подразделения
	 * @throws DocumentLogicException
	 * @throws DocumentException
	 */
	protected ExtendedCodeImpl getOfficeCode(AccountOpeningClaim accountOpeningClaim) throws DocumentLogicException, DocumentException
	{
		ExtendedCodeImpl officeCode = null;
		try
		{
			//Если вклад срочный
			if (accountOpeningClaim.isNeedInitialFee())
			{
				officeCode = new ExtendedCodeImpl(accountOpeningClaim.getChargeOffResourceLink().getOffice().getCode());
			}
			else
			{
				// в случае бессрочного вклада ("Сберегательный") показываем информацию о подразделении, выдавшем карту, по которой осуществлен вход
				BusinessDocumentOwner documentOwner = accountOpeningClaim.getOwner();
				if (documentOwner.isGuest())
					throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
				Login ownerLogin = documentOwner.getLogin();
				Map<String, String> tbReplacements = ConfigFactory.getConfig(BusinessSettingsConfig.class).getTBReplacementsMap();
				String tb = ownerLogin.getLastLogonCardTB();
				if (StringHelper.isEmpty(tb) || StringHelper.isEmpty(ownerLogin.getLastLogonCardOSB()))
				{
					log.error("Не удалось определить отделение карты, по которой клиент вошел в систему.");
					throw new DocumentLogicException(EMPTY_LOGON_CARD_MESSAGE);
				}
				if (tbReplacements.keySet().contains(tb))
					tb = tbReplacements.get(tb);

				officeCode = new ExtendedCodeImpl(StringHelper.removeLeadingZeros(tb),
						StringHelper.removeLeadingZeros(ownerLogin.getLastLogonCardOSB()),
						StringHelper.removeLeadingZeros(ownerLogin.getLastLogonCardVSP()));
			}
			// Уточняем ОСБ
			officeCode.setBranch(ConfigFactory.getConfig(OfficeCodeReplacer.class).replaceCode(officeCode.getRegion(), officeCode.getBranch()));

			return officeCode;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	protected void updateCuratorInfo(AccountOpeningClaim accountOpeningClaim, ExtendedCodeImpl officeCode) throws DocumentException
	{
		try
		{
			Report report = new Report();
			if (isOpenOnManagerBalanceAvailable())
			{
				//Сначала проверка по промоутеру
				updateCuratorWithPromoterInfo(accountOpeningClaim, officeCode, report);
				//Потом проверка по КМ
				updateCuratorWithCMInfo(accountOpeningClaim, officeCode, report);
			}
			else
			{
				//Если нет права на открытие вклада на балансе ВСП промоутера или КМ, то принудительно чистим информацию по ВСП куратора
				clearCuratorData(accountOpeningClaim);
			}
			//Для УС свой алгоритм проверки
			updateCuratorWithATMInfo(accountOpeningClaim, officeCode, report);
			accountOpeningClaim.setClaimErrorMsg(report.getErrorMsg());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new DocumentException(e);
		}
		catch (TransformerException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Проверка доступности права на открытие вклада на балансе ВСП промоутера или КМ
	 * @return признак доступности
	 */
	private boolean isOpenOnManagerBalanceAvailable()
	{
		return PersonContext.isAvailable()
				&& (ApplicationUtil.isNotApi() && PermissionUtil.impliesService(PROMO_MANAGER_OFFICE_INFO_SERVICE_KEY)
				|| (ApplicationUtil.isMobileApi() && PermissionUtil.impliesService(PROMO_MANAGER_OFFICE_INFO_IN_MAPI_SERVICE_KEY))
				|| (ApplicationUtil.isATMApi() && PermissionUtil.impliesService(ATM_OFFICE_INFO_SERVICE_KEY)));
	}

	private void updateCuratorWithCMInfo(AccountOpeningClaim accountOpeningClaim, ExtendedCodeImpl officeCode, Report report) throws BusinessException, TransformerException, DocumentException
	{
		//Проверка офиса по клиентскому менеджеру (КМ) и корректируем инфу по офису, если нужно
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		String managerTB = person.getManagerTB();
		String managerOSB = person.getManagerOSB();
		String managerVSP = person.getManagerVSP();

		//Если есть клиентский менеджер
		if (person.getManagerId() != null)
		{
			report.setId("КМ");

			Map<String, String> tbReplacements = ConfigFactory.getConfig(BusinessSettingsConfig.class).getTBReplacementsMap();
			if (tbReplacements.keySet().contains(managerTB))
				managerTB = tbReplacements.get(managerTB);

			ExtendedCodeImpl managerOfficeCode = new ExtendedCodeImpl(StringHelper.removeLeadingZeros(managerTB),
					StringHelper.removeLeadingZeros(managerOSB),
					StringHelper.removeLeadingZeros(managerVSP));
			managerOfficeCode.setBranch(ConfigFactory.getConfig(OfficeCodeReplacer.class).replaceCode(managerOfficeCode.getRegion(), managerOfficeCode.getBranch()));

			//Группа ТБ менеджера и карты/счета
			String productTB = getProductTB(officeCode);
			boolean identicalTbGroup = isIdenticalGroup(managerTB, productTB);
			//- Если ТБ КМ не отличается от ТБ счета(карты) списания или ТБ карты входа клиента (для бессрочных вкладов)
			//- или они в одной группе
			if (productTB.equals(managerOfficeCode.getRegion()) || identicalTbGroup)
			{
				// ищет значения ВСП и ОСБ в справочнике подразделений ЕРИБ.
				Department department = departmentService.findByCode(managerOfficeCode);
				if (department != null && department.isActive())
				{   //сценарий 5
					accountOpeningClaim.setCuratorType("КМ");
					accountOpeningClaim.setCuratorId(person.getManagerId());
					accountOpeningClaim.setCuratorTb(managerOfficeCode.getRegion());
					accountOpeningClaim.setCuratorOsb(managerOfficeCode.getBranch());
					accountOpeningClaim.setCuratorVsp(managerOfficeCode.getOffice());

					report.setOp(YES);
					report.setCuratorId(person.getManagerId());
					report.setTb(managerOfficeCode.getRegion());
					report.setOsb(managerOfficeCode.getBranch());
					report.setVsp(managerOfficeCode.getOffice());
					report.setError(report.isError());
					report.setDataValid(true);
					report.setCardPrimaryAccount(getFromResourceAccountNumber(accountOpeningClaim));

					//Если были ошибки при определении ТБ, ОСБ, ВСП промоутера, то благополучно чистим их, т.к. КМ имеет приоритет
					if (report.isError())
					{
						report.setOpenIn(null);
						report.setDescription(null);
						report.setError(false);
					}
					return;
				}
				else
				{   // сценарий 6
					String errorMsg = String.format(CURATOR_DEPARTMENT_NOT_FOUND_MSG, managerOfficeCode.getRegion(),
							managerOfficeCode.getBranch(), managerOfficeCode.getOffice(), "клиентского менеджера");
					//если не было ошибок при определении ВСП промоутера
					if (!report.isError() && report.getOp() != null && report.getOp().equals(YES))
						report.setOpenIn("Промоутер");
					report.setOp(NO);
					report.setCuratorId(person.getManagerId());
					report.setTb(managerOfficeCode.getRegion());
					report.setOsb(managerOfficeCode.getBranch());
					report.setVsp(managerOfficeCode.getOffice());
					report.setDescription(errorMsg);
					report.setError(true);
					report.setDataValid(false);
					report.setCardPrimaryAccount(getFromResourceAccountNumber(accountOpeningClaim));
					log.info(errorMsg);
					return;
				}
			}
			else
			{   // сценарий 7
				String errorMsg = String.format(DEPARTMENTS_IS_DIFFERENT, managerOfficeCode.getRegion(), productTB);
				//если не было ошибок при определении ВСП промоутера
				if (!report.isError() && report.getOp() != null && report.getOp().equals(YES))
					report.setOpenIn("Промоутер");
				report.setOp(NO);
				report.setCuratorId(person.getManagerId());
				report.setTb(managerOfficeCode.getRegion());
				report.setOsb(managerOfficeCode.getBranch());
				report.setVsp(managerOfficeCode.getOffice());
				report.setDescription(errorMsg);
				report.setError(true);
				report.setDataValid(false);
				report.setCardPrimaryAccount(getFromResourceAccountNumber(accountOpeningClaim));
				log.info(errorMsg);
				return;
			}
		}
	}

	private void updateCuratorWithPromoterInfo(AccountOpeningClaim accountOpeningClaim, ExtendedCodeImpl officeCode, Report report) throws BusinessException, UnsupportedEncodingException
	{
		try
		{
			clearCuratorData(accountOpeningClaim);
			//Проверка офиса по клиентскому менеджеру (КМ) и корректируем инфу по офису, если нужно
			//Если запущена сессия промоутера, проверка офиса по промоутеру и корректируем инфу по офису, если нужно
			String promoterSession = PromoterContext.getShift();
			if (StringHelper.isNotEmpty(promoterSession))
			{
				//Информация по сессии промоутера
				String promoterId = PromoterContext.getPromoterID();
				if (StringHelper.isNotEmpty(promoterId))
				{
					Map<String, Object> promoterSessionInfoMap = PromoterSessionHelper.getPromoterSessionInfoByPromoterID(promoterId);

					if (CollectionUtils.isNotEmpty(promoterSessionInfoMap.entrySet()))
					{
						String tb = (String) promoterSessionInfoMap.get(PromoterSessionHelper.TB_FIELD_NAME);
						String osb = (String) promoterSessionInfoMap.get(PromoterSessionHelper.OSB_FIELD_NAME);
						String vsp = (String) promoterSessionInfoMap.get(PromoterSessionHelper.VSP_FIELD_NAME);
						String promoterFIO = (String) promoterSessionInfoMap.get(PromoterSessionHelper.PROMOID_FIELD_NAME);

						ExtendedCodeImpl promoterOfficeCode = new ExtendedCodeImpl(StringHelper.removeLeadingZeros(tb),
								StringHelper.removeLeadingZeros(osb), StringHelper.removeLeadingZeros(vsp));
						promoterOfficeCode.setBranch(ConfigFactory.getConfig(OfficeCodeReplacer.class).replaceCode(promoterOfficeCode.getRegion(), promoterOfficeCode.getBranch()));

						//Группа ТБ промоутера и карты/счета
						String promoterTB = promoterOfficeCode.getRegion();
						String productTB = getProductTB(officeCode);
						//Находятся ли ТБ-ки в одной группе
						boolean identicalTbGroup = isIdenticalGroup(promoterTB, productTB);
						//-Если ТБ промоутера не отличается от ТБ счета(карты) списания или ТБ карты входа клиента (для бессрочных вкладов)
						//-или они в одной группе
						if (productTB.equals(promoterTB) || identicalTbGroup)
						{
							//Поиск подразделения в справочнике
							Department department = departmentService.findByCode(promoterOfficeCode);
							if (department == null || !department.isActive())
							{
								String errorMsg = String.format(CURATOR_DEPARTMENT_NOT_FOUND_MSG, promoterOfficeCode.getRegion(),
										promoterOfficeCode.getBranch(), promoterOfficeCode.getOffice(), "промоутера");

								if (!report.isError())
								{
									report.setId("Промоутер");
									report.setOp(NO);
									report.setCuratorId(promoterFIO);
									report.setTb(promoterOfficeCode.getRegion());
									report.setOsb(promoterOfficeCode.getBranch());
									report.setVsp(promoterOfficeCode.getOffice());
									report.setDescription(errorMsg);
									report.setError(true);
									report.setDataValid(false);
									report.setCardPrimaryAccount(getFromResourceAccountNumber(accountOpeningClaim));
									report.setOpenIn(CHARGE_OF_ACCOUNT);
								}
								log.info(errorMsg);
								return;
							}

							accountOpeningClaim.setCuratorType("Промоутер");
							accountOpeningClaim.setCuratorId(promoterFIO);
							accountOpeningClaim.setCuratorTb(tb);
							accountOpeningClaim.setCuratorOsb(StringHelper.removeLeadingZeros(osb));
							accountOpeningClaim.setCuratorVsp(StringHelper.removeLeadingZeros(vsp));

							//успешная отправка
							if (!report.isError())
							{
								report.setId("Промоутер");
								report.setOp(YES);
								report.setCuratorId(promoterFIO);
								report.setTb(promoterOfficeCode.getRegion());
								report.setOsb(promoterOfficeCode.getBranch());
								report.setVsp(promoterOfficeCode.getOffice());
								report.setError(false);
								report.setOpenIn(null);
							}
							else
							{ //заполняется только в случае ошибки
								report.setOpenIn("Промоутер");
							}
							report.setDataValid(true);
							report.setCardPrimaryAccount(getFromResourceAccountNumber(accountOpeningClaim));
							return;
						}
						else
						{
							String errorMsg = String.format(DEPARTMENTS_IS_DIFFERENT, tb, productTB);

							if (!report.isError())
							{
								report.setId("Промоутер");
								report.setOp(NO);
								report.setCuratorId(promoterFIO);
								report.setTb(promoterOfficeCode.getRegion());
								report.setOsb(promoterOfficeCode.getBranch());
								report.setVsp(promoterOfficeCode.getOffice());
								report.setDescription(errorMsg);
								report.setError(true);
								report.setDataValid(false);
								report.setCardPrimaryAccount(getFromResourceAccountNumber(accountOpeningClaim));
								report.setOpenIn(CHARGE_OF_ACCOUNT);
							}
							log.info(errorMsg);
							return;
						}
					}
				}
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	protected void updateCuratorWithATMInfo(AccountOpeningClaim accountOpeningClaim, ExtendedCodeImpl officeCode, Report report) throws DocumentException
	{
		try
		{
			boolean isOpenOnATMBalanceAvailable = PersonContext.isAvailable() &&
					ApplicationUtil.isATMApi() && PermissionUtil.impliesService(ATM_OFFICE_INFO_SERVICE_KEY);

			if (isOpenOnATMBalanceAvailable && !report.isDataValid())
			{
				clearCuratorData(accountOpeningClaim);

				String atmTB = StringHelper.removeLeadingZeros(StringHelper.getEmptyIfNull(accountOpeningClaim.getAtmTB()));
				String atmOSB = StringHelper.removeLeadingZeros(StringHelper.getEmptyIfNull(accountOpeningClaim.getAtmOSB()));
				String atmVSP = StringHelper.removeLeadingZeros(StringHelper.getEmptyIfNull(accountOpeningClaim.getAtmVSP()));
				String atmPlace = StringHelper.getEmptyIfNull(accountOpeningClaim.getAtmPlace()).trim();

				ExtendedCodeImpl atmCode = new ExtendedCodeImpl(atmTB, atmOSB, atmVSP);
				atmCode.setBranch(ConfigFactory.getConfig(OfficeCodeReplacer.class).replaceCode(atmCode.getRegion(), atmCode.getBranch()));

				//  УС установлен в подразделении,
				if (StringHelper.isNotEmpty(atmPlace) && atmPlace.equalsIgnoreCase("FILIAL"))
				{
					String productTB = getProductTB(officeCode);
					boolean identicalTbGroup = isIdenticalGroup(atmTB, productTB);

					//  Определяет, что ТБ УС не отличается от ТБ счета списания или ТБ карты входа.
					if (TBSynonymsDictionary.isSameTB(productTB, atmTB) || identicalTbGroup)
					{
						Department department = departmentService.findByCode(atmCode);
						// ищет значения ВСП и ОСБ в справочнике подразделений ЕРИБ.
						if (department != null && department.isActive())
						{   // сценарий 1
							accountOpeningClaim.setCuratorType("УС");
							accountOpeningClaim.setCuratorId(accountOpeningClaim.getCodeATM());
							accountOpeningClaim.setCuratorTb(atmCode.getRegion());
							accountOpeningClaim.setCuratorOsb(atmCode.getBranch());
							accountOpeningClaim.setCuratorVsp(atmCode.getOffice());

							if (!report.isError())
							{
								report.setId("УС");
								report.setOp(YES);
								report.setCuratorId(accountOpeningClaim.getCodeATM());
								report.setTb(atmCode.getRegion());
								report.setOsb(atmCode.getBranch());
								report.setVsp(atmCode.getOffice());
								report.setError(false);
								report.setOpenIn(null);
							}
							else
							{ //заполняется только в случае ошибки
								report.setOpenIn("УС");
							}
							report.setDataValid(true);
							report.setCardPrimaryAccount(getFromResourceAccountNumber(accountOpeningClaim));
							return;
						}
						else
						{   // сценарий 3
							String errorMsg = String.format(CURATOR_DEPARTMENT_NOT_FOUND_MSG, atmCode.getRegion(),
									atmCode.getBranch(), atmCode.getOffice(), "устройства самообслуживания");

							if (!report.isError())
							{
								report.setId("УС");
								report.setOp(NO);
								report.setCuratorId(accountOpeningClaim.getCodeATM());
								report.setTb(atmCode.getRegion());
								report.setOsb(atmCode.getBranch());
								report.setVsp(atmCode.getOffice());
								report.setDescription(errorMsg);
								report.setError(true);
								report.setDataValid(false);
							}
							report.setOpenIn(CHARGE_OF_ACCOUNT);
							report.setCardPrimaryAccount(getFromResourceAccountNumber(accountOpeningClaim));
							log.info(errorMsg);
							return;
						}
					}
					else
					{   // сценарий 4
						String errorMsg = String.format(DEPARTMENTS_IS_DIFFERENT, atmCode.getRegion(), productTB);

						if (!report.isError())
						{
							report.setId("УС");
							report.setOp(NO);
							report.setCuratorId(accountOpeningClaim.getCodeATM());
							report.setTb(atmCode.getRegion());
							report.setOsb(atmCode.getBranch());
							report.setVsp(atmCode.getOffice());
							report.setDescription(errorMsg);
							report.setError(true);
							report.setDataValid(false);
						}
						report.setOpenIn(CHARGE_OF_ACCOUNT);
						report.setCardPrimaryAccount(getFromResourceAccountNumber(accountOpeningClaim));
						log.info(errorMsg);
						return;
					}
				}
				else
				{   // сценарий 2

					if (!report.isError())
					{
						report.setId("УС");
						report.setOp(NO);
						report.setCuratorId(accountOpeningClaim.getCodeATM());
						report.setTb(atmCode.getRegion());
						report.setOsb(atmCode.getBranch());
						report.setVsp(atmCode.getOffice());
						report.setDescription(ATM_IS_NOT_IN_FILIAL);
						report.setError(true);
						report.setDataValid(false);
					}
					report.setOpenIn(CHARGE_OF_ACCOUNT);
					report.setCardPrimaryAccount(getFromResourceAccountNumber(accountOpeningClaim));
					log.info(ATM_IS_NOT_IN_FILIAL);
					return;
				}
			}
			else
			{
				//если нельзя открыть на ВСП УС, присутствует ошибка, не открыт ранее
				if (report.isError() && StringHelper.isEmpty(report.getOpenIn()))
				{
					report.setOpenIn(CHARGE_OF_ACCOUNT);
				}
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private String getProductTB(ExtendedCodeImpl officeCode)
	{
		String productTB = officeCode.getRegion();
		String mainProductTB = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(productTB);
		return StringHelper.isNotEmpty(mainProductTB) ? mainProductTB : productTB;
	}

	private void clearCuratorData(AccountOpeningClaim accountOpeningClaim)
	{
		accountOpeningClaim.setClaimErrorMsg(null);
		accountOpeningClaim.setCuratorType(null);
		accountOpeningClaim.setCuratorId(null);
		accountOpeningClaim.setCuratorTb(null);
		accountOpeningClaim.setCuratorOsb(null);
		accountOpeningClaim.setCuratorVsp(null);
	}

	/**
	 * Находятся ли ТБ-ки в одной группе
	 * @param curatorTB ТБ промоутера/КМ
	 * @param productTB ТБ открываемого счета
	 * @return признак нахождения ТБ в одной группе
	 */
	private boolean isIdenticalGroup(String curatorTB, String productTB)
	{
		Properties groups = ConfigFactory.getReaderByFileName(GATE_PROPERTIES_FILE).getProperties(GROUP_PREFIX);

		if (CollectionUtils.isEmpty(groups.entrySet()))
			return false;

		for (Map.Entry entry : groups.entrySet())
		{
			StringBuffer group = new StringBuffer();
			//добавляем лидирующий ноль на случай, если он упущен
			for (String tb : ((String) entry.getValue()).split(SPLITER))
			{
				if (tb.length() == 1)
					tb = StringHelper.appendLeadingZeros(tb, 2);
				group.append(tb).append(SPLITER);
			}

			//если оба тб из одной группы
			if (group.indexOf(curatorTB) > -1 && group.indexOf(productTB) > -1)
				return true;
		}
		return false;
	}

	/**
	 * Заполнить информацию о приходных/расходных операциях по вкладу.
	 * Поиск осуществляется в описании подвида
	 * @param claim заяка
	 */
	private void setTerms(AccountOpeningClaim claim) throws DocumentException
	{
		try
		{
			Long accountGroup = StringHelper.isEmpty(claim.getAccountGroup()) ? 0L : Long.valueOf(claim.getAccountGroup());
			DepositsTDOG tdog = depositProductService.getDepositAdditionalInfo(claim.getAccountType(), -22L == accountGroup ? 0L : accountGroup,
					claim.isNeedInitialFee() ? null : claim.getAccountSubType());
			if (tdog == null)
				return;

			if (StringHelper.isNotEmpty(tdog.getIncomingTransactions()))
				claim.setTermsPosition(AccountOpeningClaim.INCOMING_TRANSACTIONS, tdog.getIncomingTransactions());
			if (StringHelper.isNotEmpty(tdog.getAdditionalFeePeriod()))
				claim.setTermsPosition(AccountOpeningClaim.FREQUENCY_ADD, tdog.getAdditionalFeePeriod());
			if (StringHelper.isNotEmpty(tdog.getDebitTransactions()))
				claim.setTermsPosition(AccountOpeningClaim.DEBIT_TRANSACTIONS, tdog.getDebitTransactions());
			if (StringHelper.isNotEmpty(tdog.getFrequencyPercent()))
				claim.setTermsPosition(AccountOpeningClaim.FREQUENCY_PERCENT, tdog.getFrequencyPercent());
			if (StringHelper.isNotEmpty(tdog.getPercentOrder()))
				claim.setTermsPosition(AccountOpeningClaim.PERCENT_ORDER, tdog.getPercentOrder());
			if (StringHelper.isNotEmpty(tdog.getIncomeOrder()))
				claim.setTermsPosition(AccountOpeningClaim.INCOME_ORDER, tdog.getIncomeOrder());
			if (StringHelper.isNotEmpty(tdog.getRenewals()))
				claim.setTermsPosition(AccountOpeningClaim.RENEWALS, tdog.getRenewals());
			if (StringHelper.isNotEmpty(tdog.getRate()))
				claim.setTermsPosition(AccountOpeningClaim.PERCENT, tdog.getRate());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Возвращает номер счета списания
	 * @param claim заявка на открытие вклада
	 * @return номер счета
	 * @throws DocumentException
	 */
	private String getFromResourceAccountNumber(AccountOpeningClaim claim) throws DocumentException
	{
		PaymentAbilityERL fromResourceLink = claim.getChargeOffResourceLink();
		if (fromResourceLink == null)
			return null;
		ResourceType fromResourceType = fromResourceLink.getResourceType();
		if (fromResourceType == null)
			return null;
		//Для открытия с карт возвращяем номер счета карты
		if (fromResourceType.equals(ResourceType.CARD))
			return ((CardLink) fromResourceLink).getCardPrimaryAccount();
		return null;
	}

	/**
	 * Проверяет, есть ли для открываемого вклада льготные ставки для ТП клиента
	 * @param description - описание вклада
	 * @param accountGroup - группа открываемого вкладного продукта
	 * @param tariffCode - ТП
	 * @return true, если найдены льготные ставки
	 * @throws DocumentException
	 */
	private boolean hasDepositTariffPlan(Element description, String accountGroup, String tariffCode) throws DocumentException
	{
		if (StringHelper.isEmpty(tariffCode))
			return false;
		try
		{
			String elementPath = "";
			if (StringHelper.isNotEmpty((accountGroup)))
				elementPath = String.format(TARIFF_PATH_WITH_GROUP, tariffCode, accountGroup);
			else
				elementPath = String.format(TARIFF_PATH, tariffCode);

			List<String> elementsWithTariffPlan = XmlHelper.getSimpleListElementValue(description, elementPath);
			return !elementsWithTariffPlan.isEmpty();
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Проверяет, есть ли для открываемого вклада льготные ставки для ТП клиента
	 * @param tariffCode - ТП
	 * @return true, если найдены льготные ставки
	 * @throws DocumentException
	 */
	private boolean hasDepositTariffPlan(AccountOpeningClaim accountOpeningClaim, String tariffCode) throws DocumentException
	{
		if (StringHelper.isEmpty(tariffCode))
			return false;
		try
		{
			return depositProductService.hasDepositTariffPlan(accountOpeningClaim.getAccountType(), accountOpeningClaim.getAccountSubType(), tariffCode);
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}
}
