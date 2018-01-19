package org.jboss.unimbus.example;

import java.net.URL;

import javax.inject.Inject;

import io.restassured.RestAssured;
import io.restassured.authentication.BasicAuthScheme;
import org.jboss.unimbus.security.basic.BasicSecurity;
import org.jboss.unimbus.servlet.Management;
import org.jboss.unimbus.servlet.Primary;
import org.jboss.unimbus.test.UNimbusTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(UNimbusTestRunner.class)
public class ProofTest {

    @Before
    public void setup() {
        RestAssured.baseURI = this.url.toExternalForm();
        security.addUser("bob", "password");
    }

    @After
    public void teardown() {
        RestAssured.reset();
    }

    @Test
    public void test() {
        BasicAuthScheme auth = new BasicAuthScheme();
        auth.setUserName("bob");
        auth.setPassword("password");
        RestAssured.authentication = auth;

        when().get("/health").then()
                .statusCode(200)
                .body(
                        containsString("{\"name\":\"undertow-management\",\"state\":\"UP\"}")
                );
    }

    @Inject
    @Management
    URL url;

    @Inject
    BasicSecurity security;
}
