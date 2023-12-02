package org.kirillandrey.advice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindAdviceTest {
    private WindAdvice windAdvice = new WindAdvice();

    @Test
    void testWindAdviceLessThan5() {
        assertEquals("", windAdvice.winAdv(4));
    }
    @Test
    void testWindAdviceExactly5() {
        assertEquals("- Cегодня сильный ветер, будьте аккуратнее.", windAdvice.winAdv(5));
    }

    @Test
    void testWindAdviceBetween5And12() {
        assertEquals("- Cегодня сильный ветер, будьте аккуратнее.", windAdvice.winAdv(8));
    }

    @Test
    void testWindAdviceExactly12() {
        assertEquals("- Cегодня сильный ветер, будьте аккуратнее.", windAdvice.winAdv(12));
    }

    @Test
    void testWindAdviceBetween12And24() {
        assertEquals("- Сегодня очень сильный ветер, не берите с собой вещи которые будет тяжело удержать.", windAdvice.winAdv(20));
    }

    @Test
    void testWindAdviceExactly24() {
        assertEquals("- Сегодня очень сильный ветер, не берите с собой вещи которые будет тяжело удержать.", windAdvice.winAdv(24));
    }

    @Test
    void testWindAdviceBetween24And36() {
        assertEquals("- Сегодня штормовой ветер, не советуем выходить из дома.", windAdvice.winAdv(30));
    }

    @Test
    void testWindAdviceExactly36() {
        assertEquals("- Сегодня штормовой ветер, не советуем выходить из дома.", windAdvice.winAdv(36));
    }

    @Test
    void testWindAdviceGreaterThan36() {
        assertEquals("- Сегодня штормовой ветер, не советуем выходить из дома.", windAdvice.winAdv(40));
    }

    @Test
    void testWindAdviceWithNull() {
        assertEquals("", windAdvice.winAdv(null));
    }
}
