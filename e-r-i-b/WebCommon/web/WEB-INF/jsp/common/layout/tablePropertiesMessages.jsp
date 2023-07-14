<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">

    var tabPropertiesErrors = [];
	var error    = null;
	var message  = null;

    function unescapeCharsByCode(str)
    {
        return str.replace(/(&#)([0-9]{1,5})(;)/g,
                function (a1, a2, a3)
                {
                    return String.fromCharCode(a3);
                });
    }

	error = "";
	message = "";

	<tiles:useAttribute name="bundle"/>

	<c:if test="${empty bundle || bundle==''}">
		<c:set var="bundle" value="commonBundle"/>
	</c:if>

    var errorDictionary = {};
    var messageAr = [];

    <%-- /*Вытягиваем сообщения об ошибках из реквеста и запоминаем их в массив*/ --%>
	<phiz:messages  id="error" bundle="${bundle}" field="field" message="error">
        <c:set var="errorPost">${phiz:processBBCodeAndEscapeHtml(error, false)}</c:set>
        <c:choose>
            <c:when test="${field != null}">
                errorDictionary['<bean:write name="field" filter="false"/>'] = "${phiz:escapeForJS(errorPost, true)}";
            </c:when>
            <c:otherwise>
                 errorDictionary['formError'] = "${phiz:escapeForJS(errorPost, true)}";
            </c:otherwise>
        </c:choose>
    </phiz:messages>

    <phiz:messages  id="error" bundle="${bundle}" field="field" message="message">
         <c:set var="messagePost">${phiz:processBBCodeAndEscapeHtml(error, false)}</c:set>
        messageAr.push("${phiz:escapeForJS(messagePost, true)}");
    </phiz:messages>

    for(var i=0; i<messageAr.length; i++)
        message += trim(messageAr[i])+"\n";

    message = trim(message);
    message = unescapeCharsByCode(message);
	message = (message == "" ? null : message);

    function showMessage()
	{
        for(var key in errorDictionary)
        {
            var splitValues = key.split('_');
            var prefix = splitValues[0];
            if (document.getElementById(prefix + '_ErrorsContainer') != undefined)
            {
                $('#' + prefix + '_ErrorsContainer').show();
                $('#' + prefix + '_Errors').text(errorDictionary[key]);
            }
            else
            {
                error += trim(errorDictionary[key])+"\n";
            }
        }

        error   = trim(error);
        error = unescapeCharsByCode(error);
        error   = (error == "" ? null : error);

        if(error != null) alert(error);
        if(message != null) alert(message);
	}

</script>
