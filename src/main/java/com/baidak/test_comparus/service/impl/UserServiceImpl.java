package com.baidak.test_comparus.service.impl;

import com.baidak.test_comparus.configuration.datasource.MultiTenantDatasourceProperties;
import com.baidak.test_comparus.configuration.datasource.TargetDataSourceContextHolder;
import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.repository.UserRepository;
import com.baidak.test_comparus.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MultiTenantDatasourceProperties multiTenantDatasourceProperties;
    private final TargetDataSourceContextHolder targetDataSourceContextHolder;

    @Override
    public List<User> findAll() {
//        List<User> resultList = new ArrayList<>();
//        for (DataSourceDefinition dataSourceDefinition : multiTenantDatasourceProperties.getDataSourceDefinitions()) {
//            targetDataSourceContextHolder.setDataSourceContext(new DataSourceContext(dataSourceDefinition.getName()));
//            List<User> users = userRepository.findAll();
        return userRepository.findAll();

//            return resultList.addAll(users);
//        }
//        return resultList;
    }
}
