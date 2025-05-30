<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hospital Management System</title>
    <%-- Link to the external stylesheet --%>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">

    <%-- Your existing internal styles --%>
    <style>
        body { font-family: sans-serif; margin: 20px; }
        h1 { color: #333; }
        ul { list-style-type: none; padding: 0; }
        li { margin-bottom: 10px; }
        a {
            text-decoration: none;
            padding: 8px 15px;
            /* Simple styling, you can enhance this */
            border: 1px solid #ccc;
            display: inline-block;
            color: #007bff;
        }
        a:hover {
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>
    <h1>Hospital Management System</h1>
    <ul>
        <li><a href="<%= request.getContextPath() %>/patientadd.jsp">Add Patient</a></li>
        <li><a href="<%= request.getContextPath() %>/patientupdate.jsp">Update Patient</a></li>
        <li><a href="<%= request.getContextPath() %>/patientdelete.jsp">Delete Patient</a></li>
        
        <%-- This is the corrected link --%>
        <li><a href="<%= request.getContextPath() %>/DisplayPatientServlet">View Patients</a></li>
        
        <li><a href="<%= request.getContextPath() %>/reports.jsp">Reports</a></li>
    </ul>
</body>
</html>