package com.baidak.test_comparus.exception;

public class NoDataSourceDefinitionsException extends RuntimeException {
    public NoDataSourceDefinitionsException() {
        super("No datasource definition available!");
    }
}
