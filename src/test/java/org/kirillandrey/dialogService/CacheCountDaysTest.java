package org.kirillandrey.dialogService;

import org.junit.jupiter.api.Test;
import org.kirillandrey.dialogsService.controller.CacheCountDays;

import static org.junit.jupiter.api.Assertions.*;

class CacheCountDaysTest {

    @Test
    void testGetDays() {
        CacheCountDays.addDays(1L, 3);

        Integer result = CacheCountDays.getDays(1L);

        assertEquals(3, result);
        assertNull(CacheCountDays.getDays(2L));
    }

    @Test
    void testAddDays() {
        CacheCountDays.addDays(1L, 3);

        Integer result = CacheCountDays.getDays(1L);

        assertEquals(3, result);

        CacheCountDays.addDays(2L, 6);

        result = CacheCountDays.getDays(2L);

        assertEquals(5, result);

        CacheCountDays.addDays(null, 3);

        assertNull(CacheCountDays.getDays(null));
    }
}
