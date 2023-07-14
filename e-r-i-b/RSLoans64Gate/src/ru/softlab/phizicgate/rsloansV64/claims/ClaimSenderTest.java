package ru.softlab.phizicgate.rsloansV64.claims;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.loans.LoanOpeningClaim;
import com.rssl.phizic.gate.loans.QuestionnaireAnswer;
import com.rssl.phizic.gate.payments.owner.ClientInfo;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import ru.softlab.phizicgate.rsloansV64.junit.RSLoans64GateTestCaseBase;

import java.util.*;

/**
 * @author Omeliyanchuk
 * @ created 14.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class ClaimSenderTest extends RSLoans64GateTestCaseBase
{

	protected List<QuestionnaireAnswer> getBaseAnswers()
	{
		List<QuestionnaireAnswer> answers = new ArrayList<QuestionnaireAnswer>();
		/*answers.add( new QuestionnaireAnswerTestImpl("||10121"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10054"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10187"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10048"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10188"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10111"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10019"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10123"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10027"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10179"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10022"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10122"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10151"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10145"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10132"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10020"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10185"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10016"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10161"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10116"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10150"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10189"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10131"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10139"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10184"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10168"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10109"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10055"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10028"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10106"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10160"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10023"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10127"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10165"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10119"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10143"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10032"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10052"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10146"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10177"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10015"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10125"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10152"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10033"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10175"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10167"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10134"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10024"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10142"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10117"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10136"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10163"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10021"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10049"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10141"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10181"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10159"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10147"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10178"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10156"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10196"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10057"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10164"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10149"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10056"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10182"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10107"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10174"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10128"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10180"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10018"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10135"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10050"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10186"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10137"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10053"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10148"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10166"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10105"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10017"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10118"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10158"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10029"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10170"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10031"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10140"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10126"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10124"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10034"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10138"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10110"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10051"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10157"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10030"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10133"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10120"," ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10176"," ") );*/
		answers.add( new QuestionnaireAnswerTestImpl("CreditTypeID_Ref||","1059 ") );
		answers.add( new QuestionnaireAnswerTestImpl("LCreditDuration||","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("LCTypeDuration||","0 ") );
		answers.add( new QuestionnaireAnswerTestImpl("SUMOBJECT||","72000.00 ") );
		answers.add( new QuestionnaireAnswerTestImpl("LPercRate||","10 ") );
		answers.add( new QuestionnaireAnswerTestImpl("LSelfSum||","7200.00 ") );
		answers.add( new QuestionnaireAnswerTestImpl("LCreditSum||","64800.00 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10085","35000.00 ") );
		return answers;
	}

	protected List<QuestionnaireAnswer> getAnswers(List<QuestionnaireAnswer> answers)
	{
		answers.add( new QuestionnaireAnswerTestImpl("||10071","2) Город ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10042","Харьков ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10058","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("PlaceWork||","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10001","ПК3 10-10-10 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10074","Васильевская ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10075","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||12002","2002|Иванов И.И. ") );
		answers.add( new QuestionnaireAnswerTestImpl("PostIndex||","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10062","2) Город ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10144","1) Нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10045","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10102","2) Без участия ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10108","1) Нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10065","Васильевская ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10190","12312 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10059","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10041","1) мужской ") );
		answers.add( new QuestionnaireAnswerTestImpl("PhoneNumber2||","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("city||","Москва ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10060","89091212112 ") );
		answers.add( new QuestionnaireAnswerTestImpl("orgNumber||","777 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10099","5) Информатика, телекоммуникации ") );
		answers.add( new QuestionnaireAnswerTestImpl("PostIndexF||","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10063","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10098","3) свыше 3 лет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10090","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10093","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10068","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10103","2) Рук./Зам.рук.подразд. ") );
		answers.add( new QuestionnaireAnswerTestImpl("Born||","1992.11.11 ") );
		answers.add( new QuestionnaireAnswerTestImpl("Fax||","89091212112 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10064","Москва ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10193","1234567890|Адрес1, адрес2 ") );
		answers.add( new QuestionnaireAnswerTestImpl("corp||","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10081","3) высшее ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10047","12.12.1967 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10073","Москва ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10043","2) Иное ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10014","ВАЗИК ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10025","70000.00 ") );
		answers.add( new QuestionnaireAnswerTestImpl("cityF||","Москва ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10069","3) свыше 5 лет ") );
		answers.add( new QuestionnaireAnswerTestImpl("paperIssuedDate||","1967.12.12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("PhoneNumber||","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10070","1) Да ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10086","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10089","2) Город ") );
		answers.add( new QuestionnaireAnswerTestImpl("region||","2) Город ") );
		answers.add( new QuestionnaireAnswerTestImpl("house||","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10087","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10044","1212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("Name1||","Овечкин1 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10035","Овечкин1 ") );
        answers.add( new QuestionnaireAnswerTestImpl("Name3||","Петрович ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10037","Петрович ") );
	    answers.add( new QuestionnaireAnswerTestImpl("Name2||","Иван ") );
		answers.add( new QuestionnaireAnswerTestImpl("Name||","Овечкин1 Иван Петрович ") );
		answers.add( new QuestionnaireAnswerTestImpl("ShortName||","Овечкин1 И. П. ") );
		answers.add( new QuestionnaireAnswerTestImpl("paperSeries||","2222 ") );
		answers.add( new QuestionnaireAnswerTestImpl("paperIssuerCode||","2222222 ") );
		answers.add( new QuestionnaireAnswerTestImpl("street||","Васильевская ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10036","Иван ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10082","1) работаю/служу ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10079","1) нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10113","1) Нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("isMale||","X ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10067","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10191","Иванова ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10094","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("paperIssuer||","ОВД ") );
		answers.add( new QuestionnaireAnswerTestImpl("houseF||","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10100","2) Негосударствен. ") );
		answers.add( new QuestionnaireAnswerTestImpl("||12007","подлежит рассмотрению ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10046","ОВД ") );
		answers.add( new QuestionnaireAnswerTestImpl("||12001","YES ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10092","Петровская ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10096","1) Участие в осн.деятельности ") );
		answers.add( new QuestionnaireAnswerTestImpl("loansId||","14232 ") );
		answers.add( new QuestionnaireAnswerTestImpl("corpF||","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10195","2) Нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10095","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10077","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10080","1) нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10091","Москва ") );
		answers.add( new QuestionnaireAnswerTestImpl("regionF||","2) Город ") );
		answers.add( new QuestionnaireAnswerTestImpl("flatF||","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10038","1982.11.11 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10076","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10192","1234567890|Тест ") );
		answers.add( new QuestionnaireAnswerTestImpl("flat||","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10066","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("streetF||","Васильевская ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10013","Машина ") );
		answers.add( new QuestionnaireAnswerTestImpl("BirsPlase||","Харьков ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10088","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10072","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10097","3) свыше 5 лет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10112","1) Нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10078","1) Женат/Замужем ") );
		answers.add( new QuestionnaireAnswerTestImpl("FNCash||","1234567890 ") );
		answers.add( new QuestionnaireAnswerTestImpl("paperNumber||","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10101","1) Коммерч. ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10026","1 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10083","5) от 30001 руб. до 300000 руб. ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10104","2) Кредиты не брались ") );
		answers.add( new QuestionnaireAnswerTestImpl("||12003","2007-12-27 15:25 ") );
		return answers;
	}

	protected List<QuestionnaireAnswer> getGuarantorAnswers(List<QuestionnaireAnswer> answers)
	{
		answers.add( new QuestionnaireAnswerTestImpl("||10071","2) Город ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10042","Москва ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10058","105105 ") );
		//answers.add( new QuestionnaireAnswerTestImpl("PlaceWork||","11 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10001","ПК3 10-10-10 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10074","Пушкинская ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10075","17 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||12002","2002|Иванов И.И. ") );
		//answers.add( new QuestionnaireAnswerTestImpl("PostIndex||","105105 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10062","2) Город ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10144","1) Нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10045","105105 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10102","2) Без участия ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10108","1) Нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10065","Пушкинская ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10190","12312 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10059","105105 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10041","1) мужской ") );
		//answers.add( new QuestionnaireAnswerTestImpl("PhoneNumber2||","343343 ") );
		//answers.add( new QuestionnaireAnswerTestImpl("city||","Москва ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10060","89091212112 ") );
		//answers.add( new QuestionnaireAnswerTestImpl("orgNumber||","777 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10099","5) Информатика, телекоммуникации ") );
		//answers.add( new QuestionnaireAnswerTestImpl("PostIndexF||","105105 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10063","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10098","3) свыше 3 лет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10090","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10093","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10068","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10103","2) Рук./Зам.рук.подразд. ") );
		//answers.add( new QuestionnaireAnswerTestImpl("Born||","10.10.1962 ") );
		//answers.add( new QuestionnaireAnswerTestImpl("Fax||","89091212152 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10064","Москва ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10193","1234567890|Адрес1, адрес2 ") );
		//answers.add( new QuestionnaireAnswerTestImpl("corp||","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10081","3) высшее ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10047","1967.12.12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10073","Москва ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10043","2) Иное ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10014","ВАЗИК ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10025","70000.00 ") );
		//answers.add( new QuestionnaireAnswerTestImpl("cityF||","Москва ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10069","3) свыше 5 лет ") );
		//answers.add( new QuestionnaireAnswerTestImpl("paperIssuedDate||","12.12.1967 ") );
		//answers.add( new QuestionnaireAnswerTestImpl("PhoneNumber||","127272 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10070","1) Да ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10086","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10089","2) Город ") );
		//answers.add( new QuestionnaireAnswerTestImpl("region||","2) Город ") );
		//answers.add( new QuestionnaireAnswerTestImpl("house||","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10087","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10044","1212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("Name1||","Кирсанов ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10035","Кирсанов ") );
        answers.add( new QuestionnaireAnswerTestImpl("Name3||","Михайлович ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10037","Михайлович ") );
	    answers.add( new QuestionnaireAnswerTestImpl("Name2||","Роман ") );
		answers.add( new QuestionnaireAnswerTestImpl("Name||","Кирсанов Роман Михайлович ") );
		answers.add( new QuestionnaireAnswerTestImpl("ShortName||","Кирсанов Р. М. ") );
		answers.add( new QuestionnaireAnswerTestImpl("paperSeries||","4605 ") );
		//answers.add( new QuestionnaireAnswerTestImpl("paperIssuerCode||","121139 ") );
		answers.add( new QuestionnaireAnswerTestImpl("paperNumber||","121139 ") );
		/*answers.add( new QuestionnaireAnswerTestImpl("street||","Пушкинская ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10036","Александр ") );*/
		answers.add( new QuestionnaireAnswerTestImpl("||10082","1) работаю/служу ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10079","1) нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10113","1) Нет ") );
		//answers.add( new QuestionnaireAnswerTestImpl("isMale||","X ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10067","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10191","Пушкина ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10094","12 ") );
		/*answers.add( new QuestionnaireAnswerTestImpl("paperIssuer||","ОВД ") );
		answers.add( new QuestionnaireAnswerTestImpl("houseF||","12 ") );*/
		answers.add( new QuestionnaireAnswerTestImpl("||10100","2) Негосударствен. ") );
		answers.add( new QuestionnaireAnswerTestImpl("||12007","подлежит рассмотрению ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10046","ОВД ") );
		answers.add( new QuestionnaireAnswerTestImpl("||12001","YES ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10092","Петровская ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10096","1) Участие в осн.деятельности ") );
		/*answers.add( new QuestionnaireAnswerTestImpl("loansId||","14232 ") );
		answers.add( new QuestionnaireAnswerTestImpl("corpF||","12 ") );*/
		answers.add( new QuestionnaireAnswerTestImpl("||10195","2) Нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10095","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10077","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10080","1) нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10091","Москва ") );
		/*answers.add( new QuestionnaireAnswerTestImpl("regionF||","2) Город ") );
		answers.add( new QuestionnaireAnswerTestImpl("flatF||","12 ") );*/
		answers.add( new QuestionnaireAnswerTestImpl("||10038","1962.10.10 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10076","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10192","1234567890|Тест ") );
		//answers.add( new QuestionnaireAnswerTestImpl("flat||","12 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10066","12 ") );
		//answers.add( new QuestionnaireAnswerTestImpl("streetF||","Васильевская ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10013","Машина ") );
		//answers.add( new QuestionnaireAnswerTestImpl("BirsPlase||","Харьков ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10088","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10072","121212 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10097","3) свыше 5 лет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10112","1) Нет ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10078","1) Женат/Замужем ") );
		//answers.add( new QuestionnaireAnswerTestImpl("FNCash||","1234567890 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10101","1) Коммерч. ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10026","1 ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10083","5) от 30001 руб. до 300000 руб. ") );
		answers.add( new QuestionnaireAnswerTestImpl("||10104","2) Кредиты не брались ") );
		answers.add( new QuestionnaireAnswerTestImpl("||12003","2007-12-27 15:25 ") );
		return answers;
	}

	public void testSendLoanClaim() throws Exception
	{
		LoanOpeningClaimTestImpl claimG = new LoanOpeningClaimTestImpl();
		claimG.setQuestionnaireIterator(getGuarantorAnswers(getBaseAnswers()));

		List<LoanOpeningClaim> listG = new ArrayList<LoanOpeningClaim>();
		listG.add(claimG);

		LoanOpeningClaimTestImpl claim = new LoanOpeningClaimTestImpl();
		claim.setQuestionnaireIterator(getAnswers(getBaseAnswers()));
		claim.setGuarantorClaimsIterator(listG);		

		LoanOpeningClaimSender sender = new LoanOpeningClaimSender();
		sender.send( claim);
	}

	private class QuestionnaireAnswerTestImpl implements QuestionnaireAnswer
	{
		String id;
		String value;

		public QuestionnaireAnswerTestImpl(String id, String value)
		{
			this.id = id;
			this.value = value;
		}

		public String getId()
		{
			return id;
		}

		public String getValue()
		{
			return value;
		}
	}

	private class LoanOpeningClaimTestImpl implements LoanOpeningClaim
	{

		private String claimNumber;
		private List<QuestionnaireAnswer> answers;
		private List<LoanOpeningClaim> guarantors;
		private String externalId;
		private Calendar executionDate;

		public Money getApprovedAmount()
		{
			return null;
		}

		public DateSpan getApprovedDuration()
		{
			return null;
		}

		public String getClaimNumber()
		{
			return claimNumber;
		}

		public void setClaimNumber(String claimNumber)
		{
			this.claimNumber = claimNumber;
		}
		
		public String getConditionsId()
		{
			return null;
		}

		public DateSpan getDuration()
		{
			return null;
		}

		public Iterator<LoanOpeningClaim> getGuarantorClaimsIterator()
		{
			return guarantors.iterator();
		}

		public void setGuarantorClaimsIterator(List<LoanOpeningClaim> _guarantors)
		{
			guarantors = _guarantors;
		}

		public Money getLoanAmount()
		{
			return null;
		}

		public Money getObjectAmount()
		{
			return null;
		}

		public String getOfficeExternalId()
		{
			return null;
		}

		public Iterator<QuestionnaireAnswer> getQuestionnaireIterator()
		{
			return answers.iterator();
		}

		public void setQuestionnaireIterator(List<QuestionnaireAnswer> answers)
		{
			this.answers = answers;
		}

		public Money getSelfAmount()
		{
			return null;
		}

		public void setApprovedAmount(Money amount)
		{

		}

		public void setApprovedDuration(DateSpan dateSpan)
		{

		}

		public String getExternalId()
		{
			return externalId;
		}

		public State getState()
		{
			return null;
		}

		public void setExternalId(String _externalId)
		{
			externalId = _externalId;
		}

		public void setState(State state)
		{

		}

		public Money getCommission()
		{
			return null;
		}

		public CommissionOptions getCommissionOptions()
		{
			return null;
		}

		public ClientInfo getClientInfo()
		{
			return null;
		}

		public EmployeeInfo getCreatedEmployeeInfo() throws GateException
		{
			return null;
		}

		public EmployeeInfo getConfirmedEmployeeInfo() throws GateException
		{
			return null;
		}

		public void setConfirmedEmployeeInfo(EmployeeInfo info)
		{

		}

		public CreationType getClientCreationChannel()
		{
			return null;
		}

		public CreationType getClientOperationChannel()
		{
			return null;
		}

		public void setClientOperationChannel(CreationType channel)
		{

		}

		public CreationType getAdditionalOperationChannel()
		{
			return null;
		}

		public void setAdditionalOperationChannel(CreationType channel)
		{

		}

		public String getDocumentNumber()
		{
			return null;
		}

		public Calendar getAdmissionDate()
		{
			return null;
		}

		public boolean isTemplate()
		{
			return false;
		}

		public List<WriteDownOperation> getWriteDownOperations()
		{
			return null;
		}

		public void setWriteDownOperations(List<WriteDownOperation> list)
		{
		}

		public Calendar getClientCreationDate()
		{
			return null;
		}

		public Calendar getClientOperationDate()
		{
			return null;
		}

		public void setClientOperationDate(Calendar clientOperationDate)
		{

		}

		public Calendar getAdditionalOperationDate()
		{
			return null;
		}

		public String getExternalOwnerId()
		{
			return null;
		}

		public void setExternalOwnerId(String externalOwnerId)
		{
			
		}

		public Long getId()
		{
			return new Long(1);
		}

		public Long getInternalOwnerId()
		{
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}

		public Class<? extends GateDocument> getType()
		{
			return null;
		}

		public FormType getFormType()
		{
			return null;
		}

		public void setCommission(Money commission)
		{

		}

		public Office getOffice()
		{
			return null;	
		}

		public void setOffice(Office office)
		{

		}

		public Calendar getExecutionDate()
		{
			return executionDate;
		}

		public void setExecutionDate(Calendar executionDate)
		{
			this.executionDate = executionDate;
		}

		public String getMbOperCode()
		{
			return null;
		}

		public void setMbOperCode(String mbOperCode)
		{
		}

		public Long getSendNodeNumber()
		{
			return null;
		}

		public void setSendNodeNumber(Long nodeNumber)
		{
		}

		public TemplateInfo getTemplateInfo()
		{
			return null;
		}

		public void setTemplateInfo(TemplateInfo templateInfo)
		{

		}

		public Map<String, String> getFormData() throws GateException
		{
			return null;
		}

		public void setFormData(Map<String, String> formData)
		{

		}
	}
}
