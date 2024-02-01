package com.example.snippet2;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Snippet {
    private String id;
    private String language;
    private String code;
}
