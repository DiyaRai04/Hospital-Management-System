package com.dao;
import com.model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hospitaldb";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = ""; // 🔁 IMPORTANT: Ensure this is correct for your MySQL setup

    // Get DB connection
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("CRITICAL: MySQL JDBC Driver not found. Ensure the JAR is in WEB-INF/lib.");
            e.printStackTrace();
            // Re-throw as SQLException because without the driver, no DB operations can proceed.
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    // ✅ Add patient
    public boolean addPatient(Patient p, java.sql.Date sqlAdmissionDate) {
        String sql = "INSERT INTO patients (PatientID, PatientName, Age, Gender, AdmissionDate, Ailment, AssignedDoctor) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getPatientID());
            ps.setString(2, p.getPatientName());
            ps.setInt(3, p.getAge());
            ps.setString(4, p.getGender());
            ps.setDate(5, sqlAdmissionDate);
            ps.setString(6, p.getAilment());
            ps.setString(7, p.getAssignedDoctor());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQLException in addPatient: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Delete patient
    public boolean deletePatient(int patientID) {
        String sql = "DELETE FROM Patients WHERE PatientID = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientID);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("SQLException in deletePatient: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Get all patients
    public List<Patient> getAllPatient() {
        List<Patient> patients = new ArrayList<>();
        // IMPORTANT: Ensure "Patients" is the correct case-sensitive name of your table.
        String sql = "SELECT * FROM patients";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Patient p = new Patient();
                // Ensure these column names EXACTLY match your database table (case-sensitivity can matter)
                p.setPatientID(rs.getInt("PatientID"));
                p.setPatientName(rs.getString("PatientName"));
                p.setAge(rs.getInt("Age"));
                p.setGender(rs.getString("Gender"));

                java.sql.Date admissionDateDb = rs.getDate("AdmissionDate");
                if (admissionDateDb != null) {
                    // Assuming Patient model's setAdmissionDate takes a String in "yyyy-MM-dd" format
                    p.setAdmissionDate(admissionDateDb.toString());
                } else {
                    p.setAdmissionDate(null); // Or "" if your Patient model expects an empty string for no date
                }

                p.setAilment(rs.getString("Ailment"));
                p.setAssignedDoctor(rs.getString("AssignedDoctor"));
                patients.add(p);
            }
        } catch (SQLException e) {
            // This error message will appear in your server's console/logs if there's an SQL problem
            System.err.println("SQLException in getAllPatient: " + e.getMessage());
            e.printStackTrace(); // This provides the full stack trace for debugging
        }
        return patients; // Will return an empty list if an exception occurs or no rows are found
    }

    // ✅ Update patient (version taking Patient object and java.sql.Date)
    public boolean updatePatient(Patient p, java.sql.Date sqlAdmissionDate) {
        String sql = "UPDATE Patients SET PatientName=?, Age=?, Gender=?, AdmissionDate=?, Ailment=?, AssignedDoctor=? WHERE PatientID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getPatientName());
            ps.setInt(2, p.getAge());
            ps.setString(3, p.getGender());
            ps.setDate(4, sqlAdmissionDate);
            ps.setString(5, p.getAilment());
            ps.setString(6, p.getAssignedDoctor());
            ps.setInt(7, p.getPatientID());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("SQLException in updatePatient (with Date): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Get patient by ID (optional helper)
    public Patient getPatientById(int patientID) {
        String sql = "SELECT * FROM Patients WHERE PatientID = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Patient p = new Patient();
                    p.setPatientID(rs.getInt("PatientID"));
                    p.setPatientName(rs.getString("PatientName"));
                    p.setAge(rs.getInt("Age"));
                    p.setGender(rs.getString("Gender"));

                    java.sql.Date admissionDateDb = rs.getDate("AdmissionDate");
                    if (admissionDateDb != null) {
                        p.setAdmissionDate(admissionDateDb.toString());
                    } else {
                        p.setAdmissionDate(null);
                    }

                    p.setAilment(rs.getString("Ailment"));
                    p.setAssignedDoctor(rs.getString("AssignedDoctor"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException in getPatientById: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    //Get patients by admission date range
    public List<Patient> getPatientsByDateRange(String fromDate, String toDate) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients WHERE AdmissionDate BETWEEN ? AND ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(fromDate)); // Assumes fromDate is "yyyy-MM-dd"
            ps.setDate(2, java.sql.Date.valueOf(toDate));   // Assumes toDate is "yyyy-MM-dd"

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Patient p = new Patient();
                    p.setPatientID(rs.getInt("PatientID"));
                    p.setPatientName(rs.getString("PatientName"));
                    p.setAge(rs.getInt("Age"));
                    p.setGender(rs.getString("Gender"));
                    java.sql.Date admissionDateDb = rs.getDate("AdmissionDate");
                    if (admissionDateDb != null) {
                        p.setAdmissionDate(admissionDateDb.toString());
                    } else {
                        p.setAdmissionDate(null);
                    }
                    p.setAilment(rs.getString("Ailment"));
                    p.setAssignedDoctor(rs.getString("AssignedDoctor"));
                    patients.add(p);
                }
            }
        } catch (IllegalArgumentException iae) { // Catch if date parsing fails
            System.err.println("IllegalArgumentException in getPatientsByDateRange (invalid date format?): " + iae.getMessage());
            iae.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQLException in getPatientsByDateRange: " + e.getMessage());
            e.printStackTrace();
        }
        return patients;
    }

    //Get patients by ailment
    public List<Patient> getPatientsByAilment(String ailment) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients WHERE Ailment = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ailment);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Patient p = new Patient();
                    p.setPatientID(rs.getInt("PatientID"));
                    p.setPatientName(rs.getString("PatientName"));
                    p.setAge(rs.getInt("Age"));
                    p.setGender(rs.getString("Gender"));
                    java.sql.Date admissionDateDb = rs.getDate("AdmissionDate");
                    if (admissionDateDb != null) {
                        p.setAdmissionDate(admissionDateDb.toString());
                    } else {
                        p.setAdmissionDate(null);
                    }
                    p.setAilment(rs.getString("Ailment"));
                    p.setAssignedDoctor(rs.getString("AssignedDoctor"));
                    patients.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException in getPatientsByAilment: " + e.getMessage());
            e.printStackTrace();
        }
        return patients;
    }

    //Get patients by doctor
    public List<Patient> getPatientsByDoctor(String doctor) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients WHERE AssignedDoctor = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, doctor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Patient p = new Patient();
                    p.setPatientID(rs.getInt("PatientID"));
                    p.setPatientName(rs.getString("PatientName"));
                    p.setAge(rs.getInt("Age"));
                    p.setGender(rs.getString("Gender"));
                    java.sql.Date admissionDateDb = rs.getDate("AdmissionDate");
                    if (admissionDateDb != null) {
                        p.setAdmissionDate(admissionDateDb.toString());
                    } else {
                        p.setAdmissionDate(null);
                    }
                    p.setAilment(rs.getString("Ailment"));
                    p.setAssignedDoctor(rs.getString("AssignedDoctor"));
                    patients.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException in getPatientsByDoctor: " + e.getMessage());
            e.printStackTrace();
        }
        return patients;
    }

    // ✅ Update patient (version taking only Patient object)
    public boolean updatePatient(Patient p) {
        String sql = "UPDATE Patients SET PatientName = ?, Age = ?, Gender = ?, AdmissionDate = ?, Ailment = ?, AssignedDoctor = ? WHERE PatientID = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getPatientName());
            ps.setInt(2, p.getAge());
            ps.setString(3, p.getGender());

            // Assuming Patient.getAdmissionDate() returns a String in "yyyy-MM-dd" format
            if (p.getAdmissionDate() != null && !p.getAdmissionDate().isEmpty()) {
                ps.setDate(4, java.sql.Date.valueOf(p.getAdmissionDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, p.getAilment());
            ps.setString(6, p.getAssignedDoctor());
            ps.setInt(7, p.getPatientID());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (IllegalArgumentException iae) { // Catch if date parsing fails
             System.err.println("IllegalArgumentException in updatePatient (invalid date format?): " + iae.getMessage());
             iae.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQLException in updatePatient: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}