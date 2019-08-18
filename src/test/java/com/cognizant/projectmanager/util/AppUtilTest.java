package com.cognizant.projectmanager.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.cognizant.projectmanager.util.AppUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by hp on 18-08-2019.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppUtilTest {

    @Mock
    private ResultSet resultSet;
    private String COLUMN = "dummy";
    @InjectMocks
    private AppUtil appUtil;

    @Test
    public void testGetLocalDate() throws Exception {
        final LocalDate localDate = LocalDate.now();
        assertEquals(localDate, getLocalDate(Date.valueOf(localDate)));
        assertNull(getLocalDate(null));
    }

    @Test
    public void testGetLocalDateTime() throws Exception {
        final LocalDateTime localDateTime = LocalDateTime.now();
        assertEquals(localDateTime, getLocalDateTime(Timestamp.valueOf(localDateTime)));
        assertNull(getLocalDateTime(null));
    }

    @Test
    public void testGetSqlDate() throws Exception {
        final LocalDate now = LocalDate.now();
        final Date sqlDate = Date.valueOf(now);
        assertEquals(sqlDate, getSqlDate(now));
        assertNull(getSqlDate(null));
    }

    @Test
    public void testGetBoolean() throws Exception {
        assertTrue(getBoolean("Y"));
        assertTrue(getBoolean("y"));
        assertFalse(getBoolean("n"));
        assertFalse(getBoolean("N"));

        assertFalse(getBoolean(null));
        assertFalse(getBoolean("xyz"));
        assertFalse(getBoolean("    "));
    }

    @Test
    public void testGetStrFromBoolean() throws Exception {
        assertEquals("Y", getStrFromBoolean(true));
        assertEquals("N", getStrFromBoolean(false));
    }

    @Test
    public void testGetOptionalValueWhenValueIsNotNull() throws Exception {
        final Integer expected = 2;
        when(resultSet.wasNull()).thenReturn(false);
        when(resultSet.getObject(COLUMN, Integer.class)).thenReturn(expected);
        assertEquals(expected, getOptionalValue(resultSet, COLUMN, Integer.class));
    }

    @Test
    public void testGetOptionalValueWhenValueIsNull() throws Exception {
        when(resultSet.wasNull()).thenReturn(true);
        when(resultSet.getObject(COLUMN, Integer.class)).thenReturn(null);
        assertNull(getOptionalValue(resultSet, COLUMN, Integer.class));
    }

}