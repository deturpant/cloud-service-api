package ru.deturpant.cloud.api.listeners;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import ru.deturpant.cloud.store.entities.UserEntity;

@Getter
@Setter
public class UserCreatedEvent extends ApplicationEvent {
    private final UserEntity user;
    public UserCreatedEvent(Object source, UserEntity user) {
        super(source);
        this.user = user;
    }

}
