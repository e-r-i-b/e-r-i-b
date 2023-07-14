<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/documents/agreement">
	<bean:define id="client" name="AgreementTemplateForm" property="client"/>
	<bean:define id="templates" name="AgreementTemplateForm" property="templates"/>
	<bean:define id="activeDocument" name="AgreementTemplateForm" property="activeDocument"/>
<tiles:insert definition="print">
<script type="text/javascript">
</script>
<tiles:put name="data" type="string">
 <table width="100%" cellpadding="4">
	<tr>
  <c:choose>
     <c:when test="${not empty templates}">
		<td class="messageTab" align="center">Идёт обработка...</td>

     <script type="text/javascript">
      function replaseWord (App, Source, Target)
      {
          var wdFindContinue = 1,
		     wdReplaceAll   = 2;
	     if (App != null)
	       App.Selection.Find.Execute("{@"+Source+"@}", true, true, false, false, false, true,
	                                      wdFindContinue, false, Target, wdReplaceAll);
      }

      function infillTemplate (App)
      {
	      replaseWord (App, "Cli_SNAME", <c:choose><c:when test="${not empty client.firstName}">'<bean:write name="client" property="firstName"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_NAME", <c:choose><c:when test="${not empty client.surName}">'<bean:write name="client" property="surName"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_PNAME", <c:choose><c:when test="${not empty client.patrName}">'<bean:write name="client" property="patrName"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_Adr",<c:choose><c:when test="${not empty client.registrationAddress}">'<bean:write name="client" property="registrationAddress"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_Tel",<c:choose><c:when test="${not empty client.homePhone}">'<bean:write name="client" property="homePhone"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "num_dog",<c:choose><c:when test="${not empty client.agreementNumber}">'<bean:write name="client" property="agreementNumber"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "dt_dog",<c:choose><c:when test="${not empty client.agreementDate.time}">'<bean:write name="client" property="agreementDate.time" format="dd.MM.yyyy"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_birthDay",<c:choose><c:when test="${not empty client.birthDay}">'<bean:write name="client" property="birthDay" format="dd.MM.yyyy"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_birthPlace",<c:choose><c:when test="${not empty client.birthPlace}">'<bean:write name="client" property="birthPlace"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_passpNum",<c:choose><c:when test="${not empty activeDocument.documentNumber}">'<bean:write name="activeDocument" property="documentNumber"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_passpSeries",<c:choose><c:when test="${not empty activeDocument.documentSeries}">'<bean:write name="activeDocument" property="documentSeries"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_passpIssueDate",<c:choose><c:when test="${not empty activeDocument.documentIssueDate.time}">'<bean:write name="activeDocument" property="documentIssueDate.time" format="dd.MM.yyyy"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_passpIssueBy",<c:choose><c:when test="${not empty activeDocument.documentIssueBy}">'<bean:write name="activeDocument" property="documentIssueBy"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_email",<c:choose><c:when test="${not empty client.email}">'<bean:write name="client" property="email"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_jobPhone",<c:choose><c:when test="${not empty client.jobPhone}">'<bean:write name="client" property="jobPhone"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_mobilePhone",<c:choose><c:when test="${not empty client.mobilePhone}">'<bean:write name="client" property="mobilePhone"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_subdivisionCode",<c:choose><c:when test="${not empty activeDocument.documentIssueByCode}">'<bean:write name="activeDocument" property="documentIssueByCode"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_residenceAddr",<c:choose><c:when test="${not empty client.residenceAddress}">'<bean:write name="client" property="residenceAddress"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_messageService",<c:choose><c:when test="${not empty client.messageService}">'<bean:write name="client" property="messageService"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_mobileOperator",<c:choose><c:when test="${not empty client.mobileOperator}">'<bean:write name="client" property="mobileOperator"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_serviceInsertionDate",<c:choose><c:when test="${not empty client.serviceInsertionDate.time}">'<bean:write name="client" property="serviceInsertionDate.time" format="dd.MM.yyyy"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_gender",<c:choose><c:when test="${not empty client.gender}">'<bean:write name="client" property="gender"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);

	      replaseWord (App, "Cli_citizen",<c:choose><c:when test="${not empty client.citizenship}">'<bean:write name="client" property="citizenship"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_inn",<c:choose><c:when test="${not empty client.inn}">'<bean:write name="client" property="inn"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_prolongationRejectionDate",<c:choose><c:when test="${not empty client.prolongationRejectionDate.time}">'<bean:write name="client" property="prolongationRejectionDate.time" format="dd.MM.yyyy"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);
	      replaseWord (App, "Cli_contractCancellationCouse",<c:choose><c:when test="${not empty client.contractCancellationCouse}">'<bean:write name="client" property="contractCancellationCouse"/>'</c:when><c:otherwise>''</c:otherwise></c:choose>);

	      replaseWord (App, "Dolg",'');


	      replaseWord (App, "rDoc",'');

	      replaseWord (App, "Cli_IdentityType",
			    <c:choose><c:when test="${not empty activeDocument.documentType}">
			        <c:choose>
						    <c:when test="${activeDocument.documentType == 'REGULAR_PASSPORT_RF'}">'Общегражданский паспорт РФ'</c:when>
							<c:when test="${activeDocument.documentType == 'MILITARY_IDCARD'}">'Удостоверение личности военнослужащего'</c:when>
							<c:when test="${activeDocument.documentType == 'SEAMEN_PASSPORT'}">'Паспорт моряка'</c:when>
							<c:when test="${activeDocument.documentType == 'RESIDENTIAL_PERMIT_RF'}">'Вид на жительство РФ'</c:when>
							<c:when test="${activeDocument.documentType == 'FOREIGN_PASSPORT_RF'}">'Заграничный паспорт РФ'</c:when>
							<c:when test="${activeDocument.documentType == 'OTHER'}">
	                               <c:choose>
	                                    <c:when test="${not empty activeDocument.documentType}">
	                                        '<bean:write name="activeDocument" property="documentType"/>'
                                        </c:when>
	                                    <c:otherwise>' '</c:otherwise>
	                               </c:choose>
							</c:when>
					  </c:choose>
	             </c:when>
			     <c:otherwise>''</c:otherwise>
			     </c:choose>);
	      replaseWord(App, "FamImOth",'');
 /*
    trustingPersonId
    departmentId
 */
      }

      function loadWord()
      {
	      //IE
	      if (window.ActiveXObject)
	      {
		      try {
			      var wordObj = new ActiveXObject("Word.Application");
				  var documentObj;
				  if( wordObj != null )
				  {
					if (!wordObj.Visible)
					  wordObj.Visible = true;
				   <c:forEach items="${templates}" var="template">
					documentObj= wordObj.Documents.Open( "${phiz:calculateActionURL(pageContext, '/documents/dowloadtemplate.do')}?id=${template.id}");
					if (documentObj != null)
					  infillTemplate (wordObj);
				   </c:forEach>
					wordObj = null;
					window.close();
				  }
		      }
		      catch(exception){alert(exception.description);}
		  }
	      else
	      {
		      alert("Данная функция не поддерживается вашим браузером. Для совершения операции воспользуйтесь Internet Explorer 6.0 и выше.");
		      window.close();
	      }

       }
	     loadWord();
     </script>
     </c:when>
	 <c:otherwise>
		<td class="messageTab" align="center">Нет документов договоров у клиента!</td>
	</c:otherwise>
 </c:choose>
	</tr>
 </table>

</tiles:put>
</tiles:insert>
</html:form>
