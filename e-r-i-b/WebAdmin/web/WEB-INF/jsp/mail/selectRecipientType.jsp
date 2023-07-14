<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>


<tiles:insert definition="paymentsTemplates">
    <tiles:put name="pageTitle" type="string">
        <bean:message key="edit.title" bundle="mailBundle"/>
    </tiles:put>

	<tiles:put name="data" type="string">
	<script language="JavaScript">
       function add(event) {
            var formName =getRadioValue(document.getElementsByName('selectedFormNames'));
			preventDefault(event);
			if (formName==null) {
				alert ("Выберите вид получателя");
				return;
			}
			window.opener.newTemplate(formName, event);
			window.close();
       }
       </script>
	 <table cellspacing="2" cellpadding="0" style="margin:10px;"  class="selectWin tableArea">
	  <tr>
	   <td class="title">Выберите вид получателя</td>
	  </tr>
	  <tr>
		   <td>
			   <input type="radio" name="selectedFormNames" value="G" style="border:none"/>Группа
		   </td>
	   </tr>
	   <tr>
		   <td>
				<input type="radio" name="selectedFormNames" value="P" style="border:none"/>Клиент
		   </td>
	   </tr>
	   <tr>
	       <td align="center">
		    <table height="100%" width="30px" cellspacing="0" cellpadding="0">
            <tr>
              <td style="padding-right:4px">
				<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.next"/>
				<tiles:put name="commandHelpKey" value="button.next.help"/>
				<tiles:put name="bundle"  value="mailBundle"/>
				<tiles:put name="onclick" value="add(event);"/>
				</tiles:insert>
	          </td>
	          <td>
				<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle"  value="mailBundle"/>
				<tiles:put name="onclick" value="window.close();"/>
				</tiles:insert>
		       </td>
		    </tr>
			</table>
	       </td>
	   </tr>
	   </table>

	</tiles:put>
</tiles:insert>
