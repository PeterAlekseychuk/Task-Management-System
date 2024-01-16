package peter.alekseychuk.TaskManagementSystem.service;

import peter.alekseychuk.TaskManagementSystem.dto.CommentaryDto;
import peter.alekseychuk.TaskManagementSystem.model.Commentary;

import java.util.UUID;

public interface CommentaryService {
    Commentary addCommentary(UUID taskId, CommentaryDto commentaryDto);
}
