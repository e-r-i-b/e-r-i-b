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
		document.forms[0].elements[0].focus();
	}

	function checkData()
	{
		var n = getElement("login");
		var p = getElement("password");

		if (n.value.length == 0 || p.value.length == 0)
		{
			alert("������� ����� ������������ � ������.");
			return false;
		}

		return true;
	}
	function demo()
	{
		alert("� ����-������ ������� ����������.");
	}
	addEventListenerEx(window, 'load', onLoadFn, false);
</script>

<html:form action="/login" show="true" onsubmit="this.onsubmit = function(){ alert('��� ������ ��������������, ������� O� ��� �����������'); return false; }; return setEmptyAction();">
	<div class="Login" id="LoginDiv">
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="0%">&nbsp;</td>
			<td valign="middle">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td colspan="5" align="center" class="lpSystemTitle">��������-������<br><span>��� ���������� ���</span></td>
				</tr>
				<tr>
					<td colspan="5" align="center">
						<table cellpadding="0" cellspacing="0" width="480px" height="150px" class="lpBase" border="0">
						<tr>
							<td class="pmntBgBaseTopLeftCorner"></td>
							<td class="pmntBgBaseTop" colspan="2"><img src="${initParam.resourcesRealPath}/images/images${currentStyle}/1x1.gif" alt="" width="1" height="1" border="0"></td>
							<td class="pmntBgBaseTopRightCorner"></td>
						</tr>
						<tr>
							<td class="pmntBgBaseLeftCorner">&nbsp;</td>
							<td align="right" valign="top" width="80px" class="lpBtmLeftCorner">
								<img src="${initParam.resourcesRealPath}/images/images${currentStyle}/logoIB.gif"
										alt="" border="0">
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
													<td><img src="${initParam.resourcesRealPath}/images/images${currentStyle}/1x1.gif" alt="" height="13px" width="10px" border="0"></td>
													<td width="1%" valign="top" class="lpMessage" colspan="3">
															��� ����� � ������� �������:
													</td>
													<td><img src="${initParam.resourcesRealPath}/images/images${currentStyle}/1x1.gif" alt="" height="13px" width="10px" border="0"></td>
												</tr>
												<tr>
                                                    <td class="lpInputAreaTopLeftCorner"><img src="${initParam.resourcesRealPath}/images/images${currentStyle}/1x1.gif" alt="" height="13px" width="10px" border="0"></td>
													<td class="lpInputAreaTop">
														<img src="${initParam.resourcesRealPath}/images/images${currentStyle}/1x1.gif" alt="" height="1px" width="1px" border="0">
													</td>
													<td class="lpInputAreaTopRightCorner"><img src="${initParam.resourcesRealPath}/images/images${currentStyle}/1x1.gif" alt="" height="13px" width="10px" border="0"></td>
												</tr>
												<tr>
													<td class="lpInputAreaLeftCorner">&nbsp;</td>
													<td class="lpInputArea">
														<table cellpadding="0" cellspacing="0" width="100%">
														<tr>
															<td width="30%">�����</td>
															<td align="right">
																<html:text property="login" styleClass="inputLogin" maxlength="20"/>
															</td>
															<td width="30%" rowspan="3" class="tableArea">
															</td>
														</tr>
														<tr>
															<td>������</td>
															<td align="right">
																<html:password property="password" styleClass="inputLogin" maxlength="20"/>
															</td>
														</tr>
														<tr>
															<td>���&nbsp;�������</td>
															<td align="right">
																<html:select property="accessType">
																	<html:option value="simple">����������</html:option>
																	<html:option value="secure">����������</html:option>
																</html:select>
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
															<tiles:put name="commandKey" value="button.login"/>
															<tiles:put name="commandTextKey" value="button.next"/>
															<tiles:put name="commandHelpKey" value="button.login.help"/>
															<tiles:put name="image" value="iconSm_next.gif"/>
															<tiles:put name="bundle" value="securityBundle"/>
															<tiles:put name="isDefault" value="true"/>
															<tiles:put name="validationFunction">checkData();</tiles:put>
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
								<%--<tr>--%>
									<%--<td style="padding-left:25px;" colspan="3">--%>
										<%--<b>����������:</b><br>--%>
										<%--<li>��� ����� ������ � ������ ������, ��� �� ������������ � �������� ��������--%>
										<%--<li>���� � ��� ��� ������ � ������ �� ������ � ������� ���������� � ����.--%>
										<%--<li>��� �����-�� ������ �������� ����� ����� ������ �����										--%>
									<%--</td>--%>
								<%--</tr>--%>
								<tr>
									<td align="center" class="textNobr" colspan="3">
										<a href="http://www.derzhava.ru/">��� "�������" ���</a>
									</td>
								</tr>
								</table>
							</td>
							<td class="pmntBgBaseRightCorner">&nbsp;</td>
						</tr>
						<tr>
							<td class="pmntBgBaseBtmLeftCorner"></td>
							<td class="pmntBgBaseBtm" colspan="2"><img src="${initParam.resourcesRealPath}/images/images${currentStyle}/1x1.gif" alt="" width="1" height="1" border="0"></td>
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
