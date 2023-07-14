package com.rssl.phizic.jcpcryptoplugin;

import com.rssl.phizic.security.crypto.CheckSignatureResult;
import com.rssl.phizic.security.crypto.CryptoConstants;
import com.rssl.phizic.security.crypto.Signature;
import com.rssl.phizic.utils.resources.ResourceHelper;
import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;

import java.util.NoSuchElementException;

/**
 * @author Erkin
 * @ created 23.12.2010
 * @ $Author$
 * @ $Revision$
 */
@Deprecated
@SuppressWarnings({"JavaDoc"})
public class FNCXmlTest extends TestCase
{
	private JCPCryptoProvider provider;

	///////////////////////////////////////////////////////////////////////////

	protected void setUp() throws Exception
	{
		super.setUp();
		provider = new JCPCryptoProvider();
	}

	public void test() throws Exception
	{
		System.out.println("***** Проверка пакета документов ФНС, подписанных ЭЦП *****");

		String data = ResourceHelper.loadResourceAsString("fnc.xml", "UTF8");
		System.out.println("Пакет:\n" + data);
		System.out.println();

		Scanner docScanner = new Scanner(data);
		while (true)
		{
			try
			{
				String docTag = docScanner.next("<ED101", "</ED101>");
				System.out.println("Документ:\n" + docTag);

				String signatureTag = new Scanner(docTag).next("<dsig:SigValue>", "</dsig:SigValue>");
				String signatureAsBase64 = signatureTag.substring(
						"<dsig:SigValue>".length(), signatureTag.length()-"</dsig:SigValue>".length());
				System.out.println("Подпись: " + signatureAsBase64);

				Signature signature = Signature.fromBase64(signatureAsBase64);
				System.out.println("Подпись успешно прочитана");

				docTag = StringUtils.remove(docTag, signatureTag);
				System.out.println("Документ без подписи:\n" + docTag);

				CheckSignatureResult result = provider.checkSignature(CryptoConstants.FNS_JCP_PUBLIC_SERTIFICATE_ID, docTag, signature);
				if (result.isSuccessful())
					System.out.println("Документ и подпись верны");
				else System.out.println("Документ и подпись не верны");
				System.out.println();
			}
			catch (NoSuchElementException ignored)
			{
				break;
			}
		}
	}

	private static class Scanner
	{
		private final String data;

		private int index;

		private Scanner(String data)
		{
			this.data = data;
			index = 0;
		}

		private String next(String begin, String end) throws NoSuchElementException
		{
			int beginIndex = data.indexOf(begin, index);
			if (beginIndex < 0)
				throw new NoSuchElementException();

			int endIndex = data.indexOf(end, beginIndex + begin.length());
			if (endIndex < 0)
				throw new NoSuchElementException();
			endIndex += end.length();

			index = endIndex;
			
			return data.substring(beginIndex, endIndex);
		}
	}
}
