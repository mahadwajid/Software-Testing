package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class LabTestService {
    private ILabTestRepository labTestRepository;

    public LabTestService(ILabTestRepository labTestRepository) {
        this.labTestRepository = labTestRepository;
    }

    public List<LabTest> getLabTestsByType(String testType) {
        try {
            if (testType == null || testType.trim().isEmpty()) {
                return List.of(); // Return empty list for invalid test type input
            }
            return labTestRepository.findAllLabTests().stream()
                    .filter(labTest -> labTest.getTestType().equalsIgnoreCase(testType))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error in getLabTestsByType: " + e.getMessage());
            return List.of(); // Return empty list in case of any error
        }
    }

    public List<LabTest> getLabTestsByName(String testName) {
        try {
            if (testName == null || testName.trim().isEmpty()) {
                return List.of(); // Return empty list for invalid test name input
            }
            return labTestRepository.findAllLabTests().stream()
                    .filter(labTest -> labTest.getTestName().equalsIgnoreCase(testName))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error in getLabTestsByName: " + e.getMessage());
            return List.of(); // Return empty list in case of any error
        }
    }

    public List<LabTest> getLabTestsByTypeAndName(String testType, String testName) {
        try {
            if (testType == null || testType.trim().isEmpty()) {
                return List.of(); // Return empty list for invalid test type input
            }
            if (testName == null || testName.trim().isEmpty()) {
                return List.of(); // Return empty list for invalid test name input
            }
            return labTestRepository.findAllLabTests().stream()
                    .filter(labTest -> labTest.getTestType().equalsIgnoreCase(testType) && labTest.getTestName().equalsIgnoreCase(testName))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error in getLabTestsByTypeAndName: " + e.getMessage());
            return List.of(); // Return empty list in case of any error
        }
    }
}
