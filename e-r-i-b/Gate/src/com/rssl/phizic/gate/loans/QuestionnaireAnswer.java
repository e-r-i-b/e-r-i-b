package com.rssl.phizic.gate.loans;

/**
 * @author Omeliyanchuk
 * @ created 10.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ќтвет на вопрос анкеты
 */
public interface QuestionnaireAnswer
{
   /**
    * Domain: ExternalID
    */
   String getId();
   /**
    * ќтвет на вопрос анкеты
    * форматы
    * -строка - as is
    * -дата - yyyy.mm.dd
    * -число - nnnnn.nnnn (число знаков до и после зап€той произвольное)
    * -даньги - nnnnnnnnnnnnnn.nn (два знака после зап€той)
    *
    * Domain: Text
    */
   String getValue();	
}
