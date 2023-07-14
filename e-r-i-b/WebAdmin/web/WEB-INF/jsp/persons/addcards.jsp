<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 22.11.2006
  Time: 18:02:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/persons/cards/add" onsubmit="return checkData();">
	<tiles:insert definition="paymentsTemplates">
		<tiles:put name="submenu" type="string" value="PasswordCards"/>
        <tiles:put name="pageTitle" type="string">
			Добавление карты ключей пользователю
		</tiles:put>

        <tiles:put name="menu" type="string">
       </tiles:put>
        <tiles:put name="data" type="string">
	         <script type="text/javascript">
				function sendComment (event)
				{
						var cardNumber = document.getElementById("field(cardNumber)");
						preventDefault(event);
						window.opener.setAddNumber(cardNumber.value);
						window.close();
						return;
				}
		        document.body.className = "dictionaryBG";
				function onEnterKey(e)
				{
					var kk = navigator.appName == 'Netscape' ? e.which : e.keyCode;
					if (kk == 13)
					{
						sendComment(e);
					}
				}
			</script>
	        <c:set var="personId" value="${AddUserPasswordCardsForm.id}"/>
	        <c:set var="personId" value="${param.person}"/>                                                    
	        <input type="hidden" name="id" value="<c:out value="${personId}"/>"/>
			<table cellspacing="2" cellpadding="0" style="width:450;height:150;padding:10px;"  class="selectWin tableArea" onkeypress="javascript:onEnterKey(event);">			      
			<tr>
				<td class="title" colspan="2">Введите номер карты:</td>
			</tr>
			<tr >
			  <td class="Width160 LabelAll" >Номер&nbsp;карты</td>
              <td>
                   <input type="text" id="field(cardNumber)" size="53" maxlength="256"/>
              </td>              
 		    </tr>
            <tr>
				<td align="center" colspan="2">
					<table height="100%" width="30px" cellspacing="0" cellpadding="0">
						<tr>
							<td style="padding-right:4px">
								<input type="hidden" name="person" value="<c:out value="${personId}"/>"/>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.add"/>
                                    <tiles:put name="commandHelpKey" value="button.add"/>
                                    <tiles:put name="bundle" value="personsBundle"/>
                                    <tiles:put name="onclick">javascript:sendComment();</tiles:put>
                                </tiles:insert>
							</td>
							<td>
								<tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.close"/>
                                    <tiles:put name="commandHelpKey" value="button.close"/>
                                    <tiles:put name="bundle" value="personsBundle"/>
                                    <tiles:put name="onclick">javascript:window.close();</tiles:put>
                                </tiles:insert>
							</td>
						</tr>
					</table>
				</td>
			</tr>
	        </table>
        </tiles:put>
	</tiles:insert>
</html:form>