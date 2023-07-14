<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 19.04.2007
  Time: 19:35:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<script>
	var isInstall = false;
	var alreadyInstall = false;
	var crypto = null;
	function showError(text)
	{
		document.getElementById("error_row").style.display = '';
		document.getElementById("error_cell").innerHTML = text;
		if (document.getElementById("button_row")!=null) document.getElementById("button_row").style.display = '';
	}
	function hideError()
	{
		document.getElementById("error_row").style.display = 'none';
		document.getElementById("error_cell").innerHTML = "";
		if (document.getElementById("button_row")!=null) document.getElementById("button_row").style.display = 'block';
	}
	function hideButton()
	{
		if (document.getElementById("button_row")!=null) document.getElementById("button_row").style.display = 'none';
	}
	function startCrypto(event)
	{
		try
		{
//			hideButton();
			crypto = createRSCrypto();
			cryptoStarted();
			clearLoadMessage();
//			hideError();
			alreadyInstall = false;
		}
		catch(ex)
		{
			var message = ex.message;

			if(ex.number == LOADER_NOT_EXIST)
			{
				var path = '${phiz:calculateActionURL(pageContext,'/private/cryptoSoftware.do')}';

				if(alreadyInstall == "httpFile")
				{
					showHelp();
					return;
				}

				if(isInstall || confirm("На вашем компьютере не установлено необходимое программное обеспечение. Установить?"))
				{
					isInstall = true;
					showError("Идет загрузка ПО. Пожалуйста, подождите.");
					window.open(path);
				}
				else
				{
					hideError();
				}

			}
			else if(ex.number == CRYPTO_NOT_EXIST)
			{
				var path = '${phiz:calculateActionURL(pageContext,'/private/agavaSoftware.do')}';

				if(alreadyInstall == "Agava")
				{
					showHelp();
					return;
				}

				if(isInstall || confirm("На вашем компьютере не установлено необходимое программное обеспечение. Установить?"))
				{
					isInstall = true;
					showError("Идет загрузка ПО. Пожалуйста, подождите.");
					window.open(path);
				}
				else
				{
					hideError();
				}
			}
			else if(ex.numberValue == ACTION_USER_CANCEL)
			{
				return;
			}

			else
			{
				showHelp();
			}
		}
	}
	function showHelp()
	{
		var path = '${phiz:calculateActionURL(pageContext,'/private/instalDemand.do')}';
		showError("Идет загрузка ПО. Пожалуйста, подождите.");
		window.open(path);
	}

	function httpFileInstalled()
	{
		alreadyInstall = "httpFile";
		setTimeout("startCrypto()",500);
	}

	function AgavaInstalled()
	{
		alreadyInstall = "Agava";
		setTimeout("startCrypto()",500);
	}
	function SettingsChanged()
	{
		alreadyInstall = false;
		setTimeout("startCrypto()",500);
	}

</script>