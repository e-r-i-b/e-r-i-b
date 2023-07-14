<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/forms/payment-forms"  onsubmit="return setEmptyAction(event);">
	<tiles:insert definition="logMain">
		<tiles:put name="submenu" type="string" value="Forms"/>
        <tiles:put name="pageTitle" type="string">
            Формы платежей
        </tiles:put>
		<tiles:put type="string" name="messagesBundle" value="formsBundle"/>

		<tiles:put name="menu" type="string">
			<script type="text/javascript">
				var addUrl = "${phiz:calculateActionURL(pageContext,'/forms/editPrintForm')}";

				function doEdit()
				{
                    checkIfOneItem("selectedIds");
					if (!checkOneSelection("selectedIds", "Выберите одну форму платежа"))
						return;

					var id = getRadioValue(document.getElementsByName("selectedIds"));
					window.location = addUrl + "?formName=" + id;
				}
			</script>
			<tiles:insert definition="clientButton" flush="false" operation="UploadFormOperation">
				<tiles:put name="commandTextKey"     value="button.add"/>
				<tiles:put name="commandHelpKey" value="button.add.help"/>
				<tiles:put name="bundle"  value="formsBundle"/>
				<tiles:put name="image"   value=""/>
				<tiles:put name="action"   value="/forms/upload"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
			<script type="text/javascript">
				var downloadUrl = "${phiz:calculateActionURL(pageContext,'/forms/download')}";

				function doDownload()
				{
                    checkIfOneItem("selectedIds");
					if (!checkOneSelection("selectedIds", "Выберите одну форму платежа"))
						return;

					var id = getRadioValue(document.getElementsByName("selectedIds"))
					window.location = downloadUrl + "?form=" + id;
				}
			</script>
			<tiles:insert definition="clientButton" operation="UpdateFormOperation" flush="false">
				<tiles:put name="commandTextKey"     value="button.printForms"/>
				<tiles:put name="commandHelpKey" value="button.printForms"/>
				<tiles:put name="bundle"  value="formsBundle"/>
				<tiles:put name="onclick" value="doEdit();"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>

		</tiles:put>

		<tiles:put name="data" type="string">
        <tiles:importAttribute/>

        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>
            
		<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="paymentForms"/>
        <tiles:put name="buttons">
             <tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.download"/>
				<tiles:put name="commandHelpKey" value="button.download.help"/>
				<tiles:put name="bundle"  value="formsBundle"/>
				<tiles:put name="onclick" value="doDownload();"/>
			</tiles:insert>
        </tiles:put>
        <tiles:put name="grid">
                <sl:collection id="listElement" model="list" property="data" selectType="checkbox" selectName="selectedIds" selectProperty="name">
                    <sl:collectionItem title="Имя">
                        <c:choose>
                            <c:when test="${!empty listElement.templates or !empty listElement.packages}">
                                <img src="${imagePath}/iconSm_print.gif">
                            </c:when>
                            <c:otherwise>
                                <img src="${imagePath}/iconSm_unprint.gif">
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${not empty listElement}">
                            &nbsp;<bean:write name="listElement" property="name"/>&nbsp;
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="Описание" name="listElement"  property="description"/>
                </sl:collection>
            </tiles:put>
            <tiles:put name="emptyMessage" value="Не найдено ни одной формы платежа"/>
        </tiles:insert>
        </tiles:put>
	</tiles:insert>
</html:form>