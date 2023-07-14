<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

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
		var n = getElement("login");
		var p = getElement("password");

		if (n.value.length == 0 || p.value.length == 0)
		{
			alert("Введите логин пользователя и пароль.");
			return false;
		}

		return true;
	}
	function demo()
	{
		alert("В демо-версии функция недоступна.");
	}
	addEventListenerEx(window, 'load', onLoadFn, false);
</script>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/login" show="true" onsubmit="this.onsubmit = function(){ alert('Ваш запрос обрабатывается, нажмите OК для продолжения'); return false; }; return setEmptyAction();">
	<div class="Login" id="LoginDiv">
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		    <tr><td colspan="3" height="20%">&nbsp;</td></tr>
			<tr>
				<td width="0%">&nbsp;</td>
				<td>
					<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td height="70px;" colspan="5" align="center" class="titleLoginPass">Интернет-клиент для физических лиц</td>
						</tr>
						<tr>
							<td class="whiteShortLine"><img
									src="${globalImagePath}/1x1.gif" alt="" height="2px"
									width="1px" border="0"></td>
							<td><img
									src="${imagePath}/darkLineLeft.jpg"
									alt="" height="3px" width="101px" border="0"></td>
							<td height="3px" class="darkLine"><img
									src="${globalImagePath}/1x1.gif" alt="" height="2px"
									width="1px" border="0"></td>
							<td><img
									src="${imagePath}/darkLineRight.jpg"
									alt="" height="3px" width="101px" border="0"></td>
							<td class="whiteShortLine"><img
									src="${globalImagePath}/1x1.gif" alt="" height="2px"
									width="1px" border="0"></td>
						</tr>
						<tr><td colspan="5" width="0%"><img
									src="${globalImagePath}/1x1.gif"
									alt="" height="1px" width="1px" border="0">
							</td>
						</tr>
						<tr>
							<td class="white2pxLine"><img src="${globalImagePath}/1x1.gif"
							                              alt="" height="2px" width="1px" border="0"></td>
							<td align="right"><img
									src="${imagePath}/lightLineLeft.jpg"
									alt="" height="3px" width="30px" border="0"></td>
							<td class="lightLine"><img src="${globalImagePath}/1x1.gif"
							                           alt="" height="2px" width="1px" border="0"></td>
							<td><img
									src="${imagePath}/lightLineRight.jpg"
									alt="" height="3px" width="30px" border="0"></td>
							<td class="white2pxLine"><img src="${globalImagePath}/1x1.gif"
							                              alt="" height="2px" width="1px" border="0"></td>
						</tr>
						<tr><td colspan="5">&nbsp;</td></tr>
						<tr>
							<td colspan="5" align="center">
								<table cellpadding="0" border=0 cellspacing="0" width="480px" height="250px" class="tabBaseLoginPass">
								<tr>
									<td align="right" valign="top" width="120px" class="picBaseLoginPass">
										<img src="${imagePath}/cornTopLoginPass.gif"
												alt="" height="218px" width="95px" border="0">
									</td>
									<td align="left" width="340px">
										<br><br>
										<table cellpadding="0" cellspacing="0" width="100%" height="150px" class="tabBackLoginPass">
										<tr>
											<td>&nbsp;</td>
											<td height="250px;" valign="top" class="loginPassTable">
												<table cellpadding="0" cellspacing="0" width="100" align="center" border="0"
													   onkeypress="onEnterKey(event);">
												<tr><td colspan="3" align="center"><img src="${imagePath}/iconsLoginPass.gif"
														alt="" height="49px" width="244" border="0">
									 				</td>
												</tr>
												<tr>
													<td colspan="3" width="1%" class="paperEnter" style="text-align:center;" valign="top">Для входа в систему введите:
									    			</td>
												</tr>
												<tr><td colspan="3"><img src="${imagePath}/lineLogin.gif"
														alt="" height="3px" width="298px" border="0">
													</td>
												</tr>
												<tr><td colspan="3">&nbsp;</td></tr>
												<tr>
													<td width="30%" class="paperEnter">Логин</td>
													<td align="right">
														<html:text property="login" styleClass="inputLogin" maxlength="20"/>
													</td>
													<td width="30%" rowspan="3">
														<img src="${imagePath}/keysLoginPass.gif"
														alt="" height="71px" width="100px" border="0">
													</td>
												</tr>
												<tr>
													<td class="paperEnter">Пароль</td>
													<td align="right">
														<html:password property="password" styleClass="inputLogin" maxlength="20"/>
													</td>
												</tr>
												<tr>
													<td class="paperEnter">Тип&nbsp;доступа</td>
													<td align="right">
														<html:select property="accessType" style="width:100px;">
															<html:option value="simple">Упрощенный</html:option>
															<html:option value="secure">Защищенный</html:option>
														</html:select>
													</td>
												</tr>
												<tr><td colspan="3">&nbsp;</td></tr>
												<tr>
													<td width="30%">&nbsp;</td>
													<td align="right"><table cellpadding="0" cellspacing="0"><tr><td>
														<tiles:insert definition="commandButton" flush="false">
															<tiles:put name="commandKey" value="button.login"/>
															<tiles:put name="commandTextKey" value="button.next"/>
															<tiles:put name="commandHelpKey" value="button.login.help"/>
															<tiles:put name="image" value="next.gif"/>
															<tiles:put name="bundle" value="securityBundle"/>
															<tiles:put name="isDefault" value="true"/>
											    			<tiles:put name="validationFunction">
																checkData();
															</tiles:put>
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
												<br/>
												<br/>
												<%=error%>
												</html:messages>
											</td>
										</tr>
										</table>
											</td>
											<td>&nbsp;</td>
										</tr>
										</table>
										<br><br>
									</td>
									<td width="80px">&nbsp;</td>
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
			<tr>
				<td colspan="3" height="10px" width="100%" class="borderDownLine fontInterBank" align="right"><a
						href="http://www.softlab.ru/solutions/InterBank/">&copy;InterBank</a></td>
			</tr>
		</table>
	</div>
</html:form>
