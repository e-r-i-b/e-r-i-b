package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.RandomHelper;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author lukina
 * @ created 09.09.13
 * @ $Author$
 * @ $Revision$
 */
public class SecurityAccountHelper extends BaseResponseHelper
{
	public SecuritiesAcctInfo_Type getSecurityAccountRecs(Login login)
	{
		SecuritiesAcctInfo_Type securitiesAcctInfo = new SecuritiesAcctInfo_Type();
		List<SecurityAccountLink> securityAccountLinks;
		try
		{
			securityAccountLinks = (login==null) ? null : resourceService.getLinks(login, SecurityAccountLink.class);
		}
		catch (BusinessException e)
		{
			securityAccountLinks = new ArrayList<SecurityAccountLink>();
		}
		catch (BusinessLogicException e)
		{
			securityAccountLinks = new ArrayList<SecurityAccountLink>();
		}

		boolean isNew = !(securityAccountLinks != null && !securityAccountLinks.isEmpty());

		int count = isNew ? 5 : securityAccountLinks.size();

		SecuritiesRecShort_Type[] result = new SecuritiesRecShort_Type[count];

		for (int i=0; i<count; i++)
		{
			String accountNumber = isNew ? "СХ "+RandomHelper.rand(7, RandomHelper.DIGITS) : securityAccountLinks.get(i).getNumber();
			result[i] = createSecuritiesShortRec(accountNumber);
		}

		securitiesAcctInfo.setSecuritiesRec(result);
		CustInfo_Type custInfo = new CustInfo_Type();

		securitiesAcctInfo.setHolder(custInfo);
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setGender("м");
		custInfo.setPersonInfo(personInfo);
		return securitiesAcctInfo;
	}

	private SecuritiesRecShort_Type createSecuritiesShortRec(String accountNumber)
	{
		SecuritiesRecShort_Type result = new SecuritiesRecShort_Type();
		BlankInfo_Type blank = new BlankInfo_Type();
		blank.setSerialNum(accountNumber);
		blank.setBlankType("тип бланка");
		result.setBlankInfo(blank);
		result.setOnStorageInBank(true);
		SecuritiesInfo_Type securitiesInfo = new SecuritiesInfo_Type();
		securitiesInfo.setNominalAmt(getRandomDecimal());
		securitiesInfo.setNominalCurCode("RUB");

		securitiesInfo.setComposeDt(getStringDate(getRandomDate()));
		securitiesInfo.setTermFinishDt(getStringDate(getRandomDate()));
		securitiesInfo.setTermStartDt(getStringDate(getRandomDate()));
		securitiesInfo.setTermLimitDt(getStringDate(getRandomDate()));

		securitiesInfo.setIncomeAmt(getRandomDecimal());
		securitiesInfo.setIncomeRate(BigDecimal.valueOf(15));

		result.setSecuritiesInfo(securitiesInfo);
		return result;
	}

	public IFXRs_Type createSecuritiesInfoInqRs(IFXRq_Type parameters)
	{
		SecuritiesInfoInqRq_Type request = parameters.getSecuritiesInfoInqRq();

		SecuritiesInfoInqRs_Type securitiesInfoRs = new SecuritiesInfoInqRs_Type();
		securitiesInfoRs.setRqTm(getRqTm());
		securitiesInfoRs.setRqUID(request.getRqUID());
		securitiesInfoRs.setBankInfo(request.getBankInfo());
		securitiesInfoRs.setStatus(getStatus());
		SecuritiesRec_Type securitiesRec = new SecuritiesRec_Type();
		SecuritiesRec_Type[] list = new SecuritiesRec_Type[1];
		BlankInfo_Type blank = request.getBlankInfo();

		securitiesRec.setBlankInfo(blank);

		SecuritiesInfo_Type securitiesInfo = new SecuritiesInfo_Type();
		securitiesInfo.setNominalAmt(getRandomDecimal());
		securitiesInfo.setNominalCurCode("RUB");

		Calendar composeDate = getRandomDate();
		Calendar termStartDate = new GregorianCalendar(composeDate.get(Calendar.YEAR), composeDate.get(Calendar.MONTH), composeDate.get(Calendar.DAY_OF_MONTH));
		termStartDate.add(Calendar.DATE, new Random().nextInt(200));
		securitiesInfo.setComposeDt(getStringDate(composeDate));
		securitiesInfo.setTermFinishDt(getStringDate(getRandomDate()));
		securitiesInfo.setTermStartDt(getStringDate(termStartDate));
		securitiesInfo.setTermLimitDt(getStringDate(getRandomDate()));

		securitiesInfo.setIncomeAmt(getRandomDecimal());
		securitiesInfo.setIncomeRate(BigDecimal.valueOf(15));
		securitiesRec.setSecuritiesInfo(securitiesInfo);

		Issuer_Type issuer = new Issuer_Type();
		BankInfoExt_Type bankInfoExt = new BankInfoExt_Type();
		bankInfoExt.setBankId("77");
		bankInfoExt.setName("0000 Дополнительный офис №0000/000 Отделение №17 Сбербанка России");
		issuer.setBankInfo(bankInfoExt);
		securitiesRec.setIssuer(issuer);

		//иноформация по договору хранения
		SecuritiesDocument_Type securitiesDocument = new SecuritiesDocument_Type();
		securitiesDocument.setDocNum("123");
		securitiesDocument.setDocType("CX");
		securitiesDocument.setDocDt(getStringDate(getRandomDate()));
		Owner_Type owner = new Owner_Type();
		bankInfoExt = new BankInfoExt_Type();
		bankInfoExt.setBankId("40");
		bankInfoExt.setName("2222 Дополнительный офис №0000/000 Отделение №01 Сбербанка России");
		ContactInfoSec_Type contactInfo = new ContactInfoSec_Type();
		PostAddr_Type addr = new PostAddr_Type();
		addr.setAddrType("Primary");
		addr.setCity("Москва");
		addr.setHouse("18");
		addr.setCorpus("A");
		contactInfo.setPostAddr(new PostAddr_Type[]{addr});
		bankInfoExt.setContactInfo(contactInfo);
		owner.setBankInfo(bankInfoExt);
		securitiesDocument.setRegistrar(owner);
		securitiesRec.setSecuritiesDocument(securitiesDocument);

		SecuritiesHolder_Type securitiesHolder = new SecuritiesHolder_Type();
		PersonInfoSec_Type personInfoSec = new PersonInfoSec_Type();
		PersonName_Type personName = new PersonName_Type();
		personName.setFirstName("client");
		personName.setLastName("client");
		personInfoSec.setPersonName(personName);
		securitiesHolder.setPersonInfo(personInfoSec);
		securitiesRec.setHolder(securitiesHolder);

		securitiesRec.setFormDt(getStringDate(getRandomDate()));

		list[0] = securitiesRec;
		securitiesInfoRs.setSecuritiesRec(list);

		IFXRs_Type response = new IFXRs_Type();
		response.setSecuritiesInfoInqRs(securitiesInfoRs);
		return response;
	}

}
