package fr.byteCode.erp.persistance.util.errors;

public final class ApiErrors {
    /* Generic codes [20000-20099] */
    public static final int LABEL_MIN_LENGTH = 20001;
    public static final int ENTITY_NOT_FOUND = 20000;
    public static final int ENTITY_REFERENCED = 20002;
    public static final int ENTITY_FIELD_NOT_VALID = 20003;
    public static final int INVALID_FORMAT_EXCEPTION = 20004;
    /* Journal codes [20400-20499] */
    public static final int JOURNAL_CODE_EXISTS = 20400;
    public static final int JOURNAL_CODE_LENGTH = 20401;
    public static final int JOURNAL_MISSING_PARAMETERS = 20402;
    public static final int JOURNAL_NO_JOURNAL_A_NEW = 20403;
    public static final int JOURNAL_NOT_FOUND = 20404;
    public static final int JOURNAL_LABEL_EXISTS = 20405;
}
