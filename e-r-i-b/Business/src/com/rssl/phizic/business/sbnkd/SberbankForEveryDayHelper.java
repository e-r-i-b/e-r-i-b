package com.rssl.phizic.business.sbnkd;

import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.cellOperator.CellOperator;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.loanclaim.type.LoanRate;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.sbnkd.SBNKDConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.claims.sbnkd.IssueCardService;
import com.rssl.phizic.gate.claims.sbnkd.impl.ClaimOffer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Address;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.*;

/**
 * ������ ��� "�������� �� ������ ����"
 * @author lukina
 * @ created 30.12.2014
 * @ $Author$
 * @ $Revision$
 */
public class SberbankForEveryDayHelper
{
	public static final String COMMA = ", ";
	public static final String STREET = "��. ";
	public static final String HOUSE = "�. ";
	public static final String BUILDING = "����. ";
	public static final String FLAT = "��. ";

	public static final int PHONE_LENGTH = 10;
	public static final String FALSE_VALUE = "0";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final IssueCardService issueCardService = new IssueCardService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final  SimpleService simpleService = new SimpleService();

	/**
	 * ��������� ������ �� �� ������� � ������ ��, � ������� �������� ����������� �����
	 * @param terBank - ����� �� �������
	 * @return true - ������, false - �� ������
	 */
	private static boolean checkClientAllowedTB(String terBank)
	{
		return  ConfigFactory.getConfig(SBNKDConfig.class).getAllowedTB().contains(terBank);
	}

	/**
	 * @return �������� �� ������� �����
	 */
	public static boolean availableSBNKD()
	{
		if (!PersonContext.isAvailable())
			return false;

		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		if (person == null)
			return false;
		//���� ������ �� ����, �� ������ �� ����������
		if (person.getCreationType() != CreationType.UDBO )
			return false;

		//��� ��� ������� ������ �� ����������
		if (person.getSegmentCodeType() == SegmentCodeType.VIP)
			return false;

		try
		{
			//���� � ������� ��� �������� ��, �� ������ �� ����������
			Collection<String> phones = MobileBankManager.getPhones(person.getLogin());
			if (CollectionUtils.isEmpty(phones))
				return false;

			//��������� �������� �� ��� �� ������������ ����������� �����
			return checkClientAllowedTB(((ExtendedCodeImpl)person.asClient().getOffice().getCode()).getRegion()) && PermissionUtil.impliesServiceRigid("CreateSberbankForEveryDayClaimService");
		}
		catch (Exception e)
		{
			log.error("������ ��� ����������� �� �������", e);
			return false;
		}
	}

	/**
	 * �������� ����� ������, ������� ���� ����� ������ (�����, ���, ������, ��������) � ���� ������
	 * @param address �����
	 * @return ����� ������ ����� ������
	 */
	public static String getAfterCityAddress(Address address)
	{
		StringBuilder sb  = new StringBuilder();
		if(StringHelper.isNotEmpty(address.getStreet()) && !address.equals(STREET))
		{
			sb.append(STREET);
			sb.append(address.getStreet());
			sb.append(COMMA);
		}
		if(StringHelper.isNotEmpty(address.getHouse()) && !address.equals(HOUSE))
		{
			sb.append(HOUSE);
			sb.append(address.getHouse());
			sb.append(COMMA);
		}
		if(StringHelper.isNotEmpty(address.getBuilding())&& !address.equals(BUILDING))
		{
			sb.append(BUILDING);
			sb.append(address.getBuilding());
			sb.append(COMMA);
		}
		if(StringHelper.isNotEmpty(address.getFlat()) && !address.equals(FLAT))
		{
			sb.append(FLAT);
			sb.append(address.getFlat());
		}

		if (sb.toString().equals(""))
		{
			sb.append(address.getUnparseableAddress());
		}
		return sb.toString();
	}

	/**
	 * �������� ��������� ������������� ������ ������ (�����, ���, ������, ��������)
	 * @param street �����
	 * @param house ���
	 * @param building ������
	 * @param flat ��������
	 * @return �����
	 */

	public static String getAfterCityAddress(String street, String house, String building, String flat)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(STREET);
		if(StringHelper.isNotEmpty(street))
			sb.append(street);

		if(StringHelper.isNotEmpty(house))
		{
			sb.append(COMMA);
			sb.append(HOUSE);
			sb.append(house);
		}

		if(StringHelper.isNotEmpty(building))
		{
			sb.append(COMMA);
			sb.append(BUILDING);
			sb.append(building);
		}


		if(StringHelper.isNotEmpty(flat))
		{
			sb.append(COMMA);
			sb.append(FLAT);
			sb.append(flat);
		}

		return sb.toString();
	}

	/**
	 * �������� ����� (�����, ���, ������, ��������) ������ �� ������ ������
	 * @param selector ������ ����� ������
	 * @param address �����
	 * @return
	 */
	public static String getFromAfterCityAddress(String selector, String address)
	{
		String result = null;

		if(StringHelper.isNotEmpty(address))
		{
			String[] parts = address.split(COMMA);
			for(String part: parts)
			{
				if(part.contains(selector))
				{
					return part.substring(selector.length(), part.length());
				}
			}
		}
		return result;
	}

	/**
	 * ����� ������
	 * @param ownerId - ������������� ��������� (������ ������� ��� �����)
	 * @param isGuest - ������� �����
	 * @return ������ c ������� ������ {id, ���� ��������, ������, ��� 1-�� �����, ������ 1-�� �����, ���������� ���� � ������}
	 */
	public static List<Object[]> findClaimDataByOwnerId(Long ownerId, boolean isGuest)
	{
		if (ownerId != null && ownerId != 0)
		{
			try
			{
				return issueCardService.findClaimDataByOwnerId(ownerId, isGuest);
			}
			catch (GateException e)
			{
				log.error("������ ��� ��������� ������ ������", e);
			}
		}
		return new ArrayList<Object[]>();
	}

	/**
	 * ����� ������
	 * @param guestLogin - ������������� ��������� (������ ������� ��� �����)
	 * @return
	 */
	public static List<ClaimOffer> findClaimInfoByGuestLogin(GuestLogin guestLogin)
	{
		List<ClaimOffer> resultList = new ArrayList<ClaimOffer>();
        ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);
		Integer claimPeriod = clientConfig.getGuestClaimsPeriod();
		List<ExtendedLoanClaim> loanClaimList = null;																								  //�������
		try
		{
			loanClaimList = businessDocumentService.findExtendedLoanClaimByLogin(guestLogin, claimPeriod);
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� ������ ������ �� ��������", e);
		}

		for (ExtendedLoanClaim claim :loanClaimList)
		{
			resultList.add(convertExtendedLoanClaimToClaimOffer(claim));
		}

		List<Object[]> issueCardClaimList = null;
		try
		{
			issueCardClaimList = issueCardService.findClaimDataByOwnerIdAndPeriod(guestLogin.getGuestCode(), true, claimPeriod);
		}
		catch (GateException e)
		{
			log.error("������ ��� ��������� ������ ������ �� ������", e);
		}

		for (Object[] row :issueCardClaimList)
		{
			String documentNumber = (String)row[0];
			Calendar documentDate = (Calendar)row[1];
			String documentStatus = (String)row[2];
			String cardName = (String)row[3];
			String cardCurrency = (String)row[4];
			Long cardCount = (Long)row[5];
			resultList.add(new ClaimOffer(documentNumber, documentDate, documentStatus, cardName, cardCurrency, cardCount, true));
		}

        List<Object[]> loanCardClaimList = null;
        try
        {
            loanCardClaimList = businessDocumentService.findCardClaimByGuestId(guestLogin, claimPeriod);
        }
        catch (GateException e)
        {
            log.error("������ ��� ��������� ������ ��������� ������ �� ������", e);
        }

        for (Object[] claim : loanCardClaimList)
        {
            if (claim.length < 6)
                continue;
            String documentNumber = (String) claim[0];
            Calendar documentDate = (Calendar) claim[1];
            String documentStatus = (String) claim[2];
            String cardName = (String) claim[3];
            String cardCurrency = (String) claim[4];
            Long claimId = (Long) claim[5];
            resultList.add(new ClaimOffer(documentNumber, documentDate, documentStatus, cardName, cardCurrency, (long)loanCardClaimList.size(), claimId, true, true));
		}

		Collections.sort(resultList, new Comparator<ClaimOffer>()
		{
			public int compare(ClaimOffer o1, ClaimOffer o2)
			{
				Calendar docDate1 = o1.getDocumentDate();
				Calendar docDate2 = o2.getDocumentDate();
				return docDate1.compareTo(docDate2);
			}
		});
		return  resultList;
	}

	/**
	 * @return ����������� �� ���������� ������������ � ������ ������ (������� �������� ��������� �����)
	 */
	public static long getGuestClaimsLimit()
	{
		return ConfigFactory.getConfig(ClientConfig.class).getClaimsLimit();
	}

	/**
	 * ����� ������������ ��� ��������������� ����������� ��������� ������ � ������ ClaimOffer
	 * @param claim ����������� ��������� ������
	 * @return ����������������� ������
	 */
	private static ClaimOffer convertExtendedLoanClaimToClaimOffer(ExtendedLoanClaim claim){
		Money loanAmount = claim.getLoanAmount();
		String currency = null;
		if (loanAmount != null)
			currency = loanAmount.getCurrency().getCode();
		BigDecimal prodRate = null;
		LoanRate loanRate = claim.getLoanRate();
		if (loanRate != null)
			prodRate = loanRate.getMinLoanRate();
		Long prodDuration = claim.getLoanPeriod();
		ClaimOffer offer = new ClaimOffer(claim.getDocumentNumber(), claim.getDateCreated(), claim.getState().toString(), claim.getProductName(),currency , 1L, claim.getId(), false, false);

		offer.setProdDuration(prodDuration);
		offer.setFormName(claim.getFormName());
		offer.setProdSumm(loanAmount);
		offer.setProdRate(prodRate);

		return offer;
	}


	public static CellOperator findOperatorByPhone(String phoneNumber) throws BusinessException
	{
		final CellOperator operator = new CellOperator();
		if(phoneNumber == null)
		{
			operator.setFlAuto(FALSE_VALUE);
			return operator;
		}

		if (phoneNumber.length() < PHONE_LENGTH)
		{
			throw new BusinessException("�������� ������ ������ ��������");
		}
		if (phoneNumber.length() > PHONE_LENGTH)
		{
			phoneNumber = phoneNumber.substring(phoneNumber.length() - PHONE_LENGTH, phoneNumber.length());
		}
		final String phoneNum = phoneNumber;
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CellOperator>()
			{
				public CellOperator run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.cellOperator.CellOperator.selectByPhoneNumber");
					query.setParameter("phoneNum", phoneNum);
					if(query.list().size() > 0)
					{
						Object[] res = (Object[])query.list().get(0);
						operator.setFlAuto(String.valueOf(res[0]));
						operator.setBalance(Long.parseLong(String.valueOf(res[1])));
						operator.setMinSumm(Long.parseLong(String.valueOf(res[2])));
						operator.setMaxSumm(Long.parseLong(String.valueOf(res[3])));
					}
					else
					{
						operator.setFlAuto(FALSE_VALUE);
					}

					return operator;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
