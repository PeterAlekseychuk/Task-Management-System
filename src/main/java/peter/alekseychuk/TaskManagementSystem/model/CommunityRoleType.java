package peter.alekseychuk.TaskManagementSystem.model;

import lombok.Getter;
import org.springframework.stereotype.Component;

public enum CommunityRoleType {
    AUTHOR, EXECUTOR;

    @Component("CommunityRole")
    @Getter
    static class SpringComponent {
        private final CommunityRoleType ADMIN = CommunityRoleType.AUTHOR;
        private final CommunityRoleType MODERATOR = CommunityRoleType.EXECUTOR;
    }
}
