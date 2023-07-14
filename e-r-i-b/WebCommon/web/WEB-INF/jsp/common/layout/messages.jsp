<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
    
	var error    = null;
	var message  = null;
    

	function showMessage()
	{
        if(error != null) alert(error);
        if(message != null) alert(message);
        
	}

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

    var errorAr = [];
    var messageAr = [];
    
    <%-- /*Вытягиваем сообщения об ошибках из реквеста и запоминаем их в массив*/ --%>
	<phiz:messages  id="error" bundle="${bundle}" field="field" message="error">
        <c:set var="errorPost">${phiz:processBBCodeAndEscapeHtml(error,false)}</c:set>
        errorAr.push("${phiz:escapeForJS(errorPost, true)}");
    </phiz:messages>          
    
    <phiz:messages  id="error" bundle="${bundle}" field="field" message="message">
         <c:set var="messagePost">${phiz:processBBCodeAndEscapeHtml(error,false)}</c:set>
        messageAr.push("${phiz:escapeForJS(messagePost, true)}");
    </phiz:messages>

    for(var i=0; i <errorAr.length; i++)
        error += trim(errorAr[i])+"\n";

    for(var i=0; i<messageAr.length; i++)
        message += trim(messageAr[i])+"\n";

    error   = trim(error);
    message = trim(message);
    error = unescapeCharsByCode(error);
    message = unescapeCharsByCode(message);
	error   = (error == "" ? null : error);
	message = (message == "" ? null : message);

</script>