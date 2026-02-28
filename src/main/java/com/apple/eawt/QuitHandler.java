package com.apple.eawt;

/**
 * Minimal stub for Processing 3 on modern macOS JDKs where com.apple.eawt is absent.
 * Processing uses this to hook the macOS quit handler.
 */
public interface QuitHandler {
    void handleQuitRequestWith(AppEvent.QuitEvent e, QuitResponse response);
}
