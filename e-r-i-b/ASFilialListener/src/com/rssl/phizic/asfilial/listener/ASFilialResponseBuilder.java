package com.rssl.phizic.asfilial.listener;

import com.rssl.phizic.asfilial.listener.generated.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.ErmbProfileStatistic;
import com.rssl.phizic.business.ermb.card.PrimaryCardResolver;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.DaysOfWeek;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.axis.types.Time;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * User: Moshenko
 * Date: 24.04.2013
 * Time: 15:11:39
 * �����������  ������� ��� �� "������ ��"
 */
public class ASFilialResponseBuilder
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String CARD = "card";
	private static final String ACCOUNT = "account";
	private static final String LOAN = "loan";
	private static final String FULL = "full";
	private static final String SAVING = "saving";

	private final ASFilialServiceHelper asFilialServiceHelper = new ASFilialServiceHelper();
	private final ErmbProfileBusinessService ermbProfileBusinessService = new ErmbProfileBusinessService();

	/**
	 *
	 * @param ermbProfil ������� ����
	 * @param clientInd ����������������� ������ �������
	 * @param clientOldInd ������ ����������������� ������ �������
	 * @param isNew ������� ���� ��� ��� ������ ����� �������
	 * @return ����� ������� queryProfile
	 */
	public ResponseType buildQueryProfileResponse(ErmbProfileImpl ermbProfil, IdentityType clientInd, IdentityType clientOldInd, boolean isNew) throws BusinessLogicException, BusinessException {
		ResponseType responseType = new ResponseType();
		//����������������� ������ �������
		responseType.setClientIdentity(clientInd);
		//��������� ����������� ������ ��� ������������ ������������
		clientInd.setRegionId(StringHelper.appendLeadingZeros(clientInd.getRegionId(), 3));
		//���������� ����������������� ������ �������
		if (clientOldInd != null)
		{
			clientOldInd.setRegionId(clientInd.getRegionId());
			responseType.setClientOldIdentity(clientOldInd);
		}
		//��� �������� �������
		Set<String> phones = ermbProfil.getPhoneNumbers();
		PhoneNumberType[] clientPhones = buildPhoneArray(phones);

		responseType.setClientPhones(clientPhones);
		//��� �������� �������
		List<ResourcesType> allRes = new ArrayList<ResourcesType>();
		//��������� ��� ������ � �������� ���������
		List<PayResourceType> potentialPaymentCards = new ArrayList<PayResourceType>();
		//��������, ��������� � ��������-����������
		List<ResourcesType> internetClientServiceRes = new ArrayList<ResourcesType>();
		//��������, ��������� � ��������� ����������
		List<ResourcesType> mobileClientServiceRes = new ArrayList<ResourcesType>();
		//��������, ��������� � ����������� ����������������
		List<ResourcesType> ATMClientServiceRes = new ArrayList<ResourcesType>();
		//MobileBankService. �������� �������, �� ������� ������ ������������ ����������
		List<ResourcesType> informRes = new ArrayList<ResourcesType>();
        //��������, ��������� � ���-������.
        List<ResourcesType> visibleRes = new ArrayList<ResourcesType>();

		Map<String, ErmbProductLink> links = asFilialServiceHelper.getLinks(ermbProfil);
		for (ErmbProductLink link : links.values())
		{
			ResourcesType res = new ResourcesType();
			String nubmer = link.getNumber();
			if (link instanceof AccountLink)
			{
				res.setType(ACCOUNT);
				res.setAccount(nubmer);

			}
			else if (link instanceof CardLink)
			{
				res.setType(CARD);
                CardType cardType = new CardType();
                cardType.setNumber(nubmer);
                Card card = (Card) link.getValue();
                AdditionalCardType addCardInfo = card.getAdditionalCardType();
                if (addCardInfo != null)
                {
                    String addCardInfoStr = addCardInfo.toValue();
                    //<AdditionalCardInfo> ��������� �������������� �����  ��������, ���� ����� ��������������	[0-1]
                    if (!StringHelper.isEmpty(addCardInfoStr))
                    {
                        AdditionalCardInfoType additionalCardInfoType = new AdditionalCardInfoType();
                        additionalCardInfoType.setType(addCardInfoStr);
                        cardType.setAdditionalCardInfo(additionalCardInfoType);
                    }
                }
				res.setCard(cardType);
			}
			else if (link instanceof LoanLink)
			{
				res.setType(LOAN);
				res.setCredit(nubmer);
			}
            res.setName(link.getName());
			if (link.getShowInSystem())
				internetClientServiceRes.add(res);
			if (link.getShowInMobile())
				mobileClientServiceRes.add(res);
			if (link.isShowInATM())
				ATMClientServiceRes.add(res);
			if (link.getErmbNotification())
				informRes.add(res);
            if (link.getShowInSms())
                visibleRes.add(res);
			allRes.add(res);
		}
		List<CardLink> potentialPaymentCardLinks = ermbProfil.getErmbAvailablePaymentCards();
		for (CardLink cardLink : potentialPaymentCardLinks)
		{
			String name = cardLink.getName();
			String number = cardLink.getNumber();

			PayResourceCardType payResourceCardType = new PayResourceCardType(number);
			PayResourceType payResourceType = new PayResourceType(name, payResourceCardType);

			potentialPaymentCards.add(payResourceType);
		}

		responseType.setClientResources(allRes.toArray(new ResourcesType[allRes.size()]));
		if (ConfigFactory.getConfig(ASFilialConfig.class).isUseV19spec())
		{
			responseType.setPayResources(potentialPaymentCards.toArray(new PayResourceType[potentialPaymentCards.size()]));
		}
		responseType.setInternetClientService(internetClientServiceRes.toArray(new ResourcesType[internetClientServiceRes.size()]));
		responseType.setMobileClientService(mobileClientServiceRes.toArray(new ResourcesType[mobileClientServiceRes.size()]));
		responseType.setATMClientService(ATMClientServiceRes.toArray(new ResourcesType[ATMClientServiceRes.size()]));

		//������ �� ������ ���������� ����
		MobileBankServiceQueryProfileRsType mobileBankService = new MobileBankServiceQueryProfileRsType();

		if (!ermbProfil.isServiceStatus())
		{
			mobileBankService.setRegistrationStatus(false);
			//��� �� ������������ ������ ���������� ��������� �������� ��. �����
			mobileBankService.setTariffId(SAVING);
			mobileBankService.setQuickServices(false);
			mobileBankService.setInformNewResource(true);
			mobileBankService.setSuppressAdvertising(true);
		}
		else
		{   //������ ������� ����������
			mobileBankService.setRegistrationStatus(true);
			//��������� ������
			if (ermbProfil.isClientBlocked() && ermbProfil.isServiceStatus())
				mobileBankService.setServiceStatus("blocked");
			else if (ermbProfil.isPaymentBlocked() &&  ermbProfil.isServiceStatus())
				mobileBankService.setServiceStatus("nonpayed");
			else if (ermbProfil.isServiceStatus() && !ermbProfil.isPaymentBlocked() && !ermbProfil.isClientBlocked())
				mobileBankService.setServiceStatus("active");
			else
				throw new BusinessException("������ � ����������� ������� �������");
			//���� ������� ����������� ������
			mobileBankService.setStartOfService(DateHelper.toDate(ermbProfil.getConnectionDate()));
			//���� ��������� �������������� ������
			mobileBankService.setEndOfService(DateHelper.toDate(ermbProfil.getEndServiceDate()));
			//�������� ���� ������
			String tariffStr = ermbProfil.getTarif().getCode();
			if (tariffStr != null)
				mobileBankService.setTariffId(tariffStr);
			else
				throw new BusinessException("������ � ����������� ������");
			//������� ��������� ��� ������� ����������� ������ ������ �������� � ��������� �� ������ ��������
			mobileBankService.setQuickServices(ermbProfil.getFastServiceAvailable());
			//�������� �������, �� �������������� ������ ������������ ����������
			String mainPhoneNumber = ermbProfil.getMainPhoneNumber();
			if (mainPhoneNumber != null)
				mobileBankService.setActivePhone(new PhoneNumberType(mainPhoneNumber,null));
            //��������, ��������� � ���-������.
            mobileBankService.setVisibleResources(visibleRes.toArray(new ResourcesType[informRes.size()]));
			//�������� �������, �� ������� ������ ������������ ����������
			mobileBankService.setInformResources((ResourcesType[]) informRes.toArray(new ResourcesType[informRes.size()]));
			//����� ������������ ����� ��� �������� ����������� �����
			CardLink card = getPrimaryLink(ermbProfil, potentialPaymentCardLinks);
			if (card != null)
				mobileBankService.setChargeOffCard(card.getNumber());
			//������ ����������� ����������� �� ����� ������������ ��������
			mobileBankService.setInformNewResource(ermbProfil.getNewProductNotification());
			//��������� ���������, � ������� ��������� ���������� �����������
			DaytimePeriodType daytimePeriodType = new DaytimePeriodType();
			java.sql.Time beginTime = ermbProfil.getNotificationStartTime();
			java.sql.Time endTime = ermbProfil.getNotificationEndTime();

			if (beginTime != null && endTime != null)
			{
				org.apache.axis.types.Time axisBeginTime = new Time(beginTime.toString());
				org.apache.axis.types.Time axisendTime = new Time(endTime.toString());
				DaysOfWeek daysOfWeek = ermbProfil.getDaysOfWeek();
				if (daysOfWeek != null)
					daytimePeriodType.setDay(daysOfWeek.getFullNameStrDays());
				daytimePeriodType.setTimeZone(ermbProfil.getTimeZone());
				daytimePeriodType.setBegin(axisBeginTime);
				daytimePeriodType.setEnd(axisendTime);
				mobileBankService.setInformPeriod(daytimePeriodType);
			}
			else
			{
				mobileBankService.setInformPeriod(null);
			}

			mobileBankService.setSuppressAdvertising(ermbProfil.isSuppressAdv());
			mobileBankService.setInformDespositEnrollment(ermbProfil.getDepositsTransfer());
			ErmbProfileStatistic statistic = ermbProfileBusinessService.findStatisticById(ermbProfil.getId());
			if (statistic != null)
				mobileBankService.setLastSMSTime(statistic.getLastRequestTime());
		}
		responseType.setMobileBankService(mobileBankService);
		responseType.setNewProfile(isNew);
		return responseType;
	}

	/**
	 * ���������� ������������ ���� �����
	 * ���� � ������� ����������� ������������ �����, ���������� ��.
	 * ���� ������ ������ ������, ���������� null, ����� �������� ���������� �� ����� ������.
	 * ���� ����� �� ������ �� ����������, �� ���������� ������ ���������� �� ���������
	 * @param profile ���� �������
	 * @param cardLinks ������������ ��������� ��� ������ � �������� ��������� �����
	 * @return ���� �����
	 */
	private CardLink getPrimaryLink(ErmbProfileImpl profile, List<CardLink> cardLinks)
	{
		CardLink result = ErmbHelper.getForeginProduct(profile);
		if (result != null)
		{
			if (CardLinkHelper.selectCardByNumber(cardLinks, result.getNumber()) == null)
				log.error("��������� ��������� ����� ���� �� ������ � ������ ������������ ��������� ��� ������");
			return result;
		}
		//����������  Null ������ ���� ��� ���������
		if (CollectionUtils.isEmpty(cardLinks))
			return null;
		try
		{
			return PrimaryCardResolver.getPrimaryLink(cardLinks);
		}
		catch (BusinessException e)
		{
			log.error("�� ���. ���������� ���������� ������������ �����.", e);
			return null;
		}
	}

	/**
	 * ������������ ������ ��������� ��� ������ � ���
	 * ���� "��������� ��������" �� �����������
	 * @param phoneNumbers ������ ������� ���������
	 * @return �������� � ������� ���
	 */
	public PhoneNumberType[] buildPhoneArray(Collection<String> phoneNumbers)
	{
		PhoneNumberType[] result = new PhoneNumberType[phoneNumbers.size()];
		int i = 0;
		for (String number : phoneNumbers)
		{
			PhoneNumberType phoneNumberType = new PhoneNumberType();
			phoneNumberType.setPhoneNumberN(number);
			result[i] = phoneNumberType;
			i++;
		}
		return result;
	}

	/**
	 * ������������ ��������� � ���������
	 * @param phones ����� ������� ���������
	 * @param message ���������
	 * @return ���������
	 */
	public String getPhonesInfoMessage(Collection<String> phones, String message)
	{
		StringBuilder result = new StringBuilder(message);
		for (String number : phones)
		{
			result.append('\n').append(number);
		}
		return result.toString();
	}
}
