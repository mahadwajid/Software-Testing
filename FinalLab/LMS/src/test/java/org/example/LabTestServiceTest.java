package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class LabTestServiceTest {

    private LabTestService labTestService;
    private ILabTestRepository labTestRepository;

    @BeforeEach
    public void setUp() {
        labTestRepository = new FakeRepositoryForTest();
        labTestService = new LabTestService(labTestRepository);
    }

    // Test cases for getLabTestsByType

    @Test
    public void testGetLabTestsByType_Found() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Complete Blood Count", "Hematology"),
                new LabTest("2", "Lipid Panel", "Biochemistry")
        );
        List<LabTest> labTests = labTestService.getLabTestsByType("Hematology");
        Assertions.assertEquals(1, labTests.size());
        Assertions.assertEquals("Complete Blood Count", labTests.get(0).getTestName());
    }

    @Test
    public void testGetLabTestsByType_NoTestsFound() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Lipid Panel", "Biochemistry")
        );
        List<LabTest> labTests = labTestService.getLabTestsByType("Unknown Type");
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByType_MultipleTests() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Complete Blood Count", "Hematology"),
                new LabTest("2", "Blood Sugar", "Hematology")
        );
        List<LabTest> labTests = labTestService.getLabTestsByType("Hematology");
        Assertions.assertEquals(2, labTests.size());
    }

    @Test
    public void testGetLabTestsByType_CaseInsensitive() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Complete Blood Count", "Hematology")
        );
        List<LabTest> labTests = labTestService.getLabTestsByType("hematology");
        Assertions.assertEquals(1, labTests.size());
        Assertions.assertEquals("Complete Blood Count", labTests.get(0).getTestName());
    }

    @Test
    public void testGetLabTestsByType_NullType() {
        List<LabTest> labTests = labTestService.getLabTestsByType(null);
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByType_EmptyType() {
        List<LabTest> labTests = labTestService.getLabTestsByType("");
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByType_BlankType() {
        List<LabTest> labTests = labTestService.getLabTestsByType("   ");
        Assertions.assertEquals(0, labTests.size());
    }

    // Test cases for getLabTestsByName

    @Test
    public void testGetLabTestsByName_Found() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Complete Blood Count", "Hematology"),
                new LabTest("2", "Lipid Panel", "Biochemistry")
        );
        List<LabTest> labTests = labTestService.getLabTestsByName("Lipid Panel");
        Assertions.assertEquals(1, labTests.size());
        Assertions.assertEquals("Biochemistry", labTests.get(0).getTestType());
    }

    @Test
    public void testGetLabTestsByName_NoTestsFound() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Lipid Panel", "Biochemistry")
        );
        List<LabTest> labTests = labTestService.getLabTestsByName("Unknown Name");
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByName_MultipleTests() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Complete Blood Count", "Hematology"),
                new LabTest("2", "Complete Blood Count", "Biochemistry")
        );
        List<LabTest> labTests = labTestService.getLabTestsByName("Complete Blood Count");
        Assertions.assertEquals(2, labTests.size());
    }

    @Test
    public void testGetLabTestsByName_CaseInsensitive() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Complete Blood Count", "Hematology")
        );
        List<LabTest> labTests = labTestService.getLabTestsByName("complete blood count");
        Assertions.assertEquals(1, labTests.size());
        Assertions.assertEquals("Hematology", labTests.get(0).getTestType());
    }

    @Test
    public void testGetLabTestsByName_NullName() {
        List<LabTest> labTests = labTestService.getLabTestsByName(null);
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByName_EmptyName() {
        List<LabTest> labTests = labTestService.getLabTestsByName("");
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByName_BlankName() {
        List<LabTest> labTests = labTestService.getLabTestsByName("   ");
        Assertions.assertEquals(0, labTests.size());
    }

    // Test cases for getLabTestsByTypeAndName

    @Test
    public void testGetLabTestsByTypeAndName_Found() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Complete Blood Count", "Hematology"),
                new LabTest("2", "Lipid Panel", "Biochemistry")
        );
        List<LabTest> labTests = labTestService.getLabTestsByTypeAndName("Hematology", "Complete Blood Count");
        Assertions.assertEquals(1, labTests.size());
        Assertions.assertEquals("Complete Blood Count", labTests.get(0).getTestName());
    }

    @Test
    public void testGetLabTestsByTypeAndName_NoTestsFound() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Lipid Panel", "Biochemistry")
        );
        List<LabTest> labTests = labTestService.getLabTestsByTypeAndName("Unknown Type", "Unknown Name");
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByTypeAndName_MultipleTests() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Complete Blood Count", "Hematology"),
                new LabTest("2", "Complete Blood Count", "Hematology")
        );
        List<LabTest> labTests = labTestService.getLabTestsByTypeAndName("Hematology", "Complete Blood Count");
        Assertions.assertEquals(2, labTests.size());
    }

    @Test
    public void testGetLabTestsByTypeAndName_CaseInsensitive() {
        FakeRepositoryForTest.labTestsList = Arrays.asList(
                new LabTest("1", "Complete Blood Count", "Hematology")
        );
        List<LabTest> labTests = labTestService.getLabTestsByTypeAndName("hematology", "complete blood count");
        Assertions.assertEquals(1, labTests.size());
        Assertions.assertEquals("Complete Blood Count", labTests.get(0).getTestName());
    }

    @Test
    public void testGetLabTestsByTypeAndName_NullType() {
        List<LabTest> labTests = labTestService.getLabTestsByTypeAndName(null, "Complete Blood Count");
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByTypeAndName_EmptyType() {
        List<LabTest> labTests = labTestService.getLabTestsByTypeAndName("", "Complete Blood Count");
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByTypeAndName_BlankType() {
        List<LabTest> labTests = labTestService.getLabTestsByTypeAndName("   ", "Complete Blood Count");
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByTypeAndName_NullName() {
        List<LabTest> labTests = labTestService.getLabTestsByTypeAndName("Hematology", null);
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByTypeAndName_EmptyName() {
        List<LabTest> labTests = labTestService.getLabTestsByTypeAndName("Hematology", "");
        Assertions.assertEquals(0, labTests.size());
    }

    @Test
    public void testGetLabTestsByTypeAndName_BlankName() {
        List<LabTest> labTests = labTestService.getLabTestsByTypeAndName("Hematology", "   ");
        Assertions.assertEquals(0, labTests.size());
    }
}
