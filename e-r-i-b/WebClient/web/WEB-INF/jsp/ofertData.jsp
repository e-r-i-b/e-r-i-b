<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<script language="JavaScript" src="${initParam.resourcesRealPath}/scripts/gost.js"></script>

<c:set var="form" value="${UseOfertForm}"/>

<html:form action="/ofert" show="true" onsubmit="return setEmptyAction(event)">

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<script type="text/javascript">
	var s;
</script>
<div align="center">
    <table height="100%" cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td width="40px">&nbsp;</td>
            <td valign="top" align="center">
                <table cellpadding="0" cellspacing="0" border="0" width="920px">
                    <tr>
                        <td valign="top" width="50px" style="text-align:right;">
                            <img src="${imagePath}/login_leftCorner.gif" alt="" border="0">
                        </td>
                        <td valign="top" class="login_whiteBg">
                            <img src="${imagePath}/login_logo.png" alt="" border="0">
                        </td>
                        <td class="login_greenBg" valign="top">
                            <table cellpadding="0" cellspacing="0" width="460px"
                                   onkeypress="onEnterKey(event);">
                                <tr height="60%">
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right" style="padding-top:30px;">телефон справочной службы
                                    </td>
                                </tr>
                                <tr valign="top" style="padding-top:5px;">
                                    <td colspan="2" align="right" style="font-size:18pt;">(495) 974-77-61</td>
                                </tr>
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
                                    <td class="tblHeaderLeftCorner"><img src="${globalImagePath}/1x1.gif"
                                                                         alt="" border="0" width="33px">
                                    </td>
                                    <td class="tblHeader" colspan="3" width="100%">&nbsp;</td>
                                    <td class="tblHeaderRightCorner"><img src="${globalImagePath}/1x1.gif"
                                                                          alt="" border="0" width="33px">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tblInfLeftSpace">&nbsp;</td>
                                    <td class="tblInf" colspan="3">
                                        <table cellpadding="0" cellspacing="0" border="0" width="900px">
                                            <tr>
                                                <td class="tblHelpHdrLeftCorner">&nbsp;</td>
                                                <td class="tblHelpHdr">&nbsp;</td>
                                                <td class="tblHelpHdrRightCorner">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td class="tblHelpInfLeftSpace"></td>
                                                <td>
                                                    <table class="tableArea">
                                                        <tr>
                                                            <td align="center">
                                                                Уважаемый(ая)&nbsp;${form.fullName}!
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="redString" style="padding-top:10px;">
                                                                <div align="justify">
                                                                    <b><bean:message bundle="commonBundle" key="text.ofert.newConditions.notification"/></b>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="redString">
                                                                <div align="justify">
                                                                    <bean:message bundle="commonBundle" key="text.SBOL.getAccess"/>$nbsp;<b>С полным текстом
                                                                    <a href="http://www.sberbank.ru/common/img/uploaded/files/pdf/person/usl_sb_online.pdf" class="paperEnterLink">Условий</a>
                                                                    Вы можете ознакомиться на официальном сайте Сбербанка России ОАО  или в подразделении Банка.</b>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="redString">
                                                                <div align="justify">
                                                                    Предоставление услуг по ранее заключенным договорам будет прекращено с 16.11.09. В случае, если с Вами был заключен двухсторонний Договор о предоставлении услуг с использованием системы "Электронная Сберкасса", Банк уведомляет Вас о его расторжении с 16.11.2009 .
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="redString">
                                                                <div align="justify">
                                                                    <b><bean:message bundle="commonBundle" key="text.SBOL.getAccess.needAccept"/></b>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="redString">
                                                                <div align="justify">
                                                                    <bean:message bundle="commonBundle" key="text.ATM.list.canGetLoginAndPassword"/>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="redString">
                                                                <div align="justify">
                                                                    <bean:message bundle="commonBundle" key="text.conditions.aboutNewAndOld"/><br/>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <html:checkbox property="field(selectAgreed)" onchange="selectCheck('agree')" />&nbsp;<bean:message bundle="commonBundle" key="text.conditions.accept"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <html:checkbox property="field(selectCancel)" onchange="selectCheck('disagree')"/>&nbsp;отказаться
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="redString">
                                                                <div align="justify">
                                                                    Мы надеемся на Ваше понимание и приложим все усилия для того, чтобы обеспечить Вам наилучший уровень обслуживания в нашем банке.
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td class="tblHelpInfRightSpace"></td>
                                            </tr>
                                            <tr>
                                                <td class="tblHelpFtrLeftCorner">&nbsp;</td>
                                                <td class="tblHelpFtr">&nbsp;</td>
                                                <td class="tblHelpFtrRightCorner">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td colspan="3">
                                                    <table class="tableArea">
                                                        <tr>
                                                            <td width="60%">&nbsp;</td>
                                                            <td width="20%" >
                                                                <tiles:insert definition="commandButton" flush="false">
                                                                    <tiles:put name="commandKey"     value="button.confirmOfert"/>
                                                                    <tiles:put name="commandHelpKey" value="button.confirmOfert.help"/>
                                                                    <tiles:put name="bundle" value="securityBundle"/>
                                                                    <tiles:put name="isDefault" value="true"/>
	                                                                <tiles:put name="validationFunction">
                                                                        checkSelected();
                                                                    </tiles:put>
                                                                </tiles:insert>
                                                            </td>
                                                            <td width="20%">
                                                                <tiles:insert definition="commandButton" flush="false">
                                                                    <tiles:put name="commandKey"     value="button.skip"/>
                                                                    <tiles:put name="commandHelpKey" value="button.skip.help"/>
                                                                    <tiles:put name="bundle" value="securityBundle"/>
	                                                                <tiles:put name="validationFunction">
                                                                        alertInfo();
                                                                    </tiles:put>
                                                                </tiles:insert>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td class="tblInfRightSpace">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td class="tblInfLeftSpace">&nbsp;</td>
                                    <td class="tblInf" colspan="3">&nbsp;</td>
                                    <td class="tblInfRightSpace">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td class="tblFooterLeftCorner"><img src="${globalImagePath}/1x1.gif"
                                                                         alt="" border="0"></td>
                                    <td class="tblFooter" colspan="3">&nbsp;</td>
                                    <td class="tblFooterRightCorner"><img src="${globalImagePath}/1x1.gif"
                                                                          alt="" border="0"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
	function alertInfo()
	{
		alert("Для продолжения работы необходимо принять условия оферты!");
		return true;
	}
	function checkSelected()
	{
		if(s=='agree')
			return true;
		alert("Для продолжения работы необходимо принять условия оферты!");
		return true;
	}
	function selectCheck(a)
	{
		s = a;
	}
</script>
</html:form>