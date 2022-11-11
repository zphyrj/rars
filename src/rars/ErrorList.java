/*
 * Decompiled with CFR 0.152.
 */
package rars;

import java.util.ArrayList;
import rars.ErrorMessage;
import rars.Globals;

public class ErrorList {
    private ArrayList<ErrorMessage> messages = new ArrayList();
    private int errorCount = 0;
    private int warningCount = 0;
    public static final String ERROR_MESSAGE_PREFIX = "Error";
    public static final String WARNING_MESSAGE_PREFIX = "Warning";
    public static final String FILENAME_PREFIX = " in ";
    public static final String LINE_PREFIX = " line ";
    public static final String POSITION_PREFIX = " column ";
    public static final String MESSAGE_SEPARATOR = ": ";

    public ArrayList<ErrorMessage> getErrorMessages() {
        return this.messages;
    }

    public boolean errorsOccurred() {
        return this.errorCount != 0;
    }

    public boolean warningsOccurred() {
        return this.warningCount != 0;
    }

    public void add(ErrorMessage errorMessage) {
        this.add(errorMessage, this.messages.size());
    }

    public void add(ErrorMessage errorMessage, int n) {
        if (this.errorCount > this.getErrorLimit()) {
            return;
        }
        if (this.errorCount == this.getErrorLimit()) {
            this.messages.add(new ErrorMessage(null, errorMessage.getLine(), errorMessage.getPosition(), "Error Limit of " + this.getErrorLimit() + " exceeded."));
            ++this.errorCount;
            return;
        }
        this.messages.add(n, errorMessage);
        if (errorMessage.isWarning()) {
            ++this.warningCount;
        } else {
            ++this.errorCount;
        }
    }

    public int errorCount() {
        return this.errorCount;
    }

    public int warningCount() {
        return this.warningCount;
    }

    public boolean errorLimitExceeded() {
        return this.errorCount > this.getErrorLimit();
    }

    public int getErrorLimit() {
        return Globals.maximumErrorMessages;
    }

    public String generateErrorReport() {
        return this.generateReport(false);
    }

    public String generateWarningReport() {
        return this.generateReport(true);
    }

    public String generateErrorAndWarningReport() {
        return this.generateWarningReport() + this.generateErrorReport();
    }

    private String generateReport(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (ErrorMessage errorMessage : this.messages) {
            if ((!bl || !errorMessage.isWarning()) && (bl || errorMessage.isWarning())) continue;
            stringBuilder.append(errorMessage.generateReport());
        }
        return stringBuilder.toString();
    }
}
