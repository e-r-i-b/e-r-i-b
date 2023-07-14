package com.rssl.phizic.business.bki.dictionary;

import com.rssl.phizgate.common.credit.bki.dictionary.*;
import com.rssl.phizic.business.BusinessException;

/**
 * User: Moshenko
 * Date: 02.10.14
 * Time: 15:49
 * Cервис для работы со справочниками БКИ
 */
public class BusinessBkiDictionaryService
{
	private static final BkiDictionaryService service = new BkiDictionaryService();

	/**
	 * @param code Код канстанты банка.
	 * @return Имя банка
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
	 * @param code Код типа финансирования.
	 * @return название типа финансирования
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
	 * @param code код
	 * @return Цель кредитования
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
	 * @param code код
	 * @return Статус выплат по Счету (описание)
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
	 * @param code код
	 * @return название типа обеспечения
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
	 * @param code код
	 * @return название типа владельца счета
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
	 * @param code код
	 * @return название в справочнике ДУЛ
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
	 * @param code код
	 * @return название типа адреса
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
	 * @return получить адрес регистрации
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
	 * @return получить адрес проживания
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
	 * @param code код
	 * @return Название региона
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
	 * @param code код
	 * @return Название страны
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
	 * @param code код
	 * @return Причина закрытия
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
	 * @param code код
	 * @return Причина запроса
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
	 * @param code код
	 * @return Скоринговая карта (наименование)
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
	 * @param code код
	 * @return Класс счета (наименование)
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
	 * @param code код
	 * @return Запись об оспаривании
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
	 * @param code код
	 * @return Тип заявителя
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
	 * @param code код
	 * @return Обращение (наименование)
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
	 * @param code код
	 * @return Пол
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
	 * @param code код
	 * @return Согласие
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
	 * @param code код
	 * @return Текущий/предыдущий адрес
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
	 * @param code код
	 * @return Статус кредитной линии
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
	 * @param code код
	 * @return Специальный статус
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
	 * @param code код
	 * @return Застрахованная ссуда
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
	 * @param code код
	 * @return единица измерения срока кредита
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
