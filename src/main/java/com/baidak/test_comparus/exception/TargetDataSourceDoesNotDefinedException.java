package com.baidak.test_comparus.exception;

public class TargetDataSourceDoesNotDefinedException extends RuntimeException {

    private static final String MESSAGE =
            "TargetDataSource with specified name does not present in DataSourceDefinitions. Target DataSource name: ";

    public TargetDataSourceDoesNotDefinedException(String targetDatasourceName) {
        super(MESSAGE + targetDatasourceName);
    }
}
