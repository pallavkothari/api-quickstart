package com.pk;

import com.pk.model.Person;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * A resting place
 * Created by pallav.kothari on 4/2/17.
 */
@RestController
@RequestMapping("/api/v1/person")
@Api(tags = "person")
@Slf4j
public class RestEndpoints {

    @RequestMapping(value = "/{firstName}/{lastName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person get(@PathVariable("firstName") String firstName,
                      @PathVariable("lastName") String lastName) {
        log.info("you asksed for {} {}", firstName, lastName);
        return new Person(firstName, lastName);
    }
}
