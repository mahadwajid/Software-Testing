package org.example;

import java.util.List;

public class FakeRepositoryForTest implements ILabTestRepository {
    public static List<LabTest> labTestsList;

    @Override
    public List<LabTest> findAllLabTests() {
        return labTestsList;
    }
}
