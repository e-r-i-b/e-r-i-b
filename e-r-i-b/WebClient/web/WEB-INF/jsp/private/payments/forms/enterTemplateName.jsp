<%--
  User: IIvanova
  Date: 27.04.2006
  Time: 15:16:47
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:insert definition="enterTemplate">
<tiles:put name="pageTitle" type="string" value="Укажите название шаблона"/>
<tiles:put name="data" type="string">
	<script type="text/javascript">
	      function saveTemplate(event)
	      {
	        preventDefault(event);
	        var templateName = getElementValue("templateName");
	         if (templateName=="") {
	            alert ("Укажите название шаблона");
	            return;
	         }
	         window.opener.saveAsTemplate(templateName);
	         window.close();
	      }
	</script>

     <br/>
     <br/>
     &nbsp;<span class="asterisk Width120">Название&nbsp;шаблона:&nbsp;</span>
     <input type="text" name="templateName" size="60" maxlength="50"/>
     <br/>
     <br/>
	<div class="tablearea">
     <center>
	     <tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey"    value="button.next"/>
			<tiles:put name="commandHelpKey"    value="button.next"/>
			<tiles:put name="bundle"            value="paymentsBundle"/>
			<tiles:put name="onclick"           value="saveTemplate(event)"/>
			<tiles:put name="image"             value="iconSm_next.gif"/>
		</tiles:insert>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey"    value="button.cancel"/>
			<tiles:put name="commandHelpKey"    value="button.cancel"/>
			<tiles:put name="bundle"            value="commonBundle"/>
			<tiles:put name="onclick"           value="window.close()"/>
			<tiles:put name="image"             value="iconSm_back.gif"/>
		</tiles:insert>
	 </div>
     <%--<phiz:menuButton id="b1" onclick="saveTemplate(event);">Продолжить</phiz:menuButton>--%>
     <!--&nbsp;&nbsp;-->
     <%--<phiz:menuButton id="b2" onclick="window.close();">Отменить</phiz:menuButton>--%>
     </center>
</tiles:put>
</tiles:insert>
