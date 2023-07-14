setRSCryptoName('Agava');

// ctor
function AgavaCrypto()
{
	var initError = false;
	var AgavaStarter;
	try
	{
		AgavaStarter = new ActiveXObject("HTTPFile.HTTPFileCtl");
		AgavaStarter.AgavaRun(true);
	}
	catch(e)
	{
		initError=true;
		//var path = '${phiz:calculateActionURL(pageContext,"/private/cryptoSoftware.do"}'
		//throw new RSCryptoException(e.number, "У вас не установлено необходимое программное обеспечение. Вы можете произвести установку по <a target=\"_blank\" href=\"" +path +"\">ссылке</a> или обратиться в банк:" + e.message);
		throw new RSCryptoException(LOADER_NOT_EXIST, e.message);
	}

	if(!initError)
	{
		try
		{
			this.agava = new ActiveXObject("Agava_Client.Agava_Client_COM");
		}
		catch(e)
		{
			//var path = '${phiz:calculateActionURL(pageContext,"/private/agavaSoftware.do"}';
			//throw new RSCryptoException(e.number, "Ошибка при загрузке СКЗИ Агава. Если у вас не установлена СКЗИ Агава - перейдите по <a target=\"_blank\" href=\""+path+"\">ссылке</a>. Иначе обратитесь в банк:" + e.message);
			throw new RSCryptoException(CRYPTO_NOT_EXIST, e.message);
		}
		if(this.agava.bExistError)
			throw new RSCryptoException(0, "Ошибка при загрузке СКЗИ Агава обратитесь в банк:");
	}
}

AgavaCrypto.prototype = new RSCrypto;

AgavaCrypto.prototype.sign = function(text, cert)
{
	var agava = this.agava;
	var signature = agava.AddSignature(text, cert.id);
	this.throwIfError();

	return signature;
}

AgavaCrypto.prototype.getCertificate = function(certId)
{
	var agava = this.agava;

	agava.KeyContextMoveFirst();
	this.throwIfError();

	while ( ! agava.bExistError )
	{
		if(agava.bPrivateKey && agava.strCertSerialNumber == certId)
		{
			return new RSCertificate(certId, agava.strSubject, agava.dtStartDateTime, agava.dtEndDateTime);
		}
		agava.ContextMoveNext();
	}

	return null;
}

AgavaCrypto.prototype.throwIfError = function()
{
	var a = this.agava;
	if(a.bExistError)
		throw new RSCryptoException(a.lLastErrorNum, a.strLastErrorDescription);
}

AgavaCrypto.prototype.CreateCertRequest = function(fullName)
{
	var agava = this.agava;
	var result = new Array(2);

	result['request'] = agava.CreateCertRequest(fullName, '-');
	this.throwIfError();
	result['fileName'] = agava.strLastCertRequestFileName;
	this.throwIfError();

	return result;
}

AgavaCrypto.prototype.InstallCertAnswer = function(answer, answerFile)
{
	var agava = this.agava;

	agava.InstallCertificates(answer, answerFile);
	this.throwIfError();
}

AgavaCrypto.prototype.PrintCertificate = function(id)
{
	var agava = this.agava;

	agava.PrintDialog(id);
	this.throwIfError();
}

AgavaCrypto.prototype.PrintCertDemand = function(id)
{
	var agava = this.agava;

	agava.PrintDialog(id);
	this.throwIfError();
}

AgavaCrypto.prototype.PreviewCertificate = function(id)
{
	var agava = this.agava;

	agava.PrintPreview(id);
	this.throwIfError();
}

AgavaCrypto.prototype.PreviewCertDemand = function(id)
{
	var agava = this.agava;

	agava.PrintPreview(id);
	this.throwIfError();
}