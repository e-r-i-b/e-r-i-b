<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/claims/print" onsubmit="return setEmptyAction(event)">
	<c:set var="form" value="${PrintDocumentsForm}"/>
	<c:set var="packages"  value="${form.packages}"/>
	<c:set var="templates"  value="${form.templates}"/>
	<c:set var="businessDocuments"  value="${form.businessDocuments}"/>
	<c:set var="fieldValues"  value="${form.fieldValues}"/>

<tiles:insert definition="print">
<tiles:put name="data" type="string">
	
<table width="100%" cellpadding="4">
<tr>
	<c:choose>
		<c:when test="${(not empty templates || not empty packages) && not empty businessDocuments}">
			<td class="messageTab" align="center">Идёт обработка...</td>
			<script type="text/javascript">
				function replaseWord (App, Source, Target)
                {
                    var wdFindContinue = 1,
		            wdReplaceAll   = 2;
	                if (App != null)
	                App.Selection.Find.Execute("{#"+Source+"#}", true, true, false, false, false, true,
		                                      wdFindContinue, false, Target, wdReplaceAll);
                }

				function replaseExcel (App, Source, Target)
                {
	                App.Cells.replace("{#"+Source+"#}",Target);
                }

				function loadWord()
		        {
	            //IE
	            if (window.ActiveXObject)
	            {
			        try {
				        var documentObj;
							<c:set var="number"  value="0"/>
							<c:forEach items="${businessDocuments}" var="document">
								<c:forEach items="${templates[number]}" var="listElement">
				                    <c:if test="${listElement.fileType=='.DOC'}">
				                        var wordObj = new ActiveXObject("Word.Application");
									    if( wordObj != null )
										{
											documentObj= wordObj.Documents.Open("${phiz:calculateActionURL(pageContext, '/documents/dowloadtemplate.do')}?id=${listElement.id}");
											if (documentObj != null)
											{
												<c:forEach items="${fieldValues[number]}" var="values">
													<c:if test="${values.key!='state'}">
														replaseWord(wordObj,"${values.key}","${values.value}");
													</c:if>
													<c:if test="${values.key=='state'}">
														<c:choose>
															<c:when test="${values.value=='I'}">replaseWord(wordObj,"${values.key}","Введен");</c:when>
															<c:when test="${values.value=='W'}">replaseWord(wordObj,"${values.key}","Обрабатывается");</c:when>
															<c:when test="${values.value=='S'}">replaseWord(wordObj,"${values.key}","Исполнен");</c:when>
															<c:when test="${values.value=='E'}">replaseWord(wordObj,"${values.key}","Отказан");</c:when>
															<c:when test="${values.value=='D'}">replaseWord(wordObj,"${values.key}","Отказан");</c:when>
															<c:when test="${values.value=='V'}">replaseWord(wordObj,"${values.key}","Отозван");</c:when>
														<c:otherwise>Fix me</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach>
										    }
											if (!wordObj.Visible)
								                   wordObj.Visible = true;
											wordObj = null;
										}					                    
				                    </c:if>
				                    <c:if test="${listElement.fileType=='.XLS'}">
					                    var wordObj = new ActiveXObject("Excel.Application");
										if( wordObj != null )
										{
											documentObj= wordObj.Workbooks.Open( "${phiz:calculateActionURL(pageContext, '/documents/dowloadtemplate.do')}?id=${listElement.id}" );
											if (documentObj != null)
											{
												<c:forEach items="${fieldValues[number]}" var="values">
													<c:if test="${values.key!='state'}">
														replaseExcel(wordObj,"${values.key}","${values.value}");
													</c:if>
													<c:if test="${values.key=='state'}">
														<c:choose>
															<c:when test="${values.value=='I'}">replaseExcel(wordObj,"${values.key}","Введен");</c:when>
															<c:when test="${values.value=='W'}">replaseExcel(wordObj,"${values.key}","Обрабатывается");</c:when>
															<c:when test="${values.value=='S'}">replaseExcel(wordObj,"${values.key}","Исполнен");</c:when>
															<c:when test="${values.value=='E'}">replaseExcel(wordObj,"${values.key}","Отказан");</c:when>
															<c:when test="${values.value=='D'}">replaseExcel(wordObj,"${values.key}","Отказан");</c:when>
															<c:when test="${values.value=='V'}">replaseExcel(wordObj,"${values.key}","Отозван");</c:when>
														<c:otherwise>Fix me</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach>
										    }
											if (!wordObj.Visible)
								                   wordObj.Visible = true;
											wordObj = null;
										}
									</c:if>
				                    <c:if test="${listElement.fileType=='.HTML'}">
											openWindow(event, "${phiz:calculateActionURL(pageContext, '/claims/printHTMLTemplate.do')}?templateId=${listElement.id}&documentId=${document.id}","","resizable=1,menubar=1,toolbar=0,scrollbars=1,width=0,height=0");
				                        </c:if>
								</c:forEach>
								<c:forEach items="${packages[number]}" var="package">
									<c:forEach items="${package.templates}" var="listElement">
				                        <c:if test="${listElement.fileType=='.DOC'}">
					                        var wordObj = new ActiveXObject("Word.Application");
										    if( wordObj != null )
											{
												documentObj= wordObj.Documents.Open("${phiz:calculateActionURL(pageContext, '/documents/dowloadtemplate.do')}?id=${listElement.id}");
												if (documentObj != null)
												{
													<c:forEach items="${fieldValues[number]}" var="values">
														<c:if test="${values.key!='state'}">
															replaseWord(wordObj,"${values.key}","${values.value}");
														</c:if>
														<c:if test="${values.key=='state'}">
															<c:choose>
																<c:when test="${values.value=='I'}">replaseWord(wordObj,"${values.key}","Введен");</c:when>
																<c:when test="${values.value=='W'}">replaseWord(wordObj,"${values.key}","Обрабатывается");</c:when>
																<c:when test="${values.value=='S'}">replaseWord(wordObj,"${values.key}","Исполнен");</c:when>
																<c:when test="${values.value=='E'}">replaseWord(wordObj,"${values.key}","Отказан");</c:when>
																<c:when test="${values.value=='D'}">replaseWord(wordObj,"${values.key}","Отказан");</c:when>
																<c:when test="${values.value=='V'}">replaseWord(wordObj,"${values.key}","Отозван");</c:when>
															<c:otherwise>Fix me</c:otherwise>
															</c:choose>
														</c:if>
													</c:forEach>
											    }
												if (!wordObj.Visible)
								                    wordObj.Visible = true;
												wordObj = null;
											}
										</c:if>
										<c:if test="${listElement.fileType=='.XLS'}">
					                        var wordObj = new ActiveXObject("Excel.Application");
										    if( wordObj != null )
											{
												documentObj= wordObj.Workbooks.Open("${phiz:calculateActionURL(pageContext, '/documents/dowloadtemplate.do')}?id=${listElement.id}");
												if (documentObj != null)
												{
													<c:forEach items="${fieldValues[number]}" var="values">
														<c:if test="${values.key!='state'}">
															replaseExcel(wordObj,"${values.key}","${values.value}");
														</c:if>
														<c:if test="${values.key=='state'}">
															<c:choose>
																<c:when test="${values.value=='I'}">replaseExcel(wordObj,"${values.key}","Введен");</c:when>
																<c:when test="${values.value=='W'}">replaseExcel(wordObj,"${values.key}","Обрабатывается");</c:when>
																<c:when test="${values.value=='S'}">replaseExcel(wordObj,"${values.key}","Исполнен");</c:when>
																<c:when test="${values.value=='E'}">replaseExcel(wordObj,"${values.key}","Отказан");</c:when>
																<c:when test="${values.value=='D'}">replaseExcel(wordObj,"${values.key}","Отказан");</c:when>
																<c:when test="${values.value=='V'}">replaseExcel(wordObj,"${values.key}","Отозван");</c:when>
															<c:otherwise>Fix me</c:otherwise>
															</c:choose>
														</c:if>
													</c:forEach>
											    }
												if (!wordObj.Visible)
								                    wordObj.Visible = true;
												wordObj = null;
											}
										</c:if>
				                        <c:if test="${listElement.fileType=='.HTML'}">
											openWindow(event, "${phiz:calculateActionURL(pageContext, '/claims/printHTMLTemplate.do')}?templateId=${listElement.id}&documentId=${document.id}","","resizable=1,menubar=1,toolbar=0,scrollbars=1,width=0,height=0");
				                        </c:if>
									</c:forEach>
								</c:forEach>
								<c:set var="number"  value="${number + 1}"/>								
							</c:forEach>
							window.close();
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
