package com.proaut.claudeshim;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainMaskCredentialsTest {

    @Test
    void masksPasswordInHttpsProxy() {
        String result = Main.maskCredentials("http://user:secret@proxy.example:8080");
        assertEquals("http://*****@proxy.example:8080", result);
    }

    @Test
    void masksPasswordInHttpProxy() {
        String result = Main.maskCredentials("http://admin:p4ssw0rd@proxy.local:3128");
        assertEquals("http://*****@proxy.local:3128", result);
    }

    @Test
    void masksNoProxy() {
        String result = Main.maskCredentials("http://user:password@proxy:8080");
        assertEquals("http://*****@proxy:8080", result);
    }

    @Test
    void returnsUrlUnchangedWhenNoCredentials() {
        String result = Main.maskCredentials("http://proxy.example:8080");
        assertEquals("http://proxy.example:8080", result);
    }

    @Test
    void returnsUrlUnchangedWhenUserButNoPassword() {
        String result = Main.maskCredentials("http://admin@proxy.example:8080");
        assertEquals("http://*****@proxy.example:8080", result);
    }

    @Test
    void returnsNullForNullInput() {
        assertNull(Main.maskCredentials(null));
    }

    @Test
    void returnsUrlWithoutScheme() {
        String result = Main.maskCredentials("proxy.example:8080");
        assertEquals("proxy.example:8080", result);
    }

    @Test
    void handlesSpecialCharactersInPassword() {
        String result = Main.maskCredentials("http://user:pass:word@proxy:8080");
        assertEquals("http://*****@proxy:8080", result);
    }

    @Test
    void handlesUrlWithAtSymbolInPassword() {
        String result = Main.maskCredentials("http://user:p@ss@proxy:8080");
        assertEquals("http://*****@proxy:8080", result);
    }

    @Test
    void handlesEmptyUrl() {
        String result = Main.maskCredentials("");
        assertEquals("", result);
    }
}
