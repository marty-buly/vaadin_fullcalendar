package org.vaadin.stefan.fullcalendar;

import elemental.json.JsonArray;
import elemental.json.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Disabled // not yet working
public class BusinessHoursTest {
    @Test
    void testEmptyConstructors() {
        new BusinessHours((LocalTime) null);
        new BusinessHours((DayOfWeek[]) null);
        new BusinessHours(null, (LocalTime) null);
        new BusinessHours(null, (DayOfWeek[]) null);
        new BusinessHours(null, null, (DayOfWeek[]) null);

        Assertions.assertEquals(5, BusinessHours.class.getDeclaredConstructors().length, "There are untested constructors!");
    }

    @Test
    void testEmptyReturnValues() {
        BusinessHours hours = new BusinessHours(null, null, null);

        Assertions.assertEquals(Optional.empty(), hours.getStart());
        Assertions.assertEquals(Optional.empty(), hours.getEnd());
        Assertions.assertEquals(Collections.emptySet(), hours.getDayOfWeeks());
    }

    @Test
    void testConstructors() {
        LocalTime start = LocalTime.of(5, 0);
        LocalTime end = start.plusHours(1);
        BusinessHours hours;

        hours = new BusinessHours(start);
        Assertions.assertEquals(start, hours.getStart().get());

        Set<DayOfWeek> allDaysAsSet = new HashSet<>(Arrays.asList(BusinessHours.ALL_DAYS));

        hours = new BusinessHours(BusinessHours.ALL_DAYS);
        Assertions.assertEquals(allDaysAsSet, hours.getDayOfWeeks());

        hours = new BusinessHours(start, end);
        Assertions.assertEquals(start, hours.getStart().get());
        Assertions.assertEquals(end, hours.getEnd().get());

        hours = new BusinessHours(start, BusinessHours.ALL_DAYS);
        Assertions.assertNotSame(BusinessHours.ALL_DAYS, hours.getDayOfWeeks());
        Assertions.assertEquals(allDaysAsSet, hours.getDayOfWeeks());
        Assertions.assertEquals(start, hours.getStart().get());

        hours = new BusinessHours(start, end, BusinessHours.ALL_DAYS);
        Assertions.assertNotSame(BusinessHours.ALL_DAYS, hours.getDayOfWeeks());
        Assertions.assertEquals(allDaysAsSet, hours.getDayOfWeeks());
        Assertions.assertEquals(start, hours.getStart().get());
        Assertions.assertEquals(end, hours.getEnd().get());

        Assertions.assertEquals(5, BusinessHours.class.getDeclaredConstructors().length, "There are untested constructors!");
    }

    @Test
    void testToJson() {
        LocalTime start = LocalTime.of(5, 0);
        LocalTime end = start.plusHours(1);
        BusinessHours hours = new BusinessHours(start, end, BusinessHours.ALL_DAYS);

        JsonObject object = hours.toJson();

        Assertions.assertEquals(start.toString(), object.getString("start"));
        Assertions.assertEquals(end.toString(), object.getString("end"));

        JsonArray array = object.get("dow");
        Set<Integer> days = new HashSet<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            days.add((int) array.getNumber(i));
        }
        Assertions.assertEquals(Arrays.stream(BusinessHours.ALL_DAYS).map(DayOfWeek::getValue).collect(Collectors.toSet()), days);
    }


    @Test
    void testEmptyToJson() {
        BusinessHours hours = new BusinessHours(null, null, (DayOfWeek[]) null);

        JsonObject object = hours.toJson();

        Assertions.assertEquals("00:00", object.getString("start"));
        Assertions.assertEquals("1.00:00", object.getString("end"));

        Assertions.assertEquals(0, object.<JsonArray>get("dow").length());
    }

}
