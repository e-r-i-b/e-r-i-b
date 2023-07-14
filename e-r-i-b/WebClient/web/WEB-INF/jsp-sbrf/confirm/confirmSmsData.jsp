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
			alert ("������� ������.");
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

<html:form action="/confirm/sms" show="true" method="post" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<div class="Login" id="LoginDiv" align="center">
    <table height="100%" cellpadding="0" cellspacing="0" border="0"  onkeypress="onEnterKey(event);">
    <tr>
        <td width="40px">&nbsp;</td>
        <td valign="middle" align="center">
            <table cellpadding="0" cellspacing="0" border="0" width="920px">
            <tr>
                <td valign="top" width="50px" style="text-align:right;">
                    <img src="${imagePath}/login_leftCorner.gif" alt="" border="0">
                </td>
                <td valign="top" class="login_whiteBg">
                    <img src="${imagePath}/login_logo.png" alt="" border="0">
                </td>
                <td class="login_greenBg" valign="top">
                    <table cellpadding="0" cellspacing="0" width="460px">
                    <tr>
                        <td width="210px">sms-������</td>
                        <td class="input"><input type="text" id="passwordTxt" name="password" size="10" class="inputLogin"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="right" style="padding-top:9px;">
                            <table cellpadding="0" cellspacing="0"><tr><td>
                            <tiles:insert definition="commandButton" flush="false">
							    <tiles:put name="commandKey" value="button.confirmBySms"/>
								<tiles:put name="commandTextKey" value="button.next"/>
								<tiles:put name="commandHelpKey" value="button.login.help"/>
								<tiles:put name="image" value=""/>
								<tiles:put name="bundle" value="securityBundle"/>
								<tiles:put name="isDefault" value="true"/>
								<tiles:put name="validationFunction">
									checkData();
								</tiles:put>
							</tiles:insert>
                            </td></tr></table>
                        </td>
                    </tr>
                    <c:choose>
                        <c:when test="${form.needGetPassword}">
                        <tr>
                            <td style="padding-top:30px;">������� ���������� ������</td>
                            <td align="right" rowspan="2" style="padding-top:30px">
                                <table cellpadding="0" cellspacing="0" class="attention"><tr><td>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.getSmsPassword"/>
                                    <tiles:put name="commandTextKey" value="button.getSmsPassword"/>
                                    <tiles:put name="commandHelpKey" value="button.getSmsPassword.help"/>
                                    <tiles:put name="bundle" value="securityBundle"/>
                                    <tiles:put name="isDefault" value="true"/>
                                </tiles:insert>
                            </td></tr></table>
                            </td>
                        </tr>
                        <tr valign="top" style="padding-top:5px;">
                            <td style="font-size:18pt;">(495) 974-77-61</td>
                        </tr>
                        </c:when>
                        <c:otherwise>
                        <tr>
                            <td colspan="2" style="padding-top:30px;">������� ���������� ������</td>                            
                        </tr>
                        <tr valign="top">
                            <td colspan="2" style="font-size:18pt;">(495) 974-77-61</td>
                        </tr>
                        </c:otherwise>
                    </c:choose>
                    </table>
                </td>
                <td valign="top">
                    <img src="${imagePath}/login_rightCorner.gif" alt="" border="0">
                </td>
            </tr>
            <tr>
                <td colspan="4" valign="top">
                    <table cellpadding="0" cellspacing="0" width="920px">
                    <tr>
                        <td class="tblHeaderLeftCorner"><img src="${globalImagePath}/1x1.gif" alt="" border="0" width="33px"></td>
                        <td class="tblHeader" colspan="3" width="100%">&nbsp;</td>
                        <td class="tblHeaderRightCorner"><img src="${globalImagePath}/1x1.gif" alt="" border="0" width="33px"></td>
                    </tr>
                    <tr>
                        <td class="tblInfLeftSpace">&nbsp;</td>
                        <td class="tblInf" colspan="3">
                            <table cellpadding="0" cellspacing="0" border="0" width="900px">
                            <tr>
                                <td class="tblHelpHdrLeftCorner" >&nbsp;</td>
                                <td class="tblHelpHdr">&nbsp;</td>
                                <td class="tblHelpHdrRightCorner">&nbsp;</td>
                            </tr>
                            <tr>
                                <td class="tblHelpInfLeftSpace"></td>
                                <td>
                                    <a href="http://www.srb.ru/servicefiz/sber-online/" class="paperEnterLink" target="_blank"><bean:message bundle="commonBundle" key="text.moreInfo"/></a><br><br>
                                    <a href="https://esk.sbrf.ru/esClient/_ns/ProtectInfoPage.aspx" target="_blank">
                                        <bean:message bundle="commonBundle" key="text.SBOL.security"/>
                                    </a><br><br>
                                    <a href="http://sberbank.ru/ru/person/dist_services/warning/"
                                       class="paperEnterLink" target="_blank">� ������ ��� ������������� ���������� ������������</a>
                                </td>
                                <td class="tblHelpInfRightSpace"></td>
                            </tr>
                            <tr>
                                <td class="tblHelpFtrLeftCorner">&nbsp;</td>
                                <td class="tblHelpFtr">&nbsp;</td>
                                <td class="tblHelpFtrRightCorner">&nbsp;</td>
                            </tr>
                            </table>
                        </td>
                        <td class="tblInfRightSpace">&nbsp;</td>
                    </tr>
                    <c:set var="error">
                        <html:messages id="error" bundle="securityBundle">
                            <%=error%>
                        </html:messages>
                    </c:set>
                    <c:if test="${not empty error}">
                    <tr>
                        <td class="tblInfLeftSpace">&nbsp;</td>
                        <td class="tblInf" colspan="3">&nbsp;</td>
                        <td class="tblInfRightSpace">&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="tblInfLeftSpace"></td>
                        <td class="tblInf" colspan="3">
                            <table cellpadding="0" cellspacing="0" border="0" width="900px">
                            <tr>
                                <td class="tblErrHdrLeftCorner">&nbsp;</td>
                                <td class="tblErrHdr">&nbsp;</td>
                                <td class="tblErrHdrRightCorner">&nbsp;</td>
                            </tr>
                            <tr>
                                <td class="tblErrInfLeftSpace"></td>
                                <td class="tblErrInfSpace">
                                    <table cellpadding="0" cellspacing="0" width="100%">
                                    <tr>
                                        <td class="iconSpace">
                                            <img src="${imagePath}/iconMid_errcircle.gif" alt="" border="0">
                                        </td>
                                        <td width="100%">${error}</td>
                                    </tr>
                                    </table>
                                </td>
                                <td class="tblErrInfRightSpace"></td>
                            </tr>
                            <tr>
                                <td class="tblErrFtrLeftCorner">&nbsp;</td>
                                <td class="tblErrFtr">&nbsp;</td>
                                <td class="tblErrFtrRightCorner">&nbsp;</td>
                            </tr>
                            </table>
                        </td>
                        <td class="tblInfRightSpace"></td>
                    </tr>
                    </c:if>
                    <tr>
                        <td class="tblFooterLeftCorner"><img src="${globalImagePath}/1x1.gif" alt="" border="0"></td>
                        <td class="tblFooter" colspan="3">&nbsp;</td>
                        <td class="tblFooterRightCorner"><img src="${globalImagePath}/1x1.gif" alt="" border="0"></td>
                    </tr>
                    </table>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    </table>
    <script type="text/javascript">
        document.getElementById('passwordTxt').focus();
    </script>
</div>

<%--<div class="Login" id="LoginDiv">--%>
	<%--<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" style="padding-left:50px;padding-right:50px;padding-top:10px;">--%>
	<%--<tr><td colspan="3" height="20%">&nbsp;</td></tr>--%>
	<%--<tr>--%>
		<%--<td width="0%">&nbsp;</td>--%>
		<%--<td>--%>
			<%--<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0" class="loginPersonalLogoBank">--%>
			<%--<tr>--%>
				<%--<td colspan="5">--%>
					<%--<table cellspacing="0" cellpadding="0" width="100%" class="loginHeaderStyle">--%>
					<%--<tr>--%>
					    <%--<td width="200px;">&nbsp;</td>--%>
				        <%--<td align="center">--%>
				            <%--<img src="${globalImagePath}/logoElSber.gif" alt="" border="0">--%>
				        <%--</td>--%>
						<%--<td width="200px;" align="right">--%>
				            <%--<a href="http://www.softlab.ru/solutions/InterBank/">--%>
								<%--<img src="${iglobalImagePath}/logoIBNew.gif" alt="" border="0">--%>
				            <%--</a>--%>
				        <%--</td>--%>
					<%--</tr>--%>
					<%--</table>--%>
				<%--</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="whiteShortLine">--%>
					<%--<img src="${globalImagePath}/1x1.gif" alt="" height="2px"--%>
						 <%--width="1px" border="0">--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--<img src="${imagePath}/darkLineLeft.jpg"--%>
						 <%--alt="" height="3px" width="101px" border="0">--%>
				<%--</td>--%>
				<%--<td height="3px" class="darkLine">--%>
					<%--<img src="${globalImagePath}/1x1.gif" alt="" height="2px"--%>
						 <%--width="1px" border="0">--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--<img src="${imagePath}/darkLineRight.jpg"--%>
						<%--alt="" height="3px" width="101px" border="0">--%>
				<%--</td>--%>
				<%--<td class="whiteShortLine">--%>
					<%--<img src="${globalImagePath}/1x1.gif" alt="" height="2px"--%>
						<%--width="1px" border="0">--%>
				<%--</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="5" width="0%">--%>
					<%--<img src="${globalImagePath}/1x1.gif"--%>
						 <%--alt="" height="1px" width="1px" border="0">--%>
				<%--</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="white2pxLine">--%>
					<%--<img src="${globalImagePath}/1x1.gif"--%>
					     <%--alt="" height="2px" width="1px" border="0"></td>--%>
				<%--<td align="right">--%>
					<%--<img src="${imagePath}/lightLineLeft.jpg"--%>
						 <%--alt="" height="3px" width="30px" border="0">--%>
				<%--</td>--%>
				<%--<td class="lightLine">--%>
					<%--<img src="${globalImagePath}/1x1.gif"--%>
					     <%--alt="" height="2px" width="1px" border="0">--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--<img src="${imagePath}/lightLineRight.jpg"--%>
						 <%--alt="" height="3px" width="30px" border="0">--%>
				<%--</td>--%>
				<%--<td class="white2pxLine">--%>
					<%--<img src="${globalImagePath}/1x1.gif"--%>
					     <%--alt="" height="2px" width="1px" border="0">--%>
				<%--</td>--%>
			<%--</tr>--%>
			<%--<tr><td colspan="5">&nbsp;</td></tr>--%>
			<%--<tr>--%>
				<%--<td colspan="5" align="center">--%>
					<%--<table cellpadding="0" border=0 cellspacing="0" width="480px" height="250px" class="tabBaseLoginPass">--%>
					<%--<tr>--%>
						<%--<td align="right" valign="top" width="120px" class="picBaseLoginPass" rowspan="2">--%>
							<%--<img src="${imagePath}/cornTopLoginPass.gif" alt="" height="218px" width="95px" border="0">--%>
						<%--</td>--%>
						<%--<td align="left" width="340px">--%>
							<%--<br><br>--%>
							<%--<table cellpadding="0" cellspacing="0" width="100%" height="150px" class="tabBackLoginPass">--%>
							<%--<tr>--%>
								<%--<td>&nbsp;</td>--%>
								<%--<td height="250px;" valign="top" class="loginPassTable">--%>
									<%--<table cellpadding="0" cellspacing="0" width="100" align="center" border="0" onkeypress="onEnterKey(event);">--%>
									<%--<tr>--%>
										<%--<td colspan="3" align="center">--%>
											<%--<img src="${imagePath}/iconsLoginPass.gif" alt="" height="49px" width="244" border="0">--%>
										<%--</td>--%>
									<%--</tr>--%>
									<%--<tr>--%>
										<%--<td colspan="3" width="1%" class="paperEnter" style="text-align:center;" valign="top">��� ����� � ������� �������:</td>--%>
									<%--</tr>--%>
									<%--<tr>--%>
										<%--<td colspan="3">--%>
											<%--<img src="${imagePath}/lineLogin.gif" alt="" height="3px" width="298px" border="0">--%>
										<%--</td>--%>
									<%--</tr>--%>
									<%--<tr>--%>
										<%--<td colspan="3">&nbsp;</td>--%>
									<%--</tr>--%>
									<%--<tr>--%>
										<%--<td width="30%" class="paperEnter" nowrap="nowrap">sms-������</td>--%>
										<%--<td align="right">--%>
											<%--<input type="password" id="passwordTxt" name="password" maxlength="20" class="inputLogin"/>--%>
										<%--</td>--%>
										<%--<td width="30%" rowspan="3">--%>
											<%--<img src="${imagePath}/keysLoginPass.gif" alt="" height="71px" width="100px" border="0">--%>
										<%--</td>--%>
									<%--</tr>--%>
									<%--<tr><td colspan="3">&nbsp;</td></tr>--%>
									<%--<tr>--%>
										<%--<td width="30%">&nbsp;</td>--%>
										<%--<td align="right">--%>
											<%--<table cellpadding="0" cellspacing="0">--%>
											<%--<tr>--%>
												<%--<td>--%>
													<%--<tiles:insert definition="commandButton" flush="false">--%>
														<%--<tiles:put name="commandKey" value="button.confirmBySms"/>--%>
														<%--<tiles:put name="commandTextKey" value="button.next"/>--%>
														<%--<tiles:put name="commandHelpKey" value="button.login.help"/>--%>
														<%--<tiles:put name="image" value="iconSm_next.gif"/>--%>
														<%--<tiles:put name="bundle" value="securityBundle"/>--%>
														<%--<tiles:put name="default" value="true"/>--%>
														<%--<tiles:put name="validationFunction">--%>
															<%--checkData();--%>
														<%--</tiles:put>--%>
													<%--</tiles:insert>--%>
												<%--</td>--%>
											<%--<c:if test="${form.needGetPassword}">--%>
												<%--<td style="padding-left:15px">--%>
													<%--<tiles:insert definition="commandButton" flush="false">--%>
														<%--<tiles:put name="commandKey" value="button.getSmsPassword"/>--%>
														<%--<tiles:put name="commandTextKey" value="button.getSmsPassword"/>--%>
														<%--<tiles:put name="commandHelpKey" value="button.getSmsPassword.help"/>--%>
														<%--<tiles:put name="bundle" value="securityBundle"/>--%>
														<%--<tiles:put name="default" value="true"/>--%>
													<%--</tiles:insert>--%>
												<%--</td>--%>
											<%--</c:if>--%>
												<%--<td width="2%"></td>--%>
											<%--</tr>--%>
											<%--</table>--%>
										<%--</td>--%>
										<%--<td></td>--%>
									<%--</tr>--%>
									<%--<tr>--%>
										<%--<td colspan="3" class="error">&nbsp;--%>
											<%--<html:messages id="error" bundle="securityBundle">--%>
												<%--<br/><br/>--%>
												<%--<%=error%>--%>
											<%--</html:messages>--%>
										<%--</td>--%>
									<%--</tr>--%>
									<%--</table>--%>
								<%--</td>--%>
								<%--<td>&nbsp;</td>--%>
							<%--</tr>--%>
							<%--</table>--%>
						<%--</td>--%>
						<%--<td width="80px">&nbsp;</td>--%>
					<%--</tr>--%>
					<%--<tr>--%>
						<%--<td colspan="3" style="text-align:right; padding:2mm;">--%>
							<%--<a href="http://seb.sbrf.ru" class="paperEnterLink" target="_blank">�� ����������� ���������</a>--%>
						<%--</td>--%>
					<%--</tr>--%>
					<%--</table>--%>
				<%--</td>--%>
			<%--</tr>--%>
			<%--</table>--%>
		<%--</td>--%>
		<%--<td>&nbsp;</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
		<%--<td colspan="3" height="100%">&nbsp;</td>--%>
	<%--</tr>--%>
	<%--</table>--%>
<%--</div>--%>

</html:form>