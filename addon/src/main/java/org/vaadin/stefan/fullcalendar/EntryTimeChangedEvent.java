/*
 * Copyright 2018, Stefan Uebe
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.vaadin.stefan.fullcalendar;

import elemental.json.JsonObject;

/**
 * This specialized version of the entry changed event gives additional information about the changed time as
 * a delta instance. Please note that the entry data will be updated automatically. Also the delta gives only
 * information about the start time. The end time might have changed independently given by the client.
 */
public class EntryTimeChangedEvent extends EntryChangedEvent {
    private final Delta startDelta;
    private final Delta endDelta;

    /**
     * New instance. Awaits the changed data object for the entry plus the json object for the delta information.
     * @param source source component
     * @param fromClient is from client
     * @param jsonEntry json object with changed data
     * @param jsonStartDelta json object with start delta information
     * @param jsonEndDelta json object with end delta information
     */
    public EntryTimeChangedEvent(FullCalendar source, boolean fromClient, JsonObject jsonEntry, JsonObject jsonStartDelta, JsonObject jsonEndDelta) {
        super(source, fromClient, jsonEntry);
        this.startDelta = Delta.fromJson(jsonStartDelta);
        this.endDelta = Delta.fromJson(jsonEndDelta);
    }

    /**
     * Returns the start delta information. Please note, that the entry itself already is up-to-date, so there is no need
     * to apply the delta on it.
     * @return delta of start
     */
    public Delta getStartDelta() {
        return startDelta;
    }

    /**
     * Returns the end delta information. Please note, that the entry itself already is up-to-date, so there is no need
     * to apply the delta on it.
     * @return delta of end
     */
    public Delta getEndDelta() {
        return endDelta;
    }
}
