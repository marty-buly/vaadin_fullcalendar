package org.vaadin.stefan.fullcalendar;

import org.junit.jupiter.api.*;

@Disabled // not yet working
public class FullCalendarBuilderTest {

    public static final String EVENT_LIMIT = "eventLimit";
    private FullCalendarBuilder builder;

    @BeforeAll
    static void beforeAll() {
        TestUtils.initVaadinService(FullCalendarTest.FULL_CALENDAR_HTML);
    }

    @BeforeEach
    public void init() {
        builder = FullCalendarBuilder.create();
    }

    @Test
    void testBuildingDefaultInstance() {
        FullCalendar build = builder.build();

        Assertions.assertEquals(FullCalendar.class, build.getClass());
        Assertions.assertFalse(Boolean.parseBoolean(build.getElement().getProperty(EVENT_LIMIT)));
    }

    @Test
    void testFailOnUsingScheduler() {
        builder = builder.withScheduler();
        Assertions.assertThrows(FullCalendarBuilder.ExtensionNotFoundException.class, () -> builder.build());
    }

    void testEntryLimitFalseOnNegativeNumber() {
        builder = builder.withEntryLimit(-1);

        Assertions.assertFalse(Boolean.parseBoolean(builder.build().getElement().getProperty(EVENT_LIMIT)));
    }

    void testEntryLimitFalseOnZero() {
        builder = builder.withEntryLimit(0);

        Assertions.assertFalse(Boolean.parseBoolean(builder.build().getElement().getProperty(EVENT_LIMIT)));
    }

    void testEntryLimit() {
        builder = builder.withEntryLimit(17);

        Assertions.assertEquals(17, Integer.parseInt(builder.build().getElement().getProperty(EVENT_LIMIT)));
    }
}
