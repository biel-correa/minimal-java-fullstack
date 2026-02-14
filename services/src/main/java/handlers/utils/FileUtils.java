package handlers.utils;

public final class FileUtils {
    private FileUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Extracts the file extension from a filename, including the dot.
     * Returns empty string if no extension is found.
     *
     * @param filename the filename to extract extension from
     * @return the file extension (with dot) in lowercase, or empty string
     */
    public static String getFileExtension(String filename) {
        if (filename == null || filename.isBlank()) {
            return "";
        }

        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "";
        }

        return filename.substring(lastDotIndex).toLowerCase();
    }
}

