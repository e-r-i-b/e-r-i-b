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

  <html:form action="/confirm/card" show="true"method="post" onsubmit="return checkData();">
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
  <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
  <div class="Login" id="LoginDiv">
        <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
			  <td width="0%">&nbsp;</td>
			  <td valign="middle">
				  <table width="100%" cellpadding="0" cellspacing="0" border="0">
				  <tr>
					  <td colspan="5" align="center" class="lpSystemTitle">Интернет-клиент<br><span>для физических лиц</span></td>
				  </tr>
				  <tr>
					  <td colspan="5" align="center">
						  <table cellpadding="0" cellspacing="0" width="480px" height="150px" class="lpBase" border="0">
						  <tr>
							  <td class="pmntBgBaseTopLeftCorner"></td>
							  <td class="pmntBgBaseTop" colspan="2"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
							  <td class="pmntBgBaseTopRightCorner"></td>
						  </tr>
						  <tr>
							  <td class="pmntBgBaseLeftCorner">&nbsp;</td>
							  <td align="right" valign="top" width="80px" class="lpBtmLeftCorner">
								  <img src="${globalImagePath}/logoIB.gif"
										  alt="" height="66px" width="77px" border="0">
							  </td>
							  <td align="left" width="340px">
								  <table cellpadding="0" cellspacing="0" width="100%" border="0">
								  <tr>
									  <td>&nbsp;</td>
									  <td height="150px;" valign="top">
										  <table cellpadding="0" cellspacing="0" width="100" align="center" border="0"
											     onkeypress="onEnterKey(event);">
										  <tr>
											  <td>
												  <table cellpadding="0" cellspacing="0">
												  <tr>
													  <td><img src="${globalImagePath}/1x1.gif" alt="" height="13px" width="10px" border="0"></td>
													  <td width="1%" valign="top" class="lpMessage" colspan="3">
															  Для входа в систему введите:
													  </td>
													  <td><img src="${globalImagePath}/1x1.gif" alt="" height="13px" width="10px" border="0"></td>
												  </tr>
												  <tr>
                                                      <td class="lpInputAreaTopLeftCorner"><img src="${globalImagePath}/1x1.gif" alt="" height="13px" width="10px" border="0"></td>
													  <td class="lpInputAreaTop">
														  <img src="${globalImagePath}/1x1.gif" alt="" height="1px" width="1px" border="0">
													  </td>
													  <td class="lpInputAreaTopRightCorner"><img src="${globalImagePath}/1x1.gif" alt="" height="13px" width="10px" border="0"></td>
												  </tr>
												  <tr>
													  <td class="lpInputAreaLeftCorner">&nbsp;</td>
													  <td class="lpInputArea">
														  <table cellpadding="0" cellspacing="0" width="100%">
														  <tr>
															  <td width="30%">&nbsp;</td>
															  <td align="center">
																   ключ № ${form.passwordNumber}
															  </td>
															  <td width="30%" rowspan="3" class="tableArea">
															  </td>
														  </tr>
														  <tr>
															  <td>&nbsp;</td>
															  <td align="right">
																   из карты ключей № ${form.passwordCard}
															  </td>
														  </tr>
														  <tr>
															  <td>&nbsp;</td>
															  <td align="right">
																  <html:password property="password" styleClass="inputLogin" maxlength="20"/>
															  </td>
														  </tr>
														  </table>
													  </td>
													  <td class="lpInputAreaRightCorner">&nbsp;</td>
												  </tr>
												  <tr>
													  <td class="lpInputAreaBtmLeftCorner"></td>
													  <td align="center" class="lpInputAreaBtm"><table cellpadding="0" cellspacing="0"><tr><td>
														  <tiles:insert definition="commandButton" flush="false">
															  <tiles:put name="commandKey" value="button.confirmByCard"/>
															  <tiles:put name="commandTextKey" value="button.next"/>
															  <tiles:put name="commandHelpKey" value="button.login.help"/>
															  <tiles:put name="image" value="iconSm_next.gif"/>
															  <tiles:put name="bundle" value="securityBundle"/>
															  <tiles:put name="isDefault" value="true"/>
														      <tiles:put name="validationFunction">
																  checkData();
															  </tiles:put>
														  </tiles:insert></td></tr></table>
													  </td>
													  <td class="lpInputAreaBtmRightCorner"></td>
												  </tr>
												  </table>
											  </td>
										  </tr>
										  </table>
									  </td>
									  <td>&nbsp;</td>
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
								  <tr>
									  <td align="center" class="textNobr" colspan="3">
										  <a href="http://www.softlab.ru/solutions/InterBank/">InterBank</a>
										  <a href="http://www.softlab.ru/"><b>R-Style</b> Softlab</a>
									  </td>
								  </tr>
								  </table>
							  </td>
							  <td class="pmntBgBaseRightCorner">&nbsp;</td>
						  </tr>
						  <tr>
							  <td class="pmntBgBaseBtmLeftCorner"></td>
							  <td class="pmntBgBaseBtm" colspan="2"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
							  <td class="pmntBgBaseBtmRightCorner"></td>
						  </tr>
						  </table>
						  <br><br>
					  </td>
				  </tr>
				  <tr><td colspan="5" height="100px;">&nbsp;</td></tr>
				  </table>
			  </td>
		  </tr>
		  </table>
	</div>
  </html:form>
</body>
