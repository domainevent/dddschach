package com.iks.dddschach.rest.providers;

/**
 * Fehler-Codes, die mittels Json an den Client übertragen werden. Beispiel:
 * <pre>
 *     {
 *         "error code":"INVALID_MOVE"
 *         "INVALID_MOVE":"a1-b4"
 *     }
 * </pre>
 */
public enum ErrorCode {

    TIMEOUT,
    INVALID_MOVE,
    INVALID_GAMEID;

    /**
     * Key, unter dem der Fehler-Code (TIMEOUT, INVALID_MOVE, ...) im Json-Objekt gespeichert wird.
     */
    public final static String ERROR_CODE_KEY = "error code";
}
