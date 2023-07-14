
setRSCryptoName('Dummy');

// ctor
function DummyCrypto()
{
}

DummyCrypto.prototype = new RSCrypto;

DummyCrypto.prototype.sign = function(text, cert)
{
	var d = new Date();
	var s = d.getYear() + "." + d.getMonth() + "." + d.getDay() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
	return text.length + "\t" + s + "\t" + cert.id;
}

DummyCrypto.prototype.getCertificate = function(certId)
{
	return new RSCertificate(certId, "������ ���� ��������", new Date(), new Date());
}

DummyCrypto.prototype.CreateCertRequest = function(fullName)
{
	//�� �����������
	return null;
}

DummyCrypto.prototype.InstallCertAnswer = function(answer, answerFile)
{
	//�� �����������
	return null;
}

DummyCrypto.prototype.PrintCertificate = function(id)
{
	//�� �����������
	return null;
}

DummyCrypto.prototype.PrintCertDemand = function(id)
{
	//�� �����������
	return null;
}

DummyCrypto.prototype.PreviewCertificate = function(id)
{
	//�� �����������
	return null;
}

DummyCrypto.prototype.PreviewCertDemand = function(id)
{
	//�� �����������
	return null;
}