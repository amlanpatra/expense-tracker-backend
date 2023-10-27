package org.amlan.expensetracker.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonMapper {

    public static String asJson(Object o) {
        String jsonValue = null;
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            jsonValue = ow.writeValueAsString(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonValue;
    }
}
