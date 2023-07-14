package com.rssl.phizic.web.client.sberbankForEveryDay;

import com.rssl.phizic.business.sbnkd.SberbankForEveryDayHelper;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Форма для просмотра заявки "Сбербанк на каждый день"
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ViewSberbankForEveryDayClaimForm extends EditFormBase
{
	private IssueCardDocumentImpl claim;
	private String maskedAfterCityResAddr; // адрес проживания (маскированная часть адреса после города)
	private String maskedAfterCityRegAddr; // адрес прописки (маскированная часть адреса после города)

	/**
	 * @return завку СБНКД
	 */
	public IssueCardDocumentImpl getClaim()
	{
		return claim;
	}

	/**
	 * Задать завку СБНКД
	 * @param claim - завка
	 */
	public void setClaim(IssueCardDocumentImpl claim)
	{
		this.claim = claim;
		setMaskedAddress();
	}

	public String getMaskedAfterCityResAddr()
	{
		return maskedAfterCityResAddr;
	}

	public void setMaskedAfterCityResAddr(String maskedAfterCityResAddr)
	{
		this.maskedAfterCityResAddr = maskedAfterCityResAddr;
	}

	public String getMaskedAfterCityRegAddr()
	{
		return maskedAfterCityRegAddr;
	}

	public void setMaskedAfterCityRegAddr(String maskedAfterCityRegAddr)
	{
		this.maskedAfterCityRegAddr = maskedAfterCityRegAddr;
	}

	private void setMaskedAddress()
	{
		//Адрес_(все, что после города)_1 (проживания)
		String resStreet = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.STREET, claim.getAddressAfterCity0());
		String resBuilding = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.HOUSE, claim.getAddressAfterCity0());
		String resCorps = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.BUILDING, claim.getAddressAfterCity0());
		String resRoom = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.FLAT, claim.getAddressAfterCity0());

		if(StringHelper.isNotEmpty(resStreet))
		{
			resStreet = MaskUtil.maskString(resStreet, 0,
					resStreet.length() >= CreateSberbankForEveryDayClaimAction.STREET_MASK_CHARS_NUMBER ? resStreet.length() - CreateSberbankForEveryDayClaimAction.STREET_MASK_CHARS_NUMBER : 0,
					CreateSberbankForEveryDayClaimAction.CHAR_FOR_MASK);
		}

		if(StringHelper.isNotEmpty(resBuilding))
		{
			resBuilding = MaskUtil.maskString(resBuilding, 0, 0, CreateSberbankForEveryDayClaimAction.CHAR_FOR_MASK);
		}

		if (StringHelper.isNotEmpty(resCorps))
		{
			resCorps = MaskUtil.maskString(resCorps, 0, 0, CreateSberbankForEveryDayClaimAction.CHAR_FOR_MASK);
		}

		if(StringHelper.isNotEmpty(resRoom))
		{
			resRoom = MaskUtil.maskString(resRoom, 0, 0, CreateSberbankForEveryDayClaimAction.CHAR_FOR_MASK);
		}
		setMaskedAfterCityResAddr(SberbankForEveryDayHelper.getAfterCityAddress(resStreet, resBuilding, resCorps, resRoom));

		if(StringHelper.isNotEmpty(claim.getAddressAfterCity1()))
		{
			//Адрес_(все, что после города)_2 (прописки)
			String regStreet = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.STREET, claim.getAddressAfterCity1());
			String regBuilding = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.HOUSE, claim.getAddressAfterCity1());
			String regCorps = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.BUILDING, claim.getAddressAfterCity1());
			String regRoom = SberbankForEveryDayHelper.getFromAfterCityAddress(SberbankForEveryDayHelper.FLAT, claim.getAddressAfterCity1());

			if(StringHelper.isNotEmpty(regStreet))
			{
				regStreet = MaskUtil.maskString(regStreet, 0,
						regStreet.length() >= CreateSberbankForEveryDayClaimAction.STREET_MASK_CHARS_NUMBER ? regStreet.length() - CreateSberbankForEveryDayClaimAction.STREET_MASK_CHARS_NUMBER : 0,
						CreateSberbankForEveryDayClaimAction.CHAR_FOR_MASK);
			}

			if(StringHelper.isNotEmpty(regBuilding))
			{
				regBuilding = MaskUtil.maskString(regBuilding, 0, 0, CreateSberbankForEveryDayClaimAction.CHAR_FOR_MASK);
			}

			if (StringHelper.isNotEmpty(regCorps))
			{
				regCorps = MaskUtil.maskString(regCorps, 0, 0, CreateSberbankForEveryDayClaimAction.CHAR_FOR_MASK);
			}

			if(StringHelper.isNotEmpty(regRoom))
			{
				regRoom = MaskUtil.maskString(regRoom, 0, 0, CreateSberbankForEveryDayClaimAction.CHAR_FOR_MASK);
			}
			setMaskedAfterCityRegAddr(SberbankForEveryDayHelper.getAfterCityAddress(regStreet, regBuilding, regCorps, regRoom));
		}
	}
}
