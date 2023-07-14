
var rsCryptoPluginName;
var LOADER_NOT_EXIST = -10;
var CRYPTO_NOT_EXIST = -11;
var ACTION_USER_CANCEL = 1086;

function createRSCrypto()
{
	if(rsCryptoPluginName == null || rsCryptoPluginName == '')
		throw "�� ���������� ������ ������";

	return eval("new " + rsCryptoPluginName  + "Crypto()");
}

function setRSCryptoName(pluginName)
{
	rsCryptoPluginName = pluginName;
}

// ctor
function RSCrypto()
{
}

/*
	��������� �����
	text - ����� ��� �������
	cert - ���������� (RSCertificate) ������� ���� ���������

	���������� ������ ���������� �������
*/
RSCrypto.prototype.sign = function(text, cert){}

/*
	����� ����������� �� ��� ������
	certId - ����� �����������

	���������� ����������
*/
RSCrypto.prototype.getCertificate = function(certId){}

/*
	�������� ������� �� ����������
	fullName  - ��� �������, �� �������� ����� ����� ����������

	���������� �������
		result['request'] - ������
		result['fileName'] - ��� ����� �������
*/
RSCrypto.prototype.CreateCertRequest = function(fullName){}

/*
	���������� �����������
	answer - ����� �� ������
	answerFile - ��� ����� ������
*/
RSCrypto.prototype.InstallCertAnswer = function(answer, answerFile){}

/*
	������ �����������
*/
RSCrypto.prototype.PrintCertificate = function(id){}

/*
	������ ������� �� ����������
*/
RSCrypto.prototype.PrintCertDemand = function(id){}

/*
	�������� �����������
*/
RSCrypto.prototype.PreviewCertificate = function(id){}

/*
	�������� ������� �� �����������
*/
RSCrypto.prototype.PreviewCertDemand = function(id){}

//ctor
function RSCertificate(certId, owner, startDateTime, endDateTime)
{
	this.id           = certId;
	this.owner         = owner;
	this.startDateTime = startDateTime;
	this.endDateTime   = endDateTime;
}

RSCertificate.prototype.toString = function()
{
	return this.id;
}

//ctor
function RSCryptoException(number, message)
{
	this.number = number;
	this.message = message;
}