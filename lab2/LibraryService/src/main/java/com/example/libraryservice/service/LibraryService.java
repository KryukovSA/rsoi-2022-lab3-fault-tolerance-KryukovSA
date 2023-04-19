package com.example.libraryservice.service;

import com.example.libraryservice.model.Books;
import com.example.libraryservice.model.Library;
import com.example.libraryservice.model.Library_books;
import com.example.libraryservice.repository.BooksRepository;
import com.example.libraryservice.repository.LibraryBooksRepository;
import com.example.libraryservice.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final BooksRepository booksRepository;
    private final LibraryBooksRepository library_booksRepository;



    public List<Library> getCityLibraries(String city) {
        List<Library> libraries = libraryRepository.findAllByCity(city);
        return libraries;
    }
//UUID.fromString(String.valueOf(libraryUid))
    public List<Books> getLibraryBooks(UUID libraryUUID, Boolean showAll) {
        Library library = libraryRepository.findByLibraryUid(libraryUUID);
        List<Library_books> library_books = library_booksRepository.findAllByLibraryId(library.getId());
        List<Books> books = new ArrayList<Books>();
        for (Library_books i: library_books) {
            int id = i.getBookId();
            books.add(booksRepository.findById(id));
        }
        return books;
    }


    public  Library getLibraryInfo(UUID uuid) {
        return libraryRepository.findByLibraryUid(uuid);
    }


    public Books getBookInfo(UUID libraryUuid, UUID bookUuid) {
        Library library = libraryRepository.findByLibraryUid(libraryUuid);
        Books books = booksRepository.findByBookUid(bookUuid);

        List<Books> listBooks = new ArrayList<Books>();
        List<Library_books> library_books = library_booksRepository.findAllByLibraryId(library.getId());
        for (Library_books i: library_books) {
            int id = i.getBookId();
            listBooks.add(booksRepository.findById(id));
        }

        if(listBooks.contains(books)) {
            return books;
        }
        return null;
    }


}
