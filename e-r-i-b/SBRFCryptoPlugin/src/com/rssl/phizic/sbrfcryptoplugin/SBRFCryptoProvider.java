package com.rssl.phizic.sbrfcryptoplugin;

import com.rssl.phizic.security.crypto.*;

import java.util.Iterator;

/**
 * @author Omeliyanchuk
 * @ created 20.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class SBRFCryptoProvider implements CryptoProvider
{
	public Signature makeSignature(String certId, String data)
	{
		throw new UnsupportedOperationException();
	}

	public CheckSignatureResult checkSignature(String certId, String data, Signature signature)
	{
		throw new UnsupportedOperationException();
	}

	public void deleteCertificate(Certificate cert)
	{
		throw new UnsupportedOperationException();
	}

	public Iterator<Certificate> enumerateCertificates()
	{
		throw new UnsupportedOperationException();
	}

	public Iterator<CertificateRequest> enumerateCertRequest()
	{
		throw new UnsupportedOperationException();
	}

	public Certificate findCertificate(String certId)
	{
		throw new UnsupportedOperationException();
	}

	public Certificate getCertInFileInfo(byte[] data)
	{
		throw new UnsupportedOperationException();
	}

	public void loadCertFromFileToStorage(byte[] data)
	{
		throw new UnsupportedOperationException();
	}
}
