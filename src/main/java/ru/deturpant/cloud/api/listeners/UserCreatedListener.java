package ru.deturpant.cloud.api.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.deturpant.cloud.store.entities.FolderEntity;
import ru.deturpant.cloud.store.entities.UserEntity;
import ru.deturpant.cloud.store.repositories.FolderRepository;

import java.time.Instant;

@Component
public class UserCreatedListener implements ApplicationListener<UserCreatedEvent> {
    private final FolderRepository folderRepository;
    public UserCreatedListener(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }
    @Override
    public void onApplicationEvent(UserCreatedEvent event) {
        UserEntity user = event.getUser();
        if (user.getRootFolder() == null) {
            FolderEntity userRootFolder = FolderEntity.builder()
                    .name("root")
                    .owner(user)
                    .createdAt(Instant.now())
                    .modifiedAt(Instant.now())
                    .build();
            user.setRootFolder(userRootFolder);
            folderRepository.save(userRootFolder);
        }
    }
}
