package org.example;

public class LabTest {
    private String id;
    private String testName;
    private String testType;

    public LabTest(String id, String testName, String testType) {
        this.id = id;
        this.testName = testName;
        this.testType = testType;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }

    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }
}
