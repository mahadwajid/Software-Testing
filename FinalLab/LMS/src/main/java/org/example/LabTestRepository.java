package org.example;

import java.util.Arrays;
import java.util.List;

public class LabTestRepository implements ILabTestRepository {

    @Override
    public List<LabTest> findAllLabTests() {
        return Arrays.asList(
                new LabTest("1", "Complete Blood Count", "Hematology"),
                new LabTest("2", "Lipid Panel", "Biochemistry"),
                new LabTest("3", "Liver Function Test", "Biochemistry"),
                new LabTest("4", "Urinalysis", "Microbiology")
        );
    }
}
