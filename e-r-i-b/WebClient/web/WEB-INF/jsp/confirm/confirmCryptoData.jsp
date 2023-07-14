<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/confirm/crypto" method="post" onsubmit="return setEmptyAction();" show="true">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<div class="Login" id="LoginDiv">
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		    
<%@ include file="../crypto/agavaStart.jsp" %>
<script>
	var isInstall = false;

	function onLoadFn()
	{
		var el = document.getElementById("LoginDiv");
		var h = Math.round((document.body.clientHeight - el.offsetHeight) / 2);
		var w = Math.round((document.body.clientWidth - el.offsetWidth) / 2);
		if (h < 1) h = 1;
		if (w < 1) w = 1;
		el.style.top = h + "px";
		el.style.left = w + "px";
	}
	function checkData()
	{
		return true;
	}
	var action = null;

	function cryptoStarted()
	{
		if(action == "next")
		{
			gotoNextStart();
			return;
		}

		alert("Не найдено действие");
	}
	function gotoNext(event)
	{
		action = "next";
		startCrypto();
	}

	function gotoNextStart(event)
	{
		var certId       = '${form.certId}';
		var randomString = '${form.randomString}' + createRandomString();
		var cert = null;
		try
		{
			cert = crypto.getCertificate(certId);
		}
		catch(ex)
		{
		}

		if(cert == null)
		{
			showError('В хранилище не найден сертификат с номером ' + certId + ". Установите необходимый сертификат.");
			window.location = "../private/certification/list.do";
			return;
		}

		try
		{
			var signature = crypto.sign(randomString, cert);
			addField ('hidden', 'randomString', randomString);
			addField ('hidden', 'signature', signature);
			new CommandButton('button.next').click();
		}
		catch(ex)
		{
			//если сертификат истек.
			if(ex.number == 1109)
				window.location = "../private/certification/list.do";

			showError(ex.message);
		}
	}
	function createRandomString()
	{
		var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZ";
		var length = 256;
		var randomstring = '';
		for (var i = 0; i < length; i++)
		{
			var rnum = Math.floor(Math.random() * chars.length);
			randomstring += chars.substring(rnum, rnum + 1);
		}
		return randomstring;
	}
	addEventListenerEx(window, 'load', onLoadFn, false);
</script>
<tr>
	<td  height="100%" colspan="3"  align="center">
		<table cellpadding="0" cellspacing="2" width="100%" border="0" valign="top" align="center">
        <tr>
			<html:messages id="error" bundle="securityBundle"/>
			<%-- ПЕРВОНАЧАЛЬНОЕ ОТОБРАЖЕНИЕ --%>
			<c:if test="${pageContext.request.method == 'GET'}">
				<c:if test="${empty error}">
					<tr id="wait_row" >
						<td colspan="4" class="paperEnter" style="text-align:center">
							Идет процесс проверки электронной подписи.<br/>
							Пожалуйста, подождите. Номер сертификата ${form.certId}.
						</td>
					</tr>
					<script type="text/javascript">
						addEventListenerEx(window, 'load', gotoNext, false);
					</script>
				</c:if>
			</c:if>

			<tr id="error_row">
				<td id="error_cell" colspan="4" class="error" style="text-align:center;"></td>
			</tr>
			<tr id="button_row" >
				<td colspan="4" style="text-align:center" align="center">
					<table>
						<tr><td align="center">
					<tiles:insert definition="clientButton" flush="false">
						<tiles:put name="commandTextKey" value="button.repeat"/>
						<tiles:put name="commandHelpKey" value="button.repeat"/>
						<tiles:put name="image" value=""/>
						<tiles:put name="bundle" value="securityBundle"/>
						<tiles:put name="onclick">gotoNext();</tiles:put>
					</tiles:insert>
						</td></tr>
					</table>
				</td>
			</tr>

			<%--<script type="text/javascript">--%>
				<%--document.getElementById("error_row").style.display = 'none';--%>
				<%--document.getElementById("button_row").style.display = 'none';--%>
			<%--</script>--%>

			<c:if test="${not empty error}">
				<script type="text/javascript">
					showError('${phiz:escapeForJS(error, true)}')
				</script>
			</c:if>

			<tr>
				<td colspan="4" class="paperEnter" style="text-align:center;font-size:12pt"><br><br></td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td colspan="3" height="10px" width="100%" class="borderDownLine fontInterBank" align="right"><a
		href="http://www.softlab.ru/solutions/InterBank/">&copy;InterBank</a></td>
</tr>
</table>
</div>
</html:form>