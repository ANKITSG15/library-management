package com.lms.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookRequestDto {

    @NotNull(message = "book id cannot be null")
    @NotBlank(message = "book id cannot be blank")
    private Long bookId;

    @NotNull(message = "book title cannot be null")
    @NotBlank(message = "book title cannot be blank")
    @Size(max = 100)
    private String bookTitle;

    @NotNull(message = "ISBN number cannot be null")
    @NotBlank(message = "ISBN number cannot be blank")
    @Size(max = 20)
    private String isbnNumber;

    @NotNull(message = "author name cannot be null")
    @NotBlank(message = "author name cannot be blank")
    @Size(max = 255)
    private String authorName;

}
