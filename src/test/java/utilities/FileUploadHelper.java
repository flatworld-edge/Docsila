package utilities;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUploadHelper {

    private static final String TEST_DATA_PATH = System.getProperty("user.dir")
            + "\\src\\test\\resources\\testData\\";
    private static final String TEMP_UPLOAD_PATH = System.getProperty("user.dir")
            + "\\test-output\\temp-uploads\\";

    /**
     * Creates a unique file by copying the base file with a timestamp-based name.
     * Works for both PDF and non-PDF files (e.g., .txt, .docx, .jpg)
     *
     * @param baseFileName - Base file name (e.g., "asset_gift_letter.pdf" or "simple.txt")
     * @return Full absolute path of the newly created unique file
     */
    public static String createUniqueFile(String baseFileName) {
        try {
            // Validate base file exists
            String baseFilePath = TEST_DATA_PATH + baseFileName;
            File baseFile = new File(baseFilePath);
            if (!baseFile.exists()) {
                throw new RuntimeException("Base file not found at: " + baseFilePath);
            }

            // Extract file extension
            String extension = "";
            int dotIndex = baseFileName.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = baseFileName.substring(dotIndex);
            }

            // Generate unique filename with timestamp + milliseconds
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String uniqueFileName = "TestDoc_" + timestamp + extension;

            // Create temp directory if not exists
            File tempDir = new File(TEMP_UPLOAD_PATH);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
                System.out.println("Created temp upload directory: " + TEMP_UPLOAD_PATH);
            }

            // Copy file with new unique name
            String uniqueFilePath = TEMP_UPLOAD_PATH + uniqueFileName;
            Files.copy(Paths.get(baseFilePath),
                    Paths.get(uniqueFilePath),
                    StandardCopyOption.REPLACE_EXISTING);

            System.out.println("✓ Created unique file: " + uniqueFileName);
            System.out.println("   Full path: " + uniqueFilePath);

            return uniqueFilePath;

        } catch (Exception e) {
            System.out.println("✗ Error creating unique file: " + e.getMessage());
            throw new RuntimeException("Failed to create unique file", e);
        }
    }

    /**
     * Creates a unique PDF file - wrapper for backward compatibility
     */
    public static String createUniquePDF(String baseFileName) {
        return createUniqueFile(baseFileName);
    }

    /**
     * Get the absolute path of a file directly from testData (without copying)
     * Used for uploading existing files to test duplicate upload scenario
     *
     * @param fileName - File name in testData folder (e.g., "asset_gift_letter.pdf")
     * @return Full absolute path of the file
     */
    public static String getTestDataFilePath(String fileName) {
        String filePath = TEST_DATA_PATH + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("Test data file not found: " + filePath);
        }
        System.out.println("✓ Test data file path: " + filePath);
        return filePath;
    }

    /**
     * Extracts just the filename from a full path
     */
    public static String getFileName(String fullPath) {
        return new File(fullPath).getName();
    }

    /**
     * Validates if the file is a PDF
     */
    public static boolean isPDF(String filePath) {
        return filePath != null && filePath.toLowerCase().endsWith(".pdf");
    }

    /**
     * Cleans up temporary upload files after test execution
     */
    public static void cleanupTempUploads() {
        try {
            File dir = new File(TEMP_UPLOAD_PATH);
            if (dir.exists() && dir.isDirectory()) {
                File[] files = dir.listFiles();
                if (files != null) {
                    int count = 0;
                    for (File file : files) {
                        if (file.isFile() && file.delete()) {
                            count++;
                        }
                    }
                    System.out.println("✓ Cleaned up " + count + " temporary upload file(s)");
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ Warning: Could not clean temp uploads: " + e.getMessage());
        }
    }
}
