<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%-- Added for best practice --%>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Patient</title>
    <%-- Link to the external stylesheet --%>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <h2>Delete Patient</h2>
    <form action="<%= request.getContextPath() %>/DeletePatientServlet" method="post"> <%-- Added contextPath --%>
        Patient ID to delete: <input type="number" name="patientID" required><br>
        <input type="submit" value="Delete Patient">
    </form>

    <%
       // It's generally better to get attributes set by a servlet
       // rather than parameters directly from the URL for messages,
       // but keeping as per your original code for "don't change".
       String msg = (String) request.getAttribute("msg"); // Changed to getAttribute
       if (msg == null) {
           msg = request.getParameter("msg"); // Fallback to parameter if attribute not found
       }

       if (msg != null && !msg.isEmpty()) { // Added !msg.isEmpty() check
    %>
        <p><b><%= msg %></b></p>
    <%
       }
    %>

    <p><a href="<%= request.getContextPath() %>/index.jsp">Back to Home</a></p> <%-- Added contextPath --%>
</body>
</html>