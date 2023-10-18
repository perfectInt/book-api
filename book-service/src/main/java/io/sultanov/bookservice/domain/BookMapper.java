package io.sultanov.bookservice.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "author", column = "author"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description")
    })
    @Select("""
            INSERT INTO book (author, title, description)
            VALUES (#{author}, #{title}, #{description}) RETURNING *;
            """)
    Book insertBook(BookDto bookDto) throws RuntimeException;

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "author", column = "author"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description")
    })
    @Select("""
            SELECT * FROM book;
            """)
    List<Book> getAllBooks();

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "author", column = "author"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description")
    })
    @Select("""
            SELECT *
            FROM book
            WHERE book.id = #{id};
            """)
    Book getBookById(Integer id);
}
