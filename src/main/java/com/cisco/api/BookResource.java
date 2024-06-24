package com.cisco.api;

import com.cisco.model.Book;
import com.cisco.repo.BookRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    BookRepository bookRepository;

    @GET
    public List<Book> getBooks() {
        return bookRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Book getBookById(@PathParam("id") long id) {
        return bookRepository.findById(id);
    }

    @POST
    public Response createBook(Book book) {
        bookRepository.persist(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") long id, Book book) {
        Book existingBook = bookRepository.findById(id);
        if (existingBook == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        bookRepository.persist(existingBook);
        return Response.status(Response.Status.OK).entity(existingBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") long id) {
        Book existingBook = bookRepository.findById(id);
        if (existingBook == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        bookRepository.delete(existingBook);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
