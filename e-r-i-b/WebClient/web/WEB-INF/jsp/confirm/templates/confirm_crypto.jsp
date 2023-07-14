<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<input type="hidden" name="$$cryptoSignature" id="cryptoSignature"/>
<fieldset>
<table class="pmntConfirm" cellpadding="0" cellspacing="0">
	<c:if test="${not empty message}">
		<tr class="titleHelp"><td colspan="2">${message}</td></tr>
	</c:if>

	<c:if test="${not confirmRequest.error}">
		<tr>
			<td class="Label" style="text-align:left;">
				<b>Номер сертификата <c:out value="${confirmRequest.certificate.id}"/></b>
			</td>
		</tr>
	</c:if>

	<c:if test="${confirmRequest.error}">
		<tr>
			<td colspan="2" class="error">
				<c:out value="${confirmRequest.errorMessage}"/>
			</td>
		</tr>
	</c:if>

	<tr id="client_error_row">
		<td colspan="2" class="error" id="client_error_cell">
			<c:out value="${confirmRequest.errorMessage}"/>
		</td>
	</tr>
</table>
</fieldset>

<script type="text/javascript">
	function init()
	{
		var certId       = '${confirmRequest.certificate.id}';
		var stringToSign = '${confirmRequest.stringToSign}';

		var crypto = createRSCrypto();
		var cert = null;
		try
		{
			cert = crypto.getCertificate(certId);
		}
		catch(ex)
		{
		}

		if (cert == null)
		{
			showError('В хранилище не найден сертификат с номером ' + certId + ". Установите необходимый сертификат.");
			return;
		}

		var signature = crypto.sign(stringToSign, cert);
		var signatureInput = document.getElementById('cryptoSignature');
		signatureInput.value = signature;
	}

	function showError(text)
	{
		document.getElementById("client_error_row").style.display = '';
		document.getElementById("client_error_cell").innerHTML = text;
	}

	var err = document.getElementById('client_error_row');
	err.style.display = 'none';
	addEventListenerEx(window, 'load', init, false);
</script>