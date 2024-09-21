package com.example.mindkeep.config;

public class RoutesConfig {
    
    public static class API {
        public static final String NOTES = "/api/notes";
        public static final String NOTE = "/api/note/{noteId}";
    }

    public static class UI {
        public static final String ROOT = "/";
        public static final String NOTES = "/notes";
    }
}
