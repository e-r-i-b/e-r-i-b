<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="com.rssl.phizic.test.Tree" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/style.css">
    <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css">

    <script type="text/javascript" src="${globalUrl}/Utils.js"></script>

</head>

<body>
<%
    Set<Tree> tree= new HashSet<Tree>();

    Tree root1 = new Tree("Городское отделение №7", 7L);
    List<Tree> rootChilds1 = new ArrayList<Tree>();
        Tree temp = new Tree("Дополнительный офис №07/0101", 0101L);
        temp.setParent(root1);
    rootChilds1.add(temp);
        temp = new Tree("Дополнительный офис №07/0101", 0102L);
        temp.setParent(root1);
    rootChilds1.add(temp);
    
    //3 вложенность
    Tree childForRoot1 = new Tree("Дополнительный офис №07/0106", 0106L);
    childForRoot1.setParent(root1);
    List<Tree> childrenForchildForRoot1 =  new ArrayList<Tree>();
        temp = new Tree("Дополнительный офис №07/0106/678", 678L);
        temp.setParent(childForRoot1);
    childrenForchildForRoot1.add(temp);
    childForRoot1.setChildren(childrenForchildForRoot1);
    rootChilds1.add(childForRoot1);

        temp = new Tree("Дополнительный офис №07/0101", 0143L);
        temp.setParent(root1);
    rootChilds1.add(temp);
    root1.setChildren(rootChilds1);

    tree.add(root1);

    Tree root2 = new Tree("Городское отделение №8", 8L);
    List<Tree> rootChilds2 = new ArrayList<Tree>();
        temp = new Tree("Дополнительный офис №07/0101", 0101L);
        temp.setParent(root2);
    rootChilds2.add(temp);
        temp = new Tree("Дополнительный офис №07/0101", 0102L);
        temp.setParent(root2);
    rootChilds2.add(temp);

    //3 вложенность
    Tree childForRoot2 = new Tree("Дополнительный офис №08/0143", 0143L);
    childForRoot2.setParent(root2);
    List<Tree> childrenForchildForRoot2 =  new ArrayList<Tree>();
        temp = new Tree("Дополнительный офис №07/0101", 545L);
        temp.setParent(childForRoot2);
    childrenForchildForRoot2.add(temp);
    childForRoot2.setChildren(childrenForchildForRoot2);

    rootChilds2.add(childForRoot2);
    root2.setChildren(rootChilds2);

    tree.add(root2);        
%>
<c:set var="trees" value="<%=tree%>"/>
<c:if test="${not empty trees}">
<table cellpadding="0" cellspacing="0" width="100%" class="tblInf"  id="DepartmentsListTable">
    <c:forEach items="${trees}" var="tree">
        <tr class="tree">
           <td>
               &nbsp;
			<input type="checkbox" name="selectedDepatmentsIds" value="${tree.id}" style="border:none;">
               <c:choose>
                   <c:when test="${not empty tree.children}">
                       <a href="javascript:hideOrShow('${tree.id}')">
                            ${tree.name}
                       </a>
                       <c:set var="tree" value="${tree}" scope="request"/>
                       <jsp:include page="child.jsp"/>
                   </c:when>
                   <c:otherwise>
                        ${tree.name}
                   </c:otherwise>
               </c:choose>
		    </td>               			 
        </tr>
    </c:forEach>    
</c:if>
</body>
</html>
