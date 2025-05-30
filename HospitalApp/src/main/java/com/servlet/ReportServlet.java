package com.servlet;
import com.dao.HospitalDAO;
import com.model.Patient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {

	private HospitalDAO dao;

    @Override
    public void init() {
        dao = new HospitalDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        List<Patient> patients = null;

        try {
            switch (type) {
                case "dateRange":
                    String fromDate = request.getParameter("fromDate");
                    String toDate = request.getParameter("toDate");
                    patients = dao.getPatientsByDateRange(fromDate, toDate);
                    break;
                case "ailment":
                    String ailment = request.getParameter("ailment");
                    patients = dao.getPatientsByAilment(ailment);
                    break;
                case "doctor":
                    String doctor = request.getParameter("doctor");
                    patients = dao.getPatientsByDoctor(doctor);
                    break;
                default:
                    // Invalid type
                    request.setAttribute("msg", "Invalid report type");
                    request.getRequestDispatcher("reports.jsp").forward(request, response);
                    return;
            }

            request.setAttribute("patients", patients);
            request.getRequestDispatcher("report_result.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Error: " + e.getMessage());
            request.getRequestDispatcher("reports.jsp").forward(request, response);
        }
    }	}

