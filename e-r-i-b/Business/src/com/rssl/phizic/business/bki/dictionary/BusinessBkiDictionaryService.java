package com.rssl.phizic.business.bki.dictionary;

import com.rssl.phizgate.common.credit.bki.dictionary.*;
import com.rssl.phizic.business.BusinessException;

/**
 * User: Moshenko
 * Date: 02.10.14
 * Time: 15:49
 * C����� ��� ������ �� ������������� ���
 */
public class BusinessBkiDictionaryService
{
	private static final BkiDictionaryService service = new BkiDictionaryService();

	/**
	 * @param code ��� ��������� �����.
	 * @return ��� �����
	 * @throws BusinessException
	 */
	public String getBkiBankConstantNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiBankConstantName.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ��� ���� ��������������.
	 * @return �������� ���� ��������������
	 * @throws BusinessException
	 */
	public String getBkiFinanceTypeNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiFinanceType.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ���� ������������
	 * @throws BusinessException
	 */
	public String getBkiPurposeOfFinanceNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiPurposeOfFinance.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ������ ������ �� ����� (��������)
	 * @throws BusinessException
	 */
	public String getBkiAccountPaymentStatusNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiAccountPaymentStatus.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return �������� ���� �����������
	 * @throws BusinessException
	 */
	public String getBkiTypeOfSecurityNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiTypeOfSecurity.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return �������� ���� ��������� �����
	 * @throws BusinessException
	 */
	public String getBkiApplicantTypeNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiApplicantType.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return �������� � ����������� ���
	 * @throws BusinessException
	 */
	public String getBkiPrimaryIDTypeNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getNameByBkiCode(code);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return �������� ���� ������
	 * @throws BusinessException
	 */
	public String getBkiAddressTypeNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiAddressType.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return �������� ����� �����������
	 */
	public BkiAddressType getRegistrationAddressType() throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByFlag(BkiAddressType.class, "registration", true);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return �������� ����� ����������
	 */
	public BkiAddressType getResidenceAddressType() throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByFlag(BkiAddressType.class, "residence", true);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return �������� �������
	 * @throws BusinessException
	 */
	public String getBkiRegionCodeNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiRegionCode.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return �������� ������
	 * @throws BusinessException
	 */
	public String getBkiCountryNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiCountry.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ������� ��������
	 * @throws BusinessException
	 */
	public String getBkiReasonForClosureNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiReasonForClosure.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ������� �������
	 * @throws BusinessException
	 */
	public String getBkiReasonForEnquiryNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiReasonForEnquiry.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ����������� ����� (������������)
	 * @throws BusinessException
	 */
	public String getBkiTypeOfScoreCardNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiTypeOfScoreCard.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ����� ����� (������������)
	 * @throws BusinessException
	 */
	public String getBkiAccountClassNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiAccountClass.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ������ �� �����������
	 * @throws BusinessException
	 */
	public String getBkiDisputeIndicatorNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiDisputeIndicator.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ��� ���������
	 * @throws BusinessException
	 */
	public String getBkiApplicationTypeNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiApplicationType.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ��������� (������������)
	 * @throws BusinessException
	 */
	public String getBkiTitleNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiTitle.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ���
	 * @throws BusinessException
	 */
	public String getBkiSexNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiSex.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ��������
	 * @throws BusinessException
	 */
	public String getBkiConsentIndicatorNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiConsentIndicator.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return �������/���������� �����
	 * @throws BusinessException
	 */
	public String getBkiCurrentPreviousAddressNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiCurrentPreviousAddress.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ������ ��������� �����
	 * @throws BusinessException
	 */
	public String getBkiCreditFacilitySatusNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiCreditFacilitySatus.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ����������� ������
	 * @throws BusinessException
	 */
	public String getBkiAccountSpecialStatusNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiAccountSpecialStatus.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return �������������� �����
	 * @throws BusinessException
	 */
	public String getBkiInsuredLoanNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiInsuredLoan.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param code ���
	 * @return ������� ��������� ����� �������
	 * @throws BusinessException
	 */
	public String getBkiDurationUnitNameByCode(String code) throws BusinessException
	{
		try
		{
			return service.getBkiDictionaryRecordNameByCode(code, BkiDurationUnit.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
