package com.pk;

import com.pk.client.api.PersonApi;
import com.pk.client.invoker.ApiClient;
import com.pk.client.model.Person;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiQuickstartApplicationTests {

    private static final String SERVICE_ROOT = "http://localhost:%d";

    @LocalServerPort private int localPort;

	@Test
    @SneakyThrows
	public void swaggerClient() {
        ApiClient client = new ApiClient();
        client.setBasePath(String.format(SERVICE_ROOT, localPort));
        PersonApi api = new PersonApi(client);
        Person actual = api.getUsingGET("MotherOf", "Dragons");
        assertThat(actual, is(new Person().firstName("MotherOf").lastName("Dragons")));
    }

}
