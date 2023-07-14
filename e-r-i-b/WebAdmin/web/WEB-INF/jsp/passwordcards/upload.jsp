<%--
  User: Roshka
  Date: 18.08.2006
  Time: 13:50:29
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/passwordcards/uploadResponse" enctype="multipart/form-data">
    
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="passwordCards">
		<tiles:put name="submenu" type="string" value="UploadResponse"/>
	   <tiles:put type="string" name="messagesBundle" value="passwordcardsBundle"/>
       <tiles:put name="pageTitle" type="string">
           Обработка результатов запроса на печать карт паролей.
       </tiles:put>
       <tiles:put name="data" type="string">
         <tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="uploadResponse"/>
			<tiles:put name="name" value="Обработка результатов запроса на печать карт паролей"/>
			<tiles:put name="description" value=""/>
			<tiles:put name="data">
            <tr>
                <td>
	                <b><bean:message key="label.passwordcards.answer.file" bundle="passwordcardsBundle"/></b><br>
	                <html:file property="xmlAnswer" style="width:500px;" size="80"/><br>&nbsp;
                </td>
            </tr>
			<tr>
				<td class="pmntInfAreaSignature"><img src="${globalImagePath}/help1.gif" alt="" border="0" width="12px" height="12px">&nbsp;
                      <bean:message key="label.passwordcards.answer.file.description" bundle="passwordcardsBundle"/>&nbsp;
	                (*<bean:message key="label.passwordcards.answer.file.suffix" bundle="passwordcardsBundle"/>).
                </td>
            </tr>
            </tiles:put>
			<tiles:put name="buttons">
				 <tiles:insert definition="commandButton" flush="false">
				   <tiles:put name="commandKey" value="button.save"/>
				   <tiles:put name="commandHelpKey" value="button.save.help"/>
				   <tiles:put name="bundle" value="passwordcardsBundle"/>
				 </tiles:insert>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
		</tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>