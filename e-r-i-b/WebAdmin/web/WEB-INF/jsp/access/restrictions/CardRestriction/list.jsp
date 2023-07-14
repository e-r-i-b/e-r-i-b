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

<html:form action="/access/restrictions/CardList" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="dictionary">

<tiles:put name="pageTitle" type="string" value="Выбор карт"/>

<tiles:put name="menu" type="string">
    <phiz:menuButton id="b1"
             onclick="javascript:turnSelection (true,'selectedIds',event)"
             title="Разрешить операцию для всех карт">
             Разрешить все
     </phiz:menuButton>
     <phiz:menuButton id="b2"
             onclick="javascript:turnSelection (false,'selectedIds',event)"
             title="Запретить операцию для всех карт">
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
	<c:set var="form" value="${CardListRestrictionForm}"/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="cardRestrictionList"/>
		<tiles:put name="text" value=""/>
		<tiles:put name="head">
	        <td>Доступ</td>
			<td>Номер карты</td>
		</tiles:put>
		<tiles:put name="data">
            <% int lineNumber = 0;%>
			<c:forEach var="cardLink" items="${form.cards}">
            <% lineNumber++;%>
            <c:set var="card" value="${cardLink.card}"/>
			<tr class="listLine<%=lineNumber%2%>">
				<td id="accessCell<%=lineNumber%>" class="ListItem">
		  	        <html:multibox property="selectedIds" style="border:none"
                          onclick="setAccessDescription(this)">
		  	        ${cardLink.id}
                    </html:multibox>
                <script>getAccessDescription('<%=lineNumber%>');</script>
                </td>
				<td class="ListItem">${card.number} ${card.type}</td>
			</tr>
	</c:forEach>
	</tiles:put>
</tiles:insert>

</tiles:put>
</tiles:insert>

</html:form>
