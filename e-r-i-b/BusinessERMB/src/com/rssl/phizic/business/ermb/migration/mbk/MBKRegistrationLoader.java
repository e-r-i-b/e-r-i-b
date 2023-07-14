package com.rssl.phizic.business.ermb.migration.mbk;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizgate.common.services.types.OfficeImpl;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.annotation.Statefull;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.gate.mobilebank.MBKRegistrationResultCode;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.jmx.MobileBankConfig;
import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.lang.BooleanUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;

/**
 * @author Moshenko
 * @ created 07.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ����� ������ ��� � ������ ���������� ����������� � ��� � ����� (�������� �� ����)
 * ��������� �������� ������ �� ���.
 * ������ ������ �� ������
 */
@Statefull
public class MBKRegistrationLoader
{
	/**
	 * ������� ��������
	 */
	@SuppressWarnings("PublicInnerClass")
	public static enum Status
	{
		ENOUGH,
		MORE;
	}

	/**
	 * ������������ ����� ������� ������� ������ ����������
	 */
	private static final int MAX_RUN_COUNT = 10;

	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final MobileBankService mbkService = GateSingleton.getFactory().service(MobileBankService.class);

	private final MBKRegistrationParser parser = new MBKRegistrationParser();

	private final EribNodeResolver nodeResolver = new EribNodeResolver();

	private final ClientPassportResolver passportResolver = new ClientPassportResolver();

	private final MBKRegistrationResultDatabaseService resultDatabaseService = new MBKRegistrationResultDatabaseService();

	/**
	 * ������ ����� ������, ������� ��������� ������������ ������
	 */
	private final int packSize;

	/**
	 * ���������� �������� ����������
	 */
	private int runCount = 0;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 */
	public MBKRegistrationLoader()
	{
		MobileBankConfig mobileBank�onfig = ConfigFactory.getConfig(MobileBankConfig.class);
		mobileBank�onfig.doRefresh();

		packSize = mobileBank�onfig.getConfirmRegMaxValue();
		if (packSize < 1)
			throw new ConfigurationException("������ ����� ������� ���: " + packSize);
	}

	/**
	 * �������� ���� ������ ����������
	 * @return ������ ������ ����������
	 * @throws Exception - ����� ���� ��������
	 */
	public Status load() throws Exception
	{
		if (runCount++ >= MAX_RUN_COUNT)
			return Status.ENOUGH;

		// �3.1. ��������� ����������� �� ��� ��������� ����� ������ ����� ����� �� mb_BATCH_ERMB_GetRegistrations.
		// �� ���� ����� ���������� �� ���������� ��������.
		// ���� �� ���������� ������, ��������� �������� ��������� ������.
		List<MBKRegistration> registrations = mbkService.getMbkRegistrationsForErmb();
		// �3.2. ��� ����� resultset ���������� ����� ������.
		// ���� ����� �����, ��������� ��������� ������ � ����������� ENOUGH.
		// �3.3. ��������� ��������� resultset �� �������� � ���������� �� �� �������.
		// ���� �� ������� ��������� �� ����� ������, ��������� ��������� ������ � ����������� ENOUGH.
		// ���� �� ������� ��������� ID ������, ������ �������� �� ���������� ���������.
		// ������, �� ��������� ��������� �� ���� ��������, ���������� ��� ����������� � ��������� ������.
		// ����������� ������ ������ � �. �3.6.
		if (registrations.isEmpty())
			return Status.ENOUGH;

		// �������� �� ������
		for (List<MBKRegistration> registrationPack : ListUtil.split(registrations, packSize))
		{
			// �3.4. ��������� ��������� ������ ������ �� ������� �������� ������.
			// ���� ������ �� �������, ��������� ������ ������������.
			// ���� �� ������ �������� ������ IN_PROCESS, ��������� ������ ������������.
			// ���� �� ������ �������� ������ PROCESSED, ������ ���������� ��� ������������.
			// ������������ ������ ������ � �. �3.6.
			loadResults(registrationPack);

			// ��������� �������� ������ ������� �� ������
			loadOwners(registrationPack);

			// �3.5. ��� ������ ������ ��������� ���������� ���� ����.
			// ���� ���� ���������� �� �������, ������ ���������� ��� ����������.
			// ������, ��� ������� ������� ���������� ����,  ������ � �3.12.
			// ������, ��� ������� ���� ���� ���������� �� �������, ������ � �. �3.6.
			loadEribNodes(registrationPack);

			// �3.6. ���������� � ������������ ������ ���������� � ��� ����� ����� �� mb_BATCH_ERMB_RegistrationResult.
			// �3.7. ��� ��������� ���������� �� ������.
			// � ������ ������ ���, ��������� �������� �������� �� ������ � ���������� ������.
			returnProcessedRegistrations(registrationPack);

			// �3.8. ��������� ������� ������������ ������ �� ������� �������� ������.
			deleteAcceptedResults(registrationPack);

			// �3.9. ��������� ������������ ���� ����� ������ ����� ����� �� mb_BATCH_ERMB_ConfirmRegistrationLoading.
			// �3.10. ��� ��������� ������������� ����� ������.
			// � ������ ������ ���, ��������� �������� �������� �� ������ � ���������� ������.
			confirmNewRegistrations(registrationPack);

			// �3.11. ��������� ��������� ������ � ������� �������� ������ �� �������� IN_PROCESS
			// ������ �� ������, �.�. � ������� �������� ������ �������� ������ ���������� ���������

			// �3.12. ��������� ������� ������ � ����
			passRegistrationsToNodes(registrationPack);
		}

		for (MBKRegistration registration : registrations)
		{
			// ������ ��� ������, ���� ���� �� ���� ������ ���� ������� �������� � ����
			if (BooleanUtils.isTrue(registration.getPassedToNode()))
				return Status.MORE;
		}

		return Status.ENOUGH;
	}

	/**
	 * ������ ���������� ��������� ������
	 * ���������� ������������ � ������ � ���� resultCode � errorDescr
	 * ���� �������� ����������� ������������� ��� ����� ���� ����������
	 * @param registrations - ��� ������
	 */
	private void loadResults(List<MBKRegistration> registrations) throws BusinessException
	{
		if (registrations.isEmpty())
			return;

		// 1. ��������� ������ ID �������������� ������
		List<Long> ids = new LinkedList<Long>();
		for (MBKRegistration registration : registrations)
		{
			// ������� ������ �������� �������������� ������
			if (registration.isWellParsed() && registration.getResultCode() == null)
				ids.add(registration.getId());
		}

		// 2. ������ ������ �����������
		List<MBKRegistrationResult> resultList = resultDatabaseService.loadRegistrationsResults(ids);
		if (resultList.isEmpty())
			return;

		// 3. ��������� ���� <id -> ���������> �� ������ �����������
		Map<Long, MBKRegistrationResult> resultMap = new HashMap<Long, MBKRegistrationResult>(resultList.size());
		for (MBKRegistrationResult result : resultList)
			resultMap.put(result.id, result);

		// 4. ���������� ���������� � ������
		for (MBKRegistration registration : registrations)
		{
			MBKRegistrationResult result = resultMap.get(registration.getId());
			if (result != null)
			{
				registration.setResultCode(result.resultCode);
				registration.setErrorDescr(result.errorDescr);
			}
		}
	}

	/**
	 * �������� ��� ��� �� ��������� �������� ����� ������
	 * ���������� ������������ � ������ � ���� owner
	 * ���� ����������� ��� ��� �� ������������� ��� ������ ��������� ������ � ������������ � ������
	 * @param registrations - ��� ������
	 */
	private void loadOwners(List<MBKRegistration> registrations)
	{
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

		for (MBKRegistration registration : registrations)
		{
			// ������� ������ �������� �������������� ������
			if (registration.isWellParsed() && registration.getResultCode() == null)
			{
				try
				{
					String card = registration.getPaymentCardNumber();
					Office office = new OfficeImpl(registration.getOfficeCode());
					Pair<String, Office> cardAndOffice = new Pair<String, Office>(card, office);

					// noinspection unchecked
					Client client = GroupResultHelper.getOneResult(bankrollService.getOwnerInfoByCardNumber(cardAndOffice));
					if (client == null)
						// noinspection ThrowCaughtLocally
						throw new RuntimeException("�� ������� ����� ��������� ����� " + MaskUtil.getCutCardNumber(card));

					String passport = passportResolver.getClientPassport(client);
					String tb = registration.getOfficeCode().getFields().get("region");
					registration.setOwner(new UserInfo(client, passport, tb, tb));
				}
				catch (Exception e)
				{
					log.error(e.getMessage(), e);
					registration.setResultCode(MBKRegistrationResultCode.ERROR_NOT_REG);
					registration.setErrorDescr("������ �� ������ �� �����");
				}
			}
		}
	}

	/**
	 * ���� ����� ����, � ������� ������ �������������� ������
	 * ���������� ������ ������������ � ������ � ���� node
	 * ���� ����������� ����� ������������� ��� ������ ��������� ������ � ������������ � ������
	 * @param registrations - ��� ������
	 */
	private void loadEribNodes(List<MBKRegistration> registrations)
	{
		for (final MBKRegistration registration : registrations)
		{
			// ������� �������������� ������, �� ������� ������� ���������� ���������
			if (registration.getOwner() != null && registration.getResultCode() == null)
			{
				PhoneNumber phone = registration.getPhoneNumber();
				try
				{
					switch (registration.getFiltrationReason())
					{
						// �4.3. ���� ���� FiltrationReasonName ����� �������� � �����,
						// ����� ����� ������������ �������� � ��� �� ������ �������� � ���� PhoneNumber.
						// ���� ��� �� ���������� ����� ����� ��� ����� ����������� �����,
						// �������� ����������� ������ � ����������� ����� �� ������.
						case ERMB_PHONE:
							registration.setNode(nodeResolver.getNodeByPhone(phone));
							if (registration.getNode() == null)
								// noinspection ThrowCaughtLocally
								throw new IllegalArgumentException("�� ������ ���� �� ������ ��������");
							break;

						// �4.4. ���� ���� FiltrationReasonName ����� ������ � �����,
						// ����� ����� ������������ �������� � ��� �� ��� ��� �� ��,
						// ������� � ���� ������� ������������ �������� CRDWI �� ����� � ���� PaymentCardNumber.
						// ���� CRDWI �� ���������� ��� ��� �� ��,
						// ���� ��� �� ���������� ����� �����,
						// ���� ����� ����� ����������� �����,
						// �������� ����������� ������ � ����������� ����� �� ������.
						case ERMB_CARD:
							registration.setNode(nodeResolver.getNodeByUserInfo(registration.getOwner()));
							if (registration.getNode() == null)
								// noinspection ThrowCaughtLocally
								throw new IllegalArgumentException("�� ������ ���� �� ��������� �������� �����");
							break;

						// �4.5. ���� ���� FiltrationReasonName ����� ��������� ����,
						// ����� ����� ������������ ������� �� ������ �������� (��� � �. �4.3), � ����� �� ������ ����� (��� � �. �4.4).
						// ���� ����� �� �������� ����������� �����,
						// �������� ����������� ������ � ����������� ����� �� ������.
						// ���� �� ���� �� �������� �� ���������� ����� �����, �������� ���������� ����� ������������ �����.
						case PILOT_ZONE:
							registration.setNode(nodeResolver.getNodeByPhone(phone));
							if (registration.getNode() == null)
								registration.setNode(nodeResolver.getNodeByUserInfo(registration.getOwner()));
							if (registration.getNode() == null)
								registration.setNode(nodeResolver.getNewUserAllowedNode());
							break;

						default:
							// noinspection ThrowCaughtLocally
							throw new UnsupportedOperationException("����������� FiltrationReason " + registration.getFiltrationReason());
					}
				}
				catch (Exception e)
				{
					log.error("�� ������� ���������� ���� ���� �� ������ " + registration, e);
					registration.setResultCode(MBKRegistrationResultCode.ERROR_NOT_REG);
					registration.setErrorDescr("���� �� ������");
				}
			}
		}
	}

	/**
	 * ���������� � ��� ������������ ������
	 * ������ ������ ��������� ��������
	 * ���� �������� ������ ������������ � ��������� ������ � ����� ������������
	 * ����� �������� ������ ���������� � ������ ������� returnedToMBK
	 * (����� � ���������� ����� ������ ����� ���� ������� �� ������� �����������)
	 * @param registrations - ��� ������
	 */
	private void returnProcessedRegistrations(List<MBKRegistration> registrations)
	{
		for (MBKRegistration registration : registrations)
		{
			// ������� ������ ������������ ������ (� �.�. ����������)
			if (registration.getResultCode() != null)
			{
				try
				{
					mbkService.sendMbRegistrationProcessingResult(registration.getId(), registration.getResultCode(), registration.getErrorDescr());
					registration.setReturnedToMBK(true);
				}
				catch (Exception e)
				{
					log.error("�� ������� ��������� ��������� �� ������ " + registration, e);
					registration.setReturnedToMBK(false);
				}
			}
		}
	}

	/**
	 * ������� ���������� ��������� ������, �������� � ���
	 * ���� �������� ������������� ��� ����� ���� ����������
	 * @param registrations - ��� ������
	 */
	private void deleteAcceptedResults(List<MBKRegistration> registrations) throws Exception
	{
		List<Long> ids = new LinkedList<Long>();
		for (MBKRegistration registration : registrations)
		{
			// ������� ������ ������������ � �������� � ��� ������
			if (registration.getResultCode() != null && BooleanUtils.isTrue(registration.getReturnedToMBK()))
				ids.add(registration.getId());
		}
		if (ids.isEmpty())
			return;

		resultDatabaseService.removeByIds(ids);
	}

	/**
	 * ������������ � ��� ���� ����� ������
	 * ���� ������������� ������������� ��� ����� ���� ����������
	 * @param registrations - ��� ������
	 */
	private void confirmNewRegistrations(List<MBKRegistration> registrations) throws Exception
	{
		List<Long> ids = new LinkedList<Long>();
		for (MBKRegistration registration : registrations)
		{
			// �������� �������������� ������, �� ������� ������� ���������� ����
			if (registration.getNode() != null && registration.getResultCode() == null)
				ids.add(registration.getId());
		}
		if (ids.isEmpty())
			return;

		mbkService.confirmMbRegistrationsLoading(ids);
	}

	/**
	 * ������� ������ � ��������� � ������ ����
	 * ������ ������ ��������� ��������
	 * ���� �������� ������ ������������ � ��������� ������ � ����� ������������
	 * @param registrations - ��� ������
	 */
	private void passRegistrationsToNodes(List<MBKRegistration> registrations)
	{
		for (MBKRegistration registration : registrations)
		{
			// ���������� �������������� ������, �� ������� ������� ���������� ����
			if (registration.getNode() != null && registration.getResultCode() == null)
			{
				MQInfo mqInfo = registration.getNode().getMbkRegistrationMQ();
				JMSQueueSender sender = new JMSQueueSender(mqInfo.getFactoryName(), mqInfo.getQueueName());

				try
				{
					sender.send(registration);
					registration.setPassedToNode(true);
				}
				catch (JMSException e)
				{
					log.error("�� ������� ��������� ������� ����� ������ " + registration, e);
					registration.setPassedToNode(false);
				}
			}
		}
	}
}
