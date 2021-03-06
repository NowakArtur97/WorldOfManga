package com.NowakArtur97.WorldOfManga.feature.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    public boolean isAuthorAlreadyInDatabase(String fullName) {

        return authorRepository.existsAuthorByFullName(fullName);
    }

    public List<Author> findAll() {

        return authorRepository.findAll();
    }

    Author addOrUpdate(AuthorDTO authorDTO) {

        Author author = authorMapper.mapAuthorDTOToAuthor(authorDTO);

        return save(author);
    }

    Author save(Author author) {

        return authorRepository.save(author);
    }
}
