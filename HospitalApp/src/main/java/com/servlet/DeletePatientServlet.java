package com.servlet;
import com.dao.HospitalDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/DeletePatientServlet")
public class DeletePatientServlet extends HttpServlet {

	 private HospitalDAO dao;

	    @Override
	    public void init() {
	        dao = new HospitalDAO();
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        try {
	            int patientID = Integer.parseInt(request.getParameter("patientID"));
	            boolean deleted = dao.deletePatient(patientID);
	            if (deleted) {
	                response.sendRedirect("patientdelete.jsp?msg=Patient deleted successfully");
	            } else {
	                response.sendRedirect("patientdelete.jsp?msg=Failed to delete patient");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("patientdelete.jsp?msg=Error: " + e.getMessage());
	        }
	    }
	}

