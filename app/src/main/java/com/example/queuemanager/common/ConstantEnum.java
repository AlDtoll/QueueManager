package com.example.queuemanager.common;

/**
 * Списко констант, которые используются в приложении
 */
public enum ConstantEnum {
    BASE_URL("https://jsonplaceholder.typicode.com/"),
    TAG("MyTag");
    private String code;

    ConstantEnum(String code) {
        this.code = code;
    }

    /**
     * Получение enum по соответствующему коду
     *
     * @param code код
     * @return enum
     */
    public static ConstantEnum of(String code) {
        for (ConstantEnum constant : values()) {
            if (constant.code.equalsIgnoreCase(code)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("такой константы нет");
    }

    public String getCode() {
        return code;
    }
}
