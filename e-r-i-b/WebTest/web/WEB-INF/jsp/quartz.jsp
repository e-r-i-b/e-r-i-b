<%@ page import="org.quartz.impl.DirectSchedulerFactory"%>
<%@ page import="org.quartz.Scheduler"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.StringWriter" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	Scheduler sh = DirectSchedulerFactory.getInstance().getScheduler("PhizICDistributionsScheduler");
	String error = null;

	String runJob = request.getParameter("runJob");
	if (runJob != null)
	{
		String[] strings = runJob.split("_");
		try
		{
			sh.triggerJob(strings[1], strings[0]);
		}
		catch (Throwable tt)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			tt.printStackTrace(pw);
			pw.close();
			error = sw.getBuffer().toString();

		}
	}
	pageContext.setAttribute("scheduler", sh);
	pageContext.setAttribute("error", error);


%>

<html>
  <head><title>Test Distributions</title></head>
  <body>
    <form action="" method="POST">
	    <c:forEach var="gn" items="${scheduler.jobGroupNames}">
			<b><c:out value="${gn}"/></b>
			<%
				pageContext.setAttribute("jns", sh.getJobNames((String)pageContext.getAttribute("gn")));
			%>
			<c:forEach var="jn" items="${jns}">
				<li>
					<c:out value="${jn}"/>&nbsp;<html:link action="/quartz?runJob=${gn}_${jn}">run</html:link>
				</li>
			</c:forEach>
	    </c:forEach>
	    <c:if test="${not empty error}">
		    <pre>
			    <c:out value="${error}"/>
		    </pre>
	    </c:if>
    </form>
  </body>
</html>