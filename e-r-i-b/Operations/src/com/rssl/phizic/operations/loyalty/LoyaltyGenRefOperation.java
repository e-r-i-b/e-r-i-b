package com.rssl.phizic.operations.loyalty;

import com.Ostermiller.util.Base64;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loyalty.LoyaltyConfig;
import com.rssl.phizic.config.loyalty.LoyaltyHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.CihperHelper;
import com.rssl.phizic.utils.StringHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * User: Moshenko
 * Date: 15.07.2011
 * Time: 11:05:56
 * �������� ��������� URL ��� �������� �� ���� ��������� "����������"
 */
public class LoyaltyGenRefOperation extends OperationBase
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	protected static CihperHelper cihperHelper = new CihperHelper();

	private static String ERIB_STORE = "javax.net.ssl.eribStore";
	private static String SAT_CONST = "SATv1";
	private static String PARAM_CONST = "?sat=";
	private static String SIGN_PARAM = "&sign=";
    private static String DELIMITER = "/";
    private static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	private static String SHA1_ERROR = "����������: ������ ��� ������� �������� �������� ��������� ������(SHA1)";
	private static String INPUT_STEAM_ERROR = "����������: ������ ��� ������� �������� ������� ����� ����� ���������";
	private static String CERT_ERROR = "����������: ������ ��� ������� �������� ����������";
	private static String SIGN_ERROR = "����������: ������ ��� ������� �������� ������ �������";
	private static String CIPHER_ERROR = "����������: ������ ��� ������� �������� ������ ��������";
	private static String RSA_SIGN_ERROR = "����������: ������ ��� ������� ���������� ������� ������";
	private static String RSA_ERROR = "����������: ������ ��� ������� ���������� ���������� ������(RSA)";
	private static String NOCARD_ERROR = "����������: �� ������� �������� ����� ����� �������. Login id = ";
	private static String CERT_NOT_FOUND = "����������: ���������� �� ������ ";

	private LoyaltyConfig config = ConfigFactory.getConfig(LoyaltyConfig.class);
	private static String eribStrorePath = System.getProperty(ERIB_STORE);

	/**
	 *�������������� ������ URL
	 */
	private String ref = null;

	public String getRef()
	{
		return ref;
	}

	public void initialize() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson person = personData.getPerson();
		Login login = person.getLogin();

		//��������� N-����� �� ������� ������ ����� � ����
		String cardNumber = login.getLastLogonCardNumber();

		if (!StringHelper.isEmpty(cardNumber))
		{
			//��������� SAT
			String satString = getSat(LoyaltyHelper.generateHash(cardNumber));

			String certAlias = config.getLoyaltyPubCert();   //   alias  ��������� �����
			String privCertAlias = config.getLoyaltyPrivCert(); // alias   ��������� �����
			if (StringHelper.isEmpty(eribStrorePath))
				eribStrorePath =  config.getStorePath();// ���� � ���������

			String eribStrorePassword = config.getStorePassword(); //������ � ���������

            String satParam = getSatParam(satString, eribStrorePath, eribStrorePassword, certAlias);
            String signParam = getSignParam(satString, eribStrorePath, eribStrorePassword, privCertAlias);
			if (satParam == null || signParam == null)
				return;
			ref = getRef(satParam,  signParam);
		}
		else
		{
			log.debug(NOCARD_ERROR + login.getUserId());
		}
	}

	private String getSatParam(String satString, String eribStrorePath, String eribStrorePassword, String certAlias)
	{
		InputStream inputStream = null;
		//��������� ����������� � �������� ������
		X509Certificate cert = null;
		try
		{
			inputStream = new FileInputStream(eribStrorePath);
			cert = cihperHelper.getCertX509(inputStream,eribStrorePassword, config.getStoreType(),certAlias);
		}
		catch (FileNotFoundException e)
		{
			log.error(INPUT_STEAM_ERROR,e);
			return null;
		}
		catch (CertificateException e)
		{
			log.error(CERT_ERROR+" CertificateException",e);
			return null;
		}
		catch (IOException e)
		{
			log.error(CERT_ERROR+" IOException",e);
			return null;
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error(CERT_ERROR+ " NoSuchAlgorithmException",e);
			return null;
		}
		catch (KeyStoreException e)
		{
			log.error(CERT_ERROR+ " KeyStoreException",e);
			return null;
		}
		finally {
			try
			{
				if (inputStream != null)
					inputStream.close();
			}
			catch (IOException ex)
			{
				log.error(INPUT_STEAM_ERROR, ex);
			}
		}
		if (cert==null)
		{
			log.error(CERT_NOT_FOUND);
			return null;
		}
		//�������� ��������(RSA)
		Cipher cipher = null;
		try
		{
			cipher = cihperHelper.getChipherRSA(cert, Cipher.ENCRYPT_MODE);
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error(CIPHER_ERROR +" NoSuchAlgorithmException",e);
			return null;
		}
		catch (NoSuchPaddingException e)
		{
			log.error(CIPHER_ERROR +" NoSuchPaddingException",e);
			return null;
		}
		catch (InvalidKeyException e)
		{
			log.error(CIPHER_ERROR + " InvalidKeyException",e);
			return null;
		}
		catch (InvalidAlgorithmParameterException e)
		{
			log.error(CIPHER_ERROR  + " InvalidAlgorithmParameterException",e);
			return null;
		}

		//�������� ������������� ������
		byte[] cipherData = null;
		try
		{
			cipherData  = cipher.doFinal(satString.getBytes(),0,satString.getBytes().length);
		}
		catch (IllegalBlockSizeException e)
		{
			log.error(RSA_ERROR + " IllegalBlockSizeException",e);
			return null;
		}
		catch (BadPaddingException e)
		{
			log.error(RSA_ERROR + " BadPaddingException",e);
			return null;
		}

		return getBase64Url(cipherData);
	}

	private String getSignParam(String satString,String eribStrorePath, String eribStrorePassword, String privCertAlias)
	{
		RSAPrivateKey privateKey = null;
		InputStream inputStream = null;
		//��������� ��������� �����
		try
		{
			inputStream = new FileInputStream(eribStrorePath);
			privateKey = cihperHelper.getPrivateKey(inputStream,eribStrorePassword, config.getStoreType(),privCertAlias);
		}
		catch (FileNotFoundException e)
		{
			log.error(INPUT_STEAM_ERROR,e);
			return null;
		}
		catch (CertificateException e)
		{
			log.error(CERT_ERROR+" CertificateException",e);
			return null;
		}
		catch (IOException e)
		{
			log.error(CERT_ERROR+" IOException",e);
			return null;
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error(CERT_ERROR+ " NoSuchAlgorithmException",e);
			return null;
		}
		catch (KeyStoreException e)
		{
			log.error(CERT_ERROR+ " KeyStoreException",e);
			return null;
		}catch (UnrecoverableKeyException e)
		{
			log.error(CERT_ERROR + " UnrecoverableKeyException",e);
			return null;
		}
		finally
		{
			try
			{
				if (inputStream != null)
					inputStream.close();
			}
			catch (IOException ex)
			{
				log.error(INPUT_STEAM_ERROR, ex);
			}
		}
		if (privateKey==null)
		{
			log.error(CERT_NOT_FOUND);
			return null;
		}

		Signature sig = null;
		try
		{
			sig = cihperHelper.getSignatureRSA(privateKey);
			sig.update(satString.getBytes());
		}
		catch (SignatureException e)
		{
			log.error(SIGN_ERROR + " SignatureException",e);
			return null ;
		}
		catch (InvalidKeyException e)
		{
			log.error(SIGN_ERROR + " InvalidKeyException",e);
			return null ;
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error(SIGN_ERROR + " NoSuchAlgorithmException",e);
			return null ;
		}

		//�������� ����������� ������
		byte[] cipherData = null;
		try
		{
			cipherData = sig.sign();
		}
		catch (SignatureException e)
		{
			log.error(RSA_SIGN_ERROR + " SignatureException",e);
			return null;
		}

		return  getBase64Url(cipherData);
	}
	 /**
     * �������� Base64 ������������� ������
     * @param inputString
     * @return
     */
    protected String getBase64Url(byte[] inputString)
	{
		 byte[] base64 =  Base64.encode(inputString);
 	     return new String(base64 ).replace('+', '-').replace('/','_').replace("=", "%3D");
	}

    /**
     * ������������� sat
     * /SATv1/cur_date/card_id
     * @param card_id
     * @return
     */
    private String getSat(String card_id) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        String curDate = df.format(new Date());
        StringBuilder build = new StringBuilder();
	    build.append(DELIMITER);
        build.append(SAT_CONST);
        build.append(DELIMITER);
        build.append(curDate);
        build.append(DELIMITER);
        build.append(card_id);
        return build.toString();
    }

    /**
     * �������� ������ ��������� � "����������"
     * @param base64String
     * @return
     */
    private String getRef(String base64String, String sign)
    {
        StringBuilder build = new StringBuilder();
        build.append(config.getLoyaltyUrl());
        build.append(PARAM_CONST);
        build.append(base64String);
	    build.append(SIGN_PARAM);
        build.append(sign);
        return  build.toString();
    }
}
