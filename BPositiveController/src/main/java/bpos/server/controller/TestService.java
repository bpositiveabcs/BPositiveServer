package bpos.server.controller;

import bpos.server.repository.Implementations.DBBloodTestRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final DBBloodTestRepository bloodTestRepository;

    @Autowired
    public TestService(DBBloodTestRepository bloodTestRepository) {
        this.bloodTestRepository = bloodTestRepository;
    }

    @PostConstruct
    public void testRepository() {
        bloodTestRepository.testMethod();
    }
}