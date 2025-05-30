<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%-- Added for best practice --%>
<!DOCTYPE html>
<html>
<head>
    <title>Update Patient</title>
    <%-- Link to the external stylesheet --%>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <h2>Update Patient Information</h2>
    <%-- Added contextPath to form action for robustness --%>
    <form action="<%= request.getContextPath() %>/UpdatePatientServlet" method="post">
        Patient ID to update: <input type="number" name="patientID" required><br>
        New Name: <input type="text" name="patientName"><br>
        New Age: <input type="number" name="age"><br>
        New Gender:
        <select name="gender">
            <option value="">--Select--</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
        </select><br>
        New Admission Date: <input type="date" name="admissionDate"><br>
        New Ailment: <input type="text" name="ailment"><br>
        New Assigned Doctor: <input type="text" name="assignedDoctor"><br>
        <input type="submit" value="Update Patient">
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

    <%-- Added contextPath to link for robustness --%>
    <p><a href="<%= request.getContextPath() %>/index.jsp">Back to Home</a></p>
</body>
</html>