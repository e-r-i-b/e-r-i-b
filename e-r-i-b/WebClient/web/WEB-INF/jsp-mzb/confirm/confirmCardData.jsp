<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>




<script language="JavaScript">
	function onLoadFn()
      {
        var el = document.getElementById("LoginDiv");
        var h   = Math.round((document.body.clientHeight - el.offsetHeight)/2);
        var w   = Math.round((document.body.clientWidth  - el.offsetWidth)/2);
        if ( h < 1 ) h = 1;
        if ( w < 1 ) w = 1;
        el.style.top  = h + "px";
        el.style.left = w + "px";
      }
      function checkData()
      {
        var p=getElement("password");

        if ( p.value.length == 0 ) {
            alert ("Введите ключ.");
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

  <html:form action="/confirm/card" show="true" method="post" onsubmit="return checkData();">
  <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<div class="Login" id="LoginDiv">
	<!--Общая лишняя таблица - для дополнительных картинок особо извращенных банков, типа МЗБ-->
	<table cellpadding="0" cellspacing="0" class="MaxSize addLoginTab">
	<tr>
	  <td>
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="mainLoginTab">
			<tr>
				<td align="center"><img src="${globalImagePath}/logo1.gif"
						 alt="" height="130px" width="148px" border="0">
				</td>
			</tr>
			<tr>
				<td align="center"><img src="${globalImagePath}/logo2.gif"
						 alt="" height="28px" width="317px" border="0">
				</td>
			</tr>
			<tr>
				<td>
					<table cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td>
							<img src="${imagePath}/menuLeft.jpg"
					             alt="" height="23px" width="277px" border="0">
						</td>
						<td width="100%" align="center" class="titleLoginPass">
							Интернет-клиент для физических лиц
						</td>
						<td>
							<img src="${imagePath}/menuRight.jpg"
					             alt="" height="23px" width="316px" border="0">
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td colspan="5" align="center">
								<table cellpadding="0" border=0 cellspacing="0" width="340px" height="250px" class="tabBaseLoginPass">
								<tr>
									<td align="center" width="340px">
										<br><br>
										<table cellpadding="0" border="0" cellspacing="0" width="100%" height="150px" class="tabBackLoginPass">
										<tr>
											<td>&nbsp;</td>
											<td height="250px;" valign="top" class="loginPassTable">
												<table cellpadding="0" cellspacing="0" width="100" align="center" border="0"
													     onkeypress="onEnterKey(event);">
												  <tr>
													  <td colspan="2" width="1%" class="paperEnter" style="text-align:center;" valign="top">Для входа в систему введите:
												      </td>
													  <td>&nbsp;</td>
												  </tr>
												  <tr><td colspan="3"><img src="${imagePath}/lineLogin.gif"
														  alt="" height="3px" width="298px" border="0">
													  </td>
												  </tr>
												  <tr><td colspan="3">&nbsp;</td></tr>
												  <tr>
													  <td class="paperEnter" style="text-align:center;" colspan="2">ключ № ${form.passwordNumber}</td>
													  <td rowspan="3"></td>
												  </tr>
												  <tr>
													  <td class="paperEnter" style="text-align:center;" colspan="2">из карты ключей № ${form.passwordCard}</td>
												  </tr>
												  <tr>
													  <td style="text-align:center;" colspan="2">
														   <html:password property="password" styleClass="inputLogin" maxlength="20"/>
													  </td>
												  </tr>
												  <tr>
													  <td width="70%">&nbsp;</td>
													  <td align="left">&nbsp;</td>
													  <td>&nbsp;</td>
												  </tr>
												  <tr>
													  <td align="center" colspan="2">
														  <table cellpadding="0" cellspacing="0"><tr><td>
														  <tiles:insert definition="commandButton" flush="false">
															  <tiles:put name="commandKey" value="button.confirmByCard"/>
															  <tiles:put name="commandHelpKey" value="button.confirmByCard.help"/>
															  <tiles:put name="image" value="iconSm_confirm.gif"/>
															  <tiles:put name="bundle" value="securityBundle"/>
															  <tiles:put name="isDefault" value="true"/>
														      <tiles:put name="validationFunction">
																  checkData();
															  </tiles:put>
														  </tiles:insert>
														  </td></tr></table>
													  </td>
													  <td>&nbsp;</td>
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
									<!--<td width="80px">&nbsp;</td>-->
								</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td height="100%">&nbsp;</td>
			</tr>
			<tr>
				<td height="10px" width="100%" class="borderDownLine fontInterBank" align="right"><a
						href="http://www.softlab.ru/solutions/InterBank/">&copy;InterBank</a></td>
			</tr>
		</table>
	  </td>
	</tr>
	</table>
	</div>
</html:form>