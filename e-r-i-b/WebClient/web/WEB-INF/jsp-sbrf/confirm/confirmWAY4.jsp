<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>



<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/confirm/way4"  show="true" onsubmit="this.onsubmit = function(){ alert('Ваш запрос обрабатывается, нажмите OК для продолжения'); return false; }; return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <html:hidden  property="receiptNo"/>
    <html:hidden  property="passwordsLeft"/>
    <html:hidden  property="passwordNo"/>
    <html:hidden  property="SID"/>

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
			alert ("Введите пароль.");
			return false;
		}

		return true;
   }

    function addEventListenerEx(elem, eventType, delegate, capture)
{
	if (typeof(elem) == 'string')
	{
		if (!(elem = document.getElementById(elem)))
		{
			var msg = elem + ' element not found';
			alert(msg);
			throw msg;
		}
	}

	if (elem.addEventListener)
		elem.addEventListener(eventType, delegate, capture);
	else if (elem.attachEvent)
		elem.attachEvent('on' + eventType, delegate);
	else
		elem['on' + eventType] = delegate;
}

    addEventListenerEx(window, 'load', onLoadFn, false);
    </script>

 <tiles:insert definition="login">
     <tiles:put name="pageTitle" type="string" value="Регистрация."/>
     <tiles:put name="data" type="string">
    <div class="Login" id="LoginDiv">
	<table cellpadding="0" cellspacing="0" border="0" onkeypress="onEnterKey(event);">
                <logic:equal name="ConfirmWay4PasswordForm" property="smsSend" value="false">
		         <td width="10%" valign="top" class="lpMessage">
				  Для завершение платежа введите пароль № <bean:write name="ConfirmWay4PasswordForm"  property="passwordNo"/>
                  С чека № <bean:write name="ConfirmWay4PasswordForm"  property="receiptNo"/>
                </td>
                </logic:equal>

                <logic:equal name="ConfirmWay4PasswordForm" property="smsSend" value="true">
                   <td width="10%" valign="top" class="lpMessage">
                     Для завершение платежа введите пароль поученный по смс
                   </td>
                   <td width="10%" valign="top" class="lpMessage">
                    Получить пароль по SMS
                    <tiles:insert definition="commandButton" flush="false">
			          <tiles:put name="commandKey" value="button.smsget"/>
                      <tiles:put name="commandTextKey" value="button.smsget"/>
					  <tiles:put name="commandHelpKey" value="button.smsget"/>
			          <tiles:put name="bundle" value="commonBundle"/>
		           	  <tiles:put name="image" value="iconSm_save.gif"/>
		            </tiles:insert>
                </td>

                    </td>
               </logic:equal>

		     <td align="left">
			       <html:password property="password" styleClass="inputLogin" maxlength="20" onkeypress="onEnterKey(event);"/>
		     </td>

	        	<td class="lpInputAreaRightCorner">&nbsp;</td>
           </tr>
	       <tr>
			<td class="lpInputAreaBtmLeftCorner"></td>
            <td >
           <logic:equal name="ConfirmWay4PasswordForm" property="smsSend" value="false">
            <tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.validate"/>
			<tiles:put name="commandTextKey" value="button.validate"/>
			<tiles:put name="commandHelpKey" value="button.validate"/>
			<tiles:put name="bundle" value="commonBundle"/>
			<tiles:put name="image" value="iconSm_save.gif"/>
			<tiles:put name="isDefault" value="true"/>

            <tiles:put name="validationFunction" >
                checkData();
            </tiles:put>
		   </tiles:insert>
          </logic:equal>
          <logic:equal name="ConfirmWay4PasswordForm" property="smsSend" value="true">
            <tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.confirmSms"/>
			<tiles:put name="commandTextKey" value="button.confirmSms"/>
			<tiles:put name="commandHelpKey" value="button.confirmSms"/>
			<tiles:put name="bundle" value="commonBundle"/>
			<tiles:put name="image" value="iconSm_save.gif"/>
            <tiles:put name="isDefault" value="true"/>
            
            <tiles:put name="validationFunction">
                checkData();
            </tiles:put>
		   </tiles:insert>
          </logic:equal>
	</td></tr></table>
         </div>
		</td>
		<td class="lpInputAreaBtmRightCorner"></td>
		</tr>
		</table>
    </tiles:put>
    </tiles:insert>
</html:form>

