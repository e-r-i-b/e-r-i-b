<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% pageContext.getRequest().setAttribute("mode","Services");%>
<% pageContext.getRequest().setAttribute("userMode","Forms");%>

<html:form action="/forms/download">
	<tiles:insert definition="logMain">
       <tiles:put type="string" name="messagesBundle" value="formsBundle"/>
       <tiles:put name="submenu" type="string" value="Forms"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.close.help"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="action" value="/forms/payment-forms.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

		<tiles:put name="data" type="string">
            <tiles:importAttribute/>
            <c:set var="globalImagePath" value="${globalUrl}/images"/>
            <c:set var="imagePath" value="${skinUrl}/images"/>
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="formName" value="${form.paymentFormName}"/>
			<table cellspacing="4" cellpadding="2" border="0">
                <c:if test="${not empty formName != null}">
                    <tr>
                        <td class="alignRight" nowrap="true">
                            <html:link action="/forms/download.do?form=${formName}&file=.pfd.xml">
                                &nbsp;<bean:message key="lebel.payment.form.defenition.file" bundle="formsBundle"/>&nbsp;
                            </html:link>
                        </td>
                        <td>
                            &nbsp;<img src="${globalImagePath}/help1.gif" alt="" border="0">&nbsp;
                            <bean:message key="payment.form.defenition.file.description" bundle="formsBundle"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td class="alignRight"  nowrap="true">
                            <html:link action="/forms/download.do?form=${formName}&file=.html.xslt">
                                &nbsp;<bean:message key="lebel.payment.form.transformation.html.file" bundle="formsBundle"/>&nbsp;
                            </html:link>
                        </td>
                        <td>
                            &nbsp;<img src="${globalImagePath}/help1.gif" alt="" border="0">&nbsp;
                            <bean:message key="payment.form.transformation.html.file.description" bundle="formsBundle"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td class="alignRight" nowrap="true">
                            <html:link action="/forms/download.do?form=${formName}&file=.xml.xslt">
                                &nbsp;<bean:message key="lebel.payment.form.transformation.xml.file" bundle="formsBundle"/>&nbsp;
                            </html:link>
                        </td>
                        <td>
                            &nbsp;<img src="${globalImagePath}/help1.gif" alt="" border="0">&nbsp;
                            <bean:message key="payment.form.transformation.xml.file.description" bundle="formsBundle"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td class="alignRight" nowrap="true">
                            <html:link action="/forms/download.do?form=${formName}&file=.list-pfd.xml">
                                &nbsp;<bean:message key="lebel.payment.listform.defenition.file" bundle="formsBundle"/>&nbsp;
                            </html:link>
                        </td>
                        <td>
                            &nbsp;<img src="${globalImagePath}/help1.gif" alt="" border="0">&nbsp;
                            <bean:message key="payment.listform.defenition.file.description" bundle="formsBundle"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td class="alignRight" nowrap="true">
                            <html:link action="/forms/download.do?form=${formName}&file=.listFilter-html.xslt">
                                &nbsp;<bean:message key="lebel.payment.listform.transformation.filter.file" bundle="formsBundle"/>&nbsp;
                            </html:link>
                        </td>
                        <td>
                            &nbsp;<img src="${globalImagePath}/help1.gif" alt="" border="0">&nbsp;
                            <bean:message key="payment.listform.transformation.filter.file.description" bundle="formsBundle"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td class="alignRight" nowrap="true">
                            <html:link action="/forms/download.do?form=${formName}&file=.list-html.xslt">
                                &nbsp;<bean:message key="lebel.payment.listform.transformation.list.file" bundle="formsBundle"/>&nbsp;
                            </html:link>
                        </td>
                        <td>
                            &nbsp;<img src="${globalImagePath}/help1.gif" alt="" border="0">&nbsp;
                            <bean:message key="payment.listform.transformation.list.file.description" bundle="formsBundle"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td class="alignRight" nowrap="true">
                            <html:link action="/forms/download.do?form=${formName}&file=.*">
                                &nbsp;<bean:message key="lebel.payment.forms.files" bundle="formsBundle"/>&nbsp;
                            </html:link>
                        </td>
                        <td>
                            &nbsp;<img src="${globalImagePath}/help1.gif" alt="" border="0">&nbsp;
                            <bean:message key="payment.forms.files.description" bundle="formsBundle"/>&nbsp;
                        </td>
                    </tr>
                </c:if>
			</table>
		</tiles:put>
	</tiles:insert>
</html:form>