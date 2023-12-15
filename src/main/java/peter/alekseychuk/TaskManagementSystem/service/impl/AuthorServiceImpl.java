package peter.alekseychuk.TaskManagementSystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peter.alekseychuk.TaskManagementSystem.model.Author;
import peter.alekseychuk.TaskManagementSystem.model.CommunityRoleType;
import peter.alekseychuk.TaskManagementSystem.repository.AuthorRepository;
import peter.alekseychuk.TaskManagementSystem.service.AuthorService;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public void registerAuthor(Author author) {
        author.setRoleType(CommunityRoleType.AUTHOR);
        authorRepository.save(author);
    }


    @Transactional
    public Optional<Author> findByLastname(String lastName) {
        return authorRepository.findByLastname(lastName);
    }
    @Transactional
    public Optional<Author> findAuthorById(UUID id) {
       return authorRepository.findById(id);
    }
}
