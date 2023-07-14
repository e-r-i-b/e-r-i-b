package com.rssl.phizic.business.loanclaim.etsm;

import com.rssl.phizgate.common.credit.bki.BKIConstants;
import com.rssl.phizgate.common.credit.etsm.ETSMLoanClaimConstants;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.StatusLoanApplicationRq;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;

/**
 * @author Erkin
 * @ created 25.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class ETSMBKITest extends TestCase
{
	private static final String ETSM_EXAMPLE = "com/rssl/phizicgate/esberibgate/loanclaim/etsm/xsd/ChargeLoanApplicationRq(13 релиз).example.xml";

	private Schema loanXSDSchema;
	private JAXBContext jaxbContext;

	@Override
	public void setUp() throws Exception
	{
		loanXSDSchema = XmlHelper.schemaByResourceNames(ETSMLoanClaimConstants.XSD_RELEASE_16, BKIConstants.XSD);
		jaxbContext = JAXBContext.newInstance(ChargeLoanApplicationRq.class, StatusLoanApplicationRq.class, EnquiryResponseERIB.class);
	}

	//тест поломан (<xs:length value="11"/> <xs:pattern value="\p{Nd}"/>)
	//ошибка проявится только в тестилке (валидация этого запроса происходит на стороне КСШ)
	public void testETSMRqGood() throws JAXBException, IOException
	{
		unmarshallWithValidation(loadETSMExample());
	}

	public void testETSMRsGood() throws JAXBException
	{
		unmarshallWithValidation(getStatusLoanApplicationRqGood());
	}

	public void testETSMRsBad() throws JAXBException
	{
		unmarshallWithoutValidation(getStatusLoanApplicationRqBad());
	}

	public void testETSMRsBadShouldThrow() throws JAXBException
	{
		try
		{
			unmarshallWithValidation(getStatusLoanApplicationRqBad());
		}
		catch (UnmarshalException expected)
		{
			return;
		}
		fail();
	}

	public void testBKIRsGood() throws JAXBException
	{
		unmarshallWithValidation(getEnquiryResponseERIBGood());
	}

	public void testBKIRsBad() throws JAXBException
	{
		unmarshallWithoutValidation(getEnquiryResponseERIBBad());
	}

	public void testBKIRsBadShouldThrow() throws JAXBException
	{
		try
		{
			unmarshallWithValidation(getEnquiryResponseERIBBad());
		}
		catch (UnmarshalException expected)
		{
			return;
		}
		fail();
	}

	private void unmarshallWithoutValidation(String xml) throws JAXBException
	{
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller.setSchema(null);
		Reader reader = new StringReader(xml);
		unmarshaller.unmarshal(reader);
	}

	private void unmarshallWithValidation(String xml) throws JAXBException
	{
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller.setSchema(loanXSDSchema);
		Reader reader = new StringReader(xml);
		unmarshaller.unmarshal(reader);
	}

	private String loadETSMExample() throws IOException
	{
		InputStream xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(ETSM_EXAMPLE);
		if (xmlStream == null)
			throw new IllegalArgumentException(ETSM_EXAMPLE);
		return IOUtils.toString(xmlStream);
	}

	private String getStatusLoanApplicationRqGood()
	{
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><StatusLoanApplicationRq><RqUID>a26e68c38d5b2b5ee9950a8fba447a93</RqUID><RqTm>2014-10-24T18:31:36.981</RqTm><OperUID>AAb80c110f5ab1e89fa348f6b516abd8</OperUID><SPName>BP_ERIB</SPName><SrcRq><RqUID>5dfff9d83071d70556d58c70a99da3c3</RqUID><RqTm>2014-10-24T18:31:36.981</RqTm></SrcRq><ApplicationStatus><Status><StatusCode>2</StatusCode><ApplicationNumber>01684568349856564966</ApplicationNumber></Status><Approval><PeriodM>60</PeriodM><Amount>450000.00</Amount><InterestRate>10.00</InterestRate></Approval></ApplicationStatus></StatusLoanApplicationRq>";
	}

	//нет обязательного <StatusCode>
	private String getStatusLoanApplicationRqBad()
	{
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><StatusLoanApplicationRq><RqUID>a26e68c38d5b2b5ee9950a8fba447a93</RqUID><RqTm>2014-10-24T18:31:36.981</RqTm><OperUID>AAb80c110f5ab1e89fa348f6b516abd8</OperUID><SPName>BP_ERIB</SPName><SrcRq><RqUID>5dfff9d83071d70556d58c70a99da3c3</RqUID><RqTm>2014-10-24T18:31:36.981</RqTm></SrcRq><ApplicationStatus><Status><ApplicationNumber>01684568349856564966</ApplicationNumber></Status><Approval><PeriodM>60</PeriodM><Amount>450000.00</Amount><InterestRate>10.00</InterestRate></Approval></ApplicationStatus></StatusLoanApplicationRq>";
	}

	private String getEnquiryResponseERIBGood()
	{
		return "<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n" +
				"<enquiryResponseERIB xmlns=\"http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo\">\n" +
				"    <RqUID>d0434c985a0abb3a1cf31eb18906dd5e</RqUID>\n" +
				"    <RqTm>2014-10-10T14:02:20.663</RqTm>\n" +
				"    <OperUID>efd9dd9584f7c8edb0af9a9c04051da3</OperUID>\n" +
				"    <Consumers>\n" +
				"    <s>\n" +
				"        <BureauScore>\n" +
				"            <confidenceFlag>1</confidenceFlag>\n" +
				"            <scoreCardType>1</scoreCardType>\n" +
				"            <scoreInterval>2</scoreInterval>\n" +
				"            <scoreNumber>4</scoreNumber>\n" +
				"        </BureauScore>\n" +
				"        <Summary>\n" +
				"          <CAISDistribution1>0</CAISDistribution1>\n" +
				"          <CAISDistribution2>0</CAISDistribution2>\n" +
				"          <CAISDistribution3>0</CAISDistribution3>\n" +
				"          <CAISDistribution4>0</CAISDistribution4>\n" +
				"          <CAISDistribution5>0</CAISDistribution5>\n" +
				"          <CAISDistribution5Plus>1</CAISDistribution5Plus>\n" +
				"          <CAISRecordsGuarantor>0</CAISRecordsGuarantor>\n" +
				"          <CAISRecordsGuarantorRecip>0</CAISRecordsGuarantorRecip>\n" +
				"          <CAISRecordsJoint>0</CAISRecordsJoint>\n" +
				"          <CAISRecordsJointRecip>0</CAISRecordsJointRecip>\n" +
				"          <CAISRecordsOwner>6</CAISRecordsOwner>\n" +
				"          <CAISRecordsOwnerRecip>6</CAISRecordsOwnerRecip>\n" +
				"          <CAISRecordsReferee>0</CAISRecordsReferee>\n" +
				"          <CAISRecordsRefereeRecip>0</CAISRecordsRefereeRecip>\n" +
				"          <CAPSDistribution1>0</CAPSDistribution1>\n" +
				"          <CAPSDistribution2>0</CAPSDistribution2>\n" +
				"          <CAPSDistribution3>1</CAPSDistribution3>\n" +
				"          <CAPSDistribution4>0</CAPSDistribution4>\n" +
				"          <CAPSDistribution5>0</CAPSDistribution5>\n" +
				"          <CAPSDistribution5Plus>2</CAPSDistribution5Plus>\n" +
				"          <CAPSLast12MonthsGuarantor>0</CAPSLast12MonthsGuarantor>\n" +
				"          <CAPSLast12MonthsJoint>0</CAPSLast12MonthsJoint>\n" +
				"          <CAPSLast12MonthsOwner>0</CAPSLast12MonthsOwner>\n" +
				"          <CAPSLast12MonthsReferee>0</CAPSLast12MonthsReferee>\n" +
				"          <CAPSLast3MonthsGuarantor>0</CAPSLast3MonthsGuarantor>\n" +
				"          <CAPSLast3MonthsJoint>0</CAPSLast3MonthsJoint>\n" +
				"          <CAPSLast3MonthsOwner>20</CAPSLast3MonthsOwner>\n" +
				"          <CAPSLast3MonthsReferee>0</CAPSLast3MonthsReferee>\n" +
				"          <CAPSLast6MonthsGuarantor>0</CAPSLast6MonthsGuarantor>\n" +
				"          <CAPSLast6MonthsJoint>0</CAPSLast6MonthsJoint>\n" +
				"          <CAPSLast6MonthsOwner>0</CAPSLast6MonthsOwner>\n" +
				"          <CAPSLast6MonthsReferee>0</CAPSLast6MonthsReferee>\n" +
				"          <CAPSRecordsGuarantor>0</CAPSRecordsGuarantor>\n" +
				"          <CAPSRecordsGuarantorBeforeFilter>0</CAPSRecordsGuarantorBeforeFilter>\n" +
				"          <CAPSRecordsJoint>0</CAPSRecordsJoint>\n" +
				"          <CAPSRecordsJointBeforeFilter>0</CAPSRecordsJointBeforeFilter>\n" +
				"          <CAPSRecordsOwner>20</CAPSRecordsOwner>\n" +
				"          <CAPSRecordsOwnerBeforeFilter>20</CAPSRecordsOwnerBeforeFilter>\n" +
				"          <CAPSRecordsReferee>0</CAPSRecordsReferee>\n" +
				"          <CAPSRecordsRefereeBeforeFilter>0</CAPSRecordsRefereeBeforeFilter>\n" +
				"          <PotentialMonthlyInstalmentsAllButOwner>0</PotentialMonthlyInstalmentsAllButOwner>\n" +
				"          <PotentialMonthlyInstalmentsOwner>50500</PotentialMonthlyInstalmentsOwner>\n" +
				"          <PotentialOutstandingBalanceAllButOwner>0</PotentialOutstandingBalanceAllButOwner>\n" +
				"          <PotentialOutstandingBalanceOwner>5017000</PotentialOutstandingBalanceOwner>\n" +
				"          <TotalMonthlyInstalmentsAllButOwner>0</TotalMonthlyInstalmentsAllButOwner>\n" +
				"          <TotalMonthlyInstalmentsOwner>50500</TotalMonthlyInstalmentsOwner>\n" +
				"          <TotalOutstandingBalanceAllButOwner>0</TotalOutstandingBalanceAllButOwner>\n" +
				"          <TotalOutstandingBalanceOwner>5017000</TotalOutstandingBalanceOwner>\n" +
				"          <WorstCurrentPayStatusOwner>3</WorstCurrentPayStatusOwner>\n" +
				"          <WorstEverPayStatusOwner>3</WorstEverPayStatusOwner>\n" +
				"          <checkOther1/>\n" +
				"          <checkOther10/>\n" +
				"          <checkOther11/>\n" +
				"          <checkOther12/>\n" +
				"          <checkOther13/>\n" +
				"          <checkOther14/>\n" +
				"          <checkOther15/>\n" +
				"          <checkOther16/>\n" +
				"          <checkOther17/>\n" +
				"          <checkOther18/>\n" +
				"          <checkOther19/>\n" +
				"          <checkOther2/>\n" +
				"          <checkOther20/>\n" +
				"          <checkOther3/>\n" +
				"          <checkOther4/>\n" +
				"          <checkOther5/>\n" +
				"          <checkOther6/>\n" +
				"          <checkOther7/>\n" +
				"          <checkOther8/>\n" +
				"          <checkOther9/>\n" +
				"        </Summary>\n" +
				"        <Warning>\n" +
				"        </Warning>\n" +
				"    </s>\n" +
				"    </Consumers>\n" +
				"    <ValidationErrors>\n" +
				"    </ValidationErrors>\n" +
				"    <errorCode>0</errorCode>\n" +
				"    <responseDate>20140131113214</responseDate>\n" +
				"    <streamID>5944130</streamID>\n" +
				"</enquiryResponseERIB>";
	}

	//нет обязательного <CAISRecordsOwner>
	private String getEnquiryResponseERIBBad()
	{
		return "<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n" +
				"<enquiryResponseERIB xmlns=\"http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo\">\n" +
				"    <RqUID>d0434c985a0abb3a1cf31eb18906dd5e</RqUID>\n" +
				"    <RqTm>2014-10-10T14:02:20.663</RqTm>\n" +
				"    <OperUID>efd9dd9584f7c8edb0af9a9c04051da3</OperUID>\n" +
				"    <Consumers>\n" +
				"    <s>\n" +
				"        <BureauScore>\n" +
				"            <confidenceFlag>1</confidenceFlag>\n" +
				"            <scoreCardType>1</scoreCardType>\n" +
				"            <scoreInterval>2</scoreInterval>\n" +
				"            <scoreNumber>4</scoreNumber>\n" +
				"        </BureauScore>\n" +
				"        <Summary>\n" +
				"          <CAISDistribution1>0</CAISDistribution1>\n" +
				"          <CAISDistribution2>0</CAISDistribution2>\n" +
				"          <CAISDistribution3>0</CAISDistribution3>\n" +
				"          <CAISDistribution4>0</CAISDistribution4>\n" +
				"          <CAISDistribution5>0</CAISDistribution5>\n" +
				"          <CAISDistribution5Plus>1</CAISDistribution5Plus>\n" +
				"          <CAISRecordsGuarantor>0</CAISRecordsGuarantor>\n" +
				"          <CAISRecordsGuarantorRecip>0</CAISRecordsGuarantorRecip>\n" +
				"          <CAISRecordsJoint>0</CAISRecordsJoint>\n" +
				"          <CAISRecordsJointRecip>0</CAISRecordsJointRecip>\n" +
				"          <CAISRecordsOwnerRecip>6</CAISRecordsOwnerRecip>\n" +
				"          <CAISRecordsReferee>0</CAISRecordsReferee>\n" +
				"          <CAISRecordsRefereeRecip>0</CAISRecordsRefereeRecip>\n" +
				"          <CAPSDistribution1>0</CAPSDistribution1>\n" +
				"          <CAPSDistribution2>0</CAPSDistribution2>\n" +
				"          <CAPSDistribution3>1</CAPSDistribution3>\n" +
				"          <CAPSDistribution4>0</CAPSDistribution4>\n" +
				"          <CAPSDistribution5>0</CAPSDistribution5>\n" +
				"          <CAPSDistribution5Plus>2</CAPSDistribution5Plus>\n" +
				"          <CAPSLast12MonthsGuarantor>0</CAPSLast12MonthsGuarantor>\n" +
				"          <CAPSLast12MonthsJoint>0</CAPSLast12MonthsJoint>\n" +
				"          <CAPSLast12MonthsOwner>0</CAPSLast12MonthsOwner>\n" +
				"          <CAPSLast12MonthsReferee>0</CAPSLast12MonthsReferee>\n" +
				"          <CAPSLast3MonthsGuarantor>0</CAPSLast3MonthsGuarantor>\n" +
				"          <CAPSLast3MonthsJoint>0</CAPSLast3MonthsJoint>\n" +
				"          <CAPSLast3MonthsOwner>20</CAPSLast3MonthsOwner>\n" +
				"          <CAPSLast3MonthsReferee>0</CAPSLast3MonthsReferee>\n" +
				"          <CAPSLast6MonthsGuarantor>0</CAPSLast6MonthsGuarantor>\n" +
				"          <CAPSLast6MonthsJoint>0</CAPSLast6MonthsJoint>\n" +
				"          <CAPSLast6MonthsOwner>0</CAPSLast6MonthsOwner>\n" +
				"          <CAPSLast6MonthsReferee>0</CAPSLast6MonthsReferee>\n" +
				"          <CAPSRecordsGuarantor>0</CAPSRecordsGuarantor>\n" +
				"          <CAPSRecordsGuarantorBeforeFilter>0</CAPSRecordsGuarantorBeforeFilter>\n" +
				"          <CAPSRecordsJoint>0</CAPSRecordsJoint>\n" +
				"          <CAPSRecordsJointBeforeFilter>0</CAPSRecordsJointBeforeFilter>\n" +
				"          <CAPSRecordsOwner>20</CAPSRecordsOwner>\n" +
				"          <CAPSRecordsOwnerBeforeFilter>20</CAPSRecordsOwnerBeforeFilter>\n" +
				"          <CAPSRecordsReferee>0</CAPSRecordsReferee>\n" +
				"          <CAPSRecordsRefereeBeforeFilter>0</CAPSRecordsRefereeBeforeFilter>\n" +
				"          <PotentialMonthlyInstalmentsAllButOwner>0</PotentialMonthlyInstalmentsAllButOwner>\n" +
				"          <PotentialMonthlyInstalmentsOwner>50500</PotentialMonthlyInstalmentsOwner>\n" +
				"          <PotentialOutstandingBalanceAllButOwner>0</PotentialOutstandingBalanceAllButOwner>\n" +
				"          <PotentialOutstandingBalanceOwner>5017000</PotentialOutstandingBalanceOwner>\n" +
				"          <TotalMonthlyInstalmentsAllButOwner>0</TotalMonthlyInstalmentsAllButOwner>\n" +
				"          <TotalMonthlyInstalmentsOwner>50500</TotalMonthlyInstalmentsOwner>\n" +
				"          <TotalOutstandingBalanceAllButOwner>0</TotalOutstandingBalanceAllButOwner>\n" +
				"          <TotalOutstandingBalanceOwner>5017000</TotalOutstandingBalanceOwner>\n" +
				"          <WorstCurrentPayStatusOwner>3</WorstCurrentPayStatusOwner>\n" +
				"          <WorstEverPayStatusOwner>3</WorstEverPayStatusOwner>\n" +
				"          <checkOther1/>\n" +
				"          <checkOther10/>\n" +
				"          <checkOther11/>\n" +
				"          <checkOther12/>\n" +
				"          <checkOther13/>\n" +
				"          <checkOther14/>\n" +
				"          <checkOther15/>\n" +
				"          <checkOther16/>\n" +
				"          <checkOther17/>\n" +
				"          <checkOther18/>\n" +
				"          <checkOther19/>\n" +
				"          <checkOther2/>\n" +
				"          <checkOther20/>\n" +
				"          <checkOther3/>\n" +
				"          <checkOther4/>\n" +
				"          <checkOther5/>\n" +
				"          <checkOther6/>\n" +
				"          <checkOther7/>\n" +
				"          <checkOther8/>\n" +
				"          <checkOther9/>\n" +
				"        </Summary>\n" +
				"        <Warning>\n" +
				"        </Warning>\n" +
				"    </s>\n" +
				"    </Consumers>\n" +
				"    <ValidationErrors>\n" +
				"    </ValidationErrors>\n" +
				"    <errorCode>0</errorCode>\n" +
				"    <responseDate>20140131113214</responseDate>\n" +
				"    <streamID>5944130</streamID>\n" +
				"</enquiryResponseERIB>";
	}
}
