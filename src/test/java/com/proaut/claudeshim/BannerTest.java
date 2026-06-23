package com.proaut.claudeshim;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BannerTest {

    @Test
    void hexColorProducesTrueColorSequence() {
        assertEquals("\033[38;2;226;0;116m", Banner.ansiCode("#e20074"));
    }

    @Test
    void hexColorCaseInsensitive() {
        assertEquals(Banner.ansiCode("#e20074"), Banner.ansiCode("#E20074"));
    }

    @Test
    void namedColorFallsThrough() {
        assertEquals("\033[35m", Banner.ansiCode("magenta"));
        assertEquals("\033[36m", Banner.ansiCode("cyan"));
    }

    @Test
    void nullDefaultsCyan() {
        assertEquals("\033[36m", Banner.ansiCode(null));
    }

    @Test
    void unknownNameDefaultsCyan() {
        assertEquals("\033[36m", Banner.ansiCode("chartreuse"));
    }

    @Test
    void invalidHexDefaultsCyan() {
        assertEquals("\033[36m", Banner.ansiCode("#zzzzzz"));
    }
}
