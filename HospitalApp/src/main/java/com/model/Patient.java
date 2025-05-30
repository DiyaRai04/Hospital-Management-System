package com.model;

public class Patient {
	private int patientID;
    private String patientName;
    private int age;
    private String gender;
    private String admissionDate;
    private String ailment;
    private String assignedDoctor;

    // ✅ Add this no-argument constructor
    public Patient() {
    }

    // ✅ Getters and Setters
    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getAilment() {
        return ailment;
    }

    public void setAilment(String ailment) {
        this.ailment = ailment;
    }

    public String getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }
	}

