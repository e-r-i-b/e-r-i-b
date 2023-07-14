<%--
  Created by IntelliJ IDEA.
  User: Pakhomova
  Date: 27.01.2009
  Time: 11:14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<input type="hidden" name="stopListRiskAccepted" id="stopListRiskAccepted" value="${form.stopListRiskAccepted}"/>

<c:if test="${form.stopListState == 'shady' && form.activation==true && (isNew or isTemplate) }">
	<script type="text/javascript">
			/*если клиент находится в стоп-листе, необходимо уведомить пользователя*/
			function warnAboutStopList()
			{
	            if (confirm ("Клиент входит в предупреждающие списки. Продолжить?"))
	            {
		            var stopListAccepted = document.getElementById("stopListRiskAccepted");
		            stopListAccepted.value = true;

		            var button = new CommandButton("button.activate.person", "");
                    button.click();

	            }
				return false;
			}
			addEventListenerEx(window, 'load', warnAboutStopList, false);

	</script>
</c:if>

