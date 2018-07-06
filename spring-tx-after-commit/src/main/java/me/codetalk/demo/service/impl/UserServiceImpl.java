package me.codetalk.demo.service.impl;

import me.codetalk.demo.mapper.UserMapper;
import me.codetalk.demo.model.User;
import me.codetalk.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Created by guobiao.xu on 2018/7/6.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void createUser(User user) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                LOGGER.info("Thread = {}, User created with name = {}, age = {}, id = {}", Thread.currentThread(),
                        user.getName(), user.getAge(), user.getId());
            }
        });

        userMapper.insertUser(user);
        LOGGER.info("User {} inserted into mysql, not yet committed.", user.getName());
    }

}
