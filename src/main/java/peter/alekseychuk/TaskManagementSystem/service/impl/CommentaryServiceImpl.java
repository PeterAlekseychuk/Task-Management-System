package peter.alekseychuk.TaskManagementSystem.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peter.alekseychuk.TaskManagementSystem.dto.CommentaryDto;
import peter.alekseychuk.TaskManagementSystem.model.Commentary;
import peter.alekseychuk.TaskManagementSystem.model.Task;
import peter.alekseychuk.TaskManagementSystem.model.User;
import peter.alekseychuk.TaskManagementSystem.repository.CommentaryRepository;
import peter.alekseychuk.TaskManagementSystem.service.CommentaryService;

import java.util.UUID;

@Service
public class CommentaryServiceImpl implements CommentaryService {

    private final UserServiceImpl userService;
    private final TaskServiceImpl taskService;
    private final CommentaryRepository commentaryRepository;
    private final ModelMapper modelMapper;

    public CommentaryServiceImpl(UserServiceImpl userService, TaskServiceImpl taskService, CommentaryRepository commentaryRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.taskService = taskService;
        this.commentaryRepository = commentaryRepository;
        this.modelMapper = modelMapper;
    }

    //метод добавления комментария к задаче
    @Override
    public Commentary addCommentary(UUID taskId, CommentaryDto commentaryDto) {
        Commentary commentary = new Commentary();
        User author = userService.getCurrentUser();
        Task task = taskService.getTaskById(taskId);
        commentary.setCommentary(commentaryDto.getCommentary());
        commentary.setTaskId(task);
        commentary.setAuthorId(author);
        commentary.setAuthorName(author.getFirstname() + " " + author.getLastname());
        commentaryRepository.save(commentary);
        return commentary;
    }
}
