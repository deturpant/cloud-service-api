package ru.deturpant.cloud.api.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.deturpant.cloud.api.dto.LinkDto;
import ru.deturpant.cloud.api.exceptions.NotFoundException;
import ru.deturpant.cloud.api.factories.LinkDtoFactory;
import ru.deturpant.cloud.store.entities.FileEntity;
import ru.deturpant.cloud.store.entities.LinkEntity;
import ru.deturpant.cloud.store.repositories.FileRepository;
import ru.deturpant.cloud.store.repositories.LinkRepository;

import java.util.Random;

@RestController
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LinkController {
    LinkRepository linkRepository;
    FileRepository fileRepository;
    LinkDtoFactory linkDtoFactory;
    static final String CREATE_LINK = "/api/links/files/{file_id}";

    @GetMapping(CREATE_LINK)
    public LinkDto createLink(
            @PathVariable("file_id") Long fileId
    ) {
        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("File not found"));
        String randomUrl;
        do {
            randomUrl = generateStringLink();
        } while (linkRepository.existsByUrl(randomUrl));

        LinkEntity linkEntity = LinkEntity.builder()
                .file(file)
                .url(randomUrl)
                .build();
        linkRepository.saveAndFlush(linkEntity);
        return linkDtoFactory.makeLinkDto(linkEntity);
    }

    private String generateStringLink() {
        int length = 8;
        String alphabet = "ABCDEFUHIJKLMNOPQRSTUVWXYZabcdefghijklmnoqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder link = new StringBuilder("/f/"); // Добавляем '/f/' в начале URL
        for (int i = 0; i < length; i++) {
            link.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return link.toString();
    }

}
