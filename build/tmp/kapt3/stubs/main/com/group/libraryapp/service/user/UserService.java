package com.group.libraryapp.service.user;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0017J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fH\u0016J\u0010\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0017J\b\u0010\u0013\u001a\u00020\bH\u0017J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0015H\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/group/libraryapp/service/user/UserService;", "", "userRepository", "Lcom/group/libraryapp/domain/user/UserRepository;", "bookRepository", "Lcom/group/libraryapp/domain/book/BookRepository;", "(Lcom/group/libraryapp/domain/user/UserRepository;Lcom/group/libraryapp/domain/book/BookRepository;)V", "deleteUser", "", "name", "", "getUserLoanHistories", "", "Lcom/group/libraryapp/dto/user/response/UserLoanHistoryResponse;", "getUsers", "Lcom/group/libraryapp/dto/user/response/UserResponse;", "saveUser", "request", "Lcom/group/libraryapp/dto/user/request/UserCreateRequest;", "saveUserAndLoanTwoBooks", "updateUserName", "Lcom/group/libraryapp/dto/user/request/UserUpdateRequest;", "library-app"})
@org.springframework.transaction.annotation.Transactional(readOnly = true)
@org.springframework.stereotype.Service()
public class UserService {
    private final com.group.libraryapp.domain.user.UserRepository userRepository = null;
    private final com.group.libraryapp.domain.book.BookRepository bookRepository = null;
    
    public UserService(@org.jetbrains.annotations.NotNull()
    com.group.libraryapp.domain.user.UserRepository userRepository, @org.jetbrains.annotations.NotNull()
    com.group.libraryapp.domain.book.BookRepository bookRepository) {
        super();
    }
    
    @org.springframework.transaction.annotation.Transactional()
    public void saveUserAndLoanTwoBooks() {
    }
    
    @org.springframework.transaction.annotation.Transactional()
    public void saveUser(@org.jetbrains.annotations.NotNull()
    com.group.libraryapp.dto.user.request.UserCreateRequest request) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.group.libraryapp.dto.user.response.UserResponse> getUsers() {
        return null;
    }
    
    @org.springframework.transaction.annotation.Transactional()
    public void updateUserName(@org.jetbrains.annotations.NotNull()
    com.group.libraryapp.dto.user.request.UserUpdateRequest request) {
    }
    
    @org.springframework.transaction.annotation.Transactional()
    public void deleteUser(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.group.libraryapp.dto.user.response.UserLoanHistoryResponse> getUserLoanHistories() {
        return null;
    }
}