<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/claims/printDocumentsList" onsubmit="return setEmptyAction(event)">
	<tiles:insert definition="paymentsTemplates">
		<c:set var="form" value="${ViewPrintDocumentsListForm}"/>
		<tiles:put name="data" type="string">
			<script>
				function switchAll()
				{
					var checkList = document.getElementsByTagName("input");
					for (var i = 1; i < checkList.length; i++)
					{
						checkList[i].checked = checkList[0].checked;
					}
				}

				function printDocuments()
				{
					var checkList = document.getElementsByTagName("input");
					var params= '';
					var checked = false;
					for (var i = 1; i < checkList.length; i++)
						if(checkList[i].name.toString().indexOf('selectedPackage') != -1)
						{
							//if(checkList[i].checked)
							params = params + 'package:'+checkList[i].value+'templates:';
							for (var j = 1; j < checkList.length; j++)
								if(checkList[j].name.toString().indexOf('selectedTemplates'+checkList[i].value) != -1)
									if(checkList[j].checked)
									{
										params = params + 'temp:'+checkList[j].value;
										checked = true;
									}
						}
					params = params + 'endofpack;';
					var templateList = document.getElementsByName("selectedTemplates");
					for (var i = 0; i < templateList.length; i++)
						if(templateList[i].checked)
						{
							params = params + 'temp:'+templateList[i].value;
							checked = true;
						}
					if(!checked){
						alert('Ќе выбран ни один документ');
						return;
					}
					var payments = '';
					<c:forEach var="paymentsId" items="${form.paymentsId}">
						payments = payments + '&paymentsId='+<c:out value="${paymentsId}"/>;
					</c:forEach>
                    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/claims/print.do')}"/>
					window.location = '${url}?params='+params+payments;
				}
			</script>
			<table cellspacing="2" cellpadding="0" style="margin:10px;">
			<c:if test="${not empty form.packages or not empty form.templates}">				
				<tr><td>
                    <table cellspacing="0" cellpadding="0" width="300px" class="userTab">
		            <!-- заголовок списка -->
			         <tr class="titleTable">
						<td width="20px">
							 <html:checkbox property="selectAllPackages" style="border:none" onclick="switchAll();"/>
					    </td>
						<td width="100%">Ќаименование</td>
					 </tr>
					<!-- строки списка -->
	                 <c:set var="lineNumber" value="0"/>
                     <c:forEach var="packagesList" items="${form.packages}">
	                    <c:set var="lineNumber" value="${lineNumber+1}"/>
						<tr class="listLine${packagesList.id % 2}">
                            <td align=center class="ListItem">
	                            <input type="checkbox" name="selectedPackage${packagesList.id}" value="${packagesList.id}" style="border: medium none ;"
	                                   onclick="switchSelection('selectedPackage${packagesList.id}','selectedTemplates${packagesList.id}');"/>
							</td>
							<td class="ListItem" style="color:black;font-weight:bold;">
								<bean:write name="packagesList" property="name"/>
							</td>
						</tr>
	                     <c:forEach var="template" items="${packagesList.templates}" varStatus="lineInfo">
		                    <c:set var="lineNumber" value="${lineNumber+1}"/>
							<tr class="listLine${packagesList.id % 2}">
								<td align=center class="ListItem">
									<input type="checkbox" name="selectedTemplates${packagesList.id}" value="${template.id}" style="border: medium none ;"/>																		
								</td>								
								<td class="listItem" colspan="2"  valign="top">
									<nobr>&nbsp;&nbsp;&nbsp;<bean:write name="template" property="name"/>&nbsp;</nobr>
								</td>
							</tr>
						</c:forEach>
		            </c:forEach>
	                <c:forEach var="templatesList" items="${form.templates}">
		                <c:set var="lineNumber" value="${lineNumber+1}"/>
						<tr class="listLine${packagesList.id % 2}">
                            <td align=center class="ListItem">
								<html:multibox property="selectedTemplates" style="border:none">
									<bean:write name="templatesList" property="id"/>
								</html:multibox>
							</td>
							<td class="ListItem">
								<bean:write name="templatesList" property="name"/>
							</td>
						</tr>
		            </c:forEach>
		            </table>
				</td></tr>
				<tr>
				<td align="center">
					<table height="100%" width="30px" cellspacing="0" cellpadding="0">
						<tr><td>
							<tiles:insert definition="clientButton" flush="false">
								<tiles:put name="commandTextKey"     value="button.print.documents"/>
								<tiles:put name="commandHelpKey" value="button.print.documents.help"/>
								<tiles:put name="bundle"  value="claimsBundle"/>
								<tiles:put name="onclick" value="printDocuments();"/>
							</tiles:insert>
						</td></tr>
					</table>
				</td>
				</tr>
	        </c:if>
			<c:if test="${empty form.packages and empty form.templates}">
				<tr>
					<td class="messageTab" align="center">ƒл€ данного вида документа печатные формы не настроены</td>
				</tr>
			</c:if>
			</table>
		</tiles:put>
		<script>
			var width = 340;
			var height = 400;
            <c:if test="${lineNumber > 0}">
                height = (${lineNumber}+1)*24+70;
            </c:if>
			window.resizeTo(width,height);
			window.moveTo((screen.width-width)/2,(screen.height - height)/2);
		</script>
	</tiles:insert>
</html:form>