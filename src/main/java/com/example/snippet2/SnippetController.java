package com.example.snippet2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.List;

@RestController
@RequestMapping("/snippets")
public class SnippetController {
    @Autowired
        ResourceLoader resourceLoader;

    @GetMapping
        public ResponseEntity<Snippet[]> getSnippets() throws IOException{
            File snippetResource = resourceLoader.getResource("classpath:seedData.json").getFile();
            ObjectMapper mapper = new ObjectMapper();
            return new ResponseEntity<>(mapper.readValue(snippetResource, Snippet[].class), HttpStatus.OK);
        }


    @GetMapping("{id}")
    public ResponseEntity<Object> getSnippet(@PathVariable String id) throws IOException {
        File snippetResource = resourceLoader.getResource("classpath:seedData.json").getFile();
        ObjectMapper mapper = new ObjectMapper();
        Snippet[] snippets = mapper.readValue(snippetResource, Snippet[].class);
        Object[] snippet = Arrays.stream(snippets).filter(c -> Objects.equals(c.getId(), id)).toArray();
        if (snippet.length == 0) {
            return new ResponseEntity<>("Snippet not found :(", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(snippet[0], HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Snippet> createCupcake(@RequestBody SnippetDTO data) throws IOException {
        File snippetResource = resourceLoader.getResource("classpath:seedData.json").getFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        Snippet[] snippets = mapper.readValue(snippetResource, Snippet[].class);

        List<Snippet> snippetsList = new ArrayList<Snippet>(Arrays.asList(snippets));
        Snippet snippetToAdd = new Snippet(snippets[snippets.length - 1].getId() + 1, data.getLanguage(), data.getCode());
        snippetsList.add(snippetToAdd);

        mapper.writeValue(snippetResource, snippetsList);
        return new ResponseEntity<>(snippetToAdd, HttpStatus.OK);
    }
}
