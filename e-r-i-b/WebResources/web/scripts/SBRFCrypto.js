setRSCryptoName('SBER');

// ctor
function SBERCrypto()
{
	var initError = true;
}

SBERCrypto.prototype = new RSCrypto;

SBERCrypto.prototype.sign = function(text, cert)
{
	return text;
}

SBERCrypto.prototype.getCertificate = function(certId)
{
	return null;
}

SBERCrypto.prototype.throwIfError = function()
{
}

SBERCrypto.prototype.CreateCertRequest = function(fullName)
{
	var result = new Array(2);

	result['request'] = "123";
	result['fileName'] = "123";

	return result;
}

SBERCrypto.prototype.InstallCertAnswer = function(answer, answerFile)
{
}

SBERCrypto.prototype.PrintCertificate = function(id)
{
}

SBERCrypto.prototype.PrintCertDemand = function(id)
{
}

SBERCrypto.prototype.PreviewCertificate = function(id)
{
}

SBERCrypto.prototype.PreviewCertDemand = function(id)
{

}
