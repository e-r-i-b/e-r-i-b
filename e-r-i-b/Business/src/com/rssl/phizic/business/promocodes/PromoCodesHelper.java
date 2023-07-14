package com.rssl.phizic.business.promocodes;

import java.math.BigInteger;

/**
 * @author gladishev
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodesHelper
{
	private static final BigInteger CONST = new BigInteger(Long.toString(58493173L));
	/**
	 * генерация промокода
	 *
	 * Промо-код формируется по следующему алгоритму:
	 *  1.	Берем идентификатор документа в ЕРИБ.
	 *  2.	Умножаем идентификатор на константу 58493173 и получаем промежуточный результат 1 (ПР1).
	 *  3.	Берем младшие 8 разрядов промежуточного результата 1 (ПР1), например 93847261
	 *  4.	Рассчитываем первый контрольный разряд (КР1) = 9 – младший разряд суммы цифр промежуточного результата (ПР1), например 9 = 9 – 0
	 *  5.	Помещаем первый контрольный разряд (КР1) между 4 и 5 цифрами промежуточного результата 1 (ПР1) и получаем промежуточный результат 2 (ПР2), например, 938407261
	 *  6.	Для промежуточного результата 2 (ПР2) рассчитываем check digit, как это делается для номера карты (ЧД).  Например, 4.
	 *  7.	Помещаем ЧД между 5 и 6 цифрами промежуточного результата 2 (ПР2), например 9384047261
	 *  8.	Полученное  десятизначное число является промо-кодом, который отображается клиентам.
	 *
	 * @param documentId - id документа
	 * @return промо-код
	 */
	public static String generatePromoCode(Long documentId)
	{
		//2. Умножаем идентификатор на константу 58493173 и получаем промежуточный результат 1 (ПР1).
        BigInteger pr1BigInt = new BigInteger(documentId.toString()).multiply(CONST);

        //3. Берем младшие 8 разрядов промежуточного результата 1 (ПР1), например 93847261
        String pr1Tmp = pr1BigInt.toString();
        String pr1 = pr1Tmp.substring(pr1Tmp.length()-8);

        //4. Рассчитываем первый контрольный разряд (КР1) = 9 – младший разряд суммы цифр промежуточного результата (ПР1), например 9 = 9 – 0
        String kr1 = Integer.toString(9 - getDigitsSum(pr1)%10);

        //5. Помещаем первый контрольный разряд (КР1) между 4 и 5 цифрами промежуточного результата 1 (ПР1) и получаем промежуточный результат 2 (ПР2), например, 938407261
        StringBuilder pr2Tmp = new StringBuilder(pr1);
        pr2Tmp.insert(4, kr1);
        String pr2 = pr2Tmp.toString();

        //6. Для промежуточного результата 2 (ПР2) рассчитываем check digit, как это делается для номера карты (ЧД).  Например, 4.
        int cd = calculateCheckDigit(pr2);

        //7. Помещаем ЧД между 5 и 6 цифрами промежуточного результата 2 (ПР2), например 9384047261
        StringBuilder result = new StringBuilder(pr2);
        result.insert(5, cd);

        //8. Полученное  десятизначное число является промо-кодом, который отображается клиентам.
        return result.toString();
	}

	private static int getDigitsSum(String numberStr)
    {
        int result = 0;
        for (Character ch : numberStr.toCharArray())
        {
            result += Integer.parseInt(ch.toString());
        }

        return result;
    }

    /**
     * 9     3     8     4     0     x     7     2     6     1     is  pr2

      *2          *2          *2          *2          *2

      18          16          0           14          12
                                (x заменяем нулем)
     1 + 8 + 3 + 1 + 6 + 4 + 0 +   0  +  1 + 4 + 2 + 1 + 2 + 1 = 34, т.е. result=6 (40-34)
     */
    private static int calculateCheckDigit(String pr2)
    {
        if (pr2==null || pr2.length()!=9)
            throw new RuntimeException("Некоректное число ПР2");

        StringBuilder pr2WithZero = new StringBuilder(pr2);
        pr2WithZero.insert(5, '0');
        int sum = 0;
        int i=0;
        int tmpInt;
        for (Character ch : pr2WithZero.toString().toCharArray())
        {
            tmpInt= Integer.parseInt(ch.toString());
            if (i++%2==0)
            {
                //удваиваем числа, стоящие на позициях 0,2,4...
                tmpInt = tmpInt*2;
                if (tmpInt >= 10)
                    tmpInt -=9; //удвоенное число может быть не больше 18, поэтому сумма цифр 2-x значного числа = само число - 9
            }

            sum += tmpInt;
        }

        return sum%10 == 0 ? 0 : 10 - sum%10;
    }
}
