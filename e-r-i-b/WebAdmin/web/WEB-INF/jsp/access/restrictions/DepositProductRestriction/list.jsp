<%--
  User: Kosyakov
  Date: 15.11.2005
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/access/restrictions/DepositProductList" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="dictionary">

<tiles:put name="pageTitle" type="string" value="Выбор депозитных продуктов"/>

<tiles:put name="menu" type="string">
    <phiz:menuButton id="b1"
             onclick="javascript:turnSelection (true,'selectedIds',event)"
             title="Разрешить операцию для всех продуктов">
             Разрешить все
     </phiz:menuButton>
     <phiz:menuButton id="b2"
             onclick="javascript:turnSelection (false,'selectedIds',event)"
             title="Запретить операцию для всех продуктов">
             Запретить все
     </phiz:menuButton>
    <phiz:menuButton id="b3"
			operationKey="button.save"    bundle="personsBundle"
            title="Сохранить задание параметров">Сохранить
    </phiz:menuButton>
	<phiz:menuButton id="b3" onclick="window.close();" title="Закрыть окно"
                     >Отменить</phiz:menuButton>
 </tiles:put>

<tiles:put name="data" type="string">
	<c:set var="form" value="${DepositProductListRestrictionForm}"/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="depositProductRestrictionList"/>
		<tiles:put name="text" value=""/>
		<tiles:put name="head">
			<td>Доступ</td>
			<td>Депозитный продукт</td>
		</tiles:put>
		<tiles:put name="data">
		    <% int lineNumber = 0;%>
			<c:forEach var="product" items="${form.products}">
            <% lineNumber++;%>
			<tr class="listLine<%=lineNumber%2%>">
				<td id="accessCell<%=lineNumber%>" class="ListItem">
		  	    <html:multibox property="selectedIds" style="border:none"
                       onclick="setAccessDescription(this)">
		  	    ${product.id}
                </html:multibox>
                <script type="text/javascript">getAccessDescription('<%=lineNumber%>');</script>
                </td>
				<td class="ListItem">${product.name}</td>
			</tr>
			</c:forEach>
		</tiles:put>
	</tiles:insert>

</tiles:put>

</tiles:insert>

</html:form>
