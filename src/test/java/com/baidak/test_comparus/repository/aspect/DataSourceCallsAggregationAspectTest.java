package com.baidak.test_comparus.repository.aspect;

import com.baidak.test_comparus.configuration.datasource.MultiTenantDatasourceProperties;
import com.baidak.test_comparus.configuration.datasource.MultiTenantDatasourceProperties.DataSourceDefinition;
import com.baidak.test_comparus.configuration.datasource.TargetDataSourceContextHolder;
import com.baidak.test_comparus.configuration.datasource.TargetDataSourceContextHolder.DataSourceContext;
import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.exception.MultithreadingTaskFailedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class DataSourceCallsAggregationAspectTest {

    private final MultiTenantDatasourceProperties properties = mock(MultiTenantDatasourceProperties.class);
    private final TargetDataSourceContextHolder contextHolder = mock(TargetDataSourceContextHolder.class);
    private final ProceedingJoinPoint proceedingJoinPoint = mock(ProceedingJoinPoint.class);
    private final DataSourceDefinition firstDefinition = mock(DataSourceDefinition.class);
    private final DataSourceDefinition secondDefinition = mock(DataSourceDefinition.class);
    private final DataSourceCallsAggregationAspect aspect = new DataSourceCallsAggregationAspect(properties,
            contextHolder);

    @Test
    void aggregateCallsTest() throws Throwable {

        String firstDatabaseName = "testDB1";
        String secondDatabaseName = "testDB2";
        DataSourceContext context1 = new DataSourceContext(firstDatabaseName);
        DataSourceContext context2 = new DataSourceContext(secondDatabaseName);
        List<DataSourceDefinition> definitions = List.of(firstDefinition, secondDefinition);
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID userId3 = UUID.randomUUID();
        List<User> firstUserList = List.of(User.builder()
                .id(userId1)
                .username("username1")
                .firstName("firstname1")
                .surname("surname1")
                .build());
        List<User> secondUserList = List.of(User.builder()
                        .id(userId2)
                        .username("username2")
                        .firstName("firstname2")
                        .surname("surname2")
                        .build(),
                User.builder()
                        .id(userId3)
                        .username("username3")
                        .firstName("firstname3")
                        .surname("surname3")
                        .build()
        );

        List<User> expectedResult = new ArrayList<>();
        expectedResult.addAll(firstUserList);
        expectedResult.addAll(secondUserList);

        when(properties.getDataSourceDefinitions()).thenReturn(definitions);
        when(firstDefinition.getName()).thenReturn(firstDatabaseName);
        when(secondDefinition.getName()).thenReturn(secondDatabaseName);
        when(proceedingJoinPoint.proceed()).thenReturn(firstUserList, secondUserList);

        List<User> actualResult = aspect.aggregateCalls(proceedingJoinPoint);

        assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);
        verify(properties, times(1)).getDataSourceDefinitions();
        verify(firstDefinition, times(1)).getName();
        verify(secondDefinition, times(1)).getName();
        verify(proceedingJoinPoint, times(2)).proceed();
        verify(contextHolder, times(1)).setDataSourceContext(context1);
        verify(contextHolder, times(1)).setDataSourceContext(context2);
        verify(contextHolder, times(2)).remove();
        verifyNoMoreInteractions(properties, contextHolder, proceedingJoinPoint, firstDefinition, secondDefinition);
    }

    @Test
    void aggregateCallsMultithreadingTaskFailedExceptionTest() throws Throwable {

        String firstDatabaseName = "testDB1";
        String secondDatabaseName = "testDB2";
        DataSourceContext context1 = new DataSourceContext(firstDatabaseName);
        DataSourceContext context2 = new DataSourceContext(secondDatabaseName);
        List<DataSourceDefinition> definitions = List.of(firstDefinition, secondDefinition);
        UUID userId1 = UUID.randomUUID();
        List<User> firstUserList = List.of(User.builder()
                .id(userId1)
                .username("username1")
                .firstName("firstname1")
                .surname("surname1")
                .build());

        when(properties.getDataSourceDefinitions()).thenReturn(definitions);
        when(firstDefinition.getName()).thenReturn(firstDatabaseName);
        when(secondDefinition.getName()).thenReturn(secondDatabaseName);
        when(proceedingJoinPoint.proceed()).thenReturn(firstUserList).thenThrow(new RuntimeException());

        assertThrows(MultithreadingTaskFailedException.class, () -> aspect.aggregateCalls(proceedingJoinPoint));

        verify(properties, times(1)).getDataSourceDefinitions();
        verify(firstDefinition, times(1)).getName();
        verify(secondDefinition, times(1)).getName();
        verify(proceedingJoinPoint, times(2)).proceed();
        verify(contextHolder, times(1)).setDataSourceContext(context1);
        verify(contextHolder, times(1)).setDataSourceContext(context2);
        verify(contextHolder, times(2)).remove();
        verifyNoMoreInteractions(properties, contextHolder, proceedingJoinPoint, firstDefinition, secondDefinition);
    }

    @Test
    void aggregateCallsInterruptedExceptionTest() throws Throwable {
        ExecutorService executorService = mock(ExecutorService.class);

        String firstDatabaseName = "testDB1";
        String secondDatabaseName = "testDB2";
        int dataSourceNamesNumber = 2;
        List<DataSourceDefinition> definitions = List.of(firstDefinition, secondDefinition);

        when(properties.getDataSourceDefinitions()).thenReturn(definitions);
        when(firstDefinition.getName()).thenReturn(firstDatabaseName);
        when(secondDefinition.getName()).thenReturn(secondDatabaseName);
        when(executorService.invokeAll(any())).thenThrow(InterruptedException.class);

        try (MockedStatic<Executors> mockedExecutors = Mockito.mockStatic(Executors.class)) {
            mockedExecutors.when(() -> Executors.newFixedThreadPool(dataSourceNamesNumber)).thenReturn(executorService);
            assertThrows(MultithreadingTaskFailedException.class, () -> aspect.aggregateCalls(proceedingJoinPoint));
            mockedExecutors.verify(() -> Executors.newFixedThreadPool(dataSourceNamesNumber));
            mockedExecutors.verifyNoMoreInteractions();
        }

        verify(properties, times(1)).getDataSourceDefinitions();
        verify(firstDefinition, times(1)).getName();
        verify(secondDefinition, times(1)).getName();
        verify(executorService, times(1)).invokeAll(any());
        verifyNoMoreInteractions(properties, contextHolder, proceedingJoinPoint, firstDefinition, secondDefinition,
                executorService);
    }

    @Test
    void aggregateCallsExecutionExceptionTest() throws Throwable {
        ExecutorService executorService = mock(ExecutorService.class);

        String firstDatabaseName = "testDB1";
        String secondDatabaseName = "testDB2";
        int dataSourceNamesNumber = 2;
        List<DataSourceDefinition> definitions = List.of(firstDefinition, secondDefinition);

        when(properties.getDataSourceDefinitions()).thenReturn(definitions);
        when(firstDefinition.getName()).thenReturn(firstDatabaseName);
        when(secondDefinition.getName()).thenReturn(secondDatabaseName);
        given(executorService.invokeAll(any()))
                .willAnswer(invocation -> {
                    throw new ExecutionException(new RuntimeException());
                });
        try (MockedStatic<Executors> mockedExecutors = Mockito.mockStatic(Executors.class)) {
            mockedExecutors.when(() -> Executors.newFixedThreadPool(dataSourceNamesNumber)).thenReturn(executorService);
            assertThrows(MultithreadingTaskFailedException.class, () -> aspect.aggregateCalls(proceedingJoinPoint));
            mockedExecutors.verify(() -> Executors.newFixedThreadPool(dataSourceNamesNumber));
            mockedExecutors.verifyNoMoreInteractions();
        }

        verify(properties, times(1)).getDataSourceDefinitions();
        verify(firstDefinition, times(1)).getName();
        verify(secondDefinition, times(1)).getName();
        verify(executorService, times(1)).invokeAll(any());
        verifyNoMoreInteractions(properties, contextHolder, proceedingJoinPoint, firstDefinition, secondDefinition,
                executorService);
    }
}
