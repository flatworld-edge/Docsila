package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for handling file upload operations in tests
 */
public class FileUploadHelper {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "temp-uploads";
    private static final String TEST_DATA_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "testData";

    /**
     * Create upload directory if it doesn't exist
     */
    private static void ensureUploadDirExists() {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("✓ Created upload directory: " + UPLOAD_DIR);
        }
    }

    /**
     * Create a unique PDF file with timestamp
     * @param baseFileName Base name for the file (without extension)
     * @return Absolute path to the created PDF file
     */
    public static String createUniquePDF(String baseFileName) {
        try {
            ensureUploadDirExists();

            // Generate unique filename with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String uniqueFileName = baseFileName + "_" + timestamp + ".pdf";
            String filePath = UPLOAD_DIR + File.separator + uniqueFileName;

            // Create a minimal valid PDF file without any external library
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                StringBuilder pdf = new StringBuilder();
                pdf.append("%PDF-1.4\n");
                pdf.append("1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");
                pdf.append("2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");
                pdf.append("3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] >>\nendobj\n");
                pdf.append("xref\n0 4\n");
                pdf.append("0000000000 65535 f \n");
                pdf.append("0000000009 00000 n \n");
                pdf.append("0000000058 00000 n \n");
                pdf.append("0000000115 00000 n \n");
                pdf.append("trailer\n<< /Size 4 /Root 1 0 R >>\n");
                pdf.append("startxref\n190\n%%EOF\n");
                fos.write(pdf.toString().getBytes());
            }

            System.out.println("✓ Created unique PDF: " + uniqueFileName);
            System.out.println("   Path: " + filePath);

            return filePath;

        } catch (IOException e) {
            System.out.println("✗ Error creating unique PDF: " + e.getMessage());
            throw new RuntimeException("Failed to create unique PDF file", e);
        }
    }

    /**
     * Check if a file is a PDF
     * @param filePath Path to the file
     * @return true if file is PDF, false otherwise
     */
    public static boolean isPDF(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }
        return filePath.toLowerCase().endsWith(".pdf");
    }

    /**
     * Get the path to a test data file
     * @param fileName Name of the file in testData folder
     * @return Absolute path to the test data file
     */
    public static String getTestDataFilePath(String fileName) {
        String filePath = TEST_DATA_DIR + File.separator + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            throw new RuntimeException("Test data file not found: " + filePath);
        }

        System.out.println("✓ Test data file found: " + filePath);
        return filePath;
    }

    /**
     * Extract file name from full path
     * @param fullPath Full file path
     * @return File name only (without path)
     */
    public static String getFileName(String fullPath) {
        if (fullPath == null || fullPath.isEmpty()) {
            return null;
        }
        File file = new File(fullPath);
        return file.getName();
    }

    /**
     * Alias for getFileName for backward compatibility
     */
    public static String extractFileName(String fullPath) {
        return getFileName(fullPath);
    }

    /**
     * Delete a file
     * @param filePath Path to the file to delete
     * @return true if deleted successfully, false otherwise
     */
    public static boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                boolean deleted = file.delete();
                if (deleted) {
                    System.out.println("✓ Deleted file: " + filePath);
                } else {
                    System.out.println("⚠ Could not delete file: " + filePath);
                }
                return deleted;
            }
            return false;
        } catch (Exception e) {
            System.out.println("✗ Error deleting file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Clean up all files in the temp upload directory
     */
    public static void cleanupTempUploads() {
        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (uploadDir.exists() && uploadDir.isDirectory()) {
                File[] files = uploadDir.listFiles();
                if (files != null) {
                    int deletedCount = 0;
                    for (File file : files) {
                        if (file.isFile() && file.delete()) {
                            deletedCount++;
                        }
                    }
                    System.out.println("✓ Cleaned up " + deletedCount + " temp upload files");
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ Error cleaning up temp uploads: " + e.getMessage());
        }
    }

    /**
     * Get file size in bytes
     * @param filePath Path to the file
     * @return File size in bytes, or -1 if error
     */
    public static long getFileSize(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return file.length();
            }
            return -1;
        } catch (Exception e) {
            System.out.println("✗ Error getting file size: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Check if file exists
     * @param filePath Path to the file
     * @return true if file exists, false otherwise
     */
    public static boolean fileExists(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }
        File file = new File(filePath);
        return file.exists();
    }
}
