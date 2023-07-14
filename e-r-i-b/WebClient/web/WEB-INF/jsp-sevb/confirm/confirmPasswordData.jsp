<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<script language="JavaScript" src="${initParam.resourcesRealPath}/scripts/gost.js"></script>

<c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/confirm/password" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">
<html:hidden property="accessType" value="simple"/>
<html:hidden property="clientRandom" styleId="clientRandom"/>
<html:hidden property="serverRandom" styleId="serverRandom"/>
<html:hidden property="password" styleId="password"/>
<script language="JavaScript">
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
		var p = getElement("passwordTxt");

		if (p.value.length == 0)
		{
			alert("Введите пароль");
			return false;
		}

		var challenge = sr + cr;
		var ph = fnStrHashLikeGOST(p.value);
		var ar = fnHmacLikeGOST(ph, challenge);
		document.getElementById("password").value = ar;
		document.getElementById("clientRandom").value = cr;
	    p.value = '';
		return true;
	}

	function demo()
	{
		alert("В демо-версии функция недоступна.");
	}

	var HEX_DIGITS = "0123456789ABCDEF";
	function randomHex(n)
	{
		var res = "";

		for (var i = 0; i < n; i++)
		{
			var p = Math.floor(Math.random() * 16);
			res = res + HEX_DIGITS.charAt(p);
		}

		return res;
	}

	var sr = getElement("serverRandom").value;
	var cr = randomHex(16);

	addEventListenerEx(window, 'load', onLoadFn, false);
</script>

<div class="Login" id="LoginDiv">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" style="padding-left:50px;padding-right:50px;padding-top:10px;">
<tr><td colspan="3" height="20%">&nbsp;</td></tr>
<tr>
	<td width="0%">&nbsp;</td>
	<td>
		<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0" class="loginPersonalLogoBank">
		<tr>
			<td colspan="5">
				<table cellspacing="0" cellpadding="0" width="100%" class="loginHeaderStyle">
				<tr>
				    <td width="200px;">&nbsp;</td>
				    <td align="center">
				        <img src="${globalImagePath}/logoElSber.gif" alt="" border="0">
				    </td>
					<td width="200px;" align="right">
				        <a href="http://www.softlab.ru/solutions/InterBank/">
							<img src="${globalImagePath}/logoIBNew.gif" alt="" border="0">
				        </a>
				    </td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="whiteShortLine">
				<img src="${globalImagePath}/1x1.gif"
				     alt="" height="2px" width="1px" border="0">
			</td>
			<td>
				<img src="${imagePath}/darkLineLeft.jpg"
				     alt="" height="3px" width="101px" border="0">
			</td>
			<td height="3px" class="darkLine">
				<img src="${globalImagePath}/1x1.gif"
				     alt="" height="2px" width="1px" border="0">
			</td>
			<td>
				<img src="${imagePath}/darkLineRight.jpg"
				     alt="" height="3px" width="101px" border="0">
			</td>
			<td class="whiteShortLine">
				<img src="${globalImagePath}/1x1.gif"
				     alt="" height="2px" width="1px" border="0">
			</td>
		</tr>
		<tr>
			<td colspan="5" width="0%">
				<img src="${globalImagePath}/1x1.gif"
					 alt="" height="1px" width="1px" border="0">
			</td>
		</tr>
		<tr>
			<td class="white2pxLine">
				<img src="${globalImagePath}/1x1.gif"
				     alt="" height="2px" width="1px" border="0">
			</td>
			<td align="right">
				<img src="${imagePath}/lightLineLeft.jpg"
					 alt="" height="3px" width="30px" border="0">
			</td>
			<td class="lightLine">
				<img src="${globalImagePath}/1x1.gif"
				     alt="" height="2px" width="1px" border="0">
			</td>
			<td>
				<img src="${imagePath}/lightLineRight.jpg"
					 alt="" height="3px" width="30px" border="0">
			</td>
			<td class="white2pxLine">
				<img src="${globalImagePath}/1x1.gif"
				     alt="" height="2px" width="1px" border="0">
			</td>
		</tr>
		<tr><td colspan="5">&nbsp;</td></tr>
		<tr>
			<td colspan="5" align="center">
				<table cellpadding="0" border=0 cellspacing="0" width="480px" height="250px" class="tabBaseLoginPass">
				<tr>
					<td align="right" valign="top" width="120px" class="picBaseLoginPass" rowspan="2">
						<img src="${imagePath}/cornTopLoginPass.gif" alt="" height="218px" width="95px" border="0">
					</td>
					<td align="left" width="340px">
						<br><br>
						<table cellpadding="0" cellspacing="0" width="100%" height="150px" class="tabBackLoginPass">
						<tr>
							<td>&nbsp;</td>
							<td height="250px;" valign="top" class="loginPassTable">
								<table cellpadding="0" cellspacing="0" width="100" align="center" border="0" onkeypress="onEnterKey(event);">
								<tr>
									<td colspan="3" align="center">
										<img src="${imagePath}/iconsLoginPass.gif" alt="" height="49px" width="244" border="0">
									</td>
								</tr>
								<tr>
									<td colspan="3" width="1%" class="paperEnter" style="text-align:center;" valign="top">Для входа в систему введите:</td>
								</tr>
								<tr>
									<td colspan="3">
										<img src="${imagePath}/lineLogin.gif" alt="" height="3px" width="298px" border="0">
									</td>
								</tr>
								<tr>
									<td colspan="3">&nbsp;</td>
								</tr>
								<tr>
									<td width="30%" class="paperEnter">Пароль</td>
									<td align="right">
										<input type="password" id="passwordTxt" name="passwordTxt" maxlength="20" class="inputLogin"/>
									</td>
									<td width="30%" rowspan="3">
										<img src="${imagePath}/keysLoginPass.gif" alt="" height="71px" width="100px" border="0">
									</td>
								</tr>
								<tr>
									<td colspan="3">&nbsp;</td>
								</tr>
								<tr>
									<td width="30%">&nbsp;</td>
									<td align="right">
										<table cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<tiles:insert definition="commandButton" flush="false">
													<tiles:put name="commandKey" value="button.confirmByPassword"/>
													<tiles:put name="commandTextKey" value="button.confirmByPassword"/>
													<tiles:put name="commandHelpKey" value="button.confirmByPassword.help"/>
													<tiles:put name="image" value="iconSm_next.gif"/>
													<tiles:put name="bundle" value="securityBundle"/>
													<tiles:put name="isDefault" value="true"/>
													<tiles:put name="validationFunction">
																checkData();
													</tiles:put>
												</tiles:insert>
											</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												<tiles:insert definition="commandButton" flush="false">
													<tiles:put name="commandKey" value="button.getPassword"/>
													<tiles:put name="commandTextKey" value="button.getPassword"/>
													<tiles:put name="commandHelpKey" value="button.getPassword.help"/>
													<tiles:put name="bundle" value="securityBundle"/>
													<tiles:put name="isDefault" value="false"/>
												</tiles:insert>
											</td>
										</tr>
										</table>
									</td>
									<td></td>
								</tr>
								<tr>
									<td colspan="3" class="error">&nbsp;
										<html:messages id="error" bundle="securityBundle">
											<br/><br/>
											<%=error%>
										</html:messages>
									</td>
								</tr>
								</table>
							</td>
							<td>&nbsp;</td>
						</tr>
						</table>
					</td>
					<td width="80px">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" style="text-align:right; padding:2mm;">
						<a href="http://seb.sbrf.ru" class="paperEnterLink" target="_blank">Об Электронной Сберкассе</a>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		</table>
	</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td colspan="3" height="100%">&nbsp;</td>
</tr>
</table>
</div>
<script type="text/javascript">
	//forgetPass();
    document.getElementById('passwordTxt').focus();
</script>
</html:form>
