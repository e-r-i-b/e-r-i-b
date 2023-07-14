<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 23.11.2006
  Time: 15:36:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/cards/block">
<tiles:insert definition="dictionary">
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="cardBlock.title" bundle="passwordcardsBundle"/>
    </tiles:put>

   <tiles:put name="menu" type="string">
	 <script type="text/javascript">
        function sendComment (event)
        {
                var blockReason = document.getElementById("blockReason");
                preventDefault(event);
                window.opener.setReason(blockReason.value);
                window.close();
                return;
        }
	</script>
        <nobr>
	        <tiles:insert definition="clientButton" flush="false">
		        <tiles:put name="commandTextKey" value="button.lock"/>
		        <tiles:put name="commandHelpKey" value="button.lock.help"/>
		        <tiles:put name="image" value=""/>
		        <tiles:put name="bundle" value="personsBundle"/>
		        <tiles:put name="onclick" value="javascript:sendComment(event)"/>
                <tiles:put name="viewType" value="blueBorder"/>
	        </tiles:insert>
	        <tiles:insert definition="clientButton" flush="false">
		        <tiles:put name="commandTextKey" value="button.cancel"/>
		        <tiles:put name="commandHelpKey" value="button.cancel"/>
		        <tiles:put name="image" value=""/>
		        <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="viewType" value="blueBorder"/>
		        <tiles:put name="onclick" value="javascript:window.close()"/>
	        </tiles:insert>
        </nobr>
    </tiles:put>

	<tiles:put name="data" type="string">
		<table cellspacing="0" cellpadding="0" id="tableTitle" style="margin-bottom:2px" class="fieldBorder">
			<tr >
			  <td class="Width120 Label" >Причина блокировки</td>
              <td>
                   <input type="text" id="blockReason" size="100" maxlength="256"/>
              </td>
 		    </tr>
	    </table>
	 </tiles:put>
 </tiles:insert>
</html:form>