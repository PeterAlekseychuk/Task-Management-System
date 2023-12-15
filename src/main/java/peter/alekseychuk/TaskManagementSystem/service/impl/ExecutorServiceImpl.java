package peter.alekseychuk.TaskManagementSystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peter.alekseychuk.TaskManagementSystem.model.CommunityRoleType;
import peter.alekseychuk.TaskManagementSystem.model.Executor;
import peter.alekseychuk.TaskManagementSystem.repository.ExecutorRepository;
import peter.alekseychuk.TaskManagementSystem.service.ExecutorService;

import java.util.Optional;
import java.util.UUID;

@Service
public class ExecutorServiceImpl implements ExecutorService {

    private final ExecutorRepository repository;

    @Autowired
    public ExecutorServiceImpl(ExecutorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void registerExecutor(Executor executor) {
        executor.setRoleType(CommunityRoleType.EXECUTOR);
        repository.save(executor);
    }

    @Transactional
    public Optional<Executor> findExecutorById(UUID id) {
        return repository.findById(id);
    }



}
