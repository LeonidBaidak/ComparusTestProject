package com.baidak.test_comparus.configuration.datasource;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class TargetDataSourceContextHolder {

    private final ThreadLocal<DataSourceContext> dataSourceContext = new ThreadLocal<>();

    public void setDataSourceContext(DataSourceContext practiceContext) {
        this.dataSourceContext.set(practiceContext);
    }

    public DataSourceContext getDataSourceContext() {
        return dataSourceContext.get();
    }

    public void remove() {
        dataSourceContext.remove();
    }

    @Data
    public static class DataSourceContext {

        private final String name;
    }
}
