<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:set var="standalone" value="${empty param['action']}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:choose>
    <c:when test="${standalone}">
         <c:set var="layoutDefinition" value="dictionariesMain"/>
    </c:when>
    <c:otherwise>
        <c:set var="layoutDefinition" value="dictionary"/>
    </c:otherwise>
</c:choose>

<html:form action="/private/dictionary/offices" onsubmit="return setAction();">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="${layoutDefinition}">
<tiles:put name="submenu" type="string" value="OfficesList"/>

<tiles:put name="pageTitle" type="string" value="Справочник офисов"/>

<tiles:put name="menu" type="string">
	<c:choose>
		<c:when test="${standalone}">
				<script type="text/javascript">
					var addUrl = "${phiz:calculateActionURL(pageContext,'/dictionaries/editSBRFOffice')}";
					function doEdit()
					{
                        checkIfOneItem("selectedId");
						if (!checkOneSelection("selectedId", "Выберите один офис!"))
							return;
						var synchKey = getRadioValue(document.getElementsByName("selectedId"));
						window.location = addUrl + "?synchKey=" + synchKey;
					}
				</script>
				<tiles:insert definition="clientButton" flush="false" service="SBRFOfficesManagement">
					<tiles:put name="commandTextKey" value="button.add.office"/>
					<tiles:put name="commandHelpKey" value="button.add.office.help"/>
					<tiles:put name="bundle" value="dictionariesBundle"/>
					<tiles:put name="action" value="/dictionaries/editSBRFOffice.do"/>
                    <tiles:put name="viewType" value="blueBorder"/>
				</tiles:insert>
		</c:when>
		<c:otherwise>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle" value="dictionariesBundle"/>
				<tiles:put name="onclick" value="window.close();"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</c:otherwise>
	</c:choose>
</tiles:put>

		<%--фильтр--%>
<tiles:put name="filter" type="string">
			<tiles:insert definition="filterTextField" flush="false">
				<tiles:put name="label" value="label.receiver.sbrf.tb"/>
				<tiles:put name="bundle" value="personsBundle"/>
				<tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="region"/>
			</tiles:insert>
			<tiles:insert definition="filterTextField" flush="false">
				<tiles:put name="label" value="label.receiver.sbrf.branch"/>
				<tiles:put name="bundle" value="personsBundle"/>
				<tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="branch"/>
			</tiles:insert>

			<tiles:insert definition="filterTextField" flush="false">
				<tiles:put name="label" value="label.receiver.sbrf.office"/>
				<tiles:put name="bundle" value="personsBundle"/>
				<tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="office"/>
			</tiles:insert>

			<tiles:insert definition="filterTextField" flush="false">
				<tiles:put name="label" value="label.receiver.bank.name"/>
				<tiles:put name="bundle" value="personsBundle"/>
				<tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="name"/>
                <tiles:put name="maxlength" value="30"/>
                <tiles:put name="size" value="30"/>
			</tiles:insert>
			<tiles:insert definition="filterTextField" flush="false">
				<tiles:put name="label" value="label.receiver.sbrf.codCMFR"/>
				<tiles:put name="bundle" value="personsBundle"/>
				<tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="code"/>
			</tiles:insert>

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>


	<script type="text/javascript">
		function setAction(event)
		{
			preventDefault(event);
		<c:if test="${!standalone}">
			var frm = document.forms.item(0);
			frm.action = frm.action + "?action=getOfficesInfo";
		</c:if>
			return true;
		}
		function sendBankData(event)
		{
			var ids = document.getElementsByName("selectedId");
			preventDefault(event);
			for (var i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					var r = ids.item(i).parentNode.parentNode;
					var a = new Array(10);

					a['officeId'] = ids.item(i).value;
					a['officeKey'] = ids.item(i).value;

					if (r.cells[1].innerHTML.indexOf('&nbsp;') == -1)
						a['regionBank'] = trim(r.cells[1].innerHTML);
					else a['tb'] = '';

					if (r.cells[2].innerHTML.indexOf('&nbsp;') == -1)
						a['branch'] = trim(r.cells[2].innerHTML);
					else a['branch'] = '';

					if (r.cells[3].innerHTML.indexOf('&nbsp;') == -1)
						a['office'] = trim(r.cells[3].innerHTML);
					else a['office'] = '';

					if (r.cells[4].innerHTML.indexOf('&nbsp;') == -1)
						a['name'] = trim(r.cells[4].innerHTML);
					else a['name'] = '';

					//address
					if (r.cells[5].innerHTML.indexOf('&nbsp;') == -1)
						a['location'] = trim(r.cells[5].innerHTML);
					else a['location'] = '';

					if (r.cells[6].innerHTML.indexOf('&nbsp;') == -1)
						a['phone'] = trim(r.cells[6].innerHTML);
					else a['phone'] = '';

					//данные банка получателя
//					if (r.cells[8].innerHTML.indexOf('&nbsp;') == -1)
//						a['bankName'] = trim(r.cells[8].innerHTML);
//					else a['bankName'] = '';
//
//					if (r.cells[9].innerHTML.indexOf('&nbsp;') == -1)
//						a['bankCode'] = trim(r.cells[9].innerHTML);
//					else a['bankCode'] = '';
//
//					if (r.cells[10].innerHTML.indexOf('&nbsp;') == -1)
//						a['corAccount'] = trim(r.cells[10].innerHTML);
//					else a['corAccount'] = '';

					window.opener.setOfficeInfo(a);
					window.close();
					return;
				}
			}
			alert("Выберите офис.");
		}


		document.imgPath = "${imagePath}/";
	</script>
</tiles:put>

<tiles:put name="data" type="string">
    <html:hidden property="externalSystemId"/>
    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="id" value="OfficesListTable"/>
        <tiles:put name="emptyMessage">
            <table width="100%" cellpadding="4">
                        <tr>
                            <td class="messageTab" align="center">
                                Не найдено ни одного офиса,<br>соответствующего заданному фильтру!
                            </td>
                        </tr>
                    </table>
        </tiles:put>
        <tiles:put name="buttons">
            <c:choose>
                <c:when test="${standalone}">
                    <tiles:insert definition="clientButton" flush="false" service="SBRFOfficesManagement">
                        <tiles:put name="commandTextKey"     value="button.edit.office"/>
                        <tiles:put name="commandHelpKey"     value="button.edit.office.help"/>
                        <tiles:put name="bundle"             value="dictionariesBundle"/>
                        <tiles:put name="onclick"			 value="doEdit();"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false" service="SBRFOfficesManagement">
                        <tiles:put name="commandKey"     value="button.remove.office"/>
                        <tiles:put name="commandHelpKey" value="button.remove.office.help"/>
                        <tiles:put name="bundle"         value="dictionariesBundle"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedId");
                                return checkSelection('selectedId', 'Выберите офис!');
                            }
                        </tiles:put>
                        <tiles:put name="confirmText"    value="Удалить выбранные офисы?"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.choose"/>
                        <tiles:put name="commandHelpKey" value="button.choose"/>
                        <tiles:put name="bundle"         value="dictionariesBundle"/>
                        <tiles:put name="onclick"        value="sendBankData(event);"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
        </tiles:put>

        <c:set var="sizeResult" value="${fn:length(form.data)}"/>
        <c:set var="pageSize" value="${form.paginationSize}"/>
        <c:set var="pageOffset" value="${form.paginationOffset}"/>
        
        <c:if test="${sizeResult !=0}">
            <tiles:put name="grid">

                <%-- sl:collection не катит - проблемы с пагинацией --%>
                <table cellspacing="0" cellpadding="0" class="tblInf" align="CENTER" width="100%">
                    <tr class="tblInfHeader">
                        <c:if test="${not standalone}">
                            <th width="20px" class="titleTable">&nbsp;</th>
                        </c:if>
                        <th class="titleTable">ТБ</th>
                        <th class="titleTable">ОСБ</th>
                        <th class="titleTable">Филиал</th>
                        <th class="titleTable">Наименование</th>
                        <th class="titleTable">Адрес</th>
                        <th class="titleTable">Телефон</th>
                    </tr>
                    <c:forEach var="item" items="${form.data}" varStatus="stat">
                        <c:if test="${stat.count le pageSize}">
                            <c:choose>
                                <c:when test="${not empty item.code.region}">
                                    <c:set var="region">
                                        <c:out value="${item.code.region}"/>
                                    </c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="region" value="&nbsp;"/>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty item.code.branch}">
                                    <c:set var="branch">
                                        <c:out value="${item.code.branch}"/>
                                    </c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="branch" value="&nbsp;"/>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty item.code.office}">
                                    <c:set var="office">
                                        <c:out value="${item.code.office}"/>
                                    </c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="office" value="&nbsp;"/>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty item.name}">
                                    <c:set var="name">
                                        <c:out value="${item.name}"/>
                                    </c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="name" value="&nbsp;"/>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty item.address}">
                                    <c:set var="address">
                                        <c:out value="${item.address}"/>
                                    </c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="address" value="&nbsp;"/>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty item.telephone}">
                                    <c:set var="telephone">
                                        <c:out value="${item.telephone}"/>
                                    </c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="telephone" value="&nbsp;"/>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty item.telephone}">
                                    <c:set var="telephone">
                                        <c:out value="${item.telephone}"/>
                                    </c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="telephone" value="&nbsp;"/>
                                </c:otherwise>
                            </c:choose>
                            <c:set var="synchKey" value="${item.synchKey}"/>
                            <c:choose>
                                <c:when test="${stat.count%2 !=1}">
                                    <c:set var="trClass" value="ListLine0"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="trClass" value="ListLine1"/>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${not standalone}">
                                <c:set var="noStandRow" value="onclick='selectRow(this);' ondblclick='sendBankData();'"/>
                            </c:if>
                            <tr class="${trClass}">
                                <c:if test="${not standalone}">
                                    <td class="ListItem">
                                        <input type="radio" name="selectedId" value="${synchKey}">
                                    </td>
                                </c:if>
                                <td class="ListItem">
                                    ${region}
                                </td>
                                <td class="ListItem">
                                    ${branch}
                                </td>
                                <td class="ListItem">
                                    ${office}
                                </td>
                                <td class="ListItem">
                                    ${name}
                                </td>
                                <td class="ListItem">
                                    ${address}
                                </td>
                                <td class="ListItem">
                                    ${telephone}
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>


                    <c:if test="${pageOffset > 0 || sizeResult > pageSize}">
                        <tr>
                            <td colspan="7">
                                <table cellspacing="0" cellpadding="0" class="tblNumRec">
                                    <tr>
                                        <td nowrap="nowrap">Записи с <span class="tblNumRecIns">${pageOffset + 1} по <span>${pageOffset+pageSize}</span></td>

                                        <c:if test="${pageOffset>0}">
                                            <td>&nbsp;</td>
                                            <td nowrap="nowrap">
                                                <img src="${imagePath}/iconSm_triangleLeft.gif" alt="" border="0">
                                                <a href="#" onclick="addElementToForm('paginationOffset','${pageOffset}');
                                                                setElement('paginationOffset', '${pageOffset-pageSize}');
                                                                callOperation(event,'button.filter'); return false" href="#">
                                                    Предыдущие ${pageSize}
                                                </a>
                                            </td>
                                        </c:if>
                                        <td>&nbsp;</td>
                                        <c:if test="${pageSize < sizeResult}">
                                            <td nowrap="nowrap">
                                                <a href="#" onclick="addElementToForm('paginationOffset','${pageOffset}');
                                                                setElement('paginationOffset', '${pageOffset+pageSize}');
                                                                callOperation(event,'button.filter'); return false">
                                                    Следующие ${pageSize}
                                                </a>
                                                <img src="${imagePath}/iconSm_triangleRight.gif" alt="" border="0">
                                            </td>
                                        </c:if>

                                        <td nowrap="nowrap" align="right" width="100%">
                                            Записей на странице
                                            <html:select property="paginationSize" onchange="callOperation(event,'button.filter');">
                                                <html:option value="20">20</html:option>
                                                <html:option value="50">50</html:option>
                                                <html:option value="100">100</html:option>
                                            </html:select>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </c:if>
                </table>
            </tiles:put>
        </c:if>
    </tiles:insert>
</tiles:put>

</tiles:insert>

</html:form>
